package com.regexplus.automaton.model;

import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.common.StateType;
import com.regexplus.match.common.IMatch;

import java.util.ArrayList;
import java.util.List;

public class State implements IState {
    int index, visitIndex;
    ArrayList<IEdge> outputEdges;
    List<IMatch> matches;

    public State() {
        this.index = -1;
        this.visitIndex = -1;
        this.outputEdges = new ArrayList<>();
        this.renew();
    }

    public StateType getType() {
        return StateType.EMPTY;
    }

    public List<IEdge> getOutputEdges() {
        return this.outputEdges;
    }

    public void addEdge(IEdge edge) {
        this.getOutputEdges().add(edge);
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean visited() {
        return this.index != -1;
    }

    public int getVisitIndex() {
        return this.visitIndex;
    }

    public void setVisitIndex(int visitIndex) {
        this.visitIndex = visitIndex;
    }

    public List<IMatch> matches() {
        return this.matches;
    }

    public void setMatches(List<IMatch> matches) {
        //this.matches = new ArrayList<>();

        //for (IMatch match: matches) {
        //    this.matches.add(match.copy());
        //}
        this.matches = matches;
    }

    public void renew() {
        this.matches = new ArrayList<>();
    }
}
