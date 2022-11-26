package weblogic.diagnostics.archive;

import weblogic.diagnostics.accessor.runtime.FileArchiveRuntimeMBean;
import weblogic.diagnostics.archive.filestore.FileDataArchive;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;

public class DiagnosticFileArchiveRuntime extends DiagnosticArchiveRuntime implements FileArchiveRuntimeMBean {
   public DiagnosticFileArchiveRuntime(FileDataArchive archive, RuntimeMBean parent) throws ManagementException {
      super(archive, parent);
   }

   public int getRotatedFilesCount() {
      return ((FileDataArchive)this.archive).getRotatedFilesCount();
   }

   public int getIndexCycleCount() {
      return ((FileDataArchive)this.archive).getIndexCycleCount();
   }

   public long getIndexTime() {
      return ((FileDataArchive)this.archive).getIndexTime() / 1000000L;
   }

   public int getIncrementalIndexCycleCount() {
      return ((FileDataArchive)this.archive).getIncrementalIndexCycleCount();
   }

   public long getIncrementalIndexTime() {
      return ((FileDataArchive)this.archive).getIncrementalIndexTime() / 1000000L;
   }
}
