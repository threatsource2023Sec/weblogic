package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface DeliveryParamsOverridesBean extends SettableBean {
   String getDeliveryMode();

   void setDeliveryMode(String var1) throws IllegalArgumentException;

   String getTimeToDeliver();

   void setTimeToDeliver(String var1) throws IllegalArgumentException;

   long getTimeToLive();

   void setTimeToLive(long var1) throws IllegalArgumentException;

   int getPriority();

   void setPriority(int var1) throws IllegalArgumentException;

   long getRedeliveryDelay();

   void setRedeliveryDelay(long var1) throws IllegalArgumentException;

   TemplateBean getTemplateBean();
}
