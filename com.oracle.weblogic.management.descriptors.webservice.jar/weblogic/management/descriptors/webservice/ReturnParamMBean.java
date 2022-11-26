package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;
import weblogic.xml.stream.XMLName;

public interface ReturnParamMBean extends XMLElementMBean {
   String getParamName();

   void setParamName(String var1);

   String getLocation();

   void setLocation(String var1);

   XMLName getParamType();

   void setParamType(XMLName var1);

   String getClassName();

   void setClassName(String var1);
}
