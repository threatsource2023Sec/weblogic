package org.glassfish.tyrus.core.monitoring;

import org.glassfish.tyrus.core.Beta;

@Beta
public interface ApplicationEventListener {
   String APPLICATION_EVENT_LISTENER = "org.glassfish.tyrus.core.monitoring.ApplicationEventListener";
   ApplicationEventListener NO_OP = new ApplicationEventListener() {
      public void onApplicationInitialized(String applicationName) {
      }

      public void onApplicationDestroyed() {
      }

      public EndpointEventListener onEndpointRegistered(String endpointPath, Class endpointClass) {
         return EndpointEventListener.NO_OP;
      }

      public void onEndpointUnregistered(String endpointPath) {
      }
   };

   void onApplicationInitialized(String var1);

   void onApplicationDestroyed();

   EndpointEventListener onEndpointRegistered(String var1, Class var2);

   void onEndpointUnregistered(String var1);
}
