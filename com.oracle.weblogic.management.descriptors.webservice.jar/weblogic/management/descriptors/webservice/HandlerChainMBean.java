package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface HandlerChainMBean extends XMLElementMBean {
   String getHandlerChainName();

   void setHandlerChainName(String var1);

   HandlerMBean[] getHandlers();

   void setHandlers(HandlerMBean[] var1);

   void addHandler(HandlerMBean var1);

   void removeHandler(HandlerMBean var1);
}
