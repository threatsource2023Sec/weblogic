package org.glassfish.grizzly;

public interface TransportProbe {
   void onBeforeStartEvent(Transport var1);

   void onStartEvent(Transport var1);

   void onBeforeStopEvent(Transport var1);

   void onStopEvent(Transport var1);

   void onBeforePauseEvent(Transport var1);

   void onPauseEvent(Transport var1);

   void onBeforeResumeEvent(Transport var1);

   void onResumeEvent(Transport var1);

   void onConfigChangeEvent(Transport var1);

   void onErrorEvent(Transport var1, Throwable var2);

   public static class Adapter implements TransportProbe {
      public void onBeforeStartEvent(Transport transport) {
      }

      public void onStartEvent(Transport transport) {
      }

      public void onBeforeStopEvent(Transport transport) {
      }

      public void onStopEvent(Transport transport) {
      }

      public void onBeforePauseEvent(Transport transport) {
      }

      public void onPauseEvent(Transport transport) {
      }

      public void onBeforeResumeEvent(Transport transport) {
      }

      public void onResumeEvent(Transport transport) {
      }

      public void onConfigChangeEvent(Transport transport) {
      }

      public void onErrorEvent(Transport transport, Throwable error) {
      }
   }
}
