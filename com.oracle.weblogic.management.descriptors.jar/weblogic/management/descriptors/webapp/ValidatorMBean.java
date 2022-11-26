package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface ValidatorMBean extends WebElementMBean {
   String getClassname();

   void setClassname(String var1);

   ParameterMBean[] getParams();

   void setParams(ParameterMBean[] var1);
}
