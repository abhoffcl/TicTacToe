package service.stategy.GameWinningStrategy;

import model.Board;
import model.Cell;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderOneWinningStrategy implements GameWinningStrategy{
    private int dimension;
    private List<HashMap<Character,Integer>> rowMapList;
    private List<HashMap<Character,Integer>>colMapList;
    private HashMap<Character,Integer>leftDiagonalMap;
    private HashMap<Character,Integer>rightDiagonalMap;
    private HashMap<Character,Integer>cornerMap;

    public OrderOneWinningStrategy(int dimension) {
        this.dimension = dimension;
        leftDiagonalMap=new HashMap<>();
        rightDiagonalMap=new HashMap<>();
        cornerMap = new HashMap<>();
        rowMapList=new ArrayList<>();
        colMapList=new ArrayList<>();
        for(int i=0;i<dimension;i++){
            rowMapList.add(new HashMap<>());
            colMapList.add(new HashMap<>());
        }

    }

    public List<HashMap<Character, Integer>> getRowMapList() {
        return rowMapList;
    }

    public List<HashMap<Character, Integer>> getColMapList() {
        return colMapList;
    }

    public HashMap<Character, Integer> getLeftDiagonalMap() {
        return leftDiagonalMap;
    }

    public HashMap<Character, Integer> getRightDiagonalMap() {
        return rightDiagonalMap;
    }

    public HashMap<Character, Integer> getCornerMap() {
        return cornerMap;
    }

    public void setRowMapList(List<HashMap<Character, Integer>> rowMapList) {
        this.rowMapList = rowMapList;
    }

    public void setColMapList(List<HashMap<Character, Integer>> colMapList) {
        this.colMapList = colMapList;
    }

    public void setLeftDiagonalMap(HashMap<Character, Integer> leftDiagonalMap) {
        this.leftDiagonalMap = leftDiagonalMap;
    }

    public void setRightDiagonalMap(HashMap<Character, Integer> rightDiagonalMap) {
        this.rightDiagonalMap = rightDiagonalMap;
    }

    public void setCornerMap(HashMap<Character, Integer> cornerMap) {
        this.cornerMap = cornerMap;
    }

    @Override
    public Player checkWinner(Board board, Player player, Cell moveCell) {

        int row=moveCell.getRow();
        int col=moveCell.getCol();
        char symbol=player.getSybmol();
        boolean isWinner=(updateAndCheckRow(row,symbol) ||
                updateAndCheckCol(col,symbol)||
                moveOnLeftDiagoanl(row,col)&&updateAndCheckleftDiagonal(symbol)||
                moveOnRightDiagonal(row,col)&&updateAndCheckRightDiagonal(symbol)||
                moveOnCorner(row,col,dimension)&&updateAndCheckCorner(symbol));
        if(isWinner)
            return player;
        return null;

    }
    public  boolean moveOnCorner(int row,int col,int dimension){
        return (row==0 && col==0 || (row==0 && col==dimension-1) ||
                (row==dimension-1&&col==0 )|| (row==dimension-1 && col==dimension-1));

    }
    public boolean moveOnLeftDiagoanl(int row,int col){
        return (row==col);
    }
    public boolean moveOnRightDiagonal(int row ,int col){
        return (row+col==dimension-1);
    }
    public boolean updateAndCheckRow(int row,char symbol){
        HashMap<Character,Integer>rowMap=rowMapList.get(row);
        if(rowMap.containsKey(symbol)) {
            rowMap.put(symbol, rowMap.get(symbol) + 1);
            if (rowMap.get(symbol) == dimension)
                return true;
            return false;
        }
        else {
            rowMap.put(symbol, 1);
            return false;
        }

    }
    public boolean updateAndCheckCol(int col,char symbol){
        HashMap<Character,Integer>colMap=colMapList.get(col);
        if(colMap.containsKey(symbol)) {
            colMap.put(symbol, colMap.get(symbol) + 1);
            if (colMap.get(symbol) == dimension)
                return true;
            return false;
        }
        else {
            colMap.put(symbol, 1);
            return false;
        }

    }
    public boolean updateAndCheckleftDiagonal(char symbol){
        if(leftDiagonalMap.containsKey(symbol)) {
            leftDiagonalMap.put(symbol, leftDiagonalMap.get(symbol) + 1);
            if (leftDiagonalMap.get(symbol) == dimension)
                return true;
            return false;
        }
        else {
            leftDiagonalMap.put(symbol, 1);
            return false;
        }

    }
    public boolean updateAndCheckRightDiagonal(char symbol){
        if(rightDiagonalMap.containsKey(symbol)) {
            rightDiagonalMap.put(symbol, rightDiagonalMap.get(symbol) + 1);
            if (rightDiagonalMap.get(symbol) == dimension)
                return true;
            return false;
        }
        else {
            rightDiagonalMap.put(symbol, 1);
            return false;
        }

    }
    public boolean updateAndCheckCorner(char symbol){
        if(cornerMap.containsKey(symbol)) {
            cornerMap.put(symbol, cornerMap.get(symbol) + 1);
            if (cornerMap.get(symbol) == 4)
                return true;
            return false;
        }
        else {
            cornerMap.put(symbol, 1);
            return false;
        }

    }




}
