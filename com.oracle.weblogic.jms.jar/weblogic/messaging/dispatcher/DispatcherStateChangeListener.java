package weblogic.messaging.dispatcher;

public interface DispatcherStateChangeListener extends DispatcherPeerGoneListener {
   void stateChangeListener(DispatcherStateChangeListener var1, Throwable var2);

   boolean holdsLock();
}
