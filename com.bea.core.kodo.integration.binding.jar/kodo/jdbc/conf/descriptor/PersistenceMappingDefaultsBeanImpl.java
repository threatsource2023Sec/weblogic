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

public class PersistenceMappingDefaultsBeanImpl extends MappingDefaultsBeanImpl implements PersistenceMappingDefaultsBean, Serializable {
   private boolean _AddNullIndicator;
   private String _BaseClassStrategy;
   private String _DataStoreIdColumnName;
   private boolean _DefaultMissingInfo;
   private boolean _DeferConstraints;
   private String _DiscriminatorColumnName;
   private String _DiscriminatorStrategy;
   private String _FieldStrategies;
   private int _ForeignKeyDeleteAction;
   private boolean _IndexDiscriminator;
   private boolean _IndexLogicalForeignKeys;
   private boolean _IndexVersion;
   private String _JoinForeignKeyDeleteAction;
   private String _NullIndicatorColumnName;
   private String _OrderColumnName;
   private boolean _OrderLists;
   private boolean _StoreEnumOrdinal;
   private boolean _StoreUnmappedObjectIdString;
   private String _SubclassStrategy;
   private boolean _UseClassCriteria;
   private String _VersionColumnName;
   private String _VersionStrategy;
   private static SchemaHelper2 _schemaHelper;

   public PersistenceMappingDefaultsBeanImpl() {
      this._initializeProperty(-1);
   }

   public PersistenceMappingDefaultsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PersistenceMappingDefaultsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getUseClassCriteria() {
      return this._UseClassCriteria;
   }

   public boolean isUseClassCriteriaInherited() {
      return false;
   }

   public boolean isUseClassCriteriaSet() {
      return this._isSet(0);
   }

   public void setUseClassCriteria(boolean param0) {
      boolean _oldVal = this._UseClassCriteria;
      this._UseClassCriteria = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getBaseClassStrategy() {
      return this._BaseClassStrategy;
   }

   public boolean isBaseClassStrategyInherited() {
      return false;
   }

   public boolean isBaseClassStrategySet() {
      return this._isSet(1);
   }

   public void setBaseClassStrategy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BaseClassStrategy;
      this._BaseClassStrategy = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getVersionStrategy() {
      return this._VersionStrategy;
   }

   public boolean isVersionStrategyInherited() {
      return false;
   }

   public boolean isVersionStrategySet() {
      return this._isSet(2);
   }

   public void setVersionStrategy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._VersionStrategy;
      this._VersionStrategy = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getDiscriminatorColumnName() {
      return this._DiscriminatorColumnName;
   }

   public boolean isDiscriminatorColumnNameInherited() {
      return false;
   }

   public boolean isDiscriminatorColumnNameSet() {
      return this._isSet(3);
   }

   public void setDiscriminatorColumnName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DiscriminatorColumnName;
      this._DiscriminatorColumnName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getSubclassStrategy() {
      return this._SubclassStrategy;
   }

   public boolean isSubclassStrategyInherited() {
      return false;
   }

   public boolean isSubclassStrategySet() {
      return this._isSet(4);
   }

   public void setSubclassStrategy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SubclassStrategy;
      this._SubclassStrategy = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean getIndexVersion() {
      return this._IndexVersion;
   }

   public boolean isIndexVersionInherited() {
      return false;
   }

   public boolean isIndexVersionSet() {
      return this._isSet(5);
   }

   public void setIndexVersion(boolean param0) {
      boolean _oldVal = this._IndexVersion;
      this._IndexVersion = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean getDefaultMissingInfo() {
      return this._DefaultMissingInfo;
   }

   public boolean isDefaultMissingInfoInherited() {
      return false;
   }

   public boolean isDefaultMissingInfoSet() {
      return this._isSet(6);
   }

   public void setDefaultMissingInfo(boolean param0) {
      boolean _oldVal = this._DefaultMissingInfo;
      this._DefaultMissingInfo = param0;
      this._postSet(6, _oldVal, param0);
   }

   public boolean getIndexLogicalForeignKeys() {
      return this._IndexLogicalForeignKeys;
   }

   public boolean isIndexLogicalForeignKeysInherited() {
      return false;
   }

   public boolean isIndexLogicalForeignKeysSet() {
      return this._isSet(7);
   }

   public void setIndexLogicalForeignKeys(boolean param0) {
      boolean _oldVal = this._IndexLogicalForeignKeys;
      this._IndexLogicalForeignKeys = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getNullIndicatorColumnName() {
      return this._NullIndicatorColumnName;
   }

   public boolean isNullIndicatorColumnNameInherited() {
      return false;
   }

   public boolean isNullIndicatorColumnNameSet() {
      return this._isSet(8);
   }

   public void setNullIndicatorColumnName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NullIndicatorColumnName;
      this._NullIndicatorColumnName = param0;
      this._postSet(8, _oldVal, param0);
   }

   public int getForeignKeyDeleteAction() {
      return this._ForeignKeyDeleteAction;
   }

   public boolean isForeignKeyDeleteActionInherited() {
      return false;
   }

   public boolean isForeignKeyDeleteActionSet() {
      return this._isSet(9);
   }

   public void setForeignKeyDeleteAction(int param0) {
      int _oldVal = this._ForeignKeyDeleteAction;
      this._ForeignKeyDeleteAction = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getJoinForeignKeyDeleteAction() {
      return this._JoinForeignKeyDeleteAction;
   }

   public boolean isJoinForeignKeyDeleteActionInherited() {
      return false;
   }

   public boolean isJoinForeignKeyDeleteActionSet() {
      return this._isSet(10);
   }

   public void setJoinForeignKeyDeleteAction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JoinForeignKeyDeleteAction;
      this._JoinForeignKeyDeleteAction = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getDiscriminatorStrategy() {
      return this._DiscriminatorStrategy;
   }

   public boolean isDiscriminatorStrategyInherited() {
      return false;
   }

   public boolean isDiscriminatorStrategySet() {
      return this._isSet(11);
   }

   public void setDiscriminatorStrategy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DiscriminatorStrategy;
      this._DiscriminatorStrategy = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean getDeferConstraints() {
      return this._DeferConstraints;
   }

   public boolean isDeferConstraintsInherited() {
      return false;
   }

   public boolean isDeferConstraintsSet() {
      return this._isSet(12);
   }

   public void setDeferConstraints(boolean param0) {
      boolean _oldVal = this._DeferConstraints;
      this._DeferConstraints = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getFieldStrategies() {
      return this._FieldStrategies;
   }

   public boolean isFieldStrategiesInherited() {
      return false;
   }

   public boolean isFieldStrategiesSet() {
      return this._isSet(13);
   }

   public void setFieldStrategies(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FieldStrategies;
      this._FieldStrategies = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getVersionColumnName() {
      return this._VersionColumnName;
   }

   public boolean isVersionColumnNameInherited() {
      return false;
   }

   public boolean isVersionColumnNameSet() {
      return this._isSet(14);
   }

   public void setVersionColumnName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._VersionColumnName;
      this._VersionColumnName = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getDataStoreIdColumnName() {
      return this._DataStoreIdColumnName;
   }

   public boolean isDataStoreIdColumnNameInherited() {
      return false;
   }

   public boolean isDataStoreIdColumnNameSet() {
      return this._isSet(15);
   }

   public void setDataStoreIdColumnName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DataStoreIdColumnName;
      this._DataStoreIdColumnName = param0;
      this._postSet(15, _oldVal, param0);
   }

   public boolean getIndexDiscriminator() {
      return this._IndexDiscriminator;
   }

   public boolean isIndexDiscriminatorInherited() {
      return false;
   }

   public boolean isIndexDiscriminatorSet() {
      return this._isSet(16);
   }

   public void setIndexDiscriminator(boolean param0) {
      boolean _oldVal = this._IndexDiscriminator;
      this._IndexDiscriminator = param0;
      this._postSet(16, _oldVal, param0);
   }

   public boolean getStoreEnumOrdinal() {
      return this._StoreEnumOrdinal;
   }

   public boolean isStoreEnumOrdinalInherited() {
      return false;
   }

   public boolean isStoreEnumOrdinalSet() {
      return this._isSet(17);
   }

   public void setStoreEnumOrdinal(boolean param0) {
      boolean _oldVal = this._StoreEnumOrdinal;
      this._StoreEnumOrdinal = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean getOrderLists() {
      return this._OrderLists;
   }

   public boolean isOrderListsInherited() {
      return false;
   }

   public boolean isOrderListsSet() {
      return this._isSet(18);
   }

   public void setOrderLists(boolean param0) {
      boolean _oldVal = this._OrderLists;
      this._OrderLists = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getOrderColumnName() {
      return this._OrderColumnName;
   }

   public boolean isOrderColumnNameInherited() {
      return false;
   }

   public boolean isOrderColumnNameSet() {
      return this._isSet(19);
   }

   public void setOrderColumnName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OrderColumnName;
      this._OrderColumnName = param0;
      this._postSet(19, _oldVal, param0);
   }

   public boolean getAddNullIndicator() {
      return this._AddNullIndicator;
   }

   public boolean isAddNullIndicatorInherited() {
      return false;
   }

   public boolean isAddNullIndicatorSet() {
      return this._isSet(20);
   }

   public void setAddNullIndicator(boolean param0) {
      boolean _oldVal = this._AddNullIndicator;
      this._AddNullIndicator = param0;
      this._postSet(20, _oldVal, param0);
   }

   public boolean getStoreUnmappedObjectIdString() {
      return this._StoreUnmappedObjectIdString;
   }

   public boolean isStoreUnmappedObjectIdStringInherited() {
      return false;
   }

   public boolean isStoreUnmappedObjectIdStringSet() {
      return this._isSet(21);
   }

   public void setStoreUnmappedObjectIdString(boolean param0) {
      boolean _oldVal = this._StoreUnmappedObjectIdString;
      this._StoreUnmappedObjectIdString = param0;
      this._postSet(21, _oldVal, param0);
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
         idx = 20;
      }

      try {
         switch (idx) {
            case 20:
               this._AddNullIndicator = false;
               if (initOne) {
                  break;
               }
            case 1:
               this._BaseClassStrategy = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._DataStoreIdColumnName = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._DefaultMissingInfo = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._DeferConstraints = false;
               if (initOne) {
                  break;
               }
            case 3:
               this._DiscriminatorColumnName = "DTYPE";
               if (initOne) {
                  break;
               }
            case 11:
               this._DiscriminatorStrategy = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._FieldStrategies = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._ForeignKeyDeleteAction = 1;
               if (initOne) {
                  break;
               }
            case 16:
               this._IndexDiscriminator = true;
               if (initOne) {
                  break;
               }
            case 7:
               this._IndexLogicalForeignKeys = true;
               if (initOne) {
                  break;
               }
            case 5:
               this._IndexVersion = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._JoinForeignKeyDeleteAction = "1";
               if (initOne) {
                  break;
               }
            case 8:
               this._NullIndicatorColumnName = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._OrderColumnName = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._OrderLists = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._StoreEnumOrdinal = true;
               if (initOne) {
                  break;
               }
            case 21:
               this._StoreUnmappedObjectIdString = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._SubclassStrategy = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._UseClassCriteria = false;
               if (initOne) {
                  break;
               }
            case 14:
               this._VersionColumnName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._VersionStrategy = null;
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

   public static class SchemaHelper2 extends MappingDefaultsBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("order-lists")) {
                  return 18;
               }
            case 12:
            case 14:
            case 15:
            case 21:
            case 23:
            case 24:
            case 27:
            case 28:
            case 29:
            default:
               break;
            case 13:
               if (s.equals("index-version")) {
                  return 5;
               }
               break;
            case 16:
               if (s.equals("field-strategies")) {
                  return 13;
               }

               if (s.equals("version-strategy")) {
                  return 2;
               }
               break;
            case 17:
               if (s.equals("defer-constraints")) {
                  return 12;
               }

               if (s.equals("order-column-name")) {
                  return 19;
               }

               if (s.equals("subclass-strategy")) {
                  return 4;
               }
               break;
            case 18:
               if (s.equals("add-null-indicator")) {
                  return 20;
               }

               if (s.equals("store-enum-ordinal")) {
                  return 17;
               }

               if (s.equals("use-class-criteria")) {
                  return 0;
               }
               break;
            case 19:
               if (s.equals("base-class-strategy")) {
                  return 1;
               }

               if (s.equals("index-discriminator")) {
                  return 16;
               }

               if (s.equals("version-column-name")) {
                  return 14;
               }
               break;
            case 20:
               if (s.equals("default-missing-info")) {
                  return 6;
               }
               break;
            case 22:
               if (s.equals("discriminator-strategy")) {
                  return 11;
               }
               break;
            case 25:
               if (s.equals("data-store-id-column-name")) {
                  return 15;
               }

               if (s.equals("discriminator-column-name")) {
                  return 3;
               }

               if (s.equals("foreign-key-delete-action")) {
                  return 9;
               }
               break;
            case 26:
               if (s.equals("index-logical-foreign-keys")) {
                  return 7;
               }

               if (s.equals("null-indicator-column-name")) {
                  return 8;
               }
               break;
            case 30:
               if (s.equals("join-foreign-key-delete-action")) {
                  return 10;
               }
               break;
            case 31:
               if (s.equals("store-unmapped-object-id-string")) {
                  return 21;
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
            case 0:
               return "use-class-criteria";
            case 1:
               return "base-class-strategy";
            case 2:
               return "version-strategy";
            case 3:
               return "discriminator-column-name";
            case 4:
               return "subclass-strategy";
            case 5:
               return "index-version";
            case 6:
               return "default-missing-info";
            case 7:
               return "index-logical-foreign-keys";
            case 8:
               return "null-indicator-column-name";
            case 9:
               return "foreign-key-delete-action";
            case 10:
               return "join-foreign-key-delete-action";
            case 11:
               return "discriminator-strategy";
            case 12:
               return "defer-constraints";
            case 13:
               return "field-strategies";
            case 14:
               return "version-column-name";
            case 15:
               return "data-store-id-column-name";
            case 16:
               return "index-discriminator";
            case 17:
               return "store-enum-ordinal";
            case 18:
               return "order-lists";
            case 19:
               return "order-column-name";
            case 20:
               return "add-null-indicator";
            case 21:
               return "store-unmapped-object-id-string";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends MappingDefaultsBeanImpl.Helper {
      private PersistenceMappingDefaultsBeanImpl bean;

      protected Helper(PersistenceMappingDefaultsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "UseClassCriteria";
            case 1:
               return "BaseClassStrategy";
            case 2:
               return "VersionStrategy";
            case 3:
               return "DiscriminatorColumnName";
            case 4:
               return "SubclassStrategy";
            case 5:
               return "IndexVersion";
            case 6:
               return "DefaultMissingInfo";
            case 7:
               return "IndexLogicalForeignKeys";
            case 8:
               return "NullIndicatorColumnName";
            case 9:
               return "ForeignKeyDeleteAction";
            case 10:
               return "JoinForeignKeyDeleteAction";
            case 11:
               return "DiscriminatorStrategy";
            case 12:
               return "DeferConstraints";
            case 13:
               return "FieldStrategies";
            case 14:
               return "VersionColumnName";
            case 15:
               return "DataStoreIdColumnName";
            case 16:
               return "IndexDiscriminator";
            case 17:
               return "StoreEnumOrdinal";
            case 18:
               return "OrderLists";
            case 19:
               return "OrderColumnName";
            case 20:
               return "AddNullIndicator";
            case 21:
               return "StoreUnmappedObjectIdString";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AddNullIndicator")) {
            return 20;
         } else if (propName.equals("BaseClassStrategy")) {
            return 1;
         } else if (propName.equals("DataStoreIdColumnName")) {
            return 15;
         } else if (propName.equals("DefaultMissingInfo")) {
            return 6;
         } else if (propName.equals("DeferConstraints")) {
            return 12;
         } else if (propName.equals("DiscriminatorColumnName")) {
            return 3;
         } else if (propName.equals("DiscriminatorStrategy")) {
            return 11;
         } else if (propName.equals("FieldStrategies")) {
            return 13;
         } else if (propName.equals("ForeignKeyDeleteAction")) {
            return 9;
         } else if (propName.equals("IndexDiscriminator")) {
            return 16;
         } else if (propName.equals("IndexLogicalForeignKeys")) {
            return 7;
         } else if (propName.equals("IndexVersion")) {
            return 5;
         } else if (propName.equals("JoinForeignKeyDeleteAction")) {
            return 10;
         } else if (propName.equals("NullIndicatorColumnName")) {
            return 8;
         } else if (propName.equals("OrderColumnName")) {
            return 19;
         } else if (propName.equals("OrderLists")) {
            return 18;
         } else if (propName.equals("StoreEnumOrdinal")) {
            return 17;
         } else if (propName.equals("StoreUnmappedObjectIdString")) {
            return 21;
         } else if (propName.equals("SubclassStrategy")) {
            return 4;
         } else if (propName.equals("UseClassCriteria")) {
            return 0;
         } else if (propName.equals("VersionColumnName")) {
            return 14;
         } else {
            return propName.equals("VersionStrategy") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isAddNullIndicatorSet()) {
               buf.append("AddNullIndicator");
               buf.append(String.valueOf(this.bean.getAddNullIndicator()));
            }

            if (this.bean.isBaseClassStrategySet()) {
               buf.append("BaseClassStrategy");
               buf.append(String.valueOf(this.bean.getBaseClassStrategy()));
            }

            if (this.bean.isDataStoreIdColumnNameSet()) {
               buf.append("DataStoreIdColumnName");
               buf.append(String.valueOf(this.bean.getDataStoreIdColumnName()));
            }

            if (this.bean.isDefaultMissingInfoSet()) {
               buf.append("DefaultMissingInfo");
               buf.append(String.valueOf(this.bean.getDefaultMissingInfo()));
            }

            if (this.bean.isDeferConstraintsSet()) {
               buf.append("DeferConstraints");
               buf.append(String.valueOf(this.bean.getDeferConstraints()));
            }

            if (this.bean.isDiscriminatorColumnNameSet()) {
               buf.append("DiscriminatorColumnName");
               buf.append(String.valueOf(this.bean.getDiscriminatorColumnName()));
            }

            if (this.bean.isDiscriminatorStrategySet()) {
               buf.append("DiscriminatorStrategy");
               buf.append(String.valueOf(this.bean.getDiscriminatorStrategy()));
            }

            if (this.bean.isFieldStrategiesSet()) {
               buf.append("FieldStrategies");
               buf.append(String.valueOf(this.bean.getFieldStrategies()));
            }

            if (this.bean.isForeignKeyDeleteActionSet()) {
               buf.append("ForeignKeyDeleteAction");
               buf.append(String.valueOf(this.bean.getForeignKeyDeleteAction()));
            }

            if (this.bean.isIndexDiscriminatorSet()) {
               buf.append("IndexDiscriminator");
               buf.append(String.valueOf(this.bean.getIndexDiscriminator()));
            }

            if (this.bean.isIndexLogicalForeignKeysSet()) {
               buf.append("IndexLogicalForeignKeys");
               buf.append(String.valueOf(this.bean.getIndexLogicalForeignKeys()));
            }

            if (this.bean.isIndexVersionSet()) {
               buf.append("IndexVersion");
               buf.append(String.valueOf(this.bean.getIndexVersion()));
            }

            if (this.bean.isJoinForeignKeyDeleteActionSet()) {
               buf.append("JoinForeignKeyDeleteAction");
               buf.append(String.valueOf(this.bean.getJoinForeignKeyDeleteAction()));
            }

            if (this.bean.isNullIndicatorColumnNameSet()) {
               buf.append("NullIndicatorColumnName");
               buf.append(String.valueOf(this.bean.getNullIndicatorColumnName()));
            }

            if (this.bean.isOrderColumnNameSet()) {
               buf.append("OrderColumnName");
               buf.append(String.valueOf(this.bean.getOrderColumnName()));
            }

            if (this.bean.isOrderListsSet()) {
               buf.append("OrderLists");
               buf.append(String.valueOf(this.bean.getOrderLists()));
            }

            if (this.bean.isStoreEnumOrdinalSet()) {
               buf.append("StoreEnumOrdinal");
               buf.append(String.valueOf(this.bean.getStoreEnumOrdinal()));
            }

            if (this.bean.isStoreUnmappedObjectIdStringSet()) {
               buf.append("StoreUnmappedObjectIdString");
               buf.append(String.valueOf(this.bean.getStoreUnmappedObjectIdString()));
            }

            if (this.bean.isSubclassStrategySet()) {
               buf.append("SubclassStrategy");
               buf.append(String.valueOf(this.bean.getSubclassStrategy()));
            }

            if (this.bean.isUseClassCriteriaSet()) {
               buf.append("UseClassCriteria");
               buf.append(String.valueOf(this.bean.getUseClassCriteria()));
            }

            if (this.bean.isVersionColumnNameSet()) {
               buf.append("VersionColumnName");
               buf.append(String.valueOf(this.bean.getVersionColumnName()));
            }

            if (this.bean.isVersionStrategySet()) {
               buf.append("VersionStrategy");
               buf.append(String.valueOf(this.bean.getVersionStrategy()));
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
            PersistenceMappingDefaultsBeanImpl otherTyped = (PersistenceMappingDefaultsBeanImpl)other;
            this.computeDiff("AddNullIndicator", this.bean.getAddNullIndicator(), otherTyped.getAddNullIndicator(), false);
            this.computeDiff("BaseClassStrategy", this.bean.getBaseClassStrategy(), otherTyped.getBaseClassStrategy(), false);
            this.computeDiff("DataStoreIdColumnName", this.bean.getDataStoreIdColumnName(), otherTyped.getDataStoreIdColumnName(), false);
            this.computeDiff("DefaultMissingInfo", this.bean.getDefaultMissingInfo(), otherTyped.getDefaultMissingInfo(), false);
            this.computeDiff("DeferConstraints", this.bean.getDeferConstraints(), otherTyped.getDeferConstraints(), false);
            this.computeDiff("DiscriminatorColumnName", this.bean.getDiscriminatorColumnName(), otherTyped.getDiscriminatorColumnName(), false);
            this.computeDiff("DiscriminatorStrategy", this.bean.getDiscriminatorStrategy(), otherTyped.getDiscriminatorStrategy(), false);
            this.computeDiff("FieldStrategies", this.bean.getFieldStrategies(), otherTyped.getFieldStrategies(), false);
            this.computeDiff("ForeignKeyDeleteAction", this.bean.getForeignKeyDeleteAction(), otherTyped.getForeignKeyDeleteAction(), false);
            this.computeDiff("IndexDiscriminator", this.bean.getIndexDiscriminator(), otherTyped.getIndexDiscriminator(), false);
            this.computeDiff("IndexLogicalForeignKeys", this.bean.getIndexLogicalForeignKeys(), otherTyped.getIndexLogicalForeignKeys(), false);
            this.computeDiff("IndexVersion", this.bean.getIndexVersion(), otherTyped.getIndexVersion(), false);
            this.computeDiff("JoinForeignKeyDeleteAction", this.bean.getJoinForeignKeyDeleteAction(), otherTyped.getJoinForeignKeyDeleteAction(), false);
            this.computeDiff("NullIndicatorColumnName", this.bean.getNullIndicatorColumnName(), otherTyped.getNullIndicatorColumnName(), false);
            this.computeDiff("OrderColumnName", this.bean.getOrderColumnName(), otherTyped.getOrderColumnName(), false);
            this.computeDiff("OrderLists", this.bean.getOrderLists(), otherTyped.getOrderLists(), false);
            this.computeDiff("StoreEnumOrdinal", this.bean.getStoreEnumOrdinal(), otherTyped.getStoreEnumOrdinal(), false);
            this.computeDiff("StoreUnmappedObjectIdString", this.bean.getStoreUnmappedObjectIdString(), otherTyped.getStoreUnmappedObjectIdString(), false);
            this.computeDiff("SubclassStrategy", this.bean.getSubclassStrategy(), otherTyped.getSubclassStrategy(), false);
            this.computeDiff("UseClassCriteria", this.bean.getUseClassCriteria(), otherTyped.getUseClassCriteria(), false);
            this.computeDiff("VersionColumnName", this.bean.getVersionColumnName(), otherTyped.getVersionColumnName(), false);
            this.computeDiff("VersionStrategy", this.bean.getVersionStrategy(), otherTyped.getVersionStrategy(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PersistenceMappingDefaultsBeanImpl original = (PersistenceMappingDefaultsBeanImpl)event.getSourceBean();
            PersistenceMappingDefaultsBeanImpl proposed = (PersistenceMappingDefaultsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AddNullIndicator")) {
                  original.setAddNullIndicator(proposed.getAddNullIndicator());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("BaseClassStrategy")) {
                  original.setBaseClassStrategy(proposed.getBaseClassStrategy());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("DataStoreIdColumnName")) {
                  original.setDataStoreIdColumnName(proposed.getDataStoreIdColumnName());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("DefaultMissingInfo")) {
                  original.setDefaultMissingInfo(proposed.getDefaultMissingInfo());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("DeferConstraints")) {
                  original.setDeferConstraints(proposed.getDeferConstraints());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("DiscriminatorColumnName")) {
                  original.setDiscriminatorColumnName(proposed.getDiscriminatorColumnName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("DiscriminatorStrategy")) {
                  original.setDiscriminatorStrategy(proposed.getDiscriminatorStrategy());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("FieldStrategies")) {
                  original.setFieldStrategies(proposed.getFieldStrategies());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ForeignKeyDeleteAction")) {
                  original.setForeignKeyDeleteAction(proposed.getForeignKeyDeleteAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("IndexDiscriminator")) {
                  original.setIndexDiscriminator(proposed.getIndexDiscriminator());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("IndexLogicalForeignKeys")) {
                  original.setIndexLogicalForeignKeys(proposed.getIndexLogicalForeignKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("IndexVersion")) {
                  original.setIndexVersion(proposed.getIndexVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("JoinForeignKeyDeleteAction")) {
                  original.setJoinForeignKeyDeleteAction(proposed.getJoinForeignKeyDeleteAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("NullIndicatorColumnName")) {
                  original.setNullIndicatorColumnName(proposed.getNullIndicatorColumnName());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("OrderColumnName")) {
                  original.setOrderColumnName(proposed.getOrderColumnName());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("OrderLists")) {
                  original.setOrderLists(proposed.getOrderLists());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("StoreEnumOrdinal")) {
                  original.setStoreEnumOrdinal(proposed.getStoreEnumOrdinal());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("StoreUnmappedObjectIdString")) {
                  original.setStoreUnmappedObjectIdString(proposed.getStoreUnmappedObjectIdString());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("SubclassStrategy")) {
                  original.setSubclassStrategy(proposed.getSubclassStrategy());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("UseClassCriteria")) {
                  original.setUseClassCriteria(proposed.getUseClassCriteria());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("VersionColumnName")) {
                  original.setVersionColumnName(proposed.getVersionColumnName());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("VersionStrategy")) {
                  original.setVersionStrategy(proposed.getVersionStrategy());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            PersistenceMappingDefaultsBeanImpl copy = (PersistenceMappingDefaultsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AddNullIndicator")) && this.bean.isAddNullIndicatorSet()) {
               copy.setAddNullIndicator(this.bean.getAddNullIndicator());
            }

            if ((excludeProps == null || !excludeProps.contains("BaseClassStrategy")) && this.bean.isBaseClassStrategySet()) {
               copy.setBaseClassStrategy(this.bean.getBaseClassStrategy());
            }

            if ((excludeProps == null || !excludeProps.contains("DataStoreIdColumnName")) && this.bean.isDataStoreIdColumnNameSet()) {
               copy.setDataStoreIdColumnName(this.bean.getDataStoreIdColumnName());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultMissingInfo")) && this.bean.isDefaultMissingInfoSet()) {
               copy.setDefaultMissingInfo(this.bean.getDefaultMissingInfo());
            }

            if ((excludeProps == null || !excludeProps.contains("DeferConstraints")) && this.bean.isDeferConstraintsSet()) {
               copy.setDeferConstraints(this.bean.getDeferConstraints());
            }

            if ((excludeProps == null || !excludeProps.contains("DiscriminatorColumnName")) && this.bean.isDiscriminatorColumnNameSet()) {
               copy.setDiscriminatorColumnName(this.bean.getDiscriminatorColumnName());
            }

            if ((excludeProps == null || !excludeProps.contains("DiscriminatorStrategy")) && this.bean.isDiscriminatorStrategySet()) {
               copy.setDiscriminatorStrategy(this.bean.getDiscriminatorStrategy());
            }

            if ((excludeProps == null || !excludeProps.contains("FieldStrategies")) && this.bean.isFieldStrategiesSet()) {
               copy.setFieldStrategies(this.bean.getFieldStrategies());
            }

            if ((excludeProps == null || !excludeProps.contains("ForeignKeyDeleteAction")) && this.bean.isForeignKeyDeleteActionSet()) {
               copy.setForeignKeyDeleteAction(this.bean.getForeignKeyDeleteAction());
            }

            if ((excludeProps == null || !excludeProps.contains("IndexDiscriminator")) && this.bean.isIndexDiscriminatorSet()) {
               copy.setIndexDiscriminator(this.bean.getIndexDiscriminator());
            }

            if ((excludeProps == null || !excludeProps.contains("IndexLogicalForeignKeys")) && this.bean.isIndexLogicalForeignKeysSet()) {
               copy.setIndexLogicalForeignKeys(this.bean.getIndexLogicalForeignKeys());
            }

            if ((excludeProps == null || !excludeProps.contains("IndexVersion")) && this.bean.isIndexVersionSet()) {
               copy.setIndexVersion(this.bean.getIndexVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("JoinForeignKeyDeleteAction")) && this.bean.isJoinForeignKeyDeleteActionSet()) {
               copy.setJoinForeignKeyDeleteAction(this.bean.getJoinForeignKeyDeleteAction());
            }

            if ((excludeProps == null || !excludeProps.contains("NullIndicatorColumnName")) && this.bean.isNullIndicatorColumnNameSet()) {
               copy.setNullIndicatorColumnName(this.bean.getNullIndicatorColumnName());
            }

            if ((excludeProps == null || !excludeProps.contains("OrderColumnName")) && this.bean.isOrderColumnNameSet()) {
               copy.setOrderColumnName(this.bean.getOrderColumnName());
            }

            if ((excludeProps == null || !excludeProps.contains("OrderLists")) && this.bean.isOrderListsSet()) {
               copy.setOrderLists(this.bean.getOrderLists());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreEnumOrdinal")) && this.bean.isStoreEnumOrdinalSet()) {
               copy.setStoreEnumOrdinal(this.bean.getStoreEnumOrdinal());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreUnmappedObjectIdString")) && this.bean.isStoreUnmappedObjectIdStringSet()) {
               copy.setStoreUnmappedObjectIdString(this.bean.getStoreUnmappedObjectIdString());
            }

            if ((excludeProps == null || !excludeProps.contains("SubclassStrategy")) && this.bean.isSubclassStrategySet()) {
               copy.setSubclassStrategy(this.bean.getSubclassStrategy());
            }

            if ((excludeProps == null || !excludeProps.contains("UseClassCriteria")) && this.bean.isUseClassCriteriaSet()) {
               copy.setUseClassCriteria(this.bean.getUseClassCriteria());
            }

            if ((excludeProps == null || !excludeProps.contains("VersionColumnName")) && this.bean.isVersionColumnNameSet()) {
               copy.setVersionColumnName(this.bean.getVersionColumnName());
            }

            if ((excludeProps == null || !excludeProps.contains("VersionStrategy")) && this.bean.isVersionStrategySet()) {
               copy.setVersionStrategy(this.bean.getVersionStrategy());
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
