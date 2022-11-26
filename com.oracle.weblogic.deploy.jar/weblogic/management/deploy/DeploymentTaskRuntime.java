package weblogic.management.deploy;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.ReflectionException;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.beans.factory.InvalidTargetException;
import weblogic.deploy.common.Debug;
import weblogic.deploy.event.BaseDeploymentEvent;
import weblogic.deploy.event.DeploymentEvent;
import weblogic.deploy.event.DeploymentEventManager;
import weblogic.deploy.event.VetoableDeploymentEvent;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.adminserver.DeploymentManager;
import weblogic.deploy.internal.adminserver.EditAccessHelper;
import weblogic.deploy.internal.adminserver.operations.AbstractOperation;
import weblogic.deploy.internal.adminserver.operations.OperationHelper;
import weblogic.deploy.internal.adminserver.operations.RemoveOperation;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.deploy.service.DeploymentRequestSubTask;
import weblogic.deploy.service.internal.DeploymentRequestTaskRuntimeMBeanImpl;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.logging.Loggable;
import weblogic.management.ApplicationException;
import weblogic.management.DeferredDeploymentException;
import weblogic.management.DeploymentException;
import weblogic.management.DeploymentNotification;
import weblogic.management.ManagementException;
import weblogic.management.RemoteNotificationListener;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.configuration.WebAppComponentMBean;
import weblogic.management.configuration.WebDeploymentMBean;
import weblogic.management.context.JMXContext;
import weblogic.management.context.JMXContextHelper;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.ApplicationRuntimeState;
import weblogic.management.deploy.internal.ComponentTargetValidator;
import weblogic.management.deploy.internal.DeployerRuntimeExtendedLogger;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.deploy.internal.RetirementManager;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.internal.PartitionProcessor;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;
import weblogic.utils.StringUtils;

public final class DeploymentTaskRuntime extends DomainRuntimeMBeanDelegate implements DeploymentTaskRuntimeMBean, DeploymentRequestSubTask, BeanUpdateListener {
   private static final long serialVersionUID = 7987828709785973087L;
   private int task;
   private DeploymentAction deploymentAction;
   private final String sourcePath;
   private final DeploymentData requestData;
   private String[] targets;
   private boolean hasTargets;
   private TargetStatus[] targetsStatus;
   private Map targetStatusMap = new HashMap();
   private Map serverToTargetStatusMap = new HashMap();
   private TaskRuntimeMBean[] subTasks;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private HashSet pendingServers = new HashSet();
   private int deploymentTaskStatus = 0;
   private String deploymentTaskStatusMessage;
   private String applicationName;
   private String applicationId;
   private String applicationVersionIdentifier;
   private int failedStatusCount;
   private final String taskId;
   private long startTime;
   private long endTime;
   private Exception lastException;
   private ApplicationMBean applicationMBean;
   private AppDeploymentMBean appDeployMBean;
   private BasicDeploymentMBean basicDeployMBean;
   private String applicationDisplayName;
   private volatile boolean failedTaskAsTargetNotUp;
   private int cancelState = 0;
   private boolean isNewApplication;
   private final Set unreachableTargets;
   private final Map versionTargetStatusMap = new HashMap();
   private static final transient DeployerRuntimeTextTextFormatter fmt = DeployerRuntimeTextTextFormatter.getInstance();
   private static final String TASK_NAME_PREFIX = "ADTR-";
   private final String saveSource;
   private String deploymentTaskDescription;
   private int notifLevel = 1;
   private boolean sysTask;
   private DeploymentTaskListener appListener;
   private final transient ArrayList taskMessages = new ArrayList();
   private static transient DomainMBean domainMBean;
   private DeploymentManager depMgr;
   private transient DeploymentRequestTaskRuntimeMBean myParent;
   private transient DomainMBean editableDomainMBean;
   private final transient boolean isAControlOperation;
   private static final AppRuntimeStateManager appRTStateMgr = AppRuntimeStateManager.getManager();
   private boolean retired = false;
   private boolean configChange = false;
   private transient AuthenticatedSubject subject = null;
   private boolean pendingActivation = false;
   private HashMap failedTargets = new HashMap();
   private transient AbstractOperation adminOperation = null;
   private transient DeploymentTaskRuntime delegate = null;
   private transient List delegators = Collections.synchronizedList(new ArrayList());
   private Locale clientLocale = null;
   transient DeploymentTaskRuntime taskParent = null;
   transient Map subtaskState = new HashMap();
   private boolean inUse;
   private final HashSet handlers = new HashSet();

   public DeploymentTaskRuntime(String path, BasicDeploymentMBean dep, DeploymentData info, String id, int task, DomainMBean editDomainMBean, boolean isAControlOperation, boolean configChange) throws ManagementException {
      super("ADTR-" + id, ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName() != null && !"DOMAIN".equals(ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName()) ? ManagementService.getDomainAccess(kernelId).getDomainRuntime().lookupDomainPartitionRuntime(ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName()).getDeployerRuntime() : ManagementService.getDomainAccess(kernelId).getDomainRuntime().getDeployerRuntime(), false);
      this.setClientLocale();
      this.initMBeans(dep);
      this.taskId = id;
      this.task = task;
      this.isAControlOperation = isAControlOperation;
      this.configChange = configChange;
      this.deploymentAction = DeploymentTaskRuntime.DeploymentAction.getDeploymentAction(task);
      this.sourcePath = path;
      if (path == null) {
         this.saveSource = this.getSourcePath();
      } else {
         this.saveSource = path;
      }

      if (info == null) {
         this.requestData = new DeploymentData();
      } else {
         this.requestData = info;
      }

      if (editDomainMBean != null) {
         this.editableDomainMBean = editDomainMBean;
      }

      this.initializeTask();
      this.setDescription();
      if (info != null) {
         this.isNewApplication = info.isNewApplication();
      }

      this.unreachableTargets = new HashSet();
      this.register();
   }

   private AuthenticatedSubject getSubject() {
      return this.subject;
   }

   public void setSubject(AuthenticatedSubject subject) {
      this.debugSay("subject set to " + subject);
      this.subject = subject;
      if (this.callerOwnsEditLock() && this.editableDomainMBean != null) {
         this.editableDomainMBean.addBeanUpdateListener(this);
      }

   }

   public void initMBeans(BasicDeploymentMBean dep) {
      this.basicDeployMBean = dep;
      if (dep instanceof AppDeploymentMBean) {
         this.appDeployMBean = (AppDeploymentMBean)dep;
         this.applicationMBean = this.appDeployMBean.getAppMBean();
      }

   }

   public boolean isConfigChange() {
      return this.configChange;
   }

   public void setConfigChange(boolean change) {
      this.configChange = change;
   }

   public void setTask(int newTask) {
      this.task = newTask;
      this.deploymentAction = DeploymentTaskRuntime.DeploymentAction.getDeploymentAction(this.task);
   }

   public boolean isPendingActivation() {
      return this.pendingActivation;
   }

   public void setPendingActivation(boolean pendingActivation) {
      this.pendingActivation = pendingActivation;
      if (pendingActivation) {
         this.addMessage(DeployerRuntimeLogger.pendingActivationLoggable().getMessage(this.getClientLocale()));
      }

   }

   private String getSourcePath() {
      return this.basicDeployMBean != null ? this.basicDeployMBean.getSourcePath() : null;
   }

   public DomainMBean getDomain() {
      if (this.editableDomainMBean != null) {
         return this.editableDomainMBean;
      } else {
         if (domainMBean == null) {
            Class var1 = DeploymentTaskRuntime.class;
            synchronized(DeploymentTaskRuntime.class) {
               if (domainMBean == null) {
                  domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
               }
            }
         }

         return domainMBean;
      }
   }

   private DeploymentManager getDepMgr() {
      if (this.depMgr == null) {
         this.depMgr = DeploymentManager.getInstance(kernelId);
      }

      return this.depMgr;
   }

   public AppDeploymentMBean getAppDeploymentMBean() {
      return this.endTime > 0L ? this.getCurrentApp() : this.appDeployMBean;
   }

   public synchronized void printLog(PrintWriter out) {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating printLog() to delegate : " + theDelegate);
         theDelegate.printLog(out);
      } else {
         Iterator l = this.taskMessages.iterator();

         while(l.hasNext()) {
            out.println((String)l.next());
         }

      }
   }

   public synchronized List getTaskMessages() {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating getTaskMessages() to delegate : " + theDelegate);
         return theDelegate.getTaskMessages();
      } else {
         return (ArrayList)this.taskMessages.clone();
      }
   }

   public final boolean isAControlOperation() {
      return this.isAControlOperation;
   }

   public TargetStatus findTarget(String target) {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating findTarget() to delegate : " + theDelegate);
         return theDelegate.getTargetStatus(target);
      } else {
         TargetStatus original = this.getTargetStatus(target);
         return original != null ? original.copy() : original;
      }
   }

   private TargetStatus getTargetStatus(String target) {
      return (TargetStatus)this.targetStatusMap.get(target);
   }

   public synchronized void updateTargetStatus(String targetName, int targetStatus, Exception e) {
      this.updateTargetStatus(targetName, targetStatus, e, true);
   }

   private synchronized void updateTargetStatus(String targetName, int targetStatus, Exception e, boolean onlyUpdateRunningTasks) {
      if (!onlyUpdateRunningTasks || this.deploymentTaskStatus == 1) {
         List mappedTargets = this.getTargetStatusesForServer(targetName);
         this.debugSay(" Pending Servers : " + this.pendingServers);
         if (mappedTargets != null) {
            Iterator tsIter = mappedTargets.iterator();

            while(tsIter.hasNext()) {
               TargetStatus ts = (TargetStatus)tsIter.next();
               this.debugSay("Updating target status to " + targetStatus + " for target " + ts.getTarget() + " from server " + targetName);
               this.doUpdateTargetStatus(ts, targetStatus, e);
               this.addStatusMessage(targetStatus, this.applicationDisplayName, targetName);
            }

            if (targetStatus == 4 && targetName != null && targetName.length() > 0) {
               this.addUnreachableTarget(targetName);
            }
         } else {
            this.debugSay("Could not find a target status objects for target " + targetName + " - ignoring the status and will proceed by setting the status on the task : " + targetStatus);
            if (this.targetsStatus != null) {
               for(int i = 0; i < this.targetsStatus.length; ++i) {
                  if (this.targetsStatus[i].isTargetListEmpty()) {
                     this.doUpdateTargetStatus(this.targetsStatus[i], targetStatus, new Exception(DeployerRuntimeLogger.emptyCluster(this.targetsStatus[i].getTarget())));
                  }
               }
            }
         }

         if (e != null && !(e instanceof DeferredDeploymentException)) {
            Loggable l = DeployerRuntimeLogger.logExceptionReceivedLoggable(this.getDescription(), e.getMessage());
            this.addMessage(l.getMessage(this.getClientLocale()));
            this.addMessagesFromApplicationException(e);
            this.lastException = e;
         }

         if (targetStatus == 2 || targetStatus == 3 || targetStatus == 4) {
            this.pendingServers.remove(targetName);
            this.debugSay("Removed target: " + targetName + " from pending server list");
            this.debugSay(this.dumpPendingServerList());
            if (targetStatus == 2) {
               if (e != null) {
                  this.debugSay("DeploymentTaskRuntime: Adding target '" + targetName + "' with exception: " + e + " to failed targets list");
                  this.failedTargets.put(targetName, e);
               }

               ++this.failedStatusCount;
            }
         }

         if (this.pendingServers.isEmpty()) {
            if (this.failedStatusCount == 0) {
               this.finishUp(true, this.targets);
            } else {
               this.handleFailure();
            }
         }

         this.debugSay("Current status is " + this.deploymentTaskStatusMessage);
         this.debugSay(this.deploymentAction.getDescription() + " for " + this.applicationDisplayName + " took : " + (System.currentTimeMillis() - this.startTime) + " millis");
      }
   }

   private void addStatusMessage(int status, String applicationName, String serverName) {
      String operationName = DeployHelper.getTaskName(this.task, this.getClientLocale());
      switch (status) {
         case 0:
            this.addMessage(DeployerRuntimeLogger.logInitStatusLoggable(operationName, this.applicationDisplayName, serverName).getMessage(this.getClientLocale()));
            break;
         case 1:
            this.addMessage(DeployerRuntimeLogger.logProgressStatusLoggable(operationName, this.applicationDisplayName, serverName).getMessage(this.getClientLocale()));
            break;
         case 2:
            this.addMessage(DeployerRuntimeLogger.logFailedStatusLoggable(operationName, this.applicationDisplayName, serverName).getMessage(this.getClientLocale()));
            break;
         case 3:
            this.addMessage(DeployerRuntimeLogger.logSuccessStatusLoggable(operationName, this.applicationDisplayName, serverName).getMessage(this.getClientLocale()));
            break;
         case 4:
            this.addMessage(DeployerRuntimeLogger.logUnavailableStatusLoggable(operationName, this.applicationDisplayName, serverName).getMessage(this.getClientLocale()));
      }

   }

   private void doUpdateTargetStatus(TargetStatus ts, int state, Exception e) {
      ts.setState(state);
      if (this.taskParent != null) {
         this.taskParent.setSubtaskState(this.getId(), new Integer(state));
      }

      if (e != null) {
         this.debugSay("adding exception for target (" + e.getMessage() + ")");
         ts.addMessage(e);
      }

   }

   public void handleFailure() {
      this.finishUp(false, (String[])null);
   }

   private void finishUp(boolean taskSucceeded, String[] targets) {
      if (!this.isComplete()) {
         this.debugSay("Completing task...");
         this.endTime = System.currentTimeMillis();
         this.removeAppListener();
         this.retirePreviousActiveVersion();
         if (this.getAdminOperation() instanceof RemoveOperation) {
            boolean gracefulUndeploy = this.getDeploymentData().getDeploymentOptions().isGracefulProductionToAdmin();
            if (!gracefulUndeploy) {
               RetirementManager.waitForRetirementCompleteIfNeeded(this.getApplicationName(), this.getApplicationVersionIdentifier());
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("DTR.finishup for, app=" + this.getApplicationName() + ", version=" + this.getApplicationVersionIdentifier());
               }
            }
         }

         if (taskSucceeded) {
            if (this.task == 4) {
               if (this.getCurrentApp() == null) {
                  removeAppRuntimeState(this.applicationId);
               } else {
                  removeAppRuntimeStateForTargets(this.applicationId, targets);
               }
            }

            if (this.task == 4 && this.getCurrentApp() == null) {
               this.removeUploadedSource();
            }

            if (this.unreachableTargets.isEmpty()) {
               this.setState(2);
            } else {
               this.setState(4);
            }
         } else {
            this.setState(3);
            if (this.getCurrentApp() == null) {
               removeAppRuntimeState(this.applicationId);
            }
         }

         this.logCompletion();
      }
   }

   private static void removeAppRuntimeState(String appId) {
      try {
         AppRuntimeStateManager.getManager().remove(appId);
      } catch (ManagementException var2) {
      }

   }

   private static void removeAppRuntimeStateForTargets(String appId, String[] targets) {
      try {
         AppRuntimeStateManager.getManager().removeTargets(appId, targets);
      } catch (ManagementException var3) {
      }

   }

   private void removeUploadedSource() {
      String uploadDirName = ManagementService.getRuntimeAccess(kernelId).getServer().getUploadDirectoryName();
      if (this.appDeployMBean != null) {
         if (uploadDirName.startsWith("." + File.separator)) {
            uploadDirName = uploadDirName.substring(2);
         }

         String path = this.appDeployMBean.getSourcePath();
         this.removeIfUploaded(path, uploadDirName);
         path = this.appDeployMBean.getPlanDir();
         this.removeIfUploaded(path, uploadDirName);
      }

   }

   private void removeIfUploaded(String path, String uploadDirName) {
      if (path != null && path.indexOf(uploadDirName) != -1) {
         File uploadFile = new File(uploadDirName);
         File source = new File(path);
         File parent = null;

         do {
            parent = source.getParentFile();
            FileUtils.remove(source);
            source = parent;
         } while(!uploadFile.equals(parent) && (parent.listFiles() == null || parent.listFiles().length == 0));
      }

   }

   private void addMessagesFromApplicationException(Exception e) {
      if (e instanceof ApplicationException) {
         ApplicationException ae = (ApplicationException)e;
         Hashtable errs = ae.getModuleErrors();
         Iterator keys = errs.keySet().iterator();

         while(keys.hasNext()) {
            String mod = (String)keys.next();
            String msg = (String)errs.get(mod);
            Loggable ll = DeployerRuntimeLogger.logModuleMessageLoggable(mod, msg);
            this.addMessage(ll.getMessage(this.getClientLocale()));
         }
      }

   }

   public void start() throws ManagementException {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating start() to delegate : " + theDelegate);
         theDelegate.start();
      } else {
         try {
            this.getDepMgr().startDeploymentTask(this, this.applicationDisplayName);
         } catch (Throwable var3) {
            Loggable l = DeployerRuntimeLogger.logExceptionOccurredLoggable(this.deploymentAction.getDescription(), this.applicationDisplayName, new Exception(var3));
            l.log();
            if (var3 instanceof ManagementException) {
               throw (ManagementException)var3;
            } else {
               throw new ManagementException(var3);
            }
         }
      }
   }

   public void prepareToStart() throws ManagementException {
      if (this.hasDelegate()) {
         this.debugSay("Doing nothing in prepareToStart() since this task has been delegated to : " + this.getDelegate());
      } else {
         this.updateIntendedState();
         this.setUpStartedStateVariables();

         try {
            if (this.hasTargets && this.applicationMBean != null) {
               switch (this.task) {
                  case 2:
                  case 3:
                  case 4:
                  case 5:
                  case 8:
                  case 12:
                  case 13:
                  default:
                     break;
                  case 6:
                     this.destage();
                     break;
                  case 9:
                  case 10:
                     if (this.isNewApplication && !this.getDeploymentData().getDeploymentOptions().isRGOrRGTOperation()) {
                        Loggable l = DeployerRuntimeLogger.logNullAppLoggable(this.applicationDisplayName, this.deploymentAction.getDescription());
                        l.log();
                        throw new ManagementException(l.getMessage());
                     }
                  case 1:
                  case 7:
                  case 11:
                     if (this.sourcePath != null) {
                        this.destage();
                     }

                     if (this.task == 9 && (this.applicationMBean.getDeploymentType().equals("EAR") || this.applicationMBean.getDeploymentType().equals("COMPONENT"))) {
                        this.destage();
                     }

                     if (this.task == 9 && (this.applicationMBean.getDeploymentType().equals("EXPLODED COMPONENT") || this.applicationMBean.getDeploymentType().equals("EXPLODED EAR")) && this.requestData.getFiles() == null) {
                        this.destage();
                     }

                     boolean specifiedTargetsOnly = false;
                     DeploymentData deplData = this.getDeploymentData();
                     DeploymentOptions deplOpts = deplData.getDeploymentOptions();
                     if (deplOpts != null) {
                        specifiedTargetsOnly = deplOpts.getSpecifiedTargetsOnly();
                     }

                     this.addTargets(this.task != 7 && !specifiedTargetsOnly);
               }
            }

         } catch (ManagementException var4) {
            ManagementException exception = var4;

            for(int i = 0; i < this.targetsStatus.length; ++i) {
               this.updateTargetStatus(this.targetsStatus[i].getTarget(), 2, exception);
            }

            if (!this.isComplete()) {
               this.setLastException(exception);
               this.handleFailure();
            }

            throw exception;
         }
      }
   }

   public void cancel() throws Exception {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating cancel() to delegate : " + theDelegate);
         theDelegate.cancel();
      } else {
         if (this.myParent == null) {
            this.prepareToCancel();
         } else {
            this.myParent.cancel();
         }

      }
   }

   public void prepareToCancel() throws Exception {
      if (this.hasDelegate()) {
         this.debugSay("Doing nothing in prepareToCancel() since this task has been delegated to : " + this.getDelegate());
      } else {
         synchronized(this) {
            this.setCancelState(2);
            if (this.deploymentTaskStatus == 2 || this.deploymentTaskStatus == 3) {
               Loggable l = DeployerRuntimeLogger.logErrorCannotCancelCompletedTaskLoggable(this.taskId);
               l.log();
               this.lastException = new UnsupportedOperationException(l.getMessage());
               throw this.lastException;
            }
         }
      }
   }

   private void setUpStartedStateVariables() throws ManagementException {
      this.assertNotAlreadyStarted();
      DeployerRuntimeLogger.logStartedDeployment(this.getDescription(), this.applicationDisplayName);
      this.setPendingActivation(false);
      this.setState(1);
      this.updateAllTargetStatus(1);
      this.startTime = System.currentTimeMillis();
      this.validateAppDeploy();
      if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         StringBuffer sb = new StringBuffer();
         sb.append("Starting deployment of " + this.applicationDisplayName + " at " + new Date(this.startTime));
         if (this.targetStatusMap != null) {
            sb.append(" to: '");
            Iterator iterator = this.targetStatusMap.keySet().iterator();

            while(iterator.hasNext()) {
               sb.append((String)iterator.next());
               sb.append(" ");
            }

            sb.append("'");
         }

         if (isDebugEnabled()) {
            this.debugSay(sb.toString());
         } else {
            Debug.deploymentDebugConcise(sb.toString());
         }
      }

      if (Debug.isDeploymentDebugConciseEnabled()) {
         ApplicationRuntimeState appRuntimeState = appRTStateMgr.get(this.applicationId);
         if (appRuntimeState != null) {
            Debug.deploymentDebugConcise("Initial Application Runtime State for " + this.applicationDisplayName + ": " + appRuntimeState);
         }
      }

   }

   private void assertNotAlreadyStarted() throws ManagementException {
      if (this.getState() != 0) {
         Loggable l = DeployerRuntimeLogger.logAlreadyStartedLoggable();
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   private void replaceTargets(TargetMBean[] targets, ComponentMBean dmb) {
      TargetMBean targ = null;
      if (targets != null) {
         try {
            for(int i = 0; i < targets.length; ++i) {
               targ = targets[i];
               if (targ != null) {
                  dmb.addTarget(targ);
               }
            }
         } catch (InvalidAttributeValueException var7) {
            Loggable l = DeployerRuntimeLogger.logNoSuchTargetLoggable(targ.getName());
            l.log();
            this.setLastException(targ.getName(), new ManagementException(l.getMessage(), var7));
         } catch (ManagementException var8) {
            this.debugSay("Rcvd mgmt exception: " + var8.toString());
            this.setLastException(targ.getName(), var8);
         } catch (UndeclaredThrowableException var9) {
            this.debugSay("Rcvd unknown exception: " + var9.toString());
            Throwable t1 = var9.getUndeclaredThrowable();
            if (t1 instanceof ReflectionException) {
               t1 = ((ReflectionException)t1).getTargetException();
            }

            Loggable l = DeployerRuntimeLogger.logAddTargetLoggable(targ.getName(), dmb.getName());
            l.log();
            this.setLastException(targ.getName(), new ManagementException(l.getMessage(), (Throwable)t1));
         }
      }

   }

   private static void initializeDeploymentDataTypes(DeploymentData data) {
      String[] targets = data.getTargets();
      if (targets != null) {
         for(int i = 0; i < targets.length; ++i) {
            data.setTargetType(targets[i], TargetHelper.getTypeForTarget(targets[i]));
         }

      }
   }

   private void initializeTask() throws ManagementException {
      if (this.appDeployMBean != null) {
         this.applicationName = this.appDeployMBean.getApplicationName();
         this.applicationId = this.appDeployMBean.getApplicationIdentifier();
         this.applicationVersionIdentifier = this.appDeployMBean.getVersionIdentifier();
      } else {
         this.applicationId = this.applicationName = this.basicDeployMBean.getName();
      }

      this.applicationDisplayName = ApplicationVersionUtils.getDisplayName(this.basicDeployMBean);
      this.addListenerToAppMBean();
      this.debugSay("Initializing deployment task for: " + this.basicDeployMBean);
      if (!this.requestData.hasTargets() || ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(this.requestData) && !this.requestData.getDeploymentOptions().getSpecifiedTargetsOnly()) {
         this.requestData.addTargetsFromConfig(this.basicDeployMBean);
      }

      this.initTargetDataInTask(this.requestData);
      this.setState(0);
      this.debugSay("Normalized task info: " + this.requestData.toString());
   }

   private void setTargetTypes(DeploymentData internalDeploymentData) throws ManagementException {
      try {
         this.debugSay("Validating target list: " + StringUtils.join(internalDeploymentData.getTargets(), ","));
         initializeDeploymentDataTypes(internalDeploymentData);
      } catch (IllegalArgumentException var4) {
         Loggable l = DeployerRuntimeLogger.logUnconfigTargetsLoggable(new ArrayList(Arrays.asList((Object[])internalDeploymentData.getTargets())));
         l.log();
         throw this.setLastException(new ManagementException(l.getMessage(), var4));
      }
   }

   private synchronized String dumpPendingServerList() {
      StringBuffer sb = new StringBuffer();
      sb.append("PendingServers set : ");
      if (this.pendingServers == null) {
         sb.append("null");
      } else {
         Iterator var2 = this.pendingServers.iterator();

         while(var2.hasNext()) {
            String ps = (String)var2.next();
            sb.append(" ");
            sb.append(ps);
         }
      }

      return sb.toString();
   }

   private void initTargetDataInTask(DeploymentData dd) throws ManagementException {
      if (dd.hasTargets()) {
         this.hasTargets = true;
         this.setTargetTypes(this.requestData);
         Set logicalTargets = dd.getAllLogicalTargets();

         try {
            this.pendingServers = (HashSet)dd.getAllTargetedServers(logicalTargets, this.editableDomainMBean);
            this.debugSay(this.dumpPendingServerList());
         } catch (InvalidTargetException var4) {
            this.debugSay("initTargetDataInTask: InvalidTargetException for target: " + var4.toString());
         }

         String[] tmp = (String[])((String[])logicalTargets.toArray(new String[0]));
         this.targets = tmp;
         this.initializeTargetStatuses(logicalTargets);
      }

   }

   private void initializeTargetStatuses(Set targetNames) {
      int len = targetNames.size();
      Iterator iter = targetNames.iterator();

      int pos;
      String targetName;
      for(pos = 0; iter.hasNext(); ++pos) {
         targetName = (String)iter.next();
         if (targetName.trim().equals("")) {
            --len;
         }
      }

      this.targetsStatus = new TargetStatus[len];
      iter = targetNames.iterator();
      pos = 0;

      while(iter.hasNext()) {
         targetName = (String)iter.next();
         if (!targetName.trim().equals("")) {
            this.targetsStatus[pos] = new TargetStatus(targetName);
            this.addToServerToTargetStatusMap(targetName, this.targetsStatus[pos]);
            ++pos;
         }
      }

      for(int i = 0; i < this.targetsStatus.length; ++i) {
         this.debugSay("Adding: " + this.targetsStatus[i].getTarget() + " to targetStatusMap");
         this.targetStatusMap.put(this.targetsStatus[i].getTarget(), this.targetsStatus[i]);
      }

   }

   private AppDeploymentMBean lookupAppDeployment(String id) {
      return this.editableDomainMBean != null ? this.editableDomainMBean.lookupAppDeployment(id) : null;
   }

   private static ServerMBean lookupServer(String targetName, DomainMBean domain) {
      return domain.lookupServer(targetName);
   }

   private ServerMBean lookupServer(String targetName) {
      ServerMBean result = null;
      if (this.editableDomainMBean != null) {
         result = lookupServer(targetName, this.editableDomainMBean);
      }

      if (result == null) {
         result = lookupServer(targetName, ManagementService.getRuntimeAccess(kernelId).getDomain());
      }

      return result;
   }

   private static ClusterMBean lookupCluster(String targetName, DomainMBean domain) {
      return domain.lookupCluster(targetName);
   }

   private ClusterMBean lookupCluster(String targetName, boolean runtimeOnly) {
      ClusterMBean result = null;
      if (this.editableDomainMBean != null && !runtimeOnly) {
         result = lookupCluster(targetName, this.editableDomainMBean);
      }

      if (result == null) {
         result = lookupCluster(targetName, ManagementService.getRuntimeAccess(kernelId).getDomain());
      }

      return result;
   }

   private static JMSServerMBean lookupJMSServer(String targetName, DomainMBean domain) {
      return domain.lookupJMSServer(targetName);
   }

   private static SAFAgentMBean lookupSAFAgent(String targetName, DomainMBean domain) {
      return domain.lookupSAFAgent(targetName);
   }

   private static VirtualHostMBean lookupVirtualHost(String targetName, DomainMBean domain) {
      return domain.lookupVirtualHost(targetName);
   }

   private static VirtualTargetMBean lookupVirtualTarget(String targetName, DomainMBean domain) {
      return domain.lookupInAllVirtualTargets(targetName);
   }

   private JMSServerMBean lookupJMSServer(String targetName) {
      JMSServerMBean result = null;
      if (this.editableDomainMBean != null) {
         result = lookupJMSServer(targetName, this.editableDomainMBean);
      }

      if (result == null) {
         result = lookupJMSServer(targetName, ManagementService.getRuntimeAccess(kernelId).getDomain());
      }

      return result;
   }

   private SAFAgentMBean lookupSAFAgent(String targetName) {
      SAFAgentMBean result = null;
      if (this.editableDomainMBean != null) {
         result = lookupSAFAgent(targetName, this.editableDomainMBean);
      }

      if (result == null) {
         result = lookupSAFAgent(targetName, ManagementService.getRuntimeAccess(kernelId).getDomain());
      }

      return result;
   }

   private VirtualHostMBean lookupVirtualHost(String targetName) {
      VirtualHostMBean result = null;
      if (this.editableDomainMBean != null) {
         result = lookupVirtualHost(targetName, this.editableDomainMBean);
      }

      if (result == null) {
         result = lookupVirtualHost(targetName, ManagementService.getRuntimeAccess(kernelId).getDomain());
      }

      return result;
   }

   private VirtualTargetMBean lookupVirtualTarget(String targetName) {
      VirtualTargetMBean result = null;
      if (this.editableDomainMBean != null) {
         result = lookupVirtualTarget(targetName, this.editableDomainMBean);
      }

      if (result == null) {
         result = lookupVirtualTarget(targetName, ManagementService.getRuntimeAccess(kernelId).getDomain());
      }

      return result;
   }

   private void addToServerToTargetStatusMap(String targetName, TargetStatus targetStatus) {
      ServerMBean server = this.lookupServer(targetName);
      if (server != null) {
         this.debugSay("Adding " + server + " to server target status");
         targetStatus.setTargetListEmpty(false);
         this.checkAndAddToServerToTargetStatusMap(targetName, targetStatus);
      } else {
         ClusterMBean cluster = this.lookupCluster(targetName, false);
         if (cluster == null) {
            JMSServerMBean jmsServer = this.lookupJMSServer(targetName);
            if (jmsServer != null) {
               TargetMBean[] targets = jmsServer.getTargets();
               this.addLogicTargetToMap(targets, jmsServer.getName(), targetStatus);
            } else {
               SAFAgentMBean safAgent = this.lookupSAFAgent(targetName);
               if (safAgent != null) {
                  TargetMBean[] targets = safAgent.getTargets();
                  this.addLogicTargetToMap(targets, safAgent.getName(), targetStatus);
               } else {
                  VirtualHostMBean host = this.lookupVirtualHost(targetName);
                  if (host != null) {
                     TargetMBean[] targets = host.getTargets();
                     this.addLogicTargetToMap(targets, host.getName(), targetStatus);
                  } else {
                     VirtualTargetMBean virtualTarget = this.lookupVirtualTarget(targetName);
                     if (virtualTarget != null) {
                        TargetMBean[] targets = virtualTarget.getTargets();
                        this.addLogicTargetToMap(targets, virtualTarget.getName(), targetStatus);
                     }
                  }
               }
            }
         } else {
            ServerMBean[] servers = cluster.getServers();
            targetStatus.setTargetListEmpty(servers == null || servers.length == 0);

            for(int i = 0; i < servers.length; ++i) {
               this.debugSay("Adding " + servers[i].getName() + " to target status for cluster: " + cluster.getName());
               this.checkAndAddToServerToTargetStatusMap(servers[i].getName(), targetStatus);
            }

            ClusterMBean rtCluster = this.lookupCluster(targetName, true);
            if (rtCluster != null) {
               ServerMBean[] rtServers = rtCluster.getServers();
               if (targetStatus.isTargetListEmpty()) {
                  targetStatus.setTargetListEmpty(rtServers == null || rtServers.length == 0);
               }

               for(int i = 0; i < rtServers.length; ++i) {
                  this.debugSay("Adding " + rtServers[i].getName() + " to target status for cluster: " + cluster.getName());
                  this.checkAndAddToServerToTargetStatusMap(rtServers[i].getName(), targetStatus);
               }
            }
         }

         this.dumpServerToTargetStatusMap();
      }

   }

   private void checkAndAddToServerToTargetStatusMap(String targetName, TargetStatus targetStatus) {
      List allMappedTargets = (List)this.serverToTargetStatusMap.get(targetName);
      if (allMappedTargets == null) {
         allMappedTargets = new ArrayList();
         ((List)allMappedTargets).add(targetStatus);
      } else if (!((List)allMappedTargets).contains(targetStatus)) {
         ((List)allMappedTargets).add(targetStatus);
      }

      this.serverToTargetStatusMap.put(targetName, allMappedTargets);
   }

   private void addLogicTargetToMap(TargetMBean[] targets, String name, TargetStatus targetStatus) {
      if (targets != null && targets.length != 0) {
         Set allServerNames = new HashSet();

         for(int i = 0; i < targets.length; ++i) {
            Set serverNames = targets[i].getServerNames();
            if (serverNames != null) {
               allServerNames.addAll(serverNames);
            }

            if (targets[i] instanceof ClusterMBean) {
               ClusterMBean rtCluster = this.lookupCluster(targets[i].getName(), true);
               if (rtCluster != null) {
                  Set rtServerNames = rtCluster.getServerNames();
                  if (rtServerNames != null) {
                     allServerNames.addAll(rtServerNames);
                  }
               }
            }
         }

         this.debugSay("Collected distinct server list : " + allServerNames);
         targetStatus.setTargetListEmpty(allServerNames.isEmpty());
         Iterator iterator = allServerNames.iterator();

         while(iterator.hasNext()) {
            String serverName = (String)iterator.next();
            this.debugSay("Adding " + serverName + " to target status for target: " + name);
            this.checkAndAddToServerToTargetStatusMap(serverName, targetStatus);
         }

      } else {
         targetStatus.setTargetListEmpty(true);
      }
   }

   private void dumpServerToTargetStatusMap() {
      if (isDebugEnabled() && this.serverToTargetStatusMap != null) {
         StringBuffer sb = new StringBuffer();
         sb.append("serverToTargetStatusMap for task: ");
         sb.append(this.taskId);
         Iterator var2 = this.serverToTargetStatusMap.keySet().iterator();

         while(true) {
            List mappedTargets;
            do {
               if (!var2.hasNext()) {
                  Debug.deploymentDebug(sb.toString());
                  return;
               }

               String target = (String)var2.next();
               sb.append("\ntarget: ");
               sb.append(target);
               sb.append(" statuses: ");
               mappedTargets = (List)this.serverToTargetStatusMap.get(target);
            } while(mappedTargets == null);

            Iterator iter = mappedTargets.iterator();

            for(int counter = 0; iter.hasNext(); sb.append(iter.next())) {
               if (counter > 0) {
                  sb.append(", ");
               }
            }

            sb.append("\n");
         }
      }
   }

   private void logCompletion() {
      if (Debug.isDeploymentDebugConciseEnabled()) {
         ApplicationRuntimeState appRuntimeState = appRTStateMgr.get(this.applicationId);
         if (appRuntimeState != null) {
            Debug.deploymentDebugConcise("Final Application Runtime State for " + this.applicationDisplayName + ": " + appRuntimeState);
         }
      }

      int theState = this.getState();
      switch (theState) {
         case 2:
            DeployerRuntimeLogger.logTaskSuccess(this.getDescription());
            if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("Deployment successfully completed for task: " + this.getDescription());
            }
            break;
         case 3:
            Exception e = this.getError();
            String id;
            if (this.applicationDisplayName != null) {
               id = DeployerRuntimeLogger.logTaskFailed(this.applicationDisplayName, DeployHelper.getTaskName(this.task));
            } else {
               id = DeployerRuntimeLogger.logTaskFailedNoApp(DeployHelper.getTaskName(this.task));
            }

            if (e != null) {
               DeployerRuntimeLogger.logTrace(id, e);
            }

            if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("Deployment failed for deployment task: " + this.getDescription() + (e != null ? " with Exception: " + e : ""));
            }
            break;
         case 4:
            DeployerRuntimeLogger.logTaskDeferred(this.getDescription());
            if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("Deployment is deferred for target(s) " + Arrays.asList(this.unreachableTargets) + " for task: " + this.getDescription());
            }
      }

   }

   private void destage() {
      this.applicationMBean.unstageTargets(this.targets);
   }

   public synchronized void updatePendingServersWithSuccess() {
      if (this.deploymentTaskStatus == 1) {
         if (this.pendingServers == null || this.pendingServers.isEmpty()) {
            this.updateTargetStatus((String)null, 3, (Exception)null);
            return;
         }

         HashSet pendingServersClone = (HashSet)this.pendingServers.clone();
         Iterator var2 = pendingServersClone.iterator();

         while(var2.hasNext()) {
            String targetName = (String)var2.next();
            this.updateTargetStatus(targetName, 3, (Exception)null);
         }
      }

   }

   public void updateAllTargetsWithSuccessForMerging() {
      this.setState(1);
      this.updateAllTargetStatus(1);
      this.updatePendingServersWithSuccess();
   }

   private void updateAllTargetStatus(int targetStatus) {
      TargetStatus[] targets = this.targetsStatus;
      if (targets != null) {
         synchronized(this) {
            for(int i = 0; i < targets.length; ++i) {
               if (this.deploymentTaskStatus == 1) {
                  targets[i].setState(targetStatus);
               }
            }

         }
      }
   }

   private void addTargets(boolean throwExceptionIfServerIsContainedInExistingCluster) throws ManagementException {
      DeploymentMBean comp = null;
      DeploymentMBean[] comps = this.applicationMBean.getComponents();

      int i;
      label96:
      for(i = 0; i < comps.length; ++i) {
         ComponentTargetValidator validator = new ComponentTargetValidator((ComponentMBean)comps[i], throwExceptionIfServerIsContainedInExistingCluster);
         Iterator targs = this.requestData.getTargetsForModule(comps[i].getName()).iterator();

         while(true) {
            String currTarget;
            VirtualHostMBean virtualHost;
            do {
               VirtualTargetMBean virtualTarget;
               do {
                  if (!targs.hasNext()) {
                     continue label96;
                  }

                  currTarget = (String)targs.next();
                  this.debugSay("Checking target " + currTarget);
                  virtualTarget = this.editableDomainMBean.lookupVirtualTarget(currTarget);
               } while(virtualTarget != null);

               virtualHost = this.editableDomainMBean.lookupVirtualHost(currTarget);
            } while(virtualHost != null && !(comps[i] instanceof WebAppComponentMBean));

            validator.addTarget(currTarget, this.editableDomainMBean, throwExceptionIfServerIsContainedInExistingCluster);
         }
      }

      for(i = 0; i < this.targets.length; ++i) {
         boolean haveModules = true;
         String target = this.targets[i];
         String[] modules = this.requestData.getModulesForTarget(target);
         int targetType = this.requestData.getTargetType(target);
         Object comps;
         if (modules != null && modules.length != 0) {
            this.debugSay("Modules Specified");
            HashSet set = new HashSet();

            for(int j = 0; j < modules.length; ++j) {
               set = this.addComponentToSet(set, modules[j]);
            }

            comps = (DeploymentMBean[])((DeploymentMBean[])set.toArray(new DeploymentMBean[set.size()]));
         } else {
            this.debugSay("No Modules Specified");
            comps = this.applicationMBean.getComponents();
            haveModules = false;
         }

         Loggable l;
         try {
            for(int k = 0; k < ((Object[])comps).length; ++k) {
               comp = ((Object[])comps)[k];
               if (targetType == 3) {
                  if (comp instanceof WebDeploymentMBean) {
                     VirtualHostMBean vhmb = this.getDomain().lookupVirtualHost(target);
                     this.debugSay("Adding virtual host " + target + " for module, " + ((DeploymentMBean)comp).getName());
                     if (vhmb != null) {
                        ((WebAppComponentMBean)comp).addVirtualHost(vhmb);
                     }
                  } else if (haveModules) {
                     l = DeployerRuntimeLogger.logInvalidTargetForComponentLoggable(this.applicationDisplayName, ((DeploymentMBean)comp).getName(), target);
                     l.log();
                     throw new ManagementException(l.getMessage());
                  }
               } else {
                  TargetMBean[] tmb = new TargetMBean[]{this.getTargetMBean(target)};
               }
            }
         } catch (InvalidAttributeValueException var12) {
            l = DeployerRuntimeLogger.logNoSuchTargetLoggable(target);
            l.log();
            this.setLastException(target, new ManagementException(l.getMessage(), var12));
         } catch (ManagementException var13) {
            this.debugSay("Rcvd mgmt exception: " + var13.toString());
            this.setLastException(target, var13);
         } catch (UndeclaredThrowableException var14) {
            this.debugSay("Rcvd unknown exception: " + var14.toString());
            Throwable t1 = var14.getUndeclaredThrowable();
            if (t1 instanceof ReflectionException) {
               t1 = ((ReflectionException)t1).getTargetException();
            }

            Loggable l = DeployerRuntimeLogger.logAddTargetLoggable(target, ((DeploymentMBean)comp).getName());
            l.log();
            this.setLastException(target, new ManagementException(l.getMessage(), (Throwable)t1));
         } catch (IllegalArgumentException var15) {
            l = DeployerRuntimeLogger.logAddTargetLoggable(target, ((DeploymentMBean)comp).getName());
            l.log();
            this.setLastException(target, new ManagementException(l.getMessage(), var15));
         }
      }

   }

   private HashSet addComponentToSet(HashSet s, String comp) {
      this.debugSay("looking for component " + comp);
      ComponentMBean[] components = this.applicationMBean.getComponents();
      StringBuffer moduleNamesBuffer = new StringBuffer();

      for(int i = 0; i < components.length; ++i) {
         ComponentMBean component = components[i];
         this.debugSay("found name = " + component.getName() + ", with uri " + component.getURI());
         if (i == components.length - 1) {
            moduleNamesBuffer.append(component.getName() + " ");
         } else {
            moduleNamesBuffer.append(component.getName() + ", ");
         }

         if (component.getName().equals(comp)) {
            s.add(component);
            this.debugSay("Found the component " + comp);
         }
      }

      return s;
   }

   private synchronized void addMessage(String msg) {
      this.taskMessages.add(msg);
   }

   private void setClientLocale() {
      JMXContext jmxContext = JMXContextHelper.getJMXContext(false);
      if (jmxContext != null) {
         this.clientLocale = jmxContext.getLocale();
      }

   }

   private Locale getClientLocale() {
      return this.clientLocale;
   }

   private void addListenerToAppMBean() {
      if (this.applicationMBean != null) {
         this.getDeploymentObject().addNotificationListener(this.appListener, new DeployFilter(), this.getId());
      }

   }

   private void removeAppListener() {
      if (this.getDeploymentObject() != null) {
         try {
            this.getDeploymentObject().removeNotificationListener(this.appListener);
         } catch (ListenerNotFoundException var2) {
            this.debugSay("remove listener ex: " + var2.toString());
         }
      }

      this.appListener = null;
   }

   public void setNotificationLevel(int level) {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating setNotificationLevel() to delegate : " + theDelegate);
         theDelegate.setNotificationLevel(level);
      } else {
         if (level < 0) {
            level = 0;
         }

         if (level > 2) {
            level = 2;
         }

         this.notifLevel = level;
      }
   }

   public synchronized void setSubtaskState(String taskId, int state) {
      this.subtaskState.put(taskId, state);
   }

   public void setState(int newState) {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating setState() to delegate : " + theDelegate);
         theDelegate.setState(newState);
      } else {
         boolean toBeReset = false;
         StringBuffer sb = new StringBuffer();
         sb.append(this.deploymentAction.getDescription()).append(" ");

         try {
            switch (newState) {
               case 0:
                  sb.append(fmt.init());
                  break;
               case 1:
                  sb.append(fmt.running());
                  break;
               case 2:
                  sb.append(fmt.completed());
                  this.notifyAppDeployEnded();
                  toBeReset = true;
                  break;
               case 3:
                  sb.append(fmt.failed());
                  this.notifyAppDeployEnded();
                  toBeReset = true;
                  break;
               case 4:
                  sb.append(fmt.deferred());
                  this.notifyAppDeployEnded();
                  toBeReset = true;
                  break;
               default:
                  return;
            }

            int oldState;
            synchronized(this) {
               oldState = this.deploymentTaskStatus;
               this.deploymentTaskStatus = newState;
               this.deploymentTaskStatusMessage = sb.toString();
               this.debugSay("New Status for task " + this.taskId + " is " + this.deploymentTaskStatus + ":" + this.deploymentTaskStatusMessage);
            }

            this._postSet("State", oldState, newState);
         } finally {
            if (toBeReset) {
               this.reset();
            }

         }
      }
   }

   public void setSystemTask(boolean sys) {
      this.sysTask = sys;
   }

   private void reset() {
      this.debugSay("Resetting task " + this.getId());
      this.appDeployMBean = null;
      this.applicationMBean = null;
      this.versionTargetStatusMap.clear();
      this.basicDeployMBean = null;
      this.appListener = null;
      if (!this.callerOwnsEditLock()) {
         if (this.editableDomainMBean != null) {
            EditAccessHelper editAccessHelper = null;
            if (this.adminOperation != null) {
               editAccessHelper = this.adminOperation.getEditAccessHelper();
            } else {
               editAccessHelper = EditAccessHelper.getInstance(kernelId);
            }

            if (editAccessHelper.getCurrentEditor() == null) {
               this.editableDomainMBean.removeBeanUpdateListener(this);
            }

            this.editableDomainMBean = null;
         }
      } else if (this.editableDomainMBean != null) {
         this.editableDomainMBean.removeBeanUpdateListener(this);
         this.editableDomainMBean = null;
      }

      this.adminOperation = null;
      if (this.myParent != null) {
         try {
            ((DeploymentRequestTaskRuntimeMBeanImpl)this.myParent).unregister();
         } catch (Throwable var3) {
            var3.printStackTrace();
         }

         this.myParent = null;
      }

      if (!this.delegators.isEmpty()) {
         Iterator iter = this.delegators.iterator();

         while(iter.hasNext()) {
            DeploymentTaskRuntime theTask = (DeploymentTaskRuntime)iter.next();
            theTask.reset();
         }

         this.delegators.clear();
      }

      if (this.configChange) {
         try {
            this.unregister();
         } catch (ManagementException var4) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Remove DeploymentTaskRuntimeMBean failed for id: " + this.taskId + ", ex:" + var4.getMessage());
            }
         }
      }

   }

   private AppDeploymentMBean getCurrentApp() {
      AppDeploymentMBean realApp = ApplicationVersionUtils.getAppDeployment(this.getDomain(), this.applicationId, (String)null);
      return realApp;
   }

   private boolean callerOwnsEditLock() {
      if (this.getSubject() == null) {
         return false;
      } else {
         EditAccessHelper editAccessHelper = null;
         if (this.adminOperation != null) {
            editAccessHelper = this.adminOperation.getEditAccessHelper();
         } else {
            editAccessHelper = EditAccessHelper.getInstance(kernelId);
         }

         return editAccessHelper.isCurrentEditor(this.getSubject());
      }
   }

   public void setDescription() {
      String taskName = DeployHelper.getTaskName(this.task);
      Set logicalTargets = this.requestData.getAllLogicalTargets();
      String targets = StringUtils.join((String[])((String[])logicalTargets.toArray(new String[logicalTargets.size()])), ",");
      Loggable log;
      if (this.requestData.getDeploymentOptions().getResourceGroupTemplate() != null) {
         if (this.requestData.isLibrary()) {
            log = DeployerRuntimeLogger.logLibraryDescriptionLoggable(this.applicationDisplayName, this.requestData.getDeploymentOptions().getResourceGroupTemplate(), taskName);
         } else {
            log = DeployerRuntimeLogger.logDescriptionLoggable(this.applicationDisplayName, this.requestData.getDeploymentOptions().getResourceGroupTemplate(), taskName);
         }
      } else if (this.requestData.isLibrary()) {
         log = DeployerRuntimeLogger.logLibraryDescriptionLoggable(this.applicationDisplayName, targets, taskName);
      } else {
         log = DeployerRuntimeLogger.logDescriptionLoggable(this.applicationDisplayName, targets, taskName);
      }

      this.deploymentTaskDescription = log.getMessage();
      this.debugSay("New task: " + this.deploymentTaskDescription);
   }

   private ManagementException setLastException(String target, ManagementException e) {
      this.lastException = e;
      if (target != null) {
         TargetStatus ts = this.getTargetStatus(target);
         if (ts != null) {
            ts.addMessage(e);
            ts.setState(2);
         }
      }

      return e;
   }

   private ManagementException setLastException(ManagementException e) {
      return this.setLastException((String)null, e);
   }

   public RuntimeMBean getMBean() {
      return this;
   }

   public String getSource() {
      return this.saveSource;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public String getApplicationId() {
      return this.applicationId;
   }

   public void setApplicationId(String id) {
      this.applicationId = id;
   }

   public String getDescription() {
      return this.deploymentTaskDescription;
   }

   public String getStatus() {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating getStatus() to delegate : " + theDelegate);
         return theDelegate.getStatus();
      } else {
         return this.deploymentTaskStatusMessage;
      }
   }

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else {
         return 2 == this.getState() ? "success" : "failed";
      }
   }

   public boolean isRunning() {
      int n;
      if (this.subTasks != null && this.subTasks.length > 0) {
         for(n = 0; n < this.subTasks.length; ++n) {
            if (this.subTasks[n].isRunning()) {
               return true;
            }
         }

         return false;
      } else if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating isRunning() to delegate : " + theDelegate);
         return theDelegate.isRunning();
      } else {
         n = this.getState();
         return this.cancelState != 8 && n != 2 && n != 3 && n != 4;
      }
   }

   public long getBeginTime() {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating getBeginTime() to delegate : " + theDelegate);
         return theDelegate.getBeginTime();
      } else {
         return this.startTime;
      }
   }

   public long getEndTime() {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating getEndTime() to delegate : " + theDelegate);
         return theDelegate.getEndTime();
      } else {
         return this.endTime;
      }
   }

   public Exception getError() {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating getError() to delegate : " + theDelegate);
         return theDelegate.getError();
      } else {
         return this.lastException;
      }
   }

   public int getTask() {
      return this.task;
   }

   public ApplicationMBean getDeploymentObject() {
      if (this.endTime > 0L) {
         AppDeploymentMBean app = this.getCurrentApp();
         return app == null ? null : app.getAppMBean();
      } else {
         return this.applicationMBean;
      }
   }

   public BasicDeploymentMBean getDeploymentMBean() {
      return this.basicDeployMBean;
   }

   public String getApplicationVersionIdentifier() {
      return this.applicationVersionIdentifier;
   }

   public DeploymentData getDeploymentData() {
      return this.requestData;
   }

   public TargetStatus[] getTargets() {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating getTargets() to delegate : " + theDelegate);
         return theDelegate.getTargets();
      } else {
         TargetStatus[] copyTS = null;
         if (this.targetsStatus != null) {
            copyTS = new TargetStatus[this.targetsStatus.length];

            for(int i = 0; i < this.targetsStatus.length; ++i) {
               copyTS[i] = this.targetsStatus[i].copy();
            }
         }

         return copyTS;
      }
   }

   public String getId() {
      return this.taskId;
   }

   public int getNotificationLevel() {
      return this.notifLevel;
   }

   public Map getVersionTargetStatusMap() {
      return this.versionTargetStatusMap;
   }

   public boolean isInUse() {
      return this.inUse;
   }

   public void setInUse(boolean inUse) {
      this.inUse = inUse;
   }

   public synchronized int getState() {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating getState() to delegate : " + theDelegate);
         return theDelegate.getState();
      } else {
         if (this.subtaskState.size() > 0) {
            boolean subtasksCompleted = true;
            Collection subTaskValues = this.subtaskState.values();
            Iterator subTaskIt = subTaskValues.iterator();

            while(subTaskIt.hasNext()) {
               Integer st = (Integer)subTaskIt.next();
               if (st != 2 && st != 3) {
                  subtasksCompleted = false;
               }
            }

            if (subtasksCompleted) {
               return 2;
            }
         }

         return this.deploymentTaskStatus;
      }
   }

   public boolean isSystemTask() {
      return this.sysTask;
   }

   private boolean hasFiles() {
      return this.requestData != null && this.requestData.getFiles() != null && this.requestData.getFiles().length > 0;
   }

   public boolean hasTargets() {
      return this.hasTargets;
   }

   public TaskRuntimeMBean[] getSubTasks() {
      return this.subTasks;
   }

   public void setSubTasks(TaskRuntimeMBean[] st) {
      this.subTasks = st;
      if (st != null) {
         for(int n = 0; n < st.length; ++n) {
            ((DeploymentTaskRuntime)st[n]).setTaskParent(this);
         }
      }

   }

   public TaskRuntimeMBean getParentTask() {
      return null;
   }

   public TaskRuntimeMBean getTaskParent() {
      return this.taskParent;
   }

   void setTaskParent(DeploymentTaskRuntime theTaskParent) {
      this.taskParent = theTaskParent;
   }

   public TaskRuntimeMBean getMyParent() {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating getMyParent() to delegate : " + theDelegate);
         return theDelegate.getMyParent();
      } else {
         return this.myParent;
      }
   }

   public void setMyParent(TaskRuntimeMBean parent) {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating setMyParent() to delegate : " + theDelegate);
         theDelegate.setMyParent(parent);
      } else {
         this.myParent = (DeploymentRequestTaskRuntimeMBean)parent;
      }
   }

   public synchronized boolean isComplete() {
      if (this.subTasks != null && this.subTasks.length > 0) {
         for(int n = 0; n < this.subTasks.length; ++n) {
            if (this.subTasks[n].isRunning()) {
               return false;
            }
         }

         return true;
      } else if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating isComplete() to delegate : " + theDelegate);
         return theDelegate.isComplete();
      } else if (this.deploymentTaskStatus != 2 && this.deploymentTaskStatus != 3 && this.deploymentTaskStatus != 4) {
         this.debugSay("DeploymentTaskRuntime: isComplete status for task  id: " + this.getId() + " is 'false' deploymentStatus: " + this.deploymentTaskStatus + ", pending servers: " + this.dumpPendingServerList());
         return false;
      } else {
         return true;
      }
   }

   public final synchronized Map getFailedTargets() {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating getFailedTargets() to delegate : " + theDelegate);
         return theDelegate.getFailedTargets();
      } else {
         return (Map)this.failedTargets.clone();
      }
   }

   public HashSet getAllPhysicalServers() {
      HashSet theServers = new HashSet();
      Set tempSet = this.serverToTargetStatusMap.keySet();
      theServers.addAll(tempSet);
      return theServers;
   }

   private TargetMBean getTargetMBean(String targetName) {
      TargetMBean ret = this.getDomain().lookupServer(targetName);
      if (ret == null) {
         ret = this.getDomain().lookupCluster(targetName);
      }

      return (TargetMBean)ret;
   }

   private List getTargetStatusesForServer(String serverName) {
      this.dumpServerToTargetStatusMap();
      return (List)this.serverToTargetStatusMap.get(serverName);
   }

   public boolean isTaskFailedAsTargetNotUp() {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating isTaskFailedAsTargetNotUp() to delegate : " + theDelegate);
         return theDelegate.isTaskFailedAsTargetNotUp();
      } else {
         return this.failedTaskAsTargetNotUp;
      }
   }

   public void setTaskFailedAsTargetNotUp(boolean failTask) {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating setTaskFailedAsTargetNotUp() to delegate : " + theDelegate);
         theDelegate.setTaskFailedAsTargetNotUp(failTask);
      } else {
         this.failedTaskAsTargetNotUp = true;
      }
   }

   private void debugSay(String msg) {
      if (isDebugEnabled()) {
         Debug.deploymentDebug("[" + this.taskId + "]:" + msg);
      }

   }

   private static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   public void setCancelState(int state) {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating setCancelState() to delegate : " + theDelegate);
         theDelegate.setCancelState(state);
      } else {
         synchronized(this) {
            this.cancelState = state;
         }

         this.debugSay("cancel state set to " + this.getCancelStateString());
         if (this.cancelState == 8) {
            this.reset();
         }

      }
   }

   public synchronized int getCancelState() {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating getCancelState() to delegate : " + theDelegate);
         return theDelegate.getCancelState();
      } else {
         return this.cancelState;
      }
   }

   private void validateAppDeploy() throws ManagementException {
      if (this.basicDeployMBean != null) {
         boolean isNewApp = this.requestData == null ? false : this.requestData.isNewApplication();
         String[] modules = this.requestData == null ? null : this.requestData.getModules();
         String[] targets = this.requestData == null ? null : this.requestData.getTargets();
         if (this.task == 1) {
            DeploymentEventManager.sendVetoableDeploymentEvent(VetoableDeploymentEvent.create(this, VetoableDeploymentEvent.APP_ACTIVATE, this.basicDeployMBean, isNewApp, modules, targets));
         } else if (this.task != 11 && this.task != 9 && this.task != 10) {
            if (this.task == 7) {
               DeploymentEventManager.sendVetoableDeploymentEvent(VetoableDeploymentEvent.create(this, VetoableDeploymentEvent.APP_START, this.basicDeployMBean, isNewApp, modules, targets));
            } else if (this.task == 4 || this.task == 12) {
               DeploymentEventManager.sendVetoableDeploymentEvent(VetoableDeploymentEvent.create(this, VetoableDeploymentEvent.APP_UNDEPLOY, this.basicDeployMBean, false, modules, targets));
            }
         } else {
            DeploymentEventManager.sendVetoableDeploymentEvent(VetoableDeploymentEvent.create(this, VetoableDeploymentEvent.APP_DEPLOY, this.basicDeployMBean, isNewApp, modules, targets));
         }

      }
   }

   private void notifyAppDeployEnded() {
      if (this.appDeployMBean != null) {
         String[] modules = this.requestData == null ? null : this.requestData.getModules();
         String[] targets = this.requestData == null ? null : this.requestData.getTargets();
         if (this.task == 1) {
            DeploymentEventManager.sendDeploymentEvent(DeploymentEvent.create(this, DeploymentEvent.APP_ACTIVATED, this.appDeployMBean, modules, targets));
         } else if (this.task != 11 && this.task != 9 && this.task != 10) {
            if (this.task == 7) {
               DeploymentEventManager.sendDeploymentEvent(DeploymentEvent.create(this, DeploymentEvent.APP_STARTED, this.appDeployMBean, modules, targets));
            } else if ((this.task == 4 || this.task == 12) && this.lookupAppDeployment(this.applicationId) == null) {
               this.handleAppDeleted(modules, targets);
            }
         } else {
            BaseDeploymentEvent.EventType eventType = DeploymentEvent.APP_DEPLOYED;
            if (this.task == 9 || this.task == 10) {
               eventType = DeploymentEvent.APP_REDEPLOYED;
            }

            DeploymentEventManager.sendDeploymentEvent(DeploymentEvent.create(this, eventType, this.appDeployMBean, modules, targets));
         }

      }
   }

   private void handleAppDeleted(String[] modules, String[] targets) {
      if (this.appDeployMBean.getParent() instanceof ResourceGroupTemplateMBean && !(this.appDeployMBean.getParent() instanceof ResourceGroupMBean)) {
         ResourceGroupTemplateMBean resourceGroupTemplateMBean = (ResourceGroupTemplateMBean)this.appDeployMBean.getParent();
         this.debugSay("DeploymentListener.applicationDeleted Application is deployed to ResourceGroupTemplate " + resourceGroupTemplateMBean.getName());
      } else if (this.appDeployMBean.getParent() instanceof ResourceGroupMBean) {
         ResourceGroupMBean resourceGroupMBean = (ResourceGroupMBean)this.appDeployMBean.getParent();
         this.debugSay("DeploymentListener.applicationDeleted Application is deployed to ResourceGroup " + resourceGroupMBean.getName());
         if (resourceGroupMBean.getResourceGroupTemplate() == null) {
            this.debugSay("DeploymentListener.applicationDeleted Application is deployed to ResourceGroup, which is not associated with RGT");
            if (resourceGroupMBean.getParent() instanceof DomainMBean) {
               this.debugSay("DeploymentListener.applicationDeleted Application is deployed to ResourceGroup at domain level " + resourceGroupMBean.getParent());
               DeploymentEventManager.sendDeploymentEvent(DeploymentEvent.create(this, DeploymentEvent.APP_DELETED, this.appDeployMBean, false, modules, targets));
            } else {
               this.debugSay("DeploymentListener.applicationDeleted Application is deployed to ResourceGroup at partition level " + resourceGroupMBean.getParent());

               try {
                  AppDeploymentMBean rgAppDeploymentMBean = (AppDeploymentMBean)((AbstractDescriptorBean)resourceGroupMBean)._createChildBean(this.appDeployMBean.getClass(), -1);
                  ((DescriptorImpl)this.appDeployMBean.getDescriptor()).resolveReferences();
                  rgAppDeploymentMBean.unSet("Name");
                  rgAppDeploymentMBean.setName(PartitionProcessor.addSuffix((PartitionMBean)resourceGroupMBean.getParent(), this.appDeployMBean.getName()));
                  DeploymentEventManager.sendDeploymentEvent(DeploymentEvent.create(this, DeploymentEvent.APP_DELETED, rgAppDeploymentMBean, false, modules, targets));
                  rgAppDeploymentMBean = null;
               } catch (ManagementException | InvalidAttributeValueException var7) {
                  throw new AssertionError(var7);
               }
            }
         } else {
            this.debugSay("DeploymentListener.applicationDeleted Application is deployed to ResourceGroup, which is  associated with RGT " + resourceGroupMBean.getResourceGroupTemplate().getName());
            if (resourceGroupMBean.getParent() instanceof DomainMBean) {
               this.debugSay("DeploymentListener.applicationDeleted Application is deployed to ResourceGroup at domain level " + resourceGroupMBean.getParent());
               DeploymentEventManager.sendDeploymentEvent(DeploymentEvent.create(this, DeploymentEvent.APP_DELETED, this.appDeployMBean, false, modules, targets));
            } else {
               this.debugSay("DeploymentListener.applicationDeleted Application is deployed to ResourceGroup at partition level " + resourceGroupMBean.getParent());
               Boolean[] areDefinedInTemplate = resourceGroupMBean.areDefinedInTemplate(new ConfigurationMBean[]{this.appDeployMBean});
               if (areDefinedInTemplate != null && areDefinedInTemplate[0]) {
                  this.debugSay("DeploymentListener.applicationDeleted Application is deployed to ResourceGroup at partition level, not inherited from RGT ");

                  try {
                     AppDeploymentMBean rgAppDeploymentMBean = (AppDeploymentMBean)((AbstractDescriptorBean)resourceGroupMBean)._createChildBean(this.appDeployMBean.getClass(), -1);
                     ((DescriptorImpl)this.appDeployMBean.getDescriptor()).resolveReferences();
                     rgAppDeploymentMBean.unSet("Name");
                     rgAppDeploymentMBean.setName(PartitionProcessor.addSuffix((PartitionMBean)resourceGroupMBean.getParent(), this.appDeployMBean.getName()));
                     DeploymentEventManager.sendDeploymentEvent(DeploymentEvent.create(this, DeploymentEvent.APP_DELETED, rgAppDeploymentMBean, false, modules, targets));
                     rgAppDeploymentMBean = null;
                  } catch (ManagementException | InvalidAttributeValueException var6) {
                     throw new AssertionError(var6);
                  }
               } else {
                  this.debugSay("DeploymentListener.applicationDeleted Application is deployed to ResourceGroup at partition level, inherited from RGT ");
               }
            }
         }
      } else {
         DeploymentEventManager.sendDeploymentEvent(DeploymentEvent.create(this, DeploymentEvent.APP_DELETED, this.appDeployMBean, false, modules, targets));
      }

   }

   private void retirePreviousActiveVersion() {
      if (this.appDeployMBean != null && this.appDeployMBean.getVersionIdentifier() != null && (this.task == 1 || this.task == 11 || this.task == 7 || this.task == 9) && !this.hasFiles() && this.failedStatusCount == 0) {
         AppDeploymentMBean prevActiveAppMBean = getPrevActiveAppDeployment(this.appDeployMBean);

         try {
            appRTStateMgr.setActiveVersion(this.applicationId, true);
         } catch (ManagementException var4) {
            DeployerRuntimeLogger.logErrorPersistingActiveAppState(this.getDescription(), ManagementException.unWrapExceptions(var4));
         }

         if (!(this.appDeployMBean instanceof LibraryMBean)) {
            if (prevActiveAppMBean != null && !this.appDeployMBean.getVersionIdentifier().equals(prevActiveAppMBean.getVersionIdentifier())) {
               try {
                  RetirementManager.retire(prevActiveAppMBean, this.requestData);
               } catch (ManagementException var3) {
                  DeployerRuntimeLogger.logRetirementFailed(ApplicationVersionUtils.getDisplayName(prevActiveAppMBean), var3);
               }
            }

         }
      }
   }

   private static AppDeploymentMBean getPrevActiveAppDeployment(AppDeploymentMBean currentApp) {
      if (currentApp == null) {
         return null;
      } else {
         String appName = currentApp.getApplicationName();
         boolean adminMode = ApplicationVersionUtils.isAdminMode(currentApp);
         String currentVersionId = currentApp.getVersionIdentifier();
         if (currentVersionId == null) {
            return currentApp;
         } else {
            DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
            AppDeploymentMBean[] deps = AppDeploymentHelper.getAppsAndLibs(domain);
            if (deps == null) {
               return null;
            } else {
               for(int i = 0; i < deps.length; ++i) {
                  AppDeploymentMBean dep = deps[i];
                  if (dep.getApplicationName().equals(appName) && !currentVersionId.equals(dep.getVersionIdentifier()) && ApplicationVersionUtils.isAdminMode(dep) == adminMode && (dep.getVersionIdentifier() == null || appRTStateMgr.isActiveVersion(dep))) {
                     return dep;
                  }
               }

               return null;
            }
         }
      }
   }

   public void addUnreachableTarget(String server) {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating addUnreachableTarget() to delegate : " + theDelegate);
         theDelegate.addUnreachableTarget(server);
      } else {
         this.debugSay(" Adding unreachable target : " + server);
         this.unreachableTargets.add(server);
      }
   }

   public void waitForTaskCompletion(long timeout) {
      if (this.hasDelegate()) {
         DeploymentTaskRuntime theDelegate = this.getDelegate();
         this.debugSay("Delegating waitForTaskCompletion() to delegate : " + theDelegate);
         theDelegate.waitForTaskCompletion(timeout);
      } else {
         while(this.isRunning() && (timeout <= 0L || timeout > System.currentTimeMillis())) {
            try {
               Thread.sleep(100L);
            } catch (InterruptedException var4) {
            }
         }

      }
   }

   public boolean isRetired() {
      return this.retired;
   }

   public void setRetired() {
      this.debugSay("<" + new Date() + "> <" + Thread.currentThread().getName() + "> Setting the task with id '" + this.getId() + "' as retired");
      this.retired = true;
      this.lastException = null;
      this.failedTargets.clear();
      this.targetStatusMap.clear();
      this.serverToTargetStatusMap.clear();
      this.versionTargetStatusMap.clear();
      this.targetsStatus = null;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      this.debugSay(" +++ undo changes invoked on EditableDomain");
      DescriptorBean sourceBean = event.getSourceBean();
      this.debugSay(" +++ SourceBean on the event : " + sourceBean);
      DescriptorBean proposedBean = event.getProposedBean();
      this.debugSay(" +++ proposedBean on the event : " + proposedBean);
      this.debugSay(" +++ editableDomainMBean : " + this.editableDomainMBean);
      if (sourceBean == this.editableDomainMBean || this.editableDomainMBean == null) {
         if (this.adminOperation != null) {
            this.adminOperation.undoChangesTriggeredByUser();
         }

         this.remove();
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   public void addDelegator(DeploymentTaskRuntime delegator) throws DeploymentException {
      if (this == delegator) {
         throw new DeploymentException("Task '" + this.getId() + "' cannot be set as deletate to itself");
      } else {
         synchronized(this) {
            this.delegators.add(delegator);
            delegator.setDelegate(this);
         }
      }
   }

   private void setDelegate(DeploymentTaskRuntime theDelegate) throws DeploymentException {
      if (this.hasDelegate()) {
         throw new DeploymentException("task '" + this.getId() + "' has already been delegated to '" + this.getDelegate().getId() + "' and hence cannot be delegated again to '" + this.delegate.getId() + "'");
      } else {
         this.delegate = theDelegate;
      }
   }

   private boolean hasDelegate() {
      return this.delegate != null;
   }

   private DeploymentTaskRuntime getDelegate() {
      return this.delegate;
   }

   public static String getTaskDescription(int task) {
      try {
         return DeploymentTaskRuntime.DeploymentAction.getDeploymentAction(task).getDescription();
      } catch (Throwable var2) {
         return Integer.toString(task);
      }
   }

   public boolean isNewSource() {
      return this.sourcePath != null;
   }

   public void addHandler(DeploymentCompatibilityEventHandler h) {
      synchronized(this.handlers) {
         this.handlers.add(h);
      }
   }

   private final String getCancelStateString() {
      switch (this.cancelState) {
         case 0:
            return "CANCEL_STATE_NONE";
         case 1:
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            return "cancel state indeterminate";
         case 2:
            return "CANCEL_STATE_STARTED";
         case 4:
            return "CANCEL_STATE_FAILED";
         case 8:
            return "CANCEL_STATE_COMPLETED";
      }
   }

   public AbstractOperation getAdminOperation() {
      return this.adminOperation;
   }

   public void setAdminOperation(AbstractOperation operation) {
      this.adminOperation = operation;
   }

   private void updateIntendedState() throws ManagementException {
      if (isDebugEnabled()) {
         this.debugSay("Updating intended state from task with task type : " + this.getTask());
      }

      AppDeploymentMBean appDeploymentMBean = this.getAppDeploymentMBean();
      WebLogicMBean parentMBean = appDeploymentMBean == null ? null : appDeploymentMBean.getParent();
      if (appDeploymentMBean == null || !(parentMBean instanceof ResourceGroupTemplateMBean) || parentMBean instanceof ResourceGroupMBean) {
         String newState = null;
         switch (this.getTask()) {
            case 1:
            case 7:
            case 9:
            case 11:
               OperationHelper.setAdminMode(this.getAppDeploymentMBean(), this.getDeploymentData(), "STATE_ACTIVE");
               break;
            case 2:
            case 6:
               newState = "STATE_PREPARED";
               String currentState = appRTStateMgr.getIntendedState(this.getAppDeploymentMBean().getName());
               if (currentState != null) {
                  newState = currentState;
               }

               OperationHelper.setState(this.getAppDeploymentMBean(), this.getDeploymentData(), newState);
            case 3:
            case 4:
            case 5:
            case 10:
            case 12:
            default:
               break;
            case 8:
               newState = "STATE_PREPARED";
               if (this.requestData.hasModuleTargets() && this.appDeployMBean != null) {
                  newState = appRTStateMgr.getIntendedState(this.appDeployMBean.getName());
               }

               OperationHelper.setAdminMode(this.getAppDeploymentMBean(), this.getDeploymentData(), newState);
               break;
            case 13:
               OperationHelper.setState(this.getAppDeploymentMBean(), this.getDeploymentData(), "STATE_NEW");
         }

      }
   }

   public void remove() {
      DeployerRuntimeMBean deployerRuntime = (DeployerRuntimeMBean)this.getParent();
      if (deployerRuntime != null) {
         this.debugSay(" +++ removing Task due to undo changes triggered by user : " + this.taskId);
         deployerRuntime.removeTask(this.taskId);
      }

      this.setCancelState(8);
      if (!this.isComplete()) {
         this.setState(3);
         RuntimeException e = new RuntimeException(DeployerRuntimeExtendedLogger.logDeploymentTaskCanceledLoggable(this.taskId, this.applicationDisplayName).getMessage(this.getClientLocale()));
         HashSet pendingServersClone = (HashSet)this.pendingServers.clone();
         boolean onlyUpdateRunningTasks = false;
         Iterator var5 = pendingServersClone.iterator();

         while(var5.hasNext()) {
            String targetName = (String)var5.next();
            this.updateTargetStatus(targetName, 2, e, onlyUpdateRunningTasks);
         }
      }

   }

   public void setDepMgr(DeploymentManager deploymentManager) {
      this.depMgr = deploymentManager;
   }

   public abstract static class DeploymentAction {
      static final DeploymentAction DEPLOY_TASK_ACTIVATE = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageActivate();
         }
      };
      static final DeploymentAction DEPLOY_TASK_PREPARE = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messagePrepare();
         }
      };
      static final DeploymentAction DEPLOY_TASK_DEACTIVATE = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageDeactivate();
         }
      };
      static final DeploymentAction DEPLOY_TASK_REMOVE = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageRemove();
         }
      };
      static final DeploymentAction DEPLOY_TASK_UNPREPARE = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageUnprepare();
         }
      };
      static final DeploymentAction DEPLOY_TASK_DISTRIBUTE = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageDistribute();
         }
      };
      static final DeploymentAction DEPLOY_TASK_EXTEND_LOADER = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageExtendLoader();
         }
      };
      static final DeploymentAction DEPLOY_TASK_START = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageStart();
         }
      };
      static final DeploymentAction DEPLOY_TASK_STOP = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageStop();
         }
      };
      static final DeploymentAction DEPLOY_TASK_REDEPLOY = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageRedeploy();
         }
      };
      static final DeploymentAction DEPLOY_TASK_UPDATE = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageUpdate();
         }
      };
      static final DeploymentAction DEPLOY_TASK_DEPLOY = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageDeploy();
         }
      };
      static final DeploymentAction DEPLOY_TASK_RETIRE = new DeploymentAction() {
         String getDescription() {
            return DeploymentTaskRuntime.fmt.messageRetire();
         }
      };

      abstract String getDescription();

      static DeploymentAction getDeploymentAction(int task) {
         switch (task) {
            case 1:
               return DEPLOY_TASK_ACTIVATE;
            case 2:
               return DEPLOY_TASK_PREPARE;
            case 3:
               return DEPLOY_TASK_DEACTIVATE;
            case 4:
               return DEPLOY_TASK_REMOVE;
            case 5:
               return DEPLOY_TASK_UNPREPARE;
            case 6:
               return DEPLOY_TASK_DISTRIBUTE;
            case 7:
               return DEPLOY_TASK_START;
            case 8:
               return DEPLOY_TASK_STOP;
            case 9:
               return DEPLOY_TASK_REDEPLOY;
            case 10:
               return DEPLOY_TASK_UPDATE;
            case 11:
               return DEPLOY_TASK_DEPLOY;
            case 12:
            default:
               throw new AssertionError("Unexpected deployment action identifier: " + task);
            case 13:
               return DEPLOY_TASK_RETIRE;
            case 14:
               return DEPLOY_TASK_DISTRIBUTE;
         }
      }
   }

   static final class DeployFilter implements NotificationFilter, Serializable {
      public boolean isNotificationEnabled(Notification n) {
         return n instanceof DeploymentNotification;
      }
   }

   private final class DeploymentTaskListener implements RemoteNotificationListener {
      public void handleNotification(Notification notification, Object handback) {
         if (notification instanceof DeploymentNotification) {
            DeploymentNotification dn = (DeploymentNotification)notification;
            DeploymentTaskRuntime.this.debugSay("DTRM rcvd notification: " + dn.getMessage());
            String task = dn.getTask();
            boolean thisTask = false;
            if (task != null && handback != null && handback instanceof String) {
               thisTask = task.equals(handback);
               if (thisTask) {
                  DeploymentTaskRuntime.this.debugSay("Received matching notification for task: " + task);
               } else {
                  DeploymentTaskRuntime.this.debugSay("Skipping non-matching notification for task: " + task);
               }
            } else {
               DeploymentTaskRuntime.this.debugSay("Received non-matching notification for task: " + task);
               if (handback instanceof String) {
                  DeploymentTaskRuntime.this.debugSay("Skipping this notification for task: " + handback);
               }
            }

            if (thisTask) {
               Loggable l;
               if (dn.isAppNotification()) {
                  l = DeployerRuntimeLogger.logAppNotificationLoggable(dn.getAppName(), dn.getServerName(), dn.getPhase());
                  l.log();
                  DeploymentTaskRuntime.this.addMessage(l.getMessage(DeploymentTaskRuntime.this.getClientLocale()));
               } else if (dn.isModuleNotification()) {
                  l = null;
                  if (dn.isBeginTransition()) {
                     l = DeployerRuntimeLogger.logStartTransitionLoggable(dn.getAppName(), dn.getModuleName(), dn.getCurrentState(), dn.getTargetState(), dn.getServerName());
                  } else if (dn.isEndTransition()) {
                     l = DeployerRuntimeLogger.logSuccessfulTransitionLoggable(dn.getAppName(), dn.getModuleName(), dn.getCurrentState(), dn.getTargetState(), dn.getServerName());
                  } else if (dn.isFailedTransition()) {
                     l = DeployerRuntimeLogger.logFailedTransitionLoggable(dn.getAppName(), dn.getModuleName(), dn.getCurrentState(), dn.getTargetState(), dn.getServerName());
                  }

                  if (l != null) {
                     DeploymentTaskRuntime.this.addMessage(l.getMessage(DeploymentTaskRuntime.this.getClientLocale()));
                  }
               }
            }
         }

      }
   }
}
