package ch.bujaki.listintersectpro.intersect.impl;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the {@link HashSetIntersection} implementation of the {@link ch.bujaki.listintersectpro.intersect.IntersectionComputer}.
 */
public class HashSetIntersectionTest {

    // Object under test.
    HashSetIntersection intersectionComputer;

    @Before
    public void before()
    {
        intersectionComputer = new HashSetIntersection();
    }

    @Test
    public void test_intersectionOfEmptyLists_worksAsExpected()
    {
        Collection result = intersectionComputer.performIntersection(Collections.emptyList(), Collections.emptyList());

        assertTrue( result.isEmpty() );
        assertTrue( result.size() == 0 );
    }
    @Test
    public void test_intersectionOfEmptyAndNonEmptyLists_worksAsExpected()
    {
        Collection result = intersectionComputer.performIntersection(Collections.emptyList(), Lists.newArrayList(1, 42, 314));

        assertTrue( result.isEmpty() );
        assertTrue( result.size() == 0 );
    }

    @Test
    public void test_intersectionOfDuplicateLists_singleElementIsFound()
    {
        final Integer singleElement = 123;

        List<Integer> list_A = Lists.newArrayList(singleElement);
        List<Integer> list_B = Lists.newArrayList(singleElement);

        Collection result = intersectionComputer.performIntersection(list_A, list_B);

        assertFalse( result.isEmpty() );
        assertTrue( result.size() == 1 );
        assertTrue( result.contains(singleElement) );
    }

    @Test
    public void test_intersectionOfDuplicateLists_severalElementAreFound()
    {
        List<Integer> list_A = Lists.newArrayList(1, 2, 3);
        List<Integer> list_B = Lists.newArrayList(1, 2 ,3);

        Collection result = intersectionComputer.performIntersection(list_A, list_B);

        assertFalse( result.isEmpty() );
        assertTrue( result.size() == 3 );
        assertTrue( result.contains(1) );
        assertTrue( result.contains(2) );
        assertTrue( result.contains(3) );
    }

    @Test
    public void test_intersectionOfDuplicateLists_severalElementAreFoundAlsoInTheCaseIfTheyAreMixed()
    {
        // 1, 2 and 3 are the common elements, but now they are mixed:
        List<Integer> list_A = Lists.newArrayList(7, 8, 9, 1, 10, 2, 3, 10);
        List<Integer> list_B = Lists.newArrayList(1, 1258, 2 , 39 ,3, 12);

        Collection result = intersectionComputer.performIntersection(list_A, list_B);

        assertFalse( result.isEmpty() );
        assertTrue( result.size() == 3 );
        assertTrue( result.contains(1) );
        assertTrue( result.contains(2) );
        assertTrue( result.contains(3) );
    }

    @Test
    public void test_intersectionOfDuplicateLists_canBeRepeatedAndLeadsToTheSameResult()
    {
        // 1, 2 and 3 are the common elements, but now they are mixed:
        List<Integer> list_A = Lists.newArrayList(7, 8, 9, 1, 10, 2, 3, 10);
        List<Integer> list_B = Lists.newArrayList(1, 1258, 2 , 39 ,3, 12);

        Collection result1 = intersectionComputer.performIntersection(list_A, list_B);
        Collection result2 = intersectionComputer.performIntersection(list_A, list_B);
        Collection result3 = intersectionComputer.performIntersection(list_A, list_B);

        assertEquals( result1.size(), result2.size());
        assertEquals( result1.size(), result3.size());

        assertArrayEquals(result1.toArray(), result2.toArray());
        assertArrayEquals(result1.toArray(), result3.toArray());
    }
}
