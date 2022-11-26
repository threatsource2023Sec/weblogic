package weblogic.management.security.authorization;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;

public class DeployableRoleMapperImpl extends RoleMapperImpl {
   public DeployableRoleMapperImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected DeployableRoleMapperImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
