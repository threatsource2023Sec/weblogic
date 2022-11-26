package weblogic.jdbc.common.internal;

public final class VendorId {
   public static final int ORACLE_XA = 0;
   public static final int INET_OPTA2000_XA = 2;
   public static final int SYBASE_XA = 4;
   public static final int POINTBASE_XA = 5;
   public static final int DB2_XA = 7;
   public static final int DDMSSQL_XA = 8;
   public static final int DDDB2_XA = 9;
   public static final int DDSYBASE_XA = 10;
   public static final int DDORACLE_XA = 11;
   public static final int FIRSTSQL_XA = 12;
   public static final int INFORMIX_XA = 13;
   public static final int DERBY_XA = 14;
   public static final int DB2_JCC_XA = 15;
   public static final int DDINFORMIX_XA = 16;
   public static final int MYSQL_XA = 17;
   public static final int ORACLE_THIN = 100;
   public static final int SYBASE = 102;
   public static final int POINTBASE = 103;
   public static final int DDMSSQL = 104;
   public static final int DB2 = 105;
   public static final int DDSYBASE = 106;
   public static final int DDORACLE = 107;
   public static final int FIRSTSQL = 108;
   public static final int DERBY = 109;
   public static final int INFORMIX = 110;
   public static final int MYSQL = 111;
   public static final int DDDB2 = 112;
   public static final int DDINFORMIX = 113;
   public static final int MISC = -1;
   private static final String ORACLE_XA_CLASSNAME = "oracle.jdbc.xa.client.OracleXADataSource";
   private static final String INET_OPTA2000_XA_CLASSNAME = "com.inet.tds.XDataSource";
   private static final String SYBASE_XA_CLASSNAME_END = "SybXADataSource";
   private static final String POINTBASE_XA_CLASSNAME = "com.pointbase.xa.xaDataSource";
   private static final String DB2_XA_CLASSNAME = "COM.ibm.db2.jdbc.DB2XADataSource";
   private static final String DDMSSQL_XA_CLASSNAME = "com.ddtek.jdbcx.sqlserver.SQLServerDataSource";
   private static final String WLDDMSSQL_XA_CLASSNAME = "weblogic.jdbcx.sqlserver.SQLServerDataSource";
   private static final String WLDDDB2_XA_CLASSNAME = "weblogic.jdbcx.db2.DB2DataSource";
   private static final String WLDDSYBASE_XA_CLASSNAME = "weblogic.jdbcx.sybase.SybaseDataSource";
   private static final String WLDDORACLE_XA_CLASSNAME = "weblogic.jdbcx.oracle.OracleDataSource";
   private static final String FIRSTSQL_XA_CLASSNAME = "COM.FirstSQL.Dbcp.DbcpXADataSource";
   private static final String INFORMIX_XA_CLASSNAME = "com.informix.jdbcx.IfxXADataSource";
   private static final String DERBY_XA_CLASSNAME = "org.apache.derby.jdbc.ClientXADataSource";
   private static final String DB2_JCC_XA_CLASSNAME = "com.ibm.db2.jcc.DB2XADataSource";
   private static final String DDINFORMIX_XA_CLASSNAME = "weblogic.jdbcx.informix.InformixDataSource";
   private static final String MYSQL_XA_CLASSNAME = "com.mysql.cj.jdbc.MysqlXADataSource";
   private static final String ORACLE_THIN_START = "oracle.jdbc.";
   private static final String SYBASE_CLASSNAME_START = "com.sybase.jdbc";
   private static final String POINTBASE_CLASSNAME = "com.pointbase.jdbc.jdbcUniversalDriver";
   private static final String WLDDMSSQL_CLASSNAME = "weblogic.jdbc.sqlserver.SQLServerDriver";
   private static final String WLDDSYBASE_CLASSNAME = "weblogic.jdbc.sybase.SybaseDriver";
   private static final String WLDDDB2_CLASSNAME = "weblogic.jdbc.db2.DB2Driver";
   private static final String IBMDB2_CLASSNAME = "com.ibm.db2.jcc.DB2Driver";
   private static final String WLDDINFORMIX_CLASSNAME = "weblogic.jdbc.informix.InformixDriver";
   private static final String IBMINFORMIX_CLASSNAME = "com.informix.jdbc.IfxDriver";
   private static final String WLDDORACLE_CLASSNAME = "weblogic.jdbc.oracle.OracleDriver";
   private static final String FIRSTSQL_CLASSNAME = "COM.FirstSQL.Dbcp.DbcpDriver";
   private static final String DERBY_CLASSNAME = "org.apache.derby.jdbc.ClientDriver";
   private static final String MYSQL_CLASSNAME_PART = "com.mysql.jdbc";

   public static int get(String className) {
      if (className.equals("oracle.jdbc.xa.client.OracleXADataSource")) {
         return 0;
      } else if (className.equals("com.inet.tds.XDataSource")) {
         return 2;
      } else if (className.endsWith("SybXADataSource")) {
         return 4;
      } else if (className.startsWith("com.sybase.jdbc")) {
         return 102;
      } else if (className.equals("com.pointbase.xa.xaDataSource")) {
         return 5;
      } else if (className.equals("COM.ibm.db2.jdbc.DB2XADataSource")) {
         return 7;
      } else if (className.equals("com.ibm.db2.jcc.DB2XADataSource")) {
         return 15;
      } else if (className.equals("com.informix.jdbcx.IfxXADataSource")) {
         return 13;
      } else if (className.startsWith("oracle.jdbc.")) {
         return 100;
      } else if (className.equals("com.pointbase.jdbc.jdbcUniversalDriver")) {
         return 103;
      } else if (className.equals("com.ddtek.jdbcx.sqlserver.SQLServerDataSource")) {
         return 8;
      } else if (className.equals("weblogic.jdbcx.sqlserver.SQLServerDataSource")) {
         return 8;
      } else if (className.equals("weblogic.jdbcx.sybase.SybaseDataSource")) {
         return 10;
      } else if (className.equals("weblogic.jdbcx.db2.DB2DataSource")) {
         return 9;
      } else if (className.equals("weblogic.jdbcx.oracle.OracleDataSource")) {
         return 11;
      } else if (className.equals("COM.FirstSQL.Dbcp.DbcpXADataSource")) {
         return 12;
      } else if (className.equals("COM.FirstSQL.Dbcp.DbcpDriver")) {
         return 108;
      } else if (className.equals("org.apache.derby.jdbc.ClientXADataSource")) {
         return 14;
      } else if (className.equals("org.apache.derby.jdbc.ClientDriver")) {
         return 109;
      } else if (className.equals("weblogic.jdbc.sqlserver.SQLServerDriver")) {
         return 104;
      } else if (className.equals("weblogic.jdbc.sybase.SybaseDriver")) {
         return 106;
      } else if (className.equals("weblogic.jdbc.db2.DB2Driver")) {
         return 112;
      } else if (className.equals("com.ibm.db2.jcc.DB2Driver")) {
         return 105;
      } else if (className.equals("weblogic.jdbc.informix.InformixDriver")) {
         return 113;
      } else if (className.equals("com.informix.jdbc.IfxDriver")) {
         return 110;
      } else if (className.equals("weblogic.jdbc.oracle.OracleDriver")) {
         return 107;
      } else if (className.equals("weblogic.jdbcx.informix.InformixDataSource")) {
         return 16;
      } else if (className.equals("com.mysql.cj.jdbc.MysqlXADataSource")) {
         return 17;
      } else {
         return className.startsWith("com.mysql.jdbc") ? 111 : -1;
      }
   }

   public static String toString(int vid) {
      switch (vid) {
         case 0:
            return "Oracle XA";
         case 1:
         case 3:
         case 6:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
         case 67:
         case 68:
         case 69:
         case 70:
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 83:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 90:
         case 91:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 99:
         case 101:
         case 111:
         default:
            return "";
         case 2:
            return "Inet Opta 2000 XA";
         case 4:
            return "Sybase XA";
         case 5:
            return "PointBase XA";
         case 7:
            return "DB2 XA";
         case 8:
            return "DataDirect for MSSQL XA";
         case 9:
            return "DataDirect for DB2 XA";
         case 10:
            return "DataDirect for Sybase XA";
         case 11:
            return "DataDirect for Oracle XA";
         case 12:
            return "FirstSQL";
         case 13:
            return "Informix XA";
         case 14:
            return "Derby";
         case 15:
            return "DB2 JCC XA";
         case 16:
            return "DataDirect for Informix XA";
         case 100:
            return "Oracle Thin";
         case 102:
            return "Sybase";
         case 103:
            return "PointBase";
         case 104:
            return "DataDirect for MSSQL";
         case 105:
            return "DB2";
         case 106:
            return "DataDirect for Sybase";
         case 107:
            return "DataDirect for Oracle";
         case 108:
            return "FirstSQL";
         case 109:
            return "Derby";
         case 110:
            return "Informix";
         case 112:
            return "DataDirect for DB2";
         case 113:
            return "DataDirect for Informix";
      }
   }
}
