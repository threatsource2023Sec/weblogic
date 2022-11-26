package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface ReliableDeliveryMBean extends XMLElementMBean {
   boolean isDuplicateElimination();

   void setDuplicateElimination(boolean var1);

   int getRetries();

   void setRetries(int var1);

   int getRetryInterval();

   void setRetryInterval(int var1);

   int getPersistDuration();

   void setPersistDuration(int var1);

   boolean isInOrderDelivery();

   void setInOrderDelivery(boolean var1);
}
