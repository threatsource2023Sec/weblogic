package weblogic.j2ee.descriptor;

public interface HandlerChainBean {
   String getServiceNamePattern();

   void setServiceNamePattern(String var1);

   String getPortNamePattern();

   void setPortNamePattern(String var1);

   String getProtocolBindings();

   void setProtocolBindings(String var1);

   PortComponentHandlerBean[] getHandlers();

   PortComponentHandlerBean createHandler();

   void destroyHandler(PortComponentHandlerBean var1);

   String getId();

   void setId(String var1);
}
