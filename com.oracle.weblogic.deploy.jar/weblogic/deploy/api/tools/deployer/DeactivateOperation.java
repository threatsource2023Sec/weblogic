package weblogic.deploy.api.tools.deployer;

import weblogic.deploy.utils.MBeanHomeTool;

public class DeactivateOperation extends StopOperation {
   public DeactivateOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void execute() throws Exception {
      this.po = this.dm.deactivate(this.getTmids(), this.dOpts);
      this.postExecute();
   }

   public String getOperation() {
      return "deactivate";
   }
}
