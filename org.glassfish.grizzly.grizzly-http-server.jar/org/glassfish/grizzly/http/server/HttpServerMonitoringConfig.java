package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.ConnectionProbe;
import org.glassfish.grizzly.TransportProbe;
import org.glassfish.grizzly.http.HttpProbe;
import org.glassfish.grizzly.http.server.filecache.FileCacheProbe;
import org.glassfish.grizzly.memory.MemoryProbe;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.threadpool.ThreadPoolProbe;

public final class HttpServerMonitoringConfig {
   private final DefaultMonitoringConfig memoryConfig = new DefaultMonitoringConfig(MemoryProbe.class);
   private final DefaultMonitoringConfig transportConfig = new DefaultMonitoringConfig(TransportProbe.class);
   private final DefaultMonitoringConfig connectionConfig = new DefaultMonitoringConfig(ConnectionProbe.class);
   private final DefaultMonitoringConfig threadPoolConfig = new DefaultMonitoringConfig(ThreadPoolProbe.class);
   private final DefaultMonitoringConfig fileCacheConfig = new DefaultMonitoringConfig(FileCacheProbe.class);
   private final DefaultMonitoringConfig httpConfig = new DefaultMonitoringConfig(HttpProbe.class);
   private final DefaultMonitoringConfig webServerConfig = new DefaultMonitoringConfig(HttpServerProbe.class);

   public MonitoringConfig getMemoryConfig() {
      return this.memoryConfig;
   }

   public MonitoringConfig getConnectionConfig() {
      return this.connectionConfig;
   }

   public MonitoringConfig getThreadPoolConfig() {
      return this.threadPoolConfig;
   }

   public MonitoringConfig getTransportConfig() {
      return this.transportConfig;
   }

   public MonitoringConfig getFileCacheConfig() {
      return this.fileCacheConfig;
   }

   public MonitoringConfig getHttpConfig() {
      return this.httpConfig;
   }

   public MonitoringConfig getWebServerConfig() {
      return this.webServerConfig;
   }
}
