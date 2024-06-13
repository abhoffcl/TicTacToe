package model;

import exception.CellNotEmptyException;
import exception.InvalidDimensionException;

import java.util.Scanner;

public class Player {
    private int id;
    private String name;
    private char sybmol;
    private PlayerType type;

    public Player(int id, String name, char sybmol,PlayerType playerType) {
        this.id = id;
        this.name = name;
        this.sybmol = sybmol;
        this.type = playerType;
    }
    public Move makeMove(Board board){
        int dimension=board.getDimension();
        int targetRow,targetCol;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the target row for your move");
        targetRow=sc.nextInt();
        if(targetRow<0 || targetRow>=dimension)
            throw new InvalidDimensionException("Enter a valid row number");
        System.out.println("Enter the target col for your move");

        targetCol=sc.nextInt();
        if(targetCol<0 || targetCol>=dimension)
            throw new InvalidDimensionException("Enter a valid col number");
        if(board.getmatrix().get(targetRow).get(targetCol).getCellState()==CellState.FILLED)
            throw new CellNotEmptyException("this cell is already filled");

        Cell filledCell = board.getmatrix().get(targetRow).get(targetCol);
        filledCell.setCellState(CellState.FILLED);
        filledCell.setPlayer(this);
        return new Move(this,filledCell);
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public char getSybmol() {
        return sybmol;
    }

    public PlayerType getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSybmol(char sybmol) {
        this.sybmol = sybmol;
    }

    public void setType(PlayerType type) {
        this.type = type;
    }
}
