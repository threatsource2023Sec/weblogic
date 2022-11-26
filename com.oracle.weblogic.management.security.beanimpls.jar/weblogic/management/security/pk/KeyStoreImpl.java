package weblogic.management.security.pk;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.management.security.ProviderImpl;

public class KeyStoreImpl extends ProviderImpl {
   public KeyStoreImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected KeyStoreImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
