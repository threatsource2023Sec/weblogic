package weblogic.diagnostics.accessor.runtime;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DiagnosticDataAccessService;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;

public interface DataAccessRuntimeMBean extends RuntimeMBean {
   long DEFAULT_CURSOR_TIMEOUT = 300000L;

   Iterator retrieveDataRecords(String var1) throws ManagementException;

   Iterator retrieveDataRecords(long var1, long var3, String var5) throws ManagementException;

   Iterator retrieveDataRecords(long var1, String var3) throws ManagementException;

   Iterator retrieveDataRecords(long var1, long var3, long var5, String var7) throws ManagementException;

   void streamDiagnosticData(OutputStream var1, long var2, long var4, String var6, String var7, long var8) throws ManagementException;

   void streamDiagnosticDataFromRecord(OutputStream var1, long var2, long var4, String var6, String var7, long var8) throws ManagementException;

   String openCursor(String var1) throws ManagementException;

   String openCursor(String var1, long var2) throws ManagementException;

   String openCursor(long var1, long var3, String var5) throws ManagementException;

   String openCursor(long var1, long var3, String var5, long var6) throws ManagementException;

   String openCursor(long var1, long var3, long var5, String var7) throws ManagementException;

   String openCursor(long var1, long var3, long var5, String var7, long var8) throws ManagementException;

   int getDataRecordCount(String var1) throws ManagementException;

   int getDataRecordCount(long var1, long var3, String var5) throws ManagementException;

   int getDataRecordCount(long var1, long var3, long var5, String var7) throws ManagementException;

   boolean hasMoreData(String var1) throws ManagementException;

   Object[] fetch(String var1) throws ManagementException;

   Object[] fetch(String var1, int var2) throws ManagementException;

   void closeCursor(String var1) throws ManagementException;

   ColumnInfo[] getColumns() throws ManagementException;

   Map getColumnInfoMap() throws ManagementException;

   Map getColumnTypeMap() throws ManagementException;

   Map getColumnIndexMap() throws ManagementException;

   long getEarliestAvailableTimestamp() throws ManagementException;

   long getLatestAvailableTimestamp() throws ManagementException;

   int deleteDataRecords(long var1, long var3, String var5) throws ManagementException;

   Map getDataArchiveParameters();

   void setDataArchiveParameters(Map var1);

   void closeArchive() throws ManagementException;

   void setDiagnosticDataAccessService(DiagnosticDataAccessService var1);

   long getLatestRecordId() throws ManagementException;

   boolean isTimestampAvailable() throws ManagementException;

   String openQueryResultDataStream(long var1, long var3, String var5, String var6) throws ManagementException;

   byte[] getNextQueryResultDataChunk(String var1) throws ManagementException;

   void closeQueryResultDataStream(String var1) throws ManagementException;
}
