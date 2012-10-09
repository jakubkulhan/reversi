package info.kulhan.reversi.view;

import info.kulhan.reversi.controller.IController;
import info.kulhan.reversi.model.GameState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Menu panel
 */
public class MenuPanel extends JPanel implements ActionListener {
    /**
     * Game controller
     */
    private IController controller;
    
    /**
     * New game button
     */
    private JButton newGameButton;
    
    /**
     * Load game button
     */
    private JButton loadGameButton;
    
    /**
     * Save game button
     */
    private JButton saveGameButton;
    
    /**
     * Quit game button
     */
    private JButton quitGameButton;
    
    /**
     * Create new menu panel
     * @param s
     * @param c 
     */
    public MenuPanel(GameState s, IController c) {
        controller = c;
        
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        newGameButton = new JButton("Nová hra");
        newGameButton.addActionListener(this);
        add(newGameButton);
        
        loadGameButton = new JButton("Nahrát hru");
        loadGameButton.addActionListener(this);
        loadGameButton.setEnabled(controller.isGameLoadable());
        add(loadGameButton);
        
        saveGameButton = new JButton("Uložit hru");
        saveGameButton.addActionListener(this);
        add(saveGameButton);
        
        quitGameButton = new JButton("Konec");
        quitGameButton.addActionListener(this);
        add(quitGameButton);
    }

    /**
     * Called as a reponse to panel button clicks
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton b = (JButton) ae.getSource();
        
        if (b == newGameButton) {
            controller.newGame();
            
        } else if (b == loadGameButton) {
            controller.loadGame();
            
        } else if (b == saveGameButton) {
            controller.saveGame();
            loadGameButton.setEnabled(true);
            
        } else if (b == quitGameButton) {
            controller.quitGame();
        }
    }
}
