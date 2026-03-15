package ui;



import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProxyEventBus {

    private static final ObservableList<RequestEntry> requestHistory =
            FXCollections.observableArrayList();

    public static ObservableList<RequestEntry> getRequestHistory() {
        return requestHistory;
    }

    public static void publish(RequestEntry entry) {

        Platform.runLater(() -> {
            requestHistory.add(entry);
        });

    }
}