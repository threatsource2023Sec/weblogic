package weblogic.diagnostics.archive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.accessor.runtime.DataRetirementTaskRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.EditableArchiveRuntimeMBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class DiagnosticEditableArchiveRuntime extends DiagnosticArchiveRuntime implements EditableArchiveRuntimeMBean {
   private List retirementTasks = new ArrayList();

   public DiagnosticEditableArchiveRuntime(EditableDataArchive archive, RuntimeMBean parent) throws ManagementException {
      super(archive, parent);
   }

   public int getDataRetirementCycles() {
      return ((EditableDataArchive)this.archive).getDataRetirementCycles();
   }

   public long getDataRetirementTotalTime() {
      return ((EditableDataArchive)this.archive).getDataRetirementTotalTime();
   }

   public long getLastDataRetirementStartTime() {
      return ((EditableDataArchive)this.archive).getLastDataRetirementStartTime();
   }

   public long getLastDataRetirementTime() {
      return ((EditableDataArchive)this.archive).getLastDataRetirementTime();
   }

   public long getRetiredRecordCount() {
      return ((EditableDataArchive)this.archive).getRetiredRecordCount();
   }

   public DataRetirementTaskRuntimeMBean performRetirement() throws ManagementException {
      DataRetirementTaskRuntimeMBean task = null;

      try {
         task = DataRetirementScheduler.getInstance().scheduleDataRetirementTask((EditableDataArchive)this.archive);
         ((RuntimeMBeanDelegate)task).setRestParent(this);
         synchronized(this.retirementTasks) {
            this.retirementTasks.add(task);
            return task;
         }
      } catch (Exception var5) {
         throw new ManagementException(var5);
      }
   }

   public DataRetirementTaskRuntimeMBean[] getDataRetirementTasks() throws ManagementException {
      synchronized(this.retirementTasks) {
         DataRetirementTaskRuntimeMBean[] tasks = new DataRetirementTaskRuntimeMBean[this.retirementTasks.size()];
         tasks = (DataRetirementTaskRuntimeMBean[])this.retirementTasks.toArray(tasks);
         return tasks;
      }
   }

   public int purgeDataRetirementTasks(long completedBefore) throws ManagementException {
      int count = false;
      synchronized(this.retirementTasks) {
         List removeList = new ArrayList();
         Iterator var6 = this.retirementTasks.iterator();

         while(var6.hasNext()) {
            DataRetirementTaskRuntimeMBean task = (DataRetirementTaskRuntimeMBean)var6.next();
            String status = task.getStatus();
            if (!"Pending".equals(status) && !"Executing".equals(status)) {
               if (task.getEndTime() < completedBefore) {
                  removeList.add(task);
               }

               try {
                  ((RuntimeMBeanDelegate)task).unregister();
               } catch (ManagementException var11) {
               }
            }
         }

         int count = removeList.size();
         if (count > 0) {
            this.retirementTasks.removeAll(removeList);
         }

         return count;
      }
   }
}
