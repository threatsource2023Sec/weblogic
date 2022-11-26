package weblogic.management.security.authentication;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface GroupRemoverMBean extends StandardInterface, DescriptorBean {
   void removeGroup(String var1) throws NotFoundException, InvalidParameterException;
}
