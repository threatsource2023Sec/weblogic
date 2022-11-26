package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface ComponentMBean extends XMLElementMBean {
   String getComponentName();

   void setComponentName(String var1);
}
