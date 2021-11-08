import cv2 as cv
import argparse
import os

parser = argparse.ArgumentParser()
parser.add_argument('--input', type=str)
parser.add_argument('--output', type=str)
args = parser.parse_args()
video = cv.VideoCapture(args.input)
if not os.path.exists(args.output):
    os.mkdir(args.output)
count  = 1
while True:
    _, frame = video.read()
    if frame is None:
        break
    savepath = os.path.join(args.output,str(count) + '.png')
    cv.imwrite(savepath, frame)
    count += 1
video.release()