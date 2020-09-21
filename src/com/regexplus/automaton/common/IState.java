package com.regexplus.automaton.common;

import java.util.List;

public interface IState {
    StateType getType();
    List<IEdge> getOutputEdges();
}
