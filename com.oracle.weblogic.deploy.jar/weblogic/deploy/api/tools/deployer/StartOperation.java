package weblogic.deploy.api.tools.deployer;

import weblogic.deploy.utils.MBeanHomeTool;

public class StartOperation extends Jsr88Operation {
   public StartOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void setAllowedOptions() throws IllegalArgumentException {
      super.setAllowedOptions();
      this.allowedOptions.add("retiretimeout");
      this.allowedOptions.add("adminmode");
   }

   protected void validateName() throws IllegalArgumentException {
      if (this.options.name == null) {
         throw new IllegalArgumentException(cat.errorMissingName());
      }
   }

   public void execute() throws Exception {
      this.po = this.dm.start(this.getTmids(), this.dOpts);
      this.postExecute();
   }

   public String getOperation() {
      return "start";
   }
}
