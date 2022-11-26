package org.antlr.runtime.debug;

import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.TokenStream;

public class DebugTokenStream implements TokenStream {
   protected DebugEventListener dbg;
   public TokenStream input;
   protected boolean initialStreamState = true;
   protected int lastMarker;

   public DebugTokenStream(TokenStream input, DebugEventListener dbg) {
      this.input = input;
      this.setDebugListener(dbg);
      input.LT(1);
   }

   public void setDebugListener(DebugEventListener dbg) {
      this.dbg = dbg;
   }

   public void consume() {
      if (this.initialStreamState) {
         this.consumeInitialHiddenTokens();
      }

      int a = this.input.index();
      Token t = this.input.LT(1);
      this.input.consume();
      int b = this.input.index();
      this.dbg.consumeToken(t);
      if (b > a + 1) {
         for(int i = a + 1; i < b; ++i) {
            this.dbg.consumeHiddenToken(this.input.get(i));
         }
      }

   }

   protected void consumeInitialHiddenTokens() {
      int firstOnChannelTokenIndex = this.input.index();

      for(int i = 0; i < firstOnChannelTokenIndex; ++i) {
         this.dbg.consumeHiddenToken(this.input.get(i));
      }

      this.initialStreamState = false;
   }

   public Token LT(int i) {
      if (this.initialStreamState) {
         this.consumeInitialHiddenTokens();
      }

      this.dbg.LT(i, this.input.LT(i));
      return this.input.LT(i);
   }

   public int LA(int i) {
      if (this.initialStreamState) {
         this.consumeInitialHiddenTokens();
      }

      this.dbg.LT(i, this.input.LT(i));
      return this.input.LA(i);
   }

   public Token get(int i) {
      return this.input.get(i);
   }

   public int mark() {
      this.lastMarker = this.input.mark();
      this.dbg.mark(this.lastMarker);
      return this.lastMarker;
   }

   public int index() {
      return this.input.index();
   }

   public int range() {
      return this.input.range();
   }

   public void rewind(int marker) {
      this.dbg.rewind(marker);
      this.input.rewind(marker);
   }

   public void rewind() {
      this.dbg.rewind();
      this.input.rewind(this.lastMarker);
   }

   public void release(int marker) {
   }

   public void seek(int index) {
      this.input.seek(index);
   }

   public int size() {
      return this.input.size();
   }

   public TokenSource getTokenSource() {
      return this.input.getTokenSource();
   }

   public String getSourceName() {
      return this.getTokenSource().getSourceName();
   }

   public String toString() {
      return this.input.toString();
   }

   public String toString(int start, int stop) {
      return this.input.toString(start, stop);
   }

   public String toString(Token start, Token stop) {
      return this.input.toString(start, stop);
   }
}
