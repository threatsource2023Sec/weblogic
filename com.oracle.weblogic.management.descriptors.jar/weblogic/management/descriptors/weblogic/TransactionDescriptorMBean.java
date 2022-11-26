package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface TransactionDescriptorMBean extends XMLElementMBean {
   int getTransTimeoutSeconds();

   void setTransTimeoutSeconds(int var1);
}
