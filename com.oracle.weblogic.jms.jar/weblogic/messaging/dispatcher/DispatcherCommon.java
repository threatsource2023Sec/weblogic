package weblogic.messaging.dispatcher;

public interface DispatcherCommon {
   DispatcherId getId();

   boolean isLocal();

   void dispatchAsync(Request var1) throws DispatcherException;

   void dispatchAsyncWithId(Request var1, int var2) throws DispatcherException;

   DispatcherPeerGoneListener addDispatcherPeerGoneListener(DispatcherPeerGoneListener var1);

   void removeDispatcherPeerGoneListener(DispatcherPeerGoneListener var1);
}
