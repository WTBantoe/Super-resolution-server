import sys
import os
import torch
import cv2

def testAdd(a,b):
    return a+b

def testString(params):
    return 'pre'+params+'suf'

a=1
b=2

print("Hello World!")
print(testAdd(a,b))