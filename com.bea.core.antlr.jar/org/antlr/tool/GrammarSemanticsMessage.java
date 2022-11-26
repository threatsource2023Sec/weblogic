package org.antlr.tool;

import org.antlr.runtime.Token;
import org.stringtemplate.v4.ST;

public class GrammarSemanticsMessage extends Message {
   public Grammar g;
   public Token offendingToken;

   public GrammarSemanticsMessage(int msgID, Grammar g, Token offendingToken) {
      this(msgID, g, offendingToken, (Object)null, (Object)null);
   }

   public GrammarSemanticsMessage(int msgID, Grammar g, Token offendingToken, Object arg) {
      this(msgID, g, offendingToken, arg, (Object)null);
   }

   public GrammarSemanticsMessage(int msgID, Grammar g, Token offendingToken, Object arg, Object arg2) {
      super(msgID, arg, arg2);
      this.g = g;
      this.offendingToken = offendingToken;
   }

   public String toString() {
      this.line = 0;
      this.column = 0;
      if (this.offendingToken != null) {
         this.line = this.offendingToken.getLine();
         this.column = this.offendingToken.getCharPositionInLine();
      }

      if (this.g != null) {
         this.file = this.g.getFileName();
      }

      ST st = this.getMessageTemplate();
      if (this.arg != null) {
         st.add("arg", this.arg);
      }

      if (this.arg2 != null) {
         st.add("arg2", this.arg2);
      }

      return super.toString(st);
   }
}
