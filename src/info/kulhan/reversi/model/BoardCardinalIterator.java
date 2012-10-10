package info.kulhan.reversi.model;

import java.util.Iterator;

/**
 * Iterates through board through all cardinal directions from given square
 */
public class BoardCardinalIterator implements Iterator<BoardSquare>, Iterable<BoardSquare> {
    /**
     * Board
     */
    private Board board;
    
    /**
     * Start row
     */
    private int startRow;
    
    /**
     * Start column
     */
    private int startColumn;
    
    /**
     * Current row
     */
    private int currentRow;
    
    /**
     * Current column
     */
    private int currentColumn;
    
    /**
     * Current cardinal direction
     */
    private int currentCardinal;
    
    /**
     * true if cardinal has been just advanced
     */
    private boolean justAdvanced;
    
    /**
     * Cardinal directions (pair of row advance and column advance)
     */
    private static final int cardinals[][] = {
        { -1, 0 }, // north
        { -1, 1 }, // north-east
        { 0, 1 }, // east
        { 1, 1 }, // south-east
        { 1, 0 }, // south
        { 1, -1 }, // south-west
        { 0, -1 }, // west
        { -1, -1 } // north-west
    };
    
    /**
     * Create new cardinal iterator
     * @param b
     * @param r
     * @param c 
     */
    public BoardCardinalIterator(Board b, int r, int c) {
        board = b;
        startRow = currentRow = r;
        startColumn = currentColumn = c;
        currentCardinal = 0;
    }
    
    /**
     * Create new cardinal iterator
     * @param b
     * @param s 
     */
    public BoardCardinalIterator(Board b, BoardSquare s) {
        this(b, s.getRow(), s.getColumn());
    }

    /**
     * true if there's next element
     * @return 
     */
    @Override
    public boolean hasNext() {
        return currentRow >= 0 && currentRow < 8 &&
                currentColumn >= 0 && currentColumn < 8 &&
                currentCardinal < cardinals.length;
    }

    /**
     * Return next board square
     * @return 
     */
    @Override
    public BoardSquare next() {
        BoardSquare ret = new BoardSquare(currentRow, currentColumn, board.get(currentRow, currentColumn));
        
        currentRow += cardinals[currentCardinal][0];
        currentColumn += cardinals[currentCardinal][1];
        
        justAdvanced = false;
        
        if (currentRow < 0 || currentRow > 7 || currentColumn < 0 || currentColumn > 7) {
            currentRow = startRow;
            currentColumn = startColumn;
            ++currentCardinal;
            justAdvanced = true;
        }
        
        return ret;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Return itself
     * @return 
     */
    @Override
    public Iterator<BoardSquare> iterator() {
        return this;
    }
    
    /**
     * Advances to next cardinal direction
     */
    public void advanceCardinal() {
        if (!justAdvanced) {
            currentRow = startRow;
            currentColumn = startColumn;
            ++currentCardinal;
        }
    }
}
