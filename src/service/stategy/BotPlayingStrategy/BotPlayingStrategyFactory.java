package service.stategy.BotPlayingStrategy;

import model.BotDifficultyLevel;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy(BotDifficultyLevel botDifficultyLevel){
        return switch(botDifficultyLevel){
            case EASY, MEDIUM , HARD->  new RandomBotPlayingStrategy();

        };
    }
}
