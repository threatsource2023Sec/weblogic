package weblogic.diagnostics.archive.filestore;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;

public final class AccessLogFileDataArchive extends FileDataArchive {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private int timestampColumnIndex;
   private static final byte[] RECORD_MARKER = "".getBytes();

   public AccessLogFileDataArchive(String name, File archiveFile, File archiveDir, File indexStoreDir, String dateFormat, ColumnInfo[] columns, int dateColumnIndex, int timestampColumnIndex, boolean readOnly) throws IOException, ManagementException {
      super(name, columns, archiveFile, archiveDir, indexStoreDir, new AccessLogRecordParser(dateFormat, timestampColumnIndex), RECORD_MARKER, true, readOnly);
      this.timestampColumnIndex = 4;
      this.timestampColumnIndex = timestampColumnIndex;
   }

   public AccessLogFileDataArchive(String name, File archiveFile, File archiveDir, File indexStoreDir, String dateFormat, ColumnInfo[] columns, int timestampColumnIndex, boolean readOnly) throws IOException, ManagementException {
      this(name, archiveFile, archiveDir, indexStoreDir, dateFormat, columns, -1, timestampColumnIndex, readOnly);
   }

   public AccessLogFileDataArchive(String name, File archiveFile, File archiveDir, File indexStoreDir, String dateFormat, boolean readOnly) throws IOException, ManagementException {
      this(name, archiveFile, archiveDir, indexStoreDir, dateFormat, ArchiveConstants.getColumns(4), 4, readOnly);
   }

   public AccessLogFileDataArchive(String name, File archiveFile, File archiveDir, File indexStoreDir, boolean readOnly) throws IOException, ManagementException {
      this(name, archiveFile, archiveDir, indexStoreDir, "dd/MMM/yyyy:HH:mm:ss Z", readOnly);
   }

   public AccessLogFileDataArchive(String name, File archiveFile, File archiveDir, File indexStoreDir, String[] columnNames, boolean readOnly) throws IOException, ManagementException {
      super(name, getCols(columnNames), archiveFile, archiveDir, indexStoreDir, new AccessLogRecordParser(), RECORD_MARKER, false, readOnly);
      this.timestampColumnIndex = 4;
   }

   private static ColumnInfo[] getCols(String[] columnNames) {
      int size = (columnNames != null ? columnNames.length : 0) + 1;
      ColumnInfo[] cols = new ColumnInfo[size];
      cols[0] = new ColumnInfo("RECORDID", 2);

      for(int i = 1; i < size; ++i) {
         cols[i] = new ColumnInfo(columnNames[i - 1], 5);
      }

      return cols;
   }

   public String getDescription() {
      return "HTTP Access Log";
   }

   private static void usage() {
      DebugLogger.println("java [-Dverbose=true] " + AccessLogFileDataArchive.class.getName() + " logFile indexStoreDirectory lowTimestamp highTimestamp dateDormat [queryString]");
      DebugLogger.println("For example:");
      DebugLogger.println("java [-Dverbose=true] " + AccessLogFileDataArchive.class.getName() + " c:/mydomain/myserver/access.log c:/mydomain/servers/myserver/data/store/diagnostics 0 99999999999999 \"dd/MMM/yyyy:HH:mm:ss Z\" \"STATUS = 200\"");
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 5) {
         usage();
      } else {
         File logFile = new File(args[0]);
         File indexStoreDir = new File(args[1]);
         long startTime = Long.parseLong(args[2]);
         long endTime = Long.parseLong(args[3]);
         String dateFormat = args[4];
         String query = args.length > 5 ? args[5] : null;
         boolean verbose = Boolean.getBoolean("verbose");
         AccessLogFileDataArchive archive = new AccessLogFileDataArchive("HTTPAccessLog", logFile, logFile.getParentFile(), indexStoreDir, dateFormat, true);
         int cnt = 0;
         long t0 = System.currentTimeMillis();
         Iterator it = archive.getDataRecords(startTime, endTime, query);

         while(it.hasNext()) {
            DataRecord dataRecord = (DataRecord)it.next();
            ++cnt;
            if (verbose) {
               DebugLogger.println(dataRecord.toString());
            }
         }

         long t1 = System.currentTimeMillis();
         DebugLogger.println("Found " + cnt + " record(s) in " + (t1 - t0) + " ms");
         archive.close();
      }
   }
}
