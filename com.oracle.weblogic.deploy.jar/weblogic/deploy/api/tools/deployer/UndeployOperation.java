package weblogic.deploy.api.tools.deployer;

import weblogic.deploy.utils.MBeanHomeTool;

public class UndeployOperation extends Jsr88Operation {
   public UndeployOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void setAllowedOptions() throws IllegalArgumentException {
      super.setAllowedOptions();
      this.allowedOptions.add("graceful");
      this.allowedOptions.add("ignoresessions");
      this.allowedOptions.add("rmigraceperiod");
      this.allowedOptions.add("allversions");
      this.allowedOptions.add("usenonexclusivelock");
   }

   protected void validateName() throws IllegalArgumentException {
      if (this.options.name == null) {
         throw new IllegalArgumentException(cat.errorMissingName());
      }
   }

   public void execute() throws Exception {
      this.po = this.dm.undeploy(this.getTmids(), this.dOpts);
      this.postExecute();
   }

   public String getOperation() {
      return "undeploy";
   }
}
