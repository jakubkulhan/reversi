package info.kulhan.reversi.model;

import java.util.Iterator;

/**
 * Iterates through all legal moves available for player
 */
public class LegalMovesIterator implements Iterator<BoardSquare>, Iterable<BoardSquare> {
    /**
     * Game state
     */
    private GameState state;
    
    /**
     * Board iterator
     */
    private Iterator<BoardSquare> boardIterator;
    
    /**
     * Legal move returned by next()
     */
    private BoardSquare nextLegalMove;
    
    /**
     * Create new legal move iterator
     * @param s 
     */
    public LegalMovesIterator(GameState s) {
        state = s;
        boardIterator = s.getBoard().iterator();
        findNextLegalMove();
    }
    
    /**
     * Find next legal move
     */
    private void findNextLegalMove() {
        nextLegalMove = null;
        
        while (boardIterator.hasNext()) {
            BoardSquare sq = boardIterator.next();
            
            BoardCardinalIterator it =
                    new BoardCardinalIterator(state.getBoard(), sq.getRow(), sq.getColumn());
            
            boolean ok = false;
            
            if (sq.getPlayer() != Player.NONE) { continue; }

            for (BoardSquare t : it) {
                if (t.getRow() == sq.getRow() && t.getColumn() == sq.getColumn()) {
                    continue;

                } else if (t.getPlayer() == state.getOpponentPlayer()) {
                    ok = true;

                } else if (t.getPlayer() == Player.NONE || t.getPlayer() == state.getCurrentPlayer() && !ok) {
                    ok = false;
                    it.advanceCardinal();

                } else if (ok) {
                    nextLegalMove = sq;
                    return;
                }
            }
        }
    }

    /**
     * Return true if there are more legal moves
     * @return 
     */
    @Override
    public boolean hasNext() {
        return nextLegalMove != null;
    }

    /**
     * Return legal move
     * @return 
     */
    @Override
    public BoardSquare next() {
        BoardSquare m = nextLegalMove;
        findNextLegalMove();
        return m;
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
}
