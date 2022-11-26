package weblogic.management.configuration;

import java.util.HashSet;
import java.util.Hashtable;
import javax.management.InvalidAttributeValueException;

/** @deprecated */
@Deprecated
public interface ConnectorComponentMBean extends ComponentMBean {
   /** @deprecated */
   @Deprecated
   String getDescription();

   /** @deprecated */
   @Deprecated
   void setDescription(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getDisplayName();

   /** @deprecated */
   @Deprecated
   void setDisplayName(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getEisType();

   /** @deprecated */
   @Deprecated
   void setEisType(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getSmallIcon();

   /** @deprecated */
   @Deprecated
   void setSmallIcon(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getLargeIcon();

   /** @deprecated */
   @Deprecated
   void setLargeIcon(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getLicenseDescription();

   /** @deprecated */
   @Deprecated
   void setLicenseDescription(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean getLicenseRequired();

   /** @deprecated */
   @Deprecated
   void setLicenseRequired(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getSpecVersion();

   /** @deprecated */
   @Deprecated
   void setSpecVersion(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getVendorName();

   /** @deprecated */
   @Deprecated
   void setVendorName(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getVersion();

   /** @deprecated */
   @Deprecated
   void setVersion(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getConnectionFactoryImpl();

   /** @deprecated */
   @Deprecated
   void setConnectionFactoryImpl(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getConnectionFactoryInterface();

   /** @deprecated */
   @Deprecated
   void setConnectionFactoryInterface(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getConnectionImpl();

   /** @deprecated */
   @Deprecated
   void setConnectionImpl(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getConnectionInterface();

   /** @deprecated */
   @Deprecated
   void setConnectionInterface(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean getConnectionProfilingEnabled();

   /** @deprecated */
   @Deprecated
   void setConnectionProfilingEnabled(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getManagedConnectionFactoryClass();

   /** @deprecated */
   @Deprecated
   void setManagedConnectionFactoryClass(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean getreauthenticationSupport();

   /** @deprecated */
   @Deprecated
   void setreauthenticationSupport(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getTransactionSupport();

   /** @deprecated */
   @Deprecated
   void setTransactionSupport(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   Hashtable getConfigProperties();

   /** @deprecated */
   @Deprecated
   void setConfigProperties(Hashtable var1);

   /** @deprecated */
   @Deprecated
   HashSet getAuthenticationMechanisms();

   /** @deprecated */
   @Deprecated
   void setAuthenticationMechanisms(HashSet var1);

   /** @deprecated */
   @Deprecated
   HashSet getSecurityPermissions();

   /** @deprecated */
   @Deprecated
   void setSecurityPermissions(HashSet var1);

   /** @deprecated */
   @Deprecated
   ClassLoader getClassLoader();

   /** @deprecated */
   @Deprecated
   void setClassLoader(ClassLoader var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getConnectionFactoryName();

   /** @deprecated */
   @Deprecated
   void setConnectionFactoryName(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getConnectionFactoryDescription();

   /** @deprecated */
   @Deprecated
   void setConnectionFactoryDescription(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getMaxCapacity();

   /** @deprecated */
   @Deprecated
   void setMaxCapacity(int var1) throws InvalidAttributeValueException;
}
