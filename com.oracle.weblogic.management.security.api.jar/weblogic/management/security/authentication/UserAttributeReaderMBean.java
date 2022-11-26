package weblogic.management.security.authentication;

import javax.management.openmbean.OpenType;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface UserAttributeReaderMBean extends StandardInterface, DescriptorBean {
   String[] getSupportedUserAttributeNames();

   boolean isUserAttributeNameSupported(String var1) throws InvalidParameterException;

   OpenType getSupportedUserAttributeType(String var1) throws InvalidParameterException;

   Object getUserAttributeValue(String var1, String var2) throws NotFoundException, InvalidParameterException;
}
