package weblogic.management.provider.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.admin.plugin.ChangeList;
import weblogic.admin.plugin.ConfigurationPlugin;
import weblogic.admin.plugin.NMComponentTypeChangeList;
import weblogic.admin.plugin.NMMachineChangeList;
import weblogic.admin.plugin.PluginManager;
import weblogic.admin.plugin.ValidationException;
import weblogic.admin.plugin.ChangeList.Change.Type;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorDiff;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.ManagedExternalServerMBean;
import weblogic.management.configuration.ManagedExternalServerStartMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.configuration.SystemComponentConfigurationMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.internal.EditDirectoryManager;
import weblogic.management.mbeanservers.edit.FileChange;
import weblogic.management.mbeanservers.edit.internal.FileChangeImpl;
import weblogic.management.provider.ActivateTask;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.MachineStatus;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.SystemComponentLifeCycleRuntimeMBean;
import weblogic.nodemanager.NMException;
import weblogic.nodemanager.adminserver.NodeManagerMonitor;
import weblogic.nodemanager.client.NMClient;
import weblogic.nodemanager.client.ShellClient;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.FileUtils;

public class CommonAdminConfigurationManager implements BeanUpdateListener {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static final CAMConfigTextFormatter camConfigTextFormatter = new CAMConfigTextFormatter();
   private final PluginManager pluginManager = PluginManager.getInstance();
   private final HashMap configPlugins = new HashMap();
   private final Map nmClients = Collections.synchronizedMap(new HashMap());
   private final Map clientsForTx = Collections.synchronizedMap(new HashMap());
   private final Map machineChangeLists = Collections.synchronizedMap(new HashMap());
   private static final CommonAdminConfigurationManager camManager = new CommonAdminConfigurationManager();
   private static boolean addedListener;
   private boolean isForceOverride = false;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private NodeManagerMonitor nmMonitor;
   private static final String SYNC_TX_PREFIX = "Sync-";
   private static final String COMP_LEADSTR = "/fmwconfig/components/";
   private CAMReplicationExclusives repExclusives = null;
   private final Map activateTasks = Collections.synchronizedMap(new HashMap());
   private boolean isDeleteAlwaysAllowed = Boolean.getBoolean("weblogic.management.allowDeleteOfSystemComponent");
   private static final String[] RESERVED_COMP_NAME = new String[]{"domain_bak", "AdminServerTag"};

   CommonAdminConfigurationManager() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created common admin configuration manager " + this);
      }

      this.loadCAMReplicationExclusiveFiles();
   }

   private void loadCAMReplicationExclusiveFiles() {
      String camDir = DomainDir.getCAMConfigDir();
      File camDirFile = new File(camDir);
      this.repExclusives = CAMReplicationExclusives.parseCAMDirectory(camDirFile);
      byte[] data = "dir=., pattern=*/.wls.replication.exclusive.list\n".getBytes();
      CAMReplicationExclusive exclusive = new CAMReplicationExclusive("fmwconfig/components", new ByteArrayInputStream(data));
      this.repExclusives.addExclusiveList(exclusive);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.repExclusives.toString());
      }

   }

   public static synchronized CommonAdminConfigurationManager getInstance() {
      RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
      if (!addedListener && rt != null && rt.isAdminServer()) {
         rt.getDomain().addBeanUpdateListener(camManager);
         addedListener = true;
      }

      return camManager;
   }

   public synchronized void activateChanges(String tx, DomainMBean domain, DomainMBean currentDomain, ActivateTask task, DescriptorDiff diff) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Activating changes for CAM configuration.");
      }

      EditDirectoryManager directoryMgr = EditDirectoryManager.getDirectoryManager(task.getPartitionName(), task.getEditSessionName());
      File[] pendingFiles = directoryMgr.getAllCAMFilesAsArray();
      Set deletedFiles = directoryMgr.getCAMCandidateFilesForDeletion();
      pendingFiles = this.repExclusives.removeExclusiveFiles(pendingFiles, new File(DomainDir.getPendingDir()));
      deletedFiles = this.repExclusives.removeExclusiveFiles(deletedFiles, new File(DomainDir.getConfigDir()));
      HashMap machineCLMap = new HashMap();
      this.machineChangeLists.put(tx, machineCLMap);
      this.activateTasks.put(tx, task);
      File[] var10 = pendingFiles;
      int var11 = pendingFiles.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         File f = var10[var12];
         this.addFilesFromDirectoryToChangeList(f, this.determineAddOrEdit(directoryMgr, f), machineCLMap, tx, domain, (DomainMBean)null, true);
      }

      Iterator var14 = deletedFiles.iterator();

      while(var14.hasNext()) {
         File f = (File)var14.next();
         this.addFilesFromDirectoryToChangeList(f, Type.REMOVE, machineCLMap, tx, domain, currentDomain, true);
      }

      this.syncNewSystemComponents(tx, diff, domain, currentDomain, machineCLMap);
      this.isForceOverride = false;
   }

   private void syncNewSystemComponents(String tx, DescriptorDiff diff, DomainMBean domain, DomainMBean currentDomain, HashMap machineCLMap) throws IOException {
      Iterator var6 = diff.iterator();

      while(var6.hasNext()) {
         Object beanUpdateEvent = var6.next();
         BeanUpdateEvent.PropertyUpdate[] updates = ((BeanUpdateEvent)beanUpdateEvent).getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var9 = updates;
         int var10 = updates.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            BeanUpdateEvent.PropertyUpdate update = var9[var11];
            if (update.getPropertyName().equals("SystemComponents") && (update.getUpdateType() == 2 || update.getUpdateType() == 3)) {
               SystemComponentConfigurationMBean config = null;
               SystemComponentMBean comp;
               ChangeList.Change.Type type;
               if (update.getUpdateType() == 2) {
                  comp = (SystemComponentMBean)update.getAddedObject();
                  type = Type.ADD;
                  config = this.getComponentConfigurationMBean(domain, comp);
               } else {
                  comp = (SystemComponentMBean)update.getRemovedObject();
                  type = Type.REMOVE;
                  config = this.getComponentConfigurationMBean(currentDomain, comp);
               }

               String cfgName = config == null ? null : config.getName();
               String name = cfgName != null ? cfgName : comp.getName();
               NMComponentTypeChangeList configFiles = this.getComponentConfiguration(comp.getComponentType(), name);
               File parent = (new File(DomainDir.getConfigDir())).getParentFile();
               if (configFiles != null) {
                  Iterator var20 = configFiles.getComponentTypeChanges().getChanges().values().iterator();

                  while(var20.hasNext()) {
                     ChangeList.Change change = (ChangeList.Change)var20.next();
                     File f = new File(parent, change.getRelativePath());
                     this.addFileToCLPerComponent(comp, f, type, machineCLMap, tx, false, false);
                  }
               }
            }
         }
      }

   }

   synchronized MachineStatus[] resync(DomainMBean domain, DomainMBean currentDomain, SystemComponentMBean sysCompMBean) throws IOException {
      String tx = "Resync-" + System.currentTimeMillis();
      HashMap machineCLMap = this.createResyncChangeLists(tx, domain, currentDomain, sysCompMBean);
      if (machineCLMap.size() == 0) {
         return new MachineStatus[0];
      } else {
         Map result = new HashMap();
         this.distributeResync(tx, machineCLMap, result);
         this.commitResync(tx, machineCLMap.keySet(), result);
         if (debugLogger.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Resync tx ").append(tx).append(" result:\n");
            Iterator var8 = result.keySet().iterator();

            while(var8.hasNext()) {
               String key = (String)var8.next();
               MachineStatus status = (MachineStatus)result.get(key);
               sb.append("  ").append(key).append(" ");
               if (status.getState() == 0) {
                  sb.append("succeed.\n");
               } else if (status.getException() == null) {
                  sb.append("deferred.\n");
               } else {
                  sb.append("failed.\n").append(this.exceptionToString(status.getException())).append("\n");
               }
            }

            sb.append("***resync result ends.");
            debugLogger.debug(sb.toString());
         }

         return (MachineStatus[])result.values().toArray(new MachineStatus[result.size()]);
      }
   }

   private String exceptionToString(Exception e) {
      if (e == null) {
         return null;
      } else {
         ByteArrayOutputStream bao = new ByteArrayOutputStream();
         PrintStream ps = new PrintStream(bao);
         e.printStackTrace(ps);
         ps.close();
         return bao.toString();
      }
   }

   private HashMap createResyncChangeLists(String tx, DomainMBean domain, DomainMBean currentDomain, SystemComponentMBean sysCompMBean) throws IOException {
      HashMap machineCLMap = new HashMap();
      SystemComponentMBean[] comps = sysCompMBean == null ? domain.getSystemComponents() : new SystemComponentMBean[]{sysCompMBean};
      SystemComponentMBean[] var7 = comps;
      int var8 = comps.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         SystemComponentMBean comp = var7[var9];
         FileChange[] changes = null;

         try {
            changes = this.getRemoteFileChanges(domain, comp);
         } catch (UnsupportedOperationException var19) {
            if (comps.length == 1) {
               throw var19;
            }
         } catch (IOException var20) {
            if (comps.length == 1) {
               throw var20;
            }
         }

         if (changes != null && changes.length != 0) {
            File parent = (new File(DomainDir.getConfigDir())).getParentFile();
            FileChange[] var13 = changes;
            int var14 = changes.length;

            for(int var15 = 0; var15 < var14; ++var15) {
               FileChange change = var13[var15];
               File f = new File(parent, change.getPath());
               ChangeList.Change.Type type;
               if ("add".equals(change.getOperation())) {
                  type = Type.REMOVE;
               } else if ("remove".equals(change.getOperation())) {
                  type = Type.ADD;
               } else {
                  if (!"edit".equals(change.getOperation())) {
                     continue;
                  }

                  type = Type.EDIT;
               }

               this.addFileToCLPerComponent(comp, f, type, machineCLMap, tx, true, false);
            }
         }
      }

      return machineCLMap;
   }

   private void distributeResync(String tx, Map changeLists, Map result) throws IOException {
      ArrayList clients = (ArrayList)this.clientsForTx.get(tx);
      Iterator var5 = changeLists.keySet().iterator();

      while(true) {
         while(var5.hasNext()) {
            String machine = (String)var5.next();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Resyncing change list for machine " + machine);
            }

            NMClient nmc = (NMClient)this.nmClients.get(machine);
            if (nmc != null && clients != null && clients.contains(nmc)) {
               try {
                  nmc.changeList((NMMachineChangeList)changeLists.get(machine));
                  result.put(machine, new MachineStatus(machine, 1, (Exception)null));
               } catch (IOException var9) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Node manager for nmc is not available for resync, skipping due to exception: ", var9);
                  }

                  result.put(machine, new MachineStatus(machine, 2, var9));
                  this.syncFilesLater(machine);
                  this.cleanupNMClient(nmc);
               }
            } else {
               this.syncFilesLater(machine);
               result.put(machine, new MachineStatus(machine, 2, (Exception)null));
            }
         }

         return;
      }
   }

   private void commitResync(String tx, Set machines, Map result) throws IOException {
      try {
         ArrayList clients = (ArrayList)this.clientsForTx.get(tx);
         Iterator var5 = machines.iterator();

         while(var5.hasNext()) {
            String machine = (String)var5.next();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("committing resync change list for machine " + machine);
            }

            NMClient nmc = (NMClient)this.nmClients.get(machine);
            if (nmc != null && clients != null && clients.contains(nmc)) {
               try {
                  nmc.commitChangeList(tx);
                  result.put(machine, new MachineStatus(machine, 0, (Exception)null));
               } catch (IOException var12) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("commit resync change failed", var12);
                  }

                  result.put(machine, new MachineStatus(machine, 2, var12));
                  this.cleanupNMClient(nmc);
                  this.syncFilesLater(machine);
               }
            }
         }
      } finally {
         this.cleanup(tx);
      }

   }

   public synchronized boolean hasWork(String tx) {
      return this.machineChangeLists.containsKey(tx);
   }

   public synchronized void sync(String tx) throws IOException {
      String adminURL = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getAdministrationURL();
      if (adminURL != null) {
         URLManagerService pms = (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
         adminURL = pms.normalizeToHttpProtocol(adminURL);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Admin URL for sync is " + adminURL);
         }
      }

      HashMap changeLists = (HashMap)this.machineChangeLists.get(tx);
      if (changeLists == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No change list for this tx.");
         }

      } else {
         Iterator var4 = changeLists.keySet().iterator();

         while(true) {
            while(var4.hasNext()) {
               String machine = (String)var4.next();
               NMMachineChangeList nmChanges = (NMMachineChangeList)changeLists.get(machine);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Syncing change list for machine " + machine);
               }

               this.updateComponentStatus(tx, nmChanges, 1, (Exception)null);
               NMClient nmc = (NMClient)this.nmClients.get(machine);
               if (nmc == null) {
                  RuntimeException e = new RuntimeException(camConfigTextFormatter.nodeManagerNotAvailOnMachine(machine));
                  this.updateComponentStatus(tx, nmChanges, 8, e);
                  this.syncFilesLater(machine);
               } else {
                  ArrayList clients = (ArrayList)this.clientsForTx.get(tx);
                  if (clients != null && clients.contains(nmc)) {
                     try {
                        nmc.changeList((NMMachineChangeList)changeLists.get(machine));
                        this.updateComponentStatus(tx, nmChanges, 2, (Exception)null);
                     } catch (IOException var10) {
                        if (debugLogger.isDebugEnabled()) {
                           debugLogger.debug("Node manager for nmc is not available for sync, skipping due to exception: ", var10);
                        }

                        this.updateComponentStatus(tx, nmChanges, 8, var10);
                        this.syncFilesLater(machine);
                        this.cleanupNMClient(nmc);
                     }
                  } else {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("No clients for this tx so not connected.");
                     }

                     this.updateComponentStatus(tx, nmChanges, 8, (Exception)null);
                     this.syncFilesLater(machine);
                  }
               }
            }

            return;
         }
      }
   }

   private void syncFilesLater(String machine) {
      MachineMBean monitorMachine = null;
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      MachineMBean[] machines = domain.getMachines();
      MachineMBean[] var5 = machines;
      int var6 = machines.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         MachineMBean tmp = var5[var7];
         if (tmp.getName().equals(machine)) {
            monitorMachine = tmp;
            break;
         }
      }

      if (monitorMachine != null) {
         this.getNodeManagerMonitor().add(monitorMachine);
      }

   }

   public synchronized void prepare(String tx) throws IOException {
      ArrayList clients = (ArrayList)this.clientsForTx.get(tx);
      if (clients == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No clients for this tx.");
         }

      } else {
         Iterator var3 = clients.iterator();

         while(var3.hasNext()) {
            NMClient nmc = (NMClient)var3.next();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Call validateChangeList for tx " + tx + " on nm client " + nmc);
            }

            NMMachineChangeList nmChanges = this.getChangeList(tx, nmc);

            try {
               nmc.validateChangeList(tx);
               this.updateComponentStatus(tx, nmChanges, 2, (Exception)null);
            } catch (IOException var7) {
               this.updateComponentStatus(tx, nmChanges, 5, var7);
               this.cleanupNMClient(nmc);
               throw var7;
            }
         }

      }
   }

   public synchronized void commit(String tx) throws IOException {
      try {
         ArrayList clients = (ArrayList)this.clientsForTx.get(tx);
         if (clients == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("No clients for this tx.");
            }

            return;
         }

         boolean var29 = false;

         try {
            var29 = true;
            MultiIOException multiIOException = new MultiIOException();
            Iterator var4 = clients.iterator();

            while(true) {
               if (!var4.hasNext()) {
                  if (multiIOException.hasException()) {
                     throw multiIOException;
                  }

                  var29 = false;
                  break;
               }

               NMClient nmc = (NMClient)var4.next();
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Call commitChangeList for tx " + tx + " on nm client " + nmc);
               }

               NMMachineChangeList nmChanges = this.getChangeList(tx, nmc);

               try {
                  nmc.commitChangeList(tx);
                  this.updateComponentStatus(tx, nmChanges, 4, (Exception)null);
               } catch (IOException var32) {
                  this.updateComponentStatus(tx, nmChanges, 5, var32);
                  multiIOException.addException(var32);
                  this.cleanupNMClient(nmc);
               }
            }
         } finally {
            if (var29) {
               ActivateTask task = (ActivateTask)this.activateTasks.get(tx);
               if (task != null) {
                  DomainRuntimeMBean domainRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntime();
                  SystemComponentLifeCycleRuntimeMBean[] var13 = domainRuntime.getSystemComponentLifeCycleRuntimes();
                  int var14 = var13.length;

                  for(int var15 = 0; var15 < var14; ++var15) {
                     SystemComponentLifeCycleRuntimeMBean sclcrm = var13[var15];

                     try {
                        String state = sclcrm.getState();
                        if ("RESTART_REQUIRED".equals(state)) {
                           task.getSystemComponentRestartRequired().put(sclcrm.getName(), sclcrm.getType());
                        }
                     } catch (Exception var30) {
                     }
                  }
               }

            }
         }

         ActivateTask task = (ActivateTask)this.activateTasks.get(tx);
         if (task != null) {
            DomainRuntimeMBean domainRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntime();
            SystemComponentLifeCycleRuntimeMBean[] var37 = domainRuntime.getSystemComponentLifeCycleRuntimes();
            int var38 = var37.length;

            for(int var7 = 0; var7 < var38; ++var7) {
               SystemComponentLifeCycleRuntimeMBean sclcrm = var37[var7];

               try {
                  String state = sclcrm.getState();
                  if ("RESTART_REQUIRED".equals(state)) {
                     task.getSystemComponentRestartRequired().put(sclcrm.getName(), sclcrm.getType());
                  }
               } catch (Exception var31) {
               }
            }
         }
      } finally {
         this.cleanup(tx);
      }

   }

   public synchronized void rollback(String tx) throws IOException {
      try {
         ArrayList clients = (ArrayList)this.clientsForTx.get(tx);
         if (clients != null) {
            Iterator var3 = clients.iterator();

            while(var3.hasNext()) {
               NMClient nmc = (NMClient)var3.next();
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Call rollbackChangeList for tx " + tx + " on nm client " + nmc);
               }

               NMMachineChangeList nmChanges = this.getChangeList(tx, nmc);

               try {
                  this.updateComponentStatus(tx, nmChanges, 6, (Exception)null);
                  nmc.rollbackChangeList(tx);
                  this.updateComponentStatus(tx, nmChanges, 5, (Exception)null);
               } catch (IOException var10) {
                  this.updateComponentStatus(tx, nmChanges, 5, var10);
                  this.cleanupNMClient(nmc);
                  throw var10;
               }
            }

            return;
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No clients for this tx.");
         }
      } finally {
         this.cleanup(tx);
      }

   }

   public synchronized void cleanup(String tx) {
      if (tx != null) {
         this.clientsForTx.remove(tx);
         this.machineChangeLists.remove(tx);
         this.activateTasks.remove(tx);
      }
   }

   public void cleanupNMClient(NMClient nmc) {
      Iterator var2 = this.nmClients.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         if (nmc.equals(entry.getValue())) {
            this.nmClients.remove(entry.getKey());
            break;
         }
      }

   }

   public NMMachineChangeList getMachineConfiguration(String machine) throws IOException {
      HashMap machineCL = new HashMap();
      String camDir = DomainDir.getCAMConfigDir();
      File camDirFile = new File(camDir);
      if (camDirFile.exists() && camDirFile.isDirectory()) {
         File[] camFiles = FileUtils.find(camDirFile, new FileFilter() {
            public boolean accept(File f) {
               return f.isFile();
            }
         });
         camFiles = this.repExclusives.removeExclusiveFiles(camFiles, new File(DomainDir.getConfigDir()));
         String tx = "Sync-" + System.currentTimeMillis();
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         File[] var8 = camFiles;
         int var9 = camFiles.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            File f = var8[var10];
            this.addFilesFromDirectoryToChangeList(f, Type.ADD, machineCL, tx, domain, (DomainMBean)null, false);
         }

         return (NMMachineChangeList)machineCL.get(machine);
      } else {
         return null;
      }
   }

   private ConfigurationPlugin getConfigurationPlugin(String systemComponentType) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Get configuration plugin for type: " + systemComponentType);
      }

      synchronized(this.configPlugins) {
         ConfigurationPlugin plugin = (ConfigurationPlugin)this.configPlugins.get(systemComponentType);
         if (plugin == null) {
            plugin = (ConfigurationPlugin)this.pluginManager.createPlugin(systemComponentType, "CONFIGURATION");
            if (plugin == null) {
               throw new IOException(ManagementLogger.logPluginNotFoundLoggable(systemComponentType, "CONFIGURATION").getMessage());
            }

            plugin.init();
            this.configPlugins.put(systemComponentType, plugin);
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Configuration plugin for type: " + systemComponentType + " is " + plugin);
         }

         return plugin;
      }
   }

   private String getMachineName(ManagedExternalServerMBean esmb) {
      MachineMBean machine = esmb.getMachine();
      return machine == null ? null : machine.getName();
   }

   private NMClient getNodeManagerForExternalServer(ManagedExternalServerMBean esmb) throws IOException {
      String machineName = this.getMachineName(esmb);
      if (machineName == null) {
         return null;
      } else {
         NMClient nmc = (NMClient)this.nmClients.get(machineName);
         if (nmc != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("NM client for machine name: " + machineName + " is " + nmc);
            }

            return nmc;
         } else {
            try {
               MachineMBean machine = esmb.getMachine();
               NodeManagerMBean nmmb = machine.getNodeManager();

               assert nmmb != null;

               nmc = NMClient.getInstance(nmmb.getNMType());
               nmc.setVerbose(nmmb.isDebugEnabled());
               if (nmmb.getListenAddress() != null) {
                  nmc.setHost(nmmb.getListenAddress());
               }

               nmc.setPort(nmmb.getListenPort());
               String nmHome = nmmb.getNodeManagerHome();
               String cmd = nmmb.getShellCommand();
               if (nmmb.getNMType().equalsIgnoreCase("ssh") || nmmb.getNMType().equalsIgnoreCase("rsh")) {
                  if (nmHome != null) {
                     nmc.setNMDir(nmHome);
                  }

                  if (cmd != null) {
                     ((ShellClient)nmc).setShellCommand(cmd);
                  }
               }

               nmc.setDomainName(ManagementService.getRuntimeAccess(kernelId).getDomainName());
               DomainMBean dmb = ManagementService.getRuntimeAccess(kernelId).getDomain();
               SecurityConfigurationMBean scmb = dmb.getSecurityConfiguration();

               assert scmb != null;

               String user = scmb.getNodeManagerUsername();
               String pass = scmb.getNodeManagerPassword();
               if (user != null && user.length() > 0 && pass != null && pass.length() > 0) {
                  nmc.setNMUser(user);
                  nmc.setNMPass(pass);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Node manager username and password specified " + this);
                  }
               }

               ManagedExternalServerStartMBean essmb = esmb.getManagedExternalServerStart();
               String dir = essmb.getRootDirectory();
               if (dir != null) {
                  nmc.setDomainDir(dir);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Root directory for server is '" + dir + "'");
                  }
               }

               nmc.setServerName(esmb.getName());
               nmc.setServerType(esmb.getManagedExternalType());
               this.nmClients.put(machineName, nmc);
               return nmc;
            } catch (Throwable var14) {
               throw new IOException(var14.getLocalizedMessage());
            }
         }
      }
   }

   private void addFilesFromDirectoryToChangeList(File f, ChangeList.Change.Type type, HashMap machineCLMap, String tx, DomainMBean domain, DomainMBean currentDomain, boolean validate) throws IOException {
      String path = f.getCanonicalPath();
      String[] typename = this.extractCompTypeAndNameFromPath(path);
      if (typename == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Skip invalid CAM file " + path);
         }

      } else {
         String compType = typename[0];
         String name = typename[1];
         SystemComponentConfigurationMBean confMBean = this.findCompConfigurationMBean(compType, name, domain);
         if (confMBean == null) {
            SystemComponentMBean compMBean = this.findComponentMBean(compType, name, domain);
            if (compMBean == null && currentDomain != null) {
               compMBean = this.findComponentMBean(compType, name, currentDomain);
            }

            if (compMBean != null) {
               this.addFileToCLPerComponent(compMBean, f, type, machineCLMap, tx, this.isForceOverride, validate);
            }

         } else {
            SystemComponentMBean[] comps = confMBean.getSystemComponents();
            if (comps != null && comps.length > 0) {
               SystemComponentMBean[] var14 = comps;
               int var15 = comps.length;

               for(int var16 = 0; var16 < var15; ++var16) {
                  SystemComponentMBean compMBean = var14[var16];
                  this.addFileToCLPerComponent(compMBean, f, type, machineCLMap, tx, this.isForceOverride, validate);
               }
            }

         }
      }
   }

   private String[] extractCompTypeAndNameFromPath(String path) throws IOException {
      if (path != null && !path.isEmpty()) {
         String upath = !path.contains("\\") ? path : path.replace('\\', '/');
         int pos1 = upath.lastIndexOf("/fmwconfig/components/");
         int pos2 = pos1 == -1 ? -1 : upath.indexOf("/", pos1 + "/fmwconfig/components/".length());
         int pos3 = pos2 == -1 ? -1 : upath.indexOf("/", pos2 + 1);
         if (pos3 != -1 && pos3 + 1 < path.length()) {
            String type = upath.substring(pos1 + "/fmwconfig/components/".length(), pos2);
            String name = upath.substring(pos2 + 1, pos3);
            return !type.isEmpty() && !name.isEmpty() ? new String[]{type, name} : null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private void addFileToCLPerComponent(SystemComponentMBean compMBean, File f, ChangeList.Change.Type type, HashMap machineCLMap, String tx, boolean isForceOverride, boolean validate) throws IOException {
      MachineMBean machine = compMBean.getMachine();
      if (machine == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No machine specified for component " + compMBean.getName() + ", skipping file " + f);
         }

         if (!tx.startsWith("Sync-")) {
            ManagementLogger.logMachineNotSpecified(compMBean.getName(), f.getAbsolutePath());
         }

      } else {
         NMClient nmc = this.getNodeManagerForExternalServer(compMBean);
         this.addNMClientToTx(tx, nmc, compMBean.getName());
         NMMachineChangeList machineCL = (NMMachineChangeList)machineCLMap.get(machine.getName());
         if (machineCL == null) {
            machineCL = new NMMachineChangeList(tx);
            machineCLMap.put(machine.getName(), machineCL);
         }

         NMComponentTypeChangeList compCL = (NMComponentTypeChangeList)machineCL.getChanges().get(compMBean.getComponentType());
         String[] instanceNames;
         if (compCL == null) {
            instanceNames = new String[]{compMBean.getName()};
            compCL = new NMComponentTypeChangeList(instanceNames, new ChangeList());
            machineCL.addChangeListForType(compMBean.getComponentType(), compCL);
         }

         compCL.getComponentTypeChanges().setForceOverride(isForceOverride);
         instanceNames = compCL.getComponentNames();
         boolean fnd = false;
         String[] var14 = instanceNames;
         int var15 = instanceNames.length;

         String relPath;
         for(int var16 = 0; var16 < var15; ++var16) {
            relPath = var14[var16];
            if (compMBean.getName().equals(relPath)) {
               fnd = true;
            }
         }

         if (!fnd) {
            compCL.addComponentName(compMBean.getName());
         }

         ConfigurationPlugin plugin = this.getConfigurationPlugin(compMBean.getComponentType());
         long version = type == Type.REMOVE ? 0L : plugin.getVersion(f.getCanonicalPath());
         relPath = DomainDir.removeRootDirectoryFromPath(f.getPath());
         ChangeList cl = compCL.getComponentTypeChanges();
         if (validate) {
            try {
               ChangeList changeList = new ChangeList();
               changeList.addChange(type, compMBean.getName(), relPath, version, f.lastModified());
               ConfigurationPlugin configurationPlugin = null;

               try {
                  configurationPlugin = this.getConfigurationPlugin(compMBean.getComponentType());
               } catch (IOException var22) {
                  debugLogger.debug("Configuration plugin not found, validate() won't be invoked.", var22);
               }

               if (configurationPlugin != null) {
                  configurationPlugin.validate(changeList);
               }
            } catch (ValidationException var23) {
               throw new IOException(var23.getMessage(), var23);
            }
         }

         cl.addChange(type, compMBean.getName(), relPath, version, f.lastModified());
      }
   }

   private SystemComponentConfigurationMBean findCompConfigurationMBean(String compType, String cfgName, DomainMBean domain) {
      if (compType != null && !compType.isEmpty() && cfgName != null && !cfgName.isEmpty()) {
         SystemComponentConfigurationMBean[] compConfs = domain.getSystemComponentConfigurations();

         for(int i = 0; compConfs != null && i < compConfs.length; ++i) {
            SystemComponentConfigurationMBean compConf = compConfs[i];
            if (compType.equals(compConf.getComponentType()) && cfgName.equals(compConf.getName())) {
               return compConf;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private SystemComponentMBean findComponentMBean(String compType, String compName, DomainMBean domain) {
      if (compType != null && !compType.isEmpty() && compName != null && !compName.isEmpty()) {
         SystemComponentMBean[] comps = domain.getSystemComponents();

         for(int i = 0; comps != null && i < comps.length; ++i) {
            SystemComponentMBean comp = comps[i];
            if (compName.equals(comp.getName()) && compType.equals(comp.getComponentType())) {
               return comp;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private boolean addNMClientToTx(String tx, NMClient nmc, String instanceName) {
      ArrayList clients = (ArrayList)this.clientsForTx.get(tx);
      if (clients == null) {
         clients = new ArrayList();
         this.clientsForTx.put(tx, clients);
      }

      if (!clients.contains(nmc)) {
         try {
            nmc.getVersion();
            clients.add(nmc);
         } catch (Exception var7) {
            ActivateTask task = (ActivateTask)this.activateTasks.get(tx);
            if (task != null) {
               task.addDeferredServer(instanceName, var7);
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Node manager for nmc is not available, skipping due to exception: ", var7);
            }

            this.cleanupNMClient(nmc);
            return false;
         }
      }

      return true;
   }

   private ChangeList.Change.Type determineAddOrEdit(EditDirectoryManager directoryMgr, File pendingFile) {
      String configPath = directoryMgr.convertPendingDirectoryToConfig(pendingFile.getPath());
      return (new File(configPath)).exists() ? Type.EDIT : Type.ADD;
   }

   private NMMachineChangeList getChangeList(String tx, NMClient nmc) {
      if (tx != null && nmc != null) {
         HashMap nmChangeListMap = (HashMap)this.machineChangeLists.get(tx);
         if (nmChangeListMap == null) {
            return null;
         } else {
            Set machines = nmChangeListMap.keySet();
            Iterator var6 = machines.iterator();

            String machine;
            do {
               if (!var6.hasNext()) {
                  return null;
               }

               machine = (String)var6.next();
            } while(!nmc.equals(this.nmClients.get(machine)));

            NMMachineChangeList nmChanges = (NMMachineChangeList)nmChangeListMap.get(machine);
            return nmChanges;
         }
      } else {
         return null;
      }
   }

   private void updateComponentStatus(String tx, NMMachineChangeList nmChanges, int state, Exception e) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Update component status for tx " + tx + " state " + state + " changes " + nmChanges + " exception: " + e);
      }

      ActivateTask task = (ActivateTask)this.activateTasks.get(tx);
      if (task != null && nmChanges != null && nmChanges.getChanges() != null) {
         String machineName = null;
         HashMap nmChangeListMap = (HashMap)this.machineChangeLists.get(tx);
         if (nmChangeListMap != null) {
            Set machines = nmChangeListMap.keySet();
            Iterator var9 = machines.iterator();

            while(var9.hasNext()) {
               String machine = (String)var9.next();
               if (((NMMachineChangeList)nmChangeListMap.get(machine)).equals(nmChanges)) {
                  machineName = machine;
               }
            }
         }

         Iterator var14 = nmChanges.getChanges().values().iterator();

         while(true) {
            NMComponentTypeChangeList nm;
            do {
               if (!var14.hasNext()) {
                  return;
               }

               nm = (NMComponentTypeChangeList)var14.next();
            } while(nm.getComponentNames() == null);

            String[] var16 = nm.getComponentNames();
            int var11 = var16.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               String componentName = var16[var12];
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Update component " + componentName + " state: " + state);
               }

               task.updateServerState(componentName, state);
               if (state == 8) {
                  if (e != null) {
                     ManagementLogger.logNodeManagerUnreachableError(componentName, machineName, e);
                  } else {
                     ManagementLogger.logNodeManagerUnreachable(componentName, machineName);
                  }

                  task.addDeferredServer(componentName, e);
               } else if (state == 5) {
                  task.addFailedServer(componentName, e);
               }
            }
         }
      }
   }

   public NodeManagerMonitor getNodeManagerMonitor() {
      return this.nmMonitor;
   }

   public void setNodeManagerMonitor(NodeManagerMonitor nmMonitor) {
      this.nmMonitor = nmMonitor;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         BeanUpdateEvent.PropertyUpdate[] var3 = updates;
         int var4 = updates.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            BeanUpdateEvent.PropertyUpdate update = var3[var5];
            String propertyName = update.getPropertyName();
            SystemComponentConfigurationMBean sysCompConf;
            String name;
            int var13;
            SystemComponentMBean sysComp;
            SystemComponentMBean[] comps;
            int var40;
            String msg;
            if (update.getUpdateType() != 2) {
               if (update.getUpdateType() == 3) {
                  if (propertyName.equals("SystemComponents") && update.getRemovedObject() != null) {
                     sysComp = (SystemComponentMBean)update.getRemovedObject();
                     name = sysComp.getName();
                     DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
                     SystemComponentLifeCycleRuntimeMBean sysCompRT = domainAccess.lookupSystemComponentLifecycleRuntime(name);
                     if (sysCompRT != null && !this.isDeleteAlwaysAllowed) {
                        String state = sysCompRT.getState();
                        if (state != null && !state.equals("SHUTDOWN") && !state.equals("FAILED") && !state.equals("FAILED_NOT_RESTARTABLE") && !state.equals("UNKNOWN")) {
                           String msg = "System component \"" + name + "\" cannot be deleted since \"" + name + "\" is not in SHUTDOWN, FAILED, or FAILED_NOT_RESTARTABLE state";
                           throw new BeanUpdateRejectedException(msg);
                        }
                     }
                  } else if (propertyName.equals("SystemComponentConfigurations") && update.getRemovedObject() != null) {
                     sysCompConf = (SystemComponentConfigurationMBean)update.getRemovedObject();
                     name = sysCompConf.getName();
                     SystemComponentMBean[] scMBeans = sysCompConf.getSystemComponents();
                     DomainMBean proposedDomainMBeean = (DomainMBean)event.getProposedBean();
                     comps = scMBeans;
                     var13 = scMBeans.length;

                     for(var40 = 0; var40 < var13; ++var40) {
                        SystemComponentMBean comp = comps[var40];
                        String name = comp.getName();
                        DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
                        SystemComponentLifeCycleRuntimeMBean sysCompRT = domainAccess.lookupSystemComponentLifecycleRuntime(name);
                        if (sysCompRT != null && !this.isDeleteAlwaysAllowed) {
                           msg = sysCompRT.getState();
                           if (msg != null && msg.equals("RUNNING")) {
                              String msg = "System component configuration \"" + name + "\" cannot be deleted because its associated system component \"" + name + "\" is still in RUNNING state.";
                              throw new BeanUpdateRejectedException(msg);
                           }
                        }

                        if (proposedDomainMBeean.lookupSystemComponent(name) != null) {
                           msg = "System component configuration \"" + name + "\" cannot be deleted since it still contains system component: " + name + ".";
                           throw new BeanUpdateRejectedException(msg);
                        }
                     }
                  }
               }
            } else {
               File compDir;
               String[] var11;
               int var12;
               String resCompName;
               String msg;
               String confType;
               if (propertyName.equals("SystemComponents") && update.getAddedObject() != null) {
                  sysComp = (SystemComponentMBean)update.getAddedObject();
                  name = sysComp.getName();
                  compDir = new File(DomainDir.getPathRelativeServersDir(name));
                  var11 = RESERVED_COMP_NAME;
                  var12 = var11.length;

                  for(var13 = 0; var13 < var12; ++var13) {
                     resCompName = var11[var13];
                     if (resCompName.equals(compDir.getName())) {
                        msg = "System component \"" + name + "\" cannot be created since \"" + compDir.getName() + "\" matches reserved component name \"" + resCompName + "\".";
                        throw new BeanUpdateRejectedException(msg);
                     }
                  }

                  if (compDir.exists() && !compDir.isDirectory()) {
                     confType = "System component \"" + name + "\" cannot be created since \"" + compDir.getName() + "\" already exists:";
                     throw new BeanUpdateRejectedException(confType);
                  }
               } else if (propertyName.equals("SystemComponentConfigurations") && update.getAddedObject() != null) {
                  sysCompConf = (SystemComponentConfigurationMBean)update.getAddedObject();
                  name = sysCompConf.getName();
                  compDir = new File(DomainDir.getPathRelativeServersDir(name));
                  var11 = RESERVED_COMP_NAME;
                  var12 = var11.length;

                  for(var13 = 0; var13 < var12; ++var13) {
                     resCompName = var11[var13];
                     if (resCompName.equals(compDir.getName())) {
                        msg = "System component configuration \"" + name + "\" cannot be created since \"" + compDir.getName() + "\" matches reserved component name \"" + resCompName + "\".";
                        throw new BeanUpdateRejectedException(msg);
                     }
                  }

                  if (compDir.exists() && !compDir.isDirectory()) {
                     confType = "System component configuration \"" + name + "\" cannot be created since \"" + compDir.getName() + "\" already exists.";
                     throw new BeanUpdateRejectedException(confType);
                  }

                  confType = sysCompConf.getComponentType();
                  comps = sysCompConf.getSystemComponents();
                  SystemComponentMBean[] var38 = comps;
                  var40 = comps.length;

                  for(int var43 = 0; var43 < var40; ++var43) {
                     SystemComponentMBean comp = var38[var43];
                     if (!comp.getComponentType().equals(confType)) {
                        String msg = "System component configuration \"" + name + "\" with type \"" + confType + "\" cannot contain system component \"" + comp.getName() + "\" with different component type \"" + comp.getComponentType() + "\" .";
                        throw new BeanUpdateRejectedException(msg);
                     }
                  }

                  DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
                  SystemComponentConfigurationMBean[] currentSccmbs = this.findSysCompConfigurationsByType(confType, domain);
                  if (currentSccmbs != null) {
                     SystemComponentConfigurationMBean[] var44 = currentSccmbs;
                     int var45 = currentSccmbs.length;

                     for(int var48 = 0; var48 < var45; ++var48) {
                        SystemComponentConfigurationMBean currentSccmb = var44[var48];
                        if (sysCompConf.getName().equals(currentSccmb.getName())) {
                           msg = "The System Component Configuration with the name " + sysCompConf.getName() + " already exists.";
                           if (debugLogger.isDebugEnabled()) {
                              debugLogger.debug(msg);
                           }

                           throw new BeanUpdateRejectedException(msg);
                        }

                        SystemComponentMBean[] currentComps = currentSccmb.getSystemComponents();
                        SystemComponentMBean[] var20 = currentComps;
                        int var21 = currentComps.length;

                        for(int var22 = 0; var22 < var21; ++var22) {
                           SystemComponentMBean currentComp = var20[var22];
                           SystemComponentMBean[] newComps = sysCompConf.getSystemComponents();
                           SystemComponentMBean[] var25 = newComps;
                           int var26 = newComps.length;

                           for(int var27 = 0; var27 < var26; ++var27) {
                              SystemComponentMBean newComp = var25[var27];
                              if (currentComp.getName().equals(newComp.getName())) {
                                 String msg = "The System Component with the name " + newComp.getName() + " already exists in the configuration " + currentSccmb.getName() + ".";
                                 if (debugLogger.isDebugEnabled()) {
                                    debugLogger.debug(msg);
                                 }

                                 throw new BeanUpdateRejectedException(msg);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

      }
   }

   private SystemComponentConfigurationMBean[] findSysCompConfigurationsByType(String compType, DomainMBean domain) {
      ArrayList sccmbList = new ArrayList();
      if (compType == null) {
         return null;
      } else {
         SystemComponentConfigurationMBean[] compConfs = domain.getSystemComponentConfigurations();

         for(int i = 0; compConfs != null && i < compConfs.length; ++i) {
            SystemComponentConfigurationMBean compConf = compConfs[i];
            if (compType.equals(compConf.getComponentType())) {
               sccmbList.add(compConf);
            }
         }

         return (SystemComponentConfigurationMBean[])sccmbList.toArray(new SystemComponentConfigurationMBean[sccmbList.size()]);
      }
   }

   public void activateUpdate(BeanUpdateEvent event) {
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   public FileChange[] getRemoteFileChanges(DomainMBean domain, SystemComponentMBean comp) throws IOException {
      NMClient nmc = this.getNodeManagerForExternalServer(comp);
      if (nmc == null) {
         throw new IOException("machine " + this.getMachineName(comp) + " not available/reachable for component " + comp.getName());
      } else {
         NMComponentTypeChangeList remoteChgLst;
         try {
            remoteChgLst = nmc.getChangeListForAllFiles(comp.getComponentType(), comp.getName());
         } catch (NMException var9) {
            if (var9.getMessage().contains("does not support getChangeListForAllFiles()")) {
               throw new UnsupportedOperationException(comp.getName() + " doesn't support configuration pull.");
            }

            throw new IOException(var9.getMessage(), var9);
         }

         SystemComponentConfigurationMBean config = this.getComponentConfigurationMBean(domain, comp);
         String cfgName = config == null ? null : config.getName();
         remoteChgLst = this.adjustRemoteChangeListFilePath(remoteChgLst, comp.getComponentType(), cfgName, comp.getName());
         String name = cfgName != null ? cfgName : comp.getName();
         NMComponentTypeChangeList adminChgLst = this.getComponentConfiguration(comp.getComponentType(), name);
         return this.detectChangesFromTwoNMComponentTypeChangeList(adminChgLst, remoteChgLst);
      }
   }

   public NMComponentTypeChangeList getComponentConfiguration(String type, String name) throws IOException {
      File camDir = new File(DomainDir.getCAMConfigDir());
      File compDir = new File(camDir, type + "/" + name);
      if (compDir.exists() && compDir.isDirectory()) {
         File[] files = FileUtils.find(compDir, new FileFilter() {
            public boolean accept(File f) {
               return f.isFile();
            }
         });
         files = this.repExclusives.removeExclusiveFiles(files, new File(DomainDir.getConfigDir()));
         File domainDir = (new File(DomainDir.getRootDir())).getCanonicalFile();
         ConfigurationPlugin plugin = this.getConfigurationPlugin(type);
         ChangeList changes = new ChangeList();
         File[] var9 = files;
         int var10 = files.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            File file = var9[var11];
            String path = this.getRelativePathToDir(domainDir, file.getCanonicalFile());
            long version = plugin.getVersion(file.getCanonicalPath());
            changes.addChange(Type.ADD, name, path, version, file.lastModified());
         }

         return new NMComponentTypeChangeList(new String[]{name}, changes);
      } else {
         return null;
      }
   }

   private String getRelativePathToDir(File dir, File file) throws IOException {
      if (dir != null && file != null) {
         dir = dir.getCanonicalFile();
         File temp = file.getCanonicalFile();
         ArrayList list = new ArrayList();

         do {
            list.add(temp.getName());
            if (dir.equals(temp.getParentFile())) {
               StringBuilder sb = new StringBuilder((String)list.get(list.size() - 1));

               for(int i = list.size() - 2; i >= 0; --i) {
                  sb.append("/").append((String)list.get(i));
               }

               return sb.toString();
            }

            temp = temp.getParentFile();
         } while(temp != null);

         throw new IOException("file not under dir. dir=" + dir.getCanonicalFile() + ", file=" + file.getCanonicalFile());
      } else {
         throw new IOException("dir or file is null. dir=" + dir + ",file=" + file);
      }
   }

   private SystemComponentConfigurationMBean getComponentConfigurationMBean(DomainMBean domain, SystemComponentMBean comp) throws IOException {
      SystemComponentConfigurationMBean[] configs = domain == null ? null : domain.getSystemComponentConfigurations();
      if (comp != null && configs != null && configs.length > 0) {
         SystemComponentConfigurationMBean[] var4 = configs;
         int var5 = configs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            SystemComponentConfigurationMBean config = var4[var6];
            if (comp.getComponentType().equals(config.getComponentType())) {
               SystemComponentMBean[] comps = config.getSystemComponents();
               if (comps != null && comps.length > 0) {
                  SystemComponentMBean[] var9 = comps;
                  int var10 = comps.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     SystemComponentMBean c = var9[var11];
                     if (comp.getName().equals(c.getName())) {
                        return config;
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   private NMComponentTypeChangeList adjustRemoteChangeListFilePath(NMComponentTypeChangeList cl, String compType, String cfgName, String compName) throws IOException {
      if (cl == null) {
         return null;
      } else {
         String[] compNames = cl.getComponentNames();
         if (compNames.length != 1) {
            throw new IOException("Invalid change list from getChangeListForAllFiles() for component " + compName + ": " + cl);
         } else {
            ChangeList changes = cl.getComponentTypeChanges();
            ChangeList newchanges = new ChangeList();
            newchanges.setForceOverride(changes.isForceOverride());
            String name = cfgName != null ? cfgName : compName;
            Iterator var9 = changes.getChanges().values().iterator();

            while(var9.hasNext()) {
               ChangeList.Change c = (ChangeList.Change)var9.next();
               String path = "config/fmwconfig/components/" + compType + "/" + name + "/" + c.getRelativePath();
               newchanges.addChange(c.getType(), compName, path, c.getVersion(), c.getLastModifiedTime());
            }

            return new NMComponentTypeChangeList(new String[]{name}, newchanges);
         }
      }
   }

   private FileChange[] detectChangesFromTwoNMComponentTypeChangeList(NMComponentTypeChangeList source, NMComponentTypeChangeList target) {
      ArrayList changes = new ArrayList();
      ChangeList target2 = target == null ? null : target.getComponentTypeChanges();
      ChangeList source2 = source == null ? null : source.getComponentTypeChanges();
      Iterator var6;
      ChangeList.Change change;
      String path;
      if (target2 != null && target2.getChanges().size() > 0) {
         var6 = target2.getChanges().values().iterator();

         while(var6.hasNext()) {
            change = (ChangeList.Change)var6.next();
            path = change.getRelativePath();
            ChangeList.Change srcChg = source2 == null ? null : (ChangeList.Change)source2.getChanges().get(path);
            FileChangeImpl c;
            if (srcChg == null) {
               c = new FileChangeImpl(path, "add", 0L, change.getLastModifiedTime());
               changes.add(c);
            } else if (change.getVersion() != srcChg.getVersion()) {
               c = new FileChangeImpl(path, "edit", srcChg.getLastModifiedTime(), change.getLastModifiedTime());
               changes.add(c);
            }
         }
      }

      if (source2 != null && source2.getChanges().size() > 0) {
         var6 = source2.getChanges().values().iterator();

         while(true) {
            do {
               if (!var6.hasNext()) {
                  return changes.size() == 0 ? new FileChange[0] : (FileChange[])changes.toArray(new FileChange[changes.size()]);
               }

               change = (ChangeList.Change)var6.next();
               path = change.getRelativePath();
            } while(target2 != null && target2.getChanges().containsKey(path));

            FileChange c = new FileChangeImpl(path, "remove", change.getLastModifiedTime(), 0L);
            changes.add(c);
         }
      } else {
         return changes.size() == 0 ? new FileChange[0] : (FileChange[])changes.toArray(new FileChange[changes.size()]);
      }
   }

   public byte[] getRemoteFileContents(SystemComponentMBean sysCompMBean, FileChange chg) throws IOException {
      NMClient nmc = this.getNodeManagerForExternalServer(sysCompMBean);
      if (nmc == null) {
         throw new IOException("machine for system component " + sysCompMBean.getName() + " is not available");
      } else {
         return nmc.getFile(sysCompMBean.getComponentType(), sysCompMBean.getName(), chg.getPath());
      }
   }

   public byte[] getFileContents(String componentType, String relativePath) throws IOException {
      if (componentType != null && !componentType.isEmpty() && relativePath != null && !relativePath.isEmpty()) {
         String camDirStr = DomainDir.getCAMConfigDir();
         String path = camDirStr + File.separatorChar + componentType + File.separatorChar + relativePath;
         File file = new File(path);
         if (file.exists() && file.isFile()) {
            return this.readLocalFile(file);
         } else {
            throw new IOException("File not found: " + file.getCanonicalPath());
         }
      } else {
         throw new NullPointerException("Invalid component type and/or path: type=" + componentType + ", path=" + relativePath);
      }
   }

   private byte[] readLocalFile(File file) throws IOException {
      InputStream stream = new FileInputStream(file);
      byte[] buffer = new byte[4096];
      ByteArrayOutputStream outStream = new ByteArrayOutputStream(4096);

      try {
         int bytesRead = 1;

         while(bytesRead > 0) {
            bytesRead = stream.read(buffer);
            if (bytesRead > 0) {
               outStream.write(buffer, 0, bytesRead);
            }
         }
      } finally {
         stream.close();
      }

      return outStream.toByteArray();
   }

   public FileChange[] updateConfigurationFromRemoteSystem(DomainMBean domain, EditDirectoryManager directoryMgr, SystemComponentMBean comp) throws IOException {
      FileChange[] changes = this.getRemoteFileChanges(domain, comp);
      if (changes != null && changes.length != 0) {
         FileChange[] var5 = changes;
         int var6 = changes.length;

         int var7;
         for(var7 = 0; var7 < var6; ++var7) {
            FileChange change = var5[var7];
            if (!change.getPath().startsWith("config/fmwconfig/components/") && !change.getPath().startsWith("config\\fmwconfig\\component\\")) {
               throw new IOException("Invalid file path " + change.getPath() + " from component " + comp.getName());
            }
         }

         File domainDir = (new File(DomainDir.getRootDir())).getCanonicalFile();
         FileChange[] var14 = changes;
         var7 = changes.length;

         for(int var15 = 0; var15 < var7; ++var15) {
            FileChange change = var14[var15];
            String oper = change.getOperation();
            File file;
            if (!"add".equals(oper) && !"edit".equals(oper)) {
               if ("remove".equals(oper)) {
                  file = new File(domainDir, change.getPath());
                  directoryMgr.addCandidateFileForDeletion(file);
               }
            } else {
               file = new File(domainDir, "pending");
               file = new File(file, change.getPath().substring(7));
               byte[] content = this.getRemoteFileContents(comp, change);
               if (content != null) {
                  this.writeLocalFile(file, content);
               }
            }
         }

         return changes;
      } else {
         return null;
      }
   }

   public void enableOverwriteComponentChanges(DomainMBean domain) {
      this.isForceOverride = true;
   }

   private void writeLocalFile(File file, byte[] content) throws IOException {
      this.createParentDir(file);
      FileOutputStream fout = new FileOutputStream(file);

      try {
         fout.write(content);
      } finally {
         fout.close();
      }

   }

   private void createParentDir(File file) throws IOException {
      if (file != null) {
         File parent = file.getParentFile();
         if (!parent.exists() && !parent.mkdirs()) {
            throw new IOException("failed to create directory " + parent.getPath());
         }
      }
   }

   private static class MultiIOException extends IOException {
      private final List exceptionList = new ArrayList();

      public MultiIOException() {
      }

      public void addException(IOException e) {
         this.exceptionList.add(e);
      }

      public boolean hasException() {
         return this.exceptionList.size() > 0;
      }

      public String getMessage() {
         return this.getMessage(new MessageProvider() {
            public String getMessage(Exception e) {
               return e.getMessage();
            }
         });
      }

      public String getLocalizedMessage() {
         return this.getMessage(new MessageProvider() {
            public String getMessage(Exception e) {
               return e.getLocalizedMessage();
            }
         });
      }

      private String getMessage(MessageProvider messageProvider) {
         StringBuilder sb = new StringBuilder();
         sb.append("There are ").append(this.exceptionList.size()).append(" nested Exceptions:\n");

         for(Iterator var3 = this.exceptionList.iterator(); var3.hasNext(); sb.append('\n')) {
            IOException e = (IOException)var3.next();
            sb.append(e.getClass().getName());
            String message = messageProvider.getMessage(e);
            if (message != null) {
               sb.append(": ").append(message);
            }
         }

         return sb.toString();
      }

      public synchronized Throwable getCause() {
         return null;
      }

      public String toString() {
         return this.getLocalizedMessage();
      }

      public void printStackTrace() {
         if (this.exceptionList.size() == 1) {
            ((IOException)this.exceptionList.get(0)).printStackTrace();
         } else {
            System.err.println("There are " + this.exceptionList.size() + " nested Exceptions:\n");
            Iterator var1 = this.exceptionList.iterator();

            while(var1.hasNext()) {
               IOException e = (IOException)var1.next();
               e.printStackTrace();
            }
         }

      }

      public void printStackTrace(PrintStream s) {
         if (this.exceptionList.size() == 1) {
            ((IOException)this.exceptionList.get(0)).printStackTrace(s);
         } else {
            s.println("There are " + this.exceptionList.size() + " nested Exceptions:\n");
            Iterator var2 = this.exceptionList.iterator();

            while(var2.hasNext()) {
               IOException e = (IOException)var2.next();
               e.printStackTrace(s);
            }
         }

      }

      public void printStackTrace(PrintWriter s) {
         if (this.exceptionList.size() == 1) {
            ((IOException)this.exceptionList.get(0)).printStackTrace(s);
         } else {
            s.println("There are " + this.exceptionList.size() + " nested Exceptions:\n");
            Iterator var2 = this.exceptionList.iterator();

            while(var2.hasNext()) {
               IOException e = (IOException)var2.next();
               e.printStackTrace(s);
            }
         }

      }

      private interface MessageProvider {
         String getMessage(Exception var1);
      }
   }
}
