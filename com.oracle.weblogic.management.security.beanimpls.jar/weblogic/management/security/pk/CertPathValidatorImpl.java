package weblogic.management.security.pk;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;

public class CertPathValidatorImpl extends CertPathProviderImpl {
   public CertPathValidatorImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected CertPathValidatorImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
