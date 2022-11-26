package org.apache.oro.text.perl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import org.apache.oro.text.PatternCache;
import org.apache.oro.text.PatternCacheLRU;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Util;
import org.apache.oro.util.Cache;
import org.apache.oro.util.CacheLRU;

public final class Perl5Util implements MatchResult {
   private static final String __matchExpression = "m?(\\W)(.*)\\1([imsx]*)";
   private PatternCache __patternCache;
   private Cache __expressionCache;
   private Perl5Matcher __matcher;
   private Pattern __matchPattern;
   private MatchResult __lastMatch;
   private ArrayList __splitList;
   private Object __originalInput;
   private int __inputBeginOffset;
   private int __inputEndOffset;
   private static final String __nullString = "";
   public static final int SPLIT_ALL = 0;

   public Perl5Util(PatternCache var1) {
      this.__splitList = new ArrayList();
      this.__matcher = new Perl5Matcher();
      this.__patternCache = var1;
      this.__expressionCache = new CacheLRU(var1.capacity());
      this.__compilePatterns();
   }

   public Perl5Util() {
      this(new PatternCacheLRU());
   }

   private void __compilePatterns() {
      Perl5Compiler var1 = new Perl5Compiler();

      try {
         this.__matchPattern = var1.compile((String)"m?(\\W)(.*)\\1([imsx]*)", 16);
      } catch (MalformedPatternException var3) {
         throw new RuntimeException(var3.getMessage());
      }
   }

   private Pattern __parseMatchExpression(String var1) throws MalformedPerl5PatternException {
      Object var7 = this.__expressionCache.getElement(var1);

      try {
         if (var7 != null) {
            return (Pattern)var7;
         }
      } catch (ClassCastException var10) {
      }

      if (!this.__matcher.matches(var1, this.__matchPattern)) {
         throw new MalformedPerl5PatternException("Invalid expression: " + var1);
      } else {
         MatchResult var6 = this.__matcher.getMatch();
         String var5 = var6.group(2);
         int var3 = 0;
         String var4 = var6.group(3);
         if (var4 != null) {
            int var2 = var4.length();

            while(var2-- > 0) {
               switch (var4.charAt(var2)) {
                  case 'i':
                     var3 |= 1;
                     break;
                  case 'm':
                     var3 |= 8;
                     break;
                  case 's':
                     var3 |= 16;
                     break;
                  case 'x':
                     var3 |= 32;
                     break;
                  default:
                     throw new MalformedPerl5PatternException("Invalid options: " + var4);
               }
            }
         }

         Pattern var8 = this.__patternCache.getPattern(var5, var3);
         this.__expressionCache.addElement(var1, var8);
         return var8;
      }
   }

   public synchronized boolean match(String var1, char[] var2) throws MalformedPerl5PatternException {
      this.__parseMatchExpression(var1);
      boolean var3 = this.__matcher.contains(var2, this.__parseMatchExpression(var1));
      if (var3) {
         this.__lastMatch = this.__matcher.getMatch();
         this.__originalInput = var2;
         this.__inputBeginOffset = 0;
         this.__inputEndOffset = var2.length;
      }

      return var3;
   }

   public synchronized boolean match(String var1, String var2) throws MalformedPerl5PatternException {
      return this.match(var1, var2.toCharArray());
   }

   public synchronized boolean match(String var1, PatternMatcherInput var2) throws MalformedPerl5PatternException {
      boolean var3 = this.__matcher.contains(var2, this.__parseMatchExpression(var1));
      if (var3) {
         this.__lastMatch = this.__matcher.getMatch();
         this.__originalInput = var2.getInput();
         this.__inputBeginOffset = var2.getBeginOffset();
         this.__inputEndOffset = var2.getEndOffset();
      }

      return var3;
   }

   public synchronized MatchResult getMatch() {
      return this.__lastMatch;
   }

   public synchronized int substitute(StringBuffer var1, String var2, String var3) throws MalformedPerl5PatternException {
      Object var20 = this.__expressionCache.getElement(var2);
      int var13;
      ParsedSubstitutionEntry var18;
      if (var20 != null) {
         label124: {
            try {
               var18 = (ParsedSubstitutionEntry)var20;
            } catch (ClassCastException var22) {
               break label124;
            }

            var13 = Util.substitute(var1, this.__matcher, var18._pattern, var18._substitution, (String)var3, var18._numSubstitutions);
            this.__lastMatch = this.__matcher.getMatch();
            return var13;
         }
      }

      char[] var16 = var2.toCharArray();
      if (var16.length >= 4 && var16[0] == 's' && !Character.isLetterOrDigit(var16[1]) && var16[1] != '-') {
         char var17 = var16[1];
         byte var10 = 2;
         int var12 = -1;
         int var11 = -1;
         boolean var4 = false;

         int var6;
         for(var6 = var10; var6 < var16.length; ++var6) {
            if (var16[var6] == '\\') {
               var4 = !var4;
            } else {
               if (var16[var6] == var17 && !var4) {
                  var11 = var6;
                  break;
               }

               if (var4) {
                  var4 = !var4;
               }
            }
         }

         if (var11 != -1 && var11 != var16.length - 1) {
            var4 = false;
            boolean var5 = true;
            StringBuffer var14 = new StringBuffer(var16.length - var11);

            for(var6 = var11 + 1; var6 < var16.length; ++var6) {
               if (var16[var6] == '\\') {
                  var4 = !var4;
                  if (var4 && var6 + 1 < var16.length && var16[var6 + 1] == var17 && var2.lastIndexOf(var17, var16.length - 1) != var6 + 1) {
                     var5 = false;
                     continue;
                  }
               } else {
                  if (var16[var6] == var17 && var5) {
                     var12 = var6;
                     break;
                  }

                  var4 = false;
                  var5 = true;
               }

               var14.append(var16[var6]);
            }

            if (var12 == -1) {
               throw new MalformedPerl5PatternException("Invalid expression: " + var2);
            } else {
               int var7 = 0;
               byte var8 = 1;
               byte var9;
               if (var17 != '\'') {
                  var9 = 0;
               } else {
                  var9 = -1;
               }

               for(var6 = var12 + 1; var6 < var16.length; ++var6) {
                  switch (var16[var6]) {
                     case 'g':
                        var8 = -1;
                        break;
                     case 'h':
                     case 'j':
                     case 'k':
                     case 'l':
                     case 'n':
                     case 'p':
                     case 'q':
                     case 'r':
                     case 't':
                     case 'u':
                     case 'v':
                     case 'w':
                     default:
                        throw new MalformedPerl5PatternException("Invalid option: " + var16[var6]);
                     case 'i':
                        var7 |= 1;
                        break;
                     case 'm':
                        var7 |= 8;
                        break;
                     case 'o':
                        var9 = 1;
                        break;
                     case 's':
                        var7 |= 16;
                        break;
                     case 'x':
                        var7 |= 32;
                  }
               }

               Pattern var15 = this.__patternCache.getPattern(new String(var16, var10, var11 - var10), var7);
               Perl5Substitution var19 = new Perl5Substitution(var14.toString(), var9);
               var18 = new ParsedSubstitutionEntry(var15, var19, var8);
               this.__expressionCache.addElement(var2, var18);
               var13 = Util.substitute(var1, this.__matcher, var15, var19, (String)var3, var8);
               this.__lastMatch = this.__matcher.getMatch();
               return var13;
            }
         } else {
            throw new MalformedPerl5PatternException("Invalid expression: " + var2);
         }
      } else {
         throw new MalformedPerl5PatternException("Invalid expression: " + var2);
      }
   }

   public synchronized String substitute(String var1, String var2) throws MalformedPerl5PatternException {
      StringBuffer var3 = new StringBuffer();
      this.substitute(var3, var1, var2);
      return var3.toString();
   }

   public synchronized void split(Collection var1, String var2, String var3, int var4) throws MalformedPerl5PatternException {
      MatchResult var9 = null;
      Pattern var11 = this.__parseMatchExpression(var2);
      PatternMatcherInput var10 = new PatternMatcherInput(var3);
      int var5 = 0;

      while(true) {
         --var4;
         if (var4 == 0 || !this.__matcher.contains(var10, var11)) {
            this.__splitList.add(var3.substring(var5, var3.length()));

            for(int var12 = this.__splitList.size() - 1; var12 >= 0; --var12) {
               String var13 = (String)this.__splitList.get(var12);
               if (var13.length() != 0) {
                  break;
               }

               this.__splitList.remove(var12);
            }

            var1.addAll(this.__splitList);
            this.__splitList.clear();
            this.__lastMatch = var9;
            return;
         }

         var9 = this.__matcher.getMatch();
         this.__splitList.add(var3.substring(var5, var9.beginOffset(0)));
         int var6;
         if ((var6 = var9.groups()) > 1) {
            for(int var7 = 1; var7 < var6; ++var7) {
               String var8 = var9.group(var7);
               if (var8 != null && var8.length() > 0) {
                  this.__splitList.add(var8);
               }
            }
         }

         var5 = var9.endOffset(0);
      }
   }

   public synchronized void split(Collection var1, String var2, String var3) throws MalformedPerl5PatternException {
      this.split(var1, var2, var3, 0);
   }

   public synchronized void split(Collection var1, String var2) throws MalformedPerl5PatternException {
      this.split(var1, "/\\s+/", var2);
   }

   /** @deprecated */
   public synchronized Vector split(String var1, String var2, int var3) throws MalformedPerl5PatternException {
      Vector var4 = new Vector(20);
      this.split(var4, var1, var2, var3);
      return var4;
   }

   /** @deprecated */
   public synchronized Vector split(String var1, String var2) throws MalformedPerl5PatternException {
      return this.split(var1, var2, 0);
   }

   /** @deprecated */
   public synchronized Vector split(String var1) throws MalformedPerl5PatternException {
      return this.split("/\\s+/", var1);
   }

   public synchronized int length() {
      return this.__lastMatch.length();
   }

   public synchronized int groups() {
      return this.__lastMatch.groups();
   }

   public synchronized String group(int var1) {
      return this.__lastMatch.group(var1);
   }

   public synchronized int begin(int var1) {
      return this.__lastMatch.begin(var1);
   }

   public synchronized int end(int var1) {
      return this.__lastMatch.end(var1);
   }

   public synchronized int beginOffset(int var1) {
      return this.__lastMatch.beginOffset(var1);
   }

   public synchronized int endOffset(int var1) {
      return this.__lastMatch.endOffset(var1);
   }

   public synchronized String toString() {
      return this.__lastMatch == null ? null : this.__lastMatch.toString();
   }

   public synchronized String preMatch() {
      if (this.__originalInput == null) {
         return "";
      } else {
         int var1 = this.__lastMatch.beginOffset(0);
         if (var1 <= 0) {
            return "";
         } else if (this.__originalInput instanceof char[]) {
            char[] var3 = (char[])this.__originalInput;
            if (var1 > var3.length) {
               var1 = var3.length;
            }

            return new String(var3, this.__inputBeginOffset, var1);
         } else if (this.__originalInput instanceof String) {
            String var2 = (String)this.__originalInput;
            if (var1 > var2.length()) {
               var1 = var2.length();
            }

            return var2.substring(this.__inputBeginOffset, var1);
         } else {
            return "";
         }
      }
   }

   public synchronized String postMatch() {
      if (this.__originalInput == null) {
         return "";
      } else {
         int var1 = this.__lastMatch.endOffset(0);
         if (var1 < 0) {
            return "";
         } else if (this.__originalInput instanceof char[]) {
            char[] var3 = (char[])this.__originalInput;
            return var1 >= var3.length ? "" : new String(var3, var1, this.__inputEndOffset - var1);
         } else if (this.__originalInput instanceof String) {
            String var2 = (String)this.__originalInput;
            return var1 >= var2.length() ? "" : var2.substring(var1, this.__inputEndOffset);
         } else {
            return "";
         }
      }
   }

   public synchronized char[] preMatchCharArray() {
      char[] var2 = null;
      if (this.__originalInput == null) {
         return null;
      } else {
         int var1 = this.__lastMatch.beginOffset(0);
         if (var1 <= 0) {
            return null;
         } else {
            if (this.__originalInput instanceof char[]) {
               char[] var3 = (char[])this.__originalInput;
               if (var1 >= var3.length) {
                  var1 = var3.length;
               }

               var2 = new char[var1 - this.__inputBeginOffset];
               System.arraycopy(var3, this.__inputBeginOffset, var2, 0, var2.length);
            } else if (this.__originalInput instanceof String) {
               String var4 = (String)this.__originalInput;
               if (var1 >= var4.length()) {
                  var1 = var4.length();
               }

               var2 = new char[var1 - this.__inputBeginOffset];
               var4.getChars(this.__inputBeginOffset, var1, var2, 0);
            }

            return var2;
         }
      }
   }

   public synchronized char[] postMatchCharArray() {
      char[] var2 = null;
      if (this.__originalInput == null) {
         return null;
      } else {
         int var1 = this.__lastMatch.endOffset(0);
         if (var1 < 0) {
            return null;
         } else {
            if (this.__originalInput instanceof char[]) {
               char[] var4 = (char[])this.__originalInput;
               if (var1 >= var4.length) {
                  return null;
               }

               int var3 = this.__inputEndOffset - var1;
               var2 = new char[var3];
               System.arraycopy(var4, var1, var2, 0, var3);
            } else if (this.__originalInput instanceof String) {
               String var5 = (String)this.__originalInput;
               if (var1 >= this.__inputEndOffset) {
                  return null;
               }

               var2 = new char[this.__inputEndOffset - var1];
               var5.getChars(var1, this.__inputEndOffset, var2, 0);
            }

            return var2;
         }
      }
   }
}
