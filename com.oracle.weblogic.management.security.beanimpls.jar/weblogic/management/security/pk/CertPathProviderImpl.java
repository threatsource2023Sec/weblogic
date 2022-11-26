package weblogic.management.security.pk;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.management.security.ProviderImpl;

public class CertPathProviderImpl extends ProviderImpl {
   public CertPathProviderImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected CertPathProviderImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
