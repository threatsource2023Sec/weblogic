package weblogic.nodemanager;

import com.bea.logging.LogFileConfigBean;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.LogFileConfigUtil;
import weblogic.management.ManagementException;
import weblogic.management.NodeManagerRuntimeService;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LogMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.provider.DomainAccessSettable;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.NodeManagerRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.nodemanager.common.StateInfo;
import weblogic.nodemanager.mbean.NodeManagerAggregateProgressListener;
import weblogic.nodemanager.mbean.NodeManagerRuntime;
import weblogic.nodemanager.mbean.NodeManagerRuntimeMBeanImpl;
import weblogic.nodemanager.server.DomainDir;
import weblogic.nodemanager.server.ServerDir;
import weblogic.nodemanager.util.ConcurrentFile;
import weblogic.nodemanager.util.ProcessControl;
import weblogic.nodemanager.util.ProcessControlFactory;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.t3.srvr.T3Srvr;
import weblogic.t3.srvr.WebLogicServer;
import weblogic.utils.Debug;
import weblogic.utils.LocatorUtilities;

@Service
@Named
@RunLevel(10)
public class NMService extends AbstractServerService implements NodeManagerRuntimeService {
   static final String WEBLOGIC_STDOUT_PROPERTY_NAME = "weblogic.Stdout";
   @Inject
   @Named("CDIIntegrationService")
   private ServerService depdendency;
   @Inject
   @Named("DomainAccessService")
   private ServerService domainAccessService;
   private static NMService instance;
   private boolean started;
   private String startupMode;
   private String srvrURL;
   private StateInfo stateInfo;
   private ConcurrentFile stateFile;
   private ConcurrentFile pidFile;
   private ConcurrentFile urlFile;
   private ServerRuntimePropertyChangeListener srpcl;
   private ServerStartupPropertyChangeListener sspcl;
   private SecurityConfigPropertyChangeListener scpcl;
   private boolean isAdmin = false;
   private byte[] currentPwd;
   private String ENCODING = "UTF-8";
   private NodeManagerAggregateProgressListener progressListener;
   @Inject
   private RuntimeAccess runtimeAccess;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private final Map nmRuntimes;
   private String stdout = null;

   public NMService() {
      Class var1 = NMService.class;
      synchronized(NMService.class) {
         Debug.assertion(instance == null);
         instance = this;
      }

      this.nmRuntimes = Collections.synchronizedMap(new HashMap());
   }

   public static NMService getInstance() {
      return instance;
   }

   public NodeManagerRuntimeMBean[] getNodeManagerRuntimes() {
      return (NodeManagerRuntimeMBean[])this.nmRuntimes.values().toArray(new NodeManagerRuntimeMBean[this.nmRuntimes.size()]);
   }

   private void initNMRuntimes() {
      DomainMBean domain = this.runtimeAccess.getDomain();
      MachineMBean[] machines = domain.getMachines();
      MachineMBean[] var3 = machines;
      int var4 = machines.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MachineMBean m = var3[var5];

         try {
            NodeManagerRuntimeMBean nmRuntime = new NodeManagerRuntimeMBeanImpl(m.getNodeManager());
            this.nmRuntimes.put(m.getNodeManager().getName(), nmRuntime);
         } catch (ManagementException var8) {
            throw new Error("Unexpected exception creating NodeManagerRuntime", var8);
         }
      }

      domain.addBeanUpdateListener(this.createBeanUpdateListener());
   }

   public synchronized void start() throws ServiceFailureException {
      if (!this.started) {
         this.isAdmin = this.runtimeAccess.isAdminServer();
         if (this.isAdmin) {
            this.initNMRuntimes();
            this.startCredentialListener();
            DomainAccessSettable domainAccessSettable = (DomainAccessSettable)ManagementService.getDomainAccess(kernelId);
            domainAccessSettable.setNodeManagerRuntimeService(this);
         }

         if (Boolean.getBoolean("weblogic.nodemanager.ServiceEnabled")) {
            this.stateInfo = new StateInfo();
            ServerMBean smb = this.runtimeAccess.getServer();
            this.startupMode = smb.getStartupMode();
            ServerDir dir = this.getServerDir();
            this.pidFile = dir.getPidFile();
            this.urlFile = dir.getURLFile();
            this.stateFile = dir.getStateFile();
            this.writeProcessId();
            ServerRuntimeMBean srmb = this.runtimeAccess.getServerRuntime();
            this.srpcl = new ServerRuntimePropertyChangeListener();
            srmb.addPropertyChangeListener(this.srpcl);
            this.sspcl = new ServerStartupPropertyChangeListener();
            smb.getServerStart().addPropertyChangeListener(this.sspcl);
            smb.addPropertyChangeListener(new ServerPropertyChangeListener());
            String rotationEnabled = System.getProperty("weblogic.nmservice.RotationEnabled", Boolean.FALSE.toString());
            if (Boolean.valueOf(rotationEnabled)) {
               this.stdout = System.getProperty("weblogic.Stdout");
               String directory = null;
               if (this.stdout == null) {
                  this.stdout = ServerDir.getOutFile(smb.getName());
               }

               File f = new File(this.stdout);

               try {
                  this.stdout = f.getCanonicalPath();
               } catch (IOException var9) {
               }

               directory = f.getParent();
               this.logFileRotation(smb.getLog(), this.stdout, directory);
            }

            ServiceLocatorUtilities.addClasses(GlobalServiceLocator.getServiceLocator(), new Class[]{NodeManagerAggregateProgressListener.class});
            this.progressListener = (NodeManagerAggregateProgressListener)LocatorUtilities.getService(NodeManagerAggregateProgressListener.class);

            try {
               this.progressListener.initialize(dir.getProgressFile(), this.runtimeAccess.getDomainName(), this.runtimeAccess.getServerName());
            } catch (IOException var8) {
               throw new ServiceFailureException(var8);
            }

            this.started = true;
         }
      }
   }

   private void startCredentialListener() {
      DomainMBean dmb = this.runtimeAccess.getDomain();
      SecurityConfigurationMBean scmb = dmb.getSecurityConfiguration();
      this.scpcl = new SecurityConfigPropertyChangeListener();
      scmb.addPropertyChangeListener(this.scpcl);
      this.currentPwd = scmb.getNodeManagerPasswordEncrypted();
   }

   private boolean writeProcessId() {
      ProcessControl pc = null;

      try {
         pc = ProcessControlFactory.getProcessControl();
      } catch (UnsatisfiedLinkError var4) {
      }

      if (pc == null) {
         NodeManagerLogger.logNativePidSupportUnavailable();
         return false;
      } else {
         try {
            this.pidFile.writeLine(pc.getProcessId());
            return true;
         } catch (IOException var3) {
            NodeManagerLogger.logErrorWritingPidFile(this.pidFile.getPath(), var3);
            return false;
         }
      }
   }

   private boolean writeServerURL() {
      ServerRuntimeMBean srmb = this.runtimeAccess.getServerRuntime();
      this.srvrURL = srmb.getURL("http");
      if (this.srvrURL == null) {
         this.srvrURL = srmb.getURL("https");
      }

      try {
         this.urlFile.writeLine(this.srvrURL);
         return true;
      } catch (IOException var3) {
         NodeManagerLogger.logErrorWritingURLFile(this.urlFile.getPath(), var3);
         return false;
      }
   }

   private ServerDir getServerDir() {
      DomainDir dir = new DomainDir(BootStrap.getRootDirectory());
      String name = this.runtimeAccess.getServerName();
      return dir.getServerDir(name);
   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }

   public synchronized void halt() throws ServiceFailureException {
      if (this.isAdmin) {
         this.stopCredentialListener();
      }

      if (this.started) {
         ServerRuntimeMBean srmb = this.runtimeAccess.getServerRuntime();
         WebLogicServer srvr = T3Srvr.getT3Srvr();
         String state = srvr.getState();
         this.stateInfo.setState(state);
         this.stateInfo.setStarted(srvr.isStarted());
         this.stateInfo.setFailed(srmb.isShuttingDownDueToFailure());
         if (this.progressListener != null) {
            this.progressListener.close();
         }

         this.finishHalting();
      }
   }

   private synchronized void finishHalting() throws ServiceFailureException {
      ServerRuntimeMBean srmb = this.runtimeAccess.getServerRuntime();
      srmb.removePropertyChangeListener(this.srpcl);
      ServerMBean smb = this.runtimeAccess.getServer();
      smb.getServerStart().removePropertyChangeListener(this.sspcl);
      this.writeStateInfo();
      this.pidFile.delete();
      this.started = false;
   }

   private void stopCredentialListener() {
      DomainMBean dmb = this.runtimeAccess.getDomain();
      SecurityConfigurationMBean scmb = dmb.getSecurityConfiguration();
      scmb.removePropertyChangeListener(this.scpcl);
   }

   public void hardShutdown() throws ServiceFailureException {
      if (this.started) {
         ServerRuntimeMBean srmb = this.runtimeAccess.getServerRuntime();
         this.stateInfo.setState("FORCE_SHUTTING_DOWN");
         this.stateInfo.setStarted(true);
         this.stateInfo.setFailed(srmb.isShuttingDownDueToFailure());
         this.finishHalting();
      }
   }

   private void writeStateInfo() {
      try {
         this.stateInfo.save(this.stateFile);
      } catch (IOException var2) {
         NodeManagerLogger.logStateChangeNotificationFailureMsg(var2);
         Runtime.getRuntime().halt(1);
      }

   }

   private void logFileRotation(LogMBean logMBean, String outFile, String rotationDir) {
      final LogFileConfigBean logFileConfig = this.createLogFileConfig(logMBean, outFile, rotationDir);

      try {
         System.loadLibrary("nodemanager");
      } catch (UnsatisfiedLinkError var9) {
         NodeManagerLogger.logErrorRotatingLogFiles(var9);
         return;
      }

      long tmpTimeSpanFactor = logFileConfig.getRotationTimeSpanFactor();
      if (tmpTimeSpanFactor > 2147483647L) {
         NodeManagerLogger.logErrorRotatingLogFiles(new Exception("LogFileRotation TimeSpanFactor is unexpetedly set larger than 2147483647. Try changing TimeSpanFactory and TimeSpan to integer values to reach desired rotation time interval."));
      } else {
         final int timeSpanFactor = Long.valueOf(tmpTimeSpanFactor).intValue();
         Thread t = new Thread() {
            public void run() {
               try {
                  NMService.this.rotateLogFiles0(logFileConfig.getRotationType(), logFileConfig.getRotationTimeSpan(), timeSpanFactor, logFileConfig.getRotationTime(), logFileConfig.isNumberOfFilesLimited() ? logFileConfig.getRotatedFileCount() : -1, logFileConfig.getRotationSize(), logFileConfig.getBaseLogFileName(), logFileConfig.getLogFileRotationDir());
               } catch (UnsatisfiedLinkError var5) {
                  NMService.debugLogger.debug("NMService: rotateLogFiles0 throws UnsatisfiedLinkError: " + var5);
                  boolean failed = false;

                  try {
                     ProcessControl pc = ProcessControlFactory.getProcessControl();
                     if (pc == null) {
                        failed = true;
                     }

                     if (pc != null) {
                        pc.getProcessId();
                     }
                  } catch (UnsatisfiedLinkError var4) {
                     failed = true;
                  }

                  if (failed) {
                     NodeManagerLogger.logErrorRotatingLogFiles(var5);
                  }
               } catch (IOException var6) {
                  NodeManagerLogger.logErrorRotatingLogFiles(var6);
               }

            }
         };
         t.start();
      }
   }

   private native void rotateLogFiles0(String var1, int var2, int var3, String var4, int var5, int var6, String var7, String var8) throws IOException;

   private LogFileConfigBean createLogFileConfig(LogMBean logMBean, String outFile, String rotationDir) {
      LogFileConfigBean logFileConfig = LogFileConfigUtil.getLogFileConfig(logMBean);
      logFileConfig.setBaseLogFileName(outFile);
      if (rotationDir != null) {
         logFileConfig.setLogFileRotationDir(rotationDir);
      }

      Long timeSpanFactorForTesting = Long.getLong("weblogic.nodemanager.RotationTimeSpanFactorForTesting");
      if (timeSpanFactorForTesting != null) {
         logFileConfig.setRotationTimeSpanFactor(timeSpanFactorForTesting);
      }

      return logFileConfig;
   }

   private BeanUpdateListener createBeanUpdateListener() {
      return new BeanUpdateListener() {
         public void prepareUpdate(BeanUpdateEvent event) {
         }

         public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
            BeanUpdateEvent.PropertyUpdate[] var2 = event.getUpdateList();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               BeanUpdateEvent.PropertyUpdate update = var2[var4];
               String propName = update.getPropertyName();
               NodeManagerMBean nmBean;
               switch (update.getUpdateType()) {
                  case 2:
                     if ("Machines".equals(propName)) {
                        nmBean = ((MachineMBean)update.getAddedObject()).getNodeManager();

                        try {
                           NMService.this.nmRuntimes.put(nmBean.getName(), new NodeManagerRuntimeMBeanImpl(nmBean));
                        } catch (ManagementException var9) {
                           throw new Error("Unexpected exception creating NodeManagerRuntime " + nmBean.getName(), var9);
                        }
                     }
                     break;
                  case 3:
                     if ("Machines".equals(propName)) {
                        nmBean = ((MachineMBean)update.getRemovedObject()).getNodeManager();
                        NMService.this.nmRuntimes.remove(nmBean.getName());
                        NodeManagerRuntime.removeDebugLogger(nmBean);
                     }
               }
            }

         }

         public void rollbackUpdate(BeanUpdateEvent event) {
         }
      };
   }

   private class SecurityConfigPropertyChangeListener implements PropertyChangeListener {
      private byte[] oldUser;
      private byte[] oldPwd;

      private SecurityConfigPropertyChangeListener() {
      }

      private synchronized void updateCred(String user, byte[] pwd) {
         DomainMBean dmb = NMService.this.runtimeAccess.getDomain();
         SecurityConfigurationMBean scmb = dmb.getSecurityConfiguration();
         String tryUser = scmb.getNodeManagerUsername();
         if (user != null) {
            tryUser = user;

            try {
               this.oldUser = user.getBytes(NMService.this.ENCODING);
            } catch (UnsupportedEncodingException var18) {
               this.oldPwd = null;
               this.oldUser = null;
               return;
            }
         } else if (this.oldUser != null) {
            try {
               tryUser = new String(this.oldUser, NMService.this.ENCODING);
            } catch (UnsupportedEncodingException var17) {
               this.oldPwd = null;
               this.oldUser = null;
               return;
            }
         }

         String tryPass = scmb.getNodeManagerPassword();
         byte[] tryPwd = null;
         if (pwd != null) {
            tryPwd = NMService.this.currentPwd;
            this.oldPwd = NMService.this.currentPwd;
         } else if (this.oldPwd != null) {
            tryPwd = this.oldPwd;
         }

         if (tryPwd != null) {
            try {
               tryPass = (new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService())).decrypt(new String(tryPwd, NMService.this.ENCODING));
            } catch (UnsupportedEncodingException var16) {
            }
         }

         MachineMBean[] var8 = dmb.getMachines();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            MachineMBean m = var8[var10];
            NodeManagerRuntime nmr = NodeManagerRuntime.getInstance(m);

            try {
               nmr.updateDomainCredentials(tryUser, tryPass);
            } catch (NMException var14) {
               return;
            } catch (IOException var15) {
            }
         }

         this.oldPwd = null;
         this.oldUser = null;
         NMService.this.currentPwd = scmb.getNodeManagerPasswordEncrypted();
      }

      public synchronized void propertyChange(PropertyChangeEvent event) {
         if ("NodeManagerUsername".equals(event.getPropertyName())) {
            this.updateCred((String)event.getOldValue(), (byte[])null);
         }

         if ("NodeManagerPasswordEncrypted".equals(event.getPropertyName())) {
            this.updateCred((String)null, (byte[])((byte[])event.getOldValue()));
         }

      }

      // $FF: synthetic method
      SecurityConfigPropertyChangeListener(Object x1) {
         this();
      }
   }

   private class ServerStartupPropertyChangeListener implements PropertyChangeListener {
      private ServerStartupPropertyChangeListener() {
      }

      public synchronized void propertyChange(PropertyChangeEvent event) {
         if (NMService.this.started) {
            NMService.debugLogger.debug("NMService: ServerStartupPropertyChangeListener.propertyChange: event: " + event);
            ServerMBean smb = NMService.this.runtimeAccess.getServer();
            NodeManagerRuntime nmr = NodeManagerRuntime.getInstance((ServerTemplateMBean)smb);

            try {
               nmr.updateServerProps(smb);
            } catch (IOException var5) {
               NodeManagerLogger.logErrorUpdatingServerProps(smb.getName(), var5);
            }

         }
      }

      // $FF: synthetic method
      ServerStartupPropertyChangeListener(Object x1) {
         this();
      }
   }

   private class ServerRuntimePropertyChangeListener implements PropertyChangeListener {
      private ServerRuntimePropertyChangeListener() {
      }

      public synchronized void propertyChange(PropertyChangeEvent event) {
         if (NMService.this.started) {
            NMService.debugLogger.debug("NMService: ServerRuntimePropertyChangeListener.propertyChange: event: " + event);
            String prop = event.getPropertyName();
            if ("State".equals(prop)) {
               String orig_state = NMService.this.stateInfo.getState();
               boolean orig_isStarted = NMService.this.stateInfo.isStarted();
               boolean orig_isFailed = NMService.this.stateInfo.isFailed();
               String state = (String)event.getNewValue();
               ServerRuntimeMBean srmb = NMService.this.runtimeAccess.getServerRuntime();
               NMService.this.stateInfo.setState(state);
               if (srmb.isStartupAbortedInAdminState()) {
                  NMService.this.stateInfo.setStarted(true);
                  NMService.this.writeServerURL();
                  state = state + "_ON_ABORTED_STARTUP";
                  NMService.this.stateInfo.setState(state);
               } else if (state.equals(NMService.this.startupMode)) {
                  NMService.this.stateInfo.setStarted(true);
                  NMService.this.writeServerURL();
               } else if ("FORCE_SHUTTING_DOWN".equals(state) && srmb.isShuttingDownDueToFailure()) {
                  NMService.this.stateInfo.setFailed(true);
               }

               if (NMService.this.stateInfo.getState().equals(orig_state) && NMService.this.stateInfo.isStarted() == orig_isStarted && NMService.this.stateInfo.isFailed() == orig_isFailed) {
                  return;
               }

               NMService.this.writeStateInfo();
            }

         }
      }

      // $FF: synthetic method
      ServerRuntimePropertyChangeListener(Object x1) {
         this();
      }
   }

   private class ServerPropertyChangeListener implements PropertyChangeListener {
      private ServerPropertyChangeListener() {
      }

      public synchronized void propertyChange(PropertyChangeEvent event) {
         if (NMService.this.started) {
            NMService.debugLogger.debug("NMService: ServerPropertyChangeListener.propertyChange: event: " + event);
            String prop = event.getPropertyName();
            if ("AutoRestart".equals(prop) || "AutoKilledIfFailed".equals(prop) || "RestartMax".equals(prop) || "RestartIntervalSeconds".equals(prop) || "RestartDelaySeconds".equals(prop) || "isAutoMigrationEnabled".equals(prop) || "ListenAddress".equals(prop)) {
               ServerMBean smb = NMService.this.runtimeAccess.getServer();
               NodeManagerRuntime nmr = NodeManagerRuntime.getInstance((ServerTemplateMBean)smb);

               try {
                  nmr.updateServerProps(smb);
               } catch (IOException var6) {
                  NodeManagerLogger.logErrorUpdatingServerProps(smb.getName(), var6);
               }
            }

         }
      }

      // $FF: synthetic method
      ServerPropertyChangeListener(Object x1) {
         this();
      }
   }
}
