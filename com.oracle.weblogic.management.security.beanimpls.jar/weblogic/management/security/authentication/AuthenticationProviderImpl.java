package weblogic.management.security.authentication;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.management.security.ProviderImpl;

public class AuthenticationProviderImpl extends ProviderImpl {
   public AuthenticationProviderImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected AuthenticationProviderImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
