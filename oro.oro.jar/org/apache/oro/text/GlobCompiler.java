package org.apache.oro.text;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.Perl5Compiler;

public final class GlobCompiler implements PatternCompiler {
   public static final int DEFAULT_MASK = 0;
   public static final int CASE_INSENSITIVE_MASK = 1;
   public static final int STAR_CANNOT_MATCH_NULL_MASK = 2;
   public static final int QUESTION_MATCHES_ZERO_OR_ONE_MASK = 4;
   public static final int READ_ONLY_MASK = 8;
   private Perl5Compiler __perl5Compiler = new Perl5Compiler();

   private static boolean __isPerl5MetaCharacter(char var0) {
      return var0 == '*' || var0 == '?' || var0 == '+' || var0 == '[' || var0 == ']' || var0 == '(' || var0 == ')' || var0 == '|' || var0 == '^' || var0 == '$' || var0 == '.' || var0 == '{' || var0 == '}' || var0 == '\\';
   }

   private static boolean __isGlobMetaCharacter(char var0) {
      return var0 == '*' || var0 == '?' || var0 == '[' || var0 == ']';
   }

   public static String globToPerl5(char[] var0, int var1) {
      boolean var3 = false;
      StringBuffer var6 = new StringBuffer(2 * var0.length);
      boolean var2 = false;
      boolean var4 = (var1 & 4) != 0;
      var3 = (var1 & 2) != 0;

      for(int var5 = 0; var5 < var0.length; ++var5) {
         switch (var0[var5]) {
            case '*':
               if (var2) {
                  var6.append('*');
               } else if (var3) {
                  var6.append(".+");
               } else {
                  var6.append(".*");
               }
               break;
            case '?':
               if (var2) {
                  var6.append('?');
               } else if (var4) {
                  var6.append(".?");
               } else {
                  var6.append('.');
               }
               break;
            case '[':
               var2 = true;
               var6.append(var0[var5]);
               if (var5 + 1 < var0.length) {
                  switch (var0[var5 + 1]) {
                     case '!':
                     case '^':
                        var6.append('^');
                        ++var5;
                        break;
                     case ']':
                        var6.append(']');
                        ++var5;
                  }
               }
               break;
            case '\\':
               var6.append('\\');
               if (var5 == var0.length - 1) {
                  var6.append('\\');
               } else if (__isGlobMetaCharacter(var0[var5 + 1])) {
                  ++var5;
                  var6.append(var0[var5]);
               } else {
                  var6.append('\\');
               }
               break;
            case ']':
               var2 = false;
               var6.append(var0[var5]);
               break;
            default:
               if (!var2 && __isPerl5MetaCharacter(var0[var5])) {
                  var6.append('\\');
               }

               var6.append(var0[var5]);
         }
      }

      return var6.toString();
   }

   public Pattern compile(char[] var1, int var2) throws MalformedPatternException {
      int var3 = 0;
      if ((var2 & 1) != 0) {
         var3 |= 1;
      }

      if ((var2 & 8) != 0) {
         var3 |= 32768;
      }

      return this.__perl5Compiler.compile(globToPerl5(var1, var2), var3);
   }

   public Pattern compile(char[] var1) throws MalformedPatternException {
      return this.compile((char[])var1, 0);
   }

   public Pattern compile(String var1) throws MalformedPatternException {
      return this.compile((char[])var1.toCharArray(), 0);
   }

   public Pattern compile(String var1, int var2) throws MalformedPatternException {
      return this.compile(var1.toCharArray(), var2);
   }
}
