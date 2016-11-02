package ru.innopolis.dsa.comparator;

/**
 * Created by pavel on 02.11.16.
 */
public interface SegmentComparator<T> {

    boolean isEqual(T segment1, T segment2);
}
