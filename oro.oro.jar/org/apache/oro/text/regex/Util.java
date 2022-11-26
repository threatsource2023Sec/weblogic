package org.apache.oro.text.regex;

import java.util.Collection;
import java.util.Vector;

public final class Util {
   public static final int SUBSTITUTE_ALL = -1;
   public static final int SPLIT_ALL = 0;

   private Util() {
   }

   public static void split(Collection var0, PatternMatcher var1, Pattern var2, String var3, int var4) {
      PatternMatcherInput var7 = new PatternMatcherInput(var3);
      int var5 = 0;

      while(true) {
         --var4;
         if (var4 == 0 || !var1.contains(var7, var2)) {
            var0.add(var3.substring(var5, var3.length()));
            return;
         }

         MatchResult var6 = var1.getMatch();
         var0.add(var3.substring(var5, var6.beginOffset(0)));
         var5 = var6.endOffset(0);
      }
   }

   public static void split(Collection var0, PatternMatcher var1, Pattern var2, String var3) {
      split(var0, var1, var2, var3, 0);
   }

   /** @deprecated */
   public static Vector split(PatternMatcher var0, Pattern var1, String var2, int var3) {
      Vector var4 = new Vector(20);
      split(var4, var0, var1, var2, var3);
      return var4;
   }

   /** @deprecated */
   public static Vector split(PatternMatcher var0, Pattern var1, String var2) {
      return split(var0, var1, var2, 0);
   }

   public static String substitute(PatternMatcher var0, Pattern var1, Substitution var2, String var3, int var4) {
      StringBuffer var5 = new StringBuffer(var3.length());
      PatternMatcherInput var6 = new PatternMatcherInput(var3);
      return substitute(var5, var0, var1, var2, var6, var4) != 0 ? var5.toString() : var3;
   }

   public static String substitute(PatternMatcher var0, Pattern var1, Substitution var2, String var3) {
      return substitute(var0, var1, var2, var3, 1);
   }

   public static int substitute(StringBuffer var0, PatternMatcher var1, Pattern var2, Substitution var3, String var4, int var5) {
      PatternMatcherInput var6 = new PatternMatcherInput(var4);
      return substitute(var0, var1, var2, var3, var6, var5);
   }

   public static int substitute(StringBuffer var0, PatternMatcher var1, Pattern var2, Substitution var3, PatternMatcherInput var4, int var5) {
      int var7 = 0;
      int var6 = var4.getBeginOffset();

      char[] var8;
      for(var8 = var4.getBuffer(); var5 != 0 && var1.contains(var4, var2); var6 = var4.getMatchEndOffset()) {
         --var5;
         ++var7;
         var0.append(var8, var6, var4.getMatchBeginOffset() - var6);
         var3.appendSubstitution(var0, var1.getMatch(), var7, var4, var1, var2);
      }

      var0.append(var8, var6, var4.length() - var6);
      return var7;
   }
}
