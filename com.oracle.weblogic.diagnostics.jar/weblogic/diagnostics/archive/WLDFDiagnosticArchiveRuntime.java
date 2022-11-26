package weblogic.diagnostics.archive;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WLDFArchiveRuntimeMBean;

public class WLDFDiagnosticArchiveRuntime extends DiagnosticArchiveRuntime implements WLDFArchiveRuntimeMBean {
   public static final long NANOS_PER_MILLI = 1000000L;

   public WLDFDiagnosticArchiveRuntime(DataArchive archive, RuntimeMBean parent) throws ManagementException {
      super(archive, parent);
   }
}
