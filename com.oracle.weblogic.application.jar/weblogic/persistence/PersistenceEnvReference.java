package weblogic.persistence;

import java.util.Collection;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContextType;
import weblogic.application.naming.EnvReference;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.Environment;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.PersistenceUnitRegistryProvider;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;

public class PersistenceEnvReference extends EnvReference {
   private transient PersistenceUnitRefBean purBean;
   private transient PersistenceContextRefBean pcrBean;
   private transient PersistenceUnitRegistryProvider provider;

   public PersistenceEnvReference(Environment env, String factory, PersistenceContextRefBean bean, PersistenceUnitRegistryProvider prov, ClassLoader cl) throws EnvironmentException {
      super(env, getPersistenceManagerType(bean, cl), factory);
      this.setClassloader(cl);
      this.pcrBean = bean;
      this.provider = prov;
   }

   public PersistenceEnvReference(Environment env, String factory, PersistenceUnitRefBean bean, PersistenceUnitRegistryProvider prov, ClassLoader cl) throws EnvironmentException {
      super(env, getPersistenceManagerFactoryType(bean, cl), factory);
      this.setClassloader(cl);
      this.purBean = bean;
      this.provider = prov;
   }

   public static String getPersistenceManagerFactoryType(PersistenceUnitRefBean bean, ClassLoader cl) throws EnvironmentException {
      String type = EntityManagerFactory.class.getName();
      if (EnvUtils.isJDOPersistenceContextFactory(bean, bean.getInjectionTargets(), (Collection)null, cl)) {
         type = PersistenceManagerFactory.class.getName();
      }

      return type;
   }

   public static String getPersistenceManagerType(PersistenceContextRefBean bean, ClassLoader loader) throws EnvironmentException {
      List ifaces = EnvUtils.computeInterfaces(bean.getInjectionTargets(), loader);
      String type = EntityManager.class.getName();
      if (PersistenceContextType.valueOf(bean.getPersistenceContextType().toUpperCase()) == PersistenceContextType.TRANSACTION && EnvUtils.isJDOPersistenceContext(bean.getPersistenceContextRefName(), bean.getInjectionTargets(), ifaces, loader)) {
         type = PersistenceManager.class.getName();
      }

      return type;
   }

   public PersistenceUnitRefBean getPersistenceUnitRefBean() {
      return this.purBean;
   }

   public PersistenceContextRefBean getPersistenceContextRefBean() {
      return this.pcrBean;
   }

   public PersistenceUnitRegistryProvider getProvider() {
      return this.provider;
   }
}
