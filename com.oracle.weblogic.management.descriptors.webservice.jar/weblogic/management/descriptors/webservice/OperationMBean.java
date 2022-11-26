package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface OperationMBean extends XMLElementMBean {
   String getOperationName();

   void setOperationName(String var1);

   ParamsMBean getParams();

   void setParams(ParamsMBean var1);

   ReliableDeliveryMBean getReliableDelivery();

   void setReliableDelivery(ReliableDeliveryMBean var1);

   String getComponentName();

   void setComponentName(String var1);

   ComponentMBean getComponent();

   void setComponent(ComponentMBean var1);

   String getMethod();

   void setMethod(String var1);

   String getHandlerChainName();

   void setHandlerChainName(String var1);

   String getInvocationStyle();

   void setInvocationStyle(String var1);

   String getNamespace();

   void setNamespace(String var1);

   String getPortTypeName();

   void setPortTypeName(String var1);

   String getStyle();

   void setStyle(String var1);

   String getEncoding();

   void setEncoding(String var1);

   String getConversationPhase();

   void setConversationPhase(String var1);

   String getInSecuritySpec();

   void setInSecuritySpec(String var1);

   String getOutSecuritySpec();

   void setOutSecuritySpec(String var1);

   HandlerChainMBean getHandlerChain();

   void setHandlerChain(HandlerChainMBean var1);
}
