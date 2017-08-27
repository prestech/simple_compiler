# Simple_compiler
This is a simulated compiler for compiling and running a set of "GUI" instruction. I will call this language "J-GUI" i.e the set of instuctions targeted by this compiler.

## J-GUI 
J-GUI, like any other language, has specific Syntax and Tokens. This syntax is described using BNF as follows

##### gui ::= Window String '(' NUMBER ', ' NUMBER ')' layout widgets End '-'

##### layout ::= Layout layout_type ':'

##### Layout_type ::= Flow | Grid '(' NUMBER ', ' NUMBER ' [ ',' NUMBER ', ' NUMBER ] ')'

##### widgets ::= widget widgets | widget

##### widget ::= Button STRING ';' | Group radio_buttons End ';' | Label STRING ';' | Panel layout widgets End ';' | Textfield NUMBER ';'

##### radio_buttons ::= radio_button radio_buttons | radio_button

##### radio_button ::= Radio STRING ';'

