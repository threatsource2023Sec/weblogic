package weblogic.messaging.dispatcher;

import weblogic.messaging.ID;

public interface DispatcherPeerGoneListener {
   int incrementRefCount();

   int decrementRefCount();

   ID getId();

   void dispatcherPeerGone(Exception var1, Dispatcher var2);
}
