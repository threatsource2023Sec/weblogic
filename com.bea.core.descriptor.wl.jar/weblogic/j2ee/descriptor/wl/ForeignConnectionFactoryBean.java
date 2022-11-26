package weblogic.j2ee.descriptor.wl;

public interface ForeignConnectionFactoryBean extends ForeignJNDIObjectBean {
   String getUsername();

   void setUsername(String var1) throws IllegalArgumentException;

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1);

   String getPassword();

   void setPassword(String var1) throws IllegalArgumentException;

   String getConnectionHealthChecking();

   void setConnectionHealthChecking(String var1);
}
