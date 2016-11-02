package ru.innopolis.dsa.comparator;

/**
 * Created by pavel on 02.11.16.
 */
public class IntegerSegmentComparator implements SegmentComparator<Integer>{
    @Override
    public boolean isEqual(Integer segment1, Integer segment2) {
        return segment1.equals(segment2);
    }
}
