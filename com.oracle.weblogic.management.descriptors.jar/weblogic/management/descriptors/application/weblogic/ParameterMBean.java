package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface ParameterMBean extends XMLElementMBean {
   String getParamName();

   void setParamName(String var1);

   String getParamValue();

   void setParamValue(String var1);

   String getDescription();

   void setDescription(String var1);
}
