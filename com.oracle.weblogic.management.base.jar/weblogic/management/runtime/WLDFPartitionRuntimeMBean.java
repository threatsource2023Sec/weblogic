package weblogic.management.runtime;

public interface WLDFPartitionRuntimeMBean extends RuntimeMBean {
   WLDFPartitionAccessRuntimeMBean getWLDFPartitionAccessRuntime();

   void setWLDFPartitionAccessRuntime(WLDFPartitionAccessRuntimeMBean var1);

   WLDFPartitionImageRuntimeMBean getWLDFPartitionImageRuntime();

   void setWLDFPartitionImageRuntime(WLDFPartitionImageRuntimeMBean var1);

   WLDFWatchNotificationRuntimeMBean getWLDFWatchNotificationRuntime();

   void setWLDFWatchNotificationRuntime(WLDFWatchNotificationRuntimeMBean var1);

   WLDFPartitionHarvesterRuntimeMBean getWLDFPartitionHarvesterRuntime();

   void setWLDFPartitionHarvesterRuntime(WLDFPartitionHarvesterRuntimeMBean var1);
}
