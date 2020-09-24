package com.regexplus.parser.node.base;

import com.regexplus.automaton.base.EdgeEmpty;
import com.regexplus.automaton.base.EdgeEmptyNoList;
import com.regexplus.automaton.base.StateMinus;
import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeRepeatType;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;

public class NodeNot extends NodeGroup {
    Node nodeLeft;

    public NodeNot(INode group) {
        super(group);

        this.nodeLeft = new NodeRepeat(new NodeAnyLetter(), NodeRepeatType.STAR);
    }

    @Override
    public NodeType getType() {
        return NodeType.NOT;
    }

    @Override
    public void expand(IState[] start, IState[] finish) {
        super.expand(start, finish);

        IState[] a = this.newEmptyState();
        IState[] b = this.newEmptyState();
        IState[] c = this.newEmptyState();
        IState[] d = this.newEmptyState();
        IState[] e = new IState[] { new StateMinus() };

        IEdge edgea = new EdgeEmptyNoList(start[0], a[0]);

        ((Node) this.nodeLeft).expand(a, b);

        new EdgeEmpty(b[0], e[0]); //

        IEdge edgec = new EdgeEmptyNoList(start[0], c[0]);

        ((Node) this.group).expand(c, d);

        new EdgeEmpty(d[0], e[0]); //

        new EdgeEmpty(e[0], finish[0]);

        start[0].getOutputEdges().add(edgea);
        start[0].getOutputEdges().add(edgec);

        a[0].getInputEdges().add(edgea);
        c[0].getInputEdges().add(edgec);

    }
}
