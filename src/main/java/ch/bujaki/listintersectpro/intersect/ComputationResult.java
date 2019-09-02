package ch.bujaki.listintersectpro.intersect;

import java.util.Collection;

/**
 * Simple data container class for the data, that must be returned as the result of a "interception" computation.
 *
 * See also the {@link IntersectionManager} class.
 */
class ComputationResult
{
    /**
     * The Collection that is resulting from the intersection of the two lists.
     */
    public final Collection result;

    /**
     * The duration of the computation in nanos.
     */
    public final long duration;

    /**
     * The correlation ID for the event that led to the computation of the intersection.
     */
    public final int eventID;

    /**
     * Constructor.
     */
    public ComputationResult(Collection result, long duration, int eventID) {
        this.result = result;
        this.duration = duration;
        this.eventID = eventID;
    }
}