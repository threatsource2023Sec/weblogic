package weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBeanAdapter;
import javax.validation.constraints.NotNull;
import weblogic.diagnostics.descriptor.WLDFLogActionBean;

public class LogActionConfig extends ActionConfigBeanAdapter {
   private @NotNull String subSystemName;
   private @NotNull String logLevel;
   private @NotNull String message;

   public LogActionConfig() {
      super("LogAction");
   }

   public LogActionConfig(WLDFLogActionBean actionBean) {
      this();
      this.setName(actionBean.getName());
      this.subSystemName = actionBean.getSubsystemName();
      this.logLevel = actionBean.getSeverity();
      this.message = actionBean.getMessage();
   }

   public String getSubSystemName() {
      return this.subSystemName;
   }

   public void setSubSystemName(String subSystemName) {
      this.subSystemName = subSystemName;
   }

   public String getLogLevel() {
      return this.logLevel;
   }

   public void setLogLevel(String logLevel) {
      this.logLevel = logLevel;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
