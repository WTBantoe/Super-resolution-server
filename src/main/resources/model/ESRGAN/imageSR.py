import sys
import os.path as osp
import cv2
import numpy as np
import torch
import RRDBNet_arch as arch
import argparse
import os

parser = argparse.ArgumentParser()
parser.add_argument('--input', type=str)
parser.add_argument('--output', type=str)
args = parser.parse_args()
device = torch.device('cpu')

test_img_folder = args.input
output_img_folder = args.output
model_path = sys.path[0] + '/models/RRDB_ESRGAN_x4.pth'
model = arch.RRDBNet(3, 3, 64, 23, gc=32)
model.load_state_dict(torch.load(model_path), strict=True)
model.eval()
model = model.to(device)
print('Model path {:s}. \nTesting...'.format(model_path))
idx = 0
filelist = os.listdir(test_img_folder)
resultlist = []
for i in filelist:
    if i.endswith('.png') or i.endswith('jpg'):
        resultlist.append(i)
for filename in resultlist:
    idx += 1
    path = test_img_folder + '/' + filename
    filename = filename.split('.')[0]
    print(idx, filename)
    # read images
    img = cv2.imread(path, cv2.IMREAD_COLOR)
    img = img * 1.0 / 255
    img = torch.from_numpy(np.transpose(img[:, :, [2, 1, 0]], (2, 0, 1))).float()
    img_LR = img.unsqueeze(0)
    img_LR = img_LR.to(device)

    with torch.no_grad():
        output = model(img_LR).data.squeeze().float().cpu().clamp_(0, 1).numpy()
    output = np.transpose(output[[2, 1, 0], :, :], (1, 2, 0))
    output = (output * 255.0).round()
    output_file = output_img_folder + '/' + filename + '_rlt.png'
    cv2.imwrite(output_file, output)


