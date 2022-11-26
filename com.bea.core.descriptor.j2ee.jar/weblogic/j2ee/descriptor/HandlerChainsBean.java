package weblogic.j2ee.descriptor;

public interface HandlerChainsBean {
   HandlerChainBean[] getHandlerChains();

   HandlerChainBean createHandlerChain();

   void destroyHandlerChain(HandlerChainBean var1);

   String getId();

   void setId(String var1);
}
