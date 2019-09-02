package ch.bujaki.listintersectpro.main;

/**
 * Helper class containing public-static helper/util methods.
 */
public class Util {
    /**
     * Converts the passed nano-seconds to seconds.
     *
     * @param nanos
     *         The nano seconds to be converted.
     *
     * @return
     *         The double value representing the same time-span in seconds as the passed value in nano-seconds.
     */
    public static double nanos_to_sec(long nanos)
    {
        return (double)nanos / 1000000000.0;
    }
}
