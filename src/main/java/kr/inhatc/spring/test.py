# -*- coding: utf-8 -*-

import kss

import sys
sys.path.append('C:/Users/USER/Desktop/textrank-master')

from textrank import KeysentenceSummarizer


sents = ' '
inputArgs = sys.argv
for i in range(1, len(inputArgs)):
    # i is a number, from 1 to len(inputArgs)-1
    text = sys.argv[i]
    sents = sents + ' ' + text

characters = '"!?\n'

for x in range(len(characters)):
    sents = sents.replace(characters[x],"")
    
sts = []
for sent in kss.split_sentences(sents):
    sts.append(sent)

summarizer = KeysentenceSummarizer(
    tokenize = lambda x:x.split(),
    min_sim = 0.3,
    verbose = False
)
keysents = summarizer.summarize(sts, topk=2)
for _, _, sent in keysents:
    print(sent)