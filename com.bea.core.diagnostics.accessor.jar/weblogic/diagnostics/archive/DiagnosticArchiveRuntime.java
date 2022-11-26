package weblogic.diagnostics.archive;

import weblogic.diagnostics.accessor.runtime.ArchiveRuntimeMBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class DiagnosticArchiveRuntime extends RuntimeMBeanDelegate implements ArchiveRuntimeMBean {
   protected DataArchive archive;
   public static final long NANOS_PER_MILLI = 1000000L;

   public DiagnosticArchiveRuntime(DataArchive archive, RuntimeMBean parent) throws ManagementException {
      super(archive.getName(), parent, true, "WLDFArchiveRuntimes");
      this.archive = archive;
   }

   public long getRecordSeekCount() {
      return this.archive.getRecordSeekCount();
   }

   public long getRecordSeekTime() {
      return this.archive.getRecordSeekTime() / 1000000L;
   }

   public long getRetrievedRecordCount() {
      return this.archive.getRetrievedRecordCount();
   }

   public long getRecordRetrievalTime() {
      return this.archive.getRecordRetrievalTime() / 1000000L;
   }
}
