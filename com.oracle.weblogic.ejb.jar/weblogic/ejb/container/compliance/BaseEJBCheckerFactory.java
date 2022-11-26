package weblogic.ejb.container.compliance;

import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;

public class BaseEJBCheckerFactory implements EJBCheckerFactory {
   protected final DeploymentInfo di;
   protected final BeanInfo bi;

   public BaseEJBCheckerFactory(DeploymentInfo di, BeanInfo bi) {
      this.di = di;
      this.bi = bi;
   }

   public Object[] getBeanInfoCheckers() {
      return null;
   }

   public Class[] getDeploymentInfoCheckerClasses() {
      return new Class[]{WeblogicJarChecker.class, ClientJarChecker.class, SecurityRoleChecker.class, EnvironmentValuesChecker.class, ContainerTransactionChecker.class};
   }

   public Object getInterceptorChecker() {
      return this.bi.isEJB30() ? new InterceptorChecker(this.di) : null;
   }

   public Object[] getRelationShipCheckers() {
      return null;
   }
}
