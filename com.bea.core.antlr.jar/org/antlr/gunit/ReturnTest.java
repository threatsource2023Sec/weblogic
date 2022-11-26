package org.antlr.gunit;

import org.antlr.runtime.Token;

public class ReturnTest extends AbstractTest {
   private final Token retval;

   public ReturnTest(Token retval) {
      this.retval = retval;
   }

   public String getText() {
      return this.retval.getText();
   }

   public int getType() {
      return this.retval.getType();
   }

   public String getResult(gUnitTestResult testResult) {
      if (testResult.isSuccess()) {
         return testResult.getReturned();
      } else {
         this.hasErrorMsg = true;
         return testResult.getError();
      }
   }

   public String getExpected() {
      String expect = this.retval.getText();
      if (expect.charAt(0) == '"' && expect.charAt(expect.length() - 1) == '"') {
         expect = expect.substring(1, expect.length() - 1);
      }

      return expect;
   }
}
