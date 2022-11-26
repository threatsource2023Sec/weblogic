package weblogic.ejb.container.compliance.session;

import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import weblogic.ejb.container.compliance.BaseEJBCheckerFactory;
import weblogic.ejb.container.compliance.BusinessLocalInterfaceChecker;
import weblogic.ejb.container.compliance.BusinessRemoteInterfaceChecker;
import weblogic.ejb.container.compliance.EJBObjectClassChecker;
import weblogic.ejb.container.compliance.Ejb30SessionBeanClassChecker;
import weblogic.ejb.container.compliance.SessionBeanClassChecker;
import weblogic.ejb.container.compliance.SessionHomeInterfaceChecker;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;

public class SessionBeanCheckerFactory extends BaseEJBCheckerFactory {
   public SessionBeanCheckerFactory(DeploymentInfo di, BeanInfo bi) {
      super(di, bi);
   }

   public Object[] getBeanInfoCheckers() {
      Collection col = new ArrayList();
      SessionBeanInfo cbi = (SessionBeanInfo)this.bi;
      if (cbi.isEJB30()) {
         col.add(new Ejb30SessionBeanClassChecker(cbi));
      } else {
         col.add(new SessionBeanClassChecker(cbi));
      }

      if (cbi.hasDeclaredRemoteHome()) {
         col.add(new SessionHomeInterfaceChecker(cbi.getHomeInterfaceClass(), cbi.getRemoteInterfaceClass(), cbi.getBeanClass(), cbi, EJBHome.class));
         col.add(new EJBObjectClassChecker(cbi.getRemoteInterfaceClass(), cbi, EJBObject.class));
      }

      if (cbi.hasDeclaredLocalHome()) {
         col.add(new SessionHomeInterfaceChecker(cbi.getLocalHomeInterfaceClass(), cbi.getLocalInterfaceClass(), cbi.getBeanClass(), cbi, EJBLocalHome.class));
         col.add(new EJBObjectClassChecker(cbi.getLocalInterfaceClass(), cbi, EJBLocalObject.class));
      }

      if (cbi.hasBusinessRemotes()) {
         col.add(new BusinessRemoteInterfaceChecker(cbi));
      }

      if (cbi.hasBusinessLocals()) {
         col.add(new BusinessLocalInterfaceChecker(cbi));
      }

      return col.toArray();
   }
}
