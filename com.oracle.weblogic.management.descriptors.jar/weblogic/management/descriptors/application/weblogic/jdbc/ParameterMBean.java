package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBean;

public interface ParameterMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getParamName();

   void setParamName(String var1);

   String getParamValue();

   void setParamValue(String var1);
}
