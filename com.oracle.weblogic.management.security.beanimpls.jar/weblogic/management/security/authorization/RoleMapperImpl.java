package weblogic.management.security.authorization;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.management.security.ProviderImpl;

public class RoleMapperImpl extends ProviderImpl {
   public RoleMapperImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected RoleMapperImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
