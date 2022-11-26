package weblogic.diagnostics.archive;

import weblogic.diagnostics.archive.filestore.FileDataArchive;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WLDFFileArchiveRuntimeMBean;

public class WLDFDiagnosticFileArchiveRuntime extends DiagnosticFileArchiveRuntime implements WLDFFileArchiveRuntimeMBean {
   public WLDFDiagnosticFileArchiveRuntime(FileDataArchive archive, RuntimeMBean parent) throws ManagementException {
      super(archive, parent);
   }
}
