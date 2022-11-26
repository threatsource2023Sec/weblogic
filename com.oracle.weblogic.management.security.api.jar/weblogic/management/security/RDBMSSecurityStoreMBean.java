package weblogic.management.security;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;

public interface RDBMSSecurityStoreMBean extends StandardInterface, DescriptorBean {
   String getUsername();

   void setUsername(String var1) throws InvalidAttributeValueException;

   String getPassword();

   void setPassword(String var1) throws InvalidAttributeValueException;

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1) throws InvalidAttributeValueException;

   String getJNDIUsername();

   void setJNDIUsername(String var1) throws InvalidAttributeValueException;

   String getJNDIPassword();

   void setJNDIPassword(String var1) throws InvalidAttributeValueException;

   byte[] getJNDIPasswordEncrypted();

   void setJNDIPasswordEncrypted(byte[] var1) throws InvalidAttributeValueException;

   String getConnectionURL();

   void setConnectionURL(String var1) throws InvalidAttributeValueException;

   String getDriverName();

   void setDriverName(String var1) throws InvalidAttributeValueException;

   String getConnectionProperties();

   void setConnectionProperties(String var1) throws InvalidAttributeValueException;

   String getJMSTopic();

   void setJMSTopic(String var1) throws InvalidAttributeValueException;

   String getJMSTopicConnectionFactory();

   void setJMSTopicConnectionFactory(String var1) throws InvalidAttributeValueException;

   int getJMSExceptionReconnectAttempts();

   void setJMSExceptionReconnectAttempts(int var1) throws InvalidAttributeValueException;

   String getNotificationProperties();

   void setNotificationProperties(String var1) throws InvalidAttributeValueException;

   RealmMBean getRealm();

   String getName();

   void setName(String var1) throws InvalidAttributeValueException;

   String getCompatibilityObjectName();
}
