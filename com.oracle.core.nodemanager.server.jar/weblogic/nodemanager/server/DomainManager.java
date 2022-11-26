package weblogic.nodemanager.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import weblogic.admin.plugin.PluginManager;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.plugin.NMProcessFactory;
import weblogic.nodemanager.plugin.Provider;
import weblogic.nodemanager.util.DomainInfo;
import weblogic.nodemanager.util.ScriptExecResult;
import weblogic.nodemanager.util.ServerInfo;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;

public class DomainManager implements DomainInfo {
   private final NMServer nmServer;
   private final String domainName;
   private final File scriptDir;
   private final DomainDir domainDir;
   private UserInfo userInfo;
   private final ServerManagers managers;
   private ClearOrEncryptedService encryptor;
   private final NMPluginManager pluginMgr;
   private Logger nmLog = Logger.getLogger("weblogic.nodemanager");
   private long saltFileTimeStamp;
   private long secretFileTimeStamp;
   private final NMProcessFactory processFactory;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

   public DomainManager(NMServer server, String name, String path) throws ConfigException, IOException {
      this.nmServer = server;
      this.domainName = name;
      this.domainDir = new DomainDir(path);
      this.scriptDir = this.domainDir.getMigrationScriptDir();
      this.managers = new ServerManagers(this);
      this.pluginMgr = this.getNMPluginManager();
      this.processFactory = new NMProcessFactoryImpl(this);
      this.initialize();
   }

   private NMPluginManager getNMPluginManager() {
      Provider provider = new Provider() {
         public File getDomainDirectory() {
            return DomainManager.this.domainDir;
         }

         public NMProcessFactory getNMProcessFactory() {
            return DomainManager.this.processFactory;
         }

         public String getDomainName() {
            return DomainManager.this.domainName;
         }
      };
      return new NMPluginManager(PluginManager.getInstance(), provider);
   }

   void resetCredentials(String user, String pass) throws IOException {
      File userFile = this.domainDir.getSecretFile();
      if (!userFile.exists()) {
         throw new FileNotFoundException(nmText.getPropertiesFileNotFound(userFile.toString()));
      } else if (!userFile.canWrite()) {
         throw new FileNotFoundException(nmText.getPropertiesFileNotWritable(userFile.toString()));
      } else {
         this.userInfo.set(user, pass);
         this.userInfo.save(userFile);
      }
   }

   private void initialize() throws ConfigException, IOException {
      if (!this.domainDir.isDirectory()) {
         throw new FileNotFoundException(nmText.getDomainDirNotFound(this.domainDir.toString()));
      } else if (!this.domainDir.getSaltFile().exists() && !this.domainDir.getOldSaltFile().exists()) {
         throw new FileNotFoundException(nmText.getInvalidDomainSalt(this.domainDir.toString()));
      } else {
         this.loadSaltFile();
         if (this.nmServer.getConfig().isAuthenticationEnabled()) {
            this.loadUserInfo();
         }

         String[] serverTypes = new String[]{"WebLogic", "Coherence"};
         String[] var2 = serverTypes;
         int var3 = serverTypes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String serverType = var2[var4];
            ServerDir[] serverDirs = this.domainDir.getServerDirs(serverType);
            if (serverDirs != null) {
               ServerDir[] var7 = serverDirs;
               int var8 = serverDirs.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  ServerDir dir = var7[var9];
                  String serverName = dir.getName();
                  if (serverType.equals("WebLogic") && !dir.getNMDataDir().exists()) {
                     if (this.nmLog.getLevel().intValue() <= Level.FINE.intValue()) {
                        this.nmLog.log(Level.INFO, "Skipping SM.create for non WLS dir: " + serverName);
                     }
                  } else {
                     try {
                        ServerManagerI sm = this.managers.getOrCreate(serverName, serverType);
                        if (!sm.isManagedByThisHost()) {
                           this.managers.removeServerManager(serverName, serverType);
                        }
                     } catch (Throwable var18) {
                        this.log(Level.SEVERE, "Error when initializing " + serverType + " server '" + serverName + "', ignore it now: " + var18.toString(), var18);
                     }
                  }
               }

               Iterator var25 = this.managers.getOrCreate(serverType).values().iterator();

               while(var25.hasNext()) {
                  ServerManagerI sm = (ServerManagerI)var25.next();

                  try {
                     sm.recoverServer();
                  } catch (Throwable var17) {
                     this.log(Level.SEVERE, "Crash recovery failed on " + serverType + " server '" + sm.getServerName() + "': " + var17.toString(), var17);
                  }
               }
            }
         }

         File scDir = new File(this.domainDir, "system_components");
         if (scDir.isDirectory()) {
            String[] scTypes = scDir.list();
            if (scTypes != null && scTypes.length > 0) {
               String[] var22 = scTypes;
               int var23 = scTypes.length;

               for(int var24 = 0; var24 < var23; ++var24) {
                  String scType = var22[var24];
                  ServerDir[] scDirs = this.domainDir.getServerDirs(scType);
                  if (scDirs != null && scDirs.length > 0) {
                     ServerDir[] var29 = scDirs;
                     int var30 = scDirs.length;

                     for(int var31 = 0; var31 < var30; ++var31) {
                        ServerDir dir = var29[var31];
                        String serverName = dir.getName();

                        ServerManagerI sm;
                        try {
                           sm = this.managers.getOrCreate(serverName, scType);
                           if (!sm.isManagedByThisHost()) {
                              this.managers.removeServerManager(serverName, scType);
                           }
                        } catch (Throwable var19) {
                           this.log(Level.SEVERE, "Error when initializing System Component " + scType + " server '" + serverName + "', ignore it now: " + var19.toString(), var19);
                           continue;
                        }

                        try {
                           sm.recoverServer();
                        } catch (Throwable var16) {
                           this.log(Level.SEVERE, "Crash recovery failed on System Component " + scType + " server '" + serverName + "': " + var16.toString(), var16);
                        }
                     }
                  }
               }
            }
         }

      }
   }

   public void shutdownAllForRestart() throws IOException {
      StringBuffer unableToRestart = new StringBuffer();
      List shutdownMgrs = new ArrayList();
      Iterator var3 = this.managers.managers.values().iterator();

      while(var3.hasNext()) {
         Map managerMap = (Map)var3.next();
         Iterator var5 = managerMap.values().iterator();

         while(var5.hasNext()) {
            ServerManagerI mgr = (ServerManagerI)var5.next();

            try {
               boolean shutdown = mgr.shutdownForRestart();
               if (shutdown) {
                  shutdownMgrs.add(mgr);
               }
            } catch (Exception var12) {
               Iterator var8 = shutdownMgrs.iterator();

               while(var8.hasNext()) {
                  ServerManagerI shutdownMgr = (ServerManagerI)var8.next();

                  try {
                     shutdownMgr.start((Properties)null, (Properties)null);
                  } catch (Exception var11) {
                     unableToRestart.append(mgr.getServerName());
                     unableToRestart.append(",");
                  }
               }

               StringBuffer msg = new StringBuffer();
               msg.append(nmText.getProblemShuttingDownForRestart(mgr.getServerName()));
               if (!unableToRestart.toString().isEmpty()) {
                  msg.append(" ");
                  msg.append(nmText.unableProperlyRestart(unableToRestart.toString()));
               }

               IOException error = new IOException(msg.toString());
               error.initCause(var12);
               throw error;
            }
         }
      }

   }

   private void loadUserInfo() throws IOException {
      if (this.encryptor == null) {
         throw new FileNotFoundException(nmText.getSaltFileNotFound());
      } else {
         File userFile = this.domainDir.getSecretFile();
         if (!userFile.exists()) {
            throw new FileNotFoundException(nmText.getPropertiesFileNotFound(userFile.toString()));
         } else {
            if (this.userInfo == null) {
               this.userInfo = new UserInfo(this.domainDir);
            }

            this.userInfo.load(userFile);
            if (this.userInfo.saveNeeded() && userFile.canWrite()) {
               this.userInfo.save(userFile);
            }

            this.secretFileTimeStamp = userFile.lastModified();
         }
      }
   }

   public Map getAllStates() {
      Map serverMgrs = this.managers.getOrCreate("WebLogic");
      Map result = this.getAllStates(serverMgrs);
      if (this.nmLog.isLoggable(Level.FINE)) {
         this.log(Level.FINE, "States = " + result);
         serverMgrs = this.managers.getOrCreate("Coherence");
         this.log(Level.FINE, "Coherence States = " + (serverMgrs != null && !serverMgrs.isEmpty() ? this.getAllStates(serverMgrs) : Collections.EMPTY_MAP));
      }

      return result;
   }

   private Map getAllStates(Map serverMgrs) {
      HashMap result = new HashMap();
      if (serverMgrs != null) {
         Set s = serverMgrs.keySet();
         Iterator var4 = s.iterator();

         while(var4.hasNext()) {
            String name = (String)var4.next();
            ServerManagerI sm = (ServerManagerI)serverMgrs.get(name);
            String state = sm.getState();
            result.put(name, state == null ? "UNKNOWN" : state);
         }
      }

      return result;
   }

   private synchronized void loadSaltFile() throws IOException {
      EncryptionService es;
      try {
         es = SerializedSystemIni.getEncryptionService(this.domainDir.getPath());
         this.saltFileTimeStamp = this.getSaltFileTimeStamp();
      } catch (Throwable var3) {
         throw (IOException)(new IOException(nmText.getErrorLoadingSalt())).initCause(var3);
      }

      this.encryptor = new ClearOrEncryptedService(es);
   }

   public synchronized ClearOrEncryptedService getEncryptor() {
      return this.encryptor;
   }

   public ServerManagerI getServerManagerIfPresent(String name, String type) {
      return this.managers.get(name, type);
   }

   public ServerManagerI getServerManager(String name, String type) throws ConfigException, IOException {
      return this.managers.getOrCreate(name, type);
   }

   public void removeServerManager(String name, String type) {
      this.managers.removeServerManager(name, type);
   }

   public NMPluginManager getPluginManager() {
      return this.pluginMgr;
   }

   public NMServer getNMServer() {
      return this.nmServer;
   }

   public String getDomainName() {
      return this.domainName;
   }

   public DomainDir getDomainDir() {
      return this.domainDir;
   }

   public boolean isAuthorized(String user, String pass) {
      return this.userInfo == null || this.userInfo.verify(user, pass);
   }

   public void checkFileStamps() throws IOException {
      Map serverStates = this.getAllStates();
      Iterator var2 = serverStates.values().iterator();

      String state;
      do {
         if (!var2.hasNext()) {
            long currentSaltFileTimeStamp = this.getSaltFileTimeStamp();
            if (currentSaltFileTimeStamp > this.saltFileTimeStamp) {
               this.loadSaltFile();
            }

            if (this.nmServer.getConfig().isAuthenticationEnabled() && this.domainDir.getSecretFile().exists()) {
               long currentSecretFileStamp = this.domainDir.getSecretFile().lastModified();
               if (currentSecretFileStamp > this.secretFileTimeStamp) {
                  this.loadUserInfo();
               }
            }

            return;
         }

         state = (String)var2.next();
      } while(state.equals("SHUTDOWN") || state.equals("UNKNOWN"));

   }

   private long getSaltFileTimeStamp() {
      long timeStamp = -1L;
      if (this.domainDir.getSaltFile().exists()) {
         timeStamp = this.domainDir.getSaltFile().lastModified();
      } else if (this.domainDir.getOldSaltFile().exists()) {
         timeStamp = this.domainDir.getOldSaltFile().lastModified();
      }

      return timeStamp;
   }

   public void log(Level level, String msg, Throwable thrown) {
      LogRecord lr = new LogRecord(level, msg);
      lr.setParameters(new String[]{this.domainName});
      if (thrown != null) {
         lr.setThrown(thrown);
      }

      this.nmLog.log(lr);
   }

   public void log(Level level, String msg) {
      this.log(level, msg, (Throwable)null);
   }

   private File matchFile(File dir, String filename) {
      String[] contents = dir.list();
      if (contents != null) {
         String[] var4 = dir.list();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String f = var4[var6];
            if (f.equals(filename)) {
               return new File(dir, f);
            }
         }
      }

      return null;
   }

   public ScriptExecResult executeScript(String scriptName, String dir, Map env, long timeout) throws IOException {
      File scriptDir = null;
      if (dir != null && !dir.isEmpty()) {
         if (this.nmServer.getConfig().getWellKnownScriptLocations().contains(dir)) {
            scriptDir = this.matchFile(this.domainDir.getDomainBinDir(), dir);
            if (scriptDir == null) {
               this.nmLog.finest("Passed directory " + dir + " not found on disk, but is configured in wellKnownScriptLocations");
            }
         } else if (this.nmLog.getLevel().intValue() <= Level.FINEST.intValue()) {
            StringBuffer sb = new StringBuffer();

            String tmpDir;
            for(Iterator var8 = this.nmServer.getConfig().getWellKnownScriptLocations().iterator(); var8.hasNext(); sb.append(tmpDir)) {
               tmpDir = (String)var8.next();
               if (sb.length() != 0) {
                  sb.append(",");
               }
            }

            this.nmLog.finest("Passed directory: " + dir + " is not listed in well known locations: " + sb.toString());
         }
      } else {
         scriptDir = this.domainDir.getMigrationScriptDir();
      }

      if (scriptDir == null) {
         throw new FileNotFoundException(nmText.scriptDirNotFound(dir, this.domainDir.getDomainBinDir().getPath()));
      } else {
         File scriptFile = this.matchFile(scriptDir, scriptName);
         if (scriptFile == null) {
            throw new FileNotFoundException(nmText.scriptNotFound(scriptDir.getPath(), scriptName));
         } else {
            String[] cmd = new String[]{scriptFile.getPath()};
            return NMHelper.callScript(cmd, env, scriptFile.getParentFile(), timeout);
         }
      }
   }

   public ServerInfo getServerInfo(String serverName, String serverType) {
      return this.managers.getServerInfo(serverName, serverType);
   }

   public List getAllServerInfo() {
      return this.managers.getAllServerInfo();
   }

   public boolean isServerRegistered(String serverName, String serverType) {
      return this.managers.isServerRegistered(serverName, serverType);
   }

   public boolean isServerConfigured(String serverName, String serverType) {
      ServerManagerI srv = this.managers.get(serverName, serverType);
      return srv != null ? srv.isServerConfigured() : false;
   }

   private static class ServerManagers {
      private final DomainManager domainManager;
      private final Map managers = new ConcurrentHashMap();

      public ServerManagers(DomainManager domainManager) {
         this.domainManager = domainManager;
      }

      public Map getOrCreate(String serverType) {
         synchronized(this.managers) {
            ConcurrentHashMap map = (ConcurrentHashMap)this.managers.get(serverType);
            if (map == null) {
               map = new ConcurrentHashMap();
               this.managers.put(serverType, map);
            }

            return map;
         }
      }

      public ServerManagerI getOrCreate(String name, String serverType) throws ConfigException, IOException {
         synchronized(this.managers) {
            Map map = this.getOrCreate(serverType);
            ServerManagerI sm = (ServerManagerI)map.get(name);
            if (sm == null) {
               sm = ServerManagerFactory.instance.createServerManager(this.domainManager, name, serverType);
               map.put(name, sm);
            }

            return sm;
         }
      }

      public ServerManagerI get(String name, String serverType) {
         ConcurrentHashMap serverMap = (ConcurrentHashMap)this.managers.get(serverType);
         return serverMap == null ? null : (ServerManagerI)serverMap.get(name);
      }

      public void removeServerManager(String name, String serverType) {
         synchronized(this.managers) {
            ConcurrentHashMap map = (ConcurrentHashMap)this.managers.get(serverType);
            map.remove(name);
         }
      }

      public List getAllServerInfo() {
         List srvList = new ArrayList();
         Iterator var2 = this.managers.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry e = (Map.Entry)var2.next();
            String serverType = (String)e.getKey();
            ConcurrentHashMap srvMap = (ConcurrentHashMap)e.getValue();
            Iterator var6 = srvMap.values().iterator();

            while(var6.hasNext()) {
               ServerManagerI srv = (ServerManagerI)var6.next();
               srvList.add(this.getServerInfo(srv, serverType));
            }
         }

         return srvList;
      }

      public ServerInfo getServerInfo(String serverName, String serverType) {
         ServerInfo serverInfo = null;
         ServerManagerI srv = this.get(serverName, serverType);
         return this.getServerInfo(srv, serverType);
      }

      public boolean isServerRegistered(String serverName, String serverType) {
         return this.get(serverName, serverType) != null;
      }

      private ServerInfo getServerInfo(ServerManagerI srv, String serverType) {
         ServerInfo serverInfo = new ServerInfo(srv.getServerName(), serverType);
         serverInfo.setConfigComplete(srv.isServerConfigured());
         serverInfo.setState(srv.getState());
         return serverInfo;
      }
   }
}
