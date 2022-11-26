package weblogic.deploy.api.tools.deployer;

import weblogic.deploy.utils.MBeanHomeTool;

public class StopOperation extends Jsr88Operation {
   public StopOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void setAllowedOptions() throws IllegalArgumentException {
      super.setAllowedOptions();
      this.allowedOptions.add("graceful");
      this.allowedOptions.add("ignoresessions");
      this.allowedOptions.add("rmigraceperiod");
      this.allowedOptions.add("adminmode");
   }

   protected void validateName() throws IllegalArgumentException {
      if (this.options.name == null) {
         throw new IllegalArgumentException(cat.errorMissingName());
      }
   }

   public void execute() throws Exception {
      this.doExecute();
      this.postExecute();
   }

   public String getOperation() {
      return "stop";
   }

   protected void doExecute() throws Exception {
      this.po = this.dm.stop(this.getTmids(), this.dOpts);
   }
}
