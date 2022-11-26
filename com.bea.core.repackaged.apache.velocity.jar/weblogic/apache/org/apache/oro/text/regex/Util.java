package weblogic.apache.org.apache.oro.text.regex;

import java.util.Collection;
import java.util.Vector;

public final class Util {
   public static final int SUBSTITUTE_ALL = -1;
   public static final int SPLIT_ALL = 0;

   private Util() {
   }

   public static void split(Collection var0, PatternMatcher var1, Pattern var2, String var3) {
      split(var0, var1, var2, var3, 0);
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

   /** @deprecated */
   public static Vector split(PatternMatcher var0, Pattern var1, String var2) {
      return split(var0, var1, var2, 0);
   }

   /** @deprecated */
   public static Vector split(PatternMatcher var0, Pattern var1, String var2, int var3) {
      Vector var4 = new Vector(20);
      split(var4, var0, var1, var2, var3);
      return var4;
   }

   public static String substitute(PatternMatcher var0, Pattern var1, Substitution var2, String var3) {
      return substitute(var0, var1, var2, var3, 1);
   }

   public static String substitute(PatternMatcher var0, Pattern var1, Substitution var2, String var3, int var4) {
      StringBuffer var9 = new StringBuffer(var3.length());
      PatternMatcherInput var8 = new PatternMatcherInput(var3);
      int var6 = 0;

      int var5;
      MatchResult var7;
      for(var5 = 0; var4 != 0 && var0.contains(var8, var1); var5 = var7.endOffset(0)) {
         --var4;
         ++var6;
         var7 = var0.getMatch();
         var9.append(var3.substring(var5, var7.beginOffset(0)));
         var2.appendSubstitution(var9, var7, var6, var3, var0, var1);
      }

      if (var6 == 0) {
         return var3;
      } else {
         var9.append(var3.substring(var5, var3.length()));
         return var9.toString();
      }
   }
}
