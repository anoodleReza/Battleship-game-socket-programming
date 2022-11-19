import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BattleClient {

    public static int turn = 2;
    public static void main(String[] args) {
        try {
            //connect to host
            System.out.println("Client Started");
            Socket soc = new Socket("localhost",9806);
            clearScreen();

            //start game (maybe put this in another function?)
            GameBoard playerBoard = new GameBoard();
            int boats = 4; //get the number of boats from the server later...
            playerBoard.generateBoats(boats);
            //print boards
            System.out.println("\nPlayer Board: ");
            playerBoard.printBoard();
            System.out.println("\nEnemy Board: ");
            playerBoard.printEnemyBoard();

           /*
             Game Overview:
                1. Player 2 prepares for message
                2. Server starts turn 1. Player 1 selects coordinate
                3. Player 1 sends message to player 2 with the coordinates
                4. player 2 receives message and checks if hit, and if there are ships left
                5. player 1 prepares for message
                6. player 2 sends message containing hit/miss & win/lose (if applicable)
                7. repeat previous steps with alternating players until win/lose
             */            
            
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            
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
                            (coords.charAt(0)>=65 && coords.charAt(0)<=73) &&
                            (coords.charAt(1)>=48 && coords.charAt(1)<=57))
                         {
                            String x = coords.charAt(0) + "";
                            String y = coords.charAt(1) + "";
                            xcoord = (int)coords.charAt(0) - 65;
                                    ycoord = Integer.parseInt(y);
                                    validInput = true;
                        }else{
                            coords = userInput.readLine();
                        }
                    }

                    //send coordinates to client
                    System.out.println("Shooting at: (" + xcoord + "," + ycoord + ")");
                    out.println(coords);

                    //receive response
                    String hitMiss = in.readLine();
                    boolean isHit = true;

                    if (hitMiss.equals("false")) {
                        isHit = false;
                    }else if (hitMiss.equals("true")) {
                        isHit = true;
                    }
                    playerBoard.returnHit(xcoord, ycoord, isHit);

                    //print enemy boards again
                    clearScreen();
                    System.out.println("\nPlayer Board: ");
                    playerBoard.printBoard();
                    System.out.println("\nEnemy Board: ");
                    playerBoard.printEnemyBoard();

                    //end turn
                    turn = 2;
                }else if(turn == 2){
                    // receive coordinates from player 1
                    String coords = in.readLine();
                    //convert string to x,y coordinates
                    int xcoord = (int)coords.charAt(0) - 65;
                    int ycoord = Integer.parseInt(coords.charAt(1) + "");

                    System.out.println("Enemy fired at: (" + xcoord + "," + ycoord + ")");
                    boolean isHit = playerBoard.checkhit(xcoord, ycoord);
                    
                    //send to player 1
                    out.println(isHit);

                    //end turn
                    turn = 1;
                }
            }
            soc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}
