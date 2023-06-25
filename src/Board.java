import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int NO_PLAYER = 0;
    public static final int PLAYER_X = 1;
    public static final int PLAYER_0 = 2;
    private final int[][] board = new int[3][3];
    public Point computerMove;

    public void printBoard() {
        System.out.println();
        String value = "_";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == PLAYER_X)
                    value = "X";
                else if (board[i][j] == PLAYER_0)
                    value = "0";
                else value = "_";

                System.out.print(value + " ");
            }

            System.out.println();
        }

        System.out.println();
    }

    public boolean isPlayerWin(int player) {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == player) ||
                (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == player)) {
            return true;
        }

        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == player) ||
                    (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == player)) {
                return true;
            }
        }

        return false;
    }

    public List<Point> getAvailableCells() {
        List<Point> availableCells = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == NO_PLAYER)
                    availableCells.add(new Point(i, j));
            }
        }

        return availableCells;
    }

    public boolean gameOver() {
        return isPlayerWin(PLAYER_X) || isPlayerWin(PLAYER_0) || getAvailableCells().isEmpty();
    }

    public boolean makeMove(Point point, int player) {
        if (board[point.getX()][point.getY()] != NO_PLAYER)
            return false;

        board[point.getX()][point.getY()] = player;

        return true;
    }

    public int minimax(int depth, int player) {
        if (isPlayerWin(PLAYER_X))
            return 1;

        if (isPlayerWin(PLAYER_0))
            return -1;

        List<Point> availableCells = getAvailableCells();
        if (availableCells.isEmpty())
            return 0;

        int maxValue = Integer.MIN_VALUE;
        int minValue = Integer.MAX_VALUE;

        for (int i = 0; i < availableCells.size(); i++) {
            Point point = availableCells.get(i);

            if (player == PLAYER_X) {
                makeMove(point, PLAYER_X);
                int score = minimax(depth + 1, PLAYER_0);
                maxValue = Math.max(score, maxValue);

                if (score >= 0)
                    if (depth == 0)
                        computerMove = point;

                if (score == 1) {
                    board[point.getX()][point.getY()] = NO_PLAYER;
                    break;
                }

                if (i == availableCells.size() - 1 && maxValue < 0)
                    if (depth == 0)
                        computerMove = point;

            } else if (player == PLAYER_0) {
                makeMove(point, player);
                int score = minimax(depth + 1, PLAYER_X);
                minValue = Math.min(score, minValue);

                if (minValue == -1) {
                    board[point.getX()][point.getY()] = NO_PLAYER;
                    break;
                }
            }

            board[point.getX()][point.getY()] = NO_PLAYER;
        }

        return player == PLAYER_X ? maxValue : minValue;
    }
}
