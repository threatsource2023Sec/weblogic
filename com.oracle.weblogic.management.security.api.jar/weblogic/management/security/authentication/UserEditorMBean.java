package weblogic.management.security.authentication;

import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface UserEditorMBean extends UserReaderMBean, UserPasswordEditorMBean, UserRemoverMBean {
   void createUser(String var1, String var2, String var3) throws InvalidParameterException, AlreadyExistsException;

   void setUserDescription(String var1, String var2) throws NotFoundException, InvalidParameterException;
}
