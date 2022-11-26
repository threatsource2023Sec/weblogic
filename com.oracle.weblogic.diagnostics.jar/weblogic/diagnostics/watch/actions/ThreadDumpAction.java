package weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.expressions.ManagedServer;
import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBean;
import com.oracle.weblogic.diagnostics.watch.actions.ActionContext;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.descriptor.WLDFThreadDumpActionBean;
import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;
import weblogic.diagnostics.watch.i18n.DiagnosticsWatchLogger;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.JVMRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;

@Service(
   name = "ThreadDump"
)
@AdminServer
@ManagedServer
@PerLookup
public class ThreadDumpAction extends DumpAction {
   private static final String THREADLOGIC_HEADER = "Full thread dump ";
   private static final DiagnosticsTextWatchTextFormatter watchTextFormatter = DiagnosticsTextWatchTextFormatter.getInstance();
   public static final String ACTION_NAME = "ThreadDump";
   private static AtomicBoolean dumpInProgress = new AtomicBoolean(false);
   private ServerRuntimeMBean serverRuntime;

   public ThreadDumpAction() {
      super("ThreadDump");
   }

   public void execute(ActionContext context) {
      ActionConfigBean actionConfig = context.getActionConfig();
      boolean threadDumpAllowed = dumpInProgress.compareAndSet(false, true);
      if (!threadDumpAllowed) {
         DiagnosticsWatchLogger.logThreadDumpActionAlreadyInProgress(actionConfig.getName());
      } else {
         try {
            if (actionConfig instanceof WLDFActionConfigWrapper) {
               WLDFThreadDumpActionBean config = (WLDFThreadDumpActionBean)((WLDFActionConfigWrapper)actionConfig).getBean();
               this.performThreadDump(config, context.getWatchData());
            }
         } finally {
            dumpInProgress.set(false);
         }

      }
   }

   private void performThreadDump(WLDFThreadDumpActionBean config, Map policyData) {
      if (this.serverRuntime == null) {
         RuntimeAccess runtimeAccess = (RuntimeAccess)this.runtimeAccessProvider.get();
         if (runtimeAccess != null) {
            this.serverRuntime = runtimeAccess.getServerRuntime();
         }
      }

      if (this.serverRuntime == null) {
         throw new IllegalStateException(watchTextFormatter.getUnableToFindServerRuntimeText());
      } else {
         JVMRuntimeMBean jvmRuntime = this.serverRuntime.getJVMRuntime();

         for(int i = 0; i < config.getThreadDumpCount(); ++i) {
            if (this.isCanceled()) {
               DiagnosticsWatchLogger.logThreadDumpActionCanceled(config.getName());
               break;
            }

            String threadDump = jvmRuntime.getThreadStackDump();
            this.writeThreadDumpFile(jvmRuntime, config, threadDump, policyData);
            if (i < config.getThreadDumpCount() - 1) {
               DiagnosticsWatchLogger.logWaitForNextThreadDump(config.getThreadDumpDelaySeconds());

               try {
                  Thread.sleep((long)(config.getThreadDumpDelaySeconds() * 1000));
               } catch (InterruptedException var7) {
               }
            }
         }

      }
   }

   private void writeThreadDumpFile(JVMRuntimeMBean jvmRuntime, WLDFThreadDumpActionBean config, String threadDump, Map policyData) {
      PrintWriter writer = null;
      Date now = new Date();
      String serverName = this.serverRuntime.getName();
      String policyName = (String)policyData.get("WatchName");
      String moduleName = (String)policyData.get("WatchModule");
      File file = this.getDumpFile("ThreadDump", policyName, moduleName, config.getName(), "txt");

      try {
         String javaVendor = jvmRuntime.getJavaVendor();
         String javaVersion = jvmRuntime.getJavaVersion();
         writer = new PrintWriter(file);
         writer.println("Full thread dump " + javaVendor + " " + javaVersion + " on server " + serverName + " at " + now);
         writer.println("");
         writer.println(threadDump);
         DiagnosticsWatchLogger.logCreatedThreadDump(file.toString(), config.getName(), policyName, moduleName);
      } catch (IOException var16) {
         DiagnosticsWatchLogger.logFailedToWriteThreadDump(file.toString(), var16);
      } finally {
         if (writer != null) {
            writer.close();
         }

      }

   }
}
