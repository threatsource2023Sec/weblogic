package org.antlr.gunit.swingui.model;

public class TestCaseInputString implements ITestCaseInput {
   private String script;

   public TestCaseInputString(String text) {
      this.script = text;
   }

   public String toString() {
      return '"' + TestCase.convertPreservedChars(this.script) + '"';
   }

   public void setScript(String script) {
      this.script = script;
   }

   public String getScript() {
      return this.script;
   }
}
