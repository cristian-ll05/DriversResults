import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DriversResultsApp extends Application {

    private ObservableList<DriverStanding> driverStandings = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        // Crear un BorderPane
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Crear componentes
        Label label = new Label("Driver Results");
        root.setTop(label);

        TableView<DriverStanding> tableView = new TableView<>();
        tableView.setItems(driverStandings);

        TableColumn<DriverStanding, String> driversStandingColumn = new TableColumn<>("Drivers Standing");
        driversStandingColumn.setCellValueFactory(new PropertyValueFactory<>("driversStanding"));
        tableView.getColumns().add(driversStandingColumn);

        TableColumn<DriverStanding, Integer> raceIdColumn = new TableColumn<>("Race ID");
        raceIdColumn.setCellValueFactory(new PropertyValueFactory<>("raceId"));
        tableView.getColumns().add(raceIdColumn);

        TableColumn<DriverStanding, Integer> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        tableView.getColumns().add(pointsColumn);

        TableColumn<DriverStanding, Integer> positionColumn = new TableColumn<>("Position");
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        tableView.getColumns().add(positionColumn);

        TableColumn<DriverStanding, String> positionTextColumn = new TableColumn<>("Position Text");
        positionTextColumn.setCellValueFactory(new PropertyValueFactory<>("positionText"));
        tableView.getColumns().add(positionTextColumn);

        TableColumn<DriverStanding, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        tableView.getColumns().add(winsColumn);

        root.setCenter(tableView);

        Button buttonFilter = new Button("Conductor con más puntos");
        buttonFilter.setOnAction(e -> {
            DriverStanding driverWithMostPoints = getDriverWithMostPoints(driverStandings);
            if (driverWithMostPoints!= null) {
                ObservableList<DriverStanding> filteredList = FXCollections.observableArrayList();
                filteredList.add(driverWithMostPoints);
                tableView.setItems(filteredList);
            }
        });
        buttonFilter.setStyle("-fx-background-color: #2F4F7F; -fx-text-fill: #ffffff;");

        Button buttonReset = new Button("Todos los conductores");
        buttonReset.setOnAction(e -> {
            tableView.setItems(driverStandings);
        });
        buttonReset.setStyle("-fx-background-color: #2F4F7F; -fx-text-fill: #ffffff;");
        
        root.setBottom(new BorderPane(buttonFilter, null, null, buttonReset, null));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        connectToDatabase();
    }

    private DriverStanding getDriverWithMostPoints(ObservableList<DriverStanding> driverStandings) {
        DriverStanding driverWithMostPoints = null;
        int maxPoints = 0;
        for (DriverStanding driverStanding : driverStandings) {
            if (driverStanding.getPoints() > maxPoints) {
                maxPoints = driverStanding.getPoints();
                driverWithMostPoints = driverStanding;
            }
        }
        return driverWithMostPoints;
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/formula1";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM driverstandings")) {

            while (rs.next()) {
                DriverStanding driverStanding = new DriverStanding(
                        rs.getInt("driverStandingId"),
                        rs.getInt("raceId"),
                        rs.getInt("points"),
                        rs.getInt("position"),
                        rs.getString("positionText"),
                        rs.getInt("wins")
                );
                driverStandings.add(driverStanding);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}