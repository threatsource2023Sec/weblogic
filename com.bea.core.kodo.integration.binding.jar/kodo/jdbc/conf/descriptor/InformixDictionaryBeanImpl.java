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

public class InformixDictionaryBeanImpl extends BuiltInDBDictionaryBeanImpl implements InformixDictionaryBean, Serializable {
   private String _AutoAssignTypeName;
   private String _BigintTypeName;
   private String _BitTypeName;
   private String _BlobTypeName;
   private String _CatalogSeparator;
   private String _ClobTypeName;
   private String _ConstraintNameMode;
   private String _DateTypeName;
   private String _DoubleTypeName;
   private String _FloatTypeName;
   private String _LastGeneratedKeyQuery;
   private boolean _LockModeEnabled;
   private int _LockWaitSeconds;
   private String _LongVarcharTypeName;
   private int _MaxColumnNameLength;
   private int _MaxConstraintNameLength;
   private int _MaxIndexNameLength;
   private int _MaxTableNameLength;
   private String _Platform;
   private String _SmallintTypeName;
   private boolean _SupportsAutoAssign;
   private boolean _SupportsDeferredConstraints;
   private boolean _SupportsLockingWithDistinctClause;
   private boolean _SupportsLockingWithMultipleTables;
   private boolean _SupportsLockingWithOrderClause;
   private boolean _SupportsMultipleNontransactionalResultSets;
   private boolean _SupportsQueryTimeout;
   private boolean _SupportsSchemaForGetColumns;
   private boolean _SupportsSchemaForGetTables;
   private boolean _SwapSchemaAndCatalog;
   private String _TimestampTypeName;
   private String _TinyintTypeName;
   private boolean _UseGetStringForClobs;
   private String _ValidationSQL;
   private static SchemaHelper2 _schemaHelper;

   public InformixDictionaryBeanImpl() {
      this._initializeProperty(-1);
   }

   public InformixDictionaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public InformixDictionaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
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

   public int getLockWaitSeconds() {
      return this._LockWaitSeconds;
   }

   public boolean isLockWaitSecondsInherited() {
      return false;
   }

   public boolean isLockWaitSecondsSet() {
      return this._isSet(135);
   }

   public void setLockWaitSeconds(int param0) {
      int _oldVal = this._LockWaitSeconds;
      this._LockWaitSeconds = param0;
      this._postSet(135, _oldVal, param0);
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

   public boolean getLockModeEnabled() {
      return this._LockModeEnabled;
   }

   public boolean isLockModeEnabledInherited() {
      return false;
   }

   public boolean isLockModeEnabledSet() {
      return this._isSet(136);
   }

   public void setLockModeEnabled(boolean param0) {
      boolean _oldVal = this._LockModeEnabled;
      this._LockModeEnabled = param0;
      this._postSet(136, _oldVal, param0);
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

   public boolean getSwapSchemaAndCatalog() {
      return this._SwapSchemaAndCatalog;
   }

   public boolean isSwapSchemaAndCatalogInherited() {
      return false;
   }

   public boolean isSwapSchemaAndCatalogSet() {
      return this._isSet(137);
   }

   public void setSwapSchemaAndCatalog(boolean param0) {
      boolean _oldVal = this._SwapSchemaAndCatalog;
      this._SwapSchemaAndCatalog = param0;
      this._postSet(137, _oldVal, param0);
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
         idx = 57;
      }

      try {
         switch (idx) {
            case 57:
               this._AutoAssignTypeName = "serial";
               if (initOne) {
                  break;
               }
            case 93:
               this._BigintTypeName = "NUMERIC(32,0)";
               if (initOne) {
                  break;
               }
            case 45:
               this._BitTypeName = "BOOLEAN";
               if (initOne) {
                  break;
               }
            case 76:
               this._BlobTypeName = "BYTE";
               if (initOne) {
                  break;
               }
            case 32:
               this._CatalogSeparator = ":";
               if (initOne) {
                  break;
               }
            case 4:
               this._ClobTypeName = "TEXT";
               if (initOne) {
                  break;
               }
            case 51:
               this._ConstraintNameMode = "after";
               if (initOne) {
                  break;
               }
            case 25:
               this._DateTypeName = "DATE";
               if (initOne) {
                  break;
               }
            case 39:
               this._DoubleTypeName = "DOUBLE PRECISION";
               if (initOne) {
                  break;
               }
            case 70:
               this._FloatTypeName = "REAL";
               if (initOne) {
                  break;
               }
            case 94:
               this._LastGeneratedKeyQuery = "SELECT FIRST 1 DBINFO('sqlca.sqlerrd1') FROM informix.systables";
               if (initOne) {
                  break;
               }
            case 136:
               this._LockModeEnabled = false;
               if (initOne) {
                  break;
               }
            case 135:
               this._LockWaitSeconds = 30;
               if (initOne) {
                  break;
               }
            case 22:
               this._LongVarcharTypeName = "TEXT";
               if (initOne) {
                  break;
               }
            case 38:
               this._MaxColumnNameLength = 18;
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
            case 113:
               this._MaxTableNameLength = 18;
               if (initOne) {
                  break;
               }
            case 84:
               this._Platform = "Informix";
               if (initOne) {
                  break;
               }
            case 42:
               this._SmallintTypeName = "INT8";
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
            case 66:
               this._SupportsMultipleNontransactionalResultSets = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._SupportsQueryTimeout = false;
               if (initOne) {
                  break;
               }
            case 124:
               this._SupportsSchemaForGetColumns = false;
               if (initOne) {
                  break;
               }
            case 26:
               this._SupportsSchemaForGetTables = false;
               if (initOne) {
                  break;
               }
            case 137:
               this._SwapSchemaAndCatalog = true;
               if (initOne) {
                  break;
               }
            case 131:
               this._TimestampTypeName = "DATE";
               if (initOne) {
                  break;
               }
            case 125:
               this._TinyintTypeName = "INT8";
               if (initOne) {
                  break;
               }
            case 40:
               this._UseGetStringForClobs = true;
               if (initOne) {
                  break;
               }
            case 60:
               this._ValidationSQL = "SELECT FIRST 1 CURRENT TIMESTAMP FROM informix.systables";
               if (initOne) {
                  break;
               }
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 24:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 36:
            case 41:
            case 43:
            case 44:
            case 46:
            case 47:
            case 48:
            case 50:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 59:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 67:
            case 68:
            case 69:
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
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 110:
            case 111:
            case 112:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
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
            case 11:
            case 12:
            case 25:
            case 27:
            case 28:
            case 32:
            case 33:
            case 35:
            case 36:
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

               if (s.equals("double-type-name")) {
                  return 39;
               }
               break;
            case 17:
               if (s.equals("catalog-separator")) {
                  return 32;
               }

               if (s.equals("lock-mode-enabled")) {
                  return 136;
               }

               if (s.equals("lock-wait-seconds")) {
                  return 135;
               }

               if (s.equals("tinyint-type-name")) {
                  return 125;
               }
               break;
            case 18:
               if (s.equals("smallint-type-name")) {
                  return 42;
               }
               break;
            case 19:
               if (s.equals("timestamp-type-name")) {
                  return 131;
               }
               break;
            case 20:
               if (s.equals("constraint-name-mode")) {
                  return 51;
               }

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

               if (s.equals("supports-query-timeout")) {
                  return 10;
               }
               break;
            case 23:
               if (s.equals("swap-schema-and-catalog")) {
                  return 137;
               }
               break;
            case 24:
               if (s.equals("last-generated-key-query")) {
                  return 94;
               }

               if (s.equals("use-get-string-for-clobs")) {
                  return 40;
               }
               break;
            case 26:
               if (s.equals("max-constraint-name-length")) {
                  return 12;
               }
               break;
            case 29:
               if (s.equals("supports-deferred-constraints")) {
                  return 98;
               }
               break;
            case 30:
               if (s.equals("supports-schema-for-get-tables")) {
                  return 26;
               }
               break;
            case 31:
               if (s.equals("supports-schema-for-get-columns")) {
                  return 124;
               }
               break;
            case 34:
               if (s.equals("supports-locking-with-order-clause")) {
                  return 83;
               }
               break;
            case 37:
               if (s.equals("supports-locking-with-distinct-clause")) {
                  return 5;
               }

               if (s.equals("supports-locking-with-multiple-tables")) {
                  return 37;
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
            case 5:
               return "supports-locking-with-distinct-clause";
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 24:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 36:
            case 41:
            case 43:
            case 44:
            case 46:
            case 47:
            case 48:
            case 50:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 59:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 67:
            case 68:
            case 69:
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
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 110:
            case 111:
            case 112:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
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
            case 10:
               return "supports-query-timeout";
            case 12:
               return "max-constraint-name-length";
            case 22:
               return "long-varchar-type-name";
            case 25:
               return "date-type-name";
            case 26:
               return "supports-schema-for-get-tables";
            case 32:
               return "catalog-separator";
            case 37:
               return "supports-locking-with-multiple-tables";
            case 38:
               return "max-column-name-length";
            case 39:
               return "double-type-name";
            case 40:
               return "use-get-string-for-clobs";
            case 42:
               return "smallint-type-name";
            case 45:
               return "bit-type-name";
            case 49:
               return "supports-auto-assign";
            case 51:
               return "constraint-name-mode";
            case 57:
               return "auto-assign-type-name";
            case 60:
               return "validation-sql";
            case 66:
               return "supports-multiple-nontransactional-result-sets";
            case 70:
               return "float-type-name";
            case 76:
               return "blob-type-name";
            case 83:
               return "supports-locking-with-order-clause";
            case 84:
               return "platform";
            case 93:
               return "bigint-type-name";
            case 94:
               return "last-generated-key-query";
            case 98:
               return "supports-deferred-constraints";
            case 109:
               return "max-index-name-length";
            case 113:
               return "max-table-name-length";
            case 124:
               return "supports-schema-for-get-columns";
            case 125:
               return "tinyint-type-name";
            case 131:
               return "timestamp-type-name";
            case 135:
               return "lock-wait-seconds";
            case 136:
               return "lock-mode-enabled";
            case 137:
               return "swap-schema-and-catalog";
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
      private InformixDictionaryBeanImpl bean;

      protected Helper(InformixDictionaryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "ClobTypeName";
            case 5:
               return "SupportsLockingWithDistinctClause";
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 24:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 36:
            case 41:
            case 43:
            case 44:
            case 46:
            case 47:
            case 48:
            case 50:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 59:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 67:
            case 68:
            case 69:
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
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 110:
            case 111:
            case 112:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
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
            case 10:
               return "SupportsQueryTimeout";
            case 12:
               return "MaxConstraintNameLength";
            case 22:
               return "LongVarcharTypeName";
            case 25:
               return "DateTypeName";
            case 26:
               return "SupportsSchemaForGetTables";
            case 32:
               return "CatalogSeparator";
            case 37:
               return "SupportsLockingWithMultipleTables";
            case 38:
               return "MaxColumnNameLength";
            case 39:
               return "DoubleTypeName";
            case 40:
               return "UseGetStringForClobs";
            case 42:
               return "SmallintTypeName";
            case 45:
               return "BitTypeName";
            case 49:
               return "SupportsAutoAssign";
            case 51:
               return "ConstraintNameMode";
            case 57:
               return "AutoAssignTypeName";
            case 60:
               return "ValidationSQL";
            case 66:
               return "SupportsMultipleNontransactionalResultSets";
            case 70:
               return "FloatTypeName";
            case 76:
               return "BlobTypeName";
            case 83:
               return "SupportsLockingWithOrderClause";
            case 84:
               return "Platform";
            case 93:
               return "BigintTypeName";
            case 94:
               return "LastGeneratedKeyQuery";
            case 98:
               return "SupportsDeferredConstraints";
            case 109:
               return "MaxIndexNameLength";
            case 113:
               return "MaxTableNameLength";
            case 124:
               return "SupportsSchemaForGetColumns";
            case 125:
               return "TinyintTypeName";
            case 131:
               return "TimestampTypeName";
            case 135:
               return "LockWaitSeconds";
            case 136:
               return "LockModeEnabled";
            case 137:
               return "SwapSchemaAndCatalog";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AutoAssignTypeName")) {
            return 57;
         } else if (propName.equals("BigintTypeName")) {
            return 93;
         } else if (propName.equals("BitTypeName")) {
            return 45;
         } else if (propName.equals("BlobTypeName")) {
            return 76;
         } else if (propName.equals("CatalogSeparator")) {
            return 32;
         } else if (propName.equals("ClobTypeName")) {
            return 4;
         } else if (propName.equals("ConstraintNameMode")) {
            return 51;
         } else if (propName.equals("DateTypeName")) {
            return 25;
         } else if (propName.equals("DoubleTypeName")) {
            return 39;
         } else if (propName.equals("FloatTypeName")) {
            return 70;
         } else if (propName.equals("LastGeneratedKeyQuery")) {
            return 94;
         } else if (propName.equals("LockModeEnabled")) {
            return 136;
         } else if (propName.equals("LockWaitSeconds")) {
            return 135;
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
         } else if (propName.equals("SmallintTypeName")) {
            return 42;
         } else if (propName.equals("SupportsAutoAssign")) {
            return 49;
         } else if (propName.equals("SupportsDeferredConstraints")) {
            return 98;
         } else if (propName.equals("SupportsLockingWithDistinctClause")) {
            return 5;
         } else if (propName.equals("SupportsLockingWithMultipleTables")) {
            return 37;
         } else if (propName.equals("SupportsLockingWithOrderClause")) {
            return 83;
         } else if (propName.equals("SupportsMultipleNontransactionalResultSets")) {
            return 66;
         } else if (propName.equals("SupportsQueryTimeout")) {
            return 10;
         } else if (propName.equals("SupportsSchemaForGetColumns")) {
            return 124;
         } else if (propName.equals("SupportsSchemaForGetTables")) {
            return 26;
         } else if (propName.equals("SwapSchemaAndCatalog")) {
            return 137;
         } else if (propName.equals("TimestampTypeName")) {
            return 131;
         } else if (propName.equals("TinyintTypeName")) {
            return 125;
         } else if (propName.equals("UseGetStringForClobs")) {
            return 40;
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
            if (this.bean.isAutoAssignTypeNameSet()) {
               buf.append("AutoAssignTypeName");
               buf.append(String.valueOf(this.bean.getAutoAssignTypeName()));
            }

            if (this.bean.isBigintTypeNameSet()) {
               buf.append("BigintTypeName");
               buf.append(String.valueOf(this.bean.getBigintTypeName()));
            }

            if (this.bean.isBitTypeNameSet()) {
               buf.append("BitTypeName");
               buf.append(String.valueOf(this.bean.getBitTypeName()));
            }

            if (this.bean.isBlobTypeNameSet()) {
               buf.append("BlobTypeName");
               buf.append(String.valueOf(this.bean.getBlobTypeName()));
            }

            if (this.bean.isCatalogSeparatorSet()) {
               buf.append("CatalogSeparator");
               buf.append(String.valueOf(this.bean.getCatalogSeparator()));
            }

            if (this.bean.isClobTypeNameSet()) {
               buf.append("ClobTypeName");
               buf.append(String.valueOf(this.bean.getClobTypeName()));
            }

            if (this.bean.isConstraintNameModeSet()) {
               buf.append("ConstraintNameMode");
               buf.append(String.valueOf(this.bean.getConstraintNameMode()));
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

            if (this.bean.isLastGeneratedKeyQuerySet()) {
               buf.append("LastGeneratedKeyQuery");
               buf.append(String.valueOf(this.bean.getLastGeneratedKeyQuery()));
            }

            if (this.bean.isLockModeEnabledSet()) {
               buf.append("LockModeEnabled");
               buf.append(String.valueOf(this.bean.getLockModeEnabled()));
            }

            if (this.bean.isLockWaitSecondsSet()) {
               buf.append("LockWaitSeconds");
               buf.append(String.valueOf(this.bean.getLockWaitSeconds()));
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

            if (this.bean.isSmallintTypeNameSet()) {
               buf.append("SmallintTypeName");
               buf.append(String.valueOf(this.bean.getSmallintTypeName()));
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

            if (this.bean.isSupportsLockingWithMultipleTablesSet()) {
               buf.append("SupportsLockingWithMultipleTables");
               buf.append(String.valueOf(this.bean.getSupportsLockingWithMultipleTables()));
            }

            if (this.bean.isSupportsLockingWithOrderClauseSet()) {
               buf.append("SupportsLockingWithOrderClause");
               buf.append(String.valueOf(this.bean.getSupportsLockingWithOrderClause()));
            }

            if (this.bean.isSupportsMultipleNontransactionalResultSetsSet()) {
               buf.append("SupportsMultipleNontransactionalResultSets");
               buf.append(String.valueOf(this.bean.getSupportsMultipleNontransactionalResultSets()));
            }

            if (this.bean.isSupportsQueryTimeoutSet()) {
               buf.append("SupportsQueryTimeout");
               buf.append(String.valueOf(this.bean.getSupportsQueryTimeout()));
            }

            if (this.bean.isSupportsSchemaForGetColumnsSet()) {
               buf.append("SupportsSchemaForGetColumns");
               buf.append(String.valueOf(this.bean.getSupportsSchemaForGetColumns()));
            }

            if (this.bean.isSupportsSchemaForGetTablesSet()) {
               buf.append("SupportsSchemaForGetTables");
               buf.append(String.valueOf(this.bean.getSupportsSchemaForGetTables()));
            }

            if (this.bean.isSwapSchemaAndCatalogSet()) {
               buf.append("SwapSchemaAndCatalog");
               buf.append(String.valueOf(this.bean.getSwapSchemaAndCatalog()));
            }

            if (this.bean.isTimestampTypeNameSet()) {
               buf.append("TimestampTypeName");
               buf.append(String.valueOf(this.bean.getTimestampTypeName()));
            }

            if (this.bean.isTinyintTypeNameSet()) {
               buf.append("TinyintTypeName");
               buf.append(String.valueOf(this.bean.getTinyintTypeName()));
            }

            if (this.bean.isUseGetStringForClobsSet()) {
               buf.append("UseGetStringForClobs");
               buf.append(String.valueOf(this.bean.getUseGetStringForClobs()));
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
            InformixDictionaryBeanImpl otherTyped = (InformixDictionaryBeanImpl)other;
            this.computeDiff("AutoAssignTypeName", this.bean.getAutoAssignTypeName(), otherTyped.getAutoAssignTypeName(), false);
            this.computeDiff("BigintTypeName", this.bean.getBigintTypeName(), otherTyped.getBigintTypeName(), false);
            this.computeDiff("BitTypeName", this.bean.getBitTypeName(), otherTyped.getBitTypeName(), false);
            this.computeDiff("BlobTypeName", this.bean.getBlobTypeName(), otherTyped.getBlobTypeName(), false);
            this.computeDiff("CatalogSeparator", this.bean.getCatalogSeparator(), otherTyped.getCatalogSeparator(), false);
            this.computeDiff("ClobTypeName", this.bean.getClobTypeName(), otherTyped.getClobTypeName(), false);
            this.computeDiff("ConstraintNameMode", this.bean.getConstraintNameMode(), otherTyped.getConstraintNameMode(), false);
            this.computeDiff("DateTypeName", this.bean.getDateTypeName(), otherTyped.getDateTypeName(), false);
            this.computeDiff("DoubleTypeName", this.bean.getDoubleTypeName(), otherTyped.getDoubleTypeName(), false);
            this.computeDiff("FloatTypeName", this.bean.getFloatTypeName(), otherTyped.getFloatTypeName(), false);
            this.computeDiff("LastGeneratedKeyQuery", this.bean.getLastGeneratedKeyQuery(), otherTyped.getLastGeneratedKeyQuery(), false);
            this.computeDiff("LockModeEnabled", this.bean.getLockModeEnabled(), otherTyped.getLockModeEnabled(), false);
            this.computeDiff("LockWaitSeconds", this.bean.getLockWaitSeconds(), otherTyped.getLockWaitSeconds(), false);
            this.computeDiff("LongVarcharTypeName", this.bean.getLongVarcharTypeName(), otherTyped.getLongVarcharTypeName(), false);
            this.computeDiff("MaxColumnNameLength", this.bean.getMaxColumnNameLength(), otherTyped.getMaxColumnNameLength(), false);
            this.computeDiff("MaxConstraintNameLength", this.bean.getMaxConstraintNameLength(), otherTyped.getMaxConstraintNameLength(), false);
            this.computeDiff("MaxIndexNameLength", this.bean.getMaxIndexNameLength(), otherTyped.getMaxIndexNameLength(), false);
            this.computeDiff("MaxTableNameLength", this.bean.getMaxTableNameLength(), otherTyped.getMaxTableNameLength(), false);
            this.computeDiff("Platform", this.bean.getPlatform(), otherTyped.getPlatform(), false);
            this.computeDiff("SmallintTypeName", this.bean.getSmallintTypeName(), otherTyped.getSmallintTypeName(), false);
            this.computeDiff("SupportsAutoAssign", this.bean.getSupportsAutoAssign(), otherTyped.getSupportsAutoAssign(), false);
            this.computeDiff("SupportsDeferredConstraints", this.bean.getSupportsDeferredConstraints(), otherTyped.getSupportsDeferredConstraints(), false);
            this.computeDiff("SupportsLockingWithDistinctClause", this.bean.getSupportsLockingWithDistinctClause(), otherTyped.getSupportsLockingWithDistinctClause(), false);
            this.computeDiff("SupportsLockingWithMultipleTables", this.bean.getSupportsLockingWithMultipleTables(), otherTyped.getSupportsLockingWithMultipleTables(), false);
            this.computeDiff("SupportsLockingWithOrderClause", this.bean.getSupportsLockingWithOrderClause(), otherTyped.getSupportsLockingWithOrderClause(), false);
            this.computeDiff("SupportsMultipleNontransactionalResultSets", this.bean.getSupportsMultipleNontransactionalResultSets(), otherTyped.getSupportsMultipleNontransactionalResultSets(), false);
            this.computeDiff("SupportsQueryTimeout", this.bean.getSupportsQueryTimeout(), otherTyped.getSupportsQueryTimeout(), false);
            this.computeDiff("SupportsSchemaForGetColumns", this.bean.getSupportsSchemaForGetColumns(), otherTyped.getSupportsSchemaForGetColumns(), false);
            this.computeDiff("SupportsSchemaForGetTables", this.bean.getSupportsSchemaForGetTables(), otherTyped.getSupportsSchemaForGetTables(), false);
            this.computeDiff("SwapSchemaAndCatalog", this.bean.getSwapSchemaAndCatalog(), otherTyped.getSwapSchemaAndCatalog(), false);
            this.computeDiff("TimestampTypeName", this.bean.getTimestampTypeName(), otherTyped.getTimestampTypeName(), false);
            this.computeDiff("TinyintTypeName", this.bean.getTinyintTypeName(), otherTyped.getTinyintTypeName(), false);
            this.computeDiff("UseGetStringForClobs", this.bean.getUseGetStringForClobs(), otherTyped.getUseGetStringForClobs(), false);
            this.computeDiff("ValidationSQL", this.bean.getValidationSQL(), otherTyped.getValidationSQL(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InformixDictionaryBeanImpl original = (InformixDictionaryBeanImpl)event.getSourceBean();
            InformixDictionaryBeanImpl proposed = (InformixDictionaryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AutoAssignTypeName")) {
                  original.setAutoAssignTypeName(proposed.getAutoAssignTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 57);
               } else if (prop.equals("BigintTypeName")) {
                  original.setBigintTypeName(proposed.getBigintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 93);
               } else if (prop.equals("BitTypeName")) {
                  original.setBitTypeName(proposed.getBitTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (prop.equals("BlobTypeName")) {
                  original.setBlobTypeName(proposed.getBlobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 76);
               } else if (prop.equals("CatalogSeparator")) {
                  original.setCatalogSeparator(proposed.getCatalogSeparator());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("ClobTypeName")) {
                  original.setClobTypeName(proposed.getClobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ConstraintNameMode")) {
                  original.setConstraintNameMode(proposed.getConstraintNameMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 51);
               } else if (prop.equals("DateTypeName")) {
                  original.setDateTypeName(proposed.getDateTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("DoubleTypeName")) {
                  original.setDoubleTypeName(proposed.getDoubleTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("FloatTypeName")) {
                  original.setFloatTypeName(proposed.getFloatTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 70);
               } else if (prop.equals("LastGeneratedKeyQuery")) {
                  original.setLastGeneratedKeyQuery(proposed.getLastGeneratedKeyQuery());
                  original._conditionalUnset(update.isUnsetUpdate(), 94);
               } else if (prop.equals("LockModeEnabled")) {
                  original.setLockModeEnabled(proposed.getLockModeEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 136);
               } else if (prop.equals("LockWaitSeconds")) {
                  original.setLockWaitSeconds(proposed.getLockWaitSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 135);
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
               } else if (prop.equals("SmallintTypeName")) {
                  original.setSmallintTypeName(proposed.getSmallintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (prop.equals("SupportsAutoAssign")) {
                  original.setSupportsAutoAssign(proposed.getSupportsAutoAssign());
                  original._conditionalUnset(update.isUnsetUpdate(), 49);
               } else if (prop.equals("SupportsDeferredConstraints")) {
                  original.setSupportsDeferredConstraints(proposed.getSupportsDeferredConstraints());
                  original._conditionalUnset(update.isUnsetUpdate(), 98);
               } else if (prop.equals("SupportsLockingWithDistinctClause")) {
                  original.setSupportsLockingWithDistinctClause(proposed.getSupportsLockingWithDistinctClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("SupportsLockingWithMultipleTables")) {
                  original.setSupportsLockingWithMultipleTables(proposed.getSupportsLockingWithMultipleTables());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("SupportsLockingWithOrderClause")) {
                  original.setSupportsLockingWithOrderClause(proposed.getSupportsLockingWithOrderClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 83);
               } else if (prop.equals("SupportsMultipleNontransactionalResultSets")) {
                  original.setSupportsMultipleNontransactionalResultSets(proposed.getSupportsMultipleNontransactionalResultSets());
                  original._conditionalUnset(update.isUnsetUpdate(), 66);
               } else if (prop.equals("SupportsQueryTimeout")) {
                  original.setSupportsQueryTimeout(proposed.getSupportsQueryTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("SupportsSchemaForGetColumns")) {
                  original.setSupportsSchemaForGetColumns(proposed.getSupportsSchemaForGetColumns());
                  original._conditionalUnset(update.isUnsetUpdate(), 124);
               } else if (prop.equals("SupportsSchemaForGetTables")) {
                  original.setSupportsSchemaForGetTables(proposed.getSupportsSchemaForGetTables());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("SwapSchemaAndCatalog")) {
                  original.setSwapSchemaAndCatalog(proposed.getSwapSchemaAndCatalog());
                  original._conditionalUnset(update.isUnsetUpdate(), 137);
               } else if (prop.equals("TimestampTypeName")) {
                  original.setTimestampTypeName(proposed.getTimestampTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 131);
               } else if (prop.equals("TinyintTypeName")) {
                  original.setTinyintTypeName(proposed.getTinyintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 125);
               } else if (prop.equals("UseGetStringForClobs")) {
                  original.setUseGetStringForClobs(proposed.getUseGetStringForClobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 40);
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
            InformixDictionaryBeanImpl copy = (InformixDictionaryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AutoAssignTypeName")) && this.bean.isAutoAssignTypeNameSet()) {
               copy.setAutoAssignTypeName(this.bean.getAutoAssignTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BigintTypeName")) && this.bean.isBigintTypeNameSet()) {
               copy.setBigintTypeName(this.bean.getBigintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BitTypeName")) && this.bean.isBitTypeNameSet()) {
               copy.setBitTypeName(this.bean.getBitTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BlobTypeName")) && this.bean.isBlobTypeNameSet()) {
               copy.setBlobTypeName(this.bean.getBlobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("CatalogSeparator")) && this.bean.isCatalogSeparatorSet()) {
               copy.setCatalogSeparator(this.bean.getCatalogSeparator());
            }

            if ((excludeProps == null || !excludeProps.contains("ClobTypeName")) && this.bean.isClobTypeNameSet()) {
               copy.setClobTypeName(this.bean.getClobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConstraintNameMode")) && this.bean.isConstraintNameModeSet()) {
               copy.setConstraintNameMode(this.bean.getConstraintNameMode());
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

            if ((excludeProps == null || !excludeProps.contains("LastGeneratedKeyQuery")) && this.bean.isLastGeneratedKeyQuerySet()) {
               copy.setLastGeneratedKeyQuery(this.bean.getLastGeneratedKeyQuery());
            }

            if ((excludeProps == null || !excludeProps.contains("LockModeEnabled")) && this.bean.isLockModeEnabledSet()) {
               copy.setLockModeEnabled(this.bean.getLockModeEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("LockWaitSeconds")) && this.bean.isLockWaitSecondsSet()) {
               copy.setLockWaitSeconds(this.bean.getLockWaitSeconds());
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

            if ((excludeProps == null || !excludeProps.contains("SmallintTypeName")) && this.bean.isSmallintTypeNameSet()) {
               copy.setSmallintTypeName(this.bean.getSmallintTypeName());
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

            if ((excludeProps == null || !excludeProps.contains("SupportsLockingWithMultipleTables")) && this.bean.isSupportsLockingWithMultipleTablesSet()) {
               copy.setSupportsLockingWithMultipleTables(this.bean.getSupportsLockingWithMultipleTables());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsLockingWithOrderClause")) && this.bean.isSupportsLockingWithOrderClauseSet()) {
               copy.setSupportsLockingWithOrderClause(this.bean.getSupportsLockingWithOrderClause());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsMultipleNontransactionalResultSets")) && this.bean.isSupportsMultipleNontransactionalResultSetsSet()) {
               copy.setSupportsMultipleNontransactionalResultSets(this.bean.getSupportsMultipleNontransactionalResultSets());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsQueryTimeout")) && this.bean.isSupportsQueryTimeoutSet()) {
               copy.setSupportsQueryTimeout(this.bean.getSupportsQueryTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSchemaForGetColumns")) && this.bean.isSupportsSchemaForGetColumnsSet()) {
               copy.setSupportsSchemaForGetColumns(this.bean.getSupportsSchemaForGetColumns());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSchemaForGetTables")) && this.bean.isSupportsSchemaForGetTablesSet()) {
               copy.setSupportsSchemaForGetTables(this.bean.getSupportsSchemaForGetTables());
            }

            if ((excludeProps == null || !excludeProps.contains("SwapSchemaAndCatalog")) && this.bean.isSwapSchemaAndCatalogSet()) {
               copy.setSwapSchemaAndCatalog(this.bean.getSwapSchemaAndCatalog());
            }

            if ((excludeProps == null || !excludeProps.contains("TimestampTypeName")) && this.bean.isTimestampTypeNameSet()) {
               copy.setTimestampTypeName(this.bean.getTimestampTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("TinyintTypeName")) && this.bean.isTinyintTypeNameSet()) {
               copy.setTinyintTypeName(this.bean.getTinyintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("UseGetStringForClobs")) && this.bean.isUseGetStringForClobsSet()) {
               copy.setUseGetStringForClobs(this.bean.getUseGetStringForClobs());
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
