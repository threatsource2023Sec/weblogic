package weblogic.diagnostics.accessor.parser;

import weblogic.diagnostics.accessor.ColumnInfo;

public class DefaultRecordParser extends GenericRecordParser {
   private static final ColumnInfo[] SERVERLOG_ARCHIVE_COLUMNS = new ColumnInfo[]{new ColumnInfo("RECORDID", 2), new ColumnInfo("DATE", 5), new ColumnInfo("SEVERITY", 5), new ColumnInfo("SUBSYSTEM", 5), new ColumnInfo("MACHINE", 5), new ColumnInfo("SERVER", 5), new ColumnInfo("THREAD", 5), new ColumnInfo("USER_ID", 5), new ColumnInfo("TXID", 5), new ColumnInfo("CONTEXTID", 5), new ColumnInfo("TIMESTAMP", 2), new ColumnInfo("MSGID", 5), new ColumnInfo("MESSAGE", 5)};

   public DefaultRecordParser() {
      super(SERVERLOG_ARCHIVE_COLUMNS);
   }
}
