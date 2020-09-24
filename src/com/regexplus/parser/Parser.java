package com.regexplus.parser;

import com.regexplus.parser.node.base.*;
import com.regexplus.parser.node.common.*;

import java.io.*;

/*
R =
    (A | ["~"] (R) | ".") ["*", "+", "?"],
    R1 R2,
    R1 | R2,
    R1 & R2
    R1 - R2
 */

public class Parser {
    IParserStream ips;

    public Parser(IParserStream ips) {
        this.ips = ips;
        this.ips.Read();
    }

    public int getNext() {
        this.ips.Read();
        return this.ips.getCurrent();
    }

    public int getCurrent() {
        return this.ips.getCurrent();
    }

    public boolean AtEnd() {
        return this.ips.getCurrent() == -1;
    }

    public void SkipBlanks() {
        while (!this.AtEnd()) {
            if (" \t\r\n".indexOf(this.ips.getCurrent()) < 0) break;
            this.ips.Read();
        }
    }

    public boolean IsMeta() {
        return ")*+?|&-.~".indexOf(this.getCurrent()) >= 0;
    }

    protected INode ParseNode() {
        this.SkipBlanks();

        INode node = null;

        if (this.getCurrent() == '(') {
            node = this.ParseGroup();
        } else if (this.getCurrent() == '.') {
            this.getNext();
            node = new NodeAnyLetter();
        } else if (this.getCurrent() == '~') {
            this.getNext();
            node = new NodeNot(this.ParseNode());
        } else {
            node = this.ParseLetter();
        }

        if (node != null) {
            node = ParseRepeat(node);
        }

        return node;
    }

    protected INode ParseRepeat(INode node) {
        INode result = node;

        this.SkipBlanks();

        switch (this.getCurrent()) {
            case '*' -> {
                this.getNext();
                result = new NodeRepeat(result, NodeRepeatType.STAR);
            }
            case '+' -> {
                this.getNext();
                result = new NodeRepeat(result, NodeRepeatType.PLUS);
            }
            case '?' -> {
                this.getNext();
                result = new NodeRepeat(result, NodeRepeatType.QUESTION);
            }
            // USER_DEFINED
        }

        return result;
    }

    protected INode ParseLetter() {
        if (!this.AtEnd()) {
            INode result = new NodeLetter((char) this.getCurrent());

            this.getNext();

            return result;
        }

        return null;
    }

    protected INode ParseGroup() {
        if (this.getCurrent() == '(') {
            this.getNext();

            INode group = this.Parse();

            this.SkipBlanks();

            if (this.getCurrent() == ')') {
                this.getNext();
            }

            if (group != null) {
                return new NodeGroup(group);
            }
        }

        return null;
    }

    protected INode ParseConcat() {
        INode left = this.ParseNode();

        if (left != null) {
            if (!IsMeta()) {
                INode right = this.ParseConcat();

                if (right != null) {
                    return new NodeConcat(left, right);
                }
            }
        }

        return left;
    }

    protected INode ParseChoice() {
        INode left = this.ParseConcat();

        if (left != null) {
            if (!IsMeta() || getCurrent() == '|') {
                this.getNext();

                INode right = this.ParseChoice();

                if (right != null) {
                    return new NodeChoice(left, right);
                }
            }
        }

        return left;
    }

    protected INode ParseAnd() {
        INode left = this.ParseChoice();

        if (left != null) {
            if (!IsMeta() || getCurrent() == '&') {
                this.getNext();

                INode right = this.ParseAnd();

                if (right != null) {
                    return new NodeAnd(left, right);
                }
            }
        }

        return left;
    }

    protected INode ParseMinus() {
        INode left = this.ParseAnd();

        if (left != null) {
            if (!IsMeta() || getCurrent() == '-') {
                this.getNext();

                INode right = this.ParseMinus();

                if (right != null) {
                    return new NodeMinus(left, right);
                }
            }
        }

        return left;
    }


    // public methods
    public INode Parse() {
        return this.ParseMinus();
    }

    public static INode ParseFromString(String text) {
        Parser parser = new Parser(new ParserInputStream(new ByteArrayInputStream(text.getBytes())));

        return parser.Parse();
    }
}
