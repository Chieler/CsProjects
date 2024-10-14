import sys
import json
import base64
from io import BytesIO
from PIL import Image
import torch
from transformers import ViltProcessor, ViltForQuestionAnswering

# Load JSON input
json_string = sys.argv[1]
data = json.loads(json_string)
image_data = base64.b64decode(data['image'])
image = Image.open(BytesIO(image_data)) 
question = data['question']

# Setup device
device = "cuda" if torch.cuda.is_available() else "cpu"

# Initialize the model and processor
processor = ViltProcessor.from_pretrained("dandelin/vilt-b32-finetuned-vqa")
model = ViltForQuestionAnswering.from_pretrained("dandelin/vilt-b32-finetuned-vqa")
model.to(device)

def process_IQ(image, question):
    encoding = processor(image, question, return_tensors="pt").to(device)
    outputs = model(**encoding)
    logits = outputs.logits
    idx = logits.argmax(-1).item()
    print("Predicted answer:", model.config.id2label[idx])

if __name__ =="__main__":
    process_IQ(image, question)
