package com.regexplus.automaton.common;

public interface IEdge {
    EdgeType getType();
    IState getStart();
    IState getFinish();
}
