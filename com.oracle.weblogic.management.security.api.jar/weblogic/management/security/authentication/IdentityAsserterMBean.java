package weblogic.management.security.authentication;

import javax.management.InvalidAttributeValueException;

public interface IdentityAsserterMBean extends AuthenticationProviderMBean {
   String[] getSupportedTypes();

   String[] getActiveTypes();

   void setActiveTypes(String[] var1) throws InvalidAttributeValueException;

   boolean getBase64DecodingRequired();

   void setBase64DecodingRequired(boolean var1) throws InvalidAttributeValueException;
}
