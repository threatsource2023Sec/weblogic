package org.antlr.gunit.swingui.model;

public class TestCase {
   private ITestCaseInput input;
   private ITestCaseOutput output;
   private boolean pass;

   public boolean isPass() {
      return this.pass;
   }

   public void setPass(boolean value) {
      this.pass = value;
   }

   public ITestCaseInput getInput() {
      return this.input;
   }

   public ITestCaseOutput getOutput() {
      return this.output;
   }

   public TestCase(ITestCaseInput input, ITestCaseOutput output) {
      this.input = input;
      this.output = output;
   }

   public String toString() {
      return String.format("[%s]->[%s]", this.input.getScript(), this.output.getScript());
   }

   public void setInput(ITestCaseInput in) {
      this.input = in;
   }

   public void setOutput(ITestCaseOutput out) {
      this.output = out;
   }

   public static String convertPreservedChars(String input) {
      return input;
   }
}
