package weblogic.management.configuration;

public interface DebugMBean extends ConfigurationMBean {
   DebugScopeMBean[] getDebugScopes();

   DebugScopeMBean createDebugScope(String var1);

   void destroyDebugScope(DebugScopeMBean var1);

   DebugScopeMBean lookupDebugScope(String var1);
}
