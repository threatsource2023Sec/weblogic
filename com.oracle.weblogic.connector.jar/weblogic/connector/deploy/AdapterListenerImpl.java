package weblogic.connector.deploy;

import weblogic.connector.external.AdapterListener;
import weblogic.work.ShutdownCallback;

public class AdapterListenerImpl implements AdapterListener {
   ShutdownCallback shutdownCallback;

   public AdapterListenerImpl(ShutdownCallback shutdownCallback) {
      this.shutdownCallback = shutdownCallback;
   }

   public void completed() {
      this.shutdownCallback.completed();
   }
}
