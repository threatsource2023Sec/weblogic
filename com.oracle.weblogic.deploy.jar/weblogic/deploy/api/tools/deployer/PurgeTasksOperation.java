package weblogic.deploy.api.tools.deployer;

import java.util.Arrays;
import java.util.Comparator;
import weblogic.deploy.utils.MBeanHomeTool;

public class PurgeTasksOperation extends TaskOperation {
   private String[] retiredTaskIds = null;

   public PurgeTasksOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void setAllowedOptions() {
   }

   public void execute() throws Exception {
      try {
         this.retiredTaskIds = this.helper.getDeployer().purgeRetiredTasks();
      } catch (Throwable var3) {
         String msg = cat.errorGettingDeployerRuntime();
         throw new RuntimeException(msg, var3);
      }
   }

   public int report() {
      StringBuffer sb = new StringBuffer();
      if (this.retiredTaskIds != null && this.retiredTaskIds.length != 0) {
         Arrays.sort(this.retiredTaskIds, new Comparator() {
            public int compare(Object o1, Object o2) {
               String id1 = (String)o1;
               String id2 = (String)o2;
               return id1.compareTo(id2);
            }
         });

         for(int i = 0; i < this.retiredTaskIds.length; ++i) {
            if (i > 0) {
               sb.append(", ");
            }

            sb.append(this.retiredTaskIds[i]);
         }

         System.out.println(cat.showRetiredTasks(sb.toString()));
      } else {
         System.out.println(cat.showNoRetiredTasks());
      }

      return 0;
   }

   public String getOperation() {
      return "purgetasks";
   }
}
