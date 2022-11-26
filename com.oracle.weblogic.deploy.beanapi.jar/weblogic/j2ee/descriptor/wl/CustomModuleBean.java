package weblogic.j2ee.descriptor.wl;

public interface CustomModuleBean {
   String getUri();

   void setUri(String var1);

   String getProviderName();

   void setProviderName(String var1);

   ConfigurationSupportBean getConfigurationSupport();

   ConfigurationSupportBean createConfigurationSupport();

   void destroyConfigurationSupport(ConfigurationSupportBean var1);
}
