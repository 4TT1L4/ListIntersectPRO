package ch.bujaki.listintersectpro.controller;

import ch.bujaki.listintersectpro.main.Configuration;
import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Implements the controller for the JavaFX user interface defined in the application.fxml.
 */
public class Controller implements Initializable {

    private Logger logger = LoggerFactory.getLogger(Controller.class);

    Property<Boolean> isInitialized = new SimpleBooleanProperty(false);
    Property<String> resultCountProp = new SimpleStringProperty("");
    Property<String> runningTimeProp = new SimpleStringProperty("");

    @FXML
    TextField sizeOfListA;

    @FXML
    TextField sizeOfListB;

    @FXML
    RadioButton putListAToAHashSet;

    @FXML
    RadioButton putListBToBHashSet;

    @FXML
    TextField sizeOfResultSet;

    @FXML
    TextField runningTime;

    @FXML
    Button run;

    ReactiveUIAdapter reactUIAdapter = new ReactiveUIAdapter(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Starting initialization.");

        // Set custom font color for the output TextFields.
        sizeOfResultSet.setStyle("-fx-text-inner-color: navy;");
        runningTime.setStyle("-fx-text-inner-color: navy;");

        // Restrict "size-of-List" text fields to limited unsigned integers.
        makeTextFieldAcceptIntegersOnly(sizeOfListA);
        makeTextFieldAcceptIntegersOnly(sizeOfListB);

        runningTime.textProperty().bindBidirectional(runningTimeProp);
        sizeOfResultSet.textProperty().bindBidirectional(resultCountProp);

        reactUIAdapter.initialize();

        isInitialized.setValue(true);

        logger.info("[OK] Initialized.");
    }

    /**
     * Makes sure that the passed textField can contain only unsigned integers with a limited maximum value.
     *
     * @param textField
     */
    private void makeTextFieldAcceptIntegersOnly(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                try {
                    Integer.parseUnsignedInt(newValue);
                } catch (NumberFormatException  e) {
                    textField.setText(oldValue);
                }

                if (textField.getText().length() > Configuration.MAXIMAL_LENGTH_OF_THE_LIST_SIZE_INPUT_STRING) {
                    String s = textField.getText().substring(0, Configuration.MAXIMAL_LENGTH_OF_THE_LIST_SIZE_INPUT_STRING);
                    textField.setText(s);
                }
            }
        });
    }

    /**
     * @return a {@link Observable}, that tells, if the instance has already been initialized or not.
     */
    public Observable<Boolean> isInitialized() {
        return JavaFxObservable
                .valuesOf(isInitialized);
    }

    TextField getSizeOfListA() {
        return sizeOfListA;
    }

    TextField getSizeOfListB() {
        return sizeOfListB;
    }

    RadioButton getPutListAToAHashSet() {
        return putListAToAHashSet;
    }

    RadioButton getPutListBToBHashSet() {
        return putListBToBHashSet;
    }

    TextField getRuningTime() {
        return runningTime;
    }

    TextField getSizeOfResultSet() {
        return sizeOfResultSet;
    }

    Button getRun() {
        return run;
    }

    /**
     * @return the {@link ReactiveUIAdapter} instance.
     */
    public ReactiveUIAdapter getUIAdapter() {
        return reactUIAdapter;
    }



}
