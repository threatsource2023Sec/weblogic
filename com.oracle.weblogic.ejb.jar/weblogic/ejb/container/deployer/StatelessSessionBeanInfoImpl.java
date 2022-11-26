package weblogic.ejb.container.deployer;

import java.lang.reflect.Method;
import java.util.Locale;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.internal.StatelessEJBHomeImpl;
import weblogic.ejb.container.internal.StatelessEJBLocalHomeImpl;
import weblogic.ejb.container.manager.StatelessManager;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.utils.classloaders.GenericClassLoader;

class StatelessSessionBeanInfoImpl extends SessionBeanInfoImpl {
   public StatelessSessionBeanInfoImpl(DeploymentInfo di, CompositeDescriptor desc, GenericClassLoader cl) throws ClassNotFoundException, WLDeploymentException {
      super(di, desc, cl);
   }

   protected BaseBeanUpdateListener getBeanUpdateListener() {
      return new StatelessSessionBeanUpdateListener(this);
   }

   protected String getSyntheticHomeImplClassName() {
      return StatelessEJBHomeImpl.class.getName();
   }

   protected String getSyntheticLocalHomeImplClassName() {
      return StatelessEJBLocalHomeImpl.class.getName();
   }

   public BeanManager getBeanManagerInstance(EJBRuntimeHolder mbean) {
      return new StatelessManager(mbean);
   }

   public String getEjbCreateInitMethodName(Method m) {
      String name = m.getName();
      return "ejb" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
   }
}
