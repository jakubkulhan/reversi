package info.kulhan.reversi.view;

import info.kulhan.reversi.controller.IController;
import info.kulhan.reversi.model.GameState;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Main panel
 */
public class MainPanel extends JPanel {
    /**
     * Create new main panel
     * @param s
     * @param c 
     */
    public MainPanel(GameState s, IController c) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(new MenuPanel(s, c));
        add(new BoardPanel(s, c));
        add(new MovePanel(s, c));
    }
}
