import os, subprocess, sys, datetime, signal, shutil
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("--input_dir", type=str, required=True, help="path to input video")
parser.add_argument("--output_dir", type=str, required=True, help="path to output video")

def preexec(): # Don't forward signals.
    os.setpgrp()
    
def mycall(cmd, block=False):
    if not block:
        return subprocess.Popen(cmd)
    else:
        return subprocess.Popen(cmd, preexec_fn = preexec)
    
def folder_check(path):
    try_num = 1
    oripath = path[:-1] if path.endswith('/') else path
    while os.path.exists(path):
        print("Delete existing folder " + path + "?(Y/N)")
        decision = input()
        if decision == "Y":
            shutil.rmtree(path, ignore_errors=True)
            break
        else:
            path = oripath + "_%d/"%try_num
            try_num += 1
            print(path)
    
    return path

args = parser.parse_args()

dirstr = args.output_dir
input_dir = args.input_dir # the place to save the results
testpre = ['calendar'] # the test cases
if (not os.path.exists(dirstr)): os.mkdir(dirstr)
# run these test cases one by one:
for nn in range(len(testpre)):
    cmd1 = ["python3", "main.py",
        "--cudaID", "0",            # set the cudaID here to use only one GPU
        "--output_dir",  dirstr,    # Set the place to put the results.
        "--summary_dir", os.path.join(dirstr, 'log/'), # Set the place to put the log. 
        "--mode","inference", 
        "--input_dir_LR", input_dir,   # the LR directory
        #"--input_dir_HR", os.path.join("./HR/", testpre[nn]),  # the HR directory
        # one of (input_dir_HR,input_dir_LR) should be given
        "--output_pre", '', # the subfolder to save current scene, optional
        "--num_resblock", "16",  # our model has 16 residual blocks, 
        # the pre-trained FRVSR and TecoGAN mini have 10 residual blocks
        "--checkpoint", './model/TecoGAN',  # the path of the trained model,
        "--output_ext", "png"               # png is more accurate, jpg is smaller
    ]
    mycall(cmd1).communicate()    
