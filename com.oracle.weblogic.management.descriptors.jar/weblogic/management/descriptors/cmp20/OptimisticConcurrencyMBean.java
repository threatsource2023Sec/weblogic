package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;

public interface OptimisticConcurrencyMBean extends XMLElementMBean {
   String getVerifyFields();

   void setVerifyFields(String var1);
}
