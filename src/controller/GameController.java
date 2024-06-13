package controller;

import model.*;
import service.stategy.GameWinningStrategy.GameWinningStrategy;


import java.util.List;

public class GameController {
    public Game createGame(Board board,List<Player>players,GameWinningStrategy gameWinningStrategy){

         return Game.builder().
                setGameWinningStrategy(gameWinningStrategy).
                setPlayers(players).setCurrentBoard(board).
                build();

    }
    public void displayBoard(Game game){
        game.getCurrentBoard().displaymatrix();
    }
    public Board undoMove(Game game,Move lastMove){
        return game.undoMove(lastMove);
    }
    public Player checkWinner(Game game,Move lastMove){
        return game.getGameWinningStrategy().
                checkWinner(game.getCurrentBoard(),lastMove.getPlayer(),lastMove.getCell());
    }
    public GameStatus getGameStatus(Game game){
        return game.getGamestatus();
    }
    public Board getCurrentBoard(Game game){
        return game.getCurrentBoard();
    }
    public List<Move>getMoves(Game game){
        return game.getMoves();
    }
    public Move executeMove(Game game,Player player){
        return player.makeMove(game.getCurrentBoard());
    }


}
