package ui;



import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

public class MainView {

    private final TableView<RequestEntry> table = new TableView<>();

    private final TextArea requestViewer = new TextArea();
    private final TextArea responseViewer = new TextArea();

    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        createTable();

        TabPane viewerTabs = new TabPane();

        Tab requestTab = new Tab("Request", requestViewer);
        Tab responseTab = new Tab("Response", responseViewer);

        viewerTabs.getTabs().addAll(requestTab, responseTab);

        SplitPane splitPane = new SplitPane();
        table.setStyle("-fx-background-color: #2c2c3c; -fx-text-fill: white;");

        splitPane.getItems().addAll(table, viewerTabs);
        splitPane.setDividerPositions(0.4);

        root.setCenter(splitPane);

        root.setTop(createToolbar());


        Scene scene = new Scene(root, 1200, 700);
        
        root.setStyle("-fx-background-color:#2c2c3c;");
        stage.setTitle("Web Interceptor");
        stage.setScene(scene);

        stage.show();

        bindData();
    }

    private ToolBar createToolbar() {

        ToggleButton interceptToggle = new ToggleButton("Intercept ON");

        interceptToggle.setSelected(true);

        interceptToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal)
                interceptToggle.setText("Intercept ON");
            else
                interceptToggle.setText("Intercept OFF");

        });

        return new ToolBar(interceptToggle);
    }

    private void createTable() {

        TableColumn<RequestEntry, String> methodCol = new TableColumn<>("Method");
        methodCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getMethod()));

        TableColumn<RequestEntry, String> hostCol = new TableColumn<>("Host");
        hostCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getHost()));

        TableColumn<RequestEntry, String> pathCol = new TableColumn<>("Path");
        pathCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getPath()));

        TableColumn<RequestEntry, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));

        table.getColumns().addAll(methodCol, hostCol, pathCol, statusCol);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal != null) {

                requestViewer.setText(newVal.getRawRequest());
                responseViewer.setText(newVal.getRawResponse());

            }

        });
    }

    private void bindData() {

        table.setItems(ProxyEventBus.getRequestHistory());

    }
}