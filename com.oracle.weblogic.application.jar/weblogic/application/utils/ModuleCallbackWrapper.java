package weblogic.application.utils;

import weblogic.application.AppDeploymentExtension;
import weblogic.application.AppDeploymentExtensionFactory;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFactoryManager;
import weblogic.management.DeploymentException;

public abstract class ModuleCallbackWrapper {
   private boolean enabled = true;

   public ModuleCallbackWrapper() {
      ApplicationFactoryManager.getApplicationFactoryManager().addAppDeploymentExtensionFactory(new Factory());
   }

   public void preparePhaseComplete(ApplicationContextInternal appCtx) {
   }

   public void disable() {
      this.enabled = false;
   }

   private class PostProcessor extends BaseAppDeploymentExtension {
      private final String name;
      private final PreProcessor matchingPreprocessor;

      PostProcessor(String name, PreProcessor preProcessor) {
         this.name = name;
         this.matchingPreprocessor = preProcessor;
      }

      public String getName() {
         return this.name;
      }

      public void prepare(ApplicationContextInternal appCtx) throws DeploymentException {
         ModuleCallbackWrapper.this.preparePhaseComplete(appCtx);
         this.matchingPreprocessor.callbackInvoked = true;
         super.prepare(appCtx);
      }
   }

   private class PreProcessor extends BaseAppDeploymentExtension {
      private final String name;
      private boolean callbackInvoked = false;

      PreProcessor(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public void unprepare(ApplicationContextInternal appCtx) throws DeploymentException {
         if (!this.callbackInvoked) {
            ModuleCallbackWrapper.this.preparePhaseComplete(appCtx);
         }

         super.unprepare(appCtx);
      }
   }

   private class Factory implements AppDeploymentExtensionFactory {
      private PreProcessor preProcessor;

      private Factory() {
         this.preProcessor = null;
      }

      public AppDeploymentExtension createPostProcessorExtension(ApplicationContextInternal appCtx) {
         if (ModuleCallbackWrapper.this.enabled) {
            assert this.preProcessor != null;

            PostProcessor postProcessor = ModuleCallbackWrapper.this.new PostProcessor("Prepare Wrapper for " + appCtx.getApplicationId(), this.preProcessor);
            this.preProcessor = null;
            return postProcessor;
         } else {
            return null;
         }
      }

      public AppDeploymentExtension createPreProcessorExtension(ApplicationContextInternal appCtx) {
         if (ModuleCallbackWrapper.this.enabled) {
            assert this.preProcessor == null;

            this.preProcessor = ModuleCallbackWrapper.this.new PreProcessor("Prepare Wrapper for " + appCtx.getApplicationId());
            return this.preProcessor;
         } else {
            return null;
         }
      }

      // $FF: synthetic method
      Factory(Object x1) {
         this();
      }
   }
}
