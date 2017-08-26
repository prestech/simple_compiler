/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_compiler;

/**
 * This class represents a TokenAndLexem object
 * @author asohm
 */
public class TokenAndLexeme {

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
