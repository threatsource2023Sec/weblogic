package weblogic.deploy.api.tools.deployer;

import weblogic.deploy.utils.MBeanHomeTool;

public class UnprepareOperation extends StopOperation {
   public UnprepareOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public String getOperation() {
      return "unprepare";
   }

   protected final void doExecute() throws Exception {
      this.po = this.dm.unprepare(this.getTmids(), this.dOpts);
   }
}
