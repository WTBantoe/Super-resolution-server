import cv2
import os
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('--input', type=str)
parser.add_argument('--output', type=str)
args = parser.parse_args()


input_img_path = args.input
output_video = args.output
fps = 2
imglist = os.listdir(input_img_path)
for item in imglist:
    if item.endswith('.png') or item.endswith('jpg'):
        path = input_img_path + '/' + item
        img = cv2.imread(path)
        size = []
        size.append(img.shape[1])
        size.append(img.shape[0])
        size = tuple(size)
        break
maxframe = 0
for item in imglist:
    if item.endswith('.png') or item.endswith('.jpg'):
        first = int(item.split('_')[1].split('.')[0])
        if first > maxframe:
            maxframe = first
print(maxframe)
video = cv2.VideoWriter(output_video, cv2.VideoWriter_fourcc('I', '4', '2', '0'), fps, size)
for i in range(maxframe):
    path = input_img_path + '/' + 'output_' + format(i + 1, '04d') + 'png'
    print(path)
    img = cv2.imread(path)
    video.write(img)
video.release()