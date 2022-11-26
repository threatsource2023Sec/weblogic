package kodo.jdbc.conf.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class SybaseDictionaryBeanImpl extends BuiltInDBDictionaryBeanImpl implements SybaseDictionaryBean, Serializable {
   private boolean _AllowsAliasInBulkClause;
   private String _AutoAssignClause;
   private String _BigintTypeName;
   private String _BinaryTypeName;
   private String _BitTypeName;
   private String _BlobTypeName;
   private String _ClobTypeName;
   private String _ConcatenateFunction;
   private boolean _CreateIdentityColumn;
   private String _CrossJoinClause;
   private String _CurrentDateFunction;
   private String _CurrentTimeFunction;
   private String _CurrentTimestampFunction;
   private String _DateTypeName;
   private String _DoubleTypeName;
   private String _FloatTypeName;
   private String _ForUpdateClause;
   private String _IdentityColumnName;
   private String _IntegerTypeName;
   private String _LastGeneratedKeyQuery;
   private String _LongVarbinaryTypeName;
   private String _LongVarcharTypeName;
   private int _MaxColumnNameLength;
   private int _MaxConstraintNameLength;
   private int _MaxIndexNameLength;
   private int _MaxTableNameLength;
   private String _Platform;
   private int _RangePosition;
   private boolean _RequiresAutoCommitForMetaData;
   private boolean _RequiresConditionForCrossJoin;
   private String _SchemaCase;
   private boolean _SupportsAutoAssign;
   private boolean _SupportsDeferredConstraints;
   private boolean _SupportsLockingWithDistinctClause;
   private boolean _SupportsModOperator;
   private boolean _SupportsNullTableForGetColumns;
   private boolean _SupportsSelectEndIndex;
   private String _TimeTypeName;
   private String _TimestampTypeName;
   private String _TrimBothFunction;
   private String _TrimLeadingFunction;
   private String _TrimTrailingFunction;
   private boolean _UseGetBytesForBlobs;
   private boolean _UseGetStringForClobs;
   private boolean _UseSetBytesForBlobs;
   private boolean _UseSetStringForClobs;
   private String _ValidationSQL;
   private static SchemaHelper2 _schemaHelper;

   public SybaseDictionaryBeanImpl() {
      this._initializeProperty(-1);
   }

   public SybaseDictionaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SybaseDictionaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
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

   public String getIdentityColumnName() {
      return this._IdentityColumnName;
   }

   public boolean isIdentityColumnNameInherited() {
      return false;
   }

   public boolean isIdentityColumnNameSet() {
      return this._isSet(135);
   }

   public void setIdentityColumnName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._IdentityColumnName;
      this._IdentityColumnName = param0;
      this._postSet(135, _oldVal, param0);
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

   public boolean getCreateIdentityColumn() {
      return this._CreateIdentityColumn;
   }

   public boolean isCreateIdentityColumnInherited() {
      return false;
   }

   public boolean isCreateIdentityColumnSet() {
      return this._isSet(136);
   }

   public void setCreateIdentityColumn(boolean param0) {
      boolean _oldVal = this._CreateIdentityColumn;
      this._CreateIdentityColumn = param0;
      this._postSet(136, _oldVal, param0);
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
               this._AllowsAliasInBulkClause = false;
               if (initOne) {
                  break;
               }
            case 65:
               this._AutoAssignClause = "IDENTITY";
               if (initOne) {
                  break;
               }
            case 93:
               this._BigintTypeName = "NUMERIC(38)";
               if (initOne) {
                  break;
               }
            case 3:
               this._BinaryTypeName = "BINARY";
               if (initOne) {
                  break;
               }
            case 45:
               this._BitTypeName = "TINYINT";
               if (initOne) {
                  break;
               }
            case 76:
               this._BlobTypeName = "IMAGE";
               if (initOne) {
                  break;
               }
            case 4:
               this._ClobTypeName = "TEXT";
               if (initOne) {
                  break;
               }
            case 8:
               this._ConcatenateFunction = "({0}+{1})";
               if (initOne) {
                  break;
               }
            case 136:
               this._CreateIdentityColumn = true;
               if (initOne) {
                  break;
               }
            case 23:
               this._CrossJoinClause = "JOIN";
               if (initOne) {
                  break;
               }
            case 115:
               this._CurrentDateFunction = "GETDATE()";
               if (initOne) {
                  break;
               }
            case 28:
               this._CurrentTimeFunction = "GETDATE()";
               if (initOne) {
                  break;
               }
            case 106:
               this._CurrentTimestampFunction = "GETDATE()";
               if (initOne) {
                  break;
               }
            case 25:
               this._DateTypeName = "DATETIME";
               if (initOne) {
                  break;
               }
            case 39:
               this._DoubleTypeName = "FLOAT(32)";
               if (initOne) {
                  break;
               }
            case 70:
               this._FloatTypeName = "FLOAT(16)";
               if (initOne) {
                  break;
               }
            case 77:
               this._ForUpdateClause = "FOR UPDATE AT ISOLATION SERIALIZABLE";
               if (initOne) {
                  break;
               }
            case 135:
               this._IdentityColumnName = "UNQ_INDEX";
               if (initOne) {
                  break;
               }
            case 75:
               this._IntegerTypeName = "INT";
               if (initOne) {
                  break;
               }
            case 94:
               this._LastGeneratedKeyQuery = "SELECT @@IDENTITY";
               if (initOne) {
                  break;
               }
            case 16:
               this._LongVarbinaryTypeName = "IMAGE";
               if (initOne) {
                  break;
               }
            case 22:
               this._LongVarcharTypeName = "TEXT";
               if (initOne) {
                  break;
               }
            case 38:
               this._MaxColumnNameLength = 30;
               if (initOne) {
                  break;
               }
            case 12:
               this._MaxConstraintNameLength = 30;
               if (initOne) {
                  break;
               }
            case 109:
               this._MaxIndexNameLength = 30;
               if (initOne) {
                  break;
               }
            case 113:
               this._MaxTableNameLength = 30;
               if (initOne) {
                  break;
               }
            case 84:
               this._Platform = "Sybase";
               if (initOne) {
                  break;
               }
            case 63:
               this._RangePosition = 2;
               if (initOne) {
                  break;
               }
            case 130:
               this._RequiresAutoCommitForMetaData = true;
               if (initOne) {
                  break;
               }
            case 29:
               this._RequiresConditionForCrossJoin = true;
               if (initOne) {
                  break;
               }
            case 34:
               this._SchemaCase = "preserve";
               if (initOne) {
                  break;
               }
            case 49:
               this._SupportsAutoAssign = true;
               if (initOne) {
                  break;
               }
            case 98:
               this._SupportsDeferredConstraints = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._SupportsLockingWithDistinctClause = false;
               if (initOne) {
                  break;
               }
            case 33:
               this._SupportsModOperator = true;
               if (initOne) {
                  break;
               }
            case 46:
               this._SupportsNullTableForGetColumns = false;
               if (initOne) {
                  break;
               }
            case 48:
               this._SupportsSelectEndIndex = true;
               if (initOne) {
                  break;
               }
            case 56:
               this._TimeTypeName = "DATETIME";
               if (initOne) {
                  break;
               }
            case 131:
               this._TimestampTypeName = "DATETIME";
               if (initOne) {
                  break;
               }
            case 118:
               this._TrimBothFunction = "LTRIM(RTRIM({0}))";
               if (initOne) {
                  break;
               }
            case 19:
               this._TrimLeadingFunction = "LTRIM({0})";
               if (initOne) {
                  break;
               }
            case 102:
               this._TrimTrailingFunction = "RTRIM({0})";
               if (initOne) {
                  break;
               }
            case 71:
               this._UseGetBytesForBlobs = true;
               if (initOne) {
                  break;
               }
            case 40:
               this._UseGetStringForClobs = true;
               if (initOne) {
                  break;
               }
            case 11:
               this._UseSetBytesForBlobs = true;
               if (initOne) {
                  break;
               }
            case 82:
               this._UseSetStringForClobs = true;
               if (initOne) {
                  break;
               }
            case 60:
               this._ValidationSQL = "SELECT GETDATE()";
               if (initOne) {
                  break;
               }
            case 6:
            case 7:
            case 9:
            case 10:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 20:
            case 21:
            case 24:
            case 26:
            case 27:
            case 30:
            case 31:
            case 32:
            case 35:
            case 36:
            case 37:
            case 41:
            case 42:
            case 43:
            case 44:
            case 47:
            case 50:
            case 51:
            case 53:
            case 54:
            case 55:
            case 57:
            case 58:
            case 59:
            case 61:
            case 62:
            case 64:
            case 66:
            case 67:
            case 68:
            case 69:
            case 72:
            case 73:
            case 74:
            case 78:
            case 79:
            case 80:
            case 81:
            case 83:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 95:
            case 96:
            case 97:
            case 99:
            case 100:
            case 101:
            case 103:
            case 104:
            case 105:
            case 107:
            case 108:
            case 110:
            case 111:
            case 112:
            case 114:
            case 116:
            case 117:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 132:
            case 133:
            case 134:
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

   public static class SchemaHelper2 extends BuiltInDBDictionaryBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("platform")) {
                  return 84;
               }
            case 9:
            case 10:
            case 12:
            case 28:
            case 30:
            case 31:
            case 32:
            case 36:
            default:
               break;
            case 11:
               if (s.equals("schema-case")) {
                  return 34;
               }
               break;
            case 13:
               if (s.equals("bit-type-name")) {
                  return 45;
               }
               break;
            case 14:
               if (s.equals("blob-type-name")) {
                  return 76;
               }

               if (s.equals("clob-type-name")) {
                  return 4;
               }

               if (s.equals("date-type-name")) {
                  return 25;
               }

               if (s.equals("range-position")) {
                  return 63;
               }

               if (s.equals("time-type-name")) {
                  return 56;
               }

               if (s.equals("validation-sql")) {
                  return 60;
               }
               break;
            case 15:
               if (s.equals("float-type-name")) {
                  return 70;
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
               break;
            case 17:
               if (s.equals("cross-join-clause")) {
                  return 23;
               }

               if (s.equals("for-update-clause")) {
                  return 77;
               }

               if (s.equals("integer-type-name")) {
                  return 75;
               }
               break;
            case 18:
               if (s.equals("auto-assign-clause")) {
                  return 65;
               }

               if (s.equals("trim-both-function")) {
                  return 118;
               }
               break;
            case 19:
               if (s.equals("timestamp-type-name")) {
                  return 131;
               }
               break;
            case 20:
               if (s.equals("concatenate-function")) {
                  return 8;
               }

               if (s.equals("identity-column-name")) {
                  return 135;
               }

               if (s.equals("supports-auto-assign")) {
                  return 49;
               }
               break;
            case 21:
               if (s.equals("current-date-function")) {
                  return 115;
               }

               if (s.equals("current-time-function")) {
                  return 28;
               }

               if (s.equals("max-index-name-length")) {
                  return 109;
               }

               if (s.equals("max-table-name-length")) {
                  return 113;
               }

               if (s.equals("supports-mod-operator")) {
                  return 33;
               }

               if (s.equals("trim-leading-function")) {
                  return 19;
               }
               break;
            case 22:
               if (s.equals("create-identity-column")) {
                  return 136;
               }

               if (s.equals("long-varchar-type-name")) {
                  return 22;
               }

               if (s.equals("max-column-name-length")) {
                  return 38;
               }

               if (s.equals("trim-trailing-function")) {
                  return 102;
               }
               break;
            case 23:
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

               if (s.equals("use-get-string-for-clobs")) {
                  return 40;
               }

               if (s.equals("use-set-string-for-clobs")) {
                  return 82;
               }
               break;
            case 25:
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
               break;
            case 27:
               if (s.equals("allows-alias-in-bulk-clause")) {
                  return 52;
               }
               break;
            case 29:
               if (s.equals("supports-deferred-constraints")) {
                  return 98;
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
               break;
            case 35:
               if (s.equals("supports-null-table-for-get-columns")) {
                  return 46;
               }
               break;
            case 37:
               if (s.equals("supports-locking-with-distinct-clause")) {
                  return 5;
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
            case 3:
               return "binary-type-name";
            case 4:
               return "clob-type-name";
            case 5:
               return "supports-locking-with-distinct-clause";
            case 6:
            case 7:
            case 9:
            case 10:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 20:
            case 21:
            case 24:
            case 26:
            case 27:
            case 30:
            case 31:
            case 32:
            case 35:
            case 36:
            case 37:
            case 41:
            case 42:
            case 43:
            case 44:
            case 47:
            case 50:
            case 51:
            case 53:
            case 54:
            case 55:
            case 57:
            case 58:
            case 59:
            case 61:
            case 62:
            case 64:
            case 66:
            case 67:
            case 68:
            case 69:
            case 72:
            case 73:
            case 74:
            case 78:
            case 79:
            case 80:
            case 81:
            case 83:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 95:
            case 96:
            case 97:
            case 99:
            case 100:
            case 101:
            case 103:
            case 104:
            case 105:
            case 107:
            case 108:
            case 110:
            case 111:
            case 112:
            case 114:
            case 116:
            case 117:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 132:
            case 133:
            case 134:
            default:
               return super.getElementName(propIndex);
            case 8:
               return "concatenate-function";
            case 11:
               return "use-set-bytes-for-blobs";
            case 12:
               return "max-constraint-name-length";
            case 16:
               return "long-varbinary-type-name";
            case 19:
               return "trim-leading-function";
            case 22:
               return "long-varchar-type-name";
            case 23:
               return "cross-join-clause";
            case 25:
               return "date-type-name";
            case 28:
               return "current-time-function";
            case 29:
               return "requires-condition-for-cross-join";
            case 33:
               return "supports-mod-operator";
            case 34:
               return "schema-case";
            case 38:
               return "max-column-name-length";
            case 39:
               return "double-type-name";
            case 40:
               return "use-get-string-for-clobs";
            case 45:
               return "bit-type-name";
            case 46:
               return "supports-null-table-for-get-columns";
            case 48:
               return "supports-select-end-index";
            case 49:
               return "supports-auto-assign";
            case 52:
               return "allows-alias-in-bulk-clause";
            case 56:
               return "time-type-name";
            case 60:
               return "validation-sql";
            case 63:
               return "range-position";
            case 65:
               return "auto-assign-clause";
            case 70:
               return "float-type-name";
            case 71:
               return "use-get-bytes-for-blobs";
            case 75:
               return "integer-type-name";
            case 76:
               return "blob-type-name";
            case 77:
               return "for-update-clause";
            case 82:
               return "use-set-string-for-clobs";
            case 84:
               return "platform";
            case 93:
               return "bigint-type-name";
            case 94:
               return "last-generated-key-query";
            case 98:
               return "supports-deferred-constraints";
            case 102:
               return "trim-trailing-function";
            case 106:
               return "current-timestamp-function";
            case 109:
               return "max-index-name-length";
            case 113:
               return "max-table-name-length";
            case 115:
               return "current-date-function";
            case 118:
               return "trim-both-function";
            case 130:
               return "requires-auto-commit-for-meta-data";
            case 131:
               return "timestamp-type-name";
            case 135:
               return "identity-column-name";
            case 136:
               return "create-identity-column";
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
            case 135:
               return true;
            case 136:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends BuiltInDBDictionaryBeanImpl.Helper {
      private SybaseDictionaryBeanImpl bean;

      protected Helper(SybaseDictionaryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "BinaryTypeName";
            case 4:
               return "ClobTypeName";
            case 5:
               return "SupportsLockingWithDistinctClause";
            case 6:
            case 7:
            case 9:
            case 10:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 20:
            case 21:
            case 24:
            case 26:
            case 27:
            case 30:
            case 31:
            case 32:
            case 35:
            case 36:
            case 37:
            case 41:
            case 42:
            case 43:
            case 44:
            case 47:
            case 50:
            case 51:
            case 53:
            case 54:
            case 55:
            case 57:
            case 58:
            case 59:
            case 61:
            case 62:
            case 64:
            case 66:
            case 67:
            case 68:
            case 69:
            case 72:
            case 73:
            case 74:
            case 78:
            case 79:
            case 80:
            case 81:
            case 83:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 95:
            case 96:
            case 97:
            case 99:
            case 100:
            case 101:
            case 103:
            case 104:
            case 105:
            case 107:
            case 108:
            case 110:
            case 111:
            case 112:
            case 114:
            case 116:
            case 117:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 132:
            case 133:
            case 134:
            default:
               return super.getPropertyName(propIndex);
            case 8:
               return "ConcatenateFunction";
            case 11:
               return "UseSetBytesForBlobs";
            case 12:
               return "MaxConstraintNameLength";
            case 16:
               return "LongVarbinaryTypeName";
            case 19:
               return "TrimLeadingFunction";
            case 22:
               return "LongVarcharTypeName";
            case 23:
               return "CrossJoinClause";
            case 25:
               return "DateTypeName";
            case 28:
               return "CurrentTimeFunction";
            case 29:
               return "RequiresConditionForCrossJoin";
            case 33:
               return "SupportsModOperator";
            case 34:
               return "SchemaCase";
            case 38:
               return "MaxColumnNameLength";
            case 39:
               return "DoubleTypeName";
            case 40:
               return "UseGetStringForClobs";
            case 45:
               return "BitTypeName";
            case 46:
               return "SupportsNullTableForGetColumns";
            case 48:
               return "SupportsSelectEndIndex";
            case 49:
               return "SupportsAutoAssign";
            case 52:
               return "AllowsAliasInBulkClause";
            case 56:
               return "TimeTypeName";
            case 60:
               return "ValidationSQL";
            case 63:
               return "RangePosition";
            case 65:
               return "AutoAssignClause";
            case 70:
               return "FloatTypeName";
            case 71:
               return "UseGetBytesForBlobs";
            case 75:
               return "IntegerTypeName";
            case 76:
               return "BlobTypeName";
            case 77:
               return "ForUpdateClause";
            case 82:
               return "UseSetStringForClobs";
            case 84:
               return "Platform";
            case 93:
               return "BigintTypeName";
            case 94:
               return "LastGeneratedKeyQuery";
            case 98:
               return "SupportsDeferredConstraints";
            case 102:
               return "TrimTrailingFunction";
            case 106:
               return "CurrentTimestampFunction";
            case 109:
               return "MaxIndexNameLength";
            case 113:
               return "MaxTableNameLength";
            case 115:
               return "CurrentDateFunction";
            case 118:
               return "TrimBothFunction";
            case 130:
               return "RequiresAutoCommitForMetaData";
            case 131:
               return "TimestampTypeName";
            case 135:
               return "IdentityColumnName";
            case 136:
               return "CreateIdentityColumn";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AllowsAliasInBulkClause")) {
            return 52;
         } else if (propName.equals("AutoAssignClause")) {
            return 65;
         } else if (propName.equals("BigintTypeName")) {
            return 93;
         } else if (propName.equals("BinaryTypeName")) {
            return 3;
         } else if (propName.equals("BitTypeName")) {
            return 45;
         } else if (propName.equals("BlobTypeName")) {
            return 76;
         } else if (propName.equals("ClobTypeName")) {
            return 4;
         } else if (propName.equals("ConcatenateFunction")) {
            return 8;
         } else if (propName.equals("CreateIdentityColumn")) {
            return 136;
         } else if (propName.equals("CrossJoinClause")) {
            return 23;
         } else if (propName.equals("CurrentDateFunction")) {
            return 115;
         } else if (propName.equals("CurrentTimeFunction")) {
            return 28;
         } else if (propName.equals("CurrentTimestampFunction")) {
            return 106;
         } else if (propName.equals("DateTypeName")) {
            return 25;
         } else if (propName.equals("DoubleTypeName")) {
            return 39;
         } else if (propName.equals("FloatTypeName")) {
            return 70;
         } else if (propName.equals("ForUpdateClause")) {
            return 77;
         } else if (propName.equals("IdentityColumnName")) {
            return 135;
         } else if (propName.equals("IntegerTypeName")) {
            return 75;
         } else if (propName.equals("LastGeneratedKeyQuery")) {
            return 94;
         } else if (propName.equals("LongVarbinaryTypeName")) {
            return 16;
         } else if (propName.equals("LongVarcharTypeName")) {
            return 22;
         } else if (propName.equals("MaxColumnNameLength")) {
            return 38;
         } else if (propName.equals("MaxConstraintNameLength")) {
            return 12;
         } else if (propName.equals("MaxIndexNameLength")) {
            return 109;
         } else if (propName.equals("MaxTableNameLength")) {
            return 113;
         } else if (propName.equals("Platform")) {
            return 84;
         } else if (propName.equals("RangePosition")) {
            return 63;
         } else if (propName.equals("RequiresAutoCommitForMetaData")) {
            return 130;
         } else if (propName.equals("RequiresConditionForCrossJoin")) {
            return 29;
         } else if (propName.equals("SchemaCase")) {
            return 34;
         } else if (propName.equals("SupportsAutoAssign")) {
            return 49;
         } else if (propName.equals("SupportsDeferredConstraints")) {
            return 98;
         } else if (propName.equals("SupportsLockingWithDistinctClause")) {
            return 5;
         } else if (propName.equals("SupportsModOperator")) {
            return 33;
         } else if (propName.equals("SupportsNullTableForGetColumns")) {
            return 46;
         } else if (propName.equals("SupportsSelectEndIndex")) {
            return 48;
         } else if (propName.equals("TimeTypeName")) {
            return 56;
         } else if (propName.equals("TimestampTypeName")) {
            return 131;
         } else if (propName.equals("TrimBothFunction")) {
            return 118;
         } else if (propName.equals("TrimLeadingFunction")) {
            return 19;
         } else if (propName.equals("TrimTrailingFunction")) {
            return 102;
         } else if (propName.equals("UseGetBytesForBlobs")) {
            return 71;
         } else if (propName.equals("UseGetStringForClobs")) {
            return 40;
         } else if (propName.equals("UseSetBytesForBlobs")) {
            return 11;
         } else if (propName.equals("UseSetStringForClobs")) {
            return 82;
         } else {
            return propName.equals("ValidationSQL") ? 60 : super.getPropertyIndex(propName);
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

            if (this.bean.isAutoAssignClauseSet()) {
               buf.append("AutoAssignClause");
               buf.append(String.valueOf(this.bean.getAutoAssignClause()));
            }

            if (this.bean.isBigintTypeNameSet()) {
               buf.append("BigintTypeName");
               buf.append(String.valueOf(this.bean.getBigintTypeName()));
            }

            if (this.bean.isBinaryTypeNameSet()) {
               buf.append("BinaryTypeName");
               buf.append(String.valueOf(this.bean.getBinaryTypeName()));
            }

            if (this.bean.isBitTypeNameSet()) {
               buf.append("BitTypeName");
               buf.append(String.valueOf(this.bean.getBitTypeName()));
            }

            if (this.bean.isBlobTypeNameSet()) {
               buf.append("BlobTypeName");
               buf.append(String.valueOf(this.bean.getBlobTypeName()));
            }

            if (this.bean.isClobTypeNameSet()) {
               buf.append("ClobTypeName");
               buf.append(String.valueOf(this.bean.getClobTypeName()));
            }

            if (this.bean.isConcatenateFunctionSet()) {
               buf.append("ConcatenateFunction");
               buf.append(String.valueOf(this.bean.getConcatenateFunction()));
            }

            if (this.bean.isCreateIdentityColumnSet()) {
               buf.append("CreateIdentityColumn");
               buf.append(String.valueOf(this.bean.getCreateIdentityColumn()));
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

            if (this.bean.isDateTypeNameSet()) {
               buf.append("DateTypeName");
               buf.append(String.valueOf(this.bean.getDateTypeName()));
            }

            if (this.bean.isDoubleTypeNameSet()) {
               buf.append("DoubleTypeName");
               buf.append(String.valueOf(this.bean.getDoubleTypeName()));
            }

            if (this.bean.isFloatTypeNameSet()) {
               buf.append("FloatTypeName");
               buf.append(String.valueOf(this.bean.getFloatTypeName()));
            }

            if (this.bean.isForUpdateClauseSet()) {
               buf.append("ForUpdateClause");
               buf.append(String.valueOf(this.bean.getForUpdateClause()));
            }

            if (this.bean.isIdentityColumnNameSet()) {
               buf.append("IdentityColumnName");
               buf.append(String.valueOf(this.bean.getIdentityColumnName()));
            }

            if (this.bean.isIntegerTypeNameSet()) {
               buf.append("IntegerTypeName");
               buf.append(String.valueOf(this.bean.getIntegerTypeName()));
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

            if (this.bean.isMaxColumnNameLengthSet()) {
               buf.append("MaxColumnNameLength");
               buf.append(String.valueOf(this.bean.getMaxColumnNameLength()));
            }

            if (this.bean.isMaxConstraintNameLengthSet()) {
               buf.append("MaxConstraintNameLength");
               buf.append(String.valueOf(this.bean.getMaxConstraintNameLength()));
            }

            if (this.bean.isMaxIndexNameLengthSet()) {
               buf.append("MaxIndexNameLength");
               buf.append(String.valueOf(this.bean.getMaxIndexNameLength()));
            }

            if (this.bean.isMaxTableNameLengthSet()) {
               buf.append("MaxTableNameLength");
               buf.append(String.valueOf(this.bean.getMaxTableNameLength()));
            }

            if (this.bean.isPlatformSet()) {
               buf.append("Platform");
               buf.append(String.valueOf(this.bean.getPlatform()));
            }

            if (this.bean.isRangePositionSet()) {
               buf.append("RangePosition");
               buf.append(String.valueOf(this.bean.getRangePosition()));
            }

            if (this.bean.isRequiresAutoCommitForMetaDataSet()) {
               buf.append("RequiresAutoCommitForMetaData");
               buf.append(String.valueOf(this.bean.getRequiresAutoCommitForMetaData()));
            }

            if (this.bean.isRequiresConditionForCrossJoinSet()) {
               buf.append("RequiresConditionForCrossJoin");
               buf.append(String.valueOf(this.bean.getRequiresConditionForCrossJoin()));
            }

            if (this.bean.isSchemaCaseSet()) {
               buf.append("SchemaCase");
               buf.append(String.valueOf(this.bean.getSchemaCase()));
            }

            if (this.bean.isSupportsAutoAssignSet()) {
               buf.append("SupportsAutoAssign");
               buf.append(String.valueOf(this.bean.getSupportsAutoAssign()));
            }

            if (this.bean.isSupportsDeferredConstraintsSet()) {
               buf.append("SupportsDeferredConstraints");
               buf.append(String.valueOf(this.bean.getSupportsDeferredConstraints()));
            }

            if (this.bean.isSupportsLockingWithDistinctClauseSet()) {
               buf.append("SupportsLockingWithDistinctClause");
               buf.append(String.valueOf(this.bean.getSupportsLockingWithDistinctClause()));
            }

            if (this.bean.isSupportsModOperatorSet()) {
               buf.append("SupportsModOperator");
               buf.append(String.valueOf(this.bean.getSupportsModOperator()));
            }

            if (this.bean.isSupportsNullTableForGetColumnsSet()) {
               buf.append("SupportsNullTableForGetColumns");
               buf.append(String.valueOf(this.bean.getSupportsNullTableForGetColumns()));
            }

            if (this.bean.isSupportsSelectEndIndexSet()) {
               buf.append("SupportsSelectEndIndex");
               buf.append(String.valueOf(this.bean.getSupportsSelectEndIndex()));
            }

            if (this.bean.isTimeTypeNameSet()) {
               buf.append("TimeTypeName");
               buf.append(String.valueOf(this.bean.getTimeTypeName()));
            }

            if (this.bean.isTimestampTypeNameSet()) {
               buf.append("TimestampTypeName");
               buf.append(String.valueOf(this.bean.getTimestampTypeName()));
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

            if (this.bean.isUseGetBytesForBlobsSet()) {
               buf.append("UseGetBytesForBlobs");
               buf.append(String.valueOf(this.bean.getUseGetBytesForBlobs()));
            }

            if (this.bean.isUseGetStringForClobsSet()) {
               buf.append("UseGetStringForClobs");
               buf.append(String.valueOf(this.bean.getUseGetStringForClobs()));
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

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            SybaseDictionaryBeanImpl otherTyped = (SybaseDictionaryBeanImpl)other;
            this.computeDiff("AllowsAliasInBulkClause", this.bean.getAllowsAliasInBulkClause(), otherTyped.getAllowsAliasInBulkClause(), false);
            this.computeDiff("AutoAssignClause", this.bean.getAutoAssignClause(), otherTyped.getAutoAssignClause(), false);
            this.computeDiff("BigintTypeName", this.bean.getBigintTypeName(), otherTyped.getBigintTypeName(), false);
            this.computeDiff("BinaryTypeName", this.bean.getBinaryTypeName(), otherTyped.getBinaryTypeName(), false);
            this.computeDiff("BitTypeName", this.bean.getBitTypeName(), otherTyped.getBitTypeName(), false);
            this.computeDiff("BlobTypeName", this.bean.getBlobTypeName(), otherTyped.getBlobTypeName(), false);
            this.computeDiff("ClobTypeName", this.bean.getClobTypeName(), otherTyped.getClobTypeName(), false);
            this.computeDiff("ConcatenateFunction", this.bean.getConcatenateFunction(), otherTyped.getConcatenateFunction(), false);
            this.computeDiff("CreateIdentityColumn", this.bean.getCreateIdentityColumn(), otherTyped.getCreateIdentityColumn(), false);
            this.computeDiff("CrossJoinClause", this.bean.getCrossJoinClause(), otherTyped.getCrossJoinClause(), false);
            this.computeDiff("CurrentDateFunction", this.bean.getCurrentDateFunction(), otherTyped.getCurrentDateFunction(), false);
            this.computeDiff("CurrentTimeFunction", this.bean.getCurrentTimeFunction(), otherTyped.getCurrentTimeFunction(), false);
            this.computeDiff("CurrentTimestampFunction", this.bean.getCurrentTimestampFunction(), otherTyped.getCurrentTimestampFunction(), false);
            this.computeDiff("DateTypeName", this.bean.getDateTypeName(), otherTyped.getDateTypeName(), false);
            this.computeDiff("DoubleTypeName", this.bean.getDoubleTypeName(), otherTyped.getDoubleTypeName(), false);
            this.computeDiff("FloatTypeName", this.bean.getFloatTypeName(), otherTyped.getFloatTypeName(), false);
            this.computeDiff("ForUpdateClause", this.bean.getForUpdateClause(), otherTyped.getForUpdateClause(), false);
            this.computeDiff("IdentityColumnName", this.bean.getIdentityColumnName(), otherTyped.getIdentityColumnName(), false);
            this.computeDiff("IntegerTypeName", this.bean.getIntegerTypeName(), otherTyped.getIntegerTypeName(), false);
            this.computeDiff("LastGeneratedKeyQuery", this.bean.getLastGeneratedKeyQuery(), otherTyped.getLastGeneratedKeyQuery(), false);
            this.computeDiff("LongVarbinaryTypeName", this.bean.getLongVarbinaryTypeName(), otherTyped.getLongVarbinaryTypeName(), false);
            this.computeDiff("LongVarcharTypeName", this.bean.getLongVarcharTypeName(), otherTyped.getLongVarcharTypeName(), false);
            this.computeDiff("MaxColumnNameLength", this.bean.getMaxColumnNameLength(), otherTyped.getMaxColumnNameLength(), false);
            this.computeDiff("MaxConstraintNameLength", this.bean.getMaxConstraintNameLength(), otherTyped.getMaxConstraintNameLength(), false);
            this.computeDiff("MaxIndexNameLength", this.bean.getMaxIndexNameLength(), otherTyped.getMaxIndexNameLength(), false);
            this.computeDiff("MaxTableNameLength", this.bean.getMaxTableNameLength(), otherTyped.getMaxTableNameLength(), false);
            this.computeDiff("Platform", this.bean.getPlatform(), otherTyped.getPlatform(), false);
            this.computeDiff("RangePosition", this.bean.getRangePosition(), otherTyped.getRangePosition(), false);
            this.computeDiff("RequiresAutoCommitForMetaData", this.bean.getRequiresAutoCommitForMetaData(), otherTyped.getRequiresAutoCommitForMetaData(), false);
            this.computeDiff("RequiresConditionForCrossJoin", this.bean.getRequiresConditionForCrossJoin(), otherTyped.getRequiresConditionForCrossJoin(), false);
            this.computeDiff("SchemaCase", this.bean.getSchemaCase(), otherTyped.getSchemaCase(), false);
            this.computeDiff("SupportsAutoAssign", this.bean.getSupportsAutoAssign(), otherTyped.getSupportsAutoAssign(), false);
            this.computeDiff("SupportsDeferredConstraints", this.bean.getSupportsDeferredConstraints(), otherTyped.getSupportsDeferredConstraints(), false);
            this.computeDiff("SupportsLockingWithDistinctClause", this.bean.getSupportsLockingWithDistinctClause(), otherTyped.getSupportsLockingWithDistinctClause(), false);
            this.computeDiff("SupportsModOperator", this.bean.getSupportsModOperator(), otherTyped.getSupportsModOperator(), false);
            this.computeDiff("SupportsNullTableForGetColumns", this.bean.getSupportsNullTableForGetColumns(), otherTyped.getSupportsNullTableForGetColumns(), false);
            this.computeDiff("SupportsSelectEndIndex", this.bean.getSupportsSelectEndIndex(), otherTyped.getSupportsSelectEndIndex(), false);
            this.computeDiff("TimeTypeName", this.bean.getTimeTypeName(), otherTyped.getTimeTypeName(), false);
            this.computeDiff("TimestampTypeName", this.bean.getTimestampTypeName(), otherTyped.getTimestampTypeName(), false);
            this.computeDiff("TrimBothFunction", this.bean.getTrimBothFunction(), otherTyped.getTrimBothFunction(), false);
            this.computeDiff("TrimLeadingFunction", this.bean.getTrimLeadingFunction(), otherTyped.getTrimLeadingFunction(), false);
            this.computeDiff("TrimTrailingFunction", this.bean.getTrimTrailingFunction(), otherTyped.getTrimTrailingFunction(), false);
            this.computeDiff("UseGetBytesForBlobs", this.bean.getUseGetBytesForBlobs(), otherTyped.getUseGetBytesForBlobs(), false);
            this.computeDiff("UseGetStringForClobs", this.bean.getUseGetStringForClobs(), otherTyped.getUseGetStringForClobs(), false);
            this.computeDiff("UseSetBytesForBlobs", this.bean.getUseSetBytesForBlobs(), otherTyped.getUseSetBytesForBlobs(), false);
            this.computeDiff("UseSetStringForClobs", this.bean.getUseSetStringForClobs(), otherTyped.getUseSetStringForClobs(), false);
            this.computeDiff("ValidationSQL", this.bean.getValidationSQL(), otherTyped.getValidationSQL(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SybaseDictionaryBeanImpl original = (SybaseDictionaryBeanImpl)event.getSourceBean();
            SybaseDictionaryBeanImpl proposed = (SybaseDictionaryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AllowsAliasInBulkClause")) {
                  original.setAllowsAliasInBulkClause(proposed.getAllowsAliasInBulkClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 52);
               } else if (prop.equals("AutoAssignClause")) {
                  original.setAutoAssignClause(proposed.getAutoAssignClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 65);
               } else if (prop.equals("BigintTypeName")) {
                  original.setBigintTypeName(proposed.getBigintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 93);
               } else if (prop.equals("BinaryTypeName")) {
                  original.setBinaryTypeName(proposed.getBinaryTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("BitTypeName")) {
                  original.setBitTypeName(proposed.getBitTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (prop.equals("BlobTypeName")) {
                  original.setBlobTypeName(proposed.getBlobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 76);
               } else if (prop.equals("ClobTypeName")) {
                  original.setClobTypeName(proposed.getClobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ConcatenateFunction")) {
                  original.setConcatenateFunction(proposed.getConcatenateFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("CreateIdentityColumn")) {
                  original.setCreateIdentityColumn(proposed.getCreateIdentityColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 136);
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
               } else if (prop.equals("DateTypeName")) {
                  original.setDateTypeName(proposed.getDateTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("DoubleTypeName")) {
                  original.setDoubleTypeName(proposed.getDoubleTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("FloatTypeName")) {
                  original.setFloatTypeName(proposed.getFloatTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 70);
               } else if (prop.equals("ForUpdateClause")) {
                  original.setForUpdateClause(proposed.getForUpdateClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 77);
               } else if (prop.equals("IdentityColumnName")) {
                  original.setIdentityColumnName(proposed.getIdentityColumnName());
                  original._conditionalUnset(update.isUnsetUpdate(), 135);
               } else if (prop.equals("IntegerTypeName")) {
                  original.setIntegerTypeName(proposed.getIntegerTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 75);
               } else if (prop.equals("LastGeneratedKeyQuery")) {
                  original.setLastGeneratedKeyQuery(proposed.getLastGeneratedKeyQuery());
                  original._conditionalUnset(update.isUnsetUpdate(), 94);
               } else if (prop.equals("LongVarbinaryTypeName")) {
                  original.setLongVarbinaryTypeName(proposed.getLongVarbinaryTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("LongVarcharTypeName")) {
                  original.setLongVarcharTypeName(proposed.getLongVarcharTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("MaxColumnNameLength")) {
                  original.setMaxColumnNameLength(proposed.getMaxColumnNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 38);
               } else if (prop.equals("MaxConstraintNameLength")) {
                  original.setMaxConstraintNameLength(proposed.getMaxConstraintNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("MaxIndexNameLength")) {
                  original.setMaxIndexNameLength(proposed.getMaxIndexNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 109);
               } else if (prop.equals("MaxTableNameLength")) {
                  original.setMaxTableNameLength(proposed.getMaxTableNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 113);
               } else if (prop.equals("Platform")) {
                  original.setPlatform(proposed.getPlatform());
                  original._conditionalUnset(update.isUnsetUpdate(), 84);
               } else if (prop.equals("RangePosition")) {
                  original.setRangePosition(proposed.getRangePosition());
                  original._conditionalUnset(update.isUnsetUpdate(), 63);
               } else if (prop.equals("RequiresAutoCommitForMetaData")) {
                  original.setRequiresAutoCommitForMetaData(proposed.getRequiresAutoCommitForMetaData());
                  original._conditionalUnset(update.isUnsetUpdate(), 130);
               } else if (prop.equals("RequiresConditionForCrossJoin")) {
                  original.setRequiresConditionForCrossJoin(proposed.getRequiresConditionForCrossJoin());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("SchemaCase")) {
                  original.setSchemaCase(proposed.getSchemaCase());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("SupportsAutoAssign")) {
                  original.setSupportsAutoAssign(proposed.getSupportsAutoAssign());
                  original._conditionalUnset(update.isUnsetUpdate(), 49);
               } else if (prop.equals("SupportsDeferredConstraints")) {
                  original.setSupportsDeferredConstraints(proposed.getSupportsDeferredConstraints());
                  original._conditionalUnset(update.isUnsetUpdate(), 98);
               } else if (prop.equals("SupportsLockingWithDistinctClause")) {
                  original.setSupportsLockingWithDistinctClause(proposed.getSupportsLockingWithDistinctClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("SupportsModOperator")) {
                  original.setSupportsModOperator(proposed.getSupportsModOperator());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("SupportsNullTableForGetColumns")) {
                  original.setSupportsNullTableForGetColumns(proposed.getSupportsNullTableForGetColumns());
                  original._conditionalUnset(update.isUnsetUpdate(), 46);
               } else if (prop.equals("SupportsSelectEndIndex")) {
                  original.setSupportsSelectEndIndex(proposed.getSupportsSelectEndIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 48);
               } else if (prop.equals("TimeTypeName")) {
                  original.setTimeTypeName(proposed.getTimeTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 56);
               } else if (prop.equals("TimestampTypeName")) {
                  original.setTimestampTypeName(proposed.getTimestampTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 131);
               } else if (prop.equals("TrimBothFunction")) {
                  original.setTrimBothFunction(proposed.getTrimBothFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 118);
               } else if (prop.equals("TrimLeadingFunction")) {
                  original.setTrimLeadingFunction(proposed.getTrimLeadingFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("TrimTrailingFunction")) {
                  original.setTrimTrailingFunction(proposed.getTrimTrailingFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 102);
               } else if (prop.equals("UseGetBytesForBlobs")) {
                  original.setUseGetBytesForBlobs(proposed.getUseGetBytesForBlobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 71);
               } else if (prop.equals("UseGetStringForClobs")) {
                  original.setUseGetStringForClobs(proposed.getUseGetStringForClobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 40);
               } else if (prop.equals("UseSetBytesForBlobs")) {
                  original.setUseSetBytesForBlobs(proposed.getUseSetBytesForBlobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("UseSetStringForClobs")) {
                  original.setUseSetStringForClobs(proposed.getUseSetStringForClobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 82);
               } else if (prop.equals("ValidationSQL")) {
                  original.setValidationSQL(proposed.getValidationSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 60);
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
            SybaseDictionaryBeanImpl copy = (SybaseDictionaryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AllowsAliasInBulkClause")) && this.bean.isAllowsAliasInBulkClauseSet()) {
               copy.setAllowsAliasInBulkClause(this.bean.getAllowsAliasInBulkClause());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoAssignClause")) && this.bean.isAutoAssignClauseSet()) {
               copy.setAutoAssignClause(this.bean.getAutoAssignClause());
            }

            if ((excludeProps == null || !excludeProps.contains("BigintTypeName")) && this.bean.isBigintTypeNameSet()) {
               copy.setBigintTypeName(this.bean.getBigintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BinaryTypeName")) && this.bean.isBinaryTypeNameSet()) {
               copy.setBinaryTypeName(this.bean.getBinaryTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BitTypeName")) && this.bean.isBitTypeNameSet()) {
               copy.setBitTypeName(this.bean.getBitTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BlobTypeName")) && this.bean.isBlobTypeNameSet()) {
               copy.setBlobTypeName(this.bean.getBlobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("ClobTypeName")) && this.bean.isClobTypeNameSet()) {
               copy.setClobTypeName(this.bean.getClobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConcatenateFunction")) && this.bean.isConcatenateFunctionSet()) {
               copy.setConcatenateFunction(this.bean.getConcatenateFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("CreateIdentityColumn")) && this.bean.isCreateIdentityColumnSet()) {
               copy.setCreateIdentityColumn(this.bean.getCreateIdentityColumn());
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

            if ((excludeProps == null || !excludeProps.contains("DateTypeName")) && this.bean.isDateTypeNameSet()) {
               copy.setDateTypeName(this.bean.getDateTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("DoubleTypeName")) && this.bean.isDoubleTypeNameSet()) {
               copy.setDoubleTypeName(this.bean.getDoubleTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("FloatTypeName")) && this.bean.isFloatTypeNameSet()) {
               copy.setFloatTypeName(this.bean.getFloatTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("ForUpdateClause")) && this.bean.isForUpdateClauseSet()) {
               copy.setForUpdateClause(this.bean.getForUpdateClause());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityColumnName")) && this.bean.isIdentityColumnNameSet()) {
               copy.setIdentityColumnName(this.bean.getIdentityColumnName());
            }

            if ((excludeProps == null || !excludeProps.contains("IntegerTypeName")) && this.bean.isIntegerTypeNameSet()) {
               copy.setIntegerTypeName(this.bean.getIntegerTypeName());
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

            if ((excludeProps == null || !excludeProps.contains("MaxColumnNameLength")) && this.bean.isMaxColumnNameLengthSet()) {
               copy.setMaxColumnNameLength(this.bean.getMaxColumnNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxConstraintNameLength")) && this.bean.isMaxConstraintNameLengthSet()) {
               copy.setMaxConstraintNameLength(this.bean.getMaxConstraintNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxIndexNameLength")) && this.bean.isMaxIndexNameLengthSet()) {
               copy.setMaxIndexNameLength(this.bean.getMaxIndexNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxTableNameLength")) && this.bean.isMaxTableNameLengthSet()) {
               copy.setMaxTableNameLength(this.bean.getMaxTableNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("Platform")) && this.bean.isPlatformSet()) {
               copy.setPlatform(this.bean.getPlatform());
            }

            if ((excludeProps == null || !excludeProps.contains("RangePosition")) && this.bean.isRangePositionSet()) {
               copy.setRangePosition(this.bean.getRangePosition());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresAutoCommitForMetaData")) && this.bean.isRequiresAutoCommitForMetaDataSet()) {
               copy.setRequiresAutoCommitForMetaData(this.bean.getRequiresAutoCommitForMetaData());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresConditionForCrossJoin")) && this.bean.isRequiresConditionForCrossJoinSet()) {
               copy.setRequiresConditionForCrossJoin(this.bean.getRequiresConditionForCrossJoin());
            }

            if ((excludeProps == null || !excludeProps.contains("SchemaCase")) && this.bean.isSchemaCaseSet()) {
               copy.setSchemaCase(this.bean.getSchemaCase());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsAutoAssign")) && this.bean.isSupportsAutoAssignSet()) {
               copy.setSupportsAutoAssign(this.bean.getSupportsAutoAssign());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsDeferredConstraints")) && this.bean.isSupportsDeferredConstraintsSet()) {
               copy.setSupportsDeferredConstraints(this.bean.getSupportsDeferredConstraints());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsLockingWithDistinctClause")) && this.bean.isSupportsLockingWithDistinctClauseSet()) {
               copy.setSupportsLockingWithDistinctClause(this.bean.getSupportsLockingWithDistinctClause());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsModOperator")) && this.bean.isSupportsModOperatorSet()) {
               copy.setSupportsModOperator(this.bean.getSupportsModOperator());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsNullTableForGetColumns")) && this.bean.isSupportsNullTableForGetColumnsSet()) {
               copy.setSupportsNullTableForGetColumns(this.bean.getSupportsNullTableForGetColumns());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSelectEndIndex")) && this.bean.isSupportsSelectEndIndexSet()) {
               copy.setSupportsSelectEndIndex(this.bean.getSupportsSelectEndIndex());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeTypeName")) && this.bean.isTimeTypeNameSet()) {
               copy.setTimeTypeName(this.bean.getTimeTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("TimestampTypeName")) && this.bean.isTimestampTypeNameSet()) {
               copy.setTimestampTypeName(this.bean.getTimestampTypeName());
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

            if ((excludeProps == null || !excludeProps.contains("UseGetBytesForBlobs")) && this.bean.isUseGetBytesForBlobsSet()) {
               copy.setUseGetBytesForBlobs(this.bean.getUseGetBytesForBlobs());
            }

            if ((excludeProps == null || !excludeProps.contains("UseGetStringForClobs")) && this.bean.isUseGetStringForClobsSet()) {
               copy.setUseGetStringForClobs(this.bean.getUseGetStringForClobs());
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
