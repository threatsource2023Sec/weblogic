package org.antlr.gunit.swingui.model;

public class TestCaseOutputAST implements ITestCaseOutput {
   private String treeString;

   public TestCaseOutputAST(String script) {
      this.treeString = script;
   }

   public void setScript(String script) {
      this.treeString = script;
   }

   public String getScript() {
      return this.treeString;
   }

   public String toString() {
      return String.format(" -> %s", this.treeString);
   }
}
