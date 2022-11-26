package weblogic.management.security.authentication;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;

public class AuthenticatorImpl extends AuthenticationProviderImpl {
   public AuthenticatorImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected AuthenticatorImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
