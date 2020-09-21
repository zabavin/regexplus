package com.regexplus.parser.node.base;

import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;
import com.regexplus.parser.node.model.NodePaired;

public class NodeConcat extends NodePaired {
    public NodeConcat(INode left, INode right) {
        super (left, right);
    }

    @Override
    public NodeType getType() {
        return NodeType.CONCAT;
    }

    @Override
    public void expand(IState[] start, IState[] finish) {
        super.expand(start, finish);

        IState[] a = this.newEmptyState();

        ((Node) this.left).expand(start, a);
        ((Node) this.right).expand(a, finish);
    }
}
