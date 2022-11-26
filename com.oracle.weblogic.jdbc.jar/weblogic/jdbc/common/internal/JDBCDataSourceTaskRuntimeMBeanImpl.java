package weblogic.jdbc.common.internal;

import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCDataSourceTaskRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBeanImpl;

public class JDBCDataSourceTaskRuntimeMBeanImpl extends TaskRuntimeMBeanImpl implements JDBCDataSourceTaskRuntimeMBean {
   private static int num = 0;

   public JDBCDataSourceTaskRuntimeMBeanImpl(String nameArg, RuntimeMBean parentArg, boolean registerNow) throws ManagementException {
      super(nameArg + num++, parentArg, registerNow);
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public void setBeginTime(long beginTime) {
      this.beginTime = beginTime;
   }

   public void setEndTime(long endTime) {
      this.endTime = endTime;
   }

   public void setError(Exception error) {
      this.error = error;
   }

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else {
         return "SUCCESS".equals(this.getStatus()) ? "success" : "failed";
      }
   }

   public synchronized boolean isRunning() {
      return "PROCESSING".equals(this.getStatus());
   }
}
