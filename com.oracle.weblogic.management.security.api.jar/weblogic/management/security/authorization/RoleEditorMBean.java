package weblogic.management.security.authorization;

import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.NotFoundException;
import weblogic.management.utils.RemoveException;

public interface RoleEditorMBean extends RoleReaderMBean {
   void createRole(String var1, String var2, String var3) throws AlreadyExistsException, CreateException;

   void removeRole(String var1, String var2) throws NotFoundException, RemoveException;

   void setRoleExpression(String var1, String var2, String var3) throws NotFoundException, CreateException;
}
