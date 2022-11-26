package weblogic.deploy.service.internal.targetserver;

import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DeploymentContext;
import weblogic.deploy.service.DeploymentReceiver;
import weblogic.deploy.service.DeploymentReceiverV2;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.internal.DeploymentService;
import weblogic.deploy.service.internal.RequestImpl;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManagerFactory;

final class DeploymentReceiverCallbackDeliverer implements DeploymentReceiverV2 {
   private final DeploymentReceiver delegate;

   DeploymentReceiverCallbackDeliverer(DeploymentReceiver delegate) {
      this.delegate = delegate;
   }

   public final String getHandlerIdentity() {
      return this.delegate.getHandlerIdentity();
   }

   public final void updateDeploymentContext(DeploymentContext deploymentContext) {
      this.doUpdateDeploymentContextCallback(deploymentContext);
   }

   public final void prepare(final DeploymentContext deploymentContext) {
      DeploymentRequest request = deploymentContext.getDeploymentRequest();
      if (this.delegate != null) {
         if ((!request.isConfigurationProviderCalledLast() || this.delegate.getHandlerIdentity().equals("Configuration")) && (request.isConfigurationProviderCalledLast() || !this.delegate.getHandlerIdentity().equals("Configuration"))) {
            WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
               public void run() {
                  DeploymentReceiverCallbackDeliverer.this.doPrepareCallback(deploymentContext);
               }
            });
         } else {
            this.doPrepareCallback(deploymentContext);
         }
      }

   }

   public final void commit(final DeploymentContext deploymentContext) {
      DeploymentRequest request = deploymentContext.getDeploymentRequest();
      if (this.delegate != null) {
         if ((!request.isConfigurationProviderCalledLast() || this.delegate.getHandlerIdentity().equals("Configuration")) && (request.isConfigurationProviderCalledLast() || !this.delegate.getHandlerIdentity().equals("Configuration"))) {
            WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
               public void run() {
                  DeploymentReceiverCallbackDeliverer.this.doCommitCallback(deploymentContext);
               }
            });
         } else {
            this.doCommitCallback(deploymentContext);
         }
      }

   }

   public final void cancel(final DeploymentContext deploymentContext) {
      if (this.delegate != null) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               DeploymentReceiverCallbackDeliverer.this.doCancelCallback(deploymentContext);
            }
         });
      }

   }

   public final void prepareCompleted(final DeploymentContext deploymentContext, final String identifier) {
      if (this.delegate != null) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               DeploymentReceiverCallbackDeliverer.this.doPrepareCompletedCallback(deploymentContext, identifier);
            }
         });
      }

   }

   public final void commitCompleted(final DeploymentContext deploymentContext, final String identifier) {
      if (this.delegate != null) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               DeploymentReceiverCallbackDeliverer.this.doCommitCompletedCallback(deploymentContext, identifier);
            }
         });
      }

   }

   public final void commitSkipped(final DeploymentContext deploymentContext) {
      if (this.delegate != null && this.delegate instanceof DeploymentReceiverV2) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               DeploymentReceiverCallbackDeliverer.this.doCommitSkippedCallback(deploymentContext);
            }
         });
      }

   }

   private final void debug(String message) {
      Debug.serviceDebug(message);
   }

   private final boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   private final String getExtendedIdName(DeploymentContext deploymentContext) {
      RequestImpl deploymentRequest = (RequestImpl)((RequestImpl)deploymentContext.getDeploymentRequest());
      return deploymentRequest != null ? deploymentRequest.getExtendedIdName() : "Unknown";
   }

   private final void doUpdateDeploymentContextCallback(DeploymentContext deploymentContext) {
      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'updateDeploymentContext' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.updateDeploymentContext(deploymentContext);
      } catch (Throwable var4) {
         if (Debug.serviceLogger.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'updateDeploymentContext' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "' failed due to " + var4;
            if (Debug.serviceLogger.isDebugEnabled()) {
               Debug.serviceLogger.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         if (this.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var4));
         }

         DeploymentService.getDeploymentService().notifyContextUpdateFailed(deploymentContext.getDeploymentRequest().getId(), this.getHandlerIdentity(), new Exception(var4));
      }

   }

   private final void doPrepareCallback(DeploymentContext deploymentContext) {
      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'prepare' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.prepare(deploymentContext);
      } catch (Throwable var4) {
         if (Debug.serviceLogger.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'prepare' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "' failed due to " + var4;
            if (Debug.serviceLogger.isDebugEnabled()) {
               Debug.serviceLogger.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         if (this.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var4));
         }

         DeploymentService.getDeploymentService().notifyPrepareFailure(deploymentContext.getDeploymentRequest().getId(), this.getHandlerIdentity(), new Exception(var4));
      }

   }

   private final void doCommitCallback(DeploymentContext deploymentContext) {
      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'commit' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.commit(deploymentContext);
      } catch (Throwable var4) {
         if (Debug.serviceLogger.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'commit' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "' failed due to " + var4;
            if (Debug.serviceLogger.isDebugEnabled()) {
               Debug.serviceLogger.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         if (this.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var4));
         }

         DeploymentService.getDeploymentService().notifyCommitFailure(deploymentContext.getDeploymentRequest().getId(), this.getHandlerIdentity(), new Exception(var4));
      }

   }

   private final void doCancelCallback(DeploymentContext deploymentContext) {
      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'cancel' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.cancel(deploymentContext);
      } catch (Throwable var4) {
         if (Debug.serviceLogger.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'cancel' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "' failed due to " + var4;
            if (Debug.serviceLogger.isDebugEnabled()) {
               Debug.serviceLogger.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         if (this.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var4));
         }

         DeploymentService.getDeploymentService().notifyCancelFailure(deploymentContext.getDeploymentRequest().getId(), this.getHandlerIdentity(), new Exception(var4));
      }

   }

   private final void doPrepareCompletedCallback(DeploymentContext deploymentContext, String identifier) {
      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'prepareCompleted' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.prepareCompleted(deploymentContext, identifier);
      } catch (Throwable var5) {
         if (Debug.serviceLogger.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'prepareCompleted' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "' failed due to " + var5;
            if (Debug.serviceLogger.isDebugEnabled()) {
               Debug.serviceLogger.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         if (this.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var5));
         }

         DeploymentService.getDeploymentService().notifyPrepareFailure(deploymentContext.getDeploymentRequest().getId(), this.getHandlerIdentity(), new Exception(var5));
      }

   }

   private final void doCommitCompletedCallback(DeploymentContext deploymentContext, String identifier) {
      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'commitCompleted' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.commitCompleted(deploymentContext, identifier);
      } catch (Throwable var5) {
         if (Debug.serviceLogger.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'commitCompleted' on DeploymentReceiver for '" + this.getHandlerIdentity() + "' for id '" + this.getExtendedIdName(deploymentContext) + "' failed due to " + var5;
            if (Debug.serviceLogger.isDebugEnabled()) {
               Debug.serviceLogger.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         if (this.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var5));
         }

         DeploymentService.getDeploymentService().notifyCommitFailure(deploymentContext.getDeploymentRequest().getId(), this.getHandlerIdentity(), new Exception(var5));
      }

   }

   private final void doCommitSkippedCallback(DeploymentContext deploymentContext) {
      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'commitSkipped' on DeploymentReceiver for id '" + this.getExtendedIdName(deploymentContext) + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         DeploymentReceiverV2 v2Delegate = (DeploymentReceiverV2)this.delegate;
         v2Delegate.commitSkipped(deploymentContext);
      } catch (Throwable var4) {
         if (Debug.serviceLogger.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling 'commitSkipped' on DeploymentReceiver for id '" + this.getExtendedIdName(deploymentContext) + "' failed due to " + var4;
            if (Debug.serviceLogger.isDebugEnabled()) {
               Debug.serviceLogger.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         if (this.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var4));
         }

         DeploymentService.getDeploymentService().notifyCommitFailure(deploymentContext.getDeploymentRequest().getId(), this.getHandlerIdentity(), new Exception(var4));
      }

   }
}
