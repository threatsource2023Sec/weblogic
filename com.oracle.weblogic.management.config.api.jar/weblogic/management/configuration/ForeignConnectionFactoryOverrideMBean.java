package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface ForeignConnectionFactoryOverrideMBean extends ForeignDestinationOverrideMBean {
   String getUsername();

   void setUsername(String var1) throws InvalidAttributeValueException;

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1);

   String getPassword();

   void setPassword(String var1) throws InvalidAttributeValueException;
}
