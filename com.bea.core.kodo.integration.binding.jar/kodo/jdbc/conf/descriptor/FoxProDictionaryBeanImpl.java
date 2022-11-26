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

public class FoxProDictionaryBeanImpl extends BuiltInDBDictionaryBeanImpl implements FoxProDictionaryBean, Serializable {
   private String _BigintTypeName;
   private String _BinaryTypeName;
   private String _BitTypeName;
   private String _BlobTypeName;
   private int _CharacterColumnSize;
   private String _ClobTypeName;
   private String _DateTypeName;
   private String _DecimalTypeName;
   private String _DoubleTypeName;
   private String _FloatTypeName;
   private String _IntegerTypeName;
   private String _JoinSyntax;
   private String _LongVarbinaryTypeName;
   private String _LongVarcharTypeName;
   private int _MaxColumnNameLength;
   private int _MaxConstraintNameLength;
   private int _MaxIndexNameLength;
   private int _MaxTableNameLength;
   private String _NumericTypeName;
   private String _Platform;
   private String _RealTypeName;
   private String _SmallintTypeName;
   private boolean _SupportsDeferredConstraints;
   private boolean _SupportsForeignKeys;
   private String _TimeTypeName;
   private String _TinyintTypeName;
   private String _VarcharTypeName;
   private static SchemaHelper2 _schemaHelper;

   public FoxProDictionaryBeanImpl() {
      this._initializeProperty(-1);
   }

   public FoxProDictionaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public FoxProDictionaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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
         idx = 93;
      }

      try {
         switch (idx) {
            case 93:
               this._BigintTypeName = "DOUBLE";
               if (initOne) {
                  break;
               }
            case 3:
               this._BinaryTypeName = "GENERAL";
               if (initOne) {
                  break;
               }
            case 45:
               this._BitTypeName = "NUMERIC(1)";
               if (initOne) {
                  break;
               }
            case 76:
               this._BlobTypeName = "GENERAL";
               if (initOne) {
                  break;
               }
            case 111:
               this._CharacterColumnSize = 240;
               if (initOne) {
                  break;
               }
            case 4:
               this._ClobTypeName = "MEMO";
               if (initOne) {
                  break;
               }
            case 25:
               this._DateTypeName = "TIMESTAMP";
               if (initOne) {
                  break;
               }
            case 41:
               this._DecimalTypeName = "DOUBLE";
               if (initOne) {
                  break;
               }
            case 39:
               this._DoubleTypeName = "DOUBLE";
               if (initOne) {
                  break;
               }
            case 70:
               this._FloatTypeName = "NUMERIC(19,16)";
               if (initOne) {
                  break;
               }
            case 75:
               this._IntegerTypeName = "INTEGER";
               if (initOne) {
                  break;
               }
            case 116:
               this._JoinSyntax = "traditional";
               if (initOne) {
                  break;
               }
            case 16:
               this._LongVarbinaryTypeName = "GENERAL";
               if (initOne) {
                  break;
               }
            case 22:
               this._LongVarcharTypeName = "MEMO";
               if (initOne) {
                  break;
               }
            case 38:
               this._MaxColumnNameLength = 30;
               if (initOne) {
                  break;
               }
            case 12:
               this._MaxConstraintNameLength = 8;
               if (initOne) {
                  break;
               }
            case 109:
               this._MaxIndexNameLength = 8;
               if (initOne) {
                  break;
               }
            case 113:
               this._MaxTableNameLength = 30;
               if (initOne) {
                  break;
               }
            case 73:
               this._NumericTypeName = "INTEGER";
               if (initOne) {
                  break;
               }
            case 84:
               this._Platform = "Visual FoxPro";
               if (initOne) {
                  break;
               }
            case 99:
               this._RealTypeName = "DOUBLE";
               if (initOne) {
                  break;
               }
            case 42:
               this._SmallintTypeName = "INTEGER";
               if (initOne) {
                  break;
               }
            case 98:
               this._SupportsDeferredConstraints = false;
               if (initOne) {
                  break;
               }
            case 80:
               this._SupportsForeignKeys = false;
               if (initOne) {
                  break;
               }
            case 56:
               this._TimeTypeName = "TIMESTAMP";
               if (initOne) {
                  break;
               }
            case 125:
               this._TinyintTypeName = "INTEGER";
               if (initOne) {
                  break;
               }
            case 62:
               this._VarcharTypeName = "CHARACTER{0}";
               if (initOne) {
                  break;
               }
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 24:
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
            case 48:
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
            case 60:
            case 61:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 71:
            case 72:
            case 74:
            case 77:
            case 78:
            case 79:
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
            case 112:
            case 114:
            case 115:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
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
            case 19:
            case 20:
            case 23:
            case 25:
            case 27:
            case 28:
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
               if (s.equals("blob-type-name")) {
                  return 76;
               }

               if (s.equals("clob-type-name")) {
                  return 4;
               }

               if (s.equals("date-type-name")) {
                  return 25;
               }

               if (s.equals("real-type-name")) {
                  return 99;
               }

               if (s.equals("time-type-name")) {
                  return 56;
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
            case 21:
               if (s.equals("character-column-size")) {
                  return 111;
               }

               if (s.equals("max-index-name-length")) {
                  return 109;
               }

               if (s.equals("max-table-name-length")) {
                  return 113;
               }

               if (s.equals("supports-foreign-keys")) {
                  return 80;
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
               if (s.equals("long-varbinary-type-name")) {
                  return 16;
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
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 24:
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
            case 48:
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
            case 60:
            case 61:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 71:
            case 72:
            case 74:
            case 77:
            case 78:
            case 79:
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
            case 112:
            case 114:
            case 115:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            default:
               return super.getElementName(propIndex);
            case 12:
               return "max-constraint-name-length";
            case 16:
               return "long-varbinary-type-name";
            case 22:
               return "long-varchar-type-name";
            case 25:
               return "date-type-name";
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
            case 56:
               return "time-type-name";
            case 62:
               return "varchar-type-name";
            case 70:
               return "float-type-name";
            case 73:
               return "numeric-type-name";
            case 75:
               return "integer-type-name";
            case 76:
               return "blob-type-name";
            case 80:
               return "supports-foreign-keys";
            case 84:
               return "platform";
            case 93:
               return "bigint-type-name";
            case 98:
               return "supports-deferred-constraints";
            case 99:
               return "real-type-name";
            case 109:
               return "max-index-name-length";
            case 111:
               return "character-column-size";
            case 113:
               return "max-table-name-length";
            case 116:
               return "join-syntax";
            case 125:
               return "tinyint-type-name";
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
      private FoxProDictionaryBeanImpl bean;

      protected Helper(FoxProDictionaryBeanImpl bean) {
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
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 24:
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
            case 48:
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
            case 60:
            case 61:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 71:
            case 72:
            case 74:
            case 77:
            case 78:
            case 79:
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
            case 112:
            case 114:
            case 115:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            default:
               return super.getPropertyName(propIndex);
            case 12:
               return "MaxConstraintNameLength";
            case 16:
               return "LongVarbinaryTypeName";
            case 22:
               return "LongVarcharTypeName";
            case 25:
               return "DateTypeName";
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
            case 56:
               return "TimeTypeName";
            case 62:
               return "VarcharTypeName";
            case 70:
               return "FloatTypeName";
            case 73:
               return "NumericTypeName";
            case 75:
               return "IntegerTypeName";
            case 76:
               return "BlobTypeName";
            case 80:
               return "SupportsForeignKeys";
            case 84:
               return "Platform";
            case 93:
               return "BigintTypeName";
            case 98:
               return "SupportsDeferredConstraints";
            case 99:
               return "RealTypeName";
            case 109:
               return "MaxIndexNameLength";
            case 111:
               return "CharacterColumnSize";
            case 113:
               return "MaxTableNameLength";
            case 116:
               return "JoinSyntax";
            case 125:
               return "TinyintTypeName";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BigintTypeName")) {
            return 93;
         } else if (propName.equals("BinaryTypeName")) {
            return 3;
         } else if (propName.equals("BitTypeName")) {
            return 45;
         } else if (propName.equals("BlobTypeName")) {
            return 76;
         } else if (propName.equals("CharacterColumnSize")) {
            return 111;
         } else if (propName.equals("ClobTypeName")) {
            return 4;
         } else if (propName.equals("DateTypeName")) {
            return 25;
         } else if (propName.equals("DecimalTypeName")) {
            return 41;
         } else if (propName.equals("DoubleTypeName")) {
            return 39;
         } else if (propName.equals("FloatTypeName")) {
            return 70;
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
         } else if (propName.equals("MaxIndexNameLength")) {
            return 109;
         } else if (propName.equals("MaxTableNameLength")) {
            return 113;
         } else if (propName.equals("NumericTypeName")) {
            return 73;
         } else if (propName.equals("Platform")) {
            return 84;
         } else if (propName.equals("RealTypeName")) {
            return 99;
         } else if (propName.equals("SmallintTypeName")) {
            return 42;
         } else if (propName.equals("SupportsDeferredConstraints")) {
            return 98;
         } else if (propName.equals("SupportsForeignKeys")) {
            return 80;
         } else if (propName.equals("TimeTypeName")) {
            return 56;
         } else if (propName.equals("TinyintTypeName")) {
            return 125;
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

            if (this.bean.isCharacterColumnSizeSet()) {
               buf.append("CharacterColumnSize");
               buf.append(String.valueOf(this.bean.getCharacterColumnSize()));
            }

            if (this.bean.isClobTypeNameSet()) {
               buf.append("ClobTypeName");
               buf.append(String.valueOf(this.bean.getClobTypeName()));
            }

            if (this.bean.isDateTypeNameSet()) {
               buf.append("DateTypeName");
               buf.append(String.valueOf(this.bean.getDateTypeName()));
            }

            if (this.bean.isDecimalTypeNameSet()) {
               buf.append("DecimalTypeName");
               buf.append(String.valueOf(this.bean.getDecimalTypeName()));
            }

            if (this.bean.isDoubleTypeNameSet()) {
               buf.append("DoubleTypeName");
               buf.append(String.valueOf(this.bean.getDoubleTypeName()));
            }

            if (this.bean.isFloatTypeNameSet()) {
               buf.append("FloatTypeName");
               buf.append(String.valueOf(this.bean.getFloatTypeName()));
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

            if (this.bean.isMaxIndexNameLengthSet()) {
               buf.append("MaxIndexNameLength");
               buf.append(String.valueOf(this.bean.getMaxIndexNameLength()));
            }

            if (this.bean.isMaxTableNameLengthSet()) {
               buf.append("MaxTableNameLength");
               buf.append(String.valueOf(this.bean.getMaxTableNameLength()));
            }

            if (this.bean.isNumericTypeNameSet()) {
               buf.append("NumericTypeName");
               buf.append(String.valueOf(this.bean.getNumericTypeName()));
            }

            if (this.bean.isPlatformSet()) {
               buf.append("Platform");
               buf.append(String.valueOf(this.bean.getPlatform()));
            }

            if (this.bean.isRealTypeNameSet()) {
               buf.append("RealTypeName");
               buf.append(String.valueOf(this.bean.getRealTypeName()));
            }

            if (this.bean.isSmallintTypeNameSet()) {
               buf.append("SmallintTypeName");
               buf.append(String.valueOf(this.bean.getSmallintTypeName()));
            }

            if (this.bean.isSupportsDeferredConstraintsSet()) {
               buf.append("SupportsDeferredConstraints");
               buf.append(String.valueOf(this.bean.getSupportsDeferredConstraints()));
            }

            if (this.bean.isSupportsForeignKeysSet()) {
               buf.append("SupportsForeignKeys");
               buf.append(String.valueOf(this.bean.getSupportsForeignKeys()));
            }

            if (this.bean.isTimeTypeNameSet()) {
               buf.append("TimeTypeName");
               buf.append(String.valueOf(this.bean.getTimeTypeName()));
            }

            if (this.bean.isTinyintTypeNameSet()) {
               buf.append("TinyintTypeName");
               buf.append(String.valueOf(this.bean.getTinyintTypeName()));
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
            FoxProDictionaryBeanImpl otherTyped = (FoxProDictionaryBeanImpl)other;
            this.computeDiff("BigintTypeName", this.bean.getBigintTypeName(), otherTyped.getBigintTypeName(), false);
            this.computeDiff("BinaryTypeName", this.bean.getBinaryTypeName(), otherTyped.getBinaryTypeName(), false);
            this.computeDiff("BitTypeName", this.bean.getBitTypeName(), otherTyped.getBitTypeName(), false);
            this.computeDiff("BlobTypeName", this.bean.getBlobTypeName(), otherTyped.getBlobTypeName(), false);
            this.computeDiff("CharacterColumnSize", this.bean.getCharacterColumnSize(), otherTyped.getCharacterColumnSize(), false);
            this.computeDiff("ClobTypeName", this.bean.getClobTypeName(), otherTyped.getClobTypeName(), false);
            this.computeDiff("DateTypeName", this.bean.getDateTypeName(), otherTyped.getDateTypeName(), false);
            this.computeDiff("DecimalTypeName", this.bean.getDecimalTypeName(), otherTyped.getDecimalTypeName(), false);
            this.computeDiff("DoubleTypeName", this.bean.getDoubleTypeName(), otherTyped.getDoubleTypeName(), false);
            this.computeDiff("FloatTypeName", this.bean.getFloatTypeName(), otherTyped.getFloatTypeName(), false);
            this.computeDiff("IntegerTypeName", this.bean.getIntegerTypeName(), otherTyped.getIntegerTypeName(), false);
            this.computeDiff("JoinSyntax", this.bean.getJoinSyntax(), otherTyped.getJoinSyntax(), false);
            this.computeDiff("LongVarbinaryTypeName", this.bean.getLongVarbinaryTypeName(), otherTyped.getLongVarbinaryTypeName(), false);
            this.computeDiff("LongVarcharTypeName", this.bean.getLongVarcharTypeName(), otherTyped.getLongVarcharTypeName(), false);
            this.computeDiff("MaxColumnNameLength", this.bean.getMaxColumnNameLength(), otherTyped.getMaxColumnNameLength(), false);
            this.computeDiff("MaxConstraintNameLength", this.bean.getMaxConstraintNameLength(), otherTyped.getMaxConstraintNameLength(), false);
            this.computeDiff("MaxIndexNameLength", this.bean.getMaxIndexNameLength(), otherTyped.getMaxIndexNameLength(), false);
            this.computeDiff("MaxTableNameLength", this.bean.getMaxTableNameLength(), otherTyped.getMaxTableNameLength(), false);
            this.computeDiff("NumericTypeName", this.bean.getNumericTypeName(), otherTyped.getNumericTypeName(), false);
            this.computeDiff("Platform", this.bean.getPlatform(), otherTyped.getPlatform(), false);
            this.computeDiff("RealTypeName", this.bean.getRealTypeName(), otherTyped.getRealTypeName(), false);
            this.computeDiff("SmallintTypeName", this.bean.getSmallintTypeName(), otherTyped.getSmallintTypeName(), false);
            this.computeDiff("SupportsDeferredConstraints", this.bean.getSupportsDeferredConstraints(), otherTyped.getSupportsDeferredConstraints(), false);
            this.computeDiff("SupportsForeignKeys", this.bean.getSupportsForeignKeys(), otherTyped.getSupportsForeignKeys(), false);
            this.computeDiff("TimeTypeName", this.bean.getTimeTypeName(), otherTyped.getTimeTypeName(), false);
            this.computeDiff("TinyintTypeName", this.bean.getTinyintTypeName(), otherTyped.getTinyintTypeName(), false);
            this.computeDiff("VarcharTypeName", this.bean.getVarcharTypeName(), otherTyped.getVarcharTypeName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            FoxProDictionaryBeanImpl original = (FoxProDictionaryBeanImpl)event.getSourceBean();
            FoxProDictionaryBeanImpl proposed = (FoxProDictionaryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BigintTypeName")) {
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
               } else if (prop.equals("CharacterColumnSize")) {
                  original.setCharacterColumnSize(proposed.getCharacterColumnSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 111);
               } else if (prop.equals("ClobTypeName")) {
                  original.setClobTypeName(proposed.getClobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("DateTypeName")) {
                  original.setDateTypeName(proposed.getDateTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("DecimalTypeName")) {
                  original.setDecimalTypeName(proposed.getDecimalTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 41);
               } else if (prop.equals("DoubleTypeName")) {
                  original.setDoubleTypeName(proposed.getDoubleTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("FloatTypeName")) {
                  original.setFloatTypeName(proposed.getFloatTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 70);
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
               } else if (prop.equals("MaxIndexNameLength")) {
                  original.setMaxIndexNameLength(proposed.getMaxIndexNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 109);
               } else if (prop.equals("MaxTableNameLength")) {
                  original.setMaxTableNameLength(proposed.getMaxTableNameLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 113);
               } else if (prop.equals("NumericTypeName")) {
                  original.setNumericTypeName(proposed.getNumericTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 73);
               } else if (prop.equals("Platform")) {
                  original.setPlatform(proposed.getPlatform());
                  original._conditionalUnset(update.isUnsetUpdate(), 84);
               } else if (prop.equals("RealTypeName")) {
                  original.setRealTypeName(proposed.getRealTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 99);
               } else if (prop.equals("SmallintTypeName")) {
                  original.setSmallintTypeName(proposed.getSmallintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (prop.equals("SupportsDeferredConstraints")) {
                  original.setSupportsDeferredConstraints(proposed.getSupportsDeferredConstraints());
                  original._conditionalUnset(update.isUnsetUpdate(), 98);
               } else if (prop.equals("SupportsForeignKeys")) {
                  original.setSupportsForeignKeys(proposed.getSupportsForeignKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 80);
               } else if (prop.equals("TimeTypeName")) {
                  original.setTimeTypeName(proposed.getTimeTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 56);
               } else if (prop.equals("TinyintTypeName")) {
                  original.setTinyintTypeName(proposed.getTinyintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 125);
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
            FoxProDictionaryBeanImpl copy = (FoxProDictionaryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
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

            if ((excludeProps == null || !excludeProps.contains("CharacterColumnSize")) && this.bean.isCharacterColumnSizeSet()) {
               copy.setCharacterColumnSize(this.bean.getCharacterColumnSize());
            }

            if ((excludeProps == null || !excludeProps.contains("ClobTypeName")) && this.bean.isClobTypeNameSet()) {
               copy.setClobTypeName(this.bean.getClobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("DateTypeName")) && this.bean.isDateTypeNameSet()) {
               copy.setDateTypeName(this.bean.getDateTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("DecimalTypeName")) && this.bean.isDecimalTypeNameSet()) {
               copy.setDecimalTypeName(this.bean.getDecimalTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("DoubleTypeName")) && this.bean.isDoubleTypeNameSet()) {
               copy.setDoubleTypeName(this.bean.getDoubleTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("FloatTypeName")) && this.bean.isFloatTypeNameSet()) {
               copy.setFloatTypeName(this.bean.getFloatTypeName());
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

            if ((excludeProps == null || !excludeProps.contains("MaxIndexNameLength")) && this.bean.isMaxIndexNameLengthSet()) {
               copy.setMaxIndexNameLength(this.bean.getMaxIndexNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxTableNameLength")) && this.bean.isMaxTableNameLengthSet()) {
               copy.setMaxTableNameLength(this.bean.getMaxTableNameLength());
            }

            if ((excludeProps == null || !excludeProps.contains("NumericTypeName")) && this.bean.isNumericTypeNameSet()) {
               copy.setNumericTypeName(this.bean.getNumericTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("Platform")) && this.bean.isPlatformSet()) {
               copy.setPlatform(this.bean.getPlatform());
            }

            if ((excludeProps == null || !excludeProps.contains("RealTypeName")) && this.bean.isRealTypeNameSet()) {
               copy.setRealTypeName(this.bean.getRealTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("SmallintTypeName")) && this.bean.isSmallintTypeNameSet()) {
               copy.setSmallintTypeName(this.bean.getSmallintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsDeferredConstraints")) && this.bean.isSupportsDeferredConstraintsSet()) {
               copy.setSupportsDeferredConstraints(this.bean.getSupportsDeferredConstraints());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportsForeignKeys")) && this.bean.isSupportsForeignKeysSet()) {
               copy.setSupportsForeignKeys(this.bean.getSupportsForeignKeys());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeTypeName")) && this.bean.isTimeTypeNameSet()) {
               copy.setTimeTypeName(this.bean.getTimeTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("TinyintTypeName")) && this.bean.isTinyintTypeNameSet()) {
               copy.setTinyintTypeName(this.bean.getTinyintTypeName());
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
