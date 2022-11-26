package weblogic.j2ee.descriptor.wl;

public interface ConnectionPropertiesBean {
   String getUserName();

   void setUserName(String var1) throws IllegalArgumentException;

   String getPassword();

   void setPassword(String var1) throws IllegalArgumentException;

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1) throws IllegalArgumentException;

   String getUrl();

   void setUrl(String var1) throws IllegalArgumentException;

   String getDriverClassName();

   void setDriverClassName(String var1) throws IllegalArgumentException;

   ConnectionParamsBean[] getConnectionParams();

   ConnectionParamsBean createConnectionParams();

   void destroyConnectionParams(ConnectionParamsBean var1);
}
