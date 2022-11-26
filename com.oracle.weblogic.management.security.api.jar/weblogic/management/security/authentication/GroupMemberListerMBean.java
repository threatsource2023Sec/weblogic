package weblogic.management.security.authentication;

import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface GroupMemberListerMBean extends GroupReaderMBean {
   String listGroupMembers(String var1, String var2, int var3) throws NotFoundException, InvalidParameterException;
}
