package org.antlr.gunit.swingui.model;

public class TestCaseOutputStdOut implements ITestCaseOutput {
   private String script;

   public TestCaseOutputStdOut(String text) {
      this.script = text;
   }

   public String toString() {
      return String.format(" -> \"%s\"", this.script);
   }

   public void setScript(String script) {
      this.script = script;
   }

   public String getScript() {
      return this.script;
   }
}
