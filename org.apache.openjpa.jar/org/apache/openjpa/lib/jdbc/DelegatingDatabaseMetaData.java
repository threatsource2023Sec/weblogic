package org.apache.openjpa.lib.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DelegatingDatabaseMetaData implements DatabaseMetaData {
   private final DatabaseMetaData _metaData;
   private final Connection _conn;

   public DelegatingDatabaseMetaData(DatabaseMetaData metaData, Connection conn) {
      this._conn = conn;
      this._metaData = metaData;
   }

   public DatabaseMetaData getInnermostDelegate() {
      return this._metaData instanceof DelegatingDatabaseMetaData ? ((DelegatingDatabaseMetaData)this._metaData).getInnermostDelegate() : this._metaData;
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingDatabaseMetaData) {
            other = ((DelegatingDatabaseMetaData)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   public String toString() {
      StringBuffer buf = (new StringBuffer("metadata ")).append(this.hashCode());
      buf.append("[").append(this._metaData.toString()).append("]");
      return buf.toString();
   }

   public boolean allProceduresAreCallable() throws SQLException {
      return this._metaData.allProceduresAreCallable();
   }

   public boolean allTablesAreSelectable() throws SQLException {
      return this._metaData.allTablesAreSelectable();
   }

   public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
      return this._metaData.dataDefinitionCausesTransactionCommit();
   }

   public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
      return this._metaData.dataDefinitionIgnoredInTransactions();
   }

   public boolean deletesAreDetected(int type) throws SQLException {
      return this._metaData.deletesAreDetected(type);
   }

   public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
      return this._metaData.doesMaxRowSizeIncludeBlobs();
   }

   public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
      return this._metaData.getBestRowIdentifier(catalog, schema, table, scope, nullable);
   }

   public ResultSet getCatalogs() throws SQLException {
      return this._metaData.getCatalogs();
   }

   public String getCatalogSeparator() throws SQLException {
      return this._metaData.getCatalogSeparator();
   }

   public String getCatalogTerm() throws SQLException {
      return this._metaData.getCatalogTerm();
   }

   public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
      return this._metaData.getColumnPrivileges(catalog, schema, table, columnNamePattern);
   }

   public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
      return this._metaData.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
   }

   public Connection getConnection() throws SQLException {
      return this._conn;
   }

   public ResultSet getCrossReference(String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
      return this._metaData.getCrossReference(primaryCatalog, primarySchema, primaryTable, foreignCatalog, foreignSchema, foreignTable);
   }

   public String getDatabaseProductName() throws SQLException {
      return this._metaData.getDatabaseProductName();
   }

   public String getDatabaseProductVersion() throws SQLException {
      return this._metaData.getDatabaseProductVersion();
   }

   public int getDefaultTransactionIsolation() throws SQLException {
      return this._metaData.getDefaultTransactionIsolation();
   }

   public int getDriverMajorVersion() {
      return this._metaData.getDriverMajorVersion();
   }

   public int getDriverMinorVersion() {
      return this._metaData.getDriverMinorVersion();
   }

   public String getDriverName() throws SQLException {
      return this._metaData.getDriverName();
   }

   public String getDriverVersion() throws SQLException {
      return this._metaData.getDriverVersion();
   }

   public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
      return this._metaData.getExportedKeys(catalog, schema, table);
   }

   public String getExtraNameCharacters() throws SQLException {
      return this._metaData.getExtraNameCharacters();
   }

   public String getIdentifierQuoteString() throws SQLException {
      return this._metaData.getIdentifierQuoteString();
   }

   public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
      return this._metaData.getImportedKeys(catalog, schema, table);
   }

   public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
      return this._metaData.getIndexInfo(catalog, schema, table, unique, approximate);
   }

   public int getMaxBinaryLiteralLength() throws SQLException {
      return this._metaData.getMaxBinaryLiteralLength();
   }

   public int getMaxCatalogNameLength() throws SQLException {
      return this._metaData.getMaxCatalogNameLength();
   }

   public int getMaxCharLiteralLength() throws SQLException {
      return this._metaData.getMaxCharLiteralLength();
   }

   public int getMaxColumnNameLength() throws SQLException {
      return this._metaData.getMaxColumnNameLength();
   }

   public int getMaxColumnsInGroupBy() throws SQLException {
      return this._metaData.getMaxColumnsInGroupBy();
   }

   public int getMaxColumnsInIndex() throws SQLException {
      return this._metaData.getMaxColumnsInIndex();
   }

   public int getMaxColumnsInOrderBy() throws SQLException {
      return this._metaData.getMaxColumnsInOrderBy();
   }

   public int getMaxColumnsInSelect() throws SQLException {
      return this._metaData.getMaxColumnsInSelect();
   }

   public int getMaxColumnsInTable() throws SQLException {
      return this._metaData.getMaxColumnsInTable();
   }

   public int getMaxConnections() throws SQLException {
      return this._metaData.getMaxConnections();
   }

   public int getMaxCursorNameLength() throws SQLException {
      return this._metaData.getMaxCursorNameLength();
   }

   public int getMaxIndexLength() throws SQLException {
      return this._metaData.getMaxIndexLength();
   }

   public int getMaxProcedureNameLength() throws SQLException {
      return this._metaData.getMaxProcedureNameLength();
   }

   public int getMaxRowSize() throws SQLException {
      return this._metaData.getMaxRowSize();
   }

   public int getMaxSchemaNameLength() throws SQLException {
      return this._metaData.getMaxSchemaNameLength();
   }

   public int getMaxStatementLength() throws SQLException {
      return this._metaData.getMaxStatementLength();
   }

   public int getMaxStatements() throws SQLException {
      return this._metaData.getMaxStatements();
   }

   public int getMaxTableNameLength() throws SQLException {
      return this._metaData.getMaxTableNameLength();
   }

   public int getMaxTablesInSelect() throws SQLException {
      return this._metaData.getMaxTablesInSelect();
   }

   public int getMaxUserNameLength() throws SQLException {
      return this._metaData.getMaxUserNameLength();
   }

   public String getNumericFunctions() throws SQLException {
      return this._metaData.getNumericFunctions();
   }

   public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
      return this._metaData.getPrimaryKeys(catalog, schema, table);
   }

   public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
      return this._metaData.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern);
   }

   public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
      return this._metaData.getProcedures(catalog, schemaPattern, procedureNamePattern);
   }

   public String getProcedureTerm() throws SQLException {
      return this._metaData.getProcedureTerm();
   }

   public ResultSet getSchemas() throws SQLException {
      return this._metaData.getSchemas();
   }

   public String getSchemaTerm() throws SQLException {
      return this._metaData.getSchemaTerm();
   }

   public String getSearchStringEscape() throws SQLException {
      return this._metaData.getSearchStringEscape();
   }

   public String getSQLKeywords() throws SQLException {
      return this._metaData.getSQLKeywords();
   }

   public String getStringFunctions() throws SQLException {
      return this._metaData.getStringFunctions();
   }

   public String getSystemFunctions() throws SQLException {
      return this._metaData.getSystemFunctions();
   }

   public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
      return this._metaData.getTablePrivileges(catalog, schemaPattern, tableNamePattern);
   }

   public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
      return this._metaData.getTables(catalog, schemaPattern, tableNamePattern, types);
   }

   public ResultSet getTableTypes() throws SQLException {
      return this._metaData.getTableTypes();
   }

   public String getTimeDateFunctions() throws SQLException {
      return this._metaData.getTimeDateFunctions();
   }

   public ResultSet getTypeInfo() throws SQLException {
      return this._metaData.getTypeInfo();
   }

   public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
      return this._metaData.getUDTs(catalog, schemaPattern, typeNamePattern, types);
   }

   public String getURL() throws SQLException {
      return this._metaData.getURL();
   }

   public String getUserName() throws SQLException {
      return this._metaData.getUserName();
   }

   public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
      return this._metaData.getVersionColumns(catalog, schema, table);
   }

   public boolean insertsAreDetected(int type) throws SQLException {
      return this._metaData.insertsAreDetected(type);
   }

   public boolean isCatalogAtStart() throws SQLException {
      return this._metaData.isCatalogAtStart();
   }

   public boolean isReadOnly() throws SQLException {
      return this._metaData.isReadOnly();
   }

   public boolean nullPlusNonNullIsNull() throws SQLException {
      return this._metaData.nullPlusNonNullIsNull();
   }

   public boolean nullsAreSortedAtEnd() throws SQLException {
      return this._metaData.nullsAreSortedAtEnd();
   }

   public boolean nullsAreSortedAtStart() throws SQLException {
      return this._metaData.nullsAreSortedAtStart();
   }

   public boolean nullsAreSortedHigh() throws SQLException {
      return this._metaData.nullsAreSortedHigh();
   }

   public boolean nullsAreSortedLow() throws SQLException {
      return this._metaData.nullsAreSortedLow();
   }

   public boolean othersDeletesAreVisible(int type) throws SQLException {
      return this._metaData.othersDeletesAreVisible(type);
   }

   public boolean othersInsertsAreVisible(int type) throws SQLException {
      return this._metaData.othersInsertsAreVisible(type);
   }

   public boolean othersUpdatesAreVisible(int type) throws SQLException {
      return this._metaData.othersUpdatesAreVisible(type);
   }

   public boolean ownDeletesAreVisible(int type) throws SQLException {
      return this._metaData.ownDeletesAreVisible(type);
   }

   public boolean ownInsertsAreVisible(int type) throws SQLException {
      return this._metaData.ownInsertsAreVisible(type);
   }

   public boolean ownUpdatesAreVisible(int type) throws SQLException {
      return this._metaData.ownUpdatesAreVisible(type);
   }

   public boolean storesLowerCaseIdentifiers() throws SQLException {
      return this._metaData.storesLowerCaseIdentifiers();
   }

   public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
      return this._metaData.storesLowerCaseQuotedIdentifiers();
   }

   public boolean storesMixedCaseIdentifiers() throws SQLException {
      return this._metaData.storesMixedCaseIdentifiers();
   }

   public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
      return this._metaData.storesMixedCaseQuotedIdentifiers();
   }

   public boolean storesUpperCaseIdentifiers() throws SQLException {
      return this._metaData.storesUpperCaseIdentifiers();
   }

   public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
      return this._metaData.storesUpperCaseQuotedIdentifiers();
   }

   public boolean supportsAlterTableWithAddColumn() throws SQLException {
      return this._metaData.supportsAlterTableWithAddColumn();
   }

   public boolean supportsAlterTableWithDropColumn() throws SQLException {
      return this._metaData.supportsAlterTableWithDropColumn();
   }

   public boolean supportsANSI92EntryLevelSQL() throws SQLException {
      return this._metaData.supportsANSI92EntryLevelSQL();
   }

   public boolean supportsANSI92FullSQL() throws SQLException {
      return this._metaData.supportsANSI92FullSQL();
   }

   public boolean supportsANSI92IntermediateSQL() throws SQLException {
      return this._metaData.supportsANSI92IntermediateSQL();
   }

   public boolean supportsBatchUpdates() throws SQLException {
      return this._metaData.supportsBatchUpdates();
   }

   public boolean supportsCatalogsInDataManipulation() throws SQLException {
      return this._metaData.supportsCatalogsInDataManipulation();
   }

   public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
      return this._metaData.supportsCatalogsInIndexDefinitions();
   }

   public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
      return this._metaData.supportsCatalogsInPrivilegeDefinitions();
   }

   public boolean supportsCatalogsInProcedureCalls() throws SQLException {
      return this._metaData.supportsCatalogsInProcedureCalls();
   }

   public boolean supportsCatalogsInTableDefinitions() throws SQLException {
      return this._metaData.supportsCatalogsInTableDefinitions();
   }

   public boolean supportsColumnAliasing() throws SQLException {
      return this._metaData.supportsColumnAliasing();
   }

   public boolean supportsConvert() throws SQLException {
      return this._metaData.supportsConvert();
   }

   public boolean supportsConvert(int fromType, int toType) throws SQLException {
      return this._metaData.supportsConvert(fromType, toType);
   }

   public boolean supportsCoreSQLGrammar() throws SQLException {
      return this._metaData.supportsCoreSQLGrammar();
   }

   public boolean supportsCorrelatedSubqueries() throws SQLException {
      return this._metaData.supportsCorrelatedSubqueries();
   }

   public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
      return this._metaData.supportsDataDefinitionAndDataManipulationTransactions();
   }

   public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
      return this._metaData.supportsDataManipulationTransactionsOnly();
   }

   public boolean supportsDifferentTableCorrelationNames() throws SQLException {
      return this._metaData.supportsDifferentTableCorrelationNames();
   }

   public boolean supportsExpressionsInOrderBy() throws SQLException {
      return this._metaData.supportsExpressionsInOrderBy();
   }

   public boolean supportsExtendedSQLGrammar() throws SQLException {
      return this._metaData.supportsExtendedSQLGrammar();
   }

   public boolean supportsFullOuterJoins() throws SQLException {
      return this._metaData.supportsFullOuterJoins();
   }

   public boolean supportsGroupBy() throws SQLException {
      return this._metaData.supportsGroupBy();
   }

   public boolean supportsGroupByBeyondSelect() throws SQLException {
      return this._metaData.supportsGroupByBeyondSelect();
   }

   public boolean supportsGroupByUnrelated() throws SQLException {
      return this._metaData.supportsGroupByUnrelated();
   }

   public boolean supportsIntegrityEnhancementFacility() throws SQLException {
      return this._metaData.supportsIntegrityEnhancementFacility();
   }

   public boolean supportsLikeEscapeClause() throws SQLException {
      return this._metaData.supportsLikeEscapeClause();
   }

   public boolean supportsLimitedOuterJoins() throws SQLException {
      return this._metaData.supportsLimitedOuterJoins();
   }

   public boolean supportsMinimumSQLGrammar() throws SQLException {
      return this._metaData.supportsMinimumSQLGrammar();
   }

   public boolean supportsMixedCaseIdentifiers() throws SQLException {
      return this._metaData.supportsMixedCaseIdentifiers();
   }

   public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
      return this._metaData.supportsMixedCaseQuotedIdentifiers();
   }

   public boolean supportsMultipleResultSets() throws SQLException {
      return this._metaData.supportsMultipleResultSets();
   }

   public boolean supportsMultipleTransactions() throws SQLException {
      return this._metaData.supportsMultipleTransactions();
   }

   public boolean supportsNonNullableColumns() throws SQLException {
      return this._metaData.supportsNonNullableColumns();
   }

   public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
      return this._metaData.supportsOpenCursorsAcrossCommit();
   }

   public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
      return this._metaData.supportsOpenCursorsAcrossRollback();
   }

   public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
      return this._metaData.supportsOpenStatementsAcrossCommit();
   }

   public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
      return this._metaData.supportsOpenStatementsAcrossRollback();
   }

   public boolean supportsOrderByUnrelated() throws SQLException {
      return this._metaData.supportsOrderByUnrelated();
   }

   public boolean supportsOuterJoins() throws SQLException {
      return this._metaData.supportsOuterJoins();
   }

   public boolean supportsPositionedDelete() throws SQLException {
      return this._metaData.supportsPositionedDelete();
   }

   public boolean supportsPositionedUpdate() throws SQLException {
      return this._metaData.supportsPositionedUpdate();
   }

   public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
      return this._metaData.supportsResultSetConcurrency(type, concurrency);
   }

   public boolean supportsResultSetType(int type) throws SQLException {
      return this._metaData.supportsResultSetType(type);
   }

   public boolean supportsSchemasInDataManipulation() throws SQLException {
      return this._metaData.supportsSchemasInDataManipulation();
   }

   public boolean supportsSchemasInIndexDefinitions() throws SQLException {
      return this._metaData.supportsSchemasInIndexDefinitions();
   }

   public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
      return this._metaData.supportsSchemasInPrivilegeDefinitions();
   }

   public boolean supportsSchemasInProcedureCalls() throws SQLException {
      return this._metaData.supportsSchemasInProcedureCalls();
   }

   public boolean supportsSchemasInTableDefinitions() throws SQLException {
      return this._metaData.supportsSchemasInTableDefinitions();
   }

   public boolean supportsSelectForUpdate() throws SQLException {
      return this._metaData.supportsSelectForUpdate();
   }

   public boolean supportsStoredProcedures() throws SQLException {
      return this._metaData.supportsStoredProcedures();
   }

   public boolean supportsSubqueriesInComparisons() throws SQLException {
      return this._metaData.supportsSubqueriesInComparisons();
   }

   public boolean supportsSubqueriesInExists() throws SQLException {
      return this._metaData.supportsSubqueriesInExists();
   }

   public boolean supportsSubqueriesInIns() throws SQLException {
      return this._metaData.supportsSubqueriesInIns();
   }

   public boolean supportsSubqueriesInQuantifieds() throws SQLException {
      return this._metaData.supportsSubqueriesInQuantifieds();
   }

   public boolean supportsTableCorrelationNames() throws SQLException {
      return this._metaData.supportsTableCorrelationNames();
   }

   public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
      return this._metaData.supportsTransactionIsolationLevel(level);
   }

   public boolean supportsTransactions() throws SQLException {
      return this._metaData.supportsTransactions();
   }

   public boolean supportsUnion() throws SQLException {
      return this._metaData.supportsUnion();
   }

   public boolean supportsUnionAll() throws SQLException {
      return this._metaData.supportsUnionAll();
   }

   public boolean updatesAreDetected(int type) throws SQLException {
      return this._metaData.updatesAreDetected(type);
   }

   public boolean usesLocalFilePerTable() throws SQLException {
      return this._metaData.usesLocalFilePerTable();
   }

   public boolean usesLocalFiles() throws SQLException {
      return this._metaData.usesLocalFiles();
   }

   public boolean supportsSavepoints() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public boolean supportsNamedParameters() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public boolean supportsMultipleOpenResults() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public boolean supportsGetGeneratedKeys() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public ResultSet getSuperTypes(String catalog, String schemaPatter, String typeNamePattern) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public ResultSet getSuperTables(String catalog, String schemaPatter, String tableNamePattern) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public ResultSet getAttributes(String catalog, String schemaPatter, String typeNamePattern, String attributeNamePattern) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public boolean supportsResultSetHoldability(int holdability) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public int getResultSetHoldability() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public int getDatabaseMajorVersion() throws SQLException {
      return this._metaData.getDatabaseMajorVersion();
   }

   public int getDatabaseMinorVersion() throws SQLException {
      return this._metaData.getDatabaseMinorVersion();
   }

   public int getJDBCMajorVersion() throws SQLException {
      return this._metaData.getJDBCMajorVersion();
   }

   public int getJDBCMinorVersion() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public int getSQLStateType() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public boolean locatorsUpdateCopy() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public boolean supportsStatementPooling() throws SQLException {
      throw new UnsupportedOperationException();
   }
}
