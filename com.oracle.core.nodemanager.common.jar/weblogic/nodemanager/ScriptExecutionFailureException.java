package weblogic.nodemanager;

import java.io.IOException;

public class ScriptExecutionFailureException extends IOException {
   public static final int SCRIPT_TIMEOUT_EXIT_CODE = -101;
   public static final int SCRIPT_USAGE_ERROR_EXIT_CODE = -100;
   public final String scriptPath;
   public final int exitCode;

   public ScriptExecutionFailureException(String scriptPath, int exitCode) {
      super(NodeManagerCommonTextFormatter.getInstance().getScriptExecutionFailure(scriptPath, exitCode));
      this.scriptPath = scriptPath;
      this.exitCode = exitCode;
   }

   public String getScriptPath() {
      return this.scriptPath;
   }

   public int getExitCode() {
      return this.exitCode;
   }
}
