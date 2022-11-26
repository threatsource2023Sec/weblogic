package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface JNDINameMBean extends XMLElementMBean {
   String getPath();

   void setPath(String var1);
}
