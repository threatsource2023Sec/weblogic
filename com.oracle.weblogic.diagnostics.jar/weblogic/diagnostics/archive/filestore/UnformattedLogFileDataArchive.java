package weblogic.diagnostics.archive.filestore;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;

public class UnformattedLogFileDataArchive extends FileDataArchive {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private static final byte[] RECORD_MARKER = "".getBytes();
   private static final RecordParser RECORD_PARSER = new UnformattedLogRecordParser();

   public UnformattedLogFileDataArchive(String name, File archiveFile, File archiveDir, File indexStoreDir, boolean readOnly) throws IOException, ManagementException {
      super(name, ArchiveConstants.getColumns(5), archiveFile, archiveDir, indexStoreDir, RECORD_PARSER, RECORD_MARKER, false, readOnly);
   }

   public UnformattedLogFileDataArchive(String name, File archiveFile, File indexStoreDir, boolean readOnly) throws IOException, ManagementException {
      this(name, archiveFile, archiveFile.getParentFile(), indexStoreDir, readOnly);
   }

   public String getDescription() {
      return "Unformatted Log";
   }

   private static void usage() {
      DebugLogger.println("java [-Dverbose=true] " + UnformattedLogFileDataArchive.class.getName() + " logName logFile indexStoreDirectory lowRecordId highRecordId [queryString]");
      DebugLogger.println("For example:");
      DebugLogger.println("java [-Dverbose=true] " + UnformattedLogFileDataArchive.class.getName() + " WebAppLog c:/mydomain/myserver/webapp.log c:/mydomain/servers/myserver/data/store/diagnostics 0 99999999999999 \"LINE LIKE '%Diagnostics%'\"");
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 5) {
         usage();
      } else {
         boolean verbose = Boolean.getBoolean("verbose");
         String logName = args[0];
         File logFile = new File(args[1]);
         File indexStoreDir = new File(args[2]);
         long startId = Long.parseLong(args[3]);
         long endId = Long.parseLong(args[4]);
         String query = args.length > 5 ? args[5] : null;
         UnformattedLogFileDataArchive archive = new UnformattedLogFileDataArchive(logName, logFile, logFile.getParentFile(), indexStoreDir, true);
         int cnt = 0;
         long t0 = System.currentTimeMillis();
         Iterator it = archive.getDataRecords(startId, endId, Long.MAX_VALUE, query);

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
