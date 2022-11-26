package weblogic.diagnostics.archive;

import weblogic.diagnostics.archive.dbstore.JdbcDataArchive;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WLDFDataRetirementTaskRuntimeMBean;
import weblogic.management.runtime.WLDFDbstoreArchiveRuntimeMBean;

public class WLDFDiagnosticDbstoreArchiveRuntime extends DiagnosticDbstoreArchiveRuntime implements WLDFDbstoreArchiveRuntimeMBean {
   public WLDFDiagnosticDbstoreArchiveRuntime(JdbcDataArchive archive, RuntimeMBean parent) throws ManagementException {
      super(archive, parent);
   }

   public WLDFDataRetirementTaskRuntimeMBean performDataRetirement() throws ManagementException {
      WLDFDataRetirementTaskRuntimeMBean task = null;

      try {
         task = (WLDFDataRetirementTaskRuntimeMBean)super.performRetirement();
         return task;
      } catch (Exception var3) {
         throw new ManagementException(var3);
      }
   }
}
