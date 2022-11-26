package weblogic.persistence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.application.naming.PersistenceUnitRegistryProvider;
import weblogic.j2ee.descriptor.InjectionTargetBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.persistence.spi.JPAIntegrationProviderFactory;

public class PersistenceManagerObjectFactory implements ObjectFactory {
   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws EnvironmentException {
      if (!(obj instanceof PersistenceEnvReference)) {
         throw new AssertionError("object factory should have been referenced only from EnvReference");
      } else {
         PersistenceEnvReference ref = (PersistenceEnvReference)obj;
         return createPersistenceContextProxy(ref.getPersistenceContextRefBean(), ref.getClassloader(), ref.getProvider(), ref.getEnvironment().getApplicationName(), ref.getEnvironment().getModuleId());
      }
   }

   public static Object createPersistenceContextProxy(PersistenceContextRefBean pcb, ClassLoader cl, PersistenceUnitRegistryProvider regProvider, String appName, String moduleName) throws EnvironmentException {
      return createPersistenceContextProxy(pcb.getPersistenceContextRefName(), pcb.getPersistenceUnitName(), pcb.getPersistenceContextType(), pcb.getSynchronizationType(), pcb.getInjectionTargets(), cl, regProvider, appName, moduleName);
   }

   public static Object createPersistenceContextProxy(PersistenceContext persistenceContext, InjectionTargetBean[] injectionTargets, ClassLoader cl, PersistenceUnitRegistryProvider regProvider, String appName, String moduleName) throws EnvironmentException {
      return createPersistenceContextProxy(persistenceContext.name(), persistenceContext.unitName(), persistenceContext.type().toString(), persistenceContext.synchronization().toString(), injectionTargets, cl, regProvider, appName, moduleName);
   }

   public static Object createPersistenceContextProxy(String pcRefName, String puName, String pcType, String syncType, InjectionTargetBean[] injectionTargets, ClassLoader cl, PersistenceUnitRegistryProvider regProvider, String appName, String moduleName) throws EnvironmentException {
      List ifaces = EnvUtils.computeInterfaces(injectionTargets, cl);
      PersistenceUnitRegistry puReg = regProvider.getPersistenceUnitRegistry();
      puName = EnvUtils.getPersistenceUnitName(puName, puReg, injectionTargets);
      InvocationHandler invHandler;
      if (PersistenceContextType.valueOf(pcType.toUpperCase()) == PersistenceContextType.TRANSACTION) {
         if (EnvUtils.isJDOPersistenceContext(pcRefName, injectionTargets, ifaces, cl)) {
            BasePersistenceUnitInfo pui = (BasePersistenceUnitInfo)puReg.getPersistenceUnit(puName);
            invHandler = JPAIntegrationProviderFactory.getProvider(pui.getType()).createJDOPersistenceContextHandler(appName, moduleName, puName, puReg);
         } else {
            invHandler = EntityManagerInvocationHandlerFactory.createTransactionalEntityManagerInvocationHandler(appName, moduleName, puName, puReg, SynchronizationType.valueOf(syncType.toUpperCase()));
         }
      } else {
         invHandler = EntityManagerInvocationHandlerFactory.createExtendedEntityManagerInvocationHandler(appName, moduleName, puName, puReg);
      }

      return Proxy.newProxyInstance(cl, (Class[])((Class[])ifaces.toArray(new Class[0])), invHandler);
   }
}
