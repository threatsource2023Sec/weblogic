package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface JDBCPropertyBean extends SettableBean {
   String getName();

   void setName(String var1);

   String getValue();

   void setValue(String var1);

   String getSysPropValue();

   void setSysPropValue(String var1);

   String getEncryptedValue();

   void setEncryptedValue(String var1);

   byte[] getEncryptedValueEncrypted();

   void setEncryptedValueEncrypted(byte[] var1);
}
