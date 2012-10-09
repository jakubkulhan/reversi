package info.kulhan.reversi.model;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * Game state
 */
public class GameState extends Observable implements Serializable, Observer {
    /**
     * Player on the move
     */
    private Player currentPlayer;
    
    /**
     * Board
     */
    private Board board;
    
    /**
     * true if game ended
     */
    private boolean gameEnded;
    
    /**
     * true if begin() has been called
     */
    private boolean begun;
    
    /**
     * Create new game state
     */
    public GameState() {
        board = new Board();
        board.addObserver(this);
        begun = false;
        reset();
    }
    
    /**
     * Create new game state from different state
     */
    public GameState(GameState s) {
        board = new Board(s.board);
        currentPlayer = s.currentPlayer;
        begun = false;
    }
    
    /**
     * Create new game state from different state
     */
    public GameState(GameState s, Player newCurrentPlayer) {
        this(s);
        currentPlayer = newCurrentPlayer;
    }
    
    /**
     * Return board
     * @return
     */
    public Board getBoard() {
        return board;
    }
    
    /**
     * Return current player
     * @return 
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set current player
     * @param p 
     */
    public void setCurrentPlayer(Player p) {
        if (p == currentPlayer) { return; }
        
        currentPlayer = p;
        setChanged();
        
        if (!begun) {
            notifyObservers();
        }
    }
    
    /**
     * Return current player
     * @return 
     */
    public Player getOpponentPlayer() {
        if (currentPlayer == Player.NONE) {
            return Player.NONE;
        }
        
        return currentPlayer == Player.BLACK ? Player.WHITE : Player.BLACK;
    }

    /**
     * Listen for board updates
     * @param o
     * @param o1 
     */
    @Override
    public void update(Observable o, Object o1) {
        setChanged();
        
        if (!begun) {
            notifyObservers();
        }
    }

    /**
     * Reset to new game state
     */
    public void reset() {
        currentPlayer = Player.BLACK;
        gameEnded = false;
        board.reset(); // will trigger update() and thus notify observers
    }
    
    /**
     * Reset to given state
     * @param s 
     */
    public void reset(GameState s) {
        currentPlayer = s.currentPlayer;
        gameEnded = s.gameEnded;
        board.reset(s.board); // will trigger update() and thus notify observers
    }
    
    /**
     * Begin not notifying observers
     */
    public void begin() {
        begun = true;
    }
    
    /**
     * End not notifying observers
     * If any value changed, observers will be notified now.
     */
    public void end() {
        begun = false;
        notifyObservers();
    }
    
    /**
     * Return true if game ended
     * @return 
     */
    public boolean isGameEnded() {
        return gameEnded;
    }
    
    /**
     * Make game ended
     */
    public void endGame() {
        gameEnded = true;
        
        setChanged();
        
        if (!begun) {
            notifyObservers();
        }
    }
}
