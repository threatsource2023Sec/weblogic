package weblogic.kodo;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.jdo.PersistenceManagerFactory;
import javax.persistence.EntityManagerFactory;
import kodo.jdbc.conf.descriptor.PersistenceConfigurationBean;
import kodo.jdbc.conf.descriptor.PersistenceUnitConfigurationBean;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.PersistenceUnitBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.persistence.BaseJPAIntegrationProvider;
import weblogic.persistence.BasePersistenceUnitInfo;
import weblogic.persistence.PersistenceDescriptorLoader;
import weblogic.persistence.spi.BeanInfo;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public class KodoIntegrationProvider extends BaseJPAIntegrationProvider {
   public BasePersistenceUnitInfo createPersistenceUnitInfo(PersistenceUnitBean persistenceXml, Object persistenceConfigXml, GenericClassLoader cl, String persistenceArchiveId, URL rootUrl, URL jarParentUrl, String originalVersion, ApplicationContextInternal appCtx) throws EnvironmentException {
      KodoPersistenceUnitInfo kpui = new KodoPersistenceUnitInfo(persistenceXml, (PersistenceUnitConfigurationBean)persistenceConfigXml, cl, persistenceArchiveId, rootUrl, jarParentUrl, originalVersion, appCtx);
      kpui.init();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(this + ": created persistence unit for id:" + persistenceArchiveId);
      }

      return kpui;
   }

   public InvocationHandler createJDOPersistenceContextHandler(String appName, String moduleName, String unitName, PersistenceUnitRegistry registry) {
      return new TransactionalPersistenceManagerProxyImpl(appName, moduleName, unitName, registry);
   }

   public Map getPersistenceUnitConfigsAsBeanInfo(DescriptorBean rootBean, URL rootUrl, URL jarParentUrl) throws EnvironmentException {
      PersistenceConfigurationBean cfg = (PersistenceConfigurationBean)rootBean;
      PersistenceUnitConfigurationBean[] puUnits = cfg.getPersistenceUnitConfigurations();
      Map beans = new HashMap();

      for(int i = 0; i < puUnits.length; ++i) {
         String name = puUnits[i].getName();
         BeanInfo dup = (BeanInfo)beans.get(name);
         if (dup != null) {
            throw new EnvironmentException("duplicate persistence units with name " + name + " found in persistence-config.xml");
         }

         BeanInfo info = new BeanInfo(puUnits[i], rootUrl, jarParentUrl, rootBean);
         beans.put(name, info);
      }

      return beans;
   }

   public PersistenceManagerFactory getJDOPersistenceManagerFactory(EntityManagerFactory emf) {
      return TransactionalPersistenceManagerProxyImpl.toPersistenceManagerFactory(emf);
   }

   public PersistenceDescriptorLoader getDescriptorLoader(VirtualJarFile vjf, URL resourceURL, File configDir, DeploymentPlanBean plan, String moduleName, String qualifiedURI) throws EnvironmentException {
      return vjf == null ? new PersistenceConfigDescriptorLoader(resourceURL, configDir, plan, moduleName, qualifiedURI) : new PersistenceConfigDescriptorLoader(vjf, configDir, plan, moduleName, qualifiedURI);
   }
}
