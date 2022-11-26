package org.antlr.gunit.swingui.runner;

import org.antlr.gunit.swingui.model.ITestCaseInput;
import org.antlr.gunit.swingui.model.ITestCaseOutput;
import org.antlr.gunit.swingui.model.Rule;
import org.antlr.gunit.swingui.model.TestCase;
import org.antlr.gunit.swingui.model.TestCaseInputFile;
import org.antlr.gunit.swingui.model.TestCaseInputMultiString;
import org.antlr.gunit.swingui.model.TestCaseInputString;
import org.antlr.gunit.swingui.model.TestCaseOutputAST;
import org.antlr.gunit.swingui.model.TestCaseOutputResult;
import org.antlr.gunit.swingui.model.TestCaseOutputReturn;
import org.antlr.gunit.swingui.model.TestCaseOutputStdOut;
import org.antlr.gunit.swingui.model.TestSuite;

public class TestSuiteAdapter {
   private TestSuite model;
   private Rule currentRule;

   public TestSuiteAdapter(TestSuite testSuite) {
      this.model = testSuite;
   }

   public void setGrammarName(String name) {
      this.model.setGrammarName(name);
   }

   public void startRule(String name) {
      this.currentRule = new Rule(name);
   }

   public void endRule() {
      this.model.addRule(this.currentRule);
      this.currentRule = null;
   }

   public void addTestCase(ITestCaseInput in, ITestCaseOutput out) {
      TestCase testCase = new TestCase(in, out);
      this.currentRule.addTestCase(testCase);
   }

   private static String trimChars(String text, int numOfChars) {
      return text.substring(numOfChars, text.length() - numOfChars);
   }

   public static ITestCaseInput createFileInput(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException("null");
      } else {
         return new TestCaseInputFile(fileName);
      }
   }

   public static ITestCaseInput createStringInput(String line) {
      if (line == null) {
         throw new IllegalArgumentException("null");
      } else {
         return new TestCaseInputString(trimChars(line, 1));
      }
   }

   public static ITestCaseInput createMultiInput(String text) {
      if (text == null) {
         throw new IllegalArgumentException("null");
      } else {
         return new TestCaseInputMultiString(trimChars(text, 2));
      }
   }

   public static ITestCaseOutput createBoolOutput(boolean bool) {
      return new TestCaseOutputResult(bool);
   }

   public static ITestCaseOutput createAstOutput(String ast) {
      if (ast == null) {
         throw new IllegalArgumentException("null");
      } else {
         return new TestCaseOutputAST(ast);
      }
   }

   public static ITestCaseOutput createStdOutput(String text) {
      if (text == null) {
         throw new IllegalArgumentException("null");
      } else {
         return new TestCaseOutputStdOut(trimChars(text, 1));
      }
   }

   public static ITestCaseOutput createReturnOutput(String text) {
      if (text == null) {
         throw new IllegalArgumentException("null");
      } else {
         return new TestCaseOutputReturn(trimChars(text, 1));
      }
   }
}
