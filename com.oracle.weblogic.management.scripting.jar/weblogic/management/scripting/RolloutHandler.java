package weblogic.management.scripting;

import org.python.core.ArgParser;
import org.python.core.PyObject;
import weblogic.management.runtime.RolloutServiceRuntimeMBean;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;

public class RolloutHandler {
   WLScriptContext ctx = null;
   private WLSTMsgTextFormatter txtFmt;
   private static final String PARAM_NAME_TARGET = "target";
   private static final String PARAM_NAME_ROLLOUT_ORACLE_HOME = "rolloutOracleHome";
   private static final String PARAM_NAME_BACKUP_ORACLE_HOME = "backupOracleHome";
   private static final String PARAM_NAME_IS_ROLLBACK = "isRollback";
   private static final String PARAM_NAME_JAVA_HOME = "javaHome";
   private static final String PARAM_NAME_APPLICATION_PROPERTIES = "applicationProperties";
   private static final String PARAM_NAME_OPTIONS = "options";

   public RolloutHandler(WLScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   public Object rolloutUpdate(PyObject[] args, String[] kw) throws ScriptException {
      this.ctx.commandType = "rolloutUpdate";
      Object progressObject = null;

      try {
         String[] paramNames = new String[]{"target", "rolloutOracleHome", "backupOracleHome", "isRollback", "javaHome", "applicationProperties", "options"};
         ArgParser ap = new ArgParser("rolloutOracleHome", args, kw, paramNames);
         String targetArg = ap.getString(0);
         String rolloutOracleHomeArg = ap.getString(1);
         String backupOracleHomeArg = ap.getString(2);
         String isRollbackArg = ap.getString(3);
         String javaHomeArg = ap.getString(4);
         String applicationPropertiesArg = ap.getString(5);
         String optionsArg = ap.getString(6);
         this.ctx.printDebug("RolloutHandler.rolloutUpdate called on target: " + targetArg);
         this.ctx.printDebug("  rolloutOracleHome: " + rolloutOracleHomeArg);
         this.ctx.printDebug("  backupOracleHome: " + backupOracleHomeArg);
         this.ctx.printDebug("  isRollback: " + isRollbackArg);
         this.ctx.printDebug("  javaHome: " + javaHomeArg);
         this.ctx.printDebug("  applicationProperties: " + applicationPropertiesArg);
         this.ctx.printDebug("  options: " + optionsArg);
         RolloutServiceRuntimeMBean rolloutServiceRuntimeMBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().getRolloutService();
         progressObject = rolloutServiceRuntimeMBean.rolloutUpdate(targetArg, rolloutOracleHomeArg, backupOracleHomeArg, isRollbackArg, javaHomeArg, applicationPropertiesArg, optionsArg);
         this.ctx.printDebug("rolloutUpdate returned " + progressObject);
      } catch (Throwable var14) {
         this.ctx.throwWLSTException("Error doing rolloutUpdate " + var14.getMessage(), var14);
      }

      return progressObject;
   }

   public Object rolloutOracleHome(PyObject[] args, String[] kw) throws ScriptException {
      this.ctx.commandType = "rolloutOracleHome";
      Object progressObject = null;

      try {
         String[] paramNames = new String[]{"target", "rolloutOracleHome", "backupOracleHome", "isRollback", "options"};
         ArgParser ap = new ArgParser("rolloutOracleHome", args, kw, paramNames);
         String targetArg = ap.getString(0);
         String rolloutOracleHomeArg = ap.getString(1);
         String backupOracleHomeArg = ap.getString(2);
         String isRollbackArg = ap.getString(3);
         String optionsArg = ap.getString(4);
         this.ctx.printDebug("RolloutHandler.rolloutUpdate called on target: " + targetArg);
         this.ctx.printDebug("  rolloutOracleHome: " + rolloutOracleHomeArg);
         this.ctx.printDebug("  backupOracleHome: " + backupOracleHomeArg);
         this.ctx.printDebug("  isRollback: " + isRollbackArg);
         this.ctx.printDebug("  options: " + optionsArg);
         RolloutServiceRuntimeMBean rolloutServiceRuntimeMBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().getRolloutService();
         progressObject = rolloutServiceRuntimeMBean.rolloutOracleHome(targetArg, rolloutOracleHomeArg, backupOracleHomeArg, isRollbackArg, optionsArg);
         this.ctx.printDebug("rolloutOracleHome returned " + progressObject);
      } catch (Throwable var12) {
         this.ctx.throwWLSTException("Error doing rolloutOracleHome " + var12.getMessage(), var12);
      }

      return progressObject;
   }

   public Object rolloutJavaHome(PyObject[] args, String[] kw) throws ScriptException {
      this.ctx.commandType = "rolloutJavaHome";
      Object progressObject = null;

      try {
         String[] paramNames = new String[]{"target", "javaHome", "options"};
         ArgParser ap = new ArgParser("rolloutJavaHome", args, kw, paramNames);
         String targetArg = ap.getString(0);
         String javaHomeArg = ap.getString(1);
         String optionsArg = ap.getString(2);
         this.ctx.printDebug("RolloutHandler.rolloutJavaHome called on target: " + targetArg);
         this.ctx.printDebug("  javaHome: " + javaHomeArg);
         this.ctx.printDebug("  options: " + optionsArg);
         RolloutServiceRuntimeMBean rolloutServiceRuntimeMBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().getRolloutService();
         progressObject = rolloutServiceRuntimeMBean.rolloutJavaHome(targetArg, javaHomeArg, optionsArg);
         this.ctx.printDebug("rolloutJavaHome returned " + progressObject);
      } catch (Throwable var10) {
         this.ctx.throwWLSTException("Error doing rolloutJavaHome " + var10.getMessage(), var10);
      }

      return progressObject;
   }

   public Object rolloutApplications(PyObject[] args, String[] kw) throws ScriptException {
      this.ctx.commandType = "rolloutApplications";
      Object progressObject = null;

      try {
         String[] paramNames = new String[]{"target", "applicationProperties", "options"};
         ArgParser ap = new ArgParser("rolloutApplications", args, kw, paramNames);
         String targetArg = ap.getString(0);
         String applicationPropertiesArg = ap.getString(1);
         String optionsArg = ap.getString(2);
         this.ctx.printDebug("RolloutHandler.rolloutApplications called on target: " + targetArg);
         this.ctx.printDebug("  applicationProperties: " + applicationPropertiesArg);
         this.ctx.printDebug("  options: " + optionsArg);
         RolloutServiceRuntimeMBean rolloutServiceRuntimeMBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().getRolloutService();
         progressObject = rolloutServiceRuntimeMBean.rolloutApplications(targetArg, applicationPropertiesArg, optionsArg);
         this.ctx.printDebug("rolloutApplications returned " + progressObject);
      } catch (Throwable var10) {
         this.ctx.throwWLSTException("Error doing rolloutApplications " + var10.getMessage(), var10);
      }

      return progressObject;
   }

   public Object rollingRestart(PyObject[] args, String[] kw) throws ScriptException {
      this.ctx.commandType = "rollingRestart";
      Object progressObject = null;

      try {
         String[] paramNames = new String[]{"target", "options"};
         ArgParser ap = new ArgParser("rollingRestart", args, kw, paramNames);
         String targetArg = ap.getString(0);
         String optionsArg = ap.getString(1);
         this.ctx.printDebug("RolloutHandler.rollingRestart called on target: " + targetArg);
         this.ctx.printDebug("  options: " + optionsArg);
         RolloutServiceRuntimeMBean rolloutServiceRuntimeMBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().getRolloutService();
         progressObject = rolloutServiceRuntimeMBean.rollingRestart(targetArg, optionsArg);
         this.ctx.printDebug("rollingRestart returned " + progressObject);
      } catch (Throwable var9) {
         this.ctx.throwWLSTException("Error doing rollingRestart " + var9.getMessage(), var9);
      }

      return progressObject;
   }
}
