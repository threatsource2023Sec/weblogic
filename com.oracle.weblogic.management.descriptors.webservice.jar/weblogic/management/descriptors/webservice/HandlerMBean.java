package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface HandlerMBean extends XMLElementMBean {
   String getHandlerName();

   void setHandlerName(String var1);

   String getClassName();

   void setClassName(String var1);

   InitParamsMBean getInitParams();

   void setInitParams(InitParamsMBean var1);
}
