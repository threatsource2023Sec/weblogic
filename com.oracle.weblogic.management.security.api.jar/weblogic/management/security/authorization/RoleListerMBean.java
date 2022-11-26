package weblogic.management.security.authorization;

import java.util.Properties;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;
import weblogic.management.utils.PropertiesListerMBean;

public interface RoleListerMBean extends PropertiesListerMBean {
   String listAllRoles(int var1) throws NotFoundException;

   String listRolesByResourceType(String var1, int var2) throws NotFoundException, InvalidParameterException;

   String listRolesByApplication(String var1, int var2) throws NotFoundException, InvalidParameterException;

   String listRolesByComponent(String var1, String var2, String var3, int var4) throws NotFoundException, InvalidParameterException;

   String listRoles(String var1, int var2) throws NotFoundException, InvalidParameterException;

   String listChildRoles(String var1, int var2) throws NotFoundException, InvalidParameterException;

   String listRepeatingActionsRoles(String var1, int var2) throws NotFoundException, InvalidParameterException;

   Properties getRole(String var1, String var2) throws InvalidParameterException;

   Properties getRoleScopedByResource(String var1, String var2) throws InvalidParameterException;
}
