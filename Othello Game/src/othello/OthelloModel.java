package othello;

public class OthelloModel {
    private int[][] board;

    public OthelloModel() {
        board = new int[8][8];
    }

    /**
     * Empty all pieces of board
     */


    /***************************************************************************************************
    Purpose:            the logic for making the board
    Method name:       
    Author:             Param Savalia
    Parameters:         
    Version:            1.4
    Return Value:       
    Called Functions:   
    Reference:          https://www.codeproject.com/Articles/35628/Intro-to-JavaFX-Dummy-Chess
                        https://www.youtube.com/watch?v=6S6km5duBrM
                        https://math.hws.edu/javanotes/source/chapter7/Checkers.java
                        https://coderanch.com/t/694787/java/Convert-player-game-play-computer
    ***************************************************************************************************/

    public void initBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }
        }
    }

    /**
     * get value of specific position
     *
     * @param row number of row
     * @param col number of column
     * @return state value of respond position
     */
    public int getSquare(int row, int col) {
        return board[row][col];
    }

    /**
     * prepare board according to mode of debug scenario
     *
     * @param mode value of debug scenario
     */


    public void prepareBoard(int mode) {
        initBoard();
        switch (mode) {
            case 0:
                board[3][3] = 2;
                board[3][4] = 1;
                board[4][3] = 1;
                board[4][4] = 2;
                break;
            case 1:
                board[3][3] = 2;
                board[3][4] = 1;
                board[4][3] = 1;
                board[4][4] = 2;
                break;
            case 2:
                for (int i = 1; i < 7; i++) {
                    board[1][i] = 1;
                    board[6][i] = 1;
                }
                for (int i = 2; i < 6; i++) {
                    board[i][1] = 1;
                    board[i][6] = 1;
                }
                for (int i = 2; i < 6; i++) {
                    board[2][i] = 2;
                    board[5][i] = 2;
                }
                for (int i = 3; i < 5; i++) {
                    board[i][2] = 2;
                    board[i][5] = 2;
                }
                break;
            case 3:
                for (int i = 1; i < 7; i++) {
                    for (int j = 1; j < 7; j++) {
                        board[i][j] = 1;
                    }
                }
                for (int i = 3; i < 6; i++) {
                    for (int j = 2; j < 5; j++) {
                        board[i][j] = 2;
                    }
                }
                board[4][3] = 0;
                break;
            case 4:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        board[i][j] = 1;
                    }
                }
                for (int i = 2; i < 7; i++) {
                    for (int j = 1; j < 6; j++) {
                        board[i][j] = 2;
                    }
                }
                board[2][4] = 1;
                board[5][5] = 1;
                board[6][2] = 1;
                board[4][3] = 0;
                break;
            case 5:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        board[i][j] = 0;
                    }
                }
                break;
            case 6:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        board[i][j] = 2;
                    }
                }
                for (int i = 1; i < 7; i++) {
                    for (int j = 1; j < 7; j++) {
                        board[i][j] = 0;
                    }
                }
                for (int i = 2; i < 6; i++) {
                    for (int j = 2; j < 6; j++) {
                        board[i][j] = 2;
                    }
                }
                for (int i = 3; i < 5; i++) {
                    for (int j = 3; j < 5; j++) {
                        board[i][j] = 1;
                    }
                }
                break;
            case 7:
                for (int i = 0; i < 3; i++) {
                    board[3 - i][i] = 1;
                    board[1 + i][5 + i] = 1;
                }
                for (int i = 0; i < 8; i++) {
                    board[i][3] = 1;
                    board[i][4] = 1;
                }
                break;
        }
    }

    /**
     * evaluate whether can move on specific position
     *
     * @param row    number of row
     * @param col    number of column
     * @param player number of player
     * @return if possible to move, true
     */
    public boolean canMove(int row, int col, int player) {
        if (board[row][col] == 0) {
            //direction(0,1)
            try {
                if (board[row][col + 1] == oppositePlayer(player)) {
                    for (int i = 2; i + col < 8; i++) {
                        if (board[row][col + i] == player) {
                            return true;
                        } else if (board[row][col + i] == 0) {
                            break;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            //direction(0,-1)
            try {
                if (board[row][col - 1] == oppositePlayer(player)) {
                    for (int i = 2; col - i > -1; i++) {
                        if (board[row][col - i] == player) {
                            return true;
                        } else if (board[row][col - i] == 0) {
                            break;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            //direction(1,0)
            try {
                if (board[row + 1][col] == oppositePlayer(player)) {
                    for (int i = 2; row + i < 8; i++) {
                        if (board[row + i][col] == player) {
                            return true;
                        } else if (board[row + i][col] == 0) {
                            break;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            //direction(-1,0)
            try {
                if (board[row - 1][col] == oppositePlayer(player)) {
                    for (int i = 2; row - i > -1; i++) {
                        if (board[row - i][col] == player) {
                            return true;
                        } else if (board[row - i][col] == 0) {
                            break;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            //direction(-1,-1)
            try {
                if (board[row - 1][col - 1] == oppositePlayer(player)) {
                    for (int i = 2; row - i > -1 && col - i > -1; i++) {
                        if (board[row - i][col - i] == player) {
                            return true;
                        } else if (board[row - i][col - i] == 0) {
                            break;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            //direction(-1,1)
            try {
                if (board[row - 1][col + 1] == oppositePlayer(player)) {
                    for (int i = 2; row - i > -1 && col + i < 8; i++) {
                        if (board[row - i][col + i] == player) {
                            return true;
                        } else if (board[row - i][col + i] == 0) {
                            break;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            //direction(1,-1)
            try {
                if (board[row + 1][col - 1] == oppositePlayer(player)) {
                    for (int i = 2; row + i < 8 && col - i > -1; i++) {
                        if (board[row + i][col - i] == player) {
                            return true;
                        } else if (board[row + i][col - i] == 0) {
                            break;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            //direction(1,1)
            try {
                if (board[row + 1][col + 1] == oppositePlayer(player)) {
                    for (int i = 2; row + i < 8 && col + i < 8; i++) {
                        if (board[row + i][col + i] == player) {
                            return true;
                        } else if (board[row + i][col + i] == 0) {
                            break;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
        return false;
    }

    /**
     * get a player opposite player with current player
     *
     * @param player
     * @return
     */
    public int oppositePlayer(int player) {
        return (player == 1) ? 2 : 1;
    }

    /**
     * move of piece
     *
     * @param row    number of row
     * @param col    number of column
     * @param player number of player
     * @return if legal move, return opposite pieces captured, if illegal move, return 0
     */
    public int tryMove(int row, int col, int player) {
        board[row][col] = player;
        int capturedPieces01 = 0;
        int capturedPieces0_1 = 0;
        int capturedPieces10 = 0;
        int capturedPieces_10 = 0;
        int capturedPieces_1_1 = 0;
        int capturedPieces_11 = 0;
        int capturedPieces1_1 = 0;
        int capturedPieces11 = 0;
        //direction(0,1)
        try {
            if (board[row][col + 1] == oppositePlayer(player)) {
                for (int i = 2; i + col < 8; i++) {
                    if (board[row][col + i] == player) {
                        for (int j = 1; j < i; j++) {
                            board[row][col + j] = player;
                        }
                        capturedPieces01 = i - 1;
                        break;
                    } else if (board[row][col + i] == 0) {
                        break;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        //direction(0,-1)
        try {
            if (board[row][col - 1] == oppositePlayer(player)) {
                for (int i = 2; col - i > -1; i++) {
                    if (board[row][col - i] == player) {
                        for (int j = 1; j < i; j++) {
                            board[row][col - j] = player;
                        }
                        capturedPieces0_1 = i - 1;
                        break;
                    } else if (board[row][col - i] == 0) {
                        break;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        //direction(1,0)
        try {
            if (board[row + 1][col] == oppositePlayer(player)) {
                for (int i = 2; row + i < 8; i++) {
                    if (board[row + i][col] == player) {
                        for (int j = 1; j < i; j++) {
                            board[row + j][col] = player;
                        }
                        capturedPieces10 = i - 1;
                        break;
                    } else if (board[row + i][col] == 0) {
                        break;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        //direction(-1,0)
        try {
            if (board[row - 1][col] == oppositePlayer(player)) {
                for (int i = 2; row - i > -1; i++) {
                    if (board[row - i][col] == player) {
                        for (int j = 1; j < i; j++) {
                            board[row - j][col] = player;
                        }
                        capturedPieces_10 = i - 1;
                        break;
                    } else if (board[row - i][col] == 0) {
                        break;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        //direction(-1,-1)
        try {
            if (board[row - 1][col - 1] == oppositePlayer(player)) {
                for (int i = 2; row - i > -1 && col - i > -1; i++) {
                    if (board[row - i][col - i] == player) {
                        for (int j = 1; j < i; j++) {
                            board[row - j][col - j] = player;
                        }
                        capturedPieces_1_1 = i - 1;
                        break;
                    } else if (board[row - i][col - i] == 0) {
                        break;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        //direction(-1,1)
        try {
            if (board[row - 1][col + 1] == oppositePlayer(player)) {
                for (int i = 2; row - i > -1 && col + i < 8; i++) {
                    if (board[row - i][col + i] == player) {
                        for (int j = 1; j < i; j++) {
                            board[row - j][col + j] = player;
                        }
                        capturedPieces_11 = i - 1;
                        break;
                    } else if (board[row - i][col + i] == 0) {
                        break;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        //direction(1,-1)
        try {
            if (board[row + 1][col - 1] == oppositePlayer(player)) {
                for (int i = 2; row + i < 8 && col - i > -1; i++) {
                    if (board[row + i][col - i] == player) {
                        for (int j = 1; j < i; j++) {
                            board[row + j][col - j] = player;
                        }
                        capturedPieces1_1 = i - 1;
                        break;
                    } else if (board[row + i][col - i] == 0) {
                        break;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        //direction(1,1)
        try {
            if (board[row + 1][col + 1] == oppositePlayer(player)) {
                for (int i = 2; row + i < 8 && col + i < 8; i++) {
                    if (board[row + i][col + i] == player) {
                        for (int j = 1; j < i; j++) {
                            board[row + j][col + j] = player;
                        }
                        capturedPieces11 = i - 1;
                        break;
                    } else if (board[row + i][col + i] == 0) {
                        break;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        int totalCapturedPieces = capturedPieces01 + capturedPieces0_1 + capturedPieces10 + capturedPieces_10 + capturedPieces_1_1 + capturedPieces_11 + capturedPieces1_1 + capturedPieces11;
        return totalCapturedPieces;
    }

    /**
     * whether player can move on board
     *
     * @param player number of player
     * @return if possible, true
     */
    public boolean moveTest(int player) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (canMove(i, j, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * get number of pieces of player
     *
     * @param player number of player
     * @return number of pieces
     */
    public int chipCount(int player) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == player) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * set array board
     * @param board variable to set
     */
    public void setBoard(int[][] board) {
        this.board = board;
    }

    /**
     * get array board
     * @return array of board
     */
    public int[][] getBoard(){
        return board;
    }
}
