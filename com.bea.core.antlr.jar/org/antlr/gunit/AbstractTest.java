package org.antlr.gunit;

public abstract class AbstractTest implements ITestCase {
   protected String header;
   protected String actual;
   protected boolean hasErrorMsg;
   private String testedRuleName;
   private int testCaseIndex;

   public abstract int getType();

   public abstract String getText();

   public abstract String getExpected();

   public String getExpectedResult() {
      String expected = this.getExpected();
      if (expected != null) {
         expected = JUnitCodeGen.escapeForJava(expected);
      }

      return expected;
   }

   public abstract String getResult(gUnitTestResult var1);

   public String getHeader() {
      return this.header;
   }

   public String getActual() {
      return this.actual;
   }

   public String getActualResult() {
      String actual = this.getActual();
      if (actual != null && !this.hasErrorMsg) {
         actual = JUnitCodeGen.escapeForJava(actual);
      }

      return actual;
   }

   public String getTestedRuleName() {
      return this.testedRuleName;
   }

   public int getTestCaseIndex() {
      return this.testCaseIndex;
   }

   public void setHeader(String rule, String lexicalRule, String treeRule, int numOfTest, int line, String input) {
      StringBuffer buf = new StringBuffer();
      buf.append("test" + numOfTest + " (");
      if (treeRule != null) {
         buf.append(treeRule + " walks ");
      }

      if (lexicalRule != null) {
         buf.append(lexicalRule + ", line" + line + ")" + " - ");
      } else {
         buf.append(rule + ", line" + line + ")" + " - ");
      }

      buf.append("\"");
      buf.append(input);
      buf.append("\"");
      this.header = buf.toString();
   }

   public void setActual(String actual) {
      this.actual = actual;
   }

   public void setTestedRuleName(String testedRuleName) {
      this.testedRuleName = testedRuleName;
   }

   public void setTestCaseIndex(int testCaseIndex) {
      this.testCaseIndex = testCaseIndex;
   }
}
