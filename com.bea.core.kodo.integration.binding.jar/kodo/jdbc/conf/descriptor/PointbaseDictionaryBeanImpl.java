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

public class PointbaseDictionaryBeanImpl extends BuiltInDBDictionaryBeanImpl implements PointbaseDictionaryBean, Serializable {
   private String _AutoAssignTypeName;
   private String _BigintTypeName;
   private String _BitTypeName;
   private String _BlobTypeName;
   private String _CharTypeName;
   private String _ClobTypeName;
   private String _DoubleTypeName;
   private String _FloatTypeName;
   private String _IntegerTypeName;
   private String _LastGeneratedKeyQuery;
   private String _LongVarbinaryTypeName;
   private String _Platform;
   private String _RealTypeName;
   private boolean _RequiresAliasForSubselect;
   private String _SmallintTypeName;
   private boolean _SupportsAutoAssign;
   private boolean _SupportsDeferredConstraints;
   private boolean _SupportsLockingWithDistinctClause;
   private boolean _SupportsLockingWithMultipleTables;
   private boolean _SupportsMultipleNontransactionalResultSets;
   private String _TinyintTypeName;
   private static SchemaHelper2 _schemaHelper;

   public PointbaseDictionaryBeanImpl() {
      this._initializeProperty(-1);
   }

   public PointbaseDictionaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PointbaseDictionaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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
         idx = 57;
      }

      try {
         switch (idx) {
            case 57:
               this._AutoAssignTypeName = "BIGINT IDENTITY";
               if (initOne) {
                  break;
               }
            case 93:
               this._BigintTypeName = "BIGINT";
               if (initOne) {
                  break;
               }
            case 45:
               this._BitTypeName = "TINYINT";
               if (initOne) {
                  break;
               }
            case 76:
               this._BlobTypeName = "BLOB(1M)";
               if (initOne) {
                  break;
               }
            case 1:
               this._CharTypeName = "CHARACTER{0}";
               if (initOne) {
                  break;
               }
            case 4:
               this._ClobTypeName = "CLOB(1M)";
               if (initOne) {
                  break;
               }
            case 39:
               this._DoubleTypeName = "DOUBLE PRECISION";
               if (initOne) {
                  break;
               }
            case 70:
               this._FloatTypeName = "FLOAT";
               if (initOne) {
                  break;
               }
            case 75:
               this._IntegerTypeName = "INTEGER";
               if (initOne) {
                  break;
               }
            case 94:
               this._LastGeneratedKeyQuery = "SELECT MAX({0}) FROM {1}";
               if (initOne) {
                  break;
               }
            case 16:
               this._LongVarbinaryTypeName = "BLOB(1M)";
               if (initOne) {
                  break;
               }
            case 84:
               this._Platform = "Pointbase Embedded";
               if (initOne) {
                  break;
               }
            case 99:
               this._RealTypeName = "REAL";
               if (initOne) {
                  break;
               }
            case 100:
               this._RequiresAliasForSubselect = true;
               if (initOne) {
                  break;
               }
            case 42:
               this._SmallintTypeName = "SMALLINT";
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
            case 66:
               this._SupportsMultipleNontransactionalResultSets = false;
               if (initOne) {
                  break;
               }
            case 125:
               this._TinyintTypeName = "TINYINT";
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
            case 9:
            case 10:
            case 11:
            case 12:
            case 19:
            case 22:
            case 23:
            case 25:
            case 26:
            case 27:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
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

               if (s.equals("char-type-name")) {
                  return 1;
               }

               if (s.equals("clob-type-name")) {
                  return 4;
               }

               if (s.equals("real-type-name")) {
                  return 99;
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
               if (s.equals("integer-type-name")) {
                  return 75;
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
            case 20:
               if (s.equals("supports-auto-assign")) {
                  return 49;
               }
               break;
            case 21:
               if (s.equals("auto-assign-type-name")) {
                  return 57;
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
            case 1:
               return "char-type-name";
            case 4:
               return "clob-type-name";
            case 5:
               return "supports-locking-with-distinct-clause";
            case 16:
               return "long-varbinary-type-name";
            case 37:
               return "supports-locking-with-multiple-tables";
            case 39:
               return "double-type-name";
            case 42:
               return "smallint-type-name";
            case 45:
               return "bit-type-name";
            case 49:
               return "supports-auto-assign";
            case 57:
               return "auto-assign-type-name";
            case 66:
               return "supports-multiple-nontransactional-result-sets";
            case 70:
               return "float-type-name";
            case 75:
               return "integer-type-name";
            case 76:
               return "blob-type-name";
            case 84:
               return "platform";
            case 93:
               return "bigint-type-name";
            case 94:
               return "last-generated-key-query";
            case 98:
               return "supports-deferred-constraints";
            case 99:
               return "real-type-name";
            case 100:
               return "requires-alias-for-subselect";
            case 125:
               return "tinyint-type-name";
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

   protected static class Helper extends BuiltInDBDictionaryBeanImpl.Helper {
      private PointbaseDictionaryBeanImpl bean;

      protected Helper(PointbaseDictionaryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "CharTypeName";
            case 4:
               return "ClobTypeName";
            case 5:
               return "SupportsLockingWithDistinctClause";
            case 16:
               return "LongVarbinaryTypeName";
            case 37:
               return "SupportsLockingWithMultipleTables";
            case 39:
               return "DoubleTypeName";
            case 42:
               return "SmallintTypeName";
            case 45:
               return "BitTypeName";
            case 49:
               return "SupportsAutoAssign";
            case 57:
               return "AutoAssignTypeName";
            case 66:
               return "SupportsMultipleNontransactionalResultSets";
            case 70:
               return "FloatTypeName";
            case 75:
               return "IntegerTypeName";
            case 76:
               return "BlobTypeName";
            case 84:
               return "Platform";
            case 93:
               return "BigintTypeName";
            case 94:
               return "LastGeneratedKeyQuery";
            case 98:
               return "SupportsDeferredConstraints";
            case 99:
               return "RealTypeName";
            case 100:
               return "RequiresAliasForSubselect";
            case 125:
               return "TinyintTypeName";
            default:
               return super.getPropertyName(propIndex);
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
         } else if (propName.equals("CharTypeName")) {
            return 1;
         } else if (propName.equals("ClobTypeName")) {
            return 4;
         } else if (propName.equals("DoubleTypeName")) {
            return 39;
         } else if (propName.equals("FloatTypeName")) {
            return 70;
         } else if (propName.equals("IntegerTypeName")) {
            return 75;
         } else if (propName.equals("LastGeneratedKeyQuery")) {
            return 94;
         } else if (propName.equals("LongVarbinaryTypeName")) {
            return 16;
         } else if (propName.equals("Platform")) {
            return 84;
         } else if (propName.equals("RealTypeName")) {
            return 99;
         } else if (propName.equals("RequiresAliasForSubselect")) {
            return 100;
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
         } else if (propName.equals("SupportsMultipleNontransactionalResultSets")) {
            return 66;
         } else {
            return propName.equals("TinyintTypeName") ? 125 : super.getPropertyIndex(propName);
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

            if (this.bean.isCharTypeNameSet()) {
               buf.append("CharTypeName");
               buf.append(String.valueOf(this.bean.getCharTypeName()));
            }

            if (this.bean.isClobTypeNameSet()) {
               buf.append("ClobTypeName");
               buf.append(String.valueOf(this.bean.getClobTypeName()));
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

            if (this.bean.isLastGeneratedKeyQuerySet()) {
               buf.append("LastGeneratedKeyQuery");
               buf.append(String.valueOf(this.bean.getLastGeneratedKeyQuery()));
            }

            if (this.bean.isLongVarbinaryTypeNameSet()) {
               buf.append("LongVarbinaryTypeName");
               buf.append(String.valueOf(this.bean.getLongVarbinaryTypeName()));
            }

            if (this.bean.isPlatformSet()) {
               buf.append("Platform");
               buf.append(String.valueOf(this.bean.getPlatform()));
            }

            if (this.bean.isRealTypeNameSet()) {
               buf.append("RealTypeName");
               buf.append(String.valueOf(this.bean.getRealTypeName()));
            }

            if (this.bean.isRequiresAliasForSubselectSet()) {
               buf.append("RequiresAliasForSubselect");
               buf.append(String.valueOf(this.bean.getRequiresAliasForSubselect()));
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

            if (this.bean.isSupportsMultipleNontransactionalResultSetsSet()) {
               buf.append("SupportsMultipleNontransactionalResultSets");
               buf.append(String.valueOf(this.bean.getSupportsMultipleNontransactionalResultSets()));
            }

            if (this.bean.isTinyintTypeNameSet()) {
               buf.append("TinyintTypeName");
               buf.append(String.valueOf(this.bean.getTinyintTypeName()));
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
            PointbaseDictionaryBeanImpl otherTyped = (PointbaseDictionaryBeanImpl)other;
            this.computeDiff("AutoAssignTypeName", this.bean.getAutoAssignTypeName(), otherTyped.getAutoAssignTypeName(), false);
            this.computeDiff("BigintTypeName", this.bean.getBigintTypeName(), otherTyped.getBigintTypeName(), false);
            this.computeDiff("BitTypeName", this.bean.getBitTypeName(), otherTyped.getBitTypeName(), false);
            this.computeDiff("BlobTypeName", this.bean.getBlobTypeName(), otherTyped.getBlobTypeName(), false);
            this.computeDiff("CharTypeName", this.bean.getCharTypeName(), otherTyped.getCharTypeName(), false);
            this.computeDiff("ClobTypeName", this.bean.getClobTypeName(), otherTyped.getClobTypeName(), false);
            this.computeDiff("DoubleTypeName", this.bean.getDoubleTypeName(), otherTyped.getDoubleTypeName(), false);
            this.computeDiff("FloatTypeName", this.bean.getFloatTypeName(), otherTyped.getFloatTypeName(), false);
            this.computeDiff("IntegerTypeName", this.bean.getIntegerTypeName(), otherTyped.getIntegerTypeName(), false);
            this.computeDiff("LastGeneratedKeyQuery", this.bean.getLastGeneratedKeyQuery(), otherTyped.getLastGeneratedKeyQuery(), false);
            this.computeDiff("LongVarbinaryTypeName", this.bean.getLongVarbinaryTypeName(), otherTyped.getLongVarbinaryTypeName(), false);
            this.computeDiff("Platform", this.bean.getPlatform(), otherTyped.getPlatform(), false);
            this.computeDiff("RealTypeName", this.bean.getRealTypeName(), otherTyped.getRealTypeName(), false);
            this.computeDiff("RequiresAliasForSubselect", this.bean.getRequiresAliasForSubselect(), otherTyped.getRequiresAliasForSubselect(), false);
            this.computeDiff("SmallintTypeName", this.bean.getSmallintTypeName(), otherTyped.getSmallintTypeName(), false);
            this.computeDiff("SupportsAutoAssign", this.bean.getSupportsAutoAssign(), otherTyped.getSupportsAutoAssign(), false);
            this.computeDiff("SupportsDeferredConstraints", this.bean.getSupportsDeferredConstraints(), otherTyped.getSupportsDeferredConstraints(), false);
            this.computeDiff("SupportsLockingWithDistinctClause", this.bean.getSupportsLockingWithDistinctClause(), otherTyped.getSupportsLockingWithDistinctClause(), false);
            this.computeDiff("SupportsLockingWithMultipleTables", this.bean.getSupportsLockingWithMultipleTables(), otherTyped.getSupportsLockingWithMultipleTables(), false);
            this.computeDiff("SupportsMultipleNontransactionalResultSets", this.bean.getSupportsMultipleNontransactionalResultSets(), otherTyped.getSupportsMultipleNontransactionalResultSets(), false);
            this.computeDiff("TinyintTypeName", this.bean.getTinyintTypeName(), otherTyped.getTinyintTypeName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PointbaseDictionaryBeanImpl original = (PointbaseDictionaryBeanImpl)event.getSourceBean();
            PointbaseDictionaryBeanImpl proposed = (PointbaseDictionaryBeanImpl)event.getProposedBean();
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
               } else if (prop.equals("CharTypeName")) {
                  original.setCharTypeName(proposed.getCharTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ClobTypeName")) {
                  original.setClobTypeName(proposed.getClobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("DoubleTypeName")) {
                  original.setDoubleTypeName(proposed.getDoubleTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("FloatTypeName")) {
                  original.setFloatTypeName(proposed.getFloatTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 70);
               } else if (prop.equals("IntegerTypeName")) {
                  original.setIntegerTypeName(proposed.getIntegerTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 75);
               } else if (prop.equals("LastGeneratedKeyQuery")) {
                  original.setLastGeneratedKeyQuery(proposed.getLastGeneratedKeyQuery());
                  original._conditionalUnset(update.isUnsetUpdate(), 94);
               } else if (prop.equals("LongVarbinaryTypeName")) {
                  original.setLongVarbinaryTypeName(proposed.getLongVarbinaryTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("Platform")) {
                  original.setPlatform(proposed.getPlatform());
                  original._conditionalUnset(update.isUnsetUpdate(), 84);
               } else if (prop.equals("RealTypeName")) {
                  original.setRealTypeName(proposed.getRealTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 99);
               } else if (prop.equals("RequiresAliasForSubselect")) {
                  original.setRequiresAliasForSubselect(proposed.getRequiresAliasForSubselect());
                  original._conditionalUnset(update.isUnsetUpdate(), 100);
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
               } else if (prop.equals("SupportsMultipleNontransactionalResultSets")) {
                  original.setSupportsMultipleNontransactionalResultSets(proposed.getSupportsMultipleNontransactionalResultSets());
                  original._conditionalUnset(update.isUnsetUpdate(), 66);
               } else if (prop.equals("TinyintTypeName")) {
                  original.setTinyintTypeName(proposed.getTinyintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 125);
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
            PointbaseDictionaryBeanImpl copy = (PointbaseDictionaryBeanImpl)initialCopy;
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

            if ((excludeProps == null || !excludeProps.contains("CharTypeName")) && this.bean.isCharTypeNameSet()) {
               copy.setCharTypeName(this.bean.getCharTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("ClobTypeName")) && this.bean.isClobTypeNameSet()) {
               copy.setClobTypeName(this.bean.getClobTypeName());
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

            if ((excludeProps == null || !excludeProps.contains("LastGeneratedKeyQuery")) && this.bean.isLastGeneratedKeyQuerySet()) {
               copy.setLastGeneratedKeyQuery(this.bean.getLastGeneratedKeyQuery());
            }

            if ((excludeProps == null || !excludeProps.contains("LongVarbinaryTypeName")) && this.bean.isLongVarbinaryTypeNameSet()) {
               copy.setLongVarbinaryTypeName(this.bean.getLongVarbinaryTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("Platform")) && this.bean.isPlatformSet()) {
               copy.setPlatform(this.bean.getPlatform());
            }

            if ((excludeProps == null || !excludeProps.contains("RealTypeName")) && this.bean.isRealTypeNameSet()) {
               copy.setRealTypeName(this.bean.getRealTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresAliasForSubselect")) && this.bean.isRequiresAliasForSubselectSet()) {
               copy.setRequiresAliasForSubselect(this.bean.getRequiresAliasForSubselect());
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

            if ((excludeProps == null || !excludeProps.contains("SupportsMultipleNontransactionalResultSets")) && this.bean.isSupportsMultipleNontransactionalResultSetsSet()) {
               copy.setSupportsMultipleNontransactionalResultSets(this.bean.getSupportsMultipleNontransactionalResultSets());
            }

            if ((excludeProps == null || !excludeProps.contains("TinyintTypeName")) && this.bean.isTinyintTypeNameSet()) {
               copy.setTinyintTypeName(this.bean.getTinyintTypeName());
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
