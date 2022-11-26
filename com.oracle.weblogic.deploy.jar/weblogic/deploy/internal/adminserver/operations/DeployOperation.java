package weblogic.deploy.internal.adminserver.operations;

import java.security.AccessController;
import weblogic.deploy.api.internal.DeploymentValidationMessagesTextFormatter;
import weblogic.deployment.configuration.DeploymentValidationPlugin;
import weblogic.deployment.configuration.ValidationResult;
import weblogic.deployment.configuration.internal.DeploymentValidationContextImpl;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DeploymentConfigurationMBean;
import weblogic.management.configuration.DeploymentValidationPluginMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ParameterMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.management.deploy.internal.TaskRuntimeValidator;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DeployOperation extends ActivateOperation {
   private static final DeployTaskRuntimeValidator taskRuntimeValidator = new DeployTaskRuntimeValidator();
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static Class pluginClass = null;
   private static String classname = null;
   private String newClassname = null;
   private DeploymentValidationPlugin plugin = null;
   private ParameterMBean[] params = null;
   private static DeploymentValidationMessagesTextFormatter deploymentValidationMessages = DeploymentValidationMessagesTextFormatter.getInstance();

   public DeployOperation() throws ManagementException {
      this.taskType = 11;
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      DeploymentConfigurationMBean deplConf = domain.getDeploymentConfiguration();
      if (deplConf != null) {
         DeploymentValidationPluginMBean validationPlugin = deplConf.getDeploymentValidationPlugin();
         if (validationPlugin != null) {
            this.newClassname = validationPlugin.getFactoryClassname();
            if (this.newClassname == null) {
               classname = null;
               this.plugin = null;
               pluginClass = null;
            }

            this.params = validationPlugin.getParameters();
            if (this.newClassname != null && this.newClassname != classname) {
               try {
                  Class clz = Class.forName(this.newClassname);
                  if (!DeploymentValidationPlugin.class.isAssignableFrom(clz)) {
                     return;
                  }

                  pluginClass = clz;
                  classname = this.newClassname;
               } catch (Throwable var6) {
                  throw new ManagementException(deploymentValidationMessages.deploymentValidationPluginClassNotFound(classname), var6);
               }
            }

            if (pluginClass != null) {
               try {
                  Object obj = pluginClass.newInstance();
                  if (obj instanceof DeploymentValidationPlugin) {
                     this.plugin = (DeploymentValidationPlugin)obj;
                  }
               } catch (Throwable var5) {
                  throw new ManagementException(deploymentValidationMessages.deploymentValidationPluginClassNotFound(classname), var5);
               }
            }
         }
      }

   }

   public DeployOperation(boolean redeployWithSource) {
      super(redeployWithSource);
      this.taskType = 11;
   }

   protected AppDeploymentMBean updateConfiguration(String source, String name, String stagingMode, DeploymentData info, String id, boolean callerOwnsEditLock) throws ManagementException {
      AppDeploymentMBean appMBean = super.updateConfiguration(source, name, stagingMode, info, id, callerOwnsEditLock);
      if (this.plugin != null) {
         this.plugin.initialize(this.params);
         ValidationResult result = this.plugin.validate(new DeploymentValidationContextImpl(appMBean));
         if (!result.isDeploymentValid()) {
            throw new ManagementException(deploymentValidationMessages.deploymentValidationFailure(), result.getException());
         }
      }

      return appMBean;
   }

   protected AbstractOperation createCopy() {
      try {
         return new DeployOperation();
      } catch (ManagementException var2) {
         throw new RuntimeException(var2);
      }
   }

   protected void checkVersionSupport(DeploymentData info, String name, String deprecatedOp) throws ManagementException {
   }

   protected int getCreateTaskType() {
      return 11;
   }

   protected TaskRuntimeValidator getTaskRuntimeValidator() {
      return taskRuntimeValidator;
   }

   private static class DeployTaskRuntimeValidator implements TaskRuntimeValidator {
      private DeployTaskRuntimeValidator() {
      }

      public void validate(DeploymentTaskRuntime runningTaskRuntime, DeploymentTaskRuntime newTaskRuntime) throws ManagementException {
         TaskRuntimeValidatorHelper.validateDifferentOperation(runningTaskRuntime, newTaskRuntime);
         TaskRuntimeValidatorHelper.validateDeployOperation(runningTaskRuntime, newTaskRuntime);
      }

      // $FF: synthetic method
      DeployTaskRuntimeValidator(Object x0) {
         this();
      }
   }
}
