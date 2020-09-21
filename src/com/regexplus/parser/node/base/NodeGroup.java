package com.regexplus.parser.node.base;

import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.model.Node;
import com.regexplus.parser.node.common.NodeType;

import java.util.Arrays;
import java.util.List;

public class NodeGroup extends Node {
    INode group;

    public NodeGroup(INode group) {
        this.group = group;
    }

    public INode getGroup() {
        return this.group;
    }

    @Override
    public NodeType getType() {
        return NodeType.GROUP;
    }

    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public List<INode> getChildren() {
        return Arrays.asList(this.group);
    }

    @Override
    public void expand(IState[] start, IState[] finish) {
        super.expand(start, finish);
        ((Node) this.getGroup()).expand(start, finish);
    }
}
