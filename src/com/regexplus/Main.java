package com.regexplus;

import com.regexplus.automaton.common.IState;
import com.regexplus.parser.Parser;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.model.Node;
import com.regexplus.test.Case;
import com.regexplus.test.CaseResult;

import java.io.*;

public class Main {
    static void testOne() {
        String text = "abcd"; //"(Hel+lo*)?|, Regex((Pl|us)!)";

        INode node = Parser.ParseFromString(text);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testOne-1.gv")));
            Case.writeNode(bw, node);
            bw.close();

            bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testOne-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];

            ((Node) node).expand(start, finish);

            Case.writeState(bw, start[0]);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testTwo() {
        new Case("a+bc?d*|efgh", new CaseResult[] {
                new CaseResult("aaab", 4),
                new CaseResult("aabc", 4),
                new CaseResult("abcd", 4),
                new CaseResult("efgh", 4),
                new CaseResult("cad", -1),
                new CaseResult("ab", 2),
                new CaseResult("abcddddd", 8)
        }).test();
    }

    static void testThree() {
        String text = "(a*|aa)&aa"; //"(Hel+lo*)?|, Regex((Pl|us)!)";

        INode node = Parser.ParseFromString(text);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testThree-1.gv")));
            Case.writeNode(bw, node);
            bw.close();

            bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testThree-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];

            ((Node) node).expand(start, finish);

            Case.writeState(bw, start[0]);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testFour() {
        new Case("a*&a+", new CaseResult[] {
                //new CaseResult("aa", 2),
                new CaseResult("a", 1)
        }).test();
    }

    static void testFive() {
        String text = "a*-aa"; //"(Hel+lo*)?|, Regex((Pl|us)!)";

        INode node = Parser.ParseFromString(text);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testFive-1.gv")));
            Case.writeNode(bw, node);
            bw.close();

            bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testFive-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];

            ((Node) node).expand(start, finish);

            Case.writeState(bw, start[0]);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testSix() {
        new Case("a*-aa", new CaseResult[] {
                //new CaseResult("aa", 2),
                new CaseResult("aa", 1),
                new CaseResult("aaa", 3)
        }).test();

        new Case("(Hello|world)-world", new CaseResult[] {
                //new CaseResult("aa", 2),
                new CaseResult("world", -1),
                new CaseResult("Hello", 5)
        }).test();
    }

    static void testSeven() {
        String text = ".*"; //"(Hel+lo*)?|, Regex((Pl|us)!)";

        INode node = Parser.ParseFromString(text);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testSeven-1.gv")));
            Case.writeNode(bw, node);
            bw.close();

            bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testSeven-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];

            ((Node) node).expand(start, finish);

            Case.writeState(bw, start[0]);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testEight() {
        new Case(".*", new CaseResult[] {
                //new CaseResult("aa", 2),
                new CaseResult("aa", 2),
                new CaseResult("aaa", 3)
        }).test();
    }

    static void testNine() {
        String text = "~(a|b)"; //"(Hel+lo*)?|, Regex((Pl|us)!)";

        INode node = Parser.ParseFromString(text);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testNine-1.gv")));
            Case.writeNode(bw, node);
            bw.close();

            bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testNine-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];

            ((Node) node).expand(start, finish);

            Case.writeState(bw, start[0]);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testTen() {
        new Case("~(a|b)|c", new CaseResult[] {
                //new CaseResult("aa", 2),
                new CaseResult("b", 0),
                new CaseResult("a", 0),
                new CaseResult("c", 1)
        }).test();
    }

    static void testEleven() {
        String text = "[ab]*"; //"(Hel+lo*)?|, Regex((Pl|us)!)";

        INode node = Parser.ParseFromString(text);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testEleven-1.gv")));
            Case.writeNode(bw, node);
            bw.close();

            bw = new BufferedWriter(new FileWriter(new File("C:/Debug/testEleven-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];

            ((Node) node).expand(start, finish);

            Case.writeState(bw, start[0]);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testTwelfth() {
        new Case("[ab]*", new CaseResult[] {
                //new CaseResult("aa", 2),
                new CaseResult("b", 1),
                new CaseResult("a", 1),
                new CaseResult("abab", 4),
                new CaseResult("c", 0)
        }).test();
    }

    static void testThirteenth() {
        new Case("~(ab-cd)", new CaseResult[] {
                //new CaseResult("aa", 2),
                new CaseResult("ab", 1),
                new CaseResult("cd", 2),
                new CaseResult("abcd", 4)
        }).test();
    }


    public static void main(String[] args) {
        testOne();
        testTwo();
        testThree();
        testFour();
        testFive();
        testSix();
        testSeven();
        testEight();
        testNine();
        testTen();
        testEleven();
        testTwelfth();
        testThirteenth();
    }
}
