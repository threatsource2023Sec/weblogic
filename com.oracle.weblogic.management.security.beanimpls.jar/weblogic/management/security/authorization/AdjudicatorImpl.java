package weblogic.management.security.authorization;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.management.security.ProviderImpl;

public class AdjudicatorImpl extends ProviderImpl {
   public AdjudicatorImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected AdjudicatorImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
