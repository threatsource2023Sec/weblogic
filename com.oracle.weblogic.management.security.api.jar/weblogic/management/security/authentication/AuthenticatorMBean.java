package weblogic.management.security.authentication;

import javax.management.InvalidAttributeValueException;

public interface AuthenticatorMBean extends AuthenticationProviderMBean {
   String getControlFlag();

   void setControlFlag(String var1) throws InvalidAttributeValueException;
}
