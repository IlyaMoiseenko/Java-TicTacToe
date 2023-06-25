import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final Random RANDOM = new Random();

    public static void main(String[] args) {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);

        board.printBoard();
        System.out.println("Select player: \n1. Computer (X) / 2.User(0) : ");
        int guess = scanner.nextInt();

        if (guess == Board.PLAYER_X) {
            Point point = new Point(RANDOM.nextInt(3), RANDOM.nextInt(3));
            board.makeMove(point, Board.PLAYER_X);
            board.printBoard();
        }

        while (!board.gameOver()) {
            boolean moveOk = true;

            do {
                if (!moveOk) System.out.println("Cell already filled!");

                System.out.println("Your move: ");
                Point userMove = new Point(scanner.nextInt(), scanner.nextInt());
                moveOk = board.makeMove(userMove, Board.PLAYER_0);
            } while (!moveOk);

            board.printBoard();

            if (board.gameOver()) break;

            board.minimax(0, Board.PLAYER_X);
            System.out.println("Computer choose position: " + board.computerMove);

            board.makeMove(board.computerMove, Board.PLAYER_X);
            board.printBoard();
        }

        if (board.isPlayerWin(Board.PLAYER_X))
            System.out.println("You lost!");
        else if (board.isPlayerWin(Board.PLAYER_0))
            System.out.println("You win!");
        else System.out.println("Draw!");
    }
}