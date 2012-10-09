package info.kulhan.reversi.controller;

import info.kulhan.reversi.model.BoardCardinalIterator;
import info.kulhan.reversi.model.BoardSquare;
import info.kulhan.reversi.model.GameState;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 * Game controller
 */
public class Controller implements IController {
    /**
     * Save game file
     */
    private static final String SAVE_FILE = ".reversi.sav";
    
    /**
     * Game state
     */
    private GameState state;
    
    /**
     * Create new controller
     * @param s 
     */
    public Controller(GameState s) {
        state = s;
    }

    /**
     * Place stone of current player at given row and column
     * @param row
     * @param column 
     */
    @Override
    public void placeStone(int row, int column) {
        Set<BoardSquare> sqs = new HashSet<BoardSquare>();
        BoardCardinalIterator it = new BoardCardinalIterator(state.getBoard(), row, column);
        
        state.begin();
        
        state.getBoard().set(row, column, state.getCurrentPlayer());
        
        for (BoardSquare sq : it) {
            if (sq.getRow() == row && sq.getColumn() == column) {
                sqs.clear();
                
            } else if (sq.getPlayer() == state.getOpponentPlayer()) {
                sqs.add(sq);
                
            } else if (sq.getPlayer() == state.getCurrentPlayer()) {
                for (BoardSquare t : sqs) {
                    state.getBoard().set(t.getRow(), t.getColumn(), state.getCurrentPlayer());
                }
                
                it.advanceCardinal();
                
            } else { // sq.getPlayer() == Player.NONE
                it.advanceCardinal();
            }
        }
        
        state.setCurrentPlayer(state.getOpponentPlayer());
        
        state.end();
    }

    /**
     * Pass play to opponent
     */
    @Override
    public void pass() {
        state.setCurrentPlayer(state.getOpponentPlayer());
    }

    /**
     * Start new game
     */
    @Override
    public void newGame() {
        state.reset();
    }

    /**
     * Load saved game
     */
    @Override
    public void loadGame() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(getSaveFile()));
            
            state.reset((GameState) in.readObject());
            
            in.close();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nepodařilo se načíst hru.");
        }
    }

    /**
     * Save current game
     */
    @Override
    public void saveGame() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getSaveFile()));
            
            out.writeObject(state);
            
            out.close();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nepodařilo se uložit hru.");
        }
    }

    /**
     * Exit application
     */
    @Override
    public void quitGame() {
        System.exit(0);
    }

    /**
     * Finish current game
     */
    @Override
    public void endGame() {
        state.endGame();
    }

    /**
     * Return true if there is loadable game
     * @return 
     */
    @Override
    public boolean isGameLoadable() {
        return getSaveFile().exists();
    }
    
    /**
     * Return absolute file to save file
     * @return 
     */
    private File getSaveFile() {
        return new File(System.getProperty("user.home") + File.separator + SAVE_FILE);
    }
}
