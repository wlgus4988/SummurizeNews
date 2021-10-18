import torch
from transformers import GPT2LMHeadModel
import tokenizer
from transformers import PreTrainedTokenizerFast
import sys

tokenizer = PreTrainedTokenizerFast.from_pretrained("skt/kogpt2-base-v2",
  bos_token='</s>', eos_token='</s>', unk_token='<unk>',
  pad_token='<pad>', mask_token='<mask>')

model = GPT2LMHeadModel.from_pretrained('skt/kogpt2-base-v2')

sents = ' '
inputArgs = sys.argv
for i in range(1, len(inputArgs)):
    # i is a number, from 1 to len(inputArgs)-1
    text = sys.argv[i]
    sents = sents +' '+ text
#text = '피부의 조직이 괴사된다면 '
input_ids = tokenizer.encode(sents)
gen_ids = model.generate(torch.tensor([input_ids]),
                           max_length=150,
                           repetition_penalty=2.0,
                           pad_token_id=tokenizer.pad_token_id,
                           eos_token_id=tokenizer.eos_token_id,
                           bos_token_id=tokenizer.bos_token_id,
                           use_cache=True)
generated = tokenizer.decode(gen_ids[0,:].tolist())
print(generated)