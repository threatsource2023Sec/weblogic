package org.apache.oro.text.regex;

public interface PatternMatcher {
   boolean matchesPrefix(char[] var1, Pattern var2, int var3);

   boolean matchesPrefix(String var1, Pattern var2);

   boolean matchesPrefix(char[] var1, Pattern var2);

   boolean matchesPrefix(PatternMatcherInput var1, Pattern var2);

   boolean matches(String var1, Pattern var2);

   boolean matches(char[] var1, Pattern var2);

   boolean matches(PatternMatcherInput var1, Pattern var2);

   boolean contains(String var1, Pattern var2);

   boolean contains(char[] var1, Pattern var2);

   boolean contains(PatternMatcherInput var1, Pattern var2);

   MatchResult getMatch();
}
