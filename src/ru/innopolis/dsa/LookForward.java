package ru.innopolis.dsa;

import javafx.util.Pair;
import ru.innopolis.dsa.comparator.SegmentComparator;

import java.util.ArrayList;

/**
 * Created by Admin on 13-Oct-16.
 */

public class LookForward<T> {

    public ArrayList<Pair<Integer, Integer>> split(ArrayList<T> segments, int lookBack,
                                                   int lookForward, SegmentComparator<T> comparator) {
        ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
        int startSegment = 0;
        int currentSegment = 0;

        while (currentSegment < segments.size()) {
            int nextSegment = -1;
            for (int i = currentSegment; i >= Math.max(currentSegment - lookBack, 0); i--) {
                for (int j = currentSegment + 1; j <= Math.min(currentSegment + lookForward, segments.size() - 1); j++) {
                    if (comparator.isEqual(segments.get(i), segments.get(j))){
                        nextSegment = j;
                        break;
                    }
                }
                if (nextSegment != -1)
                    break;
            }

            if (nextSegment == -1) {
                result.add(new Pair<>(startSegment, currentSegment));
                currentSegment++;
                startSegment = currentSegment;
            }
            else {
                currentSegment = nextSegment;
            }
        }

        return result;
    }
}
