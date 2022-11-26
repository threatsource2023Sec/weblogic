package weblogic.server;

import javax.inject.Inject;
import javax.inject.Provider;
import weblogic.management.ManagementException;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.AggregateProgressMBean;
import weblogic.management.runtime.ProgressMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.utils.progress.SubsystemProgressListener;

public class SubsystemProgressListenerImpl implements SubsystemProgressListener {
   @Inject
   private Provider runtimeAccess;

   public void subsystemAdded(String subsystemName) {
      try {
         ServerRuntimeMBean serverRuntime = ((RuntimeAccess)this.runtimeAccess.get()).getServerRuntime();
         AggregateProgressMBean aggregateProcessMBean = serverRuntime.getAggregateProgress();
         aggregateProcessMBean.createProgress(subsystemName);
      } catch (ManagementException var4) {
      }

   }

   public void subsystemRemoved(String subsystemName) {
      try {
         ServerRuntimeMBean serverRuntime = ((RuntimeAccess)this.runtimeAccess.get()).getServerRuntime();
         AggregateProgressMBean aggregateProcessMBean = serverRuntime.getAggregateProgress();
         ProgressMBean pBean = aggregateProcessMBean.lookupProgress(subsystemName);
         aggregateProcessMBean.destroyProgress(pBean);
      } catch (ManagementException var5) {
      }

   }
}
