package hwl.controller;

import hwl.constraint.IMainController;
import hwl.constraint.IMainView;
import hwl.model.Database;

public class MainController implements IMainController {

    private final IMainView view;

    public MainController(IMainView view) {
        this.view = view;

        Database.getInstance().addOnErrorListener(exc -> this.view.showErrorDialog(exc.getMessage()));

        Database.getInstance().postReload();
    }

    @Override
    public void onWindowClosed() {
        Database.getInstance().shutdownAndJoin();
    }
}
