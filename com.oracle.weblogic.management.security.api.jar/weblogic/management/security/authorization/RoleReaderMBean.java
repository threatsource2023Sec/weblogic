package weblogic.management.security.authorization;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.NotFoundException;

public interface RoleReaderMBean extends StandardInterface, DescriptorBean {
   boolean roleExists(String var1, String var2);

   String getRoleExpression(String var1, String var2) throws NotFoundException;

   String[] listRolesForResource(String var1);
}
