package info.kulhan.reversi.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Observable;

/**
 * Represents game board
 */
public class Board extends Observable implements Serializable, Iterable<BoardSquare> {
    /**
     * Board data
     */
    private Player data[][];
    
    /**
     * true if begin() has been called
     */
    private boolean begun;

    /**
     * Create new game board
     */
    public Board() {
        data = new Player[8][8];
        begun = false;
        reset();
    }

    /**
     * Create game board from existing board
     * @param board 
     */
    public Board(Board b) {
        data = new Player[8][8];
        begun = false;
        reset(b);
    }
    
    /**
     * Reset board to new game state
     */
    public void reset() {
        int i, j;
        
        for (i = 0; i < 8; ++i) {
            for (j = 0; j < 8; ++j) {
                data[i][j] = Player.NONE;
            }
        }
        
        data[3][4] = data[4][3] = Player.BLACK;
        data[3][3] = data[4][4] = Player.WHITE;
        
        setChanged();
        notifyObservers();
    }
    
    /**
     * Reset board to given state
     * @param b
     */
    public void reset(Board b) {
        int i, j;
        
        for (i = 0; i < 8; ++i) {
            for (j = 0; j < 8; ++j) {
                data[i][j] = b.data[i][j];
            }
        }
        
        setChanged();
        notifyObservers();
    }
    
    /**
     * Return game board data on position
     * @param row
     * @param column
     * @return 
     */
    public Player get(int row, int column) {
        return data[row][column];
    }
    
    /**
     * Change board data
     * If value changes, observers will be notified (unless begin() has been called).
     * @param row
     * @param column
     * @param newValue 
     */
    public void set(int row, int column, Player newValue) {
        Player oldValue = data[row][column];
        data[row][column] = newValue;
        
        if (newValue != oldValue) {
            setChanged();
        }
        
        if (!begun) {
            notifyObservers();
        }
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
     * Return board iterator
     * @return 
     */
    @Override
    public Iterator<BoardSquare> iterator() {
        return new Iterator<BoardSquare>() {
            private int r = 0, c = 0;

            @Override
            public boolean hasNext() {
                return r < 8 && c < 8;
            }

            @Override
            public BoardSquare next() {
                BoardSquare sq = new BoardSquare(r, c, data[r][c]);
                
                ++c;
                if (c > 7) {
                    ++r;
                    c = 0;
                }
                
                return sq;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        };
    }
}
