package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.spi.RoleDeployerProvider;
import weblogic.security.spi.ApplicationInfo;
import weblogic.security.spi.DeployHandleCreationException;
import weblogic.security.spi.DeployRoleHandle;
import weblogic.security.spi.DeployableRoleProviderV2;
import weblogic.security.spi.Resource;
import weblogic.security.spi.RoleCreationException;
import weblogic.security.spi.RoleRemovalException;
import weblogic.security.spi.SecurityProvider;

public class RoleDeployerProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.RoleDeploymentService");
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".init()");
      }

      RoleDeployerProviderConfig myconfig = (RoleDeployerProviderConfig)config;
      boolean bParallel = myconfig.isSupportParallelDeploy();
      long depolyTimeout = myconfig.getDeployTimeout();
      SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getRoleProviderName());
      if (provider instanceof DeployableRoleProviderV2) {
         if (PolicyDeployerProviderImpl.enableNewCSSPolicyDeploymentProcess()) {
            V2AdapterExt aV2 = new V2AdapterExt((DeployableRoleProviderV2)provider);
            aV2.setLog(this.logger);
            aV2.setSupportParallelDeploy(bParallel);
            aV2.setBlockTime(depolyTimeout);
            return aV2;
         } else {
            return new V2Adapter((DeployableRoleProviderV2)provider);
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getNotInstanceof("DeployableRoleProvider/V2"));
      }
   }

   public void shutdown() {
   }

   private class V2AdapterExt extends AdapterRoleBase implements RoleDeployerProvider {
      private DeployableRoleProviderV2 v2;

      private V2AdapterExt(DeployableRoleProviderV2 v2) {
         super();
         this.v2 = v2;
      }

      public RoleDeployerProvider.DeploymentHandler startDeployRoles(ApplicationInfo application) throws DeployHandleCreationException {
         RoleDeployerProvider.DeploymentHandler dplhandle = null;
         if (this.bParallelProvider) {
            if (RoleDeployerProviderImpl.this.logger.isDebugEnabled()) {
               RoleDeployerProviderImpl.this.logger.debug("startDeployRoles: parallel, application is " + application);
            }

            dplhandle = new DeploymentHandlerImpl(this.v2.startDeployRoles(application));
         } else {
            DeploymentHandlerImpl wHd = new DeploymentHandlerImpl();

            try {
               if (RoleDeployerProviderImpl.this.logger.isDebugEnabled()) {
                  RoleDeployerProviderImpl.this.logger.debug("startDeployRoles: serial,  application is " + application);
               }

               dplhandle = (RoleDeployerProvider.DeploymentHandler)this.startDeployPoliciesBlock(application, wHd);
            } catch (Exception var5) {
               this.processCreationException(wHd, var5);
               return null;
            }
         }

         if (RoleDeployerProviderImpl.this.logger.isDebugEnabled()) {
            RoleDeployerProviderImpl.this.logger.debug("Finished startDeployRoles for application: " + application + ", the handle is " + dplhandle);
         }

         return (RoleDeployerProvider.DeploymentHandler)dplhandle;
      }

      protected Object startPoliciesV(ApplicationInfo application) throws DeployHandleCreationException {
         return this.v2.startDeployRoles(application);
      }

      public void deleteApplicationRoles(ApplicationInfo application) throws RoleRemovalException {
         if (this.bParallelProvider) {
            this.v2.deleteApplicationRoles(application);
         } else {
            this.delApplicationRoleBlocking(application);
         }

         if (RoleDeployerProviderImpl.this.logger.isDebugEnabled()) {
            RoleDeployerProviderImpl.this.logger.debug("Finished deleteApplicationRoles for application: " + application);
         }

      }

      protected void deleteApplicationRoleCallBack(ApplicationInfo application) throws RoleRemovalException {
         this.v2.deleteApplicationRoles(application);
      }

      // $FF: synthetic method
      V2AdapterExt(DeployableRoleProviderV2 x1, Object x2) {
         this(x1);
      }

      private class DeploymentHandlerImpl implements RoleDeployerProvider.DeploymentHandler, PolicyDeployerProviderImpl.Setable {
         private DeployRoleHandle handle;

         DeploymentHandlerImpl() {
         }

         DeploymentHandlerImpl(DeployRoleHandle handle) {
            this.handle = handle;
         }

         public void setHandle(Object handle) {
            this.handle = (DeployRoleHandle)handle;
         }

         public void deployRole(Resource resource, String roleName, String[] userAndGroupNames) throws RoleCreationException {
            try {
               if (RoleDeployerProviderImpl.this.logger.isDebugEnabled()) {
                  RoleDeployerProviderImpl.this.logger.debug("***** deploy Role = " + resource + ", roleNames= " + roleName + ", userAndGroupNames= " + userAndGroupNames);
               }

               V2AdapterExt.this.checkDeployHandleActive(this);
               V2AdapterExt.this.v2.deployRole(this.handle, resource, roleName, userAndGroupNames);
            } catch (Exception var5) {
               V2AdapterExt.this.processRoleException(this, var5);
            }

         }

         public void endDeployRoles() throws RoleCreationException {
            try {
               if (RoleDeployerProviderImpl.this.logger.isDebugEnabled()) {
                  RoleDeployerProviderImpl.this.logger.debug("***** start endDeployRoles for handle " + this);
               }

               V2AdapterExt.this.checkDeployHandleActive(this);
               V2AdapterExt.this.v2.endDeployRoles(this.handle);
            } catch (Exception var2) {
               V2AdapterExt.this.processRoleException(this, var2);
            }

            if (!V2AdapterExt.this.bParallelProvider) {
               V2AdapterExt.this.notifyNextDeployCycle(this);
            }

            if (RoleDeployerProviderImpl.this.logger.isDebugEnabled()) {
               RoleDeployerProviderImpl.this.logger.debug("***** done endDeployRoles for handle " + this);
            }

         }

         public void undeployAllRoles() throws RoleRemovalException {
            try {
               if (RoleDeployerProviderImpl.this.logger.isDebugEnabled()) {
                  RoleDeployerProviderImpl.this.logger.debug("*****  undeployAllRoles for handle " + this);
               }

               V2AdapterExt.this.checkDeployHandleActive(this);
               V2AdapterExt.this.v2.undeployAllRoles(this.handle);
            } catch (Exception var2) {
               V2AdapterExt.this.processRemoveRoleException(this, var2);
            }

         }
      }
   }

   abstract class AdapterRoleBase extends PolicyDeployerProviderImpl.AdapterBase {
      protected void deleteApplicationRoleCallBack(ApplicationInfo application) throws RoleRemovalException {
      }

      protected synchronized void delApplicationRoleBlocking(ApplicationInfo application) throws RoleRemovalException {
         try {
            while(this.isDeploymentProcessGoingOn()) {
               this.wait(this.blockTimeout * 3L);
            }

            this.deleteApplicationRoleCallBack(application);
         } catch (InterruptedException var3) {
            throw new RoleRemovalException(var3);
         }
      }

      protected void processRoleException(Object obj, Exception e) throws RoleCreationException {
         if (!this.bParallelProvider) {
            this.notifyNextDeployCycle(obj);
         }

         if (e instanceof RoleCreationException) {
            throw (RoleCreationException)e;
         } else {
            RoleCreationException re = new RoleCreationException(e.getMessage(), e);
            throw re;
         }
      }

      protected void processRemoveRoleException(Object obj, Exception e) throws RoleRemovalException {
         if (!this.bParallelProvider) {
            this.notifyNextDeployCycle(obj);
         }

         if (e instanceof RoleRemovalException) {
            throw (RoleRemovalException)e;
         } else {
            RoleRemovalException re = new RoleRemovalException(e.getMessage(), e);
            throw re;
         }
      }
   }

   private class V2Adapter implements RoleDeployerProvider {
      private DeployableRoleProviderV2 v2;

      private V2Adapter(DeployableRoleProviderV2 v2) {
         this.v2 = v2;
      }

      public RoleDeployerProvider.DeploymentHandler startDeployRoles(ApplicationInfo application) throws DeployHandleCreationException {
         return new DeploymentHandlerImpl(this.v2.startDeployRoles(application));
      }

      public void deleteApplicationRoles(ApplicationInfo application) throws RoleRemovalException {
         this.v2.deleteApplicationRoles(application);
      }

      // $FF: synthetic method
      V2Adapter(DeployableRoleProviderV2 x1, Object x2) {
         this(x1);
      }

      private class DeploymentHandlerImpl implements RoleDeployerProvider.DeploymentHandler {
         private DeployRoleHandle handle;

         DeploymentHandlerImpl(DeployRoleHandle handle) {
            this.handle = handle;
         }

         public void deployRole(Resource resource, String roleName, String[] userAndGroupNames) throws RoleCreationException {
            V2Adapter.this.v2.deployRole(this.handle, resource, roleName, userAndGroupNames);
         }

         public void endDeployRoles() throws RoleCreationException {
            V2Adapter.this.v2.endDeployRoles(this.handle);
         }

         public void undeployAllRoles() throws RoleRemovalException {
            V2Adapter.this.v2.undeployAllRoles(this.handle);
         }
      }
   }
}
