package service.stategy.BotPlayingStrategy;

import model.Board;
import model.Bot;
import model.Move;

public interface BotPlayingStrategy {
    Move makeMove(Board board, Bot bot);
}
