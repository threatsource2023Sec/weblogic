package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.AuthenticatorMBean;

public interface LoginExceptionPropagatorMBean extends StandardInterface, DescriptorBean, AuthenticatorMBean {
   boolean getPropagateCauseForLoginException();

   void setPropagateCauseForLoginException(boolean var1) throws InvalidAttributeValueException;

   String getName();
}
