package weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.watch.actions.ActionAdapter;
import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Provider;
import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;
import weblogic.diagnostics.watch.i18n.DiagnosticsWatchLogger;
import weblogic.management.DomainDir;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.provider.RuntimeAccess;

public abstract class DumpAction extends ActionAdapter {
   protected static final String DEFAULT_DATE_FORMAT = "yyyy_MM_dd_HH_mm_ss";
   protected static final String THREAD_DUMP_TYPE = "ThreadDump";
   protected static final String HEAP_DUMP_TYPE = "HeapDump";
   protected static final DiagnosticsTextWatchTextFormatter watchTextFormatter = DiagnosticsTextWatchTextFormatter.getInstance();
   @Inject
   protected Provider runtimeAccessProvider;

   public DumpAction(String name) {
      super(name);
   }

   protected File getDumpFile(String dumpType, String policyName, String moduleName, String actionName, String suffix) {
      RuntimeAccess runtimeAccess = (RuntimeAccess)this.runtimeAccessProvider.get();
      ServerMBean serverMBean = null;
      if (runtimeAccess != null) {
         serverMBean = runtimeAccess.getServer();
      }

      if (serverMBean == null) {
         throw new IllegalStateException(watchTextFormatter.getUnableToFindServerText());
      } else {
         String serverName = serverMBean.getName();
         WLDFServerDiagnosticMBean wldfDiag = serverMBean.getServerDiagnosticConfig();
         File dumpDir = new File(wldfDiag.getDiagnosticDumpsDir());
         if (!dumpDir.isAbsolute()) {
            File serverDir = new File(DomainDir.getDirForServer(serverName));
            dumpDir = new File(serverDir, wldfDiag.getDiagnosticDumpsDir());
         }

         if (!dumpDir.exists()) {
            boolean succ = dumpDir.mkdirs();
            if (!succ) {
               throw new RuntimeException(watchTextFormatter.getUnableToCreateDirText(dumpDir.getAbsolutePath()));
            }
         }

         int limit = "ThreadDump".equals(dumpType) ? wldfDiag.getMaxThreadDumpCount() : wldfDiag.getMaxHeapDumpCount();
         String prefix = dumpType + "_";
         this.deleteOldDumpFiles(dumpDir, prefix, limit);
         Date now = new Date();
         SimpleDateFormat tsFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
         String fname = prefix + serverName + "_" + moduleName + "_" + policyName + "_" + actionName + "_" + tsFormat.format(now) + "." + suffix;
         File dumpFile = new File(dumpDir, fname);
         return dumpFile;
      }
   }

   private void deleteOldDumpFiles(File dumpDir, final String prefix, int limit) {
      File[] existingDumps = dumpDir.listFiles(new FilenameFilter() {
         public boolean accept(File dir, String name) {
            return name.startsWith(prefix);
         }
      });
      int count = existingDumps != null ? existingDumps.length : 0;
      if (count >= limit) {
         Arrays.sort(existingDumps, new Comparator() {
            public int compare(File f1, File f2) {
               long modTime1 = f1.lastModified();
               long modTime2 = f2.lastModified();
               if (modTime1 > modTime2) {
                  return -1;
               } else {
                  return modTime1 < modTime2 ? 1 : 0;
               }
            }
         });

         for(int i = limit - 1; i < count; ++i) {
            File f = existingDumps[i];
            if (!f.delete()) {
               throw new RuntimeException(watchTextFormatter.getFailedToDeleteFileText(f.getAbsolutePath()));
            }

            DiagnosticsWatchLogger.logDumpFileRemoved(f.getAbsolutePath());
         }
      }

   }
}
