package weblogic.management.security.authentication;

import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NameListerMBean;
import weblogic.management.utils.NotFoundException;

public interface GroupReaderMBean extends NameListerMBean {
   String listGroups(String var1, int var2) throws InvalidParameterException;

   boolean groupExists(String var1) throws InvalidParameterException;

   String getGroupDescription(String var1) throws NotFoundException, InvalidParameterException;

   boolean isMember(String var1, String var2, boolean var3) throws NotFoundException, InvalidParameterException;
}
