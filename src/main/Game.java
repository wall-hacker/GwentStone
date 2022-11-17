package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio.DecksInput;
import fileio.GameInput;
import fileio.Input;
import fileio.StartGameInput;
import main.cards.Card;
import main.cards.Disciple;
import main.cards.TheCursedOne;
import main.cards.Firestorm;
import main.cards.TheRipper;
import main.cards.Warden;
import main.cards.Winterfell;
import main.cards.Berserker;
import main.cards.Goliath;
import main.cards.HeartHound;
import main.cards.Miraj;
import main.cards.Sentinel;
import main.heroes.Hero;
import main.heroes.EmpressThorina;
import main.heroes.KingMudface;
import main.heroes.LordRoyce;
import main.heroes.GeneralKocioraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class Game {
    private ObjectMapper objectMapper;
    private Input input;
    private ArrayNode output;
    private int playerOneWins = 0, playerTwoWins = 0, totalGamesPlayed = 0;

    public Game(final ObjectMapper objectMapper, final Input input, final ArrayNode output) {
        this.objectMapper = objectMapper;
        this.input = input;
        this.output = output;
    }

    /**
     * playerOneWins getter
     * @return
     */
    public int getPlayerOneWins() {
        return playerOneWins;
    }

    /**
     * playerTwoWins getter
     * @return
     */
    public int getPlayerTwoWins() {
        return playerTwoWins;
    }

    /**
     * totalGamesPlayed getter
     * @return
     */
    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    /**
     * playerOneWins setter
     * @param playerOneWins
     */
    public void setPlayerOneWins(final int playerOneWins) {
        this.playerOneWins = playerOneWins;
    }

    /**
     * playerTwoWins setter
     * @param playerOneWins
     */
    public void setPlayerTwoWins(final int playerTwoWins) {
        this.playerTwoWins = playerTwoWins;
    }

    /**
     * totalGamesPlayed setter
     * @param playerOneWins
     */
    public void setTotalGamesPlayed(final int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    /**
     * a method that calls all the different Card subclass constructors in order to initialize
     * the chosen player deck
     * @param startGame
     * @param deck
     * @param player
     * @param playerIdx
     */
    public void chooseDeck(final StartGameInput startGame, final DecksInput deck,
                           final Player player, final int playerIdx) {
        ArrayList<CardInput> chosenDeck = new ArrayList<>();
        chosenDeck = deck.getDecks().get(playerIdx);
        for (int i = 0; i < chosenDeck.size(); i++) {
            String name = chosenDeck.get(i).getName();
            Card card = null;
            switch (name) {
                case "Winterfell":
                    card = new Winterfell(chosenDeck.get(i));
                    break;
                case "Firestorm":
                    card = new Firestorm(chosenDeck.get(i));
                    break;
                case "Heart Hound":
                    card = new HeartHound(chosenDeck.get(i));
                    break;
                case "The Ripper":
                    card = new TheRipper(chosenDeck.get(i));
                    break;
                case "Miraj":
                    card = new Miraj(chosenDeck.get(i));
                    break;
                case "The Cursed One":
                    card = new TheCursedOne(chosenDeck.get(i));
                    break;
                case "Disciple":
                    card = new Disciple(chosenDeck.get(i));
                    break;
                case "Sentinel":
                    card = new Sentinel(chosenDeck.get(i));
                    break;
                case "Berserker":
                    card = new Berserker(chosenDeck.get(i));
                    break;
                case "Goliath":
                    card = new Goliath(chosenDeck.get(i));
                    break;
                case "Warden":
                    card = new Warden(chosenDeck.get(i));
                    break;
                default:
                    break;
            }
            player.getDeck().add(card);
        }
        Collections.shuffle(player.getDeck(), new Random(startGame.getShuffleSeed()));
    }

    /**
     * a method that calls all the different Hero subclass constructors in order to initialize
     * the chosen player hero
     * @param startGame
     * @param player1
     * @param player2
     */
    public void chooseHero(final StartGameInput startGame, final Player player1,
                           final Player player2) {
        Hero hero1 = null;
        String name1 = startGame.getPlayerOneHero().getName();
        switch (name1) {
            case "Lord Royce":
                hero1 = new LordRoyce(startGame.getPlayerOneHero());
                break;
            case "Empress Thorina":
                hero1 = new EmpressThorina(startGame.getPlayerOneHero());
                break;
            case "General Kocioraw":
                hero1 = new GeneralKocioraw(startGame.getPlayerOneHero());
                break;
            case "King Mudface":
                hero1 = new KingMudface(startGame.getPlayerOneHero());
                break;
            default:
                break;
        }
        player1.setHero(hero1);

        Hero hero2 = null;
        String name2 = startGame.getPlayerTwoHero().getName();
        switch (name2) {
            case "Lord Royce":
                hero2 = new LordRoyce(startGame.getPlayerTwoHero());
                break;
            case "Empress Thorina":
                hero2 = new EmpressThorina(startGame.getPlayerTwoHero());
                break;
            case "General Kocioraw":
                hero2 = new GeneralKocioraw(startGame.getPlayerTwoHero());
                break;
            case "King Mudface":
                hero2 = new KingMudface(startGame.getPlayerTwoHero());
                break;
            default:
                break;
        }
        player2.setHero(hero2);
    }



    /**
     * the main method of the program which parses all input data including games and then actions
     */
    public void start() {

        DecksInput playerOneDecks = input.getPlayerOneDecks();
        DecksInput playerTwoDecks = input.getPlayerTwoDecks();
        ArrayList<GameInput> games = input.getGames();

        for (int i = 0; i < games.size(); i++) {
            Debug debug = new Debug();
            Solver solver = new Solver(objectMapper, output, this);
            Player player1 = new Player();
            Player player2 = new Player();

            StartGameInput startGame = new StartGameInput();
            startGame = games.get(i).getStartGame();

            ArrayList<ActionsInput> actions = new ArrayList<>();
            actions = games.get(i).getActions();

            chooseDeck(startGame, playerOneDecks, player1, startGame.getPlayerOneDeckIdx());
            chooseDeck(startGame, playerTwoDecks, player2, startGame.getPlayerTwoDeckIdx());
            chooseHero(startGame, player1, player2);

            GameBoard gameBoard = new GameBoard(startGame);
            gameBoard.nextRound(player1, player2);


            for (int j = 0; j < actions.size(); j++) {

                ActionsInput curentAction = actions.get(j);
                int playerIdx = curentAction.getPlayerIdx();
                String command = curentAction.getCommand();

                switch (command) {
                    case "getPlayerDeck":
                        solver.solveGetPlayerDeck(playerIdx, player1, player2, debug);
                        break;
                    case "getCardsInHand":
                        solver.solveGetCardsInHand(playerIdx, player1, player2, debug);
                        break;
                    case "getPlayerHero":
                        solver.solveGetPlayerHero(playerIdx, player1, player2);
                        break;
                    case "getPlayerTurn":
                        solver.solveGetPlayerTurn(gameBoard, debug);
                        break;
                    case "endPlayerTurn":
                        gameBoard.endPlayerTurn(player1, player2);
                        break;
                    case "placeCard":
                        solver.solvePlaceCard(curentAction, gameBoard, player1, player2);
                        break;
                    case "getPlayerMana":
                        solver.solveGetPlayerMana(debug, playerIdx, player1, player2);
                        break;
                    case "getCardsOnTable":
                        solver.solveGetCardsOnTable(debug, gameBoard);
                        break;
                    case "getEnvironmentCardsInHand":
                        solver.solveGetEnvironmentCardsInHand(debug, playerIdx, player1, player2);
                        break;
                    case "getCardAtPosition":
                        solver.solveGetCardAtPosition(curentAction, debug, gameBoard);
                        break;
                    case "useEnvironmentCard":
                        solver.solveUseEnvironmentCard(curentAction, player1, player2, gameBoard);
                        break;
                    case "getFrozenCardsOnTable":
                        solver.solveGetFrozenCardsOnTable(debug, gameBoard);
                        break;
                    case "cardUsesAttack":
                        solver.solveCardUsesAttack(curentAction, gameBoard);
                        break;
                    case "cardUsesAbility":
                        solver.solveCardUsesAbility(curentAction, gameBoard);
                        break;
                    case "useAttackHero":
                        solver.solveUseAttackHero(curentAction, player1, player2, gameBoard);
                        break;
                    case "useHeroAbility":
                        solver.solveUseHeroAbility(curentAction, player1, player2, gameBoard);
                        break;
                    case "getPlayerOneWins":
                        solver.solveGetPlayerOneWins();
                        break;
                    case "getPlayerTwoWins":
                        solver.solveGetPlayerTwoWins();
                        break;
                    case "getTotalGamesPlayed":
                        solver.solveGetTotalGamesPlayed();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
