package weblogic.ejb.container.deployer;

import weblogic.descriptor.DescriptorBean;
import weblogic.ejb.container.interfaces.SessionBeanInfo;

public class SingletonSessionBeanUpdateListener extends BaseBeanUpdateListener {
   protected SingletonSessionBeanUpdateListener(SessionBeanInfo sbi) {
      super(sbi);
   }

   protected void handleProperyChange(String propertyName, DescriptorBean newBean) {
      throw new AssertionError("Unexpected propertyName: " + propertyName);
   }
}
