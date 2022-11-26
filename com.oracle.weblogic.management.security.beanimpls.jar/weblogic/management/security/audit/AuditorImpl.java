package weblogic.management.security.audit;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.management.security.ProviderImpl;

public class AuditorImpl extends ProviderImpl {
   public AuditorImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected AuditorImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
