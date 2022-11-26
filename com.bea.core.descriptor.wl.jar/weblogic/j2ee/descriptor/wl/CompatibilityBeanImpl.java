package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class CompatibilityBeanImpl extends AbstractDescriptorBean implements CompatibilityBean, Serializable {
   private boolean _AllowReadonlyCreateAndRemove;
   private boolean _DisableStringTrimming;
   private boolean _FindersReturnNulls;
   private String _Id;
   private boolean _LoadRelatedBeansFromDbInPostCreate;
   private boolean _SerializeByteArrayToOracleBlob;
   private boolean _SerializeCharArrayToBytes;
   private static SchemaHelper2 _schemaHelper;

   public CompatibilityBeanImpl() {
      this._initializeProperty(-1);
   }

   public CompatibilityBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CompatibilityBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isSerializeByteArrayToOracleBlob() {
      return this._SerializeByteArrayToOracleBlob;
   }

   public boolean isSerializeByteArrayToOracleBlobInherited() {
      return false;
   }

   public boolean isSerializeByteArrayToOracleBlobSet() {
      return this._isSet(0);
   }

   public void setSerializeByteArrayToOracleBlob(boolean param0) {
      boolean _oldVal = this._SerializeByteArrayToOracleBlob;
      this._SerializeByteArrayToOracleBlob = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isSerializeCharArrayToBytes() {
      return this._SerializeCharArrayToBytes;
   }

   public boolean isSerializeCharArrayToBytesInherited() {
      return false;
   }

   public boolean isSerializeCharArrayToBytesSet() {
      return this._isSet(1);
   }

   public void setSerializeCharArrayToBytes(boolean param0) {
      boolean _oldVal = this._SerializeCharArrayToBytes;
      this._SerializeCharArrayToBytes = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean isAllowReadonlyCreateAndRemove() {
      return this._AllowReadonlyCreateAndRemove;
   }

   public boolean isAllowReadonlyCreateAndRemoveInherited() {
      return false;
   }

   public boolean isAllowReadonlyCreateAndRemoveSet() {
      return this._isSet(2);
   }

   public void setAllowReadonlyCreateAndRemove(boolean param0) {
      boolean _oldVal = this._AllowReadonlyCreateAndRemove;
      this._AllowReadonlyCreateAndRemove = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isDisableStringTrimming() {
      return this._DisableStringTrimming;
   }

   public boolean isDisableStringTrimmingInherited() {
      return false;
   }

   public boolean isDisableStringTrimmingSet() {
      return this._isSet(3);
   }

   public void setDisableStringTrimming(boolean param0) {
      boolean _oldVal = this._DisableStringTrimming;
      this._DisableStringTrimming = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isFindersReturnNulls() {
      return this._FindersReturnNulls;
   }

   public boolean isFindersReturnNullsInherited() {
      return false;
   }

   public boolean isFindersReturnNullsSet() {
      return this._isSet(4);
   }

   public void setFindersReturnNulls(boolean param0) {
      boolean _oldVal = this._FindersReturnNulls;
      this._FindersReturnNulls = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isLoadRelatedBeansFromDbInPostCreate() {
      return this._LoadRelatedBeansFromDbInPostCreate;
   }

   public boolean isLoadRelatedBeansFromDbInPostCreateInherited() {
      return false;
   }

   public boolean isLoadRelatedBeansFromDbInPostCreateSet() {
      return this._isSet(5);
   }

   public void setLoadRelatedBeansFromDbInPostCreate(boolean param0) {
      boolean _oldVal = this._LoadRelatedBeansFromDbInPostCreate;
      this._LoadRelatedBeansFromDbInPostCreate = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(6);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(6, _oldVal, param0);
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
         idx = 6;
      }

      try {
         switch (idx) {
            case 6:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._AllowReadonlyCreateAndRemove = false;
               if (initOne) {
                  break;
               }
            case 3:
               this._DisableStringTrimming = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._FindersReturnNulls = true;
               if (initOne) {
                  break;
               }
            case 5:
               this._LoadRelatedBeansFromDbInPostCreate = false;
               if (initOne) {
                  break;
               }
            case 0:
               this._SerializeByteArrayToOracleBlob = false;
               if (initOne) {
                  break;
               }
            case 1:
               this._SerializeCharArrayToBytes = false;
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

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 6;
               }
               break;
            case 20:
               if (s.equals("finders-return-nulls")) {
                  return 4;
               }
               break;
            case 23:
               if (s.equals("disable-string-trimming")) {
                  return 3;
               }
               break;
            case 29:
               if (s.equals("serialize-char-array-to-bytes")) {
                  return 1;
               }
               break;
            case 32:
               if (s.equals("allow-readonly-create-and-remove")) {
                  return 2;
               }
               break;
            case 35:
               if (s.equals("serialize-byte-array-to-oracle-blob")) {
                  return 0;
               }
               break;
            case 41:
               if (s.equals("load-related-beans-from-db-in-post-create")) {
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
            case 0:
               return "serialize-byte-array-to-oracle-blob";
            case 1:
               return "serialize-char-array-to-bytes";
            case 2:
               return "allow-readonly-create-and-remove";
            case 3:
               return "disable-string-trimming";
            case 4:
               return "finders-return-nulls";
            case 5:
               return "load-related-beans-from-db-in-post-create";
            case 6:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private CompatibilityBeanImpl bean;

      protected Helper(CompatibilityBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "SerializeByteArrayToOracleBlob";
            case 1:
               return "SerializeCharArrayToBytes";
            case 2:
               return "AllowReadonlyCreateAndRemove";
            case 3:
               return "DisableStringTrimming";
            case 4:
               return "FindersReturnNulls";
            case 5:
               return "LoadRelatedBeansFromDbInPostCreate";
            case 6:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 6;
         } else if (propName.equals("AllowReadonlyCreateAndRemove")) {
            return 2;
         } else if (propName.equals("DisableStringTrimming")) {
            return 3;
         } else if (propName.equals("FindersReturnNulls")) {
            return 4;
         } else if (propName.equals("LoadRelatedBeansFromDbInPostCreate")) {
            return 5;
         } else if (propName.equals("SerializeByteArrayToOracleBlob")) {
            return 0;
         } else {
            return propName.equals("SerializeCharArrayToBytes") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isAllowReadonlyCreateAndRemoveSet()) {
               buf.append("AllowReadonlyCreateAndRemove");
               buf.append(String.valueOf(this.bean.isAllowReadonlyCreateAndRemove()));
            }

            if (this.bean.isDisableStringTrimmingSet()) {
               buf.append("DisableStringTrimming");
               buf.append(String.valueOf(this.bean.isDisableStringTrimming()));
            }

            if (this.bean.isFindersReturnNullsSet()) {
               buf.append("FindersReturnNulls");
               buf.append(String.valueOf(this.bean.isFindersReturnNulls()));
            }

            if (this.bean.isLoadRelatedBeansFromDbInPostCreateSet()) {
               buf.append("LoadRelatedBeansFromDbInPostCreate");
               buf.append(String.valueOf(this.bean.isLoadRelatedBeansFromDbInPostCreate()));
            }

            if (this.bean.isSerializeByteArrayToOracleBlobSet()) {
               buf.append("SerializeByteArrayToOracleBlob");
               buf.append(String.valueOf(this.bean.isSerializeByteArrayToOracleBlob()));
            }

            if (this.bean.isSerializeCharArrayToBytesSet()) {
               buf.append("SerializeCharArrayToBytes");
               buf.append(String.valueOf(this.bean.isSerializeCharArrayToBytes()));
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
            CompatibilityBeanImpl otherTyped = (CompatibilityBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("AllowReadonlyCreateAndRemove", this.bean.isAllowReadonlyCreateAndRemove(), otherTyped.isAllowReadonlyCreateAndRemove(), false);
            this.computeDiff("DisableStringTrimming", this.bean.isDisableStringTrimming(), otherTyped.isDisableStringTrimming(), false);
            this.computeDiff("FindersReturnNulls", this.bean.isFindersReturnNulls(), otherTyped.isFindersReturnNulls(), false);
            this.computeDiff("LoadRelatedBeansFromDbInPostCreate", this.bean.isLoadRelatedBeansFromDbInPostCreate(), otherTyped.isLoadRelatedBeansFromDbInPostCreate(), false);
            this.computeDiff("SerializeByteArrayToOracleBlob", this.bean.isSerializeByteArrayToOracleBlob(), otherTyped.isSerializeByteArrayToOracleBlob(), false);
            this.computeDiff("SerializeCharArrayToBytes", this.bean.isSerializeCharArrayToBytes(), otherTyped.isSerializeCharArrayToBytes(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CompatibilityBeanImpl original = (CompatibilityBeanImpl)event.getSourceBean();
            CompatibilityBeanImpl proposed = (CompatibilityBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("AllowReadonlyCreateAndRemove")) {
                  original.setAllowReadonlyCreateAndRemove(proposed.isAllowReadonlyCreateAndRemove());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("DisableStringTrimming")) {
                  original.setDisableStringTrimming(proposed.isDisableStringTrimming());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("FindersReturnNulls")) {
                  original.setFindersReturnNulls(proposed.isFindersReturnNulls());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("LoadRelatedBeansFromDbInPostCreate")) {
                  original.setLoadRelatedBeansFromDbInPostCreate(proposed.isLoadRelatedBeansFromDbInPostCreate());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("SerializeByteArrayToOracleBlob")) {
                  original.setSerializeByteArrayToOracleBlob(proposed.isSerializeByteArrayToOracleBlob());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("SerializeCharArrayToBytes")) {
                  original.setSerializeCharArrayToBytes(proposed.isSerializeCharArrayToBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            CompatibilityBeanImpl copy = (CompatibilityBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowReadonlyCreateAndRemove")) && this.bean.isAllowReadonlyCreateAndRemoveSet()) {
               copy.setAllowReadonlyCreateAndRemove(this.bean.isAllowReadonlyCreateAndRemove());
            }

            if ((excludeProps == null || !excludeProps.contains("DisableStringTrimming")) && this.bean.isDisableStringTrimmingSet()) {
               copy.setDisableStringTrimming(this.bean.isDisableStringTrimming());
            }

            if ((excludeProps == null || !excludeProps.contains("FindersReturnNulls")) && this.bean.isFindersReturnNullsSet()) {
               copy.setFindersReturnNulls(this.bean.isFindersReturnNulls());
            }

            if ((excludeProps == null || !excludeProps.contains("LoadRelatedBeansFromDbInPostCreate")) && this.bean.isLoadRelatedBeansFromDbInPostCreateSet()) {
               copy.setLoadRelatedBeansFromDbInPostCreate(this.bean.isLoadRelatedBeansFromDbInPostCreate());
            }

            if ((excludeProps == null || !excludeProps.contains("SerializeByteArrayToOracleBlob")) && this.bean.isSerializeByteArrayToOracleBlobSet()) {
               copy.setSerializeByteArrayToOracleBlob(this.bean.isSerializeByteArrayToOracleBlob());
            }

            if ((excludeProps == null || !excludeProps.contains("SerializeCharArrayToBytes")) && this.bean.isSerializeCharArrayToBytesSet()) {
               copy.setSerializeCharArrayToBytes(this.bean.isSerializeCharArrayToBytes());
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
