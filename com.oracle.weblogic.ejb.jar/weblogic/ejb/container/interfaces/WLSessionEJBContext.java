package weblogic.ejb.container.interfaces;

import javax.xml.rpc.handler.MessageContext;

public interface WLSessionEJBContext extends WLEJBContext {
   void setMessageContext(MessageContext var1);

   void setPrimaryKey(Object var1);
}
