package model;

import exception.EmptyMovesUndoException;
import exception.InvalidBotCountException;
import exception.InvalidPlayerCountException;
import exception.InvalidSymbolSetupException;
import service.stategy.GameWinningStrategy.GameWinningStrategy;
import service.stategy.GameWinningStrategy.OrderOneWinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class Game {
    private Board currentBoard;
    private List<Player> players;
    private Player player;
    private List<Board> boardStates;
    private List<Move> moves;
    private GameStatus gamestatus;
    private GameWinningStrategy gameWinningStrategy;

    public Game(Board board, List<Player> players,GameWinningStrategy gameWinningStrategy) {
        this.currentBoard = board;
        this.players = players;
        this.boardStates = new ArrayList<>();
        this.boardStates.add(this.currentBoard);
        this.moves = new ArrayList<>();
        this.gamestatus = GameStatus.INPROGRESS;
        this.gameWinningStrategy=gameWinningStrategy;
    }
    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private Board currentBoard;
        private List<Player> players;
        private GameWinningStrategy gameWinningStrategy;


        public Builder setCurrentBoard(Board currentBoard) {
            this.currentBoard = currentBoard;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setGameWinningStrategy(GameWinningStrategy gameWinningStrategy) {
            this.gameWinningStrategy = gameWinningStrategy;
            return this;
        }

        public void validatePlayerSymbol(){
            HashSet<Character>symbolSet=new HashSet<>();
            for(Player player:players){
                symbolSet.add(player.getSybmol());
            }
            if(symbolSet.size()!= players.size())
                throw new  InvalidSymbolSetupException("There should be unique symbols for each player");
        }
        public void validateBotCount(){
            int botCount=0;
            for(Player player:players){
                if(player.getType()==PlayerType.BOT)
                    botCount++;
            }
            if(botCount<0 || botCount>1)
                throw new InvalidBotCountException("There should be upto 1 bot per game");
        }
        public void validatePlayerCount(){
            int validPlayerCount=this.currentBoard.getDimension()-1;
            if(players.size()!=validPlayerCount)
                throw new InvalidPlayerCountException
                        ("There should be "+ validPlayerCount +" players");

        }

        public void validate(){
            validatePlayerCount();
            validateBotCount();
            validatePlayerSymbol();
        }
        public Game build(){
            validate();
            return new Game(currentBoard,players,gameWinningStrategy);

        }


    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Board> getBoardStates() {
        return boardStates;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public GameStatus getGamestatus() {
        return gamestatus;
    }

    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBoardStates(List<Board> boardStates) {
        this.boardStates = boardStates;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public void setGamestatus(GameStatus gamestatus) {
        this.gamestatus = gamestatus;
    }

    public GameWinningStrategy getGameWinningStrategy() {
        return gameWinningStrategy;
    }

    public void setGameWinningStrategy(GameWinningStrategy gameWinningStrategy) {
        this.gameWinningStrategy = gameWinningStrategy;
    }

    public Board undoMove(Move lastMove){
        List<Move>moves=this.getMoves();
        List<Board>boards=this.getBoardStates();
        int dimension = this.getCurrentBoard().getDimension();
        Player player=lastMove.getPlayer();
        char playerSymbol = player.getSybmol();
        Cell lastMoveCell = lastMove.getCell();
        int lastMoveCellRow=lastMoveCell.getRow();
        int lastMoveCellCol = lastMoveCell.getCol();

        if(moves==null || moves.isEmpty())
            throw new EmptyMovesUndoException("No moves made yet");
        moves.remove(lastMove);
        boards.remove(boards.size()-1);


        if(gameWinningStrategy instanceof OrderOneWinningStrategy){
             List< HashMap<Character,Integer>>rowMapList=((OrderOneWinningStrategy) gameWinningStrategy).getRowMapList();
             List< HashMap<Character,Integer>>colMapList=((OrderOneWinningStrategy) gameWinningStrategy).getColMapList();

             HashMap<Character,Integer> rowMap=rowMapList.get(lastMoveCellRow);
             HashMap<Character,Integer> colMap=colMapList.get(lastMoveCellCol);
             HashMap<Character,Integer>leftDiagonalMap = ((OrderOneWinningStrategy) gameWinningStrategy).getLeftDiagonalMap();
             HashMap<Character,Integer>rightDiagonalMap = ((OrderOneWinningStrategy) gameWinningStrategy).getRightDiagonalMap();
             HashMap<Character,Integer>cornerMap=((OrderOneWinningStrategy) gameWinningStrategy).getCornerMap();
             rowMap.put(playerSymbol,rowMap.get(playerSymbol)-1);
             colMap.put(playerSymbol,colMap.get(playerSymbol)-1);

            rowMapList.set(lastMoveCellRow,rowMap);
            colMapList.set(lastMoveCellCol,colMap);
             if(lastMoveCellCol==lastMoveCellRow) {
                 leftDiagonalMap.put(playerSymbol, leftDiagonalMap.get(playerSymbol) - 1);
                 ((OrderOneWinningStrategy) gameWinningStrategy).setLeftDiagonalMap(leftDiagonalMap);
             }
            if(lastMoveCellCol+lastMoveCellRow==dimension-1) {
                rightDiagonalMap.put(playerSymbol, rightDiagonalMap.get(playerSymbol) - 1);
                ((OrderOneWinningStrategy) gameWinningStrategy).setRightDiagonalMap(rightDiagonalMap);
            }
            if(((OrderOneWinningStrategy) gameWinningStrategy).moveOnCorner(lastMoveCellRow,lastMoveCellCol,dimension)) {
                cornerMap.put(playerSymbol, cornerMap.get(playerSymbol) - 1);
                ((OrderOneWinningStrategy) gameWinningStrategy).setCornerMap(cornerMap);
            }
            ((OrderOneWinningStrategy) gameWinningStrategy).setRowMapList(rowMapList);
            ((OrderOneWinningStrategy) gameWinningStrategy).setColMapList(colMapList);

        }
        if(boards.size()>0)
        return boards.get(boards.size()-1);
        else
            return new Board(dimension);
    }
}