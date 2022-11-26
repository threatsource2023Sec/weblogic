package org.antlr.gunit.swingui.model;

public class TestCaseInputFile implements ITestCaseInput {
   private String fileName;

   public TestCaseInputFile(String file) {
      this.fileName = file;
   }

   public String getLabel() {
      return "FILE:" + this.fileName;
   }

   public void setScript(String script) {
      this.fileName = script;
   }

   public String toString() {
      return this.fileName;
   }

   public String getScript() {
      return this.fileName;
   }
}
