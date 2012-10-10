package info.kulhan.reversi.view;

import info.kulhan.reversi.controller.IController;
import info.kulhan.reversi.model.BoardSquare;
import info.kulhan.reversi.model.GameState;
import info.kulhan.reversi.model.LegalMovesIterator;
import info.kulhan.reversi.model.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Shows state of current move and player
 */
public class MovePanel extends JPanel implements Observer {
    /**
     * Game state
     */
    private GameState state;
    
    /**
     * Game controller
     */
    private IController controller;
    
    /**
     * Player label, shows icon of player on the move, or text with result of match
     */
    private JLabel playerLabel;
    
    /**
     * Pass button, when clicked passes play to another player
     */
    private JButton passButton;
    
    /**
     * Black player icon
     */
    private ImageIcon blackIcon;
    
    /**
     * White player icon
     */
    private ImageIcon whiteIcon;
    
    /**
     * Create new move panel
     * @param s
     * @param c 
     */
    public MovePanel(GameState s, IController c) {
        state = s;
        state.addObserver(this);
        
        controller = c;
        
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        playerLabel = new JLabel();
        add(playerLabel);
        
        add(Box.createGlue());
        
        passButton = new JButton("PAS");
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                controller.pass();
            }
        });
        add(passButton);
        
        blackIcon = new ImageIcon(getClass().getResource("resources/black.png"));
        whiteIcon = new ImageIcon(getClass().getResource("resources/white.png"));
        
        update(null, null);
    }

    /**
     * Called when state changed
     * @param o
     * @param o1 
     */
    @Override
    public void update(Observable o, Object o1) {
        playerLabel.setIcon(null);
        playerLabel.setText(null);
        passButton.setEnabled(false);
        
        if (state.isGameEnded()) {
            int blackScore = 0, whiteScore = 0;
            
            for (BoardSquare sq : state.getBoard()) {
                if (sq.getPlayer() == Player.BLACK) {
                    ++blackScore;
                } else if (sq.getPlayer() == Player.WHITE) {
                    ++whiteScore;
                }
            }
            
            playerLabel.setText("Konec hry. Černý: " + blackScore +
                    ". Bílý: " + whiteScore + ". " +
                    (blackScore != whiteScore
                        ? (
                            (blackScore > whiteScore && blackScore != whiteScore ? "Černý" : "Bílý") +
                            " vyhrál.")
                        : ""));
            
        } else {
            Player p = state.getCurrentPlayer();

            if (p != Player.NONE) {
                playerLabel.setIcon(p == Player.BLACK ? blackIcon : whiteIcon);
            }

            playerLabel.requestFocus();

            if (new LegalMovesIterator(state).hasNext()) {
                if (new LegalMovesIterator(new GameState(state, state.getOpponentPlayer())).hasNext()) {
                    passButton.setEnabled(true);
                    
                } else {
                    controller.endGame();
                }
            }
        }
    }
}
