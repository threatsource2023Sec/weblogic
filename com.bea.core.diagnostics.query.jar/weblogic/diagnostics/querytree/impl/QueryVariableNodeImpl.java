package weblogic.diagnostics.querytree.impl;

import weblogic.diagnostics.querytree.QueryNode;
import weblogic.diagnostics.querytree.QueryNodeType;
import weblogic.diagnostics.querytree.QueryVariableNode;

public class QueryVariableNodeImpl extends QueryNodeImpl implements QueryVariableNode {
   private String name;

   public QueryVariableNodeImpl(String name) {
      super(QueryNodeType.VAR);
      this.name = name;
   }

   public int getChildCount() {
      return 0;
   }

   public void addChild(QueryNode child) {
      throw new UnsupportedOperationException();
   }

   public String getName() {
      return this.name;
   }

   public void displayNode(int margin, StringBuffer buf) {
      this.addMargin(margin, buf);
      buf.append(this.getType()).append(": " + this.name).append("\n");
   }
}
