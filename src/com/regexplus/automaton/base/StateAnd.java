package com.regexplus.automaton.base;

import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.StateType;
import com.regexplus.automaton.model.State;

import java.util.Arrays;

public class StateAnd extends State {
    int counter, counterVisitIndex;
    boolean[] visited;

    public StateAnd() {
        super ();
        this.counterVisitIndex = -1;
    }

    public StateType getType() {
        return StateType.AND;
    }

    public boolean visit(int visitIndex, IEdge edge) {
        if (this.counterVisitIndex != visitIndex) {
            this.counterVisitIndex = visitIndex;
            this.counter = this.getInputEdges().size();
            this.visited = new boolean[this.getInputEdges().size()];

            Arrays.fill(visited, false);
        }

        if (!visited[this.getInputEdges().indexOf(edge)]

/*                &&
                (
                        this.matches().size() == 0 ||
                        ((State)edge.getFinish()).matches().get(0).start() == this.matches().get(0).start()
                )
          */
        )
        {
            --this.counter;
        }

        visited[this.getInputEdges().indexOf(edge)] = true;

        return this.counter == 0;
    }
}
