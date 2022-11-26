package weblogic.j2ee.descriptor;

public interface ServiceRefHandlerChainsBean {
   ServiceRefHandlerChainBean[] getHandlerChains();

   ServiceRefHandlerChainBean createHandlerChain();

   void destroyHandlerChain(ServiceRefHandlerChainBean var1);

   String getId();

   void setId(String var1);
}
