package weblogic.management.security.authentication;

import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface UserAttributeEditorMBean extends UserAttributeReaderMBean {
   void setUserAttributeValue(String var1, String var2, Object var3) throws NotFoundException, InvalidParameterException;
}
