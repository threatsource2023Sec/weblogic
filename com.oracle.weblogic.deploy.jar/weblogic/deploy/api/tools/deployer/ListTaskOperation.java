package weblogic.deploy.api.tools.deployer;

import java.util.Arrays;
import java.util.Comparator;
import weblogic.deploy.utils.MBeanHomeTool;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;

public class ListTaskOperation extends TaskOperation {
   private DeploymentTaskRuntimeMBean[] tasks = null;

   public ListTaskOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void setAllowedOptions() {
      this.allowedOptions.add("id");
   }

   public void execute() throws Exception {
      if (this.options.id != null) {
         this.task = this.helper.getTaskByID(this.options.id);
         if (this.task == null) {
            throw new DeployerException(cat.errorTaskNotFound(this.options.id));
         }
      } else {
         this.tasks = this.helper.getAllTasks();
         Arrays.sort(this.tasks, new Comparator() {
            public int compare(Object o1, Object o2) {
               DeploymentTaskRuntimeMBean t1 = (DeploymentTaskRuntimeMBean)o1;
               DeploymentTaskRuntimeMBean t2 = (DeploymentTaskRuntimeMBean)o2;
               return t1.getId().compareTo(t2.getId());
            }
         });
      }

   }

   public int report() {
      if (this.tasks != null) {
         for(int i = 0; i < this.tasks.length; ++i) {
            this.showTaskInformation(this.tasks[i]);
         }
      } else if (this.task != null) {
         if (this.options.formatted) {
            this.showTaskInformationHeader();
         }

         this.showTaskInformation(this.task);
      }

      return 0;
   }

   public String getOperation() {
      return this.options.listOp ? "list" : "listtask";
   }
}
