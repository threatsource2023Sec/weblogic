package weblogic.management.security.authorization;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;

public class DeployableAuthorizerImpl extends AuthorizerImpl {
   public DeployableAuthorizerImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected DeployableAuthorizerImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
