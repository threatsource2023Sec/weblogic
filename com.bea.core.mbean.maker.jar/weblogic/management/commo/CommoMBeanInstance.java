package weblogic.management.commo;

import javax.management.JMException;
import javax.management.MBeanException;

public abstract class CommoMBeanInstance extends BaseModelMBean {
   public CommoMBeanInstance(CommoModelMBeanInfoSupport info) throws JMException {
      super(info);
   }

   CommoMBeanInstance() throws MBeanException {
   }

   public boolean isType() throws MBeanException {
      return false;
   }
}
