package service.stategy.GameWinningStrategy;

import model.Board;
import model.Cell;
import model.Player;

public interface GameWinningStrategy {
   Player checkWinner(Board board, Player player, Cell moveCell);
}
