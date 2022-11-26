package weblogic.deploy.api.tools.deployer;

import weblogic.deploy.utils.MBeanHomeTool;

public class RemoveOperation extends UndeployOperation {
   public RemoveOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void execute() throws Exception {
      this.po = this.dm.remove(this.getTmids(), this.dOpts);
      this.postExecute();
   }

   public String getOperation() {
      return "remove";
   }
}
