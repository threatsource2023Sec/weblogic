package weblogic.management.security.authentication;

import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface MemberGroupListerMBean extends GroupReaderMBean {
   String listMemberGroups(String var1) throws NotFoundException, InvalidParameterException;
}
