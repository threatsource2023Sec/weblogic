package org.apache.regexp;

public interface CharacterIterator {
   String substring(int var1, int var2);

   String substring(int var1);

   char charAt(int var1);

   boolean isEnd(int var1);
}
