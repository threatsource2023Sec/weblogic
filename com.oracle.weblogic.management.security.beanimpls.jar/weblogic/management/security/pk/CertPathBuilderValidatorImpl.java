package weblogic.management.security.pk;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;

public class CertPathBuilderValidatorImpl extends CertPathBuilderImpl {
   private CertPathValidatorImpl validatorImpl;

   public CertPathBuilderValidatorImpl(ModelMBean base) throws MBeanException {
      super(base);
      this.validatorImpl = new CertPathValidatorImpl(base);
   }

   protected CertPathBuilderValidatorImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
      this.validatorImpl = new CertPathValidatorImpl(base);
   }
}
