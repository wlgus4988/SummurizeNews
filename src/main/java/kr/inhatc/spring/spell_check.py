# -*- coding: utf-8 -*-
import unittest
from hanspell import spell_checker
from hanspell.constants import CheckResult
from textwrap import dedent as trim
import sys

#sent = "맞춤법 틀리면 외 않되? 하마트면 큰일날뻔"

sents = ' '
inputArgs = sys.argv
for i in range(1, len(inputArgs)):
    # i is a number, from 1 to len(inputArgs)-1
    text = sys.argv[i]
    sents = sents + ' ' + text
spelled_sent = spell_checker.check(sents)

hanspell_sent = spelled_sent.checked
print(hanspell_sent)
