package dam.jlr.fichajes0;

import dam.jlr.fichajes0.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class HelloApplication extends Application {
    private final String user="root";
    private final String password="root";
    Scene scene;
     Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        updateDialog();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        HelloController controller = fxmlLoader.getController();
        controller.cargarTabla2();
        stage.show();







    }

    public static void main(String[] args) {

        launch();



        //database connection is closed when application is closed


        HibernateUtil.getSessionFactory().close();
    }
    public static void updateDialog() {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Update Dialog");
        dialog.setHeaderText("Please Update your details");
        dialog.initModality(Modality.WINDOW_MODAL);
        //dialog.initOwner(stage);
        System.out.println(dialog.getOwner());

        ButtonType updateButtonType = new ButtonType("Try", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        //panel
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        //textfield
        TextField db = new TextField();
        db.setPromptText("Database");
        grid.add(new Label("Database name:"), 0, 0);
        grid.add(db, 1, 0);
        dialog.getDialogPane().setContent(grid);



        dialog.showAndWait();
        String dbName = db.getText();
        HibernateUtil.setDatabaseName(dbName);

        //if update button is pressed

            //get textfield



    }
}