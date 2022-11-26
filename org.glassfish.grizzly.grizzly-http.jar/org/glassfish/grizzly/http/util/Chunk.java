package org.glassfish.grizzly.http.util;

public interface Chunk {
   int getStart();

   void setStart(int var1);

   int getEnd();

   void setEnd(int var1);

   int getLength();

   String toString(int var1, int var2);

   int indexOf(char var1, int var2);

   int indexOf(String var1, int var2);

   void delete(int var1, int var2);
}
