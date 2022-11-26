package weblogic.j2ee.descriptor;

public interface ServiceRefHandlerChainBean {
   String getServiceNamePattern();

   void setServiceNamePattern(String var1);

   String getPortNamePattern();

   void setPortNamePattern(String var1);

   String getProtocolBindings();

   void setProtocolBindings(String var1);

   ServiceRefHandlerBean[] getHandlers();

   ServiceRefHandlerBean createHandler();

   void destroyHandler(ServiceRefHandlerBean var1);

   String getId();

   void setId(String var1);
}
