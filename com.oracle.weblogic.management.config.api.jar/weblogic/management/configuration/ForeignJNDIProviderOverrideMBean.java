package weblogic.management.configuration;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;

public interface ForeignJNDIProviderOverrideMBean extends ConfigurationMBean {
   String getInitialContextFactory();

   void setInitialContextFactory(String var1) throws InvalidAttributeValueException;

   String getProviderURL();

   void setProviderURL(String var1) throws InvalidAttributeValueException;

   String getPassword();

   void setPassword(String var1) throws InvalidAttributeValueException;

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1) throws InvalidAttributeValueException;

   String getUser();

   void setUser(String var1) throws InvalidAttributeValueException;

   void setProperties(Properties var1) throws InvalidAttributeValueException;

   Properties getProperties();

   ForeignJNDILinkOverrideMBean[] getForeignJNDILinks();

   ForeignJNDILinkOverrideMBean lookupForeignJNDILink(String var1);

   ForeignJNDILinkOverrideMBean createForeignJNDILink(String var1);

   void destroyForeignJNDILink(ForeignJNDILinkOverrideMBean var1);
}
