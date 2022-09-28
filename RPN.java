import java.io.*;
import java.util.*;


class RPN{

    /**
     * @author Henrique Rebelo
     */
    public enum TokenType {

      // Literals.
      NUM,

      // Single-character tokens for operations.
      MINUS, PLUS, SLASH, STAR,
      
      EOF

    }

    public static class Token {

      public final TokenType type; // token type
      public final String lexeme; // token value
    
      public Token (TokenType type, String value) {
        this.type = type;
        this.lexeme = value;
      }
    
      @Override
      public String toString() {
        return "Token [type=" + this.type + ", lexeme=" + this.lexeme + "]";
      }
    }

    /**
    *
    */


    public static void main(String[] args) throws Exception{
        File inputFile = new File("input.txt");
        boolean validInput = true;
        Scanner inputReader = new Scanner(inputFile);
        Stack<Token> stack = new Stack<Token>();
        LinkedList<Token> inputHolder = new LinkedList<Token>();

        while (inputReader.hasNextLine() && validInput) {
            String inputText = inputReader.nextLine().strip();
            Token token;

            if (inputText.equals("-")){
                 token = new Token(TokenType.MINUS, inputText);
            }

            else if (inputText.equals("+")){
                token = new Token(TokenType.PLUS, inputText);
            }
            
            else if (inputText.equals("/")){
                token =new Token(TokenType.SLASH, inputText);
            }

            else if (inputText.equals("*")){
                token =new Token(TokenType.STAR, inputText);
            }
            
            else if (inputText.chars().allMatch( Character::isDigit )){
                token = new Token(TokenType.NUM, inputText);
            }
            
            else {
                token = new Token(TokenType.EOF, inputText);
                validInput = false;
                System.out.print("Error: Unexpected character: "); System.out.print(inputText);
            }

            inputHolder.add(token);
            
            if (validInput && (token.type.equals(TokenType.PLUS) || token.type.equals(TokenType.MINUS) || token.type.equals(TokenType.STAR) || token.type.equals(TokenType.SLASH))){
              int value1 = Integer.parseInt(stack.pop().lexeme);
              int value0 = Integer.parseInt(stack.pop().lexeme);
              
              if (token.type.equals(TokenType.PLUS)) {
                stack.push(new Token(TokenType.NUM, String.valueOf(value0 + value1)));
              }

              else if (token.type.equals(TokenType.MINUS)) {
                stack.push(new Token(TokenType.NUM, String.valueOf(value0 - value1)));
              }

              else if (token.type.equals(TokenType.STAR)) {
                stack.push(new Token(TokenType.NUM, String.valueOf(value0 * value1)));
              }

              else if (token.type.equals(TokenType.SLASH)) {
                stack.push(new Token(TokenType.NUM, String.valueOf(value0 / value1)));
              }
            }

            else if (validInput) {
              stack.push(token);
            }
        }

        if (validInput) {
          while (!inputHolder.isEmpty()) {
              System.out.println(inputHolder.getFirst());
              inputHolder.removeFirst();
          }

          System.out.println();
          System.out.print("Saida: "); System.out.print(stack.pop());

        }

    };
}