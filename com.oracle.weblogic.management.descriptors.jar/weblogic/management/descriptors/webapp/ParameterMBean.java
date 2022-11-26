package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface ParameterMBean extends WebElementMBean {
   String getParamName();

   void setParamName(String var1);

   String getParamValue();

   void setParamValue(String var1);

   String getDescription();

   void setDescription(String var1);
}
