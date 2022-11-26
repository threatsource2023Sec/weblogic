package org.antlr.runtime;

import org.antlr.runtime.misc.LookaheadStream;

public class UnbufferedTokenStream extends LookaheadStream implements TokenStream {
   protected TokenSource tokenSource;
   protected int tokenIndex = 0;
   protected int channel = 0;

   public UnbufferedTokenStream(TokenSource tokenSource) {
      this.tokenSource = tokenSource;
   }

   public Token nextElement() {
      Token t = this.tokenSource.nextToken();
      t.setTokenIndex(this.tokenIndex++);
      return t;
   }

   public boolean isEOF(Token o) {
      return o.getType() == -1;
   }

   public TokenSource getTokenSource() {
      return this.tokenSource;
   }

   public String toString(int start, int stop) {
      return "n/a";
   }

   public String toString(Token start, Token stop) {
      return "n/a";
   }

   public int LA(int i) {
      return ((Token)this.LT(i)).getType();
   }

   public Token get(int i) {
      throw new UnsupportedOperationException("Absolute token indexes are meaningless in an unbuffered stream");
   }

   public String getSourceName() {
      return this.tokenSource.getSourceName();
   }
}
