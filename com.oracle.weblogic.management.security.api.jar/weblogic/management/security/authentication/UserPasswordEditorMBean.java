package weblogic.management.security.authentication;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface UserPasswordEditorMBean extends StandardInterface, DescriptorBean {
   void changeUserPassword(String var1, String var2, String var3) throws NotFoundException, InvalidParameterException;

   void resetUserPassword(String var1, String var2) throws NotFoundException, InvalidParameterException;
}
