package ch.bujaki.listintersectpro.controller;

import io.reactivex.Observable;

/**
 * Provides a reactive interface for accessing the UI.
 */
public interface UserInterfaceAccessor {

    /**
     *  Enumeration of the available lists.
     */
    enum InputList
    {
        /**
         * "A" represents the "list A"
         */
        A,

        /**
         * "B" stands for the "list B"
         */
        B
    }

    /**
     * @return an {@link javafx.beans.Observable} providing the current setting for the list size of the List-A.
     */
    Observable<Integer> sizeOfListA();

    /**
     * @return an {@link Observable} providing the current setting for the list size of the List-B
     */
    Observable<Integer> sizeOfListB();

    /**
     * @return an {@link javafx.beans.Observable} providing the current setting for the list to be put in the
     * {@link java.util.HashMap}.
     *
     * Please also see the enumeration {@link InputList} for the possible values.
     */
    Observable<InputList> listToPutInHashMap();

    /**
     * @return An {@link Observable}, that is being triggered, when the "Run" button has been clicked and the
     * intersection algorithm should be started with the current settings.
     *
     * The passed Integer value could be used as correlation ID. (It is simply being incremented one by one)
     */
    Observable<Integer> runButtonClicked();

    /**
     * Sets the "running time" value on the UI. This field should contain how long the algorithm was running with the
     * used configuration,
     *
     * @param ns
     *         The running time in Nanos.
     */
    void setDuration(long ns);

    /**
     * Sets the value on the User Interface, that should represent how many items were in the intersection of the two
     * lists.
     *
     * @param count
     *         The size of the Collection, that results from the intersection of the two lists.
     */
    void setIntersectionCount(int count);

    /**
     * Disables the "Run" button in the UI.
     */
    void disableRun();

    /**
     * Enables the "Run" button in the UI.
     */
    void enableRun();
}
