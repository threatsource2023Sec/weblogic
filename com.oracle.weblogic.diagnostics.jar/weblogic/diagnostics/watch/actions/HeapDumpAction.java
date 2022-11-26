package weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.expressions.ManagedServer;
import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBean;
import com.oracle.weblogic.diagnostics.watch.actions.ActionContext;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.descriptor.WLDFHeapDumpActionBean;
import weblogic.diagnostics.watch.i18n.DiagnosticsWatchLogger;
import weblogic.management.provider.RuntimeAccess;

@Service(
   name = "HeapDump"
)
@AdminServer
@ManagedServer
@PerLookup
public class HeapDumpAction extends DumpAction {
   public static final String ACTION_NAME = "HeapDump";
   static final String HOTSPOT_DIAG_MBEAN = "com.sun.management:type=HotSpotDiagnostic";
   static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
   private static final String DUMP_HEAP_METHOD_NAME = "dumpHeap";
   private static AtomicBoolean dumpInProgress = new AtomicBoolean(false);
   private ObjectName hotSpotDiagMBean;
   private MBeanServer platformMBeanServer;
   private boolean heapDumpAvailable = false;
   private String serverName;

   public HeapDumpAction() throws MalformedObjectNameException {
      super("HeapDump");
   }

   public void execute(ActionContext context) {
      boolean heapDumpAllowed = dumpInProgress.compareAndSet(false, true);
      if (!heapDumpAllowed) {
         DiagnosticsWatchLogger.logHeapDumpAlreadyInProgress();
      } else {
         try {
            if (!this.isHeapDumpAvailable()) {
               DiagnosticsWatchLogger.logHeapDumpActionUnavailable(context.getActionConfig().getName());
               return;
            }

            ActionConfigBean actionConfig = context.getActionConfig();
            if (actionConfig instanceof WLDFActionConfigWrapper) {
               WLDFHeapDumpActionBean config = (WLDFHeapDumpActionBean)((WLDFActionConfigWrapper)actionConfig).getBean();
               if (config.isEnabled()) {
                  this.dumpHeap(config, context.getWatchData());
               }
            }
         } finally {
            dumpInProgress.set(false);
         }

      }
   }

   private void dumpHeap(WLDFHeapDumpActionBean config, Map policyData) {
      if (this.serverName == null) {
         RuntimeAccess runtimeAccess = (RuntimeAccess)this.runtimeAccessProvider.get();
         if (runtimeAccess != null) {
            this.serverName = runtimeAccess.getServerName();
         }
      }

      String actionName = config.getName();
      String policyName = (String)policyData.get("WatchName");
      String moduleName = (String)policyData.get("WatchModule");
      File dumpFile = this.getDumpFile("HeapDump", policyName, moduleName, actionName, "hprof");
      String dumpPath = dumpFile.getAbsolutePath();
      boolean liveSetOnly = config.isLiveSetOnly();
      DiagnosticsWatchLogger.logHeapDumpCaptureInitiated(dumpPath, liveSetOnly);

      try {
         this.platformMBeanServer.invoke(this.hotSpotDiagMBean, "dumpHeap", new Object[]{dumpPath, liveSetOnly}, new String[]{"java.lang.String", "boolean"});
         DiagnosticsWatchLogger.logHeapDumpCaptureComplete(dumpPath, actionName, (String)policyData.get("WatchName"), (String)policyData.get("WatchModule"));
      } catch (ReflectionException | MBeanException | InstanceNotFoundException var10) {
         DiagnosticsWatchLogger.logUnexpectedErrorGeneratingHeapDump(var10);
         this.heapDumpAvailable = false;
      }

   }

   public synchronized boolean isHeapDumpAvailable() {
      if (this.hotSpotDiagMBean == null) {
         if (this.platformMBeanServer == null) {
            this.platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
         }

         if (this.platformMBeanServer == null) {
            DiagnosticsWatchLogger.logHeapDumpActionPlatformMBeanServerUnavailable();
         } else {
            try {
               this.hotSpotDiagMBean = new ObjectName("com.sun.management:type=HotSpotDiagnostic");
            } catch (MalformedObjectNameException var6) {
               throw new RuntimeException(var6);
            }

            if (this.platformMBeanServer.isRegistered(this.hotSpotDiagMBean)) {
               try {
                  MBeanInfo mbeanInfo = this.platformMBeanServer.getMBeanInfo(this.hotSpotDiagMBean);
                  MBeanOperationInfo[] var2 = mbeanInfo.getOperations();
                  int var3 = var2.length;

                  for(int var4 = 0; var4 < var3; ++var4) {
                     MBeanOperationInfo operation = var2[var4];
                     if (operation.getName().equalsIgnoreCase("dumpHeap")) {
                        this.heapDumpAvailable = true;
                        break;
                     }
                  }

                  if (!this.heapDumpAvailable) {
                     DiagnosticsWatchLogger.logHeapDumpMethodUnavailable();
                  }
               } catch (InstanceNotFoundException | ReflectionException | IntrospectionException var7) {
                  DiagnosticsWatchLogger.logHeapDumpActionAvailabilityCheckException(var7);
               }
            } else {
               DiagnosticsWatchLogger.logHotSpotDiagnosticMXBeanUnavailable("com.sun.management:type=HotSpotDiagnostic");
            }
         }
      }

      return this.heapDumpAvailable;
   }
}
