import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetAddress;


public class BattleServer {

  public static int turn = 1;

  public static void main(String[] args) {
    // start game (maybe put this in another function?)
    GameBoard playerBoard = new GameBoard();

    InetAddress ip;
    try {
      ip = InetAddress.getLocalHost();
      System.out.println("Your current IP address : " + ip);
    } catch (Exception e) {
    }

    try {
      // establishing connection
      System.out.println("Waiting for Other Player...");
      ServerSocket ss = new ServerSocket(9806);
      Socket soc = ss.accept();
      System.out.println("Connection established");
      clearScreen();

      playerBoard.generateBoats(4);
      System.out.println("\nPlayer Board: ");
      playerBoard.printBoard();
      System.out.println("\nEnemy Board: ");
      playerBoard.printEnemyBoard();

      BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in)); // get user input
      PrintWriter out = new PrintWriter(soc.getOutputStream(), true); // output to other player
      BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream())); // input from other player

      while (turn > 0) {
        // turn 0 = win/lose, turn 1 = player 1, turn 2 = player 2
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
            playerBoard.addscore();
          }
          
          playerBoard.returnHit(xcoord, ycoord, isHit);
          
          //check if win
          if (playerBoard.score == 4) {
            clearScreen();
            playerBoard.matchResult(false);
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
            playerBoard.matchResult(true);
            turn = 0;
          } else {
            //end turn
            turn = 1;
          }
        }
      }

      //closing everything
      userInput.close(); 
      in .close();
      out.close();
      soc.close();
      ss.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  public void printBoards() {

  }

  public static boolean isNumeric(String string) {
    int intValue;

    System.out.println(String.format("Parsing string: \"%s\"", string));

    if (string == null || string.equals("")) {
      System.out.println("String cannot be parsed, it is null or empty.");
      return false;
    }

    try {
      intValue = Integer.parseInt(string);
      return true;
    } catch (NumberFormatException e) {
      System.out.println("Input String cannot be parsed to Integer.");
    }
    return false;
  }
}