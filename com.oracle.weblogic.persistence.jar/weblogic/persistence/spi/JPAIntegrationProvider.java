package weblogic.persistence.spi;

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
import weblogic.j2ee.descriptor.PersistenceUnitBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.persistence.BasePersistenceUnitInfo;
import weblogic.persistence.PersistenceDescriptorLoader;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public interface JPAIntegrationProvider {
   String PERSISTENCE_RESOURCE_URI = "META-INF/persistence.xml";
   String PERSISTENCE_CONFIG_RESOURCE_URI = "META-INF/persistence-configuration.xml";

   BasePersistenceUnitInfo createPersistenceUnitInfo(PersistenceUnitBean var1, Object var2, GenericClassLoader var3, String var4, URL var5, URL var6, String var7, ApplicationContextInternal var8) throws EnvironmentException;

   InvocationHandler createJDOPersistenceContextHandler(String var1, String var2, String var3, PersistenceUnitRegistry var4);

   Map getPersistenceUnitConfigsAsBeanInfo(DescriptorBean var1, URL var2, URL var3) throws EnvironmentException;

   PersistenceManagerFactory getJDOPersistenceManagerFactory(EntityManagerFactory var1);

   PersistenceDescriptorLoader getDescriptorLoader(VirtualJarFile var1, URL var2, File var3, DeploymentPlanBean var4, String var5, String var6) throws EnvironmentException;

   public static enum Type {
      KODO,
      TOPLINK,
      HYBERNATE,
      OTHER;
   }
}
