package weblogic.deploy.api.tools.deployer;

import java.io.Serializable;
import weblogic.deploy.utils.MBeanHomeTool;

public class ActivateOperation extends DeployOperation implements Serializable {
   private static final long serialVersionUID = 1L;

   public ActivateOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void execute() throws Exception {
      this.po = this.dm.activate(this.getTmids(), this.src, this.plan, this.dOpts);
      this.postExecute();
   }

   public String getOperation() {
      return "activate";
   }
}
