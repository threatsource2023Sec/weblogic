package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.spi.PolicyDeployerProvider;
import java.util.Vector;
import weblogic.security.spi.ApplicationInfo;
import weblogic.security.spi.DeployHandleCreationException;
import weblogic.security.spi.DeployPolicyHandle;
import weblogic.security.spi.DeployableAuthorizationProviderV2;
import weblogic.security.spi.Resource;
import weblogic.security.spi.ResourceCreationException;
import weblogic.security.spi.ResourceRemovalException;
import weblogic.security.spi.SecurityProvider;

public class PolicyDeployerProviderImpl implements ServiceLifecycleSpi {
   private static final String[] V1_UNCHECKED_ROLE = new String[]{"Anonymous"};
   private static final String[] V1_EXCLUDED_ROLE = new String[]{"ExcludedPolicyWLSAdapterRole"};
   private LoggerSpi logger;

   public static boolean enableNewCSSPolicyDeploymentProcess() {
      return !Boolean.getBoolean("weblogic.security.deployment.backwardcompatible");
   }

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.PolicyDeploymentService");
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".init()");
      }

      PolicyDeployerProviderConfig myconfig = (PolicyDeployerProviderConfig)config;
      boolean bParallel = myconfig.isSupportParallelDeploy();
      long depolyTimeout = myconfig.getDeployTimeout();
      SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getAuthorizationProviderName());
      if (provider instanceof DeployableAuthorizationProviderV2) {
         if (enableNewCSSPolicyDeploymentProcess()) {
            V2AdapterExt aV2 = new V2AdapterExt((DeployableAuthorizationProviderV2)provider);
            aV2.setLog(this.logger);
            aV2.setSupportParallelDeploy(bParallel);
            aV2.setBlockTime(depolyTimeout);
            return aV2;
         } else {
            return new V2Adapter((DeployableAuthorizationProviderV2)provider);
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getNotInstanceof("DeployableAuthorizationProvider/V2"));
      }
   }

   public void shutdown() {
   }

   private class V2AdapterExt extends AdapterBase implements PolicyDeployerProvider {
      private DeployableAuthorizationProviderV2 v2;

      private V2AdapterExt(DeployableAuthorizationProviderV2 v2) {
         this.v2 = v2;
      }

      public PolicyDeployerProvider.DeploymentHandler startDeployPolicies(ApplicationInfo application) throws DeployHandleCreationException {
         PolicyDeployerProvider.DeploymentHandler dplhandle = null;
         if (this.bParallelProvider) {
            if (PolicyDeployerProviderImpl.this.logger.isDebugEnabled()) {
               PolicyDeployerProviderImpl.this.logger.debug("startDeployPolicies: parallel, application is " + application);
            }

            dplhandle = new DeploymentHandlerImpl(this.v2.startDeployPolicies(application));
         } else {
            DeploymentHandlerImpl wHd = new DeploymentHandlerImpl();

            try {
               if (PolicyDeployerProviderImpl.this.logger.isDebugEnabled()) {
                  PolicyDeployerProviderImpl.this.logger.debug("startDeployPolicies: serial,  application is " + application);
               }

               dplhandle = (PolicyDeployerProvider.DeploymentHandler)this.startDeployPoliciesBlock(application, wHd);
            } catch (Exception var5) {
               this.processCreationException(wHd, var5);
               return null;
            }
         }

         if (PolicyDeployerProviderImpl.this.logger.isDebugEnabled()) {
            PolicyDeployerProviderImpl.this.logger.debug("Finished startDeployPolicies for application: " + application + ", the handle is " + dplhandle);
         }

         return (PolicyDeployerProvider.DeploymentHandler)dplhandle;
      }

      protected Object startPoliciesV(ApplicationInfo application) throws DeployHandleCreationException {
         return this.v2.startDeployPolicies(application);
      }

      public void deleteApplicationPolicies(ApplicationInfo application) throws ResourceRemovalException {
         if (this.bParallelProvider) {
            this.v2.deleteApplicationPolicies(application);
         } else {
            this.delApplicationPolicyBlocking(application);
         }

         if (PolicyDeployerProviderImpl.this.logger.isDebugEnabled()) {
            PolicyDeployerProviderImpl.this.logger.debug("Finished deleteApplicationPolicies for application: " + application);
         }

      }

      protected void deleteApplicationPolicyCallBack(ApplicationInfo application) throws ResourceRemovalException {
         this.v2.deleteApplicationPolicies(application);
      }

      // $FF: synthetic method
      V2AdapterExt(DeployableAuthorizationProviderV2 x1, Object x2) {
         this(x1);
      }

      private class DeploymentHandlerImpl implements PolicyDeployerProvider.DeploymentHandler, Setable {
         private DeployPolicyHandle handle;

         DeploymentHandlerImpl() {
         }

         DeploymentHandlerImpl(DeployPolicyHandle handle) {
            this.handle = handle;
         }

         public void setHandle(Object handle) {
            this.handle = (DeployPolicyHandle)handle;
         }

         public void deployPolicy(Resource resource, String[] roleNames) throws ResourceCreationException {
            try {
               if (PolicyDeployerProviderImpl.this.logger.isDebugEnabled()) {
                  PolicyDeployerProviderImpl.this.logger.debug("***** deploy Policy = " + resource + ", roleNames= " + roleNames);
               }

               V2AdapterExt.this.checkDeployHandleActive(this);
               V2AdapterExt.this.v2.deployPolicy(this.handle, resource, roleNames);
            } catch (Exception var4) {
               V2AdapterExt.this.processException(this, var4);
            }

         }

         public void deployUncheckedPolicy(Resource resource) throws ResourceCreationException {
            try {
               if (PolicyDeployerProviderImpl.this.logger.isDebugEnabled()) {
                  PolicyDeployerProviderImpl.this.logger.debug("***** deploy UncheckedPolicy = " + resource);
               }

               V2AdapterExt.this.checkDeployHandleActive(this);
               V2AdapterExt.this.v2.deployUncheckedPolicy(this.handle, resource);
            } catch (Exception var3) {
               V2AdapterExt.this.processException(this, var3);
            }

         }

         public void deployExcludedPolicy(Resource resource) throws ResourceCreationException {
            try {
               if (PolicyDeployerProviderImpl.this.logger.isDebugEnabled()) {
                  PolicyDeployerProviderImpl.this.logger.debug("***** deploy deployExcludedPolicy = " + resource);
               }

               V2AdapterExt.this.checkDeployHandleActive(this);
               V2AdapterExt.this.v2.deployExcludedPolicy(this.handle, resource);
            } catch (Exception var3) {
               V2AdapterExt.this.processException(this, var3);
            }

         }

         public void endDeployPolicies() throws ResourceCreationException {
            if (PolicyDeployerProviderImpl.this.logger.isDebugEnabled()) {
               PolicyDeployerProviderImpl.this.logger.debug("***** start endDeployPolicies for handle " + this);
            }

            try {
               V2AdapterExt.this.checkDeployHandleActive(this);
               V2AdapterExt.this.v2.endDeployPolicies(this.handle);
            } catch (Exception var2) {
               V2AdapterExt.this.processException(this, var2);
            }

            if (!V2AdapterExt.this.bParallelProvider) {
               V2AdapterExt.this.notifyNextDeployCycle(this);
            }

            if (PolicyDeployerProviderImpl.this.logger.isDebugEnabled()) {
               PolicyDeployerProviderImpl.this.logger.debug("***** done endDeployPolicies for handle " + this);
            }

         }

         public void undeployAllPolicies() throws ResourceRemovalException {
            try {
               if (PolicyDeployerProviderImpl.this.logger.isDebugEnabled()) {
                  PolicyDeployerProviderImpl.this.logger.debug("*****  undeployAllPolicies for handle " + this);
               }

               V2AdapterExt.this.checkDeployHandleActive(this);
               V2AdapterExt.this.v2.undeployAllPolicies(this.handle);
            } catch (Exception var2) {
               V2AdapterExt.this.processRemoveException(this, var2);
            }

         }
      }
   }

   abstract static class AdapterBase {
      private LoggerSpi logger;
      protected boolean bParallelProvider = false;
      protected long blockTimeout = 60000L;
      private Object activeItem;
      protected Vector aQueue = new Vector();

      protected void setLog(LoggerSpi log) {
         this.logger = log;
      }

      protected synchronized boolean isDeploymentProcessGoingOn() {
         return this.aQueue.size() > 0;
      }

      protected void deleteApplicationPolicyCallBack(ApplicationInfo application) throws ResourceRemovalException {
      }

      protected synchronized void delApplicationPolicyBlocking(ApplicationInfo application) throws ResourceRemovalException {
         try {
            while(this.isDeploymentProcessGoingOn()) {
               this.wait(this.blockTimeout * 3L);
            }

            this.deleteApplicationPolicyCallBack(application);
         } catch (InterruptedException var3) {
            throw new ResourceRemovalException(var3);
         }
      }

      protected void setSupportParallelDeploy(boolean support) {
         this.bParallelProvider = support;
      }

      protected void setBlockTime(long dur) {
         if (dur != 0L) {
            this.blockTimeout = dur;
         }

      }

      private synchronized void setActiveHandle(Object act) {
         this.activeItem = act;
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("The active deployment handle is set to :" + this.activeItem);
         }

      }

      protected synchronized Setable startDeployPoliciesBlock(ApplicationInfo application, Setable wHd) throws DeployHandleCreationException {
         if (this.aQueue.size() == 0) {
            this.aQueue.add(wHd);
            wHd.setHandle(this.startPoliciesV(application));
            this.setActiveHandle(wHd);
         } else {
            try {
               this.aQueue.add(wHd);

               while(this.activeItem != wHd) {
                  this.wait(this.blockTimeout);
               }

               wHd.setHandle(this.startPoliciesV(application));
            } catch (InterruptedException var4) {
               throw new DeployHandleCreationException(var4.getMessage(), var4);
            }
         }

         return wHd;
      }

      protected synchronized void notifyNextDeployCycle(Object itm) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("The queue size =" + this.aQueue.size() + "  for handle of " + itm);
         }

         if (this.aQueue.size() != 0) {
            boolean exist = this.aQueue.remove(itm);
            if (exist && this.aQueue.size() != 0) {
               this.setActiveHandle(this.aQueue.firstElement());
            }

            this.notifyAll();
         }

         if (this.logger.isDebugEnabled()) {
            this.logger.debug("The active deployment handle now is :" + this.activeItem);
         }

      }

      protected void checkDeployHandleActive(Object obj) throws WrongDeploymenCycleException {
         if (!this.bParallelProvider) {
            synchronized(this) {
               if (this.activeItem != obj) {
                  if (this.aQueue.size() != 0) {
                     throw new WrongDeploymenCycleException("The deploy handle of " + obj + "is not the active one");
                  }

                  this.activeItem = obj;
               }

            }
         }
      }

      protected abstract Object startPoliciesV(ApplicationInfo var1) throws DeployHandleCreationException;

      protected void processException(Object obj, Exception e) throws ResourceCreationException {
         if (!this.bParallelProvider) {
            this.notifyNextDeployCycle(obj);
         }

         if (e instanceof ResourceCreationException) {
            throw (ResourceCreationException)e;
         } else {
            ResourceCreationException re = new ResourceCreationException(e.getMessage(), e);
            throw re;
         }
      }

      protected void processCreationException(Object obj, Exception e) throws DeployHandleCreationException {
         if (!this.bParallelProvider) {
            this.notifyNextDeployCycle(obj);
         }

         if (e instanceof DeployHandleCreationException) {
            throw (DeployHandleCreationException)e;
         } else {
            DeployHandleCreationException re = new DeployHandleCreationException(e.getMessage(), e);
            throw re;
         }
      }

      protected void processRemoveException(Object obj, Exception e) throws ResourceRemovalException {
         if (!this.bParallelProvider) {
            this.notifyNextDeployCycle(obj);
         }

         if (e instanceof ResourceRemovalException) {
            throw (ResourceRemovalException)e;
         } else {
            ResourceRemovalException re = new ResourceRemovalException(e.getMessage(), e);
            throw re;
         }
      }
   }

   static class WrongDeploymenCycleException extends Exception {
      public WrongDeploymenCycleException(String msg) {
         super(msg);
      }

      public WrongDeploymenCycleException(Throwable e) {
         super(e);
      }
   }

   interface Setable {
      void setHandle(Object var1);
   }

   private class V2Adapter implements PolicyDeployerProvider {
      private DeployableAuthorizationProviderV2 v2;

      private V2Adapter(DeployableAuthorizationProviderV2 v2) {
         this.v2 = v2;
      }

      public PolicyDeployerProvider.DeploymentHandler startDeployPolicies(ApplicationInfo application) throws DeployHandleCreationException {
         return new DeploymentHandlerImpl(this.v2.startDeployPolicies(application));
      }

      public void deleteApplicationPolicies(ApplicationInfo application) throws ResourceRemovalException {
         this.v2.deleteApplicationPolicies(application);
      }

      // $FF: synthetic method
      V2Adapter(DeployableAuthorizationProviderV2 x1, Object x2) {
         this(x1);
      }

      private class DeploymentHandlerImpl implements PolicyDeployerProvider.DeploymentHandler {
         private DeployPolicyHandle handle;

         DeploymentHandlerImpl(DeployPolicyHandle handle) {
            this.handle = handle;
         }

         public void deployPolicy(Resource resource, String[] roleNames) throws ResourceCreationException {
            V2Adapter.this.v2.deployPolicy(this.handle, resource, roleNames);
         }

         public void deployUncheckedPolicy(Resource resource) throws ResourceCreationException {
            V2Adapter.this.v2.deployUncheckedPolicy(this.handle, resource);
         }

         public void deployExcludedPolicy(Resource resource) throws ResourceCreationException {
            V2Adapter.this.v2.deployExcludedPolicy(this.handle, resource);
         }

         public void endDeployPolicies() throws ResourceCreationException {
            V2Adapter.this.v2.endDeployPolicies(this.handle);
         }

         public void undeployAllPolicies() throws ResourceRemovalException {
            V2Adapter.this.v2.undeployAllPolicies(this.handle);
         }
      }
   }
}
