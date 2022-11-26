package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface HandlerChainsMBean extends XMLElementMBean {
   HandlerChainMBean[] getHandlerChains();

   void setHandlerChains(HandlerChainMBean[] var1);

   void addHandlerChain(HandlerChainMBean var1);

   void removeHandlerChain(HandlerChainMBean var1);
}
