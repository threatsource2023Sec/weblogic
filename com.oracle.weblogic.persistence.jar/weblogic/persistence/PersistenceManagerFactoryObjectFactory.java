package weblogic.persistence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import javax.persistence.EntityManagerFactory;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.application.naming.PersistenceUnitRegistryProvider;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.persistence.spi.JPAIntegrationProvider;
import weblogic.persistence.spi.JPAIntegrationProviderFactory;

public class PersistenceManagerFactoryObjectFactory implements ObjectFactory {
   private static final String MSG = "WLS client does not support server-defined persistent-unit-reference(s).";

   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws EnvironmentException {
      if (!(obj instanceof PersistenceEnvReference)) {
         throw new AssertionError("Object factory should have been referenced only from EnvReference");
      } else {
         PersistenceEnvReference ref = (PersistenceEnvReference)obj;
         return ref.getPersistenceUnitRefBean() == null && ref.getEnvironment() == null && ref.getProvider() == null && ref.getClassloader() == null ? this.createEntityManagerFactoryProxy() : getPersistenceContextFactory(ref.getPersistenceUnitRefBean(), ref.getProvider(), ref.getClassloader(), ref.getEnvironment().getApplicationName(), ref.getEnvironment().getModuleId());
      }
   }

   private EntityManagerFactory createEntityManagerFactoryProxy() {
      InvocationHandler invocationHandler = new InvocationHandler() {
         public Object invoke(Object proxy, Method method, Object[] args) {
            if ((args == null || args.length == 0) && method.getName().equals("toString")) {
               return super.toString() + "WLS client does not support server-defined persistent-unit-reference(s).";
            } else {
               throw new RuntimeException("WLS client does not support server-defined persistent-unit-reference(s).");
            }
         }
      };
      return (EntityManagerFactory)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{EntityManagerFactory.class}, invocationHandler);
   }

   public static Object getPersistenceContextFactory(PersistenceUnitRefBean prb, PersistenceUnitRegistryProvider regProvider, ClassLoader cl, String appName, String moduleName) throws EnvironmentException {
      PersistenceUnitRegistry puReg = regProvider.getPersistenceUnitRegistry();
      String name = EnvUtils.getPersistenceUnitName(prb.getPersistenceUnitName(), puReg, prb.getInjectionTargets());
      BasePersistenceUnitInfo puInfo = (BasePersistenceUnitInfo)puReg.getPersistenceUnit(name);
      EntityManagerFactory emf = puInfo.getEntityManagerFactory();
      EntityManagerFactoryProxyImpl proxyImpl = (EntityManagerFactoryProxyImpl)Proxy.getInvocationHandler(emf);
      proxyImpl.setAppName(appName);
      proxyImpl.setModuleName(moduleName);
      if (EnvUtils.isJDOPersistenceContextFactory(prb, prb.getInjectionTargets(), (Collection)null, cl)) {
         JPAIntegrationProvider provider = JPAIntegrationProviderFactory.getProvider(puInfo.getType());
         return provider.getJDOPersistenceManagerFactory(emf);
      } else {
         return emf;
      }
   }
}
