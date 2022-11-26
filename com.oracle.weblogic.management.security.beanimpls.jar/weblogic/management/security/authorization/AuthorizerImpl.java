package weblogic.management.security.authorization;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.management.security.ProviderImpl;

public class AuthorizerImpl extends ProviderImpl {
   public AuthorizerImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected AuthorizerImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
