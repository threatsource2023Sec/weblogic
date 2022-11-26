package org.antlr.runtime;

public interface CharStream extends IntStream {
   int EOF = -1;

   String substring(int var1, int var2);

   int LT(int var1);

   int getLine();

   void setLine(int var1);

   void setCharPositionInLine(int var1);

   int getCharPositionInLine();
}
