# Simple_compiler
This is a simulated compiler for compiling and running a set of "GUI" instruction. I will call this language "J-GUI" i.e the set of instuctions targeted by this compiler.

## J-GUI Syntax via BNF                                                                                                              
J-GUI, like any other language, has specific Syntax and Tokens. This syntax is described using BNF as follows                        
                                                                                                                                     
##### gui ::= Window String '(' NUMBER ', ' NUMBER ')' layout widgets End '-'                                                        
                                                                                                                                     
##### layout ::= Layout layout_type ':'                                                                                              
                                                                                                                                     
##### Layout_type ::= Flow | Grid '(' NUMBER ', ' NUMBER ' [ ',' NUMBER ', ' NUMBER ] ')'                                            
                                                                                                                                     
##### widgets ::= widget widgets | widget                                                                                            
                                                                                                                                     
##### widget ::= Button STRING ';' | Group radio_buttons End ';' | Label STRING ';' | Panel layout widgets End ';' | Textfield NUMBER';                                                                                                                              
##### radio_buttons ::= radio_button radio_buttons | radio_button                                                                                                                                                                                                        
##### radio_button ::= Radio STRING ';'                                                                                              


### Summary Description of the BNF
The the sources code should begin with the <b>Window</b> toke while specifying the name (string), the size of the window and its layout. This is followed by any number of <b> widgets </b> in the syntax specified by J-GUI's BNF (see the examples source code below). The source code is terminated with <b> End. </b> notice the fullstop (.) at the end of "END". 

Failure to follow this rule of syntax will lead to a syntax error which will cause your program to throw an Exception and terminate. 

## Example Source Code 
Below is and example source code of a program written in J-GUI to be compiled and run by this compiler (GUI Compiler). Notice that this language does not have support for <b>Comment</b> yet. Commenting capabilties, both inline and block comments, will added in the near future.  <br></br>



Window  " Test Program " (500, 500) Layout Flow :

	Textfield 20;

	Panel Layout Flow : 
	Panel Layout Grid ( 4 , 4 ) :
		Button " 1 " ;
		Button " 2 " ;
		Button " 3 " ;
		Button " 4 " ;
		Button " 5 " ;
		Button " 6 " ;
		Button " 7 " ;
		Button " 8 " ;
		Button " 0 " ;
			
	 End;
	Panel Layout Grid (4 , 4):
		Label "Label1";
		Label "";
		Label "Label3";
		Label "Label4";
		Label "";
		Label "Label6";
		Label "Label7";
		Label "";
		Label "Label0";
			
	 End;
	Panel Layout Grid ( 4 ,4):
		Group:
		   Radio"1";
		   Radio"2";
		   Radio"3";
		   Radio"4";
		   Radio"5";
		   Radio"6";
		   Radio"7";
		   Radio"8";
		   Radio"0";
		End;
			
	 End;
    End;
 End. 
 </i>
 <br></br>
 
 #### Description of Source Code 
 
Ignore any information after the forward slashes “//” for there are used to explain the source code below. Note that this program cannot process the “//” as a comment specifier so do not copy and paste this code without removing all comments.

<b>[Window "Test Program" (500, 500) Layout Flow:]</b> creates a  500 x 500 window named “Test Program	<br></br>
<b>[Textfield 20;] </b> adds a text field  to the window, with sze 20 <br></br>
<b>[Panel Layout Flow]</b> Creates Panel (panel 1) with Flow layout. Notice that this panel contains three child panel; with all having a 4 by 4 grid layout. <b>These child panels can also contain child panels or other widgets with children, and the cycle continues...) </b>  <br></br>

	Panel Layout Grid ( 4 , 4 ) : //Creates a Panel 2, nested in panel 1, and with a Grid layout and Button Components
             //The Button can be specified as  {Button " 1 " ;}  or { Button"1";} . The white spaces between are ignored; see the Label components and Radio below.
		Button " 1 " ;
		Button " 2 " ;
		Button " 3 " ;
		Button " 4 " ;
		Button " 5 " ;
		Button " 6 " ;
		Button " 7 " ;
		Button " 8 " ;
		Button " 0 " ;		
	 End; //Ends the Panel scope. If this is not specified  a syntax exception will be thrown
	Panel Layout Grid (4 , 4): //Creates another Panel, panel 3 nested in panel 1, at the same level with Panel 2. Specify label components to be add to it. 
		Label "Label1";
		Label ""; //A label with an empty string (name) . The Quotes needs to be specified to show that the string is empty
		Label "Label3";
		Label "Label4";
		Label "";
		Label "Label6";
		Label "Label7";
		Label "";
		Label "Label0";		
	 End;

	Panel Layout Grid ( 4 ,4):  //Creates another Panel, panel 3 nested in panel 1, at the same level with Panel 2. Specify label components to be add to it. 
		Group: //the COLON must be specified as the start of the scope else a SyntaxErrorException will be thrown.
		   Radio"1";
		   Radio"2";
		   Radio"3";
		   Radio"4";
		   Radio"5";
		   Radio"6";
		   Radio"7";
		   Radio"8";
		   Radio"0";
		End;	
	 End;
  End;
 End. //The scope of the source code ends with the “END” and a Full stop “.”; if not specified an SyntaxErrorException will be thrown.

 #### Result of the Source Code 
 Do you really want to see the result of this source code ? All you have to do is download this project and run it. 
 
 ## QUESTIONS? NEED ASSISTANCE? 
 Leave your comment below and I will make sure to respond. Thank you. 
