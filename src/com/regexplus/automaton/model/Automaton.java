package com.regexplus.automaton.model;

import com.regexplus.automaton.base.*;
import com.regexplus.automaton.common.*;
import com.regexplus.match.common.IMatch;
import com.regexplus.match.model.Match;
import com.regexplus.parser.Parser;
import com.regexplus.parser.ParserInputStream;
import com.regexplus.parser.node.model.Node;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Automaton implements IAutomaton {
    IState start, finish;
    int currentStateIndex;

    public Automaton() {
        this.start = new StateStart();
        this.finish = new StateFinal();
        this.currentStateIndex = 0;
    }

    public IState getStart() {
        return this.start;
    }

    public IState getFinish() {
        return this.finish;
    }

    public void build(StringStream stream) {
        ParserInputStream pis = new ParserInputStream(new ByteArrayInputStream(stream.getSource().getBytes()));

        Parser parser = new Parser(pis);

        Node node = (Node) parser.Parse();

        IState[] astart = new IState[] { this.start };
        IState[] afinish = new IState[] { this.finish };

        node.expand(astart, afinish);
    }

    public List<IMatch> match(IStream stream) {
        //int bestMatch = NO_MATCH;
        //int currentPosition = 0;
        Stack<State> stack = new Stack<>();
        List<IMatch> bestMatches = new ArrayList<>();

        stack.push((State) this.start);

        int index = 0;

        while (!stack.empty()) {
            State state = stack.pop();

            state.setIndex(++index);

            for (IEdge edge: state.getOutputEdges()) {
                State outState = (State) edge.getFinish();

                if (!outState.visited()) {
                    outState.setIndex(++index);

                    stack.push(outState);
                }
            }
        }

        //stack.push((State) this.start);
        //((State) this.start).setVisitIndex(++this.currentStateIndex);

        //boolean doContinue;

        int iterationsCount = 0;
        int currentIterationCount = 0;

        stack.push((State) this.start);
        ((State) this.start).setVisitIndex(this.currentStateIndex);
        ((State) this.start).renew();
        ((State) this.start).matches().add(new Match(stream, stream.position()));

        do {
            Stack<State> closure = new Stack<>();

            //++this.currentStateIndex;
            ++currentIterationCount;

            //stack.push((State) this.start);
            //((State) this.start).setVisitIndex(this.currentStateIndex);
            //((State) this.start).renew();
            //((State) this.start).matches().add(new Match(stream, stream.position()));

            //doContinue = false;

            while (!stack.empty()) {
                State state = stack.pop();

                //state.setVisitIndex(this.currentStateIndex);

                if (state.getType() == StateType.FINAL) {
                    //bestMatch = this.currentStateIndex;//currentPosition;
                    state.matches().get(0).setEnd(stream.position());

                    if (Match.isBetter(state.matches(), bestMatches)) {
                        bestMatches = state.matches();
                    }

                    //System.out.println("z: " + this.currentStateIndex);
                }

                closure.push(state);

//                if (state.getIndex() == 5) {
//                    System.out.println("ZZ");
//                }

                //System.out.println ("1: " + state.getIndex());

                for (IEdge edge: state.getOutputEdges()) {
                    if (edge.getType() == EdgeType.EMPTY) {
                        State outState = (State) edge.getFinish();

                        boolean prePass = true;

                        if (outState.getType() == StateType.AND) {
                            StateAnd outStateAnd = (StateAnd) outState;

                            prePass = outStateAnd.visit(this.currentStateIndex, edge);
                        }

                        if (outState.getType() == StateType.MINUS) {
                            StateMinus outStateMinus = (StateMinus) outState;

                            prePass = outStateMinus.visit(this.currentStateIndex, edge);
                        }


                        //System.out.println("1: " + outState.getIndex());

                        if (outState.getVisitIndex() != this.currentStateIndex && prePass) {
                            outState.setVisitIndex(this.currentStateIndex);

                            //System.out.println("A: " + outState.getIndex() + " " + outState.getVisitIndex());

                            stack.push(outState);

                            //doContinue = true;

                            if (Match.isBetter(state.matches(), outState.matches())) {
                                outState.setMatches(state.matches());
                            }
                        }
                    }
                }
            }

            ++this.currentStateIndex;
            ++currentIterationCount;

            //++currentPosition;

            //System.out.println("z: " + stream.current());


            while (!closure.empty()) {
                State state = closure.pop();

                //state.setVisitIndex(this.currentStateIndex);

                //if (state.getType() == StateType.FINAL) {
                //    bestMatch = currentPosition;
                //}

                for (IEdge edge: state.getOutputEdges()) {
                    if (edge.getType() == EdgeType.LETTER) {
                        EdgeLetter edgeLetter = (EdgeLetter) edge;

                        //System.out.println("2: " + (char)edgeLetter.getLetter() + " " + (char)stream.current());

                        if (edgeLetter.accepts((char) stream.current())) {
                            State outState = (State) edgeLetter.getFinish();

                            //doContinue = true;

                            boolean prePass = true;

                            if (outState.getType() == StateType.AND) {
                                StateAnd outStateAnd = (StateAnd) outState;

                                prePass = outStateAnd.visit(this.currentStateIndex, edge);
                            }

                            if (outState.getType() == StateType.MINUS) {
                                StateMinus outStateMinus = (StateMinus) outState;

                                prePass = outStateMinus.visit(this.currentStateIndex, edge);
                            }

                            if (outState.getVisitIndex() != this.currentStateIndex && prePass) {
                                outState.setVisitIndex(this.currentStateIndex);

                                stack.push(outState);

                                outState.setMatches(state.matches());
                            }
                        }
                    }
                }
            }

            //++this.currentStateIndex;
            ++currentIterationCount;

            stream.next();

            if (stream.atEnd() || stack.empty()) {
                iterationsCount++;
            }

            //System.out.println("--");
        } while (iterationsCount <= 1);

        return bestMatches;
    }

    public boolean matches(IStream stream) {
        List<IMatch> result = this.match(stream);

        if (result.size() == 0) {
            return false;
        }

        return result.get(0).matches();
    }
}
