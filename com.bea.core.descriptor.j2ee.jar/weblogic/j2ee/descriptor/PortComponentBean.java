package weblogic.j2ee.descriptor;

import javax.xml.namespace.QName;

public interface PortComponentBean {
   String getDescription();

   void setDescription(String var1);

   String getDisplayName();

   void setDisplayName(String var1);

   IconBean getIcon();

   IconBean createIcon();

   void destroyIcon(IconBean var1);

   String getPortComponentName();

   void setPortComponentName(String var1);

   QName getWsdlService();

   void setWsdlService(QName var1);

   QName getWsdlPort();

   void setWsdlPort(QName var1);

   boolean isEnableMtom();

   void setEnableMtom(boolean var1);

   String getProtocolBinding();

   void setProtocolBinding(String var1);

   String getServiceEndpointInterface();

   void setServiceEndpointInterface(String var1);

   ServiceImplBeanBean getServiceImplBean();

   ServiceImplBeanBean createServiceImplBean();

   void destroyServiceImplBean(ServiceImplBeanBean var1);

   PortComponentHandlerBean[] getHandlers();

   PortComponentHandlerBean createHandler();

   void destroyHandler(PortComponentHandlerBean var1);

   HandlerChainsBean getHandlerChains();

   HandlerChainsBean createHandlerChains();

   void destroyHandlerChains(HandlerChainsBean var1);

   String getId();

   void setId(String var1);
}
