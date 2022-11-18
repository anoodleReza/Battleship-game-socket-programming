import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BattleServer {

    public static int turn = 1;
    public static void main(String[] args) {
        try{
            //establishing connection
            System.out.println("Waiting for Other Player...");
            ServerSocket ss = new ServerSocket(9806);
            Socket soc = ss.accept();      
            System.out.println("Connection established");     
            clearScreen();
            
            //start game (maybe put this in another function?)
            GameBoard playerBoard = new GameBoard();
            playerBoard.generateBoats(4);
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
            while (turn > 0) {
                //turn 0 = win/lose, turn 1 = player 1, turn 2 = player 2
                if (turn == 1) {
                    // send to player 1
                }else if(turn == 2){
                    // wait for player 2
                }
            }

            ss.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}