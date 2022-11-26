package weblogic.management.provider.internal;

import com.bea.xml.XmlValidationError;
import com.google.common.collect.Maps;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.xml.stream.XMLStreamException;
import weblogic.version;
import weblogic.deploy.service.ChangeDescriptor;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.DeploymentException;
import weblogic.deploy.service.DeploymentProvider;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.DeploymentServiceCallbackHandlerV2;
import weblogic.deploy.service.FailureDescription;
import weblogic.deploy.service.InvalidCreateChangeDescriptorException;
import weblogic.deploy.service.RequiresRestartFailureDescription;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.internal.DeploymentService;
import weblogic.deploy.service.internal.adminserver.AdminRequestImpl;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorCache;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.descriptor.conflict.ConflictDescriptorDiff;
import weblogic.descriptor.conflict.DiffConflictException;
import weblogic.descriptor.conflict.NonResolvableDiffConflictException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.descriptor.internal.DiffConflicts;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.ManagementRuntimeException;
import weblogic.management.PartitionDir;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ConfigurationExtensionMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionFileSystemMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.eventbus.InternalEventBusLogger;
import weblogic.management.eventbus.apis.InternalEvent;
import weblogic.management.eventbus.apis.InternalEventBus;
import weblogic.management.eventbus.apis.InternalEventBusFactory;
import weblogic.management.eventbus.apis.InternalEventImpl;
import weblogic.management.eventbus.apis.InternalEvent.EventType;
import weblogic.management.filelock.FileLockHandle;
import weblogic.management.filelock.ManagementFileLockService;
import weblogic.management.internal.EditDirectoryManager;
import weblogic.management.mbeanservers.edit.AutoResolveResult;
import weblogic.management.mbeanservers.edit.NotEditorException;
import weblogic.management.provider.ActivateTask;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditAccessCallbackHandler;
import weblogic.management.provider.EditChangesValidationException;
import weblogic.management.provider.EditFailedException;
import weblogic.management.provider.EditNotEditorException;
import weblogic.management.provider.EditSaveChangesFailedException;
import weblogic.management.provider.EditWaitTimedOutException;
import weblogic.management.provider.MachineStatus;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.provider.ResolveTask;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.upgrade.ConfigFileHelper;
import weblogic.management.utils.AdminServerDeploymentManagerService;
import weblogic.management.utils.AdminServerDeploymentManagerServiceGenerator;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.FileUtils;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.collections.ConcurrentHashSet;

public class EditAccessImpl implements EditAccess {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static final String SCHEMA_VALIDATION_ENABLED_PROP = "weblogic.configuration.schemaValidationEnabled";
   private static final boolean schemaValidationEnabled = getBooleanProperty("weblogic.configuration.schemaValidationEnabled", true);
   private static final ActivateQueue activateQueue = new ActivateQueue();
   private static int CANCEL = 1;
   private static int DEPLOY = 2;
   private static int COMMIT = 3;
   private final EditLockManager lockMgr;
   private final EditDirectoryManager directoryMgr;
   private Descriptor editTree;
   private DomainMBean editDomainMBean;
   private Descriptor currentTree;
   private DomainMBean currentDomainMBean;
   private static final AuthenticatedSubject kernelIdentity = obtainKernelIdentity();
   private static final Map activationTasksByRequest = Collections.synchronizedMap(new HashMap());
   private static final WeakHashMap oldActivationTasks = new WeakHashMap();
   private boolean preparing;
   private long preparingTimeout;
   private long preparingId;
   private boolean pendingChange;
   private final WeakHashMap temporaryTrees;
   private final List callbackHandlers;
   private final Set lifecycleListeners;
   private final Random random;
   private AutoResolveResult startEditResolveResult;
   private volatile boolean destroyed;
   private final ManagementFileLockService mfls;
   private static final DeploymentServiceCallbackHandlerV2 callbackHandler = new DeploymentServiceCallbackHandlerV2() {
      public String getHandlerIdentity() {
         return "Configuration";
      }

      public Deployment[] getDeployments(Version fromVersion, Version toVersion, String serverName, String partitionName) {
         if (EditAccessImpl.debugLogger.isDebugEnabled()) {
            EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Get deployments for server " + serverName + " from version " + fromVersion + " to version " + toVersion);
         }

         if (fromVersion != null && fromVersion.equals(toVersion)) {
            if (EditAccessImpl.debugLogger.isDebugEnabled()) {
               EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Get deployments - from and to version are equal.");
            }

            Deployment[] result = new Deployment[0];
            return result;
         } else {
            try {
               ConfigurationDeployment configDeployment = EditAccessImpl.getConfigDeploymentCurrent(fromVersion, toVersion, serverName, this.getHandlerIdentity());
               Object resultx;
               if (configDeployment == null) {
                  resultx = new Deployment[0];
               } else {
                  resultx = new ConfigurationDeployment[]{configDeployment};
               }

               return (Deployment[])resultx;
            } catch (InvalidCreateChangeDescriptorException var7) {
               if (EditAccessImpl.debugLogger.isDebugEnabled()) {
                  EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Exception getting current config deployment ", var7);
               }

               throw new ManagementRuntimeException(var7);
            }
         }
      }

      public synchronized void deploySucceeded(long identifier, FailureDescription[] deferredDeployments) {
         try {
            List requiresRestartFailures = new ArrayList();
            List deferredFailures = new ArrayList();
            if (deferredDeployments != null) {
               FailureDescription[] var6 = deferredDeployments;
               int var7 = deferredDeployments.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  FailureDescription eachFailure = var6[var8];
                  if (eachFailure instanceof RequiresRestartFailureDescription) {
                     requiresRestartFailures.add(eachFailure);
                  } else {
                     deferredFailures.add(eachFailure);
                  }
               }
            }

            if (EditAccessImpl.debugLogger.isDebugEnabled()) {
               EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access deploy succeeded for identifier " + identifier + " with " + deferredFailures.size() + " deferred deployments and " + requiresRestartFailures.size() + " requires restart deployments");
            }

            ActivateTaskImpl task = EditAccessImpl.lookupTask(identifier);
            if (task == null) {
               return;
            }

            task.getEditAccessImpl().prepareCompleted(identifier);
            FailureDescription[] deferredDescs = new FailureDescription[deferredFailures.size()];
            deferredDescs = (FailureDescription[])deferredFailures.toArray(deferredDescs);
            task.deploySucceeded(deferredDescs);
         } catch (Exception var10) {
            if (EditAccessImpl.debugLogger.isDebugEnabled()) {
               EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access deploy succeeded failed with exception ", var10);
            }
         }

      }

      public synchronized void deployFailed(long identifier, DeploymentException reason) {
         if (EditAccessImpl.debugLogger.isDebugEnabled()) {
            EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access deploy failed for identifier " + identifier + " with reason:" + reason);
         }

         ActivateTaskImpl task = EditAccessImpl.lookupTask(identifier);
         if (task != null) {
            task.getEditAccessImpl().failed(identifier, reason != null ? reason.getFailures() : null, EditAccessImpl.DEPLOY);
         }

      }

      public synchronized void appPrepareFailed(long identifier, DeploymentException reason) {
         if (EditAccessImpl.debugLogger.isDebugEnabled()) {
            EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access app prepare failed for identifier " + identifier + " with reason:" + reason);
         }

         ActivateTaskImpl task = EditAccessImpl.lookupTask(identifier);
         if (task != null) {
            task.reLock();
            AdminRequestImpl request = (AdminRequestImpl)task.getDeploymentRequestTaskRuntimeMBean().getDeploymentRequest();
            ActivateQueue.TaskWithTimeout queueTask = EditAccessImpl.activateQueue.lookupTask(identifier);
            queueTask.reLock(request.getSendCancelAction());
         }

      }

      public synchronized void commitFailed(long identifier, FailureDescription[] failureDescriptions) {
         if (EditAccessImpl.debugLogger.isDebugEnabled()) {
            EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access deploy encountered commit failures for identifier " + identifier + " to " + failureDescriptions.length + " targets");
            FailureDescription[] var4 = failureDescriptions;
            int var5 = failureDescriptions.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               FailureDescription failureDescription = var4[var6];
               EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access commit failure " + failureDescription);
            }
         }

         ActivateTaskImpl task = EditAccessImpl.lookupTask(identifier);
         if (task != null) {
            if (task.getEditAccessImpl().editTree instanceof DescriptorImpl) {
               ((DescriptorImpl)task.getEditAccessImpl().editTree).setModified(true);
            }

            task.getEditAccessImpl().failed(identifier, failureDescriptions, EditAccessImpl.COMMIT);
         }

      }

      public synchronized void commitSucceeded(long identifier) {
         if (EditAccessImpl.debugLogger.isDebugEnabled()) {
            EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access commit suceeded for identifier " + identifier);
         }

         try {
            ActivateTaskImpl task = EditAccessImpl.lookupTask(identifier);
            if (task == null) {
               return;
            }

            task.commitSucceeded();
         } catch (Exception var4) {
            if (EditAccessImpl.debugLogger.isDebugEnabled()) {
               EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access commit succeeded failed with exception ", var4);
            }
         }

      }

      public synchronized void cancelSucceeded(long identifier, FailureDescription[] failureDescriptions) {
         if (EditAccessImpl.debugLogger.isDebugEnabled()) {
            EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access cancel suceeded for identifier " + identifier + " with " + failureDescriptions.length + " cancel delivery attempt failures");
         }

         ActivateTaskImpl task = EditAccessImpl.lookupTask(identifier);
         if (task != null) {
            task.getEditAccessImpl().failed(identifier, failureDescriptions, EditAccessImpl.CANCEL);
         }

      }

      public synchronized void cancelFailed(long identifier, DeploymentException reason) {
         if (EditAccessImpl.debugLogger.isDebugEnabled()) {
            EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access cancel failed for identifier " + identifier + " with reason:" + reason);
         }

         ActivateTaskImpl task = EditAccessImpl.lookupTask(identifier);
         if (task != null) {
            task.getEditAccessImpl().failed(identifier, reason != null ? reason.getFailures() : null, EditAccessImpl.CANCEL);
         }

      }

      public synchronized void receivedStatusFrom(long identifier, Serializable statusObject, String server) {
         if (EditAccessImpl.debugLogger.isDebugEnabled()) {
            EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access received status for identifier " + identifier + " for server " + server + " with status " + statusObject);
         }

         String newState = (String)statusObject;
         if (newState.equals("COMMIT_PENDING")) {
            ActivateTaskImpl task = EditAccessImpl.lookupTask(identifier);
            if (task != null) {
               task.getEditAccessImpl().handleRequestStatusUpdate(identifier, newState, server);
            }
         }

      }

      public synchronized void requestStatusUpdated(long identifier, String action, String server) {
         if (EditAccessImpl.debugLogger.isDebugEnabled()) {
            EditAccessImpl.debugLogger.debug(this.getDebugInfo() + "Edit Access received request status update for identifier " + identifier + " for server " + server + " with status " + action);
         }

         ActivateTaskImpl task = EditAccessImpl.lookupTask(identifier);
         if (task != null) {
            task.getEditAccessImpl().handleRequestStatusUpdate(identifier, action, server);
         }

      }

      private String getDebugInfo() {
         return "[EditAccessImpl:DeploymentServiceCallbackHandlerV2] ";
      }
   };
   private ReLockState relockState;
   private byte[] activeConfigXmlBytes;

   private EditAccessImpl() {
      this("DOMAIN", "default", "");

      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Registering with deployment service");
         }

         ConfigurationVersion currentVersion = new ConfigurationVersion(true);
         DeploymentService.getDeploymentService().register(currentVersion, callbackHandler);
         if (ManagementService.getPropertyService(kernelIdentity).isAdminServer()) {
            RuntimeAccessDeploymentReceiverService.getService().registerHandler();
         }
      } catch (Exception var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Exception occurred registering with deploy service", var2);
         }

         ManagementLogger.logDeploymentRegistrationFailed(var2);
      }

   }

   private EditAccessImpl(String partitionName, String sessionName, String description) {
      this.preparing = false;
      this.pendingChange = false;
      this.temporaryTrees = new WeakHashMap();
      this.callbackHandlers = new ArrayList();
      this.lifecycleListeners = new ConcurrentHashSet();
      this.random = new Random();
      this.destroyed = false;
      this.mfls = (ManagementFileLockService)LocatorUtilities.getService(ManagementFileLockService.class);
      this.relockState = new ReLockState();
      if (partitionName == null || "".equals(partitionName)) {
         partitionName = "DOMAIN";
      }

      if (sessionName == null || "".equals(sessionName)) {
         sessionName = "default";
      }

      this.directoryMgr = EditDirectoryManager.getDirectoryManager(partitionName, sessionName);
      this.lockMgr = new EditLockManager(partitionName, sessionName, description);
      File[] pendingFiles = this.directoryMgr.getAllPendingFilesAsArray();
      if (pendingFiles.length > 0) {
         boolean pendingFilesAfterRecovery = DescriptorHelper.recoverPendingDirectory(pendingFiles, this.directoryMgr);
         if (pendingFilesAfterRecovery) {
            this.setPendingChange(true);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Changes are pending in pending directory");
            }
         }
      }

      if (!this.directoryMgr.hasPendingConfigs()) {
         this.directoryMgr.deleteAllOriginal();
      }

      if (ManagementService.getPropertyService(kernelIdentity).isAdminServer()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Created edit access impl " + this);
         }

      }
   }

   public static void initGlobalEditAccess() {
      ManagementServiceRestricted.setEditAccess(new EditAccessImpl());
   }

   public static void initNamedEditAccess() throws IOException {
      Map map = Maps.newHashMap();
      Map globalSessions = Maps.newHashMap();
      EditAccess editAccess = ManagementServiceRestricted.getEditAccess(kernelIdentity);
      if (editAccess != null) {
         globalSessions.put("default", editAccess);
         map.put("DOMAIN", globalSessions);
      }

      Iterator var3 = EditDirectoryManager.findDomainEditLocks().iterator();

      File lok;
      EditAccess edit;
      while(var3.hasNext()) {
         lok = (File)var3.next();
         edit = createEditAccess(lok);
         if (edit != null) {
            globalSessions.put(edit.getEditSessionName(), edit);
         }
      }

      var3 = EditDirectoryManager.findPartitionEditLocks().iterator();

      while(var3.hasNext()) {
         lok = (File)var3.next();
         edit = createEditAccess(lok);
         addEditAccessToPartitionMap(map, edit);
      }

      DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelIdentity).getDomain();
      ManagementServiceRestricted.setNamedEditAccess(map);
   }

   private static void addEditAccessToPartitionMap(Map partitionMap, EditAccess edit) {
      if (edit != null) {
         Map partitionEditAccess = (Map)partitionMap.get(edit.getPartitionName());
         if (partitionEditAccess == null) {
            partitionEditAccess = Maps.newHashMap();
            partitionMap.put(edit.getPartitionName(), partitionEditAccess);
         }

         ((Map)partitionEditAccess).put(edit.getEditSessionName(), edit);
      }

   }

   private static EditAccess createEditAccess(File editLok) {
      FileInputStream is = null;

      try {
         is = new FileInputStream(editLok);
         Properties props = new Properties();
         props.load(is);
         String[] var3 = new String[]{"name", "partition_name", "description"};
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String prop = var3[var5];
            if (!props.containsKey(prop)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Required property " + prop + " not found in " + editLok.getAbsolutePath() + ". Ignoring session.");
               }

               Object var7 = null;
               return (EditAccess)var7;
            }
         }

         EditAccessImpl var20 = new EditAccessImpl(props.getProperty("partition_name"), props.getProperty("name"), props.getProperty("description"));
         return var20;
      } catch (Exception var18) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception occurred reading edit lock file.", var18);
         }

         ManagementLogger.logReadEditLockFileFailed(var18);
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (IOException var17) {
            }
         }

      }

      return null;
   }

   public void destroy() {
      if ("default".equals(this.getEditSessionName())) {
         throw new IllegalStateException("Default session cannot be destroyed.");
      } else if (activateQueue.containsTaskFor(this)) {
         throw new IllegalStateException("Edit session with a pending activate task cannot be destroyed.");
      } else {
         this.forceDestroy();
      }
   }

   public synchronized void forceDestroy() {
      if ("default".equals(this.getEditSessionName()) && "DOMAIN".equals(this.getPartitionName())) {
         throw new IllegalStateException("Domain default edit session cannot be destroyed.");
      } else {
         ManagementServiceRestricted.notifyEditSessionDestroyed(this);
         Iterator var1 = this.lifecycleListeners.iterator();

         while(var1.hasNext()) {
            WeakReference listenerRef = (WeakReference)var1.next();
            EditAccess.EventListener eventListener = (EditAccess.EventListener)listenerRef.get();
            if (eventListener != null) {
               eventListener.onDestroy(this, this.editDomainMBean);
            }
         }

         InternalEventBus internalEventbus = InternalEventBusFactory.getInstance();
         Map eventPayload = new HashMap();
         eventPayload.put("session_name", this.getEditSessionName());
         eventPayload.put("partition_name", this.getPartitionName());
         InternalEvent editSessionDestroyedEvent = new InternalEventImpl(EventType.MANAGEMENT_EDIT_SESSION_DESTROYED, eventPayload);
         Future result = internalEventbus.send(editSessionDestroyedEvent);

         try {
            result.get();
         } catch (ExecutionException var6) {
            InternalEventBusLogger.logErrorProcessingInternalEvent(editSessionDestroyedEvent.toString(), var6.getCause());
         } catch (InterruptedException var7) {
            InternalEventBusLogger.logErrorProcessingInternalEvent(editSessionDestroyedEvent.toString(), var7);
         }

         this.destroyed = true;
         this.directoryMgr.destroy();
      }
   }

   public DomainMBean getDomainBean() throws EditNotEditorException, EditFailedException {
      this.checkEditLock();
      if (this.editDomainMBean == null) {
         this.ensureBeanTreeLoaded();
      }

      return this.editDomainMBean;
   }

   public DomainMBean getCurrentDomainBean() throws EditFailedException {
      if (this.currentDomainMBean == null) {
         this.ensureBeanTreeLoaded();
      }

      return this.currentDomainMBean;
   }

   void reLock() throws EditWaitTimedOutException, EditFailedException {
      this.acquireLock(this.relockState.waitTimeInMillis, this.relockState.timeOutInMillis, this.relockState.exclusive, this.relockState.lastLockHolder);
   }

   private AuthenticatedSubject acquireLock(int waitTimeInMillis, int timeOutInMillis, boolean exclusive, AuthenticatedSubject subject) throws EditWaitTimedOutException, EditFailedException {
      if (subject == null) {
         subject = SecurityServiceManager.getCurrentSubject(kernelIdentity);
      }

      this.relockState.waitTimeInMillis = waitTimeInMillis;
      this.relockState.timeOutInMillis = timeOutInMillis;
      this.relockState.exclusive = exclusive;
      this.relockState.lastLockHolder = subject;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Starting edit session for " + subject);
      }

      boolean canceledLock = this.lockMgr.getEditLock(subject, waitTimeInMillis, timeOutInMillis, exclusive);
      if (canceledLock && this.editDomainMBean != null) {
         try {
            this.undoUnsavedChanges();
         } catch (EditNotEditorException var7) {
            throw new AssertionError("Should have edit lock");
         }
      }

      return subject;
   }

   public DomainMBean startEdit(int waitTimeInMillis, int timeOutInMillis) throws EditWaitTimedOutException, EditFailedException {
      return this.startEdit(waitTimeInMillis, timeOutInMillis, false);
   }

   public DomainMBean startEdit(int waitTimeInMillis, int timeOutInMillis, boolean exclusive) throws EditWaitTimedOutException, EditFailedException {
      return this.startEdit(waitTimeInMillis, timeOutInMillis, exclusive, true);
   }

   private DomainMBean startEdit(int waitTimeInMillis, int timeOutInMillis, boolean exclusive, boolean genEvent) throws EditWaitTimedOutException, EditFailedException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "startEdit()");
      }

      this.startEditResolveResult = null;
      long now = System.currentTimeMillis();
      AuthenticatedSubject owner = this.acquireLock(waitTimeInMillis, timeOutInMillis, exclusive, (AuthenticatedSubject)null);
      if (debugLogger.isDebugEnabled()) {
         if (owner != null) {
            debugLogger.debug(this.getDebugInfo() + "owner is " + owner.getPrincipals());
         } else {
            debugLogger.debug(this.getDebugInfo() + "owner is null");
         }

         debugLogger.debug(this.getDebugInfo() + "session_name is " + this.getEditSessionName());
         debugLogger.debug(this.getDebugInfo() + "Edit Session Description is " + this.getEditSessionDescription());
         debugLogger.debug(this.getDebugInfo() + "partition_name is " + this.getPartitionName());
         debugLogger.debug(this.getDebugInfo() + "creator name is " + this.getCreator());
         debugLogger.debug(this.getDebugInfo() + "Editor name is " + this.getEditor());
      }

      if (this.editDomainMBean == null) {
         this.ensureBeanTreeLoaded();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Loaded bean tree");
         }

         String v = version.getReleaseBuildVersion();
         this.editDomainMBean.setConfigurationVersion(v);
      }

      if (!this.originalConfigExists()) {
         if (this.lockMgr.isMergeNeeded() && !this.isModified() && !this.isPendingChange()) {
            this.doLoadEditTree();
         }

         this.createOriginalFiles(this.editTree);
      }

      ManagementServiceRestricted.notifyEditSessionStarted(this);
      if (genEvent) {
         InternalEventBus internalEventbus = InternalEventBusFactory.getInstance();
         Map eventPayload = new HashMap();
         eventPayload.put("owner", owner != null ? owner.getPrincipals() : null);
         eventPayload.put("session_name", this.getEditSessionName());
         eventPayload.put("partition_name", this.getPartitionName());
         InternalEvent editSessionStartedEvent = new InternalEventImpl(EventType.MANAGEMENT_EDIT_SESSION_STARTED, eventPayload);
         internalEventbus.send(editSessionStartedEvent);
      }

      if (this.isMergeNeeded()) {
         long resolveWaitTime = (long)waitTimeInMillis;
         if (waitTimeInMillis > 0) {
            resolveWaitTime = now + (long)waitTimeInMillis - System.currentTimeMillis();
            if (resolveWaitTime < 0L) {
               EditWaitTimedOutException ex = new EditWaitTimedOutException("Timeout before resolve.");
               ManagementLogger.logCanNotResolveInStartEdit(ex);
               this.startEditResolveResult = new AutoResolveResult(now, ex);
            }
         }

         try {
            ResolveTask task = this.resolve(true, resolveWaitTime > 0L ? resolveWaitTime : Long.MAX_VALUE);
            if (resolveWaitTime > 0L) {
               task.waitForTaskCompletion(resolveWaitTime);
            } else {
               task.waitForTaskCompletion();
            }

            this.startEditResolveResult = new AutoResolveResult(now, task.getError());
         } catch (Throwable var11) {
            ManagementLogger.logCanNotResolveInStartEdit(var11);
            this.startEditResolveResult = new AutoResolveResult(now, var11);
         }
      }

      return this.editDomainMBean;
   }

   public void stopEdit() throws EditNotEditorException, EditFailedException {
      AuthenticatedSubject currSubject = this.checkEditLock();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Stopping edit session for " + currSubject);
      }

      try {
         this.undoUnsavedChanges();
      } catch (EditNotEditorException var5) {
         throw new AssertionError("Should have edit lock");
      }

      this.lockMgr.releaseEditLock(currSubject);
      InternalEventBus internalEventbus = InternalEventBusFactory.getInstance();
      Map eventPayload = new HashMap();
      eventPayload.put("owner", currSubject != null ? currSubject.getPrincipals() : null);
      eventPayload.put("session_name", this.getEditSessionName());
      eventPayload.put("partition_name", this.getPartitionName());
      InternalEvent editSessionStoppedEvent = new InternalEventImpl(EventType.MANAGEMENT_EDIT_SESSION_STOPPED, eventPayload);
      internalEventbus.send(editSessionStoppedEvent);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Stopped edit session");
      }

   }

   public synchronized void cancelEdit() throws EditFailedException {
      AuthenticatedSubject currSubject = SecurityServiceManager.getCurrentSubject(kernelIdentity);
      if (!this.canOverrideCancelEditLock(currSubject)) {
         throw new EditFailedException(SubjectUtils.getUsername(currSubject) + " does not have privilege to cancel lock of user with Administrator privilege.");
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Canceling edit session for " + currSubject);
         }

         if (this.isPendingChange()) {
            ManagementLogger.logDetectedPendingChangesWhileCancelingEditLock();
         }

         if (this.isPreparing()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Cancel outstanding requests");
            }

            ActivateTaskImpl task = lookupTask(this.preparingId);
            this.resetPreparingInfo();
            this.rollbackCurrent();
            if (task != null) {
               task.setError(new EditFailedException("Request canceled by cancelEdit operation"));
               task.setState(5);
            }
         }

         this.lockMgr.cancelEditLock(currSubject);

         try {
            this.undoUnsavedChanges();
         } catch (EditNotEditorException var5) {
            throw new AssertionError("Should have edit lock");
         } catch (EditFailedException var6) {
            this.lockMgr.releaseEditLock(currSubject);
            throw var6;
         }

         this.lockMgr.releaseEditLock(currSubject);
         InternalEventBus internalEventbus = InternalEventBusFactory.getInstance();
         Map eventPayload = new HashMap();
         eventPayload.put("owner", currSubject != null ? currSubject.getPrincipals() : null);
         eventPayload.put("session_name", this.getEditSessionName());
         eventPayload.put("partition_name", this.getPartitionName());
         InternalEvent editSessionCancelledEvent = new InternalEventImpl(EventType.MANAGEMENT_EDIT_SESSION_CANCELLED, eventPayload);
         internalEventbus.send(editSessionCancelledEvent);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Cancelled edit session");
         }

      }
   }

   public synchronized Iterator getUnsavedChanges() throws EditNotEditorException, EditFailedException {
      AuthenticatedSubject currSubject = this.checkEditLock();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Getting iterator of unsaved changes for " + currSubject);
      }

      if (this.editTree == null) {
         throw new AssertionError("Edit bean tree is null");
      } else {
         ArrayList combinedDiffs = new ArrayList();

         try {
            Iterator it = this.callbackGetChanges();
            if (it != null) {
               while(it.hasNext()) {
                  combinedDiffs.add(it.next());
               }
            }
         } catch (Exception var15) {
            throw new EditFailedException(var15);
         }

         try {
            boolean externalModified = this.areAnyExternalTreesModified(this.editTree);
            if (!this.editTree.isModified() && !externalModified) {
               return combinedDiffs.iterator();
            } else {
               Descriptor pendingTree = this.loadEditTreeFromPending();
               this.addTemporaryTree(pendingTree, "getUnsavedChanges");
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Loaded pending tree " + pendingTree + " compute diff with edit tree " + this.editTree);
               }

               DescriptorDiff diff = pendingTree.computeDiff(this.editTree);
               Iterator it;
               if (diff != null) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug(this.getDebugInfo() + "Returning iterator of unsaved changes");
                  }

                  it = diff.iterator();

                  while(it.hasNext()) {
                     Object update = it.next();
                     combinedDiffs.add(update);
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug(this.getDebugInfo() + "Beanupdate event " + update);
                     }
                  }
               }

               if (!externalModified) {
                  return combinedDiffs.iterator();
               } else {
                  it = DescriptorInfoUtils.getDescriptorInfos(this.editTree);

                  while(it != null && it.hasNext()) {
                     DescriptorInfo descInfo = (DescriptorInfo)it.next();
                     Descriptor desc = descInfo.getDescriptor();
                     if (desc.isModified()) {
                        Descriptor externalPendingTree = this.loadExternalBeanTree(descInfo, true, false);
                        this.addTemporaryTree(externalPendingTree, "getUnsavedChanges." + descInfo.getDescriptorClass());
                        DescriptorDiff externalDiff = externalPendingTree.computeDiff(desc);
                        if (externalDiff != null) {
                           Iterator var11 = externalDiff.iterator();

                           while(var11.hasNext()) {
                              Object anExternalDiff = var11.next();
                              combinedDiffs.add(anExternalDiff);
                           }
                        }
                     }
                  }

                  return combinedDiffs.iterator();
               }
            }
         } catch (EditFailedException var13) {
            throw var13;
         } catch (Exception var14) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception in computing diff: ", var14);
            }

            throw new EditFailedException(var14);
         }
      }
   }

   public synchronized void undoUnsavedChanges() throws EditNotEditorException, EditFailedException {
      AuthenticatedSubject currSubject = this.checkEditLock();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Undoing unsaved changes for " + currSubject);
      }

      if (this.editTree != null) {
         try {
            this.callbackUndoUnsavedChanges();
         } catch (Exception var7) {
            throw new EditFailedException(var7);
         }

         try {
            if (this.editTree.isModified()) {
               Descriptor pendingTree = this.loadEditTreeFromPending();
               this.addTemporaryTree(pendingTree, "undoUnsavedChanges");
               this.editTree.prepareUpdate(pendingTree, false);
               this.editTree.activateUpdate();
            }

            Iterator it = DescriptorInfoUtils.getDescriptorInfos(this.editTree);

            while(it != null && it.hasNext()) {
               DescriptorInfo descInfo = (DescriptorInfo)it.next();
               Descriptor desc = descInfo.getDescriptor();
               if (desc.isModified()) {
                  Descriptor externalPendingTree = this.loadExternalBeanTree(descInfo, true, true);
                  if (externalPendingTree != null) {
                     this.addTemporaryTree(externalPendingTree, "undoUnsavedChanges." + descInfo.getDescriptorClass());
                     desc.prepareUpdate(externalPendingTree, false);
                     desc.activateUpdate();
                  }
               }
            }

            DescriptorInfoUtils.removeAllDeletedDescriptorInfos(this.editTree);
         } catch (IOException var8) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception loading tree ", var8);
            }

            throw new EditFailedException(var8);
         } catch (EditFailedException var9) {
            throw var9;
         } catch (ManagementException var10) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception running processors ", var10);
            }

            throw new EditFailedException(var10);
         } catch (DescriptorUpdateRejectedException var11) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception in prepare/activate: ", var11);
            }

            throw new EditFailedException(var11);
         } catch (DescriptorUpdateFailedException var12) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception activating in undo unsaved ", var12);
            }

            throw new EditFailedException(var12);
         }

         ManagementServiceRestricted.notifyEditSessionUndidUnsavedChanges(this);
         this.removePendingUpdateTasks();
      }
   }

   private void removePendingUpdateTasks() {
      AdminServerDeploymentManagerServiceGenerator generator = (AdminServerDeploymentManagerServiceGenerator)LocatorUtilities.getService(AdminServerDeploymentManagerServiceGenerator.class);
      AdminServerDeploymentManagerService dms = generator.createAdminServerDeploymentManager(kernelIdentity, this.getPartitionName(), this.getEditSessionName());
      dms.removePendingUpdateTasks();
   }

   public String getEditSessionName() {
      return this.lockMgr.getEditSessionName();
   }

   public boolean isDefault() {
      return "default".equals(this.lockMgr.getEditSessionName());
   }

   public String getEditSessionDescription() {
      return this.lockMgr.getEditSessionDescription();
   }

   public String getPartitionName() {
      return this.lockMgr.getPartitionName();
   }

   public String getCreator() {
      Object creator = this.lockMgr.getLockCreator();
      if (creator == null) {
         return null;
      } else {
         return creator instanceof AuthenticatedSubject ? SubjectUtils.getUsername((AuthenticatedSubject)creator) : creator.toString();
      }
   }

   public String getEditor() {
      Object owner = this.lockMgr.getLockOwner();
      if (owner == null) {
         return null;
      } else {
         return owner instanceof AuthenticatedSubject ? SubjectUtils.getUsername((AuthenticatedSubject)owner) : owner.toString();
      }
   }

   public boolean isEditor() {
      AuthenticatedSubject currSubject = SecurityServiceManager.getCurrentSubject(kernelIdentity);
      return this.lockMgr.isLockOwner(currSubject);
   }

   public long getEditorStartTime() {
      return this.lockMgr.getLockAcquisitionTime();
   }

   public long getEditorExpirationTime() {
      return this.lockMgr.getLockExpirationTime();
   }

   public boolean isEditorExclusive() {
      return this.lockMgr.isLockExclusive();
   }

   public synchronized void validateChanges() throws EditNotEditorException, EditChangesValidationException {
      AuthenticatedSubject currSubject = this.checkEditLock();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Validating changes for " + currSubject);
      }

      if (this.editTree == null) {
         throw new AssertionError("Edit bean tree is null");
      } else {
         try {
            this.editTree.validate();
            Iterator it = DescriptorInfoUtils.getDescriptorInfos(this.editTree);

            while(it != null && it.hasNext()) {
               DescriptorInfo descInfo = (DescriptorInfo)it.next();
               Descriptor desc = descInfo.getDescriptor();
               if (desc != this.editTree && desc.isModified()) {
                  desc.validate();
               }
            }

         } catch (DescriptorValidateException var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception in validation: ", var5);
            }

            throw new EditChangesValidationException(var5);
         }
      }
   }

   public synchronized void reload() throws EditNotEditorException, EditChangesValidationException {
      AuthenticatedSubject currSubject = this.checkEditLock();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Reloading changes for " + currSubject);
      }

      if (this.editTree != null) {
         try {
            Descriptor pendingTree = this.loadEditTreeFromPending();
            this.addTemporaryTree(pendingTree, "reload");
            this.editTree.prepareUpdate(pendingTree, false);
            this.editTree.activateUpdate();
            Iterator it = DescriptorInfoUtils.getDescriptorInfos(this.editTree);

            while(true) {
               if (it == null || !it.hasNext()) {
                  DescriptorInfoUtils.removeAllDeletedDescriptorInfos(this.editTree);
                  break;
               }

               DescriptorInfo descInfo = (DescriptorInfo)it.next();
               Descriptor desc = descInfo.getDescriptor();
               Descriptor externalPendingTree = this.loadExternalBeanTree(descInfo, true, true);
               if (externalPendingTree != null) {
                  this.addTemporaryTree(externalPendingTree, "reload." + descInfo.getDescriptorClass());
                  desc.prepareUpdate(externalPendingTree, false);
                  desc.activateUpdate();
               }
            }
         } catch (IOException var7) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception loading tree ", var7);
            }

            throw new EditChangesValidationException(var7);
         } catch (EditFailedException var8) {
            throw new EditChangesValidationException(var8);
         } catch (ManagementException var9) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception running processors ", var9);
            }

            throw new EditChangesValidationException(var9);
         } catch (DescriptorUpdateRejectedException var10) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception in prepare/activate: ", var10);
            }

            throw new EditChangesValidationException(var10);
         } catch (DescriptorUpdateFailedException var11) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception activating in undo unsaved ", var11);
            }

            throw new EditChangesValidationException(var11);
         }

         this.removePendingUpdateTasks();
         ManagementServiceRestricted.notifyEditSessionReloaded(this);
      }
   }

   private synchronized void saveChangesInternal() throws EditSaveChangesFailedException {
      try {
         boolean wroteFiles = DescriptorHelper.savePendingDescriptorTree(this.editTree, this.directoryMgr, (String)null);
         if (wroteFiles) {
            this.setPendingChange(true);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Saved tree, changes are pending in pending directory");
            }
         }

         Iterator it = DescriptorInfoUtils.getDeletedDescriptorInfos(this.editTree);

         while(it != null && it.hasNext()) {
            DescriptorInfo descInfo = (DescriptorInfo)it.next();
            String descFileName = descInfo.getConfigurationExtension().getDescriptorFileName();
            this.directoryMgr.deleteFile(descFileName);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Deleting from pending directory - deleted descriptor file " + descFileName);
            }
         }

         DescriptorInfoUtils.removeAllDeletedDescriptorInfos(this.editTree);
      } catch (IOException var5) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Exception in write: ", var5);
         }

         throw new EditSaveChangesFailedException(var5);
      }
   }

   public synchronized void saveChanges() throws EditNotEditorException, EditSaveChangesFailedException, EditChangesValidationException {
      AuthenticatedSubject currSubject = this.checkEditLock();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Saving changes for " + currSubject);
      }

      String[] addedFiles = this.getAddedFiles();
      String[] removedFiles = this.getRemovedFiles();
      String[] editedFiles = this.getEditedFiles();
      boolean anyFileChanges = addedFiles != null && addedFiles.length > 0 || removedFiles != null && removedFiles.length > 0 || editedFiles != null && editedFiles.length > 0;
      if (!anyFileChanges && this.editTree == null) {
         throw new AssertionError("Edit bean tree is null");
      } else {
         this.validateChanges();
         if (this.editTree != null) {
            this.validatePreparingInfo();
            if (this.isPreparing()) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Attempted to save changes while preparing: ");
               }

               throw new EditSaveChangesFailedException("Can not save changes while prepare changes are still in progress");
            }

            this.saveChangesInternal();
         }

         try {
            this.callbackSaveChanges();
         } catch (Exception var7) {
            throw new EditSaveChangesFailedException(var7);
         }
      }
   }

   public void updateApplication() throws EditNotEditorException {
      try {
         this.checkEditLock();
         this.callbackUpdateApplication();
         this.resetPreparingInfo();
      } catch (Exception var2) {
         throw new EditNotEditorException(var2);
      }
   }

   public ActivateTask activateChanges(long timeout) throws EditNotEditorException, EditFailedException {
      AuthenticatedSubject user = this.checkEditLock();
      String requestId = null;
      long timeoutTime;
      synchronized(this) {
         this.validatePreparingInfo();
         if (this.isPreparing()) {
            throw new EditFailedException("Unable to start new activation while preparing");
         }

         if (timeout == Long.MAX_VALUE) {
            timeoutTime = Long.MAX_VALUE;
         } else {
            timeoutTime = System.currentTimeMillis() + timeout;
         }

         try {
            this.callbackActivateChanges();
         } catch (Exception var19) {
            throw new EditFailedException(var19);
         }
      }

      try {
         DeploymentRequest request;
         try {
            request = DeploymentService.getDeploymentService().createDeploymentRequest();
         } catch (ManagementException var21) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Failed to create deployment request with error: ", var21);
            }

            throw new EditFailedException(var21);
         }

         this.saveChanges();
         DescriptorDiff diff = this.currentTree.prepareUpdateDiff(this.editTree, false);
         Iterator changes = diff.iterator();
         String singlePart = DeploymentService.isCrossPartitionConcurrentAppPrepareEnabled() ? this.singlePartitionUpdate() : null;
         ConfigurationDeployment configDeployment = this.getConfigDeploymentPending(changes);
         if (configDeployment != null) {
            configDeployment.setEditSessionName(this.getEditSessionName());
            configDeployment.setPartitionName(this.getPartitionName());
            configDeployment.setConstrainedToPartitionName(singlePart);
            request.addDeployment(configDeployment);
         }

         ConfigurationContextImpl configCtx = new ConfigurationContextImpl(request);
         configCtx.addContextComponent("beanUpdateDescriptorDiffId", diff);
         Map externalDiffs = this.getExternalDescriptorDiffMap(false);
         configCtx.addContextComponent("externalDescritorDiffId", externalDiffs);
         ArrayList combinedDiffs = new ArrayList();
         this.combineDiffs(combinedDiffs, diff, externalDiffs);
         Set deploymentProviders = DeploymentService.getDeploymentService().getRegisteredDeploymentProviders();
         Iterator var16 = deploymentProviders.iterator();

         while(var16.hasNext()) {
            Object deploymentProvider = var16.next();
            DeploymentProvider provider = (DeploymentProvider)deploymentProvider;
            if (this.providerApplicableToThisEditSession(provider)) {
               provider.addDeploymentsTo(request, configCtx);
            }
         }

         request.setInitiator(user);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Deploying request : " + request);
         }

         boolean haveConfigDeployments = false;
         if (configDeployment != null) {
            haveConfigDeployments = true;
         }

         ActivateTaskImpl activateTask = this.createActivationTask(request.getId(), timeout, timeoutTime, haveConfigDeployments, combinedDiffs, user, this.lockMgr, configDeployment);
         requestId = Long.toString(request.getId());
         CommonAdminConfigurationManager.getInstance().activateChanges(requestId, this.editDomainMBean, this.currentDomainMBean, activateTask, diff);
         request.setTimeoutInterval(timeout);
         if (configDeployment == null && !request.getDeployments().hasNext()) {
            this.doResolve(false, (ResolveActivateTask)null);
            ((AdminRequestImpl)request).reset();
            CommonAdminConfigurationManager.getInstance().cleanup(requestId);
            this.lockMgr.releaseEditLock(user);
            activateTask.releaseEditAccess();
            return activateTask;
         } else {
            ManagementServiceRestricted.notifyEditSessionActivateStarted(this, activateTask);
            activateQueue.enqueue(new ActivateQueue.ActivateTaskImplWithTimeout(activateQueue, activateTask, request, new ActivateTaskStartListener(activateTask), new ActivateTaskCompletionListener(activateTask), timeout));
            return activateTask;
         }
      } catch (Exception var22) {
         CommonAdminConfigurationManager.getInstance().cleanup(requestId);
         this.resetPreparingInfo();

         try {
            this.rollbackCurrent();
         } catch (Exception var20) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Can not rollback as a side effect of different issue. ", var20);
            }
         }

         throw new EditFailedException(var22);
      }
   }

   private boolean providerApplicableToThisEditSession(DeploymentProvider provider) {
      if (provider.getPartitionName() == null && provider.getEditSessionName() == null) {
         return true;
      } else {
         return this.getPartitionName().equals(provider.getPartitionName()) && this.getEditSessionName().equals(provider.getEditSessionName());
      }
   }

   public ActivateTask activateChangesAndWaitForCompletion(long timeout) throws EditNotEditorException, EditFailedException {
      ActivateTaskImpl taskBean = (ActivateTaskImpl)this.activateChanges(timeout);
      if (taskBean != null) {
         taskBean.waitForTaskCompletion();
         if (taskBean.getState() == 5 && taskBean.getError() == null) {
            taskBean.setError(new EditFailedException("Activate failed with unknown exception"));
         }
      }

      return taskBean;
   }

   public MachineStatus[] resyncAll() throws NotEditorException {
      return this.resyncInternal((SystemComponentMBean)null);
   }

   public MachineStatus[] resync(SystemComponentMBean sysComp) throws NotEditorException {
      if (sysComp == null) {
         throw new NullPointerException("null system component");
      } else {
         return this.resyncInternal(sysComp);
      }
   }

   private MachineStatus[] resyncInternal(SystemComponentMBean sysComp) throws NotEditorException {
      if (this.getEditor() != null) {
         throw new NotEditorException("resync is not allowed with existing edit session.");
      } else {
         try {
            this.startEdit(30000, 300000, false, false);
         } catch (EditWaitTimedOutException var9) {
            throw new NotEditorException("resync is not allowed with existing edit session.");
         } catch (EditFailedException var10) {
            throw new RuntimeException("resync is not allowed with exist editing session.");
         }

         AuthenticatedSubject user = SecurityServiceManager.getCurrentSubject(kernelIdentity);

         MachineStatus[] var3;
         try {
            var3 = CommonAdminConfigurationManager.getInstance().resync(this.editDomainMBean, this.currentDomainMBean, sysComp);
         } catch (Exception var11) {
            if (var11 instanceof RuntimeException) {
               throw (RuntimeException)var11;
            }

            throw new RuntimeException(var11);
         } finally {
            this.lockMgr.releaseEditLock(user);
         }

         return var3;
      }
   }

   public synchronized void undoUnactivatedChanges() throws EditNotEditorException, EditFailedException {
      AuthenticatedSubject currSubject = this.checkEditLock();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Undoing unactivated changes for " + currSubject);
      }

      if (this.editTree == null) {
         throw new AssertionError("Edit bean tree is null");
      } else {
         try {
            this.callbackUndoUnactivatedChanges();
         } catch (Exception var7) {
            throw new EditFailedException(var7);
         }

         this.validatePreparingInfo();
         if (this.isPreparing()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Attempted to undo activated changes while perpaaring: ");
            }

            throw new EditFailedException("Can not undo unactivated changes while an activate changes operation is still in progress");
         } else {
            try {
               if (!this.deletePendingDirectory()) {
                  throw new EditFailedException("Can not delete all the files in the pending directory");
               }

               this.setPendingChange(false);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Undo unactivated tree, no changes are pending in pending directory");
                  debugLogger.debug(this.getDebugInfo() + "Computing diff of unactivated changes");
                  DescriptorDiff diff = this.editTree.computeDiff(this.currentTree);
                  if (diff != null) {
                     Iterator var3 = diff.iterator();

                     while(var3.hasNext()) {
                        Object aDiff = var3.next();
                        debugLogger.debug(this.getDebugInfo() + "Beanupdate event " + aDiff);
                     }
                  }
               }

               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Preparing revert of edit tree");
               }

               this.editTree.prepareUpdate(this.currentTree, false);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Committing revert of edit tree");
               }

               this.editTree.activateUpdate();
               Iterator it = DescriptorInfoUtils.getDescriptorInfos(this.editTree);

               while(true) {
                  if (it == null || !it.hasNext()) {
                     DescriptorInfoUtils.removeAllDeletedDescriptorInfos(this.editTree);
                     break;
                  }

                  DescriptorInfo descInfo = (DescriptorInfo)it.next();
                  Descriptor desc = descInfo.getDescriptor();
                  Descriptor externalPendingTree = this.loadExternalBeanTree(descInfo, true, true);
                  if (externalPendingTree != null) {
                     this.addTemporaryTree(externalPendingTree, "undoUnactivatedChanges." + descInfo.getDescriptorClass());
                     desc.prepareUpdate(externalPendingTree, false);
                     desc.activateUpdate();
                  }
               }
            } catch (IOException var9) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Exception loading tree ", var9);
               }

               throw new EditFailedException(var9);
            } catch (DescriptorUpdateRejectedException var10) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Exception in prepare/activate: ", var10);
               }

               throw new EditFailedException(var10);
            } catch (DescriptorUpdateFailedException var11) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Exception activating in undo unactivated ", var11);
               }

               throw new EditFailedException(var11);
            }

            ManagementServiceRestricted.notifyEditSessionUndidUnactivatedChanges(this);
            InternalEventBus internalEventbus = InternalEventBusFactory.getInstance();
            Map eventPayload = new HashMap();
            eventPayload.put("owner", currSubject != null ? currSubject.getPrincipals() : null);
            eventPayload.put("session_name", this.getEditSessionName());
            eventPayload.put("partition_name", this.getPartitionName());
            InternalEvent editSessionUndoEvent = new InternalEventImpl(EventType.MANAGEMENT_EDIT_SESSION_UNDO_CHANGES, eventPayload);
            Future result = internalEventbus.send(editSessionUndoEvent);

            try {
               result.get();
            } catch (Exception var8) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Exception while undoing the Edit Session configuration changes: ", var8);
               }

               throw new EditFailedException(var8);
            }
         }
      }
   }

   public synchronized Iterator getUnactivatedChanges() throws EditNotEditorException, EditFailedException {
      AuthenticatedSubject currSubject = this.checkEditLock();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Getting unactivated changes for " + currSubject);
      }

      ArrayList combinedDiffs = new ArrayList();

      try {
         Iterator it = this.callbackGetUnactivatedChanges();
         if (it != null) {
            while(it.hasNext()) {
               combinedDiffs.add((BeanUpdateEvent)it.next());
            }
         }
      } catch (Exception var6) {
         throw new EditFailedException(var6);
      }

      if (!this.isPendingChange() && !this.isModified()) {
         return combinedDiffs.iterator();
      } else {
         DescriptorDiff diff = this.getUnactivatedChangesDiff();

         try {
            Map externalDiffs = this.getExternalDescriptorDiffMap(true);
            this.combineDiffs(combinedDiffs, diff, externalDiffs);
            return combinedDiffs.iterator();
         } catch (Exception var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception in computing diff: ", var5);
            }

            throw new EditFailedException(var5);
         }
      }
   }

   private void combineDiffs(ArrayList combinedDiffs, DescriptorDiff configDiff, Map externalDiffs) {
      Iterator var4;
      if (configDiff != null) {
         var4 = configDiff.iterator();

         while(var4.hasNext()) {
            BeanUpdateEvent aConfigDiff = (BeanUpdateEvent)var4.next();
            combinedDiffs.add(aConfigDiff);
         }
      }

      if (!externalDiffs.isEmpty()) {
         var4 = externalDiffs.values().iterator();

         while(var4.hasNext()) {
            List beanUpdateEventList = (List)var4.next();
            Iterator var6 = beanUpdateEventList.iterator();

            while(var6.hasNext()) {
               BeanUpdateEvent beanUpdateEvent = (BeanUpdateEvent)var6.next();
               combinedDiffs.add(beanUpdateEvent);
            }
         }
      }

   }

   private Map getExternalDescriptorDiffMap(boolean includeNew) throws EditFailedException, IOException {
      Map descDiffMap = new HashMap();
      Iterator it = DescriptorInfoUtils.getDescriptorInfos(this.editTree);

      label51:
      while(it != null && it.hasNext()) {
         DescriptorInfo descInfo = (DescriptorInfo)it.next();
         Descriptor desc = descInfo.getDescriptor();
         Descriptor externalCurrentTree = this.getExternalCurrentTree(descInfo);
         if (externalCurrentTree == null) {
            externalCurrentTree = this.loadExternalBeanTree(descInfo, false, !includeNew);
         }

         if (externalCurrentTree != null) {
            DescriptorDiff externalDiff = externalCurrentTree.computeDiff(desc);
            if (externalDiff != null) {
               ArrayList diffs = new ArrayList();
               Iterator var9 = externalDiff.iterator();

               while(true) {
                  BeanUpdateEvent beanUpdate;
                  do {
                     if (!var9.hasNext()) {
                        descDiffMap.put(descInfo.getDescriptorBean(), diffs);
                        continue label51;
                     }

                     beanUpdate = (BeanUpdateEvent)var9.next();
                     diffs.add(beanUpdate);
                  } while(!debugLogger.isDebugEnabled());

                  debugLogger.debug(this.getDebugInfo() + "Added external diff for bean: " + beanUpdate.getSource() + " in external desc for: " + descInfo.getDescriptorBean());
                  BeanUpdateEvent.PropertyUpdate[] changes = beanUpdate.getUpdateList();

                  for(int i = 0; i < changes.length; ++i) {
                     debugLogger.debug(this.getDebugInfo() + "diff[" + i + "], updateType: " + changes[i].getUpdateType() + ", propertyName: " + changes[i].getPropertyName() + ", isDynamic: " + changes[i].isDynamic());
                  }
               }
            }
         }
      }

      return descDiffMap;
   }

   public boolean isModified() {
      if (this.editTree == null) {
         return false;
      } else {
         return this.editTree.isModified() || this.areAnyExternalTreesModified(this.editTree) || this.areAnyAppDeploymentConfigurationTreesModified();
      }
   }

   private boolean areAnyAppDeploymentConfigurationTreesModified() {
      synchronized(this.callbackHandlers) {
         Iterator var2 = this.callbackHandlers.iterator();

         EditAccessCallbackHandler handler;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            handler = (EditAccessCallbackHandler)var2.next();
         } while(!handler.isModified());

         return true;
      }
   }

   public boolean isPendingChange() {
      return this.pendingChange;
   }

   public synchronized void setPendingChange(boolean pendingChange) {
      this.pendingChange = pendingChange;
   }

   public boolean isMergeNeeded() {
      if (this.originalConfigExists() && this.lockMgr.isMergeNeeded()) {
         return true;
      } else if (this.lockMgr.isMergeNeeded() && this.isModified()) {
         return true;
      } else {
         return this.lockMgr.isMergeNeeded() && this.isPendingChange();
      }
   }

   public synchronized void markMergeNeeded() {
      if (this.editTree != null && !this.destroyed) {
         this.lockMgr.markMergeNeeded();
      }
   }

   private synchronized DescriptorDiff getUnactivatedChangesDiff() throws EditFailedException {
      if (this.editTree == null) {
         throw new AssertionError("Edit bean tree is null");
      } else {
         try {
            DescriptorDiff diff = this.currentTree.computeDiff(this.editTree);
            if (diff == null) {
               return null;
            } else {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Returning iterator of unactivated changes");
                  Iterator var2 = diff.iterator();

                  while(var2.hasNext()) {
                     Object aDiff = var2.next();
                     debugLogger.debug(this.getDebugInfo() + "Beanupdate event " + aDiff);
                  }
               }

               return diff;
            }
         } catch (Exception var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception in diff: ", var4);
            }

            throw new EditFailedException(var4);
         }
      }
   }

   public EditLockManager getEditLockManager() {
      return this.lockMgr;
   }

   public DomainMBean getDomainBeanWithoutLock() throws EditFailedException {
      if (this.editDomainMBean == null) {
         this.ensureBeanTreeLoaded();
      }

      return this.editDomainMBean;
   }

   public synchronized boolean isDomainBeanTreeLoaded() {
      return this.editDomainMBean != null;
   }

   private void handleRequestStatusUpdate(long identifier, String action, String server) {
      ActivateTaskImpl task = lookupTask(identifier);
      if (task != null) {
         boolean commit = false;
         switch (action) {
            case "PrepareSuccessReceived":
               commit = task.updateServerState(server, 2);
               break;
            case "PrepareFailedReceived":
               commit = task.updateServerState(server, 5);
               break;
            case "CommitSuccessReceived":
               commit = task.updateServerState(server, 4);
               break;
            case "CommitFailedReceived":
               commit = task.updateServerState(server, 7);
               break;
            case "CancelSuccessReceived":
               commit = task.updateServerState(server, 6);
               break;
            case "CancelFailedReceived":
               commit = task.updateServerState(server, 6);
               break;
            case "COMMIT_PENDING":
               commit = task.updateServerState(server, 3);
         }

         if ((task.getState() == 2 || task.getState() == 4 || commit) && this.isPreparing() && identifier == this.preparingId) {
            this.prepareCompleted(identifier);
         }

         if (commit) {
            task.releaseAndSetCommitted();
         }

         if (!task.isRunning()) {
            this.removeTask(task);
         }

      }
   }

   public synchronized void cancelActivate() throws EditFailedException {
      AuthenticatedSubject currSubject = SecurityServiceManager.getCurrentSubject(kernelIdentity);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Canceling activate operation for " + currSubject);
      }

      if (this.isPreparing()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Cancel outstanding requests");
         }

         ActivateTaskImpl task = lookupTask(this.preparingId);
         this.resetPreparingInfo();
         this.rollbackCurrent();
         if (task != null) {
            try {
               task.cancel();
            } catch (Exception var4) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Exception canceling task ", var4);
               }

               throw new EditFailedException(var4);
            }
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Cancelled activate.");
      }

   }

   private void failed(long identifier, FailureDescription[] failures, int failureType) {
      ActivateTaskImpl task = lookupTask(identifier);
      if (this.isPreparing() && identifier == this.preparingId) {
         this.resetPreparingInfo();
         this.rollbackCurrent();
      } else if (task != null && task.getCurrentTreeConcurrentAppPrepareRevertDiff() != null) {
         try {
            PartitionDiff currentTreePartitionRevertDiff = new PartitionDiff(task.getCurrentTreeConcurrentAppPrepareRevertDiff());
            PartitionDiff editTreePartitionRevertDiff = task.getEditTreeConcurrentAppPrepareRevertDiff() != null ? new PartitionDiff(task.getEditTreeConcurrentAppPrepareRevertDiff()) : null;
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Reverting currentTree using partition diff: " + currentTreePartitionRevertDiff);
               debugLogger.debug("Reverting editTree using partition diff: " + editTreePartitionRevertDiff);
            }

            this.currentTree.applyDiff(currentTreePartitionRevertDiff);
            if (editTreePartitionRevertDiff != null) {
               this.editTree.applyDiff(editTreePartitionRevertDiff);
            }
         } catch (Exception var8) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Exception applying revert diff on currentTree and editTree. ", var8);
            }
         }
      }

      boolean taskRemoved = false;
      if (task == null) {
         taskRemoved = true;
         task = lookupOldTask(identifier);
         if (task == null) {
            return;
         }
      }

      for(int i = 0; failures != null && i < failures.length; ++i) {
         task.addFailedServer(failures[i].getServer(), failures[i].getReason());
      }

      if (!task.isWaitingForEndFailureCallback() && failureType != COMMIT && (failureType != DEPLOY || task.getState() != 6)) {
         task.setWaitingForEndFailureCallback(true);
      } else {
         task.setState(5);
         if (!taskRemoved) {
            this.removeTask(task);
         }
      }

   }

   private AuthenticatedSubject checkEditLock() throws EditNotEditorException {
      AuthenticatedSubject currSubject = SecurityServiceManager.getCurrentSubject(kernelIdentity);
      if (!this.lockMgr.isLockOwner(currSubject)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Subject " + currSubject + " does not have the edit lock");
         }

         throw new EditNotEditorException("Not edit lock owner");
      } else {
         return currSubject;
      }
   }

   private static AuthenticatedSubject obtainKernelIdentity() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private boolean canOverrideCancelEditLock(AuthenticatedSubject currSubject) {
      if (this.lockMgr.isLockOwner(currSubject)) {
         return true;
      } else {
         String[] adminRoles = new String[]{"Admin"};
         if (SubjectUtils.isUserInAdminRoles(currSubject, adminRoles)) {
            return true;
         } else {
            AuthenticatedSubject currentLockOwner = (AuthenticatedSubject)this.lockMgr.getLockOwner();
            if (currentLockOwner != null && SubjectUtils.isUserInAdminRoles(currentLockOwner, adminRoles)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(SubjectUtils.getUsername(currSubject) + " user does not have privilege to cancel lock of user " + this.getEditor() + " with Administrator privilege.");
               }

               return false;
            } else {
               return true;
            }
         }
      }
   }

   private synchronized ConfigurationDeployment getConfigDeploymentPending(Iterator changes) throws InvalidCreateChangeDescriptorException {
      Set pendingDeletedFiles = this.directoryMgr.getCandidateFilesForDeletion();
      File[] pendingFiles = this.directoryMgr.getAllPendingFilesAsArray();
      if (pendingFiles.length == 0 && pendingDeletedFiles.isEmpty()) {
         return null;
      } else {
         ConfigurationVersion sourceVersion = new ConfigurationVersion(true);
         ConfigurationDeployment configDeployment = new ConfigurationDeployment(callbackHandler.getHandlerIdentity());
         configDeployment.setProposedVersion(sourceVersion);
         boolean configChanged = this.directoryMgr.configExists();

         for(int i = 0; i < pendingFiles.length; ++i) {
            String sourcePath = pendingFiles[i].getPath();
            if (configChanged && this.directoryMgr.getConfigFile().getPath().equals(sourcePath) && i < pendingFiles.length - 1) {
               File temp = pendingFiles[pendingFiles.length - 1];
               pendingFiles[pendingFiles.length - 1] = pendingFiles[i];
               pendingFiles[i] = temp;
               break;
            }
         }

         File[] var16 = pendingFiles;
         int var18 = pendingFiles.length;

         for(int var20 = 0; var20 < var18; ++var20) {
            File pendingFile = var16[var20];
            String sourcePath = pendingFile.getPath();
            String destPath = this.directoryMgr.convertPendingDirectoryToConfig(sourcePath);
            String operation = "update";
            String identity = "config";
            if (!configChanged || !this.directoryMgr.getConfigFile().getPath().equals(sourcePath)) {
               if (this.isWLSExternalFile(destPath)) {
                  identity = "external";
               } else {
                  identity = "non-wls";
               }

               File f = new File(destPath);
               if (!f.exists()) {
                  operation = "add";
               }
            }

            sourceVersion.addOrUpdateFile(sourcePath, destPath);
            sourcePath = DomainDir.removeRootDirectoryFromPath(sourcePath);
            destPath = DomainDir.removeRootDirectoryFromPath(destPath);
            addChangeDescriptor(sourcePath, destPath, configDeployment, operation, sourceVersion, identity);
         }

         Iterator var17 = pendingDeletedFiles.iterator();

         while(var17.hasNext()) {
            File candidateForDeletion = (File)var17.next();
            String sourcePath = candidateForDeletion.getPath();
            sourceVersion.removeFile(sourcePath);
            sourcePath = DomainDir.removeRootDirectoryFromPath(sourcePath);
            String filePath = DomainDir.getRootDir() + File.separator + sourcePath;
            File file = new File(filePath);
            if (file.exists()) {
               addChangeDescriptor(sourcePath, sourcePath, configDeployment, "delete", sourceVersion, "non-wls");
            }
         }

         this.processBeanUpdateEvents(changes, configDeployment, sourceVersion);
         addTargets(configDeployment);
         return configDeployment;
      }
   }

   private boolean isWLSExternalFile(String destPath) {
      String path = (new File(DomainDir.removeConfigDirectoryFromPath(destPath))).getPath();
      Iterator dis = DescriptorInfoUtils.getDescriptorInfos(this.editTree);

      while(dis != null && dis.hasNext()) {
         DescriptorInfo di = (DescriptorInfo)dis.next();
         if (di != null && di.getConfigurationExtension() != null) {
            ConfigurationExtensionMBean confExt = di.getConfigurationExtension();
            if (confExt != null) {
               String confExtPath = (new File(confExt.getDescriptorFileName())).getPath();
               if (confExtPath.equals(path)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private static ConfigurationDeployment getConfigDeploymentCurrent(Version fromVersion, Version toVersion, String serverName, String handlerIdentity) throws InvalidCreateChangeDescriptorException {
      ConfigurationVersion sourceVersion = new ConfigurationVersion(true, serverName);
      if (fromVersion == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("GetConfigDeploymentCurrent - fromVersion cannot be null.");
         }

         throw new InvalidCreateChangeDescriptorException("fromVersion canno be null.");
      } else if (fromVersion.equals(sourceVersion)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("GetConfigDeploymentCurrent - from and source version are equal.");
         }

         return null;
      } else {
         ConfigurationDeployment configDeployment = new ConfigurationDeployment(handlerIdentity);
         configDeployment.setProposedVersion(sourceVersion);
         Map files = sourceVersion.getVersionComponents();
         Map fromFiles = fromVersion.getVersionComponents();
         String configPath = "config" + File.separator + BootStrap.getDefaultConfigFileName();
         Iterator var9 = files.keySet().iterator();

         while(var9.hasNext()) {
            Object o = var9.next();
            String sourcePath = (String)o;
            String identity = "external";
            if (configPath.equals(sourcePath)) {
               identity = "config";
            }

            boolean changed = true;
            String fromVersionStr = (String)fromFiles.get(sourcePath);
            if (fromVersionStr != null && fromVersionStr.equals(files.get(sourcePath))) {
               changed = false;
            }

            if (changed) {
               sourcePath = DomainDir.removeRootDirectoryFromPath(sourcePath);
               addChangeDescriptor(sourcePath, sourcePath, configDeployment, "update", sourceVersion, identity);
            }
         }

         addTargets(configDeployment);
         return configDeployment;
      }
   }

   private static void addTargets(ConfigurationDeployment configDeployment) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelIdentity).getDomain();
      ServerMBean[] serverList = domain.getServers();

      for(int i = 0; serverList != null && i < serverList.length; ++i) {
         configDeployment.addTarget(serverList[i].getName());
      }

   }

   private static void addChangeDescriptor(String sourcePath, String targetPath, ConfigurationDeployment configDeployment, String changeType, ConfigurationVersion sourceVersion, String identity) throws InvalidCreateChangeDescriptorException {
      String deploySrcPath = (new File(sourcePath)).getPath();
      String deployTargetPath = (new File(targetPath)).getPath();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Create change descriptor with target path " + deployTargetPath);
         debugLogger.debug("Create change descriptor with src path " + deploySrcPath);
      }

      ChangeDescriptor changeDescriptor = DeploymentService.getDeploymentService().createChangeDescriptor(changeType, deployTargetPath, deploySrcPath, (Serializable)null, identity);
      configDeployment.addChangeDescriptor(changeDescriptor);
   }

   private void processBeanUpdateEvents(Iterator changes, ConfigurationDeployment configDeployment, ConfigurationVersion sourceVersion) throws InvalidCreateChangeDescriptorException {
      label64:
      while(true) {
         if (changes != null && changes.hasNext()) {
            BeanUpdateEvent event = (BeanUpdateEvent)changes.next();
            String[] restartServers = ChangeUtils.getRestartRequiredServers(event);
            configDeployment.addServersToBeRestarted(restartServers);
            String[] restartPartitions = ChangeUtils.getRestartRequiredPartitions(event);
            configDeployment.addPartitionsToBeRestarted(restartPartitions);
            Map partitionSystemResources = ChangeUtils.getPartitionSystemResourcesForRestart(event);
            Iterator var8 = partitionSystemResources.entrySet().iterator();

            while(true) {
               Set resources;
               String partitionName;
               do {
                  if (!var8.hasNext()) {
                     Map serverSystemResources = ChangeUtils.getServerSystemResourcesForRestart(event);
                     Iterator var16 = serverSystemResources.entrySet().iterator();

                     while(true) {
                        Set resources;
                        String serverName;
                        do {
                           if (!var16.hasNext()) {
                              BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
                              BeanUpdateEvent.PropertyUpdate[] var19 = updates;
                              int var21 = updates.length;
                              int var23 = 0;

                              while(true) {
                                 if (var23 >= var21) {
                                    continue label64;
                                 }

                                 BeanUpdateEvent.PropertyUpdate update = var19[var23];
                                 if (update.getUpdateType() == 3) {
                                    Object removedBean = update.getRemovedObject();
                                    if (removedBean instanceof ConfigurationExtensionMBean) {
                                       this.handleConfigurationExtensionMBean(removedBean, configDeployment, sourceVersion);
                                    }
                                 }

                                 ++var23;
                              }
                           }

                           Map.Entry entry = (Map.Entry)var16.next();
                           resources = (Set)entry.getValue();
                           serverName = (String)entry.getKey();
                        } while(resources == null);

                        Iterator var24 = resources.iterator();

                        while(var24.hasNext()) {
                           String resource = (String)var24.next();
                           configDeployment.addServerSystemResourcesToBeRestarted(serverName, resource);
                        }
                     }
                  }

                  Map.Entry entry = (Map.Entry)var8.next();
                  resources = (Set)entry.getValue();
                  partitionName = (String)entry.getKey();
               } while(resources == null);

               Iterator var12 = resources.iterator();

               while(var12.hasNext()) {
                  String resource = (String)var12.next();
                  configDeployment.addPartitionSystemResourcesToBeRestarted(partitionName, resource);
               }
            }
         }

         return;
      }
   }

   private void handleConfigurationExtensionMBean(Object removedBean, ConfigurationDeployment configDeployment, ConfigurationVersion sourceVersion) throws InvalidCreateChangeDescriptorException {
      ConfigurationExtensionMBean extBean = (ConfigurationExtensionMBean)removedBean;
      if (extBean.getParent() instanceof ResourceGroupMBean) {
         ResourceGroupMBean rg = (ResourceGroupMBean)extBean.getParent();
         ResourceGroupTemplateMBean rgt = rg.getResourceGroupTemplate();
         if (rgt != null) {
            SystemResourceMBean[] var7 = rgt.getSystemResources();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               SystemResourceMBean srmb = var7[var9];
               if (srmb.getClass().getName().equals(extBean.getClass().getName()) && srmb.getName().equals(extBean.getName())) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("SystemResource " + extBean.getName() + " is defined in ResourceGroup beacause of ResourceGroupTemplate. Do not DELETE related file!");
                  }

                  return;
               }
            }
         }
      }

      String sourcePath = DomainDir.getConfigDir() + File.separator + extBean.getDescriptorFileName();
      sourceVersion.removeFile(sourcePath);
      sourcePath = DomainDir.removeRootDirectoryFromPath(sourcePath);
      String filePath = DomainDir.getRootDir() + File.separator + sourcePath;
      File file = new File(filePath);
      if (file.exists()) {
         addChangeDescriptor(sourcePath, sourcePath, configDeployment, "delete", sourceVersion, "external");
      }
   }

   public String[] getAddedFiles() {
      File[] pendingFiles = this.directoryMgr.getAllPendingFilesAsArray();
      Set addedFiles = new HashSet();
      File[] var3 = pendingFiles;
      int var4 = pendingFiles.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File pendingFile = var3[var5];
         String destPath = this.directoryMgr.convertPendingDirectoryToConfig(pendingFile.getPath());
         File targetFile = new File(destPath);
         if (!targetFile.exists()) {
            String relativePathFromRootInstall = DomainDir.removeRootDirectoryFromPath(destPath);
            addedFiles.add(relativePathFromRootInstall);
         }
      }

      return (String[])addedFiles.toArray(new String[addedFiles.size()]);
   }

   public String[] getRemovedFiles() {
      Set deletedFiles = this.directoryMgr.getCandidateFilesForDeletion();
      String[] deletedFilesRelativePathFromRootInstall = new String[deletedFiles.size()];
      int i = 0;

      File f;
      for(Iterator var4 = deletedFiles.iterator(); var4.hasNext(); deletedFilesRelativePathFromRootInstall[i++] = DomainDir.removeRootDirectoryFromPath(f.getPath())) {
         f = (File)var4.next();
      }

      return deletedFilesRelativePathFromRootInstall;
   }

   public String[] getEditedFiles() {
      File[] pendingFiles = this.directoryMgr.getAllPendingFilesAsArray();
      Set editedFilePaths = new HashSet();
      File[] var3 = pendingFiles;
      int var4 = pendingFiles.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File pendingFile = var3[var5];
         String destPath = this.directoryMgr.convertPendingDirectoryToConfig(pendingFile.getPath());
         File targetFile = new File(destPath);
         if (targetFile.exists()) {
            String relativePathFromRootInstall = DomainDir.removeRootDirectoryFromPath(destPath);
            editedFilePaths.add(relativePathFromRootInstall);
         }
      }

      return (String[])editedFilePaths.toArray(new String[editedFilePaths.size()]);
   }

   private void ensureBeanTreeLoaded() throws EditFailedException {
      synchronized(this) {
         if (this.editDomainMBean == null) {
            File configFile = BootStrap.getConfigDirectoryConfigFile();
            if (!configFile.canWrite()) {
               ManagementLogger.logConfigurationFileIsReadOnly(configFile.getPath());
            }

            this.doLoadEditTree();
         }

         if (this.currentDomainMBean == null) {
            this.doLoadCurrentTree(false);
         }

      }
   }

   private void doLoadCurrentTree(boolean forceLoadFromFile) throws EditFailedException {
      try {
         this.currentTree = this.loadBeanTreeFromActive(BootStrap.getConfigDirectoryConfigFile(), forceLoadFromFile);
         this.currentDomainMBean = (DomainMBean)this.currentTree.getRootBean();
      } catch (IOException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception loading tree ", var3);
         }

         throw new EditFailedException(var3);
      }
   }

   private synchronized void doLoadEditTree() throws EditFailedException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "doLoadEditTree()");
      }

      try {
         if (this.editTree == null) {
            this.editTree = this.loadEditTreeFromPending();
            this.editDomainMBean = (DomainMBean)this.editTree.getRootBean();
         } else {
            Descriptor newEditTree = this.loadEditTreeFromPending();
            this.updateTree(this.editTree, newEditTree);
         }

      } catch (ManagementException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Exception running processors ", var2);
         }

         throw new EditFailedException(var2);
      } catch (IOException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Exception loading tree ", var3);
         }

         throw new EditFailedException(var3);
      } catch (DescriptorUpdateRejectedException | DescriptorUpdateFailedException var4) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Exception updating tree ", var4);
         }

         throw new EditFailedException(var4);
      }
   }

   private void updateTree(Descriptor updatedTree, Descriptor modelTree) throws DescriptorUpdateFailedException, DescriptorUpdateRejectedException, EditNotEditorException {
      updatedTree.prepareUpdate(modelTree, false);
      this.activateTreeUpdate(updatedTree);
      Iterator it = DescriptorInfoUtils.getDescriptorInfos(modelTree);

      while(it != null && it.hasNext()) {
         DescriptorInfo externalModelTreeDescInfo = (DescriptorInfo)it.next();
         Descriptor externalModelTree = externalModelTreeDescInfo.getDescriptor();
         Descriptor externalUpdatedTree = getExternalTree(externalModelTreeDescInfo, updatedTree);
         if (externalUpdatedTree != null) {
            externalUpdatedTree.prepareUpdate(externalModelTree, false);
            this.activateTreeUpdate(externalUpdatedTree);
         }
      }

      DescriptorInfoUtils.removeAllDeletedDescriptorInfos(updatedTree);
   }

   private void activateTreeUpdate(Descriptor updatedTree) throws DescriptorUpdateFailedException {
      boolean editable = updatedTree.isEditable();

      try {
         ((DescriptorImpl)updatedTree).setEditable(false);
         updatedTree.activateUpdate();
      } finally {
         if (editable) {
            ((DescriptorImpl)updatedTree).setEditable(true);
         }

      }

   }

   private void createOriginalFiles(Descriptor sourceTree) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "createOriginalFiles()");
      }

      this.directoryMgr.createOriginalDir();
      if (!this.directoryMgr.getOriginalDirectoryFile().exists()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("directory: " + this.directoryMgr.getOriginalDirectoryFile().getAbsolutePath() + " NOT created;");
         }

      } else {
         try {
            DescriptorHelper.saveConfigDescriptorTree(sourceTree, this.directoryMgr.getOriginalDirectory(), (String)null);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("original configuration saved to " + this.directoryMgr.getOriginalDirectoryFile().getAbsolutePath());
            }
         } catch (IOException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Error when saving original config to " + this.directoryMgr.getOriginalDirectory() + ".", var3);
            }
         }

      }
   }

   private Descriptor loadBeanTreeFromActive(File inputFile, boolean forceReload) throws IOException {
      Descriptor var8;
      try {
         InputStream is = !forceReload && this.activeConfigXmlBytes != null ? new ByteArrayInputStream(this.activeConfigXmlBytes) : new FileInputStream(inputFile);
         Throwable var34 = null;

         try {
            DescriptorManager descMgr = DescriptorManagerHelper.getDescriptorManager(false);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Loading active bean tree from " + (is instanceof FileInputStream ? "" : "cached ") + "stream");
            }

            ArrayList errs = new ArrayList();
            Descriptor result = descMgr.createDescriptor(new ConfigReader((InputStream)is), errs, true);
            checkErrors(inputFile.getAbsolutePath(), errs);
            setProductionModeInfo(result);
            var8 = result;
         } catch (Throwable var28) {
            var34 = var28;
            throw var28;
         } finally {
            if (is != null) {
               if (var34 != null) {
                  try {
                     ((InputStream)is).close();
                  } catch (Throwable var27) {
                     var34.addSuppressed(var27);
                  }
               } else {
                  ((InputStream)is).close();
               }
            }

         }
      } catch (XMLStreamException var30) {
         IOException ioe = new IOException("Error loading " + inputFile + ": " + var30.getMessage());
         ioe.initCause(var30);
         throw ioe;
      } catch (DescriptorValidateException var31) {
         DescriptorCache descCache = DescriptorCache.getInstance();
         descCache.removeCRC(new File(DomainDir.getConfigDir() + System.getProperty("file.separator") + "configCache"));
         throw var31;
      } finally {
         this.activeConfigXmlBytes = null;
      }

      return var8;
   }

   public synchronized void renewEditTreeIfPossible() {
      if (this.editTree != null && !this.isPendingChange() && this.lockMgr.getLockOwner() == null && !this.isModified()) {
         boolean updateOriginal = false;
         if (this.originalConfigExists()) {
            this.directoryMgr.deleteAllOriginal();
            updateOriginal = true;
         }

         try {
            this.doLoadEditTree();
         } catch (EditFailedException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Error when updating unused edit session. ", var3);
            }
         }

         if (updateOriginal && this.editTree != null) {
            this.createOriginalFiles(this.editTree);
         }
      }

   }

   private Descriptor loadEditTreeFromPending() throws IOException, ManagementException {
      FileLockHandle configFileLock = null;

      try {
         configFileLock = this.mfls.getConfigFileLock();
      } catch (IOException var14) {
         warnAcquireConfigLock(var14);
      }

      if (configFileLock == null) {
         ManagementLogger.logCouldNotGetConfigFileLock();
      }

      Descriptor var17;
      try {
         InputStream is = this.directoryMgr.getPendingConfigFileAsStream();
         if (is != null) {
            this.activeConfigXmlBytes = null;
         } else {
            InputStream in = this.directoryMgr.getConfigAsStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            int len;
            while((len = in.read(buffer)) > -1) {
               baos.write(buffer, 0, len);
            }

            baos.flush();
            this.activeConfigXmlBytes = baos.toByteArray();
            is = new ByteArrayInputStream(this.activeConfigXmlBytes);
         }

         Descriptor desc = this.loadBeanTree((InputStream)is, DomainDir.removeRootDirectoryFromPath(this.directoryMgr.getConfigFile() == null ? BootStrap.getDefaultConfigFileName() : this.directoryMgr.getConfigFile().getAbsolutePath()), this.directoryMgr.getPendingDirectory());
         ((DescriptorImpl)desc).setModified(false);
         this.setExternalTreesUnmodified(desc);
         var17 = desc;
      } finally {
         if (configFileLock != null) {
            try {
               configFileLock.close();
            } catch (IOException var13) {
               infoReleaseConfigLock(var13);
            }
         }

      }

      return var17;
   }

   private Descriptor loadOriginalEditTreeFromPending() throws IOException, ManagementException {
      InputStream is = this.directoryMgr.getOriginalConfigAsStream();
      Descriptor desc = this.loadBeanTree(is, DomainDir.removeRootDirectoryFromPath(this.directoryMgr.getOriginalConfig()), this.directoryMgr.getOriginalDirectory());
      ((DescriptorImpl)desc).setModified(false);
      this.setExternalTreesUnmodified(desc);
      return desc;
   }

   private Descriptor loadExternalBeanTree(DescriptorInfo descInfo, boolean usePending, boolean doNotCreate) throws EditFailedException, IOException {
      FileLockHandle configFileLock = this.mfls.getConfigFileLock();
      if (configFileLock == null) {
         ManagementLogger.logCouldNotGetConfigFileLock();
      }

      String fileName = null;
      InputStream is = null;

      String descriptorFileName;
      try {
         try {
            ConfigurationExtensionMBean extBean = descInfo.getConfigurationExtension();
            descriptorFileName = extBean.getDescriptorFileName();
            DescriptorManager descMgr = descInfo.getDescriptorManager();
            File currentFile = new File(DomainDir.getPathRelativeConfigDir(descriptorFileName));
            ArrayList errs;
            Descriptor result;
            Descriptor var13;
            if (usePending && this.directoryMgr.fileExists(descriptorFileName) || currentFile.exists()) {
               if (usePending) {
                  fileName = extBean.getDescriptorFileName();
                  is = this.directoryMgr.getPendingFileAsStream(extBean.getDescriptorFileName());
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug(this.getDebugInfo() + "Loading external bean tree from stream");
                  }
               } else {
                  is = new FileInputStream(currentFile);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug(this.getDebugInfo() + "Loading external bean tree from current file");
                  }
               }

               errs = new ArrayList();
               result = descMgr.createDescriptor(new ExternalConfigReader((InputStream)is), errs, true, this.directoryMgr.getPendingDirectory());
               checkErrors(currentFile.getAbsolutePath(), errs);
               setProductionModeInfo(result);
               var13 = result;
               return var13;
            }

            if (doNotCreate) {
               errs = null;
               return errs;
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Creating external bean tree from class");
            }

            errs = new ArrayList();
            result = descMgr.createDescriptorRoot(descInfo.getDescriptorClass(), "UTF-8");
            checkErrors(currentFile.getAbsolutePath(), errs);
            setProductionModeInfo(result);
            var13 = result;
            return var13;
         } catch (XMLStreamException var18) {
            throw new EditFailedException("Error loading " + fileName, var18);
         } catch (IOException var19) {
            if (!(var19 instanceof FileNotFoundException)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getDebugInfo() + "Exception in load external edit tree: ", var19);
               }

               throw new EditFailedException("Error loading " + fileName, var19);
            }
         }

         descriptorFileName = null;
      } finally {
         if (is != null) {
            ((InputStream)is).close();
         }

         if (configFileLock != null) {
            configFileLock.close();
         }

      }

      return descriptorFileName;
   }

   private Descriptor loadBeanTree(InputStream is, String fileName, String rootDirectoryPath) throws EditFailedException, IOException {
      Descriptor var7;
      try {
         EditableDescriptorManager descMgr = (EditableDescriptorManager)DescriptorManagerHelper.getDescriptorManager(true);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Loading bean tree from stream");
         }

         ArrayList errs = new ArrayList();
         Descriptor result = descMgr.createDescriptor(new ConfigReader(is), errs, false, rootDirectoryPath);
         checkErrors(fileName, errs);
         setProductionModeInfo(result);
         var7 = result;
      } catch (XMLStreamException var12) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Exception in load edit tree: ", var12);
         }

         throw new EditFailedException("Error loading " + fileName, var12);
      } catch (IOException var13) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Exception in load edit tree: ", var13);
         }

         throw new EditFailedException(var13);
      } finally {
         is.close();
      }

      return var7;
   }

   private boolean isPreparing() {
      if (!this.preparing) {
         return false;
      } else if (System.currentTimeMillis() >= this.preparingTimeout) {
         this.resetPreparingInfo();
         return false;
      } else {
         return true;
      }
   }

   private void setPreparing(boolean isPreparing) {
      this.preparing = isPreparing;
   }

   private void resetPreparingInfo() {
      this.setPreparing(false);
      this.setPreparingTimeout(0L);
      this.preparingId = 0L;
   }

   private void validatePreparingInfo() {
      if (this.isPreparing()) {
         if (this.preparingId == 0L) {
            this.resetPreparingInfo();
         } else {
            ActivateTaskImpl task = lookupTask(this.preparingId);
            if (task == null) {
               task = lookupOldTask(this.preparingId);
            }

            if (task == null || !task.isRunning()) {
               this.resetPreparingInfo();
            }

         }
      }
   }

   private void prepareCompleted(long identifier) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Prepare completed " + this.isPreparing() + " id " + identifier + " prepare id " + this.preparingId);
      }

      if (this.isPreparing() && identifier == this.preparingId) {
         Descriptor currentTreeBeforePrepare = null;
         ActivateTaskImpl task = lookupTask(identifier);
         if (task != null && task.getDeploymentRequestTaskRuntimeMBean().getDeploymentRequest().concurrentAppPrepareEnabled()) {
            currentTreeBeforePrepare = (Descriptor)this.currentTree.clone();
         }

         RuntimeAccessDeploymentReceiverService.getService().commitAnyPendingRequests();
         if (!this.deletePendingDirectory()) {
            if (task != null) {
               task.setError(new EditFailedException("Can not delete all the files in the pending directory"));
               task.setState(5);
            }
         } else {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Prepare completed, no changes are pending in pending directory");
            }

            this.setPendingChange(false);
         }

         try {
            this.currentTree.activateUpdate();
            Iterator it = DescriptorInfoUtils.getDescriptorInfos(this.editTree);

            while(it != null && it.hasNext()) {
               DescriptorInfo descInfo = (DescriptorInfo)it.next();
               Descriptor desc = descInfo.getDescriptor();
               Descriptor externalCurrentTree = this.getExternalCurrentTree(descInfo);
               if (externalCurrentTree != null) {
                  externalCurrentTree.prepareUpdate(desc, false);
                  externalCurrentTree.activateUpdate();
               }
            }

            if (task != null && currentTreeBeforePrepare != null) {
               task.setCurrentTreeConcurrentAppPrepareRevertDiff(this.currentTree.computeDiff(currentTreeBeforePrepare));
               task.setEditTreeConcurrentAppPrepareRevertDiff(this.editTree.computeDiff(currentTreeBeforePrepare));
            }
         } catch (DescriptorUpdateFailedException var13) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception in prepare/activate of external current tree: ", var13);
            }
         } catch (DescriptorUpdateRejectedException var14) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Exception in prepare/activate of external current tree: ", var14);
            }
         } finally {
            this.resetPreparingInfo();
         }
      } else if (identifier != this.preparingId && this.preparingId != 0L) {
         ActivateTaskImpl task = lookupTask(identifier);
         if (task == null) {
            task = lookupOldTask(identifier);
         }

         if (task == null || task.isRunning()) {
            ManagementLogger.logInvalidPrepareCallback("" + identifier);
         }
      }

   }

   private static void warnAcquireConfigLock(IOException ioe) {
      ManagementLogger.logExceptionAcquiringConfigLock(ioe);
   }

   private static void infoReleaseConfigLock(IOException ioe) {
      ManagementLogger.logExceptionReleasingConfigLock(ioe);
   }

   private boolean deletePendingDirectory() {
      FileLockHandle configFileLock = null;

      try {
         configFileLock = this.mfls.getConfigFileLock();
      } catch (IOException var14) {
         warnAcquireConfigLock(var14);
      }

      if (configFileLock == null) {
         ManagementLogger.logCouldNotGetConfigFileLock();
      }

      try {
         boolean deleted = false;

         for(int i = 0; i < 5 && !deleted; ++i) {
            deleted = this.directoryMgr.deleteAllPending();
            if (!deleted) {
               try {
                  Thread.sleep(3000L);
               } catch (Exception var13) {
               }
            }
         }

         boolean var16 = deleted;
         return var16;
      } finally {
         if (configFileLock != null) {
            try {
               configFileLock.close();
            } catch (IOException var12) {
               infoReleaseConfigLock(var12);
            }
         }

      }
   }

   private void setPreparingInfo(long timeoutTime, long identifier) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Set preparing id = " + identifier + " timeouttime = " + timeoutTime);
      }

      this.setPreparing(true);
      this.setPreparingTimeout(timeoutTime);
      this.preparingId = identifier;
   }

   private void setPreparingTimeout(long timeoutTime) {
      this.preparingTimeout = timeoutTime;
   }

   private synchronized ActivateTaskImpl createActivationTask(long requestId, long timeout, long timeoutTime, boolean haveConfigDeployments, ArrayList changes, AuthenticatedSubject editUser, EditLockManager lockMgr, ConfigurationDeployment configDeployment) throws EditFailedException {
      if (!haveConfigDeployments) {
         try {
            return new ActivateTaskImpl("Activate task with id: " + requestId, lockMgr, haveConfigDeployments, changes, editUser, requestId, timeout, (String[])null, this);
         } catch (ManagementException var13) {
            throw new EditFailedException(var13);
         }
      } else {
         this.validatePreparingInfo();
         if (this.isPreparing()) {
            throw new EditFailedException("Unable to start new Activation while preparing.");
         } else {
            try {
               this.setPreparingInfo(timeoutTime, requestId);
               ActivateTaskImpl task = new ActivateTaskImpl("Activate task with id: " + requestId, lockMgr, haveConfigDeployments, changes, editUser, requestId, timeout, configDeployment.getTargets(), this);
               activationTasksByRequest.put(requestId, task);
               return task;
            } catch (ManagementException var14) {
               throw new EditFailedException(var14);
            }
         }
      }
   }

   private static ActivateTaskImpl lookupTask(long taskId) {
      return (ActivateTaskImpl)activationTasksByRequest.get(taskId);
   }

   private static ActivateTaskImpl lookupOldTask(long taskId) {
      try {
         synchronized(oldActivationTasks) {
            Iterator var3 = oldActivationTasks.keySet().iterator();

            while(var3.hasNext()) {
               ActivateTaskImpl task = (ActivateTaskImpl)var3.next();
               if (task.getTaskId() == taskId) {
                  return task;
               }
            }
         }
      } catch (Exception var7) {
      }

      return null;
   }

   void removeTask(ActivateTaskImpl task) {
      activationTasksByRequest.remove(task.getTaskId());
      synchronized(oldActivationTasks) {
         try {
            oldActivationTasks.put(task, (Object)null);
         } catch (Exception var5) {
         }

      }
   }

   private boolean areAnyExternalTreesModified(Descriptor descTree) {
      boolean externalModified = false;
      Iterator it = DescriptorInfoUtils.getDescriptorInfos(descTree);

      while(it != null && it.hasNext() && !externalModified) {
         DescriptorInfo descInfo = (DescriptorInfo)it.next();
         Descriptor desc = descInfo.getDescriptor();
         if (desc.isModified()) {
            externalModified = true;
         }
      }

      return externalModified;
   }

   private void setExternalTreesUnmodified(Descriptor descTree) {
      Iterator it = DescriptorInfoUtils.getDescriptorInfos(descTree);

      while(it != null && it.hasNext()) {
         DescriptorInfo descInfo = (DescriptorInfo)it.next();
         Descriptor desc = descInfo.getDescriptor();
         if (desc.isModified()) {
            ((DescriptorImpl)desc).setModified(false);
         }
      }

   }

   private void rollbackCurrent() {
      if (this.currentTree != null) {
         this.currentTree.rollbackUpdate();
      }

   }

   private Descriptor getExternalCurrentTree(DescriptorInfo descInfo) {
      return getExternalTree(descInfo, this.currentTree);
   }

   static Descriptor getExternalTree(DescriptorInfo descInfo, Descriptor currentTree) {
      Iterator it = DescriptorInfoUtils.getDescriptorInfos(currentTree);

      while(it != null && it.hasNext()) {
         DescriptorInfo currDescInfo = (DescriptorInfo)it.next();
         ConfigurationExtensionMBean currExt = currDescInfo.getConfigurationExtension();
         ConfigurationExtensionMBean editExt = descInfo.getConfigurationExtension();
         if (currExt != null && editExt != null && currExt.getName().equals(editExt.getName()) && currExt.getDescriptorFileName().equals(editExt.getDescriptorFileName())) {
            return currDescInfo.getDescriptor();
         }
      }

      return null;
   }

   public BeanInfo getBeanInfo(DescriptorBean bean) {
      BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();
      return beanInfoAccess.getBeanInfoForDescriptorBean(bean);
   }

   public PropertyDescriptor getPropertyDescriptor(BeanInfo beanInfo, String propertyName) {
      BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();
      return beanInfoAccess.getPropertyDescriptor(beanInfo, propertyName);
   }

   public boolean getRestartValue(PropertyDescriptor propertyDescriptor) {
      return ChangeUtils.getRestartValue(propertyDescriptor);
   }

   public static void checkErrors(String fileName, ArrayList errs) throws IOException {
      if (errs.size() > 0) {
         int noErrs = errs.size();
         Iterator var3 = errs.iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            if (o instanceof XmlValidationError) {
               XmlValidationError ve = (XmlValidationError)o;
               if (ConfigFileHelper.isAcceptableXmlValidationError(ve)) {
                  --noErrs;
               } else {
                  ManagementLogger.logConfigurationValidationProblem(fileName, ve.getMessage());
               }
            } else {
               ManagementLogger.logConfigurationValidationProblem(fileName, o.toString());
            }
         }

         if (schemaValidationEnabled && noErrs > 0) {
            String option = "-Dweblogic.configuration.schemaValidationEnabled=false";
            Loggable l = ManagementLogger.logConfigurationSchemaFailureLoggable(fileName, option);
            throw new IOException(l.getMessage());
         }
      }

   }

   public static boolean getBooleanProperty(String prop, boolean _default) {
      String value = System.getProperty(prop);
      return value != null ? Boolean.parseBoolean(value) : _default;
   }

   public synchronized void shutdown() {
      if (!this.temporaryTrees.isEmpty()) {
         System.gc();
         Iterator var1 = this.temporaryTrees.values().iterator();

         while(var1.hasNext()) {
            String treeType = (String)var1.next();
            ManagementLogger.logTemporaryBeanTreeNotGarbageCollected(treeType);
         }

      }
   }

   private void addTemporaryTree(Object beanTree, String name) {
      this.temporaryTrees.put(beanTree, name + "(" + new Date() + ")");
   }

   static void setProductionModeInfo(Descriptor descTree) {
      DescriptorBean root = descTree.getRootBean();
      if (root instanceof DomainMBean && ((DomainMBean)root).isProductionModeEnabled()) {
         DescriptorHelper.setDescriptorTreeProductionMode(descTree, true);
      } else {
         DescriptorHelper.setDescriptorTreeProductionMode(descTree, false);
      }

   }

   public void registerCallbackHandler(EditAccessCallbackHandler handler) {
      synchronized(this.callbackHandlers) {
         if (!this.callbackHandlers.contains(handler)) {
            this.callbackHandlers.add(handler);
         }

      }
   }

   private void callbackSaveChanges() {
      synchronized(this.callbackHandlers) {
         Iterator var2 = this.callbackHandlers.iterator();

         while(var2.hasNext()) {
            EditAccessCallbackHandler handler = (EditAccessCallbackHandler)var2.next();
            handler.saveChanges();
         }

      }
   }

   private void callbackUndoUnsavedChanges() {
      synchronized(this.callbackHandlers) {
         Iterator var2 = this.callbackHandlers.iterator();

         while(var2.hasNext()) {
            EditAccessCallbackHandler handler = (EditAccessCallbackHandler)var2.next();
            handler.undoUnsavedChanges();
         }

      }
   }

   private void callbackUndoUnactivatedChanges() {
      synchronized(this.callbackHandlers) {
         Iterator var2 = this.callbackHandlers.iterator();

         while(var2.hasNext()) {
            EditAccessCallbackHandler handler = (EditAccessCallbackHandler)var2.next();
            handler.undoUnactivatedChanges();
         }

      }
   }

   private void callbackActivateChanges() throws IOException {
      synchronized(this.callbackHandlers) {
         Iterator var2 = this.callbackHandlers.iterator();

         while(var2.hasNext()) {
            EditAccessCallbackHandler handler = (EditAccessCallbackHandler)var2.next();
            handler.activateChanges();
         }

      }
   }

   private Iterator callbackGetChanges() {
      synchronized(this.callbackHandlers) {
         ArrayList changes = new ArrayList();
         Iterator var3 = this.callbackHandlers.iterator();

         while(var3.hasNext()) {
            EditAccessCallbackHandler handler = (EditAccessCallbackHandler)var3.next();
            Iterator chIt = handler.getChanges();

            while(chIt.hasNext()) {
               changes.add(chIt.next());
            }
         }

         return changes.iterator();
      }
   }

   private Iterator callbackGetUnactivatedChanges() {
      synchronized(this.callbackHandlers) {
         ArrayList changes = new ArrayList();
         Iterator var3 = this.callbackHandlers.iterator();

         while(var3.hasNext()) {
            EditAccessCallbackHandler handler = (EditAccessCallbackHandler)var3.next();
            Iterator chIt = handler.getUnactivatedChanges();

            while(chIt.hasNext()) {
               changes.add(chIt.next());
            }
         }

         return changes.iterator();
      }
   }

   private void callbackUpdateApplication() throws IOException {
      synchronized(this.callbackHandlers) {
         Iterator var2 = this.callbackHandlers.iterator();

         while(var2.hasNext()) {
            EditAccessCallbackHandler handler = (EditAccessCallbackHandler)var2.next();
            handler.updateApplication();
         }

      }
   }

   public void syncPartitionConfig(String partitionName) throws Exception {
      PartitionFileSystemMBean pfsBean = this.getDomainBean().lookupPartition(partitionName).getSystemFileSystem();
      PartitionDir partitionDir = new PartitionDir(pfsBean.getRoot(), partitionName);
      String partitionConfigDir = partitionDir.getConfigDir();

      try {
         File partitionConfigDirFile = new File(partitionConfigDir);
         File pendingDirFile = new File(DomainDir.getPendingDir());
         FileUtils.copyPreservePermissions(partitionConfigDirFile, pendingDirFile);
      } catch (IOException var7) {
         this.undoUnactivatedChanges();
         throw new Exception(var7);
      }
   }

   public EditAccess createEditAccess(String partitionName, String name, String description) {
      return new EditAccessImpl(partitionName, name, description);
   }

   public ResolveTask resolve(boolean stopOnConflict, long timeout) throws EditNotEditorException {
      AuthenticatedSubject user = this.checkEditLock();
      ResolveActivateTask resolveTask = new ResolveActivateTask(this, user, (long)this.random.nextInt(), stopOnConflict, timeout);
      activateQueue.enqueue(new ActivateQueue.ResolveTaskWithTimeout(activateQueue, resolveTask));
      return resolveTask;
   }

   synchronized void doResolve(boolean stopOnConflict, ResolveActivateTask resolveTask) throws ManagementException, DiffConflictException, NonResolvableDiffConflictException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "doResolve()");
      }

      Map conflictsPerEditTree = this.computeConflicts();
      if (conflictsPerEditTree != null) {
         if (resolveTask != null) {
            resolveTask.setConflicts(conflictsPerEditTree.keySet());
         }

         DiffConflicts.checkNonResolvableConflicts(conflictsPerEditTree.keySet());
         List allConflicts = new ArrayList();
         Iterator var5 = conflictsPerEditTree.keySet().iterator();

         while(var5.hasNext()) {
            DiffConflicts conflict = (DiffConflicts)var5.next();
            allConflicts.addAll(conflict.getConflicts());
         }

         if (!allConflicts.isEmpty() && stopOnConflict) {
            throw new DiffConflictException(allConflicts);
         }

         List patches = new ArrayList();

         try {
            Iterator var19 = conflictsPerEditTree.entrySet().iterator();

            label185:
            while(true) {
               Map.Entry treeConflict;
               ConflictDescriptorDiff patch;
               do {
                  if (!var19.hasNext()) {
                     this.saveChangesInternal();
                     this.doLoadEditTree();
                     if (resolveTask != null) {
                        resolveTask.setPatchDescription(ConflictDescriptorDiff.constructMessage(patches));
                     }
                     break label185;
                  }

                  treeConflict = (Map.Entry)var19.next();
                  patch = ((DiffConflicts)treeConflict.getKey()).getPatch();
               } while(patch.size() <= 0);

               patches.add(patch);
               Iterator var9 = patch.getResolveUpdateEvents().iterator();

               while(var9.hasNext()) {
                  ConflictDescriptorDiff.ResolveUpdateEvent event = (ConflictDescriptorDiff.ResolveUpdateEvent)var9.next();
                  event.apply();
               }

               Descriptor editTree = (Descriptor)treeConflict.getValue();
               if (editTree != null) {
                  boolean editable = editTree.isEditable();

                  try {
                     ((DescriptorImpl)editTree).setEditable(false);
                     editTree.applyDiff(patch);
                  } finally {
                     if (editable) {
                        ((DescriptorImpl)editTree).setEditable(true);
                     }

                  }
               }
            }
         } catch (DescriptorUpdateFailedException var17) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Exception updating edit tree ", var17);
            }

            throw new EditFailedException(var17);
         }
      } else if (this.lockMgr.isMergeNeeded()) {
         this.doLoadEditTree();
         this.doLoadCurrentTree(false);
      }

      if (resolveTask == null) {
         try {
            this.currentTree.prepareUpdateDiff(this.editTree, false);
         } catch (DescriptorUpdateRejectedException var15) {
            debugLogger.debug("Prepare update diff is rejected. It means that it is already prepared", var15);
         }
      }

      this.directoryMgr.deleteAllOriginal();
      this.lockMgr.clearMergeNeeded();
      if (resolveTask != null) {
         this.createOriginalFiles(this.currentTree);
      }

   }

   private synchronized Map computeConflicts() throws ManagementException {
      if (this.lockMgr.isMergeNeeded() && (this.isModified() || this.directoryMgr.hasPendingConfigs())) {
         if (this.isModified()) {
            this.saveChangesInternal();
            this.doLoadEditTree();
         }

         this.doLoadCurrentTree(true);

         Descriptor originalTree;
         try {
            originalTree = this.loadOriginalEditTreeFromPending();
         } catch (IOException var13) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Exception loading tree ", var13);
            }

            throw new EditFailedException(var13);
         }

         Map conflicts = new HashMap();
         Map originalTrees = this.getAllTrees(originalTree);
         Map editTrees = this.getAllTrees(this.editTree);
         Map currentTrees = this.getAllTrees(this.currentTree);
         Set treeNames = new HashSet();
         treeNames.addAll(originalTrees.keySet());
         treeNames.addAll(editTrees.keySet());
         treeNames.addAll(currentTrees.keySet());
         Iterator var7 = treeNames.iterator();

         while(var7.hasNext()) {
            String treeName = (String)var7.next();
            Descriptor original = (Descriptor)originalTrees.get(treeName);
            Descriptor edit = (Descriptor)editTrees.get(treeName);
            Descriptor current = (Descriptor)currentTrees.get(treeName);
            DiffConflicts diffConflicts = new DiffConflicts(current, original, edit, treeName);
            conflicts.put(diffConflicts, edit);
         }

         return conflicts;
      } else {
         return null;
      }
   }

   private Map getAllTrees(Descriptor mainTree) {
      Map trees = new HashMap();
      trees.put(BootStrap.getDefaultConfigFileName(), mainTree);
      Iterator it = DescriptorInfoUtils.getDescriptorInfos(mainTree);

      while(it != null && it.hasNext()) {
         DescriptorInfo descriptorInfo = (DescriptorInfo)it.next();
         ConfigurationExtensionMBean configurationExtension = descriptorInfo.getConfigurationExtension();
         String treeName = ((AbstractDescriptorBean)configurationExtension)._getQualifiedName();
         trees.put(treeName, descriptorInfo.getDescriptor());
      }

      return trees;
   }

   public void register(EditAccess.EventListener listener) {
      if (listener != null) {
         this.lifecycleListeners.add(new WeakReference(listener));
      }

   }

   public void nonConfigTaskCompleted(long taskId, boolean success) {
      ActivateTaskImpl task = null;
      ActivateQueue.ActivateTaskImplWithTimeout taskWithTimeout = (ActivateQueue.ActivateTaskImplWithTimeout)activateQueue.lookupTask(taskId);
      if (taskWithTimeout != null) {
         task = taskWithTimeout.getActivateTask();
      }

      if (task != null && !task.isHaveConfigDeployments()) {
         if (success) {
            task.setState(4);
         } else {
            task.setState(5);
         }
      }

   }

   public AutoResolveResult getStartEditResolveResult() {
      return this.startEditResolveResult;
   }

   private String singlePartitionUpdate() {
      DescriptorDiff currentDiff = this.currentTree.computeDiff(this.editTree);
      String singlePart = null;
      Iterator var3 = currentDiff.iterator();

      BeanUpdateEvent.PropertyUpdate[] prop;
      label48:
      do {
         String partName;
         do {
            if (!var3.hasNext()) {
               return singlePart;
            }

            BeanUpdateEvent bue = (BeanUpdateEvent)var3.next();
            DescriptorBean bean = bue.getProposedBean();
            partName = null;
            if (bean instanceof DomainMBean) {
               prop = bue.getUpdateList();
               if (prop.length != 1) {
                  return null;
               }
               continue label48;
            }

            if (!this.isPartitionResourceGroupAppUpdate(bue)) {
               return null;
            }

            do {
               if (bean instanceof PartitionMBean) {
                  partName = ((PartitionMBean)bean).getName();
                  if (singlePart == null) {
                     singlePart = partName;
                  } else if (!singlePart.equals(partName)) {
                     return null;
                  }
               }

               bean = bean.getParentBean();
            } while(bean != null && bean.getParentBean() != null);
         } while(partName != null);

         return null;
      } while("ConfigurationVersion".equals(prop[0].getPropertyName()));

      return null;
   }

   private boolean isPartitionResourceGroupAppUpdate(BeanUpdateEvent bue) {
      DescriptorBean bean = bue.getProposedBean();
      if (bean instanceof ResourceGroupMBean) {
         BeanUpdateEvent.PropertyUpdate[] props = bue.getUpdateList();
         if (props != null && props.length == 1) {
            BeanUpdateEvent.PropertyUpdate prop = props[0];
            Object value = prop.getAddedObject();
            if (value == null) {
               value = prop.getRemovedObject();
            }

            return value instanceof AppDeploymentMBean && "AppDeployments".equals(prop.getPropertyName());
         }
      }

      return false;
   }

   private boolean isResourceGroupUpdate(BeanUpdateEvent bue) {
      DescriptorBean bean = bue.getProposedBean();
      if (bean instanceof PartitionMBean) {
         BeanUpdateEvent.PropertyUpdate[] props = bue.getUpdateList();
         if (props != null && props.length == 1) {
            BeanUpdateEvent.PropertyUpdate prop = props[0];
            Object value = prop.getAddedObject();
            if (value == null) {
               value = prop.getRemovedObject();
            }

            return value instanceof ResourceGroupMBean && "ResourceGroups".equals(prop.getPropertyName());
         }
      }

      return false;
   }

   private boolean originalConfigExists() {
      return this.directoryMgr.getOriginalConfigFile().exists();
   }

   private String getDebugInfo() {
      return "[EditAccessImpl partition:" + this.getPartitionName() + " name:" + this.getEditSessionName() + "] ";
   }

   public void failedBeforeDeploymentStart(long taskId) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Task " + taskId + " failed before starting deployment");
      }

      ActivateQueue.TaskWithTimeout failedTaskWithTimeout = activateQueue.lookupTask(taskId);
      if (failedTaskWithTimeout instanceof ActivateQueue.ActivateTaskImplWithTimeout) {
         ActivateQueue.ActivateTaskImplWithTimeout failedTask = (ActivateQueue.ActivateTaskImplWithTimeout)failedTaskWithTimeout;
         DeploymentRequest req = failedTask.getRequest();
         req.deploymentFailedInConfigLayer();
      }

   }

   private class ActivateTaskCompletionListener implements ActivateTaskImpl.CompletionListener {
      private final ActivateTask activateTask;

      private ActivateTaskCompletionListener(ActivateTask activateTask) {
         this.activateTask = activateTask;
      }

      public void onCompleted() {
         if (this.activateTask.getState() == 4 || 2 == this.activateTask.getState()) {
            ((DescriptorImpl)EditAccessImpl.this.editTree).setModified(false);
            EditAccessImpl.this.setExternalTreesUnmodified(EditAccessImpl.this.editTree);

            try {
               DomainConfiguration.getInstance().update(EditAccessImpl.this.currentTree);
            } catch (Exception var2) {
               if (EditAccessImpl.debugLogger.isDebugEnabled()) {
                  EditAccessImpl.debugLogger.debug("Domain configuration could not be updated");
               }
            }
         }

         EditAccessImpl.this.resetPreparingInfo();
         ManagementServiceRestricted.runOnAllEditSessions(new ManagementServiceRestricted.RunnableWithParam() {
            public void run(EditAccess editAccess) {
               if (!editAccess.equals(EditAccessImpl.this)) {
                  if (editAccess instanceof EditAccessImpl) {
                     ((EditAccessImpl)editAccess).renewEditTreeIfPossible();
                  }

               }
            }
         }, EditAccessImpl.kernelIdentity);
         ManagementServiceRestricted.notifyEditSessionActivateCompleted(EditAccessImpl.this, this.activateTask);
      }

      public void onLockReleased() {
      }

      // $FF: synthetic method
      ActivateTaskCompletionListener(ActivateTask x1, Object x2) {
         this(x1);
      }
   }

   private class ActivateTaskStartListener implements ActivateTaskImpl.StartListener {
      private final ActivateTaskImpl activateTask;

      ActivateTaskStartListener(ActivateTaskImpl activateTask) {
         this.activateTask = activateTask;
      }

      public void onStarted() {
         if (EditAccessImpl.debugLogger.isDebugEnabled()) {
            EditAccessImpl.debugLogger.debug("processQueue() - task: " + this.activateTask.getTaskId() + " started;");
         }

         this.backupConfig();
         ManagementServiceRestricted.runOnAllEditSessions(new ManagementServiceRestricted.RunnableWithParam() {
            public void run(EditAccess editAccess) {
               if (!editAccess.equals(EditAccessImpl.this)) {
                  editAccess.markMergeNeeded();
               }
            }
         }, EditAccessImpl.kernelIdentity);
      }

      private void backupConfig() {
         try {
            ConfigBackup.saveVersioned();
         } catch (IOException var2) {
            if (EditAccessImpl.debugLogger.isDebugEnabled()) {
               EditAccessImpl.debugLogger.debug(EditAccessImpl.this.getDebugInfo() + "Exception backing up config ", var2);
            }
         }

      }
   }

   private static class ReLockState {
      int waitTimeInMillis;
      int timeOutInMillis;
      boolean exclusive;
      AuthenticatedSubject lastLockHolder;

      private ReLockState() {
      }

      // $FF: synthetic method
      ReLockState(Object x0) {
         this();
      }
   }
}
