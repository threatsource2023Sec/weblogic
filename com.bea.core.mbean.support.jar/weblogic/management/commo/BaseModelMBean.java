package weblogic.management.commo;

import javax.management.MBeanException;
import javax.management.MBeanRegistration;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.RequiredModelMBean;

public abstract class BaseModelMBean extends RequiredModelMBean implements CommoMBean, MBeanRegistration {
   public BaseModelMBean() throws MBeanException, RuntimeOperationsException {
   }

   public BaseModelMBean(ModelMBeanInfo mbi) throws MBeanException, RuntimeOperationsException {
   }
}
