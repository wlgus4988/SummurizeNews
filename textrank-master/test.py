# -*- coding: utf-8 -*-

import torch
from transformers import GPT2LMHeadModel
import sys
sys.path.append('C:/Users/USER/Desktop/KoGPT2-master/kogpt2-base-v2')

sents = ' '
inputArgs = sys.argv
for i in range(1, len(inputArgs)):
    text = sys.argv[i]
    sents = sents + ' ' + text

model = GPT2LMHeadModel.from_pretrained('C:/Users/USER/Desktop/KoGPT2-master/kogpt2-base-v2')

input_ids = tokenizer.encode(sents)
gen_ids = model.generate(torch.tensor([input_ids]),
        max_length=128,
        repetition_penalty=2.0,
        pad_token_id=tokenizer.pad_token_id,
        eos_token_id=tokenizer.eos_token_id,
        bos_token_id=tokenizer.bos_token_id,
        use_cache=True)

generated = tokenizer.decode(gen_ids[0,:].tolist())
print(generated)