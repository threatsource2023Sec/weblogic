package weblogic.diagnostics.archive;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WLDFDataRetirementTaskRuntimeMBean;
import weblogic.management.runtime.WLDFEditableArchiveRuntimeMBean;

public class WLDFDiagnosticEditableArchiveRuntime extends DiagnosticEditableArchiveRuntime implements WLDFEditableArchiveRuntimeMBean {
   public WLDFDiagnosticEditableArchiveRuntime(EditableDataArchive archive, RuntimeMBean parent) throws ManagementException {
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
