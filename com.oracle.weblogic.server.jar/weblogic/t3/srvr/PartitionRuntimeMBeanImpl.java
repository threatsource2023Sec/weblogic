package weblogic.t3.srvr;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.descriptor.DescriptorBean;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.health.HealthStateBuilder;
import weblogic.management.ManagementException;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.PartitionRuntimeStateManager;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;
import weblogic.management.partition.admin.PartitionManagerService;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGOperation;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RegistrationHandler;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.Service;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.BatchJobRepositoryRuntimeMBean;
import weblogic.management.runtime.ConcurrentManagedObjectsRuntimeMBean;
import weblogic.management.runtime.ConnectorServiceRuntimeMBean;
import weblogic.management.runtime.JDBCPartitionRuntimeMBean;
import weblogic.management.runtime.JMSRuntimeMBean;
import weblogic.management.runtime.JTAPartitionRuntimeMBean;
import weblogic.management.runtime.LibraryRuntimeMBean;
import weblogic.management.runtime.MailSessionRuntimeMBean;
import weblogic.management.runtime.MaxThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.MessagingBridgeRuntimeMBean;
import weblogic.management.runtime.MinThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.PartitionLifeCycleModel;
import weblogic.management.runtime.PartitionResourceMetricsRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.PartitionWorkManagerRuntimeMBean;
import weblogic.management.runtime.PathServiceRuntimeMBean;
import weblogic.management.runtime.PersistentStoreRuntimeMBean;
import weblogic.management.runtime.RequestClassRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SAFRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.management.runtime.WseeClusterFrontEndRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.Operation;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.management.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManagerFactory;

public class PartitionRuntimeMBeanImpl extends RuntimeMBeanDelegate implements PartitionRuntimeMBean {
   private PartitionRuntimeMBean.State state;
   private PartitionRuntimeMBean.State subState;
   private PartitionRuntimeMBean.State prevState;
   private PartitionRuntimeMBean.State prevSubState;
   private String partitionID;
   private String partitionName;
   private ConnectorServiceRuntimeMBean connectorServiceRuntime;
   private JDBCPartitionRuntimeMBean jdbcRuntime;
   private JMSRuntimeMBean jmsRuntime;
   private SAFRuntimeMBean safRuntime;
   private final Set applicationRuntimes;
   private final Map libraryRuntimes;
   private final Map persistentStoreRuntimes;
   private final Set workManagerRuntimes;
   private final Set minThreadsConstraintRuntimes;
   private final Set maxThreadsConstraintRuntimes;
   private final Set requestClassRuntimes;
   private final Set mailSessionRuntimes;
   private final Set messagingBridgeRuntimes;
   private WseeClusterFrontEndRuntimeMBean wseeFrontEndRuntime;
   private WLDFPartitionRuntimeMBean wldfRuntime;
   private JTAPartitionRuntimeMBean jtaRuntime;
   private Set pathServiceRuntimes;
   private PathServiceRuntimeMBean domainScopePathServiceRuntimeMBean;
   private ConcurrentManagedObjectsRuntimeMBean concurrentManagedObjectsRuntime;
   private PartitionResourceMetricsRuntimeMBean partitionResourceMetricsRuntime;
   private PartitionWorkManagerRuntimeMBean partitionWorkMnagerRuntime;
   private BatchJobRepositoryRuntimeMBean batchJobRepositoryPartitionRuntimeMBean;
   private PartitionManagerService partitionManagerService;
   private PartitionRuntimeStateManager partitionRuntimeStateManager;
   private ConcurrentHashMap rgStates;
   private final RegistrationHandler registrationHandler;
   private boolean partitionRestartRequired;
   private final Set pendingRestartResources;
   private final Set pendingRestartSystemResources;
   private PartitionRuntimeMBean.Operation partitionOpInProgress;
   private ConcurrentHashMap rgOpInProgress;
   private RuntimeAccess runtimeAccess;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public PartitionRuntimeMBeanImpl(String name, String id) throws ManagementException {
      super(name);
      this.state = State.UNKNOWN;
      this.subState = State.UNKNOWN;
      this.prevState = null;
      this.prevSubState = null;
      this.applicationRuntimes = Collections.synchronizedSet(new HashSet());
      this.libraryRuntimes = Collections.synchronizedMap(new HashMap());
      this.persistentStoreRuntimes = new HashMap();
      this.workManagerRuntimes = new HashSet();
      this.minThreadsConstraintRuntimes = new HashSet();
      this.maxThreadsConstraintRuntimes = new HashSet();
      this.requestClassRuntimes = new HashSet();
      this.mailSessionRuntimes = new HashSet();
      this.messagingBridgeRuntimes = new HashSet();
      this.pathServiceRuntimes = new HashSet();
      this.partitionManagerService = (PartitionManagerService)GlobalServiceLocator.getServiceLocator().getService(PartitionManagerService.class, new Annotation[0]);
      this.partitionRuntimeStateManager = (PartitionRuntimeStateManager)GlobalServiceLocator.getServiceLocator().getService(PartitionRuntimeStateManager.class, new Annotation[0]);
      this.rgStates = new ConcurrentHashMap();
      this.pendingRestartResources = new HashSet();
      this.pendingRestartSystemResources = new HashSet();
      this.rgOpInProgress = new ConcurrentHashMap();
      this.runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      this.partitionID = id;
      this.partitionName = name;
      this.registrationHandler = this.createRegistrationHandler();
      this.runtimeAccess.addRegistrationHandler(this.registrationHandler);
   }

   private RegistrationHandler createRegistrationHandler() {
      return new RegistrationHandler() {
         public void registered(RuntimeMBean runtime, DescriptorBean config) {
            PartitionRuntimeMBean parent;
            if (runtime instanceof ApplicationRuntimeMBean && runtime.getParent() instanceof PartitionRuntimeMBean) {
               parent = (PartitionRuntimeMBean)runtime.getParent();
               if (parent.getName().equals(PartitionRuntimeMBeanImpl.this.partitionName)) {
                  PartitionRuntimeMBeanImpl.this.applicationRuntimes.add((ApplicationRuntimeMBean)runtime);
               }
            } else if (runtime instanceof LibraryRuntimeMBean && runtime.getParent() instanceof PartitionRuntimeMBean) {
               parent = (PartitionRuntimeMBean)runtime.getParent();
               if (parent.getName().equals(PartitionRuntimeMBeanImpl.this.partitionName)) {
                  PartitionRuntimeMBeanImpl.this.libraryRuntimes.put(runtime.getName(), (LibraryRuntimeMBean)runtime);
               }
            }

         }

         public void unregistered(RuntimeMBean runtime) {
            PartitionRuntimeMBean parent;
            if (runtime instanceof ApplicationRuntimeMBean && runtime.getParent() instanceof PartitionRuntimeMBean) {
               parent = (PartitionRuntimeMBean)runtime.getParent();
               if (parent.getName().equals(PartitionRuntimeMBeanImpl.this.partitionName)) {
                  PartitionRuntimeMBeanImpl.this.applicationRuntimes.remove(runtime);
               }
            } else if (runtime instanceof LibraryRuntimeMBean && runtime.getParent() instanceof PartitionRuntimeMBean) {
               parent = (PartitionRuntimeMBean)runtime.getParent();
               if (parent.getName().equals(PartitionRuntimeMBeanImpl.this.partitionName)) {
                  PartitionRuntimeMBeanImpl.this.libraryRuntimes.remove(runtime.getName());
               }
            }

         }

         public void registeredCustom(ObjectName oname, Object custom) {
         }

         public void unregisteredCustom(ObjectName custom) {
         }

         public void registered(Service bean) {
         }

         public void unregistered(Service bean) {
         }
      };
   }

   public String getState() {
      return State.chooseUserDesiredStateName(this.state);
   }

   public String getSubState() {
      return State.chooseUserDesiredStateName(this.subState);
   }

   public PartitionRuntimeMBean.State getInternalState() {
      return this.state;
   }

   public PartitionRuntimeMBean.State getPrevInternalState() {
      return this.prevState;
   }

   public PartitionRuntimeMBean.State getPrevInternalSubState() {
      return this.prevSubState;
   }

   public PartitionRuntimeMBean.State getInternalSubState() {
      return this.subState;
   }

   public String getPartitionID() {
      return this.partitionID;
   }

   public JMSRuntimeMBean getJMSRuntime() {
      return this.jmsRuntime;
   }

   public void setJMSRuntime(JMSRuntimeMBean b) {
      this.jmsRuntime = b;
   }

   public SAFRuntimeMBean getSAFRuntime() {
      return this.safRuntime;
   }

   public void setSAFRuntime(SAFRuntimeMBean b) {
      this.safRuntime = b;
   }

   public JDBCPartitionRuntimeMBean getJDBCPartitionRuntime() {
      return this.jdbcRuntime;
   }

   public void setJDBCPartitionRuntime(JDBCPartitionRuntimeMBean b) {
      this.jdbcRuntime = b;
   }

   public WorkManagerRuntimeMBean[] getWorkManagerRuntimes() {
      int len = this.workManagerRuntimes.size();
      return (WorkManagerRuntimeMBean[])((WorkManagerRuntimeMBean[])this.workManagerRuntimes.toArray(new WorkManagerRuntimeMBean[len]));
   }

   public boolean addWorkManagerRuntime(WorkManagerRuntimeMBean q) {
      return this.workManagerRuntimes.add(q);
   }

   public boolean removeWorkManagerRuntime(WorkManagerRuntimeMBean q) {
      return this.workManagerRuntimes.remove(q);
   }

   public MinThreadsConstraintRuntimeMBean lookupMinThreadsConstraintRuntime(String name) {
      MinThreadsConstraintRuntimeMBean[] min = this.getMinThreadsConstraintRuntimes();

      for(int i = 0; i < min.length; ++i) {
         if (min[i].getName().equals(name)) {
            return min[i];
         }
      }

      return null;
   }

   public RequestClassRuntimeMBean lookupRequestClassRuntime(String name) {
      RequestClassRuntimeMBean[] rcs = this.getRequestClassRuntimes();

      for(int i = 0; i < rcs.length; ++i) {
         if (rcs[i].getName().equals(name)) {
            return rcs[i];
         }
      }

      return null;
   }

   public MaxThreadsConstraintRuntimeMBean lookupMaxThreadsConstraintRuntime(String name) {
      MaxThreadsConstraintRuntimeMBean[] max = this.getMaxThreadsConstraintRuntimes();

      for(int i = 0; i < max.length; ++i) {
         if (max[i].getName().equals(name)) {
            return max[i];
         }
      }

      return null;
   }

   public boolean addMaxThreadsConstraintRuntime(MaxThreadsConstraintRuntimeMBean temp) {
      return this.maxThreadsConstraintRuntimes.add(temp);
   }

   public MaxThreadsConstraintRuntimeMBean[] getMaxThreadsConstraintRuntimes() {
      int len = this.maxThreadsConstraintRuntimes.size();
      return (MaxThreadsConstraintRuntimeMBean[])((MaxThreadsConstraintRuntimeMBean[])this.maxThreadsConstraintRuntimes.toArray(new MaxThreadsConstraintRuntimeMBean[len]));
   }

   public boolean addMinThreadsConstraintRuntime(MinThreadsConstraintRuntimeMBean temp) {
      return this.minThreadsConstraintRuntimes.add(temp);
   }

   public boolean addRequestClassRuntime(RequestClassRuntimeMBean temp) {
      return this.requestClassRuntimes.add(temp);
   }

   public MinThreadsConstraintRuntimeMBean[] getMinThreadsConstraintRuntimes() {
      int len = this.minThreadsConstraintRuntimes.size();
      return (MinThreadsConstraintRuntimeMBean[])((MinThreadsConstraintRuntimeMBean[])this.minThreadsConstraintRuntimes.toArray(new MinThreadsConstraintRuntimeMBean[len]));
   }

   public RequestClassRuntimeMBean[] getRequestClassRuntimes() {
      int len = this.requestClassRuntimes.size();
      return (RequestClassRuntimeMBean[])((RequestClassRuntimeMBean[])this.requestClassRuntimes.toArray(new RequestClassRuntimeMBean[len]));
   }

   public ApplicationRuntimeMBean[] getApplicationRuntimes() {
      synchronized(this.applicationRuntimes) {
         int len = this.applicationRuntimes.size();
         return (ApplicationRuntimeMBean[])this.applicationRuntimes.toArray(new ApplicationRuntimeMBean[len]);
      }
   }

   public ApplicationRuntimeMBean lookupApplicationRuntime(String name) {
      synchronized(this.applicationRuntimes) {
         Iterator var3 = this.applicationRuntimes.iterator();

         ApplicationRuntimeMBean applicationRuntimeMBean;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            applicationRuntimeMBean = (ApplicationRuntimeMBean)var3.next();
         } while(!applicationRuntimeMBean.getName().equals(name));

         return applicationRuntimeMBean;
      }
   }

   public LibraryRuntimeMBean[] getLibraryRuntimes() {
      synchronized(this.libraryRuntimes) {
         return (LibraryRuntimeMBean[])this.libraryRuntimes.values().toArray(new LibraryRuntimeMBean[this.libraryRuntimes.size()]);
      }
   }

   public LibraryRuntimeMBean lookupLibraryRuntime(String name) {
      return (LibraryRuntimeMBean)this.libraryRuntimes.get(name);
   }

   public WLDFPartitionRuntimeMBean getWLDFPartitionRuntime() {
      return this.wldfRuntime;
   }

   public void setWLDFPartitionRuntime(WLDFPartitionRuntimeMBean mbean) {
      this.wldfRuntime = mbean;
   }

   public JTAPartitionRuntimeMBean getJTAPartitionRuntime() {
      return this.jtaRuntime;
   }

   public void setJTAPartitionRuntime(JTAPartitionRuntimeMBean mbean) {
      this.jtaRuntime = mbean;
   }

   public MailSessionRuntimeMBean[] getMailSessionRuntimes() {
      return (MailSessionRuntimeMBean[])((MailSessionRuntimeMBean[])this.mailSessionRuntimes.toArray(new MailSessionRuntimeMBean[this.mailSessionRuntimes.size()]));
   }

   public boolean addMailSessionRuntime(MailSessionRuntimeMBean r) {
      return this.mailSessionRuntimes.add(r);
   }

   public boolean removeMailSessionRuntime(MailSessionRuntimeMBean r) {
      return this.mailSessionRuntimes.remove(r);
   }

   public synchronized MessagingBridgeRuntimeMBean[] getMessagingBridgeRuntimes() {
      int len = this.messagingBridgeRuntimes.size();
      return (MessagingBridgeRuntimeMBean[])((MessagingBridgeRuntimeMBean[])this.messagingBridgeRuntimes.toArray(new MessagingBridgeRuntimeMBean[len]));
   }

   public synchronized boolean addMessagingBridgeRuntime(MessagingBridgeRuntimeMBean r) {
      return !this.messagingBridgeRuntimes.contains(r) ? this.messagingBridgeRuntimes.add(r) : false;
   }

   public synchronized boolean removeMessagingBridgeRuntime(MessagingBridgeRuntimeMBean r) {
      return this.messagingBridgeRuntimes.remove(r);
   }

   public synchronized MessagingBridgeRuntimeMBean lookupMessagingBridgeRuntime(String name) {
      MessagingBridgeRuntimeMBean[] mbrs = this.getMessagingBridgeRuntimes();

      for(int i = 0; i < mbrs.length; ++i) {
         if (mbrs[i].getName().equals(name)) {
            return mbrs[i];
         }
      }

      return null;
   }

   public PersistentStoreRuntimeMBean[] getPersistentStoreRuntimes() {
      Collection runtimes = this.persistentStoreRuntimes.values();
      return (PersistentStoreRuntimeMBean[])((PersistentStoreRuntimeMBean[])runtimes.toArray(new PersistentStoreRuntimeMBean[this.persistentStoreRuntimes.size()]));
   }

   public PersistentStoreRuntimeMBean lookupPersistentStoreRuntime(String name) {
      return (PersistentStoreRuntimeMBean)this.persistentStoreRuntimes.get(name);
   }

   public void addPersistentStoreRuntime(PersistentStoreRuntimeMBean r) {
      this.persistentStoreRuntimes.put(r.getName(), r);
   }

   public void removePersistentStoreRuntime(PersistentStoreRuntimeMBean r) {
      this.persistentStoreRuntimes.remove(r.getName());
   }

   public ConnectorServiceRuntimeMBean getConnectorServiceRuntime() {
      return this.connectorServiceRuntime;
   }

   public void setConnectorServiceRuntime(ConnectorServiceRuntimeMBean connectorService) {
      this.connectorServiceRuntime = connectorService;
   }

   public PathServiceRuntimeMBean getPathServiceRuntime() {
      return this.domainScopePathServiceRuntimeMBean;
   }

   public PathServiceRuntimeMBean[] getPathServiceRuntimes() {
      int len = this.pathServiceRuntimes.size();
      return (PathServiceRuntimeMBean[])this.pathServiceRuntimes.toArray(new PathServiceRuntimeMBean[len]);
   }

   public boolean addPathServiceRuntime(PathServiceRuntimeMBean q, boolean isDomainScope) {
      if (isDomainScope) {
         this.domainScopePathServiceRuntimeMBean = q;
      }

      return this.pathServiceRuntimes.add(q);
   }

   public boolean removePathServiceRuntime(PathServiceRuntimeMBean q, boolean isDomainScope) {
      if (isDomainScope) {
         this.domainScopePathServiceRuntimeMBean = null;
      }

      return this.pathServiceRuntimes.remove(q);
   }

   public void setPathServiceRuntime(PathServiceRuntimeMBean b) {
      throw new UnsupportedOperationException("@deprecated. incorrect 12.2.1 api will soon be removed. is replaced by addPathServiceRuntime(psr, boolean isDomainScope)");
   }

   public ConcurrentManagedObjectsRuntimeMBean getConcurrentManagedObjectsRuntime() {
      return this.concurrentManagedObjectsRuntime;
   }

   public void setConcurrentManagedObjectsRuntime(ConcurrentManagedObjectsRuntimeMBean r) {
      this.concurrentManagedObjectsRuntime = r;
   }

   public PartitionResourceMetricsRuntimeMBean getPartitionResourceMetricsRuntime() {
      return this.partitionResourceMetricsRuntime;
   }

   public void setPartitionResourceMetricsRuntime(PartitionResourceMetricsRuntimeMBean obj) {
      this.partitionResourceMetricsRuntime = obj;
   }

   public PartitionWorkManagerRuntimeMBean getPartitionWorkManagerRuntime() {
      return this.partitionWorkMnagerRuntime;
   }

   public void setPartitionWorkManagerRuntime(PartitionWorkManagerRuntimeMBean r) {
      this.partitionWorkMnagerRuntime = r;
   }

   public BatchJobRepositoryRuntimeMBean getBatchJobRepositoryRuntime() {
      return this.batchJobRepositoryPartitionRuntimeMBean;
   }

   public void setBatchJobRepositoryRuntime(BatchJobRepositoryRuntimeMBean batchJobRepositoryPartitionRuntimeMBean) {
      this.batchJobRepositoryPartitionRuntimeMBean = batchJobRepositoryPartitionRuntimeMBean;
   }

   public void suspend(int timeout, boolean ignoreSessions) throws PartitionLifeCycleException {
      this.doOperation(Operation.SUSPEND, timeout, ignoreSessions);
   }

   public void suspend() throws PartitionLifeCycleException {
      this.suspend(0, false);
   }

   public void forceSuspend() throws PartitionLifeCycleException {
      this.doOperation(Operation.FORCE_SUSPEND);
   }

   public void resume() throws PartitionLifeCycleException {
      this.doOperation(Operation.RESUME);
   }

   public void shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws PartitionLifeCycleException {
      this.doOperation(Operation.SHUTDOWN, timeout, ignoreSessions, waitForAllSessions);
      this.resetPartitionRestartRequiredFlagOnPartitionShutdown();
   }

   private void resetPartitionRestartRequiredFlagOnPartitionShutdown() throws PartitionLifeCycleException {
      if (this.runtimeAccess.isAdminServer()) {
         PartitionMBean partitionMBean = this.getDomain().lookupPartition(this.partitionName);
         ResourceGroupMBean[] adminRGs = partitionMBean.findAdminResourceGroupsTargeted(this.runtimeAccess.getAdminServerName());
         if (adminRGs.length > 0) {
            ResourceGroupMBean[] var3 = adminRGs;
            int var4 = adminRGs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               ResourceGroupMBean rg = var3[var5];

               try {
                  if (RGState.SHUTDOWN != this.getInternalRgState(rg.getName())) {
                     return;
                  }
               } catch (ResourceGroupLifecycleException var8) {
                  throw new PartitionLifeCycleException("resetPartitionRestartRequiredFlagOnPartitionShutdown()::Exception while trying to get InternalRgState. " + var8.getMessage(), var8);
               }
            }
         }

         this.partitionRestartRequired = false;
      }

   }

   public void shutdown(int timeout, boolean ignoreSessions) throws PartitionLifeCycleException {
      this.shutdown(timeout, ignoreSessions, false);
   }

   public void shutdown() throws PartitionLifeCycleException {
      this.shutdown(0, false);
   }

   public void forceShutdown() throws PartitionLifeCycleException {
      this.doOperation(Operation.FORCE_SHUTDOWN);
      this.resetPartitionRestartRequiredFlagOnPartitionShutdown();
   }

   public void halt() throws PartitionLifeCycleException {
      this.doOperation(Operation.HALT);
   }

   public void startResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doRGOperation(RGOperation.START, resourceGroupName);
   }

   public void startResourceGroupInAdmin(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doRGOperation(RGOperation.ADMIN, resourceGroupName);
   }

   public void suspendResourceGroup(String resourceGroupName, int timeout, boolean ignoreSessions) throws ResourceGroupLifecycleException {
      this.doRGOperation(RGOperation.SUSPEND, resourceGroupName, timeout, ignoreSessions);
   }

   public void suspendResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.suspendResourceGroup(resourceGroupName, 0, false);
   }

   public void forceSuspendResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doRGOperation(RGOperation.FORCE_SUSPEND, resourceGroupName);
   }

   public void resumeResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doRGOperation(RGOperation.RESUME, resourceGroupName);
   }

   public void forceShutdownResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doRGOperation(RGOperation.FORCE_SHUTDOWN, resourceGroupName);
   }

   public void shutdownResourceGroup(String resourceGroupName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws ResourceGroupLifecycleException {
      this.doRGOperation(RGOperation.SHUTDOWN, resourceGroupName, timeout, ignoreSessions, waitForAllSessions);
   }

   public void shutdownResourceGroup(String resourceGroupName, int timeout, boolean ignoreSessions) throws ResourceGroupLifecycleException {
      this.shutdownResourceGroup(resourceGroupName, timeout, ignoreSessions, true);
   }

   public void shutdownResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.shutdownResourceGroup(resourceGroupName, 0, false);
   }

   public void setState(PartitionRuntimeMBean.State state) {
      this.prevState = this.state;
      String oldState = this.state.name();
      this.updateRunState(state.name(), oldState);
      this.state = state;
      this.setAllRGStates();
   }

   public void setSubState(PartitionRuntimeMBean.State subState) {
      this.prevSubState = this.subState;
      this.subState = subState;
   }

   private void setStates(PartitionRuntimeMBean.State state, PartitionRuntimeMBean.State subState) {
      this.setSubState(subState);
      this.setState(state);
   }

   private void setAllRGStates() {
      boolean isDebug = PartitionLifecycleDebugger.isDebugEnabled();
      Set rgsForThisServer = PartitionUtils.getResourceGroupsForThisServer(this.partitionName);
      Iterator var3 = rgsForThisServer.iterator();

      while(var3.hasNext()) {
         String rg = (String)var3.next();
         ResourceGroupMBean rgmb = this.getDomain().lookupPartition(this.partitionName).lookupResourceGroup(rg);
         ResourceGroupLifecycleOperations.RGState proposedRGState = PartitionUtils.filteredResourceGroupState(this.getInternalState().name(), rgmb.isAdministrative());
         if (this.isRGStateAffected(rg)) {
            String desiredOnServerRGState = this.partitionRuntimeStateManager.getResourceGroupState(this.partitionName, rg, this.getServerName(), rgmb.isAdministrative());
            String desiredPState = this.partitionRuntimeStateManager.getPartitionState(this.partitionName);
            if (isDebug) {
               debug("Compare phase for RG " + rg + ":  the proposed RG state is " + proposedRGState.name() + " current RG state : " + this.rgStates.get(rg) + " the desiredOnServerRGState : " + desiredOnServerRGState + " the previous partition state: " + this.states(this.prevState, this.prevSubState) + " the desired partition state :" + desiredPState);
            }

            ResourceGroupLifecycleOperations.RGState desiredOnServer = RGState.valueOf(desiredOnServerRGState);
            if (this.rgStates.get(rg) == null) {
               this.rgStates.put(rg, RGState.min(new ResourceGroupLifecycleOperations.RGState[]{desiredOnServer, proposedRGState}));
               if (isDebug) {
                  debug("Null phase : RG state for RG " + rg + " was not previously recorded and has been set to " + this.rgStates.get(rg));
               }
            } else if (ResourceGroupLifecycleOperations.isStartUpTransitionState(proposedRGState)) {
               this.rgStates.put(rg, RGState.min(new ResourceGroupLifecycleOperations.RGState[]{desiredOnServer, proposedRGState}));
               if (isDebug) {
                  debug("Startup phase : Setting the state for RG " + rg + " the RG state is " + this.rgStates.get(rg) + " the partition state is : " + this.getInternalState().name());
               }
            } else if (ResourceGroupLifecycleOperations.isStateChangeAllowed(this.prevState, this.prevSubState, this.state, this.subState, (ResourceGroupLifecycleOperations.RGState)this.rgStates.get(rg), proposedRGState, desiredOnServer)) {
               this.rgStates.put(rg, proposedRGState);
               if (isDebug) {
                  debug("Other phase : Setting the state for RG " + rg + " the RG state is " + this.rgStates.get(rg) + " the partition state is : " + this.getInternalState().name());
               }
            } else if (isDebug) {
               debug("No change in state recorded for RG " + rg);
            }
         }
      }

   }

   private String states(PartitionRuntimeMBean.State state, PartitionRuntimeMBean.State substate) {
      return state.name() + (substate == null ? "" : "/" + substate.name());
   }

   private boolean isRGStateAffected(String rg) {
      if (PartitionLifeCycleModel.SHUTDOWN_TO_HALTED) {
         debug("Method isRGStateAffected is returning true, as state of RG : " + rg + " will be affected during shutting down and booted states of partition, because SHUTDOWN_TO_HALTED flag is true.");
         return true;
      } else {
         ResourceGroupMBean resourceGroupMBean = this.getDomain().lookupPartition(this.partitionName).lookupResourceGroup(rg);
         return !resourceGroupMBean.isAdministrative() || !PartitionUtils.statesNotImpactingAdminRG(this.getInternalSubState()) && !PartitionUtils.statesNotImpactingAdminRG(this.getInternalState());
      }
   }

   public void setRgState(String resourceGroupName, ResourceGroupLifecycleOperations.RGState rgState) throws ResourceGroupLifecycleException {
      if (PartitionUtils.getResourceGroupsForThisServer(this.partitionName).contains(resourceGroupName)) {
         if (rgState == RGState.SHUTDOWN) {
            this.rgStates.remove(resourceGroupName);
         } else {
            this.rgStates.put(resourceGroupName, rgState);
         }

      } else {
         throw new ResourceGroupLifecycleException("Resource group " + resourceGroupName + " does not exists on this target");
      }
   }

   public String getRgState(String resourceGroupName) throws ResourceGroupLifecycleException {
      return RGState.chooseUserDesiredStateName(this.getInternalRgState(resourceGroupName));
   }

   public ResourceGroupLifecycleOperations.RGState getInternalRgState(String resourceGroupName) throws ResourceGroupLifecycleException {
      if (PartitionUtils.getResourceGroupsForThisServer(this.partitionName).contains(resourceGroupName)) {
         ResourceGroupLifecycleOperations.RGState rgState = (ResourceGroupLifecycleOperations.RGState)this.rgStates.get(resourceGroupName);
         return rgState == null ? RGState.SHUTDOWN : rgState;
      } else {
         throw new ResourceGroupLifecycleException("Resource group " + resourceGroupName + " does not exists on this target");
      }
   }

   public HealthState getOverallHealthState() {
      HealthStateBuilder builder = new HealthStateBuilder();
      HealthState[] var2 = this.getSubsystemHealthStates();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         HealthState healthState = var2[var4];
         builder.append(healthState);
      }

      HealthState health = builder.get();
      health.setSubsystemName("PartitionRuntime." + this.getName());
      health.setMBeanName(this.getName());
      health.setMBeanType("PartitionTime");
      return health;
   }

   public CompositeData getOverallHealthStateJMX() throws OpenDataException {
      return this.getOverallHealthState().toCompositeData();
   }

   public HealthState[] getSubsystemHealthStates() {
      return HealthMonitorService.getPartitionHealthStates(this.partitionName);
   }

   public CompositeData[] getSubsystemHealthStatesJMX() throws OpenDataException {
      HealthState[] healthStates = this.getSubsystemHealthStates();
      CompositeData[] arr = new CompositeData[healthStates.length];

      for(int i = 0; i < healthStates.length; ++i) {
         arr[i] = healthStates[i].toCompositeData();
      }

      return arr;
   }

   public void updateRunState(String newRunState, String oldState) {
      this._postSet("State", oldState, newRunState);
   }

   private void checkCurrentPartitionState(PartitionRuntimeMBean.State newState) throws PartitionLifeCycleException {
      switch (newState) {
         case ADMIN:
            if (this.getInternalState() != null && !State.isRunning(this.getInternalState())) {
               PartitionRuntimeMBean.State var10000 = this.getInternalState();
               PartitionRuntimeMBean.State var10001 = this.state;
               if (var10000 != State.SUSPENDING) {
                  throw new PartitionLifeCycleException("Partition is not in " + State.runningState().name() + " state to transition to " + newState);
               }
            }
         default:
      }
   }

   private boolean isSameRGstate(String resourceGroupName, ResourceGroupLifecycleOperations.RGState newState) throws ResourceGroupLifecycleException {
      this.lookupResourceGroup(resourceGroupName);
      ResourceGroupLifecycleOperations.RGState rgState = this.getInternalRgState(resourceGroupName);
      if (rgState != null && newState == rgState) {
         return true;
      } else {
         return rgState == null && newState == RGState.SHUTDOWN;
      }
   }

   public void unregister() throws ManagementException {
      super.unregister();
   }

   private synchronized void removePartitionRuntimeMBean() throws ManagementException {
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
      ServerRuntimeMBean serverRuntime = runtime.getServerRuntime();
      if (serverRuntime.lookupPartitionRuntime(this.partitionName) != null) {
         serverRuntime.removePartitionRuntime(this);
      }

      runtime.removeRegistrationHandler(this.registrationHandler);
      this.unregister();
   }

   private ResourceGroupMBean lookupResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      PartitionMBean pMBean = this.getDomain().lookupPartition(this.name);
      if (pMBean != null) {
         ResourceGroupMBean rg = pMBean.lookupResourceGroup(resourceGroupName);
         if (rg == null) {
            throw new ResourceGroupLifecycleException("Partition  " + this.name + " does not contain resource group with name " + resourceGroupName);
         } else {
            return rg;
         }
      } else {
         throw new ResourceGroupLifecycleException("Partition  " + this.name + " does not contain resource group with name " + resourceGroupName);
      }
   }

   private boolean isAdminRG(String resourceGroupName) {
      ResourceGroupMBean rg = null;

      try {
         rg = this.lookupResourceGroup(resourceGroupName);
      } catch (ResourceGroupLifecycleException var4) {
      }

      return rg != null && rg.isAdministrative();
   }

   public String getServerName() {
      return ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getName();
   }

   private DomainMBean getDomain() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   private void doOperation(PartitionRuntimeMBean.Operation operation) throws PartitionLifeCycleException {
      this.doOperation(operation, 0, true, false);
   }

   private void doOperation(PartitionRuntimeMBean.Operation operation, int timeout, boolean ignoreSessions) throws PartitionLifeCycleException {
      this.doOperation(operation, timeout, ignoreSessions, false);
   }

   private void doOperation(PartitionRuntimeMBean.Operation operation, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws PartitionLifeCycleException {
      try {
         if (this.partitionOpInProgress == null || this.partitionOpInProgress.isAllowedOp(operation)) {
            if (this.subState == State.getLowestState(operation)) {
               PartitionLifecycleLogger.logPartitionAlreadyInState(this.partitionName, this.state.name());
               return;
            }

            if (!operation.isValidForState(this.state)) {
               PartitionLifecycleLogger.logPartitionOpIncompatible(this.partitionName, operation.name(), this.state.name());
               return;
            }

            this.checkCurrentPartitionState(operation.nextSuccessState);
            this.partitionOpInProgress = operation;
            PartitionLifecycleLogger.logPartitionOperationInitiated(operation.name(), this.partitionName);
            this.setStates(operation.nextState, operation.nextState);
            boolean isShutdownConvertedToHalt = false;
            PartitionRuntimeMBean.Operation effectiveOperation = operation;
            switch (operation) {
               case SUSPEND:
                  this.doGracefulPartitionOperation(operation, ignoreSessions, waitForAllSessions, timeout);
                  break;
               case FORCE_SUSPEND:
                  this.partitionManagerService.forceSuspendPartition(this.getName());
                  break;
               case RESUME:
                  this.partitionManagerService.resumePartition(this.getName());
                  break;
               case SHUTDOWN:
                  this.doGracefulPartitionOperation(operation, ignoreSessions, waitForAllSessions, timeout);
                  if (isShutdownConvertedToHalt = this.shouldTreatShutdownAsHalt()) {
                     effectiveOperation = Operation.HALT;
                  }
                  break;
               case FORCE_SHUTDOWN:
                  if (PartitionLifeCycleModel.SHUTDOWN_TO_HALTED) {
                     debug("Shutdown leads to halt partition, because flag SHUTDOWN_TO_HALTED is true");
                     this.partitionManagerService.haltPartition(this.getName());
                  } else {
                     this.partitionManagerService.forceShutdownPartition(this.getName());
                  }

                  if (isShutdownConvertedToHalt = this.shouldTreatShutdownAsHalt()) {
                     effectiveOperation = Operation.HALT;
                  }
                  break;
               case HALT:
                  this.partitionManagerService.haltPartition(this.getName());
            }

            this.setStates(effectiveOperation.nextSuccessState, effectiveOperation.successSubState);
            debug("Setting the partition lifecycle operation state :" + effectiveOperation.nextSuccessState);
            if (isShutdownConvertedToHalt) {
               PartitionLifecycleLogger.logPartitionOperationAutoHalt(operation.name(), this.partitionName);
            }

            if (operation == Operation.HALT || isShutdownConvertedToHalt) {
               this.removePartitionRuntimeMBean();
            }

            PartitionLifecycleLogger.logPartitionOperationComplete(operation.name(), this.partitionName);
            return;
         }

         PartitionLifecycleLogger.logPartitionOpInProgress(this.partitionName, this.partitionOpInProgress.name());
      } catch (Throwable var10) {
         PartitionLifeCycleException pe = new PartitionLifeCycleException(var10);
         PartitionLifecycleLogger.logPartitionOperationException(operation.name(), this.partitionName, pe);
         this.setStates(State.UNKNOWN, State.UNKNOWN);
         throw pe;
      } finally {
         this.partitionOpInProgress = null;
      }

   }

   private void doGracefulPartitionOperation(PartitionRuntimeMBean.Operation operation, boolean ignoreSessions, boolean waitForAllSessions, int timeout) throws Throwable {
      GracefulRequest gracefulShutdownRequest = new PartitionGracefulRequest(operation, ignoreSessions, waitForAllSessions, timeout);
      WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(gracefulShutdownRequest));
      if (PartitionLifeCycleModel.SHUTDOWN_TO_HALTED) {
         PartitionLifecycleLogger.logPartitionOperationShutdownToHaltedFlagIsTrue(operation.name(), this.getName());
      }

      gracefulShutdownRequest.waitForCompletion(timeout * 1000);
      if (gracefulShutdownRequest.getException() != null) {
         throw gracefulShutdownRequest.getException();
      } else {
         if (!gracefulShutdownRequest.isCompleted()) {
            if (operation == Operation.SUSPEND) {
               PartitionLifecycleLogger.logGracefulPartitionOperationTimedOut(operation.name(), this.getName(), Operation.FORCE_SUSPEND.name());
               this.forceSuspend();
            } else if (operation == Operation.SHUTDOWN) {
               PartitionLifecycleLogger.logGracefulPartitionOperationTimedOut(operation.name(), this.getName(), Operation.FORCE_SHUTDOWN.name());
               this.forceShutdown();
            }
         } else if (operation == Operation.SHUTDOWN && PartitionLifeCycleModel.SHUTDOWN_TO_HALTED) {
            debug("Graceful shutdown operation is completed. Since flag SHUTDOWN_TO_HALTED is true, invoking forceShutdown, which will be treated as halt with the flag on.");
            this.forceShutdown();
         }

      }
   }

   private void doGracefulRGOperation(ResourceGroupLifecycleOperations.RGOperation rGoperation, String resourceGroupName, boolean ignoreSessions, boolean waitForAllSessions, int timeout) throws Throwable {
      GracefulRequest gracefulShutdownRequest = new RGGracefulRequest(rGoperation, this.partitionName, resourceGroupName, ignoreSessions, waitForAllSessions, timeout);
      WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(gracefulShutdownRequest));
      gracefulShutdownRequest.waitForCompletion(timeout * 1000);
      if (gracefulShutdownRequest.getException() != null) {
         throw gracefulShutdownRequest.getException();
      } else {
         if (!gracefulShutdownRequest.isCompleted()) {
            if (rGoperation == RGOperation.SUSPEND) {
               this.forceSuspendResourceGroup(resourceGroupName);
            } else {
               if (rGoperation != RGOperation.SHUTDOWN) {
                  throw new IllegalArgumentException("Unexpected graceful operation " + rGoperation.toString() + " timed out.");
               }

               this.forceShutdownResourceGroup(resourceGroupName);
            }
         }

      }
   }

   private void doRGOperation(ResourceGroupLifecycleOperations.RGOperation rGoperation, String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doRGOperation(rGoperation, resourceGroupName, 0, true, false);
   }

   private void doRGOperation(ResourceGroupLifecycleOperations.RGOperation rGoperation, String resourceGroupName, int timeOut, boolean ignoreSessions) throws ResourceGroupLifecycleException {
      this.doRGOperation(rGoperation, resourceGroupName, timeOut, ignoreSessions, false);
   }

   private void doRGOperation(ResourceGroupLifecycleOperations.RGOperation rGoperation, String resourceGroupName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws ResourceGroupLifecycleException {
      this.doRGOperation(rGoperation, resourceGroupName, timeout, ignoreSessions, waitForAllSessions, false);
   }

   private void doRGOperation(ResourceGroupLifecycleOperations.RGOperation rGoperation, String resourceGroupName, int timeout, boolean ignoreSessions, boolean waitForAllSessions, boolean isInternal) throws ResourceGroupLifecycleException {
      try {
         if (this.partitionOpInProgress != null && !isInternal) {
            PartitionLifecycleLogger.logPartitionOpInProgress(this.partitionName, this.partitionOpInProgress.name());
            return;
         }

         if (this.rgOpInProgress.containsKey(resourceGroupName) && !((ResourceGroupLifecycleOperations.RGOperation)this.rgOpInProgress.get(resourceGroupName)).isAllowedOp(rGoperation)) {
            PartitionLifecycleLogger.logResourceGroupOpInProgress(resourceGroupName, this.partitionName, ((ResourceGroupLifecycleOperations.RGOperation)this.rgOpInProgress.get(resourceGroupName)).name());
            return;
         }

         if (this.isSameRGstate(resourceGroupName, rGoperation.nextSuccessRGState)) {
            PartitionLifecycleLogger.logResourceGroupAlreadyInState(resourceGroupName, this.partitionName, rGoperation.nextSuccessRGState.name());
         }

         if (rGoperation.isValidForState(this.getInternalRgState(resourceGroupName))) {
            if (!this.isPartitionStateValid(State.valueOf(rGoperation.nextSuccessRGState.name()), resourceGroupName)) {
               PartitionLifecycleLogger.partitionStateNotAllowed(this.partitionName, resourceGroupName, this.getState(), rGoperation.nextSuccessRGState.name());
               return;
            }

            if (this.getInternalRgState(resourceGroupName) == RGState.ADMIN && RGOperation.START == rGoperation) {
               rGoperation = RGOperation.RESUME;
               PartitionLifecycleLogger.logStartPartitionResourceGroupConvertedToResume(this.partitionName, resourceGroupName);
            }

            this.rgOpInProgress.put(resourceGroupName, rGoperation);
            PartitionLifecycleLogger.logResourceGroupOperationInitiated(rGoperation.name(), this.partitionName, resourceGroupName);
            this.setRgState(resourceGroupName, rGoperation.nextRGState);
            switch (rGoperation) {
               case START:
                  this.partitionManagerService.startResourceGroup(this.getName(), resourceGroupName);
                  break;
               case ADMIN:
                  this.partitionManagerService.startResourceGroupInAdmin(this.getName(), resourceGroupName);
                  break;
               case SUSPEND:
                  this.doGracefulRGOperation(rGoperation, resourceGroupName, ignoreSessions, waitForAllSessions, timeout);
                  break;
               case FORCE_SUSPEND:
                  this.partitionManagerService.forceSuspendResourceGroup(this.getName(), resourceGroupName);
                  break;
               case RESUME:
                  this.partitionManagerService.resumeResourceGroup(this.getName(), resourceGroupName);
                  break;
               case SHUTDOWN:
                  this.doGracefulRGOperation(rGoperation, resourceGroupName, ignoreSessions, waitForAllSessions, timeout);
                  break;
               case FORCE_SHUTDOWN:
                  this.partitionManagerService.forceShutdownResourceGroup(this.getName(), resourceGroupName);
            }

            this.setRgState(resourceGroupName, rGoperation.nextSuccessRGState);
            debug("Setting the partition's resource group lifecycle operation state :" + rGoperation.nextSuccessRGState);
            PartitionLifecycleLogger.logResourceGroupOperationComplete(rGoperation.name(), this.partitionName, resourceGroupName);
            return;
         }

         PartitionLifecycleLogger.logResourceGroupOpIncompatible(resourceGroupName, this.partitionName, rGoperation.name(), this.getInternalRgState(resourceGroupName).name());
      } catch (Throwable var12) {
         debug("Exception while resource group lifecycle operation:" + rGoperation + var12.getMessage());
         ResourceGroupLifecycleException re = new ResourceGroupLifecycleException(var12);
         PartitionLifecycleLogger.logResourceGroupOperationException(rGoperation.name(), this.partitionName, resourceGroupName, re);
         this.setRgState(resourceGroupName, RGState.UNKNOWN);
         throw re;
      } finally {
         this.rgOpInProgress.remove(resourceGroupName);
      }

   }

   private boolean shouldTreatShutdownAsHalt() {
      if (PartitionLifeCycleModel.SHUTDOWN_TO_HALTED) {
         debug("Shutdown leads to halt partition because flag SHUTDOWN_TO_HALTED is true");
         return true;
      } else {
         return !PartitionUtils.isAdminRelatedActionNeeded(this.partitionName, ManagementService.getRuntimeAccess(kernelId).getServerRuntime());
      }
   }

   private boolean isPartitionStateValid(PartitionRuntimeMBean.State newState, String resourceGroupName) throws ResourceGroupLifecycleException {
      switch (newState) {
         case RUNNING:
            if (!this.isAdminRG(resourceGroupName) && !State.isRunning(this.getInternalState())) {
               return false;
            } else if (this.isAdminRG(resourceGroupName) && !this.adminRGValidPartitionStates()) {
               return false;
            }
         case ADMIN:
            if (!this.isAdminRG(resourceGroupName) && !State.isRunning(this.getInternalState()) && !State.isAdmin(this.getInternalState())) {
               return false;
            } else if (this.isAdminRG(resourceGroupName) && !this.adminRGValidPartitionStates()) {
               return false;
            }
         default:
            return true;
      }
   }

   private boolean adminRGValidPartitionStates() {
      return State.isShutdownBooted(this.getInternalSubState()) || State.isRunning(this.getInternalState()) || State.isAdmin(this.getInternalState());
   }

   public void setWseeClusterFrontEndRuntime(WseeClusterFrontEndRuntimeMBean mbean) {
      this.wseeFrontEndRuntime = mbean;
   }

   public WseeClusterFrontEndRuntimeMBean getWseeClusterFrontEndRuntime() {
      return this.wseeFrontEndRuntime;
   }

   private static void debug(String debugMessage) {
      if (PartitionLifecycleDebugger.isDebugEnabled()) {
         PartitionLifecycleDebugger.debug("<PartitionRuntimeMBeanImpl> " + debugMessage);
      }

   }

   public void setRestartRequired(boolean restartRequired) {
      this.partitionRestartRequired = restartRequired;
   }

   public boolean isRestartRequired() {
      return this.partitionRestartRequired;
   }

   public boolean addPendingRestartResourceMBean(ConfigurationMBean resource) {
      boolean status = this.pendingRestartResources.add(resource);
      if (this.pendingRestartResources.size() > 0) {
         this.partitionRestartRequired = true;
      }

      return status;
   }

   public boolean removePendingRestartResourceMBean(ConfigurationMBean resource) {
      boolean status = this.pendingRestartResources.remove(resource);
      if (this.pendingRestartResources.size() == 0) {
         this.partitionRestartRequired = false;
      }

      return status;
   }

   public boolean isRestartPendingForResourceMBean(ConfigurationMBean resource) {
      return this.pendingRestartResources.contains(resource);
   }

   public ConfigurationMBean[] getPendingRestartResourceMBeans() {
      return (ConfigurationMBean[])((ConfigurationMBean[])this.pendingRestartResources.toArray(new ConfigurationMBean[this.pendingRestartResources.size()]));
   }

   public boolean removePendingRestartResource(String resource) {
      Iterator var2 = this.pendingRestartResources.iterator();

      ConfigurationMBean resourceMBean;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         resourceMBean = (ConfigurationMBean)var2.next();
      } while(!resource.equals(resourceMBean.getName()));

      boolean status = this.pendingRestartResources.remove(resourceMBean);
      if (this.pendingRestartResources.size() == 0) {
         this.partitionRestartRequired = false;
      }

      return status;
   }

   public boolean isRestartPendingForResource(String resource) {
      Iterator var2 = this.pendingRestartResources.iterator();

      ConfigurationMBean resourceMBean;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         resourceMBean = (ConfigurationMBean)var2.next();
      } while(!resource.equals(resourceMBean.getName()));

      return true;
   }

   public String[] getPendingRestartResources() {
      int i = 0;
      String[] restartResources = new String[this.pendingRestartResources.size()];

      ConfigurationMBean resourceMBean;
      for(Iterator var3 = this.pendingRestartResources.iterator(); var3.hasNext(); restartResources[i++] = resourceMBean.getName()) {
         resourceMBean = (ConfigurationMBean)var3.next();
      }

      return restartResources;
   }

   public boolean addPendingRestartSystemResource(String resource) {
      return this.pendingRestartSystemResources.add(resource);
   }

   public boolean removePendingRestartSystemResource(String resource) {
      return this.pendingRestartSystemResources.remove(resource);
   }

   public boolean isRestartPendingForSystemResource(String resource) {
      return this.pendingRestartSystemResources.contains(resource);
   }

   public String[] getPendingRestartSystemResources() {
      return (String[])((String[])this.pendingRestartSystemResources.toArray(new String[this.pendingRestartSystemResources.size()]));
   }

   public String[] urlMappingForVT(String vtName, String protocol) {
      return PartitionUtils.urlMappingForVT(this.partitionName, vtName, protocol);
   }

   public String getSystemFileSystemRoot() {
      return PartitionUtils.resolveSystemFileSystemRoot(this.partitionName);
   }

   public String getUserFileSystemRoot() {
      return PartitionUtils.resolveUserFileSystemRoot(this.partitionName);
   }

   private class PartitionGracefulRequest extends GracefulRequest {
      PartitionGracefulRequest(PartitionRuntimeMBean.Operation operation, boolean ignoreSessions, boolean waitForAllSessions, int timeout) {
         super(operation, ignoreSessions, waitForAllSessions, timeout);
      }

      protected boolean isNextSuccessStateShutdown() {
         return ((PartitionRuntimeMBean.Operation)this.operation).nextSuccessState == State.SHUTDOWN;
      }

      protected boolean isNextSuccessStateSuspend() {
         return ((PartitionRuntimeMBean.Operation)this.operation).nextSuccessState == State.ADMIN;
      }

      protected void execSuspend() {
         PartitionRuntimeMBeanImpl.this.partitionManagerService.suspendPartition(PartitionRuntimeMBeanImpl.this.getName(), this.timeout, this.ignoreSessions);
      }

      protected void execShutdown() {
         PartitionRuntimeMBeanImpl.this.partitionManagerService.shutdownPartition(PartitionRuntimeMBeanImpl.this.getName(), this.timeout, this.ignoreSessions, this.waitForAllSessions);
      }
   }
}
