package org.antlr.runtime.tree;

import org.antlr.runtime.Token;

public class CommonTree extends BaseTree {
   public Token token;
   protected int startIndex = -1;
   protected int stopIndex = -1;
   public CommonTree parent;
   public int childIndex = -1;

   public CommonTree() {
   }

   public CommonTree(CommonTree node) {
      super(node);
      this.token = node.token;
      this.startIndex = node.startIndex;
      this.stopIndex = node.stopIndex;
   }

   public CommonTree(Token t) {
      this.token = t;
   }

   public Token getToken() {
      return this.token;
   }

   public Tree dupNode() {
      return new CommonTree(this);
   }

   public boolean isNil() {
      return this.token == null;
   }

   public int getType() {
      return this.token == null ? 0 : this.token.getType();
   }

   public String getText() {
      return this.token == null ? null : this.token.getText();
   }

   public int getLine() {
      if (this.token != null && this.token.getLine() != 0) {
         return this.token.getLine();
      } else {
         return this.getChildCount() > 0 ? this.getChild(0).getLine() : 0;
      }
   }

   public int getCharPositionInLine() {
      if (this.token != null && this.token.getCharPositionInLine() != -1) {
         return this.token.getCharPositionInLine();
      } else {
         return this.getChildCount() > 0 ? this.getChild(0).getCharPositionInLine() : 0;
      }
   }

   public int getTokenStartIndex() {
      return this.startIndex == -1 && this.token != null ? this.token.getTokenIndex() : this.startIndex;
   }

   public void setTokenStartIndex(int index) {
      this.startIndex = index;
   }

   public int getTokenStopIndex() {
      return this.stopIndex == -1 && this.token != null ? this.token.getTokenIndex() : this.stopIndex;
   }

   public void setTokenStopIndex(int index) {
      this.stopIndex = index;
   }

   public void setUnknownTokenBoundaries() {
      if (this.children == null) {
         if (this.startIndex < 0 || this.stopIndex < 0) {
            this.startIndex = this.stopIndex = this.token.getTokenIndex();
         }

      } else {
         for(int i = 0; i < this.children.size(); ++i) {
            ((CommonTree)this.children.get(i)).setUnknownTokenBoundaries();
         }

         if (this.startIndex < 0 || this.stopIndex < 0) {
            if (this.children.size() > 0) {
               CommonTree firstChild = (CommonTree)this.children.get(0);
               CommonTree lastChild = (CommonTree)this.children.get(this.children.size() - 1);
               this.startIndex = firstChild.getTokenStartIndex();
               this.stopIndex = lastChild.getTokenStopIndex();
            }

         }
      }
   }

   public int getChildIndex() {
      return this.childIndex;
   }

   public Tree getParent() {
      return this.parent;
   }

   public void setParent(Tree t) {
      this.parent = (CommonTree)t;
   }

   public void setChildIndex(int index) {
      this.childIndex = index;
   }

   public String toString() {
      if (this.isNil()) {
         return "nil";
      } else if (this.getType() == 0) {
         return "<errornode>";
      } else {
         return this.token == null ? null : this.token.getText();
      }
   }
}
