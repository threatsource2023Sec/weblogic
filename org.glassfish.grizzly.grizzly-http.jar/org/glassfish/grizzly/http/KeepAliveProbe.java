package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Connection;

public interface KeepAliveProbe {
   void onConnectionAcceptEvent(Connection var1);

   void onHitEvent(Connection var1, int var2);

   void onRefuseEvent(Connection var1);

   void onTimeoutEvent(Connection var1);

   public static class Adapter implements KeepAliveProbe {
      public void onConnectionAcceptEvent(Connection connection) {
      }

      public void onHitEvent(Connection connection, int requestNumber) {
      }

      public void onRefuseEvent(Connection connection) {
      }

      public void onTimeoutEvent(Connection connection) {
      }
   }
}
