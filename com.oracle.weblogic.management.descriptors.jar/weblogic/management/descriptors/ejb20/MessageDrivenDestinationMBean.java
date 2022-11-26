package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface MessageDrivenDestinationMBean extends XMLElementMBean {
   String getDestinationType();

   void setDestinationType(String var1);

   String getSubscriptionDurability();

   void setSubscriptionDurability(String var1);
}
