import google.generativeai as genai
import os
import sys
import json
import base64
from io import BytesIO
from PIL import Image

def init():
    genai.configure(api_key=os.environ["API_KEY"])
    model = genai.GenerativeModel("gemini-1.5-flash")
    return model

if __name__ =="__main__":
    json_string = sys.argv[1]
    data = json.loads(json_string)
    model = init()
    for key, value in data.items():
        question = key
        image = Image.open(BytesIO(base64.b64decode(value))) 
        result = model.generate_content(
        [image, "\n\n",question]
        )
        print(f"{result.text=}")