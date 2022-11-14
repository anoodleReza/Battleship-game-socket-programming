import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BattleServer {

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