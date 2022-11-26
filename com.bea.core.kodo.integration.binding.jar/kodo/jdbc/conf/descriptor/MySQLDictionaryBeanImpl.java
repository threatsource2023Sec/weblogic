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

public class MySQLDictionaryBeanImpl extends BuiltInDBDictionaryBeanImpl implements MySQLDictionaryBean, Serializable {
   private boolean _AllowsAliasInBulkClause;
   private String _AutoAssignClause;
   private String _ClobTypeName;
   private String _ConcatenateFunction;
   private String _ConstraintNameMode;
   private String _DistinctCountColumnSeparator;
   private boolean _DriverDeserializesBlobs;
   private String _LastGeneratedKeyQuery;
   private String _LongVarbinaryTypeName;
   private String _LongVarcharTypeName;
   private int _MaxColumnNameLength;
   private int _MaxConstraintNameLength;
   private int _MaxIndexNameLength;
   private int _MaxIndexesPerTable;
   private int _MaxTableNameLength;
   private String _Platform;
   private boolean _RequiresAliasForSubselect;
   private String _SchemaCase;
   private boolean _SupportsAutoAssign;
   private boolean _SupportsDeferredConstraints;
   private boolean _SupportsMultipleNontransactionalResultSets;
   private boolean _SupportsSelectEndIndex;
   private boolean _SupportsSelectStartIndex;
   private boolean _SupportsSubselect;
   private String _TableType;
   private String _TimestampTypeName;
   private boolean _UseClobs;
   private String _ValidationSQL;
   private static SchemaHelper2 _schemaHelper;

   public MySQLDictionaryBeanImpl() {
      this._initializeProperty(-1);
   }

   public MySQLDictionaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MySQLDictionaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getUseClobs() {
      return this._UseClobs;
   }

   public boolean isUseClobsInherited() {
      return false;
   }

   public boolean isUseClobsSet() {
      return this._isSet(135);
   }

   public void setUseClobs(boolean param0) {
      boolean _oldVal = this._UseClobs;
      this._UseClobs = param0;
      this._postSet(135, _oldVal, param0);
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

   public String getTableType() {
      return this._TableType;
   }

   public boolean isTableTypeInherited() {
      return false;
   }

   public boolean isTableTypeSet() {
      return this._isSet(136);
   }

   public void setTableType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TableType;
      this._TableType = param0;
      this._postSet(136, _oldVal, param0);
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

   public boolean getDriverDeserializesBlobs() {
      return this._DriverDeserializesBlobs;
   }

   public boolean isDriverDeserializesBlobsInherited() {
      return false;
   }

   public boolean isDriverDeserializesBlobsSet() {
      return this._isSet(137);
   }

   public void setDriverDeserializesBlobs(boolean param0) {
      boolean _oldVal = this._DriverDeserializesBlobs;
      this._DriverDeserializesBlobs = param0;
      this._postSet(137, _oldVal, param0);
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
               this._AutoAssignClause = "AUTO_INCREMENT";
               if (initOne) {
                  break;
               }
            case 4:
               this._ClobTypeName = "TEXT";
               if (initOne) {
                  break;
               }
            case 8:
               this._ConcatenateFunction = "CONCAT({0},{1})";
               if (initOne) {
                  break;
               }
            case 51:
               this._ConstraintNameMode = "mid";
               if (initOne) {
                  break;
               }
            case 54:
               this._DistinctCountColumnSeparator = ",";
               if (initOne) {
                  break;
               }
            case 137:
               this._DriverDeserializesBlobs = true;
               if (initOne) {
                  break;
               }
            case 94:
               this._LastGeneratedKeyQuery = "SELECT LAST_INSERT_ID()";
               if (initOne) {
                  break;
               }
            case 16:
               this._LongVarbinaryTypeName = "LONG VARBINARY";
               if (initOne) {
                  break;
               }
            case 22:
               this._LongVarcharTypeName = "TEXT";
               if (initOne) {
                  break;
               }
            case 38:
               this._MaxColumnNameLength = 64;
               if (initOne) {
                  break;
               }
            case 12:
               this._MaxConstraintNameLength = 64;
               if (initOne) {
                  break;
               }
            case 109:
               this._MaxIndexNameLength = 64;
               if (initOne) {
                  break;
               }
            case 87:
               this._MaxIndexesPerTable = 32;
               if (initOne) {
                  break;
               }
            case 113:
               this._MaxTableNameLength = 64;
               if (initOne) {
                  break;
               }
            case 84:
               this._Platform = "MySQL";
               if (initOne) {
                  break;
               }
            case 100:
               this._RequiresAliasForSubselect = true;
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
            case 66:
               this._SupportsMultipleNontransactionalResultSets = false;
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
            case 55:
               this._SupportsSubselect = false;
               if (initOne) {
                  break;
               }
            case 136:
               this._TableType = "innodb";
               if (initOne) {
                  break;
               }
            case 131:
               this._TimestampTypeName = "DATETIME";
               if (initOne) {
                  break;
               }
            case 135:
               this._UseClobs = true;
               if (initOne) {
                  break;
               }
            case 60:
               this._ValidationSQL = "SELECT NOW()";
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

   public static class SchemaHelper2 extends BuiltInDBDictionaryBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("platform")) {
                  return 84;
               }
               break;
            case 9:
               if (s.equals("use-clobs")) {
                  return 135;
               }
               break;
            case 10:
               if (s.equals("table-type")) {
                  return 136;
               }
               break;
            case 11:
               if (s.equals("schema-case")) {
                  return 34;
               }
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 23:
            case 30:
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
            default:
               break;
            case 14:
               if (s.equals("clob-type-name")) {
                  return 4;
               }

               if (s.equals("validation-sql")) {
                  return 60;
               }
               break;
            case 18:
               if (s.equals("auto-assign-clause")) {
                  return 65;
               }

               if (s.equals("supports-subselect")) {
                  return 55;
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

               if (s.equals("constraint-name-mode")) {
                  return 51;
               }

               if (s.equals("supports-auto-assign")) {
                  return 49;
               }
               break;
            case 21:
               if (s.equals("max-index-name-length")) {
                  return 109;
               }

               if (s.equals("max-indexes-per-table")) {
                  return 87;
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
            case 24:
               if (s.equals("last-generated-key-query")) {
                  return 94;
               }

               if (s.equals("long-varbinary-type-name")) {
                  return 16;
               }
               break;
            case 25:
               if (s.equals("driver-deserializes-blobs")) {
                  return 137;
               }

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
            case 31:
               if (s.equals("distinct-count-column-separator")) {
                  return 54;
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
            case 4:
               return "clob-type-name";
            case 8:
               return "concatenate-function";
            case 12:
               return "max-constraint-name-length";
            case 16:
               return "long-varbinary-type-name";
            case 22:
               return "long-varchar-type-name";
            case 34:
               return "schema-case";
            case 38:
               return "max-column-name-length";
            case 48:
               return "supports-select-end-index";
            case 49:
               return "supports-auto-assign";
            case 51:
               return "constraint-name-mode";
            case 52:
               return "allows-alias-in-bulk-clause";
            case 54:
               return "distinct-count-column-separator";
            case 55:
               return "supports-subselect";
            case 60:
               return "validation-sql";
            case 65:
               return "auto-assign-clause";
            case 66:
               return "supports-multiple-nontransactional-result-sets";
            case 84:
               return "platform";
            case 87:
               return "max-indexes-per-table";
            case 94:
               return "last-generated-key-query";
            case 98:
               return "supports-deferred-constraints";
            case 100:
               return "requires-alias-for-subselect";
            case 109:
               return "max-index-name-length";
            case 113:
               return "max-table-name-length";
            case 119:
               return "supports-select-start-index";
            case 131:
               return "timestamp-type-name";
            case 135:
               return "use-clobs";
            case 136:
               return "table-type";
            case 137:
               return "driver-deserializes-blobs";
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
            case 135:
               return true;
            case 136:
               return true;
            case 137:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends BuiltInDBDictionaryBeanImpl.Helper {
      private MySQLDictionaryBeanImpl bean;

      protected Helper(MySQLDictionaryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "ClobTypeName";
            case 8:
               return "ConcatenateFunction";
            case 12:
               return "MaxConstraintNameLength";
            case 16:
               return "LongVarbinaryTypeName";
            case 22:
               return "LongVarcharTypeName";
            case 34:
               return "SchemaCase";
            case 38:
               return "MaxColumnNameLength";
            case 48:
               return "SupportsSelectEndIndex";
            case 49:
               return "SupportsAutoAssign";
            case 51:
               return "ConstraintNameMode";
            case 52:
               return "AllowsAliasInBulkClause";
            case 54:
               return "DistinctCountColumnSeparator";
            case 55:
               return "SupportsSubselect";
            case 60:
               return "ValidationSQL";
            case 65:
               return "AutoAssignClause";
            case 66:
               return "SupportsMultipleNontransactionalResultSets";
            case 84:
               return "Platform";
            case 87:
               return "MaxIndexesPerTable";
            case 94:
               return "LastGeneratedKeyQuery";
            case 98:
               return "SupportsDeferredConstraints";
            case 100:
               return "RequiresAliasForSubselect";
            case 109:
               return "MaxIndexNameLength";
            case 113:
               return "MaxTableNameLength";
            case 119:
               return "SupportsSelectStartIndex";
            case 131:
               return "TimestampTypeName";
            case 135:
               return "UseClobs";
            case 136:
               return "TableType";
            case 137:
               return "DriverDeserializesBlobs";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AllowsAliasInBulkClause")) {
            return 52;
         } else if (propName.equals("AutoAssignClause")) {
            return 65;
         } else if (propName.equals("ClobTypeName")) {
            return 4;
         } else if (propName.equals("ConcatenateFunction")) {
            return 8;
         } else if (propName.equals("ConstraintNameMode")) {
            return 51;
         } else if (propName.equals("DistinctCountColumnSeparator")) {
            return 54;
         } else if (propName.equals("DriverDeserializesBlobs")) {
            return 137;
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
         } else if (propName.equals("MaxIndexesPerTable")) {
            return 87;
         } else if (propName.equals("MaxTableNameLength")) {
            return 113;
         } else if (propName.equals("Platform")) {
            return 84;
         } else if (propName.equals("RequiresAliasForSubselect")) {
            return 100;
         } else if (propName.equals("SchemaCase")) {
            return 34;
         } else if (propName.equals("SupportsAutoAssign")) {
            return 49;
         } else if (propName.equals("SupportsDeferredConstraints")) {
            return 98;
         } else if (propName.equals("SupportsMultipleNontransactionalResultSets")) {
            return 66;
         } else if (propName.equals("SupportsSelectEndIndex")) {
            return 48;
         } else if (propName.equals("SupportsSelectStartIndex")) {
            return 119;
         } else if (propName.equals("SupportsSubselect")) {
            return 55;
         } else if (propName.equals("TableType")) {
            return 136;
         } else if (propName.equals("TimestampTypeName")) {
            return 131;
         } else if (propName.equals("UseClobs")) {
            return 135;
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

            if (this.bean.isClobTypeNameSet()) {
               buf.append("ClobTypeName");
               buf.append(String.valueOf(this.bean.getClobTypeName()));
            }

            if (this.bean.isConcatenateFunctionSet()) {
               buf.append("ConcatenateFunction");
               buf.append(String.valueOf(this.bean.getConcatenateFunction()));
            }

            if (this.bean.isConstraintNameModeSet()) {
               buf.append("ConstraintNameMode");
               buf.append(String.valueOf(this.bean.getConstraintNameMode()));
            }

            if (this.bean.isDistinctCountColumnSeparatorSet()) {
               buf.append("DistinctCountColumnSeparator");
               buf.append(String.valueOf(this.bean.getDistinctCountColumnSeparator()));
            }

            if (this.bean.isDriverDeserializesBlobsSet()) {
               buf.append("DriverDeserializesBlobs");
               buf.append(String.valueOf(this.bean.getDriverDeserializesBlobs()));
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

            if (this.bean.isMaxIndexesPerTableSet()) {
               buf.append("MaxIndexesPerTable");
               buf.append(String.valueOf(this.bean.getMaxIndexesPerTable()));
            }

            if (this.bean.isMaxTableNameLengthSet()) {
               buf.append("MaxTableNameLength");
               buf.append(String.valueOf(this.bean.getMaxTableNameLength()));
            }

            if (this.bean.isPlatformSet()) {
               buf.append("Platform");
               buf.append(String.valueOf(this.bean.getPlatform()));
            }

            if (this.bean.isRequiresAliasForSubselectSet()) {
               buf.append("RequiresAliasForSubselect");
               buf.append(String.valueOf(this.bean.getRequiresAliasForSubselect()));
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

            if (this.bean.isSupportsMultipleNontransactionalResultSetsSet()) {
               buf.append("SupportsMultipleNontransactionalResultSets");
               buf.append(String.valueOf(this.bean.getSupportsMultipleNontransactionalResultSets()));
            }

            if (this.bean.isSupportsSelectEndIndexSet()) {
               buf.append("SupportsSelectEndIndex");
               buf.append(String.valueOf(this.bean.getSupportsSelectEndIndex()));
            }

            if (this.bean.isSupportsSelectStartIndexSet()) {
               buf.append("SupportsSelectStartIndex");
               buf.append(String.valueOf(this.bean.getSupportsSelectStartIndex()));
            }

            if (this.bean.isSupportsSubselectSet()) {
               buf.append("SupportsSubselect");
               buf.append(String.valueOf(this.bean.getSupportsSubselect()));
            }

            if (this.bean.isTableTypeSet()) {
               buf.append("TableType");
               buf.append(String.valueOf(this.bean.getTableType()));
            }

            if (this.bean.isTimestampTypeNameSet()) {
               buf.append("TimestampTypeName");
               buf.append(String.valueOf(this.bean.getTimestampTypeName()));
            }

            if (this.bean.isUseClobsSet()) {
               buf.append("UseClobs");
               buf.append(String.valueOf(this.bean.getUseClobs()));
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
            MySQLDictionaryBeanImpl otherTyped = (MySQLDictionaryBeanImpl)other;
            this.computeDiff("AllowsAliasInBulkClause", this.bean.getAllowsAliasInBulkClause(), otherTyped.getAllowsAliasInBulkClause(), false);
            this.computeDiff("AutoAssignClause", this.bean.getAutoAssignClause(), otherTyped.getAutoAssignClause(), false);
            this.computeDiff("ClobTypeName", this.bean.getClobTypeName(), otherTyped.getClobTypeName(), false);
            this.computeDiff("ConcatenateFunction", this.bean.getConcatenateFunction(), otherTyped.getConcatenateFunction(), false);
            this.computeDiff("ConstraintNameMode", this.bean.getConstraintNameMode(), otherTyped.getConstraintNameMode(), false);
            this.computeDiff("DistinctCountColumnSeparator", this.bean.getDistinctCountColumnSeparator(), otherTyped.getDistinctCountColumnSeparator(), false);
            this.computeDiff("DriverDeserializesBlobs", this.bean.getDriverDeserializesBlobs(), otherTyped.getDriverDeserializesBlobs(), false);
            this.computeDiff("LastGeneratedKeyQuery", this.bean.getLastGeneratedKeyQuery(), otherTyped.getLastGeneratedKeyQuery(), false);
            this.computeDiff("LongVarbinaryTypeName", this.bean.getLongVarbinaryTypeName(), otherTyped.getLongVarbinaryTypeName(), false);
            this.computeDiff("LongVarcharTypeName", this.bean.getLongVarcharTypeName(), otherTyped.getLongVarcharTypeName(), false);
            this.computeDiff("MaxColumnNameLength", this.bean.getMaxColumnNameLength(), otherTyped.getMaxColumnNameLength(), false);
            this.computeDiff("MaxConstraintNameLength", this.bean.getMaxConstraintNameLength(), otherTyped.getMaxConstraintNameLength(), false);
            this.computeDiff("MaxIndexNameLength", this.bean.getMaxIndexNameLength(), otherTyped.getMaxIndexNameLength(), false);
            this.computeDiff("MaxIndexesPerTable", this.bean.getMaxIndexesPerTable(), otherTyped.getMaxIndexesPerTable(), false);
            this.computeDiff("MaxTableNameLength", this.bean.getMaxTableNameLength(), otherTyped.getMaxTableNameLength(), false);
            this.computeDiff("Platform", this.bean.getPlatform(), otherTyped.getPlatform(), false);
            this.computeDiff("RequiresAliasForSubselect", this.bean.getRequiresAliasForSubselect(), otherTyped.getRequiresAliasForSubselect(), false);
            this.computeDiff("SchemaCase", this.bean.getSchemaCase(), otherTyped.getSchemaCase(), false);
            this.computeDiff("SupportsAutoAssign", this.bean.getSupportsAutoAssign(), otherTyped.getSupportsAutoAssign(), false);
            this.computeDiff("SupportsDeferredConstraints", this.bean.getSupportsDeferredConstraints(), otherTyped.getSupportsDeferredConstraints(), false);
            this.computeDiff("SupportsMultipleNontransactionalResultSets", this.bean.getSupportsMultipleNontransactionalResultSets(), otherTyped.getSupportsMultipleNontransactionalResultSets(), false);
            this.computeDiff("SupportsSelectEndIndex", this.bean.getSupportsSelectEndIndex(), otherTyped.getSupportsSelectEndIndex(), false);
            this.computeDiff("SupportsSelectStartIndex", this.bean.getSupportsSelectStartIndex(), otherTyped.getSupportsSelectStartIndex(), false);
            this.computeDiff("SupportsSubselect", this.bean.getSupportsSubselect(), otherTyped.getSupportsSubselect(), false);
            this.computeDiff("TableType", this.bean.getTableType(), otherTyped.getTableType(), false);
            this.computeDiff("TimestampTypeName", this.bean.getTimestampTypeName(), otherTyped.getTimestampTypeName(), false);
            this.computeDiff("UseClobs", this.bean.getUseClobs(), otherTyped.getUseClobs(), false);
            this.computeDiff("ValidationSQL", this.bean.getValidationSQL(), otherTyped.getValidationSQL(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MySQLDictionaryBeanImpl original = (MySQLDictionaryBeanImpl)event.getSourceBean();
            MySQLDictionaryBeanImpl proposed = (MySQLDictionaryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AllowsAliasInBulkClause")) {
                  original.setAllowsAliasInBulkClause(proposed.getAllowsAliasInBulkClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 52);
               } else if (prop.equals("AutoAssignClause")) {
                  original.setAutoAssignClause(proposed.getAutoAssignClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 65);
               } else if (prop.equals("ClobTypeName")) {
                  original.setClobTypeName(proposed.getClobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ConcatenateFunction")) {
                  original.setConcatenateFunction(proposed.getConcatenateFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("ConstraintNameMode")) {
                  original.setConstraintNameMode(proposed.getConstraintNameMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 51);
               } else if (prop.equals("DistinctCountColumnSeparator")) {
                  original.setDistinctCountColumnSeparator(proposed.getDistinctCountColumnSeparator());
                  original._conditionalUnset(update.isUnsetUpdate(), 54);
               } else if (prop.equals("DriverDeserializesBlobs")) {
                  original.setDriverDeserializesBlobs(proposed.getDriverDeserializesBlobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 137);
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
               } else if (prop.equals("MaxIndexesPerTable")) {
                  original.setMaxIndexesPerTable(proposed.getMaxIndexesPerTable());
                  original._conditionalUnset(update.isUnsetUpdate(), 87);
               } else if (prop.equals("MaxTableNameLength")) {
                  original.setMaxTableNameLength(proposed.getMaxTableNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 113);
               } else if (prop.equals("Platform")) {
                  original.setPlatform(proposed.getPlatform());
                  original._conditionalUnset(update.isUnsetUpdate(), 84);
               } else if (prop.equals("RequiresAliasForSubselect")) {
                  original.setRequiresAliasForSubselect(proposed.getRequiresAliasForSubselect());
                  original._conditionalUnset(update.isUnsetUpdate(), 100);
               } else if (prop.equals("SchemaCase")) {
                  original.setSchemaCase(proposed.getSchemaCase());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("SupportsAutoAssign")) {
                  original.setSupportsAutoAssign(proposed.getSupportsAutoAssign());
                  original._conditionalUnset(update.isUnsetUpdate(), 49);
               } else if (prop.equals("SupportsDeferredConstraints")) {
                  original.setSupportsDeferredConstraints(proposed.getSupportsDeferredConstraints());
                  original._conditionalUnset(update.isUnsetUpdate(), 98);
               } else if (prop.equals("SupportsMultipleNontransactionalResultSets")) {
                  original.setSupportsMultipleNontransactionalResultSets(proposed.getSupportsMultipleNontransactionalResultSets());
                  original._conditionalUnset(update.isUnsetUpdate(), 66);
               } else if (prop.equals("SupportsSelectEndIndex")) {
                  original.setSupportsSelectEndIndex(proposed.getSupportsSelectEndIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 48);
               } else if (prop.equals("SupportsSelectStartIndex")) {
                  original.setSupportsSelectStartIndex(proposed.getSupportsSelectStartIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 119);
               } else if (prop.equals("SupportsSubselect")) {
                  original.setSupportsSubselect(proposed.getSupportsSubselect());
                  original._conditionalUnset(update.isUnsetUpdate(), 55);
               } else if (prop.equals("TableType")) {
                  original.setTableType(proposed.getTableType());
                  original._conditionalUnset(update.isUnsetUpdate(), 136);
               } else if (prop.equals("TimestampTypeName")) {
                  original.setTimestampTypeName(proposed.getTimestampTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 131);
               } else if (prop.equals("UseClobs")) {
                  original.setUseClobs(proposed.getUseClobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 135);
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
            MySQLDictionaryBeanImpl copy = (MySQLDictionaryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AllowsAliasInBulkClause")) && this.bean.isAllowsAliasInBulkClauseSet()) {
               copy.setAllowsAliasInBulkClause(this.bean.getAllowsAliasInBulkClause());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoAssignClause")) && this.bean.isAutoAssignClauseSet()) {
               copy.setAutoAssignClause(this.bean.getAutoAssignClause());
            }

            if ((excludeProps == null || !excludeProps.contains("ClobTypeName")) && this.bean.isClobTypeNameSet()) {
               copy.setClobTypeName(this.bean.getClobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConcatenateFunction")) && this.bean.isConcatenateFunctionSet()) {
               copy.setConcatenateFunction(this.bean.getConcatenateFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("ConstraintNameMode")) && this.bean.isConstraintNameModeSet()) {
               copy.setConstraintNameMode(this.bean.getConstraintNameMode());
            }

            if ((excludeProps == null || !excludeProps.contains("DistinctCountColumnSeparator")) && this.bean.isDistinctCountColumnSeparatorSet()) {
               copy.setDistinctCountColumnSeparator(this.bean.getDistinctCountColumnSeparator());
            }

            if ((excludeProps == null || !excludeProps.contains("DriverDeserializesBlobs")) && this.bean.isDriverDeserializesBlobsSet()) {
               copy.setDriverDeserializesBlobs(this.bean.getDriverDeserializesBlobs());
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

            if ((excludeProps == null || !excludeProps.contains("MaxIndexesPerTable")) && this.bean.isMaxIndexesPerTableSet()) {
               copy.setMaxIndexesPerTable(this.bean.getMaxIndexesPerTable());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxTableNameLength")) && this.bean.isMaxTableNameLengthSet()) {
               copy.setMaxTableNameLength(this.bean.getMaxTableNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("Platform")) && this.bean.isPlatformSet()) {
               copy.setPlatform(this.bean.getPlatform());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresAliasForSubselect")) && this.bean.isRequiresAliasForSubselectSet()) {
               copy.setRequiresAliasForSubselect(this.bean.getRequiresAliasForSubselect());
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

            if ((excludeProps == null || !excludeProps.contains("SupportsMultipleNontransactionalResultSets")) && this.bean.isSupportsMultipleNontransactionalResultSetsSet()) {
               copy.setSupportsMultipleNontransactionalResultSets(this.bean.getSupportsMultipleNontransactionalResultSets());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSelectEndIndex")) && this.bean.isSupportsSelectEndIndexSet()) {
               copy.setSupportsSelectEndIndex(this.bean.getSupportsSelectEndIndex());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSelectStartIndex")) && this.bean.isSupportsSelectStartIndexSet()) {
               copy.setSupportsSelectStartIndex(this.bean.getSupportsSelectStartIndex());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSubselect")) && this.bean.isSupportsSubselectSet()) {
               copy.setSupportsSubselect(this.bean.getSupportsSubselect());
            }

            if ((excludeProps == null || !excludeProps.contains("TableType")) && this.bean.isTableTypeSet()) {
               copy.setTableType(this.bean.getTableType());
            }

            if ((excludeProps == null || !excludeProps.contains("TimestampTypeName")) && this.bean.isTimestampTypeNameSet()) {
               copy.setTimestampTypeName(this.bean.getTimestampTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("UseClobs")) && this.bean.isUseClobsSet()) {
               copy.setUseClobs(this.bean.getUseClobs());
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
