package kodo.jdbc.conf.descriptor;

public interface KodoPoolingDataSourceBean extends DriverDataSourceBean {
   String getConnectionUserName();

   void setConnectionUserName(String var1);

   int getLoginTimeout();

   void setLoginTimeout(int var1);

   String getConnectionPassword();

   void setConnectionPassword(String var1);

   byte[] getConnectionPasswordEncrypted();

   void setConnectionPasswordEncrypted(byte[] var1);

   String getConnectionURL();

   void setConnectionURL(String var1);

   String getConnectionDriverName();

   void setConnectionDriverName(String var1);
}
