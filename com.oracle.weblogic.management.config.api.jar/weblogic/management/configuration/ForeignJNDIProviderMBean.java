package weblogic.management.configuration;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;

public interface ForeignJNDIProviderMBean extends DeploymentMBean {
   String getName();

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

   ForeignJNDILinkMBean[] getForeignJNDILinks();

   ForeignJNDILinkMBean lookupForeignJNDILink(String var1);

   ForeignJNDILinkMBean createForeignJNDILink(String var1);

   void addForeignJNDILink(ForeignJNDILinkMBean var1);

   void destroyForeignJNDILink(ForeignJNDILinkMBean var1);

   void setProperties(Properties var1) throws InvalidAttributeValueException;

   Properties getProperties();
}
