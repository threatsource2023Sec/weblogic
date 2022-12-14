package org.antlr.runtime;

public interface IntStream {
   void consume();

   int LA(int var1);

   int mark();

   int index();

   void rewind(int var1);

   void rewind();

   void release(int var1);

   void seek(int var1);

   int size();

   String getSourceName();
}
