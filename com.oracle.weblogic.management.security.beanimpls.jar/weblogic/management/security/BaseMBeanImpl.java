package weblogic.management.security;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.management.commo.RequiredModelMBeanWrapper;
import weblogic.management.commo.StandardInterface;

public class BaseMBeanImpl {
   private ModelMBean requiredModelMBean = null;

   public BaseMBeanImpl(ModelMBean base) {
      this.requiredModelMBean = base;
   }

   protected BaseMBeanImpl(RequiredModelMBean base) {
      this.requiredModelMBean = base;
   }

   protected ModelMBean getRequiredModelMBean() {
      return this.requiredModelMBean;
   }

   protected StandardInterface getProxy() throws MBeanException {
      StandardInterface si = null;
      if (this.requiredModelMBean instanceof RequiredModelMBeanWrapper) {
         si = ((RequiredModelMBeanWrapper)this.requiredModelMBean).getProxy();
         return si;
      } else {
         throw new AssertionError("GetProxy on BaseMBeanImpl called with old requiredModelMBean");
      }
   }
}
