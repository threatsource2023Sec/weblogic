package weblogic.diagnostics.archive;

import weblogic.diagnostics.accessor.ColumnInfo;

public final class ArchiveConstants {
   public static final String PARTITION_ID_COL_NAME = "PARTITION_ID";
   public static final String PARTITION_NAME_COL_NAME = "PARTITION_NAME";
   public static final String CONNECTION_NAME_PREFIX = "weblogic.diagnostics.";
   public static final int ARCHIVE_EVENTS = 1;
   public static final int ARCHIVE_HARVESTER = 2;
   public static final int ARCHIVE_SERVERLOG = 3;
   public static final int ARCHIVE_ACCESSLOG = 4;
   public static final int ARCHIVE_UNFORMATTEDLOG = 5;
   public static final int ARCHIVE_JMSLOG = 6;
   public static final int ARCHIVE_DATASOURCELOG = 7;
   public static final String EVENTS_ARCHIVE_DESCRIPTION = "Diagnostic Events";
   public static final String EVENTS_ARCHIVE_NAME = "WLS_EVENTS";
   private static final ColumnInfo[] EVENTS_ARCHIVE_COLUMNS = new ColumnInfo[]{new ColumnInfo("RECORDID", 2), new ColumnInfo("TIMESTAMP", 2), new ColumnInfo("CONTEXTID", 5), new ColumnInfo("TXID", 5), new ColumnInfo("USERID", 5), new ColumnInfo("TYPE", 5), new ColumnInfo("DOMAIN", 5), new ColumnInfo("SERVER", 5), new ColumnInfo("SCOPE", 5), new ColumnInfo("MODULE", 5), new ColumnInfo("MONITOR", 5), new ColumnInfo("FILENAME", 5), new ColumnInfo("LINENUM", 1), new ColumnInfo("CLASSNAME", 5), new ColumnInfo("METHODNAME", 5), new ColumnInfo("METHODDSC", 5), new ColumnInfo("ARGUMENTS", 5), new ColumnInfo("RETVAL", 5), new ColumnInfo("PAYLOAD", 6), new ColumnInfo("CTXPAYLOAD", 5), new ColumnInfo("DYES", 2), new ColumnInfo("THREADNAME", 5), new ColumnInfo("PARTITION_ID", 5), new ColumnInfo("PARTITION_NAME", 5)};
   public static final int EVENTS_ARCHIVE_PARTITIONID_COLUMN_INDEX = 22;
   public static final int EVENTS_ARCHIVE_COLUMNS_COUNT;
   public static final String HARVESTER_ARCHIVE_DESCRIPTION = "Harvested diagnostic data";
   public static final String HARVESTER_ARCHIVE_NAME = "WLS_HVST";
   private static final ColumnInfo[] HARVESTER_ARCHIVE_COLUMNS;
   public static final int HARVESTER_ARCHIVE_COLUMNS_COUNT;
   public static final String SERVERLOG_ARCHIVE_DESCRIPTION = "Server Log";
   private static final ColumnInfo[] SERVERLOG_ARCHIVE_COLUMNS;
   public static final int SERVERLOG_ARCHIVE_COLUMNS_COUNT;
   public static final int SERVERLOG_COL_TIMESTAMP = 10;
   public static final String ACCESSLOG_ARCHIVE_DESCRIPTION = "HTTP Access Log";
   private static final ColumnInfo[] ACCESSLOG_ARCHIVE_COLUMNS;
   public static final int ACCESSLOG_ARCHIVE_COLUMNS_COUNT;
   public static final int ACCESSLOG_COL_TIMESTAMP = 4;
   public static final String ACCESSLOG_DEFAULT_DATE_FORMAT = "dd/MMM/yyyy:HH:mm:ss Z";
   public static final String UNFORMATTEDLOG_ARCHIVE_DESCRIPTION = "Unformatted Log";
   private static final ColumnInfo[] UNFORMATTEDLOG_ARCHIVE_COLUMNS;
   public static final int UNFORMATTEDLOG_ARCHIVE_COLUMNS_COUNT;
   public static final String JMSLOG_ARCHIVE_DESCRIPTION = "JMS Log";
   private static final ColumnInfo[] JMSLOG_ARCHIVE_COLUMNS;
   public static final int JMSLOG_ARCHIVE_COLUMNS_COUNT;
   public static final int JMSLOG_COL_TIMESTAMP = 4;
   public static final String DATASOURCELOG_ARCHIVE_DESCRIPTION = "DATASOURCE Log";
   private static final ColumnInfo[] DATASOURCELOG_ARCHIVE_COLUMNS;
   public static final int DATASOURCELOG_ARCHIVE_COLUMNS_COUNT;
   public static final int DATASOURCELOG_COL_TIMESTAMP = 3;

   public static ColumnInfo[] getColumns(int archiveType) {
      ColumnInfo[] columns = null;
      switch (archiveType) {
         case 1:
            columns = EVENTS_ARCHIVE_COLUMNS;
            break;
         case 2:
            columns = HARVESTER_ARCHIVE_COLUMNS;
            break;
         case 3:
            columns = SERVERLOG_ARCHIVE_COLUMNS;
            break;
         case 4:
            columns = ACCESSLOG_ARCHIVE_COLUMNS;
            break;
         case 5:
            columns = UNFORMATTEDLOG_ARCHIVE_COLUMNS;
            break;
         case 6:
            columns = JMSLOG_ARCHIVE_COLUMNS;
            break;
         case 7:
            columns = DATASOURCELOG_ARCHIVE_COLUMNS;
            break;
         default:
            return null;
      }

      ColumnInfo[] out = new ColumnInfo[columns.length];
      System.arraycopy(columns, 0, out, 0, columns.length);
      return out;
   }

   public static int getColumnIndex(int archiveType, String columnName) {
      ColumnInfo[] columnInfos = getColumns(archiveType);
      if (columnInfos == null) {
         return -1;
      } else {
         for(int i = 0; i < columnInfos.length; ++i) {
            if (columnInfos[i].getColumnName().equals(columnName)) {
               return i;
            }
         }

         return -1;
      }
   }

   static {
      EVENTS_ARCHIVE_COLUMNS_COUNT = EVENTS_ARCHIVE_COLUMNS.length;
      HARVESTER_ARCHIVE_COLUMNS = new ColumnInfo[]{new ColumnInfo("RECORDID", 2), new ColumnInfo("TIMESTAMP", 2), new ColumnInfo("DOMAIN", 5), new ColumnInfo("SERVER", 5), new ColumnInfo("TYPE", 5), new ColumnInfo("NAME", 5), new ColumnInfo("ATTRNAME", 5), new ColumnInfo("ATTRTYPE", 1), new ColumnInfo("ATTRVALUE", 6), new ColumnInfo("WLDFMODULE", 5), new ColumnInfo("PARTITION_ID", 5), new ColumnInfo("PARTITION_NAME", 5)};
      HARVESTER_ARCHIVE_COLUMNS_COUNT = HARVESTER_ARCHIVE_COLUMNS.length;
      SERVERLOG_ARCHIVE_COLUMNS = new ColumnInfo[]{new ColumnInfo("RECORDID", 2), new ColumnInfo("DATE", 5), new ColumnInfo("SEVERITY", 5), new ColumnInfo("SUBSYSTEM", 5), new ColumnInfo("MACHINE", 5), new ColumnInfo("SERVER", 5), new ColumnInfo("THREAD", 5), new ColumnInfo("USERID", 5), new ColumnInfo("TXID", 5), new ColumnInfo("CONTEXTID", 5), new ColumnInfo("TIMESTAMP", 2), new ColumnInfo("MSGID", 5), new ColumnInfo("MESSAGE", 5), new ColumnInfo("SUPP_ATTRS", 5), new ColumnInfo("SEVERITY_VALUE", 1), new ColumnInfo("PARTITION_ID", 5), new ColumnInfo("PARTITION_NAME", 5), new ColumnInfo("RID", 5)};
      SERVERLOG_ARCHIVE_COLUMNS_COUNT = SERVERLOG_ARCHIVE_COLUMNS.length;
      ACCESSLOG_ARCHIVE_COLUMNS = new ColumnInfo[]{new ColumnInfo("RECORDID", 2), new ColumnInfo("HOST", 5), new ColumnInfo("REMOTEUSER", 5), new ColumnInfo("AUTHUSER", 5), new ColumnInfo("TIMESTAMP", 5), new ColumnInfo("REQUEST", 5), new ColumnInfo("STATUS", 5), new ColumnInfo("BYTECOUNT", 5)};
      ACCESSLOG_ARCHIVE_COLUMNS_COUNT = ACCESSLOG_ARCHIVE_COLUMNS.length;
      UNFORMATTEDLOG_ARCHIVE_COLUMNS = new ColumnInfo[]{new ColumnInfo("RECORDID", 2), new ColumnInfo("LINE", 5)};
      UNFORMATTEDLOG_ARCHIVE_COLUMNS_COUNT = UNFORMATTEDLOG_ARCHIVE_COLUMNS.length;
      JMSLOG_ARCHIVE_COLUMNS = new ColumnInfo[]{new ColumnInfo("RECORDID", 2), new ColumnInfo("DATE", 5), new ColumnInfo("TXID", 5), new ColumnInfo("CONTEXTID", 5), new ColumnInfo("TIMESTAMP", 2), new ColumnInfo("NANOTIMESTAMP", 2), new ColumnInfo("JMSMESSAGEID", 5), new ColumnInfo("JMSCORRELATIONID", 5), new ColumnInfo("DESTINATION", 5), new ColumnInfo("EVENT", 5), new ColumnInfo("USERID", 5), new ColumnInfo("MESSAGECONSUMER", 5), new ColumnInfo("MESSAGE", 5), new ColumnInfo("SELECTOR", 5)};
      JMSLOG_ARCHIVE_COLUMNS_COUNT = JMSLOG_ARCHIVE_COLUMNS.length;
      DATASOURCELOG_ARCHIVE_COLUMNS = new ColumnInfo[]{new ColumnInfo("RECORDID", 2), new ColumnInfo("DATASOURCE", 5), new ColumnInfo("PROFILETYPE", 5), new ColumnInfo("TIMESTAMP", 5), new ColumnInfo("USER", 5), new ColumnInfo("PROFILEINFORMATION", 5), new ColumnInfo("SUPP_ATTRS", 5), new ColumnInfo("PARTITION_ID", 5), new ColumnInfo("PARTITION_NAME", 5)};
      DATASOURCELOG_ARCHIVE_COLUMNS_COUNT = DATASOURCELOG_ARCHIVE_COLUMNS.length;
   }
}
