package org.apache.regexp;

import java.io.StringBufferInputStream;
import java.io.StringReader;

final class RETestCase {
   private final StringBuffer log = new StringBuffer();
   private final int number;
   private final String tag;
   private final String pattern;
   private final String toMatch;
   private final boolean badPattern;
   private final boolean shouldMatch;
   private final String[] parens;
   private final RETest test;
   private RE regexp;

   public RETestCase(RETest var1, String var2, String var3, String var4, boolean var5, boolean var6, String[] var7) {
      this.number = ++var1.testCount;
      this.test = var1;
      this.tag = var2;
      this.pattern = var3;
      this.toMatch = var4;
      this.badPattern = var5;
      this.shouldMatch = var6;
      if (var7 != null) {
         this.parens = new String[var7.length];

         for(int var8 = 0; var8 < var7.length; ++var8) {
            this.parens[var8] = var7[var8];
         }
      } else {
         this.parens = null;
      }

   }

   public void runTest() {
      this.test.say(this.tag + "(" + this.number + "): " + this.pattern);
      if (this.testCreation()) {
         this.testMatch();
      }

   }

   boolean testCreation() {
      try {
         this.regexp = new RE();
         this.regexp.setProgram(this.test.compiler.compile(this.pattern));
         if (this.badPattern) {
            this.test.fail(this.log, "Was expected to be an error, but wasn't.");
            return false;
         }

         return true;
      } catch (Exception var3) {
         if (this.badPattern) {
            this.log.append("   Match: ERR\n");
            this.success("Produces an error (" + var3.toString() + "), as expected.");
            return false;
         }

         String var2 = var3.getMessage() == null ? var3.toString() : var3.getMessage();
         this.test.fail(this.log, "Produces an unexpected exception \"" + var2 + "\"");
         var3.printStackTrace();
      } catch (Error var4) {
         this.test.fail(this.log, "Compiler threw fatal error \"" + var4.getMessage() + "\"");
         var4.printStackTrace();
      }

      return false;
   }

   private void testMatch() {
      this.log.append("   Match against: '" + this.toMatch + "'\n");

      try {
         boolean var1 = this.regexp.match(this.toMatch);
         this.log.append("   Matched: " + (var1 ? "YES" : "NO") + "\n");
         if (this.checkResult(var1) && (!this.shouldMatch || this.checkParens())) {
            this.log.append("   Match using StringCharacterIterator\n");
            if (!this.tryMatchUsingCI(new StringCharacterIterator(this.toMatch))) {
               return;
            }

            this.log.append("   Match using CharacterArrayCharacterIterator\n");
            if (!this.tryMatchUsingCI(new CharacterArrayCharacterIterator(this.toMatch.toCharArray(), 0, this.toMatch.length()))) {
               return;
            }

            this.log.append("   Match using StreamCharacterIterator\n");
            if (!this.tryMatchUsingCI(new StreamCharacterIterator(new StringBufferInputStream(this.toMatch)))) {
               return;
            }

            this.log.append("   Match using ReaderCharacterIterator\n");
            if (!this.tryMatchUsingCI(new ReaderCharacterIterator(new StringReader(this.toMatch)))) {
               return;
            }
         }
      } catch (Exception var3) {
         this.test.fail(this.log, "Matcher threw exception: " + var3.toString());
         var3.printStackTrace();
      } catch (Error var4) {
         this.test.fail(this.log, "Matcher threw fatal error \"" + var4.getMessage() + "\"");
         var4.printStackTrace();
      }

   }

   private boolean checkResult(boolean var1) {
      if (var1 == this.shouldMatch) {
         this.success((this.shouldMatch ? "Matched" : "Did not match") + " \"" + this.toMatch + "\", as expected:");
         return true;
      } else {
         if (this.shouldMatch) {
            this.test.fail(this.log, "Did not match \"" + this.toMatch + "\", when expected to.");
         } else {
            this.test.fail(this.log, "Matched \"" + this.toMatch + "\", when not expected to.");
         }

         return false;
      }
   }

   private boolean checkParens() {
      this.log.append("   Paren count: " + this.regexp.getParenCount() + "\n");
      if (!this.assertEquals(this.log, "Wrong number of parens", this.parens.length, this.regexp.getParenCount())) {
         return false;
      } else {
         for(int var1 = 0; var1 < this.regexp.getParenCount(); ++var1) {
            this.log.append("   Paren " + var1 + ": " + this.regexp.getParen(var1) + "\n");
            if ((!"null".equals(this.parens[var1]) || this.regexp.getParen(var1) != null) && !this.assertEquals(this.log, "Wrong register " + var1, this.parens[var1], this.regexp.getParen(var1))) {
               return false;
            }
         }

         return true;
      }
   }

   boolean tryMatchUsingCI(CharacterIterator var1) {
      try {
         boolean var2 = this.regexp.match((CharacterIterator)var1, 0);
         this.log.append("   Match: " + (var2 ? "YES" : "NO") + "\n");
         return this.checkResult(var2) && (!this.shouldMatch || this.checkParens());
      } catch (Exception var4) {
         this.test.fail(this.log, "Matcher threw exception: " + var4.toString());
         var4.printStackTrace();
      } catch (Error var5) {
         this.test.fail(this.log, "Matcher threw fatal error \"" + var5.getMessage() + "\"");
         var5.printStackTrace();
      }

      return false;
   }

   public boolean assertEquals(StringBuffer var1, String var2, String var3, String var4) {
      if ((var3 == null || var3.equals(var4)) && (var4 == null || var4.equals(var3))) {
         return true;
      } else {
         this.test.fail(var1, var2 + " (expected \"" + var3 + "\", actual \"" + var4 + "\")");
         return false;
      }
   }

   public boolean assertEquals(StringBuffer var1, String var2, int var3, int var4) {
      if (var3 != var4) {
         this.test.fail(var1, var2 + " (expected \"" + var3 + "\", actual \"" + var4 + "\")");
         return false;
      } else {
         return true;
      }
   }

   void success(String var1) {
   }
}
