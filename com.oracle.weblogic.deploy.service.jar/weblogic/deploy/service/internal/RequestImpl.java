package weblogic.deploy.service.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.DeploymentFailureHandler;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManagerFactory;

public class RequestImpl implements DeploymentRequest {
   protected List deployments = new ArrayList();
   protected long identifier;
   private long timeoutInterval = 120000L;
   private boolean callConfigurationProviderLast;
   private AuthenticatedSubject initiator;
   private transient Timer timeoutMonitor;
   private transient String timeoutIdentifier;
   private transient boolean startControl;
   private transient boolean isAControlRequest;
   private final transient HashSet failureListeners = new HashSet();
   private String appId = null;
   private transient boolean configCommitCalled = false;
   private Boolean singlePartitionDeploymentRequest = null;
   private static long lastNanoTime = -1L;
   private static int lastNumberOfIds = 0;
   private static final Object lock = new Object();
   private transient volatile boolean enqueued = false;

   protected RequestImpl() {
   }

   protected static void debug(String message) {
      Debug.serviceDebug(message);
   }

   protected static boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   public final long getId() {
      return this.identifier;
   }

   public final void setId() {
      this.identifier = getNewRequestId();
      if (isDebugEnabled()) {
         debug("setting id for request to '" + this.identifier + "'");
      }

   }

   public final String getExtendedIdName() {
      if (this.appId == null) {
         Iterator deployments = this.getDeployments("Application");
         if (deployments.hasNext()) {
            Deployment deployment = (Deployment)deployments.next();
            this.appId = deployment.getIdentity();
         }
      }

      return this.getId() + (this.appId != null ? " [" + this.appId + "]" : "");
   }

   public final void addDeployment(Deployment deployment) {
      synchronized(this.deployments) {
         if (deployment != null) {
            this.deployments.add(deployment);
            if (this.appId == null && deployment.getCallbackHandlerId().equals("Application")) {
               this.appId = deployment.getIdentity();
            }
         } else if (isDebugEnabled()) {
            Debug.serviceDebug("Attempt to add an empty deployment " + StackTraceUtils.throwable2StackTrace((new Throwable()).fillInStackTrace()));
         }

      }
   }

   public Iterator getDeployments() {
      List clonedList;
      synchronized(this.deployments) {
         clonedList = (List)((ArrayList)this.deployments).clone();
      }

      return clonedList.iterator();
   }

   public Iterator getDeployments(String deploymentIdentity) {
      List result = new ArrayList();
      synchronized(this.deployments) {
         Iterator var4 = this.deployments.iterator();

         while(var4.hasNext()) {
            Object deployment1 = var4.next();
            Deployment deployment = (Deployment)deployment1;
            if (deploymentIdentity.equals(deployment.getCallbackHandlerId())) {
               result.add(deployment);
            }
         }

         return result.iterator();
      }
   }

   public DeploymentRequestTaskRuntimeMBean getTaskRuntime() {
      return null;
   }

   public final boolean isConfigurationProviderCalledLast() {
      return this.callConfigurationProviderLast;
   }

   public final void setCallConfigurationProviderLast() {
      this.callConfigurationProviderLast = true;
   }

   public final boolean isStartControlEnabled() {
      return this.startControl;
   }

   public final void setStartControl(boolean flag) {
      this.startControl = flag;
   }

   public boolean isControlRequest() {
      return this.isAControlRequest;
   }

   public void setControlRequest(boolean given) {
      this.isAControlRequest = given;
   }

   public final void registerFailureListener(DeploymentFailureHandler failureHandler) {
      synchronized(this.failureListeners) {
         this.failureListeners.add(failureHandler);
      }
   }

   public final Set getRegisteredFailureListeners() {
      synchronized(this.failureListeners) {
         return this.failureListeners;
      }
   }

   public final void setInitiator(AuthenticatedSubject initiator) {
      if (this.initiator == null) {
         this.initiator = initiator;
      }

   }

   public final AuthenticatedSubject getInitiator() {
      return this.initiator;
   }

   public boolean equals(Object inObj) {
      if (this == inObj) {
         return true;
      } else {
         return inObj instanceof RequestImpl && this.identifier != 0L && ((RequestImpl)inObj).getId() == this.identifier;
      }
   }

   public int hashCode() {
      return (int)this.identifier;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("DeploymentRequest: id: ").append(this.getId());
      Iterator iterator = this.getDeployments();
      int i = 1;

      while(iterator.hasNext()) {
         Deployment next = (Deployment)iterator.next();
         sb.append(" Deployment[");
         sb.append(i);
         ++i;
         sb.append("]: type: ");
         sb.append(next.getCallbackHandlerId());
         sb.append(" , proposedVersion: ");
         sb.append(next.getProposedVersion());
         sb.append(" , targets: ");
         String[] targets = next.getTargets();
         String[] var6 = targets;
         int var7 = targets.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String target = var6[var8];
            sb.append(target);
            sb.append(" ");
         }
      }

      return sb.toString();
   }

   public final void setTimeoutInterval(long timeout) {
      if (timeout > 120000L) {
         if (this.timeoutInterval <= 120000L) {
            this.timeoutInterval = timeout;
         }
      }
   }

   public final long getTimeoutInterval() {
      return this.timeoutInterval;
   }

   public final void startTimeoutMonitor(String timeoutId) {
      if (this.timeoutMonitor == null) {
         this.timeoutIdentifier = timeoutId;
         this.timeoutMonitor = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.deploy.RequestTimeout", WorkManagerFactory.getInstance().getSystem()).schedule(new TimeoutMonitor(), this.timeoutInterval);
         if (isDebugEnabled()) {
            debug("Starting timer '" + timeoutId + "' to expire in '" + this.timeoutInterval + "' ms at '" + new Date(System.currentTimeMillis() + this.timeoutInterval) + "'");
         }

      }
   }

   public final void cancelTimeoutMonitor() {
      try {
         if (this.timeoutMonitor != null) {
            if (isDebugEnabled()) {
               debug("Cancelling timeout monitor for '" + this.timeoutIdentifier + "'");
            }

            this.timeoutMonitor.cancel();
            this.timeoutMonitor = null;
         }
      } catch (Throwable var2) {
         if (isDebugEnabled()) {
            Debug.serviceDebug(var2.getMessage() + " " + StackTraceUtils.throwable2StackTrace(var2));
         }
      }

   }

   public void requestTimedout() {
      if (isDebugEnabled()) {
         debug(this.timeoutIdentifier + " timed out");
      }

   }

   public boolean isSinglePartitionDeploymentRequest() {
      if (this.singlePartitionDeploymentRequest != null) {
         return this.singlePartitionDeploymentRequest;
      } else {
         Iterator var1 = this.deployments.iterator();

         while(var1.hasNext()) {
            Object dep = var1.next();
            Deployment deployment = (Deployment)dep;
            if (deployment.getCallbackHandlerId().equals("Configuration")) {
               String singlePart = ((BaseDeploymentImpl)deployment).getConstrainedToPartitionName();
               if (singlePart == null) {
                  this.singlePartitionDeploymentRequest = Boolean.FALSE;
               } else {
                  this.singlePartitionDeploymentRequest = Boolean.TRUE;
               }
               break;
            }
         }

         if (this.singlePartitionDeploymentRequest == null) {
            this.singlePartitionDeploymentRequest = Boolean.FALSE;
         }

         return this.singlePartitionDeploymentRequest;
      }
   }

   private static long getNewRequestId() {
      long tentativeId = System.nanoTime();
      synchronized(lock) {
         if (tentativeId == lastNanoTime) {
            tentativeId = lastNanoTime / (long)Math.pow(2.0, (double)lastNumberOfIds);
            ++lastNumberOfIds;
         } else {
            lastNanoTime = tentativeId;
            lastNumberOfIds = 1;
         }

         return tentativeId;
      }
   }

   public boolean concurrentAppPrepareEnabled() {
      return DeploymentService.isCrossPartitionConcurrentAppPrepareEnabled() && this.isSinglePartitionDeploymentRequest() && !this.isConfigurationProviderCalledLast();
   }

   public void enqueued() {
      this.enqueued = true;
   }

   public boolean isEnqueued() {
      return this.enqueued;
   }

   public void setConfigCommitCalled() {
      this.configCommitCalled = true;
   }

   public boolean getConfigCommitCalled() {
      return this.configCommitCalled;
   }

   public void deploymentFailedInConfigLayer() {
      Iterator appDeployments = this.getDeployments("Application");
      if (appDeployments != null) {
         while(appDeployments.hasNext()) {
            Deployment dep = (Deployment)appDeployments.next();
            if (dep != null) {
               dep.updateDeploymentTaskStatus(3);
            }
         }

      }
   }

   private final class TimeoutMonitor implements TimerListener {
      private TimeoutMonitor() {
      }

      public final void timerExpired(Timer timer) {
         try {
            RequestImpl.this.requestTimedout();
         } catch (Throwable var3) {
            if (RequestImpl.isDebugEnabled()) {
               RequestImpl.debug("Time out for request id: " + RequestImpl.this.timeoutIdentifier);
            }
         }

      }

      // $FF: synthetic method
      TimeoutMonitor(Object x1) {
         this();
      }
   }
}
