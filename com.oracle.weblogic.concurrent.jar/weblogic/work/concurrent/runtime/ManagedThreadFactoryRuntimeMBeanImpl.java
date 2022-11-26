package weblogic.work.concurrent.runtime;

import weblogic.management.ManagementException;
import weblogic.management.runtime.ManagedThreadFactoryRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.work.concurrent.ManagedThreadFactoryImpl;

public class ManagedThreadFactoryRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ManagedThreadFactoryRuntimeMBean {
   ManagedThreadFactoryImpl mtf;

   public ManagedThreadFactoryRuntimeMBeanImpl(ManagedThreadFactoryImpl mtf, RuntimeMBean parent) throws ManagementException {
      super(mtf.getName(), parent);
      this.mtf = mtf;
   }

   public String getPartitionName() {
      return this.mtf.getPartitionName();
   }

   public String getApplicationName() {
      return this.mtf.getAppId();
   }

   public String getModuleName() {
      return this.mtf.getModuleId();
   }

   public long getCompletedThreadsCount() {
      return this.mtf.getCompletedThreadsCount();
   }

   public int getRunningThreadsCount() {
      return this.mtf.getRunningThreadsCount();
   }

   public long getRejectedNewThreadRequests() {
      return this.mtf.getRejectedNewThreadRequests();
   }
}
