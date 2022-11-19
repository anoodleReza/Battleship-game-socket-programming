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
            playerBoard.printBoard();

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
                    // send to player 1
                }else if(turn == 2){
                    // wait for player 1
                    String name = in.readLine();
                    System.out.println("Opposing Enemy is: " + name);
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
