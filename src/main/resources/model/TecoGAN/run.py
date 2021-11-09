import os,sys
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("--input", type=str, required=True, help="path to input video")
parser.add_argument("--output", type=str, required=True, help="path to output video")

args = parser.parse_args()

dirstr = args.output
input_dir = args.input # the place to save the results
if (not os.path.exists(dirstr)): os.mkdir(dirstr)
# run these test cases one by one:
cmd2 = ["python", sys.path[0] +"/main.py",
        "--output_dir",  dirstr,
        "--summary_dir", os.path.join(dirstr, 'log/'), 
        "--input_dir_LR", input_dir,
        "--checkpoint", sys.path[0] +'/model/TecoGAN',
]
cmd1 = ''
for i in range(len(cmd2)):
        cmd1 = cmd1 + cmd2[i] + ' '
print(cmd1)
os.system(cmd1)
