package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface MulticastParamsBean extends SettableBean {
   String getMulticastAddress();

   void setMulticastAddress(String var1) throws IllegalArgumentException;

   int getMulticastPort();

   void setMulticastPort(int var1) throws IllegalArgumentException;

   int getMulticastTimeToLive();

   void setMulticastTimeToLive(int var1) throws IllegalArgumentException;

   TemplateBean getTemplateBean();
}
