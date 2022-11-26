package weblogic.diagnostics.archive.filestore;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;

public class ServerLogFileDataArchive extends FileDataArchive {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private static final byte[] RECORD_MARKER = "####".getBytes();
   private static final RecordParser RECORD_PARSER = new ServerLogRecordParser();

   public ServerLogFileDataArchive(String name, File archiveFile, File archiveDir, File indexStoreDir, boolean readOnly) throws IOException, ManagementException {
      super(name, ArchiveConstants.getColumns(3), archiveFile, archiveDir, indexStoreDir, RECORD_PARSER, RECORD_MARKER, true, readOnly);
   }

   public ServerLogFileDataArchive(String name, File archiveFile, File indexStoreDir, boolean readOnly) throws IOException, ManagementException {
      this(name, archiveFile, archiveFile.getParentFile(), indexStoreDir, readOnly);
   }

   public String getDescription() {
      return "Server Log";
   }

   private static void usage() {
      DebugLogger.println("java [-Dverbose=true] " + ServerLogFileDataArchive.class.getName() + " logFile indexStoreDirectory lowTimestamp highTimestamp [queryString]");
      DebugLogger.println("For example:");
      DebugLogger.println("java [-Dverbose=true] " + ServerLogFileDataArchive.class.getName() + " c:/mydomain/myserver/myserver.log c:/mydomain/servers/myserver/data/store/diagnostics 0 99999999999999 \"SUBSYSTEM LIKE '%Diagnostics%'\"");
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 4) {
         usage();
      } else {
         boolean verbose = Boolean.getBoolean("verbose");
         boolean buildIndex = Boolean.getBoolean("buildIndex");
         boolean byID = Boolean.getBoolean("byID");
         boolean printIndex = Boolean.getBoolean("printIndex");
         System.setProperty("_Offline_FileDataArchive", "true");
         File logFile = new File(args[0]);
         File indexStoreDir = new File(args[1]);
         ServerLogFileDataArchive archive = new ServerLogFileDataArchive("ServerLog", logFile, indexStoreDir, !buildIndex);
         long lo = Long.parseLong(args[2]);
         long hi = Long.parseLong(args[3]);
         String query = args.length > 4 ? args[4] : null;
         int cnt = 0;
         long t0 = System.currentTimeMillis();
         Iterator it;
         if (byID) {
            it = archive.getDataRecords(lo, hi, Long.MAX_VALUE, query);
         } else {
            it = archive.getDataRecords(lo, hi, query);
         }

         while(it.hasNext()) {
            DataRecord dataRecord = (DataRecord)it.next();
            ++cnt;
            if (verbose) {
               DebugLogger.println(dataRecord.toString());
            }
         }

         long t1 = System.currentTimeMillis();
         DebugLogger.println("Found " + cnt + " record(s) in " + (t1 - t0) + " ms");
         if (printIndex) {
            archive.printIndex();
         }

         archive.close();
      }
   }
}
