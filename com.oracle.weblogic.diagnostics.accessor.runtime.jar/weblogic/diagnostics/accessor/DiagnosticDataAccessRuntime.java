package weblogic.diagnostics.accessor;

import weblogic.management.ManagementException;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;
import weblogic.management.runtime.WLDFDataAccessRuntimeMBean;

public class DiagnosticDataAccessRuntime extends DataAccessRuntime implements WLDFDataAccessRuntimeMBean, AccessorConstants {
   DiagnosticDataAccessRuntime(String name, WLDFAccessRuntimeMBean parentArg, DiagnosticDataAccessService service) throws ManagementException {
      super(name, parentArg, service);
   }
}
