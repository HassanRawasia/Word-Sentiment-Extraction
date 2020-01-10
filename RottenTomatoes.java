/*
NAME: Hassan Rawasia
PROGRAM: ROTTEN TOMATOES - MOVIE REVIEW ANALYSIS
DATE: February 22, 2019
VERSION: 1.0
 */
package edu.hdsb.gwss.hassan.ics4u.u1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author 1rawasiahas
 */
public class RottenTomatoes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //VARIABLES
        Scanner userInput = new Scanner(System.in); //import scanner for user input
        String word; //stores the word to search for

        //SET UP MENU, WHICH THEN DETERMINES WHICH METHOD TO CALL
        switch (RottenTomatoes.menu()) {
            case 1:
                //First "formats" the program for design purposes, then gets the word which is then passed to the required method
                System.out.println("_______________________________________________________");
                System.out.println("WORD REVIEW" + "\n");
                //Ask for the word to search for
                System.out.print("Enter a word: ");
                word = userInput.nextLine();
                word = word.toLowerCase();
                word = " " + word + " "; //adds a space before and after to ensure the program searches for the individual word, and doesn't count it when it is part of a larger word
                RottenTomatoes.wordReview(word);
                break;
            case 2:
                RottenTomatoes.sentenceReview();
                break;
            case 3:
                RottenTomatoes.multipleWordScore();
                break;
            case 4:
                RottenTomatoes.multipleWordScoreSort();
                break;
            case 5:
                System.exit(0);
        }

    }

    public static int menu() {
        Scanner userInput = new Scanner(System.in); //import scanner for user input
        int choice = 0; //creates variable to store user choice
        //display menu
        System.out.println("What would you like to do?"
                + "\n1: Get the score of a word"
                + "\n2: Get the average score of words in a file"
                + "\n3: Find the highest/lowest scoring words in a file"
                + "\n4: Sort words in a file into positive.txt and negative.txt"
                + "\n5: Exit the program" + "\n ");

        System.out.print("Enter a number 1-5: ");
        choice = userInput.nextInt(); //get user choice
        while (choice < 1 || choice > 5) {
            System.out.print("Invalid choice. Enter a number again 1-5: ");
            choice = userInput.nextInt(); //get user choice
        }
        System.out.println(" "); //blank line for spacing
        return choice;

    }

    public static void wordReview(String word) throws FileNotFoundException, IOException {
        //SCANNERS AND VARIABLES
        String[] args = new String[] {"123"};
        File data = new File(".\\data\\movie.review\\MovieReviews.txt");
        Scanner input = new Scanner(data); //import scanner to read from file
        int runningScore = 0; //adds up the total score for the word
        int wordCounter = 0; //counts the number of times the word appears

        //Processing - searches for word and calculates score
        while (input.hasNextLine()) {
            String line = input.nextLine().toLowerCase(); //retrieves a line from the file and converts it to lower case
            if (line.contains(word)) {
                wordCounter++;
                if (line.startsWith("0")) {
                    runningScore = runningScore + 0;
                } else if (line.startsWith("1")) {
                    runningScore++;
                } else if (line.startsWith("2")) {
                    runningScore = runningScore + 2;
                } else if (line.startsWith("3")) {
                    runningScore = runningScore + 3;
                } else if (line.startsWith("4")) {
                    runningScore = runningScore + 4;
                }
            }
        }

        double averageScore = ((double) runningScore / wordCounter);
        System.out.println(word + " appears " + wordCounter + " times.");
        if (runningScore > 0) { //makes sure there is a score other than zero to avoid an error
            System.out.println("Average Score: " + averageScore + "\n");
        } else {
            System.out.println("Average Score: " + runningScore + "\n");
        }
        input.close();
        RottenTomatoes.main(args);
    }

    public static int[] wordReviewMethod(String word) throws FileNotFoundException { //this is the method used to search a word in part 2-4
        //SCANNERS AND VARIABLES
        File data = new File(".\\data\\movie.review\\MovieReviews.txt");
        Scanner input = new Scanner(data); //import scanner to read from file
        int runningScore = 0; //adds up the total score for the word
        int wordCounter = 0; //counts the number of times the word appears

        //Processing - searches for word and calculates score
        while (input.hasNextLine()) {
            String line = input.nextLine().toLowerCase(); //retrieves a line from the file and converts it to lower case
            if (line.contains(word)) {
                wordCounter++;
                if (line.startsWith("0")) {
                    runningScore = runningScore + 0;
                } else if (line.startsWith("1")) {
                    runningScore++;
                } else if (line.startsWith("2")) {
                    runningScore = runningScore + 2;
                } else if (line.startsWith("3")) {
                    runningScore = runningScore + 3;
                } else if (line.startsWith("4")) {
                    runningScore = runningScore + 4;
                }
            }
        }
        int[] scoreAndCounter = new int[2];
        scoreAndCounter[0] = wordCounter;
        scoreAndCounter[1] = runningScore;
        input.close();
        return scoreAndCounter;
    }

    public static void sentenceReview() throws FileNotFoundException, IOException {
        //SCANNERS AND VARIABLES
        String[] args = new String[] {"123"}; //ignore this, is only needed to call menu again
        double runningScore = 0; //adds up the total score for the word
        double wordCounter = 0; //counts the number of times the word appears
        String word; //stores the word to search for

        //Formats the program, and gets the name of the file
        System.out.println("_______________________________________________________");
        System.out.println("SENTENCE REVIEW" + "\n");
        //Ask for the file to find the average score of
        String fileName = RottenTomatoes.getFileName(); //call method to get file name
        File givenFile = new File(fileName); //***EDIT
        Scanner source = new Scanner(givenFile);

        //Processing - Grabs one word at a time from the given file, then counts their scores and occurences
        while (source.hasNextLine()) {
            word = source.nextLine();
            word = " " + word + " ";
            int[] scoreAndCounter = RottenTomatoes.wordReviewMethod(word); //calls method to calculate word score
            wordCounter += scoreAndCounter[0];
            runningScore += scoreAndCounter[1];
        }
        //Calculate average score of the file
        double averageScore = (runningScore / wordCounter);
        //Output
        System.out.println("The average score of words in this file is: " + averageScore);
        if (averageScore > 2.01) {
            System.out.println("The overall sentiment is positive." + "\n");
        } else if (averageScore < 1.99) {
            System.out.println("The overall sentiment is negative." + "\n");
        } else {
            System.out.println("The overall sentiment is neutral." + "\n");
        }
        source.close();
        RottenTomatoes.main(args);
    }

    public static void multipleWordScore() throws FileNotFoundException, IOException {
        //SCANNERS AND VARIABLES
        String[] args = new String[] {"123"}; //ignore this, is only needed to call menu again
        double score = 0; //variable that stores the score of the word
        double mostPositive = 0; //stores the most positive word's score
        String mostPositiveName = ""; //stores the name of the most positive word
        double mostNegative = 4; //stores the most negative word's score
        String mostNegativeName = ""; //stores the name of the most negative word
        String word; //stores the word to search for

        //Formats the program, and gets the name of the file
        System.out.println("_______________________________________________________");
        System.out.println("MULTIPLE WORD SCORE" + "\n");
        //Ask for the file to find the average score of
        String fileName = RottenTomatoes.getFileName(); //call method to get file name
        File givenFile = new File(fileName); //***EDIT
        Scanner source = new Scanner(givenFile);

        /*PROCESSING - Calls a method to calculate the score one word at a time, if the score of the latest 
        word is more negative or more positive than the current most positive/negative, it will take that spot*/
        while (source.hasNextLine()) {
            word = source.nextLine();
            word = " " + word + " ";
            int[] scoreAndCounter = RottenTomatoes.wordReviewMethod(word); //calls method which returns the runningScore and word count
            score = ((double) scoreAndCounter[1] / scoreAndCounter[0]); //calcualtes average score, index 1 in the array is the running score, index 0 is the word count
            if (score < mostNegative) {
                mostNegative = score;
                mostNegativeName = word;
            }
            if (score > mostPositive) {
                mostPositive = score;
                mostPositiveName = word;
            }
        }
        System.out.println("The most positive word, with a score of " + mostPositive + " is " + mostPositiveName);
        System.out.println("The most negative word, with a score of " + mostNegative + " is " + mostNegativeName + "\n");
        source.close();
        RottenTomatoes.main(args);
    }

    public static void multipleWordScoreSort() throws FileNotFoundException, IOException {
        //SCANNERS AND VARIABLES
        String[] args = new String[] {"123"}; //ignore this, is only needed to call menu again
        double score = 0; //variable that stores the score of the word
        String word; //word that is being searched for
        //FILES TO SORT INTO
        File positive = new File (".\\data\\movie.review\\positive.txt");
        File negative = new File (".\\data\\movie.review\\negative.txt");

        //FILE WRITER
        BufferedWriter writer = new BufferedWriter(new FileWriter(positive));
        BufferedWriter writerTwo = new BufferedWriter(new FileWriter(negative));
        
        //Formats the program, and gets the name of the file
        System.out.println("_______________________________________________________");
        System.out.println("MULTIPLE WORD SCORE SORT" + "\n");
        //Ask for the file to find the average score of
        String fileName = RottenTomatoes.getFileName(); //call method to get file name
        File givenFile = new File(fileName); //***EDIT
        Scanner source = new Scanner(givenFile);

        //PROCESSING
        while (source.hasNextLine()) {
            word = source.nextLine();
            word = " " + word + " ";
            int[] scoreAndCounter = RottenTomatoes.wordReviewMethod(word);
            score = ((double) scoreAndCounter[1] / scoreAndCounter[0]);
            if (score > 2.1) {
                writer.write(word + "\n");
            }
            if (score < 1.9) {
                writerTwo.write(word + "\n");
            }
        }
        writer.close();
        writerTwo.close();
        RottenTomatoes.main(args);
    }
    
    public static String getFileName () {
        Scanner userInput = new Scanner(System.in); //import scanner for user input
       System.out.print("Enter the name of the file: ");
       String fileName = (".\\data\\movie.review\\" + userInput.nextLine()); 
       return fileName;
    }
}
