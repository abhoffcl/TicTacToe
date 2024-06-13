package service.stategy.GameWinningStrategy;

import model.GameWinningStrategyName;

public class GameWinningStrategyFactory {
    public static GameWinningStrategy getGameWinningStrategy(GameWinningStrategyName winningStrategyName,int dimension){
        return switch(winningStrategyName){
            case ORDERNSQUAREWINNINGSTRATEGY ->new OrderNSquareWinningStrategy();
            case ORDERONEWINNINGSTRATEGY ->  new OrderOneWinningStrategy(dimension);
            case ORDERNWINNINGSTRATEGY -> new OrderNWinningStrategy();

        };
    }
}
