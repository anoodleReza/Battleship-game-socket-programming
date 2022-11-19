import java.util.Random;

public class GameBoard {
  public boolean[][] board;

  public GameBoard() {
    board = new boolean[10][10];
  }

  // create random boats
  void generateBoats(int num) {
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
    //print column labels
    System.out.println("  0 1 2 3 4 5 6 7 8 9");
    for (int i = 0; i < board.length; i++) {
      //print row labels
      System.out.print((char) ('@' + i + 1) + " ");
      for (int j = 0; j < board.length; j++) {
        if (board[i][j]) {
          System.out.print("X ");
        } else {
          System.out.print("- ");
        }
      }
      System.out.println();
    }
  }

  boolean checkhit(int x, int y){
    //function to check if coordinates are a hit
    boolean isHit = false;
    
    return(isHit);
  }

  void matchResult(boolean hasLost){
    //function to display victory or defeat screen
  }
}