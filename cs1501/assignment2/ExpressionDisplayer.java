/*
 *
 *     Author: Andrew Land
 *      Email: aml136#pitt.edu
 *        PS#: 3688776
 *       Date: 9/24/13
 * Assignment: 2
 *
 */

import java.util.*;
import java.io.*;

public class ExpressionDisplayer{

  public static void main(String args[]){
    String end;
    Scanner userInput = new Scanner(System.in);

    if(args.length != 1){
      System.out.println("Usage: ExpressionDisplayer data.txt.");
      System.exit(-1);
    }
    try{
      BufferedReader file = new BufferedReader(new FileReader(args[0]));
      String currentLine = "";

      currentLine = file.readLine();
      while(currentLine != null){
        try{
          Expression newExpression = new Expression(currentLine);
          newExpression.displayNormalized();
          currentLine = file.readLine();
          System.out.println("Expression = " + newExpression);
        }
        catch(ParseError error){
          System.out.println(error);
        }

        do{
          System.out.println("Do you want to keep going? \n Enter Yes or No (y/n):");
          end = userInput.nextLine();
          if(end.charAt(0) == 'n' || end.charAt(0) == 'N'){
            System.exit(-1);
          }
        }while(end.charAt(0) != 'y' && end.charAt(0) != 'Y');

        currentLine = file.readLine();
      }
    }
    catch (IOException error){
      System.out.println(error.toString());
    }

    System.out.println("End of file. Press the enter key to exit.");
    end = userInput.nextLine();
    System.exit(-1);
  }
}
