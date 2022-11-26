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

public class OracleDictionaryBeanImpl extends BuiltInDBDictionaryBeanImpl implements OracleDictionaryBean, Serializable {
   private String _AutoAssignSequenceName;
   private String _BigintTypeName;
   private String _BinaryTypeName;
   private String _BitTypeName;
   private String _DecimalTypeName;
   private String _DoubleTypeName;
   private String _IntegerTypeName;
   private String _JoinSyntax;
   private String _LongVarbinaryTypeName;
   private String _LongVarcharTypeName;
   private int _MaxColumnNameLength;
   private int _MaxConstraintNameLength;
   private int _MaxEmbeddedBlobSize;
   private int _MaxEmbeddedClobSize;
   private int _MaxIndexNameLength;
   private int _MaxTableNameLength;
   private String _NextSequenceQuery;
   private String _NumericTypeName;
   private boolean _Openjpa3GeneratedKeyNames;
   private String _Platform;
   private String _SmallintTypeName;
   private String _StringLengthFunction;
   private boolean _SupportsDeferredConstraints;
   private boolean _SupportsLockingWithDistinctClause;
   private boolean _SupportsSelectEndIndex;
   private boolean _SupportsSelectStartIndex;
   private String _TimeTypeName;
   private String _TinyintTypeName;
   private boolean _UseSetFormOfUseForUnicode;
   private boolean _UseTriggersForAutoAssign;
   private String _ValidationSQL;
   private String _VarcharTypeName;
   private static SchemaHelper2 _schemaHelper;

   public OracleDictionaryBeanImpl() {
      this._initializeProperty(-1);
   }

   public OracleDictionaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public OracleDictionaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public boolean getUseSetFormOfUseForUnicode() {
      return this._UseSetFormOfUseForUnicode;
   }

   public boolean isUseSetFormOfUseForUnicodeInherited() {
      return false;
   }

   public boolean isUseSetFormOfUseForUnicodeSet() {
      return this._isSet(135);
   }

   public void setUseSetFormOfUseForUnicode(boolean param0) {
      boolean _oldVal = this._UseSetFormOfUseForUnicode;
      this._UseSetFormOfUseForUnicode = param0;
      this._postSet(135, _oldVal, param0);
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

   public boolean getUseTriggersForAutoAssign() {
      return this._UseTriggersForAutoAssign;
   }

   public boolean isUseTriggersForAutoAssignInherited() {
      return false;
   }

   public boolean isUseTriggersForAutoAssignSet() {
      return this._isSet(136);
   }

   public void setUseTriggersForAutoAssign(boolean param0) {
      boolean _oldVal = this._UseTriggersForAutoAssign;
      this._UseTriggersForAutoAssign = param0;
      this._postSet(136, _oldVal, param0);
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

   public int getMaxEmbeddedClobSize() {
      return this._MaxEmbeddedClobSize;
   }

   public boolean getOpenjpa3GeneratedKeyNames() {
      return this._Openjpa3GeneratedKeyNames;
   }

   public boolean isMaxEmbeddedClobSizeInherited() {
      return false;
   }

   public boolean isMaxEmbeddedClobSizeSet() {
      return this._isSet(24);
   }

   public boolean isOpenjpa3GeneratedKeyNamesInherited() {
      return false;
   }

   public boolean isOpenjpa3GeneratedKeyNamesSet() {
      return this._isSet(137);
   }

   public void setMaxEmbeddedClobSize(int param0) {
      int _oldVal = this._MaxEmbeddedClobSize;
      this._MaxEmbeddedClobSize = param0;
      this._postSet(24, _oldVal, param0);
   }

   public void setOpenjpa3GeneratedKeyNames(boolean param0) {
      boolean _oldVal = this._Openjpa3GeneratedKeyNames;
      this._Openjpa3GeneratedKeyNames = param0;
      this._postSet(137, _oldVal, param0);
   }

   public String getAutoAssignSequenceName() {
      return this._AutoAssignSequenceName;
   }

   public boolean isAutoAssignSequenceNameInherited() {
      return false;
   }

   public boolean isAutoAssignSequenceNameSet() {
      return this._isSet(138);
   }

   public void setAutoAssignSequenceName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AutoAssignSequenceName;
      this._AutoAssignSequenceName = param0;
      this._postSet(138, _oldVal, param0);
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
         idx = 138;
      }

      try {
         switch (idx) {
            case 138:
               this._AutoAssignSequenceName = null;
               if (initOne) {
                  break;
               }
            case 93:
               this._BigintTypeName = "NUMBER{0}";
               if (initOne) {
                  break;
               }
            case 3:
               this._BinaryTypeName = "BLOB";
               if (initOne) {
                  break;
               }
            case 45:
               this._BitTypeName = "NUMBER{0}";
               if (initOne) {
                  break;
               }
            case 41:
               this._DecimalTypeName = "NUMBER{0}";
               if (initOne) {
                  break;
               }
            case 39:
               this._DoubleTypeName = "NUMBER{0}";
               if (initOne) {
                  break;
               }
            case 75:
               this._IntegerTypeName = "NUMBER{0}";
               if (initOne) {
                  break;
               }
            case 116:
               this._JoinSyntax = "database";
               if (initOne) {
                  break;
               }
            case 16:
               this._LongVarbinaryTypeName = "BLOB";
               if (initOne) {
                  break;
               }
            case 22:
               this._LongVarcharTypeName = "LONG";
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
            case 117:
               this._MaxEmbeddedBlobSize = 4000;
               if (initOne) {
                  break;
               }
            case 24:
               this._MaxEmbeddedClobSize = 4000;
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
            case 21:
               this._NextSequenceQuery = "SELECT {0}.NEXTVAL FROM DUAL";
               if (initOne) {
                  break;
               }
            case 73:
               this._NumericTypeName = "NUMBER{0}";
               if (initOne) {
                  break;
               }
            case 137:
               this._Openjpa3GeneratedKeyNames = false;
               if (initOne) {
                  break;
               }
            case 84:
               this._Platform = "Oracle";
               if (initOne) {
                  break;
               }
            case 42:
               this._SmallintTypeName = "NUMBER{0}";
               if (initOne) {
                  break;
               }
            case 15:
               this._StringLengthFunction = "LENGTH({0})";
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
            case 56:
               this._TimeTypeName = "DATE";
               if (initOne) {
                  break;
               }
            case 125:
               this._TinyintTypeName = "NUMBER{0}";
               if (initOne) {
                  break;
               }
            case 135:
               this._UseSetFormOfUseForUnicode = true;
               if (initOne) {
                  break;
               }
            case 136:
               this._UseTriggersForAutoAssign = false;
               if (initOne) {
                  break;
               }
            case 60:
               this._ValidationSQL = "SELECT SYSDATE FROM DUAL";
               if (initOne) {
                  break;
               }
            case 62:
               this._VarcharTypeName = "VARCHAR2{0}";
               if (initOne) {
                  break;
               }
            case 4:
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
            case 19:
            case 20:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 40:
            case 43:
            case 44:
            case 46:
            case 47:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 57:
            case 58:
            case 59:
            case 61:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 74:
            case 76:
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
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 94:
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
            case 12:
            case 15:
            case 20:
            case 23:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            default:
               break;
            case 11:
               if (s.equals("join-syntax")) {
                  return 116;
               }
               break;
            case 13:
               if (s.equals("bit-type-name")) {
                  return 45;
               }
               break;
            case 14:
               if (s.equals("time-type-name")) {
                  return 56;
               }

               if (s.equals("validation-sql")) {
                  return 60;
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
               if (s.equals("decimal-type-name")) {
                  return 41;
               }

               if (s.equals("integer-type-name")) {
                  return 75;
               }

               if (s.equals("numeric-type-name")) {
                  return 73;
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
               break;
            case 21:
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

               if (s.equals("max-embedded-blob-size")) {
                  return 117;
               }

               if (s.equals("max-embedded-clob-size")) {
                  return 24;
               }

               if (s.equals("string-length-function")) {
                  return 15;
               }
               break;
            case 24:
               if (s.equals("long-varbinary-type-name")) {
                  return 16;
               }
               break;
            case 25:
               if (s.equals("auto-assign-sequence-name")) {
                  return 138;
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
               if (s.equals("supports-select-start-index")) {
                  return 119;
               }
               break;
            case 28:
               if (s.equals("openjpa3-generated-key-names")) {
                  return 137;
               }

               if (s.equals("use-triggers-for-auto-assign")) {
                  return 136;
               }
               break;
            case 29:
               if (s.equals("supports-deferred-constraints")) {
                  return 98;
               }
               break;
            case 31:
               if (s.equals("use-set-form-of-use-for-unicode")) {
                  return 135;
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
            case 19:
            case 20:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 40:
            case 43:
            case 44:
            case 46:
            case 47:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 57:
            case 58:
            case 59:
            case 61:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 74:
            case 76:
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
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 94:
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
            case 131:
            case 132:
            case 133:
            case 134:
            default:
               return super.getElementName(propIndex);
            case 5:
               return "supports-locking-with-distinct-clause";
            case 12:
               return "max-constraint-name-length";
            case 15:
               return "string-length-function";
            case 16:
               return "long-varbinary-type-name";
            case 21:
               return "next-sequence-query";
            case 22:
               return "long-varchar-type-name";
            case 24:
               return "max-embedded-clob-size";
            case 38:
               return "max-column-name-length";
            case 39:
               return "double-type-name";
            case 41:
               return "decimal-type-name";
            case 42:
               return "smallint-type-name";
            case 45:
               return "bit-type-name";
            case 48:
               return "supports-select-end-index";
            case 56:
               return "time-type-name";
            case 60:
               return "validation-sql";
            case 62:
               return "varchar-type-name";
            case 73:
               return "numeric-type-name";
            case 75:
               return "integer-type-name";
            case 84:
               return "platform";
            case 93:
               return "bigint-type-name";
            case 98:
               return "supports-deferred-constraints";
            case 109:
               return "max-index-name-length";
            case 113:
               return "max-table-name-length";
            case 116:
               return "join-syntax";
            case 117:
               return "max-embedded-blob-size";
            case 119:
               return "supports-select-start-index";
            case 125:
               return "tinyint-type-name";
            case 135:
               return "use-set-form-of-use-for-unicode";
            case 136:
               return "use-triggers-for-auto-assign";
            case 137:
               return "openjpa3-generated-key-names";
            case 138:
               return "auto-assign-sequence-name";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends BuiltInDBDictionaryBeanImpl.Helper {
      private OracleDictionaryBeanImpl bean;

      protected Helper(OracleDictionaryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "BinaryTypeName";
            case 4:
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
            case 19:
            case 20:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 40:
            case 43:
            case 44:
            case 46:
            case 47:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 57:
            case 58:
            case 59:
            case 61:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 74:
            case 76:
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
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 94:
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
            case 131:
            case 132:
            case 133:
            case 134:
            default:
               return super.getPropertyName(propIndex);
            case 5:
               return "SupportsLockingWithDistinctClause";
            case 12:
               return "MaxConstraintNameLength";
            case 15:
               return "StringLengthFunction";
            case 16:
               return "LongVarbinaryTypeName";
            case 21:
               return "NextSequenceQuery";
            case 22:
               return "LongVarcharTypeName";
            case 24:
               return "MaxEmbeddedClobSize";
            case 38:
               return "MaxColumnNameLength";
            case 39:
               return "DoubleTypeName";
            case 41:
               return "DecimalTypeName";
            case 42:
               return "SmallintTypeName";
            case 45:
               return "BitTypeName";
            case 48:
               return "SupportsSelectEndIndex";
            case 56:
               return "TimeTypeName";
            case 60:
               return "ValidationSQL";
            case 62:
               return "VarcharTypeName";
            case 73:
               return "NumericTypeName";
            case 75:
               return "IntegerTypeName";
            case 84:
               return "Platform";
            case 93:
               return "BigintTypeName";
            case 98:
               return "SupportsDeferredConstraints";
            case 109:
               return "MaxIndexNameLength";
            case 113:
               return "MaxTableNameLength";
            case 116:
               return "JoinSyntax";
            case 117:
               return "MaxEmbeddedBlobSize";
            case 119:
               return "SupportsSelectStartIndex";
            case 125:
               return "TinyintTypeName";
            case 135:
               return "UseSetFormOfUseForUnicode";
            case 136:
               return "UseTriggersForAutoAssign";
            case 137:
               return "Openjpa3GeneratedKeyNames";
            case 138:
               return "AutoAssignSequenceName";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AutoAssignSequenceName")) {
            return 138;
         } else if (propName.equals("BigintTypeName")) {
            return 93;
         } else if (propName.equals("BinaryTypeName")) {
            return 3;
         } else if (propName.equals("BitTypeName")) {
            return 45;
         } else if (propName.equals("DecimalTypeName")) {
            return 41;
         } else if (propName.equals("DoubleTypeName")) {
            return 39;
         } else if (propName.equals("IntegerTypeName")) {
            return 75;
         } else if (propName.equals("JoinSyntax")) {
            return 116;
         } else if (propName.equals("LongVarbinaryTypeName")) {
            return 16;
         } else if (propName.equals("LongVarcharTypeName")) {
            return 22;
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
         } else if (propName.equals("MaxTableNameLength")) {
            return 113;
         } else if (propName.equals("NextSequenceQuery")) {
            return 21;
         } else if (propName.equals("NumericTypeName")) {
            return 73;
         } else if (propName.equals("Openjpa3GeneratedKeyNames")) {
            return 137;
         } else if (propName.equals("Platform")) {
            return 84;
         } else if (propName.equals("SmallintTypeName")) {
            return 42;
         } else if (propName.equals("StringLengthFunction")) {
            return 15;
         } else if (propName.equals("SupportsDeferredConstraints")) {
            return 98;
         } else if (propName.equals("SupportsLockingWithDistinctClause")) {
            return 5;
         } else if (propName.equals("SupportsSelectEndIndex")) {
            return 48;
         } else if (propName.equals("SupportsSelectStartIndex")) {
            return 119;
         } else if (propName.equals("TimeTypeName")) {
            return 56;
         } else if (propName.equals("TinyintTypeName")) {
            return 125;
         } else if (propName.equals("UseSetFormOfUseForUnicode")) {
            return 135;
         } else if (propName.equals("UseTriggersForAutoAssign")) {
            return 136;
         } else if (propName.equals("ValidationSQL")) {
            return 60;
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
            if (this.bean.isAutoAssignSequenceNameSet()) {
               buf.append("AutoAssignSequenceName");
               buf.append(String.valueOf(this.bean.getAutoAssignSequenceName()));
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

            if (this.bean.isDecimalTypeNameSet()) {
               buf.append("DecimalTypeName");
               buf.append(String.valueOf(this.bean.getDecimalTypeName()));
            }

            if (this.bean.isDoubleTypeNameSet()) {
               buf.append("DoubleTypeName");
               buf.append(String.valueOf(this.bean.getDoubleTypeName()));
            }

            if (this.bean.isIntegerTypeNameSet()) {
               buf.append("IntegerTypeName");
               buf.append(String.valueOf(this.bean.getIntegerTypeName()));
            }

            if (this.bean.isJoinSyntaxSet()) {
               buf.append("JoinSyntax");
               buf.append(String.valueOf(this.bean.getJoinSyntax()));
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

            if (this.bean.isMaxTableNameLengthSet()) {
               buf.append("MaxTableNameLength");
               buf.append(String.valueOf(this.bean.getMaxTableNameLength()));
            }

            if (this.bean.isNextSequenceQuerySet()) {
               buf.append("NextSequenceQuery");
               buf.append(String.valueOf(this.bean.getNextSequenceQuery()));
            }

            if (this.bean.isNumericTypeNameSet()) {
               buf.append("NumericTypeName");
               buf.append(String.valueOf(this.bean.getNumericTypeName()));
            }

            if (this.bean.isOpenjpa3GeneratedKeyNamesSet()) {
               buf.append("Openjpa3GeneratedKeyNames");
               buf.append(String.valueOf(this.bean.getOpenjpa3GeneratedKeyNames()));
            }

            if (this.bean.isPlatformSet()) {
               buf.append("Platform");
               buf.append(String.valueOf(this.bean.getPlatform()));
            }

            if (this.bean.isSmallintTypeNameSet()) {
               buf.append("SmallintTypeName");
               buf.append(String.valueOf(this.bean.getSmallintTypeName()));
            }

            if (this.bean.isStringLengthFunctionSet()) {
               buf.append("StringLengthFunction");
               buf.append(String.valueOf(this.bean.getStringLengthFunction()));
            }

            if (this.bean.isSupportsDeferredConstraintsSet()) {
               buf.append("SupportsDeferredConstraints");
               buf.append(String.valueOf(this.bean.getSupportsDeferredConstraints()));
            }

            if (this.bean.isSupportsLockingWithDistinctClauseSet()) {
               buf.append("SupportsLockingWithDistinctClause");
               buf.append(String.valueOf(this.bean.getSupportsLockingWithDistinctClause()));
            }

            if (this.bean.isSupportsSelectEndIndexSet()) {
               buf.append("SupportsSelectEndIndex");
               buf.append(String.valueOf(this.bean.getSupportsSelectEndIndex()));
            }

            if (this.bean.isSupportsSelectStartIndexSet()) {
               buf.append("SupportsSelectStartIndex");
               buf.append(String.valueOf(this.bean.getSupportsSelectStartIndex()));
            }

            if (this.bean.isTimeTypeNameSet()) {
               buf.append("TimeTypeName");
               buf.append(String.valueOf(this.bean.getTimeTypeName()));
            }

            if (this.bean.isTinyintTypeNameSet()) {
               buf.append("TinyintTypeName");
               buf.append(String.valueOf(this.bean.getTinyintTypeName()));
            }

            if (this.bean.isUseSetFormOfUseForUnicodeSet()) {
               buf.append("UseSetFormOfUseForUnicode");
               buf.append(String.valueOf(this.bean.getUseSetFormOfUseForUnicode()));
            }

            if (this.bean.isUseTriggersForAutoAssignSet()) {
               buf.append("UseTriggersForAutoAssign");
               buf.append(String.valueOf(this.bean.getUseTriggersForAutoAssign()));
            }

            if (this.bean.isValidationSQLSet()) {
               buf.append("ValidationSQL");
               buf.append(String.valueOf(this.bean.getValidationSQL()));
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
            OracleDictionaryBeanImpl otherTyped = (OracleDictionaryBeanImpl)other;
            this.computeDiff("AutoAssignSequenceName", this.bean.getAutoAssignSequenceName(), otherTyped.getAutoAssignSequenceName(), false);
            this.computeDiff("BigintTypeName", this.bean.getBigintTypeName(), otherTyped.getBigintTypeName(), false);
            this.computeDiff("BinaryTypeName", this.bean.getBinaryTypeName(), otherTyped.getBinaryTypeName(), false);
            this.computeDiff("BitTypeName", this.bean.getBitTypeName(), otherTyped.getBitTypeName(), false);
            this.computeDiff("DecimalTypeName", this.bean.getDecimalTypeName(), otherTyped.getDecimalTypeName(), false);
            this.computeDiff("DoubleTypeName", this.bean.getDoubleTypeName(), otherTyped.getDoubleTypeName(), false);
            this.computeDiff("IntegerTypeName", this.bean.getIntegerTypeName(), otherTyped.getIntegerTypeName(), false);
            this.computeDiff("JoinSyntax", this.bean.getJoinSyntax(), otherTyped.getJoinSyntax(), false);
            this.computeDiff("LongVarbinaryTypeName", this.bean.getLongVarbinaryTypeName(), otherTyped.getLongVarbinaryTypeName(), false);
            this.computeDiff("LongVarcharTypeName", this.bean.getLongVarcharTypeName(), otherTyped.getLongVarcharTypeName(), false);
            this.computeDiff("MaxColumnNameLength", this.bean.getMaxColumnNameLength(), otherTyped.getMaxColumnNameLength(), false);
            this.computeDiff("MaxConstraintNameLength", this.bean.getMaxConstraintNameLength(), otherTyped.getMaxConstraintNameLength(), false);
            this.computeDiff("MaxEmbeddedBlobSize", this.bean.getMaxEmbeddedBlobSize(), otherTyped.getMaxEmbeddedBlobSize(), false);
            this.computeDiff("MaxEmbeddedClobSize", this.bean.getMaxEmbeddedClobSize(), otherTyped.getMaxEmbeddedClobSize(), false);
            this.computeDiff("MaxIndexNameLength", this.bean.getMaxIndexNameLength(), otherTyped.getMaxIndexNameLength(), false);
            this.computeDiff("MaxTableNameLength", this.bean.getMaxTableNameLength(), otherTyped.getMaxTableNameLength(), false);
            this.computeDiff("NextSequenceQuery", this.bean.getNextSequenceQuery(), otherTyped.getNextSequenceQuery(), false);
            this.computeDiff("NumericTypeName", this.bean.getNumericTypeName(), otherTyped.getNumericTypeName(), false);
            this.computeDiff("Openjpa3GeneratedKeyNames", this.bean.getOpenjpa3GeneratedKeyNames(), otherTyped.getOpenjpa3GeneratedKeyNames(), false);
            this.computeDiff("Platform", this.bean.getPlatform(), otherTyped.getPlatform(), false);
            this.computeDiff("SmallintTypeName", this.bean.getSmallintTypeName(), otherTyped.getSmallintTypeName(), false);
            this.computeDiff("StringLengthFunction", this.bean.getStringLengthFunction(), otherTyped.getStringLengthFunction(), false);
            this.computeDiff("SupportsDeferredConstraints", this.bean.getSupportsDeferredConstraints(), otherTyped.getSupportsDeferredConstraints(), false);
            this.computeDiff("SupportsLockingWithDistinctClause", this.bean.getSupportsLockingWithDistinctClause(), otherTyped.getSupportsLockingWithDistinctClause(), false);
            this.computeDiff("SupportsSelectEndIndex", this.bean.getSupportsSelectEndIndex(), otherTyped.getSupportsSelectEndIndex(), false);
            this.computeDiff("SupportsSelectStartIndex", this.bean.getSupportsSelectStartIndex(), otherTyped.getSupportsSelectStartIndex(), false);
            this.computeDiff("TimeTypeName", this.bean.getTimeTypeName(), otherTyped.getTimeTypeName(), false);
            this.computeDiff("TinyintTypeName", this.bean.getTinyintTypeName(), otherTyped.getTinyintTypeName(), false);
            this.computeDiff("UseSetFormOfUseForUnicode", this.bean.getUseSetFormOfUseForUnicode(), otherTyped.getUseSetFormOfUseForUnicode(), false);
            this.computeDiff("UseTriggersForAutoAssign", this.bean.getUseTriggersForAutoAssign(), otherTyped.getUseTriggersForAutoAssign(), false);
            this.computeDiff("ValidationSQL", this.bean.getValidationSQL(), otherTyped.getValidationSQL(), false);
            this.computeDiff("VarcharTypeName", this.bean.getVarcharTypeName(), otherTyped.getVarcharTypeName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            OracleDictionaryBeanImpl original = (OracleDictionaryBeanImpl)event.getSourceBean();
            OracleDictionaryBeanImpl proposed = (OracleDictionaryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AutoAssignSequenceName")) {
                  original.setAutoAssignSequenceName(proposed.getAutoAssignSequenceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 138);
               } else if (prop.equals("BigintTypeName")) {
                  original.setBigintTypeName(proposed.getBigintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 93);
               } else if (prop.equals("BinaryTypeName")) {
                  original.setBinaryTypeName(proposed.getBinaryTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("BitTypeName")) {
                  original.setBitTypeName(proposed.getBitTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (prop.equals("DecimalTypeName")) {
                  original.setDecimalTypeName(proposed.getDecimalTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 41);
               } else if (prop.equals("DoubleTypeName")) {
                  original.setDoubleTypeName(proposed.getDoubleTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("IntegerTypeName")) {
                  original.setIntegerTypeName(proposed.getIntegerTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 75);
               } else if (prop.equals("JoinSyntax")) {
                  original.setJoinSyntax(proposed.getJoinSyntax());
                  original._conditionalUnset(update.isUnsetUpdate(), 116);
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
               } else if (prop.equals("MaxEmbeddedBlobSize")) {
                  original.setMaxEmbeddedBlobSize(proposed.getMaxEmbeddedBlobSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 117);
               } else if (prop.equals("MaxEmbeddedClobSize")) {
                  original.setMaxEmbeddedClobSize(proposed.getMaxEmbeddedClobSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("MaxIndexNameLength")) {
                  original.setMaxIndexNameLength(proposed.getMaxIndexNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 109);
               } else if (prop.equals("MaxTableNameLength")) {
                  original.setMaxTableNameLength(proposed.getMaxTableNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 113);
               } else if (prop.equals("NextSequenceQuery")) {
                  original.setNextSequenceQuery(proposed.getNextSequenceQuery());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("NumericTypeName")) {
                  original.setNumericTypeName(proposed.getNumericTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 73);
               } else if (prop.equals("Openjpa3GeneratedKeyNames")) {
                  original.setOpenjpa3GeneratedKeyNames(proposed.getOpenjpa3GeneratedKeyNames());
                  original._conditionalUnset(update.isUnsetUpdate(), 137);
               } else if (prop.equals("Platform")) {
                  original.setPlatform(proposed.getPlatform());
                  original._conditionalUnset(update.isUnsetUpdate(), 84);
               } else if (prop.equals("SmallintTypeName")) {
                  original.setSmallintTypeName(proposed.getSmallintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (prop.equals("StringLengthFunction")) {
                  original.setStringLengthFunction(proposed.getStringLengthFunction());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("SupportsDeferredConstraints")) {
                  original.setSupportsDeferredConstraints(proposed.getSupportsDeferredConstraints());
                  original._conditionalUnset(update.isUnsetUpdate(), 98);
               } else if (prop.equals("SupportsLockingWithDistinctClause")) {
                  original.setSupportsLockingWithDistinctClause(proposed.getSupportsLockingWithDistinctClause());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("SupportsSelectEndIndex")) {
                  original.setSupportsSelectEndIndex(proposed.getSupportsSelectEndIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 48);
               } else if (prop.equals("SupportsSelectStartIndex")) {
                  original.setSupportsSelectStartIndex(proposed.getSupportsSelectStartIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 119);
               } else if (prop.equals("TimeTypeName")) {
                  original.setTimeTypeName(proposed.getTimeTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 56);
               } else if (prop.equals("TinyintTypeName")) {
                  original.setTinyintTypeName(proposed.getTinyintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 125);
               } else if (prop.equals("UseSetFormOfUseForUnicode")) {
                  original.setUseSetFormOfUseForUnicode(proposed.getUseSetFormOfUseForUnicode());
                  original._conditionalUnset(update.isUnsetUpdate(), 135);
               } else if (prop.equals("UseTriggersForAutoAssign")) {
                  original.setUseTriggersForAutoAssign(proposed.getUseTriggersForAutoAssign());
                  original._conditionalUnset(update.isUnsetUpdate(), 136);
               } else if (prop.equals("ValidationSQL")) {
                  original.setValidationSQL(proposed.getValidationSQL());
                  original._conditionalUnset(update.isUnsetUpdate(), 60);
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
            OracleDictionaryBeanImpl copy = (OracleDictionaryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AutoAssignSequenceName")) && this.bean.isAutoAssignSequenceNameSet()) {
               copy.setAutoAssignSequenceName(this.bean.getAutoAssignSequenceName());
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

            if ((excludeProps == null || !excludeProps.contains("DecimalTypeName")) && this.bean.isDecimalTypeNameSet()) {
               copy.setDecimalTypeName(this.bean.getDecimalTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("DoubleTypeName")) && this.bean.isDoubleTypeNameSet()) {
               copy.setDoubleTypeName(this.bean.getDoubleTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("IntegerTypeName")) && this.bean.isIntegerTypeNameSet()) {
               copy.setIntegerTypeName(this.bean.getIntegerTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("JoinSyntax")) && this.bean.isJoinSyntaxSet()) {
               copy.setJoinSyntax(this.bean.getJoinSyntax());
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

            if ((excludeProps == null || !excludeProps.contains("MaxEmbeddedBlobSize")) && this.bean.isMaxEmbeddedBlobSizeSet()) {
               copy.setMaxEmbeddedBlobSize(this.bean.getMaxEmbeddedBlobSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxEmbeddedClobSize")) && this.bean.isMaxEmbeddedClobSizeSet()) {
               copy.setMaxEmbeddedClobSize(this.bean.getMaxEmbeddedClobSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxIndexNameLength")) && this.bean.isMaxIndexNameLengthSet()) {
               copy.setMaxIndexNameLength(this.bean.getMaxIndexNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxTableNameLength")) && this.bean.isMaxTableNameLengthSet()) {
               copy.setMaxTableNameLength(this.bean.getMaxTableNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("NextSequenceQuery")) && this.bean.isNextSequenceQuerySet()) {
               copy.setNextSequenceQuery(this.bean.getNextSequenceQuery());
            }

            if ((excludeProps == null || !excludeProps.contains("NumericTypeName")) && this.bean.isNumericTypeNameSet()) {
               copy.setNumericTypeName(this.bean.getNumericTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("Openjpa3GeneratedKeyNames")) && this.bean.isOpenjpa3GeneratedKeyNamesSet()) {
               copy.setOpenjpa3GeneratedKeyNames(this.bean.getOpenjpa3GeneratedKeyNames());
            }

            if ((excludeProps == null || !excludeProps.contains("Platform")) && this.bean.isPlatformSet()) {
               copy.setPlatform(this.bean.getPlatform());
            }

            if ((excludeProps == null || !excludeProps.contains("SmallintTypeName")) && this.bean.isSmallintTypeNameSet()) {
               copy.setSmallintTypeName(this.bean.getSmallintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("StringLengthFunction")) && this.bean.isStringLengthFunctionSet()) {
               copy.setStringLengthFunction(this.bean.getStringLengthFunction());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsDeferredConstraints")) && this.bean.isSupportsDeferredConstraintsSet()) {
               copy.setSupportsDeferredConstraints(this.bean.getSupportsDeferredConstraints());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsLockingWithDistinctClause")) && this.bean.isSupportsLockingWithDistinctClauseSet()) {
               copy.setSupportsLockingWithDistinctClause(this.bean.getSupportsLockingWithDistinctClause());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSelectEndIndex")) && this.bean.isSupportsSelectEndIndexSet()) {
               copy.setSupportsSelectEndIndex(this.bean.getSupportsSelectEndIndex());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsSelectStartIndex")) && this.bean.isSupportsSelectStartIndexSet()) {
               copy.setSupportsSelectStartIndex(this.bean.getSupportsSelectStartIndex());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeTypeName")) && this.bean.isTimeTypeNameSet()) {
               copy.setTimeTypeName(this.bean.getTimeTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("TinyintTypeName")) && this.bean.isTinyintTypeNameSet()) {
               copy.setTinyintTypeName(this.bean.getTinyintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("UseSetFormOfUseForUnicode")) && this.bean.isUseSetFormOfUseForUnicodeSet()) {
               copy.setUseSetFormOfUseForUnicode(this.bean.getUseSetFormOfUseForUnicode());
            }

            if ((excludeProps == null || !excludeProps.contains("UseTriggersForAutoAssign")) && this.bean.isUseTriggersForAutoAssignSet()) {
               copy.setUseTriggersForAutoAssign(this.bean.getUseTriggersForAutoAssign());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidationSQL")) && this.bean.isValidationSQLSet()) {
               copy.setValidationSQL(this.bean.getValidationSQL());
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
