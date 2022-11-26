package weblogic.diagnostics.accessor;

public final class AccessorEnvironment {
   private AccessorConfigurationProvider configurationProvider;
   private AccessorSecurityProvider securityProvider;
   private AccessorMBeanFactory mbeanFactory;

   public AccessorEnvironment(AccessorConfigurationProvider configurationProvider) {
      this(configurationProvider, (AccessorSecurityProvider)null, (AccessorMBeanFactory)null);
   }

   public AccessorEnvironment(AccessorConfigurationProvider configurationProvider, AccessorSecurityProvider securityProvider) {
      this(configurationProvider, securityProvider, (AccessorMBeanFactory)null);
   }

   public AccessorEnvironment(AccessorConfigurationProvider configurationProvider, AccessorSecurityProvider securityProvider, AccessorMBeanFactory mbeanFactory) {
      this.configurationProvider = configurationProvider;
      this.securityProvider = securityProvider;
      this.mbeanFactory = mbeanFactory;
   }

   public AccessorConfigurationProvider getConfigurationProvider() {
      return this.configurationProvider;
   }

   public AccessorSecurityProvider getSecurityProvider() {
      return this.securityProvider;
   }

   public AccessorMBeanFactory getAccessorMBeanFactory() {
      return this.mbeanFactory;
   }
}
