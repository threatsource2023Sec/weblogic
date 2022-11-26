package weblogic.nodemanager.server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.common.StateInfo;
import weblogic.nodemanager.plugin.ProcessManagementPlugin;
import weblogic.nodemanager.util.ConcurrentFile;

public class WLSInstanceCustomizer extends InternalInstanceCustomizer {
   private final CallBackHandler callBackHandler;

   public WLSInstanceCustomizer(ServerManagerI serverMgr, StartupConfig conf, WLSProcessBuilder builder) {
      super(serverMgr, conf, builder);
      this.callBackHandler = new CallBackHandler(serverMgr, conf);
   }

   public String getStartString(StringBuilder text) {
      return nmText.msgStarting(text.toString());
   }

   public NMProcess createProcess() throws IOException {
      NMProcess theProcess = super.createProcess();
      this.callBackHandler.addCallBackHooks(theProcess);
      this.callBackHandler.addStopScriptHook(theProcess);
      return theProcess;
   }

   public NMProcess createProcess(String pid) throws IOException {
      NMProcess theProcess = super.createProcess(pid);
      this.callBackHandler.addStopScriptHook(theProcess);
      return theProcess;
   }

   public void afterCrashCleanUp() throws IOException {
      this.callBackHandler.runStopScrips();
   }

   public void preStart() throws IOException {
      this.callBackHandler.initializeCallBackHooks();
   }

   public void ensureStateInfo(StateInfo stateInfo, ConcurrentFile stateFile) throws IOException {
   }

   public ProcessManagementPlugin.SystemComponentState getState() {
      throw new UnsupportedOperationException();
   }

   private static class CallBackHandler {
      private final ServerManagerI serverMgr;
      private final StartupConfig conf;
      private List preStartHooks = new ArrayList();
      private List postStopHooks = new ArrayList();

      public CallBackHandler(ServerManagerI serverMgr, StartupConfig conf) {
         this.serverMgr = serverMgr;
         this.conf = conf;
      }

      public void initializeCallBackHooks() throws IOException {
         this.preStartHooks = this.getStartCallbacks(this.serverMgr, this.conf);
         this.postStopHooks = this.getStopCallbacks(this.serverMgr, this.conf);
      }

      public void addCallBackHooks(NMProcess theProcess) {
         Iterator var2;
         NMProcess.ExecuteCallbackHook hook;
         if (this.preStartHooks != null) {
            var2 = this.preStartHooks.iterator();

            while(var2.hasNext()) {
               hook = (NMProcess.ExecuteCallbackHook)var2.next();
               theProcess.addPreStartHook(hook);
            }
         }

         if (this.postStopHooks != null) {
            var2 = this.postStopHooks.iterator();

            while(var2.hasNext()) {
               hook = (NMProcess.ExecuteCallbackHook)var2.next();
               theProcess.addPostStopHook(hook);
            }
         }

      }

      public void addStopScriptHook(NMProcess theProcess) {
         NMProcess.ExecuteCallbackHook stopScriptHook = this.createStopScriptAsPostHook(this.serverMgr, this.conf);
         if (stopScriptHook != null) {
            theProcess.addPostStopHook(stopScriptHook);
         }

      }

      public void runStopScrips() throws IOException {
         List cleanupHooks = this.getStopCallbacks(this.serverMgr, this.conf);
         if (cleanupHooks != null) {
            Iterator var2 = cleanupHooks.iterator();

            while(var2.hasNext()) {
               NMProcess.ExecuteCallbackHook hook = (NMProcess.ExecuteCallbackHook)var2.next();

               try {
                  hook.execute();
               } catch (IOException var5) {
                  this.serverMgr.log(Level.FINEST, "The server cleanup failed.", var5);
               }
            }
         }

      }

      private List getStartCallbacks(ServerManagerI sm, StartupConfig conf) throws IOException {
         List hooks = new ArrayList();
         if (this.isIPForBinding(sm, conf)) {
            String serverName = sm.getServerName();
            DomainManager domainMgr = sm.getDomainManager();
            NMServer nmServer = domainMgr.getNMServer();

            String serverIP;
            NetworkInfo netInfo;
            for(Iterator var7 = conf.getServerIPList().iterator(); var7.hasNext(); hooks.add(NMHelper.createBindIPHook(serverName, serverIP, netInfo.getInterfaceName(), netInfo.getNetMaskOrPrefixLength(), domainMgr.getDomainDir().getServerDir(serverName).getPath(), this.getIfConfigScriptDir(domainMgr, nmServer), nmServer.getConfig().useMACBroadcast()))) {
               serverIP = (String)var7.next();
               netInfo = nmServer.getConfig().getNetworkInfoFor(serverIP);

               try {
                  InetAddress listenAddress = InetAddress.getByName(serverIP);
                  serverIP = listenAddress.getHostAddress();
               } catch (UnknownHostException var11) {
                  NMServer.nmLog.log(Level.WARNING, var11.getLocalizedMessage(), var11);
               }
            }
         }

         return hooks;
      }

      private List getStopCallbacks(ServerManagerI sm, StartupConfig conf) throws IOException {
         List hooks = new ArrayList();
         if (this.isIPForBinding(sm, conf)) {
            String serverName = sm.getServerName();
            DomainManager domainMgr = sm.getDomainManager();
            NMServer nmServer = domainMgr.getNMServer();

            String serverIP;
            NetworkInfo netInfo;
            for(Iterator var7 = conf.getServerIPList().iterator(); var7.hasNext(); hooks.add(NMHelper.createUnbindIPHook(serverName, serverIP, netInfo.getInterfaceName(), netInfo.getNetMaskOrPrefixLength(), domainMgr.getDomainDir().getServerDir(serverName).getPath(), this.getIfConfigScriptDir(domainMgr, nmServer)))) {
               serverIP = (String)var7.next();
               netInfo = nmServer.getConfig().getNetworkInfoFor(serverIP);

               try {
                  InetAddress listenAddress = InetAddress.getByName(serverIP);
                  serverIP = listenAddress.getHostAddress();
               } catch (UnknownHostException var11) {
                  NMServer.nmLog.log(Level.WARNING, var11.getLocalizedMessage(), var11);
               }
            }
         }

         return hooks;
      }

      private String getIfConfigScriptDir(DomainManager domainMgr, NMServer nmServer) {
         String dir = nmServer.getConfig().getIfConfigDir();
         return dir != null ? dir : domainMgr.getDomainDir().getIfConfigDir();
      }

      private boolean isIPForBinding(ServerManagerI sm, StartupConfig conf) {
         if (conf == null) {
            sm.log(Level.WARNING, "The server manager for " + sm.getServerName() + " is not initialized", (Throwable)null);
            return false;
         } else {
            return conf.getServerIPList() != null && !conf.getServerIPList().isEmpty();
         }
      }

      private NMProcess.ExecuteCallbackHook createStopScriptAsPostHook(final ServerManagerI serverMgr, final StartupConfig conf) {
         NMServerConfig nmsc = serverMgr.getDomainManager().getNMServer().getConfig();
         final String stopScript = nmsc.getStopScriptName();
         final long execScriptTimeout = nmsc.getExecScriptTimeout();
         NMProcess.ExecuteCallbackHook stopScriptHook = null;
         if (nmsc.isStopScriptEnabled() && stopScript != null) {
            stopScriptHook = new NMProcess.ExecuteCallbackHook() {
               public void execute() {
                  try {
                     WLSProcessBuilder wpb = new WLSProcessBuilder(serverMgr, conf, true);
                     List stopScriptCmd = wpb.getCommandLine();
                     Map stopScriptEnv = wpb.getEnvironment();
                     Properties env = stopScriptEnv != null ? CallBackHandler.this.toProps(stopScriptEnv) : null;
                     File stopScriptDir = wpb.getDirectory();
                     int exitCode = NMHelper.executeScript((String[])stopScriptCmd.toArray(new String[stopScriptCmd.size()]), env, stopScriptDir, execScriptTimeout);
                     if (exitCode != 0) {
                        if (exitCode == -101) {
                           NMServer.nmLog.warning(InternalInstanceCustomizer.nmText.cmdTimedOut(stopScript, serverMgr.getServerName(), execScriptTimeout));
                        } else {
                           NMServer.nmLog.warning(InternalInstanceCustomizer.nmText.cmdFailedSvr(stopScript, serverMgr.getServerName()));
                        }
                     }
                  } catch (Exception var7) {
                     NMServer.nmLog.warning(InternalInstanceCustomizer.nmText.cmdFailedSvrReason(stopScript, serverMgr.getServerName(), var7.toString()));
                  }

               }
            };
         }

         return stopScriptHook;
      }

      private Properties toProps(Map envMap) {
         Properties env = new Properties();
         Iterator var3 = envMap.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry me = (Map.Entry)var3.next();
            env.put(me.getKey(), me.getValue());
         }

         return env;
      }
   }
}
