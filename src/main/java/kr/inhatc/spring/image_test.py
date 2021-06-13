import cv2
import sys
import pytesseract

pytesseract.pytesseract.tesseract_cmd = r"C:\Program Files\Tesseract-OCR\tesseract.exe"
image = cv2.imread(str(sys.argv[1]))

data = pytesseract.image_to_string(image, lang='kor+eng', config='-l kor --oem 3 --psm 4')
text = data.replace(" ", "")
print(text.replace(".", ". "))
