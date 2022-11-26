package weblogic.management.configuration;

import weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean;

/** @deprecated */
@Deprecated
public interface ForeignJMSConnectionFactoryMBean extends ForeignJNDIObjectMBean {
   void setLocalJNDIName(String var1);

   String getLocalJNDIName();

   void setRemoteJNDIName(String var1);

   String getRemoteJNDIName();

   void setUsername(String var1);

   String getUsername();

   void setPassword(String var1);

   String getPassword();

   void setConnectionHealthChecking(String var1);

   String getConnectionHealthChecking();

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1);

   void useDelegates(ForeignConnectionFactoryBean var1);
}
