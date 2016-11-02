package ru.innopolis.dsa;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 13-Oct-16.
 */
public class BridgeFinding {

    private int[] discovery;
    private int[] low;
    LinkedList<LinkedList<Edge>> graph;
    List<Edge> bridges;

    public ArrayList<Pair<Integer, Integer>> split(ArrayList<Integer> segments, int T) {
        makeGraph(segments, T);

        findBridges(segments);
        Collections.sort(bridges);

        ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
        int segmentStartTime = 0;
        for (Edge bridge : bridges){
            result.add(new Pair<>(segmentStartTime, bridge.time - 1));
            segmentStartTime = bridge.time;
        }
        result.add(new Pair<>(segmentStartTime, segments.size() - 1));

        return result;
    }

    public ArrayList<Pair<Integer, Integer>> refinementSplit(ArrayList<Integer> segments, int T) {
        BridgeFinding bf = new BridgeFinding();

        ArrayList<Pair<Integer, Integer>> lsu = bf.split(segments, T);

        return this.refinement(segments, lsu);
    }

    private ArrayList<Pair<Integer, Integer>> refinement(ArrayList<Integer> segments, ArrayList<Pair<Integer, Integer>> units){
        ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
        result.add(units.get(0));

        int m = 0;
        for (int i = 1; i < units.size(); i++) {
            ArrayList<Integer> united = new ArrayList<>();
            Pair<Integer, Integer> u1 = result.get(m);
            Pair<Integer, Integer> u2 = units.get(i);

            for (int j = u1.getKey(); j <= u1.getValue(); j++)
                united.add(segments.get(j));
            for (int j = u2.getKey(); j <= u2.getValue(); j++)
                united.add(segments.get(j));

            ArrayList<Pair<Integer, Integer>> unitedSplit = split(united, united.size());
            boolean flag = false;
            for (Pair<Integer, Integer> pair : unitedSplit){
                if (pair.getKey() < u2.getKey() && pair.getValue() >= u2.getValue()){
                    result.set(m, new Pair<>(u1.getKey(), u2.getValue()));
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                result.add(units.get(i));
                m++;
            }
        }
        return result;
    }

    private void findBridges(ArrayList<Integer> segments) {
        discovery = new int[segments.size()];
        for (int i = 0; i < segments.size(); i++){
            discovery[i] = -1;
        }
        low = new int[segments.size()];
        bridges = new ArrayList<>();
        dfs(new Edge(-1, 0, -1), 0);
    }

    private void makeGraph(ArrayList<Integer> segments, int T) {
        ArrayList<Integer> vertex = new ArrayList<>();
        for (int i = 0; i < segments.size(); i++)
            vertex.add(-1);

        int maxVertex = -1;
        for (int i = 0; i < segments.size(); i++){
            if (vertex.get(i) == -1){
                maxVertex++;
                vertex.set(i, maxVertex);
            }
            else
                continue;

            for (int j = i + 1; j <= Math.min(i + T, segments.size() - 1); j++){
                if (segments.get(i).equals(segments.get(j))){
                    vertex.set(j, vertex.get(i));
                }
            }
        }

        graph = new LinkedList<>();
        for (int i = 0; i <= maxVertex; i++)
            graph.add(new LinkedList<>());

        for (int i = 1; i < segments.size(); i++){
            int prev = vertex.get(i-1);
            int cur = vertex.get(i);
            graph.get(prev).add(new Edge(prev, cur, i));
        }
    }


    private int dfs(Edge e, int t) {
        int v = e.to;
        if (discovery[v] != -1){
            return low[v];
        }

        discovery[v] = t;
        low[v] = t;
        LinkedList<Edge> edges = graph.get(v);
        for (Edge edge : edges){
            int lowu = dfs(edge, t+1);
            if (lowu < low[v])
                low[v] = lowu;
        }

        if (low[v] == discovery[v] && e.time != -1)
            bridges.add(e);
        return low[v];
    }
}

class Edge implements Comparable{
    public int to;
    public int time;
    public int from;

    public Edge(int from, int to, int time){
        this.to = to;
        this.from = from;
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(time, ((Edge)o).time);
    }
}
