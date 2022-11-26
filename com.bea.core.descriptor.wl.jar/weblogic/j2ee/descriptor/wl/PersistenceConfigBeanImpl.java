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
import weblogic.j2ee.descriptor.wl.validators.WseeConfigBeanValidator;
import weblogic.utils.collections.CombinedIterator;

public class PersistenceConfigBeanImpl extends AbstractDescriptorBean implements PersistenceConfigBean, Serializable {
   private boolean _Customized;
   private String _DefaultLogicalStoreName;
   private static SchemaHelper2 _schemaHelper;

   public PersistenceConfigBeanImpl() {
      this._initializeProperty(-1);
   }

   public PersistenceConfigBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PersistenceConfigBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isCustomized() {
      return this._Customized;
   }

   public boolean isCustomizedInherited() {
      return false;
   }

   public boolean isCustomizedSet() {
      return this._isSet(0);
   }

   public void setCustomized(boolean param0) {
      boolean _oldVal = this._Customized;
      this._Customized = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDefaultLogicalStoreName() {
      return this._DefaultLogicalStoreName;
   }

   public boolean isDefaultLogicalStoreNameInherited() {
      return false;
   }

   public boolean isDefaultLogicalStoreNameSet() {
      return this._isSet(1);
   }

   public void setDefaultLogicalStoreName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WseeConfigBeanValidator.validateDefaultLogicalStoreName(param0);
      String _oldVal = this._DefaultLogicalStoreName;
      this._DefaultLogicalStoreName = param0;
      this._postSet(1, _oldVal, param0);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._DefaultLogicalStoreName = "WseeStore";
               if (initOne) {
                  break;
               }
            case 0:
               this._Customized = true;
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
            case 10:
               if (s.equals("customized")) {
                  return 0;
               }
               break;
            case 26:
               if (s.equals("default-logical-store-name")) {
                  return 1;
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
               return "customized";
            case 1:
               return "default-logical-store-name";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PersistenceConfigBeanImpl bean;

      protected Helper(PersistenceConfigBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Customized";
            case 1:
               return "DefaultLogicalStoreName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DefaultLogicalStoreName")) {
            return 1;
         } else {
            return propName.equals("Customized") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isDefaultLogicalStoreNameSet()) {
               buf.append("DefaultLogicalStoreName");
               buf.append(String.valueOf(this.bean.getDefaultLogicalStoreName()));
            }

            if (this.bean.isCustomizedSet()) {
               buf.append("Customized");
               buf.append(String.valueOf(this.bean.isCustomized()));
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
            PersistenceConfigBeanImpl otherTyped = (PersistenceConfigBeanImpl)other;
            this.computeDiff("DefaultLogicalStoreName", this.bean.getDefaultLogicalStoreName(), otherTyped.getDefaultLogicalStoreName(), true);
            this.computeDiff("Customized", this.bean.isCustomized(), otherTyped.isCustomized(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PersistenceConfigBeanImpl original = (PersistenceConfigBeanImpl)event.getSourceBean();
            PersistenceConfigBeanImpl proposed = (PersistenceConfigBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DefaultLogicalStoreName")) {
                  original.setDefaultLogicalStoreName(proposed.getDefaultLogicalStoreName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Customized")) {
                  original.setCustomized(proposed.isCustomized());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            PersistenceConfigBeanImpl copy = (PersistenceConfigBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DefaultLogicalStoreName")) && this.bean.isDefaultLogicalStoreNameSet()) {
               copy.setDefaultLogicalStoreName(this.bean.getDefaultLogicalStoreName());
            }

            if ((excludeProps == null || !excludeProps.contains("Customized")) && this.bean.isCustomizedSet()) {
               copy.setCustomized(this.bean.isCustomized());
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
