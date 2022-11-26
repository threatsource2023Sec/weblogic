package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface InitParamMBean extends XMLElementMBean {
   String getParamName();

   void setParamName(String var1);

   String getParamValue();

   void setParamValue(String var1);
}
