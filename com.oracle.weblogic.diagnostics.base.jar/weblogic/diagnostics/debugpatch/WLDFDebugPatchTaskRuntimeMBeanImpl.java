package weblogic.diagnostics.debugpatch;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBeanImpl;
import weblogic.management.runtime.WLDFDebugPatchTaskRuntimeMBean;

public class WLDFDebugPatchTaskRuntimeMBeanImpl extends TaskRuntimeMBeanImpl implements WLDFDebugPatchTaskRuntimeMBean {
   private boolean activation;
   private String patches;
   private String appName;
   private String moduleName;
   private String partitionName;
   private boolean cancelled;

   public WLDFDebugPatchTaskRuntimeMBeanImpl(String name, String patches, String appName, String moduleName, String partitionName, boolean activation) throws ManagementException {
      super(name, (RuntimeMBean)null, true);
      this.activation = activation;
      this.patches = patches;
      this.appName = appName;
      this.moduleName = moduleName;
      this.partitionName = partitionName;
   }

   public boolean isActivationTask() {
      return this.activation;
   }

   public String getPatches() {
      return this.patches;
   }

   public String getApplicationName() {
      return this.appName;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void setStatus(String status) {
      super.setStatus(status);
   }

   public void setBeginTime(long start) {
      super.setBeginTime(start);
   }

   public void setEndTime(long end) {
      super.setEndTime(end);
   }

   public void cancel() throws Exception {
      String st = this.getStatus();
      if (!"SCHEDULED".equals(st) && !"RUNNING".equals(st) && !"CANCELLED".equals(st)) {
         throw new IllegalStateException("Attempt to cancel task in status " + st);
      } else {
         this.cancelled = true;
      }
   }

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else {
         String st = this.getStatus();
         if ("SCHEDULED".equals(st)) {
            return "pending";
         } else {
            return "FINISHED".equals(st) ? "success" : "failed";
         }
      }
   }

   public void setError(Exception ex) {
      super.setError(ex);
   }

   public boolean isCancelled() {
      return this.cancelled;
   }
}
