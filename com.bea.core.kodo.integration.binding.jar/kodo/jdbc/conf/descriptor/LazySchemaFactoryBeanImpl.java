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

public class LazySchemaFactoryBeanImpl extends SchemaFactoryBeanImpl implements LazySchemaFactoryBean, Serializable {
   private boolean _ForeignKeys;
   private boolean _Indexes;
   private boolean _PrimaryKeys;
   private static SchemaHelper2 _schemaHelper;

   public LazySchemaFactoryBeanImpl() {
      this._initializeProperty(-1);
   }

   public LazySchemaFactoryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LazySchemaFactoryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getForeignKeys() {
      return this._ForeignKeys;
   }

   public boolean isForeignKeysInherited() {
      return false;
   }

   public boolean isForeignKeysSet() {
      return this._isSet(0);
   }

   public void setForeignKeys(boolean param0) {
      boolean _oldVal = this._ForeignKeys;
      this._ForeignKeys = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean getIndexes() {
      return this._Indexes;
   }

   public boolean isIndexesInherited() {
      return false;
   }

   public boolean isIndexesSet() {
      return this._isSet(1);
   }

   public void setIndexes(boolean param0) {
      boolean _oldVal = this._Indexes;
      this._Indexes = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean getPrimaryKeys() {
      return this._PrimaryKeys;
   }

   public boolean isPrimaryKeysInherited() {
      return false;
   }

   public boolean isPrimaryKeysSet() {
      return this._isSet(2);
   }

   public void setPrimaryKeys(boolean param0) {
      boolean _oldVal = this._PrimaryKeys;
      this._PrimaryKeys = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._ForeignKeys = false;
               if (initOne) {
                  break;
               }
            case 1:
               this._Indexes = false;
               if (initOne) {
                  break;
               }
            case 2:
               this._PrimaryKeys = false;
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

   public static class SchemaHelper2 extends SchemaFactoryBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("indexes")) {
                  return 1;
               }
               break;
            case 12:
               if (s.equals("foreign-keys")) {
                  return 0;
               }

               if (s.equals("primary-keys")) {
                  return 2;
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
               return "foreign-keys";
            case 1:
               return "indexes";
            case 2:
               return "primary-keys";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends SchemaFactoryBeanImpl.Helper {
      private LazySchemaFactoryBeanImpl bean;

      protected Helper(LazySchemaFactoryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ForeignKeys";
            case 1:
               return "Indexes";
            case 2:
               return "PrimaryKeys";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ForeignKeys")) {
            return 0;
         } else if (propName.equals("Indexes")) {
            return 1;
         } else {
            return propName.equals("PrimaryKeys") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isForeignKeysSet()) {
               buf.append("ForeignKeys");
               buf.append(String.valueOf(this.bean.getForeignKeys()));
            }

            if (this.bean.isIndexesSet()) {
               buf.append("Indexes");
               buf.append(String.valueOf(this.bean.getIndexes()));
            }

            if (this.bean.isPrimaryKeysSet()) {
               buf.append("PrimaryKeys");
               buf.append(String.valueOf(this.bean.getPrimaryKeys()));
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
            LazySchemaFactoryBeanImpl otherTyped = (LazySchemaFactoryBeanImpl)other;
            this.computeDiff("ForeignKeys", this.bean.getForeignKeys(), otherTyped.getForeignKeys(), false);
            this.computeDiff("Indexes", this.bean.getIndexes(), otherTyped.getIndexes(), false);
            this.computeDiff("PrimaryKeys", this.bean.getPrimaryKeys(), otherTyped.getPrimaryKeys(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LazySchemaFactoryBeanImpl original = (LazySchemaFactoryBeanImpl)event.getSourceBean();
            LazySchemaFactoryBeanImpl proposed = (LazySchemaFactoryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ForeignKeys")) {
                  original.setForeignKeys(proposed.getForeignKeys());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Indexes")) {
                  original.setIndexes(proposed.getIndexes());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("PrimaryKeys")) {
                  original.setPrimaryKeys(proposed.getPrimaryKeys());
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
            LazySchemaFactoryBeanImpl copy = (LazySchemaFactoryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ForeignKeys")) && this.bean.isForeignKeysSet()) {
               copy.setForeignKeys(this.bean.getForeignKeys());
            }

            if ((excludeProps == null || !excludeProps.contains("Indexes")) && this.bean.isIndexesSet()) {
               copy.setIndexes(this.bean.getIndexes());
            }

            if ((excludeProps == null || !excludeProps.contains("PrimaryKeys")) && this.bean.isPrimaryKeysSet()) {
               copy.setPrimaryKeys(this.bean.getPrimaryKeys());
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
