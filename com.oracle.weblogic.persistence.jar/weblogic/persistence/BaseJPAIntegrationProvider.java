package weblogic.persistence;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.net.URL;
import java.util.Map;
import javax.jdo.PersistenceManagerFactory;
import javax.persistence.EntityManagerFactory;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.PersistenceUnitBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.persistence.spi.JPAIntegrationProvider;
import weblogic.persistence.spi.JPAIntegrationProviderFactory;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public class BaseJPAIntegrationProvider implements JPAIntegrationProvider {
   protected static final DebugLogger DEBUG;

   public BasePersistenceUnitInfo createPersistenceUnitInfo(PersistenceUnitBean persistenceXml, Object persistenceConfigXml, GenericClassLoader cl, String persistenceArchiveId, URL rootUrl, URL jarParentUrl, String originalVersion, ApplicationContextInternal appCtx) throws EnvironmentException {
      if (persistenceConfigXml != null) {
         J2EELogger.logWarningPersistenceUnitConfigurationIgnored(persistenceXml.getName(), persistenceArchiveId);
      }

      String providerClass = persistenceXml.getProvider();
      BasePersistenceUnitInfo bpui = new BasePersistenceUnitInfo(JPAIntegrationProviderFactory.providerClassToType(providerClass), persistenceXml, persistenceArchiveId, rootUrl, cl, jarParentUrl, originalVersion, appCtx);
      bpui.init();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(this + ": created persistence unit for id:" + persistenceArchiveId);
      }

      return bpui;
   }

   public InvocationHandler createJDOPersistenceContextHandler(String appName, String moduleName, String unitName, PersistenceUnitRegistry registry) {
      throw new UnsupportedOperationException("JDO persistence context detected. Configured JPA provider cannot handle it.");
   }

   public Map getPersistenceUnitConfigsAsBeanInfo(DescriptorBean rootBean, URL rootUrl, URL jarParentUrl) throws EnvironmentException {
      return null;
   }

   public PersistenceManagerFactory getJDOPersistenceManagerFactory(EntityManagerFactory emf) {
      throw new UnsupportedOperationException("The JPA provider cannot convert JDO persistence manager factory");
   }

   public PersistenceDescriptorLoader getDescriptorLoader(VirtualJarFile vjf, URL resourceURL, File configDir, DeploymentPlanBean plan, String moduleName, String qualifiedURI) throws EnvironmentException {
      PersistenceDescriptorLoader loader = null;
      if (vjf == null) {
         loader = new PersistenceDescriptorLoader(resourceURL, configDir, plan, moduleName, qualifiedURI);
      } else {
         loader = new PersistenceDescriptorLoader(vjf, configDir, plan, moduleName, qualifiedURI);
      }

      if (qualifiedURI.endsWith("META-INF/persistence-configuration.xml") && this.descriptorExists(loader)) {
         J2EELogger.logWarningPersistenceConfigurationFileIgnored(moduleName, "META-INF/persistence-configuration.xml");
      }

      return !qualifiedURI.endsWith("META-INF/persistence.xml") ? null : loader;
   }

   private boolean descriptorExists(PersistenceDescriptorLoader loader) {
      try {
         return loader.loadDescriptorBean() != null;
      } catch (Throwable var3) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug(this + ": unexpected exception in descriptorExists", var3);
         }

         return false;
      }
   }

   static {
      DEBUG = JPAIntegrationProviderFactory.DEBUG;
   }
}
