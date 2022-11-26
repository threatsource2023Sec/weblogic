package weblogic.application;

public interface AppDeploymentExtensionFactory {
   AppDeploymentExtension createPreProcessorExtension(ApplicationContextInternal var1);

   AppDeploymentExtension createPostProcessorExtension(ApplicationContextInternal var1);
}
