package weblogic.management.configuration;

public interface JASPICMBean extends ConfigurationMBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   AuthConfigProviderMBean[] getAuthConfigProviders();

   AuthConfigProviderMBean lookupAuthConfigProvider(String var1);

   AuthConfigProviderMBean createAuthConfigProvider(String var1);

   void destroyAuthConfigProvider(AuthConfigProviderMBean var1);

   CustomAuthConfigProviderMBean createCustomAuthConfigProvider(String var1);

   WLSAuthConfigProviderMBean createWLSAuthConfigProvider(String var1);
}
