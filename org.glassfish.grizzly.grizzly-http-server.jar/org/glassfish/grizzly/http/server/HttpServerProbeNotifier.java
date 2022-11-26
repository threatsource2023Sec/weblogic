package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.Connection;

final class HttpServerProbeNotifier {
   static void notifyRequestReceive(HttpServerFilter filter, Connection connection, Request request) {
      HttpServerProbe[] probes = (HttpServerProbe[])filter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpServerProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            HttpServerProbe probe = var4[var6];
            probe.onRequestReceiveEvent(filter, connection, request);
         }
      }

   }

   static void notifyRequestComplete(HttpServerFilter filter, Connection connection, Response response) {
      HttpServerProbe[] probes = (HttpServerProbe[])filter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpServerProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            HttpServerProbe probe = var4[var6];
            probe.onRequestCompleteEvent(filter, connection, response);
         }
      }

   }

   static void notifyRequestSuspend(HttpServerFilter filter, Connection connection, Request request) {
      HttpServerProbe[] probes = (HttpServerProbe[])filter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpServerProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            HttpServerProbe probe = var4[var6];
            probe.onRequestSuspendEvent(filter, connection, request);
         }
      }

   }

   static void notifyRequestResume(HttpServerFilter filter, Connection connection, Request request) {
      HttpServerProbe[] probes = (HttpServerProbe[])filter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpServerProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            HttpServerProbe probe = var4[var6];
            probe.onRequestResumeEvent(filter, connection, request);
         }
      }

   }

   static void notifyRequestTimeout(HttpServerFilter filter, Connection connection, Request request) {
      HttpServerProbe[] probes = (HttpServerProbe[])filter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpServerProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            HttpServerProbe probe = var4[var6];
            probe.onRequestTimeoutEvent(filter, connection, request);
         }
      }

   }

   static void notifyRequestCancel(HttpServerFilter filter, Connection connection, Request request) {
      HttpServerProbe[] probes = (HttpServerProbe[])filter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpServerProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            HttpServerProbe probe = var4[var6];
            probe.onRequestCancelEvent(filter, connection, request);
         }
      }

   }

   static void notifyBeforeService(HttpServerFilter filter, Connection connection, Request request, HttpHandler httpHandler) {
      HttpServerProbe[] probes = (HttpServerProbe[])filter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpServerProbe[] var5 = probes;
         int var6 = probes.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            HttpServerProbe probe = var5[var7];
            probe.onBeforeServiceEvent(filter, connection, request, httpHandler);
         }
      }

   }
}
