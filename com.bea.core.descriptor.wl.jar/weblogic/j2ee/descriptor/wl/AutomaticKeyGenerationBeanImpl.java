package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class AutomaticKeyGenerationBeanImpl extends AbstractDescriptorBean implements AutomaticKeyGenerationBean, Serializable {
   private String _GeneratorName;
   private String _GeneratorType;
   private String _Id;
   private int _KeyCacheSize;
   private boolean _SelectFirstSequenceKeyBeforeUpdate;
   private static SchemaHelper2 _schemaHelper;

   public AutomaticKeyGenerationBeanImpl() {
      this._initializeProperty(-1);
   }

   public AutomaticKeyGenerationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AutomaticKeyGenerationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getGeneratorType() {
      return this._GeneratorType;
   }

   public boolean isGeneratorTypeInherited() {
      return false;
   }

   public boolean isGeneratorTypeSet() {
      return this._isSet(0);
   }

   public void setGeneratorType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Identity", "Sequence", "SequenceTable"};
      param0 = LegalChecks.checkInEnum("GeneratorType", param0, _set);
      String _oldVal = this._GeneratorType;
      this._GeneratorType = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getGeneratorName() {
      return this._GeneratorName;
   }

   public boolean isGeneratorNameInherited() {
      return false;
   }

   public boolean isGeneratorNameSet() {
      return this._isSet(1);
   }

   public void setGeneratorName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._GeneratorName;
      this._GeneratorName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getKeyCacheSize() {
      return this._KeyCacheSize;
   }

   public boolean isKeyCacheSizeInherited() {
      return false;
   }

   public boolean isKeyCacheSizeSet() {
      return this._isSet(2);
   }

   public void setKeyCacheSize(int param0) {
      LegalChecks.checkMin("KeyCacheSize", param0, 1);
      int _oldVal = this._KeyCacheSize;
      this._KeyCacheSize = param0;
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

   public void setSelectFirstSequenceKeyBeforeUpdate(boolean param0) {
      boolean _oldVal = this._SelectFirstSequenceKeyBeforeUpdate;
      this._SelectFirstSequenceKeyBeforeUpdate = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean getSelectFirstSequenceKeyBeforeUpdate() {
      return this._SelectFirstSequenceKeyBeforeUpdate;
   }

   public boolean isSelectFirstSequenceKeyBeforeUpdateInherited() {
      return false;
   }

   public boolean isSelectFirstSequenceKeyBeforeUpdateSet() {
      return this._isSet(4);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("GeneratorType", this.isGeneratorTypeSet());
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._GeneratorName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._GeneratorType = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._KeyCacheSize = 0;
               if (initOne) {
                  break;
               }
            case 4:
               this._SelectFirstSequenceKeyBeforeUpdate = false;
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
            case 14:
               if (s.equals("generator-name")) {
                  return 1;
               }

               if (s.equals("generator-type")) {
                  return 0;
               }

               if (s.equals("key-cache-size")) {
                  return 2;
               }
               break;
            case 39:
               if (s.equals("select-first-sequence-key-before-update")) {
                  return 4;
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
               return "generator-type";
            case 1:
               return "generator-name";
            case 2:
               return "key-cache-size";
            case 3:
               return "id";
            case 4:
               return "select-first-sequence-key-before-update";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AutomaticKeyGenerationBeanImpl bean;

      protected Helper(AutomaticKeyGenerationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "GeneratorType";
            case 1:
               return "GeneratorName";
            case 2:
               return "KeyCacheSize";
            case 3:
               return "Id";
            case 4:
               return "SelectFirstSequenceKeyBeforeUpdate";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("GeneratorName")) {
            return 1;
         } else if (propName.equals("GeneratorType")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("KeyCacheSize")) {
            return 2;
         } else {
            return propName.equals("SelectFirstSequenceKeyBeforeUpdate") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isGeneratorNameSet()) {
               buf.append("GeneratorName");
               buf.append(String.valueOf(this.bean.getGeneratorName()));
            }

            if (this.bean.isGeneratorTypeSet()) {
               buf.append("GeneratorType");
               buf.append(String.valueOf(this.bean.getGeneratorType()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isKeyCacheSizeSet()) {
               buf.append("KeyCacheSize");
               buf.append(String.valueOf(this.bean.getKeyCacheSize()));
            }

            if (this.bean.isSelectFirstSequenceKeyBeforeUpdateSet()) {
               buf.append("SelectFirstSequenceKeyBeforeUpdate");
               buf.append(String.valueOf(this.bean.getSelectFirstSequenceKeyBeforeUpdate()));
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
            AutomaticKeyGenerationBeanImpl otherTyped = (AutomaticKeyGenerationBeanImpl)other;
            this.computeDiff("GeneratorName", this.bean.getGeneratorName(), otherTyped.getGeneratorName(), false);
            this.computeDiff("GeneratorType", this.bean.getGeneratorType(), otherTyped.getGeneratorType(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("KeyCacheSize", this.bean.getKeyCacheSize(), otherTyped.getKeyCacheSize(), true);
            this.computeDiff("SelectFirstSequenceKeyBeforeUpdate", this.bean.getSelectFirstSequenceKeyBeforeUpdate(), otherTyped.getSelectFirstSequenceKeyBeforeUpdate(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AutomaticKeyGenerationBeanImpl original = (AutomaticKeyGenerationBeanImpl)event.getSourceBean();
            AutomaticKeyGenerationBeanImpl proposed = (AutomaticKeyGenerationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("GeneratorName")) {
                  original.setGeneratorName(proposed.getGeneratorName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("GeneratorType")) {
                  original.setGeneratorType(proposed.getGeneratorType());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("KeyCacheSize")) {
                  original.setKeyCacheSize(proposed.getKeyCacheSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SelectFirstSequenceKeyBeforeUpdate")) {
                  original.setSelectFirstSequenceKeyBeforeUpdate(proposed.getSelectFirstSequenceKeyBeforeUpdate());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            AutomaticKeyGenerationBeanImpl copy = (AutomaticKeyGenerationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("GeneratorName")) && this.bean.isGeneratorNameSet()) {
               copy.setGeneratorName(this.bean.getGeneratorName());
            }

            if ((excludeProps == null || !excludeProps.contains("GeneratorType")) && this.bean.isGeneratorTypeSet()) {
               copy.setGeneratorType(this.bean.getGeneratorType());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("KeyCacheSize")) && this.bean.isKeyCacheSizeSet()) {
               copy.setKeyCacheSize(this.bean.getKeyCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("SelectFirstSequenceKeyBeforeUpdate")) && this.bean.isSelectFirstSequenceKeyBeforeUpdateSet()) {
               copy.setSelectFirstSequenceKeyBeforeUpdate(this.bean.getSelectFirstSequenceKeyBeforeUpdate());
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
