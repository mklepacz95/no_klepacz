package pl.mklepacz;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Node> nodeList;
    private List<Edge> edgeList;

    public Graph() {
        this.nodeList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
    }

    public void addNodeToGraph(Node n) {
        nodeList.add(n);
    }

    public void addEdgeToGraph(Edge e) {
        edgeList.add(e);
    }

    public Integer getNumberOfEdge() {
        return edgeList.size();
    }

    public Integer getNumberOfNodes() {
        return nodeList.size();
    }

    public Integer getCyclomatic() {
        return edgeList.size() - nodeList.size() + 2;
    }


    public void addStartAndStopNode(Node startNode, Node stopNode) {

        Edge startNodeToFirstNode = new Edge(startNode, nodeList.get(0));
        edgeList.add(startNodeToFirstNode);

        Edge stopNodeToLastNode = new Edge(stopNode, nodeList.get(nodeList.size() - 1));
        edgeList.add(stopNodeToLastNode);

        nodeList.add(startNode);
        nodeList.add(stopNode);
    }

    public Node getLastNode() {
        return nodeList.get(nodeList.size() - 1);
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    @Override
    public String toString() {
        return "Graph{" +
                ", edgeList=" + edgeList +
                '}';
    }
}
