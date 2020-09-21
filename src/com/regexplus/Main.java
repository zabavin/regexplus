package com.regexplus;

import com.regexplus.automaton.base.EdgeLetter;
import com.regexplus.automaton.common.EdgeType;
import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.model.Edge;
import com.regexplus.automaton.model.State;
import com.regexplus.parser.Parser;
import com.regexplus.parser.node.base.NodeRepeat;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.model.Node;
import com.regexplus.parser.node.base.NodeLetter;
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

    public static void main(String[] args) {
        testOne();
        testTwo();
    }
}
