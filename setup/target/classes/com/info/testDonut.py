import sys
import json
import base64
from io import BytesIO
from PIL import Image
import torch
from transformers import DonutProcessor, VisionEncoderDecoderModel

json_string = sys.argv[1]
data = json.loads(json_string)
image_data = base64.b64decode(data['image'])
image = Image.open(BytesIO(image_data)) 
question = data['question']

#Donut setup
processor = DonutProcessor.from_pretrained("naver-clova-ix/donut-base-finetuned-rvlcdip")
model = VisionEncoderDecoderModel.from_pretrained("naver-clova-ix/donut-base-finetuned-rvlcdip")
device = "cuda" if torch.cuda.is_available() else "cpu"
model.to(device)
decoder_input_ids=processor.tokenizer(question, add_special_tokens=False, return_tensors="pt").input_ids
def process_IQ(image, question):
    prompt = f"<s_docvqa><s_question>{question}</s_question><s_answer>"
    pixel_values = processor(image, return_tensors="pt").pixel_values
    outputs = model.generate(
        pixel_values.to(device),
        max_length=512,
        early_stopping=False,
        pad_token_id=processor.tokenizer.pad_token_id,
        eos_token_id=processor.tokenizer.eos_token_id,
        use_cache=True,
        num_beams=1,
        bad_words_ids=[[processor.tokenizer.unk_token_id]],
        return_dict_in_generate=True,
    )

    sequence = processor.batch_decode(outputs.sequences)[0]
    sequence = sequence.replace(processor.tokenizer.eos_token, "").replace(processor.tokenizer.pad_token, "")
    sequence = sequence.replace("</s_answer></s_docvqa>", "").split("</s_question>")[-1].strip()
    return sequence

if __name__ =="__main__":
   #Input setup

    answer = process_IQ(image, question)
    print(f"Question: {question}")
    print(f"Answer: {answer}")

