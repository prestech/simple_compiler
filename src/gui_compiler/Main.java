/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_compiler;

import java.io.FileNotFoundException;

/**
 *
 * @author asohm
 */
public class Main {
    
    
        
    /**************************************************************************************
     * 
     * @param args 
     */
    public static void main(String[] args)  {
       
            try {
                
                //create a LexicalAnalyzer and specify the name of the sourcefile as parameter
                LexicalAnalyzer lexer = new LexicalAnalyzer("sourceFile.txt");
                
                //read the information from the source file
                lexer.readSourceCode();

                //Create a DescentParser Parser to execute tokens from the Lexical analyzer
                DescentParser paser = new DescentParser(lexer);
                
                //construct the GUI base on the tokens
                paser.buildGui();
              
             //catches exceptions 
            }catch (SyntaxErrorException ex) {
                System.err.println(ex.toString());
            }
            catch (FileNotFoundException ex) {
                System.err.println("sourceCodeFile.txt cannot be found ");
            }
            catch (Exception ex) {
                System.err.println("Check source file, problem has not been identified ");
            }
       
    }//Mian Ends
    
}//Mian Clas Ends 
