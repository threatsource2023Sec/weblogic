package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;

public final class KeepAlive implements MonitoringAware {
   protected final DefaultMonitoringConfig monitoringConfig = new DefaultMonitoringConfig(KeepAliveProbe.class) {
      public Object createManagementObject() {
         return KeepAlive.this.createJmxManagementObject();
      }
   };
   private int idleTimeoutInSeconds = 30;
   private int maxRequestsCount = 256;

   public KeepAlive() {
   }

   public KeepAlive(KeepAlive keepAlive) {
      this.idleTimeoutInSeconds = keepAlive.idleTimeoutInSeconds;
      this.maxRequestsCount = keepAlive.maxRequestsCount;
   }

   public int getIdleTimeoutInSeconds() {
      return this.idleTimeoutInSeconds;
   }

   public void setIdleTimeoutInSeconds(int idleTimeoutInSeconds) {
      if (idleTimeoutInSeconds < 0) {
         this.idleTimeoutInSeconds = -1;
      } else {
         this.idleTimeoutInSeconds = idleTimeoutInSeconds;
      }

   }

   public int getMaxRequestsCount() {
      return this.maxRequestsCount;
   }

   public void setMaxRequestsCount(int maxRequestsCount) {
      this.maxRequestsCount = maxRequestsCount;
   }

   public MonitoringConfig getMonitoringConfig() {
      return this.monitoringConfig;
   }

   protected Object createJmxManagementObject() {
      return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.jmx.KeepAlive", this, KeepAlive.class);
   }

   protected static void notifyProbesConnectionAccepted(KeepAlive keepAlive, Connection connection) {
      KeepAliveProbe[] probes = (KeepAliveProbe[])keepAlive.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         KeepAliveProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            KeepAliveProbe probe = var3[var5];
            probe.onConnectionAcceptEvent(connection);
         }
      }

   }

   protected static void notifyProbesHit(KeepAlive keepAlive, Connection connection, int requestNumber) {
      KeepAliveProbe[] probes = (KeepAliveProbe[])keepAlive.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         KeepAliveProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            KeepAliveProbe probe = var4[var6];
            probe.onHitEvent(connection, requestNumber);
         }
      }

   }

   protected static void notifyProbesRefused(KeepAlive keepAlive, Connection connection) {
      KeepAliveProbe[] probes = (KeepAliveProbe[])keepAlive.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         KeepAliveProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            KeepAliveProbe probe = var3[var5];
            probe.onRefuseEvent(connection);
         }
      }

   }

   protected static void notifyProbesTimeout(KeepAlive keepAlive, Connection connection) {
      KeepAliveProbe[] probes = (KeepAliveProbe[])keepAlive.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         KeepAliveProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            KeepAliveProbe probe = var3[var5];
            probe.onTimeoutEvent(connection);
         }
      }

   }
}
