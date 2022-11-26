package org.antlr.gunit.swingui.model;

public class TestCaseOutputResult implements ITestCaseOutput {
   public static String OK = "OK";
   public static String FAIL = "FAIL";
   private boolean success;

   public TestCaseOutputResult(boolean result) {
      this.success = result;
   }

   public String toString() {
      return this.getScript();
   }

   public String getScript() {
      return this.success ? OK : FAIL;
   }

   public void setScript(boolean value) {
      this.success = value;
   }

   public void setScript(String script) {
      this.success = Boolean.parseBoolean(script);
   }
}
