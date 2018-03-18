public class GameLogic implements IGameLogic {
    private int x = 0;
    private int y = 0;
    private int playerID;
    private Integer[][] gameSpace;
    private int placedCoins = 0;
    private int indexX = 0;
    private int indexY = 0;
    private int consecutivesToWin = 4;
    private int player1Consecutives = 0;
    private int player2Consecutives = 0;

    // blue is number 1, red number 2
    
    public GameLogic() {
        //TODO Write your implementation for this method
    }
	
    public void initializeGame(int x, int y, int playerID) {
        this.x = x;
        this.y = y;
        this.playerID = playerID;
        gameSpace = new Integer[x][y];
        //TODO Write your implementation for this method
    }

    private Winner checkConsecutives(Integer[] array){
        int player1Counter = 0;
        int player2Counter = 0;
        int placesAvailable = 0;
        for (int i = 0; i < array.length; i ++){

            if (array[i] == null){
                placesAvailable++;
                player1Counter = 0;
                player2Counter =0;
                continue;
            }
            if (array[i] == 1){
                player1Counter++;
                player2Counter = 0;
            }
            else if (array[i] == 2){
                player2Counter++;
                player1Counter = 0;
            }

            // keeps track of how close the players are to win
            if (player1Counter > player1Consecutives && placesAvailable + player1Counter >= consecutivesToWin) player1Consecutives = player1Counter;
            if (player1Counter > player2Consecutives && placesAvailable + player2Counter >= consecutivesToWin) player2Consecutives = player1Counter;

            if (player1Counter >= consecutivesToWin) return Winner.PLAYER1;
            if (player2Counter >= consecutivesToWin) return Winner.PLAYER2;


        }
        return Winner.NOT_FINISHED;
    }
	
    public Winner gameFinished() {
        //TODO Write your implementation for this method

        // check for the row where the coin was inserted
        for (int i = 0; i < gameSpace.length ; i ++) {
            if (checkConsecutives(gameSpace[i]) == Winner.PLAYER1) return Winner.PLAYER1;
            else if (checkConsecutives(gameSpace[i]) == Winner.PLAYER2) return Winner.PLAYER2;
        }

        // check for the colums
        for (int i = 0; i < x ; i ++) {
            Integer[] currentColum = getColumn(gameSpace, i);
            if (checkConsecutives(currentColum) == Winner.PLAYER1) return Winner.PLAYER1;
            else if (checkConsecutives(currentColum) == Winner.PLAYER2) return Winner.PLAYER2;
        }



        // check for diagonals
        for (int k = 0; k < x; k++) {
            for (int l = 0; l < y ; l ++) {

                // checks for left to right
                Integer[] diagonalLefttoRight = new Integer[x];
                int j = y - 1; // starts at the end of the colum
                for (int i = 0; i < this.x ; i ++){ // increase x coordinate
                    diagonalLefttoRight[i] = gameSpace[j][i];
                    j--;
                }
                if (checkConsecutives(diagonalLefttoRight) == Winner.PLAYER1) return Winner.PLAYER1;
                else if (checkConsecutives(diagonalLefttoRight) == Winner.PLAYER2) return Winner.PLAYER2;

                // checks for right to left
                Integer[] diagonalRighttoLeft = new Integer[x];
                int p = y - 1; // starts at the end of the colum
                for (int i = l; i >= 0; i--) { // decrease x coordinate
                    diagonalRighttoLeft[i] = gameSpace[p][i];
                    p--;
                }

                if (checkConsecutives(diagonalRighttoLeft) == Winner.PLAYER1) return Winner.PLAYER1;
                else if (checkConsecutives(diagonalRighttoLeft) == Winner.PLAYER2) return Winner.PLAYER2;
            }
        }

        // check if it is a tie
        if (placedCoins >= x * y) return Winner.TIE;

        // check for the colums.
        return Winner.NOT_FINISHED;

    }



    public void insertCoin(int column, int playerID) {
        //TODO Write your implementation for this method

        if (gameSpace[x - 1][column] == null) {
            gameSpace[x - 1][column] = playerID; // places the coin if its empty
            placedCoins++;
            indexX = x-1;
            indexY = column;
        }
        else {
            int index = x - 1; // start at the last colum
            while (gameSpace[index][column] != null){
                index --;
            }

            gameSpace[index][column] = playerID;
            placedCoins++;
            indexX = index;
            indexY = column;

        }


    }


    public int decideNextMove() {
        Integer result = new MiniMax(this).decideNextMove();
        return result;

        // naive implementation
      /*  for (int i = 0; i < gameSpace[i].length; i ++){
            for (int j = 0; j < getColumn(gameSpace,i).length; j ++) {
                if (getColumn(gameSpace, i)[j] == null) return i;
            }
        }
        return 0;*/


    }

    // returns colum
    public static Integer[] getColumn(Integer[][] array, int index){
        Integer[] column = new Integer[array[0].length];
        for(int i=0; i<column.length; i++){
            column[i] = array[i][index];
        }
        return column;
    }

    // used to print the two dimensional array
    public String toString() {
        String separator = ", ";
        StringBuffer result = new StringBuffer();

        // iterate over the first dimension
        for (int i = 0; i < gameSpace.length; i++) {
            // iterate over the second dimension
            for(int j = 0; j < gameSpace[i].length; j++){
                result.append(gameSpace[i][j]);
                result.append(separator);
            }
            // remove the last separator
            result.setLength(result.length() - separator.length());
            // add a line break.
            result.append("\n");
        }
        return result.toString();
    }

    public Integer[][] getGameSpace() {
        return gameSpace;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void SetX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void SetY(int y) {
        this.y = y;
    }

    public void SetplayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getPlacedCoins() {
        return placedCoins;
    }

    public void SetPlacedCoins(int placedCoins) {
        this.placedCoins = placedCoins;
    }

    public void SetGameSpace(Integer[][] gameSpace) {
        this.gameSpace = gameSpace;
    }


    public int getconsecutivesToWin() {
        return consecutivesToWin;
    }

    public int getPlayer1Consecutives() {
        return player1Consecutives;
    }

    public int getPlayer2Consecutives() {
        return player2Consecutives;
    }

}
