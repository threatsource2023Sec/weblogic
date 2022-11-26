package org.antlr.gunit.swingui.model;

public class TestCaseOutputReturn implements ITestCaseOutput {
   private String script;

   public TestCaseOutputReturn(String text) {
      this.script = text;
   }

   public String toString() {
      return String.format(" returns [%s]", this.script);
   }

   public void setScript(String script) {
      this.script = script;
   }

   public String getScript() {
      return this.script;
   }
}
