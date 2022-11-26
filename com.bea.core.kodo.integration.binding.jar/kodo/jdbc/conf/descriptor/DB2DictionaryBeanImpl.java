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

public class DB2DictionaryBeanImpl extends BuiltInDBDictionaryBeanImpl implements DB2DictionaryBean, Serializable {
   private String _AutoAssignClause;
   private String _BinaryTypeName;
   private String _BitTypeName;
   private String _ClobTypeName;
   private String _CrossJoinClause;
   private String _ForUpdateClause;
   private String _LastGeneratedKeyQuery;
   private String _LongVarbinaryTypeName;
   private String _LongVarcharTypeName;
   private int _MaxColumnNameLength;
   private int _MaxConstraintNameLength;
   private int _MaxIndexNameLength;
   private String _NextSequenceQuery;
   private String _NumericTypeName;
   private String _Platform;
   private boolean _RequiresAliasForSubselect;
   private boolean _RequiresAutoCommitForMetaData;
   private boolean _RequiresConditionForCrossJoin;
   private String _SmallintTypeName;
   private String _StringLengthFunction;
   private boolean _SupportsAlterTableWithDropColumn;
   private boolean _SupportsAutoAssign;
   private boolean _SupportsDefaultDeleteAction;
   private boolean _SupportsDeferredConstraints;
   private boolean _SupportsLockingWithDistinctClause;
   private boolean _SupportsLockingWithInnerJoin;
   private boolean _SupportsLockingWithMultipleTables;
   private boolean _SupportsLockingWithOrderClause;
   private boolean _SupportsLockingWithOuterJoin;
   private boolean _SupportsLockingWithSelectRange;
   private boolean _SupportsNullTableForGetColumns;
   private boolean _SupportsSelectEndIndex;
   private String _TinyintTypeName;
   private String _ToLowerCaseFunction;
   private String _ToUpperCaseFunction;
   private String _TrimBothFunction;
   private String _TrimLeadingFunction;
   private String _TrimTrailingFunction;
   private String _ValidationSQL;
   private String _VarbinaryTypeName;
   private static SchemaHelper2 _schemaHelper;

   public DB2DictionaryBeanImpl() {
      this._initializeProperty(-1);
   }

   public DB2DictionaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DB2DictionaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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
         idx = 65;
      }

      try {
         switch (idx) {
            case 65:
               this._AutoAssignClause = "GENERATED BY DEFAULT AS IDENTITY";
               if (initOne) {
                  break;
               }
            case 3:
               this._BinaryTypeName = "BLOB(1M)";
               if (initOne) {
                  break;
               }
            case 45:
               this._BitTypeName = "SMALLINT";
               if (initOne) {
                  break;
               }
            case 4:
               this._ClobTypeName = "CLOB(1M)";
               if (initOne) {
                  break;
               }
            case 23:
               this._CrossJoinClause = "JOIN";
               if (initOne) {
                  break;
               }
            case 77:
               this._ForUpdateClause = "FOR UPDATE WITH RR";
               if (initOne) {
                  break;
               }
            case 94:
               this._LastGeneratedKeyQuery = "VALUES(IDENTITY_VAL_LOCAL())";
               if (initOne) {
                  break;
               }
            case 16:
               this._LongVarbinaryTypeName = "BLOB(1M)";
               if (initOne) {
                  break;
               }
            case 22:
               this._LongVarcharTypeName = "LONG VARCHAR";
               if (initOne) {
                  break;
               }
            case 38:
               this._MaxColumnNameLength = 30;
               if (initOne) {
                  break;
               }
            case 12:
               this._MaxConstraintNameLength = 18;
               if (initOne) {
                  break;
               }
            case 109:
               this._MaxIndexNameLength = 18;
               if (initOne) {
                  break;
               }
            case 21:
               this._NextSequenceQuery = "VALUES NEXTVAL FOR {0}";
               if (initOne) {
                  break;
               }
            case 73:
               this._NumericTypeName = "DOUBLE";
               if (initOne) {
                  break;
               }
            case 84:
               this._Platform = "DB2";
               if (initOne) {
                  break;
               }
            case 100:
               this._RequiresAliasForSubselect = true;
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
            case 42:
               this._SmallintTypeName = "SMALLINT";
               if (initOne) {
                  break;
               }
            case 15:
               this._StringLengthFunction = "LENGTH(CAST({0} AS VARCHAR(1000)))";
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
            case 20:
               this._SupportsDefaultDeleteAction = false;
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
            case 105:
               this._SupportsLockingWithInnerJoin = false;
               if (initOne) {
                  break;
               }
            case 37:
               this._SupportsLockingWithMultipleTables = false;
               if (initOne) {
                  break;
               }
            case 83:
               this._SupportsLockingWithOrderClause = false;
               if (initOne) {
                  break;
               }
            case 90:
               this._SupportsLockingWithOuterJoin = false;
               if (initOne) {
                  break;
               }
            case 103:
               this._SupportsLockingWithSelectRange = false;
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
            case 125:
               this._TinyintTypeName = "SMALLINT";
               if (initOne) {
                  break;
               }
            case 120:
               this._ToLowerCaseFunction = "LOWER(CAST({0} AS VARCHAR(1000)))";
               if (initOne) {
                  break;
               }
            case 47:
               this._ToUpperCaseFunction = "UPPER(CAST({0} AS VARCHAR(1000)))";
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
            case 60:
               this._ValidationSQL = "SELECT DISTINCT(CURRENT TIMESTAMP) FROM SYSIBM.SYSTABLES";
               if (initOne) {
                  break;
               }
            case 112:
               this._VarbinaryTypeName = "BLOB(1M)";
               if (initOne) {
                  break;
               }
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 17:
            case 18:
            case 24:
            case 25:
            case 26:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 39:
            case 40:
            case 41:
            case 43:
            case 44:
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
            case 61:
            case 62:
            case 63:
            case 64:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 74:
            case 75:
            case 76:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 91:
            case 92:
            case 93:
            case 95:
            case 96:
            case 97:
            case 99:
            case 101:
            case 104:
            case 106:
            case 107:
            case 108:
            case 110:
            case 111:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 119:
            case 121:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
            case 128:
            case 129:
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
            case 11:
            case 12:
            case 15:
            case 23:
            case 27:
            case 31:
            case 36:
            default:
               break;
            case 13:
               if (s.equals("bit-type-name")) {
                  return 45;
               }
               break;
            case 14:
               if (s.equals("clob-type-name")) {
                  return 4;
               }

               if (s.equals("validation-sql")) {
                  return 60;
               }
               break;
            case 16:
               if (s.equals("binary-type-name")) {
                  return 3;
               }
               break;
            case 17:
               if (s.equals("cross-join-clause")) {
                  return 23;
               }

               if (s.equals("for-update-clause")) {
                  return 77;
               }

               if (s.equals("numeric-type-name")) {
                  return 73;
               }

               if (s.equals("tinyint-type-name")) {
                  return 125;
               }
               break;
            case 18:
               if (s.equals("auto-assign-clause")) {
                  return 65;
               }

               if (s.equals("smallint-type-name")) {
                  return 42;
               }

               if (s.equals("trim-both-function")) {
                  return 118;
               }
               break;
            case 19:
               if (s.equals("next-sequence-query")) {
                  return 21;
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
               if (s.equals("max-index-name-length")) {
                  return 109;
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

               if (s.equals("string-length-function")) {
                  return 15;
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
            case 24:
               if (s.equals("last-generated-key-query")) {
                  return 94;
               }

               if (s.equals("long-varbinary-type-name")) {
                  return 16;
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
            case 30:
               if (s.equals("supports-default-delete-action")) {
                  return 20;
               }
               break;
            case 32:
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
            case 11:
            case 13:
            case 14:
            case 17:
            case 18:
            case 24:
            case 25:
            case 26:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 39:
            case 40:
            case 41:
            case 43:
            case 44:
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
            case 61:
            case 62:
            case 63:
            case 64:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 74:
            case 75:
            case 76:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 91:
            case 92:
            case 93:
            case 95:
            case 96:
            case 97:
            case 99:
            case 101:
            case 104:
            case 106:
            case 107:
            case 108:
            case 110:
            case 111:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 119:
            case 121:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
            case 128:
            case 129:
            default:
               return super.getElementName(propIndex);
            case 12:
               return "max-constraint-name-length";
            case 15:
               return "string-length-function";
            case 16:
               return "long-varbinary-type-name";
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
            case 27:
               return "supports-alter-table-with-drop-column";
            case 29:
               return "requires-condition-for-cross-join";
            case 37:
               return "supports-locking-with-multiple-tables";
            case 38:
               return "max-column-name-length";
            case 42:
               return "smallint-type-name";
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
            case 60:
               return "validation-sql";
            case 65:
               return "auto-assign-clause";
            case 73:
               return "numeric-type-name";
            case 77:
               return "for-update-clause";
            case 83:
               return "supports-locking-with-order-clause";
            case 84:
               return "platform";
            case 90:
               return "supports-locking-with-outer-join";
            case 94:
               return "last-generated-key-query";
            case 98:
               return "supports-deferred-constraints";
            case 100:
               return "requires-alias-for-subselect";
            case 102:
               return "trim-trailing-function";
            case 103:
               return "supports-locking-with-select-range";
            case 105:
               return "supports-locking-with-inner-join";
            case 109:
               return "max-index-name-length";
            case 112:
               return "varbinary-type-name";
            case 118:
               return "trim-both-function";
            case 120:
               return "to-lower-case-function";
            case 125:
               return "tinyint-type-name";
            case 130:
               return "requires-auto-commit-for-meta-data";
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

   protected static class Helper extends BuiltInDBDictionaryBeanImpl.Helper {
      private DB2DictionaryBeanImpl bean;

      protected Helper(DB2DictionaryBeanImpl bean) {
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
            case 11:
            case 13:
            case 14:
            case 17:
            case 18:
            case 24:
            case 25:
            case 26:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 39:
            case 40:
            case 41:
            case 43:
            case 44:
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
            case 61:
            case 62:
            case 63:
            case 64:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 74:
            case 75:
            case 76:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 91:
            case 92:
            case 93:
            case 95:
            case 96:
            case 97:
            case 99:
            case 101:
            case 104:
            case 106:
            case 107:
            case 108:
            case 110:
            case 111:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 119:
            case 121:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
            case 128:
            case 129:
            default:
               return super.getPropertyName(propIndex);
            case 12:
               return "MaxConstraintNameLength";
            case 15:
               return "StringLengthFunction";
            case 16:
               return "LongVarbinaryTypeName";
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
            case 27:
               return "SupportsAlterTableWithDropColumn";
            case 29:
               return "RequiresConditionForCrossJoin";
            case 37:
               return "SupportsLockingWithMultipleTables";
            case 38:
               return "MaxColumnNameLength";
            case 42:
               return "SmallintTypeName";
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
            case 60:
               return "ValidationSQL";
            case 65:
               return "AutoAssignClause";
            case 73:
               return "NumericTypeName";
            case 77:
               return "ForUpdateClause";
            case 83:
               return "SupportsLockingWithOrderClause";
            case 84:
               return "Platform";
            case 90:
               return "SupportsLockingWithOuterJoin";
            case 94:
               return "LastGeneratedKeyQuery";
            case 98:
               return "SupportsDeferredConstraints";
            case 100:
               return "RequiresAliasForSubselect";
            case 102:
               return "TrimTrailingFunction";
            case 103:
               return "SupportsLockingWithSelectRange";
            case 105:
               return "SupportsLockingWithInnerJoin";
            case 109:
               return "MaxIndexNameLength";
            case 112:
               return "VarbinaryTypeName";
            case 118:
               return "TrimBothFunction";
            case 120:
               return "ToLowerCaseFunction";
            case 125:
               return "TinyintTypeName";
            case 130:
               return "RequiresAutoCommitForMetaData";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AutoAssignClause")) {
            return 65;
         } else if (propName.equals("BinaryTypeName")) {
            return 3;
         } else if (propName.equals("BitTypeName")) {
            return 45;
         } else if (propName.equals("ClobTypeName")) {
            return 4;
         } else if (propName.equals("CrossJoinClause")) {
            return 23;
         } else if (propName.equals("ForUpdateClause")) {
            return 77;
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
         } else if (propName.equals("NextSequenceQuery")) {
            return 21;
         } else if (propName.equals("NumericTypeName")) {
            return 73;
         } else if (propName.equals("Platform")) {
            return 84;
         } else if (propName.equals("RequiresAliasForSubselect")) {
            return 100;
         } else if (propName.equals("RequiresAutoCommitForMetaData")) {
            return 130;
         } else if (propName.equals("RequiresConditionForCrossJoin")) {
            return 29;
         } else if (propName.equals("SmallintTypeName")) {
            return 42;
         } else if (propName.equals("StringLengthFunction")) {
            return 15;
         } else if (propName.equals("SupportsAlterTableWithDropColumn")) {
            return 27;
         } else if (propName.equals("SupportsAutoAssign")) {
            return 49;
         } else if (propName.equals("SupportsDefaultDeleteAction")) {
            return 20;
         } else if (propName.equals("SupportsDeferredConstraints")) {
            return 98;
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
         } else if (propName.equals("SupportsNullTableForGetColumns")) {
            return 46;
         } else if (propName.equals("SupportsSelectEndIndex")) {
            return 48;
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
         } else if (propName.equals("ValidationSQL")) {
            return 60;
         } else {
            return propName.equals("VarbinaryTypeName") ? 112 : super.getPropertyIndex(propName);
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
            if (this.bean.isAutoAssignClauseSet()) {
               buf.append("AutoAssignClause");
               buf.append(String.valueOf(this.bean.getAutoAssignClause()));
            }

            if (this.bean.isBinaryTypeNameSet()) {
               buf.append("BinaryTypeName");
               buf.append(String.valueOf(this.bean.getBinaryTypeName()));
            }

            if (this.bean.isBitTypeNameSet()) {
               buf.append("BitTypeName");
               buf.append(String.valueOf(this.bean.getBitTypeName()));
            }

            if (this.bean.isClobTypeNameSet()) {
               buf.append("ClobTypeName");
               buf.append(String.valueOf(this.bean.getClobTypeName()));
            }

            if (this.bean.isCrossJoinClauseSet()) {
               buf.append("CrossJoinClause");
               buf.append(String.valueOf(this.bean.getCrossJoinClause()));
            }

            if (this.bean.isForUpdateClauseSet()) {
               buf.append("ForUpdateClause");
               buf.append(String.valueOf(this.bean.getForUpdateClause()));
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

            if (this.bean.isNextSequenceQuerySet()) {
               buf.append("NextSequenceQuery");
               buf.append(String.valueOf(this.bean.getNextSequenceQuery()));
            }

            if (this.bean.isNumericTypeNameSet()) {
               buf.append("NumericTypeName");
               buf.append(String.valueOf(this.bean.getNumericTypeName()));
            }

            if (this.bean.isPlatformSet()) {
               buf.append("Platform");
               buf.append(String.valueOf(this.bean.getPlatform()));
            }

            if (this.bean.isRequiresAliasForSubselectSet()) {
               buf.append("RequiresAliasForSubselect");
               buf.append(String.valueOf(this.bean.getRequiresAliasForSubselect()));
            }

            if (this.bean.isRequiresAutoCommitForMetaDataSet()) {
               buf.append("RequiresAutoCommitForMetaData");
               buf.append(String.valueOf(this.bean.getRequiresAutoCommitForMetaData()));
            }

            if (this.bean.isRequiresConditionForCrossJoinSet()) {
               buf.append("RequiresConditionForCrossJoin");
               buf.append(String.valueOf(this.bean.getRequiresConditionForCrossJoin()));
            }

            if (this.bean.isSmallintTypeNameSet()) {
               buf.append("SmallintTypeName");
               buf.append(String.valueOf(this.bean.getSmallintTypeName()));
            }

            if (this.bean.isStringLengthFunctionSet()) {
               buf.append("StringLengthFunction");
               buf.append(String.valueOf(this.bean.getStringLengthFunction()));
            }

            if (this.bean.isSupportsAlterTableWithDropColumnSet()) {
               buf.append("SupportsAlterTableWithDropColumn");
               buf.append(String.valueOf(this.bean.getSupportsAlterTableWithDropColumn()));
            }

            if (this.bean.isSupportsAutoAssignSet()) {
               buf.append("SupportsAutoAssign");
               buf.append(String.valueOf(this.bean.getSupportsAutoAssign()));
            }

            if (this.bean.isSupportsDefaultDeleteActionSet()) {
               buf.append("SupportsDefaultDeleteAction");
               buf.append(String.valueOf(this.bean.getSupportsDefaultDeleteAction()));
            }

            if (this.bean.isSupportsDeferredConstraintsSet()) {
               buf.append("SupportsDeferredConstraints");
               buf.append(String.valueOf(this.bean.getSupportsDeferredConstraints()));
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

            if (this.bean.isSupportsNullTableForGetColumnsSet()) {
               buf.append("SupportsNullTableForGetColumns");
               buf.append(String.valueOf(this.bean.getSupportsNullTableForGetColumns()));
            }

            if (this.bean.isSupportsSelectEndIndexSet()) {
               buf.append("SupportsSelectEndIndex");
               buf.append(String.valueOf(this.bean.getSupportsSelectEndIndex()));
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

            if (this.bean.isValidationSQLSet()) {
               buf.append("ValidationSQL");
               buf.append(String.valueOf(this.bean.getValidationSQL()));
            }

            if (this.bean.isVarbinaryTypeNameSet()) {
               buf.append("VarbinaryTypeName");
               buf.append(String.valueOf(this.bean.getVarbinaryTypeName()));
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
            DB2DictionaryBeanImpl otherTyped = (DB2DictionaryBeanImpl)other;
            this.computeDiff("AutoAssignClause", this.bean.getAutoAssignClause(), otherTyped.getAutoAssignClause(), false);
            this.computeDiff("BinaryTypeName", this.bean.getBinaryTypeName(), otherTyped.getBinaryTypeName(), false);
            this.computeDiff("BitTypeName", this.bean.getBitTypeName(), otherTyped.getBitTypeName(), false);
            this.computeDiff("ClobTypeName", this.bean.getClobTypeName(), otherTyped.getClobTypeName(), false);
            this.computeDiff("CrossJoinClause", this.bean.getCrossJoinClause(), otherTyped.getCrossJoinClause(), false);
            this.computeDiff("ForUpdateClause", this.bean.getForUpdateClause(), otherTyped.getForUpdateClause(), false);
            this.computeDiff("LastGeneratedKeyQuery", this.bean.getLastGeneratedKeyQuery(), otherTyped.getLastGeneratedKeyQuery(), false);
            this.computeDiff("LongVarbinaryTypeName", this.bean.getLongVarbinaryTypeName(), otherTyped.getLongVarbinaryTypeName(), false);
            this.computeDiff("LongVarcharTypeName", this.bean.getLongVarcharTypeName(), otherTyped.getLongVarcharTypeName(), false);
            this.computeDiff("MaxColumnNameLength", this.bean.getMaxColumnNameLength(), otherTyped.getMaxColumnNameLength(), false);
            this.computeDiff("MaxConstraintNameLength", this.bean.getMaxConstraintNameLength(), otherTyped.getMaxConstraintNameLength(), false);
            this.computeDiff("MaxIndexNameLength", this.bean.getMaxIndexNameLength(), otherTyped.getMaxIndexNameLength(), false);
            this.computeDiff("NextSequenceQuery", this.bean.getNextSequenceQuery(), otherTyped.getNextSequenceQuery(), false);
            this.computeDiff("NumericTypeName", this.bean.getNumericTypeName(), otherTyped.getNumericTypeName(), false);
            this.computeDiff("Platform", this.bean.getPlatform(), otherTyped.getPlatform(), false);
            this.computeDiff("RequiresAliasForSubselect", this.bean.getRequiresAliasForSubselect(), otherTyped.getRequiresAliasForSubselect(), false);
            this.computeDiff("RequiresAutoCommitForMetaData", this.bean.getRequiresAutoCommitForMetaData(), otherTyped.getRequiresAutoCommitForMetaData(), false);
            this.computeDiff("RequiresConditionForCrossJoin", this.bean.getRequiresConditionForCrossJoin(), otherTyped.getRequiresConditionForCrossJoin(), false);
            this.computeDiff("SmallintTypeName", this.bean.getSmallintTypeName(), otherTyped.getSmallintTypeName(), false);
            this.computeDiff("StringLengthFunction", this.bean.getStringLengthFunction(), otherTyped.getStringLengthFunction(), false);
            this.computeDiff("SupportsAlterTableWithDropColumn", this.bean.getSupportsAlterTableWithDropColumn(), otherTyped.getSupportsAlterTableWithDropColumn(), false);
            this.computeDiff("SupportsAutoAssign", this.bean.getSupportsAutoAssign(), otherTyped.getSupportsAutoAssign(), false);
            this.computeDiff("SupportsDefaultDeleteAction", this.bean.getSupportsDefaultDeleteAction(), otherTyped.getSupportsDefaultDeleteAction(), false);
            this.computeDiff("SupportsDeferredConstraints", this.bean.getSupportsDeferredConstraints(), otherTyped.getSupportsDeferredConstraints(), false);
            this.computeDiff("SupportsLockingWithDistinctClause", this.bean.getSupportsLockingWithDistinctClause(), otherTyped.getSupportsLockingWithDistinctClause(), false);
            this.computeDiff("SupportsLockingWithInnerJoin", this.bean.getSupportsLockingWithInnerJoin(), otherTyped.getSupportsLockingWithInnerJoin(), false);
            this.computeDiff("SupportsLockingWithMultipleTables", this.bean.getSupportsLockingWithMultipleTables(), otherTyped.getSupportsLockingWithMultipleTables(), false);
            this.computeDiff("SupportsLockingWithOrderClause", this.bean.getSupportsLockingWithOrderClause(), otherTyped.getSupportsLockingWithOrderClause(), false);
            this.computeDiff("SupportsLockingWithOuterJoin", this.bean.getSupportsLockingWithOuterJoin(), otherTyped.getSupportsLockingWithOuterJoin(), false);
            this.computeDiff("SupportsLockingWithSelectRange", this.bean.getSupportsLockingWithSelectRange(), otherTyped.getSupportsLockingWithSelectRange(), false);
            this.computeDiff("SupportsNullTableForGetColumns", this.bean.getSupportsNullTableForGetColumns(), otherTyped.getSupportsNullTableForGetColumns(), false);
            this.computeDiff("SupportsSelectEndIndex", this.bean.getSupportsSelectEndIndex(), otherTyped.getSupportsSelectEndIndex(), false);
            this.computeDiff("TinyintTypeName", this.bean.getTinyintTypeName(), otherTyped.getTinyintTypeName(), false);
            this.computeDiff("ToLowerCaseFunction", this.bean.getToLowerCaseFunction(), otherTyped.getToLowerCaseFunction(), false);
            this.computeDiff("ToUpperCaseFunction", this.bean.getToUpperCaseFunction(), otherTyped.getToUpperCaseFunction(), false);
            this.computeDiff("TrimBothFunction", this.bean.getTrimBothFunction(), otherTyped.getTrimBothFunction(), false);
            this.computeDiff("TrimLeadingFunction", this.bean.getTrimLeadingFunction(), otherTyped.getTrimLeadingFunction(), false);
            this.computeDiff("TrimTrailingFunction", this.bean.getTrimTrailingFunction(), otherTyped.getTrimTrailingFunction(), false);
            this.computeDiff("ValidationSQL", this.bean.getValidationSQL(), otherTyped.getValidationSQL(), false);
            this.computeDiff("VarbinaryTypeName", this.bean.getVarbinaryTypeName(), otherTyped.getVarbinaryTypeName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DB2DictionaryBeanImpl original = (DB2DictionaryBeanImpl)event.getSourceBean();
            DB2DictionaryBeanImpl proposed = (DB2DictionaryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AutoAssignClause")) {
                  original.setAutoAssignClause(proposed.getAutoAssignClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 65);
               } else if (prop.equals("BinaryTypeName")) {
                  original.setBinaryTypeName(proposed.getBinaryTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("BitTypeName")) {
                  original.setBitTypeName(proposed.getBitTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (prop.equals("ClobTypeName")) {
                  original.setClobTypeName(proposed.getClobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("CrossJoinClause")) {
                  original.setCrossJoinClause(proposed.getCrossJoinClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("ForUpdateClause")) {
                  original.setForUpdateClause(proposed.getForUpdateClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 77);
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
               } else if (prop.equals("NextSequenceQuery")) {
                  original.setNextSequenceQuery(proposed.getNextSequenceQuery());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("NumericTypeName")) {
                  original.setNumericTypeName(proposed.getNumericTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 73);
               } else if (prop.equals("Platform")) {
                  original.setPlatform(proposed.getPlatform());
                  original._conditionalUnset(update.isUnsetUpdate(), 84);
               } else if (prop.equals("RequiresAliasForSubselect")) {
                  original.setRequiresAliasForSubselect(proposed.getRequiresAliasForSubselect());
                  original._conditionalUnset(update.isUnsetUpdate(), 100);
               } else if (prop.equals("RequiresAutoCommitForMetaData")) {
                  original.setRequiresAutoCommitForMetaData(proposed.getRequiresAutoCommitForMetaData());
                  original._conditionalUnset(update.isUnsetUpdate(), 130);
               } else if (prop.equals("RequiresConditionForCrossJoin")) {
                  original.setRequiresConditionForCrossJoin(proposed.getRequiresConditionForCrossJoin());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("SmallintTypeName")) {
                  original.setSmallintTypeName(proposed.getSmallintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (prop.equals("StringLengthFunction")) {
                  original.setStringLengthFunction(proposed.getStringLengthFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("SupportsAlterTableWithDropColumn")) {
                  original.setSupportsAlterTableWithDropColumn(proposed.getSupportsAlterTableWithDropColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("SupportsAutoAssign")) {
                  original.setSupportsAutoAssign(proposed.getSupportsAutoAssign());
                  original._conditionalUnset(update.isUnsetUpdate(), 49);
               } else if (prop.equals("SupportsDefaultDeleteAction")) {
                  original.setSupportsDefaultDeleteAction(proposed.getSupportsDefaultDeleteAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("SupportsDeferredConstraints")) {
                  original.setSupportsDeferredConstraints(proposed.getSupportsDeferredConstraints());
                  original._conditionalUnset(update.isUnsetUpdate(), 98);
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
               } else if (prop.equals("SupportsNullTableForGetColumns")) {
                  original.setSupportsNullTableForGetColumns(proposed.getSupportsNullTableForGetColumns());
                  original._conditionalUnset(update.isUnsetUpdate(), 46);
               } else if (prop.equals("SupportsSelectEndIndex")) {
                  original.setSupportsSelectEndIndex(proposed.getSupportsSelectEndIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 48);
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
               } else if (prop.equals("ValidationSQL")) {
                  original.setValidationSQL(proposed.getValidationSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 60);
               } else if (prop.equals("VarbinaryTypeName")) {
                  original.setVarbinaryTypeName(proposed.getVarbinaryTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 112);
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
            DB2DictionaryBeanImpl copy = (DB2DictionaryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AutoAssignClause")) && this.bean.isAutoAssignClauseSet()) {
               copy.setAutoAssignClause(this.bean.getAutoAssignClause());
            }

            if ((excludeProps == null || !excludeProps.contains("BinaryTypeName")) && this.bean.isBinaryTypeNameSet()) {
               copy.setBinaryTypeName(this.bean.getBinaryTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BitTypeName")) && this.bean.isBitTypeNameSet()) {
               copy.setBitTypeName(this.bean.getBitTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("ClobTypeName")) && this.bean.isClobTypeNameSet()) {
               copy.setClobTypeName(this.bean.getClobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("CrossJoinClause")) && this.bean.isCrossJoinClauseSet()) {
               copy.setCrossJoinClause(this.bean.getCrossJoinClause());
            }

            if ((excludeProps == null || !excludeProps.contains("ForUpdateClause")) && this.bean.isForUpdateClauseSet()) {
               copy.setForUpdateClause(this.bean.getForUpdateClause());
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

            if ((excludeProps == null || !excludeProps.contains("NextSequenceQuery")) && this.bean.isNextSequenceQuerySet()) {
               copy.setNextSequenceQuery(this.bean.getNextSequenceQuery());
            }

            if ((excludeProps == null || !excludeProps.contains("NumericTypeName")) && this.bean.isNumericTypeNameSet()) {
               copy.setNumericTypeName(this.bean.getNumericTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("Platform")) && this.bean.isPlatformSet()) {
               copy.setPlatform(this.bean.getPlatform());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresAliasForSubselect")) && this.bean.isRequiresAliasForSubselectSet()) {
               copy.setRequiresAliasForSubselect(this.bean.getRequiresAliasForSubselect());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresAutoCommitForMetaData")) && this.bean.isRequiresAutoCommitForMetaDataSet()) {
               copy.setRequiresAutoCommitForMetaData(this.bean.getRequiresAutoCommitForMetaData());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresConditionForCrossJoin")) && this.bean.isRequiresConditionForCrossJoinSet()) {
               copy.setRequiresConditionForCrossJoin(this.bean.getRequiresConditionForCrossJoin());
            }

            if ((excludeProps == null || !excludeProps.contains("SmallintTypeName")) && this.bean.isSmallintTypeNameSet()) {
               copy.setSmallintTypeName(this.bean.getSmallintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("StringLengthFunction")) && this.bean.isStringLengthFunctionSet()) {
               copy.setStringLengthFunction(this.bean.getStringLengthFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsAlterTableWithDropColumn")) && this.bean.isSupportsAlterTableWithDropColumnSet()) {
               copy.setSupportsAlterTableWithDropColumn(this.bean.getSupportsAlterTableWithDropColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsAutoAssign")) && this.bean.isSupportsAutoAssignSet()) {
               copy.setSupportsAutoAssign(this.bean.getSupportsAutoAssign());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsDefaultDeleteAction")) && this.bean.isSupportsDefaultDeleteActionSet()) {
               copy.setSupportsDefaultDeleteAction(this.bean.getSupportsDefaultDeleteAction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsDeferredConstraints")) && this.bean.isSupportsDeferredConstraintsSet()) {
               copy.setSupportsDeferredConstraints(this.bean.getSupportsDeferredConstraints());
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

            if ((excludeProps == null || !excludeProps.contains("SupportsNullTableForGetColumns")) && this.bean.isSupportsNullTableForGetColumnsSet()) {
               copy.setSupportsNullTableForGetColumns(this.bean.getSupportsNullTableForGetColumns());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSelectEndIndex")) && this.bean.isSupportsSelectEndIndexSet()) {
               copy.setSupportsSelectEndIndex(this.bean.getSupportsSelectEndIndex());
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

            if ((excludeProps == null || !excludeProps.contains("ValidationSQL")) && this.bean.isValidationSQLSet()) {
               copy.setValidationSQL(this.bean.getValidationSQL());
            }

            if ((excludeProps == null || !excludeProps.contains("VarbinaryTypeName")) && this.bean.isVarbinaryTypeNameSet()) {
               copy.setVarbinaryTypeName(this.bean.getVarbinaryTypeName());
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
