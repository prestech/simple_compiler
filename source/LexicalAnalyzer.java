/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *LexicalAnalyzer breaks down the users instructions into individual strings and
 * put them into tokens objects, by using the TokenAndLexeme class, as specified by the 
 * production
 * 
 * The tokens are then put in a queue so that the can be processed in the future, as 
 * they are read.
 * 
 * author PRESLEY M
 */

public class LexicalAnalyzer {
        
    //the scanner is used for reading file
    private Scanner scanner;
    
    //LinkedList is the queue object used in storing the token
    private LinkedList<TokenAndLexeme> sourceCodeQueue;
   
    
    //this keeps track of the number of quotes around a string
    //It reset to 0 each time count is 2
    private int countOfQuotes = 0;
    
    //TokenAndLexeme is used to create Token object
    private TokenAndLexeme tokenAndLexeme ;

    //constructor initializes the field variables 
    public LexicalAnalyzer(String fileName) throws FileNotFoundException {
        tokenAndLexeme = new TokenAndLexeme(Token.NOTHING, null);
        scanner =  new Scanner(new BufferedReader(new FileReader(fileName)));
        sourceCodeQueue = new LinkedList();
    }//constructor ends
    
    
    /**
     * This method is called to separate a joint lexeme which is intended to be seperate
     * for example; Grid(3,4); When pass to it, it will be separated to Grid ( 3 , 4 )
     * @param sourceCodeLexeme
     * @throws SyntaxErrorException 
     */
    public void seperateJointLexemes(String sourceCodeLexeme) throws SyntaxErrorException{
        char charArray[];

        //stores the lexeme into an array
        charArray = sourceCodeLexeme.trim().toCharArray();
        
        //reset string to an empty set, this would be used to
        //reassemble the strings
        sourceCodeLexeme  = "";
        

           int index =0;
           
           //read and process each character in the joint lexeme
            while(index < charArray.length){

                //if the characte equal to any of these, it would create a token out 
                //of it.
                if((charArray[index]==','|| charArray[index]==':' 
                   || charArray[index]=='"'|| charArray[index]==';' 
                   || charArray[index]==')'|| charArray[index]=='.')
                   ||charArray[index]=='('){

                     //if sourceCodeLexeme is not empty, create a token out of it 
                     if(!sourceCodeLexeme.equals("")){
                           tockenizeSourceCode(sourceCodeLexeme);
                           sourceCodeLexeme = "";
                     }
                     
                     //create a token with the punctuations{ (. , ; : " ) }
                     tockenizeSourceCode(""+charArray[index]);


                }else{
                    //else if character is not a punctuation continue rebuilding the 
                    //lexeme
                    sourceCodeLexeme = sourceCodeLexeme + charArray[index];
                }
                 //increment index
                 index++;   
           }
            
           //upon exit check if sourceCodeLexeme contains any string and create a Tocken out of it
           if(!sourceCodeLexeme.equals("")){

                tockenizeSourceCode(sourceCodeLexeme);
                sourceCodeLexeme = "";
            }

    }

    
    /**
     * This method simply read the source code from the file, create a out of and add it to the LinkedList
     * Queue as it is read.
     * @throws SyntaxErrorException 
     */
    public void readSourceCode() throws SyntaxErrorException {
        
        //holds the Strings read from the file
        String sourceCodeLexem;
        
        //if there is a next data to be read
        if(scanner.hasNext()){
           
           //read it and store it in the sourceCodeLexem
           sourceCodeLexem = scanner.next().trim();
           
           //if the string length is greate than 1 and contains any of the following 
           //character, then its migh contain a joint lexeme. So call the seperateJointLexemes()
           //to seperated the lexemes and add them as seperate tokens
           if((sourceCodeLexem.contains("(")||sourceCodeLexem.contains(")")
               ||sourceCodeLexem.contains(";")||sourceCodeLexem.contains("\"")
               ||sourceCodeLexem.contains(",")||sourceCodeLexem.contains(".")
               ||sourceCodeLexem.contains(":"))&& sourceCodeLexem.length() > 1){
                
                seperateJointLexemes(sourceCodeLexem);
                
            //else just create token object with the lexeme
           }else{
               tockenizeSourceCode(sourceCodeLexem);
                 
           }
           
           //it recursivel calls it self
           readSourceCode();
        }
        
    }//readSourceCode 
    
    //lastBracketType keeps track of the type of brace that was last read (OPEN or CLOSE)
    //so that the program can know what typ of brace to expect next
    Token lastBracketType;
    
    
    /**
     * This method receives the lexeme and create a Token object out of it using the
     * Syntax of the program
     * @param lexemes
     * @return
     * @throws SyntaxErrorException 
     */
    public TokenAndLexeme tockenizeSourceCode(String lexemes) throws SyntaxErrorException{
        
        //uses the switch - case conditional to seperate the lexem and create the various 
        //token-lexeme binds by calling and instance of TokenAndLexeme Class
        // With each case there is an if statement that check if the previous token was
        //not an OPEN_QUOTE. If it is an OPEN_QUOTE then it creates a STRING token instead
        switch (lexemes){

            case "Window" :
                 
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1 ){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else
                    tokenAndLexeme = new TokenAndLexeme(Token.WINDOW, lexemes);
                 break;
                
            case "Layout" :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE)|| countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else tokenAndLexeme = new TokenAndLexeme(Token.LAYOUT, lexemes);

                 break;
            case "Button" :
                if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else
                    tokenAndLexeme = new TokenAndLexeme(Token.BUTTON, lexemes);

                 break;
            case "Group" :
                if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                else tokenAndLexeme = new TokenAndLexeme(Token.GROUP, lexemes);

                 break;
            case "Panel" :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else tokenAndLexeme = new TokenAndLexeme(Token.PANEL, lexemes);

                 break;
            case "Textfield" :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends'
                 else tokenAndLexeme = new TokenAndLexeme(Token.TEXTFIELD, lexemes);

                 break;
            case "Flow" :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else tokenAndLexeme = new TokenAndLexeme(Token.FLOW, lexemes);

                 break;
            case "Grid" :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else tokenAndLexeme = new TokenAndLexeme(Token.GRID, lexemes);

                 break;
            case "Label" :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else tokenAndLexeme = new TokenAndLexeme(Token.LABEL, lexemes);
                 break;
            case "Radio" :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else tokenAndLexeme = new TokenAndLexeme(Token.RADIO_BUTTON, lexemes);

                 break;

            case "(" :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else if(tokenAndLexeme.getToken().equals(Token.GRID)){
                         tokenAndLexeme = new TokenAndLexeme(Token.LEFT_BRACKET_FOR_GRID, lexemes);
                         lastBracketType = Token.LEFT_BRACKET_FOR_GRID;
                         
                 }else {
                         tokenAndLexeme = new TokenAndLexeme(Token.LEFT_BRACKET_FOR__WINDOW_SIZE, lexemes);
                         lastBracketType = Token.LEFT_BRACKET_FOR__WINDOW_SIZE;
                  }
                 
                 break;

            case ")" :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE)|| countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else if(lastBracketType.equals(Token.LEFT_BRACKET_FOR_GRID)){
                         tokenAndLexeme = new TokenAndLexeme(Token.RIGHT_BRACKET_FOR_GRID, lexemes);
                 }else tokenAndLexeme = new TokenAndLexeme(Token.RIGHT_BRACKET_FOR__WINDOW_SIZE, lexemes);

                 break;
                
            case ";" :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE)|| countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else tokenAndLexeme = new TokenAndLexeme(Token.SEMI_COLON, lexemes);

                 break;
                
            case "\"" :
                 ++countOfQuotes;
                 if(tokenAndLexeme.getToken().equals(Token.STRING) || 
                         tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE)){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.CLOSING_QUOTE, lexemes);
                     countOfQuotes = 0;
                  }else
                    tokenAndLexeme = new TokenAndLexeme(Token.OPENING_QUOTE, lexemes);

                 break;
            case "." :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else tokenAndLexeme = new TokenAndLexeme(Token.FULL_STOP, lexemes);

                 break;
            case "," :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else tokenAndLexeme = new TokenAndLexeme(Token.COMMA, lexemes);

                 break;
            case ":" :
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else tokenAndLexeme = new TokenAndLexeme(Token.COLON, lexemes);
                 break;
            case "End":
                 if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) || countOfQuotes==1){
                     
                     tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                     
                 }//if ends
                 else tokenAndLexeme = new TokenAndLexeme(Token.END, lexemes);
                 break;
                
            default:
                 if(countOfQuotes == 1){
                     if(tokenAndLexeme.getToken().equals(Token.OPENING_QUOTE) ||
                          tokenAndLexeme.getToken().equals(Token.STRING))
                         
                         tokenAndLexeme = new TokenAndLexeme(Token.STRING, lexemes);
                 }else
                     tokenAndLexeme = new TokenAndLexeme(Token.NUMBER, lexemes);
                 break;

      }//switch ends
           
         
        
                           
        //add token and lexeme to the queue
        sourceCodeQueue.add(tokenAndLexeme);
                

        //return the TokenAndLexeme Object
        return tokenAndLexeme;
    }
    

    
    //predictedTokenAndLexeme remove and return the predictedTokenAndLexeme
    public TokenAndLexeme nextTokenAndLexeme(){
        
        return sourceCodeQueue.pop();
    }
   
    
    
    //peek returns the first tokenAndLexeme but does not remove it
    public TokenAndLexeme peek(){
         
        return sourceCodeQueue.peek();
    }
    

    
    //hasNext returns true if the queue still have elements in it.
    public boolean hasNext(){
        if(sourceCodeQueue.isEmpty()){
            return false;
            
        }else return true;
        
    }//hasNext() ends
    
  
}


/**
 * This class represents a TokenAndLexem object
 * @author asohm
 */
class TokenAndLexeme {

    private Token token;
    private String lexeme;
    
    //constructer recieve a Token input and a String lexem
    //and initializes then
    public TokenAndLexeme(Token token, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {

        return "< "+token+", "+ lexeme+" >";
    }
 
    //return the token
    public Token getToken() {
        return token;
    }
    
    //returns the lexeme of the object
    public String getLexeme() {
        return lexeme;
    }
    
    
  
}//tokenAndLexeme ends


/**
 * This enum class list the Tokens for the production
 * @author asohm
 */
enum Token {
    WINDOW, LAYOUT, FLOW, GRID, BUTTON, GROUP,
    LABEL, PANEL, RADIO_BUTTON, TEXTFIELD, 
    
    NUMBER,STRING, LEFT_BRACKET_FOR__WINDOW_SIZE, RIGHT_BRACKET_FOR__WINDOW_SIZE, 
    
    LEFT_BRACKET_FOR_GRID, RIGHT_BRACKET_FOR_GRID,
    
    SEMI_COLON, FULL_STOP, END, COLON, OPENING_QUOTE, CLOSING_QUOTE, COMMA,
    NOTHING;
   
}



/**
 * This class defines the Exception to be thrown 
 * if a syntax occurs
 */
 class SyntaxErrorException extends Exception{
   private String message;
   public SyntaxErrorException(String  message)
   {
      this.message = message;
   } 

    @Override
    public String toString() {
        return message; 
    }
}
