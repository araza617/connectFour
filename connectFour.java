/* Text based version of Connect Four between two players */

import java.util.Scanner;
import java.util.Arrays;

public class connectFour {
    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final String CURRENT_BOARD = "\nCurrent Board";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    
    public static void main(String[] args) {
        intro();
        Scanner keyboard = new Scanner(System.in);
        String[] player = getNames(keyboard);
        System.out.println("\nCurrent Board");
        String[][] gameBoard = initialGameBoard();
        playerOne(gameBoard, player, keyboard);
    }

    public static String[] getNames(Scanner keyboard) {
        String[] player = new String[2];
        System.out.print("Player 1 enter your name: ");
        player[0] = keyboard.next();
        keyboard.nextLine();
        System.out.print("Player 2 enter your name: ");
        player[1] = keyboard.next();
        return player;
    }

    // Creates the initial gameboard with periods. Sends off to printGameBoard method.
    public static String[][] initialGameBoard () {
        String[][] gameBoard = new String[ROWS][COLS];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = ". ";
            }
        }
        printGameBoard(gameBoard);
        return gameBoard;
    }

    // Takes the gameBoard as a paramter and simply prints it out with the column numbers on top
    public static void printGameBoard(String[][] gameBoard) {
        System.out.println("1 2 3 4 5 6 7  column numbers");
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                System.out.print(gameBoard[i][j]);
            }
            System.out.println();
        }
    }

    // This method handles everything related to player one.
    public static void playerOne(String[][] gameBoard, String[] player, Scanner keyboard) {
        System.out.println("\n" + player[0] + " it is your turn.\nYour pieces are the r's.");
        String prompt = (player[0] + ", enter the column to drop your checker: ");
        System.out.print(prompt);
        int results = getInt(keyboard, prompt, gameBoard);
        int result = checkInt(keyboard, prompt, gameBoard, results);
        createNewBoard(result, "r ", gameBoard);
        boolean cont = false;
        cont = checkForSpace(gameBoard, false);
        boolean win = false;
        win = checkForWin(gameBoard, result, player, false, "r ");
        if (cont) {
            System.out.println("The game is a draw.\n");
            System.out.println("Final Board");
            printGameBoard(gameBoard);
        }
        else if (win) {
            System.out.println(player[0]+" wins!!\n");
            System.out.println("Final Board");
            printGameBoard(gameBoard);
        }
        else {
            System.out.println(CURRENT_BOARD);
            printGameBoard(gameBoard);
            playerTwo(gameBoard, player, keyboard);
        }
    }

    // This method checks to see if the player has one through any of the different ways
    public static boolean checkForWin(String[][] gameBoard, int result, String[] player, boolean win, String pieces) {        
        int count = 0;
        // Checks for win up and down
        for (int i = 0; i <= 6; i++) {
            count = 0;
            for (int j = 5; j >= 0; j--) {                
                if (gameBoard[j][i] == pieces)
                    count++;
                if(count == 4){
                    return true;
                }
                else if(gameBoard[j][i] == "." || gameBoard[j][i] != pieces)
                    count = 0;
            }
        }

        // Checks for win left to right       
        for (int i = 5; i >= 0; i--) {
            count = 0;
            for (int j = 0; j <= 6; j++) {
                if (gameBoard[i][j] == pieces)
                    count++;
                if (count == 4){
                    return true;
                }
                else if(gameBoard[i][j] == ". " || gameBoard[i][j] != pieces)
                    count = 0;
            }
        }    

        // Checks for win bottom left to top right
        for (int i = 0; i <= 3; i++) {
            for (int j = 5; j >= 3; j--) {
                if (gameBoard[j-1][i+1] == gameBoard[j][i] && gameBoard[j-2][i+2] == gameBoard[j][i] && gameBoard[j-3][i+3] == gameBoard[j][i]
                    && gameBoard[j][i] == pieces) {
                    count=4; 
                    if (count == 4){
                        return true;
                    }
                    else if(gameBoard[j][i+1] != pieces){
                        count = 0;
                    } 
                }
            }            
        }

        // Checks for win bottom right to top left
        for (int i = 6; i >= 3; i--) {
            for (int j = 5; j >= 3; j--) {
                if (gameBoard[j-1][i-1] == gameBoard[j][i] && gameBoard[j-2][i-2] == gameBoard[j][i] && gameBoard[j-3][i-3] == gameBoard[j][i]
                    && gameBoard[j][i] == pieces) {
                    count=4;
                    if(count == 4){
                        return true;
                    }
                }
                else if(gameBoard[j][i-1] == ". " || gameBoard[j][i-1] != pieces) {
                    count = 0;
                } 
            }
        }
        return win;
    }

    // This method handles everything related to player two.
    public static void playerTwo(String[][] gameBoard, String[] player, Scanner keyboard) {
        System.out.println("\n" + player[1] + " it is your turn.\nYour pieces are the b's.");
        String prompt = (player[1] + ", enter the column to drop your checker: ");
        System.out.print(prompt);
        int results = getInt(keyboard, prompt, gameBoard);
        int result = checkInt(keyboard, prompt, gameBoard, results);
        createNewBoard(result, "b ", gameBoard);
        boolean cont = false;
        cont = checkForSpace(gameBoard, false);
        boolean win = false;
        win = checkForWin(gameBoard, result, player, false, "b ");
        if (cont) {
            System.out.println("The game is a draw.\n");
            System.out.println("Final Board");
            printGameBoard(gameBoard);
        }
        else if (win) {
            System.out.println(player[1]+" wins!!\n");
            System.out.println("Final Board");
            printGameBoard(gameBoard);
        }
        else {
            System.out.println(CURRENT_BOARD);
            printGameBoard(gameBoard);
            playerOne(gameBoard, player, keyboard);
        }
    }

    // This method checks to see if there is a draw if there is no space left
    public static boolean checkForSpace(String[][] gameBoard, boolean cont) {
        int drawCheck = 0;
        for (int i = 0; i <= 6; i++) {
            if ((gameBoard[0][i] == "r ") || (gameBoard[0][i] == "b ")){           
                drawCheck++;
                if(drawCheck == 7){
                    return true;
                }
            }                
        }
        return false;
    }

    // show the intro
    public static void intro() {
        System.out.println("This program allows two people to play the");
        System.out.println("game of Connect four. Each player takes turns");
        System.out.println("dropping a checker in one of the open columns");
        System.out.println("on the board. The columns are numbered 1 to 7.");
        System.out.println("The first player to get four checkers in a row");
        System.out.println("horizontally, vertically, or diagonally wins");
        System.out.println("the game. If no player gets fours in a row and");
        System.out.println("and all spots are taken the game is a draw.");
        System.out.println("Player one's checkers will appear as r's and");
        System.out.println("player two's checkers will appear as b's.");
        System.out.println("Open spaces on the board will appear as .'s.\n");
    }

    // prompt the user for an int. The String prompt will
    // be printed out. I expect key is connected to System.in.
    public static int getInt(Scanner keyboard, String prompt, String[][] gameBoard) {
        while(!keyboard.hasNextInt()) {
            String notAnInt = keyboard.next();
            System.out.println(notAnInt + " is not an integer.");
            System.out.print(prompt);
        }
        int result = keyboard.nextInt();
        keyboard.nextLine();
        return result;
    }

    // Checks to see if the int entered by the user is actually able to be used. If not, goes back to getInt() method.
    // Did not create the while loop as a seperate method because it causes userInput to remember the invalid int 
    public static int checkInt(Scanner keyboard, String prompt, String[][] gameBoard, int userInput) {
        while (userInput < 1 || userInput > 7) {
            System.out.println(userInput + " is not a valid column.");
            System.out.print(prompt);
            userInput = getInt(keyboard, prompt, gameBoard);
        }
        while (gameBoard[0][userInput - 1] == "r " || gameBoard[0][userInput - 1] == "b ") {
            System.out.println(userInput + " is not a legal column. That column is full");
            System.out.print(prompt);
            userInput = getInt(keyboard, prompt, gameBoard);
            while (userInput < 1 || userInput > 7) {
                System.out.println(userInput + " is not a valid column.");
                System.out.print(prompt);
                userInput = getInt(keyboard, prompt, gameBoard);
            }
        }
        return userInput;
    }

    // This creates the new board but does not print it out
    public static void createNewBoard(int result, String replace, String[][] gameBoard) {
        boolean cont = true;
        for(int i = 5; i >= 0;){
            if (gameBoard[i][result - 1] == ". ") {
                gameBoard[i][result - 1] = replace;
                return;
            }
            else
                i--;
        }
    }
}
