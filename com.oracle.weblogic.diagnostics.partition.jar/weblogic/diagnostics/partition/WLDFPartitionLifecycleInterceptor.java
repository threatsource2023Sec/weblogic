package weblogic.diagnostics.partition;

import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.accessor.PartitionAccessRuntime;
import weblogic.diagnostics.harvester.internal.HarvesterImageSource;
import weblogic.diagnostics.harvester.internal.MetricArchiver;
import weblogic.diagnostics.harvester.internal.PartitionHarvesterRuntime;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.ImageSourceNotFoundException;
import weblogic.diagnostics.image.PartitionImageRuntimeMBeanImpl;
import weblogic.diagnostics.metrics.PartitionResourceMetricsRuntimeMBeanImpl;
import weblogic.diagnostics.watch.JMXNotificationSource;
import weblogic.diagnostics.watch.WatchManagerFactory;
import weblogic.diagnostics.watch.WatchNotificationRuntimeMBeanImpl;
import weblogic.diagnostics.watch.WatchSource;
import weblogic.management.ManagementException;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionRuntimeMBean;
import weblogic.management.runtime.WLDFWatchNotificationSourceRuntimeMBean;

@Service
@Interceptor
@ContractsProvided({WLDFPartitionLifecycleInterceptor.class, MethodInterceptor.class})
public class WLDFPartitionLifecycleInterceptor extends PartitionManagerInterceptorAdapter {
   @Inject
   private ImageManager imageManager;

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      PartitionRuntimeMBean partitionRuntime = (PartitionRuntimeMBean)this.getPartitionRuntime(methodInvocation);
      if (partitionRuntime != null && partitionRuntime.getWLDFPartitionRuntime() == null) {
         try {
            WLDFPartitionRuntimeMBean wldfPartitionRuntime = new WLDFPartitionRuntimeMBeanImpl(partitionRuntime);
            this.initWLDFPartitionRuntime(wldfPartitionRuntime);
            partitionRuntime.setWLDFPartitionRuntime(wldfPartitionRuntime);
            partitionRuntime.setPartitionResourceMetricsRuntime(new PartitionResourceMetricsRuntimeMBeanImpl(partitionRuntime, partitionRuntime.getName(), partitionRuntime.getPartitionID()));
         } catch (ManagementException var5) {
            throw new RuntimeException(var5);
         }
      }

      methodInvocation.proceed();
   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      this.startPartition(methodInvocation, partitionName);
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      this.forceShutdownPartition(methodInvocation, partitionName);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
      PartitionRuntimeMBean partitionRuntime = (PartitionRuntimeMBean)this.getPartitionRuntime(methodInvocation);
      if (partitionRuntime != null && partitionRuntime.getWLDFPartitionRuntime() != null) {
         partitionRuntime.setWLDFPartitionRuntime((WLDFPartitionRuntimeMBean)null);
         WatchManagerFactory.removeFactoryInstance(partitionRuntime.getName());
         MetricArchiver.removeMetricArchiver(partitionRuntime.getName());

         try {
            this.imageManager.unregisterImageSource("WatchSource$" + partitionRuntime.getName());
            this.imageManager.unregisterImageSource("HarvesterImageSource$" + partitionRuntime.getName());
         } catch (ImageSourceNotFoundException var5) {
         }
      }

   }

   private void initWLDFPartitionRuntime(WLDFPartitionRuntimeMBean wldfPartitionRuntime) throws ManagementException {
      wldfPartitionRuntime.setWLDFPartitionAccessRuntime(new PartitionAccessRuntime(wldfPartitionRuntime));
      wldfPartitionRuntime.setWLDFPartitionImageRuntime(new PartitionImageRuntimeMBeanImpl(wldfPartitionRuntime));
      WatchManagerFactory wmFactory = WatchManagerFactory.getFactoryInstance(wldfPartitionRuntime.getName());
      WatchNotificationRuntimeMBeanImpl watchNotificationRuntime = new WatchNotificationRuntimeMBeanImpl(wldfPartitionRuntime, wmFactory);
      wldfPartitionRuntime.setWLDFWatchNotificationRuntime(watchNotificationRuntime);
      WLDFWatchNotificationSourceRuntimeMBean jmxNotificationSource = new JMXNotificationSource(watchNotificationRuntime);
      watchNotificationRuntime.setWatchJMXNotificationSource(jmxNotificationSource);
      wmFactory.setWatchNotificationRuntime(watchNotificationRuntime);
      this.imageManager.registerImageSource("WatchSource$" + wldfPartitionRuntime.getName(), new WatchSource(wmFactory, watchNotificationRuntime));
      MetricArchiver archiver = MetricArchiver.findOrCreateMetricArchiver(wldfPartitionRuntime.getName());
      PartitionHarvesterRuntime hvstRuntime = new PartitionHarvesterRuntime(archiver, wldfPartitionRuntime.getName(), wldfPartitionRuntime);
      wldfPartitionRuntime.setWLDFPartitionHarvesterRuntime(hvstRuntime);
      this.imageManager.registerImageSource("HarvesterImageSource$" + wldfPartitionRuntime.getName(), new HarvesterImageSource(hvstRuntime));
   }
}
