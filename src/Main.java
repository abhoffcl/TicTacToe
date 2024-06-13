
import controller.GameController;
import exception.InvalidInputException;
import model.*;
import service.stategy.GameWinningStrategy.GameWinningStrategy;
import service.stategy.GameWinningStrategy.GameWinningStrategyFactory;

import java.util.*;

public class Main {
    public static void playGame() {

        GameController gameController = new GameController();
        List<Player> players = new ArrayList<>();
        int playerId = 0, humanPlayers = 0, dimension = 0, playerIndex = 0;
        char botChoice = 'a';
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to TicTacToe !");
        System.out.println(" Enter the dimension of the board ");
        dimension = sc.nextInt();
        System.out.println("Do you want a bot,Y/N ?");
        botChoice = sc.next().charAt(0);
        if (botChoice == 'Y' || botChoice == 'y') {
            Bot bot = new Bot(++playerId, "Chitti", '*', BotDifficultyLevel.EASY);
            players.add(bot);
            humanPlayers = dimension - 2;
        } else if (botChoice == 'N' || botChoice == 'n')
            humanPlayers = dimension - 1;
        else
            throw new InvalidInputException("Invalid character input");

        for (int i = 0; i < humanPlayers; i++) {
            ++playerId;
            System.out.println("Enter name of player");
            String playerName = sc.next();
            System.out.println("Enter the symbol");
            char symbol = sc.next().charAt(0);
            Player player = new Player(playerId, playerName, symbol, PlayerType.HUMAN);
            players.add(player);
        }
        Collections.shuffle(players);
        // create board
        Board board = new Board(dimension);
        //set the winning strategy
        GameWinningStrategy gameWinningStrategy = GameWinningStrategyFactory.getGameWinningStrategy(
                GameWinningStrategyName.ORDERONEWINNINGSTRATEGY, dimension);
        Game game = gameController.createGame(board, players, gameWinningStrategy);

        while (game.getGamestatus() == GameStatus.INPROGRESS) {
            System.out.println("current status of board");
            gameController.displayBoard(game);
            if (!(game.getMoves() == null || game.getMoves().isEmpty())) {
                Move lastMove = game.getMoves().get(game.getMoves().size() - 1);
                if (lastMove.getPlayer().getType() == PlayerType.HUMAN) {
                    System.out.println("Do you want to undo,y/n ");
                    char ch = sc.next().charAt(0);
                    if (ch == 'y' || ch == 'Y') {
                        Board currentBoard = gameController.undoMove(game, lastMove);
                        game.setCurrentBoard(currentBoard);
                        System.out.println("Board status after undo");
                        gameController.displayBoard(game);
                        playerIndex--;


                    }
                }
            }
            playerIndex = playerIndex % players.size();
            Move movePlayed = gameController.executeMove(game, players.get(playerIndex++));
            game.getMoves().add(movePlayed); // add moves
            game.getBoardStates().add(game.getCurrentBoard()); // add board states
            Player winner = gameController.checkWinner(game, movePlayed);
            if (winner != null) {
                System.out.println("WINNER IS : " + winner.getName());
                for(Board bd: game.getBoardStates()){
                    gameController.displayBoard(game);
                    System.out.println("------------------");
                }
                break;
            }
            if (game.getMoves().size() == dimension * dimension) {
                System.out.println("GAME IS DRAW");
                break;
            }
        }
        System.out.println("Final Board Status");
        gameController.displayBoard(game);

    }
    public static void main(String[] args) {
       Scanner sc= new Scanner(System.in);
        while(true){
            playGame();
            System.out.println("Do you want to replay,1/0 ? ");
            int ch= sc.nextInt();
                if (ch == 0)
                    break;
        }
        sc.close();
    }
}
