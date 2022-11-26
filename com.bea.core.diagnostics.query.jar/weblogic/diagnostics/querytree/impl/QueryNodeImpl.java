package weblogic.diagnostics.querytree.impl;

import weblogic.diagnostics.querytree.QueryNode;
import weblogic.diagnostics.querytree.QueryNodeType;

public class QueryNodeImpl implements QueryNode {
   private QueryNodeType type;
   private QueryNode[] children;

   public QueryNodeImpl(QueryNodeType type) {
      this.type = type;
   }

   public QueryNodeImpl(QueryNodeType type, QueryNode child) {
      this.type = type;
      this.addChild(child);
   }

   public QueryNodeImpl(QueryNodeType type, QueryNode child1, QueryNode child2) {
      this.type = type;
      this.addChild(child1);
      this.addChild(child2);
   }

   public QueryNodeImpl(QueryNodeType type, QueryNode[] children) {
      this.type = type;
      QueryNode[] var3 = children;
      int var4 = children.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         QueryNode child = var3[var5];
         this.addChild(child);
      }

   }

   public QueryNodeType getType() {
      return this.type;
   }

   public int getChildCount() {
      return this.children != null ? this.children.length : 0;
   }

   public QueryNode getChild(int index) {
      if (index >= 0 && index < this.getChildCount()) {
         return this.children[index];
      } else {
         throw new ArrayIndexOutOfBoundsException(index);
      }
   }

   public void addChild(QueryNode child) {
      int len = this.getChildCount();
      QueryNode[] arr = new QueryNode[len + 1];

      for(int i = 0; i < len; ++i) {
         arr[i] = this.children[i];
      }

      arr[len] = child;
      this.children = arr;
   }

   protected void addMargin(int margin, StringBuffer buf) {
      for(int i = 0; i < margin; ++i) {
         buf.append("..");
      }

   }

   public void displayNode(int margin, StringBuffer buf) {
      this.addMargin(margin, buf);
      buf.append(this.type).append("\n");
      int len = this.getChildCount();

      for(int i = 0; i < len; ++i) {
         this.children[i].displayNode(margin + 1, buf);
      }

   }
}
