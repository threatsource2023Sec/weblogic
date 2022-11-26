package weblogic.jms.dispatcher;

import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.messaging.dispatcher.DispatcherId;

interface InitDispatcherManager {
   int abstractThreadPoolSize(boolean var1);

   ManagedInvocationContext pushCIC(ComponentInvocationContext var1);

   String getObjectHandlerClassName();

   DispatcherId getLocalDispatcherId();

   boolean isServer();

   boolean isServerLocalRJVM(weblogic.messaging.dispatcher.DispatcherRemote var1);
}
