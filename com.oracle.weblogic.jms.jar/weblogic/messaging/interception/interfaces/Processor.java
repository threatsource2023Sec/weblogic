package weblogic.messaging.interception.interfaces;

import javax.xml.rpc.handler.MessageContext;
import weblogic.messaging.interception.exceptions.InterceptionException;
import weblogic.messaging.interception.exceptions.MessageContextException;

public interface Processor {
   boolean process(MessageContext var1, AssociationInfo var2) throws InterceptionException, MessageContextException;

   void processOnly(MessageContext var1, AssociationInfo var2) throws InterceptionException, MessageContextException;

   void onShutdown();

   void associationStateChange(boolean var1);

   void processAsync(MessageContext var1, AssociationInfo var2, InterceptionCallBack var3) throws InterceptionException, MessageContextException;

   void processOnlyAsync(MessageContext var1, AssociationInfo var2, InterceptionCallBack var3) throws InterceptionException, MessageContextException;
}
