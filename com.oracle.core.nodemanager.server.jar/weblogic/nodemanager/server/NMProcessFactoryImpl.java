package weblogic.nodemanager.server;

import com.bea.logging.LogFileConfigBean;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.plugin.NMProcessFactory;
import weblogic.nodemanager.plugin.ProcessManagementPlugin;
import weblogic.nodemanager.util.ProcessControl;

public class NMProcessFactoryImpl implements NMProcessFactory {
   private ProcessControl processControl;
   protected LogFileConfigBean logFileRotationConfig;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

   public NMProcessFactoryImpl(DomainManager domainMgr) {
      NMServerConfig nmConf = domainMgr.getNMServer().getConfig();
      this.processControl = nmConf.getProcessControl();
      this.logFileRotationConfig = nmConf.getProcFileRotationConfig();
   }

   public ProcessManagementPlugin.Process createProcess(NMProcessInfo processInfo) {
      return (ProcessManagementPlugin.Process)(this.processControl != null ? new NMProcessNativeImpl(this.processControl, processInfo) : new NMProcessImpl(processInfo, this.logFileRotationConfig));
   }

   public ProcessManagementPlugin.Process createProcess(NMProcessInfo processInfo, String pid) throws UnsupportedOperationException {
      if (this.processControl != null) {
         return new NMProcessNativeImpl(this.processControl, processInfo, pid);
      } else {
         throw new UnsupportedOperationException(nmText.noProcessControl());
      }
   }

   public boolean isProcessAlive(String pid) throws UnsupportedOperationException {
      if (this.processControl != null) {
         return this.processControl.isProcessAlive(pid);
      } else {
         throw new UnsupportedOperationException(nmText.noProcessControl());
      }
   }
}
