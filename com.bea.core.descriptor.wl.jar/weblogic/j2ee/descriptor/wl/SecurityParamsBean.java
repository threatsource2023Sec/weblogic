package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface SecurityParamsBean extends SettableBean {
   boolean isAttachJMSXUserId();

   void setAttachJMSXUserId(boolean var1) throws IllegalArgumentException;

   String getSecurityPolicy();

   void setSecurityPolicy(String var1) throws IllegalArgumentException;
}
