package weblogic.deploy.api.tools.deployer;

import weblogic.deploy.utils.MBeanHomeTool;

public class ExtendLoaderOperation extends Jsr88Operation {
   public ExtendLoaderOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void setAllowedOptions() throws IllegalArgumentException {
      super.setAllowedOptions();
      this.allowedOptions.add("upload");
      this.allowedOptions.add("enableSecurityValidation");
      this.allowedOptions.add("source");
      this.allowedOptions.add("securityModel");
      this.allowedOptions.add("stage");
      this.allowedOptions.add("nostage");
   }

   protected boolean isSourceRequired() {
      return true;
   }

   public void execute() throws Exception {
      this.po = this.dm.appendToExtensionLoader(this.getTmids(), this.src, this.dOpts);
      this.postExecute();
   }

   public String getOperation() {
      return "extendloader";
   }
}
