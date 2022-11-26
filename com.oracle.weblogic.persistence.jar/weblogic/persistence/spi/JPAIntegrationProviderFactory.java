package weblogic.persistence.spi;

import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.PersistenceUnitBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JPAMBean;
import weblogic.management.provider.ManagementService;
import weblogic.persistence.BaseJPAIntegrationProvider;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JPAIntegrationProviderFactory {
   public static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugJPA");
   public static final String KODO_PROVIDER_CLASS = "org.apache.openjpa.persistence.PersistenceProviderImpl";
   public static final String KODO_PROVIDER2_CLASS = "kodo.persistence.PersistenceProviderImpl";
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final JPAIntegrationProvider JPA_INTEGRATION_PROVIDER = new BaseJPAIntegrationProvider();
   private static final JPAIntegrationProvider KODO_INTEGRATION_PROVIDER = loadProvider("weblogic.kodo.KodoIntegrationProvider");

   private static final JPAIntegrationProvider loadProvider(String clz) {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if (loader == null) {
         loader = JPAIntegrationProviderFactory.class.getClassLoader();
      }

      try {
         Class c = loader.loadClass(clz);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Loaded JPA integration provider: " + c);
         }

         return (JPAIntegrationProvider)c.newInstance();
      } catch (Exception var3) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Error loading provider: " + clz, var3);
         }

         return null;
      }
   }

   public static String getDefaultJPAProviderClassName() {
      if (ManagementService.isRuntimeAccessInitialized()) {
         DomainMBean dm = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
         JPAMBean jpaMBean = dm.getJPA();
         if (jpaMBean != null) {
            return jpaMBean.getDefaultJPAProvider();
         }
      }

      return "org.eclipse.persistence.jpa.PersistenceProvider";
   }

   public static boolean isKodoProviderClass(String clz) {
      return "kodo.persistence.PersistenceProviderImpl".equals(clz) || "org.apache.openjpa.persistence.PersistenceProviderImpl".equals(clz);
   }

   public static boolean isToplinkProviderClass(String clz) {
      return "org.eclipse.persistence.jpa.PersistenceProvider".equals(clz);
   }

   public static boolean isHibernateProviderClass(String clz) {
      return clz != null && clz.contains("hibernate");
   }

   public static JPAIntegrationProvider.Type providerClassToType(String clz) {
      if (clz != null) {
         clz = clz.trim();
      }

      if (clz == null || clz.length() == 0) {
         clz = getDefaultJPAProviderClassName();
      }

      if (isKodoProviderClass(clz)) {
         return JPAIntegrationProvider.Type.KODO;
      } else if (isHibernateProviderClass(clz)) {
         return JPAIntegrationProvider.Type.HYBERNATE;
      } else {
         return isToplinkProviderClass(clz) ? JPAIntegrationProvider.Type.TOPLINK : JPAIntegrationProvider.Type.OTHER;
      }
   }

   private static JPAIntegrationProvider type2provider(JPAIntegrationProvider.Type type) {
      return type.equals(JPAIntegrationProvider.Type.KODO) && KODO_INTEGRATION_PROVIDER != null ? KODO_INTEGRATION_PROVIDER : JPA_INTEGRATION_PROVIDER;
   }

   public static JPAIntegrationProvider getProvider(PersistenceUnitBean persistenceXml) {
      String clz = null;
      if (persistenceXml != null) {
         clz = persistenceXml.getProvider();
      }

      if (clz == null || clz.trim().length() == 0) {
         clz = getDefaultJPAProviderClassName();
      }

      return getProvider(providerClassToType(clz));
   }

   public static JPAIntegrationProvider getProvider(JPAIntegrationProvider.Type type) {
      return type2provider(type);
   }

   public static JPAIntegrationProvider getDefaultJPAIntegrationProvider() {
      String dpClassName = getDefaultJPAProviderClassName();
      return isKodoProviderClass(dpClassName) ? KODO_INTEGRATION_PROVIDER : JPA_INTEGRATION_PROVIDER;
   }

   public static JPAIntegrationProvider getKodoJPAIntegrationProvider() {
      return KODO_INTEGRATION_PROVIDER;
   }
}
