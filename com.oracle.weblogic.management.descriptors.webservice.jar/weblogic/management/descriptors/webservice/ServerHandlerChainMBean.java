package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface ServerHandlerChainMBean extends XMLElementMBean {
   InboundHandlerChainMBean getInboundHandlerChain();

   void setInboundHandlerChain(InboundHandlerChainMBean var1);

   OutboundHandlerChainMBean getOutboundHandlerChain();

   void setOutboundHandlerChain(OutboundHandlerChainMBean var1);
}
