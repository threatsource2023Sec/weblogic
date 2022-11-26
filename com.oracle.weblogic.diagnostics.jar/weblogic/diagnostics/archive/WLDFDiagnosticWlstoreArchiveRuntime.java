package weblogic.diagnostics.archive;

import weblogic.diagnostics.archive.wlstore.PersistentStoreDataArchive;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WLDFDataRetirementTaskRuntimeMBean;
import weblogic.management.runtime.WLDFWlstoreArchiveRuntimeMBean;

public class WLDFDiagnosticWlstoreArchiveRuntime extends DiagnosticWlstoreArchiveRuntime implements WLDFWlstoreArchiveRuntimeMBean {
   public WLDFDiagnosticWlstoreArchiveRuntime(PersistentStoreDataArchive archive, RuntimeMBean parent) throws ManagementException {
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
