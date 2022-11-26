package weblogic.diagnostics.archive.filestore;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;

public class JMSLogFileDataArchive extends FileDataArchive {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private static final byte[] RECORD_MARKER = "####".getBytes();
   private static final RecordParser RECORD_PARSER = new JMSLogRecordParser();

   public JMSLogFileDataArchive(String name, File archiveFile, File archiveDir, File indexStoreDir, boolean readOnly) throws IOException, ManagementException {
      super(name, ArchiveConstants.getColumns(6), archiveFile, archiveDir, indexStoreDir, RECORD_PARSER, RECORD_MARKER, true, readOnly);
   }

   public JMSLogFileDataArchive(String name, File archiveFile, File indexStoreDir, boolean readOnly) throws IOException, ManagementException {
      this(name, archiveFile, archiveFile.getParentFile(), indexStoreDir, readOnly);
   }

   public String getDescription() {
      return "JMS Log";
   }

   private static void usage() {
      DebugLogger.println("java [-Dverbose=true] " + JMSLogFileDataArchive.class.getName() + " logFile indexStoreDirectory lowTimestamp highTimestamp [queryString]");
      DebugLogger.println("For example:");
      DebugLogger.println("java [-Dverbose=true] " + JMSLogFileDataArchive.class.getName() + " c:/mydomain/myserver/jms.log c:/mydomain/servers/myserver/data/store/diagnostics 0 99999999999999 \"DESTINATION = 'myDestonation'\"");
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 4) {
         usage();
      } else {
         File logFile = new File(args[0]);
         File indexStoreDir = new File(args[1]);
         JMSLogFileDataArchive archive = new JMSLogFileDataArchive("JMSMessageLog", logFile, indexStoreDir, true);
         long startTime = Long.parseLong(args[2]);
         long endTime = Long.parseLong(args[3]);
         String query = args.length > 4 ? args[4] : null;
         boolean verbose = Boolean.getBoolean("verbose");
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
