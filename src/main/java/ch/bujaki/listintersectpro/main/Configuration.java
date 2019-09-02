package ch.bujaki.listintersectpro.main;

/**
 * Contains the configuration settings for the application.
 */
// TODO: Add properties file or some other more flexible way of changing the configuration settings.
public class Configuration {

    /**
     * The limit for the random numbers to be generated for the lists with random numbers.
     *
     * The generated random values are going to be between this values and -1 * this value.
     *
     * For details see the implementation in the {@link ch.bujaki.listintersectpro.intersect.IntersectionManager} class.
     */
    public static int RANDOM_LIMIT_FOR_RANDOM_NUMBER_GENERATION = 1000000;

    /**
     * In order to avoid too frequent calculations, there is a debounce operator applied on the observable of the
     * button clicked observable in the {@link ch.bujaki.listintersectpro.intersect.IntersectionManager}.
     *
     * This value can be used to change the used debounce value. The passed value is interpreted as milliseconds.
     */
    public static int DEBOUNCE_MS_TIME_SPAN_FOR_IGNORING_RUN_BUTTON_CLICKS = 100;

    /**
     * In order to limit the maximal size of the lists, that can be generated the maximal size of the
     */
    public static int MAXIMAL_LENGTH_OF_THE_LIST_SIZE_INPUT_STRING = 5;

}
