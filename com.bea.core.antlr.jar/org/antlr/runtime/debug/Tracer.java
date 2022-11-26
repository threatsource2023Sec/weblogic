package org.antlr.runtime.debug;

import org.antlr.runtime.IntStream;
import org.antlr.runtime.TokenStream;

public class Tracer extends BlankDebugEventListener {
   public IntStream input;
   protected int level = 0;

   public Tracer(IntStream input) {
      this.input = input;
   }

   public void enterRule(String ruleName) {
      for(int i = 1; i <= this.level; ++i) {
         System.out.print(" ");
      }

      System.out.println("> " + ruleName + " lookahead(1)=" + this.getInputSymbol(1));
      ++this.level;
   }

   public void exitRule(String ruleName) {
      --this.level;

      for(int i = 1; i <= this.level; ++i) {
         System.out.print(" ");
      }

      System.out.println("< " + ruleName + " lookahead(1)=" + this.getInputSymbol(1));
   }

   public Object getInputSymbol(int k) {
      return this.input instanceof TokenStream ? ((TokenStream)this.input).LT(k) : (char)this.input.LA(k);
   }
}
