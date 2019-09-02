package ch.bujaki.listintersectpro.intersect;

import ch.bujaki.listintersectpro.controller.Controller;
import ch.bujaki.listintersectpro.controller.ReactiveUIAdapter;
import ch.bujaki.listintersectpro.controller.UserInterfaceAccessor;
import ch.bujaki.listintersectpro.intersect.impl.HashSetIntersection;
import ch.bujaki.listintersectpro.main.Configuration;
import ch.bujaki.listintersectpro.main.Util;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Manages the execution of the intersection calculation.
 *
 * This class also subscribes on the relevant events and gathers all the data that is necessary for the execution of
 * the expected intersection calculation.
 *
 * As soon as the calculation is over; the update of the UI is also requested via the {@link ReactiveUIAdapter}.
 */
public class IntersectionManager {

    private final ReactiveUIAdapter adapter;
    private final IntersectionComputer intersectionComputer = new HashSetIntersection();
    private final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final Scheduler scheduler = Schedulers.from(Executors.newFixedThreadPool(10));
    private final Random random = new Random();

    /**
     * Construction
     *
     * @param adapter
     *         The {@link ReactiveUIAdapter}, that is going to be used to make the subscriptions and keep the UI
     *         up-to-date. (Some fields must be updated as soon as the calculation is done)
     */
    public IntersectionManager(ReactiveUIAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Initializes the instance. (Creates the subscription for the button-click events of the "Run-Button")
     */
    public void initialize()
    {
        adapter.runButtonClicked()
                // Let's disable the "Run-Button" as long as the computation is running.
                // We don't want to make it possible to trigger several concurrent computations.
                .doOnNext( event -> adapter.disableRun())
                // Debounce the requests; this helps to avoid NPEs and ConcurrentModificationExceptions
                // that happen, when the user triggers a lot of computations in a very fast manner.
                .debounce(Configuration.DEBOUNCE_MS_TIME_SPAN_FOR_IGNORING_RUN_BUTTON_CLICKS, TimeUnit.MILLISECONDS)
                .observeOn(scheduler)
                .doOnNext( id -> logger.info("[START] Run button clicked (" + id + ")"))
                .withLatestFrom(
                        adapter.sizeOfListA(), adapter.sizeOfListB(), adapter.listToPutInHashMap(),
                        Parameters::new
                )
                .map(this::intersectLists)
                .subscribe(this::updateUI);
    }

    /**
     * Executes the intersection of the randomly generated lists. (including measuring the running-time)
     *
     * @param params
     *         The current paramers, as they were set-up, when the computation was triggered.
     *
     * @return the {@link ComputationResult} for the current execution of the algorithm using the actual
     *         {@link Parameters} as they are set-up in the UI.
     */
    public ComputationResult intersectLists(Parameters params)
    {
        logger.info("intersectLists params:" + params);
        logger.info("Generate lists...");
        List<Integer> listA = generateListWithRandomElements(params.sizeOfListA);
        List<Integer> listB = generateListWithRandomElements(params.sizeOfListB);

        logger.info("Size of list A: " + listA.size());
        logger.info("Size of list B: " + listB.size());

        List<Integer> listToPutInHashSet;
        List<Integer> listToIterateOver;

        if (UserInterfaceAccessor.InputList.A == params.listToPutInHashMap)
        {
            // Put List-A to the HashMap
            logger.info("Putting listA to HashSet.");
            listToPutInHashSet = listA;
            listToIterateOver = listB;
        }
        else
        {
            // Put List-B to the HashMap
            logger.info("Putting listB to HashSet.");
            listToPutInHashSet = listB;
            listToIterateOver = listA;
        }

        logger.debug("listToPutInHashSet:" + listToPutInHashSet);
        logger.debug("listToInterateOver:" + listToIterateOver);
        logger.debug("intersectLists:" + params);

        logger.info("Start computation...");
        long startTime = System.nanoTime();

        Collection result = intersectionComputer.performIntersection(listToPutInHashSet, listToIterateOver);

        long endTime = System.nanoTime();

        logger.info("Finished computation!");

        return new ComputationResult(result, endTime - startTime, params.eventId);
    }

    /**
     * Generates a list with random numbers, having the size, that is passed as parameter.
     *
     * @param sizeToBeGenerated
     *         The size of the list to be generated.
     *
     * @return
     *         A List, that is containing exactly as many random integer values, as the passed parameter.
     */
    private List<Integer> generateListWithRandomElements(int sizeToBeGenerated) {
        List<Integer> list = new ArrayList(sizeToBeGenerated);

        for(int i = 0; i < sizeToBeGenerated; i++)
        {
            // We do not use all the possible integer values to make intersections bigger.
            // If we use fewer different random values, then it is more likely, that the two lists contain more
            // common elements, that are going to appear in the intersection.
            list.add(random.nextInt() % Configuration.RANDOM_LIMIT_FOR_RANDOM_NUMBER_GENERATION);
        }

        return list;
    }

    /**
     * Updates the user interface according to the {@link ComputationResult} of the current execution of the
     * intersection algorithm.
     *
     * The running time and the size of the intersection must be updated according to the current results.
     *
     * @param result
     *         The {@link ComputationResult} for the current execution of the intersection algorithm.
     */
    private void updateUI(ComputationResult result) {
        logger.info("Count: " + result.result.size());
        logger.info("Duration: " + Util.nanos_to_sec(result.duration) + "s");

        // Update the UI elements via the UI adapter instance.
        adapter.setDuration(result.duration);
        adapter.setIntersectionCount(result.result.size());

        logger.info("[DONE] Run button click (" + result.eventID +")");

        // Since the whole process is over, the "Run-Button" must be enabled again, so the user could trigger the
        // computation again.
        adapter.enableRun();
    }
}
