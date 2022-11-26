package org.apache.regexp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class RETest {
   static final boolean showSuccesses = false;
   static final String NEW_LINE = System.getProperty("line.separator");
   REDebugCompiler compiler = new REDebugCompiler();
   int testCount = 0;
   int failures = 0;

   public static void main(String[] var0) {
      try {
         if (!test(var0)) {
            System.exit(1);
         }
      } catch (Exception var2) {
         var2.printStackTrace();
         System.exit(1);
      }

   }

   public static boolean test(String[] var0) throws Exception {
      RETest var1 = new RETest();
      if (var0.length == 2) {
         var1.runInteractiveTests(var0[1]);
      } else if (var0.length == 1) {
         var1.runAutomatedTests(var0[0]);
      } else {
         System.out.println("Usage: RETest ([-i] [regex]) ([/path/to/testfile.txt])");
         System.out.println("By Default will run automated tests from file 'docs/RETest.txt' ...");
         System.out.println();
         var1.runAutomatedTests("docs/RETest.txt");
      }

      return var1.failures == 0;
   }

   void runInteractiveTests(String var1) {
      RE var2 = new RE();

      try {
         var2.setProgram(this.compiler.compile(var1));
         this.say("" + NEW_LINE + "" + var1 + "" + NEW_LINE + "");
         PrintWriter var3 = new PrintWriter(System.out);
         this.compiler.dumpProgram(var3);
         var3.flush();
         boolean var4 = true;

         while(var4) {
            BufferedReader var5 = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("> ");
            System.out.flush();
            String var6 = var5.readLine();
            if (var6 != null) {
               if (var2.match(var6)) {
                  this.say("Match successful.");
               } else {
                  this.say("Match failed.");
               }

               this.showParens(var2);
            } else {
               var4 = false;
               System.out.println();
            }
         }
      } catch (Exception var7) {
         this.say("Error: " + var7.toString());
         var7.printStackTrace();
      }

   }

   void die(String var1) {
      this.say("FATAL ERROR: " + var1);
      System.exit(-1);
   }

   void fail(StringBuffer var1, String var2) {
      System.out.print(var1.toString());
      this.fail(var2);
   }

   void fail(String var1) {
      ++this.failures;
      this.say("" + NEW_LINE + "");
      this.say("*******************************************************");
      this.say("*********************  FAILURE!  **********************");
      this.say("*******************************************************");
      this.say("" + NEW_LINE + "");
      this.say(var1);
      this.say("");
      if (this.compiler != null) {
         PrintWriter var2 = new PrintWriter(System.out);
         this.compiler.dumpProgram(var2);
         var2.flush();
         this.say("" + NEW_LINE + "");
      }

   }

   void say(String var1) {
      System.out.println(var1);
   }

   void showParens(RE var1) {
      for(int var2 = 0; var2 < var1.getParenCount(); ++var2) {
         this.say("$" + var2 + " = " + var1.getParen(var2));
      }

   }

   void runAutomatedTests(String var1) throws Exception {
      long var2 = System.currentTimeMillis();
      this.testPrecompiledRE();
      this.testSplitAndGrep();
      this.testSubst();
      this.testOther();
      File var4 = new File(var1);
      if (!var4.exists()) {
         throw new Exception("Could not find: " + var1);
      } else {
         BufferedReader var5 = new BufferedReader(new FileReader(var4));

         try {
            while(var5.ready()) {
               RETestCase var6 = this.getNextTestCase(var5);
               if (var6 != null) {
                  var6.runTest();
               }
            }
         } finally {
            var5.close();
         }

         this.say(NEW_LINE + NEW_LINE + "Match time = " + (System.currentTimeMillis() - var2) + " ms.");
         if (this.failures > 0) {
            this.say("*************** THERE ARE FAILURES! *******************");
         }

         this.say("Tests complete.  " + this.testCount + " tests, " + this.failures + " failure(s).");
      }
   }

   void testOther() throws Exception {
      RE var1 = new RE("(a*)b");
      this.say("Serialized/deserialized (a*)b");
      ByteArrayOutputStream var2 = new ByteArrayOutputStream(128);
      (new ObjectOutputStream(var2)).writeObject(var1);
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2.toByteArray());
      var1 = (RE)(new ObjectInputStream(var3)).readObject();
      if (!var1.match("aaab")) {
         this.fail("Did not match 'aaab' with deserialized RE.");
      } else {
         this.say("aaaab = true");
         this.showParens(var1);
      }

      var2.reset();
      this.say("Deserialized (a*)b");
      (new ObjectOutputStream(var2)).writeObject(var1);
      var3 = new ByteArrayInputStream(var2.toByteArray());
      var1 = (RE)(new ObjectInputStream(var3)).readObject();
      if (var1.getParenCount() != 0) {
         this.fail("Has parens after deserialization.");
      }

      if (!var1.match("aaab")) {
         this.fail("Did not match 'aaab' with deserialized RE.");
      } else {
         this.say("aaaab = true");
         this.showParens(var1);
      }

      var1 = new RE("abc(\\w*)");
      this.say("MATCH_CASEINDEPENDENT abc(\\w*)");
      var1.setMatchFlags(1);
      this.say("abc(d*)");
      if (!var1.match("abcddd")) {
         this.fail("Did not match 'abcddd'.");
      } else {
         this.say("abcddd = true");
         this.showParens(var1);
      }

      if (!var1.match("aBcDDdd")) {
         this.fail("Did not match 'aBcDDdd'.");
      } else {
         this.say("aBcDDdd = true");
         this.showParens(var1);
      }

      if (!var1.match("ABCDDDDD")) {
         this.fail("Did not match 'ABCDDDDD'.");
      } else {
         this.say("ABCDDDDD = true");
         this.showParens(var1);
      }

      var1 = new RE("(A*)b\\1");
      var1.setMatchFlags(1);
      if (!var1.match("AaAaaaBAAAAAA")) {
         this.fail("Did not match 'AaAaaaBAAAAAA'.");
      } else {
         this.say("AaAaaaBAAAAAA = true");
         this.showParens(var1);
      }

      var1 = new RE("[A-Z]*");
      var1.setMatchFlags(1);
      if (!var1.match("CaBgDe12")) {
         this.fail("Did not match 'CaBgDe12'.");
      } else {
         this.say("CaBgDe12 = true");
         this.showParens(var1);
      }

      var1 = new RE("^abc$");
      if (var1.match("\nabc")) {
         this.fail("\"\\nabc\" matches \"^abc$\"");
      }

      var1 = new RE("^abc$", 2);
      if (!var1.match("\nabc")) {
         this.fail("\"\\nabc\" doesn't match \"^abc$\"");
      }

      if (!var1.match("\rabc")) {
         this.fail("\"\\rabc\" doesn't match \"^abc$\"");
      }

      if (!var1.match("\r\nabc")) {
         this.fail("\"\\r\\nabc\" doesn't match \"^abc$\"");
      }

      if (!var1.match("\u0085abc")) {
         this.fail("\"\\u0085abc\" doesn't match \"^abc$\"");
      }

      if (!var1.match("\u2028abc")) {
         this.fail("\"\\u2028abc\" doesn't match \"^abc$\"");
      }

      if (!var1.match("\u2029abc")) {
         this.fail("\"\\u2029abc\" doesn't match \"^abc$\"");
      }

      var1 = new RE("^a.*b$", 2);
      if (var1.match("a\nb")) {
         this.fail("\"a\\nb\" matches \"^a.*b$\"");
      }

      if (var1.match("a\rb")) {
         this.fail("\"a\\rb\" matches \"^a.*b$\"");
      }

      if (var1.match("a\r\nb")) {
         this.fail("\"a\\r\\nb\" matches \"^a.*b$\"");
      }

      if (var1.match("a\u0085b")) {
         this.fail("\"a\\u0085b\" matches \"^a.*b$\"");
      }

      if (var1.match("a\u2028b")) {
         this.fail("\"a\\u2028b\" matches \"^a.*b$\"");
      }

      if (var1.match("a\u2029b")) {
         this.fail("\"a\\u2029b\" matches \"^a.*b$\"");
      }

   }

   private void testPrecompiledRE() {
      char[] var1 = new char[]{'|', '\u0000', '\u001a', '|', '\u0000', '\r', 'A', '\u0001', '\u0004', 'a', '|', '\u0000', '\u0003', 'G', '\u0000', '\ufff6', '|', '\u0000', '\u0003', 'N', '\u0000', '\u0003', 'A', '\u0001', '\u0004', 'b', 'E', '\u0000', '\u0000'};
      REProgram var2 = new REProgram(var1);
      RE var3 = new RE(var2);
      this.say("a*b");
      boolean var4 = var3.match("aaab");
      this.say("aaab = " + var4);
      this.showParens(var3);
      if (!var4) {
         this.fail("\"aaab\" doesn't match to precompiled \"a*b\"");
      }

      var4 = var3.match("b");
      this.say("b = " + var4);
      this.showParens(var3);
      if (!var4) {
         this.fail("\"b\" doesn't match to precompiled \"a*b\"");
      }

      var4 = var3.match("c");
      this.say("c = " + var4);
      this.showParens(var3);
      if (var4) {
         this.fail("\"c\" matches to precompiled \"a*b\"");
      }

      var4 = var3.match("ccccaaaaab");
      this.say("ccccaaaaab = " + var4);
      this.showParens(var3);
      if (!var4) {
         this.fail("\"ccccaaaaab\" doesn't match to precompiled \"a*b\"");
      }

   }

   private void testSplitAndGrep() {
      String[] var1 = new String[]{"xxxx", "xxxx", "yyyy", "zzz"};
      RE var2 = new RE("a*b");
      String[] var3 = var2.split("xxxxaabxxxxbyyyyaaabzzz");

      for(int var4 = 0; var4 < var1.length && var4 < var3.length; ++var4) {
         this.assertEquals("Wrong splitted part", var1[var4], var3[var4]);
      }

      this.assertEquals("Wrong number of splitted parts", var1.length, var3.length);
      var2 = new RE("x+");
      var1 = new String[]{"xxxx", "xxxx"};
      var3 = var2.grep(var3);

      for(int var5 = 0; var5 < var3.length; ++var5) {
         this.say("s[" + var5 + "] = " + var3[var5]);
         this.assertEquals("Grep fails", var1[var5], var3[var5]);
      }

      this.assertEquals("Wrong number of string found by grep", var1.length, var3.length);
   }

   private void testSubst() {
      RE var1 = new RE("a*b");
      String var2 = "-foo-garply-wacky-";
      String var3 = var1.subst("aaaabfooaaabgarplyaaabwackyb", "-");
      this.assertEquals("Wrong result of substitution in \"a*b\"", var2, var3);
      var1 = new RE("http://[\\.\\w\\-\\?/~_@&=%]+");
      var3 = var1.subst("visit us: http://www.apache.org!", "1234<a href=\"$0\">$0</a>", 2);
      this.assertEquals("Wrong subst() result", "visit us: 1234<a href=\"http://www.apache.org\">http://www.apache.org</a>!", var3);
      var1 = new RE("(.*?)=(.*)");
      var3 = var1.subst("variable=value", "$1_test_$212", 2);
      this.assertEquals("Wrong subst() result", "variable_test_value12", var3);
      var1 = new RE("^a$");
      var3 = var1.subst("a", "b", 2);
      this.assertEquals("Wrong subst() result", "b", var3);
      var1 = new RE("^a$", 2);
      var3 = var1.subst("\r\na\r\n", "b", 2);
      this.assertEquals("Wrong subst() result", "\r\nb\r\n", var3);
      var1 = new RE("fo(o)");
      var3 = var1.subst("foo", "$1", 2);
      this.assertEquals("Wrong subst() result", "o", var3);
   }

   public void assertEquals(String var1, String var2, String var3) {
      if (var2 != null && !var2.equals(var3) || var3 != null && !var3.equals(var2)) {
         this.fail(var1 + " (expected \"" + var2 + "\", actual \"" + var3 + "\")");
      }

   }

   public void assertEquals(String var1, int var2, int var3) {
      if (var2 != var3) {
         this.fail(var1 + " (expected \"" + var2 + "\", actual \"" + var3 + "\")");
      }

   }

   private boolean getExpectedResult(String var1) {
      if ("NO".equals(var1)) {
         return false;
      } else if ("YES".equals(var1)) {
         return true;
      } else {
         this.die("Test script error!");
         return false;
      }
   }

   private String findNextTest(BufferedReader var1) throws IOException {
      String var2 = "";

      while(var1.ready()) {
         var2 = var1.readLine();
         if (var2 == null) {
            break;
         }

         var2 = var2.trim();
         if (var2.startsWith("#")) {
            break;
         }

         if (!var2.equals("")) {
            this.say("Script error.  Line = " + var2);
            System.exit(-1);
         }
      }

      return var2;
   }

   private RETestCase getNextTestCase(BufferedReader var1) throws IOException {
      String var2 = this.findNextTest(var1);
      if (!var1.ready()) {
         return null;
      } else {
         String var3 = var1.readLine();
         String var4 = var1.readLine();
         boolean var5 = "ERR".equals(var4);
         boolean var6 = false;
         boolean var7 = false;
         String[] var8 = null;
         if (!var5) {
            var6 = this.getExpectedResult(var1.readLine().trim());
            if (var6) {
               int var10 = Integer.parseInt(var1.readLine().trim());
               var8 = new String[var10];

               for(int var9 = 0; var9 < var10; ++var9) {
                  var8[var9] = var1.readLine();
               }
            }
         }

         return new RETestCase(this, var2, var3, var4, var5, var6, var8);
      }
   }
}
