package com.regexplus.automaton.base;

import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.StateType;
import com.regexplus.automaton.model.State;

import java.util.Arrays;

public class StateMinus extends State {
    int counterVisitIndex;
    boolean[] visited;

    public StateMinus() {
        super ();
        this.counterVisitIndex = -1;
    }

    public StateType getType() {
        return StateType.MINUS;
    }

    public boolean visit(int visitIndex, IEdge edge) {
        if (this.counterVisitIndex != visitIndex) {
            this.counterVisitIndex = visitIndex;
            this.visited = new boolean[this.getInputEdges().size()];

            Arrays.fill(visited, false);
        }

        visited[this.getInputEdges().size() - 1 - this.getInputEdges().indexOf(edge)] = true;

        return !visited[0] && visited[1];
    }

}
