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

public class SecurityPluginBeanImpl extends AbstractDescriptorBean implements SecurityPluginBean, Serializable {
   private String _Key;
   private String _PluginClass;
   private static SchemaHelper2 _schemaHelper;

   public SecurityPluginBeanImpl() {
      this._initializeProperty(-1);
   }

   public SecurityPluginBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SecurityPluginBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getPluginClass() {
      return this._PluginClass;
   }

   public boolean isPluginClassInherited() {
      return false;
   }

   public boolean isPluginClassSet() {
      return this._isSet(0);
   }

   public void setPluginClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PluginClass;
      this._PluginClass = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getKey() {
      return this._Key;
   }

   public boolean isKeyInherited() {
      return false;
   }

   public boolean isKeySet() {
      return this._isSet(1);
   }

   public void setKey(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Key;
      this._Key = param0;
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
               this._Key = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._PluginClass = null;
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
            case 3:
               if (s.equals("key")) {
                  return 1;
               }
               break;
            case 12:
               if (s.equals("plugin-class")) {
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
               return "plugin-class";
            case 1:
               return "key";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SecurityPluginBeanImpl bean;

      protected Helper(SecurityPluginBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "PluginClass";
            case 1:
               return "Key";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Key")) {
            return 1;
         } else {
            return propName.equals("PluginClass") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isKeySet()) {
               buf.append("Key");
               buf.append(String.valueOf(this.bean.getKey()));
            }

            if (this.bean.isPluginClassSet()) {
               buf.append("PluginClass");
               buf.append(String.valueOf(this.bean.getPluginClass()));
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
            SecurityPluginBeanImpl otherTyped = (SecurityPluginBeanImpl)other;
            this.computeDiff("Key", this.bean.getKey(), otherTyped.getKey(), false);
            this.computeDiff("PluginClass", this.bean.getPluginClass(), otherTyped.getPluginClass(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SecurityPluginBeanImpl original = (SecurityPluginBeanImpl)event.getSourceBean();
            SecurityPluginBeanImpl proposed = (SecurityPluginBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Key")) {
                  original.setKey(proposed.getKey());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("PluginClass")) {
                  original.setPluginClass(proposed.getPluginClass());
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
            SecurityPluginBeanImpl copy = (SecurityPluginBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Key")) && this.bean.isKeySet()) {
               copy.setKey(this.bean.getKey());
            }

            if ((excludeProps == null || !excludeProps.contains("PluginClass")) && this.bean.isPluginClassSet()) {
               copy.setPluginClass(this.bean.getPluginClass());
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
