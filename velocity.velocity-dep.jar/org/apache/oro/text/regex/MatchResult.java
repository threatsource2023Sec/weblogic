package org.apache.oro.text.regex;

public interface MatchResult {
   int begin(int var1);

   int beginOffset(int var1);

   int end(int var1);

   int endOffset(int var1);

   String group(int var1);

   int groups();

   int length();

   String toString();
}
