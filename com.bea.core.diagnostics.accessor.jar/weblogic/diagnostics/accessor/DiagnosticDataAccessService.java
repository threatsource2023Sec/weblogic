package weblogic.diagnostics.accessor;

import java.util.Iterator;
import weblogic.diagnostics.query.QueryException;
import weblogic.management.ManagementException;

public interface DiagnosticDataAccessService {
   String getName();

   String getDescription();

   ColumnInfo[] getColumns();

   long getEarliestAvailableTimestamp();

   long getLatestAvailableTimestamp();

   String[] getAttributeNames();

   Object getAttribute(String var1, Object[] var2) throws InvalidParameterException, MissingParameterException;

   Iterator getDataRecords(String var1) throws QueryException, DiagnosticDataAccessException;

   Iterator getDataRecords(long var1, long var3, String var5) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException;

   Iterator getDataRecords(long var1, long var3, long var5, String var7) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException;

   int getDataRecordCount(String var1) throws QueryException, DiagnosticDataAccessException;

   int getDataRecordCount(long var1, long var3, String var5) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException;

   int getDataRecordCount(long var1, long var3, long var5, String var7) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException;

   int deleteDataRecords(long var1, long var3, String var5) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException;

   void close() throws DiagnosticDataAccessException, ManagementException;

   long getLatestKnownRecordID() throws DiagnosticDataAccessException;

   boolean isTimestampAvailable();
}
