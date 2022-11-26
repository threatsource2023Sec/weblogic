package weblogic.ejb.container.compliance.mdb;

import weblogic.ejb.container.compliance.BaseEJBCheckerFactory;
import weblogic.ejb.container.compliance.Ejb30MessageDrivenBeanClassChecker;
import weblogic.ejb.container.compliance.MessageDrivenBeanClassChecker;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;

public class MessageDrivenBeanCheckerFactory extends BaseEJBCheckerFactory {
   public MessageDrivenBeanCheckerFactory(DeploymentInfo di, BeanInfo bi) {
      super(di, bi);
   }

   public Object[] getBeanInfoCheckers() {
      return this.bi.isEJB30() ? new Object[]{new Ejb30MessageDrivenBeanClassChecker(this.bi)} : new Object[]{new MessageDrivenBeanClassChecker(this.bi)};
   }
}
