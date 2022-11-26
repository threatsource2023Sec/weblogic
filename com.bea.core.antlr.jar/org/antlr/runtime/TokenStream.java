package org.antlr.runtime;

public interface TokenStream extends IntStream {
   Token LT(int var1);

   int range();

   Token get(int var1);

   TokenSource getTokenSource();

   String toString(int var1, int var2);

   String toString(Token var1, Token var2);
}
