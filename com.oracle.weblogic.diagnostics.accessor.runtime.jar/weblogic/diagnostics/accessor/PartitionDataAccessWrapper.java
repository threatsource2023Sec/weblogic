package weblogic.diagnostics.accessor;

import java.util.Iterator;
import weblogic.diagnostics.query.QueryException;
import weblogic.management.ManagementException;

public class PartitionDataAccessWrapper implements DiagnosticDataAccessService {
   private DiagnosticDataAccessService delegate;
   private String queryModifier = "";

   PartitionDataAccessWrapper(DiagnosticDataAccessService archive, String partitionId) {
      this.delegate = archive;
   }

   public String getName() {
      return this.delegate.getName();
   }

   public String getDescription() {
      return this.delegate.getDescription();
   }

   public ColumnInfo[] getColumns() {
      return this.delegate.getColumns();
   }

   public long getEarliestAvailableTimestamp() {
      return this.delegate.getEarliestAvailableTimestamp();
   }

   public long getLatestAvailableTimestamp() {
      return this.delegate.getLatestAvailableTimestamp();
   }

   public String[] getAttributeNames() {
      return this.delegate.getAttributeNames();
   }

   public Object getAttribute(String attributeName, Object[] params) throws InvalidParameterException, MissingParameterException {
      return this.delegate.getAttribute(attributeName, params);
   }

   public Iterator getDataRecords(String query) throws QueryException, DiagnosticDataAccessException {
      return this.delegate.getDataRecords(this.normalizeQuery(query));
   }

   public Iterator getDataRecords(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.delegate.getDataRecords(startTime, endTime, this.normalizeQuery(query));
   }

   public Iterator getDataRecords(long startRecordId, long endrecordId, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.delegate.getDataRecords(startRecordId, endrecordId, endTime, this.normalizeQuery(query));
   }

   public int getDataRecordCount(String query) throws QueryException, DiagnosticDataAccessException {
      return this.delegate.getDataRecordCount(this.normalizeQuery(query));
   }

   public int getDataRecordCount(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.delegate.getDataRecordCount(startTime, endTime, this.normalizeQuery(query));
   }

   public int getDataRecordCount(long startRecordId, long endRecordId, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.delegate.getDataRecordCount(startRecordId, endRecordId, endTime, this.normalizeQuery(query));
   }

   public int deleteDataRecords(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.delegate.deleteDataRecords(startTime, endTime, this.normalizeQuery(query));
   }

   public void close() throws DiagnosticDataAccessException, ManagementException {
      this.delegate.close();
   }

   public long getLatestKnownRecordID() throws DiagnosticDataAccessException {
      return this.delegate.getLatestKnownRecordID();
   }

   public boolean isTimestampAvailable() {
      return this.delegate.isTimestampAvailable();
   }

   private String normalizeQuery(String query) {
      if (query == null) {
         query = "";
      }

      if (!this.queryModifier.isEmpty()) {
         StringBuilder sb = new StringBuilder();
         sb.append(query);
         if (!query.isEmpty()) {
            sb.append(" AND ");
         }

         sb.append(this.queryModifier);
         return sb.toString();
      } else {
         return query;
      }
   }

   DiagnosticDataAccessService getDelegate() {
      return this.delegate;
   }

   void setDelegate(DiagnosticDataAccessService archive) {
      this.delegate = archive;
   }

   String getQueryModifier() {
      return this.queryModifier;
   }

   void setQueryModifier(String queryModifier) {
      this.queryModifier = queryModifier;
   }
}
