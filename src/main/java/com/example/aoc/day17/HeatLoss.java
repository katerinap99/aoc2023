package com.example.aoc.day17;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HeatLoss {

    private Set<Node> nodes = new HashSet<>();

    private Map<Position, Node> nodesMap = new HashMap<>();

    public record Position(int x, int y){
        @Override
        public int hashCode() {
            return x*10000 + y;
        }
    }

    public class Node{
        private Position position;
        private int weight;
        private int distance = Integer.MAX_VALUE;
        private Set<Node> adjacentNodes = new HashSet<>();
        private List<Node> shortestPath = new LinkedList<>();

        public Node(int x, int y, int weight) {
            this.position = new Position(x, y);
            this.weight = weight;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getWeight() {
            return weight;
        }

        public Set<Node> getAdjacentNodes() {
            return adjacentNodes;
        }

        public int getDistance() {
            return distance;
        }

        public Position getPosition() {
            return position;
        }

        public List<Node> getShortestPath() {
            return shortestPath;
        }

        public void setAdjacentNodes(Set<Node> adjacentNodes) {
            this.adjacentNodes = adjacentNodes;
        }

        public void setShortestPath(List<Node> shortestPath) {
            this.shortestPath = shortestPath;
        }

        @Override
        public int hashCode() {
            return position.hashCode();
        }
    }


    public static int[][] parse(List<String> input) {
        int[][] map = new int[input.size()][];
        for (int i=0; i< input.size(); i++) {
            map[i] = input.get(i).chars()
                    .mapToObj(c -> (char) c)
                    .mapToInt(Character::getNumericValue)
                    .toArray();
        }
        return map;
    }

    public void createGraph(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Node node = new Node(i, j, map[i][j]);
                nodes.add(node);
                nodesMap.put(node.position, node);
            }
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Node currentNode = nodesMap.get(new Position(i, j));
                Optional.ofNullable(nodesMap.get(new Position(i-1, j))).ifPresent(currentNode.adjacentNodes::add);
                Optional.ofNullable(nodesMap.get(new Position(i+1, j))).ifPresent(currentNode.adjacentNodes::add);
                Optional.ofNullable(nodesMap.get(new Position(i, j-1))).ifPresent(currentNode.adjacentNodes::add);
                Optional.ofNullable(nodesMap.get(new Position(i, j+1))).ifPresent(currentNode.adjacentNodes::add);
            }
        }
    }

    public void calculateShortestPathFromSource(Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Node node: currentNode.getAdjacentNodes()) {
                if (!settledNodes.contains(node)) {
                    calculateMinimumDistance(node, node.getWeight(), currentNode);
                    unsettledNodes.add(node);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private void calculateMinimumDistance(Node evaluationNode,
                                                 Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }



    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day17/input.txt"));
        int[][] map = parse(input);
        HeatLoss heatLoss = new HeatLoss();
        heatLoss.createGraph(map);
        heatLoss.calculateShortestPathFromSource(heatLoss.nodesMap.get(new Position(0, 0)));
        System.out.println();
    }
}
