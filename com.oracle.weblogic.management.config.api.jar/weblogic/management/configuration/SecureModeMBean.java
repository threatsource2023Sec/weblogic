package weblogic.management.configuration;

public interface SecureModeMBean extends ConfigurationMBean {
   boolean isSecureModeEnabled();

   void setSecureModeEnabled(boolean var1);

   boolean isRestrictiveJMXPolicies();

   void setRestrictiveJMXPolicies(boolean var1);

   boolean isWarnOnInsecureSSL();

   void setWarnOnInsecureSSL(boolean var1);

   boolean isWarnOnInsecureFileSystem();

   void setWarnOnInsecureFileSystem(boolean var1);

   boolean isWarnOnAuditing();

   void setWarnOnAuditing(boolean var1);

   boolean isWarnOnInsecureApplications();

   void setWarnOnInsecureApplications(boolean var1);

   boolean isWarnOnJavaSecurityManager();

   void setWarnOnJavaSecurityManager(boolean var1);
}
