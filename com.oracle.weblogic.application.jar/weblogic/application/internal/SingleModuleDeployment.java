package weblogic.application.internal;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.Module;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.internal.flow.AppDeploymentExtensionPostInitFlow;
import weblogic.application.internal.flow.AppDeploymentExtensionPostProcessorFlow;
import weblogic.application.internal.flow.AppDeploymentExtensionPreProcessorFlow;
import weblogic.application.internal.flow.ApplicationRuntimeMBeanDeactivationFlow;
import weblogic.application.internal.flow.ApplicationRuntimeMBeanFlow;
import weblogic.application.internal.flow.ApplicationRuntimeMBeanSetupFlow;
import weblogic.application.internal.flow.AvailabilityRegistrationFlow;
import weblogic.application.internal.flow.CheckLibraryReferenceFlow;
import weblogic.application.internal.flow.ClassLoaderIndexingFlow;
import weblogic.application.internal.flow.ComponentRuntimeStateFlow;
import weblogic.application.internal.flow.CreateAppDeploymentExtensionsFlow;
import weblogic.application.internal.flow.DeploymentCallbackFlow;
import weblogic.application.internal.flow.DescriptorCacheDirFlow;
import weblogic.application.internal.flow.DescriptorFinderFlow;
import weblogic.application.internal.flow.EnvContextFlow;
import weblogic.application.internal.flow.HeadLifecycleFlow;
import weblogic.application.internal.flow.HeadWorkContextFlow;
import weblogic.application.internal.flow.ImportOptionalPackagesFlow;
import weblogic.application.internal.flow.InitModulesFlow;
import weblogic.application.internal.flow.JACCPolicyConfigurationFlow;
import weblogic.application.internal.flow.ModuleContextNameUpdateFlow;
import weblogic.application.internal.flow.ModuleContextSetupFlow;
import weblogic.application.internal.flow.OptionalPackageReferencerFlow;
import weblogic.application.internal.flow.PojoAnnotationProcessingFlow;
import weblogic.application.internal.flow.ReferenceResolutionFlow;
import weblogic.application.internal.flow.RegistrationCompleteFlow;
import weblogic.application.internal.flow.SingleModuleClassLoaderFlow;
import weblogic.application.internal.flow.SingleModuleContainerFlow;
import weblogic.application.internal.flow.SingletonServicesFlow;
import weblogic.application.internal.flow.StartModulesFlow;
import weblogic.application.internal.flow.StateFlow;
import weblogic.application.internal.flow.TailFreeMemoryFlow;
import weblogic.application.internal.flow.TailLifecycleFlow;
import weblogic.application.internal.flow.TailWorkContextFlow;
import weblogic.application.internal.flow.WorkManagerFlow;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;

public abstract class SingleModuleDeployment extends BaseDeployment implements Deployment {
   private final Flow[] flow;

   public SingleModuleDeployment(SystemResourceMBean mbean, Module module, File f) throws DeploymentException {
      super(mbean, f);
      this.flow = this.initFlow(module);
   }

   /** @deprecated */
   @Deprecated
   public SingleModuleDeployment(SystemResourceMBean mbean, Module module, ApplicationArchive f) throws DeploymentException {
      super(mbean, f);
      this.flow = this.initFlow(module);
   }

   public SingleModuleDeployment(AppDeploymentMBean mbean, Module module, File f) throws DeploymentException {
      super(mbean, f);
      this.flow = this.initFlow(module);
   }

   /** @deprecated */
   @Deprecated
   public SingleModuleDeployment(AppDeploymentMBean mbean, Module module, ApplicationArchive f) throws DeploymentException {
      super(mbean, f);
      this.flow = this.initFlow(module);
   }

   private Flow[] initFlow(Module module) throws DeploymentException {
      return new Flow[]{new RegistrationCompleteFlow(this.appCtx), new ApplicationRuntimeMBeanFlow(this.appCtx), new OptionalPackageReferencerFlow(this.appCtx), new DescriptorCacheDirFlow(this.appCtx), new SingleModuleClassLoaderFlow(this.appCtx), new WorkManagerFlow(this.appCtx), new EnvContextFlow(this.appCtx), new HeadLifecycleFlow(this.appCtx), new HeadWorkContextFlow(this.appCtx), new SingleModuleContainerFlow(this.appCtx, module), new ModuleContextSetupFlow(this.appCtx), new CreateAppDeploymentExtensionsFlow(this.appCtx), new InitModulesFlow(this.appCtx), new AppDeploymentExtensionPostInitFlow(this.appCtx), new ClassLoaderIndexingFlow(this.appCtx), new ApplicationRuntimeMBeanDeactivationFlow(this.appCtx), new ModuleContextNameUpdateFlow(this.appCtx), new AppDeploymentExtensionPreProcessorFlow(this.appCtx), new DeploymentCallbackFlow(this.appCtx), new AppDeploymentExtensionPostProcessorFlow(this.appCtx), new AvailabilityRegistrationFlow(this.appCtx), new PojoAnnotationProcessingFlow(this.appCtx), new ReferenceResolutionFlow(this.appCtx), new DescriptorFinderFlow(this.appCtx), new JACCPolicyConfigurationFlow(this.appCtx), new StartModulesFlow(this.appCtx), new ApplicationRuntimeMBeanSetupFlow(this.appCtx), new SingletonServicesFlow(this.appCtx), new TailWorkContextFlow(this.appCtx), new TailLifecycleFlow(this.appCtx), new CheckLibraryReferenceFlow(this.appCtx), new ImportOptionalPackagesFlow(this.appCtx), new ComponentRuntimeStateFlow(this.appCtx), new StateFlow(this.appCtx), new TailFreeMemoryFlow(this.appCtx)};
   }

   protected Flow[] getFlow() {
      return this.flow;
   }
}
