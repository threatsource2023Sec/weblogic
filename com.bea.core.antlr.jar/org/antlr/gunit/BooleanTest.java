package org.antlr.gunit;

public class BooleanTest extends AbstractTest {
   private boolean ok;

   public BooleanTest(boolean ok) {
      this.ok = ok;
   }

   public String getText() {
      return this.ok ? "OK" : "FAIL";
   }

   public int getType() {
      return this.ok ? 16 : 10;
   }

   public String getResult(gUnitTestResult testResult) {
      if (testResult.isLexerTest()) {
         if (testResult.isSuccess()) {
            return "OK";
         } else {
            this.hasErrorMsg = true;
            return testResult.getError();
         }
      } else {
         return testResult.isSuccess() ? "OK" : "FAIL";
      }
   }

   public String getExpected() {
      return this.ok ? "OK" : "FAIL";
   }
}
