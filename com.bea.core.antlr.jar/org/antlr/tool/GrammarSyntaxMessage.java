package org.antlr.tool;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.stringtemplate.v4.ST;

public class GrammarSyntaxMessage extends Message {
   public Grammar g;
   public Token offendingToken;
   public RecognitionException exception;

   public GrammarSyntaxMessage(int msgID, Grammar grammar, Token offendingToken, RecognitionException exception) {
      this(msgID, grammar, offendingToken, (Object)null, exception);
   }

   public GrammarSyntaxMessage(int msgID, Grammar grammar, Token offendingToken, Object arg, RecognitionException exception) {
      super(msgID, arg, (Object)null);
      this.offendingToken = offendingToken;
      this.exception = exception;
      this.g = grammar;
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

      return super.toString(st);
   }
}
