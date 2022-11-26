package kodo.jdbc.conf.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class BuiltInDBDictionaryBeanImpl extends DBDictionaryBeanImpl implements BuiltInDBDictionaryBean, Serializable {
   private boolean _AllowsAliasInBulkClause;
   private String _ArrayTypeName;
   private String _AutoAssignClause;
   private String _AutoAssignTypeName;
   private String _BigintTypeName;
   private String _BinaryTypeName;
   private String _BitLengthFunction;
   private String _BitTypeName;
   private String _BlobTypeName;
   private String _BooleanTypeName;
   private String _CastFunction;
   private String _CatalogSeparator;
   private String _CharTypeName;
   private int _CharacterColumnSize;
   private String _ClobTypeName;
   private String _ClosePoolSQL;
   private String _ConcatenateDelimiter;
   private String _ConcatenateFunction;
   private String _ConstraintNameMode;
   private boolean _CreatePrimaryKeys;
   private String _CrossJoinClause;
   private String _CurrentDateFunction;
   private String _CurrentTimeFunction;
   private String _CurrentTimestampFunction;
   private int _DatePrecision;
   private String _DateTypeName;
   private String _DecimalTypeName;
   private String _DistinctCountColumnSeparator;
   private String _DistinctTypeName;
   private String _DoubleTypeName;
   private String _DriverVendor;
   private String _DropTableSQL;
   private String _FixedSizeTypeNames;
   private String _FloatTypeName;
   private String _ForUpdateClause;
   private String _InitializationSQL;
   private String _InnerJoinClause;
   private String _IntegerTypeName;
   private String _JavaObjectTypeName;
   private String _JoinSyntax;
   private String _LastGeneratedKeyQuery;
   private String _LongVarbinaryTypeName;
   private String _LongVarcharTypeName;
   private int _MaxAutoAssignNameLength;
   private int _MaxColumnNameLength;
   private int _MaxConstraintNameLength;
   private int _MaxEmbeddedBlobSize;
   private int _MaxEmbeddedClobSize;
   private int _MaxIndexNameLength;
   private int _MaxIndexesPerTable;
   private int _MaxTableNameLength;
   private String _NextSequenceQuery;
   private String _NullTypeName;
   private String _NumericTypeName;
   private String _OtherTypeName;
   private String _OuterJoinClause;
   private String _Platform;
   private int _RangePosition;
   private String _RealTypeName;
   private String _RefTypeName;
   private boolean _RequiresAliasForSubselect;
   private boolean _RequiresAutoCommitForMetaData;
   private boolean _RequiresCastForComparisons;
   private boolean _RequiresCastForMathFunctions;
   private boolean _RequiresConditionForCrossJoin;
   private String _ReservedWords;
   private String _SchemaCase;
   private String _SearchStringEscape;
   private boolean _SimulateLocking;
   private String _SmallintTypeName;
   private boolean _StorageLimitationsFatal;
   private boolean _StoreCharsAsNumbers;
   private boolean _StoreLargeNumbersAsStrings;
   private String _StringLengthFunction;
   private String _StructTypeName;
   private String _SubstringFunctionName;
   private boolean _SupportsAlterTableWithAddColumn;
   private boolean _SupportsAlterTableWithDropColumn;
   private boolean _SupportsAutoAssign;
   private boolean _SupportsCascadeDeleteAction;
   private boolean _SupportsCascadeUpdateAction;
   private boolean _SupportsCorrelatedSubselect;
   private boolean _SupportsDefaultDeleteAction;
   private boolean _SupportsDefaultUpdateAction;
   private boolean _SupportsDeferredConstraints;
   private boolean _SupportsForeignKeys;
   private boolean _SupportsHaving;
   private boolean _SupportsLockingWithDistinctClause;
   private boolean _SupportsLockingWithInnerJoin;
   private boolean _SupportsLockingWithMultipleTables;
   private boolean _SupportsLockingWithOrderClause;
   private boolean _SupportsLockingWithOuterJoin;
   private boolean _SupportsLockingWithSelectRange;
   private boolean _SupportsModOperator;
   private boolean _SupportsMultipleNontransactionalResultSets;
   private boolean _SupportsNullDeleteAction;
   private boolean _SupportsNullTableForGetColumns;
   private boolean _SupportsNullTableForGetImportedKeys;
   private boolean _SupportsNullTableForGetIndexInfo;
   private boolean _SupportsNullTableForGetPrimaryKeys;
   private boolean _SupportsNullUpdateAction;
   private boolean _SupportsQueryTimeout;
   private boolean _SupportsRestrictDeleteAction;
   private boolean _SupportsRestrictUpdateAction;
   private boolean _SupportsSchemaForGetColumns;
   private boolean _SupportsSchemaForGetTables;
   private boolean _SupportsSelectEndIndex;
   private boolean _SupportsSelectForUpdate;
   private boolean _SupportsSelectStartIndex;
   private boolean _SupportsSubselect;
   private boolean _SupportsTimestampNanos;
   private boolean _SupportsUniqueConstraints;
   private String _SystemSchemas;
   private String _SystemTables;
   private String _TableForUpdateClause;
   private String _TableTypes;
   private String _TimeTypeName;
   private String _TimestampTypeName;
   private String _TinyintTypeName;
   private String _ToLowerCaseFunction;
   private String _ToUpperCaseFunction;
   private String _TrimBothFunction;
   private String _TrimLeadingFunction;
   private String _TrimTrailingFunction;
   private boolean _UseGetBestRowIdentifierForPrimaryKeys;
   private boolean _UseGetBytesForBlobs;
   private boolean _UseGetObjectForBlobs;
   private boolean _UseGetStringForClobs;
   private boolean _UseSchemaName;
   private boolean _UseSetBytesForBlobs;
   private boolean _UseSetStringForClobs;
   private String _ValidationSQL;
   private String _VarbinaryTypeName;
   private String _VarcharTypeName;
   private static SchemaHelper2 _schemaHelper;

   public BuiltInDBDictionaryBeanImpl() {
      this._initializeProperty(-1);
   }

   public BuiltInDBDictionaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public BuiltInDBDictionaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getCharTypeName() {
      return this._CharTypeName;
   }

   public boolean isCharTypeNameInherited() {
      return false;
   }

   public boolean isCharTypeNameSet() {
      return this._isSet(1);
   }

   public void setCharTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CharTypeName;
      this._CharTypeName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getOuterJoinClause() {
      return this._OuterJoinClause;
   }

   public boolean isOuterJoinClauseInherited() {
      return false;
   }

   public boolean isOuterJoinClauseSet() {
      return this._isSet(2);
   }

   public void setOuterJoinClause(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OuterJoinClause;
      this._OuterJoinClause = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getBinaryTypeName() {
      return this._BinaryTypeName;
   }

   public boolean isBinaryTypeNameInherited() {
      return false;
   }

   public boolean isBinaryTypeNameSet() {
      return this._isSet(3);
   }

   public void setBinaryTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BinaryTypeName;
      this._BinaryTypeName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getClobTypeName() {
      return this._ClobTypeName;
   }

   public boolean isClobTypeNameInherited() {
      return false;
   }

   public boolean isClobTypeNameSet() {
      return this._isSet(4);
   }

   public void setClobTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClobTypeName;
      this._ClobTypeName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean getSupportsLockingWithDistinctClause() {
      return this._SupportsLockingWithDistinctClause;
   }

   public boolean isSupportsLockingWithDistinctClauseInherited() {
      return false;
   }

   public boolean isSupportsLockingWithDistinctClauseSet() {
      return this._isSet(5);
   }

   public void setSupportsLockingWithDistinctClause(boolean param0) {
      boolean _oldVal = this._SupportsLockingWithDistinctClause;
      this._SupportsLockingWithDistinctClause = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean getSimulateLocking() {
      return this._SimulateLocking;
   }

   public boolean isSimulateLockingInherited() {
      return false;
   }

   public boolean isSimulateLockingSet() {
      return this._isSet(6);
   }

   public void setSimulateLocking(boolean param0) {
      boolean _oldVal = this._SimulateLocking;
      this._SimulateLocking = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getSystemTables() {
      return this._SystemTables;
   }

   public boolean isSystemTablesInherited() {
      return false;
   }

   public boolean isSystemTablesSet() {
      return this._isSet(7);
   }

   public void setSystemTables(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SystemTables;
      this._SystemTables = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getConcatenateFunction() {
      return this._ConcatenateFunction;
   }

   public boolean isConcatenateFunctionInherited() {
      return false;
   }

   public boolean isConcatenateFunctionSet() {
      return this._isSet(8);
   }

   public void setConcatenateFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConcatenateFunction;
      this._ConcatenateFunction = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getSubstringFunctionName() {
      return this._SubstringFunctionName;
   }

   public boolean isSubstringFunctionNameInherited() {
      return false;
   }

   public boolean isSubstringFunctionNameSet() {
      return this._isSet(9);
   }

   public void setSubstringFunctionName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SubstringFunctionName;
      this._SubstringFunctionName = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean getSupportsQueryTimeout() {
      return this._SupportsQueryTimeout;
   }

   public boolean isSupportsQueryTimeoutInherited() {
      return false;
   }

   public boolean isSupportsQueryTimeoutSet() {
      return this._isSet(10);
   }

   public void setSupportsQueryTimeout(boolean param0) {
      boolean _oldVal = this._SupportsQueryTimeout;
      this._SupportsQueryTimeout = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean getUseSetBytesForBlobs() {
      return this._UseSetBytesForBlobs;
   }

   public boolean isUseSetBytesForBlobsInherited() {
      return false;
   }

   public boolean isUseSetBytesForBlobsSet() {
      return this._isSet(11);
   }

   public void setUseSetBytesForBlobs(boolean param0) {
      boolean _oldVal = this._UseSetBytesForBlobs;
      this._UseSetBytesForBlobs = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getMaxConstraintNameLength() {
      return this._MaxConstraintNameLength;
   }

   public boolean isMaxConstraintNameLengthInherited() {
      return false;
   }

   public boolean isMaxConstraintNameLengthSet() {
      return this._isSet(12);
   }

   public void setMaxConstraintNameLength(int param0) {
      int _oldVal = this._MaxConstraintNameLength;
      this._MaxConstraintNameLength = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getSearchStringEscape() {
      return this._SearchStringEscape;
   }

   public boolean isSearchStringEscapeInherited() {
      return false;
   }

   public boolean isSearchStringEscapeSet() {
      return this._isSet(13);
   }

   public void setSearchStringEscape(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SearchStringEscape;
      this._SearchStringEscape = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean getSupportsCascadeUpdateAction() {
      return this._SupportsCascadeUpdateAction;
   }

   public boolean isSupportsCascadeUpdateActionInherited() {
      return false;
   }

   public boolean isSupportsCascadeUpdateActionSet() {
      return this._isSet(14);
   }

   public void setSupportsCascadeUpdateAction(boolean param0) {
      boolean _oldVal = this._SupportsCascadeUpdateAction;
      this._SupportsCascadeUpdateAction = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getStringLengthFunction() {
      return this._StringLengthFunction;
   }

   public boolean isStringLengthFunctionInherited() {
      return false;
   }

   public boolean isStringLengthFunctionSet() {
      return this._isSet(15);
   }

   public void setStringLengthFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._StringLengthFunction;
      this._StringLengthFunction = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getLongVarbinaryTypeName() {
      return this._LongVarbinaryTypeName;
   }

   public boolean isLongVarbinaryTypeNameInherited() {
      return false;
   }

   public boolean isLongVarbinaryTypeNameSet() {
      return this._isSet(16);
   }

   public void setLongVarbinaryTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LongVarbinaryTypeName;
      this._LongVarbinaryTypeName = param0;
      this._postSet(16, _oldVal, param0);
   }

   public boolean getSupportsUniqueConstraints() {
      return this._SupportsUniqueConstraints;
   }

   public boolean isSupportsUniqueConstraintsInherited() {
      return false;
   }

   public boolean isSupportsUniqueConstraintsSet() {
      return this._isSet(17);
   }

   public void setSupportsUniqueConstraints(boolean param0) {
      boolean _oldVal = this._SupportsUniqueConstraints;
      this._SupportsUniqueConstraints = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean getSupportsRestrictDeleteAction() {
      return this._SupportsRestrictDeleteAction;
   }

   public boolean isSupportsRestrictDeleteActionInherited() {
      return false;
   }

   public boolean isSupportsRestrictDeleteActionSet() {
      return this._isSet(18);
   }

   public void setSupportsRestrictDeleteAction(boolean param0) {
      boolean _oldVal = this._SupportsRestrictDeleteAction;
      this._SupportsRestrictDeleteAction = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getTrimLeadingFunction() {
      return this._TrimLeadingFunction;
   }

   public boolean isTrimLeadingFunctionInherited() {
      return false;
   }

   public boolean isTrimLeadingFunctionSet() {
      return this._isSet(19);
   }

   public void setTrimLeadingFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TrimLeadingFunction;
      this._TrimLeadingFunction = param0;
      this._postSet(19, _oldVal, param0);
   }

   public boolean getSupportsDefaultDeleteAction() {
      return this._SupportsDefaultDeleteAction;
   }

   public boolean isSupportsDefaultDeleteActionInherited() {
      return false;
   }

   public boolean isSupportsDefaultDeleteActionSet() {
      return this._isSet(20);
   }

   public void setSupportsDefaultDeleteAction(boolean param0) {
      boolean _oldVal = this._SupportsDefaultDeleteAction;
      this._SupportsDefaultDeleteAction = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getNextSequenceQuery() {
      return this._NextSequenceQuery;
   }

   public boolean isNextSequenceQueryInherited() {
      return false;
   }

   public boolean isNextSequenceQuerySet() {
      return this._isSet(21);
   }

   public void setNextSequenceQuery(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NextSequenceQuery;
      this._NextSequenceQuery = param0;
      this._postSet(21, _oldVal, param0);
   }

   public String getLongVarcharTypeName() {
      return this._LongVarcharTypeName;
   }

   public boolean isLongVarcharTypeNameInherited() {
      return false;
   }

   public boolean isLongVarcharTypeNameSet() {
      return this._isSet(22);
   }

   public void setLongVarcharTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LongVarcharTypeName;
      this._LongVarcharTypeName = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getCrossJoinClause() {
      return this._CrossJoinClause;
   }

   public boolean isCrossJoinClauseInherited() {
      return false;
   }

   public boolean isCrossJoinClauseSet() {
      return this._isSet(23);
   }

   public void setCrossJoinClause(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CrossJoinClause;
      this._CrossJoinClause = param0;
      this._postSet(23, _oldVal, param0);
   }

   public int getMaxEmbeddedClobSize() {
      return this._MaxEmbeddedClobSize;
   }

   public boolean isMaxEmbeddedClobSizeInherited() {
      return false;
   }

   public boolean isMaxEmbeddedClobSizeSet() {
      return this._isSet(24);
   }

   public void setMaxEmbeddedClobSize(int param0) {
      int _oldVal = this._MaxEmbeddedClobSize;
      this._MaxEmbeddedClobSize = param0;
      this._postSet(24, _oldVal, param0);
   }

   public String getDateTypeName() {
      return this._DateTypeName;
   }

   public boolean isDateTypeNameInherited() {
      return false;
   }

   public boolean isDateTypeNameSet() {
      return this._isSet(25);
   }

   public void setDateTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DateTypeName;
      this._DateTypeName = param0;
      this._postSet(25, _oldVal, param0);
   }

   public boolean getSupportsSchemaForGetTables() {
      return this._SupportsSchemaForGetTables;
   }

   public boolean isSupportsSchemaForGetTablesInherited() {
      return false;
   }

   public boolean isSupportsSchemaForGetTablesSet() {
      return this._isSet(26);
   }

   public void setSupportsSchemaForGetTables(boolean param0) {
      boolean _oldVal = this._SupportsSchemaForGetTables;
      this._SupportsSchemaForGetTables = param0;
      this._postSet(26, _oldVal, param0);
   }

   public boolean getSupportsAlterTableWithDropColumn() {
      return this._SupportsAlterTableWithDropColumn;
   }

   public boolean isSupportsAlterTableWithDropColumnInherited() {
      return false;
   }

   public boolean isSupportsAlterTableWithDropColumnSet() {
      return this._isSet(27);
   }

   public void setSupportsAlterTableWithDropColumn(boolean param0) {
      boolean _oldVal = this._SupportsAlterTableWithDropColumn;
      this._SupportsAlterTableWithDropColumn = param0;
      this._postSet(27, _oldVal, param0);
   }

   public String getCurrentTimeFunction() {
      return this._CurrentTimeFunction;
   }

   public boolean isCurrentTimeFunctionInherited() {
      return false;
   }

   public boolean isCurrentTimeFunctionSet() {
      return this._isSet(28);
   }

   public void setCurrentTimeFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CurrentTimeFunction;
      this._CurrentTimeFunction = param0;
      this._postSet(28, _oldVal, param0);
   }

   public boolean getRequiresConditionForCrossJoin() {
      return this._RequiresConditionForCrossJoin;
   }

   public boolean isRequiresConditionForCrossJoinInherited() {
      return false;
   }

   public boolean isRequiresConditionForCrossJoinSet() {
      return this._isSet(29);
   }

   public void setRequiresConditionForCrossJoin(boolean param0) {
      boolean _oldVal = this._RequiresConditionForCrossJoin;
      this._RequiresConditionForCrossJoin = param0;
      this._postSet(29, _oldVal, param0);
   }

   public String getRefTypeName() {
      return this._RefTypeName;
   }

   public boolean isRefTypeNameInherited() {
      return false;
   }

   public boolean isRefTypeNameSet() {
      return this._isSet(30);
   }

   public void setRefTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RefTypeName;
      this._RefTypeName = param0;
      this._postSet(30, _oldVal, param0);
   }

   public String getConcatenateDelimiter() {
      return this._ConcatenateDelimiter;
   }

   public boolean isConcatenateDelimiterInherited() {
      return false;
   }

   public boolean isConcatenateDelimiterSet() {
      return this._isSet(31);
   }

   public void setConcatenateDelimiter(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConcatenateDelimiter;
      this._ConcatenateDelimiter = param0;
      this._postSet(31, _oldVal, param0);
   }

   public String getCatalogSeparator() {
      return this._CatalogSeparator;
   }

   public boolean isCatalogSeparatorInherited() {
      return false;
   }

   public boolean isCatalogSeparatorSet() {
      return this._isSet(32);
   }

   public void setCatalogSeparator(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CatalogSeparator;
      this._CatalogSeparator = param0;
      this._postSet(32, _oldVal, param0);
   }

   public boolean getSupportsModOperator() {
      return this._SupportsModOperator;
   }

   public boolean isSupportsModOperatorInherited() {
      return false;
   }

   public boolean isSupportsModOperatorSet() {
      return this._isSet(33);
   }

   public void setSupportsModOperator(boolean param0) {
      boolean _oldVal = this._SupportsModOperator;
      this._SupportsModOperator = param0;
      this._postSet(33, _oldVal, param0);
   }

   public String getSchemaCase() {
      return this._SchemaCase;
   }

   public boolean isSchemaCaseInherited() {
      return false;
   }

   public boolean isSchemaCaseSet() {
      return this._isSet(34);
   }

   public void setSchemaCase(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SchemaCase;
      this._SchemaCase = param0;
      this._postSet(34, _oldVal, param0);
   }

   public String getJavaObjectTypeName() {
      return this._JavaObjectTypeName;
   }

   public boolean isJavaObjectTypeNameInherited() {
      return false;
   }

   public boolean isJavaObjectTypeNameSet() {
      return this._isSet(35);
   }

   public void setJavaObjectTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JavaObjectTypeName;
      this._JavaObjectTypeName = param0;
      this._postSet(35, _oldVal, param0);
   }

   public String getDriverVendor() {
      return this._DriverVendor;
   }

   public boolean isDriverVendorInherited() {
      return false;
   }

   public boolean isDriverVendorSet() {
      return this._isSet(36);
   }

   public void setDriverVendor(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DriverVendor;
      this._DriverVendor = param0;
      this._postSet(36, _oldVal, param0);
   }

   public boolean getSupportsLockingWithMultipleTables() {
      return this._SupportsLockingWithMultipleTables;
   }

   public boolean isSupportsLockingWithMultipleTablesInherited() {
      return false;
   }

   public boolean isSupportsLockingWithMultipleTablesSet() {
      return this._isSet(37);
   }

   public void setSupportsLockingWithMultipleTables(boolean param0) {
      boolean _oldVal = this._SupportsLockingWithMultipleTables;
      this._SupportsLockingWithMultipleTables = param0;
      this._postSet(37, _oldVal, param0);
   }

   public int getMaxColumnNameLength() {
      return this._MaxColumnNameLength;
   }

   public boolean isMaxColumnNameLengthInherited() {
      return false;
   }

   public boolean isMaxColumnNameLengthSet() {
      return this._isSet(38);
   }

   public void setMaxColumnNameLength(int param0) {
      int _oldVal = this._MaxColumnNameLength;
      this._MaxColumnNameLength = param0;
      this._postSet(38, _oldVal, param0);
   }

   public String getDoubleTypeName() {
      return this._DoubleTypeName;
   }

   public boolean isDoubleTypeNameInherited() {
      return false;
   }

   public boolean isDoubleTypeNameSet() {
      return this._isSet(39);
   }

   public void setDoubleTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DoubleTypeName;
      this._DoubleTypeName = param0;
      this._postSet(39, _oldVal, param0);
   }

   public boolean getUseGetStringForClobs() {
      return this._UseGetStringForClobs;
   }

   public boolean isUseGetStringForClobsInherited() {
      return false;
   }

   public boolean isUseGetStringForClobsSet() {
      return this._isSet(40);
   }

   public void setUseGetStringForClobs(boolean param0) {
      boolean _oldVal = this._UseGetStringForClobs;
      this._UseGetStringForClobs = param0;
      this._postSet(40, _oldVal, param0);
   }

   public String getDecimalTypeName() {
      return this._DecimalTypeName;
   }

   public boolean isDecimalTypeNameInherited() {
      return false;
   }

   public boolean isDecimalTypeNameSet() {
      return this._isSet(41);
   }

   public void setDecimalTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DecimalTypeName;
      this._DecimalTypeName = param0;
      this._postSet(41, _oldVal, param0);
   }

   public String getSmallintTypeName() {
      return this._SmallintTypeName;
   }

   public boolean isSmallintTypeNameInherited() {
      return false;
   }

   public boolean isSmallintTypeNameSet() {
      return this._isSet(42);
   }

   public void setSmallintTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SmallintTypeName;
      this._SmallintTypeName = param0;
      this._postSet(42, _oldVal, param0);
   }

   public int getDatePrecision() {
      return this._DatePrecision;
   }

   public boolean isDatePrecisionInherited() {
      return false;
   }

   public boolean isDatePrecisionSet() {
      return this._isSet(43);
   }

   public void setDatePrecision(int param0) {
      int _oldVal = this._DatePrecision;
      this._DatePrecision = param0;
      this._postSet(43, _oldVal, param0);
   }

   public boolean getSupportsAlterTableWithAddColumn() {
      return this._SupportsAlterTableWithAddColumn;
   }

   public boolean isSupportsAlterTableWithAddColumnInherited() {
      return false;
   }

   public boolean isSupportsAlterTableWithAddColumnSet() {
      return this._isSet(44);
   }

   public void setSupportsAlterTableWithAddColumn(boolean param0) {
      boolean _oldVal = this._SupportsAlterTableWithAddColumn;
      this._SupportsAlterTableWithAddColumn = param0;
      this._postSet(44, _oldVal, param0);
   }

   public String getBitTypeName() {
      return this._BitTypeName;
   }

   public boolean isBitTypeNameInherited() {
      return false;
   }

   public boolean isBitTypeNameSet() {
      return this._isSet(45);
   }

   public void setBitTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BitTypeName;
      this._BitTypeName = param0;
      this._postSet(45, _oldVal, param0);
   }

   public boolean getSupportsNullTableForGetColumns() {
      return this._SupportsNullTableForGetColumns;
   }

   public boolean isSupportsNullTableForGetColumnsInherited() {
      return false;
   }

   public boolean isSupportsNullTableForGetColumnsSet() {
      return this._isSet(46);
   }

   public void setSupportsNullTableForGetColumns(boolean param0) {
      boolean _oldVal = this._SupportsNullTableForGetColumns;
      this._SupportsNullTableForGetColumns = param0;
      this._postSet(46, _oldVal, param0);
   }

   public String getToUpperCaseFunction() {
      return this._ToUpperCaseFunction;
   }

   public boolean isToUpperCaseFunctionInherited() {
      return false;
   }

   public boolean isToUpperCaseFunctionSet() {
      return this._isSet(47);
   }

   public void setToUpperCaseFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ToUpperCaseFunction;
      this._ToUpperCaseFunction = param0;
      this._postSet(47, _oldVal, param0);
   }

   public boolean getSupportsSelectEndIndex() {
      return this._SupportsSelectEndIndex;
   }

   public boolean isSupportsSelectEndIndexInherited() {
      return false;
   }

   public boolean isSupportsSelectEndIndexSet() {
      return this._isSet(48);
   }

   public void setSupportsSelectEndIndex(boolean param0) {
      boolean _oldVal = this._SupportsSelectEndIndex;
      this._SupportsSelectEndIndex = param0;
      this._postSet(48, _oldVal, param0);
   }

   public boolean getSupportsAutoAssign() {
      return this._SupportsAutoAssign;
   }

   public boolean isSupportsAutoAssignInherited() {
      return false;
   }

   public boolean isSupportsAutoAssignSet() {
      return this._isSet(49);
   }

   public void setSupportsAutoAssign(boolean param0) {
      boolean _oldVal = this._SupportsAutoAssign;
      this._SupportsAutoAssign = param0;
      this._postSet(49, _oldVal, param0);
   }

   public boolean getStoreLargeNumbersAsStrings() {
      return this._StoreLargeNumbersAsStrings;
   }

   public boolean isStoreLargeNumbersAsStringsInherited() {
      return false;
   }

   public boolean isStoreLargeNumbersAsStringsSet() {
      return this._isSet(50);
   }

   public void setStoreLargeNumbersAsStrings(boolean param0) {
      boolean _oldVal = this._StoreLargeNumbersAsStrings;
      this._StoreLargeNumbersAsStrings = param0;
      this._postSet(50, _oldVal, param0);
   }

   public String getConstraintNameMode() {
      return this._ConstraintNameMode;
   }

   public boolean isConstraintNameModeInherited() {
      return false;
   }

   public boolean isConstraintNameModeSet() {
      return this._isSet(51);
   }

   public void setConstraintNameMode(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConstraintNameMode;
      this._ConstraintNameMode = param0;
      this._postSet(51, _oldVal, param0);
   }

   public boolean getAllowsAliasInBulkClause() {
      return this._AllowsAliasInBulkClause;
   }

   public boolean isAllowsAliasInBulkClauseInherited() {
      return false;
   }

   public boolean isAllowsAliasInBulkClauseSet() {
      return this._isSet(52);
   }

   public void setAllowsAliasInBulkClause(boolean param0) {
      boolean _oldVal = this._AllowsAliasInBulkClause;
      this._AllowsAliasInBulkClause = param0;
      this._postSet(52, _oldVal, param0);
   }

   public boolean getSupportsSelectForUpdate() {
      return this._SupportsSelectForUpdate;
   }

   public boolean isSupportsSelectForUpdateInherited() {
      return false;
   }

   public boolean isSupportsSelectForUpdateSet() {
      return this._isSet(53);
   }

   public void setSupportsSelectForUpdate(boolean param0) {
      boolean _oldVal = this._SupportsSelectForUpdate;
      this._SupportsSelectForUpdate = param0;
      this._postSet(53, _oldVal, param0);
   }

   public String getDistinctCountColumnSeparator() {
      return this._DistinctCountColumnSeparator;
   }

   public boolean isDistinctCountColumnSeparatorInherited() {
      return false;
   }

   public boolean isDistinctCountColumnSeparatorSet() {
      return this._isSet(54);
   }

   public void setDistinctCountColumnSeparator(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DistinctCountColumnSeparator;
      this._DistinctCountColumnSeparator = param0;
      this._postSet(54, _oldVal, param0);
   }

   public boolean getSupportsSubselect() {
      return this._SupportsSubselect;
   }

   public boolean isSupportsSubselectInherited() {
      return false;
   }

   public boolean isSupportsSubselectSet() {
      return this._isSet(55);
   }

   public void setSupportsSubselect(boolean param0) {
      boolean _oldVal = this._SupportsSubselect;
      this._SupportsSubselect = param0;
      this._postSet(55, _oldVal, param0);
   }

   public String getTimeTypeName() {
      return this._TimeTypeName;
   }

   public boolean isTimeTypeNameInherited() {
      return false;
   }

   public boolean isTimeTypeNameSet() {
      return this._isSet(56);
   }

   public void setTimeTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TimeTypeName;
      this._TimeTypeName = param0;
      this._postSet(56, _oldVal, param0);
   }

   public String getAutoAssignTypeName() {
      return this._AutoAssignTypeName;
   }

   public boolean isAutoAssignTypeNameInherited() {
      return false;
   }

   public boolean isAutoAssignTypeNameSet() {
      return this._isSet(57);
   }

   public void setAutoAssignTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AutoAssignTypeName;
      this._AutoAssignTypeName = param0;
      this._postSet(57, _oldVal, param0);
   }

   public boolean getUseGetObjectForBlobs() {
      return this._UseGetObjectForBlobs;
   }

   public boolean isUseGetObjectForBlobsInherited() {
      return false;
   }

   public boolean isUseGetObjectForBlobsSet() {
      return this._isSet(58);
   }

   public void setUseGetObjectForBlobs(boolean param0) {
      boolean _oldVal = this._UseGetObjectForBlobs;
      this._UseGetObjectForBlobs = param0;
      this._postSet(58, _oldVal, param0);
   }

   public int getMaxAutoAssignNameLength() {
      return this._MaxAutoAssignNameLength;
   }

   public boolean isMaxAutoAssignNameLengthInherited() {
      return false;
   }

   public boolean isMaxAutoAssignNameLengthSet() {
      return this._isSet(59);
   }

   public void setMaxAutoAssignNameLength(int param0) {
      int _oldVal = this._MaxAutoAssignNameLength;
      this._MaxAutoAssignNameLength = param0;
      this._postSet(59, _oldVal, param0);
   }

   public String getValidationSQL() {
      return this._ValidationSQL;
   }

   public boolean isValidationSQLInherited() {
      return false;
   }

   public boolean isValidationSQLSet() {
      return this._isSet(60);
   }

   public void setValidationSQL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ValidationSQL;
      this._ValidationSQL = param0;
      this._postSet(60, _oldVal, param0);
   }

   public String getStructTypeName() {
      return this._StructTypeName;
   }

   public boolean isStructTypeNameInherited() {
      return false;
   }

   public boolean isStructTypeNameSet() {
      return this._isSet(61);
   }

   public void setStructTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._StructTypeName;
      this._StructTypeName = param0;
      this._postSet(61, _oldVal, param0);
   }

   public String getVarcharTypeName() {
      return this._VarcharTypeName;
   }

   public boolean isVarcharTypeNameInherited() {
      return false;
   }

   public boolean isVarcharTypeNameSet() {
      return this._isSet(62);
   }

   public void setVarcharTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._VarcharTypeName;
      this._VarcharTypeName = param0;
      this._postSet(62, _oldVal, param0);
   }

   public int getRangePosition() {
      return this._RangePosition;
   }

   public boolean isRangePositionInherited() {
      return false;
   }

   public boolean isRangePositionSet() {
      return this._isSet(63);
   }

   public void setRangePosition(int param0) {
      int _oldVal = this._RangePosition;
      this._RangePosition = param0;
      this._postSet(63, _oldVal, param0);
   }

   public boolean getSupportsRestrictUpdateAction() {
      return this._SupportsRestrictUpdateAction;
   }

   public boolean isSupportsRestrictUpdateActionInherited() {
      return false;
   }

   public boolean isSupportsRestrictUpdateActionSet() {
      return this._isSet(64);
   }

   public void setSupportsRestrictUpdateAction(boolean param0) {
      boolean _oldVal = this._SupportsRestrictUpdateAction;
      this._SupportsRestrictUpdateAction = param0;
      this._postSet(64, _oldVal, param0);
   }

   public String getAutoAssignClause() {
      return this._AutoAssignClause;
   }

   public boolean isAutoAssignClauseInherited() {
      return false;
   }

   public boolean isAutoAssignClauseSet() {
      return this._isSet(65);
   }

   public void setAutoAssignClause(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AutoAssignClause;
      this._AutoAssignClause = param0;
      this._postSet(65, _oldVal, param0);
   }

   public boolean getSupportsMultipleNontransactionalResultSets() {
      return this._SupportsMultipleNontransactionalResultSets;
   }

   public boolean isSupportsMultipleNontransactionalResultSetsInherited() {
      return false;
   }

   public boolean isSupportsMultipleNontransactionalResultSetsSet() {
      return this._isSet(66);
   }

   public void setSupportsMultipleNontransactionalResultSets(boolean param0) {
      boolean _oldVal = this._SupportsMultipleNontransactionalResultSets;
      this._SupportsMultipleNontransactionalResultSets = param0;
      this._postSet(66, _oldVal, param0);
   }

   public String getBitLengthFunction() {
      return this._BitLengthFunction;
   }

   public boolean isBitLengthFunctionInherited() {
      return false;
   }

   public boolean isBitLengthFunctionSet() {
      return this._isSet(67);
   }

   public void setBitLengthFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BitLengthFunction;
      this._BitLengthFunction = param0;
      this._postSet(67, _oldVal, param0);
   }

   public boolean getCreatePrimaryKeys() {
      return this._CreatePrimaryKeys;
   }

   public boolean isCreatePrimaryKeysInherited() {
      return false;
   }

   public boolean isCreatePrimaryKeysSet() {
      return this._isSet(68);
   }

   public void setCreatePrimaryKeys(boolean param0) {
      boolean _oldVal = this._CreatePrimaryKeys;
      this._CreatePrimaryKeys = param0;
      this._postSet(68, _oldVal, param0);
   }

   public String getNullTypeName() {
      return this._NullTypeName;
   }

   public boolean isNullTypeNameInherited() {
      return false;
   }

   public boolean isNullTypeNameSet() {
      return this._isSet(69);
   }

   public void setNullTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NullTypeName;
      this._NullTypeName = param0;
      this._postSet(69, _oldVal, param0);
   }

   public String getFloatTypeName() {
      return this._FloatTypeName;
   }

   public boolean isFloatTypeNameInherited() {
      return false;
   }

   public boolean isFloatTypeNameSet() {
      return this._isSet(70);
   }

   public void setFloatTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FloatTypeName;
      this._FloatTypeName = param0;
      this._postSet(70, _oldVal, param0);
   }

   public boolean getUseGetBytesForBlobs() {
      return this._UseGetBytesForBlobs;
   }

   public boolean isUseGetBytesForBlobsInherited() {
      return false;
   }

   public boolean isUseGetBytesForBlobsSet() {
      return this._isSet(71);
   }

   public void setUseGetBytesForBlobs(boolean param0) {
      boolean _oldVal = this._UseGetBytesForBlobs;
      this._UseGetBytesForBlobs = param0;
      this._postSet(71, _oldVal, param0);
   }

   public String getTableTypes() {
      return this._TableTypes;
   }

   public boolean isTableTypesInherited() {
      return false;
   }

   public boolean isTableTypesSet() {
      return this._isSet(72);
   }

   public void setTableTypes(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TableTypes;
      this._TableTypes = param0;
      this._postSet(72, _oldVal, param0);
   }

   public String getNumericTypeName() {
      return this._NumericTypeName;
   }

   public boolean isNumericTypeNameInherited() {
      return false;
   }

   public boolean isNumericTypeNameSet() {
      return this._isSet(73);
   }

   public void setNumericTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NumericTypeName;
      this._NumericTypeName = param0;
      this._postSet(73, _oldVal, param0);
   }

   public String getTableForUpdateClause() {
      return this._TableForUpdateClause;
   }

   public boolean isTableForUpdateClauseInherited() {
      return false;
   }

   public boolean isTableForUpdateClauseSet() {
      return this._isSet(74);
   }

   public void setTableForUpdateClause(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TableForUpdateClause;
      this._TableForUpdateClause = param0;
      this._postSet(74, _oldVal, param0);
   }

   public String getIntegerTypeName() {
      return this._IntegerTypeName;
   }

   public boolean isIntegerTypeNameInherited() {
      return false;
   }

   public boolean isIntegerTypeNameSet() {
      return this._isSet(75);
   }

   public void setIntegerTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._IntegerTypeName;
      this._IntegerTypeName = param0;
      this._postSet(75, _oldVal, param0);
   }

   public String getBlobTypeName() {
      return this._BlobTypeName;
   }

   public boolean isBlobTypeNameInherited() {
      return false;
   }

   public boolean isBlobTypeNameSet() {
      return this._isSet(76);
   }

   public void setBlobTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BlobTypeName;
      this._BlobTypeName = param0;
      this._postSet(76, _oldVal, param0);
   }

   public String getForUpdateClause() {
      return this._ForUpdateClause;
   }

   public boolean isForUpdateClauseInherited() {
      return false;
   }

   public boolean isForUpdateClauseSet() {
      return this._isSet(77);
   }

   public void setForUpdateClause(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ForUpdateClause;
      this._ForUpdateClause = param0;
      this._postSet(77, _oldVal, param0);
   }

   public String getBooleanTypeName() {
      return this._BooleanTypeName;
   }

   public boolean isBooleanTypeNameInherited() {
      return false;
   }

   public boolean isBooleanTypeNameSet() {
      return this._isSet(78);
   }

   public void setBooleanTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BooleanTypeName;
      this._BooleanTypeName = param0;
      this._postSet(78, _oldVal, param0);
   }

   public boolean getUseGetBestRowIdentifierForPrimaryKeys() {
      return this._UseGetBestRowIdentifierForPrimaryKeys;
   }

   public boolean isUseGetBestRowIdentifierForPrimaryKeysInherited() {
      return false;
   }

   public boolean isUseGetBestRowIdentifierForPrimaryKeysSet() {
      return this._isSet(79);
   }

   public void setUseGetBestRowIdentifierForPrimaryKeys(boolean param0) {
      boolean _oldVal = this._UseGetBestRowIdentifierForPrimaryKeys;
      this._UseGetBestRowIdentifierForPrimaryKeys = param0;
      this._postSet(79, _oldVal, param0);
   }

   public boolean getSupportsForeignKeys() {
      return this._SupportsForeignKeys;
   }

   public boolean isSupportsForeignKeysInherited() {
      return false;
   }

   public boolean isSupportsForeignKeysSet() {
      return this._isSet(80);
   }

   public void setSupportsForeignKeys(boolean param0) {
      boolean _oldVal = this._SupportsForeignKeys;
      this._SupportsForeignKeys = param0;
      this._postSet(80, _oldVal, param0);
   }

   public String getDropTableSQL() {
      return this._DropTableSQL;
   }

   public boolean isDropTableSQLInherited() {
      return false;
   }

   public boolean isDropTableSQLSet() {
      return this._isSet(81);
   }

   public void setDropTableSQL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DropTableSQL;
      this._DropTableSQL = param0;
      this._postSet(81, _oldVal, param0);
   }

   public boolean getUseSetStringForClobs() {
      return this._UseSetStringForClobs;
   }

   public boolean isUseSetStringForClobsInherited() {
      return false;
   }

   public boolean isUseSetStringForClobsSet() {
      return this._isSet(82);
   }

   public void setUseSetStringForClobs(boolean param0) {
      boolean _oldVal = this._UseSetStringForClobs;
      this._UseSetStringForClobs = param0;
      this._postSet(82, _oldVal, param0);
   }

   public boolean getSupportsLockingWithOrderClause() {
      return this._SupportsLockingWithOrderClause;
   }

   public boolean isSupportsLockingWithOrderClauseInherited() {
      return false;
   }

   public boolean isSupportsLockingWithOrderClauseSet() {
      return this._isSet(83);
   }

   public void setSupportsLockingWithOrderClause(boolean param0) {
      boolean _oldVal = this._SupportsLockingWithOrderClause;
      this._SupportsLockingWithOrderClause = param0;
      this._postSet(83, _oldVal, param0);
   }

   public String getPlatform() {
      return this._Platform;
   }

   public boolean isPlatformInherited() {
      return false;
   }

   public boolean isPlatformSet() {
      return this._isSet(84);
   }

   public void setPlatform(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Platform;
      this._Platform = param0;
      this._postSet(84, _oldVal, param0);
   }

   public String getFixedSizeTypeNames() {
      return this._FixedSizeTypeNames;
   }

   public boolean isFixedSizeTypeNamesInherited() {
      return false;
   }

   public boolean isFixedSizeTypeNamesSet() {
      return this._isSet(85);
   }

   public void setFixedSizeTypeNames(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FixedSizeTypeNames;
      this._FixedSizeTypeNames = param0;
      this._postSet(85, _oldVal, param0);
   }

   public boolean getStoreCharsAsNumbers() {
      return this._StoreCharsAsNumbers;
   }

   public boolean isStoreCharsAsNumbersInherited() {
      return false;
   }

   public boolean isStoreCharsAsNumbersSet() {
      return this._isSet(86);
   }

   public void setStoreCharsAsNumbers(boolean param0) {
      boolean _oldVal = this._StoreCharsAsNumbers;
      this._StoreCharsAsNumbers = param0;
      this._postSet(86, _oldVal, param0);
   }

   public int getMaxIndexesPerTable() {
      return this._MaxIndexesPerTable;
   }

   public boolean isMaxIndexesPerTableInherited() {
      return false;
   }

   public boolean isMaxIndexesPerTableSet() {
      return this._isSet(87);
   }

   public void setMaxIndexesPerTable(int param0) {
      int _oldVal = this._MaxIndexesPerTable;
      this._MaxIndexesPerTable = param0;
      this._postSet(87, _oldVal, param0);
   }

   public boolean getRequiresCastForComparisons() {
      return this._RequiresCastForComparisons;
   }

   public boolean isRequiresCastForComparisonsInherited() {
      return false;
   }

   public boolean isRequiresCastForComparisonsSet() {
      return this._isSet(88);
   }

   public void setRequiresCastForComparisons(boolean param0) {
      boolean _oldVal = this._RequiresCastForComparisons;
      this._RequiresCastForComparisons = param0;
      this._postSet(88, _oldVal, param0);
   }

   public boolean getSupportsHaving() {
      return this._SupportsHaving;
   }

   public boolean isSupportsHavingInherited() {
      return false;
   }

   public boolean isSupportsHavingSet() {
      return this._isSet(89);
   }

   public void setSupportsHaving(boolean param0) {
      boolean _oldVal = this._SupportsHaving;
      this._SupportsHaving = param0;
      this._postSet(89, _oldVal, param0);
   }

   public boolean getSupportsLockingWithOuterJoin() {
      return this._SupportsLockingWithOuterJoin;
   }

   public boolean isSupportsLockingWithOuterJoinInherited() {
      return false;
   }

   public boolean isSupportsLockingWithOuterJoinSet() {
      return this._isSet(90);
   }

   public void setSupportsLockingWithOuterJoin(boolean param0) {
      boolean _oldVal = this._SupportsLockingWithOuterJoin;
      this._SupportsLockingWithOuterJoin = param0;
      this._postSet(90, _oldVal, param0);
   }

   public boolean getSupportsCorrelatedSubselect() {
      return this._SupportsCorrelatedSubselect;
   }

   public boolean isSupportsCorrelatedSubselectInherited() {
      return false;
   }

   public boolean isSupportsCorrelatedSubselectSet() {
      return this._isSet(91);
   }

   public void setSupportsCorrelatedSubselect(boolean param0) {
      boolean _oldVal = this._SupportsCorrelatedSubselect;
      this._SupportsCorrelatedSubselect = param0;
      this._postSet(91, _oldVal, param0);
   }

   public boolean getSupportsNullTableForGetImportedKeys() {
      return this._SupportsNullTableForGetImportedKeys;
   }

   public boolean isSupportsNullTableForGetImportedKeysInherited() {
      return false;
   }

   public boolean isSupportsNullTableForGetImportedKeysSet() {
      return this._isSet(92);
   }

   public void setSupportsNullTableForGetImportedKeys(boolean param0) {
      boolean _oldVal = this._SupportsNullTableForGetImportedKeys;
      this._SupportsNullTableForGetImportedKeys = param0;
      this._postSet(92, _oldVal, param0);
   }

   public String getBigintTypeName() {
      return this._BigintTypeName;
   }

   public boolean isBigintTypeNameInherited() {
      return false;
   }

   public boolean isBigintTypeNameSet() {
      return this._isSet(93);
   }

   public void setBigintTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BigintTypeName;
      this._BigintTypeName = param0;
      this._postSet(93, _oldVal, param0);
   }

   public String getLastGeneratedKeyQuery() {
      return this._LastGeneratedKeyQuery;
   }

   public boolean isLastGeneratedKeyQueryInherited() {
      return false;
   }

   public boolean isLastGeneratedKeyQuerySet() {
      return this._isSet(94);
   }

   public void setLastGeneratedKeyQuery(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LastGeneratedKeyQuery;
      this._LastGeneratedKeyQuery = param0;
      this._postSet(94, _oldVal, param0);
   }

   public String getReservedWords() {
      return this._ReservedWords;
   }

   public boolean isReservedWordsInherited() {
      return false;
   }

   public boolean isReservedWordsSet() {
      return this._isSet(95);
   }

   public void setReservedWords(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ReservedWords;
      this._ReservedWords = param0;
      this._postSet(95, _oldVal, param0);
   }

   public boolean getSupportsNullUpdateAction() {
      return this._SupportsNullUpdateAction;
   }

   public boolean isSupportsNullUpdateActionInherited() {
      return false;
   }

   public boolean isSupportsNullUpdateActionSet() {
      return this._isSet(96);
   }

   public void setSupportsNullUpdateAction(boolean param0) {
      boolean _oldVal = this._SupportsNullUpdateAction;
      this._SupportsNullUpdateAction = param0;
      this._postSet(96, _oldVal, param0);
   }

   public boolean getUseSchemaName() {
      return this._UseSchemaName;
   }

   public boolean isUseSchemaNameInherited() {
      return false;
   }

   public boolean isUseSchemaNameSet() {
      return this._isSet(97);
   }

   public void setUseSchemaName(boolean param0) {
      boolean _oldVal = this._UseSchemaName;
      this._UseSchemaName = param0;
      this._postSet(97, _oldVal, param0);
   }

   public boolean getSupportsDeferredConstraints() {
      return this._SupportsDeferredConstraints;
   }

   public boolean isSupportsDeferredConstraintsInherited() {
      return false;
   }

   public boolean isSupportsDeferredConstraintsSet() {
      return this._isSet(98);
   }

   public void setSupportsDeferredConstraints(boolean param0) {
      boolean _oldVal = this._SupportsDeferredConstraints;
      this._SupportsDeferredConstraints = param0;
      this._postSet(98, _oldVal, param0);
   }

   public String getRealTypeName() {
      return this._RealTypeName;
   }

   public boolean isRealTypeNameInherited() {
      return false;
   }

   public boolean isRealTypeNameSet() {
      return this._isSet(99);
   }

   public void setRealTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RealTypeName;
      this._RealTypeName = param0;
      this._postSet(99, _oldVal, param0);
   }

   public boolean getRequiresAliasForSubselect() {
      return this._RequiresAliasForSubselect;
   }

   public boolean isRequiresAliasForSubselectInherited() {
      return false;
   }

   public boolean isRequiresAliasForSubselectSet() {
      return this._isSet(100);
   }

   public void setRequiresAliasForSubselect(boolean param0) {
      boolean _oldVal = this._RequiresAliasForSubselect;
      this._RequiresAliasForSubselect = param0;
      this._postSet(100, _oldVal, param0);
   }

   public boolean getSupportsNullTableForGetIndexInfo() {
      return this._SupportsNullTableForGetIndexInfo;
   }

   public boolean isSupportsNullTableForGetIndexInfoInherited() {
      return false;
   }

   public boolean isSupportsNullTableForGetIndexInfoSet() {
      return this._isSet(101);
   }

   public void setSupportsNullTableForGetIndexInfo(boolean param0) {
      boolean _oldVal = this._SupportsNullTableForGetIndexInfo;
      this._SupportsNullTableForGetIndexInfo = param0;
      this._postSet(101, _oldVal, param0);
   }

   public String getTrimTrailingFunction() {
      return this._TrimTrailingFunction;
   }

   public boolean isTrimTrailingFunctionInherited() {
      return false;
   }

   public boolean isTrimTrailingFunctionSet() {
      return this._isSet(102);
   }

   public void setTrimTrailingFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TrimTrailingFunction;
      this._TrimTrailingFunction = param0;
      this._postSet(102, _oldVal, param0);
   }

   public boolean getSupportsLockingWithSelectRange() {
      return this._SupportsLockingWithSelectRange;
   }

   public boolean isSupportsLockingWithSelectRangeInherited() {
      return false;
   }

   public boolean isSupportsLockingWithSelectRangeSet() {
      return this._isSet(103);
   }

   public void setSupportsLockingWithSelectRange(boolean param0) {
      boolean _oldVal = this._SupportsLockingWithSelectRange;
      this._SupportsLockingWithSelectRange = param0;
      this._postSet(103, _oldVal, param0);
   }

   public boolean getStorageLimitationsFatal() {
      return this._StorageLimitationsFatal;
   }

   public boolean isStorageLimitationsFatalInherited() {
      return false;
   }

   public boolean isStorageLimitationsFatalSet() {
      return this._isSet(104);
   }

   public void setStorageLimitationsFatal(boolean param0) {
      boolean _oldVal = this._StorageLimitationsFatal;
      this._StorageLimitationsFatal = param0;
      this._postSet(104, _oldVal, param0);
   }

   public boolean getSupportsLockingWithInnerJoin() {
      return this._SupportsLockingWithInnerJoin;
   }

   public boolean isSupportsLockingWithInnerJoinInherited() {
      return false;
   }

   public boolean isSupportsLockingWithInnerJoinSet() {
      return this._isSet(105);
   }

   public void setSupportsLockingWithInnerJoin(boolean param0) {
      boolean _oldVal = this._SupportsLockingWithInnerJoin;
      this._SupportsLockingWithInnerJoin = param0;
      this._postSet(105, _oldVal, param0);
   }

   public String getCurrentTimestampFunction() {
      return this._CurrentTimestampFunction;
   }

   public boolean isCurrentTimestampFunctionInherited() {
      return false;
   }

   public boolean isCurrentTimestampFunctionSet() {
      return this._isSet(106);
   }

   public void setCurrentTimestampFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CurrentTimestampFunction;
      this._CurrentTimestampFunction = param0;
      this._postSet(106, _oldVal, param0);
   }

   public String getCastFunction() {
      return this._CastFunction;
   }

   public boolean isCastFunctionInherited() {
      return false;
   }

   public boolean isCastFunctionSet() {
      return this._isSet(107);
   }

   public void setCastFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CastFunction;
      this._CastFunction = param0;
      this._postSet(107, _oldVal, param0);
   }

   public String getOtherTypeName() {
      return this._OtherTypeName;
   }

   public boolean isOtherTypeNameInherited() {
      return false;
   }

   public boolean isOtherTypeNameSet() {
      return this._isSet(108);
   }

   public void setOtherTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OtherTypeName;
      this._OtherTypeName = param0;
      this._postSet(108, _oldVal, param0);
   }

   public int getMaxIndexNameLength() {
      return this._MaxIndexNameLength;
   }

   public boolean isMaxIndexNameLengthInherited() {
      return false;
   }

   public boolean isMaxIndexNameLengthSet() {
      return this._isSet(109);
   }

   public void setMaxIndexNameLength(int param0) {
      int _oldVal = this._MaxIndexNameLength;
      this._MaxIndexNameLength = param0;
      this._postSet(109, _oldVal, param0);
   }

   public String getDistinctTypeName() {
      return this._DistinctTypeName;
   }

   public boolean isDistinctTypeNameInherited() {
      return false;
   }

   public boolean isDistinctTypeNameSet() {
      return this._isSet(110);
   }

   public void setDistinctTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DistinctTypeName;
      this._DistinctTypeName = param0;
      this._postSet(110, _oldVal, param0);
   }

   public int getCharacterColumnSize() {
      return this._CharacterColumnSize;
   }

   public boolean isCharacterColumnSizeInherited() {
      return false;
   }

   public boolean isCharacterColumnSizeSet() {
      return this._isSet(111);
   }

   public void setCharacterColumnSize(int param0) {
      int _oldVal = this._CharacterColumnSize;
      this._CharacterColumnSize = param0;
      this._postSet(111, _oldVal, param0);
   }

   public String getVarbinaryTypeName() {
      return this._VarbinaryTypeName;
   }

   public boolean isVarbinaryTypeNameInherited() {
      return false;
   }

   public boolean isVarbinaryTypeNameSet() {
      return this._isSet(112);
   }

   public void setVarbinaryTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._VarbinaryTypeName;
      this._VarbinaryTypeName = param0;
      this._postSet(112, _oldVal, param0);
   }

   public int getMaxTableNameLength() {
      return this._MaxTableNameLength;
   }

   public boolean isMaxTableNameLengthInherited() {
      return false;
   }

   public boolean isMaxTableNameLengthSet() {
      return this._isSet(113);
   }

   public void setMaxTableNameLength(int param0) {
      int _oldVal = this._MaxTableNameLength;
      this._MaxTableNameLength = param0;
      this._postSet(113, _oldVal, param0);
   }

   public String getClosePoolSQL() {
      return this._ClosePoolSQL;
   }

   public boolean isClosePoolSQLInherited() {
      return false;
   }

   public boolean isClosePoolSQLSet() {
      return this._isSet(114);
   }

   public void setClosePoolSQL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClosePoolSQL;
      this._ClosePoolSQL = param0;
      this._postSet(114, _oldVal, param0);
   }

   public String getCurrentDateFunction() {
      return this._CurrentDateFunction;
   }

   public boolean isCurrentDateFunctionInherited() {
      return false;
   }

   public boolean isCurrentDateFunctionSet() {
      return this._isSet(115);
   }

   public void setCurrentDateFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CurrentDateFunction;
      this._CurrentDateFunction = param0;
      this._postSet(115, _oldVal, param0);
   }

   public String getJoinSyntax() {
      return this._JoinSyntax;
   }

   public boolean isJoinSyntaxInherited() {
      return false;
   }

   public boolean isJoinSyntaxSet() {
      return this._isSet(116);
   }

   public void setJoinSyntax(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"sql92", "traditional", "database"};
      param0 = LegalChecks.checkInEnum("JoinSyntax", param0, _set);
      String _oldVal = this._JoinSyntax;
      this._JoinSyntax = param0;
      this._postSet(116, _oldVal, param0);
   }

   public int getMaxEmbeddedBlobSize() {
      return this._MaxEmbeddedBlobSize;
   }

   public boolean isMaxEmbeddedBlobSizeInherited() {
      return false;
   }

   public boolean isMaxEmbeddedBlobSizeSet() {
      return this._isSet(117);
   }

   public void setMaxEmbeddedBlobSize(int param0) {
      int _oldVal = this._MaxEmbeddedBlobSize;
      this._MaxEmbeddedBlobSize = param0;
      this._postSet(117, _oldVal, param0);
   }

   public String getTrimBothFunction() {
      return this._TrimBothFunction;
   }

   public boolean isTrimBothFunctionInherited() {
      return false;
   }

   public boolean isTrimBothFunctionSet() {
      return this._isSet(118);
   }

   public void setTrimBothFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TrimBothFunction;
      this._TrimBothFunction = param0;
      this._postSet(118, _oldVal, param0);
   }

   public boolean getSupportsSelectStartIndex() {
      return this._SupportsSelectStartIndex;
   }

   public boolean isSupportsSelectStartIndexInherited() {
      return false;
   }

   public boolean isSupportsSelectStartIndexSet() {
      return this._isSet(119);
   }

   public void setSupportsSelectStartIndex(boolean param0) {
      boolean _oldVal = this._SupportsSelectStartIndex;
      this._SupportsSelectStartIndex = param0;
      this._postSet(119, _oldVal, param0);
   }

   public String getToLowerCaseFunction() {
      return this._ToLowerCaseFunction;
   }

   public boolean isToLowerCaseFunctionInherited() {
      return false;
   }

   public boolean isToLowerCaseFunctionSet() {
      return this._isSet(120);
   }

   public void setToLowerCaseFunction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ToLowerCaseFunction;
      this._ToLowerCaseFunction = param0;
      this._postSet(120, _oldVal, param0);
   }

   public String getArrayTypeName() {
      return this._ArrayTypeName;
   }

   public boolean isArrayTypeNameInherited() {
      return false;
   }

   public boolean isArrayTypeNameSet() {
      return this._isSet(121);
   }

   public void setArrayTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ArrayTypeName;
      this._ArrayTypeName = param0;
      this._postSet(121, _oldVal, param0);
   }

   public String getInnerJoinClause() {
      return this._InnerJoinClause;
   }

   public boolean isInnerJoinClauseInherited() {
      return false;
   }

   public boolean isInnerJoinClauseSet() {
      return this._isSet(122);
   }

   public void setInnerJoinClause(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InnerJoinClause;
      this._InnerJoinClause = param0;
      this._postSet(122, _oldVal, param0);
   }

   public boolean getSupportsDefaultUpdateAction() {
      return this._SupportsDefaultUpdateAction;
   }

   public boolean isSupportsDefaultUpdateActionInherited() {
      return false;
   }

   public boolean isSupportsDefaultUpdateActionSet() {
      return this._isSet(123);
   }

   public void setSupportsDefaultUpdateAction(boolean param0) {
      boolean _oldVal = this._SupportsDefaultUpdateAction;
      this._SupportsDefaultUpdateAction = param0;
      this._postSet(123, _oldVal, param0);
   }

   public boolean getSupportsSchemaForGetColumns() {
      return this._SupportsSchemaForGetColumns;
   }

   public boolean isSupportsSchemaForGetColumnsInherited() {
      return false;
   }

   public boolean isSupportsSchemaForGetColumnsSet() {
      return this._isSet(124);
   }

   public void setSupportsSchemaForGetColumns(boolean param0) {
      boolean _oldVal = this._SupportsSchemaForGetColumns;
      this._SupportsSchemaForGetColumns = param0;
      this._postSet(124, _oldVal, param0);
   }

   public String getTinyintTypeName() {
      return this._TinyintTypeName;
   }

   public boolean isTinyintTypeNameInherited() {
      return false;
   }

   public boolean isTinyintTypeNameSet() {
      return this._isSet(125);
   }

   public void setTinyintTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TinyintTypeName;
      this._TinyintTypeName = param0;
      this._postSet(125, _oldVal, param0);
   }

   public boolean getSupportsNullTableForGetPrimaryKeys() {
      return this._SupportsNullTableForGetPrimaryKeys;
   }

   public boolean isSupportsNullTableForGetPrimaryKeysInherited() {
      return false;
   }

   public boolean isSupportsNullTableForGetPrimaryKeysSet() {
      return this._isSet(126);
   }

   public void setSupportsNullTableForGetPrimaryKeys(boolean param0) {
      boolean _oldVal = this._SupportsNullTableForGetPrimaryKeys;
      this._SupportsNullTableForGetPrimaryKeys = param0;
      this._postSet(126, _oldVal, param0);
   }

   public String getSystemSchemas() {
      return this._SystemSchemas;
   }

   public boolean isSystemSchemasInherited() {
      return false;
   }

   public boolean isSystemSchemasSet() {
      return this._isSet(127);
   }

   public void setSystemSchemas(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SystemSchemas;
      this._SystemSchemas = param0;
      this._postSet(127, _oldVal, param0);
   }

   public boolean getRequiresCastForMathFunctions() {
      return this._RequiresCastForMathFunctions;
   }

   public boolean isRequiresCastForMathFunctionsInherited() {
      return false;
   }

   public boolean isRequiresCastForMathFunctionsSet() {
      return this._isSet(128);
   }

   public void setRequiresCastForMathFunctions(boolean param0) {
      boolean _oldVal = this._RequiresCastForMathFunctions;
      this._RequiresCastForMathFunctions = param0;
      this._postSet(128, _oldVal, param0);
   }

   public boolean getSupportsNullDeleteAction() {
      return this._SupportsNullDeleteAction;
   }

   public boolean isSupportsNullDeleteActionInherited() {
      return false;
   }

   public boolean isSupportsNullDeleteActionSet() {
      return this._isSet(129);
   }

   public void setSupportsNullDeleteAction(boolean param0) {
      boolean _oldVal = this._SupportsNullDeleteAction;
      this._SupportsNullDeleteAction = param0;
      this._postSet(129, _oldVal, param0);
   }

   public boolean getRequiresAutoCommitForMetaData() {
      return this._RequiresAutoCommitForMetaData;
   }

   public boolean isRequiresAutoCommitForMetaDataInherited() {
      return false;
   }

   public boolean isRequiresAutoCommitForMetaDataSet() {
      return this._isSet(130);
   }

   public void setRequiresAutoCommitForMetaData(boolean param0) {
      boolean _oldVal = this._RequiresAutoCommitForMetaData;
      this._RequiresAutoCommitForMetaData = param0;
      this._postSet(130, _oldVal, param0);
   }

   public String getTimestampTypeName() {
      return this._TimestampTypeName;
   }

   public boolean isTimestampTypeNameInherited() {
      return false;
   }

   public boolean isTimestampTypeNameSet() {
      return this._isSet(131);
   }

   public void setTimestampTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TimestampTypeName;
      this._TimestampTypeName = param0;
      this._postSet(131, _oldVal, param0);
   }

   public String getInitializationSQL() {
      return this._InitializationSQL;
   }

   public boolean isInitializationSQLInherited() {
      return false;
   }

   public boolean isInitializationSQLSet() {
      return this._isSet(132);
   }

   public void setInitializationSQL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InitializationSQL;
      this._InitializationSQL = param0;
      this._postSet(132, _oldVal, param0);
   }

   public boolean getSupportsCascadeDeleteAction() {
      return this._SupportsCascadeDeleteAction;
   }

   public boolean isSupportsCascadeDeleteActionInherited() {
      return false;
   }

   public boolean isSupportsCascadeDeleteActionSet() {
      return this._isSet(133);
   }

   public void setSupportsCascadeDeleteAction(boolean param0) {
      boolean _oldVal = this._SupportsCascadeDeleteAction;
      this._SupportsCascadeDeleteAction = param0;
      this._postSet(133, _oldVal, param0);
   }

   public boolean getSupportsTimestampNanos() {
      return this._SupportsTimestampNanos;
   }

   public boolean isSupportsTimestampNanosInherited() {
      return false;
   }

   public boolean isSupportsTimestampNanosSet() {
      return this._isSet(134);
   }

   public void setSupportsTimestampNanos(boolean param0) {
      boolean _oldVal = this._SupportsTimestampNanos;
      this._SupportsTimestampNanos = param0;
      this._postSet(134, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 52;
      }

      try {
         switch (idx) {
            case 52:
               this._AllowsAliasInBulkClause = true;
               if (initOne) {
                  break;
               }
            case 121:
               this._ArrayTypeName = "ARRAY";
               if (initOne) {
                  break;
               }
            case 65:
               this._AutoAssignClause = null;
               if (initOne) {
                  break;
               }
            case 57:
               this._AutoAssignTypeName = null;
               if (initOne) {
                  break;
               }
            case 93:
               this._BigintTypeName = "BIGINT";
               if (initOne) {
                  break;
               }
            case 3:
               this._BinaryTypeName = "BINARY";
               if (initOne) {
                  break;
               }
            case 67:
               this._BitLengthFunction = "(OCTET_LENGTH({0}) * 8)";
               if (initOne) {
                  break;
               }
            case 45:
               this._BitTypeName = "BIT";
               if (initOne) {
                  break;
               }
            case 76:
               this._BlobTypeName = "BLOB";
               if (initOne) {
                  break;
               }
            case 78:
               this._BooleanTypeName = "BOOLEAN";
               if (initOne) {
                  break;
               }
            case 107:
               this._CastFunction = "CAST({0} AS {1})";
               if (initOne) {
                  break;
               }
            case 32:
               this._CatalogSeparator = ".";
               if (initOne) {
                  break;
               }
            case 1:
               this._CharTypeName = "CHAR";
               if (initOne) {
                  break;
               }
            case 111:
               this._CharacterColumnSize = 255;
               if (initOne) {
                  break;
               }
            case 4:
               this._ClobTypeName = "CLOB";
               if (initOne) {
                  break;
               }
            case 114:
               this._ClosePoolSQL = null;
               if (initOne) {
                  break;
               }
            case 31:
               this._ConcatenateDelimiter = "'OPENJPATOKEN'";
               if (initOne) {
                  break;
               }
            case 8:
               this._ConcatenateFunction = "({0}||{1})";
               if (initOne) {
                  break;
               }
            case 51:
               this._ConstraintNameMode = "before";
               if (initOne) {
                  break;
               }
            case 68:
               this._CreatePrimaryKeys = true;
               if (initOne) {
                  break;
               }
            case 23:
               this._CrossJoinClause = "CROSS JOIN";
               if (initOne) {
                  break;
               }
            case 115:
               this._CurrentDateFunction = "CURRENT_DATE";
               if (initOne) {
                  break;
               }
            case 28:
               this._CurrentTimeFunction = "CURRENT_TIME";
               if (initOne) {
                  break;
               }
            case 106:
               this._CurrentTimestampFunction = "CURRENT_TIMESTAMP";
               if (initOne) {
                  break;
               }
            case 43:
               this._DatePrecision = 1000000;
               if (initOne) {
                  break;
               }
            case 25:
               this._DateTypeName = "DATE";
               if (initOne) {
                  break;
               }
            case 41:
               this._DecimalTypeName = "DECIMAL";
               if (initOne) {
                  break;
               }
            case 54:
               this._DistinctCountColumnSeparator = null;
               if (initOne) {
                  break;
               }
            case 110:
               this._DistinctTypeName = "DISTINCT";
               if (initOne) {
                  break;
               }
            case 39:
               this._DoubleTypeName = "DOUBLE";
               if (initOne) {
                  break;
               }
            case 36:
               this._DriverVendor = null;
               if (initOne) {
                  break;
               }
            case 81:
               this._DropTableSQL = "DROP TABLE {0}";
               if (initOne) {
                  break;
               }
            case 85:
               this._FixedSizeTypeNames = null;
               if (initOne) {
                  break;
               }
            case 70:
               this._FloatTypeName = "FLOAT";
               if (initOne) {
                  break;
               }
            case 77:
               this._ForUpdateClause = "FOR UPDATE";
               if (initOne) {
                  break;
               }
            case 132:
               this._InitializationSQL = null;
               if (initOne) {
                  break;
               }
            case 122:
               this._InnerJoinClause = "INNER JOIN";
               if (initOne) {
                  break;
               }
            case 75:
               this._IntegerTypeName = "INTEGER";
               if (initOne) {
                  break;
               }
            case 35:
               this._JavaObjectTypeName = "JAVA_OBJECT";
               if (initOne) {
                  break;
               }
            case 116:
               this._JoinSyntax = "sql92";
               if (initOne) {
                  break;
               }
            case 94:
               this._LastGeneratedKeyQuery = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._LongVarbinaryTypeName = "LONGVARBINARY";
               if (initOne) {
                  break;
               }
            case 22:
               this._LongVarcharTypeName = "LONGVARCHAR";
               if (initOne) {
                  break;
               }
            case 59:
               this._MaxAutoAssignNameLength = 31;
               if (initOne) {
                  break;
               }
            case 38:
               this._MaxColumnNameLength = 128;
               if (initOne) {
                  break;
               }
            case 12:
               this._MaxConstraintNameLength = 128;
               if (initOne) {
                  break;
               }
            case 117:
               this._MaxEmbeddedBlobSize = -1;
               if (initOne) {
                  break;
               }
            case 24:
               this._MaxEmbeddedClobSize = -1;
               if (initOne) {
                  break;
               }
            case 109:
               this._MaxIndexNameLength = 128;
               if (initOne) {
                  break;
               }
            case 87:
               this._MaxIndexesPerTable = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 113:
               this._MaxTableNameLength = 0;
               if (initOne) {
                  break;
               }
            case 21:
               this._NextSequenceQuery = null;
               if (initOne) {
                  break;
               }
            case 69:
               this._NullTypeName = "NULL";
               if (initOne) {
                  break;
               }
            case 73:
               this._NumericTypeName = "NUMERIC";
               if (initOne) {
                  break;
               }
            case 108:
               this._OtherTypeName = "OTHER";
               if (initOne) {
                  break;
               }
            case 2:
               this._OuterJoinClause = "LEFT OUTER JOIN";
               if (initOne) {
                  break;
               }
            case 84:
               this._Platform = "Generic";
               if (initOne) {
                  break;
               }
            case 63:
               this._RangePosition = 0;
               if (initOne) {
                  break;
               }
            case 99:
               this._RealTypeName = "REAL";
               if (initOne) {
                  break;
               }
            case 30:
               this._RefTypeName = "REF";
               if (initOne) {
                  break;
               }
            case 100:
               this._RequiresAliasForSubselect = false;
               if (initOne) {
                  break;
               }
            case 130:
               this._RequiresAutoCommitForMetaData = false;
               if (initOne) {
                  break;
               }
            case 88:
               this._RequiresCastForComparisons = false;
               if (initOne) {
                  break;
               }
            case 128:
               this._RequiresCastForMathFunctions = false;
               if (initOne) {
                  break;
               }
            case 29:
               this._RequiresConditionForCrossJoin = false;
               if (initOne) {
                  break;
               }
            case 95:
               this._ReservedWords = null;
               if (initOne) {
                  break;
               }
            case 34:
               this._SchemaCase = "upper";
               if (initOne) {
                  break;
               }
            case 13:
               this._SearchStringEscape = "\\";
               if (initOne) {
                  break;
               }
            case 6:
               this._SimulateLocking = false;
               if (initOne) {
                  break;
               }
            case 42:
               this._SmallintTypeName = "SMALLINT";
               if (initOne) {
                  break;
               }
            case 104:
               this._StorageLimitationsFatal = false;
               if (initOne) {
                  break;
               }
            case 86:
               this._StoreCharsAsNumbers = true;
               if (initOne) {
                  break;
               }
            case 50:
               this._StoreLargeNumbersAsStrings = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._StringLengthFunction = "CHAR_LENGTH({0})";
               if (initOne) {
                  break;
               }
            case 61:
               this._StructTypeName = "STRUCT";
               if (initOne) {
                  break;
               }
            case 9:
               this._SubstringFunctionName = "SUBSTRING";
               if (initOne) {
                  break;
               }
            case 44:
               this._SupportsAlterTableWithAddColumn = true;
               if (initOne) {
                  break;
               }
            case 27:
               this._SupportsAlterTableWithDropColumn = true;
               if (initOne) {
                  break;
               }
            case 49:
               this._SupportsAutoAssign = false;
               if (initOne) {
                  break;
               }
            case 133:
               this._SupportsCascadeDeleteAction = true;
               if (initOne) {
                  break;
               }
            case 14:
               this._SupportsCascadeUpdateAction = true;
               if (initOne) {
                  break;
               }
            case 91:
               this._SupportsCorrelatedSubselect = true;
               if (initOne) {
                  break;
               }
            case 20:
               this._SupportsDefaultDeleteAction = true;
               if (initOne) {
                  break;
               }
            case 123:
               this._SupportsDefaultUpdateAction = true;
               if (initOne) {
                  break;
               }
            case 98:
               this._SupportsDeferredConstraints = true;
               if (initOne) {
                  break;
               }
            case 80:
               this._SupportsForeignKeys = true;
               if (initOne) {
                  break;
               }
            case 89:
               this._SupportsHaving = true;
               if (initOne) {
                  break;
               }
            case 5:
               this._SupportsLockingWithDistinctClause = true;
               if (initOne) {
                  break;
               }
            case 105:
               this._SupportsLockingWithInnerJoin = true;
               if (initOne) {
                  break;
               }
            case 37:
               this._SupportsLockingWithMultipleTables = true;
               if (initOne) {
                  break;
               }
            case 83:
               this._SupportsLockingWithOrderClause = true;
               if (initOne) {
                  break;
               }
            case 90:
               this._SupportsLockingWithOuterJoin = true;
               if (initOne) {
                  break;
               }
            case 103:
               this._SupportsLockingWithSelectRange = true;
               if (initOne) {
                  break;
               }
            case 33:
               this._SupportsModOperator = false;
               if (initOne) {
                  break;
               }
            case 66:
               this._SupportsMultipleNontransactionalResultSets = true;
               if (initOne) {
                  break;
               }
            case 129:
               this._SupportsNullDeleteAction = true;
               if (initOne) {
                  break;
               }
            case 46:
               this._SupportsNullTableForGetColumns = true;
               if (initOne) {
                  break;
               }
            case 92:
               this._SupportsNullTableForGetImportedKeys = false;
               if (initOne) {
                  break;
               }
            case 101:
               this._SupportsNullTableForGetIndexInfo = false;
               if (initOne) {
                  break;
               }
            case 126:
               this._SupportsNullTableForGetPrimaryKeys = false;
               if (initOne) {
                  break;
               }
            case 96:
               this._SupportsNullUpdateAction = true;
               if (initOne) {
                  break;
               }
            case 10:
               this._SupportsQueryTimeout = true;
               if (initOne) {
                  break;
               }
            case 18:
               this._SupportsRestrictDeleteAction = true;
               if (initOne) {
                  break;
               }
            case 64:
               this._SupportsRestrictUpdateAction = true;
               if (initOne) {
                  break;
               }
            case 124:
               this._SupportsSchemaForGetColumns = true;
               if (initOne) {
                  break;
               }
            case 26:
               this._SupportsSchemaForGetTables = true;
               if (initOne) {
                  break;
               }
            case 48:
               this._SupportsSelectEndIndex = false;
               if (initOne) {
                  break;
               }
            case 53:
               this._SupportsSelectForUpdate = true;
               if (initOne) {
                  break;
               }
            case 119:
               this._SupportsSelectStartIndex = false;
               if (initOne) {
                  break;
               }
            case 55:
               this._SupportsSubselect = true;
               if (initOne) {
                  break;
               }
            case 134:
               this._SupportsTimestampNanos = true;
               if (initOne) {
                  break;
               }
            case 17:
               this._SupportsUniqueConstraints = true;
               if (initOne) {
                  break;
               }
            case 127:
               this._SystemSchemas = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._SystemTables = null;
               if (initOne) {
                  break;
               }
            case 74:
               this._TableForUpdateClause = null;
               if (initOne) {
                  break;
               }
            case 72:
               this._TableTypes = "TABLE";
               if (initOne) {
                  break;
               }
            case 56:
               this._TimeTypeName = "TIME";
               if (initOne) {
                  break;
               }
            case 131:
               this._TimestampTypeName = "TIMESTAMP";
               if (initOne) {
                  break;
               }
            case 125:
               this._TinyintTypeName = "TINYINT";
               if (initOne) {
                  break;
               }
            case 120:
               this._ToLowerCaseFunction = "LOWER({0})";
               if (initOne) {
                  break;
               }
            case 47:
               this._ToUpperCaseFunction = "UPPER({0})";
               if (initOne) {
                  break;
               }
            case 118:
               this._TrimBothFunction = "TRIM(BOTH {1} FROM {0})";
               if (initOne) {
                  break;
               }
            case 19:
               this._TrimLeadingFunction = "TRIM(LEADING {1} FROM {0})";
               if (initOne) {
                  break;
               }
            case 102:
               this._TrimTrailingFunction = "TRIM(TRAILING {1} FROM {0})";
               if (initOne) {
                  break;
               }
            case 79:
               this._UseGetBestRowIdentifierForPrimaryKeys = false;
               if (initOne) {
                  break;
               }
            case 71:
               this._UseGetBytesForBlobs = false;
               if (initOne) {
                  break;
               }
            case 58:
               this._UseGetObjectForBlobs = false;
               if (initOne) {
                  break;
               }
            case 40:
               this._UseGetStringForClobs = false;
               if (initOne) {
                  break;
               }
            case 97:
               this._UseSchemaName = true;
               if (initOne) {
                  break;
               }
            case 11:
               this._UseSetBytesForBlobs = false;
               if (initOne) {
                  break;
               }
            case 82:
               this._UseSetStringForClobs = false;
               if (initOne) {
                  break;
               }
            case 60:
               this._ValidationSQL = null;
               if (initOne) {
                  break;
               }
            case 112:
               this._VarbinaryTypeName = "VARBINARY";
               if (initOne) {
                  break;
               }
            case 62:
               this._VarcharTypeName = "VARCHAR";
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends DBDictionaryBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("platform")) {
                  return 84;
               }
            case 9:
            case 10:
            case 12:
            case 39:
            case 42:
            case 43:
            case 45:
            default:
               break;
            case 11:
               if (s.equals("join-syntax")) {
                  return 116;
               }

               if (s.equals("schema-case")) {
                  return 34;
               }

               if (s.equals("table-types")) {
                  return 72;
               }
               break;
            case 13:
               if (s.equals("bit-type-name")) {
                  return 45;
               }

               if (s.equals("cast-function")) {
                  return 107;
               }

               if (s.equals("driver-vendor")) {
                  return 36;
               }

               if (s.equals("ref-type-name")) {
                  return 30;
               }

               if (s.equals("system-tables")) {
                  return 7;
               }
               break;
            case 14:
               if (s.equals("blob-type-name")) {
                  return 76;
               }

               if (s.equals("char-type-name")) {
                  return 1;
               }

               if (s.equals("clob-type-name")) {
                  return 4;
               }

               if (s.equals("close-pool-sql")) {
                  return 114;
               }

               if (s.equals("date-precision")) {
                  return 43;
               }

               if (s.equals("date-type-name")) {
                  return 25;
               }

               if (s.equals("drop-table-sql")) {
                  return 81;
               }

               if (s.equals("null-type-name")) {
                  return 69;
               }

               if (s.equals("range-position")) {
                  return 63;
               }

               if (s.equals("real-type-name")) {
                  return 99;
               }

               if (s.equals("reserved-words")) {
                  return 95;
               }

               if (s.equals("system-schemas")) {
                  return 127;
               }

               if (s.equals("time-type-name")) {
                  return 56;
               }

               if (s.equals("validation-sql")) {
                  return 60;
               }
               break;
            case 15:
               if (s.equals("array-type-name")) {
                  return 121;
               }

               if (s.equals("float-type-name")) {
                  return 70;
               }

               if (s.equals("other-type-name")) {
                  return 108;
               }

               if (s.equals("supports-having")) {
                  return 89;
               }

               if (s.equals("use-schema-name")) {
                  return 97;
               }
               break;
            case 16:
               if (s.equals("bigint-type-name")) {
                  return 93;
               }

               if (s.equals("binary-type-name")) {
                  return 3;
               }

               if (s.equals("double-type-name")) {
                  return 39;
               }

               if (s.equals("simulate-locking")) {
                  return 6;
               }

               if (s.equals("struct-type-name")) {
                  return 61;
               }
               break;
            case 17:
               if (s.equals("boolean-type-name")) {
                  return 78;
               }

               if (s.equals("catalog-separator")) {
                  return 32;
               }

               if (s.equals("cross-join-clause")) {
                  return 23;
               }

               if (s.equals("decimal-type-name")) {
                  return 41;
               }

               if (s.equals("for-update-clause")) {
                  return 77;
               }

               if (s.equals("inner-join-clause")) {
                  return 122;
               }

               if (s.equals("integer-type-name")) {
                  return 75;
               }

               if (s.equals("numeric-type-name")) {
                  return 73;
               }

               if (s.equals("outer-join-clause")) {
                  return 2;
               }

               if (s.equals("tinyint-type-name")) {
                  return 125;
               }

               if (s.equals("varchar-type-name")) {
                  return 62;
               }
               break;
            case 18:
               if (s.equals("auto-assign-clause")) {
                  return 65;
               }

               if (s.equals("distinct-type-name")) {
                  return 110;
               }

               if (s.equals("initialization-sql")) {
                  return 132;
               }

               if (s.equals("smallint-type-name")) {
                  return 42;
               }

               if (s.equals("supports-subselect")) {
                  return 55;
               }

               if (s.equals("trim-both-function")) {
                  return 118;
               }
               break;
            case 19:
               if (s.equals("bit-length-function")) {
                  return 67;
               }

               if (s.equals("create-primary-keys")) {
                  return 68;
               }

               if (s.equals("next-sequence-query")) {
                  return 21;
               }

               if (s.equals("timestamp-type-name")) {
                  return 131;
               }

               if (s.equals("varbinary-type-name")) {
                  return 112;
               }
               break;
            case 20:
               if (s.equals("concatenate-function")) {
                  return 8;
               }

               if (s.equals("constraint-name-mode")) {
                  return 51;
               }

               if (s.equals("search-string-escape")) {
                  return 13;
               }

               if (s.equals("supports-auto-assign")) {
                  return 49;
               }
               break;
            case 21:
               if (s.equals("auto-assign-type-name")) {
                  return 57;
               }

               if (s.equals("character-column-size")) {
                  return 111;
               }

               if (s.equals("concatenate-delimiter")) {
                  return 31;
               }

               if (s.equals("current-date-function")) {
                  return 115;
               }

               if (s.equals("current-time-function")) {
                  return 28;
               }

               if (s.equals("fixed-size-type-names")) {
                  return 85;
               }

               if (s.equals("java-object-type-name")) {
                  return 35;
               }

               if (s.equals("max-index-name-length")) {
                  return 109;
               }

               if (s.equals("max-indexes-per-table")) {
                  return 87;
               }

               if (s.equals("max-table-name-length")) {
                  return 113;
               }

               if (s.equals("supports-foreign-keys")) {
                  return 80;
               }

               if (s.equals("supports-mod-operator")) {
                  return 33;
               }

               if (s.equals("trim-leading-function")) {
                  return 19;
               }
               break;
            case 22:
               if (s.equals("long-varchar-type-name")) {
                  return 22;
               }

               if (s.equals("max-column-name-length")) {
                  return 38;
               }

               if (s.equals("max-embedded-blob-size")) {
                  return 117;
               }

               if (s.equals("max-embedded-clob-size")) {
                  return 24;
               }

               if (s.equals("store-chars-as-numbers")) {
                  return 86;
               }

               if (s.equals("string-length-function")) {
                  return 15;
               }

               if (s.equals("supports-query-timeout")) {
                  return 10;
               }

               if (s.equals("to-lower-case-function")) {
                  return 120;
               }

               if (s.equals("to-upper-case-function")) {
                  return 47;
               }

               if (s.equals("trim-trailing-function")) {
                  return 102;
               }
               break;
            case 23:
               if (s.equals("substring-function-name")) {
                  return 9;
               }

               if (s.equals("table-for-update-clause")) {
                  return 74;
               }

               if (s.equals("use-get-bytes-for-blobs")) {
                  return 71;
               }

               if (s.equals("use-set-bytes-for-blobs")) {
                  return 11;
               }
               break;
            case 24:
               if (s.equals("last-generated-key-query")) {
                  return 94;
               }

               if (s.equals("long-varbinary-type-name")) {
                  return 16;
               }

               if (s.equals("supports-timestamp-nanos")) {
                  return 134;
               }

               if (s.equals("use-get-object-for-blobs")) {
                  return 58;
               }

               if (s.equals("use-get-string-for-clobs")) {
                  return 40;
               }

               if (s.equals("use-set-string-for-clobs")) {
                  return 82;
               }
               break;
            case 25:
               if (s.equals("storage-limitations-fatal")) {
                  return 104;
               }

               if (s.equals("supports-select-end-index")) {
                  return 48;
               }
               break;
            case 26:
               if (s.equals("current-timestamp-function")) {
                  return 106;
               }

               if (s.equals("max-constraint-name-length")) {
                  return 12;
               }

               if (s.equals("supports-select-for-update")) {
                  return 53;
               }
               break;
            case 27:
               if (s.equals("allows-alias-in-bulk-clause")) {
                  return 52;
               }

               if (s.equals("max-auto-assign-name-length")) {
                  return 59;
               }

               if (s.equals("supports-null-delete-action")) {
                  return 129;
               }

               if (s.equals("supports-null-update-action")) {
                  return 96;
               }

               if (s.equals("supports-select-start-index")) {
                  return 119;
               }

               if (s.equals("supports-unique-constraints")) {
                  return 17;
               }
               break;
            case 28:
               if (s.equals("requires-alias-for-subselect")) {
                  return 100;
               }
               break;
            case 29:
               if (s.equals("requires-cast-for-comparisons")) {
                  return 88;
               }

               if (s.equals("supports-correlated-subselect")) {
                  return 91;
               }

               if (s.equals("supports-deferred-constraints")) {
                  return 98;
               }
               break;
            case 30:
               if (s.equals("store-large-numbers-as-strings")) {
                  return 50;
               }

               if (s.equals("supports-cascade-delete-action")) {
                  return 133;
               }

               if (s.equals("supports-cascade-update-action")) {
                  return 14;
               }

               if (s.equals("supports-default-delete-action")) {
                  return 20;
               }

               if (s.equals("supports-default-update-action")) {
                  return 123;
               }

               if (s.equals("supports-schema-for-get-tables")) {
                  return 26;
               }
               break;
            case 31:
               if (s.equals("distinct-count-column-separator")) {
                  return 54;
               }

               if (s.equals("supports-restrict-delete-action")) {
                  return 18;
               }

               if (s.equals("supports-restrict-update-action")) {
                  return 64;
               }

               if (s.equals("supports-schema-for-get-columns")) {
                  return 124;
               }
               break;
            case 32:
               if (s.equals("requires-cast-for-math-functions")) {
                  return 128;
               }

               if (s.equals("supports-locking-with-inner-join")) {
                  return 105;
               }

               if (s.equals("supports-locking-with-outer-join")) {
                  return 90;
               }
               break;
            case 33:
               if (s.equals("requires-condition-for-cross-join")) {
                  return 29;
               }
               break;
            case 34:
               if (s.equals("requires-auto-commit-for-meta-data")) {
                  return 130;
               }

               if (s.equals("supports-locking-with-order-clause")) {
                  return 83;
               }

               if (s.equals("supports-locking-with-select-range")) {
                  return 103;
               }
               break;
            case 35:
               if (s.equals("supports-null-table-for-get-columns")) {
                  return 46;
               }
               break;
            case 36:
               if (s.equals("supports-alter-table-with-add-column")) {
                  return 44;
               }
               break;
            case 37:
               if (s.equals("supports-alter-table-with-drop-column")) {
                  return 27;
               }

               if (s.equals("supports-locking-with-distinct-clause")) {
                  return 5;
               }

               if (s.equals("supports-locking-with-multiple-tables")) {
                  return 37;
               }
               break;
            case 38:
               if (s.equals("supports-null-table-for-get-index-info")) {
                  return 101;
               }
               break;
            case 40:
               if (s.equals("supports-null-table-for-get-primary-keys")) {
                  return 126;
               }
               break;
            case 41:
               if (s.equals("supports-null-table-for-get-imported-keys")) {
                  return 92;
               }
               break;
            case 44:
               if (s.equals("use-get-best-row-identifier-for-primary-keys")) {
                  return 79;
               }
               break;
            case 46:
               if (s.equals("supports-multiple-nontransactional-result-sets")) {
                  return 66;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "char-type-name";
            case 2:
               return "outer-join-clause";
            case 3:
               return "binary-type-name";
            case 4:
               return "clob-type-name";
            case 5:
               return "supports-locking-with-distinct-clause";
            case 6:
               return "simulate-locking";
            case 7:
               return "system-tables";
            case 8:
               return "concatenate-function";
            case 9:
               return "substring-function-name";
            case 10:
               return "supports-query-timeout";
            case 11:
               return "use-set-bytes-for-blobs";
            case 12:
               return "max-constraint-name-length";
            case 13:
               return "search-string-escape";
            case 14:
               return "supports-cascade-update-action";
            case 15:
               return "string-length-function";
            case 16:
               return "long-varbinary-type-name";
            case 17:
               return "supports-unique-constraints";
            case 18:
               return "supports-restrict-delete-action";
            case 19:
               return "trim-leading-function";
            case 20:
               return "supports-default-delete-action";
            case 21:
               return "next-sequence-query";
            case 22:
               return "long-varchar-type-name";
            case 23:
               return "cross-join-clause";
            case 24:
               return "max-embedded-clob-size";
            case 25:
               return "date-type-name";
            case 26:
               return "supports-schema-for-get-tables";
            case 27:
               return "supports-alter-table-with-drop-column";
            case 28:
               return "current-time-function";
            case 29:
               return "requires-condition-for-cross-join";
            case 30:
               return "ref-type-name";
            case 31:
               return "concatenate-delimiter";
            case 32:
               return "catalog-separator";
            case 33:
               return "supports-mod-operator";
            case 34:
               return "schema-case";
            case 35:
               return "java-object-type-name";
            case 36:
               return "driver-vendor";
            case 37:
               return "supports-locking-with-multiple-tables";
            case 38:
               return "max-column-name-length";
            case 39:
               return "double-type-name";
            case 40:
               return "use-get-string-for-clobs";
            case 41:
               return "decimal-type-name";
            case 42:
               return "smallint-type-name";
            case 43:
               return "date-precision";
            case 44:
               return "supports-alter-table-with-add-column";
            case 45:
               return "bit-type-name";
            case 46:
               return "supports-null-table-for-get-columns";
            case 47:
               return "to-upper-case-function";
            case 48:
               return "supports-select-end-index";
            case 49:
               return "supports-auto-assign";
            case 50:
               return "store-large-numbers-as-strings";
            case 51:
               return "constraint-name-mode";
            case 52:
               return "allows-alias-in-bulk-clause";
            case 53:
               return "supports-select-for-update";
            case 54:
               return "distinct-count-column-separator";
            case 55:
               return "supports-subselect";
            case 56:
               return "time-type-name";
            case 57:
               return "auto-assign-type-name";
            case 58:
               return "use-get-object-for-blobs";
            case 59:
               return "max-auto-assign-name-length";
            case 60:
               return "validation-sql";
            case 61:
               return "struct-type-name";
            case 62:
               return "varchar-type-name";
            case 63:
               return "range-position";
            case 64:
               return "supports-restrict-update-action";
            case 65:
               return "auto-assign-clause";
            case 66:
               return "supports-multiple-nontransactional-result-sets";
            case 67:
               return "bit-length-function";
            case 68:
               return "create-primary-keys";
            case 69:
               return "null-type-name";
            case 70:
               return "float-type-name";
            case 71:
               return "use-get-bytes-for-blobs";
            case 72:
               return "table-types";
            case 73:
               return "numeric-type-name";
            case 74:
               return "table-for-update-clause";
            case 75:
               return "integer-type-name";
            case 76:
               return "blob-type-name";
            case 77:
               return "for-update-clause";
            case 78:
               return "boolean-type-name";
            case 79:
               return "use-get-best-row-identifier-for-primary-keys";
            case 80:
               return "supports-foreign-keys";
            case 81:
               return "drop-table-sql";
            case 82:
               return "use-set-string-for-clobs";
            case 83:
               return "supports-locking-with-order-clause";
            case 84:
               return "platform";
            case 85:
               return "fixed-size-type-names";
            case 86:
               return "store-chars-as-numbers";
            case 87:
               return "max-indexes-per-table";
            case 88:
               return "requires-cast-for-comparisons";
            case 89:
               return "supports-having";
            case 90:
               return "supports-locking-with-outer-join";
            case 91:
               return "supports-correlated-subselect";
            case 92:
               return "supports-null-table-for-get-imported-keys";
            case 93:
               return "bigint-type-name";
            case 94:
               return "last-generated-key-query";
            case 95:
               return "reserved-words";
            case 96:
               return "supports-null-update-action";
            case 97:
               return "use-schema-name";
            case 98:
               return "supports-deferred-constraints";
            case 99:
               return "real-type-name";
            case 100:
               return "requires-alias-for-subselect";
            case 101:
               return "supports-null-table-for-get-index-info";
            case 102:
               return "trim-trailing-function";
            case 103:
               return "supports-locking-with-select-range";
            case 104:
               return "storage-limitations-fatal";
            case 105:
               return "supports-locking-with-inner-join";
            case 106:
               return "current-timestamp-function";
            case 107:
               return "cast-function";
            case 108:
               return "other-type-name";
            case 109:
               return "max-index-name-length";
            case 110:
               return "distinct-type-name";
            case 111:
               return "character-column-size";
            case 112:
               return "varbinary-type-name";
            case 113:
               return "max-table-name-length";
            case 114:
               return "close-pool-sql";
            case 115:
               return "current-date-function";
            case 116:
               return "join-syntax";
            case 117:
               return "max-embedded-blob-size";
            case 118:
               return "trim-both-function";
            case 119:
               return "supports-select-start-index";
            case 120:
               return "to-lower-case-function";
            case 121:
               return "array-type-name";
            case 122:
               return "inner-join-clause";
            case 123:
               return "supports-default-update-action";
            case 124:
               return "supports-schema-for-get-columns";
            case 125:
               return "tinyint-type-name";
            case 126:
               return "supports-null-table-for-get-primary-keys";
            case 127:
               return "system-schemas";
            case 128:
               return "requires-cast-for-math-functions";
            case 129:
               return "supports-null-delete-action";
            case 130:
               return "requires-auto-commit-for-meta-data";
            case 131:
               return "timestamp-type-name";
            case 132:
               return "initialization-sql";
            case 133:
               return "supports-cascade-delete-action";
            case 134:
               return "supports-timestamp-nanos";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
               return true;
            case 33:
               return true;
            case 34:
               return true;
            case 35:
               return true;
            case 36:
               return true;
            case 37:
               return true;
            case 38:
               return true;
            case 39:
               return true;
            case 40:
               return true;
            case 41:
               return true;
            case 42:
               return true;
            case 43:
               return true;
            case 44:
               return true;
            case 45:
               return true;
            case 46:
               return true;
            case 47:
               return true;
            case 48:
               return true;
            case 49:
               return true;
            case 50:
               return true;
            case 51:
               return true;
            case 52:
               return true;
            case 53:
               return true;
            case 54:
               return true;
            case 55:
               return true;
            case 56:
               return true;
            case 57:
               return true;
            case 58:
               return true;
            case 59:
               return true;
            case 60:
               return true;
            case 61:
               return true;
            case 62:
               return true;
            case 63:
               return true;
            case 64:
               return true;
            case 65:
               return true;
            case 66:
               return true;
            case 67:
               return true;
            case 68:
               return true;
            case 69:
               return true;
            case 70:
               return true;
            case 71:
               return true;
            case 72:
               return true;
            case 73:
               return true;
            case 74:
               return true;
            case 75:
               return true;
            case 76:
               return true;
            case 77:
               return true;
            case 78:
               return true;
            case 79:
               return true;
            case 80:
               return true;
            case 81:
               return true;
            case 82:
               return true;
            case 83:
               return true;
            case 84:
               return true;
            case 85:
               return true;
            case 86:
               return true;
            case 87:
               return true;
            case 88:
               return true;
            case 89:
               return true;
            case 90:
               return true;
            case 91:
               return true;
            case 92:
               return true;
            case 93:
               return true;
            case 94:
               return true;
            case 95:
               return true;
            case 96:
               return true;
            case 97:
               return true;
            case 98:
               return true;
            case 99:
               return true;
            case 100:
               return true;
            case 101:
               return true;
            case 102:
               return true;
            case 103:
               return true;
            case 104:
               return true;
            case 105:
               return true;
            case 106:
               return true;
            case 107:
               return true;
            case 108:
               return true;
            case 109:
               return true;
            case 110:
               return true;
            case 111:
               return true;
            case 112:
               return true;
            case 113:
               return true;
            case 114:
               return true;
            case 115:
               return true;
            case 116:
               return true;
            case 117:
               return true;
            case 118:
               return true;
            case 119:
               return true;
            case 120:
               return true;
            case 121:
               return true;
            case 122:
               return true;
            case 123:
               return true;
            case 124:
               return true;
            case 125:
               return true;
            case 126:
               return true;
            case 127:
               return true;
            case 128:
               return true;
            case 129:
               return true;
            case 130:
               return true;
            case 131:
               return true;
            case 132:
               return true;
            case 133:
               return true;
            case 134:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends DBDictionaryBeanImpl.Helper {
      private BuiltInDBDictionaryBeanImpl bean;

      protected Helper(BuiltInDBDictionaryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "CharTypeName";
            case 2:
               return "OuterJoinClause";
            case 3:
               return "BinaryTypeName";
            case 4:
               return "ClobTypeName";
            case 5:
               return "SupportsLockingWithDistinctClause";
            case 6:
               return "SimulateLocking";
            case 7:
               return "SystemTables";
            case 8:
               return "ConcatenateFunction";
            case 9:
               return "SubstringFunctionName";
            case 10:
               return "SupportsQueryTimeout";
            case 11:
               return "UseSetBytesForBlobs";
            case 12:
               return "MaxConstraintNameLength";
            case 13:
               return "SearchStringEscape";
            case 14:
               return "SupportsCascadeUpdateAction";
            case 15:
               return "StringLengthFunction";
            case 16:
               return "LongVarbinaryTypeName";
            case 17:
               return "SupportsUniqueConstraints";
            case 18:
               return "SupportsRestrictDeleteAction";
            case 19:
               return "TrimLeadingFunction";
            case 20:
               return "SupportsDefaultDeleteAction";
            case 21:
               return "NextSequenceQuery";
            case 22:
               return "LongVarcharTypeName";
            case 23:
               return "CrossJoinClause";
            case 24:
               return "MaxEmbeddedClobSize";
            case 25:
               return "DateTypeName";
            case 26:
               return "SupportsSchemaForGetTables";
            case 27:
               return "SupportsAlterTableWithDropColumn";
            case 28:
               return "CurrentTimeFunction";
            case 29:
               return "RequiresConditionForCrossJoin";
            case 30:
               return "RefTypeName";
            case 31:
               return "ConcatenateDelimiter";
            case 32:
               return "CatalogSeparator";
            case 33:
               return "SupportsModOperator";
            case 34:
               return "SchemaCase";
            case 35:
               return "JavaObjectTypeName";
            case 36:
               return "DriverVendor";
            case 37:
               return "SupportsLockingWithMultipleTables";
            case 38:
               return "MaxColumnNameLength";
            case 39:
               return "DoubleTypeName";
            case 40:
               return "UseGetStringForClobs";
            case 41:
               return "DecimalTypeName";
            case 42:
               return "SmallintTypeName";
            case 43:
               return "DatePrecision";
            case 44:
               return "SupportsAlterTableWithAddColumn";
            case 45:
               return "BitTypeName";
            case 46:
               return "SupportsNullTableForGetColumns";
            case 47:
               return "ToUpperCaseFunction";
            case 48:
               return "SupportsSelectEndIndex";
            case 49:
               return "SupportsAutoAssign";
            case 50:
               return "StoreLargeNumbersAsStrings";
            case 51:
               return "ConstraintNameMode";
            case 52:
               return "AllowsAliasInBulkClause";
            case 53:
               return "SupportsSelectForUpdate";
            case 54:
               return "DistinctCountColumnSeparator";
            case 55:
               return "SupportsSubselect";
            case 56:
               return "TimeTypeName";
            case 57:
               return "AutoAssignTypeName";
            case 58:
               return "UseGetObjectForBlobs";
            case 59:
               return "MaxAutoAssignNameLength";
            case 60:
               return "ValidationSQL";
            case 61:
               return "StructTypeName";
            case 62:
               return "VarcharTypeName";
            case 63:
               return "RangePosition";
            case 64:
               return "SupportsRestrictUpdateAction";
            case 65:
               return "AutoAssignClause";
            case 66:
               return "SupportsMultipleNontransactionalResultSets";
            case 67:
               return "BitLengthFunction";
            case 68:
               return "CreatePrimaryKeys";
            case 69:
               return "NullTypeName";
            case 70:
               return "FloatTypeName";
            case 71:
               return "UseGetBytesForBlobs";
            case 72:
               return "TableTypes";
            case 73:
               return "NumericTypeName";
            case 74:
               return "TableForUpdateClause";
            case 75:
               return "IntegerTypeName";
            case 76:
               return "BlobTypeName";
            case 77:
               return "ForUpdateClause";
            case 78:
               return "BooleanTypeName";
            case 79:
               return "UseGetBestRowIdentifierForPrimaryKeys";
            case 80:
               return "SupportsForeignKeys";
            case 81:
               return "DropTableSQL";
            case 82:
               return "UseSetStringForClobs";
            case 83:
               return "SupportsLockingWithOrderClause";
            case 84:
               return "Platform";
            case 85:
               return "FixedSizeTypeNames";
            case 86:
               return "StoreCharsAsNumbers";
            case 87:
               return "MaxIndexesPerTable";
            case 88:
               return "RequiresCastForComparisons";
            case 89:
               return "SupportsHaving";
            case 90:
               return "SupportsLockingWithOuterJoin";
            case 91:
               return "SupportsCorrelatedSubselect";
            case 92:
               return "SupportsNullTableForGetImportedKeys";
            case 93:
               return "BigintTypeName";
            case 94:
               return "LastGeneratedKeyQuery";
            case 95:
               return "ReservedWords";
            case 96:
               return "SupportsNullUpdateAction";
            case 97:
               return "UseSchemaName";
            case 98:
               return "SupportsDeferredConstraints";
            case 99:
               return "RealTypeName";
            case 100:
               return "RequiresAliasForSubselect";
            case 101:
               return "SupportsNullTableForGetIndexInfo";
            case 102:
               return "TrimTrailingFunction";
            case 103:
               return "SupportsLockingWithSelectRange";
            case 104:
               return "StorageLimitationsFatal";
            case 105:
               return "SupportsLockingWithInnerJoin";
            case 106:
               return "CurrentTimestampFunction";
            case 107:
               return "CastFunction";
            case 108:
               return "OtherTypeName";
            case 109:
               return "MaxIndexNameLength";
            case 110:
               return "DistinctTypeName";
            case 111:
               return "CharacterColumnSize";
            case 112:
               return "VarbinaryTypeName";
            case 113:
               return "MaxTableNameLength";
            case 114:
               return "ClosePoolSQL";
            case 115:
               return "CurrentDateFunction";
            case 116:
               return "JoinSyntax";
            case 117:
               return "MaxEmbeddedBlobSize";
            case 118:
               return "TrimBothFunction";
            case 119:
               return "SupportsSelectStartIndex";
            case 120:
               return "ToLowerCaseFunction";
            case 121:
               return "ArrayTypeName";
            case 122:
               return "InnerJoinClause";
            case 123:
               return "SupportsDefaultUpdateAction";
            case 124:
               return "SupportsSchemaForGetColumns";
            case 125:
               return "TinyintTypeName";
            case 126:
               return "SupportsNullTableForGetPrimaryKeys";
            case 127:
               return "SystemSchemas";
            case 128:
               return "RequiresCastForMathFunctions";
            case 129:
               return "SupportsNullDeleteAction";
            case 130:
               return "RequiresAutoCommitForMetaData";
            case 131:
               return "TimestampTypeName";
            case 132:
               return "InitializationSQL";
            case 133:
               return "SupportsCascadeDeleteAction";
            case 134:
               return "SupportsTimestampNanos";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AllowsAliasInBulkClause")) {
            return 52;
         } else if (propName.equals("ArrayTypeName")) {
            return 121;
         } else if (propName.equals("AutoAssignClause")) {
            return 65;
         } else if (propName.equals("AutoAssignTypeName")) {
            return 57;
         } else if (propName.equals("BigintTypeName")) {
            return 93;
         } else if (propName.equals("BinaryTypeName")) {
            return 3;
         } else if (propName.equals("BitLengthFunction")) {
            return 67;
         } else if (propName.equals("BitTypeName")) {
            return 45;
         } else if (propName.equals("BlobTypeName")) {
            return 76;
         } else if (propName.equals("BooleanTypeName")) {
            return 78;
         } else if (propName.equals("CastFunction")) {
            return 107;
         } else if (propName.equals("CatalogSeparator")) {
            return 32;
         } else if (propName.equals("CharTypeName")) {
            return 1;
         } else if (propName.equals("CharacterColumnSize")) {
            return 111;
         } else if (propName.equals("ClobTypeName")) {
            return 4;
         } else if (propName.equals("ClosePoolSQL")) {
            return 114;
         } else if (propName.equals("ConcatenateDelimiter")) {
            return 31;
         } else if (propName.equals("ConcatenateFunction")) {
            return 8;
         } else if (propName.equals("ConstraintNameMode")) {
            return 51;
         } else if (propName.equals("CreatePrimaryKeys")) {
            return 68;
         } else if (propName.equals("CrossJoinClause")) {
            return 23;
         } else if (propName.equals("CurrentDateFunction")) {
            return 115;
         } else if (propName.equals("CurrentTimeFunction")) {
            return 28;
         } else if (propName.equals("CurrentTimestampFunction")) {
            return 106;
         } else if (propName.equals("DatePrecision")) {
            return 43;
         } else if (propName.equals("DateTypeName")) {
            return 25;
         } else if (propName.equals("DecimalTypeName")) {
            return 41;
         } else if (propName.equals("DistinctCountColumnSeparator")) {
            return 54;
         } else if (propName.equals("DistinctTypeName")) {
            return 110;
         } else if (propName.equals("DoubleTypeName")) {
            return 39;
         } else if (propName.equals("DriverVendor")) {
            return 36;
         } else if (propName.equals("DropTableSQL")) {
            return 81;
         } else if (propName.equals("FixedSizeTypeNames")) {
            return 85;
         } else if (propName.equals("FloatTypeName")) {
            return 70;
         } else if (propName.equals("ForUpdateClause")) {
            return 77;
         } else if (propName.equals("InitializationSQL")) {
            return 132;
         } else if (propName.equals("InnerJoinClause")) {
            return 122;
         } else if (propName.equals("IntegerTypeName")) {
            return 75;
         } else if (propName.equals("JavaObjectTypeName")) {
            return 35;
         } else if (propName.equals("JoinSyntax")) {
            return 116;
         } else if (propName.equals("LastGeneratedKeyQuery")) {
            return 94;
         } else if (propName.equals("LongVarbinaryTypeName")) {
            return 16;
         } else if (propName.equals("LongVarcharTypeName")) {
            return 22;
         } else if (propName.equals("MaxAutoAssignNameLength")) {
            return 59;
         } else if (propName.equals("MaxColumnNameLength")) {
            return 38;
         } else if (propName.equals("MaxConstraintNameLength")) {
            return 12;
         } else if (propName.equals("MaxEmbeddedBlobSize")) {
            return 117;
         } else if (propName.equals("MaxEmbeddedClobSize")) {
            return 24;
         } else if (propName.equals("MaxIndexNameLength")) {
            return 109;
         } else if (propName.equals("MaxIndexesPerTable")) {
            return 87;
         } else if (propName.equals("MaxTableNameLength")) {
            return 113;
         } else if (propName.equals("NextSequenceQuery")) {
            return 21;
         } else if (propName.equals("NullTypeName")) {
            return 69;
         } else if (propName.equals("NumericTypeName")) {
            return 73;
         } else if (propName.equals("OtherTypeName")) {
            return 108;
         } else if (propName.equals("OuterJoinClause")) {
            return 2;
         } else if (propName.equals("Platform")) {
            return 84;
         } else if (propName.equals("RangePosition")) {
            return 63;
         } else if (propName.equals("RealTypeName")) {
            return 99;
         } else if (propName.equals("RefTypeName")) {
            return 30;
         } else if (propName.equals("RequiresAliasForSubselect")) {
            return 100;
         } else if (propName.equals("RequiresAutoCommitForMetaData")) {
            return 130;
         } else if (propName.equals("RequiresCastForComparisons")) {
            return 88;
         } else if (propName.equals("RequiresCastForMathFunctions")) {
            return 128;
         } else if (propName.equals("RequiresConditionForCrossJoin")) {
            return 29;
         } else if (propName.equals("ReservedWords")) {
            return 95;
         } else if (propName.equals("SchemaCase")) {
            return 34;
         } else if (propName.equals("SearchStringEscape")) {
            return 13;
         } else if (propName.equals("SimulateLocking")) {
            return 6;
         } else if (propName.equals("SmallintTypeName")) {
            return 42;
         } else if (propName.equals("StorageLimitationsFatal")) {
            return 104;
         } else if (propName.equals("StoreCharsAsNumbers")) {
            return 86;
         } else if (propName.equals("StoreLargeNumbersAsStrings")) {
            return 50;
         } else if (propName.equals("StringLengthFunction")) {
            return 15;
         } else if (propName.equals("StructTypeName")) {
            return 61;
         } else if (propName.equals("SubstringFunctionName")) {
            return 9;
         } else if (propName.equals("SupportsAlterTableWithAddColumn")) {
            return 44;
         } else if (propName.equals("SupportsAlterTableWithDropColumn")) {
            return 27;
         } else if (propName.equals("SupportsAutoAssign")) {
            return 49;
         } else if (propName.equals("SupportsCascadeDeleteAction")) {
            return 133;
         } else if (propName.equals("SupportsCascadeUpdateAction")) {
            return 14;
         } else if (propName.equals("SupportsCorrelatedSubselect")) {
            return 91;
         } else if (propName.equals("SupportsDefaultDeleteAction")) {
            return 20;
         } else if (propName.equals("SupportsDefaultUpdateAction")) {
            return 123;
         } else if (propName.equals("SupportsDeferredConstraints")) {
            return 98;
         } else if (propName.equals("SupportsForeignKeys")) {
            return 80;
         } else if (propName.equals("SupportsHaving")) {
            return 89;
         } else if (propName.equals("SupportsLockingWithDistinctClause")) {
            return 5;
         } else if (propName.equals("SupportsLockingWithInnerJoin")) {
            return 105;
         } else if (propName.equals("SupportsLockingWithMultipleTables")) {
            return 37;
         } else if (propName.equals("SupportsLockingWithOrderClause")) {
            return 83;
         } else if (propName.equals("SupportsLockingWithOuterJoin")) {
            return 90;
         } else if (propName.equals("SupportsLockingWithSelectRange")) {
            return 103;
         } else if (propName.equals("SupportsModOperator")) {
            return 33;
         } else if (propName.equals("SupportsMultipleNontransactionalResultSets")) {
            return 66;
         } else if (propName.equals("SupportsNullDeleteAction")) {
            return 129;
         } else if (propName.equals("SupportsNullTableForGetColumns")) {
            return 46;
         } else if (propName.equals("SupportsNullTableForGetImportedKeys")) {
            return 92;
         } else if (propName.equals("SupportsNullTableForGetIndexInfo")) {
            return 101;
         } else if (propName.equals("SupportsNullTableForGetPrimaryKeys")) {
            return 126;
         } else if (propName.equals("SupportsNullUpdateAction")) {
            return 96;
         } else if (propName.equals("SupportsQueryTimeout")) {
            return 10;
         } else if (propName.equals("SupportsRestrictDeleteAction")) {
            return 18;
         } else if (propName.equals("SupportsRestrictUpdateAction")) {
            return 64;
         } else if (propName.equals("SupportsSchemaForGetColumns")) {
            return 124;
         } else if (propName.equals("SupportsSchemaForGetTables")) {
            return 26;
         } else if (propName.equals("SupportsSelectEndIndex")) {
            return 48;
         } else if (propName.equals("SupportsSelectForUpdate")) {
            return 53;
         } else if (propName.equals("SupportsSelectStartIndex")) {
            return 119;
         } else if (propName.equals("SupportsSubselect")) {
            return 55;
         } else if (propName.equals("SupportsTimestampNanos")) {
            return 134;
         } else if (propName.equals("SupportsUniqueConstraints")) {
            return 17;
         } else if (propName.equals("SystemSchemas")) {
            return 127;
         } else if (propName.equals("SystemTables")) {
            return 7;
         } else if (propName.equals("TableForUpdateClause")) {
            return 74;
         } else if (propName.equals("TableTypes")) {
            return 72;
         } else if (propName.equals("TimeTypeName")) {
            return 56;
         } else if (propName.equals("TimestampTypeName")) {
            return 131;
         } else if (propName.equals("TinyintTypeName")) {
            return 125;
         } else if (propName.equals("ToLowerCaseFunction")) {
            return 120;
         } else if (propName.equals("ToUpperCaseFunction")) {
            return 47;
         } else if (propName.equals("TrimBothFunction")) {
            return 118;
         } else if (propName.equals("TrimLeadingFunction")) {
            return 19;
         } else if (propName.equals("TrimTrailingFunction")) {
            return 102;
         } else if (propName.equals("UseGetBestRowIdentifierForPrimaryKeys")) {
            return 79;
         } else if (propName.equals("UseGetBytesForBlobs")) {
            return 71;
         } else if (propName.equals("UseGetObjectForBlobs")) {
            return 58;
         } else if (propName.equals("UseGetStringForClobs")) {
            return 40;
         } else if (propName.equals("UseSchemaName")) {
            return 97;
         } else if (propName.equals("UseSetBytesForBlobs")) {
            return 11;
         } else if (propName.equals("UseSetStringForClobs")) {
            return 82;
         } else if (propName.equals("ValidationSQL")) {
            return 60;
         } else if (propName.equals("VarbinaryTypeName")) {
            return 112;
         } else {
            return propName.equals("VarcharTypeName") ? 62 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isAllowsAliasInBulkClauseSet()) {
               buf.append("AllowsAliasInBulkClause");
               buf.append(String.valueOf(this.bean.getAllowsAliasInBulkClause()));
            }

            if (this.bean.isArrayTypeNameSet()) {
               buf.append("ArrayTypeName");
               buf.append(String.valueOf(this.bean.getArrayTypeName()));
            }

            if (this.bean.isAutoAssignClauseSet()) {
               buf.append("AutoAssignClause");
               buf.append(String.valueOf(this.bean.getAutoAssignClause()));
            }

            if (this.bean.isAutoAssignTypeNameSet()) {
               buf.append("AutoAssignTypeName");
               buf.append(String.valueOf(this.bean.getAutoAssignTypeName()));
            }

            if (this.bean.isBigintTypeNameSet()) {
               buf.append("BigintTypeName");
               buf.append(String.valueOf(this.bean.getBigintTypeName()));
            }

            if (this.bean.isBinaryTypeNameSet()) {
               buf.append("BinaryTypeName");
               buf.append(String.valueOf(this.bean.getBinaryTypeName()));
            }

            if (this.bean.isBitLengthFunctionSet()) {
               buf.append("BitLengthFunction");
               buf.append(String.valueOf(this.bean.getBitLengthFunction()));
            }

            if (this.bean.isBitTypeNameSet()) {
               buf.append("BitTypeName");
               buf.append(String.valueOf(this.bean.getBitTypeName()));
            }

            if (this.bean.isBlobTypeNameSet()) {
               buf.append("BlobTypeName");
               buf.append(String.valueOf(this.bean.getBlobTypeName()));
            }

            if (this.bean.isBooleanTypeNameSet()) {
               buf.append("BooleanTypeName");
               buf.append(String.valueOf(this.bean.getBooleanTypeName()));
            }

            if (this.bean.isCastFunctionSet()) {
               buf.append("CastFunction");
               buf.append(String.valueOf(this.bean.getCastFunction()));
            }

            if (this.bean.isCatalogSeparatorSet()) {
               buf.append("CatalogSeparator");
               buf.append(String.valueOf(this.bean.getCatalogSeparator()));
            }

            if (this.bean.isCharTypeNameSet()) {
               buf.append("CharTypeName");
               buf.append(String.valueOf(this.bean.getCharTypeName()));
            }

            if (this.bean.isCharacterColumnSizeSet()) {
               buf.append("CharacterColumnSize");
               buf.append(String.valueOf(this.bean.getCharacterColumnSize()));
            }

            if (this.bean.isClobTypeNameSet()) {
               buf.append("ClobTypeName");
               buf.append(String.valueOf(this.bean.getClobTypeName()));
            }

            if (this.bean.isClosePoolSQLSet()) {
               buf.append("ClosePoolSQL");
               buf.append(String.valueOf(this.bean.getClosePoolSQL()));
            }

            if (this.bean.isConcatenateDelimiterSet()) {
               buf.append("ConcatenateDelimiter");
               buf.append(String.valueOf(this.bean.getConcatenateDelimiter()));
            }

            if (this.bean.isConcatenateFunctionSet()) {
               buf.append("ConcatenateFunction");
               buf.append(String.valueOf(this.bean.getConcatenateFunction()));
            }

            if (this.bean.isConstraintNameModeSet()) {
               buf.append("ConstraintNameMode");
               buf.append(String.valueOf(this.bean.getConstraintNameMode()));
            }

            if (this.bean.isCreatePrimaryKeysSet()) {
               buf.append("CreatePrimaryKeys");
               buf.append(String.valueOf(this.bean.getCreatePrimaryKeys()));
            }

            if (this.bean.isCrossJoinClauseSet()) {
               buf.append("CrossJoinClause");
               buf.append(String.valueOf(this.bean.getCrossJoinClause()));
            }

            if (this.bean.isCurrentDateFunctionSet()) {
               buf.append("CurrentDateFunction");
               buf.append(String.valueOf(this.bean.getCurrentDateFunction()));
            }

            if (this.bean.isCurrentTimeFunctionSet()) {
               buf.append("CurrentTimeFunction");
               buf.append(String.valueOf(this.bean.getCurrentTimeFunction()));
            }

            if (this.bean.isCurrentTimestampFunctionSet()) {
               buf.append("CurrentTimestampFunction");
               buf.append(String.valueOf(this.bean.getCurrentTimestampFunction()));
            }

            if (this.bean.isDatePrecisionSet()) {
               buf.append("DatePrecision");
               buf.append(String.valueOf(this.bean.getDatePrecision()));
            }

            if (this.bean.isDateTypeNameSet()) {
               buf.append("DateTypeName");
               buf.append(String.valueOf(this.bean.getDateTypeName()));
            }

            if (this.bean.isDecimalTypeNameSet()) {
               buf.append("DecimalTypeName");
               buf.append(String.valueOf(this.bean.getDecimalTypeName()));
            }

            if (this.bean.isDistinctCountColumnSeparatorSet()) {
               buf.append("DistinctCountColumnSeparator");
               buf.append(String.valueOf(this.bean.getDistinctCountColumnSeparator()));
            }

            if (this.bean.isDistinctTypeNameSet()) {
               buf.append("DistinctTypeName");
               buf.append(String.valueOf(this.bean.getDistinctTypeName()));
            }

            if (this.bean.isDoubleTypeNameSet()) {
               buf.append("DoubleTypeName");
               buf.append(String.valueOf(this.bean.getDoubleTypeName()));
            }

            if (this.bean.isDriverVendorSet()) {
               buf.append("DriverVendor");
               buf.append(String.valueOf(this.bean.getDriverVendor()));
            }

            if (this.bean.isDropTableSQLSet()) {
               buf.append("DropTableSQL");
               buf.append(String.valueOf(this.bean.getDropTableSQL()));
            }

            if (this.bean.isFixedSizeTypeNamesSet()) {
               buf.append("FixedSizeTypeNames");
               buf.append(String.valueOf(this.bean.getFixedSizeTypeNames()));
            }

            if (this.bean.isFloatTypeNameSet()) {
               buf.append("FloatTypeName");
               buf.append(String.valueOf(this.bean.getFloatTypeName()));
            }

            if (this.bean.isForUpdateClauseSet()) {
               buf.append("ForUpdateClause");
               buf.append(String.valueOf(this.bean.getForUpdateClause()));
            }

            if (this.bean.isInitializationSQLSet()) {
               buf.append("InitializationSQL");
               buf.append(String.valueOf(this.bean.getInitializationSQL()));
            }

            if (this.bean.isInnerJoinClauseSet()) {
               buf.append("InnerJoinClause");
               buf.append(String.valueOf(this.bean.getInnerJoinClause()));
            }

            if (this.bean.isIntegerTypeNameSet()) {
               buf.append("IntegerTypeName");
               buf.append(String.valueOf(this.bean.getIntegerTypeName()));
            }

            if (this.bean.isJavaObjectTypeNameSet()) {
               buf.append("JavaObjectTypeName");
               buf.append(String.valueOf(this.bean.getJavaObjectTypeName()));
            }

            if (this.bean.isJoinSyntaxSet()) {
               buf.append("JoinSyntax");
               buf.append(String.valueOf(this.bean.getJoinSyntax()));
            }

            if (this.bean.isLastGeneratedKeyQuerySet()) {
               buf.append("LastGeneratedKeyQuery");
               buf.append(String.valueOf(this.bean.getLastGeneratedKeyQuery()));
            }

            if (this.bean.isLongVarbinaryTypeNameSet()) {
               buf.append("LongVarbinaryTypeName");
               buf.append(String.valueOf(this.bean.getLongVarbinaryTypeName()));
            }

            if (this.bean.isLongVarcharTypeNameSet()) {
               buf.append("LongVarcharTypeName");
               buf.append(String.valueOf(this.bean.getLongVarcharTypeName()));
            }

            if (this.bean.isMaxAutoAssignNameLengthSet()) {
               buf.append("MaxAutoAssignNameLength");
               buf.append(String.valueOf(this.bean.getMaxAutoAssignNameLength()));
            }

            if (this.bean.isMaxColumnNameLengthSet()) {
               buf.append("MaxColumnNameLength");
               buf.append(String.valueOf(this.bean.getMaxColumnNameLength()));
            }

            if (this.bean.isMaxConstraintNameLengthSet()) {
               buf.append("MaxConstraintNameLength");
               buf.append(String.valueOf(this.bean.getMaxConstraintNameLength()));
            }

            if (this.bean.isMaxEmbeddedBlobSizeSet()) {
               buf.append("MaxEmbeddedBlobSize");
               buf.append(String.valueOf(this.bean.getMaxEmbeddedBlobSize()));
            }

            if (this.bean.isMaxEmbeddedClobSizeSet()) {
               buf.append("MaxEmbeddedClobSize");
               buf.append(String.valueOf(this.bean.getMaxEmbeddedClobSize()));
            }

            if (this.bean.isMaxIndexNameLengthSet()) {
               buf.append("MaxIndexNameLength");
               buf.append(String.valueOf(this.bean.getMaxIndexNameLength()));
            }

            if (this.bean.isMaxIndexesPerTableSet()) {
               buf.append("MaxIndexesPerTable");
               buf.append(String.valueOf(this.bean.getMaxIndexesPerTable()));
            }

            if (this.bean.isMaxTableNameLengthSet()) {
               buf.append("MaxTableNameLength");
               buf.append(String.valueOf(this.bean.getMaxTableNameLength()));
            }

            if (this.bean.isNextSequenceQuerySet()) {
               buf.append("NextSequenceQuery");
               buf.append(String.valueOf(this.bean.getNextSequenceQuery()));
            }

            if (this.bean.isNullTypeNameSet()) {
               buf.append("NullTypeName");
               buf.append(String.valueOf(this.bean.getNullTypeName()));
            }

            if (this.bean.isNumericTypeNameSet()) {
               buf.append("NumericTypeName");
               buf.append(String.valueOf(this.bean.getNumericTypeName()));
            }

            if (this.bean.isOtherTypeNameSet()) {
               buf.append("OtherTypeName");
               buf.append(String.valueOf(this.bean.getOtherTypeName()));
            }

            if (this.bean.isOuterJoinClauseSet()) {
               buf.append("OuterJoinClause");
               buf.append(String.valueOf(this.bean.getOuterJoinClause()));
            }

            if (this.bean.isPlatformSet()) {
               buf.append("Platform");
               buf.append(String.valueOf(this.bean.getPlatform()));
            }

            if (this.bean.isRangePositionSet()) {
               buf.append("RangePosition");
               buf.append(String.valueOf(this.bean.getRangePosition()));
            }

            if (this.bean.isRealTypeNameSet()) {
               buf.append("RealTypeName");
               buf.append(String.valueOf(this.bean.getRealTypeName()));
            }

            if (this.bean.isRefTypeNameSet()) {
               buf.append("RefTypeName");
               buf.append(String.valueOf(this.bean.getRefTypeName()));
            }

            if (this.bean.isRequiresAliasForSubselectSet()) {
               buf.append("RequiresAliasForSubselect");
               buf.append(String.valueOf(this.bean.getRequiresAliasForSubselect()));
            }

            if (this.bean.isRequiresAutoCommitForMetaDataSet()) {
               buf.append("RequiresAutoCommitForMetaData");
               buf.append(String.valueOf(this.bean.getRequiresAutoCommitForMetaData()));
            }

            if (this.bean.isRequiresCastForComparisonsSet()) {
               buf.append("RequiresCastForComparisons");
               buf.append(String.valueOf(this.bean.getRequiresCastForComparisons()));
            }

            if (this.bean.isRequiresCastForMathFunctionsSet()) {
               buf.append("RequiresCastForMathFunctions");
               buf.append(String.valueOf(this.bean.getRequiresCastForMathFunctions()));
            }

            if (this.bean.isRequiresConditionForCrossJoinSet()) {
               buf.append("RequiresConditionForCrossJoin");
               buf.append(String.valueOf(this.bean.getRequiresConditionForCrossJoin()));
            }

            if (this.bean.isReservedWordsSet()) {
               buf.append("ReservedWords");
               buf.append(String.valueOf(this.bean.getReservedWords()));
            }

            if (this.bean.isSchemaCaseSet()) {
               buf.append("SchemaCase");
               buf.append(String.valueOf(this.bean.getSchemaCase()));
            }

            if (this.bean.isSearchStringEscapeSet()) {
               buf.append("SearchStringEscape");
               buf.append(String.valueOf(this.bean.getSearchStringEscape()));
            }

            if (this.bean.isSimulateLockingSet()) {
               buf.append("SimulateLocking");
               buf.append(String.valueOf(this.bean.getSimulateLocking()));
            }

            if (this.bean.isSmallintTypeNameSet()) {
               buf.append("SmallintTypeName");
               buf.append(String.valueOf(this.bean.getSmallintTypeName()));
            }

            if (this.bean.isStorageLimitationsFatalSet()) {
               buf.append("StorageLimitationsFatal");
               buf.append(String.valueOf(this.bean.getStorageLimitationsFatal()));
            }

            if (this.bean.isStoreCharsAsNumbersSet()) {
               buf.append("StoreCharsAsNumbers");
               buf.append(String.valueOf(this.bean.getStoreCharsAsNumbers()));
            }

            if (this.bean.isStoreLargeNumbersAsStringsSet()) {
               buf.append("StoreLargeNumbersAsStrings");
               buf.append(String.valueOf(this.bean.getStoreLargeNumbersAsStrings()));
            }

            if (this.bean.isStringLengthFunctionSet()) {
               buf.append("StringLengthFunction");
               buf.append(String.valueOf(this.bean.getStringLengthFunction()));
            }

            if (this.bean.isStructTypeNameSet()) {
               buf.append("StructTypeName");
               buf.append(String.valueOf(this.bean.getStructTypeName()));
            }

            if (this.bean.isSubstringFunctionNameSet()) {
               buf.append("SubstringFunctionName");
               buf.append(String.valueOf(this.bean.getSubstringFunctionName()));
            }

            if (this.bean.isSupportsAlterTableWithAddColumnSet()) {
               buf.append("SupportsAlterTableWithAddColumn");
               buf.append(String.valueOf(this.bean.getSupportsAlterTableWithAddColumn()));
            }

            if (this.bean.isSupportsAlterTableWithDropColumnSet()) {
               buf.append("SupportsAlterTableWithDropColumn");
               buf.append(String.valueOf(this.bean.getSupportsAlterTableWithDropColumn()));
            }

            if (this.bean.isSupportsAutoAssignSet()) {
               buf.append("SupportsAutoAssign");
               buf.append(String.valueOf(this.bean.getSupportsAutoAssign()));
            }

            if (this.bean.isSupportsCascadeDeleteActionSet()) {
               buf.append("SupportsCascadeDeleteAction");
               buf.append(String.valueOf(this.bean.getSupportsCascadeDeleteAction()));
            }

            if (this.bean.isSupportsCascadeUpdateActionSet()) {
               buf.append("SupportsCascadeUpdateAction");
               buf.append(String.valueOf(this.bean.getSupportsCascadeUpdateAction()));
            }

            if (this.bean.isSupportsCorrelatedSubselectSet()) {
               buf.append("SupportsCorrelatedSubselect");
               buf.append(String.valueOf(this.bean.getSupportsCorrelatedSubselect()));
            }

            if (this.bean.isSupportsDefaultDeleteActionSet()) {
               buf.append("SupportsDefaultDeleteAction");
               buf.append(String.valueOf(this.bean.getSupportsDefaultDeleteAction()));
            }

            if (this.bean.isSupportsDefaultUpdateActionSet()) {
               buf.append("SupportsDefaultUpdateAction");
               buf.append(String.valueOf(this.bean.getSupportsDefaultUpdateAction()));
            }

            if (this.bean.isSupportsDeferredConstraintsSet()) {
               buf.append("SupportsDeferredConstraints");
               buf.append(String.valueOf(this.bean.getSupportsDeferredConstraints()));
            }

            if (this.bean.isSupportsForeignKeysSet()) {
               buf.append("SupportsForeignKeys");
               buf.append(String.valueOf(this.bean.getSupportsForeignKeys()));
            }

            if (this.bean.isSupportsHavingSet()) {
               buf.append("SupportsHaving");
               buf.append(String.valueOf(this.bean.getSupportsHaving()));
            }

            if (this.bean.isSupportsLockingWithDistinctClauseSet()) {
               buf.append("SupportsLockingWithDistinctClause");
               buf.append(String.valueOf(this.bean.getSupportsLockingWithDistinctClause()));
            }

            if (this.bean.isSupportsLockingWithInnerJoinSet()) {
               buf.append("SupportsLockingWithInnerJoin");
               buf.append(String.valueOf(this.bean.getSupportsLockingWithInnerJoin()));
            }

            if (this.bean.isSupportsLockingWithMultipleTablesSet()) {
               buf.append("SupportsLockingWithMultipleTables");
               buf.append(String.valueOf(this.bean.getSupportsLockingWithMultipleTables()));
            }

            if (this.bean.isSupportsLockingWithOrderClauseSet()) {
               buf.append("SupportsLockingWithOrderClause");
               buf.append(String.valueOf(this.bean.getSupportsLockingWithOrderClause()));
            }

            if (this.bean.isSupportsLockingWithOuterJoinSet()) {
               buf.append("SupportsLockingWithOuterJoin");
               buf.append(String.valueOf(this.bean.getSupportsLockingWithOuterJoin()));
            }

            if (this.bean.isSupportsLockingWithSelectRangeSet()) {
               buf.append("SupportsLockingWithSelectRange");
               buf.append(String.valueOf(this.bean.getSupportsLockingWithSelectRange()));
            }

            if (this.bean.isSupportsModOperatorSet()) {
               buf.append("SupportsModOperator");
               buf.append(String.valueOf(this.bean.getSupportsModOperator()));
            }

            if (this.bean.isSupportsMultipleNontransactionalResultSetsSet()) {
               buf.append("SupportsMultipleNontransactionalResultSets");
               buf.append(String.valueOf(this.bean.getSupportsMultipleNontransactionalResultSets()));
            }

            if (this.bean.isSupportsNullDeleteActionSet()) {
               buf.append("SupportsNullDeleteAction");
               buf.append(String.valueOf(this.bean.getSupportsNullDeleteAction()));
            }

            if (this.bean.isSupportsNullTableForGetColumnsSet()) {
               buf.append("SupportsNullTableForGetColumns");
               buf.append(String.valueOf(this.bean.getSupportsNullTableForGetColumns()));
            }

            if (this.bean.isSupportsNullTableForGetImportedKeysSet()) {
               buf.append("SupportsNullTableForGetImportedKeys");
               buf.append(String.valueOf(this.bean.getSupportsNullTableForGetImportedKeys()));
            }

            if (this.bean.isSupportsNullTableForGetIndexInfoSet()) {
               buf.append("SupportsNullTableForGetIndexInfo");
               buf.append(String.valueOf(this.bean.getSupportsNullTableForGetIndexInfo()));
            }

            if (this.bean.isSupportsNullTableForGetPrimaryKeysSet()) {
               buf.append("SupportsNullTableForGetPrimaryKeys");
               buf.append(String.valueOf(this.bean.getSupportsNullTableForGetPrimaryKeys()));
            }

            if (this.bean.isSupportsNullUpdateActionSet()) {
               buf.append("SupportsNullUpdateAction");
               buf.append(String.valueOf(this.bean.getSupportsNullUpdateAction()));
            }

            if (this.bean.isSupportsQueryTimeoutSet()) {
               buf.append("SupportsQueryTimeout");
               buf.append(String.valueOf(this.bean.getSupportsQueryTimeout()));
            }

            if (this.bean.isSupportsRestrictDeleteActionSet()) {
               buf.append("SupportsRestrictDeleteAction");
               buf.append(String.valueOf(this.bean.getSupportsRestrictDeleteAction()));
            }

            if (this.bean.isSupportsRestrictUpdateActionSet()) {
               buf.append("SupportsRestrictUpdateAction");
               buf.append(String.valueOf(this.bean.getSupportsRestrictUpdateAction()));
            }

            if (this.bean.isSupportsSchemaForGetColumnsSet()) {
               buf.append("SupportsSchemaForGetColumns");
               buf.append(String.valueOf(this.bean.getSupportsSchemaForGetColumns()));
            }

            if (this.bean.isSupportsSchemaForGetTablesSet()) {
               buf.append("SupportsSchemaForGetTables");
               buf.append(String.valueOf(this.bean.getSupportsSchemaForGetTables()));
            }

            if (this.bean.isSupportsSelectEndIndexSet()) {
               buf.append("SupportsSelectEndIndex");
               buf.append(String.valueOf(this.bean.getSupportsSelectEndIndex()));
            }

            if (this.bean.isSupportsSelectForUpdateSet()) {
               buf.append("SupportsSelectForUpdate");
               buf.append(String.valueOf(this.bean.getSupportsSelectForUpdate()));
            }

            if (this.bean.isSupportsSelectStartIndexSet()) {
               buf.append("SupportsSelectStartIndex");
               buf.append(String.valueOf(this.bean.getSupportsSelectStartIndex()));
            }

            if (this.bean.isSupportsSubselectSet()) {
               buf.append("SupportsSubselect");
               buf.append(String.valueOf(this.bean.getSupportsSubselect()));
            }

            if (this.bean.isSupportsTimestampNanosSet()) {
               buf.append("SupportsTimestampNanos");
               buf.append(String.valueOf(this.bean.getSupportsTimestampNanos()));
            }

            if (this.bean.isSupportsUniqueConstraintsSet()) {
               buf.append("SupportsUniqueConstraints");
               buf.append(String.valueOf(this.bean.getSupportsUniqueConstraints()));
            }

            if (this.bean.isSystemSchemasSet()) {
               buf.append("SystemSchemas");
               buf.append(String.valueOf(this.bean.getSystemSchemas()));
            }

            if (this.bean.isSystemTablesSet()) {
               buf.append("SystemTables");
               buf.append(String.valueOf(this.bean.getSystemTables()));
            }

            if (this.bean.isTableForUpdateClauseSet()) {
               buf.append("TableForUpdateClause");
               buf.append(String.valueOf(this.bean.getTableForUpdateClause()));
            }

            if (this.bean.isTableTypesSet()) {
               buf.append("TableTypes");
               buf.append(String.valueOf(this.bean.getTableTypes()));
            }

            if (this.bean.isTimeTypeNameSet()) {
               buf.append("TimeTypeName");
               buf.append(String.valueOf(this.bean.getTimeTypeName()));
            }

            if (this.bean.isTimestampTypeNameSet()) {
               buf.append("TimestampTypeName");
               buf.append(String.valueOf(this.bean.getTimestampTypeName()));
            }

            if (this.bean.isTinyintTypeNameSet()) {
               buf.append("TinyintTypeName");
               buf.append(String.valueOf(this.bean.getTinyintTypeName()));
            }

            if (this.bean.isToLowerCaseFunctionSet()) {
               buf.append("ToLowerCaseFunction");
               buf.append(String.valueOf(this.bean.getToLowerCaseFunction()));
            }

            if (this.bean.isToUpperCaseFunctionSet()) {
               buf.append("ToUpperCaseFunction");
               buf.append(String.valueOf(this.bean.getToUpperCaseFunction()));
            }

            if (this.bean.isTrimBothFunctionSet()) {
               buf.append("TrimBothFunction");
               buf.append(String.valueOf(this.bean.getTrimBothFunction()));
            }

            if (this.bean.isTrimLeadingFunctionSet()) {
               buf.append("TrimLeadingFunction");
               buf.append(String.valueOf(this.bean.getTrimLeadingFunction()));
            }

            if (this.bean.isTrimTrailingFunctionSet()) {
               buf.append("TrimTrailingFunction");
               buf.append(String.valueOf(this.bean.getTrimTrailingFunction()));
            }

            if (this.bean.isUseGetBestRowIdentifierForPrimaryKeysSet()) {
               buf.append("UseGetBestRowIdentifierForPrimaryKeys");
               buf.append(String.valueOf(this.bean.getUseGetBestRowIdentifierForPrimaryKeys()));
            }

            if (this.bean.isUseGetBytesForBlobsSet()) {
               buf.append("UseGetBytesForBlobs");
               buf.append(String.valueOf(this.bean.getUseGetBytesForBlobs()));
            }

            if (this.bean.isUseGetObjectForBlobsSet()) {
               buf.append("UseGetObjectForBlobs");
               buf.append(String.valueOf(this.bean.getUseGetObjectForBlobs()));
            }

            if (this.bean.isUseGetStringForClobsSet()) {
               buf.append("UseGetStringForClobs");
               buf.append(String.valueOf(this.bean.getUseGetStringForClobs()));
            }

            if (this.bean.isUseSchemaNameSet()) {
               buf.append("UseSchemaName");
               buf.append(String.valueOf(this.bean.getUseSchemaName()));
            }

            if (this.bean.isUseSetBytesForBlobsSet()) {
               buf.append("UseSetBytesForBlobs");
               buf.append(String.valueOf(this.bean.getUseSetBytesForBlobs()));
            }

            if (this.bean.isUseSetStringForClobsSet()) {
               buf.append("UseSetStringForClobs");
               buf.append(String.valueOf(this.bean.getUseSetStringForClobs()));
            }

            if (this.bean.isValidationSQLSet()) {
               buf.append("ValidationSQL");
               buf.append(String.valueOf(this.bean.getValidationSQL()));
            }

            if (this.bean.isVarbinaryTypeNameSet()) {
               buf.append("VarbinaryTypeName");
               buf.append(String.valueOf(this.bean.getVarbinaryTypeName()));
            }

            if (this.bean.isVarcharTypeNameSet()) {
               buf.append("VarcharTypeName");
               buf.append(String.valueOf(this.bean.getVarcharTypeName()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            BuiltInDBDictionaryBeanImpl otherTyped = (BuiltInDBDictionaryBeanImpl)other;
            this.computeDiff("AllowsAliasInBulkClause", this.bean.getAllowsAliasInBulkClause(), otherTyped.getAllowsAliasInBulkClause(), false);
            this.computeDiff("ArrayTypeName", this.bean.getArrayTypeName(), otherTyped.getArrayTypeName(), false);
            this.computeDiff("AutoAssignClause", this.bean.getAutoAssignClause(), otherTyped.getAutoAssignClause(), false);
            this.computeDiff("AutoAssignTypeName", this.bean.getAutoAssignTypeName(), otherTyped.getAutoAssignTypeName(), false);
            this.computeDiff("BigintTypeName", this.bean.getBigintTypeName(), otherTyped.getBigintTypeName(), false);
            this.computeDiff("BinaryTypeName", this.bean.getBinaryTypeName(), otherTyped.getBinaryTypeName(), false);
            this.computeDiff("BitLengthFunction", this.bean.getBitLengthFunction(), otherTyped.getBitLengthFunction(), false);
            this.computeDiff("BitTypeName", this.bean.getBitTypeName(), otherTyped.getBitTypeName(), false);
            this.computeDiff("BlobTypeName", this.bean.getBlobTypeName(), otherTyped.getBlobTypeName(), false);
            this.computeDiff("BooleanTypeName", this.bean.getBooleanTypeName(), otherTyped.getBooleanTypeName(), false);
            this.computeDiff("CastFunction", this.bean.getCastFunction(), otherTyped.getCastFunction(), false);
            this.computeDiff("CatalogSeparator", this.bean.getCatalogSeparator(), otherTyped.getCatalogSeparator(), false);
            this.computeDiff("CharTypeName", this.bean.getCharTypeName(), otherTyped.getCharTypeName(), false);
            this.computeDiff("CharacterColumnSize", this.bean.getCharacterColumnSize(), otherTyped.getCharacterColumnSize(), false);
            this.computeDiff("ClobTypeName", this.bean.getClobTypeName(), otherTyped.getClobTypeName(), false);
            this.computeDiff("ClosePoolSQL", this.bean.getClosePoolSQL(), otherTyped.getClosePoolSQL(), false);
            this.computeDiff("ConcatenateDelimiter", this.bean.getConcatenateDelimiter(), otherTyped.getConcatenateDelimiter(), false);
            this.computeDiff("ConcatenateFunction", this.bean.getConcatenateFunction(), otherTyped.getConcatenateFunction(), false);
            this.computeDiff("ConstraintNameMode", this.bean.getConstraintNameMode(), otherTyped.getConstraintNameMode(), false);
            this.computeDiff("CreatePrimaryKeys", this.bean.getCreatePrimaryKeys(), otherTyped.getCreatePrimaryKeys(), false);
            this.computeDiff("CrossJoinClause", this.bean.getCrossJoinClause(), otherTyped.getCrossJoinClause(), false);
            this.computeDiff("CurrentDateFunction", this.bean.getCurrentDateFunction(), otherTyped.getCurrentDateFunction(), false);
            this.computeDiff("CurrentTimeFunction", this.bean.getCurrentTimeFunction(), otherTyped.getCurrentTimeFunction(), false);
            this.computeDiff("CurrentTimestampFunction", this.bean.getCurrentTimestampFunction(), otherTyped.getCurrentTimestampFunction(), false);
            this.computeDiff("DatePrecision", this.bean.getDatePrecision(), otherTyped.getDatePrecision(), false);
            this.computeDiff("DateTypeName", this.bean.getDateTypeName(), otherTyped.getDateTypeName(), false);
            this.computeDiff("DecimalTypeName", this.bean.getDecimalTypeName(), otherTyped.getDecimalTypeName(), false);
            this.computeDiff("DistinctCountColumnSeparator", this.bean.getDistinctCountColumnSeparator(), otherTyped.getDistinctCountColumnSeparator(), false);
            this.computeDiff("DistinctTypeName", this.bean.getDistinctTypeName(), otherTyped.getDistinctTypeName(), false);
            this.computeDiff("DoubleTypeName", this.bean.getDoubleTypeName(), otherTyped.getDoubleTypeName(), false);
            this.computeDiff("DriverVendor", this.bean.getDriverVendor(), otherTyped.getDriverVendor(), false);
            this.computeDiff("DropTableSQL", this.bean.getDropTableSQL(), otherTyped.getDropTableSQL(), false);
            this.computeDiff("FixedSizeTypeNames", this.bean.getFixedSizeTypeNames(), otherTyped.getFixedSizeTypeNames(), false);
            this.computeDiff("FloatTypeName", this.bean.getFloatTypeName(), otherTyped.getFloatTypeName(), false);
            this.computeDiff("ForUpdateClause", this.bean.getForUpdateClause(), otherTyped.getForUpdateClause(), false);
            this.computeDiff("InitializationSQL", this.bean.getInitializationSQL(), otherTyped.getInitializationSQL(), false);
            this.computeDiff("InnerJoinClause", this.bean.getInnerJoinClause(), otherTyped.getInnerJoinClause(), false);
            this.computeDiff("IntegerTypeName", this.bean.getIntegerTypeName(), otherTyped.getIntegerTypeName(), false);
            this.computeDiff("JavaObjectTypeName", this.bean.getJavaObjectTypeName(), otherTyped.getJavaObjectTypeName(), false);
            this.computeDiff("JoinSyntax", this.bean.getJoinSyntax(), otherTyped.getJoinSyntax(), false);
            this.computeDiff("LastGeneratedKeyQuery", this.bean.getLastGeneratedKeyQuery(), otherTyped.getLastGeneratedKeyQuery(), false);
            this.computeDiff("LongVarbinaryTypeName", this.bean.getLongVarbinaryTypeName(), otherTyped.getLongVarbinaryTypeName(), false);
            this.computeDiff("LongVarcharTypeName", this.bean.getLongVarcharTypeName(), otherTyped.getLongVarcharTypeName(), false);
            this.computeDiff("MaxAutoAssignNameLength", this.bean.getMaxAutoAssignNameLength(), otherTyped.getMaxAutoAssignNameLength(), false);
            this.computeDiff("MaxColumnNameLength", this.bean.getMaxColumnNameLength(), otherTyped.getMaxColumnNameLength(), false);
            this.computeDiff("MaxConstraintNameLength", this.bean.getMaxConstraintNameLength(), otherTyped.getMaxConstraintNameLength(), false);
            this.computeDiff("MaxEmbeddedBlobSize", this.bean.getMaxEmbeddedBlobSize(), otherTyped.getMaxEmbeddedBlobSize(), false);
            this.computeDiff("MaxEmbeddedClobSize", this.bean.getMaxEmbeddedClobSize(), otherTyped.getMaxEmbeddedClobSize(), false);
            this.computeDiff("MaxIndexNameLength", this.bean.getMaxIndexNameLength(), otherTyped.getMaxIndexNameLength(), false);
            this.computeDiff("MaxIndexesPerTable", this.bean.getMaxIndexesPerTable(), otherTyped.getMaxIndexesPerTable(), false);
            this.computeDiff("MaxTableNameLength", this.bean.getMaxTableNameLength(), otherTyped.getMaxTableNameLength(), false);
            this.computeDiff("NextSequenceQuery", this.bean.getNextSequenceQuery(), otherTyped.getNextSequenceQuery(), false);
            this.computeDiff("NullTypeName", this.bean.getNullTypeName(), otherTyped.getNullTypeName(), false);
            this.computeDiff("NumericTypeName", this.bean.getNumericTypeName(), otherTyped.getNumericTypeName(), false);
            this.computeDiff("OtherTypeName", this.bean.getOtherTypeName(), otherTyped.getOtherTypeName(), false);
            this.computeDiff("OuterJoinClause", this.bean.getOuterJoinClause(), otherTyped.getOuterJoinClause(), false);
            this.computeDiff("Platform", this.bean.getPlatform(), otherTyped.getPlatform(), false);
            this.computeDiff("RangePosition", this.bean.getRangePosition(), otherTyped.getRangePosition(), false);
            this.computeDiff("RealTypeName", this.bean.getRealTypeName(), otherTyped.getRealTypeName(), false);
            this.computeDiff("RefTypeName", this.bean.getRefTypeName(), otherTyped.getRefTypeName(), false);
            this.computeDiff("RequiresAliasForSubselect", this.bean.getRequiresAliasForSubselect(), otherTyped.getRequiresAliasForSubselect(), false);
            this.computeDiff("RequiresAutoCommitForMetaData", this.bean.getRequiresAutoCommitForMetaData(), otherTyped.getRequiresAutoCommitForMetaData(), false);
            this.computeDiff("RequiresCastForComparisons", this.bean.getRequiresCastForComparisons(), otherTyped.getRequiresCastForComparisons(), false);
            this.computeDiff("RequiresCastForMathFunctions", this.bean.getRequiresCastForMathFunctions(), otherTyped.getRequiresCastForMathFunctions(), false);
            this.computeDiff("RequiresConditionForCrossJoin", this.bean.getRequiresConditionForCrossJoin(), otherTyped.getRequiresConditionForCrossJoin(), false);
            this.computeDiff("ReservedWords", this.bean.getReservedWords(), otherTyped.getReservedWords(), false);
            this.computeDiff("SchemaCase", this.bean.getSchemaCase(), otherTyped.getSchemaCase(), false);
            this.computeDiff("SearchStringEscape", this.bean.getSearchStringEscape(), otherTyped.getSearchStringEscape(), false);
            this.computeDiff("SimulateLocking", this.bean.getSimulateLocking(), otherTyped.getSimulateLocking(), false);
            this.computeDiff("SmallintTypeName", this.bean.getSmallintTypeName(), otherTyped.getSmallintTypeName(), false);
            this.computeDiff("StorageLimitationsFatal", this.bean.getStorageLimitationsFatal(), otherTyped.getStorageLimitationsFatal(), false);
            this.computeDiff("StoreCharsAsNumbers", this.bean.getStoreCharsAsNumbers(), otherTyped.getStoreCharsAsNumbers(), false);
            this.computeDiff("StoreLargeNumbersAsStrings", this.bean.getStoreLargeNumbersAsStrings(), otherTyped.getStoreLargeNumbersAsStrings(), false);
            this.computeDiff("StringLengthFunction", this.bean.getStringLengthFunction(), otherTyped.getStringLengthFunction(), false);
            this.computeDiff("StructTypeName", this.bean.getStructTypeName(), otherTyped.getStructTypeName(), false);
            this.computeDiff("SubstringFunctionName", this.bean.getSubstringFunctionName(), otherTyped.getSubstringFunctionName(), false);
            this.computeDiff("SupportsAlterTableWithAddColumn", this.bean.getSupportsAlterTableWithAddColumn(), otherTyped.getSupportsAlterTableWithAddColumn(), false);
            this.computeDiff("SupportsAlterTableWithDropColumn", this.bean.getSupportsAlterTableWithDropColumn(), otherTyped.getSupportsAlterTableWithDropColumn(), false);
            this.computeDiff("SupportsAutoAssign", this.bean.getSupportsAutoAssign(), otherTyped.getSupportsAutoAssign(), false);
            this.computeDiff("SupportsCascadeDeleteAction", this.bean.getSupportsCascadeDeleteAction(), otherTyped.getSupportsCascadeDeleteAction(), false);
            this.computeDiff("SupportsCascadeUpdateAction", this.bean.getSupportsCascadeUpdateAction(), otherTyped.getSupportsCascadeUpdateAction(), false);
            this.computeDiff("SupportsCorrelatedSubselect", this.bean.getSupportsCorrelatedSubselect(), otherTyped.getSupportsCorrelatedSubselect(), false);
            this.computeDiff("SupportsDefaultDeleteAction", this.bean.getSupportsDefaultDeleteAction(), otherTyped.getSupportsDefaultDeleteAction(), false);
            this.computeDiff("SupportsDefaultUpdateAction", this.bean.getSupportsDefaultUpdateAction(), otherTyped.getSupportsDefaultUpdateAction(), false);
            this.computeDiff("SupportsDeferredConstraints", this.bean.getSupportsDeferredConstraints(), otherTyped.getSupportsDeferredConstraints(), false);
            this.computeDiff("SupportsForeignKeys", this.bean.getSupportsForeignKeys(), otherTyped.getSupportsForeignKeys(), false);
            this.computeDiff("SupportsHaving", this.bean.getSupportsHaving(), otherTyped.getSupportsHaving(), false);
            this.computeDiff("SupportsLockingWithDistinctClause", this.bean.getSupportsLockingWithDistinctClause(), otherTyped.getSupportsLockingWithDistinctClause(), false);
            this.computeDiff("SupportsLockingWithInnerJoin", this.bean.getSupportsLockingWithInnerJoin(), otherTyped.getSupportsLockingWithInnerJoin(), false);
            this.computeDiff("SupportsLockingWithMultipleTables", this.bean.getSupportsLockingWithMultipleTables(), otherTyped.getSupportsLockingWithMultipleTables(), false);
            this.computeDiff("SupportsLockingWithOrderClause", this.bean.getSupportsLockingWithOrderClause(), otherTyped.getSupportsLockingWithOrderClause(), false);
            this.computeDiff("SupportsLockingWithOuterJoin", this.bean.getSupportsLockingWithOuterJoin(), otherTyped.getSupportsLockingWithOuterJoin(), false);
            this.computeDiff("SupportsLockingWithSelectRange", this.bean.getSupportsLockingWithSelectRange(), otherTyped.getSupportsLockingWithSelectRange(), false);
            this.computeDiff("SupportsModOperator", this.bean.getSupportsModOperator(), otherTyped.getSupportsModOperator(), false);
            this.computeDiff("SupportsMultipleNontransactionalResultSets", this.bean.getSupportsMultipleNontransactionalResultSets(), otherTyped.getSupportsMultipleNontransactionalResultSets(), false);
            this.computeDiff("SupportsNullDeleteAction", this.bean.getSupportsNullDeleteAction(), otherTyped.getSupportsNullDeleteAction(), false);
            this.computeDiff("SupportsNullTableForGetColumns", this.bean.getSupportsNullTableForGetColumns(), otherTyped.getSupportsNullTableForGetColumns(), false);
            this.computeDiff("SupportsNullTableForGetImportedKeys", this.bean.getSupportsNullTableForGetImportedKeys(), otherTyped.getSupportsNullTableForGetImportedKeys(), false);
            this.computeDiff("SupportsNullTableForGetIndexInfo", this.bean.getSupportsNullTableForGetIndexInfo(), otherTyped.getSupportsNullTableForGetIndexInfo(), false);
            this.computeDiff("SupportsNullTableForGetPrimaryKeys", this.bean.getSupportsNullTableForGetPrimaryKeys(), otherTyped.getSupportsNullTableForGetPrimaryKeys(), false);
            this.computeDiff("SupportsNullUpdateAction", this.bean.getSupportsNullUpdateAction(), otherTyped.getSupportsNullUpdateAction(), false);
            this.computeDiff("SupportsQueryTimeout", this.bean.getSupportsQueryTimeout(), otherTyped.getSupportsQueryTimeout(), false);
            this.computeDiff("SupportsRestrictDeleteAction", this.bean.getSupportsRestrictDeleteAction(), otherTyped.getSupportsRestrictDeleteAction(), false);
            this.computeDiff("SupportsRestrictUpdateAction", this.bean.getSupportsRestrictUpdateAction(), otherTyped.getSupportsRestrictUpdateAction(), false);
            this.computeDiff("SupportsSchemaForGetColumns", this.bean.getSupportsSchemaForGetColumns(), otherTyped.getSupportsSchemaForGetColumns(), false);
            this.computeDiff("SupportsSchemaForGetTables", this.bean.getSupportsSchemaForGetTables(), otherTyped.getSupportsSchemaForGetTables(), false);
            this.computeDiff("SupportsSelectEndIndex", this.bean.getSupportsSelectEndIndex(), otherTyped.getSupportsSelectEndIndex(), false);
            this.computeDiff("SupportsSelectForUpdate", this.bean.getSupportsSelectForUpdate(), otherTyped.getSupportsSelectForUpdate(), false);
            this.computeDiff("SupportsSelectStartIndex", this.bean.getSupportsSelectStartIndex(), otherTyped.getSupportsSelectStartIndex(), false);
            this.computeDiff("SupportsSubselect", this.bean.getSupportsSubselect(), otherTyped.getSupportsSubselect(), false);
            this.computeDiff("SupportsTimestampNanos", this.bean.getSupportsTimestampNanos(), otherTyped.getSupportsTimestampNanos(), false);
            this.computeDiff("SupportsUniqueConstraints", this.bean.getSupportsUniqueConstraints(), otherTyped.getSupportsUniqueConstraints(), false);
            this.computeDiff("SystemSchemas", this.bean.getSystemSchemas(), otherTyped.getSystemSchemas(), false);
            this.computeDiff("SystemTables", this.bean.getSystemTables(), otherTyped.getSystemTables(), false);
            this.computeDiff("TableForUpdateClause", this.bean.getTableForUpdateClause(), otherTyped.getTableForUpdateClause(), false);
            this.computeDiff("TableTypes", this.bean.getTableTypes(), otherTyped.getTableTypes(), false);
            this.computeDiff("TimeTypeName", this.bean.getTimeTypeName(), otherTyped.getTimeTypeName(), false);
            this.computeDiff("TimestampTypeName", this.bean.getTimestampTypeName(), otherTyped.getTimestampTypeName(), false);
            this.computeDiff("TinyintTypeName", this.bean.getTinyintTypeName(), otherTyped.getTinyintTypeName(), false);
            this.computeDiff("ToLowerCaseFunction", this.bean.getToLowerCaseFunction(), otherTyped.getToLowerCaseFunction(), false);
            this.computeDiff("ToUpperCaseFunction", this.bean.getToUpperCaseFunction(), otherTyped.getToUpperCaseFunction(), false);
            this.computeDiff("TrimBothFunction", this.bean.getTrimBothFunction(), otherTyped.getTrimBothFunction(), false);
            this.computeDiff("TrimLeadingFunction", this.bean.getTrimLeadingFunction(), otherTyped.getTrimLeadingFunction(), false);
            this.computeDiff("TrimTrailingFunction", this.bean.getTrimTrailingFunction(), otherTyped.getTrimTrailingFunction(), false);
            this.computeDiff("UseGetBestRowIdentifierForPrimaryKeys", this.bean.getUseGetBestRowIdentifierForPrimaryKeys(), otherTyped.getUseGetBestRowIdentifierForPrimaryKeys(), false);
            this.computeDiff("UseGetBytesForBlobs", this.bean.getUseGetBytesForBlobs(), otherTyped.getUseGetBytesForBlobs(), false);
            this.computeDiff("UseGetObjectForBlobs", this.bean.getUseGetObjectForBlobs(), otherTyped.getUseGetObjectForBlobs(), false);
            this.computeDiff("UseGetStringForClobs", this.bean.getUseGetStringForClobs(), otherTyped.getUseGetStringForClobs(), false);
            this.computeDiff("UseSchemaName", this.bean.getUseSchemaName(), otherTyped.getUseSchemaName(), false);
            this.computeDiff("UseSetBytesForBlobs", this.bean.getUseSetBytesForBlobs(), otherTyped.getUseSetBytesForBlobs(), false);
            this.computeDiff("UseSetStringForClobs", this.bean.getUseSetStringForClobs(), otherTyped.getUseSetStringForClobs(), false);
            this.computeDiff("ValidationSQL", this.bean.getValidationSQL(), otherTyped.getValidationSQL(), false);
            this.computeDiff("VarbinaryTypeName", this.bean.getVarbinaryTypeName(), otherTyped.getVarbinaryTypeName(), false);
            this.computeDiff("VarcharTypeName", this.bean.getVarcharTypeName(), otherTyped.getVarcharTypeName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            BuiltInDBDictionaryBeanImpl original = (BuiltInDBDictionaryBeanImpl)event.getSourceBean();
            BuiltInDBDictionaryBeanImpl proposed = (BuiltInDBDictionaryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AllowsAliasInBulkClause")) {
                  original.setAllowsAliasInBulkClause(proposed.getAllowsAliasInBulkClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 52);
               } else if (prop.equals("ArrayTypeName")) {
                  original.setArrayTypeName(proposed.getArrayTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 121);
               } else if (prop.equals("AutoAssignClause")) {
                  original.setAutoAssignClause(proposed.getAutoAssignClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 65);
               } else if (prop.equals("AutoAssignTypeName")) {
                  original.setAutoAssignTypeName(proposed.getAutoAssignTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 57);
               } else if (prop.equals("BigintTypeName")) {
                  original.setBigintTypeName(proposed.getBigintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 93);
               } else if (prop.equals("BinaryTypeName")) {
                  original.setBinaryTypeName(proposed.getBinaryTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("BitLengthFunction")) {
                  original.setBitLengthFunction(proposed.getBitLengthFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 67);
               } else if (prop.equals("BitTypeName")) {
                  original.setBitTypeName(proposed.getBitTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (prop.equals("BlobTypeName")) {
                  original.setBlobTypeName(proposed.getBlobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 76);
               } else if (prop.equals("BooleanTypeName")) {
                  original.setBooleanTypeName(proposed.getBooleanTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 78);
               } else if (prop.equals("CastFunction")) {
                  original.setCastFunction(proposed.getCastFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 107);
               } else if (prop.equals("CatalogSeparator")) {
                  original.setCatalogSeparator(proposed.getCatalogSeparator());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("CharTypeName")) {
                  original.setCharTypeName(proposed.getCharTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("CharacterColumnSize")) {
                  original.setCharacterColumnSize(proposed.getCharacterColumnSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 111);
               } else if (prop.equals("ClobTypeName")) {
                  original.setClobTypeName(proposed.getClobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ClosePoolSQL")) {
                  original.setClosePoolSQL(proposed.getClosePoolSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 114);
               } else if (prop.equals("ConcatenateDelimiter")) {
                  original.setConcatenateDelimiter(proposed.getConcatenateDelimiter());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("ConcatenateFunction")) {
                  original.setConcatenateFunction(proposed.getConcatenateFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("ConstraintNameMode")) {
                  original.setConstraintNameMode(proposed.getConstraintNameMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 51);
               } else if (prop.equals("CreatePrimaryKeys")) {
                  original.setCreatePrimaryKeys(proposed.getCreatePrimaryKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 68);
               } else if (prop.equals("CrossJoinClause")) {
                  original.setCrossJoinClause(proposed.getCrossJoinClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("CurrentDateFunction")) {
                  original.setCurrentDateFunction(proposed.getCurrentDateFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 115);
               } else if (prop.equals("CurrentTimeFunction")) {
                  original.setCurrentTimeFunction(proposed.getCurrentTimeFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("CurrentTimestampFunction")) {
                  original.setCurrentTimestampFunction(proposed.getCurrentTimestampFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 106);
               } else if (prop.equals("DatePrecision")) {
                  original.setDatePrecision(proposed.getDatePrecision());
                  original._conditionalUnset(update.isUnsetUpdate(), 43);
               } else if (prop.equals("DateTypeName")) {
                  original.setDateTypeName(proposed.getDateTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("DecimalTypeName")) {
                  original.setDecimalTypeName(proposed.getDecimalTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 41);
               } else if (prop.equals("DistinctCountColumnSeparator")) {
                  original.setDistinctCountColumnSeparator(proposed.getDistinctCountColumnSeparator());
                  original._conditionalUnset(update.isUnsetUpdate(), 54);
               } else if (prop.equals("DistinctTypeName")) {
                  original.setDistinctTypeName(proposed.getDistinctTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 110);
               } else if (prop.equals("DoubleTypeName")) {
                  original.setDoubleTypeName(proposed.getDoubleTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("DriverVendor")) {
                  original.setDriverVendor(proposed.getDriverVendor());
                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("DropTableSQL")) {
                  original.setDropTableSQL(proposed.getDropTableSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 81);
               } else if (prop.equals("FixedSizeTypeNames")) {
                  original.setFixedSizeTypeNames(proposed.getFixedSizeTypeNames());
                  original._conditionalUnset(update.isUnsetUpdate(), 85);
               } else if (prop.equals("FloatTypeName")) {
                  original.setFloatTypeName(proposed.getFloatTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 70);
               } else if (prop.equals("ForUpdateClause")) {
                  original.setForUpdateClause(proposed.getForUpdateClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 77);
               } else if (prop.equals("InitializationSQL")) {
                  original.setInitializationSQL(proposed.getInitializationSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 132);
               } else if (prop.equals("InnerJoinClause")) {
                  original.setInnerJoinClause(proposed.getInnerJoinClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 122);
               } else if (prop.equals("IntegerTypeName")) {
                  original.setIntegerTypeName(proposed.getIntegerTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 75);
               } else if (prop.equals("JavaObjectTypeName")) {
                  original.setJavaObjectTypeName(proposed.getJavaObjectTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("JoinSyntax")) {
                  original.setJoinSyntax(proposed.getJoinSyntax());
                  original._conditionalUnset(update.isUnsetUpdate(), 116);
               } else if (prop.equals("LastGeneratedKeyQuery")) {
                  original.setLastGeneratedKeyQuery(proposed.getLastGeneratedKeyQuery());
                  original._conditionalUnset(update.isUnsetUpdate(), 94);
               } else if (prop.equals("LongVarbinaryTypeName")) {
                  original.setLongVarbinaryTypeName(proposed.getLongVarbinaryTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("LongVarcharTypeName")) {
                  original.setLongVarcharTypeName(proposed.getLongVarcharTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("MaxAutoAssignNameLength")) {
                  original.setMaxAutoAssignNameLength(proposed.getMaxAutoAssignNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 59);
               } else if (prop.equals("MaxColumnNameLength")) {
                  original.setMaxColumnNameLength(proposed.getMaxColumnNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 38);
               } else if (prop.equals("MaxConstraintNameLength")) {
                  original.setMaxConstraintNameLength(proposed.getMaxConstraintNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("MaxEmbeddedBlobSize")) {
                  original.setMaxEmbeddedBlobSize(proposed.getMaxEmbeddedBlobSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 117);
               } else if (prop.equals("MaxEmbeddedClobSize")) {
                  original.setMaxEmbeddedClobSize(proposed.getMaxEmbeddedClobSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("MaxIndexNameLength")) {
                  original.setMaxIndexNameLength(proposed.getMaxIndexNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 109);
               } else if (prop.equals("MaxIndexesPerTable")) {
                  original.setMaxIndexesPerTable(proposed.getMaxIndexesPerTable());
                  original._conditionalUnset(update.isUnsetUpdate(), 87);
               } else if (prop.equals("MaxTableNameLength")) {
                  original.setMaxTableNameLength(proposed.getMaxTableNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 113);
               } else if (prop.equals("NextSequenceQuery")) {
                  original.setNextSequenceQuery(proposed.getNextSequenceQuery());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("NullTypeName")) {
                  original.setNullTypeName(proposed.getNullTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 69);
               } else if (prop.equals("NumericTypeName")) {
                  original.setNumericTypeName(proposed.getNumericTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 73);
               } else if (prop.equals("OtherTypeName")) {
                  original.setOtherTypeName(proposed.getOtherTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 108);
               } else if (prop.equals("OuterJoinClause")) {
                  original.setOuterJoinClause(proposed.getOuterJoinClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Platform")) {
                  original.setPlatform(proposed.getPlatform());
                  original._conditionalUnset(update.isUnsetUpdate(), 84);
               } else if (prop.equals("RangePosition")) {
                  original.setRangePosition(proposed.getRangePosition());
                  original._conditionalUnset(update.isUnsetUpdate(), 63);
               } else if (prop.equals("RealTypeName")) {
                  original.setRealTypeName(proposed.getRealTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 99);
               } else if (prop.equals("RefTypeName")) {
                  original.setRefTypeName(proposed.getRefTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("RequiresAliasForSubselect")) {
                  original.setRequiresAliasForSubselect(proposed.getRequiresAliasForSubselect());
                  original._conditionalUnset(update.isUnsetUpdate(), 100);
               } else if (prop.equals("RequiresAutoCommitForMetaData")) {
                  original.setRequiresAutoCommitForMetaData(proposed.getRequiresAutoCommitForMetaData());
                  original._conditionalUnset(update.isUnsetUpdate(), 130);
               } else if (prop.equals("RequiresCastForComparisons")) {
                  original.setRequiresCastForComparisons(proposed.getRequiresCastForComparisons());
                  original._conditionalUnset(update.isUnsetUpdate(), 88);
               } else if (prop.equals("RequiresCastForMathFunctions")) {
                  original.setRequiresCastForMathFunctions(proposed.getRequiresCastForMathFunctions());
                  original._conditionalUnset(update.isUnsetUpdate(), 128);
               } else if (prop.equals("RequiresConditionForCrossJoin")) {
                  original.setRequiresConditionForCrossJoin(proposed.getRequiresConditionForCrossJoin());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("ReservedWords")) {
                  original.setReservedWords(proposed.getReservedWords());
                  original._conditionalUnset(update.isUnsetUpdate(), 95);
               } else if (prop.equals("SchemaCase")) {
                  original.setSchemaCase(proposed.getSchemaCase());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("SearchStringEscape")) {
                  original.setSearchStringEscape(proposed.getSearchStringEscape());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("SimulateLocking")) {
                  original.setSimulateLocking(proposed.getSimulateLocking());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("SmallintTypeName")) {
                  original.setSmallintTypeName(proposed.getSmallintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (prop.equals("StorageLimitationsFatal")) {
                  original.setStorageLimitationsFatal(proposed.getStorageLimitationsFatal());
                  original._conditionalUnset(update.isUnsetUpdate(), 104);
               } else if (prop.equals("StoreCharsAsNumbers")) {
                  original.setStoreCharsAsNumbers(proposed.getStoreCharsAsNumbers());
                  original._conditionalUnset(update.isUnsetUpdate(), 86);
               } else if (prop.equals("StoreLargeNumbersAsStrings")) {
                  original.setStoreLargeNumbersAsStrings(proposed.getStoreLargeNumbersAsStrings());
                  original._conditionalUnset(update.isUnsetUpdate(), 50);
               } else if (prop.equals("StringLengthFunction")) {
                  original.setStringLengthFunction(proposed.getStringLengthFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("StructTypeName")) {
                  original.setStructTypeName(proposed.getStructTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 61);
               } else if (prop.equals("SubstringFunctionName")) {
                  original.setSubstringFunctionName(proposed.getSubstringFunctionName());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("SupportsAlterTableWithAddColumn")) {
                  original.setSupportsAlterTableWithAddColumn(proposed.getSupportsAlterTableWithAddColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 44);
               } else if (prop.equals("SupportsAlterTableWithDropColumn")) {
                  original.setSupportsAlterTableWithDropColumn(proposed.getSupportsAlterTableWithDropColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("SupportsAutoAssign")) {
                  original.setSupportsAutoAssign(proposed.getSupportsAutoAssign());
                  original._conditionalUnset(update.isUnsetUpdate(), 49);
               } else if (prop.equals("SupportsCascadeDeleteAction")) {
                  original.setSupportsCascadeDeleteAction(proposed.getSupportsCascadeDeleteAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 133);
               } else if (prop.equals("SupportsCascadeUpdateAction")) {
                  original.setSupportsCascadeUpdateAction(proposed.getSupportsCascadeUpdateAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("SupportsCorrelatedSubselect")) {
                  original.setSupportsCorrelatedSubselect(proposed.getSupportsCorrelatedSubselect());
                  original._conditionalUnset(update.isUnsetUpdate(), 91);
               } else if (prop.equals("SupportsDefaultDeleteAction")) {
                  original.setSupportsDefaultDeleteAction(proposed.getSupportsDefaultDeleteAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("SupportsDefaultUpdateAction")) {
                  original.setSupportsDefaultUpdateAction(proposed.getSupportsDefaultUpdateAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 123);
               } else if (prop.equals("SupportsDeferredConstraints")) {
                  original.setSupportsDeferredConstraints(proposed.getSupportsDeferredConstraints());
                  original._conditionalUnset(update.isUnsetUpdate(), 98);
               } else if (prop.equals("SupportsForeignKeys")) {
                  original.setSupportsForeignKeys(proposed.getSupportsForeignKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 80);
               } else if (prop.equals("SupportsHaving")) {
                  original.setSupportsHaving(proposed.getSupportsHaving());
                  original._conditionalUnset(update.isUnsetUpdate(), 89);
               } else if (prop.equals("SupportsLockingWithDistinctClause")) {
                  original.setSupportsLockingWithDistinctClause(proposed.getSupportsLockingWithDistinctClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("SupportsLockingWithInnerJoin")) {
                  original.setSupportsLockingWithInnerJoin(proposed.getSupportsLockingWithInnerJoin());
                  original._conditionalUnset(update.isUnsetUpdate(), 105);
               } else if (prop.equals("SupportsLockingWithMultipleTables")) {
                  original.setSupportsLockingWithMultipleTables(proposed.getSupportsLockingWithMultipleTables());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("SupportsLockingWithOrderClause")) {
                  original.setSupportsLockingWithOrderClause(proposed.getSupportsLockingWithOrderClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 83);
               } else if (prop.equals("SupportsLockingWithOuterJoin")) {
                  original.setSupportsLockingWithOuterJoin(proposed.getSupportsLockingWithOuterJoin());
                  original._conditionalUnset(update.isUnsetUpdate(), 90);
               } else if (prop.equals("SupportsLockingWithSelectRange")) {
                  original.setSupportsLockingWithSelectRange(proposed.getSupportsLockingWithSelectRange());
                  original._conditionalUnset(update.isUnsetUpdate(), 103);
               } else if (prop.equals("SupportsModOperator")) {
                  original.setSupportsModOperator(proposed.getSupportsModOperator());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("SupportsMultipleNontransactionalResultSets")) {
                  original.setSupportsMultipleNontransactionalResultSets(proposed.getSupportsMultipleNontransactionalResultSets());
                  original._conditionalUnset(update.isUnsetUpdate(), 66);
               } else if (prop.equals("SupportsNullDeleteAction")) {
                  original.setSupportsNullDeleteAction(proposed.getSupportsNullDeleteAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 129);
               } else if (prop.equals("SupportsNullTableForGetColumns")) {
                  original.setSupportsNullTableForGetColumns(proposed.getSupportsNullTableForGetColumns());
                  original._conditionalUnset(update.isUnsetUpdate(), 46);
               } else if (prop.equals("SupportsNullTableForGetImportedKeys")) {
                  original.setSupportsNullTableForGetImportedKeys(proposed.getSupportsNullTableForGetImportedKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 92);
               } else if (prop.equals("SupportsNullTableForGetIndexInfo")) {
                  original.setSupportsNullTableForGetIndexInfo(proposed.getSupportsNullTableForGetIndexInfo());
                  original._conditionalUnset(update.isUnsetUpdate(), 101);
               } else if (prop.equals("SupportsNullTableForGetPrimaryKeys")) {
                  original.setSupportsNullTableForGetPrimaryKeys(proposed.getSupportsNullTableForGetPrimaryKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 126);
               } else if (prop.equals("SupportsNullUpdateAction")) {
                  original.setSupportsNullUpdateAction(proposed.getSupportsNullUpdateAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 96);
               } else if (prop.equals("SupportsQueryTimeout")) {
                  original.setSupportsQueryTimeout(proposed.getSupportsQueryTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("SupportsRestrictDeleteAction")) {
                  original.setSupportsRestrictDeleteAction(proposed.getSupportsRestrictDeleteAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("SupportsRestrictUpdateAction")) {
                  original.setSupportsRestrictUpdateAction(proposed.getSupportsRestrictUpdateAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 64);
               } else if (prop.equals("SupportsSchemaForGetColumns")) {
                  original.setSupportsSchemaForGetColumns(proposed.getSupportsSchemaForGetColumns());
                  original._conditionalUnset(update.isUnsetUpdate(), 124);
               } else if (prop.equals("SupportsSchemaForGetTables")) {
                  original.setSupportsSchemaForGetTables(proposed.getSupportsSchemaForGetTables());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("SupportsSelectEndIndex")) {
                  original.setSupportsSelectEndIndex(proposed.getSupportsSelectEndIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 48);
               } else if (prop.equals("SupportsSelectForUpdate")) {
                  original.setSupportsSelectForUpdate(proposed.getSupportsSelectForUpdate());
                  original._conditionalUnset(update.isUnsetUpdate(), 53);
               } else if (prop.equals("SupportsSelectStartIndex")) {
                  original.setSupportsSelectStartIndex(proposed.getSupportsSelectStartIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 119);
               } else if (prop.equals("SupportsSubselect")) {
                  original.setSupportsSubselect(proposed.getSupportsSubselect());
                  original._conditionalUnset(update.isUnsetUpdate(), 55);
               } else if (prop.equals("SupportsTimestampNanos")) {
                  original.setSupportsTimestampNanos(proposed.getSupportsTimestampNanos());
                  original._conditionalUnset(update.isUnsetUpdate(), 134);
               } else if (prop.equals("SupportsUniqueConstraints")) {
                  original.setSupportsUniqueConstraints(proposed.getSupportsUniqueConstraints());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("SystemSchemas")) {
                  original.setSystemSchemas(proposed.getSystemSchemas());
                  original._conditionalUnset(update.isUnsetUpdate(), 127);
               } else if (prop.equals("SystemTables")) {
                  original.setSystemTables(proposed.getSystemTables());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("TableForUpdateClause")) {
                  original.setTableForUpdateClause(proposed.getTableForUpdateClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 74);
               } else if (prop.equals("TableTypes")) {
                  original.setTableTypes(proposed.getTableTypes());
                  original._conditionalUnset(update.isUnsetUpdate(), 72);
               } else if (prop.equals("TimeTypeName")) {
                  original.setTimeTypeName(proposed.getTimeTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 56);
               } else if (prop.equals("TimestampTypeName")) {
                  original.setTimestampTypeName(proposed.getTimestampTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 131);
               } else if (prop.equals("TinyintTypeName")) {
                  original.setTinyintTypeName(proposed.getTinyintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 125);
               } else if (prop.equals("ToLowerCaseFunction")) {
                  original.setToLowerCaseFunction(proposed.getToLowerCaseFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 120);
               } else if (prop.equals("ToUpperCaseFunction")) {
                  original.setToUpperCaseFunction(proposed.getToUpperCaseFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 47);
               } else if (prop.equals("TrimBothFunction")) {
                  original.setTrimBothFunction(proposed.getTrimBothFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 118);
               } else if (prop.equals("TrimLeadingFunction")) {
                  original.setTrimLeadingFunction(proposed.getTrimLeadingFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("TrimTrailingFunction")) {
                  original.setTrimTrailingFunction(proposed.getTrimTrailingFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 102);
               } else if (prop.equals("UseGetBestRowIdentifierForPrimaryKeys")) {
                  original.setUseGetBestRowIdentifierForPrimaryKeys(proposed.getUseGetBestRowIdentifierForPrimaryKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 79);
               } else if (prop.equals("UseGetBytesForBlobs")) {
                  original.setUseGetBytesForBlobs(proposed.getUseGetBytesForBlobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 71);
               } else if (prop.equals("UseGetObjectForBlobs")) {
                  original.setUseGetObjectForBlobs(proposed.getUseGetObjectForBlobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 58);
               } else if (prop.equals("UseGetStringForClobs")) {
                  original.setUseGetStringForClobs(proposed.getUseGetStringForClobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 40);
               } else if (prop.equals("UseSchemaName")) {
                  original.setUseSchemaName(proposed.getUseSchemaName());
                  original._conditionalUnset(update.isUnsetUpdate(), 97);
               } else if (prop.equals("UseSetBytesForBlobs")) {
                  original.setUseSetBytesForBlobs(proposed.getUseSetBytesForBlobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("UseSetStringForClobs")) {
                  original.setUseSetStringForClobs(proposed.getUseSetStringForClobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 82);
               } else if (prop.equals("ValidationSQL")) {
                  original.setValidationSQL(proposed.getValidationSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 60);
               } else if (prop.equals("VarbinaryTypeName")) {
                  original.setVarbinaryTypeName(proposed.getVarbinaryTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 112);
               } else if (prop.equals("VarcharTypeName")) {
                  original.setVarcharTypeName(proposed.getVarcharTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 62);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            BuiltInDBDictionaryBeanImpl copy = (BuiltInDBDictionaryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AllowsAliasInBulkClause")) && this.bean.isAllowsAliasInBulkClauseSet()) {
               copy.setAllowsAliasInBulkClause(this.bean.getAllowsAliasInBulkClause());
            }

            if ((excludeProps == null || !excludeProps.contains("ArrayTypeName")) && this.bean.isArrayTypeNameSet()) {
               copy.setArrayTypeName(this.bean.getArrayTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoAssignClause")) && this.bean.isAutoAssignClauseSet()) {
               copy.setAutoAssignClause(this.bean.getAutoAssignClause());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoAssignTypeName")) && this.bean.isAutoAssignTypeNameSet()) {
               copy.setAutoAssignTypeName(this.bean.getAutoAssignTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BigintTypeName")) && this.bean.isBigintTypeNameSet()) {
               copy.setBigintTypeName(this.bean.getBigintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BinaryTypeName")) && this.bean.isBinaryTypeNameSet()) {
               copy.setBinaryTypeName(this.bean.getBinaryTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BitLengthFunction")) && this.bean.isBitLengthFunctionSet()) {
               copy.setBitLengthFunction(this.bean.getBitLengthFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("BitTypeName")) && this.bean.isBitTypeNameSet()) {
               copy.setBitTypeName(this.bean.getBitTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BlobTypeName")) && this.bean.isBlobTypeNameSet()) {
               copy.setBlobTypeName(this.bean.getBlobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BooleanTypeName")) && this.bean.isBooleanTypeNameSet()) {
               copy.setBooleanTypeName(this.bean.getBooleanTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("CastFunction")) && this.bean.isCastFunctionSet()) {
               copy.setCastFunction(this.bean.getCastFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("CatalogSeparator")) && this.bean.isCatalogSeparatorSet()) {
               copy.setCatalogSeparator(this.bean.getCatalogSeparator());
            }

            if ((excludeProps == null || !excludeProps.contains("CharTypeName")) && this.bean.isCharTypeNameSet()) {
               copy.setCharTypeName(this.bean.getCharTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("CharacterColumnSize")) && this.bean.isCharacterColumnSizeSet()) {
               copy.setCharacterColumnSize(this.bean.getCharacterColumnSize());
            }

            if ((excludeProps == null || !excludeProps.contains("ClobTypeName")) && this.bean.isClobTypeNameSet()) {
               copy.setClobTypeName(this.bean.getClobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("ClosePoolSQL")) && this.bean.isClosePoolSQLSet()) {
               copy.setClosePoolSQL(this.bean.getClosePoolSQL());
            }

            if ((excludeProps == null || !excludeProps.contains("ConcatenateDelimiter")) && this.bean.isConcatenateDelimiterSet()) {
               copy.setConcatenateDelimiter(this.bean.getConcatenateDelimiter());
            }

            if ((excludeProps == null || !excludeProps.contains("ConcatenateFunction")) && this.bean.isConcatenateFunctionSet()) {
               copy.setConcatenateFunction(this.bean.getConcatenateFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("ConstraintNameMode")) && this.bean.isConstraintNameModeSet()) {
               copy.setConstraintNameMode(this.bean.getConstraintNameMode());
            }

            if ((excludeProps == null || !excludeProps.contains("CreatePrimaryKeys")) && this.bean.isCreatePrimaryKeysSet()) {
               copy.setCreatePrimaryKeys(this.bean.getCreatePrimaryKeys());
            }

            if ((excludeProps == null || !excludeProps.contains("CrossJoinClause")) && this.bean.isCrossJoinClauseSet()) {
               copy.setCrossJoinClause(this.bean.getCrossJoinClause());
            }

            if ((excludeProps == null || !excludeProps.contains("CurrentDateFunction")) && this.bean.isCurrentDateFunctionSet()) {
               copy.setCurrentDateFunction(this.bean.getCurrentDateFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("CurrentTimeFunction")) && this.bean.isCurrentTimeFunctionSet()) {
               copy.setCurrentTimeFunction(this.bean.getCurrentTimeFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("CurrentTimestampFunction")) && this.bean.isCurrentTimestampFunctionSet()) {
               copy.setCurrentTimestampFunction(this.bean.getCurrentTimestampFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("DatePrecision")) && this.bean.isDatePrecisionSet()) {
               copy.setDatePrecision(this.bean.getDatePrecision());
            }

            if ((excludeProps == null || !excludeProps.contains("DateTypeName")) && this.bean.isDateTypeNameSet()) {
               copy.setDateTypeName(this.bean.getDateTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("DecimalTypeName")) && this.bean.isDecimalTypeNameSet()) {
               copy.setDecimalTypeName(this.bean.getDecimalTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("DistinctCountColumnSeparator")) && this.bean.isDistinctCountColumnSeparatorSet()) {
               copy.setDistinctCountColumnSeparator(this.bean.getDistinctCountColumnSeparator());
            }

            if ((excludeProps == null || !excludeProps.contains("DistinctTypeName")) && this.bean.isDistinctTypeNameSet()) {
               copy.setDistinctTypeName(this.bean.getDistinctTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("DoubleTypeName")) && this.bean.isDoubleTypeNameSet()) {
               copy.setDoubleTypeName(this.bean.getDoubleTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("DriverVendor")) && this.bean.isDriverVendorSet()) {
               copy.setDriverVendor(this.bean.getDriverVendor());
            }

            if ((excludeProps == null || !excludeProps.contains("DropTableSQL")) && this.bean.isDropTableSQLSet()) {
               copy.setDropTableSQL(this.bean.getDropTableSQL());
            }

            if ((excludeProps == null || !excludeProps.contains("FixedSizeTypeNames")) && this.bean.isFixedSizeTypeNamesSet()) {
               copy.setFixedSizeTypeNames(this.bean.getFixedSizeTypeNames());
            }

            if ((excludeProps == null || !excludeProps.contains("FloatTypeName")) && this.bean.isFloatTypeNameSet()) {
               copy.setFloatTypeName(this.bean.getFloatTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("ForUpdateClause")) && this.bean.isForUpdateClauseSet()) {
               copy.setForUpdateClause(this.bean.getForUpdateClause());
            }

            if ((excludeProps == null || !excludeProps.contains("InitializationSQL")) && this.bean.isInitializationSQLSet()) {
               copy.setInitializationSQL(this.bean.getInitializationSQL());
            }

            if ((excludeProps == null || !excludeProps.contains("InnerJoinClause")) && this.bean.isInnerJoinClauseSet()) {
               copy.setInnerJoinClause(this.bean.getInnerJoinClause());
            }

            if ((excludeProps == null || !excludeProps.contains("IntegerTypeName")) && this.bean.isIntegerTypeNameSet()) {
               copy.setIntegerTypeName(this.bean.getIntegerTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaObjectTypeName")) && this.bean.isJavaObjectTypeNameSet()) {
               copy.setJavaObjectTypeName(this.bean.getJavaObjectTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("JoinSyntax")) && this.bean.isJoinSyntaxSet()) {
               copy.setJoinSyntax(this.bean.getJoinSyntax());
            }

            if ((excludeProps == null || !excludeProps.contains("LastGeneratedKeyQuery")) && this.bean.isLastGeneratedKeyQuerySet()) {
               copy.setLastGeneratedKeyQuery(this.bean.getLastGeneratedKeyQuery());
            }

            if ((excludeProps == null || !excludeProps.contains("LongVarbinaryTypeName")) && this.bean.isLongVarbinaryTypeNameSet()) {
               copy.setLongVarbinaryTypeName(this.bean.getLongVarbinaryTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("LongVarcharTypeName")) && this.bean.isLongVarcharTypeNameSet()) {
               copy.setLongVarcharTypeName(this.bean.getLongVarcharTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxAutoAssignNameLength")) && this.bean.isMaxAutoAssignNameLengthSet()) {
               copy.setMaxAutoAssignNameLength(this.bean.getMaxAutoAssignNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxColumnNameLength")) && this.bean.isMaxColumnNameLengthSet()) {
               copy.setMaxColumnNameLength(this.bean.getMaxColumnNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxConstraintNameLength")) && this.bean.isMaxConstraintNameLengthSet()) {
               copy.setMaxConstraintNameLength(this.bean.getMaxConstraintNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxEmbeddedBlobSize")) && this.bean.isMaxEmbeddedBlobSizeSet()) {
               copy.setMaxEmbeddedBlobSize(this.bean.getMaxEmbeddedBlobSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxEmbeddedClobSize")) && this.bean.isMaxEmbeddedClobSizeSet()) {
               copy.setMaxEmbeddedClobSize(this.bean.getMaxEmbeddedClobSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxIndexNameLength")) && this.bean.isMaxIndexNameLengthSet()) {
               copy.setMaxIndexNameLength(this.bean.getMaxIndexNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxIndexesPerTable")) && this.bean.isMaxIndexesPerTableSet()) {
               copy.setMaxIndexesPerTable(this.bean.getMaxIndexesPerTable());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxTableNameLength")) && this.bean.isMaxTableNameLengthSet()) {
               copy.setMaxTableNameLength(this.bean.getMaxTableNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("NextSequenceQuery")) && this.bean.isNextSequenceQuerySet()) {
               copy.setNextSequenceQuery(this.bean.getNextSequenceQuery());
            }

            if ((excludeProps == null || !excludeProps.contains("NullTypeName")) && this.bean.isNullTypeNameSet()) {
               copy.setNullTypeName(this.bean.getNullTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("NumericTypeName")) && this.bean.isNumericTypeNameSet()) {
               copy.setNumericTypeName(this.bean.getNumericTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("OtherTypeName")) && this.bean.isOtherTypeNameSet()) {
               copy.setOtherTypeName(this.bean.getOtherTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("OuterJoinClause")) && this.bean.isOuterJoinClauseSet()) {
               copy.setOuterJoinClause(this.bean.getOuterJoinClause());
            }

            if ((excludeProps == null || !excludeProps.contains("Platform")) && this.bean.isPlatformSet()) {
               copy.setPlatform(this.bean.getPlatform());
            }

            if ((excludeProps == null || !excludeProps.contains("RangePosition")) && this.bean.isRangePositionSet()) {
               copy.setRangePosition(this.bean.getRangePosition());
            }

            if ((excludeProps == null || !excludeProps.contains("RealTypeName")) && this.bean.isRealTypeNameSet()) {
               copy.setRealTypeName(this.bean.getRealTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("RefTypeName")) && this.bean.isRefTypeNameSet()) {
               copy.setRefTypeName(this.bean.getRefTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresAliasForSubselect")) && this.bean.isRequiresAliasForSubselectSet()) {
               copy.setRequiresAliasForSubselect(this.bean.getRequiresAliasForSubselect());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresAutoCommitForMetaData")) && this.bean.isRequiresAutoCommitForMetaDataSet()) {
               copy.setRequiresAutoCommitForMetaData(this.bean.getRequiresAutoCommitForMetaData());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresCastForComparisons")) && this.bean.isRequiresCastForComparisonsSet()) {
               copy.setRequiresCastForComparisons(this.bean.getRequiresCastForComparisons());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresCastForMathFunctions")) && this.bean.isRequiresCastForMathFunctionsSet()) {
               copy.setRequiresCastForMathFunctions(this.bean.getRequiresCastForMathFunctions());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresConditionForCrossJoin")) && this.bean.isRequiresConditionForCrossJoinSet()) {
               copy.setRequiresConditionForCrossJoin(this.bean.getRequiresConditionForCrossJoin());
            }

            if ((excludeProps == null || !excludeProps.contains("ReservedWords")) && this.bean.isReservedWordsSet()) {
               copy.setReservedWords(this.bean.getReservedWords());
            }

            if ((excludeProps == null || !excludeProps.contains("SchemaCase")) && this.bean.isSchemaCaseSet()) {
               copy.setSchemaCase(this.bean.getSchemaCase());
            }

            if ((excludeProps == null || !excludeProps.contains("SearchStringEscape")) && this.bean.isSearchStringEscapeSet()) {
               copy.setSearchStringEscape(this.bean.getSearchStringEscape());
            }

            if ((excludeProps == null || !excludeProps.contains("SimulateLocking")) && this.bean.isSimulateLockingSet()) {
               copy.setSimulateLocking(this.bean.getSimulateLocking());
            }

            if ((excludeProps == null || !excludeProps.contains("SmallintTypeName")) && this.bean.isSmallintTypeNameSet()) {
               copy.setSmallintTypeName(this.bean.getSmallintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("StorageLimitationsFatal")) && this.bean.isStorageLimitationsFatalSet()) {
               copy.setStorageLimitationsFatal(this.bean.getStorageLimitationsFatal());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreCharsAsNumbers")) && this.bean.isStoreCharsAsNumbersSet()) {
               copy.setStoreCharsAsNumbers(this.bean.getStoreCharsAsNumbers());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreLargeNumbersAsStrings")) && this.bean.isStoreLargeNumbersAsStringsSet()) {
               copy.setStoreLargeNumbersAsStrings(this.bean.getStoreLargeNumbersAsStrings());
            }

            if ((excludeProps == null || !excludeProps.contains("StringLengthFunction")) && this.bean.isStringLengthFunctionSet()) {
               copy.setStringLengthFunction(this.bean.getStringLengthFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("StructTypeName")) && this.bean.isStructTypeNameSet()) {
               copy.setStructTypeName(this.bean.getStructTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("SubstringFunctionName")) && this.bean.isSubstringFunctionNameSet()) {
               copy.setSubstringFunctionName(this.bean.getSubstringFunctionName());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsAlterTableWithAddColumn")) && this.bean.isSupportsAlterTableWithAddColumnSet()) {
               copy.setSupportsAlterTableWithAddColumn(this.bean.getSupportsAlterTableWithAddColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsAlterTableWithDropColumn")) && this.bean.isSupportsAlterTableWithDropColumnSet()) {
               copy.setSupportsAlterTableWithDropColumn(this.bean.getSupportsAlterTableWithDropColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsAutoAssign")) && this.bean.isSupportsAutoAssignSet()) {
               copy.setSupportsAutoAssign(this.bean.getSupportsAutoAssign());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsCascadeDeleteAction")) && this.bean.isSupportsCascadeDeleteActionSet()) {
               copy.setSupportsCascadeDeleteAction(this.bean.getSupportsCascadeDeleteAction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsCascadeUpdateAction")) && this.bean.isSupportsCascadeUpdateActionSet()) {
               copy.setSupportsCascadeUpdateAction(this.bean.getSupportsCascadeUpdateAction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsCorrelatedSubselect")) && this.bean.isSupportsCorrelatedSubselectSet()) {
               copy.setSupportsCorrelatedSubselect(this.bean.getSupportsCorrelatedSubselect());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsDefaultDeleteAction")) && this.bean.isSupportsDefaultDeleteActionSet()) {
               copy.setSupportsDefaultDeleteAction(this.bean.getSupportsDefaultDeleteAction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsDefaultUpdateAction")) && this.bean.isSupportsDefaultUpdateActionSet()) {
               copy.setSupportsDefaultUpdateAction(this.bean.getSupportsDefaultUpdateAction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsDeferredConstraints")) && this.bean.isSupportsDeferredConstraintsSet()) {
               copy.setSupportsDeferredConstraints(this.bean.getSupportsDeferredConstraints());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsForeignKeys")) && this.bean.isSupportsForeignKeysSet()) {
               copy.setSupportsForeignKeys(this.bean.getSupportsForeignKeys());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsHaving")) && this.bean.isSupportsHavingSet()) {
               copy.setSupportsHaving(this.bean.getSupportsHaving());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsLockingWithDistinctClause")) && this.bean.isSupportsLockingWithDistinctClauseSet()) {
               copy.setSupportsLockingWithDistinctClause(this.bean.getSupportsLockingWithDistinctClause());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsLockingWithInnerJoin")) && this.bean.isSupportsLockingWithInnerJoinSet()) {
               copy.setSupportsLockingWithInnerJoin(this.bean.getSupportsLockingWithInnerJoin());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsLockingWithMultipleTables")) && this.bean.isSupportsLockingWithMultipleTablesSet()) {
               copy.setSupportsLockingWithMultipleTables(this.bean.getSupportsLockingWithMultipleTables());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsLockingWithOrderClause")) && this.bean.isSupportsLockingWithOrderClauseSet()) {
               copy.setSupportsLockingWithOrderClause(this.bean.getSupportsLockingWithOrderClause());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsLockingWithOuterJoin")) && this.bean.isSupportsLockingWithOuterJoinSet()) {
               copy.setSupportsLockingWithOuterJoin(this.bean.getSupportsLockingWithOuterJoin());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsLockingWithSelectRange")) && this.bean.isSupportsLockingWithSelectRangeSet()) {
               copy.setSupportsLockingWithSelectRange(this.bean.getSupportsLockingWithSelectRange());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsModOperator")) && this.bean.isSupportsModOperatorSet()) {
               copy.setSupportsModOperator(this.bean.getSupportsModOperator());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsMultipleNontransactionalResultSets")) && this.bean.isSupportsMultipleNontransactionalResultSetsSet()) {
               copy.setSupportsMultipleNontransactionalResultSets(this.bean.getSupportsMultipleNontransactionalResultSets());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsNullDeleteAction")) && this.bean.isSupportsNullDeleteActionSet()) {
               copy.setSupportsNullDeleteAction(this.bean.getSupportsNullDeleteAction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsNullTableForGetColumns")) && this.bean.isSupportsNullTableForGetColumnsSet()) {
               copy.setSupportsNullTableForGetColumns(this.bean.getSupportsNullTableForGetColumns());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsNullTableForGetImportedKeys")) && this.bean.isSupportsNullTableForGetImportedKeysSet()) {
               copy.setSupportsNullTableForGetImportedKeys(this.bean.getSupportsNullTableForGetImportedKeys());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsNullTableForGetIndexInfo")) && this.bean.isSupportsNullTableForGetIndexInfoSet()) {
               copy.setSupportsNullTableForGetIndexInfo(this.bean.getSupportsNullTableForGetIndexInfo());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsNullTableForGetPrimaryKeys")) && this.bean.isSupportsNullTableForGetPrimaryKeysSet()) {
               copy.setSupportsNullTableForGetPrimaryKeys(this.bean.getSupportsNullTableForGetPrimaryKeys());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsNullUpdateAction")) && this.bean.isSupportsNullUpdateActionSet()) {
               copy.setSupportsNullUpdateAction(this.bean.getSupportsNullUpdateAction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsQueryTimeout")) && this.bean.isSupportsQueryTimeoutSet()) {
               copy.setSupportsQueryTimeout(this.bean.getSupportsQueryTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsRestrictDeleteAction")) && this.bean.isSupportsRestrictDeleteActionSet()) {
               copy.setSupportsRestrictDeleteAction(this.bean.getSupportsRestrictDeleteAction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsRestrictUpdateAction")) && this.bean.isSupportsRestrictUpdateActionSet()) {
               copy.setSupportsRestrictUpdateAction(this.bean.getSupportsRestrictUpdateAction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSchemaForGetColumns")) && this.bean.isSupportsSchemaForGetColumnsSet()) {
               copy.setSupportsSchemaForGetColumns(this.bean.getSupportsSchemaForGetColumns());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSchemaForGetTables")) && this.bean.isSupportsSchemaForGetTablesSet()) {
               copy.setSupportsSchemaForGetTables(this.bean.getSupportsSchemaForGetTables());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSelectEndIndex")) && this.bean.isSupportsSelectEndIndexSet()) {
               copy.setSupportsSelectEndIndex(this.bean.getSupportsSelectEndIndex());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSelectForUpdate")) && this.bean.isSupportsSelectForUpdateSet()) {
               copy.setSupportsSelectForUpdate(this.bean.getSupportsSelectForUpdate());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSelectStartIndex")) && this.bean.isSupportsSelectStartIndexSet()) {
               copy.setSupportsSelectStartIndex(this.bean.getSupportsSelectStartIndex());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSubselect")) && this.bean.isSupportsSubselectSet()) {
               copy.setSupportsSubselect(this.bean.getSupportsSubselect());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsTimestampNanos")) && this.bean.isSupportsTimestampNanosSet()) {
               copy.setSupportsTimestampNanos(this.bean.getSupportsTimestampNanos());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsUniqueConstraints")) && this.bean.isSupportsUniqueConstraintsSet()) {
               copy.setSupportsUniqueConstraints(this.bean.getSupportsUniqueConstraints());
            }

            if ((excludeProps == null || !excludeProps.contains("SystemSchemas")) && this.bean.isSystemSchemasSet()) {
               copy.setSystemSchemas(this.bean.getSystemSchemas());
            }

            if ((excludeProps == null || !excludeProps.contains("SystemTables")) && this.bean.isSystemTablesSet()) {
               copy.setSystemTables(this.bean.getSystemTables());
            }

            if ((excludeProps == null || !excludeProps.contains("TableForUpdateClause")) && this.bean.isTableForUpdateClauseSet()) {
               copy.setTableForUpdateClause(this.bean.getTableForUpdateClause());
            }

            if ((excludeProps == null || !excludeProps.contains("TableTypes")) && this.bean.isTableTypesSet()) {
               copy.setTableTypes(this.bean.getTableTypes());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeTypeName")) && this.bean.isTimeTypeNameSet()) {
               copy.setTimeTypeName(this.bean.getTimeTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("TimestampTypeName")) && this.bean.isTimestampTypeNameSet()) {
               copy.setTimestampTypeName(this.bean.getTimestampTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("TinyintTypeName")) && this.bean.isTinyintTypeNameSet()) {
               copy.setTinyintTypeName(this.bean.getTinyintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("ToLowerCaseFunction")) && this.bean.isToLowerCaseFunctionSet()) {
               copy.setToLowerCaseFunction(this.bean.getToLowerCaseFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("ToUpperCaseFunction")) && this.bean.isToUpperCaseFunctionSet()) {
               copy.setToUpperCaseFunction(this.bean.getToUpperCaseFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("TrimBothFunction")) && this.bean.isTrimBothFunctionSet()) {
               copy.setTrimBothFunction(this.bean.getTrimBothFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("TrimLeadingFunction")) && this.bean.isTrimLeadingFunctionSet()) {
               copy.setTrimLeadingFunction(this.bean.getTrimLeadingFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("TrimTrailingFunction")) && this.bean.isTrimTrailingFunctionSet()) {
               copy.setTrimTrailingFunction(this.bean.getTrimTrailingFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("UseGetBestRowIdentifierForPrimaryKeys")) && this.bean.isUseGetBestRowIdentifierForPrimaryKeysSet()) {
               copy.setUseGetBestRowIdentifierForPrimaryKeys(this.bean.getUseGetBestRowIdentifierForPrimaryKeys());
            }

            if ((excludeProps == null || !excludeProps.contains("UseGetBytesForBlobs")) && this.bean.isUseGetBytesForBlobsSet()) {
               copy.setUseGetBytesForBlobs(this.bean.getUseGetBytesForBlobs());
            }

            if ((excludeProps == null || !excludeProps.contains("UseGetObjectForBlobs")) && this.bean.isUseGetObjectForBlobsSet()) {
               copy.setUseGetObjectForBlobs(this.bean.getUseGetObjectForBlobs());
            }

            if ((excludeProps == null || !excludeProps.contains("UseGetStringForClobs")) && this.bean.isUseGetStringForClobsSet()) {
               copy.setUseGetStringForClobs(this.bean.getUseGetStringForClobs());
            }

            if ((excludeProps == null || !excludeProps.contains("UseSchemaName")) && this.bean.isUseSchemaNameSet()) {
               copy.setUseSchemaName(this.bean.getUseSchemaName());
            }

            if ((excludeProps == null || !excludeProps.contains("UseSetBytesForBlobs")) && this.bean.isUseSetBytesForBlobsSet()) {
               copy.setUseSetBytesForBlobs(this.bean.getUseSetBytesForBlobs());
            }

            if ((excludeProps == null || !excludeProps.contains("UseSetStringForClobs")) && this.bean.isUseSetStringForClobsSet()) {
               copy.setUseSetStringForClobs(this.bean.getUseSetStringForClobs());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidationSQL")) && this.bean.isValidationSQLSet()) {
               copy.setValidationSQL(this.bean.getValidationSQL());
            }

            if ((excludeProps == null || !excludeProps.contains("VarbinaryTypeName")) && this.bean.isVarbinaryTypeNameSet()) {
               copy.setVarbinaryTypeName(this.bean.getVarbinaryTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("VarcharTypeName")) && this.bean.isVarcharTypeNameSet()) {
               copy.setVarcharTypeName(this.bean.getVarcharTypeName());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}
