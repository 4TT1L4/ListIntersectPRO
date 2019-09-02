package ch.bujaki.listintersectpro.main;

import ch.bujaki.listintersectpro.controller.Controller;
import ch.bujaki.listintersectpro.controller.ReactiveUIAdapter;
import ch.bujaki.listintersectpro.intersect.IntersectionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The Main class of the application; containing the main method (entry point).
 */
public class Main extends Application {

    private Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     *  The entry point for the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Called on application startup.
     */
    @Override
    public void start(Stage primaryStage){
        logger.info("[START] Application is started");

        initStage(primaryStage);

        logger.info("[SHOW] Stage is initialized.");
        primaryStage.show();
    }

    /**
     * Called on closing of the application.
     */
    @Override
    public void stop(){
        logger.info("[EXIT] Application is closed.");
        System.exit(0);
    }

    /**
     * Initializes the stage before the UI is shown.
     */
    private void initStage(Stage primaryStage)
    {
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        primaryStage.getIcons().add(icon);

        FXMLLoader loader = new FXMLLoader( getClass().getResource( "application.fxml" ) );
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[ERROR] FATAL ERROR: Failed to load application.fxml. Exiting", e);
            System.exit(0);
        }

        Controller controller = loader.getController();
        ReactiveUIAdapter adapter = controller.getUIAdapter();
        IntersectionManager intersect = new IntersectionManager(adapter);
        intersect.initialize();

        primaryStage.setTitle("ListIntersectPRO");
        primaryStage.setScene(new Scene(root, 450, 275));
        primaryStage.setResizable(false);
    }
}
