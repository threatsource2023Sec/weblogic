package weblogic.management.security.pk;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;

public class CertPathBuilderImpl extends CertPathProviderImpl {
   public CertPathBuilderImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected CertPathBuilderImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
