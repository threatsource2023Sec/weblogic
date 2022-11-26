package org.antlr.runtime.tree;

import java.util.List;
import org.antlr.runtime.Token;

public class ParseTree extends BaseTree {
   public Object payload;
   public List hiddenTokens;

   public ParseTree(Object label) {
      this.payload = label;
   }

   public Tree dupNode() {
      return null;
   }

   public int getType() {
      return 0;
   }

   public String getText() {
      return this.toString();
   }

   public int getTokenStartIndex() {
      return 0;
   }

   public void setTokenStartIndex(int index) {
   }

   public int getTokenStopIndex() {
      return 0;
   }

   public void setTokenStopIndex(int index) {
   }

   public String toString() {
      if (this.payload instanceof Token) {
         Token t = (Token)this.payload;
         return t.getType() == -1 ? "<EOF>" : t.getText();
      } else {
         return this.payload.toString();
      }
   }

   public String toStringWithHiddenTokens() {
      StringBuilder buf = new StringBuilder();
      if (this.hiddenTokens != null) {
         for(int i = 0; i < this.hiddenTokens.size(); ++i) {
            Token hidden = (Token)this.hiddenTokens.get(i);
            buf.append(hidden.getText());
         }
      }

      String nodeText = this.toString();
      if (!nodeText.equals("<EOF>")) {
         buf.append(nodeText);
      }

      return buf.toString();
   }

   public String toInputString() {
      StringBuffer buf = new StringBuffer();
      this._toStringLeaves(buf);
      return buf.toString();
   }

   public void _toStringLeaves(StringBuffer buf) {
      if (this.payload instanceof Token) {
         buf.append(this.toStringWithHiddenTokens());
      } else {
         for(int i = 0; this.children != null && i < this.children.size(); ++i) {
            ParseTree t = (ParseTree)this.children.get(i);
            t._toStringLeaves(buf);
         }

      }
   }
}
