package weblogic.management.configuration;

import weblogic.diagnostics.debug.DebugScopeBean;

public interface DebugScopeMBean extends ConfigurationMBean, DebugScopeBean {
   void setName(String var1);

   String getName();

   boolean isEnabled();

   void setEnabled(boolean var1);
}
