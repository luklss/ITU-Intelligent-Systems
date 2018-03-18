



import java.util.*;

/**
 * Created by lucas on 3/11/2017.
 */
public class MiniMax {
    IGameLogic.Winner currentPlayer;
    int deepness = 13;
    private GameLogic mainGame;

    public MiniMax (GameLogic mainGame){
        this.mainGame = mainGame;
    }


    // the evaluation function takes the 1 /
    private double evaluation (GameLogic game) {
        if (terminalTest(game)) return utility(game);
        //System.out.println(game.toString());
        double player1PiecesToWin = game.getconsecutivesToWin() - game.getPlayer1Consecutives();
        double player2PiecesToWin = game.getconsecutivesToWin() - game.getPlayer2Consecutives();
        double evaluation;
        if (mainGame.getPlayerID() == 1) {
            evaluation = (double) 1/player1PiecesToWin - (double)1/player2PiecesToWin;
            //System.out.println(evaluation);
        }
        else {
            evaluation = (double) 1/player2PiecesToWin - (double) 1/player1PiecesToWin;
        }
        return evaluation;
    }

    private boolean cutOffTest (GameLogic game, int depth) {
        if (terminalTest(game) || depth >= deepness) {
            return true;
        }
        return false;
    }

    private boolean terminalTest(GameLogic game) {
        if (game.gameFinished().equals(GameLogic.Winner.NOT_FINISHED)){
            return false;
        }
        return true;
    }

    private int utility(GameLogic game) {
        IGameLogic.Winner finishedResult = game.gameFinished();
        //System.out.println(game.toString());
        //System.out.println(game.gameFinished());
        if (finishedResult.equals(currentPlayer)){
            return 1;
        } else if (finishedResult.equals(IGameLogic.Winner.TIE)) {
            return 0;
        } else if (!finishedResult.equals(currentPlayer)) {
            return -1;
        }
        return 0;
    }

    public double maxValue(GameLogic game, int depth) {
        // checks if it is a terminal node
        depth++;
        if (cutOffTest(game, depth)){
            return evaluation(game);
        }

        double v = -10000.0;
        for (Integer action : actions(game)) {
            double result = minValue(result(game, action), depth);
            if (result > v){
                v = result;
            }
        }
        //System.out.println("v after max is " + v);
        //System.out.println(v);
        return v;
    }

    public double minValue(GameLogic game, int depth) {
        // checks if it is a terminal node
        depth++;
        if (cutOffTest(game, depth)){
            return evaluation(game);
        }


        double v = 100000.0;
        for (Integer action : actions(game)) {
            double result = maxValue(result(game, action), depth);
            if (result < v)
                v = result;
        }
        //System.out.println("v after min is " + v);
        //System.out.println(v);
        return v;
    }

    private GameLogic result(GameLogic game, Integer action) {
        //System.out.println("input game");
        //System.out.println(game.toString());
        //System.out.println("input action: " + action);
        GameLogic result = copy(game);
        result.insertCoin(action, game.getPlayerID());
        int pIDtoSet = 1;
        if (game.getPlayerID() == 1) pIDtoSet = 2;
        result.setPlayerID(pIDtoSet);

        //System.out.println("output game");
        //System.out.println(result.toString());
        return result;
    }

    // seems to work
    private GameLogic copy(GameLogic game) {
        GameLogic result = new GameLogic();
        result.SetX(game.getX());
        result.SetY(game.getY());
        result.SetplayerID(game.getPlayerID());
        result.SetPlacedCoins(game.getPlacedCoins());
        result.SetGameSpace(cloneBoard(game.getGameSpace()));
        return result;
    }

    // seems to work
    private Integer[][] cloneBoard(Integer[][] gameSpace) {
        Integer[][] result = new Integer[gameSpace.length][gameSpace[0].length];
        for(int i = 0; i < gameSpace.length; i++){
            for (int j = 0; j < gameSpace[0].length; j++){
                result[i][j] = gameSpace[i][j];
            }
        }
        return result;
    }

    // seems to work
    private List<Integer> actions(GameLogic game) {
        List<Integer> actions = new ArrayList<>();
        for (int i = 0; i < game.getGameSpace().length; i ++) {
            for (int j = 0; j < game.getGameSpace()[i].length; j ++) {
                if (game.getGameSpace()[j][i] == null){
                    actions.add(i);
                    break;
                }
            }
        }
        //System.out.println(actions.toString());
        //System.out.println(game.toString());
        return actions;
    }


    public int decideNextMove() {
        System.out.println("processing");
        if(mainGame.getPlayerID()== 1) currentPlayer = IGameLogic.Winner.PLAYER1;
        if(mainGame.getPlayerID()== 2) currentPlayer = IGameLogic.Winner.PLAYER2;
        int depth = 1;
        double higiestUtility = -2;
        int choosenAction = -2;
        for (Integer action : actions(mainGame)) {
            double currentUtility = minValue(result(mainGame, action), depth);
            if (currentUtility  > higiestUtility ) {
                higiestUtility = currentUtility;
                choosenAction = action;
            }

            System.out.println("action is " + action + " utility is " + currentUtility  );
        }


        System.out.println("action choosen is is " + choosenAction);
        return choosenAction;
    }


}
