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

public class ExpressionEvaluator{

  public static  ArrayList<Expression> expressions = new ArrayList<Expression>();

  public static void main(String args[]){
    String end;
    Scanner userInput = new Scanner(System.in);
    BufferedReader file;
    String currentLine = "";

    if(args.length != 1){
      System.out.println("Usage: ExpressionEvaluator data.txt.");
      System.exit(-1);
    }
    try{
      file = new BufferedReader(new FileReader(args[0]));

      for(int i = 0; i < 26; i++){
        currentLine = file.readLine().trim().toUpperCase();
        if(currentLine.contains("TRUE")){
          Expression.setAtom(Character.toString(currentLine.charAt(0)), "true");
        }
        else if(currentLine.contains("FALSE")){
          Expression.setAtom(Character.toString(currentLine.charAt(0)), "false");
        }
        else{
          System.out.println("Error in Atoms");
        }
      }

      for(int i = 0; i < Expression.atoms.length; i++){
        System.out.printf("%c =  %s \n", i+65, Expression.atoms[i]);
      }

      currentLine = file.readLine();
      while(currentLine != null){
        try{
          Expression newExpression = new Expression(currentLine);
          expressions.add(newExpression);
          currentLine = file.readLine();
          System.out.println("Expression = " + newExpression);
        }
        catch(ParseError error){
          System.out.println(error);
        }

        do{
          System.out.println("Do you want to continue? \n Enter Yes or No (y/n):");
          end = userInput.nextLine();
          if(end.charAt(0) == 'n' || end.charAt(0) == 'N'){
            System.exit(-1);
          }
        }while(end.charAt(0) != 'y' && end.charAt(0) != 'Y');

        currentLine = file.readLine();
      }
    }
    catch (IOException error){
      System.out.println(error);
    }

    System.out.println("End of file. Press the enter key to exit.");
    end = userInput.nextLine();
    System.exit(-1);
  }
}
