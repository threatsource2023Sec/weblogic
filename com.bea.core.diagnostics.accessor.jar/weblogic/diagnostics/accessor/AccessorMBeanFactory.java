package weblogic.diagnostics.accessor;

import java.util.Map;
import weblogic.diagnostics.accessor.runtime.AccessRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.ArchiveRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.DataRetirementTaskRuntimeMBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;

public interface AccessorMBeanFactory {
   String[] getAvailableDiagnosticDataAccessorNames() throws ManagementException;

   AccessRuntimeMBean createDiagnosticAccessRuntime(AccessorConfigurationProvider var1, AccessorSecurityProvider var2, RuntimeMBean var3) throws ManagementException;

   DataAccessRuntimeMBean createDiagnosticDataAccessRuntime(String var1, ColumnInfo[] var2, AccessRuntimeMBean var3) throws ManagementException;

   DiagnosticDataAccessService createDiagnosticDataAccessService(String var1, String var2, ColumnInfo[] var3, Map var4) throws UnknownLogTypeException, DataAccessServiceCreateException;

   ArchiveRuntimeMBean createDiagnosticArchiveRuntime(DiagnosticDataAccessService var1) throws ManagementException;

   void destroyDiagnosticArchiveRuntime(ArchiveRuntimeMBean var1) throws ManagementException;

   DataRetirementTaskRuntimeMBean createRetirementByAgeTask(DiagnosticDataAccessService var1, long var2) throws ManagementException;
}
