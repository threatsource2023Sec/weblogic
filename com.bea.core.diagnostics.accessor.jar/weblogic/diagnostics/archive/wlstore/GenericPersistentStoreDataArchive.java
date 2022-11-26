package weblogic.diagnostics.archive.wlstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.accessor.DiagnosticDataAccessException;
import weblogic.diagnostics.archive.DiagnosticStoreRepository;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.query.QueryException;
import weblogic.management.ManagementException;
import weblogic.store.PersistentStoreException;

public class GenericPersistentStoreDataArchive extends PersistentStoreDataArchive {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private static final String CUSTOM_ARCHIVE_PREFIX = "CUSTOM/";
   private ColumnInfo[] columns;
   private int recordIdColumnIndex;
   private int timestampColumnIndex;
   private static final ColumnInfo[] ARCHIVE_COLUMNS = new ColumnInfo[]{new ColumnInfo("RECORDID", 2), new ColumnInfo("TIMESTAMP", 2), new ColumnInfo("ALERTID", 5), new ColumnInfo("USERID", 5), new ColumnInfo("RULEPATHELEMENT", 5), new ColumnInfo("SEVERITY", 5), new ColumnInfo("BODY", 5)};

   public GenericPersistentStoreDataArchive(String name, ColumnInfo[] columns, String storeDirectory, boolean readOnly) throws PersistentStoreException, ManagementException {
      super(validateName(name), columns, name, storeDirectory, true, readOnly);
      this.columns = this.getColumns();
      this.recordIdColumnIndex = getColumnIndex(name, this.columns, "RECORDID");
      this.timestampColumnIndex = getColumnIndex(name, this.columns, "TIMESTAMP");
      if (this.columns != null) {
         int var10000 = this.columns.length;
      } else {
         boolean var8 = false;
      }

      ColumnInfo col1 = this.recordIdColumnIndex == 0 ? this.columns[0] : null;
      ColumnInfo col2 = this.timestampColumnIndex == 1 ? this.columns[1] : null;
      if (!checkColumn(col1, "RECORDID")) {
         throw new PersistentStoreException("Missing first column RECORDID of type COLTYPE_LONG");
      } else if (!checkColumn(col2, "TIMESTAMP")) {
         throw new PersistentStoreException("Missing second column TIMESTAMP of type COLTYPE_LONG");
      }
   }

   private static String validateName(String name) throws ManagementException {
      if (name != null && name.startsWith("CUSTOM/")) {
         return name;
      } else {
         throw new ManagementException("User defined archive name must start with: CUSTOM/");
      }
   }

   private static int getColumnIndex(String name, ColumnInfo[] columns, String colName) {
      int index = -1;

      for(int i = 0; index < 0 && i < columns.length; ++i) {
         if (colName.equals(columns[i].getColumnName())) {
            index = i;
         }
      }

      return index;
   }

   private static boolean checkColumn(ColumnInfo col, String colName) {
      return col != null && col.getColumnName().equals(colName) && col.getColumnType() == 2;
   }

   public GenericPersistentStoreDataArchive(String name, String storeDirectory, boolean readOnly) throws PersistentStoreException, ManagementException {
      this(name, (ColumnInfo[])null, storeDirectory, readOnly);
   }

   public String getDescription() {
      return "GenericPersistentStoreDataArchive-" + this.getName();
   }

   public Iterator getDataRecords(String query) throws QueryException, DiagnosticDataAccessException {
      return this.getDataRecords(0L, Long.MAX_VALUE, query);
   }

   public Iterator getDataRecords(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return new GenericPersistentRecordIterator(this, startTime, endTime, query);
   }

   public Iterator getDataRecords(long startRecordId, long endrecordId, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return new GenericPersistentRecordIterator(this, startRecordId, endrecordId, endTime, query);
   }

   private static ArrayList createEvents() {
      ArrayList list = new ArrayList();

      for(int i = 0; i < 5; ++i) {
         Object[] data = new Object[ARCHIVE_COLUMNS.length];
         int cnt = 0;
         data[cnt++] = null;
         data[cnt++] = System.currentTimeMillis();
         data[cnt++] = "ALERT-" + i;
         data[cnt++] = "ALERTRULE-" + i;
         data[cnt++] = "ALERTPATHELE-" + i;
         data[cnt++] = "SEVERITY-" + i;
         data[cnt++] = "BODY-" + i;
         list.add(new DataRecord(data));
      }

      return list;
   }

   public static void main(String[] args) throws Exception {
      String archiveName = args[0];
      String storeDir = args[1];
      long lo = Long.parseLong(args[2]);
      long hi = Long.parseLong(args[3]);
      String query = args.length > 4 ? args[4] : null;
      boolean verbose = Boolean.getBoolean("verbose");
      boolean doInsert = Boolean.getBoolean("doInsert");
      boolean doDelete = Boolean.getBoolean("doDelete");
      boolean doUpdate = Boolean.getBoolean("doUpdate");
      boolean doCompact = Boolean.getBoolean("doCompact");
      boolean byID = Boolean.getBoolean("byID");
      String updateSpec = System.getProperty("updateSpec");
      if (!doInsert && !DiagnosticStoreRepository.storeFileExists(storeDir)) {
         DebugLogger.println("Specified store directory " + storeDir + " does not exist or does not contain the diagnostic store file.");
      } else {
         GenericPersistentStoreDataArchive archive = null;
         if (doInsert) {
            archive = new GenericPersistentStoreDataArchive(archiveName, ARCHIVE_COLUMNS, storeDir, false);
         } else if (!doDelete && !doUpdate && !doCompact) {
            archive = new GenericPersistentStoreDataArchive(archiveName, (ColumnInfo[])null, storeDir, true);
         } else {
            archive = new GenericPersistentStoreDataArchive(archiveName, (ColumnInfo[])null, storeDir, false);
         }

         if (doInsert) {
            archive.writeData(createEvents());
         } else {
            if (doUpdate) {
               HashMap changeMap = new HashMap();
               String[] keyValPairs = updateSpec.split(",");

               for(int i = 0; i < keyValPairs.length; ++i) {
                  String[] pair = keyValPairs[i].split("=");
                  changeMap.put(pair[0], pair[1]);
               }

               archive.updateRecord(lo, changeMap);
               DebugLogger.println("Record updated");
               return;
            }

            if (doCompact) {
               DebugLogger.println("Compacting archive: " + archive.getName());
               long t0 = System.currentTimeMillis();
               int[] stats = archive.compact();
               long t1 = System.currentTimeMillis();
               DebugLogger.println("Deleted " + stats[0] + " Updated " + stats[1] + " snapshots in " + (t1 - t0) + " ms");
               return;
            }
         }

         ColumnInfo[] columns = archive.getColumns();

         int cnt;
         for(cnt = 0; cnt < columns.length; ++cnt) {
            DebugLogger.println(columns[cnt].toString());
         }

         cnt = 0;
         long t0 = System.currentTimeMillis();
         if (doDelete) {
            cnt = archive.deleteDataRecords(lo, hi, query);
         } else {
            Iterator it;
            if (byID) {
               it = archive.getDataRecords(lo, hi, Long.MAX_VALUE, query);
            } else {
               it = archive.getDataRecords(lo, hi, query);
            }

            for(; it.hasNext(); ++cnt) {
               Object obj = it.next();
               if (verbose) {
                  DebugLogger.println("RECORD>>>> " + obj);
               }
            }
         }

         long t1 = System.currentTimeMillis();
         if (doDelete) {
            DebugLogger.println("Deleted " + cnt + " matching records in " + (t1 - t0) + " ms");
         } else {
            DebugLogger.println("Found " + cnt + " matches in " + (t1 - t0) + " ms");
         }

         archive.close();
      }
   }
}
