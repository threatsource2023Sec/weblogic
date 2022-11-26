package org.antlr.gunit;

import org.antlr.runtime.Token;

public class OutputTest extends AbstractTest {
   private final Token token;

   public OutputTest(Token token) {
      this.token = token;
   }

   public String getText() {
      return this.token.getText();
   }

   public int getType() {
      return this.token.getType();
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
      return this.token.getText();
   }
}
