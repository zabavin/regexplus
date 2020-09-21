package com.regexplus.parser.node.base;

import com.regexplus.automaton.base.EdgeLetter;
import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.model.Node;
import com.regexplus.parser.node.common.NodeType;

import java.util.List;

public class NodeLetter extends Node {
    char letter;

    public NodeLetter(char letter) {
        this.letter = letter;
    }

    @Override
    public NodeType getType() {
        return NodeType.LETTER;
    }

    @Override
    public boolean isAtomic() {
        return true;
    }

    @Override
    public List<INode> getChildren() {
        return null;
    }

    public char getLetter() {
        return this.letter;
    }

    @Override
    public void expand(IState[] start, IState[] finish) {
        super.expand(start, finish);
        new EdgeLetter(start[0], finish[0], this.getLetter());
    }
}
