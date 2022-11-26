package weblogic.ejb.container.utils;

import java.security.AccessController;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.interfaces.WLCMPPersistenceManager;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.logging.Loggable;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;

public abstract class TableVerifier {
   protected static final DebugLogger debugLogger;
   private static final AuthenticatedSubject kernelId;
   public static final int TABLE_NOT_EXISTS = 0;
   protected static final int TABLE_EXISTS_AND_OK = 1;
   protected static final int POOL_NOT_EXISTS = 2;
   protected static final int TABLE_MISSING_COLUMNS = 3;
   protected static final int TABLE_CREATED_BY_CONTAINER = 4;
   protected static final int TABLE_CREATED_BY_USER = 5;
   private static final int SEQUENCE_NOT_EXISTS = 10;
   private static final int SEQUENCE_PROD_EXISTS_AND_OK = 11;
   private static final int SEQUENCE_DEV_EXISTS_AND_OK = 12;
   private static final int SEQUENCE_PROD_INCREMENT_LT_DBINCREMENT = 13;
   private static final int SEQUENCE_PROD_INCREMENT_GT_DBINCREMENT = 14;
   private static final int SEQUENCE_DEV_INCREMENT_LT_DBINCREMENT = 15;
   private static final int SEQUENCE_DEV_INCREMENT_GT_DBINCREMENT = 16;
   private static final String PREPAREDSTATEMENT_CREATE_SEQUENCE_ORCL = "CREATE SEQUENCE ? INCREMENT BY ?";
   private static final String PREPAREDSTATEMENT_CREATE_SEQUENCE_INFORMIX = "CREATE SEQUENCE ? INCREMENT ?";
   private static final String PREPAREDSTATEMENT_ALTER_SEQUENCE_ORCL = "ALTER SEQUENCE ? INCREMENT BY ?";
   private static final String PREPAREDSTATEMENT_ALTER_SEQUENCE_INFORMIX = "ALTER SEQUENCE ? INCREMENT ?";
   protected WLDeploymentException wldexception = null;
   protected Set newColumns;
   protected Set removedColumns;
   protected String createDefaultDBMSTable;
   private static Boolean productionModeEnabled;

   private static boolean isProductionModeEnabled() {
      if (productionModeEnabled == null) {
         boolean enabled = ManagementService.getRuntimeAccess(kernelId).getDomain().isProductionModeEnabled();
         if (enabled) {
            productionModeEnabled = Boolean.TRUE;
         } else {
            productionModeEnabled = Boolean.FALSE;
         }
      }

      return productionModeEnabled == Boolean.TRUE;
   }

   public void verifyOrCreateOrAlterTable(WLCMPPersistenceManager pm, Connection conn, String tableName, List columnList, boolean loadFieldSQLTypeMap, List fieldNamesList, Map variable2SQLType, Map variable2nullable, String createDefaultDBMSTable, boolean tableUsesTrigger) throws WLDeploymentException {
      String[] columnNames = (String[])columnList.toArray(new String[0]);
      if (isProductionModeEnabled() && !createDefaultDBMSTable.equals("Disabled")) {
         EJBLogger.logTableCannotBeCreatedInProductionMode();
         createDefaultDBMSTable = "Disabled";
      }

      if (tableUsesTrigger && createDefaultDBMSTable != null && !createDefaultDBMSTable.equalsIgnoreCase("Disabled")) {
         EJBLogger.logTableUsesTriggerCannotBeDroppedOrCreated(tableName);
         createDefaultDBMSTable = "Disabled";
      }

      if (createDefaultDBMSTable != null && (createDefaultDBMSTable.equals("DropAndCreateAlways") || createDefaultDBMSTable.equals("DropAndCreate") || createDefaultDBMSTable.equals("AlterOrCreate"))) {
         int status = this.isTableCreatedByContainer(pm, conn, tableName);
         if (status == 0) {
            pm.createDefaultDBMSTable(tableName);
            return;
         }

         if (status == 5) {
            EJBLogger.logTableCreatedByUser(tableName);
            createDefaultDBMSTable = "Disabled";
         }
      }

      this.createDefaultDBMSTable = createDefaultDBMSTable;
      if (createDefaultDBMSTable != null && !createDefaultDBMSTable.equalsIgnoreCase("Disabled")) {
         if (createDefaultDBMSTable.equalsIgnoreCase("DropAndCreateAlways")) {
            pm.dropAndCreateDefaultDBMSTable(tableName);
         } else if (createDefaultDBMSTable.equalsIgnoreCase("DropAndCreate")) {
            this.dropAndCreate(pm, conn, tableName, columnNames, loadFieldSQLTypeMap, fieldNamesList, variable2SQLType, variable2nullable);
         } else if (createDefaultDBMSTable.equalsIgnoreCase("AlterOrCreate")) {
            this.alterOrCreate(pm, conn, tableName, columnNames, loadFieldSQLTypeMap, fieldNamesList, variable2SQLType, variable2nullable);
         } else if (createDefaultDBMSTable.equalsIgnoreCase("CreateOnly")) {
            this.createOnly(pm, conn, tableName, columnNames, loadFieldSQLTypeMap, fieldNamesList, variable2SQLType, variable2nullable);
         }
      } else {
         this.verifyTable(pm, conn, tableName, columnNames, loadFieldSQLTypeMap, fieldNamesList, variable2SQLType, variable2nullable);
      }

   }

   private void dropAndCreate(WLCMPPersistenceManager pm, Connection conn, String tableName, String[] columnNames, boolean loadFieldSQLTypeMap, List fieldNamesList, Map variable2SQLType, Map variable2nullable) throws WLDeploymentException {
      int status = this.checkTableAndColumns(pm, conn, tableName, columnNames, loadFieldSQLTypeMap, fieldNamesList, variable2SQLType, variable2nullable);
      if (status != 1) {
         pm.dropAndCreateDefaultDBMSTable(tableName);
      }

   }

   private void createOnly(WLCMPPersistenceManager pm, Connection conn, String tableName, String[] columnNames, boolean loadFieldSQLTypeMap, List fieldNamesList, Map variable2SQLType, Map variable2nullable) throws WLDeploymentException {
      int status = this.checkTableAndColumns(pm, conn, tableName, columnNames, loadFieldSQLTypeMap, fieldNamesList, variable2SQLType, variable2nullable);
      if (status == 0) {
         pm.createDefaultDBMSTable(tableName);
      } else if (status == 3) {
         if (!this.newColumns.isEmpty()) {
            String[] mc = (String[])this.newColumns.toArray(new String[this.newColumns.size()]);
            Loggable l = EJBLogger.logCmpTableMissingColumnsLoggable(tableName, this.strArrayToCommaList(mc));
            throw new WLDeploymentException(l.getMessageText());
         }

         Loggable l = EJBLogger.logCmpTableMissingColumnsLoggable(tableName, this.strArrayToCommaList(new String[0]));
         throw new WLDeploymentException(l.getMessageText());
      }

   }

   private void alterOrCreate(WLCMPPersistenceManager pm, Connection conn, String tableName, String[] columnNames, boolean loadFieldSQLTypeMap, List fieldNamesList, Map variable2SQLType, Map variable2nullable) throws WLDeploymentException {
      int status = this.checkTableAndColumns(pm, conn, tableName, columnNames, loadFieldSQLTypeMap, fieldNamesList, variable2SQLType, variable2nullable);
      if (status == 0) {
         pm.createDefaultDBMSTable(tableName);
      } else {
         pm.alterDefaultDBMSTable(tableName, this.newColumns, this.removedColumns);
      }

   }

   private void verifyTable(WLCMPPersistenceManager pm, Connection conn, String tableName, String[] columnNames, boolean loadFieldSQLTypeMap, List fieldNamesList, Map variable2SQLType, Map variable2nullable) throws WLDeploymentException {
      int status = this.checkTableAndColumns(pm, conn, tableName, columnNames, loadFieldSQLTypeMap, fieldNamesList, variable2SQLType, variable2nullable);
      if (status != 1) {
         if (status == 0) {
            Loggable l = EJBLogger.logDeploymentFailedTableDoesNotExistLoggable(pm.getEjbName(), tableName);
            l.log();
            throw new WLDeploymentException(l.getMessage());
         } else if (status == 3) {
            if (!this.newColumns.isEmpty()) {
               String[] mc = (String[])this.newColumns.toArray(new String[this.newColumns.size()]);
               Loggable l = EJBLogger.logCmpTableMissingColumnsLoggable(tableName, this.strArrayToCommaList(mc));
               throw new WLDeploymentException(l.getMessageText());
            } else {
               throw new AssertionError("The table verification failed for unknown reason.Please try redeploying the bean");
            }
         }
      }
   }

   public abstract int checkTableAndColumns(WLCMPPersistenceManager var1, Connection var2, String var3, String[] var4, boolean var5, List var6, Map var7, Map var8);

   public abstract int isTableCreatedByContainer(WLCMPPersistenceManager var1, Connection var2, String var3);

   protected String strArrayToCommaList(String[] s) {
      StringBuilder sb = new StringBuilder(200);

      for(int i = 0; i < s.length; ++i) {
         if (i != 0) {
            sb.append(", ");
         }

         sb.append(s[i]);
      }

      return sb.toString();
   }

   public String verifyOrCreateOrAlterSequence(Connection conn, String sequenceName, int increment, String createDefaultDBMSTable, int dbType) throws WLDeploymentException {
      if (isProductionModeEnabled() && !createDefaultDBMSTable.equals("Disabled")) {
         EJBLogger.logSequenceCannotBeAlteredInProductionMode(sequenceName);
         createDefaultDBMSTable = "Disabled";
      }

      int seqStatus = this.verifySequence(conn, sequenceName, increment, dbType);
      if (dbType == 1 && seqStatus == 10) {
         String synonymName = sequenceName;
         sequenceName = this.resolveIfOracleSynonym(conn, sequenceName);
         if (debugLogger.isDebugEnabled() && !sequenceName.equals(synonymName)) {
            debug(synonymName + " resolved to sequence: " + sequenceName + ", verifying the validity of the actual Oracle sequence");
         }

         seqStatus = this.verifySequence(conn, sequenceName, increment, dbType);
         if ((seqStatus == 11 || seqStatus == 12) && debugLogger.isDebugEnabled()) {
            debug(synonymName + " resolved to a valid oracle sequence: " + sequenceName);
            sequenceName = synonymName;
         }
      }

      if (isProductionModeEnabled()) {
         if (seqStatus == 11) {
            return sequenceName;
         }

         if (seqStatus == 13) {
            this.logWarningSequenceIncrementLesserThanDBIncrement(sequenceName, increment);
            return sequenceName;
         }

         if (seqStatus == 14) {
            this.throwSequenceIncrementGreaterThanDBIncrement(sequenceName, increment);
         }

         this.throwSequenceNotExists(sequenceName, increment);
      }

      if (seqStatus == 11) {
         return sequenceName;
      } else if (seqStatus == 12) {
         return RDBMSUtils.getContainerSequenceName(sequenceName);
      } else if (seqStatus == 13) {
         this.logWarningSequenceIncrementLesserThanDBIncrement(sequenceName, increment);
         return sequenceName;
      } else {
         if (createDefaultDBMSTable == null || createDefaultDBMSTable.equalsIgnoreCase("Disabled")) {
            if (seqStatus == 10) {
               this.throwSequenceNotExists(sequenceName, increment);
            } else {
               if (seqStatus == 15) {
                  this.logWarningSequenceIncrementLesserThanDBIncrement(RDBMSUtils.getContainerSequenceName(sequenceName), increment);
                  return RDBMSUtils.getContainerSequenceName(sequenceName);
               }

               if (seqStatus == 14) {
                  this.throwSequenceIncrementGreaterThanDBIncrement(sequenceName, increment);
               } else {
                  if (seqStatus != 16) {
                     throw new AssertionError("Sequence: '" + sequenceName + "' increment '" + increment + "' Unknown SEQUENCE STATUS CODE: " + seqStatus);
                  }

                  this.throwSequenceIncrementGreaterThanDBIncrement(RDBMSUtils.getContainerSequenceName(sequenceName), increment);
               }
            }
         }

         if (seqStatus == 14) {
            this.throwSequenceIncrementGreaterThanDBIncrement(sequenceName, increment);
         }

         if (createDefaultDBMSTable.equalsIgnoreCase("CreateOnly")) {
            return this.createOnlySequence(conn, seqStatus, RDBMSUtils.getContainerSequenceName(sequenceName), increment, dbType);
         } else {
            assert createDefaultDBMSTable.equalsIgnoreCase("DropAndCreateAlways") || createDefaultDBMSTable.equalsIgnoreCase("DropAndCreate") || createDefaultDBMSTable.equalsIgnoreCase("AlterOrCreate") : "Unknown table creation option code " + createDefaultDBMSTable;

            return this.alterOrCreateSequence(conn, seqStatus, RDBMSUtils.getContainerSequenceName(sequenceName), increment, dbType);
         }
      }
   }

   private String resolveIfOracleSynonym(Connection conn, String synonymName) {
      PreparedStatement stmt = null;
      ResultSet rs = null;
      String sequenceName = null;

      try {
         String query = null;
         int index = false;
         int index;
         if ((index = synonymName.indexOf(".")) != -1) {
            String schemaName = synonymName.substring(0, index);
            String onlySynonymName = synonymName.substring(index + 1);
            query = "SELECT TABLE_NAME FROM ALL_SYNONYMS WHERE SYNONYM_NAME= UPPER( ? ) AND OWNER = UPPER( ? )";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, onlySynonymName);
            stmt.setString(2, schemaName);
         } else {
            query = "SELECT TABLE_NAME FROM ALL_SYNONYMS WHERE SYNONYM_NAME= UPPER( ? )";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, synonymName);
         }

         if (debugLogger.isDebugEnabled()) {
            debug("Query for resolving the synonym to its sequenceName: " + query);
         }

         rs = stmt.executeQuery();
         if (rs.next()) {
            sequenceName = rs.getString("TABLE_NAME");
         } else {
            sequenceName = synonymName;
         }
      } catch (Exception var13) {
      } finally {
         closeQuietly(rs);
         closeQuietly((Statement)stmt);
      }

      return sequenceName;
   }

   private int verifySequence(Connection conn, String sequenceName, int increment, int dbType) throws WLDeploymentException {
      Statement stmt = null;
      ResultSet rs = null;
      String query = null;
      int index = false;
      String schemaName = null;
      String onlySequenceName = null;
      String containerSeq = null;
      int index;
      if ((index = sequenceName.indexOf(".")) != -1) {
         schemaName = sequenceName.substring(0, index);
         onlySequenceName = sequenceName.substring(index + 1);
         query = this.getSequenceQuery(dbType, onlySequenceName, schemaName);
      } else {
         query = this.getSequenceQuery(dbType, sequenceName, (String)null);
      }

      byte var21;
      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(query);
         int dbIncrement;
         if (!rs.next()) {
            if (schemaName != null) {
               containerSeq = RDBMSUtils.getContainerSequenceName(onlySequenceName);
               query = this.getSequenceQuery(dbType, containerSeq, schemaName);
            } else {
               containerSeq = RDBMSUtils.getContainerSequenceName(sequenceName);
               query = this.getSequenceQuery(dbType, containerSeq, (String)null);
            }

            closeQuietly(rs);
            rs = stmt.executeQuery(query);
            if (!rs.next()) {
               byte var20 = 10;
               return var20;
            }

            dbIncrement = rs.getInt(1);
            if (increment < Math.abs(dbIncrement)) {
               var21 = 15;
               return var21;
            }

            if (increment > Math.abs(dbIncrement)) {
               var21 = 16;
               return var21;
            }

            var21 = 12;
            return var21;
         }

         dbIncrement = rs.getInt(1);
         if (increment < Math.abs(dbIncrement)) {
            var21 = 13;
            return var21;
         }

         if (increment <= Math.abs(dbIncrement)) {
            var21 = 11;
            return var21;
         }

         var21 = 14;
      } catch (Exception var17) {
         Loggable l = EJBLogger.logSequenceSetupFailureLoggable(sequenceName, Integer.toString(increment), var17.getMessage());
         throw new WLDeploymentException(l.getMessage(), var17);
      } finally {
         closeQuietly(rs);
         closeQuietly(stmt);
      }

      return var21;
   }

   private String getSequenceQuery(int dbType, String sequenceName, String schemaName) {
      switch (dbType) {
         case 1:
            if (schemaName != null) {
               return "SELECT INCREMENT_BY FROM ALL_SEQUENCES WHERE SEQUENCE_NAME = UPPER('" + sequenceName + "') AND SEQUENCE_OWNER = UPPER('" + schemaName + "')";
            }

            return "SELECT INCREMENT_BY FROM ALL_SEQUENCES WHERE SEQUENCE_NAME = UPPER('" + sequenceName + "')";
         case 2:
         default:
            throw new AssertionError("Unexpected dbType: " + dbType);
         case 3:
            if (schemaName != null) {
               return "SELECT SEQ.INC_VAL FROM SYSSEQUENCES SEQ, SYSTABLES TAB WHERE UPPER(TAB.TABNAME)=UPPER('" + sequenceName + "') AND UPPER(TAB.OWNER)=UPPER('" + schemaName + "') AND SEQ.TABID=TAB.TABID";
            }

            return "SELECT SEQ.INC_VAL FROM SYSSEQUENCES SEQ, SYSTABLES TAB WHERE UPPER(TAB.TABNAME)=UPPER('" + sequenceName + "') AND SEQ.TABID=TAB.TABID";
         case 4:
            return schemaName != null ? "SELECT INCREMENT FROM SYSIBM.SYSSEQUENCES WHERE UPPER(SEQNAME)=UPPER('" + sequenceName + "') AND UPPER(SEQSCHEMA)=UPPER('" + schemaName + "')" : "SELECT INCREMENT FROM SYSIBM.SYSSEQUENCES WHERE UPPER(SEQNAME)=UPPER('" + sequenceName + "')";
      }
   }

   private String createOnlySequence(Connection conn, int seqStatus, String sequenceName, int increment, int dbType) throws WLDeploymentException {
      Debug.assertion(RDBMSUtils.isContainerSequenceName(sequenceName), "called with a non-development sequence name '" + sequenceName + "'");
      if (seqStatus != 16 && seqStatus != 15) {
         return this.createSequence(conn, sequenceName, increment, dbType);
      } else {
         Loggable l = EJBLogger.logSequenceIncrementMismatchLoggable(sequenceName, Integer.toString(increment));
         throw new WLDeploymentException(l.getMessage());
      }
   }

   private String alterOrCreateSequence(Connection conn, int seqStatus, String sequenceName, int increment, int dbType) throws WLDeploymentException {
      Debug.assertion(RDBMSUtils.isContainerSequenceName(sequenceName), "called with a non-development sequence name '" + sequenceName + "'");
      if (seqStatus == 12) {
         return sequenceName;
      } else if (seqStatus == 10) {
         return this.createOnlySequence(conn, seqStatus, sequenceName, increment, dbType);
      } else if (seqStatus != 16 && seqStatus != 15) {
         throw new AssertionError("Unknown SEQUENCE STATUS CODE: " + seqStatus);
      } else {
         return this.alterSequence(conn, sequenceName, increment, dbType);
      }
   }

   private String createSequence(Connection conn, String sequenceName, int increment, int dbType) throws WLDeploymentException {
      Debug.assertion(RDBMSUtils.isContainerSequenceName(sequenceName), "called with a non-development sequence name '" + sequenceName + "'");
      PreparedStatement stmt = null;

      String var6;
      try {
         Loggable l;
         try {
            if (dbType != 1 && dbType != 4) {
               if (dbType != 3) {
                  throw new AssertionError("Unexpected dbType: " + dbType);
               }

               stmt = conn.prepareStatement("CREATE SEQUENCE ? INCREMENT ?");
            } else {
               stmt = conn.prepareStatement("CREATE SEQUENCE ? INCREMENT BY ?");
            }

            stmt.setString(1, sequenceName);
            stmt.setInt(2, increment);

            try {
               stmt.executeUpdate();
            } catch (Exception var12) {
               l = EJBLogger.logFailedToCreateSequenceLoggable(sequenceName, Integer.toString(increment), var12.getMessage());
               throw new WLDeploymentException(l.getMessage(), var12);
            }

            var6 = sequenceName;
         } catch (Exception var13) {
            l = EJBLogger.logSequenceSetupFailureLoggable(sequenceName, Integer.toString(increment), var13.getMessage());
            throw new WLDeploymentException(l.getMessage(), var13);
         }
      } finally {
         closeQuietly((Statement)stmt);
      }

      return var6;
   }

   private String alterSequence(Connection conn, String sequenceName, int increment, int dbType) throws WLDeploymentException {
      Debug.assertion(RDBMSUtils.isContainerSequenceName(sequenceName), "called with a non-development sequence name '" + sequenceName + "'");
      PreparedStatement stmt = null;

      String var6;
      try {
         if (dbType != 1 && dbType != 4) {
            if (dbType != 3) {
               throw new AssertionError("Unexpected dbType: " + dbType);
            }

            stmt = conn.prepareStatement("ALTER SEQUENCE ? INCREMENT ?");
         } else {
            stmt = conn.prepareStatement("ALTER SEQUENCE ? INCREMENT BY ?");
         }

         stmt.setString(1, sequenceName);
         stmt.setInt(2, increment);
         stmt.executeUpdate();
         var6 = sequenceName;
      } catch (Exception var11) {
         Loggable l = EJBLogger.logFailedToAlterSequenceLoggable(sequenceName, Integer.toString(increment), var11.getMessage());
         throw new WLDeploymentException(l.getMessage(), var11);
      } finally {
         closeQuietly((Statement)stmt);
      }

      return var6;
   }

   private void throwSequenceNotExists(String sequenceName, int increment) throws WLDeploymentException {
      Loggable l = EJBLogger.logSequenceNotExistLoggable(sequenceName, Integer.toString(increment));
      throw new WLDeploymentException(l.getMessage());
   }

   private void logWarningSequenceIncrementLesserThanDBIncrement(String sequenceName, int increment) {
      EJBLogger.logWarningSequenceIncrementLesserThanDBIncrement(sequenceName, Integer.toString(increment));
   }

   private void throwSequenceIncrementGreaterThanDBIncrement(String sequenceName, int increment) throws WLDeploymentException {
      Loggable l = EJBLogger.logErrorSequenceIncrementGreaterThanDBIncrementLoggable(sequenceName, Integer.toString(increment));
      throw new WLDeploymentException(l.getMessage());
   }

   public int verifyDatabaseType(Connection conn, int databaseType) {
      try {
         DatabaseMetaData dbmd = conn.getMetaData();
         String databaseProductName = dbmd.getDatabaseProductName();
         String driverName = dbmd.getDriverName();
         if (debugLogger.isDebugEnabled()) {
            debug("DB product name is " + databaseProductName + "Driver name is " + driverName);
         }

         if (databaseProductName.indexOf("Microsoft") != -1) {
            databaseProductName = "SQL_SERVER";
         }

         if (databaseProductName.indexOf("Derby") != -1) {
            databaseProductName = "DERBY";
         } else if (databaseProductName.indexOf("DB2") != -1) {
            databaseProductName = "DB2";
         } else if (databaseProductName.indexOf("Sybase") == -1 && databaseProductName.indexOf("Adaptive Server Enterprise") == -1 && !driverName.startsWith("Sybase") && !driverName.startsWith("jConnect")) {
            if (databaseProductName.indexOf("Informix") != -1) {
               databaseProductName = "INFORMIX";
            } else if (databaseProductName.indexOf("Times") != -1) {
               databaseProductName = "TIMESTEN";
            }
         } else {
            databaseProductName = "SYBASE";
         }

         if (databaseType == 0) {
            Integer dbType = (Integer)DDConstants.DBTYPE_MAP.get(databaseProductName.toUpperCase(Locale.ENGLISH));
            if (dbType != null) {
               databaseType = dbType;
            }
         } else {
            String ddDbName = DDConstants.getDBNameForType(databaseType);
            if (ddDbName != null && ddDbName.equalsIgnoreCase("SQLSERVER")) {
               ddDbName = "SQL_SERVER";
            }

            if (ddDbName != null && ddDbName.equalsIgnoreCase("SQLSERVER2000")) {
               ddDbName = "SQL_SERVER";
            }

            if (ddDbName != null && !databaseProductName.equalsIgnoreCase(ddDbName)) {
               EJBLogger.logErrorAboutDatabaseType(ddDbName, databaseProductName);
            }
         }
      } catch (Exception var7) {
      }

      return databaseType;
   }

   protected static final void closeQuietly(ResultSet rs) {
      if (rs != null) {
         try {
            rs.close();
         } catch (Exception var2) {
         }

      }
   }

   protected static final void closeQuietly(Statement stmt) {
      if (stmt != null) {
         try {
            stmt.close();
         } catch (Exception var2) {
         }

      }
   }

   protected static final void closeQuietly(Connection conn) {
      if (conn != null) {
         try {
            conn.close();
         } catch (Exception var2) {
         }

      }
   }

   private static void debug(String s) {
      debugLogger.debug("[TableVerifier] " + s);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
