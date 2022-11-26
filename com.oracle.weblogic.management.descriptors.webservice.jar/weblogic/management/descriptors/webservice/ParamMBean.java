package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;
import weblogic.xml.stream.XMLName;

public interface ParamMBean extends XMLElementMBean {
   String getParamName();

   void setParamName(String var1);

   String getParamStyle();

   void setParamStyle(String var1);

   String getLocation();

   void setLocation(String var1);

   boolean isImplicit();

   void setImplicit(boolean var1);

   String getContentType();

   void setContentType(String var1);

   XMLName getParamType();

   void setParamType(XMLName var1);

   String getClassName();

   void setClassName(String var1);
}
