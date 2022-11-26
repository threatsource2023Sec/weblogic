package weblogic.diagnostics.querytree.impl;

import weblogic.diagnostics.querytree.QueryNode;
import weblogic.diagnostics.querytree.QueryNodeType;
import weblogic.diagnostics.querytree.QueryValueNode;

public class QueryValueNodeImpl extends QueryNodeImpl implements QueryValueNode {
   private Object value;

   public QueryValueNodeImpl(Object value) {
      super(QueryNodeType.VALUE);
      this.value = value;
   }

   public int getChildCount() {
      return 0;
   }

   public void addChild(QueryNode child) {
      throw new UnsupportedOperationException();
   }

   public Object getValue() {
      return this.value;
   }

   public void displayNode(int margin, StringBuffer buf) {
      this.addMargin(margin, buf);
      String type = this.value != null ? this.value.getClass().getName() : "?";
      buf.append(this.getType()).append(": [" + type + "] " + this.value).append("\n");
   }
}
