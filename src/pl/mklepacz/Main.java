package pl.mklepacz;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Wybierz program do wyliczenia miary cyklometrynczej. Wybierz liczbę z poniższej listy ");
        System.out.println("1 -> Program1.c \n" +
                "2 -> Porgram2.c\n" +
                "3 -> Program3.c\n" +
                "4 -> Program4.c (program wlasny)"
        );

        Scanner scan = new Scanner(System.in);
        String programNumber = scan.nextLine();

        File inputFile = new File("programs/Program" + programNumber + ".c");
        Boolean proccess = false;
        List<String> nazwyFunkcji = new ArrayList<>();


        Graph g = new Graph();
        try {
            InputStream inputStream = new FileInputStream(inputFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            Node startNode = null;
            Boolean doForWhile = false;
            Boolean switchCase = false;
            Node switchStartNode = null;
            Node switchStopNode = null;
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                if (line.contains("main")) {
                    proccess = true;
                    startNode = new Node("startNode");
                }
                if (proccess) {
                    Node ifNodeForElse = null;

                    if (line.contains("if") && !switchCase) {
                        String nameNode = line.substring(line.indexOf("if") + 1, line.lastIndexOf(")"));
                        //ALL NODE FROM IF
                        Node ifNode = new Node("ifNode: " + nameNode);
                        if(g.getNumberOfNodes() != 0) {
                            Edge lastNodetoIfNode = new Edge(g.getLastNode(),ifNode);
                            g.addEdgeToGraph(lastNodetoIfNode);
                        }
                        ifNodeForElse = ifNode;

                        g.addNodeToGraph(ifNode);

                        Node instructionIfNode = new Node("Instruction in ifNode: " + nameNode);
                        g.addNodeToGraph(instructionIfNode);

                        Node afterIfNode = new Node("Afrter ifNode: " + nameNode);
                        g.addNodeToGraph(afterIfNode);

                        //ADD ALL EDGE IN IF
                        Edge ifNodeToInstructionNode = new Edge(ifNode, instructionIfNode);
                        g.addEdgeToGraph(ifNodeToInstructionNode);

                        Edge ifNodeToAfterIfNode = new Edge(ifNode, afterIfNode);
                        g.addEdgeToGraph(ifNodeToAfterIfNode);

                        Edge instructionNodeToAfterIfNode = new Edge(instructionIfNode, afterIfNode);
                        g.addEdgeToGraph(instructionNodeToAfterIfNode);

                    }
                    if (line.contains("else") && !switchCase) {
                        Node elseNode = new Node("elseAfterIfNode");
                        g.addNodeToGraph(elseNode);

                        //ADD EDGE FROM ELSE
                        Edge ifNodeToElseNode = new Edge(ifNodeForElse, elseNode);
                        g.addEdgeToGraph(ifNodeToElseNode);
                    }
                    if (line.contains("for") && !switchCase) {
                        String[] nodeName = line.substring(line.indexOf("for") + 1, line.lastIndexOf(")")).split(";");

                        //ADD ALL NODE FROM FOR
                        Node initForNode = new Node("forNode: " + nodeName[0]);
                        if(g.getNumberOfNodes() != 0) {
                            Edge lastNodetoIfNode = new Edge(g.getLastNode(),initForNode);
                            g.addEdgeToGraph(lastNodetoIfNode);
                        }
                        g.addNodeToGraph(initForNode);

                        Node conditionForNode = new Node("conditionForNode: " + nodeName[1]);
                        g.addNodeToGraph(conditionForNode);

                        Node incremetationAndInstructionNode = new Node("incremetationAndInstructionNode: " + nodeName[2]);
                        g.addNodeToGraph(incremetationAndInstructionNode);

                        Node endForNode = new Node("endForNode: " + nodeName[0]);
                        g.addNodeToGraph(endForNode);

                        //ADD EDGE FROM FOR
                        Edge initForNodeToCondition = new Edge(initForNode, conditionForNode);
                        g.addEdgeToGraph(initForNodeToCondition);

                        Edge condtionNodeToIncremetationAndInstructionNode = new Edge(conditionForNode, incremetationAndInstructionNode);
                        g.addEdgeToGraph(condtionNodeToIncremetationAndInstructionNode);

                        Edge incremetationAndInstructionNodeToConditionNode = new Edge(incremetationAndInstructionNode, conditionForNode);
                        g.addEdgeToGraph(incremetationAndInstructionNodeToConditionNode);

                        Edge conditionNodeToEndForNode = new Edge(conditionForNode, endForNode);
                        g.addEdgeToGraph(conditionNodeToEndForNode);

                    }
                    if (line.contains("while") && !doForWhile) {
                        String nodeName = line.substring(line.indexOf("("), line.lastIndexOf(")"));

                        //ALL NODE FROM WHILE
                        Node whileConditionNode = new Node("whileConditionNode" + nodeName);
                        if(g.getNumberOfNodes() != 0) {
                            Edge lastNodetoIfNode = new Edge(g.getLastNode(),whileConditionNode);
                            g.addEdgeToGraph(lastNodetoIfNode);
                        }
                        g.addNodeToGraph(whileConditionNode);

                        Node whileInstrucitonNode = new Node("whileInstructionNode: " + nodeName);
                        g.addNodeToGraph(whileInstrucitonNode);

                        Node endWhileNode = new Node("endWhileNode: " + nodeName);
                        g.addNodeToGraph(endWhileNode);

                        //ALL EDGE FROM WHILE
                        Edge whileConditionNodeToWhileInstrucionNode = new Edge(whileConditionNode, whileInstrucitonNode);
                        g.addEdgeToGraph(whileConditionNodeToWhileInstrucionNode);

                        Edge whileInstrucionNodeToWhileConditionNode = new Edge(whileInstrucitonNode, whileConditionNode);
                        g.addEdgeToGraph(whileInstrucionNodeToWhileConditionNode);

                        Edge whileCondtitionNodeToEndWhileNode = new Edge(whileConditionNode, endWhileNode);
                        g.addEdgeToGraph(whileCondtitionNodeToEndWhileNode);
                    }
                    if(line.contains("while") && doForWhile) doForWhile = false;
                    if(line.contains("do {") || line.contains("do{") && !switchCase) {
                        doForWhile = true;

                        //ADD NODE FROM do{} while();
                        Node instructionDoWhileNode = new Node("instructionDoWhileNode");
                        if(g.getNumberOfNodes() != 0) {
                            Edge lastNodetoIfNode = new Edge(g.getLastNode(),instructionDoWhileNode);
                            g.addEdgeToGraph(lastNodetoIfNode);
                        }
                        g.addNodeToGraph(instructionDoWhileNode);

                        Node whileConditionDoWhileNode = new Node ("whileConditionDoWhileNode");
                        g.addNodeToGraph(whileConditionDoWhileNode);

                        Node afterWhileDoWhileNode = new Node("afterWhileDoWhileNode");
                        g.addNodeToGraph(afterWhileDoWhileNode);

                        //ADD EDGE FROM do{} while();
                        Edge instrucionDoWhileNodeToWhileConditionDoWhileNode = new Edge(instructionDoWhileNode, whileConditionDoWhileNode);
                        g.addEdgeToGraph(instrucionDoWhileNodeToWhileConditionDoWhileNode);

                        Edge whileConditionDoWhileNodeToInstructionDoWhileNode = new Edge(whileConditionDoWhileNode, instructionDoWhileNode);
                        g.addEdgeToGraph(whileConditionDoWhileNodeToInstructionDoWhileNode);

                        Edge whileConditionDoWhileNodeToAfterWhileDoWhileNode = new Edge(whileConditionDoWhileNode, afterWhileDoWhileNode);
                        g.addEdgeToGraph(whileConditionDoWhileNodeToAfterWhileDoWhileNode);
                    }
                    if(line.contains("switch")) {
                        switchCase = true;
                        switchStartNode = new Node("switchStartNode");
                        switchStopNode = new Node("switchStopNode");

                        //ADD NODE FROM SWITCH
                        g.addNodeToGraph(switchStartNode);
                        g.addNodeToGraph(switchStopNode);
                    }
                    if(line.contains("case") && switchCase) {
                        String nodeName = line.substring(line.indexOf("case "), line.indexOf(":"));

                        Node caseSwitchNode = new Node("caseSwitchNode" + nodeName);
                        g.addNodeToGraph(caseSwitchNode);

                        Edge swichStartNodeToCaseSwitchNode = new Edge(switchStartNode, caseSwitchNode);
                        g.addEdgeToGraph(swichStartNodeToCaseSwitchNode);

                        Edge caseSwitchNodeToSwitchStopNode = new Edge(caseSwitchNode, switchStopNode);
                        g.addEdgeToGraph(caseSwitchNodeToSwitchStopNode);

                    }
                    if(line.contains("default") && switchCase) {
                        switchCase = false;
                    }
                }
                else {
                    if(line.contains("(") && line.contains(")")) {
                        line = line.trim().substring(line.indexOf(" "));
                        String functionName = line.trim().substring(line.indexOf(" "), line.indexOf("("));
                        nazwyFunkcji.add(functionName);
                    }
                }
            }
            Node stopNode = new Node("stopNode");
            g.addStartAndStopNode(startNode, stopNode);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numberOfNodes = g.getNumberOfNodes();
        int numberOfEdges = g.getNumberOfEdge();

        System.out.println(g.toString());
        if(nazwyFunkcji.size() != 0 ) {
            numberOfEdges += 2*nazwyFunkcji.size();
            numberOfNodes += nazwyFunkcji.size();
        }
        System.out.println("Liczba krawedzi: " + numberOfEdges);
        System.out.println("Liczba wierzchołków: " + numberOfNodes);
        System.out.println("v(G) = " + g.getCyclomatic());
    }
}
