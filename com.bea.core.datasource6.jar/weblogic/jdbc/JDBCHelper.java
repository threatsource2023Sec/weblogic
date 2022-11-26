package weblogic.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import weblogic.common.ResourceException;
import weblogic.jdbc.common.internal.ConnectionPoolManager;
import weblogic.jdbc.common.internal.JDBCUtil;

public final class JDBCHelper {
   public static final int UNKNOWN_DBMS = 0;
   public static final String UNKNOWN_DBMS_STR = "unknown";
   public static final int ORACLE = 1;
   public static final String ORACLE_STR = "oracle";
   public static final int SYBASE = 3;
   public static final String SYBASE_STR = "sybase";
   public static final int MSSQL = 4;
   public static final String MSSQL_STR = "mssql";
   public static final int INFORMIX = 5;
   public static final String INFORMIX_STR = "informix";
   public static final int DB2 = 6;
   public static final String DB2_STR = "db2";
   public static final int TIMESTEN = 7;
   public static final String TIMESTEN_STR = "timesten";
   public static final int POINTBASE = 8;
   public static final String POINTBASE_STR = "pointbase";
   public static final int MYSQL = 9;
   public static final String MYSQL_STR = "mysql";
   public static final int FIRSTSQL = 10;
   public static final String FIRSTSQL_STR = "firstsql";
   public static final int NSSQL = 11;
   public static final String NSSQL_STR = "nssql";
   public static final int DERBY = 12;
   public static final String DERBY_STR = "derby";
   public static final int EDB = 13;
   public static final String EDB_STR = "edb";
   public static final int INGRES = 14;
   public static final String INGRES_STR = "ingres";
   private static final int INDEX = 1;
   private static final int DML = 2;
   private static final int DDL = 3;

   private JDBCHelper() {
   }

   public static void close(Connection myconn) {
      if (myconn != null) {
         try {
            myconn.close();
         } catch (Exception var2) {
         }
      }

   }

   public static void close(Statement mystmt) {
      if (mystmt != null) {
         try {
            mystmt.close();
         } catch (Exception var2) {
         }
      }

   }

   public static void close(ResultSet rs) {
      if (rs != null) {
         try {
            rs.close();
         } catch (Exception var2) {
         }
      }

   }

   public static String parseCatalog(String name) {
      return parseTableReference(name, 1);
   }

   public static String parseSchema(String name) {
      return parseTableReference(name, 2);
   }

   public static String parseTable(String name) {
      return parseTableReference(name, 3);
   }

   public static String getIndexIdentifier(DatabaseMetaData metadata, String tableRef, int dbmsType) throws SQLException {
      return createTableIdentifier(metadata, tableRef, 1, dbmsType);
   }

   public static String getDMLIdentifier(DatabaseMetaData metadata, String tableRef, int dbmsType) throws SQLException {
      return createTableIdentifier(metadata, tableRef, 2, dbmsType);
   }

   public static String getDDLIdentifier(DatabaseMetaData metadata, String tableRef, int dbmsType) throws SQLException {
      return createTableIdentifier(metadata, tableRef, 3, dbmsType);
   }

   private static void checkLength(String type, String tableRef, String val, int maxLength) throws SQLException {
      if (val != null && val.length() > maxLength) {
         throw new SQLException(JDBCLogger.logInvalidTableReferenceLoggable(type, val, tableRef, maxLength).getMessage());
      }
   }

   private static String createTableIdentifier(DatabaseMetaData metadata, String tableRef, int type, int dbmsType) throws SQLException {
      try {
         if (metadata.storesUpperCaseIdentifiers()) {
            tableRef = tableRef.toUpperCase();
         } else if (metadata.storesLowerCaseIdentifiers()) {
            tableRef = tableRef.toLowerCase();
         }

         String catalog = parseCatalog(tableRef);
         String schema = parseSchema(tableRef);
         String table = parseTable(tableRef);
         if (dbmsType == 6) {
            catalog = null;
            schema = schema.toUpperCase();
            if (schema == null || schema.length() == 0) {
               schema = getDb2SchemaName((Connection)null, metadata, tableRef);
            }
         }

         if (table != null && table.length() != 0) {
            checkLength("table", tableRef, table, metadata.getMaxTableNameLength());
            boolean supportsCatalogs = false;
            boolean supportsSchemas = false;
            switch (type) {
               case 1:
                  supportsSchemas = metadata.supportsSchemasInIndexDefinitions();
                  supportsCatalogs = metadata.supportsCatalogsInIndexDefinitions();
                  break;
               case 2:
                  supportsSchemas = metadata.supportsSchemasInDataManipulation();
                  supportsCatalogs = metadata.supportsCatalogsInDataManipulation();
                  break;
               case 3:
                  supportsSchemas = metadata.supportsSchemasInTableDefinitions();
                  supportsCatalogs = metadata.supportsCatalogsInTableDefinitions();
            }

            String ret = table;
            if (supportsSchemas && schema != null && schema.length() > 0) {
               checkLength("schema", tableRef, schema, metadata.getMaxSchemaNameLength());
               ret = schema + "." + table;
            }

            if (supportsCatalogs && catalog != null && catalog.length() > 0) {
               checkLength("catalog", tableRef, catalog, metadata.getMaxCatalogNameLength());
               String catalogSeparator = metadata.getCatalogSeparator();
               if (metadata.isCatalogAtStart()) {
                  ret = catalog + catalogSeparator + ret;
               } else {
                  ret = ret + catalogSeparator + catalog;
               }
            }

            return ret;
         } else {
            throw new SQLException(JDBCLogger.logInvalidTableReference2Loggable(tableRef).getMessage());
         }
      } catch (RuntimeException var11) {
         throw new SQLExceptionWrapper(var11);
      }
   }

   public static boolean tableExists(Connection conn, DatabaseMetaData metadata, String ref) throws SQLException {
      int dbmsType = getDBMSType(metadata, (String[])null);
      ResultSet rs = null;
      Statement stmt = null;
      if (parseTable(ref).length() == 0) {
         throw new SQLException(JDBCLogger.logInvalidTableReference2Loggable(ref).getMessage());
      } else {
         try {
            String catalog = parseCatalog(ref);
            String schema = parseSchema(ref);
            String table = parseTable(ref);
            if (dbmsType == 1 && schema.length() == 0) {
               schema = metadata.getUserName();
            }

            if (dbmsType == 6) {
               catalog = "";
               schema = schema.toUpperCase();
               if (schema == null || schema.length() == 0) {
                  schema = getDb2SchemaName(conn, metadata, ref);
               }
            }

            if (metadata.storesUpperCaseIdentifiers()) {
               catalog = catalog.toUpperCase();
               schema = schema.toUpperCase();
               table = table.toUpperCase();
            } else if (metadata.storesLowerCaseIdentifiers()) {
               catalog = catalog.toLowerCase();
               schema = schema.toLowerCase();
               table = table.toLowerCase();
            }

            if (catalog.length() == 0) {
               catalog = null;
            }

            if (schema.length() == 0) {
               schema = null;
            }

            rs = metadata.getTables(catalog, schema, table, (String[])null);
            int colNumber = 3;

            while(rs.next()) {
               String name = rs.getString(colNumber);
               if (name != null && name.equalsIgnoreCase(table)) {
                  boolean var11 = true;
                  return var11;
               }
            }
         } catch (RuntimeException var15) {
            throw new SQLExceptionWrapper(var15);
         } finally {
            close(rs);
            close((Statement)stmt);
         }

         return false;
      }
   }

   public static void checkPool(String poolName) throws SQLException {
      boolean poolExists = false;

      try {
         poolExists = ConnectionPoolManager.poolExists(poolName, (String)null, (String)null, (String)null);
      } catch (ResourceException var3) {
      }

      if (!poolExists) {
         throw new SQLException(JDBCUtil.getTextFormatter().poolNotFound(poolName));
      }
   }

   public static String getDBMSTypeString(int type) {
      switch (type) {
         case 0:
            return "unknown";
         case 1:
            return "oracle";
         case 2:
         default:
            throw new AssertionError();
         case 3:
            return "sybase";
         case 4:
            return "mssql";
         case 5:
            return "informix";
         case 6:
            return "db2";
         case 7:
            return "timesten";
         case 8:
            return "pointbase";
         case 9:
            return "mysql";
         case 10:
            return "firstsql";
         case 11:
            return "nssql";
         case 12:
            return "derby";
         case 13:
            return "edb";
         case 14:
            return "ingres";
      }
   }

   public static int getMaxORDeleteCount(DatabaseMetaData var0) throws SQLException {
      // $FF: Couldn't be decompiled
   }

   public static boolean isBatchCapable(DatabaseMetaData metadata) {
      try {
         String dn = metadata.getDriverName();
         if (dn.startsWith("IBM DB2")) {
            return false;
         } else {
            if (dn.startsWith("Oracle JDBC")) {
               String url = metadata.getURL();
               if (url.toLowerCase(Locale.ENGLISH).indexOf(":oci:") != -1) {
                  return false;
               }

               if (metadata.getDriverMajorVersion() <= 10) {
                  return false;
               }

               if (metadata.getDatabaseProductVersion().startsWith("Oracle8") || metadata.getDatabaseProductVersion().startsWith("Oracle9")) {
                  return false;
               }
            }

            if (dn.startsWith("Informix")) {
               return false;
            } else {
               return dn.startsWith("IBM Informix") ? false : metadata.supportsBatchUpdates();
            }
         }
      } catch (Throwable var3) {
         return false;
      }
   }

   public static int getDBMSType(DatabaseMetaData metadata, String[] trace) throws SQLException {
      String dn = "";
      String pn = "";

      try {
         try {
            pn = metadata.getDatabaseProductName().toLowerCase(Locale.ENGLISH);
            dn = metadata.getDriverName();
         } catch (RuntimeException var7) {
            throw new SQLExceptionWrapper(var7);
         }
      } catch (SQLException var8) {
         String exceptionString = "cannot determine DBMS type";
         if (dn != null) {
            exceptionString = exceptionString + ", driver name = <" + dn + ">";
         }

         SQLException newE = new SQLException(exceptionString);
         newE.initCause(var8);
         throw newE;
      }

      byte dbmsType;
      if (pn.indexOf("oracle") == -1 && !dn.startsWith("Weblogic, Inc. Java-OCI JDBC Driver") && !dn.startsWith("Oracle JDBC driver")) {
         if (pn.indexOf("sybase") == -1 && !dn.startsWith("Sybase") && !dn.startsWith("jConnect")) {
            if (pn.indexOf("microsoft") == -1 && !dn.startsWith("jdbc:weblogic:microsoft") && !dn.startsWith("jdbc:weblogic:mssqlserver") && !dn.startsWith("MSSQLDriver") && !dn.startsWith("i-net") && !dn.startsWith("weblogic.jdbc.mssqlserver")) {
               if (pn.indexOf("informix") == -1 && !dn.startsWith("Informix JDBC Driver") && !dn.startsWith("weblogic.jdbc.informix4.Driver")) {
                  if (pn.indexOf("db2") == -1 && !dn.startsWith("DB2") && !dn.startsWith("libdb2")) {
                     if (pn.indexOf("timesten") != -1) {
                        dbmsType = 7;
                     } else if (pn.indexOf("pointbase") != -1) {
                        dbmsType = 8;
                     } else if (pn.indexOf("mysql") != -1) {
                        dbmsType = 9;
                     } else if (pn.indexOf("firstsql") != -1) {
                        dbmsType = 10;
                     } else if (pn.indexOf("nonstop") != -1) {
                        dbmsType = 11;
                     } else if (pn.indexOf("derby") != -1) {
                        dbmsType = 12;
                     } else if (pn.indexOf("ingres") != -1) {
                        dbmsType = 14;
                     } else if (pn.indexOf("enterprisedb") != -1) {
                        dbmsType = 13;
                     } else {
                        dbmsType = 0;
                     }
                  } else {
                     dbmsType = 6;
                  }
               } else {
                  dbmsType = 5;
               }
            } else {
               dbmsType = 4;
            }
         } else {
            dbmsType = 3;
         }
      } else {
         dbmsType = 1;
      }

      if (trace != null && trace.length > 0) {
         trace[0] = "Driver Name = (" + dn + ") Product Name = (" + pn + ") Driver Type = (" + getDBMSTypeString(dbmsType) + ")";
      }

      return dbmsType;
   }

   public static boolean isOracleBlobColumn(int dbmsType, ResultSet results, int columnNo) throws SQLException {
      if (dbmsType != 1) {
         return false;
      } else {
         String recType;
         try {
            recType = results.getMetaData().getColumnTypeName(columnNo);
         } catch (RuntimeException var5) {
            throw new SQLExceptionWrapper(var5);
         }

         return recType.toLowerCase(Locale.ENGLISH).indexOf("raw") == -1;
      }
   }

   public static boolean mustSelectForUpdateToInsertBinary(int dbmsType, DatabaseMetaData metadata) throws SQLException {
      if (dbmsType != 1) {
         return false;
      } else {
         String url;
         try {
            String dn = metadata.getDriverName();
            if (!dn.startsWith("Oracle JDBC")) {
               return false;
            }

            url = metadata.getURL();
         } catch (RuntimeException var4) {
            throw new SQLExceptionWrapper(var4);
         }

         return url.toLowerCase(Locale.ENGLISH).indexOf(":oci:") == -1;
      }
   }

   private static String parseTableReference(String name, int part) {
      if (name == null) {
         name = "";
      }

      String[] parts = name.split("\\.");
      if (parts.length == 0 || parts.length > 3 || parts.length >= 1 && parts[0].length() == 0 || parts.length >= 2 && parts[1].length() == 0 || parts.length >= 3 && parts[2].length() == 0) {
         throw new AssertionError("Parse error, expected <[[catalog.]schema.]]table>, got <" + name + ">");
      } else {
         part = part + parts.length - 4;
         return part < 0 ? "" : parts[part];
      }
   }

   public static String getDb2SchemaName(Connection conn, DatabaseMetaData metadata, String ref) throws SQLException {
      String schemaName = metadata.getUserName().toUpperCase();
      if (conn != null) {
         Statement s = null;
         ResultSet rs = null;

         try {
            s = conn.createStatement();
            rs = s.executeQuery("select current_schema from sysibm.sysdummy1");
            if (rs.next()) {
               schemaName = rs.getString(1).toUpperCase();
            }
         } catch (Exception var10) {
         } finally {
            close(rs);
            close(s);
         }
      }

      return schemaName;
   }

   public static String getDb2LLRTableName(Connection conn, DatabaseMetaData metadata, String ref) throws SQLException {
      String llrTableName = ref;
      String schemaNameFromRefTable = parseSchema(ref);
      String tableName = parseTable(ref);
      if (schemaNameFromRefTable != null && schemaNameFromRefTable.trim().length() > 0) {
         llrTableName = schemaNameFromRefTable + "." + tableName;
      } else {
         String schemaNameFromConn = getDb2SchemaName(conn, metadata, ref);
         if (schemaNameFromConn != null && schemaNameFromConn.length() > 0) {
            llrTableName = schemaNameFromConn + "." + tableName;
         }
      }

      return llrTableName;
   }
}
