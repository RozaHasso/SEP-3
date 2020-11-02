import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Admin dashboard");
        Parent root = FXMLLoader.load(getClass().getResource("view/AdminView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(100);
        primaryStage.setMinHeight(200);
        primaryStage.show();
    }
}