package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;
import weblogic.xml.stream.XMLName;

public interface FaultMBean extends XMLElementMBean {
   String getFaultName();

   void setFaultName(String var1);

   XMLName getFaultType();

   void setFaultType(XMLName var1);

   String getClassName();

   void setClassName(String var1);
}
