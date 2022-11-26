package weblogic.work.concurrent.runtime;

import weblogic.management.ManagementException;
import weblogic.management.runtime.ManagedExecutorServiceRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.concurrent.ManagedExecutorServiceImpl;

public class ManagedExecutorServiceRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ManagedExecutorServiceRuntimeMBean {
   private ManagedExecutorServiceImpl service;
   private WorkManagerRuntimeMBean wmRuntimeMBean;

   public ManagedExecutorServiceRuntimeMBeanImpl(ManagedExecutorServiceImpl service, RuntimeMBean parent, WorkManagerRuntimeMBean wmRuntimeMBean) throws ManagementException {
      super(service.getName(), parent);
      this.service = service;
      this.wmRuntimeMBean = wmRuntimeMBean;
   }

   public String getApplicationName() {
      return this.service.getAppId();
   }

   public String getModuleName() {
      return this.service.getModuleId();
   }

   public String getPartitionName() {
      return this.service.getPartitionName();
   }

   public WorkManagerRuntimeMBean getWorkManager() {
      return this.wmRuntimeMBean;
   }

   public long getRunningLongRunningRequests() {
      return (long)this.service.getRunningLongRunningRequest();
   }

   public long getCompletedShortRunningRequests() {
      return this.service.getCompletedRequest(false);
   }

   public long getCompletedLongRunningRequests() {
      return this.service.getCompletedRequest(true);
   }

   public long getSubmitedShortRunningRequests() {
      return this.service.getSubmittedRequest(false);
   }

   public long getSubmittedLongRunningRequests() {
      return this.service.getSubmittedRequest(true);
   }

   public long getRejectedShortRunningRequests() {
      return this.service.getRejectedRequest(false);
   }

   public long getRejectedLongRunningRequests() {
      return this.service.getRejectedRequest(true);
   }

   public long getFailedRequests() {
      return this.service.getFailedRequest();
   }
}
