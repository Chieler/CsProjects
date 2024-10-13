from transformers import pipeline
import sys;
import json;
import base64
from PIL import Image
from io import BytesIO

json_string = sys.argv[1]
data = json.loads(json_string)
image_data = base64.b64decode(data['image'])
image = Image.open(BytesIO(image_data))

vqa_pipeline = pipeline("visual-question-answering", model="Salesforce/blip-vqa-base", device='cpu')
question = data['question']
result = vqa_pipeline(image, question)
print(f"Question: {question}")
print(f"Answer: {result}")

# for key, value in data.items():
#     question =key
#     context = value
#     result = qa_pipeline(question=question, context=context)
#     print(f"Answer: {result['answer']}")
