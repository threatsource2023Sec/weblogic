package weblogic.nodemanager.util;

import java.io.InputStream;

public class ScriptExecResult {
   private int scriptResponseCode;
   private InputStream scriptOutput;

   public ScriptExecResult(int scriptResponseCode, InputStream scriptOutput) {
      this.scriptResponseCode = scriptResponseCode;
      this.scriptOutput = scriptOutput;
   }

   public int getScriptResponseCode() {
      return this.scriptResponseCode;
   }

   public InputStream getScriptOutput() {
      return this.scriptOutput;
   }
}
