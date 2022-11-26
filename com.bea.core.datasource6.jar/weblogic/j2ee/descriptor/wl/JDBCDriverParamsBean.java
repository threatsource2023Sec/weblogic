package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface JDBCDriverParamsBean extends SettableBean {
   String getUrl();

   void setUrl(String var1);

   String getDriverName();

   void setDriverName(String var1);

   JDBCPropertiesBean getProperties();

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1);

   boolean isUseXaDataSourceInterface();

   void setUseXaDataSourceInterface(boolean var1);

   String getPassword();

   void setPassword(String var1);

   boolean isUsePasswordIndirection();

   void setUsePasswordIndirection(boolean var1);
}
