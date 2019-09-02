package ch.bujaki.listintersectpro.controller;

import ch.bujaki.listintersectpro.main.Util;
import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Adapter class, that provides a reactive interface for interaction with the UI.
 *
 * For details see the description of the {@link UserInterfaceAccessor} interface.
 */
public class ReactiveUIAdapter implements UserInterfaceAccessor {

    private Observable<Integer> sizeOfListA;
    private Observable<Integer> sizeOfListB;
    private Observable<InputList> listToPutInHashMap;
    private Observable<Integer> runButtonClicked;

    private Logger logger = LoggerFactory.getLogger(ReactiveUIAdapter.class);

    private Controller controller;
    private int correlationIdentifier = 0;

    private final DecimalFormat decimalFormatForRunningTime = new DecimalFormat("#,###.################s", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    /**
     * Constuctor.
     *
     * @param controller
     *         The {@link Controller} instance to be used.
     */
    ReactiveUIAdapter(Controller controller)
    {
        logger.info("Created.");

        this.controller = controller;
    }

    /**
     * Initializes the instance.
     */
    void initialize()
    {
        // We have to make sure, that we create our subscriptions only, when the Observables of the Controller have been
        // all initialized.
        controller
                .isInitialized()
                .doOnEach( isInitialized -> logger.info(isInitialized.toString()))
                .filter(Boolean::valueOf)
                .subscribe(
                        // Create subscriptions only after initialization.
                        isInitialized -> createSubscriptions()
                );
    }

    /**
     * Creates reactive bindings for the relevant JavaFX Properties of the UI.
     */
    private void createSubscriptions() {
        logger.info("Creating subscriptions.");

        sizeOfListA = JavaFxObservable
                .valuesOf(controller.getSizeOfListA().textProperty())
                .filter(value -> value.matches("\\d*"))
                .map(Integer::parseInt);

        sizeOfListB = JavaFxObservable
                .valuesOf(controller.getSizeOfListB().textProperty())
                .filter(value -> value.matches("\\d*"))
                .map(Integer::parseInt);

        listToPutInHashMap =  JavaFxObservable.valuesOf(controller.getPutListAToAHashSet().selectedProperty())
                .map( putListAToHashSet -> putListAToHashSet ? UserInterfaceAccessor.InputList.A : UserInterfaceAccessor.InputList.B );

        runButtonClicked = JavaFxObservable
                .actionEventsOf(controller.getRun())
                .map(actionEvent -> correlationIdentifier++);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<Integer> sizeOfListA() {
        return sizeOfListA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<Integer> sizeOfListB() {
        return sizeOfListB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<InputList> listToPutInHashMap() {
        return listToPutInHashMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<Integer> runButtonClicked() {
        return runButtonClicked;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDuration(long ns) {
        controller.runningTimeProp.setValue(decimalFormatForRunningTime.format(Util.nanos_to_sec(ns)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIntersectionCount(int count) {
        Platform.runLater(() -> controller.resultCountProp.setValue(count + "" ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableRun() {
        Platform.runLater(() ->  controller.getRun().setDisable(true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enableRun() {
        Platform.runLater(() ->  controller.getRun().setDisable(false));
    }
}
