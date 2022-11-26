package weblogic.management.security.authentication;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface UserRemoverMBean extends StandardInterface, DescriptorBean {
   void removeUser(String var1) throws NotFoundException, InvalidParameterException;
}
