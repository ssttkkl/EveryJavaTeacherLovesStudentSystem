package hwl.controller;

import hwl.model.Database;
import hwl.view.MainWindow;

public class MainController {

    private final MainWindow view;

    public MainController(MainWindow view) {
        this.view = view;

        Database.getInstance().addOnErrorListener(exc -> this.view.showErrorDialog(exc.getMessage()));

        Database.getInstance().postReload();
    }

    public void onWindowClosed() {
        Database.getInstance().shutdownAndJoin();
    }
}
