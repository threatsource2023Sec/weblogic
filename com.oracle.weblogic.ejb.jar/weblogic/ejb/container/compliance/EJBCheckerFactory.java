package weblogic.ejb.container.compliance;

public interface EJBCheckerFactory {
   Object[] getBeanInfoCheckers();

   Class[] getDeploymentInfoCheckerClasses();

   Object getInterceptorChecker();

   Object[] getRelationShipCheckers();
}
