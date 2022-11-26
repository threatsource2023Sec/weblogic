package weblogic.diagnostics.accessor;

import weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;
import weblogic.management.runtime.WLDFDataAccessRuntimeMBean;

public class DiagnosticAccessRuntime extends AccessRuntime implements WLDFAccessRuntimeMBean, AccessorConstants {
   private static final DebugLogger ACCESSOR_DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");

   public static DiagnosticAccessRuntime getInstance() throws ManagementException {
      try {
         return (DiagnosticAccessRuntime)AccessRuntime.getAccessorInstance();
      } catch (ClassCastException var1) {
         throw new ManagementException(var1);
      }
   }

   DiagnosticAccessRuntime(AccessorMBeanFactory accessorFactory, AccessorConfigurationProvider confProvider, AccessorSecurityProvider securityProvider, RuntimeMBean parent) throws ManagementException {
      super(accessorFactory, confProvider, securityProvider, parent);
   }

   public WLDFDataAccessRuntimeMBean lookupWLDFDataAccessRuntime(String logicalName) throws ManagementException {
      return this.lookupWLDFDataAccessRuntime(logicalName, (ColumnInfo[])null);
   }

   public WLDFDataAccessRuntimeMBean lookupWLDFDataAccessRuntime(String logicalName, ColumnInfo[] columns) throws ManagementException {
      try {
         return (WLDFDataAccessRuntimeMBean)super.lookupDataAccessRuntime(logicalName, columns);
      } catch (ClassCastException var4) {
         throw new ManagementException(var4);
      }
   }

   public WLDFDataAccessRuntimeMBean[] getWLDFDataAccessRuntimes() throws ManagementException {
      try {
         DataAccessRuntimeMBean[] arr = super.getDataAccessRuntimes();
         int size = arr != null ? arr.length : 0;
         WLDFDataAccessRuntimeMBean[] dars = new WLDFDataAccessRuntimeMBean[size];
         System.arraycopy(arr, 0, dars, 0, size);
         return dars;
      } catch (ClassCastException var4) {
         throw new ManagementException(var4);
      }
   }
}
