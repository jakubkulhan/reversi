package info.kulhan.reversi.view;

import info.kulhan.reversi.controller.IController;

/**
 * View interface
 */
public interface IView {

    public void showMain(IController controller);

    public void showCannotSave();

    public void showCannotLoad();
    
}
