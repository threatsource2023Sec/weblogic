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

public class ModuleProviderBeanImpl extends AbstractDescriptorBean implements ModuleProviderBean, Serializable {
   private String _BindingJarUri;
   private String _ModuleFactoryClassName;
   private String _Name;
   private static SchemaHelper2 _schemaHelper;

   public ModuleProviderBeanImpl() {
      this._initializeProperty(-1);
   }

   public ModuleProviderBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ModuleProviderBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getModuleFactoryClassName() {
      return this._ModuleFactoryClassName;
   }

   public boolean isModuleFactoryClassNameInherited() {
      return false;
   }

   public boolean isModuleFactoryClassNameSet() {
      return this._isSet(1);
   }

   public void setModuleFactoryClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ModuleFactoryClassName;
      this._ModuleFactoryClassName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getBindingJarUri() {
      return this._BindingJarUri;
   }

   public boolean isBindingJarUriInherited() {
      return false;
   }

   public boolean isBindingJarUriSet() {
      return this._isSet(2);
   }

   public void setBindingJarUri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BindingJarUri;
      this._BindingJarUri = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._BindingJarUri = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ModuleFactoryClassName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
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
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 15:
               if (s.equals("binding-jar-uri")) {
                  return 2;
               }
               break;
            case 25:
               if (s.equals("module-factory-class-name")) {
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
               return "name";
            case 1:
               return "module-factory-class-name";
            case 2:
               return "binding-jar-uri";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ModuleProviderBeanImpl bean;

      protected Helper(ModuleProviderBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "ModuleFactoryClassName";
            case 2:
               return "BindingJarUri";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BindingJarUri")) {
            return 2;
         } else if (propName.equals("ModuleFactoryClassName")) {
            return 1;
         } else {
            return propName.equals("Name") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isBindingJarUriSet()) {
               buf.append("BindingJarUri");
               buf.append(String.valueOf(this.bean.getBindingJarUri()));
            }

            if (this.bean.isModuleFactoryClassNameSet()) {
               buf.append("ModuleFactoryClassName");
               buf.append(String.valueOf(this.bean.getModuleFactoryClassName()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
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
            ModuleProviderBeanImpl otherTyped = (ModuleProviderBeanImpl)other;
            this.computeDiff("BindingJarUri", this.bean.getBindingJarUri(), otherTyped.getBindingJarUri(), false);
            this.computeDiff("ModuleFactoryClassName", this.bean.getModuleFactoryClassName(), otherTyped.getModuleFactoryClassName(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ModuleProviderBeanImpl original = (ModuleProviderBeanImpl)event.getSourceBean();
            ModuleProviderBeanImpl proposed = (ModuleProviderBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BindingJarUri")) {
                  original.setBindingJarUri(proposed.getBindingJarUri());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ModuleFactoryClassName")) {
                  original.setModuleFactoryClassName(proposed.getModuleFactoryClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
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
            ModuleProviderBeanImpl copy = (ModuleProviderBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BindingJarUri")) && this.bean.isBindingJarUriSet()) {
               copy.setBindingJarUri(this.bean.getBindingJarUri());
            }

            if ((excludeProps == null || !excludeProps.contains("ModuleFactoryClassName")) && this.bean.isModuleFactoryClassNameSet()) {
               copy.setModuleFactoryClassName(this.bean.getModuleFactoryClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
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
