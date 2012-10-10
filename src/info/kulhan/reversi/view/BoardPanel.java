package info.kulhan.reversi.view;

import info.kulhan.reversi.controller.IController;
import info.kulhan.reversi.model.Board;
import info.kulhan.reversi.model.BoardSquare;
import info.kulhan.reversi.model.GameState;
import info.kulhan.reversi.model.LegalMovesIterator;
import info.kulhan.reversi.model.Player;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Board panel
 */
public class BoardPanel extends JPanel implements Observer {
    /**
     * Board data
     */
    private GameState state;
    
    /**
     * Buttons representing fields on state
     */
    private JButton buttons[][];
    
    /**
     * Black player icon
     */
    private ImageIcon blackIcon;
    
    /**
     * White player icon
     */
    private ImageIcon whiteIcon;
    
    /**
     * Create new state panel
     * @param b 
     */
    public BoardPanel(GameState s, IController c) {
        state = s;
        state.addObserver(this);
        
        buttons = new JButton[8][8];
        
        setLayout(new GridLayout(8, 8));
        
        int i, j;
        
        for (i = 0; i < 8; ++i) {
            for (j = 0; j < 8; ++j) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(new BoardButtonActionListener(c, i, j));
                add(buttons[i][j]);
            }
        }
        
        blackIcon = new ImageIcon(getClass().getResource("resources/black.png"));
        whiteIcon = new ImageIcon(getClass().getResource("resources/white.png"));
        
        update(null, null);
    }

    /**
     * Update panel
     * @param o
     * @param o1 
     */
    @Override
    public void update(Observable o, Object o1) {
        Board b = state.getBoard();
        
        int i, j;
        
        for (i = 0; i < 8; ++i) {
            for (j = 0; j < 8; ++j) {
                Player p = b.get(i, j);
                
                buttons[i][j].setIcon(null);
                buttons[i][j].setEnabled(false);
                
                if (p != Player.NONE) {
                    buttons[i][j].setEnabled(true);
                    buttons[i][j].setIcon(p == Player.BLACK ? blackIcon : whiteIcon);
                }
            }
        }

        for (BoardSquare sq : new LegalMovesIterator(state)) {
            buttons[sq.getRow()][sq.getColumn()].setEnabled(true);
        }
    }
    
    /**
     * Listen to board button clicks
     */
    private class BoardButtonActionListener implements ActionListener {
        /**
         * Board panel
         */
        private IController controller;
        
        /**
         * Row on state
         */
        private int row;
        
        /**
         * Column on state
         */
        private int column;
        
        /**
         * Create new listener
         * @param ctrl
         * @param r
         * @param c 
         */
        public BoardButtonActionListener(IController ctrl, int r, int c) {
            controller = ctrl;
            row = r;
            column = c;
        }

        /**
         * Fired when the button is clicked, calls controller
         * @param ae 
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (state.getBoard().get(row, column) != Player.NONE) { return; }
        
            controller.placeStone(row, column);
        }
        
    }
}
