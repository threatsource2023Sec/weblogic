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

public class AccessDictionaryBeanImpl extends BuiltInDBDictionaryBeanImpl implements AccessDictionaryBean, Serializable {
   private String _AutoAssignTypeName;
   private String _BigintTypeName;
   private String _BinaryTypeName;
   private String _BlobTypeName;
   private String _ClobTypeName;
   private String _IntegerTypeName;
   private String _JoinSyntax;
   private String _LastGeneratedKeyQuery;
   private String _LongVarbinaryTypeName;
   private String _LongVarcharTypeName;
   private int _MaxColumnNameLength;
   private int _MaxConstraintNameLength;
   private int _MaxIndexNameLength;
   private int _MaxIndexesPerTable;
   private int _MaxTableNameLength;
   private String _NumericTypeName;
   private String _Platform;
   private String _SmallintTypeName;
   private boolean _SupportsAutoAssign;
   private boolean _SupportsDeferredConstraints;
   private boolean _SupportsForeignKeys;
   private String _TinyintTypeName;
   private boolean _UseGetBestRowIdentifierForPrimaryKeys;
   private boolean _UseGetBytesForBlobs;
   private String _ValidationSQL;
   private static SchemaHelper2 _schemaHelper;

   public AccessDictionaryBeanImpl() {
      this._initializeProperty(-1);
   }

   public AccessDictionaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AccessDictionaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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
         idx = 57;
      }

      try {
         switch (idx) {
            case 57:
               this._AutoAssignTypeName = "COUNTER";
               if (initOne) {
                  break;
               }
            case 93:
               this._BigintTypeName = "REAL";
               if (initOne) {
                  break;
               }
            case 3:
               this._BinaryTypeName = "LONGBINARY";
               if (initOne) {
                  break;
               }
            case 76:
               this._BlobTypeName = "LONGBINARY";
               if (initOne) {
                  break;
               }
            case 4:
               this._ClobTypeName = "LONGCHAR";
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
            case 94:
               this._LastGeneratedKeyQuery = "SELECT @@identity";
               if (initOne) {
                  break;
               }
            case 16:
               this._LongVarbinaryTypeName = "LONGBINARY";
               if (initOne) {
                  break;
               }
            case 22:
               this._LongVarcharTypeName = "LONGCHAR";
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
            case 73:
               this._NumericTypeName = "REAL";
               if (initOne) {
                  break;
               }
            case 84:
               this._Platform = "Microsoft Access";
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
            case 80:
               this._SupportsForeignKeys = false;
               if (initOne) {
                  break;
               }
            case 125:
               this._TinyintTypeName = "SMALLINT";
               if (initOne) {
                  break;
               }
            case 79:
               this._UseGetBestRowIdentifierForPrimaryKeys = true;
               if (initOne) {
                  break;
               }
            case 71:
               this._UseGetBytesForBlobs = true;
               if (initOne) {
                  break;
               }
            case 60:
               this._ValidationSQL = "SELECT 1";
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
            case 12:
            case 13:
            case 15:
            case 19:
            case 25:
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
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            default:
               break;
            case 11:
               if (s.equals("join-syntax")) {
                  return 116;
               }
               break;
            case 14:
               if (s.equals("blob-type-name")) {
                  return 76;
               }

               if (s.equals("clob-type-name")) {
                  return 4;
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
               break;
            case 17:
               if (s.equals("integer-type-name")) {
                  return 75;
               }

               if (s.equals("numeric-type-name")) {
                  return 73;
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
               if (s.equals("use-get-bytes-for-blobs")) {
                  return 71;
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
            case 44:
               if (s.equals("use-get-best-row-identifier-for-primary-keys")) {
                  return 79;
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
            case 12:
               return "max-constraint-name-length";
            case 16:
               return "long-varbinary-type-name";
            case 22:
               return "long-varchar-type-name";
            case 38:
               return "max-column-name-length";
            case 42:
               return "smallint-type-name";
            case 49:
               return "supports-auto-assign";
            case 57:
               return "auto-assign-type-name";
            case 60:
               return "validation-sql";
            case 71:
               return "use-get-bytes-for-blobs";
            case 73:
               return "numeric-type-name";
            case 75:
               return "integer-type-name";
            case 76:
               return "blob-type-name";
            case 79:
               return "use-get-best-row-identifier-for-primary-keys";
            case 80:
               return "supports-foreign-keys";
            case 84:
               return "platform";
            case 87:
               return "max-indexes-per-table";
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
            case 116:
               return "join-syntax";
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
      private AccessDictionaryBeanImpl bean;

      protected Helper(AccessDictionaryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "BinaryTypeName";
            case 4:
               return "ClobTypeName";
            case 12:
               return "MaxConstraintNameLength";
            case 16:
               return "LongVarbinaryTypeName";
            case 22:
               return "LongVarcharTypeName";
            case 38:
               return "MaxColumnNameLength";
            case 42:
               return "SmallintTypeName";
            case 49:
               return "SupportsAutoAssign";
            case 57:
               return "AutoAssignTypeName";
            case 60:
               return "ValidationSQL";
            case 71:
               return "UseGetBytesForBlobs";
            case 73:
               return "NumericTypeName";
            case 75:
               return "IntegerTypeName";
            case 76:
               return "BlobTypeName";
            case 79:
               return "UseGetBestRowIdentifierForPrimaryKeys";
            case 80:
               return "SupportsForeignKeys";
            case 84:
               return "Platform";
            case 87:
               return "MaxIndexesPerTable";
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
            case 116:
               return "JoinSyntax";
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
         } else if (propName.equals("BinaryTypeName")) {
            return 3;
         } else if (propName.equals("BlobTypeName")) {
            return 76;
         } else if (propName.equals("ClobTypeName")) {
            return 4;
         } else if (propName.equals("IntegerTypeName")) {
            return 75;
         } else if (propName.equals("JoinSyntax")) {
            return 116;
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
         } else if (propName.equals("NumericTypeName")) {
            return 73;
         } else if (propName.equals("Platform")) {
            return 84;
         } else if (propName.equals("SmallintTypeName")) {
            return 42;
         } else if (propName.equals("SupportsAutoAssign")) {
            return 49;
         } else if (propName.equals("SupportsDeferredConstraints")) {
            return 98;
         } else if (propName.equals("SupportsForeignKeys")) {
            return 80;
         } else if (propName.equals("TinyintTypeName")) {
            return 125;
         } else if (propName.equals("UseGetBestRowIdentifierForPrimaryKeys")) {
            return 79;
         } else if (propName.equals("UseGetBytesForBlobs")) {
            return 71;
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

            if (this.bean.isBinaryTypeNameSet()) {
               buf.append("BinaryTypeName");
               buf.append(String.valueOf(this.bean.getBinaryTypeName()));
            }

            if (this.bean.isBlobTypeNameSet()) {
               buf.append("BlobTypeName");
               buf.append(String.valueOf(this.bean.getBlobTypeName()));
            }

            if (this.bean.isClobTypeNameSet()) {
               buf.append("ClobTypeName");
               buf.append(String.valueOf(this.bean.getClobTypeName()));
            }

            if (this.bean.isIntegerTypeNameSet()) {
               buf.append("IntegerTypeName");
               buf.append(String.valueOf(this.bean.getIntegerTypeName()));
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

            if (this.bean.isNumericTypeNameSet()) {
               buf.append("NumericTypeName");
               buf.append(String.valueOf(this.bean.getNumericTypeName()));
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

            if (this.bean.isSupportsForeignKeysSet()) {
               buf.append("SupportsForeignKeys");
               buf.append(String.valueOf(this.bean.getSupportsForeignKeys()));
            }

            if (this.bean.isTinyintTypeNameSet()) {
               buf.append("TinyintTypeName");
               buf.append(String.valueOf(this.bean.getTinyintTypeName()));
            }

            if (this.bean.isUseGetBestRowIdentifierForPrimaryKeysSet()) {
               buf.append("UseGetBestRowIdentifierForPrimaryKeys");
               buf.append(String.valueOf(this.bean.getUseGetBestRowIdentifierForPrimaryKeys()));
            }

            if (this.bean.isUseGetBytesForBlobsSet()) {
               buf.append("UseGetBytesForBlobs");
               buf.append(String.valueOf(this.bean.getUseGetBytesForBlobs()));
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
            AccessDictionaryBeanImpl otherTyped = (AccessDictionaryBeanImpl)other;
            this.computeDiff("AutoAssignTypeName", this.bean.getAutoAssignTypeName(), otherTyped.getAutoAssignTypeName(), false);
            this.computeDiff("BigintTypeName", this.bean.getBigintTypeName(), otherTyped.getBigintTypeName(), false);
            this.computeDiff("BinaryTypeName", this.bean.getBinaryTypeName(), otherTyped.getBinaryTypeName(), false);
            this.computeDiff("BlobTypeName", this.bean.getBlobTypeName(), otherTyped.getBlobTypeName(), false);
            this.computeDiff("ClobTypeName", this.bean.getClobTypeName(), otherTyped.getClobTypeName(), false);
            this.computeDiff("IntegerTypeName", this.bean.getIntegerTypeName(), otherTyped.getIntegerTypeName(), false);
            this.computeDiff("JoinSyntax", this.bean.getJoinSyntax(), otherTyped.getJoinSyntax(), false);
            this.computeDiff("LastGeneratedKeyQuery", this.bean.getLastGeneratedKeyQuery(), otherTyped.getLastGeneratedKeyQuery(), false);
            this.computeDiff("LongVarbinaryTypeName", this.bean.getLongVarbinaryTypeName(), otherTyped.getLongVarbinaryTypeName(), false);
            this.computeDiff("LongVarcharTypeName", this.bean.getLongVarcharTypeName(), otherTyped.getLongVarcharTypeName(), false);
            this.computeDiff("MaxColumnNameLength", this.bean.getMaxColumnNameLength(), otherTyped.getMaxColumnNameLength(), false);
            this.computeDiff("MaxConstraintNameLength", this.bean.getMaxConstraintNameLength(), otherTyped.getMaxConstraintNameLength(), false);
            this.computeDiff("MaxIndexNameLength", this.bean.getMaxIndexNameLength(), otherTyped.getMaxIndexNameLength(), false);
            this.computeDiff("MaxIndexesPerTable", this.bean.getMaxIndexesPerTable(), otherTyped.getMaxIndexesPerTable(), false);
            this.computeDiff("MaxTableNameLength", this.bean.getMaxTableNameLength(), otherTyped.getMaxTableNameLength(), false);
            this.computeDiff("NumericTypeName", this.bean.getNumericTypeName(), otherTyped.getNumericTypeName(), false);
            this.computeDiff("Platform", this.bean.getPlatform(), otherTyped.getPlatform(), false);
            this.computeDiff("SmallintTypeName", this.bean.getSmallintTypeName(), otherTyped.getSmallintTypeName(), false);
            this.computeDiff("SupportsAutoAssign", this.bean.getSupportsAutoAssign(), otherTyped.getSupportsAutoAssign(), false);
            this.computeDiff("SupportsDeferredConstraints", this.bean.getSupportsDeferredConstraints(), otherTyped.getSupportsDeferredConstraints(), false);
            this.computeDiff("SupportsForeignKeys", this.bean.getSupportsForeignKeys(), otherTyped.getSupportsForeignKeys(), false);
            this.computeDiff("TinyintTypeName", this.bean.getTinyintTypeName(), otherTyped.getTinyintTypeName(), false);
            this.computeDiff("UseGetBestRowIdentifierForPrimaryKeys", this.bean.getUseGetBestRowIdentifierForPrimaryKeys(), otherTyped.getUseGetBestRowIdentifierForPrimaryKeys(), false);
            this.computeDiff("UseGetBytesForBlobs", this.bean.getUseGetBytesForBlobs(), otherTyped.getUseGetBytesForBlobs(), false);
            this.computeDiff("ValidationSQL", this.bean.getValidationSQL(), otherTyped.getValidationSQL(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AccessDictionaryBeanImpl original = (AccessDictionaryBeanImpl)event.getSourceBean();
            AccessDictionaryBeanImpl proposed = (AccessDictionaryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AutoAssignTypeName")) {
                  original.setAutoAssignTypeName(proposed.getAutoAssignTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 57);
               } else if (prop.equals("BigintTypeName")) {
                  original.setBigintTypeName(proposed.getBigintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 93);
               } else if (prop.equals("BinaryTypeName")) {
                  original.setBinaryTypeName(proposed.getBinaryTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("BlobTypeName")) {
                  original.setBlobTypeName(proposed.getBlobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 76);
               } else if (prop.equals("ClobTypeName")) {
                  original.setClobTypeName(proposed.getClobTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("IntegerTypeName")) {
                  original.setIntegerTypeName(proposed.getIntegerTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 75);
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
               } else if (prop.equals("NumericTypeName")) {
                  original.setNumericTypeName(proposed.getNumericTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 73);
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
               } else if (prop.equals("SupportsForeignKeys")) {
                  original.setSupportsForeignKeys(proposed.getSupportsForeignKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 80);
               } else if (prop.equals("TinyintTypeName")) {
                  original.setTinyintTypeName(proposed.getTinyintTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 125);
               } else if (prop.equals("UseGetBestRowIdentifierForPrimaryKeys")) {
                  original.setUseGetBestRowIdentifierForPrimaryKeys(proposed.getUseGetBestRowIdentifierForPrimaryKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 79);
               } else if (prop.equals("UseGetBytesForBlobs")) {
                  original.setUseGetBytesForBlobs(proposed.getUseGetBytesForBlobs());
                  original._conditionalUnset(update.isUnsetUpdate(), 71);
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
            AccessDictionaryBeanImpl copy = (AccessDictionaryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AutoAssignTypeName")) && this.bean.isAutoAssignTypeNameSet()) {
               copy.setAutoAssignTypeName(this.bean.getAutoAssignTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BigintTypeName")) && this.bean.isBigintTypeNameSet()) {
               copy.setBigintTypeName(this.bean.getBigintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BinaryTypeName")) && this.bean.isBinaryTypeNameSet()) {
               copy.setBinaryTypeName(this.bean.getBinaryTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("BlobTypeName")) && this.bean.isBlobTypeNameSet()) {
               copy.setBlobTypeName(this.bean.getBlobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("ClobTypeName")) && this.bean.isClobTypeNameSet()) {
               copy.setClobTypeName(this.bean.getClobTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("IntegerTypeName")) && this.bean.isIntegerTypeNameSet()) {
               copy.setIntegerTypeName(this.bean.getIntegerTypeName());
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

            if ((excludeProps == null || !excludeProps.contains("NumericTypeName")) && this.bean.isNumericTypeNameSet()) {
               copy.setNumericTypeName(this.bean.getNumericTypeName());
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

            if ((excludeProps == null || !excludeProps.contains("SupportsForeignKeys")) && this.bean.isSupportsForeignKeysSet()) {
               copy.setSupportsForeignKeys(this.bean.getSupportsForeignKeys());
            }

            if ((excludeProps == null || !excludeProps.contains("TinyintTypeName")) && this.bean.isTinyintTypeNameSet()) {
               copy.setTinyintTypeName(this.bean.getTinyintTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("UseGetBestRowIdentifierForPrimaryKeys")) && this.bean.isUseGetBestRowIdentifierForPrimaryKeysSet()) {
               copy.setUseGetBestRowIdentifierForPrimaryKeys(this.bean.getUseGetBestRowIdentifierForPrimaryKeys());
            }

            if ((excludeProps == null || !excludeProps.contains("UseGetBytesForBlobs")) && this.bean.isUseGetBytesForBlobsSet()) {
               copy.setUseGetBytesForBlobs(this.bean.getUseGetBytesForBlobs());
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
