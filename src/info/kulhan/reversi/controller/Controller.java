package info.kulhan.reversi.controller;

import info.kulhan.reversi.model.BoardCardinalIterator;
import info.kulhan.reversi.model.BoardSquare;
import info.kulhan.reversi.model.GameState;
import info.kulhan.reversi.model.GameState.Type;
import info.kulhan.reversi.model.LegalMovesIterator;
import info.kulhan.reversi.view.GUIView;
import info.kulhan.reversi.view.IView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

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
     * View
     */
    private IView view;
    
    /**
     * Scores of squares for AI
     */
    private static final byte[][] scores = {
        { 99,  -8,   8,   6,   6,   8,  -8, 99 },
        { -8, -24,  -4,  -3,  -3,  -4, -24, -8 },
        {  8,  -4,   7,   4,   4,   7,  -4,  8 },
        {  6,  -3,   4,   0,   0,   4,   3,  6 },
        {  6,  -3,   4,   0,   0,   4,   3,  6 },
        {  8,  -4,   7,   4,   4,   7,  -4,  8 },
        { -8, -24,  -4,  -3,  -3,  -4, -24, -8 },
        { 99,  -8,   8,   6,   6,   8,  -8, 99 }
    };
    
    /**
     * Create new controller
     * @param s 
     */
    public Controller(GameState s, IView v) {
        state = s;
        view = v;
    }

    /**
     * Make human player move
     * @param row
     * @param column 
     */
    @Override
    public void move(int row, int column) {
        state.begin();
        
        placeStone(row, column);
        state.setCurrentPlayer(state.getOpponentPlayer());
        
        if (state.getType() == GameState.Type.TWO_PLAYER) {
            state.setState(GameState.State.INTERACTIVE);
            state.end();
            
        } else if (state.getType() == GameState.Type.ONE_PLAYER) {
            state.setState(GameState.State.WAITING);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    List<BoardSquare> legalMoves = new LinkedList();

                    for (BoardSquare sq : new LegalMovesIterator(state)) {
                        legalMoves.add(sq);
                    }

                    if (legalMoves.size() > 0) {
                        BoardSquare max = legalMoves.get(0);
                        
                        for (BoardSquare sq : legalMoves) {
                            if (scores[sq.getRow()][sq.getColumn()] > scores[max.getRow()][max.getColumn()]) {
                                max = sq;
                            }
                        }
                        
                        placeStone(max.getRow(), max.getColumn());
                    }

                    state.setCurrentPlayer(state.getOpponentPlayer());
                    state.setState(GameState.State.INTERACTIVE);
                    
                    state.end();
                }
            }, 500);
            
        } else {
            throw new RuntimeException("Game type " + state.getType() + " not implemented.");
        }
    }

    /**
     * Place stone of the current player on the board, flank opponent stones
     * @param row
     * @param column 
     */
    private void placeStone(int row, int column) {
        Set<BoardSquare> sqs = new HashSet<BoardSquare>();
        BoardCardinalIterator it = new BoardCardinalIterator(state.getBoard(), row, column);
        
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
        view.showSelectGameType(new IView.IGameTypeSelector() {
            @Override
            public void gameTypeSelected(Type t) {
                state.reset(new GameState(t));
            }
        });
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
            view.showCannotLoad();
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
            view.showCannotSave();
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
        state.setState(GameState.State.ENDED);
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
    
    /**
     * Application entry point
     * @param args 
     */
    public static void main(String[] args) {
        GameState s = new GameState();
        IView v = new GUIView(s);
        IController c = new Controller(s, v);
        
        v.showMain(c);
    }
}
