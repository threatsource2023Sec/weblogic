package org.glassfish.grizzly;

public interface ConnectionProbe {
   void onBindEvent(Connection var1);

   void onAcceptEvent(Connection var1, Connection var2);

   void onConnectEvent(Connection var1);

   void onReadEvent(Connection var1, Buffer var2, int var3);

   void onWriteEvent(Connection var1, Buffer var2, long var3);

   void onErrorEvent(Connection var1, Throwable var2);

   void onCloseEvent(Connection var1);

   void onIOEventReadyEvent(Connection var1, IOEvent var2);

   void onIOEventEnableEvent(Connection var1, IOEvent var2);

   void onIOEventDisableEvent(Connection var1, IOEvent var2);

   public static class Adapter implements ConnectionProbe {
      public void onBindEvent(Connection connection) {
      }

      public void onAcceptEvent(Connection serverConnection, Connection clientConnection) {
      }

      public void onConnectEvent(Connection connection) {
      }

      public void onReadEvent(Connection connection, Buffer data, int size) {
      }

      public void onWriteEvent(Connection connection, Buffer data, long size) {
      }

      public void onErrorEvent(Connection connection, Throwable error) {
      }

      public void onCloseEvent(Connection connection) {
      }

      public void onIOEventReadyEvent(Connection connection, IOEvent ioEvent) {
      }

      public void onIOEventEnableEvent(Connection connection, IOEvent ioEvent) {
      }

      public void onIOEventDisableEvent(Connection connection, IOEvent ioEvent) {
      }
   }
}
