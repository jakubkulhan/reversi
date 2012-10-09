package info.kulhan.reversi.model;

/**
 * Square on board
 */
public class BoardSquare {
    /**
     * Row
     */
    private int row;
    
    /**
     * Column
     */
    private int column;
    
    /**
     * Player
     */
    private Player player;
    
    /**
     * Create new square from row and column (player will be initialized to NONE)
     * @param r
     * @param c 
     */
    public BoardSquare(int r, int c) {
        this(r, c, Player.NONE);
    }

    /**
     * Create new square from row, column and player
     * @param r
     * @param c 
     */
    public BoardSquare(int r, int c, Player p) {
        row = r;
        column = c;
        player = p;
    }

    /**
     * Create coordinate copy
     * @param c 
     */
    public BoardSquare(BoardSquare c) {
        row = c.row;
        column = c.column;
        player = c.player;
    }

    /**
     * Return row
     * @return 
     */
    public int getRow() {
        return row;
    }

    /**
     * Return column
     * @return 
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * Return player
     * @return 
     */
    public Player getPlayer() {
        return player;
    }
}
