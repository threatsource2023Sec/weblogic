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

public class HSQLDictionaryBeanImpl extends BuiltInDBDictionaryBeanImpl implements HSQLDictionaryBean, Serializable {
   private String _AutoAssignClause;
   private String _AutoAssignTypeName;
   private String _BlobTypeName;
   private boolean _CacheTables;
   private String _ClosePoolSQL;
   private String _CrossJoinClause;
   private String _DoubleTypeName;
   private String _LastGeneratedKeyQuery;
   private String _NextSequenceQuery;
   private String _Platform;
   private int _RangePosition;
   private boolean _RequiresCastForComparisons;
   private boolean _RequiresCastForMathFunctions;
   private boolean _RequiresConditionForCrossJoin;
   private String _StringLengthFunction;
   private boolean _SupportsAutoAssign;
   private boolean _SupportsDeferredConstraints;
   private boolean _SupportsNullTableForGetIndexInfo;
   private boolean _SupportsNullTableForGetPrimaryKeys;
   private boolean _SupportsSelectEndIndex;
   private boolean _SupportsSelectForUpdate;
   private boolean _SupportsSelectStartIndex;
   private String _TrimBothFunction;
   private String _TrimLeadingFunction;
   private String _TrimTrailingFunction;
   private boolean _UseGetObjectForBlobs;
   private boolean _UseSchemaName;
   private String _ValidationSQL;
   private static SchemaHelper2 _schemaHelper;

   public HSQLDictionaryBeanImpl() {
      this._initializeProperty(-1);
   }

   public HSQLDictionaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public HSQLDictionaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getCacheTables() {
      return this._CacheTables;
   }

   public boolean isCacheTablesInherited() {
      return false;
   }

   public boolean isCacheTablesSet() {
      return this._isSet(135);
   }

   public void setCacheTables(boolean param0) {
      boolean _oldVal = this._CacheTables;
      this._CacheTables = param0;
      this._postSet(135, _oldVal, param0);
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
               this._AutoAssignClause = "IDENTITY";
               if (initOne) {
                  break;
               }
            case 57:
               this._AutoAssignTypeName = "INTEGER";
               if (initOne) {
                  break;
               }
            case 76:
               this._BlobTypeName = "VARBINARY";
               if (initOne) {
                  break;
               }
            case 135:
               this._CacheTables = false;
               if (initOne) {
                  break;
               }
            case 114:
               this._ClosePoolSQL = "SHUTDOWN";
               if (initOne) {
                  break;
               }
            case 23:
               this._CrossJoinClause = "JOIN";
               if (initOne) {
                  break;
               }
            case 39:
               this._DoubleTypeName = "DOUBLE";
               if (initOne) {
                  break;
               }
            case 94:
               this._LastGeneratedKeyQuery = "CALL IDENTITY()";
               if (initOne) {
                  break;
               }
            case 21:
               this._NextSequenceQuery = "SELECT NEXT VALUE FOR {0} FROM INFORMATION_SCHEMA.SYSTEM_SEQUENCES";
               if (initOne) {
                  break;
               }
            case 84:
               this._Platform = "HSQL";
               if (initOne) {
                  break;
               }
            case 63:
               this._RangePosition = 1;
               if (initOne) {
                  break;
               }
            case 88:
               this._RequiresCastForComparisons = true;
               if (initOne) {
                  break;
               }
            case 128:
               this._RequiresCastForMathFunctions = true;
               if (initOne) {
                  break;
               }
            case 29:
               this._RequiresConditionForCrossJoin = true;
               if (initOne) {
                  break;
               }
            case 15:
               this._StringLengthFunction = "LENGTH({0})";
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
            case 101:
               this._SupportsNullTableForGetIndexInfo = true;
               if (initOne) {
                  break;
               }
            case 126:
               this._SupportsNullTableForGetPrimaryKeys = true;
               if (initOne) {
                  break;
               }
            case 48:
               this._SupportsSelectEndIndex = true;
               if (initOne) {
                  break;
               }
            case 53:
               this._SupportsSelectForUpdate = false;
               if (initOne) {
                  break;
               }
            case 119:
               this._SupportsSelectStartIndex = true;
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
            case 58:
               this._UseGetObjectForBlobs = true;
               if (initOne) {
                  break;
               }
            case 97:
               this._UseSchemaName = false;
               if (initOne) {
                  break;
               }
            case 60:
               this._ValidationSQL = "CALL 1";
               if (initOne) {
                  break;
               }
            case 16:
            case 17:
            case 18:
            case 20:
            case 22:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 50:
            case 51:
            case 52:
            case 54:
            case 55:
            case 56:
            case 59:
            case 61:
            case 62:
            case 64:
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
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 85:
            case 86:
            case 87:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 95:
            case 96:
            case 99:
            case 100:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 115:
            case 116:
            case 117:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 127:
            case 129:
            case 130:
            case 131:
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
            case 11:
            case 13:
            case 23:
            case 28:
            case 30:
            case 31:
            case 34:
            case 35:
            case 36:
            case 37:
            case 39:
            default:
               break;
            case 12:
               if (s.equals("cache-tables")) {
                  return 135;
               }
               break;
            case 14:
               if (s.equals("blob-type-name")) {
                  return 76;
               }

               if (s.equals("close-pool-sql")) {
                  return 114;
               }

               if (s.equals("range-position")) {
                  return 63;
               }

               if (s.equals("validation-sql")) {
                  return 60;
               }
               break;
            case 15:
               if (s.equals("use-schema-name")) {
                  return 97;
               }
               break;
            case 16:
               if (s.equals("double-type-name")) {
                  return 39;
               }
               break;
            case 17:
               if (s.equals("cross-join-clause")) {
                  return 23;
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
               if (s.equals("next-sequence-query")) {
                  return 21;
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

               if (s.equals("trim-leading-function")) {
                  return 19;
               }
               break;
            case 22:
               if (s.equals("string-length-function")) {
                  return 15;
               }

               if (s.equals("trim-trailing-function")) {
                  return 102;
               }
               break;
            case 24:
               if (s.equals("last-generated-key-query")) {
                  return 94;
               }

               if (s.equals("use-get-object-for-blobs")) {
                  return 58;
               }
               break;
            case 25:
               if (s.equals("supports-select-end-index")) {
                  return 48;
               }
               break;
            case 26:
               if (s.equals("supports-select-for-update")) {
                  return 53;
               }
               break;
            case 27:
               if (s.equals("supports-select-start-index")) {
                  return 119;
               }
               break;
            case 29:
               if (s.equals("requires-cast-for-comparisons")) {
                  return 88;
               }

               if (s.equals("supports-deferred-constraints")) {
                  return 98;
               }
               break;
            case 32:
               if (s.equals("requires-cast-for-math-functions")) {
                  return 128;
               }
               break;
            case 33:
               if (s.equals("requires-condition-for-cross-join")) {
                  return 29;
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
            case 15:
               return "string-length-function";
            case 16:
            case 17:
            case 18:
            case 20:
            case 22:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 50:
            case 51:
            case 52:
            case 54:
            case 55:
            case 56:
            case 59:
            case 61:
            case 62:
            case 64:
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
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 85:
            case 86:
            case 87:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 95:
            case 96:
            case 99:
            case 100:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 115:
            case 116:
            case 117:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 127:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            default:
               return super.getElementName(propIndex);
            case 19:
               return "trim-leading-function";
            case 21:
               return "next-sequence-query";
            case 23:
               return "cross-join-clause";
            case 29:
               return "requires-condition-for-cross-join";
            case 39:
               return "double-type-name";
            case 48:
               return "supports-select-end-index";
            case 49:
               return "supports-auto-assign";
            case 53:
               return "supports-select-for-update";
            case 57:
               return "auto-assign-type-name";
            case 58:
               return "use-get-object-for-blobs";
            case 60:
               return "validation-sql";
            case 63:
               return "range-position";
            case 65:
               return "auto-assign-clause";
            case 76:
               return "blob-type-name";
            case 84:
               return "platform";
            case 88:
               return "requires-cast-for-comparisons";
            case 94:
               return "last-generated-key-query";
            case 97:
               return "use-schema-name";
            case 98:
               return "supports-deferred-constraints";
            case 101:
               return "supports-null-table-for-get-index-info";
            case 102:
               return "trim-trailing-function";
            case 114:
               return "close-pool-sql";
            case 118:
               return "trim-both-function";
            case 119:
               return "supports-select-start-index";
            case 126:
               return "supports-null-table-for-get-primary-keys";
            case 128:
               return "requires-cast-for-math-functions";
            case 135:
               return "cache-tables";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends BuiltInDBDictionaryBeanImpl.Helper {
      private HSQLDictionaryBeanImpl bean;

      protected Helper(HSQLDictionaryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 15:
               return "StringLengthFunction";
            case 16:
            case 17:
            case 18:
            case 20:
            case 22:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 50:
            case 51:
            case 52:
            case 54:
            case 55:
            case 56:
            case 59:
            case 61:
            case 62:
            case 64:
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
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 85:
            case 86:
            case 87:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 95:
            case 96:
            case 99:
            case 100:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 115:
            case 116:
            case 117:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 127:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            default:
               return super.getPropertyName(propIndex);
            case 19:
               return "TrimLeadingFunction";
            case 21:
               return "NextSequenceQuery";
            case 23:
               return "CrossJoinClause";
            case 29:
               return "RequiresConditionForCrossJoin";
            case 39:
               return "DoubleTypeName";
            case 48:
               return "SupportsSelectEndIndex";
            case 49:
               return "SupportsAutoAssign";
            case 53:
               return "SupportsSelectForUpdate";
            case 57:
               return "AutoAssignTypeName";
            case 58:
               return "UseGetObjectForBlobs";
            case 60:
               return "ValidationSQL";
            case 63:
               return "RangePosition";
            case 65:
               return "AutoAssignClause";
            case 76:
               return "BlobTypeName";
            case 84:
               return "Platform";
            case 88:
               return "RequiresCastForComparisons";
            case 94:
               return "LastGeneratedKeyQuery";
            case 97:
               return "UseSchemaName";
            case 98:
               return "SupportsDeferredConstraints";
            case 101:
               return "SupportsNullTableForGetIndexInfo";
            case 102:
               return "TrimTrailingFunction";
            case 114:
               return "ClosePoolSQL";
            case 118:
               return "TrimBothFunction";
            case 119:
               return "SupportsSelectStartIndex";
            case 126:
               return "SupportsNullTableForGetPrimaryKeys";
            case 128:
               return "RequiresCastForMathFunctions";
            case 135:
               return "CacheTables";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AutoAssignClause")) {
            return 65;
         } else if (propName.equals("AutoAssignTypeName")) {
            return 57;
         } else if (propName.equals("BlobTypeName")) {
            return 76;
         } else if (propName.equals("CacheTables")) {
            return 135;
         } else if (propName.equals("ClosePoolSQL")) {
            return 114;
         } else if (propName.equals("CrossJoinClause")) {
            return 23;
         } else if (propName.equals("DoubleTypeName")) {
            return 39;
         } else if (propName.equals("LastGeneratedKeyQuery")) {
            return 94;
         } else if (propName.equals("NextSequenceQuery")) {
            return 21;
         } else if (propName.equals("Platform")) {
            return 84;
         } else if (propName.equals("RangePosition")) {
            return 63;
         } else if (propName.equals("RequiresCastForComparisons")) {
            return 88;
         } else if (propName.equals("RequiresCastForMathFunctions")) {
            return 128;
         } else if (propName.equals("RequiresConditionForCrossJoin")) {
            return 29;
         } else if (propName.equals("StringLengthFunction")) {
            return 15;
         } else if (propName.equals("SupportsAutoAssign")) {
            return 49;
         } else if (propName.equals("SupportsDeferredConstraints")) {
            return 98;
         } else if (propName.equals("SupportsNullTableForGetIndexInfo")) {
            return 101;
         } else if (propName.equals("SupportsNullTableForGetPrimaryKeys")) {
            return 126;
         } else if (propName.equals("SupportsSelectEndIndex")) {
            return 48;
         } else if (propName.equals("SupportsSelectForUpdate")) {
            return 53;
         } else if (propName.equals("SupportsSelectStartIndex")) {
            return 119;
         } else if (propName.equals("TrimBothFunction")) {
            return 118;
         } else if (propName.equals("TrimLeadingFunction")) {
            return 19;
         } else if (propName.equals("TrimTrailingFunction")) {
            return 102;
         } else if (propName.equals("UseGetObjectForBlobs")) {
            return 58;
         } else if (propName.equals("UseSchemaName")) {
            return 97;
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
            if (this.bean.isAutoAssignClauseSet()) {
               buf.append("AutoAssignClause");
               buf.append(String.valueOf(this.bean.getAutoAssignClause()));
            }

            if (this.bean.isAutoAssignTypeNameSet()) {
               buf.append("AutoAssignTypeName");
               buf.append(String.valueOf(this.bean.getAutoAssignTypeName()));
            }

            if (this.bean.isBlobTypeNameSet()) {
               buf.append("BlobTypeName");
               buf.append(String.valueOf(this.bean.getBlobTypeName()));
            }

            if (this.bean.isCacheTablesSet()) {
               buf.append("CacheTables");
               buf.append(String.valueOf(this.bean.getCacheTables()));
            }

            if (this.bean.isClosePoolSQLSet()) {
               buf.append("ClosePoolSQL");
               buf.append(String.valueOf(this.bean.getClosePoolSQL()));
            }

            if (this.bean.isCrossJoinClauseSet()) {
               buf.append("CrossJoinClause");
               buf.append(String.valueOf(this.bean.getCrossJoinClause()));
            }

            if (this.bean.isDoubleTypeNameSet()) {
               buf.append("DoubleTypeName");
               buf.append(String.valueOf(this.bean.getDoubleTypeName()));
            }

            if (this.bean.isLastGeneratedKeyQuerySet()) {
               buf.append("LastGeneratedKeyQuery");
               buf.append(String.valueOf(this.bean.getLastGeneratedKeyQuery()));
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

            if (this.bean.isStringLengthFunctionSet()) {
               buf.append("StringLengthFunction");
               buf.append(String.valueOf(this.bean.getStringLengthFunction()));
            }

            if (this.bean.isSupportsAutoAssignSet()) {
               buf.append("SupportsAutoAssign");
               buf.append(String.valueOf(this.bean.getSupportsAutoAssign()));
            }

            if (this.bean.isSupportsDeferredConstraintsSet()) {
               buf.append("SupportsDeferredConstraints");
               buf.append(String.valueOf(this.bean.getSupportsDeferredConstraints()));
            }

            if (this.bean.isSupportsNullTableForGetIndexInfoSet()) {
               buf.append("SupportsNullTableForGetIndexInfo");
               buf.append(String.valueOf(this.bean.getSupportsNullTableForGetIndexInfo()));
            }

            if (this.bean.isSupportsNullTableForGetPrimaryKeysSet()) {
               buf.append("SupportsNullTableForGetPrimaryKeys");
               buf.append(String.valueOf(this.bean.getSupportsNullTableForGetPrimaryKeys()));
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

            if (this.bean.isUseGetObjectForBlobsSet()) {
               buf.append("UseGetObjectForBlobs");
               buf.append(String.valueOf(this.bean.getUseGetObjectForBlobs()));
            }

            if (this.bean.isUseSchemaNameSet()) {
               buf.append("UseSchemaName");
               buf.append(String.valueOf(this.bean.getUseSchemaName()));
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
            HSQLDictionaryBeanImpl otherTyped = (HSQLDictionaryBeanImpl)other;
            this.computeDiff("AutoAssignClause", this.bean.getAutoAssignClause(), otherTyped.getAutoAssignClause(), false);
            this.computeDiff("AutoAssignTypeName", this.bean.getAutoAssignTypeName(), otherTyped.getAutoAssignTypeName(), false);
            this.computeDiff("BlobTypeName", this.bean.getBlobTypeName(), otherTyped.getBlobTypeName(), false);
            this.computeDiff("CacheTables", this.bean.getCacheTables(), otherTyped.getCacheTables(), false);
            this.computeDiff("ClosePoolSQL", this.bean.getClosePoolSQL(), otherTyped.getClosePoolSQL(), false);
            this.computeDiff("CrossJoinClause", this.bean.getCrossJoinClause(), otherTyped.getCrossJoinClause(), false);
            this.computeDiff("DoubleTypeName", this.bean.getDoubleTypeName(), otherTyped.getDoubleTypeName(), false);
            this.computeDiff("LastGeneratedKeyQuery", this.bean.getLastGeneratedKeyQuery(), otherTyped.getLastGeneratedKeyQuery(), false);
            this.computeDiff("NextSequenceQuery", this.bean.getNextSequenceQuery(), otherTyped.getNextSequenceQuery(), false);
            this.computeDiff("Platform", this.bean.getPlatform(), otherTyped.getPlatform(), false);
            this.computeDiff("RangePosition", this.bean.getRangePosition(), otherTyped.getRangePosition(), false);
            this.computeDiff("RequiresCastForComparisons", this.bean.getRequiresCastForComparisons(), otherTyped.getRequiresCastForComparisons(), false);
            this.computeDiff("RequiresCastForMathFunctions", this.bean.getRequiresCastForMathFunctions(), otherTyped.getRequiresCastForMathFunctions(), false);
            this.computeDiff("RequiresConditionForCrossJoin", this.bean.getRequiresConditionForCrossJoin(), otherTyped.getRequiresConditionForCrossJoin(), false);
            this.computeDiff("StringLengthFunction", this.bean.getStringLengthFunction(), otherTyped.getStringLengthFunction(), false);
            this.computeDiff("SupportsAutoAssign", this.bean.getSupportsAutoAssign(), otherTyped.getSupportsAutoAssign(), false);
            this.computeDiff("SupportsDeferredConstraints", this.bean.getSupportsDeferredConstraints(), otherTyped.getSupportsDeferredConstraints(), false);
            this.computeDiff("SupportsNullTableForGetIndexInfo", this.bean.getSupportsNullTableForGetIndexInfo(), otherTyped.getSupportsNullTableForGetIndexInfo(), false);
            this.computeDiff("SupportsNullTableForGetPrimaryKeys", this.bean.getSupportsNullTableForGetPrimaryKeys(), otherTyped.getSupportsNullTableForGetPrimaryKeys(), false);
            this.computeDiff("SupportsSelectEndIndex", this.bean.getSupportsSelectEndIndex(), otherTyped.getSupportsSelectEndIndex(), false);
            this.computeDiff("SupportsSelectForUpdate", this.bean.getSupportsSelectForUpdate(), otherTyped.getSupportsSelectForUpdate(), false);
            this.computeDiff("SupportsSelectStartIndex", this.bean.getSupportsSelectStartIndex(), otherTyped.getSupportsSelectStartIndex(), false);
            this.computeDiff("TrimBothFunction", this.bean.getTrimBothFunction(), otherTyped.getTrimBothFunction(), false);
            this.computeDiff("TrimLeadingFunction", this.bean.getTrimLeadingFunction(), otherTyped.getTrimLeadingFunction(), false);
            this.computeDiff("TrimTrailingFunction", this.bean.getTrimTrailingFunction(), otherTyped.getTrimTrailingFunction(), false);
            this.computeDiff("UseGetObjectForBlobs", this.bean.getUseGetObjectForBlobs(), otherTyped.getUseGetObjectForBlobs(), false);
            this.computeDiff("UseSchemaName", this.bean.getUseSchemaName(), otherTyped.getUseSchemaName(), false);
            this.computeDiff("ValidationSQL", this.bean.getValidationSQL(), otherTyped.getValidationSQL(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            HSQLDictionaryBeanImpl original = (HSQLDictionaryBeanImpl)event.getSourceBean();
            HSQLDictionaryBeanImpl proposed = (HSQLDictionaryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AutoAssignClause")) {
                  original.setAutoAssignClause(proposed.getAutoAssignClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 65);
               } else if (prop.equals("AutoAssignTypeName")) {
                  original.setAutoAssignTypeName(proposed.getAutoAssignTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 57);
               } else if (prop.equals("BlobTypeName")) {
                  original.setBlobTypeName(proposed.getBlobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 76);
               } else if (prop.equals("CacheTables")) {
                  original.setCacheTables(proposed.getCacheTables());
                  original._conditionalUnset(update.isUnsetUpdate(), 135);
               } else if (prop.equals("ClosePoolSQL")) {
                  original.setClosePoolSQL(proposed.getClosePoolSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 114);
               } else if (prop.equals("CrossJoinClause")) {
                  original.setCrossJoinClause(proposed.getCrossJoinClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("DoubleTypeName")) {
                  original.setDoubleTypeName(proposed.getDoubleTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("LastGeneratedKeyQuery")) {
                  original.setLastGeneratedKeyQuery(proposed.getLastGeneratedKeyQuery());
                  original._conditionalUnset(update.isUnsetUpdate(), 94);
               } else if (prop.equals("NextSequenceQuery")) {
                  original.setNextSequenceQuery(proposed.getNextSequenceQuery());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("Platform")) {
                  original.setPlatform(proposed.getPlatform());
                  original._conditionalUnset(update.isUnsetUpdate(), 84);
               } else if (prop.equals("RangePosition")) {
                  original.setRangePosition(proposed.getRangePosition());
                  original._conditionalUnset(update.isUnsetUpdate(), 63);
               } else if (prop.equals("RequiresCastForComparisons")) {
                  original.setRequiresCastForComparisons(proposed.getRequiresCastForComparisons());
                  original._conditionalUnset(update.isUnsetUpdate(), 88);
               } else if (prop.equals("RequiresCastForMathFunctions")) {
                  original.setRequiresCastForMathFunctions(proposed.getRequiresCastForMathFunctions());
                  original._conditionalUnset(update.isUnsetUpdate(), 128);
               } else if (prop.equals("RequiresConditionForCrossJoin")) {
                  original.setRequiresConditionForCrossJoin(proposed.getRequiresConditionForCrossJoin());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("StringLengthFunction")) {
                  original.setStringLengthFunction(proposed.getStringLengthFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("SupportsAutoAssign")) {
                  original.setSupportsAutoAssign(proposed.getSupportsAutoAssign());
                  original._conditionalUnset(update.isUnsetUpdate(), 49);
               } else if (prop.equals("SupportsDeferredConstraints")) {
                  original.setSupportsDeferredConstraints(proposed.getSupportsDeferredConstraints());
                  original._conditionalUnset(update.isUnsetUpdate(), 98);
               } else if (prop.equals("SupportsNullTableForGetIndexInfo")) {
                  original.setSupportsNullTableForGetIndexInfo(proposed.getSupportsNullTableForGetIndexInfo());
                  original._conditionalUnset(update.isUnsetUpdate(), 101);
               } else if (prop.equals("SupportsNullTableForGetPrimaryKeys")) {
                  original.setSupportsNullTableForGetPrimaryKeys(proposed.getSupportsNullTableForGetPrimaryKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 126);
               } else if (prop.equals("SupportsSelectEndIndex")) {
                  original.setSupportsSelectEndIndex(proposed.getSupportsSelectEndIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 48);
               } else if (prop.equals("SupportsSelectForUpdate")) {
                  original.setSupportsSelectForUpdate(proposed.getSupportsSelectForUpdate());
                  original._conditionalUnset(update.isUnsetUpdate(), 53);
               } else if (prop.equals("SupportsSelectStartIndex")) {
                  original.setSupportsSelectStartIndex(proposed.getSupportsSelectStartIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 119);
               } else if (prop.equals("TrimBothFunction")) {
                  original.setTrimBothFunction(proposed.getTrimBothFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 118);
               } else if (prop.equals("TrimLeadingFunction")) {
                  original.setTrimLeadingFunction(proposed.getTrimLeadingFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("TrimTrailingFunction")) {
                  original.setTrimTrailingFunction(proposed.getTrimTrailingFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 102);
               } else if (prop.equals("UseGetObjectForBlobs")) {
                  original.setUseGetObjectForBlobs(proposed.getUseGetObjectForBlobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 58);
               } else if (prop.equals("UseSchemaName")) {
                  original.setUseSchemaName(proposed.getUseSchemaName());
                  original._conditionalUnset(update.isUnsetUpdate(), 97);
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
            HSQLDictionaryBeanImpl copy = (HSQLDictionaryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AutoAssignClause")) && this.bean.isAutoAssignClauseSet()) {
               copy.setAutoAssignClause(this.bean.getAutoAssignClause());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoAssignTypeName")) && this.bean.isAutoAssignTypeNameSet()) {
               copy.setAutoAssignTypeName(this.bean.getAutoAssignTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BlobTypeName")) && this.bean.isBlobTypeNameSet()) {
               copy.setBlobTypeName(this.bean.getBlobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheTables")) && this.bean.isCacheTablesSet()) {
               copy.setCacheTables(this.bean.getCacheTables());
            }

            if ((excludeProps == null || !excludeProps.contains("ClosePoolSQL")) && this.bean.isClosePoolSQLSet()) {
               copy.setClosePoolSQL(this.bean.getClosePoolSQL());
            }

            if ((excludeProps == null || !excludeProps.contains("CrossJoinClause")) && this.bean.isCrossJoinClauseSet()) {
               copy.setCrossJoinClause(this.bean.getCrossJoinClause());
            }

            if ((excludeProps == null || !excludeProps.contains("DoubleTypeName")) && this.bean.isDoubleTypeNameSet()) {
               copy.setDoubleTypeName(this.bean.getDoubleTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("LastGeneratedKeyQuery")) && this.bean.isLastGeneratedKeyQuerySet()) {
               copy.setLastGeneratedKeyQuery(this.bean.getLastGeneratedKeyQuery());
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

            if ((excludeProps == null || !excludeProps.contains("RequiresCastForComparisons")) && this.bean.isRequiresCastForComparisonsSet()) {
               copy.setRequiresCastForComparisons(this.bean.getRequiresCastForComparisons());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresCastForMathFunctions")) && this.bean.isRequiresCastForMathFunctionsSet()) {
               copy.setRequiresCastForMathFunctions(this.bean.getRequiresCastForMathFunctions());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresConditionForCrossJoin")) && this.bean.isRequiresConditionForCrossJoinSet()) {
               copy.setRequiresConditionForCrossJoin(this.bean.getRequiresConditionForCrossJoin());
            }

            if ((excludeProps == null || !excludeProps.contains("StringLengthFunction")) && this.bean.isStringLengthFunctionSet()) {
               copy.setStringLengthFunction(this.bean.getStringLengthFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsAutoAssign")) && this.bean.isSupportsAutoAssignSet()) {
               copy.setSupportsAutoAssign(this.bean.getSupportsAutoAssign());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsDeferredConstraints")) && this.bean.isSupportsDeferredConstraintsSet()) {
               copy.setSupportsDeferredConstraints(this.bean.getSupportsDeferredConstraints());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsNullTableForGetIndexInfo")) && this.bean.isSupportsNullTableForGetIndexInfoSet()) {
               copy.setSupportsNullTableForGetIndexInfo(this.bean.getSupportsNullTableForGetIndexInfo());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsNullTableForGetPrimaryKeys")) && this.bean.isSupportsNullTableForGetPrimaryKeysSet()) {
               copy.setSupportsNullTableForGetPrimaryKeys(this.bean.getSupportsNullTableForGetPrimaryKeys());
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

            if ((excludeProps == null || !excludeProps.contains("TrimBothFunction")) && this.bean.isTrimBothFunctionSet()) {
               copy.setTrimBothFunction(this.bean.getTrimBothFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("TrimLeadingFunction")) && this.bean.isTrimLeadingFunctionSet()) {
               copy.setTrimLeadingFunction(this.bean.getTrimLeadingFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("TrimTrailingFunction")) && this.bean.isTrimTrailingFunctionSet()) {
               copy.setTrimTrailingFunction(this.bean.getTrimTrailingFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("UseGetObjectForBlobs")) && this.bean.isUseGetObjectForBlobsSet()) {
               copy.setUseGetObjectForBlobs(this.bean.getUseGetObjectForBlobs());
            }

            if ((excludeProps == null || !excludeProps.contains("UseSchemaName")) && this.bean.isUseSchemaNameSet()) {
               copy.setUseSchemaName(this.bean.getUseSchemaName());
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
