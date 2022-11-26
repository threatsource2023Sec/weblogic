package weblogic.cluster.migration.management;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.cluster.migration.MigrationManagerService;
import weblogic.cluster.migration.RemoteMigratableServiceCoordinator;
import weblogic.cluster.migration.RemoteMigrationControl;
import weblogic.cluster.singleton.SingletonMonitorRemote;
import weblogic.jndi.Environment;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.internal.ManagementTextTextFormatter;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.runtime.MigrationException;
import weblogic.management.runtime.MigrationTaskRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;
import weblogic.work.Work;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public class MigrationTask extends RuntimeMBeanDelegate implements MigrationTaskRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final int SECONDS_TO_WAIT_FOR_MIGRATION = 30;
   private static int num = 0;
   private MigratableTargetMBean mt;
   private ServerMBean destination;
   private boolean jta;
   private int status = 0;
   private Exception ex;
   private long startTime;
   private long endTime;
   private StringBuffer log;
   private boolean isSourceServerDown;
   private boolean isDestinationServerDown;
   private String[] statusTextLUT = null;
   private boolean sysTask = false;
   private final MigratableServiceCoordinatorRuntime runtime;
   private ServerMBean currentServer;
   private final ManagementTextTextFormatter texter = ManagementTextTextFormatter.getInstance();
   private final String currentServerURL;
   private final String destinationServerURL;
   private final String currentServerName;
   private final String destinationServerName;

   public MigrationTask(MigratableTargetMBean mt, ServerMBean destination, boolean jta, boolean sourceDown, boolean destinationDown, MigratableServiceCoordinatorRuntime runtime) throws ManagementException {
      super(ManagementTextTextFormatter.getInstance().getMigrationTaskTitle(mt != null ? mt.getName() : "(null)", destination != null ? destination.getName() : "(null)", jta ? "JTA " : "") + "-" + num++, runtime);
      Debug.assertion(mt != null);
      this.mt = mt;
      this.destination = destination;
      this.jta = jta;
      this.isSourceServerDown = sourceDown || jta;
      this.isDestinationServerDown = destinationDown;
      this.log = new StringBuffer(4096);
      this.runtime = runtime;
      if (jta) {
         try {
            DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
            String url = getURLManagerService().findAdministrationURL(domainMBean.getAdminServerName());
            RemoteMigratableServiceCoordinator remoteMigratableServiceCoordinator = this.getServiceCoordinator(url);
            String storedCurrentServerName = remoteMigratableServiceCoordinator.getCurrentLocationOfJTA(mt.getName());
            this.currentServer = domainMBean.lookupServer(storedCurrentServerName);
            if (this.currentServer == null) {
               this.currentServer = mt.getUserPreferredServer();
            }

            Debug.assertion(this.currentServer != null);
            this.currentServerURL = this.getServerAdminURL(this.currentServer.getName());
            this.currentServerName = this.currentServer.getName();
         } catch (Exception var11) {
            throw new ManagementException(var11);
         }
      } else {
         if (this.isTargetAutoMigratable(mt)) {
            this.currentServer = mt.getHostingServer();
            if (this.currentServer == null) {
               this.isSourceServerDown = true;
            }
         } else {
            this.currentServer = mt.getUserPreferredServer();
            Debug.assertion(this.currentServer != null);
         }

         if (this.currentServer != null) {
            this.currentServerURL = this.getServerAdminURL(this.currentServer.getName());
            this.currentServerName = this.currentServer.getName();
         } else {
            this.currentServerURL = null;
            this.currentServerName = null;
         }

         if (this.currentServerURL == null) {
            this.isSourceServerDown = true;
         }
      }

      this.destinationServerURL = this.getServerAdminURL(destination.getName());
      this.destinationServerName = destination.getName();
      this.statusTextLUT = new String[]{ManagementTextTextFormatter.getInstance().getMigrationTaskStatusInProgress(), ManagementTextTextFormatter.getInstance().getMigrationTaskStatusDone(), ManagementTextTextFormatter.getInstance().getMigrationTaskStatusFailed(), ManagementTextTextFormatter.getInstance().getMigrationTaskStatusQIsTheSourceServerDown(), ManagementTextTextFormatter.getInstance().getMigrationTaskStatusQIsTheDestinationServerDown(), ManagementTextTextFormatter.getInstance().getMigrationTaskStatusCanceled()};
   }

   public boolean isSystemTask() {
      return this.sysTask;
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private RemoteMigratableServiceCoordinator getServiceCoordinator(String url) throws MigrationException {
      Context ctx = null;

      try {
         Environment env = new Environment();
         env.setProviderUrl(url);
         ctx = env.getInitialContext();
         return (RemoteMigratableServiceCoordinator)ctx.lookup("weblogic.cluster.migration.migratableServiceCoordinator");
      } catch (NamingException var4) {
         throw new MigrationException("Unexpected naming exception", var4);
      }
   }

   public String getDescription() {
      return ManagementTextTextFormatter.getInstance().getMigrationTaskTitle(this.mt != null ? this.mt.getName() : "(null)", this.destination != null ? this.destinationServerName : "(null)", this.jta ? "JTA " : "");
   }

   public String getStatus() {
      return this.statusTextLUT[this.status];
   }

   public String getProgress() {
      if (!this.isTerminal()) {
         return "processing";
      } else {
         return this.status == 1 ? "success" : "failed";
      }
   }

   public int getStatusCode() {
      return this.status;
   }

   public synchronized boolean isRunning() {
      return this.status == 0;
   }

   public synchronized boolean isTerminal() {
      return this.status == 5 || this.status == 2 || this.status == 1;
   }

   public boolean isWaitingForUser() {
      return this.status == 3 || this.status == 4;
   }

   public long getBeginTime() {
      return this.startTime;
   }

   public long getEndTime() {
      return this.endTime;
   }

   public synchronized Exception getError() {
      return this.ex;
   }

   public ServerMBean getSourceServer() {
      return this.mt.getUserPreferredServer();
   }

   public ServerMBean getDestinationServer() {
      return this.destination;
   }

   public MigratableTargetMBean getMigratableTarget() {
      return this.mt;
   }

   public boolean isJTA() {
      return this.jta;
   }

   public TaskRuntimeMBean[] getSubTasks() {
      return null;
   }

   public TaskRuntimeMBean getParentTask() {
      return null;
   }

   public void setSystemTask(boolean sys) {
      this.sysTask = sys;
   }

   public void run() {
      Work work = new WorkAdapter() {
         public void run() {
            MigrationTask.this.status = 0;
            MigrationTask.this.startTime = System.currentTimeMillis();
            SecurityServiceManager.runAs(MigrationTask.kernelId, MigrationTask.kernelId, new PrivilegedAction() {
               public Object run() {
                  boolean var23 = false;

                  label189: {
                     label190: {
                        try {
                           var23 = true;
                           synchronized(MigrationManagerService.singleton()) {
                              while(MigrationManagerService.singleton().isMigrating()) {
                                 MigrationManagerService.singleton().wait(1000L);
                              }

                              MigrationManagerService.singleton().setMigrating(true);
                              MigrationTask.this.migrate();
                           }

                           synchronized(MigrationTask.this) {
                              MigrationTask.this.status = 1;
                              var23 = false;
                              break label189;
                           }
                        } catch (RuntimeException var32) {
                           RuntimeException ex = var32;
                           synchronized(MigrationTask.this) {
                              MigrationTask.this.ex = ex;
                              MigrationTask.this.status = 2;
                           }

                           MigrationTask.this.logLine(var32.toString());
                           MigrationTask.this.logLine(StackTraceUtils.throwable2StackTrace(var32));
                           var23 = false;
                        } catch (Exception var33) {
                           Exception e = var33;
                           synchronized(MigrationTask.this) {
                              MigrationTask.this.ex = e;
                              MigrationTask.this.status = 2;
                           }

                           MigrationTask.this.logLine(var33.toString());
                           MigrationTask.this.logLine(StackTraceUtils.throwable2StackTrace(var33));
                           var23 = false;
                           break label190;
                        } finally {
                           if (var23) {
                              MigrationTask.this.endTime = System.currentTimeMillis();
                              synchronized(MigrationManagerService.singleton()) {
                                 MigrationManagerService.singleton().setMigrating(false);
                              }
                           }
                        }

                        MigrationTask.this.endTime = System.currentTimeMillis();
                        synchronized(MigrationManagerService.singleton()) {
                           MigrationManagerService.singleton().setMigrating(false);
                           return null;
                        }
                     }

                     MigrationTask.this.endTime = System.currentTimeMillis();
                     synchronized(MigrationManagerService.singleton()) {
                        MigrationManagerService.singleton().setMigrating(false);
                        return null;
                     }
                  }

                  MigrationTask.this.endTime = System.currentTimeMillis();
                  synchronized(MigrationManagerService.singleton()) {
                     MigrationManagerService.singleton().setMigrating(false);
                  }

                  return null;
               }
            });
         }

         public String toString() {
            return MigrationTask.this.getDescription();
         }
      };
      WorkManagerFactory.getInstance().getSystem().schedule(work);
   }

   public synchronized void cancel() throws Exception {
      if (this.status != 3 && this.status != 4) {
         throw new Exception(ManagementTextTextFormatter.getInstance().getMigrationTaskCannotCancelHere());
      } else {
         this.status = 5;
         this.notifyAll();
      }
   }

   public void printLog(PrintWriter out) {
      out.print(this.log.toString());
      out.flush();
   }

   public synchronized void continueWithSourceServerDown(boolean isSourceServerDown) {
      Debug.assertion(this.status == 3);
      this.isSourceServerDown = isSourceServerDown || this.jta;
      this.status = 0;
      this.notifyAll();
   }

   public synchronized void continueWithDestinationServerDown(boolean isDestinationServerDown) {
      Debug.assertion(this.status == 4);
      this.isDestinationServerDown = isDestinationServerDown;
      this.status = 0;
      this.notifyAll();
   }

   private void validateConfiguration() throws MigrationException {
      this.check(this.mt.getAllCandidateServers().length > 0, this.texter.getMigrationTaskErrorCandidateServerMustNotBeEmpty());
      if (!this.jta) {
         if (this.isTargetAutoMigratable(this.mt)) {
            if (this.currentServer != null) {
               this.check(!this.currentServer.getName().equals(this.destination.getName()), this.texter.getMigrationTaskErrorDestinationMustNotBeCurrentlyActiveServer());
            }
         } else {
            this.check(!this.mt.isManualActiveOn(this.destination), this.texter.getMigrationTaskErrorDestinationMustNotBeCurrentlyActiveServer());
         }
      }

      this.check(this.isDestinationServerMemberOfCandidateServers(this.destinationServerName), this.texter.getMigrationTaskErrorDestinationMustBeMemberOfCandidiateServers());
   }

   private boolean isDestinationServerMemberOfCandidateServers(String name) {
      ServerMBean[] candidates = this.mt.getAllCandidateServers();

      for(int i = 0; i < candidates.length; ++i) {
         if (candidates[i].getName().equals(name)) {
            return true;
         }
      }

      return false;
   }

   private String getServerAdminURL(String name) {
      try {
         return getURLManagerService().findAdministrationURL(name);
      } catch (UnknownHostException var3) {
         return null;
      }
   }

   private void migrate() throws MigrationException {
      try {
         this.validateConfiguration();
         if (this.jta) {
            this.logLine(this.texter.getMigrationTaskLoglineJTAMigrationStarted(this.mt.getName(), this.destinationServerName));
         } else {
            this.logLine(this.texter.getMigrationTaskLoglineMigrationStarted(this.mt.getName(), this.destinationServerName));
         }

         this.logLine("Destination URL " + this.destinationServerURL + "\t" + this.isDestinationServerDown);
         this.logLine("Source URL " + this.currentServerURL + "\t" + this.isSourceServerDown);
         if (this.destinationServerURL == null && !this.isDestinationServerDown) {
            this.failWith(this.texter.getMigrationTaskUserStopDestinationNotReachable(this.destinationServerName));
         }

         if (this.currentServerURL == null && !this.isSourceServerDown) {
            this.failWith(this.texter.getMigrationTaskLoglineUnableToConnectToCurrentServer(this.currentServerName));
         }

         if (this.jta) {
            this.checkIfRunningServerTlogIsBeingMigrated(this.currentServerURL);
         }

         if (this.isTargetAutoMigratable(this.mt)) {
            ClusterMBean clusterMBean = this.mt.getCluster();
            SingletonMonitorRemote smr = this.getSingletonMonitor(clusterMBean);
            if (smr == null) {
               throw new MigrationException("Cluster still not started. Please try after sometime.");
            }

            smr.migrate(this.mt.getName(), this.destinationServerName);
            if (!this.mt.getUserPreferredServer().getName().equals(this.destinationServerName)) {
               this.runtime.changeMigratableTargetsConfiguration(this.mt.getName(), this.destinationServerName);
            }
         } else {
            this.deactivateMigratableTarget();
            this.activateMigratableTarget();
            if (!this.jta) {
               this.runtime.changeMigratableTargetsConfiguration(this.mt.getName(), this.destinationServerName);
            } else {
               this.runtime.updateState(this.mt.getName(), this.destination.getName());
            }
         }

         this.logLine(this.texter.getActivationSucceeded());
      } catch (Exception var3) {
         this.failWith(var3.toString());
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else if (var3 instanceof MigrationException) {
            throw (MigrationException)var3;
         } else {
            throw new MigrationException(var3);
         }
      }
   }

   private void deactivateMigratableTarget() throws MigrationException {
      if (this.currentServerURL == null) {
         Debug.say("Cannot reach " + this.currentServerName + " to deactivate MT " + this.mt.getName());
      }

      if (this.currentServerURL != null) {
         Environment env = new Environment();
         Context ctx = null;
         RemoteMigrationControl control = null;

         try {
            env.setProviderUrl(this.currentServerURL);
            this.logLine(this.texter.getMigrationTaskLoglineTryingToConnectToCurrentServer(this.currentServerURL));
            ctx = env.getInitialContext();
            control = (RemoteMigrationControl)ctx.lookup("weblogic.cluster.migrationControl");
            this.logLine(this.texter.getMigrationTaskLoglineConnectedSuccessfulyToCurrentServer(this.currentServerURL));
            this.logLine(this.texter.getTryingToDeactivateMigratableTarget(this.mt.getName()));
            control.deactivateTarget(this.mt.getName(), this.destinationServerName);
            this.logLine(this.texter.getDeactivationSucceeded());
         } catch (NamingException var15) {
            String s = this.texter.getMigrationTaskLoglineUnableToConnectToCurrentServer(this.currentServerURL);
            this.logLine(s);
            throw new MigrationException(s);
         } catch (weblogic.cluster.migration.MigrationException var16) {
            this.logLine(this.texter.getServiceNotDeactivatedOnCurrentHostingServer(this.currentServerName));
            throw new MigrationException(this.texter.getServiceWasNotDeactivatedOnCurrentHostingServer(this.currentServerName, var16.toString()));
         } catch (RemoteException var17) {
            this.logLine(this.texter.getLostConnectionToCurrentHostingServerDeactivation(this.currentServerName));
            throw new MigrationException(this.texter.getLostConnectionToCurrentHostingServerDeactivationEx(this.currentServerName, var17.toString()));
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var14) {
               }
            }

         }
      } else if (!this.isSourceServerDown) {
         throw new MigrationException(this.texter.getServiceWasNotDeactivatedOnCurrentHostingServer(this.currentServerName, "Current server is unreachable"));
      }

   }

   private void activateMigratableTarget() throws MigrationException {
      if (this.destinationServerURL == null) {
         Debug.say("Cannot reach " + this.destinationServerName + " to activate MT " + this.mt.getName());
      }

      if (this.destinationServerURL != null) {
         Environment env = new Environment();
         Context ctx = null;
         RemoteMigrationControl control = null;

         try {
            env.setProviderUrl(this.destinationServerURL);
            this.logLine(this.texter.getTyingToConnectDestinationServer(this.destinationServerURL));
            ctx = env.getInitialContext();
            this.logLine(this.texter.getConnectedSuccessfulyToDestinationServer(this.destinationServerURL));
            control = (RemoteMigrationControl)ctx.lookup("weblogic.cluster.migrationControl");
            this.logLine(this.texter.getTryingToActivateMigratableTarget(this.mt.getName()));
            control.activateTarget(this.mt.getName());
            EditAccess editAccess = ManagementServiceRestricted.getEditAccess(kernelId);
            boolean isConsole = false;
            if (editAccess.getEditor() != null && !editAccess.isEditorExclusive()) {
               isConsole = true;
            }

            if (!this.isDestinationServerDown && !this.jta && !isConsole) {
               for(int i = 0; i < 30 && control.getMigratableState(this.mt.getName()) == 0; ++i) {
                  try {
                     Thread.sleep(1000L);
                  } catch (InterruptedException var18) {
                  }

                  if (i == 29) {
                     throw new weblogic.cluster.migration.MigrationException("Could not determine state of Migratable services on destination server. Services may not be active.");
                  }
               }
            }
         } catch (NamingException var19) {
            this.logLine(this.texter.getUnableToConnectToDestinationServer(this.destinationServerName, this.destinationServerURL));
            throw new MigrationException(this.texter.getUnableToConnectToDestinationServer(this.destinationServerName, this.destinationServerURL));
         } catch (weblogic.cluster.migration.MigrationException var20) {
            this.logLine(this.texter.getMigratableServiceWasNotActivatedOnDestination(this.destinationServerName));
            throw new MigrationException(this.texter.getMigratableServiceWasNotActivatedOnDestinationEx(this.destinationServerName, var20.toString()));
         } catch (RemoteException var21) {
            this.logLine(this.texter.getLostConnectToDestinationServer(this.destinationServerName));
            throw new MigrationException(this.texter.getLostConnectToDestinationServerEx(this.destinationServerName, var21.toString()));
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var17) {
               }
            }

         }
      } else if (!this.isDestinationServerDown) {
         throw new MigrationException(this.texter.getMigratableServiceWasNotActivatedOnDestination(this.destinationServerName));
      }

   }

   private void checkIfRunningServerTlogIsBeingMigrated(String url) throws MigrationException {
      if (this.currentServerName.equals(this.mt.getName()) && this.currentServerURL != null) {
         this.logLine(this.texter.getMigrationTaskLoglineCannotMigrateTransactionRecoveryService(this.currentServerName, url));
         throw new MigrationException(this.texter.getMigrationTaskLoglineMigrationTaskLoglineCannotMigrateTransactionRecoveryServiceForTheCurrentServiceHost(this.currentServerName));
      }
   }

   private void check(boolean t, String s) throws MigrationException {
      if (!t) {
         this.failWith(s);
      }

   }

   private void failWith(String s) throws MigrationException {
      this.logLine(s);
      this.status = 2;
      throw new MigrationException(s);
   }

   private void logLine(String s) {
      ManagementLogger.logMigrationTaskProgressInfo(this.getDescription(), s);
      this.log.append(s + "\n");
   }

   private boolean isTargetAutoMigratable(MigratableTargetMBean target) {
      return !target.getMigrationPolicy().equals("manual");
   }

   private SingletonMonitorRemote getSingletonMonitor(ClusterMBean cluster) {
      ServerMBean[] servers = cluster.getServers();
      SingletonMonitorRemote smr = null;

      for(int i = 0; i < servers.length; ++i) {
         smr = this.getSingletonMonitorRemote(servers[i].getName());
         if (smr != null) {
            return smr;
         }
      }

      return smr;
   }

   private SingletonMonitorRemote getSingletonMonitorRemote(String server) {
      Environment env = new Environment();
      Context ctx = null;

      SingletonMonitorRemote var5;
      try {
         String url = getURLManagerService().findAdministrationURL(server);
         if (url != null) {
            env.setProviderUrl(url);
            ctx = env.getInitialContext();
            var5 = (SingletonMonitorRemote)ctx.lookup("weblogic/cluster/singleton/SingletonMonitorRemote");
            return var5;
         }

         var5 = null;
      } catch (NamingException var18) {
         var5 = null;
         return var5;
      } catch (UnknownHostException var19) {
         var5 = null;
         return var5;
      } finally {
         if (ctx != null) {
            try {
               ctx.close();
            } catch (NamingException var17) {
            }
         }

      }

      return var5;
   }
}
