package weblogic.application.internal;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.internal.flow.AdminModulesFlow;
import weblogic.application.internal.flow.AppDeploymentExtensionPostInitFlow;
import weblogic.application.internal.flow.AppDeploymentExtensionPostProcessorFlow;
import weblogic.application.internal.flow.AppDeploymentExtensionPreProcessorFlow;
import weblogic.application.internal.flow.ApplicationParamFlow;
import weblogic.application.internal.flow.ApplicationRuntimeMBeanDeactivationFlow;
import weblogic.application.internal.flow.ApplicationRuntimeMBeanFlow;
import weblogic.application.internal.flow.ApplicationRuntimeMBeanSetupFlow;
import weblogic.application.internal.flow.AvailabilityRegistrationFlow;
import weblogic.application.internal.flow.CheckLibraryReferenceFlow;
import weblogic.application.internal.flow.ClassLoaderIndexingFlow;
import weblogic.application.internal.flow.ComponentRuntimeStateFlow;
import weblogic.application.internal.flow.CreateAppDeploymentExtensionsFlow;
import weblogic.application.internal.flow.CreateModulesFlow;
import weblogic.application.internal.flow.CustomModuleProviderFlow;
import weblogic.application.internal.flow.DeploymentCallbackFlow;
import weblogic.application.internal.flow.DescriptorCacheDirFlow;
import weblogic.application.internal.flow.DescriptorFinderFlow;
import weblogic.application.internal.flow.DescriptorParsingFlow;
import weblogic.application.internal.flow.EarClassLoaderFlow;
import weblogic.application.internal.flow.EarClassLoaderUpdateFlow;
import weblogic.application.internal.flow.EnvContextFlow;
import weblogic.application.internal.flow.HeadLifecycleFlow;
import weblogic.application.internal.flow.HeadModuleRedeployFlow;
import weblogic.application.internal.flow.HeadVersionLifecycleFlow;
import weblogic.application.internal.flow.HeadWorkContextFlow;
import weblogic.application.internal.flow.ImportLibrariesFlow;
import weblogic.application.internal.flow.ImportOptionalPackagesFlow;
import weblogic.application.internal.flow.InitFastSwapLoaderFlow;
import weblogic.application.internal.flow.InitModulesFlow;
import weblogic.application.internal.flow.JACCPolicyConfigurationFlow;
import weblogic.application.internal.flow.JavaAppNamingFlow;
import weblogic.application.internal.flow.LibraryDirectoryFlow;
import weblogic.application.internal.flow.ModuleContextNameUpdateFlow;
import weblogic.application.internal.flow.ModuleContextSetupFlow;
import weblogic.application.internal.flow.OptionalPackageReferencerFlow;
import weblogic.application.internal.flow.ParseAnnotationsFlow;
import weblogic.application.internal.flow.PermissionsRegistrationFlow;
import weblogic.application.internal.flow.PojoAnnotationProcessingFlow;
import weblogic.application.internal.flow.ReferenceResolutionFlow;
import weblogic.application.internal.flow.RegistrationCompleteFlow;
import weblogic.application.internal.flow.SecurityRoleFlow;
import weblogic.application.internal.flow.SingletonServicesFlow;
import weblogic.application.internal.flow.StartModulesFlow;
import weblogic.application.internal.flow.StateFlow;
import weblogic.application.internal.flow.TailFreeMemoryFlow;
import weblogic.application.internal.flow.TailLifecycleFlow;
import weblogic.application.internal.flow.TailModuleRedeployFlow;
import weblogic.application.internal.flow.TailVersionLifecycleFlow;
import weblogic.application.internal.flow.TailWorkContextFlow;
import weblogic.application.internal.flow.WorkManagerFlow;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;

public final class EarDeployment extends BaseDeployment implements Deployment {
   private final Flow[] flow = this.initFlows();

   private Flow[] initFlows() {
      return new Flow[]{new HeadVersionLifecycleFlow(this.appCtx), new RegistrationCompleteFlow(this.appCtx), new HeadModuleRedeployFlow(this.appCtx), new ApplicationRuntimeMBeanFlow(this.appCtx), new OptionalPackageReferencerFlow(this.appCtx), new EarClassLoaderFlow(this.appCtx), new DescriptorCacheDirFlow(this.appCtx), new DescriptorParsingFlow(this.appCtx), new ImportLibrariesFlow(this.appCtx), new InitFastSwapLoaderFlow(this.appCtx), new EarClassLoaderUpdateFlow(this.appCtx), new LibraryDirectoryFlow(this.appCtx), new PermissionsRegistrationFlow(this.appCtx), new ParseAnnotationsFlow(this.appCtx), new CheckLibraryReferenceFlow(this.appCtx), new WorkManagerFlow(this.appCtx), new ApplicationParamFlow(this.appCtx), new EnvContextFlow(this.appCtx), new SecurityRoleFlow(this.appCtx), new CustomModuleProviderFlow(this.appCtx), new CreateModulesFlow(this.appCtx), new ModuleContextSetupFlow(this.appCtx), new CreateAppDeploymentExtensionsFlow(this.appCtx), new InitModulesFlow(this.appCtx), new AppDeploymentExtensionPostInitFlow(this.appCtx), new ClassLoaderIndexingFlow(this.appCtx), new AvailabilityRegistrationFlow(this.appCtx), new HeadLifecycleFlow(this.appCtx), new HeadWorkContextFlow(this.appCtx), new ApplicationRuntimeMBeanDeactivationFlow(this.appCtx), new ModuleContextNameUpdateFlow(this.appCtx), new AppDeploymentExtensionPreProcessorFlow(this.appCtx), new DeploymentCallbackFlow(this.appCtx), new AppDeploymentExtensionPostProcessorFlow(this.appCtx), new PojoAnnotationProcessingFlow(this.appCtx), new ReferenceResolutionFlow(this.appCtx), new JavaAppNamingFlow(this.appCtx), new DescriptorFinderFlow(this.appCtx), new JACCPolicyConfigurationFlow(this.appCtx), new StartModulesFlow(this.appCtx), new AdminModulesFlow(this.appCtx), new ApplicationRuntimeMBeanSetupFlow(this.appCtx), new CheckLibraryReferenceFlow(this.appCtx), new SingletonServicesFlow(this.appCtx), new ImportOptionalPackagesFlow(this.appCtx), new TailWorkContextFlow(this.appCtx), new ComponentRuntimeStateFlow(this.appCtx), new TailModuleRedeployFlow(this.appCtx), new TailLifecycleFlow(this.appCtx), new TailVersionLifecycleFlow(this.appCtx), new StateFlow(this.appCtx), new TailFreeMemoryFlow(this.appCtx)};
   }

   public EarDeployment(AppDeploymentMBean mbean, ApplicationArchive f) throws DeploymentException {
      super(mbean, f);
   }

   public EarDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      super(mbean, f);
   }

   public Flow[] getFlow() {
      return this.flow;
   }
}
