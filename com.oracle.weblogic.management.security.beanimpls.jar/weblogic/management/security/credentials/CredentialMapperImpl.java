package weblogic.management.security.credentials;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.management.security.ProviderImpl;

public class CredentialMapperImpl extends ProviderImpl {
   public CredentialMapperImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected CredentialMapperImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
