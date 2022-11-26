package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.Connection;

public interface HttpServerProbe {
   void onRequestReceiveEvent(HttpServerFilter var1, Connection var2, Request var3);

   void onRequestCompleteEvent(HttpServerFilter var1, Connection var2, Response var3);

   void onRequestSuspendEvent(HttpServerFilter var1, Connection var2, Request var3);

   void onRequestResumeEvent(HttpServerFilter var1, Connection var2, Request var3);

   void onRequestTimeoutEvent(HttpServerFilter var1, Connection var2, Request var3);

   void onRequestCancelEvent(HttpServerFilter var1, Connection var2, Request var3);

   void onBeforeServiceEvent(HttpServerFilter var1, Connection var2, Request var3, HttpHandler var4);

   public static class Adapter implements HttpServerProbe {
      public void onRequestReceiveEvent(HttpServerFilter filter, Connection connection, Request request) {
      }

      public void onRequestCompleteEvent(HttpServerFilter filter, Connection connection, Response response) {
      }

      public void onRequestSuspendEvent(HttpServerFilter filter, Connection connection, Request request) {
      }

      public void onRequestResumeEvent(HttpServerFilter filter, Connection connection, Request request) {
      }

      public void onRequestTimeoutEvent(HttpServerFilter filter, Connection connection, Request request) {
      }

      public void onRequestCancelEvent(HttpServerFilter filter, Connection connection, Request request) {
      }

      public void onBeforeServiceEvent(HttpServerFilter filter, Connection connection, Request request, HttpHandler httpHandler) {
      }
   }
}
