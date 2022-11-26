package weblogic.management.security.authentication;

import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface GroupUserListerMBean extends GroupReaderMBean {
   String[] listAllUsersInGroup(String var1, String var2, int var3) throws NotFoundException, InvalidParameterException;
}
