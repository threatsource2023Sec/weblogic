package weblogic.diagnostics.archive.filestore;

import java.io.File;
import java.io.IOException;
import weblogic.diagnostics.accessor.parser.LogRecordParser;
import weblogic.management.ManagementException;

public class GenericLogFileArchive extends FileDataArchive {
   public GenericLogFileArchive(String name, File archiveFile, File archiveDir, File indexStoreDir, LogRecordParser recordParser) throws IOException, ManagementException {
      super(name, recordParser.getColumnInfos(), archiveFile, archiveDir, indexStoreDir, recordParser, recordParser.getRecordMarker(), recordParser.getTimestampColumnIndex() >= 0, false);
   }

   public String getDescription() {
      return "GenericLogFileArchive";
   }
}
