package service.stategy.BotPlayingStrategy;

import exception.NoEmptyCellException;
import model.*;

import java.util.List;

public class RandomBotPlayingStrategy implements BotPlayingStrategy{
    @Override
    public Move makeMove(Board board, Bot bot) {
        List<List<Cell>>matrix=board.getmatrix();
        for(int i=0;i<matrix.size();i++){
            for(int j=0;j<matrix.size();j++){
                if(matrix.get(i).get(j).getCellState()== CellState.EMPTY)
                {
                    matrix.get(i).get(j).setCellState(CellState.FILLED);
                    matrix.get(i).get(j).setPlayer(bot);
                    return new Move(bot,matrix.get(i).get(j));

                }
            }
        }
        throw new NoEmptyCellException("No empty cell left for "+bot.getName()+" to make move");
    }
}
