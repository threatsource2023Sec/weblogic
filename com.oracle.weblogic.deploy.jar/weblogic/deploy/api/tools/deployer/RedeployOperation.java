package weblogic.deploy.api.tools.deployer;

import weblogic.deploy.utils.MBeanHomeTool;

public class RedeployOperation extends Jsr88Operation {
   public RedeployOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void setAllowedOptions() throws IllegalArgumentException {
      super.setAllowedOptions();
      this.allowedOptions.add("adminmode");
      this.allowedOptions.add("source");
      this.allowedOptions.add("retiretimeout");
      this.allowedOptions.add("plan");
      this.allowedOptions.add("stage");
      this.allowedOptions.add("nostage");
      this.allowedOptions.add("external_stage");
      this.allowedOptions.add("planstage");
      this.allowedOptions.add("plannostage");
      this.allowedOptions.add("planexternal_stage");
      this.allowedOptions.add("upload");
      this.allowedOptions.add("delete_files");
      this.allowedOptions.add("graceful");
      this.allowedOptions.add("ignoresessions");
      this.allowedOptions.add("rmigraceperiod");
      this.allowedOptions.add("defaultsubmoduletargets");
      this.allowedOptions.add("nodefaultsubmoduletargets");
      this.allowedOptions.add("noversion");
      this.allowedOptions.add("usenonexclusivelock");
      this.allowedOptions.add("removePlanOverride");
   }

   public void validate() throws IllegalArgumentException, DeployerException {
      super.validate();
   }

   protected void validateName() throws IllegalArgumentException {
      if (this.options.name == null) {
         throw new IllegalArgumentException(cat.errorMissingName());
      }
   }

   protected void validateSource() throws IllegalArgumentException {
      if (this.isSourceRequired() && this.options.source == null) {
         throw new IllegalArgumentException(cat.errorNoSourceSpecified());
      } else {
         if (this.options.sourceFromArgs) {
            this.options.sourceFromArgs = false;
            this.options.source = null;
            if (this.options.name == null) {
               this.options.delta = null;
            }
         }

      }
   }

   protected boolean isSourceRequired() {
      return this.options.upload && this.options.plan == null;
   }

   protected void validateDelta() throws IllegalArgumentException {
      if (this.options.delta != null && this.options.sourceFromOpts && this.options.delta.length > 0 & !this.options.upload) {
         throw new IllegalArgumentException(cat.errorFilesIllegal());
      }
   }

   public void execute() throws Exception {
      if (this.options.delta != null && !this.options.sourceFromArgs) {
         if (this.options.deleteFiles) {
            this.po = this.dm.undeploy(this.getTmids(), this.src, this.options.delta, this.dOpts);
         } else {
            this.po = this.dm.redeploy(this.getTmids(), this.src, this.options.delta, this.dOpts);
         }
      } else {
         this.po = this.dm.redeploy(this.getTmids(), this.src, this.plan, this.dOpts);
      }

      this.postExecute();
   }

   public String getOperation() {
      return "redeploy";
   }
}
