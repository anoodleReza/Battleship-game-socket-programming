import java.util.Random;

public class GameBoard {
  public boolean[][] board;
  public int[][] eBoard;
  public int score = 0, boats = 0;

  public GameBoard() {
    board = new boolean[10][10];
    eBoard = new int[10][10]; //0=nothing 1=miss 2=hit
    System.out.println(
      "====================================================================================\r\n" +    
      "\r\n\r\n" +
      "\tWelcome To Battleships" + 
      "\r\n\r\n\r\n" +
      "\tHow to Play:" + 
      "\r\n\r\n" +
      "\t1. Enter Coordinates as [Letter][Number], For example: A9" + 
      "\r\n\r\n" +
      "\t2. Guess all the Enemy Battleship Loations before the enemy does" +
      "\r\n\r\n\r\n" + 
      "====================================================================================\r\n" +    
      "\r\n\r\n"
      );
  }

  // create random boats
  void generateBoats(int num) {
    boats = num;
    int curNum = 0;
    while (curNum < num) {
      // get random location
      Random random = new Random();
      int posx = random.nextInt(10);
      int posy = random.nextInt(10);
      // check if its empty
      if (!board[posx][posy]) {
        // if yes, add ship there
        board[posx][posy] = true;
        curNum++;
      }
      // if its not, repeat without increasing ship number
    }
  }

  void printBoard() {
    // print column labels
    System.out.println("  0 1 2 3 4 5 6 7 8 9 \t \t  0 1 2 3 4 5 6 7 8 9");
    for (int i = 0; i < board.length; i++) {
      // print row labels
      System.out.print((char)('@' + i + 1) + " ");
      for (int j = 0; j < board.length; j++) {
        if (board[i][j]) {
          System.out.print("X ");
        } else {
          System.out.print("- ");
        }
      }
      System.out.print('\t');
      System.out.print('\t');
      //enemy
      System.out.print((char)('@' + i + 1) + " ");
      for (int j = 0; j < board.length; j++) {
        switch (eBoard[i][j]) {
        case 0:
          System.out.print("- ");
          break;
        case 1:
          System.out.print("M ");
          break;
        case 2:
          System.out.print("H ");
          break;

        default:
          break;
        }
      }
      System.out.println();
    }
  }

  boolean checkhit(int x, int y) {
    // function to check if coordinates are a hit
    boolean isHit = board[x][y];
    if (isHit) {
      boats--;
      System.out.println("\nYour ship has been Hit");
    } else {
      System.out.println("\nYour ship has been Missed");
    }
    return (isHit);
  }

  void returnHit(int x, int y, boolean isHit) {
    if (isHit) {
      eBoard[x][y] = 2;
    } else {
      eBoard[x][y] = 1;
    }
  }

  void matchResult(boolean hasLost) {
    // function to display victory or defeat screen
    System.out.print("\033[H\033[2J");
    System.out.flush();

    if (hasLost) {
      System.out.println(
        "====================================================================================\r\n" +    
        "\r\n\r\n" +
        "\tDefeat" + 
        "\r\n\r\n\r\n" +
        "\tThe enemy has sunken all of your ships" + 
        "\r\n\r\n" + 
        "====================================================================================\r\n" +    
        "\r\n\r\n"
        );
    } else {
      System.out.println(
        "====================================================================================\r\n" +    
        "\r\n\r\n" +
        "\tVictory" + 
        "\r\n\r\n\r\n" +
        "\tYou have sunken all enemy ships" + 
        "\r\n\r\n\r\n" +
        "====================================================================================\r\n" +    
        "\r\n\r\n"
        );
    }
  }

  void addscore() {
    score++;
  }

  public static void main(String[] args) {
    GameBoard player = new GameBoard();
    player.generateBoats(2);
    player.printBoard();

  }
}