import java.net.Socket;

public class BattleClient {
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
