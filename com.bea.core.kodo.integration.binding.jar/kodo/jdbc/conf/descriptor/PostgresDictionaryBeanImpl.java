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

public class PostgresDictionaryBeanImpl extends BuiltInDBDictionaryBeanImpl implements PostgresDictionaryBean, Serializable {
   private String _AllSequencesFromOneSchemaSQL;
   private String _AllSequencesSQL;
   private boolean _AllowsAliasInBulkClause;
   private String _AutoAssignTypeName;
   private String _BinaryTypeName;
   private String _BitTypeName;
   private String _BlobTypeName;
   private String _ClobTypeName;
   private int _DatePrecision;
   private String _DoubleTypeName;
   private String _LastGeneratedKeyQuery;
   private String _LongVarbinaryTypeName;
   private String _LongVarcharTypeName;
   private int _MaxAutoAssignNameLength;
   private int _MaxColumnNameLength;
   private int _MaxConstraintNameLength;
   private int _MaxIndexNameLength;
   private int _MaxTableNameLength;
   private String _NamedSequenceFromOneSchemaSQL;
   private String _NamedSequencesFromAllSchemasSQL;
   private String _NextSequenceQuery;
   private String _Platform;
   private int _RangePosition;
   private String _RealTypeName;
   private boolean _RequiresAliasForSubselect;
   private String _SchemaCase;
   private String _SmallintTypeName;
   private boolean _SupportsAlterTableWithDropColumn;
   private boolean _SupportsAutoAssign;
   private boolean _SupportsDeferredConstraints;
   private boolean _SupportsLockingWithDistinctClause;
   private boolean _SupportsLockingWithOuterJoin;
   private boolean _SupportsNullTableForGetImportedKeys;
   private boolean _SupportsSelectEndIndex;
   private boolean _SupportsSelectStartIndex;
   private boolean _SupportsSetFetchSize;
   private String _TimestampTypeName;
   private String _TinyintTypeName;
   private boolean _UseGetBytesForBlobs;
   private boolean _UseGetStringForClobs;
   private boolean _UseSetBytesForBlobs;
   private boolean _UseSetStringForClobs;
   private String _ValidationSQL;
   private String _VarbinaryTypeName;
   private String _VarcharTypeName;
   private static SchemaHelper2 _schemaHelper;

   public PostgresDictionaryBeanImpl() {
      this._initializeProperty(-1);
   }

   public PostgresDictionaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PostgresDictionaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String getAllSequencesFromOneSchemaSQL() {
      return this._AllSequencesFromOneSchemaSQL;
   }

   public boolean isAllSequencesFromOneSchemaSQLInherited() {
      return false;
   }

   public boolean isAllSequencesFromOneSchemaSQLSet() {
      return this._isSet(135);
   }

   public void setAllSequencesFromOneSchemaSQL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AllSequencesFromOneSchemaSQL;
      this._AllSequencesFromOneSchemaSQL = param0;
      this._postSet(135, _oldVal, param0);
   }

   public boolean getSupportsSetFetchSize() {
      return this._SupportsSetFetchSize;
   }

   public boolean isSupportsSetFetchSizeInherited() {
      return false;
   }

   public boolean isSupportsSetFetchSizeSet() {
      return this._isSet(136);
   }

   public void setSupportsSetFetchSize(boolean param0) {
      boolean _oldVal = this._SupportsSetFetchSize;
      this._SupportsSetFetchSize = param0;
      this._postSet(136, _oldVal, param0);
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

   public String getAllSequencesSQL() {
      return this._AllSequencesSQL;
   }

   public int getMaxConstraintNameLength() {
      return this._MaxConstraintNameLength;
   }

   public boolean isAllSequencesSQLInherited() {
      return false;
   }

   public boolean isAllSequencesSQLSet() {
      return this._isSet(137);
   }

   public boolean isMaxConstraintNameLengthInherited() {
      return false;
   }

   public boolean isMaxConstraintNameLengthSet() {
      return this._isSet(12);
   }

   public void setAllSequencesSQL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AllSequencesSQL;
      this._AllSequencesSQL = param0;
      this._postSet(137, _oldVal, param0);
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

   public String getNamedSequencesFromAllSchemasSQL() {
      return this._NamedSequencesFromAllSchemasSQL;
   }

   public boolean isNamedSequencesFromAllSchemasSQLInherited() {
      return false;
   }

   public boolean isNamedSequencesFromAllSchemasSQLSet() {
      return this._isSet(138);
   }

   public void setNamedSequencesFromAllSchemasSQL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NamedSequencesFromAllSchemasSQL;
      this._NamedSequencesFromAllSchemasSQL = param0;
      this._postSet(138, _oldVal, param0);
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

   public String getNamedSequenceFromOneSchemaSQL() {
      return this._NamedSequenceFromOneSchemaSQL;
   }

   public boolean isNamedSequenceFromOneSchemaSQLInherited() {
      return false;
   }

   public boolean isNamedSequenceFromOneSchemaSQLSet() {
      return this._isSet(139);
   }

   public void setNamedSequenceFromOneSchemaSQL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NamedSequenceFromOneSchemaSQL;
      this._NamedSequenceFromOneSchemaSQL = param0;
      this._postSet(139, _oldVal, param0);
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
         idx = 135;
      }

      try {
         switch (idx) {
            case 135:
               this._AllSequencesFromOneSchemaSQL = "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class, pg_namespace WHERE relkind='S' AND pg_class.relnamespace = pg_namespace.oid AND nspname = ?";
               if (initOne) {
                  break;
               }
            case 137:
               this._AllSequencesSQL = "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class WHERE relkind='S'";
               if (initOne) {
                  break;
               }
            case 52:
               this._AllowsAliasInBulkClause = false;
               if (initOne) {
                  break;
               }
            case 57:
               this._AutoAssignTypeName = "BIGSERIAL";
               if (initOne) {
                  break;
               }
            case 3:
               this._BinaryTypeName = "BYTEA";
               if (initOne) {
                  break;
               }
            case 45:
               this._BitTypeName = "BOOL";
               if (initOne) {
                  break;
               }
            case 76:
               this._BlobTypeName = "BYTEA";
               if (initOne) {
                  break;
               }
            case 4:
               this._ClobTypeName = "TEXT";
               if (initOne) {
                  break;
               }
            case 43:
               this._DatePrecision = 10000000;
               if (initOne) {
                  break;
               }
            case 39:
               this._DoubleTypeName = "DOUBLE PRECISION";
               if (initOne) {
                  break;
               }
            case 94:
               this._LastGeneratedKeyQuery = "SELECT CURRVAL(''{2}'')";
               if (initOne) {
                  break;
               }
            case 16:
               this._LongVarbinaryTypeName = "BYTEA";
               if (initOne) {
                  break;
               }
            case 22:
               this._LongVarcharTypeName = "TEXT";
               if (initOne) {
                  break;
               }
            case 59:
               this._MaxAutoAssignNameLength = 31;
               if (initOne) {
                  break;
               }
            case 38:
               this._MaxColumnNameLength = 31;
               if (initOne) {
                  break;
               }
            case 12:
               this._MaxConstraintNameLength = 31;
               if (initOne) {
                  break;
               }
            case 109:
               this._MaxIndexNameLength = 31;
               if (initOne) {
                  break;
               }
            case 113:
               this._MaxTableNameLength = 31;
               if (initOne) {
                  break;
               }
            case 139:
               this._NamedSequenceFromOneSchemaSQL = "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class, pg_namespace WHERE relkind='S' AND pg_class.relnamespace = pg_namespace.oid AND relname = ? AND nspname = ?";
               if (initOne) {
                  break;
               }
            case 138:
               this._NamedSequencesFromAllSchemasSQL = "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class WHERE relkind='S' AND relname = ?";
               if (initOne) {
                  break;
               }
            case 21:
               this._NextSequenceQuery = "SELECT NEXTVAL(''{0}'')";
               if (initOne) {
                  break;
               }
            case 84:
               this._Platform = "PostgreSQL";
               if (initOne) {
                  break;
               }
            case 63:
               this._RangePosition = 3;
               if (initOne) {
                  break;
               }
            case 99:
               this._RealTypeName = "FLOAT8";
               if (initOne) {
                  break;
               }
            case 100:
               this._RequiresAliasForSubselect = true;
               if (initOne) {
                  break;
               }
            case 34:
               this._SchemaCase = "lower";
               if (initOne) {
                  break;
               }
            case 42:
               this._SmallintTypeName = "SMALLINT";
               if (initOne) {
                  break;
               }
            case 27:
               this._SupportsAlterTableWithDropColumn = false;
               if (initOne) {
                  break;
               }
            case 49:
               this._SupportsAutoAssign = true;
               if (initOne) {
                  break;
               }
            case 98:
               this._SupportsDeferredConstraints = true;
               if (initOne) {
                  break;
               }
            case 5:
               this._SupportsLockingWithDistinctClause = false;
               if (initOne) {
                  break;
               }
            case 90:
               this._SupportsLockingWithOuterJoin = false;
               if (initOne) {
                  break;
               }
            case 92:
               this._SupportsNullTableForGetImportedKeys = true;
               if (initOne) {
                  break;
               }
            case 48:
               this._SupportsSelectEndIndex = true;
               if (initOne) {
                  break;
               }
            case 119:
               this._SupportsSelectStartIndex = true;
               if (initOne) {
                  break;
               }
            case 136:
               this._SupportsSetFetchSize = true;
               if (initOne) {
                  break;
               }
            case 131:
               this._TimestampTypeName = "ABSTIME";
               if (initOne) {
                  break;
               }
            case 125:
               this._TinyintTypeName = "SMALLINT";
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
               this._ValidationSQL = "SELECT NOW()";
               if (initOne) {
                  break;
               }
            case 112:
               this._VarbinaryTypeName = "BYTEA";
               if (initOne) {
                  break;
               }
            case 62:
               this._VarcharTypeName = "VARCHAR{0}";
               if (initOne) {
                  break;
               }
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 41:
            case 44:
            case 46:
            case 47:
            case 50:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 61:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 72:
            case 73:
            case 74:
            case 75:
            case 77:
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 97:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 110:
            case 111:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
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
            case 15:
            case 30:
            case 31:
            case 35:
            case 38:
            case 39:
            case 40:
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

               if (s.equals("date-precision")) {
                  return 43;
               }

               if (s.equals("range-position")) {
                  return 63;
               }

               if (s.equals("real-type-name")) {
                  return 99;
               }

               if (s.equals("validation-sql")) {
                  return 60;
               }
               break;
            case 16:
               if (s.equals("binary-type-name")) {
                  return 3;
               }

               if (s.equals("double-type-name")) {
                  return 39;
               }
               break;
            case 17:
               if (s.equals("all-sequences-sql")) {
                  return 137;
               }

               if (s.equals("tinyint-type-name")) {
                  return 125;
               }

               if (s.equals("varchar-type-name")) {
                  return 62;
               }
               break;
            case 18:
               if (s.equals("smallint-type-name")) {
                  return 42;
               }
               break;
            case 19:
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
               if (s.equals("supports-auto-assign")) {
                  return 49;
               }
               break;
            case 21:
               if (s.equals("auto-assign-type-name")) {
                  return 57;
               }

               if (s.equals("max-index-name-length")) {
                  return 109;
               }

               if (s.equals("max-table-name-length")) {
                  return 113;
               }
               break;
            case 22:
               if (s.equals("long-varchar-type-name")) {
                  return 22;
               }

               if (s.equals("max-column-name-length")) {
                  return 38;
               }
               break;
            case 23:
               if (s.equals("supports-set-fetch-size")) {
                  return 136;
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
               if (s.equals("max-constraint-name-length")) {
                  return 12;
               }
               break;
            case 27:
               if (s.equals("allows-alias-in-bulk-clause")) {
                  return 52;
               }

               if (s.equals("max-auto-assign-name-length")) {
                  return 59;
               }

               if (s.equals("supports-select-start-index")) {
                  return 119;
               }
               break;
            case 28:
               if (s.equals("requires-alias-for-subselect")) {
                  return 100;
               }
               break;
            case 29:
               if (s.equals("supports-deferred-constraints")) {
                  return 98;
               }
               break;
            case 32:
               if (s.equals("supports-locking-with-outer-join")) {
                  return 90;
               }
               break;
            case 33:
               if (s.equals("all-sequences-from-one-schema-sql")) {
                  return 135;
               }
               break;
            case 34:
               if (s.equals("named-sequence-from-one-schema-sql")) {
                  return 139;
               }
               break;
            case 36:
               if (s.equals("named-sequences-from-all-schemas-sql")) {
                  return 138;
               }
               break;
            case 37:
               if (s.equals("supports-alter-table-with-drop-column")) {
                  return 27;
               }

               if (s.equals("supports-locking-with-distinct-clause")) {
                  return 5;
               }
               break;
            case 41:
               if (s.equals("supports-null-table-for-get-imported-keys")) {
                  return 92;
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
            case 8:
            case 9:
            case 10:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 41:
            case 44:
            case 46:
            case 47:
            case 50:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 61:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 72:
            case 73:
            case 74:
            case 75:
            case 77:
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 97:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 110:
            case 111:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 132:
            case 133:
            case 134:
            default:
               return super.getElementName(propIndex);
            case 11:
               return "use-set-bytes-for-blobs";
            case 12:
               return "max-constraint-name-length";
            case 16:
               return "long-varbinary-type-name";
            case 21:
               return "next-sequence-query";
            case 22:
               return "long-varchar-type-name";
            case 27:
               return "supports-alter-table-with-drop-column";
            case 34:
               return "schema-case";
            case 38:
               return "max-column-name-length";
            case 39:
               return "double-type-name";
            case 40:
               return "use-get-string-for-clobs";
            case 42:
               return "smallint-type-name";
            case 43:
               return "date-precision";
            case 45:
               return "bit-type-name";
            case 48:
               return "supports-select-end-index";
            case 49:
               return "supports-auto-assign";
            case 52:
               return "allows-alias-in-bulk-clause";
            case 57:
               return "auto-assign-type-name";
            case 59:
               return "max-auto-assign-name-length";
            case 60:
               return "validation-sql";
            case 62:
               return "varchar-type-name";
            case 63:
               return "range-position";
            case 71:
               return "use-get-bytes-for-blobs";
            case 76:
               return "blob-type-name";
            case 82:
               return "use-set-string-for-clobs";
            case 84:
               return "platform";
            case 90:
               return "supports-locking-with-outer-join";
            case 92:
               return "supports-null-table-for-get-imported-keys";
            case 94:
               return "last-generated-key-query";
            case 98:
               return "supports-deferred-constraints";
            case 99:
               return "real-type-name";
            case 100:
               return "requires-alias-for-subselect";
            case 109:
               return "max-index-name-length";
            case 112:
               return "varbinary-type-name";
            case 113:
               return "max-table-name-length";
            case 119:
               return "supports-select-start-index";
            case 125:
               return "tinyint-type-name";
            case 131:
               return "timestamp-type-name";
            case 135:
               return "all-sequences-from-one-schema-sql";
            case 136:
               return "supports-set-fetch-size";
            case 137:
               return "all-sequences-sql";
            case 138:
               return "named-sequences-from-all-schemas-sql";
            case 139:
               return "named-sequence-from-one-schema-sql";
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
            case 137:
               return true;
            case 138:
               return true;
            case 139:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends BuiltInDBDictionaryBeanImpl.Helper {
      private PostgresDictionaryBeanImpl bean;

      protected Helper(PostgresDictionaryBeanImpl bean) {
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
            case 8:
            case 9:
            case 10:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 41:
            case 44:
            case 46:
            case 47:
            case 50:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 61:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 72:
            case 73:
            case 74:
            case 75:
            case 77:
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 97:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 110:
            case 111:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 132:
            case 133:
            case 134:
            default:
               return super.getPropertyName(propIndex);
            case 11:
               return "UseSetBytesForBlobs";
            case 12:
               return "MaxConstraintNameLength";
            case 16:
               return "LongVarbinaryTypeName";
            case 21:
               return "NextSequenceQuery";
            case 22:
               return "LongVarcharTypeName";
            case 27:
               return "SupportsAlterTableWithDropColumn";
            case 34:
               return "SchemaCase";
            case 38:
               return "MaxColumnNameLength";
            case 39:
               return "DoubleTypeName";
            case 40:
               return "UseGetStringForClobs";
            case 42:
               return "SmallintTypeName";
            case 43:
               return "DatePrecision";
            case 45:
               return "BitTypeName";
            case 48:
               return "SupportsSelectEndIndex";
            case 49:
               return "SupportsAutoAssign";
            case 52:
               return "AllowsAliasInBulkClause";
            case 57:
               return "AutoAssignTypeName";
            case 59:
               return "MaxAutoAssignNameLength";
            case 60:
               return "ValidationSQL";
            case 62:
               return "VarcharTypeName";
            case 63:
               return "RangePosition";
            case 71:
               return "UseGetBytesForBlobs";
            case 76:
               return "BlobTypeName";
            case 82:
               return "UseSetStringForClobs";
            case 84:
               return "Platform";
            case 90:
               return "SupportsLockingWithOuterJoin";
            case 92:
               return "SupportsNullTableForGetImportedKeys";
            case 94:
               return "LastGeneratedKeyQuery";
            case 98:
               return "SupportsDeferredConstraints";
            case 99:
               return "RealTypeName";
            case 100:
               return "RequiresAliasForSubselect";
            case 109:
               return "MaxIndexNameLength";
            case 112:
               return "VarbinaryTypeName";
            case 113:
               return "MaxTableNameLength";
            case 119:
               return "SupportsSelectStartIndex";
            case 125:
               return "TinyintTypeName";
            case 131:
               return "TimestampTypeName";
            case 135:
               return "AllSequencesFromOneSchemaSQL";
            case 136:
               return "SupportsSetFetchSize";
            case 137:
               return "AllSequencesSQL";
            case 138:
               return "NamedSequencesFromAllSchemasSQL";
            case 139:
               return "NamedSequenceFromOneSchemaSQL";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AllSequencesFromOneSchemaSQL")) {
            return 135;
         } else if (propName.equals("AllSequencesSQL")) {
            return 137;
         } else if (propName.equals("AllowsAliasInBulkClause")) {
            return 52;
         } else if (propName.equals("AutoAssignTypeName")) {
            return 57;
         } else if (propName.equals("BinaryTypeName")) {
            return 3;
         } else if (propName.equals("BitTypeName")) {
            return 45;
         } else if (propName.equals("BlobTypeName")) {
            return 76;
         } else if (propName.equals("ClobTypeName")) {
            return 4;
         } else if (propName.equals("DatePrecision")) {
            return 43;
         } else if (propName.equals("DoubleTypeName")) {
            return 39;
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
         } else if (propName.equals("MaxIndexNameLength")) {
            return 109;
         } else if (propName.equals("MaxTableNameLength")) {
            return 113;
         } else if (propName.equals("NamedSequenceFromOneSchemaSQL")) {
            return 139;
         } else if (propName.equals("NamedSequencesFromAllSchemasSQL")) {
            return 138;
         } else if (propName.equals("NextSequenceQuery")) {
            return 21;
         } else if (propName.equals("Platform")) {
            return 84;
         } else if (propName.equals("RangePosition")) {
            return 63;
         } else if (propName.equals("RealTypeName")) {
            return 99;
         } else if (propName.equals("RequiresAliasForSubselect")) {
            return 100;
         } else if (propName.equals("SchemaCase")) {
            return 34;
         } else if (propName.equals("SmallintTypeName")) {
            return 42;
         } else if (propName.equals("SupportsAlterTableWithDropColumn")) {
            return 27;
         } else if (propName.equals("SupportsAutoAssign")) {
            return 49;
         } else if (propName.equals("SupportsDeferredConstraints")) {
            return 98;
         } else if (propName.equals("SupportsLockingWithDistinctClause")) {
            return 5;
         } else if (propName.equals("SupportsLockingWithOuterJoin")) {
            return 90;
         } else if (propName.equals("SupportsNullTableForGetImportedKeys")) {
            return 92;
         } else if (propName.equals("SupportsSelectEndIndex")) {
            return 48;
         } else if (propName.equals("SupportsSelectStartIndex")) {
            return 119;
         } else if (propName.equals("SupportsSetFetchSize")) {
            return 136;
         } else if (propName.equals("TimestampTypeName")) {
            return 131;
         } else if (propName.equals("TinyintTypeName")) {
            return 125;
         } else if (propName.equals("UseGetBytesForBlobs")) {
            return 71;
         } else if (propName.equals("UseGetStringForClobs")) {
            return 40;
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
            if (this.bean.isAllSequencesFromOneSchemaSQLSet()) {
               buf.append("AllSequencesFromOneSchemaSQL");
               buf.append(String.valueOf(this.bean.getAllSequencesFromOneSchemaSQL()));
            }

            if (this.bean.isAllSequencesSQLSet()) {
               buf.append("AllSequencesSQL");
               buf.append(String.valueOf(this.bean.getAllSequencesSQL()));
            }

            if (this.bean.isAllowsAliasInBulkClauseSet()) {
               buf.append("AllowsAliasInBulkClause");
               buf.append(String.valueOf(this.bean.getAllowsAliasInBulkClause()));
            }

            if (this.bean.isAutoAssignTypeNameSet()) {
               buf.append("AutoAssignTypeName");
               buf.append(String.valueOf(this.bean.getAutoAssignTypeName()));
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

            if (this.bean.isDatePrecisionSet()) {
               buf.append("DatePrecision");
               buf.append(String.valueOf(this.bean.getDatePrecision()));
            }

            if (this.bean.isDoubleTypeNameSet()) {
               buf.append("DoubleTypeName");
               buf.append(String.valueOf(this.bean.getDoubleTypeName()));
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

            if (this.bean.isMaxIndexNameLengthSet()) {
               buf.append("MaxIndexNameLength");
               buf.append(String.valueOf(this.bean.getMaxIndexNameLength()));
            }

            if (this.bean.isMaxTableNameLengthSet()) {
               buf.append("MaxTableNameLength");
               buf.append(String.valueOf(this.bean.getMaxTableNameLength()));
            }

            if (this.bean.isNamedSequenceFromOneSchemaSQLSet()) {
               buf.append("NamedSequenceFromOneSchemaSQL");
               buf.append(String.valueOf(this.bean.getNamedSequenceFromOneSchemaSQL()));
            }

            if (this.bean.isNamedSequencesFromAllSchemasSQLSet()) {
               buf.append("NamedSequencesFromAllSchemasSQL");
               buf.append(String.valueOf(this.bean.getNamedSequencesFromAllSchemasSQL()));
            }

            if (this.bean.isNextSequenceQuerySet()) {
               buf.append("NextSequenceQuery");
               buf.append(String.valueOf(this.bean.getNextSequenceQuery()));
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

            if (this.bean.isRequiresAliasForSubselectSet()) {
               buf.append("RequiresAliasForSubselect");
               buf.append(String.valueOf(this.bean.getRequiresAliasForSubselect()));
            }

            if (this.bean.isSchemaCaseSet()) {
               buf.append("SchemaCase");
               buf.append(String.valueOf(this.bean.getSchemaCase()));
            }

            if (this.bean.isSmallintTypeNameSet()) {
               buf.append("SmallintTypeName");
               buf.append(String.valueOf(this.bean.getSmallintTypeName()));
            }

            if (this.bean.isSupportsAlterTableWithDropColumnSet()) {
               buf.append("SupportsAlterTableWithDropColumn");
               buf.append(String.valueOf(this.bean.getSupportsAlterTableWithDropColumn()));
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

            if (this.bean.isSupportsLockingWithOuterJoinSet()) {
               buf.append("SupportsLockingWithOuterJoin");
               buf.append(String.valueOf(this.bean.getSupportsLockingWithOuterJoin()));
            }

            if (this.bean.isSupportsNullTableForGetImportedKeysSet()) {
               buf.append("SupportsNullTableForGetImportedKeys");
               buf.append(String.valueOf(this.bean.getSupportsNullTableForGetImportedKeys()));
            }

            if (this.bean.isSupportsSelectEndIndexSet()) {
               buf.append("SupportsSelectEndIndex");
               buf.append(String.valueOf(this.bean.getSupportsSelectEndIndex()));
            }

            if (this.bean.isSupportsSelectStartIndexSet()) {
               buf.append("SupportsSelectStartIndex");
               buf.append(String.valueOf(this.bean.getSupportsSelectStartIndex()));
            }

            if (this.bean.isSupportsSetFetchSizeSet()) {
               buf.append("SupportsSetFetchSize");
               buf.append(String.valueOf(this.bean.getSupportsSetFetchSize()));
            }

            if (this.bean.isTimestampTypeNameSet()) {
               buf.append("TimestampTypeName");
               buf.append(String.valueOf(this.bean.getTimestampTypeName()));
            }

            if (this.bean.isTinyintTypeNameSet()) {
               buf.append("TinyintTypeName");
               buf.append(String.valueOf(this.bean.getTinyintTypeName()));
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
            PostgresDictionaryBeanImpl otherTyped = (PostgresDictionaryBeanImpl)other;
            this.computeDiff("AllSequencesFromOneSchemaSQL", this.bean.getAllSequencesFromOneSchemaSQL(), otherTyped.getAllSequencesFromOneSchemaSQL(), false);
            this.computeDiff("AllSequencesSQL", this.bean.getAllSequencesSQL(), otherTyped.getAllSequencesSQL(), false);
            this.computeDiff("AllowsAliasInBulkClause", this.bean.getAllowsAliasInBulkClause(), otherTyped.getAllowsAliasInBulkClause(), false);
            this.computeDiff("AutoAssignTypeName", this.bean.getAutoAssignTypeName(), otherTyped.getAutoAssignTypeName(), false);
            this.computeDiff("BinaryTypeName", this.bean.getBinaryTypeName(), otherTyped.getBinaryTypeName(), false);
            this.computeDiff("BitTypeName", this.bean.getBitTypeName(), otherTyped.getBitTypeName(), false);
            this.computeDiff("BlobTypeName", this.bean.getBlobTypeName(), otherTyped.getBlobTypeName(), false);
            this.computeDiff("ClobTypeName", this.bean.getClobTypeName(), otherTyped.getClobTypeName(), false);
            this.computeDiff("DatePrecision", this.bean.getDatePrecision(), otherTyped.getDatePrecision(), false);
            this.computeDiff("DoubleTypeName", this.bean.getDoubleTypeName(), otherTyped.getDoubleTypeName(), false);
            this.computeDiff("LastGeneratedKeyQuery", this.bean.getLastGeneratedKeyQuery(), otherTyped.getLastGeneratedKeyQuery(), false);
            this.computeDiff("LongVarbinaryTypeName", this.bean.getLongVarbinaryTypeName(), otherTyped.getLongVarbinaryTypeName(), false);
            this.computeDiff("LongVarcharTypeName", this.bean.getLongVarcharTypeName(), otherTyped.getLongVarcharTypeName(), false);
            this.computeDiff("MaxAutoAssignNameLength", this.bean.getMaxAutoAssignNameLength(), otherTyped.getMaxAutoAssignNameLength(), false);
            this.computeDiff("MaxColumnNameLength", this.bean.getMaxColumnNameLength(), otherTyped.getMaxColumnNameLength(), false);
            this.computeDiff("MaxConstraintNameLength", this.bean.getMaxConstraintNameLength(), otherTyped.getMaxConstraintNameLength(), false);
            this.computeDiff("MaxIndexNameLength", this.bean.getMaxIndexNameLength(), otherTyped.getMaxIndexNameLength(), false);
            this.computeDiff("MaxTableNameLength", this.bean.getMaxTableNameLength(), otherTyped.getMaxTableNameLength(), false);
            this.computeDiff("NamedSequenceFromOneSchemaSQL", this.bean.getNamedSequenceFromOneSchemaSQL(), otherTyped.getNamedSequenceFromOneSchemaSQL(), false);
            this.computeDiff("NamedSequencesFromAllSchemasSQL", this.bean.getNamedSequencesFromAllSchemasSQL(), otherTyped.getNamedSequencesFromAllSchemasSQL(), false);
            this.computeDiff("NextSequenceQuery", this.bean.getNextSequenceQuery(), otherTyped.getNextSequenceQuery(), false);
            this.computeDiff("Platform", this.bean.getPlatform(), otherTyped.getPlatform(), false);
            this.computeDiff("RangePosition", this.bean.getRangePosition(), otherTyped.getRangePosition(), false);
            this.computeDiff("RealTypeName", this.bean.getRealTypeName(), otherTyped.getRealTypeName(), false);
            this.computeDiff("RequiresAliasForSubselect", this.bean.getRequiresAliasForSubselect(), otherTyped.getRequiresAliasForSubselect(), false);
            this.computeDiff("SchemaCase", this.bean.getSchemaCase(), otherTyped.getSchemaCase(), false);
            this.computeDiff("SmallintTypeName", this.bean.getSmallintTypeName(), otherTyped.getSmallintTypeName(), false);
            this.computeDiff("SupportsAlterTableWithDropColumn", this.bean.getSupportsAlterTableWithDropColumn(), otherTyped.getSupportsAlterTableWithDropColumn(), false);
            this.computeDiff("SupportsAutoAssign", this.bean.getSupportsAutoAssign(), otherTyped.getSupportsAutoAssign(), false);
            this.computeDiff("SupportsDeferredConstraints", this.bean.getSupportsDeferredConstraints(), otherTyped.getSupportsDeferredConstraints(), false);
            this.computeDiff("SupportsLockingWithDistinctClause", this.bean.getSupportsLockingWithDistinctClause(), otherTyped.getSupportsLockingWithDistinctClause(), false);
            this.computeDiff("SupportsLockingWithOuterJoin", this.bean.getSupportsLockingWithOuterJoin(), otherTyped.getSupportsLockingWithOuterJoin(), false);
            this.computeDiff("SupportsNullTableForGetImportedKeys", this.bean.getSupportsNullTableForGetImportedKeys(), otherTyped.getSupportsNullTableForGetImportedKeys(), false);
            this.computeDiff("SupportsSelectEndIndex", this.bean.getSupportsSelectEndIndex(), otherTyped.getSupportsSelectEndIndex(), false);
            this.computeDiff("SupportsSelectStartIndex", this.bean.getSupportsSelectStartIndex(), otherTyped.getSupportsSelectStartIndex(), false);
            this.computeDiff("SupportsSetFetchSize", this.bean.getSupportsSetFetchSize(), otherTyped.getSupportsSetFetchSize(), false);
            this.computeDiff("TimestampTypeName", this.bean.getTimestampTypeName(), otherTyped.getTimestampTypeName(), false);
            this.computeDiff("TinyintTypeName", this.bean.getTinyintTypeName(), otherTyped.getTinyintTypeName(), false);
            this.computeDiff("UseGetBytesForBlobs", this.bean.getUseGetBytesForBlobs(), otherTyped.getUseGetBytesForBlobs(), false);
            this.computeDiff("UseGetStringForClobs", this.bean.getUseGetStringForClobs(), otherTyped.getUseGetStringForClobs(), false);
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
            PostgresDictionaryBeanImpl original = (PostgresDictionaryBeanImpl)event.getSourceBean();
            PostgresDictionaryBeanImpl proposed = (PostgresDictionaryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AllSequencesFromOneSchemaSQL")) {
                  original.setAllSequencesFromOneSchemaSQL(proposed.getAllSequencesFromOneSchemaSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 135);
               } else if (prop.equals("AllSequencesSQL")) {
                  original.setAllSequencesSQL(proposed.getAllSequencesSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 137);
               } else if (prop.equals("AllowsAliasInBulkClause")) {
                  original.setAllowsAliasInBulkClause(proposed.getAllowsAliasInBulkClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 52);
               } else if (prop.equals("AutoAssignTypeName")) {
                  original.setAutoAssignTypeName(proposed.getAutoAssignTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 57);
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
               } else if (prop.equals("DatePrecision")) {
                  original.setDatePrecision(proposed.getDatePrecision());
                  original._conditionalUnset(update.isUnsetUpdate(), 43);
               } else if (prop.equals("DoubleTypeName")) {
                  original.setDoubleTypeName(proposed.getDoubleTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
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
               } else if (prop.equals("MaxIndexNameLength")) {
                  original.setMaxIndexNameLength(proposed.getMaxIndexNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 109);
               } else if (prop.equals("MaxTableNameLength")) {
                  original.setMaxTableNameLength(proposed.getMaxTableNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 113);
               } else if (prop.equals("NamedSequenceFromOneSchemaSQL")) {
                  original.setNamedSequenceFromOneSchemaSQL(proposed.getNamedSequenceFromOneSchemaSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 139);
               } else if (prop.equals("NamedSequencesFromAllSchemasSQL")) {
                  original.setNamedSequencesFromAllSchemasSQL(proposed.getNamedSequencesFromAllSchemasSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 138);
               } else if (prop.equals("NextSequenceQuery")) {
                  original.setNextSequenceQuery(proposed.getNextSequenceQuery());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("Platform")) {
                  original.setPlatform(proposed.getPlatform());
                  original._conditionalUnset(update.isUnsetUpdate(), 84);
               } else if (prop.equals("RangePosition")) {
                  original.setRangePosition(proposed.getRangePosition());
                  original._conditionalUnset(update.isUnsetUpdate(), 63);
               } else if (prop.equals("RealTypeName")) {
                  original.setRealTypeName(proposed.getRealTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 99);
               } else if (prop.equals("RequiresAliasForSubselect")) {
                  original.setRequiresAliasForSubselect(proposed.getRequiresAliasForSubselect());
                  original._conditionalUnset(update.isUnsetUpdate(), 100);
               } else if (prop.equals("SchemaCase")) {
                  original.setSchemaCase(proposed.getSchemaCase());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("SmallintTypeName")) {
                  original.setSmallintTypeName(proposed.getSmallintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (prop.equals("SupportsAlterTableWithDropColumn")) {
                  original.setSupportsAlterTableWithDropColumn(proposed.getSupportsAlterTableWithDropColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("SupportsAutoAssign")) {
                  original.setSupportsAutoAssign(proposed.getSupportsAutoAssign());
                  original._conditionalUnset(update.isUnsetUpdate(), 49);
               } else if (prop.equals("SupportsDeferredConstraints")) {
                  original.setSupportsDeferredConstraints(proposed.getSupportsDeferredConstraints());
                  original._conditionalUnset(update.isUnsetUpdate(), 98);
               } else if (prop.equals("SupportsLockingWithDistinctClause")) {
                  original.setSupportsLockingWithDistinctClause(proposed.getSupportsLockingWithDistinctClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("SupportsLockingWithOuterJoin")) {
                  original.setSupportsLockingWithOuterJoin(proposed.getSupportsLockingWithOuterJoin());
                  original._conditionalUnset(update.isUnsetUpdate(), 90);
               } else if (prop.equals("SupportsNullTableForGetImportedKeys")) {
                  original.setSupportsNullTableForGetImportedKeys(proposed.getSupportsNullTableForGetImportedKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 92);
               } else if (prop.equals("SupportsSelectEndIndex")) {
                  original.setSupportsSelectEndIndex(proposed.getSupportsSelectEndIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 48);
               } else if (prop.equals("SupportsSelectStartIndex")) {
                  original.setSupportsSelectStartIndex(proposed.getSupportsSelectStartIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 119);
               } else if (prop.equals("SupportsSetFetchSize")) {
                  original.setSupportsSetFetchSize(proposed.getSupportsSetFetchSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 136);
               } else if (prop.equals("TimestampTypeName")) {
                  original.setTimestampTypeName(proposed.getTimestampTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 131);
               } else if (prop.equals("TinyintTypeName")) {
                  original.setTinyintTypeName(proposed.getTinyintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 125);
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
            PostgresDictionaryBeanImpl copy = (PostgresDictionaryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AllSequencesFromOneSchemaSQL")) && this.bean.isAllSequencesFromOneSchemaSQLSet()) {
               copy.setAllSequencesFromOneSchemaSQL(this.bean.getAllSequencesFromOneSchemaSQL());
            }

            if ((excludeProps == null || !excludeProps.contains("AllSequencesSQL")) && this.bean.isAllSequencesSQLSet()) {
               copy.setAllSequencesSQL(this.bean.getAllSequencesSQL());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowsAliasInBulkClause")) && this.bean.isAllowsAliasInBulkClauseSet()) {
               copy.setAllowsAliasInBulkClause(this.bean.getAllowsAliasInBulkClause());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoAssignTypeName")) && this.bean.isAutoAssignTypeNameSet()) {
               copy.setAutoAssignTypeName(this.bean.getAutoAssignTypeName());
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

            if ((excludeProps == null || !excludeProps.contains("DatePrecision")) && this.bean.isDatePrecisionSet()) {
               copy.setDatePrecision(this.bean.getDatePrecision());
            }

            if ((excludeProps == null || !excludeProps.contains("DoubleTypeName")) && this.bean.isDoubleTypeNameSet()) {
               copy.setDoubleTypeName(this.bean.getDoubleTypeName());
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

            if ((excludeProps == null || !excludeProps.contains("MaxIndexNameLength")) && this.bean.isMaxIndexNameLengthSet()) {
               copy.setMaxIndexNameLength(this.bean.getMaxIndexNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxTableNameLength")) && this.bean.isMaxTableNameLengthSet()) {
               copy.setMaxTableNameLength(this.bean.getMaxTableNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("NamedSequenceFromOneSchemaSQL")) && this.bean.isNamedSequenceFromOneSchemaSQLSet()) {
               copy.setNamedSequenceFromOneSchemaSQL(this.bean.getNamedSequenceFromOneSchemaSQL());
            }

            if ((excludeProps == null || !excludeProps.contains("NamedSequencesFromAllSchemasSQL")) && this.bean.isNamedSequencesFromAllSchemasSQLSet()) {
               copy.setNamedSequencesFromAllSchemasSQL(this.bean.getNamedSequencesFromAllSchemasSQL());
            }

            if ((excludeProps == null || !excludeProps.contains("NextSequenceQuery")) && this.bean.isNextSequenceQuerySet()) {
               copy.setNextSequenceQuery(this.bean.getNextSequenceQuery());
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

            if ((excludeProps == null || !excludeProps.contains("RequiresAliasForSubselect")) && this.bean.isRequiresAliasForSubselectSet()) {
               copy.setRequiresAliasForSubselect(this.bean.getRequiresAliasForSubselect());
            }

            if ((excludeProps == null || !excludeProps.contains("SchemaCase")) && this.bean.isSchemaCaseSet()) {
               copy.setSchemaCase(this.bean.getSchemaCase());
            }

            if ((excludeProps == null || !excludeProps.contains("SmallintTypeName")) && this.bean.isSmallintTypeNameSet()) {
               copy.setSmallintTypeName(this.bean.getSmallintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsAlterTableWithDropColumn")) && this.bean.isSupportsAlterTableWithDropColumnSet()) {
               copy.setSupportsAlterTableWithDropColumn(this.bean.getSupportsAlterTableWithDropColumn());
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

            if ((excludeProps == null || !excludeProps.contains("SupportsLockingWithOuterJoin")) && this.bean.isSupportsLockingWithOuterJoinSet()) {
               copy.setSupportsLockingWithOuterJoin(this.bean.getSupportsLockingWithOuterJoin());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsNullTableForGetImportedKeys")) && this.bean.isSupportsNullTableForGetImportedKeysSet()) {
               copy.setSupportsNullTableForGetImportedKeys(this.bean.getSupportsNullTableForGetImportedKeys());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSelectEndIndex")) && this.bean.isSupportsSelectEndIndexSet()) {
               copy.setSupportsSelectEndIndex(this.bean.getSupportsSelectEndIndex());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSelectStartIndex")) && this.bean.isSupportsSelectStartIndexSet()) {
               copy.setSupportsSelectStartIndex(this.bean.getSupportsSelectStartIndex());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSetFetchSize")) && this.bean.isSupportsSetFetchSizeSet()) {
               copy.setSupportsSetFetchSize(this.bean.getSupportsSetFetchSize());
            }

            if ((excludeProps == null || !excludeProps.contains("TimestampTypeName")) && this.bean.isTimestampTypeNameSet()) {
               copy.setTimestampTypeName(this.bean.getTimestampTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("TinyintTypeName")) && this.bean.isTinyintTypeNameSet()) {
               copy.setTinyintTypeName(this.bean.getTinyintTypeName());
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
