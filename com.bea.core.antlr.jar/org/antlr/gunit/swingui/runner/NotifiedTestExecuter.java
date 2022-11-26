package org.antlr.gunit.swingui.runner;

import org.antlr.gunit.GrammarInfo;
import org.antlr.gunit.ITestCase;
import org.antlr.gunit.gUnitExecutor;
import org.antlr.gunit.swingui.model.Rule;
import org.antlr.gunit.swingui.model.TestCase;
import org.antlr.gunit.swingui.model.TestSuite;

public class NotifiedTestExecuter extends gUnitExecutor {
   private TestSuite testSuite;

   public NotifiedTestExecuter(GrammarInfo grammarInfo, ClassLoader loader, String testsuiteDir, TestSuite suite) {
      super(grammarInfo, loader, testsuiteDir);
      this.testSuite = suite;
   }

   public void onFail(ITestCase failTest) {
      if (failTest == null) {
         throw new IllegalArgumentException("Null fail test");
      } else {
         String ruleName = failTest.getTestedRuleName();
         if (ruleName == null) {
            throw new NullPointerException("Null rule name");
         } else {
            Rule rule = this.testSuite.getRule(ruleName);
            TestCase failCase = (TestCase)rule.getElementAt(failTest.getTestCaseIndex());
            failCase.setPass(false);
         }
      }
   }

   public void onPass(ITestCase passTest) {
      if (passTest == null) {
         throw new IllegalArgumentException("Null pass test");
      } else {
         String ruleName = passTest.getTestedRuleName();
         if (ruleName == null) {
            throw new NullPointerException("Null rule name");
         } else {
            Rule rule = this.testSuite.getRule(ruleName);
            TestCase passCase = (TestCase)rule.getElementAt(passTest.getTestCaseIndex());
            passCase.setPass(true);
         }
      }
   }
}
