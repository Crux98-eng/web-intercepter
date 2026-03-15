package  com.example.web_tester;

import proxy.ProxyServer;
import ui.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {


        ProxyServer proxy = new ProxyServer(443);

        Thread proxyThread = new Thread(proxy);
        proxyThread.setDaemon(true);
        proxyThread.start();

        MainView view = new MainView();
        view.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}