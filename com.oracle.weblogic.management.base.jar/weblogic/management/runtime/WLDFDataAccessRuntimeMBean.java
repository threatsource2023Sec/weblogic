package weblogic.management.runtime;

import java.io.OutputStream;
import weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean;
import weblogic.management.ManagementException;

public interface WLDFDataAccessRuntimeMBean extends DataAccessRuntimeMBean {
   void streamDiagnosticData(OutputStream var1, long var2, long var4, String var6, String var7, long var8) throws ManagementException;

   void streamDiagnosticDataFromRecord(OutputStream var1, long var2, long var4, String var6, String var7, long var8) throws ManagementException;
}
