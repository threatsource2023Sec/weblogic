package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface CommonHandlerChainMBean extends XMLElementMBean {
   HandlerChainsMBean getHandlerChains();

   void setHandlerChains(HandlerChainsMBean var1);
}
