import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BattleClient {

  public static int turn = -1;
  public static void main(String[] args) {
    GameBoard playerBoard = new GameBoard();
    boolean winCondition = false;

    Scanner input = new Scanner(System.in); 
    System.out.println("Enter the Server IP Address");
    String ipaddress = input.nextLine();
    input.close();

    try {
      //connect to host
      System.out.println("Client Started");
      Socket soc = new Socket(ipaddress, 9806);
      clearScreen();

      BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
      PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));

      //get numebr of boats
      System.out.println("Waiting for host to set-up game");
      int boatsNumber = Integer.parseInt(in.readLine());
      System.out.println(boatsNumber);
      turn = 2;

      playerBoard.generateBoats(boatsNumber);

      //print boards
      System.out.println("\nPlayer Board: ");
      playerBoard.printBoard();
      System.out.println("\nEnemy Board: ");
      playerBoard.printEnemyBoard();


      while (turn > 0) {
        //turn 0 = win/lose, turn 1 = player 1, turn 2 = player 2
        if (turn == 1) {
          // input coordinate
          System.out.println("Enter Coordinates:");

          // check if coordinates are valid (A-H 0-9)
          boolean validInput = false;
          String coords = userInput.readLine();
          int xcoord = -1, ycoord = -1;

          while (!validInput) {
            //2 characters only
            if (coords.length() == 2 &&
              (coords.charAt(0) >= 65 && coords.charAt(0) <= 74) &&
              (coords.charAt(1) >= 48 && coords.charAt(1) <= 57)) {
              String y = coords.charAt(1) + "";
              xcoord = (int) coords.charAt(0) - 65;
              ycoord = Integer.parseInt(y);
              validInput = true;
            } else {
              coords = userInput.readLine();
            }
          }

          //send coordinates to client
          System.out.println("Shooting at: (" + xcoord + "," + ycoord + ")");
          out.println(coords);

          //receive response
          String hitMiss = in .readLine();
          boolean isHit = true;
          clearScreen();

          //convert string to boolean
          if (hitMiss.equals("false")) {
            isHit = false;
          } else if (hitMiss.equals("true")) {
            isHit = true;
          }

          playerBoard.returnHit(xcoord, ycoord, isHit);
          
          //check if win
          if (playerBoard.score == boatsNumber) {
            clearScreen();
            winCondition = true;
            turn = 0;
          } else {
            //print enemy boards again
            System.out.println("\nPlayer Board: ");
            playerBoard.printBoard();
            System.out.println("\nEnemy Board: ");
            playerBoard.printEnemyBoard();

            //end turn
            turn = 2;
          }
        } else if (turn == 2) {
          //send waiting message
          System.out.println("Waiting for other player...");

          // receive coordinates from player 1
          String coords = in .readLine();

          //convert string to x,y coordinates
          int xcoord = (int) coords.charAt(0) - 65;
          int ycoord = Integer.parseInt(coords.charAt(1) + "");

          System.out.println("Enemy fired at: (" + xcoord + "," + ycoord + ")");
          boolean isHit = playerBoard.checkhit(xcoord, ycoord);

          //send to player 1
          out.println(isHit);

          //check if lose
          if (playerBoard.boats == 0) {
            turn = 0;
          } else {
            //end turn
            turn = 1;
          }
        }
      }
      //closing everything
      soc.close();
      userInput.close(); 
      in.close();
      out.close();
    } catch (Exception e) {

    }

    //print match results
    if (winCondition) {
      clearScreen();
      playerBoard.matchResult(false);
    } else {
      clearScreen();
      playerBoard.matchResult(true);
    }
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}