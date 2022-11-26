package weblogic.j2ee.descriptor.wl;

import weblogic.j2ee.descriptor.ServiceRefHandlerBean;

public interface WeblogicWseeClientHandlerChainBean {
   ServiceRefHandlerBean[] getHandlers();

   ServiceRefHandlerBean createHandler();

   void destroyHandler(ServiceRefHandlerBean var1);

   String getVersion();

   void setVersion(String var1);
}
