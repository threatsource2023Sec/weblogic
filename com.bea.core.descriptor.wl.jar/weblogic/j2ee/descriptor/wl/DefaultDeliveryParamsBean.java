package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface DefaultDeliveryParamsBean extends SettableBean {
   String getDefaultDeliveryMode();

   void setDefaultDeliveryMode(String var1) throws IllegalArgumentException;

   String getDefaultTimeToDeliver();

   void setDefaultTimeToDeliver(String var1) throws IllegalArgumentException;

   long getDefaultTimeToLive();

   void setDefaultTimeToLive(long var1) throws IllegalArgumentException;

   int getDefaultPriority();

   void setDefaultPriority(int var1) throws IllegalArgumentException;

   long getDefaultRedeliveryDelay();

   void setDefaultRedeliveryDelay(long var1) throws IllegalArgumentException;

   long getSendTimeout();

   void setSendTimeout(long var1) throws IllegalArgumentException;

   int getDefaultCompressionThreshold();

   void setDefaultCompressionThreshold(int var1) throws IllegalArgumentException;

   String getDefaultUnitOfOrder();

   void setDefaultUnitOfOrder(String var1) throws IllegalArgumentException;
}
