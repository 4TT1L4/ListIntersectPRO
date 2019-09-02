package ch.bujaki.listintersectpro.intersect.impl;

import ch.bujaki.listintersectpro.intersect.IntersectionComputer;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Simple implementation of the {@link IntersectionComputer}.
 *
 * This implementation simply builds a {@link HashSet} from one of the {@link List} instances and uses the retainAll
 * method of the {@link HashSet} to get the intersection.
 */
public class HashSetIntersection implements IntersectionComputer {

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection performIntersection(List<Integer> listToPutInHashSet, List<Integer> listToIterateOver) {
            HashSet<Integer> set = new HashSet<Integer>(listToPutInHashSet);
            set.retainAll(listToIterateOver);

            return set;
        }
}
