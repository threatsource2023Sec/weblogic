package weblogic.management.security.authentication;

import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface GroupEditorMBean extends GroupReaderMBean, GroupRemoverMBean {
   void createGroup(String var1, String var2) throws AlreadyExistsException, InvalidParameterException;

   void setGroupDescription(String var1, String var2) throws NotFoundException, InvalidParameterException;

   void addMemberToGroup(String var1, String var2) throws NotFoundException, InvalidParameterException;

   void removeMemberFromGroup(String var1, String var2) throws NotFoundException, InvalidParameterException;
}
