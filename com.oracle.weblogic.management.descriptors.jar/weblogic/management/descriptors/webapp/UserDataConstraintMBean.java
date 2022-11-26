package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface UserDataConstraintMBean extends WebElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getTransportGuarantee();

   void setTransportGuarantee(String var1);
}
