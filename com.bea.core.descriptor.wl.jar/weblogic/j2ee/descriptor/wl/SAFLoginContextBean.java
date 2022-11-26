package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface SAFLoginContextBean extends SettableBean {
   String getLoginURL();

   void setLoginURL(String var1) throws IllegalArgumentException;

   String getUsername();

   void setUsername(String var1) throws IllegalArgumentException;

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1);

   String getPassword();

   void setPassword(String var1) throws IllegalArgumentException;
}
