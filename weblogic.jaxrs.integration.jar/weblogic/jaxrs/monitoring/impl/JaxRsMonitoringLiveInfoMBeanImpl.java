package weblogic.jaxrs.monitoring.impl;

/** @deprecated */
@Deprecated
final class JaxRsMonitoringLiveInfoMBeanImpl {
   private long id;
   private long startTime;

   public JaxRsMonitoringLiveInfoMBeanImpl(long id, long startTime) {
      this.id = id;
      this.startTime = startTime;
   }

   public long getId() {
      return this.id;
   }

   public long getStartTime() {
      return this.startTime;
   }
}
