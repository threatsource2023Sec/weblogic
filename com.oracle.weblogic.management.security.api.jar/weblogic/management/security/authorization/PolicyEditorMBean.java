package weblogic.management.security.authorization;

import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.NotFoundException;
import weblogic.management.utils.RemoveException;

public interface PolicyEditorMBean extends PolicyReaderMBean {
   void setPolicyExpression(String var1, String var2) throws NotFoundException, CreateException;

   void createPolicy(String var1, String var2) throws AlreadyExistsException, CreateException;

   void removePolicy(String var1) throws NotFoundException, RemoveException;
}
