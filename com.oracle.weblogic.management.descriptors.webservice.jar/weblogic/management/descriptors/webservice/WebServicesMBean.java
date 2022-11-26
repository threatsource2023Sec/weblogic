package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface WebServicesMBean extends XMLElementMBean {
   HandlerChainsMBean getHandlerChains();

   void setHandlerChains(HandlerChainsMBean var1);

   WebServiceMBean[] getWebServices();

   void setWebServices(WebServiceMBean[] var1);

   void addWebService(WebServiceMBean var1);

   void removeWebService(WebServiceMBean var1);
}
