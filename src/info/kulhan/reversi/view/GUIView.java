package info.kulhan.reversi.view;

import info.kulhan.reversi.controller.IController;
import info.kulhan.reversi.model.GameState;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author admin
 */
public class GUIView implements IView {
    /**
     * Game state
     */
    private GameState state;
    
    /**
     * Create new GUI view
     * @param s 
     */
    public GUIView(GameState s) {
        state = s;
    }

    /**
     * Show main game window
     * @param controller 
     */
    @Override
    public void showMain(final IController controller) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainPanel p = new MainPanel(state, controller);
                
                JFrame f = new JFrame();
                
                f.add(p);
                f.setTitle("REVERSI");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }

    /**
     * Show cannot save message dialog
     */
    @Override
    public void showCannotSave() {
        JOptionPane.showMessageDialog(null, "Nepodařilo se uložit hru.");
    }

    /**
     * Show cannot load message dialog
     */
    @Override
    public void showCannotLoad() {
        JOptionPane.showMessageDialog(null, "Nepodařilo se načíst hru.");
    }
    
}
