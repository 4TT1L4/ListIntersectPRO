package ch.bujaki.listintersectpro.intersect;

import java.util.Collection;
import java.util.List;

/**
 * Common interface for performing the intersection computing for the passed lists.
 */
public interface IntersectionComputer {

    /**
     * This method computes the intersection of the passed two lists.
     *
     * @param listToPutInHashSet
     *         Contains the list, that should be put in a HashSet during the execution.
     * @param listToIterateOver
     *         Contains the list, that should be iterated over during the execution.
     * @return
     *         The {@link Collection} is returned, that is the result of the intersection of the two passed lists.
     */
    Collection performIntersection(List<Integer> listToPutInHashSet, List<Integer> listToIterateOver);
}
