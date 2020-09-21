package com.regexplus.parser.node.common;

import java.util.*;

public interface INode {
    NodeType getType();
    boolean isAtomic();
    List<INode> getChildren();
}
