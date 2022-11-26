package weblogic.management.deploy;

import weblogic.application.utils.XMLWriter;
import weblogic.deploy.internal.diagnostics.ImageProvider;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;

public class DeploymentTaskImageProvider extends ImageProvider {
   public void writeDiagnosticImage(XMLWriter writer) {
      if (isAdminServer) {
         DeployerRuntimeMBean deployerRuntime = DeployerRuntime.getDeployerRuntime();
         DeploymentTaskRuntimeMBean[] tasks = deployerRuntime.list();
         if (tasks == null) {
            writer.addElement("deployment-task-count", "0");
         } else {
            writer.addElement("deployment-task-count", "" + tasks.length);

            for(int i = 0; i < tasks.length && !this.timedOut; ++i) {
               writer.addElement("deployment-task");
               writer.addElement("id", tasks[i].getId());
               writer.addElement("status", this.getState(tasks[i].getState()));
               DeploymentTaskRuntime.DeploymentAction op = DeploymentTaskRuntime.DeploymentAction.getDeploymentAction(tasks[i].getTask());
               writer.addElement("operation", op.getDescription());
               writer.addElement("application-name", tasks[i].getApplicationName());
               writer.closeElement();
               writer.flush();
            }

         }
      }
   }

   private String getState(int taskState) {
      switch (taskState) {
         case 0:
            return "initialized";
         case 1:
            return "running";
         case 2:
            return "completed";
         case 3:
            return "failed";
         case 4:
            return "deferred";
         default:
            return "unknown";
      }
   }
}
