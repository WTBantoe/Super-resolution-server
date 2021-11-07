import sys
import os
import torch
import cv2
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("-i",required=False,type=str)
parser.add_argument("-o",required=False,type=str)
args = parser.parse_args()

def testAdd(a,b):
    return a+b

def testString(params):
    return 'pre'+params+'suf'

a=1
b=2

print("Hello World!")
print(testAdd(a,b))
print(args.i,args.o)