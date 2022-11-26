package weblogic.management.security.authorization;

import javax.management.MBeanException;
import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface RoleAuxiliaryMBean extends RoleEditorMBean {
   void createRole(String var1, String var2, String var3, String var4) throws AlreadyExistsException, CreateException;

   void setRoleAuxiliary(String var1, String var2, String var3) throws AlreadyExistsException, CreateException, MBeanException;

   String getRoleAuxiliary(String var1, String var2) throws NotFoundException, MBeanException;

   void exportResource(String var1, String var2) throws InvalidParameterException, ErrorCollectionException;

   String[] listAllRolesAndURIs(String var1, String var2);

   String[][] getRoleNames(String var1);
}
