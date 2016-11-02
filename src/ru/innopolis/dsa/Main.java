package ru.innopolis.dsa;

import javafx.util.Pair;
import ru.innopolis.dsa.comparator.IntegerSegmentComparator;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int segmentCount = sc.nextInt();
        int lookBack = sc.nextInt();
        int lookForward = sc.nextInt();
        ArrayList<Integer> segments = new ArrayList<>(segmentCount);
        for (int i = 0; i < segmentCount; i++){
            segments.add(sc.nextInt());
        }

        LookForward<Integer> divider = new LookForward<>();
        ArrayList<Pair<Integer, Integer>> lsu = divider.split(segments, lookBack, lookForward, new IntegerSegmentComparator());

/*        int segmentCount = sc.nextInt();
        int t = sc.nextInt();

        BridgeFinding bf = new BridgeFinding();
        ArrayList<Pair<Integer, Integer>> lsu = bf.split(segments, t);*/

        System.out.println("Number of lsu: " + lsu.size());
        for (int i = 0; i < lsu.size(); i++) {
            System.out.println("[" + (lsu.get(i).getKey() + 1) + "; " + (lsu.get(i).getValue() + 1) + "]");
        }

//       testBridges();
    }

     private static void testBridges(){
        Scanner sc = new Scanner(System.in);
        int segmentCount = sc.nextInt();
        ArrayList<Integer> segments = new ArrayList<>(segmentCount);
        for (int i = 0; i < segmentCount; i++){
            segments.add(sc.nextInt());
        }
        BridgeFinding bf = new BridgeFinding();

        for (int t = 1; t <= segments.size(); t++){
            ArrayList<Pair<Integer, Integer>> lsu1 = bf.split(segments, t);
            ArrayList<Pair<Integer, Integer>> lsu = bf.refinementSplit(segments, t);
            System.out.println("T = " + t + "; Number of logical units = " + lsu.size() + " without: " + lsu1.size());
        }
    }


}
