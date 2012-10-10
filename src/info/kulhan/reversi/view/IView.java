package info.kulhan.reversi.view;

import info.kulhan.reversi.controller.IController;
import info.kulhan.reversi.model.GameState.Type;

/**
 * View interface
 */
public interface IView {

    public void showMain(IController controller);

    public void showCannotSave();

    public void showCannotLoad();

    public void showSelectGameType(IGameTypeSelector s);
    
    public interface IGameTypeSelector {
        public void gameTypeSelected(Type t);
    }
    
}
