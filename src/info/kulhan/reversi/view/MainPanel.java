package info.kulhan.reversi.view;

import info.kulhan.reversi.controller.Controller;
import info.kulhan.reversi.controller.IController;
import info.kulhan.reversi.model.GameState;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
    
    /**
     * Application entry point, create window and place instance of this class into it
     * @param args 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameState s = new GameState();
                IController c = new Controller(s);
                MainPanel p = new MainPanel(s, c);
                
                JFrame f = new JFrame();
                
                f.add(p);
                f.setTitle("REVERSI");
                f.setSize(500, 500);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }
}
