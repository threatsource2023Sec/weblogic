package weblogic.management.commo;

import javax.management.JMException;

public class RequiredModelMBeanWrapper extends CommoMBeanInstance implements CommoMBean {
   protected AbstractCommoConfigurationBean delegate = null;

   public RequiredModelMBeanWrapper(AbstractCommoConfigurationBean realMBean) throws JMException {
      this.delegate = realMBean;
   }

   public StandardInterface getProxy() {
      return this.delegate;
   }

   public String getObjectName() {
      return this.delegate.wls_getObjectName().toString();
   }
}
