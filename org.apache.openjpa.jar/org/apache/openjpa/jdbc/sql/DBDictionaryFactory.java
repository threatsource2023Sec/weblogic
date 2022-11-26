package org.apache.openjpa.jdbc.sql;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UserException;

public class DBDictionaryFactory {
   private static final Localizer _loc = Localizer.forPackage(DBDictionaryFactory.class);

   public static DBDictionary newDBDictionary(JDBCConfiguration conf, String dclass, String props) {
      return newDBDictionary(conf, dclass, props, (Connection)null);
   }

   public static DBDictionary calculateDBDictionary(JDBCConfiguration conf, String url, String driver, String props) {
      String dclass = dictionaryClassForString(driver, conf);
      if (dclass == null) {
         dclass = dictionaryClassForString(getProtocol(url), conf);
      }

      return dclass == null ? null : newDBDictionary(conf, dclass, props);
   }

   public static DBDictionary newDBDictionary(JDBCConfiguration conf, DataSource ds, String props) {
      Connection conn = null;

      DBDictionary var6;
      try {
         conn = ds.getConnection();
         DatabaseMetaData meta = conn.getMetaData();
         String dclass = dictionaryClassForString(meta.getDatabaseProductName(), conf);
         if (dclass == null) {
            dclass = dictionaryClassForString(getProtocol(meta.getURL()), conf);
         }

         if (dclass == null) {
            dclass = DBDictionary.class.getName();
         }

         var6 = newDBDictionary(conf, dclass, props, conn);
      } catch (SQLException var15) {
         throw (new StoreException(var15)).setFatal(true);
      } finally {
         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var14) {
            }
         }

      }

      return var6;
   }

   static String getProtocol(String url) {
      String protocol = null;
      if (!StringUtils.isEmpty(url) && url.startsWith("jdbc:")) {
         int colonCount = 1;
         int next = "jdbc:".length();
         int protoEnd = next;

         label35: {
            char c;
            do {
               while(true) {
                  if (colonCount >= 3 || next >= url.length()) {
                     break label35;
                  }

                  c = url.charAt(next++);
                  if (c != ':') {
                     break;
                  }

                  ++colonCount;
                  protoEnd = next;
               }
            } while(c != '@' && c != '/' && c != '\\');

            --next;
         }

         protocol = url.substring(0, protoEnd);
      }

      return protocol;
   }

   private static DBDictionary newDBDictionary(JDBCConfiguration conf, String dclass, String props, Connection conn) {
      DBDictionary dict = null;

      try {
         Class c = Class.forName(dclass, true, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(DBDictionary.class)));
         dict = (DBDictionary)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(c));
      } catch (ClassNotFoundException var10) {
         try {
            Class c = Thread.currentThread().getContextClassLoader().loadClass(dclass);
            dict = (DBDictionary)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(c));
         } catch (Exception var9) {
            Exception e = var9;
            if (var9 instanceof PrivilegedActionException) {
               e = ((PrivilegedActionException)var9).getException();
            }

            throw (new UserException(e)).setFatal(true);
         }
      } catch (Exception var11) {
         Exception e = var11;
         if (var11 instanceof PrivilegedActionException) {
            e = ((PrivilegedActionException)var11).getException();
         }

         throw (new UserException(e)).setFatal(true);
      }

      Log log = conf.getLog("openjpa.jdbc.JDBC");
      if (log.isWarnEnabled() && dict.getClass() == DBDictionary.class) {
         log.warn(_loc.get("warn-generic"));
      }

      if (log.isInfoEnabled()) {
         String infoString = "";
         if (conn != null) {
            try {
               DatabaseMetaData meta = conn.getMetaData();
               infoString = " (" + meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion() + " ," + meta.getDriverName() + " " + meta.getDriverVersion() + ")";
            } catch (SQLException var12) {
               if (log.isTraceEnabled()) {
                  log.trace(var12.toString(), var12);
               }
            }
         }

         log.info(_loc.get("using-dict", dclass, infoString));
      }

      Configurations.configureInstance(dict, conf, (String)props, "DBDictionary");
      if (conn != null) {
         try {
            dict.connectedConfiguration(conn);
         } catch (SQLException var8) {
            throw (new StoreException(var8)).setFatal(true);
         }
      }

      return dict;
   }

   private static String dictionaryClassForString(String prod, JDBCConfiguration conf) {
      if (StringUtils.isEmpty(prod)) {
         return null;
      } else {
         prod = prod.toLowerCase();
         PluginValue dbdictionaryPlugin = ((JDBCConfigurationImpl)conf).dbdictionaryPlugin;
         if (prod.indexOf("oracle") != -1) {
            return dbdictionaryPlugin.unalias("oracle");
         } else if (prod.indexOf("sqlserver") != -1) {
            return dbdictionaryPlugin.unalias("sqlserver");
         } else if (prod.indexOf("jsqlconnect") != -1) {
            return dbdictionaryPlugin.unalias("sqlserver");
         } else if (prod.indexOf("mysql") != -1) {
            return dbdictionaryPlugin.unalias("mysql");
         } else if (prod.indexOf("postgres") != -1) {
            return dbdictionaryPlugin.unalias("postgres");
         } else if (prod.indexOf("sybase") != -1) {
            return dbdictionaryPlugin.unalias("sybase");
         } else if (prod.indexOf("adaptive server") != -1) {
            return dbdictionaryPlugin.unalias("sybase");
         } else if (prod.indexOf("informix") != -1) {
            return dbdictionaryPlugin.unalias("informix");
         } else if (prod.indexOf("hsql") != -1) {
            return dbdictionaryPlugin.unalias("hsql");
         } else if (prod.indexOf("foxpro") != -1) {
            return dbdictionaryPlugin.unalias("foxpro");
         } else if (prod.indexOf("interbase") != -1) {
            return InterbaseDictionary.class.getName();
         } else if (prod.indexOf("jdatastore") != -1) {
            return JDataStoreDictionary.class.getName();
         } else if (prod.indexOf("borland") != -1) {
            return JDataStoreDictionary.class.getName();
         } else if (prod.indexOf("access") != -1) {
            return dbdictionaryPlugin.unalias("access");
         } else if (prod.indexOf("pointbase") != -1) {
            return dbdictionaryPlugin.unalias("pointbase");
         } else if (prod.indexOf("empress") != -1) {
            return dbdictionaryPlugin.unalias("empress");
         } else if (prod.indexOf("firebird") != -1) {
            return FirebirdDictionary.class.getName();
         } else if (prod.indexOf("cache") != -1) {
            return CacheDictionary.class.getName();
         } else if (prod.indexOf("derby") != -1) {
            return dbdictionaryPlugin.unalias("derby");
         } else if (prod.indexOf("jdbc:h2:") != -1) {
            return dbdictionaryPlugin.unalias("h2");
         } else if (prod.indexOf("h2 database") != -1) {
            return dbdictionaryPlugin.unalias("h2");
         } else if (prod.indexOf("db2") == -1 && prod.indexOf("as400") == -1) {
            if (prod.indexOf("cloudscape") != -1) {
               return DBDictionary.class.getName();
            } else if (prod.indexOf("daffodil") != -1) {
               return DBDictionary.class.getName();
            } else if (prod.indexOf("sapdb") != -1) {
               return DBDictionary.class.getName();
            } else if (prod.indexOf("idb") != -1) {
               return DBDictionary.class.getName();
            } else {
               String prodClassName = dbdictionaryPlugin.unalias(prod);
               return !StringUtils.equals(prod, prodClassName) ? prodClassName : null;
            }
         } else {
            return dbdictionaryPlugin.unalias("db2");
         }
      }
   }

   public static String toString(DatabaseMetaData meta) throws SQLException {
      String lineSep = J2DoPrivHelper.getLineSeparator();
      StringBuffer buf = new StringBuffer();

      try {
         buf.append("catalogSeparator: ").append(meta.getCatalogSeparator()).append(lineSep).append("catalogTerm: ").append(meta.getCatalogTerm()).append(lineSep).append("databaseProductName: ").append(meta.getDatabaseProductName()).append(lineSep).append("databaseProductVersion: ").append(meta.getDatabaseProductVersion()).append(lineSep).append("driverName: ").append(meta.getDriverName()).append(lineSep).append("driverVersion: ").append(meta.getDriverVersion()).append(lineSep).append("extraNameCharacters: ").append(meta.getExtraNameCharacters()).append(lineSep).append("identifierQuoteString: ").append(meta.getIdentifierQuoteString()).append(lineSep).append("numericFunctions: ").append(meta.getNumericFunctions()).append(lineSep).append("procedureTerm: ").append(meta.getProcedureTerm()).append(lineSep).append("schemaTerm: ").append(meta.getSchemaTerm()).append(lineSep).append("searchStringEscape: ").append(meta.getSearchStringEscape()).append(lineSep).append("sqlKeywords: ").append(meta.getSQLKeywords()).append(lineSep).append("stringFunctions: ").append(meta.getStringFunctions()).append(lineSep).append("systemFunctions: ").append(meta.getSystemFunctions()).append(lineSep).append("timeDateFunctions: ").append(meta.getTimeDateFunctions()).append(lineSep).append("url: ").append(meta.getURL()).append(lineSep).append("userName: ").append(meta.getUserName()).append(lineSep).append("defaultTransactionIsolation: ").append(meta.getDefaultTransactionIsolation()).append(lineSep).append("driverMajorVersion: ").append(meta.getDriverMajorVersion()).append(lineSep).append("driverMinorVersion: ").append(meta.getDriverMinorVersion()).append(lineSep).append("maxBinaryLiteralLength: ").append(meta.getMaxBinaryLiteralLength()).append(lineSep).append("maxCatalogNameLength: ").append(meta.getMaxCatalogNameLength()).append(lineSep).append("maxCharLiteralLength: ").append(meta.getMaxCharLiteralLength()).append(lineSep).append("maxColumnNameLength: ").append(meta.getMaxColumnNameLength()).append(lineSep).append("maxColumnsInGroupBy: ").append(meta.getMaxColumnsInGroupBy()).append(lineSep).append("maxColumnsInIndex: ").append(meta.getMaxColumnsInIndex()).append(lineSep).append("maxColumnsInOrderBy: ").append(meta.getMaxColumnsInOrderBy()).append(lineSep).append("maxColumnsInSelect: ").append(meta.getMaxColumnsInSelect()).append(lineSep).append("maxColumnsInTable: ").append(meta.getMaxColumnsInTable()).append(lineSep).append("maxConnections: ").append(meta.getMaxConnections()).append(lineSep).append("maxCursorNameLength: ").append(meta.getMaxCursorNameLength()).append(lineSep).append("maxIndexLength: ").append(meta.getMaxIndexLength()).append(lineSep).append("maxProcedureNameLength: ").append(meta.getMaxProcedureNameLength()).append(lineSep).append("maxRowSize: ").append(meta.getMaxRowSize()).append(lineSep).append("maxSchemaNameLength: ").append(meta.getMaxSchemaNameLength()).append(lineSep).append("maxStatementLength: ").append(meta.getMaxStatementLength()).append(lineSep).append("maxStatements: ").append(meta.getMaxStatements()).append(lineSep).append("maxTableNameLength: ").append(meta.getMaxTableNameLength()).append(lineSep).append("maxTablesInSelect: ").append(meta.getMaxTablesInSelect()).append(lineSep).append("maxUserNameLength: ").append(meta.getMaxUserNameLength()).append(lineSep).append("isCatalogAtStart: ").append(meta.isCatalogAtStart()).append(lineSep).append("isReadOnly: ").append(meta.isReadOnly()).append(lineSep).append("nullPlusNonNullIsNull: ").append(meta.nullPlusNonNullIsNull()).append(lineSep).append("nullsAreSortedAtEnd: ").append(meta.nullsAreSortedAtEnd()).append(lineSep).append("nullsAreSortedAtStart: ").append(meta.nullsAreSortedAtStart()).append(lineSep).append("nullsAreSortedHigh: ").append(meta.nullsAreSortedHigh()).append(lineSep).append("nullsAreSortedLow: ").append(meta.nullsAreSortedLow()).append(lineSep).append("storesLowerCaseIdentifiers: ").append(meta.storesLowerCaseIdentifiers()).append(lineSep).append("storesLowerCaseQuotedIdentifiers: ").append(meta.storesLowerCaseQuotedIdentifiers()).append(lineSep).append("storesMixedCaseIdentifiers: ").append(meta.storesMixedCaseIdentifiers()).append(lineSep).append("storesMixedCaseQuotedIdentifiers: ").append(meta.storesMixedCaseQuotedIdentifiers()).append(lineSep).append("storesUpperCaseIdentifiers: ").append(meta.storesUpperCaseIdentifiers()).append(lineSep).append("storesUpperCaseQuotedIdentifiers: ").append(meta.storesUpperCaseQuotedIdentifiers()).append(lineSep).append("supportsAlterTableWithAddColumn: ").append(meta.supportsAlterTableWithAddColumn()).append(lineSep).append("supportsAlterTableWithDropColumn: ").append(meta.supportsAlterTableWithDropColumn()).append(lineSep).append("supportsANSI92EntryLevelSQL: ").append(meta.supportsANSI92EntryLevelSQL()).append(lineSep).append("supportsANSI92FullSQL: ").append(meta.supportsANSI92FullSQL()).append(lineSep).append("supportsANSI92IntermediateSQL: ").append(meta.supportsANSI92IntermediateSQL()).append(lineSep).append("supportsCatalogsInDataManipulation: ").append(meta.supportsCatalogsInDataManipulation()).append(lineSep).append("supportsCatalogsInIndexDefinitions: ").append(meta.supportsCatalogsInIndexDefinitions()).append(lineSep).append("supportsCatalogsInPrivilegeDefinitions: ").append(meta.supportsCatalogsInPrivilegeDefinitions()).append(lineSep).append("supportsCatalogsInProcedureCalls: ").append(meta.supportsCatalogsInProcedureCalls()).append(lineSep).append("supportsCatalogsInTableDefinitions: ").append(meta.supportsCatalogsInTableDefinitions()).append(lineSep).append("supportsColumnAliasing: ").append(meta.supportsColumnAliasing()).append(lineSep).append("supportsConvert: ").append(meta.supportsConvert()).append(lineSep).append("supportsCoreSQLGrammar: ").append(meta.supportsCoreSQLGrammar()).append(lineSep).append("supportsCorrelatedSubqueries: ").append(meta.supportsCorrelatedSubqueries()).append(lineSep).append("supportsDataDefinitionAndDataManipulationTransactions: ").append(meta.supportsDataDefinitionAndDataManipulationTransactions()).append(lineSep).append("supportsDataManipulationTransactionsOnly: ").append(meta.supportsDataManipulationTransactionsOnly()).append(lineSep).append("supportsDifferentTableCorrelationNames: ").append(meta.supportsDifferentTableCorrelationNames()).append(lineSep).append("supportsExpressionsInOrderBy: ").append(meta.supportsExpressionsInOrderBy()).append(lineSep).append("supportsExtendedSQLGrammar: ").append(meta.supportsExtendedSQLGrammar()).append(lineSep).append("supportsFullOuterJoins: ").append(meta.supportsFullOuterJoins()).append(lineSep).append("supportsGroupBy: ").append(meta.supportsGroupBy()).append(lineSep).append("supportsGroupByBeyondSelect: ").append(meta.supportsGroupByBeyondSelect()).append(lineSep).append("supportsGroupByUnrelated: ").append(meta.supportsGroupByUnrelated()).append(lineSep).append("supportsIntegrityEnhancementFacility: ").append(meta.supportsIntegrityEnhancementFacility()).append(lineSep).append("supportsLikeEscapeClause: ").append(meta.supportsLikeEscapeClause()).append(lineSep).append("supportsLimitedOuterJoins: ").append(meta.supportsLimitedOuterJoins()).append(lineSep).append("supportsMinimumSQLGrammar: ").append(meta.supportsMinimumSQLGrammar()).append(lineSep).append("supportsMixedCaseIdentifiers: ").append(meta.supportsMixedCaseIdentifiers()).append(lineSep).append("supportsMixedCaseQuotedIdentifiers: ").append(meta.supportsMixedCaseQuotedIdentifiers()).append(lineSep).append("supportsMultipleResultSets: ").append(meta.supportsMultipleResultSets()).append(lineSep).append("supportsMultipleTransactions: ").append(meta.supportsMultipleTransactions()).append(lineSep).append("supportsNonNullableColumns: ").append(meta.supportsNonNullableColumns()).append(lineSep).append("supportsOpenCursorsAcrossCommit: ").append(meta.supportsOpenCursorsAcrossCommit()).append(lineSep).append("supportsOpenCursorsAcrossRollback: ").append(meta.supportsOpenCursorsAcrossRollback()).append(lineSep).append("supportsOpenStatementsAcrossCommit: ").append(meta.supportsOpenStatementsAcrossCommit()).append(lineSep).append("supportsOpenStatementsAcrossRollback: ").append(meta.supportsOpenStatementsAcrossRollback()).append(lineSep).append("supportsOrderByUnrelated: ").append(meta.supportsOrderByUnrelated()).append(lineSep).append("supportsOuterJoins: ").append(meta.supportsOuterJoins()).append(lineSep).append("supportsPositionedDelete: ").append(meta.supportsPositionedDelete()).append(lineSep).append("supportsPositionedUpdate: ").append(meta.supportsPositionedUpdate()).append(lineSep).append("supportsSchemasInDataManipulation: ").append(meta.supportsSchemasInDataManipulation()).append(lineSep).append("supportsSchemasInIndexDefinitions: ").append(meta.supportsSchemasInIndexDefinitions()).append(lineSep).append("supportsSchemasInPrivilegeDefinitions: ").append(meta.supportsSchemasInPrivilegeDefinitions()).append(lineSep).append("supportsSchemasInProcedureCalls: ").append(meta.supportsSchemasInProcedureCalls()).append(lineSep).append("supportsSchemasInTableDefinitions: ").append(meta.supportsSchemasInTableDefinitions()).append(lineSep).append("supportsSelectForUpdate: ").append(meta.supportsSelectForUpdate()).append(lineSep).append("supportsStoredProcedures: ").append(meta.supportsStoredProcedures()).append(lineSep).append("supportsSubqueriesInComparisons: ").append(meta.supportsSubqueriesInComparisons()).append(lineSep).append("supportsSubqueriesInExists: ").append(meta.supportsSubqueriesInExists()).append(lineSep).append("supportsSubqueriesInIns: ").append(meta.supportsSubqueriesInIns()).append(lineSep).append("supportsSubqueriesInQuantifieds: ").append(meta.supportsSubqueriesInQuantifieds()).append(lineSep).append("supportsTableCorrelationNames: ").append(meta.supportsTableCorrelationNames()).append(lineSep).append("supportsTransactions: ").append(meta.supportsTransactions()).append(lineSep).append("supportsUnion: ").append(meta.supportsUnion()).append(lineSep).append("supportsUnionAll: ").append(meta.supportsUnionAll()).append(lineSep).append("usesLocalFilePerTable: ").append(meta.usesLocalFilePerTable()).append(lineSep).append("usesLocalFiles: ").append(meta.usesLocalFiles()).append(lineSep).append("allProceduresAreCallable: ").append(meta.allProceduresAreCallable()).append(lineSep).append("allTablesAreSelectable: ").append(meta.allTablesAreSelectable()).append(lineSep).append("dataDefinitionCausesTransactionCommit: ").append(meta.dataDefinitionCausesTransactionCommit()).append(lineSep).append("dataDefinitionIgnoredInTransactions: ").append(meta.dataDefinitionIgnoredInTransactions()).append(lineSep).append("doesMaxRowSizeIncludeBlobs: ").append(meta.doesMaxRowSizeIncludeBlobs()).append(lineSep).append("supportsBatchUpdates: ").append(meta.supportsBatchUpdates());
      } catch (Throwable var4) {
         buf.append(lineSep).append("Caught throwable: ").append(var4);
      }

      return buf.toString();
   }
}
