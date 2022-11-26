package weblogic.management.security.credentials;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;

public class DeployableCredentialMapperImpl extends CredentialMapperImpl {
   public DeployableCredentialMapperImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected DeployableCredentialMapperImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
