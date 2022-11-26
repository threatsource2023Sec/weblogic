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

public class PersistenceUseBeanImpl extends AbstractDescriptorBean implements PersistenceUseBean, Serializable {
   private String _Id;
   private String _TypeIdentifier;
   private String _TypeStorage;
   private String _TypeVersion;
   private static SchemaHelper2 _schemaHelper;

   public PersistenceUseBeanImpl() {
      this._initializeProperty(-1);
   }

   public PersistenceUseBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PersistenceUseBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getTypeIdentifier() {
      return this._TypeIdentifier;
   }

   public boolean isTypeIdentifierInherited() {
      return false;
   }

   public boolean isTypeIdentifierSet() {
      return this._isSet(0);
   }

   public void setTypeIdentifier(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TypeIdentifier;
      this._TypeIdentifier = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getTypeVersion() {
      return this._TypeVersion;
   }

   public boolean isTypeVersionInherited() {
      return false;
   }

   public boolean isTypeVersionSet() {
      return this._isSet(1);
   }

   public void setTypeVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TypeVersion;
      this._TypeVersion = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getTypeStorage() {
      return this._TypeStorage;
   }

   public boolean isTypeStorageInherited() {
      return false;
   }

   public boolean isTypeStorageSet() {
      return this._isSet(2);
   }

   public void setTypeStorage(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TypeStorage;
      this._TypeStorage = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(3, _oldVal, param0);
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._TypeIdentifier = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._TypeStorage = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._TypeVersion = null;
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
                  return 3;
               }
               break;
            case 12:
               if (s.equals("type-storage")) {
                  return 2;
               }

               if (s.equals("type-version")) {
                  return 1;
               }
               break;
            case 15:
               if (s.equals("type-identifier")) {
                  return 0;
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
               return "type-identifier";
            case 1:
               return "type-version";
            case 2:
               return "type-storage";
            case 3:
               return "id";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PersistenceUseBeanImpl bean;

      protected Helper(PersistenceUseBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TypeIdentifier";
            case 1:
               return "TypeVersion";
            case 2:
               return "TypeStorage";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("TypeIdentifier")) {
            return 0;
         } else if (propName.equals("TypeStorage")) {
            return 2;
         } else {
            return propName.equals("TypeVersion") ? 1 : super.getPropertyIndex(propName);
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

            if (this.bean.isTypeIdentifierSet()) {
               buf.append("TypeIdentifier");
               buf.append(String.valueOf(this.bean.getTypeIdentifier()));
            }

            if (this.bean.isTypeStorageSet()) {
               buf.append("TypeStorage");
               buf.append(String.valueOf(this.bean.getTypeStorage()));
            }

            if (this.bean.isTypeVersionSet()) {
               buf.append("TypeVersion");
               buf.append(String.valueOf(this.bean.getTypeVersion()));
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
            PersistenceUseBeanImpl otherTyped = (PersistenceUseBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("TypeIdentifier", this.bean.getTypeIdentifier(), otherTyped.getTypeIdentifier(), false);
            this.computeDiff("TypeStorage", this.bean.getTypeStorage(), otherTyped.getTypeStorage(), false);
            this.computeDiff("TypeVersion", this.bean.getTypeVersion(), otherTyped.getTypeVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PersistenceUseBeanImpl original = (PersistenceUseBeanImpl)event.getSourceBean();
            PersistenceUseBeanImpl proposed = (PersistenceUseBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("TypeIdentifier")) {
                  original.setTypeIdentifier(proposed.getTypeIdentifier());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("TypeStorage")) {
                  original.setTypeStorage(proposed.getTypeStorage());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TypeVersion")) {
                  original.setTypeVersion(proposed.getTypeVersion());
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
            PersistenceUseBeanImpl copy = (PersistenceUseBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("TypeIdentifier")) && this.bean.isTypeIdentifierSet()) {
               copy.setTypeIdentifier(this.bean.getTypeIdentifier());
            }

            if ((excludeProps == null || !excludeProps.contains("TypeStorage")) && this.bean.isTypeStorageSet()) {
               copy.setTypeStorage(this.bean.getTypeStorage());
            }

            if ((excludeProps == null || !excludeProps.contains("TypeVersion")) && this.bean.isTypeVersionSet()) {
               copy.setTypeVersion(this.bean.getTypeVersion());
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
