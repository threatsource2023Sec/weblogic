package weblogic.management.security.authentication;

import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NameListerMBean;
import weblogic.management.utils.NotFoundException;

public interface UserReaderMBean extends NameListerMBean {
   String listUsers(String var1, int var2) throws InvalidParameterException;

   boolean userExists(String var1) throws InvalidParameterException;

   String getUserDescription(String var1) throws NotFoundException, InvalidParameterException;
}
