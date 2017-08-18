/* author PRESLEY M.
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.FileNotFoundException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * Parser class receive tokens from the LexicalAnalyzer class and call different
 * recursive functions to build a Graphic User Interface as specified by the 
 * User in the sourceCode text file.
 */
public class DescentParser {
    
    //declares an instance of TokenAndLexem class
    private TokenAndLexeme tokenAndLexeme;
    
    //Declares a reference to JFrame 
    private JFrame window;
    
    //Declares a reference to the lexical analyzer class
    private LexicalAnalyzer lexer;
        
        
    
    
    /**
     * This parser constructor receives an object of LexicalAnalyzer
     * which is used to access the processed tokens and lexeme.
     * @param lexer 
     */ 
    DescentParser(LexicalAnalyzer lexer){
        this.tokenAndLexeme = null;
        this.lexer = lexer;
        
    }//Parser

    /**
     * This method is responsible for putting all parts of the GUI
     * together as instructed by the user in the sourceCodeFile.txt
     * It recursively calls it self until all the source code is  has been 
     * read and executed
     */
    public void buildGui() throws SyntaxErrorException{
        
        //check if there is more lexeme to be read
        if(lexer.hasNext()){
            //get the token
            tokenAndLexeme = lexer.nextTokenAndLexeme();
            
            //Build the GUI according to the instructions of the token
            switch(tokenAndLexeme.getToken()){
                
                case WINDOW:
                    window = createWindow();
                    break;
                   
                //special case: each time a panel is call there is a call to other widgets
                case PANEL:
                    window.add(createPanel());
                    break;
                    
                case TEXTFIELD :
                    window.add(createTextField());
                    break;
               
            }//switch ends
            
            //recursively call the method 
            buildGui();
        }//while ends
        
          if(!tokenAndLexeme.getToken().equals(Token.FULL_STOP)){
                 throw new SyntaxErrorException("SyntaxErrorException The Source code must be Terminated with \" END \" followed by a FULLSTOP\".\" ");

          }
    /*
      set the window (JFRAME) properties after the GUI has been build
    */
        //make window visible 
        window.setVisible(true);
        
        //make program terminates upon window exiting
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }//build_gui ends
    
    
    /**
     * This method returns a JFrame object. It calls other method to 
     * add GUI components into the JFrame as instructed by the USER
     * @return 
     */
    public JFrame createWindow() throws SyntaxErrorException{
        
        //create a new JFrame object
        JFrame frame = new JFrame();
        
        //holds frame title
        String frameTitle = " ";
        
        //this variable would be used to store information about the 
        //windows dimensions
        int dimension[] = new int[2];
        
        //this would be used to acces the information about window's
        //width and height from the dimension[] array
        int index = 0; 
        
        //Check if the windows name is writed inside quotation marks as
        //a string
        
        if(lexer.peek().getToken().equals(Token.OPENING_QUOTE)){
            lexer.nextTokenAndLexeme();
            
           
            if(lexer.peek().getToken().equals(Token.STRING)){
                
                 //read all the strings with the Quotes
                while(lexer.peek().getToken().equals(Token.STRING)){

                     frameTitle = frameTitle + lexer.nextTokenAndLexeme().getLexeme() + " ";

                }
                 
                frame.setTitle(frameTitle);
            }
            
            //If the user specify an empty title
            else if(lexer.peek().getToken().equals(Token.CLOSING_QUOTE)){
                frame.setTitle("");
            }//else if ends

            //check if the string is enclosed with a closing quote
            if(lexer.peek().getToken().equals(Token.CLOSING_QUOTE)){
                lexer.nextTokenAndLexeme();
              //read all information to the closing brace
            }else{
                throw new SyntaxErrorException("SyntaxErrorException : Names of Labels Should be withing Quotes: CLOSING QUOTES need ");
            }
        } else  throw new SyntaxErrorException("SyntaxErrorException :: Names of Labels Should be withing Quotes: OPENING QUOTES need ");

        
        
        //Before getting information about the dimensions of the window, check if
       //the information is enclosed within its brackets
        if(lexer.peek().getToken().equals(Token.LEFT_BRACKET_FOR__WINDOW_SIZE)){
            
             //read all the token until the right bracket is reach
             while(!(lexer.peek().getToken().equals(Token.RIGHT_BRACKET_FOR__WINDOW_SIZE))){

               //move to the next tokenAndLexeme within the braces
               tokenAndLexeme = lexer.nextTokenAndLexeme();


               //retrieve only the numbers fron with the braces
               if(tokenAndLexeme.getToken().equals(Token.NUMBER)){
                   
                  dimension[index] = Integer.parseInt(tokenAndLexeme.getLexeme());

                  index++;
              }//if ends
            }//while ends 
             
             //throw and exception if there is less than two parameter values; height and width
             if(index == 2){
                frame.setSize(dimension[0], dimension[1]);
             }else
                 throw new SyntaxErrorException(" The window's dimensions, height and width, has to be specified within brackets");
            
        }//if ends 
        
        //moves to the closing right bracket of the windows size parameter
        lexer.nextTokenAndLexeme();
        
        
        /*
         Retrieve and process information about the window's layout
        */
        if(lexer.peek().getToken().equals(Token.LAYOUT)){
   
             lexer.nextTokenAndLexeme();
             
            //move to the layout_type parameter and call the createLayoutType method
            //to create layout
             
             if(lexer.peek().getToken().equals(Token.GRID)){
                 
                 //call the createGridLayout() to create windows layout
                 frame.setLayout(createGridLayout());
                 

             }else if (lexer.peek().getToken().equals(Token.FLOW)){
                 
                 //call the createFlowLayout() to create the windows layout
                 frame.setLayout(createFlowLayout());

             }
             
             
        }
        
        //return the frame (window)
        return frame;
    }
    
   
     /**
      * This method creates a GridLayout for container class (Panel, and window)
      * as specified by the user. It has a return Type of GridLayout
      * @return 
      */
     private GridLayout createGridLayout() throws SyntaxErrorException{

        //this variable will be used to store the 
        //grid numeric information for a grid layout
        int gridInfo[]= new int[4];;
        
        //Declares a reference to GridLayout
        GridLayout gridLayout = null;
         
        //move to the next token (LEFT_BRACKET_FOR_GRID)
        //to get the grid information 
        lexer.nextTokenAndLexeme();
            

         //look if the Grid iformation; number of row and columns, are within
         //Braces
         if(lexer.peek().getToken().equals(Token.LEFT_BRACKET_FOR_GRID)){

              //index is used to access the  gridInfo array
              int index = 0;

              //read all information to the closing brace
              while(!(lexer.peek().getToken().equals(Token.RIGHT_BRACKET_FOR_GRID))){

                  //move to the next tokenAndLexeme within the braces
                  tokenAndLexeme = lexer.nextTokenAndLexeme();

                  //retrieve only the numbers fron with the braces
                  if(tokenAndLexeme.getToken().equals(Token.NUMBER)){

                      gridInfo[index] = Integer.parseInt(tokenAndLexeme.getLexeme());

                      index++;
                  }//if ends
              }//while ends
              
              //move to the right brace 
              lexer.nextTokenAndLexeme();

              //make sure that after the Flow there is a COLON
              if(!lexer.peek().getToken().equals(Token.COLON)){
                 throw new SyntaxErrorException("SyntaxErrorException : A COLON (:) is needed after Grid's Parameters have been set ");
              }//if ends
              
              //the Grid parameter can containe either 2 or 4 integers. 
              if(index==4||index==2){
                  
                //create a new GridLayout with the information collected from the parameter
                gridLayout =  new GridLayout(gridInfo[0], gridInfo[1], gridInfo[2], gridInfo[3]);
              }else{
                 throw new SyntaxErrorException(" The number of rows and columns (and optionally, the vertical and horizontal gat)"
                         + "\n needs to be passed in as GRID parameters.");
              }
         }//if ends

        //return the GridLayout object
        return gridLayout;
    }//createGridLayout() ends
    
     
     //creates and return a flow layout object
     public FlowLayout createFlowLayout() throws SyntaxErrorException{
         
        //move to the COLON
        lexer.nextTokenAndLexeme();
       
        //make sure that after the Flow there is a COLON
        if(!lexer.peek().getToken().equals(Token.COLON)){
                 throw new SyntaxErrorException("SyntaxErrorException : A COLON (:) is needed after Flow layout  has been specied ");
        }//if ends
        
        return new FlowLayout();
                 
     }//createFlowLayout ends
    

    
   /**
    * This method creates and returns a panel component. It recursively 
    * calls itself to create nest panel as instructed by the user
    */
    public JPanel  createPanel() throws SyntaxErrorException{
       
        //declares a reference to JPanel
        JPanel panel = null;
        
        //creates a reference to LayoutMananger
        LayoutManager layout = null;
        
        //declares a reference to TokenAndLexeme 
        TokenAndLexeme tokenAndLexeme;
        
        //Check if the user has specified the Layout syntax 
        if(lexer.peek().getToken().equals(Token.LAYOUT)){
            
            //move to into the Layout tokenAndLexemeAndLexeme
            //to see layout type
              lexer.nextTokenAndLexeme();
              
              //checkc the layout type and create a new layout (Flow or Grid)
              //by the calling the createGridLayout() or createFlowLayout()
              if(lexer.peek().getToken().equals(Token.GRID)){
                 
                  layout = (createGridLayout());
                  
                  
              }else if (lexer.peek().getToken().equals(Token.FLOW)){
                 
                  layout = (createFlowLayout());
                  
              }              
             
             //create a new panel and pass the specified layout type
             //into the constructor
             panel = new JPanel(layout);
   
            
            //check if the user has specified any widgets to be add in the panel
            //add all the specified widgets and add it to the panel.
            while(!lexer.peek().getToken().equals(Token.END)){
                 tokenAndLexeme = lexer.nextTokenAndLexeme();

                 //check the widget type, calls the widget's method and add it
                 //to the panel
                 if(tokenAndLexeme.getToken().equals(Token.BUTTON)||
                    tokenAndLexeme.getToken().equals(Token.TEXTFIELD)||
                    tokenAndLexeme.getToken().equals(Token.LABEL)||
                    tokenAndLexeme.getToken().equals(Token.GROUP) ||
                    tokenAndLexeme.getToken().equals(Token.PANEL)){
   

                     
                     switch (tokenAndLexeme.getToken()){
                         case BUTTON:
                            panel.add(createButton());
                             break;
                         case TEXTFIELD :
                            panel.add(createTextField());
                             break;
                         case LABEL :
                           panel.add(createLabel());
                           break;
                         case GROUP :
                             ButtonGroup group = new ButtonGroup();
                             createRadioGroup(panel, group);
                             break;
                         case PANEL :
                           //recursively calls the createPanel method
                           panel.add(createPanel());
                            break;
                     }//switch ends

                 }//if ends
                 
            }//while ends
            
            //make sure that the panel's components terminates with the END token
            //else exception is thrown
            if(lexer.peek().getToken().equals(Token.END)){

                 lexer.nextTokenAndLexeme();
                
                //check to make sure the END token is followed by a SEMI_COLON
                if(!lexer.peek().getToken().equals(Token.SEMI_COLON))
                 throw new SyntaxErrorException("The keyword \"END\" followed by a SEMI_COLON (;) is needed to terminate Panel ");
            }else
                 throw new SyntaxErrorException("SyntaxErrorException :The JPanel must be Termited with \" END \" followed by a SEMI-COLON\";\" ");
            

          }// if ends
        
        //return the panel object
        return panel;
               
    }//createPanel()
    
       
    
    
    
    
   /**
    * This method creates and returns JLabel object
    * @return 
    */
    public JLabel createLabel() throws SyntaxErrorException{
        
        //refernce the JLabel object
        JLabel jlabel = null;
        String stringOfLabel = "";
       
       //check if the name of the button is quoted 
        if(lexer.peek().getToken().equals(Token.OPENING_QUOTE)){
            
            //move to the string of the label
            lexer.nextTokenAndLexeme();
                        
            //check if the name of the button is inside the quote
            if(lexer.peek().getToken().equals(Token.STRING)){
               
                //read all the strings with the Quotes
                while(lexer.peek().getToken().equals(Token.STRING)){

                    stringOfLabel = stringOfLabel + lexer.nextTokenAndLexeme().getLexeme() + " ";
                    //set the title of the JFrame
                }
                 
                jlabel = new JLabel(""+stringOfLabel);
                
            }
            //if the user specify an empty string
            else if(lexer.peek().getToken().equals(Token.CLOSING_QUOTE)){
                 jlabel = new JLabel("");
            }

            //check if the closing quote exist
            if(lexer.peek().getToken().equals(Token.CLOSING_QUOTE)){

                lexer.nextTokenAndLexeme();
                
                //check to make sure the string is followed by a semi colon
                //else throw exception
                if(!lexer.peek().getToken().equals(Token.SEMI_COLON)){
                 throw new SyntaxErrorException(" A SEMI_COLON (;) is needed after Label's name is Specified ");
                }
            }else{
                throw new SyntaxErrorException("Names of Labels Should be withing Quotes: CLOSING QUOTES need ");
            }
        }//if ends
        else
                throw new SyntaxErrorException("SyntaxErrorException  :Names of Labels Should be withing Quotes: OPENING QUOTES need ");
        
        //return a JLabel object
        return jlabel;
    }//createLabel()
    
    
    /**
     * this method creates and return  a JButton
     */
    public JButton createButton() throws SyntaxErrorException{
       
       //reference a JButton object
       JButton button = null;
       
       //Holds button's string
       String stringOfButton="";
       
       //check if the name of the button is quoted 
        if(lexer.peek().getToken().equals(Token.OPENING_QUOTE)){
            
            lexer.nextTokenAndLexeme();

            //check if the name of the button is inside the quote
            if(lexer.peek().getToken().equals(Token.STRING)){
               //read all the strings with the Quotes
                while(lexer.peek().getToken().equals(Token.STRING)){

                    stringOfButton = stringOfButton + lexer.nextTokenAndLexeme().getLexeme() + " ";
                    //set the title of the JFrame
                }
                 
                button = new JButton(""+stringOfButton);
            } 
             //if the user specify an empty string, create a button with an
             //empty string
            else if(lexer.peek().getToken().equals(Token.CLOSING_QUOTE)){
                button = new JButton("");
            }//else if ends
            
            //if no string is specified, (including no empty string; an empty double quote)
            else{
                 throw new SyntaxErrorException("SyntaxErrorException  :Button's name needs to be Specified as a STRING ");
            }//else ends
            
            //check if the closing quote exist
            if(lexer.peek().getToken().equals(Token.CLOSING_QUOTE)){
                
                lexer.nextTokenAndLexeme();
                
                //check to make sure the string is followed by a semi colon
                //else throw exception
                if(!lexer.peek().getToken().equals(Token.SEMI_COLON)){
                 throw new SyntaxErrorException("SyntaxErrorException  :A SEMI_COLON (;) is needed after the Button's name is Specified ");
                }
                
            }else{
                System.out.println("Exception needed here For no CLOSING QUOTE AFTER BUTTON");
                throw new SyntaxErrorException("SyntaxErrorException  :Names of Buttons Should be withing Quotes: CLOSING QUOTES need ");
            }
        }
        else
                throw new SyntaxErrorException("SyntaxErrorException : Names of Buttons Should be withing Quotes: OPENING QUOTES need ");
        
        //return the JButton object
        return button;
        
    }//createButton() ends
    
    
    public JTextField createTextField() throws SyntaxErrorException{
        
        JTextField jTextField = null;
         if(lexer.peek().getToken().equals(Token.NUMBER)){

             int sizeOfField = Integer.parseInt(lexer.nextTokenAndLexeme().getLexeme());

             jTextField = new JTextField(sizeOfField);
             
             //if no semi_colon exist after the number
             if(lexer.peek().getToken().equals(Token.SEMI_COLON)){

             }else{
                 System.out.println("Need a Semi colon Exception needed here");
                 throw new SyntaxErrorException("SyntaxErrorException : A SEMI_COLON (;) is needed after the Texfield Size is Specified ");
            }
                          
          }//if ends
        
         //else if the user those not have size parameter
         else {
             throw new SyntaxErrorException("SyntaxErrorException : The size of the Texfield needs to be specified as an integer ");
         }
         //retun an object of JTextField()
         return jTextField;
    }//createTextField() ends
    
  
      
    
    /**
     * this method creates group of Radio Button objects. It receives two
     * parameter; JPanel, and ButtonGroup object. The JPanel parameter is the
     * container in which the Radio Button would be added. And the ButtonGroup
     * group radio buttons in the same group.
     * 
     */
    private void createRadioGroup(JPanel containerPanel, ButtonGroup group ) throws SyntaxErrorException{
        //reference to the JRadioButton object
        JRadioButton radioButton= null;
        
        //if the Group token is no followed by a COLON, throw an exception
        if(!lexer.peek().getToken().equals(Token.COLON)){
               throw new SyntaxErrorException("SyntaxErrorException  :A COLON \" : \" must follow the GROUP keyword");
         }
        
        lexer.nextTokenAndLexeme();
        
        //check if the user passed in instructions to create a radio 
        //button
        if(lexer.peek().getToken().equals(Token.RADIO_BUTTON)){
             
 
            //move to the string name of the radio button
            lexer.nextTokenAndLexeme();
             //call the createRadioButton() to create new RadioButton
             //and add them to a specified group
             createRadioButton(containerPanel, group);
            
            //after creating the RadioButton make sure the the End syntax
            //is specified by the user. else throw an exception 
            if(lexer.peek().getToken().equals(Token.END)){
                lexer.nextTokenAndLexeme();
                
                //check to make sure the string is followed by a semi colon
                //else throw exception
                if(!lexer.peek().getToken().equals(Token.SEMI_COLON)){
                   throw new SyntaxErrorException("SyntaxErrorException : No SEMI_COLON \";\" to terminate GROUP statement ");
                }
             
            }else{
                 throw new SyntaxErrorException("SyntaxErrorException  :No \"END \" Keyword at to indicate the end bound of GROUP ");
            }//else ends
            
        }//if ends
        
    }//createARadioButton ends
    
   
    /**
     * This method recursive creates a RadioButton object and adds it to a 
     * specified group and panel container.
     * @param containerPanel
     * @param group 
     */
    public void createRadioButton(JPanel containerPanel, ButtonGroup group) throws SyntaxErrorException{
       
       //create a radio button object
       JRadioButton radioButton = null;
       
       //holds the string of the radio
       String stringOfRadio ="";
       //move to the information about the radio button, the name,
       if(lexer.peek().getToken().equals(Token.RADIO_BUTTON)){
               lexer.nextTokenAndLexeme();
        }
        
            //Check if the name of the radio button is enclosed within Quotes
            if(lexer.peek().getToken().equals(Token.OPENING_QUOTE)){
                 lexer.nextTokenAndLexeme();

            

                //check if the next information is a string, name of the object
                if(lexer.peek().getToken().equals(Token.STRING)){
                    
                  
                    //read all Strings in the radio name
                    while(lexer.peek().getToken().equals(Token.STRING)){
                        
                       stringOfRadio = stringOfRadio + lexer.nextTokenAndLexeme().getLexeme() + " ";
                    //set the title of the JFrame
                    }
                   
                    //create a radio button bject, and add it to a container panel
                    radioButton = new JRadioButton(""+stringOfRadio);
                    containerPanel.add(radioButton);
                    
                    
                    //add radio buttons to a group
                    group.add(radioButton);
                    
                    
                    if(lexer.peek().getToken().equals(Token.CLOSING_QUOTE)){

                         lexer.nextTokenAndLexeme();
                        
                    }
                     
                    else{
                        throw new SyntaxErrorException("SyntaxErrorException  :Radio Button String needs a Closing Quotes ");
                     }//else ends                    
                    
                    
                }//if ends
                
                //if no string is specified,including no empty string; an empty double quote)
                else if(lexer.peek().getToken().equals(Token.CLOSING_QUOTE)){
                       radioButton = new JRadioButton("");
                       
                       lexer.nextTokenAndLexeme();
               }//else if ends

             }else{
                    
                     throw new SyntaxErrorException("SyntaxErrorException  :Radio Button String needs an OPENING QUOTE ");
             }
            
             //check if the instruction is termited with a semic colon, else throw and exception
             if(lexer.peek().getToken().equals(Token.SEMI_COLON)){

                    //move to the semi colon
                     lexer.nextTokenAndLexeme();
                     
             }else {
                   throw new SyntaxErrorException("SyntaxErrorException  :Radio Button String should be followed by a SEMI_COLON (;) ");
             }
                    
            
             //recursively call the createRadioButton as specified by the user
             if(lexer.peek().getToken().equals(Token.RADIO_BUTTON)){
                 createRadioButton(containerPanel, group);
             }
    }//createRadioButton() ends
    
    
    /**
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
       
    }
}

    
