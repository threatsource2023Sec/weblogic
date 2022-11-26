package org.antlr.gunit;

public class gUnitTestResult {
   private boolean success;
   private String output;
   private String error;
   private String returned;
   private boolean isLexerTest;

   public gUnitTestResult(boolean success, String output) {
      this.success = success;
      this.output = output;
   }

   public gUnitTestResult(boolean success, String output, boolean isLexerTest) {
      this(success, output);
      this.isLexerTest = isLexerTest;
   }

   public gUnitTestResult(boolean success, String output, String returned) {
      this(success, output);
      this.returned = returned;
   }

   public boolean isSuccess() {
      return this.success;
   }

   public String getOutput() {
      return this.output;
   }

   public String getError() {
      return this.error;
   }

   public String getReturned() {
      return this.returned;
   }

   public boolean isLexerTest() {
      return this.isLexerTest;
   }

   public void setError(String error) {
      this.error = error;
   }
}
