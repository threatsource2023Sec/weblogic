package weblogic.application.compiler;

import weblogic.application.compiler.deploymentview.EditableDeployableObjectFactory;

public class DeploymentViewOptions {
   private final EditableDeployableObjectFactory objectFactory;
   private boolean disableLibraryMerge = false;
   private boolean verifyLibraryReferences = true;
   private boolean keepLibraryRegistrationOnExit = true;
   private boolean basicView = false;
   private boolean beanScaffoldingEnabled = false;

   DeploymentViewOptions(EditableDeployableObjectFactory objectFactory) {
      this.objectFactory = objectFactory;
   }

   public void disableLibraryMerge() {
      this.disableLibraryMerge = true;
   }

   public void disableLibraryVerification() {
      this.verifyLibraryReferences = false;
   }

   public void clearLibraryRegistrationOnExit() {
      this.keepLibraryRegistrationOnExit = false;
   }

   public void enableBasicView() {
      this.basicView = true;
   }

   public void enableBeanScaffolding() {
      this.beanScaffoldingEnabled = true;
   }

   void transferOptions(CompilerCtx ctx) {
      ctx.setObjectFactory(this.objectFactory);
      if (this.disableLibraryMerge) {
         ctx.disableLibraryMerge();
      }

      ctx.setVerifyLibraryReferences(this.verifyLibraryReferences);
      if (this.keepLibraryRegistrationOnExit) {
         ctx.keepLibraryRegistrationOnExit();
      }

      if (this.basicView) {
         ctx.setBasicView();
      }

      if (this.beanScaffoldingEnabled) {
         ctx.enableBeanScaffolding();
      }

   }
}
