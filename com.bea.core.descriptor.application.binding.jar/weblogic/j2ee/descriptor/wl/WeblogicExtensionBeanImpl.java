package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicExtensionBeanImpl extends AbstractDescriptorBean implements WeblogicExtensionBean, Serializable {
   private CustomModuleBean[] _CustomModules;
   private ModuleProviderBean[] _ModuleProviders;
   private String _Version;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicExtensionBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicExtensionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicExtensionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addModuleProvider(ModuleProviderBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         ModuleProviderBean[] _new;
         if (this._isSet(0)) {
            _new = (ModuleProviderBean[])((ModuleProviderBean[])this._getHelper()._extendArray(this.getModuleProviders(), ModuleProviderBean.class, param0));
         } else {
            _new = new ModuleProviderBean[]{param0};
         }

         try {
            this.setModuleProviders(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ModuleProviderBean[] getModuleProviders() {
      return this._ModuleProviders;
   }

   public boolean isModuleProvidersInherited() {
      return false;
   }

   public boolean isModuleProvidersSet() {
      return this._isSet(0);
   }

   public void removeModuleProvider(ModuleProviderBean param0) {
      this.destroyModuleProvider(param0);
   }

   public void setModuleProviders(ModuleProviderBean[] param0) throws InvalidAttributeValueException {
      ModuleProviderBean[] param0 = param0 == null ? new ModuleProviderBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ModuleProviderBean[] _oldVal = this._ModuleProviders;
      this._ModuleProviders = (ModuleProviderBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public ModuleProviderBean createModuleProvider() {
      ModuleProviderBeanImpl _val = new ModuleProviderBeanImpl(this, -1);

      try {
         this.addModuleProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyModuleProvider(ModuleProviderBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         ModuleProviderBean[] _old = this.getModuleProviders();
         ModuleProviderBean[] _new = (ModuleProviderBean[])((ModuleProviderBean[])this._getHelper()._removeElement(_old, ModuleProviderBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setModuleProviders(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addCustomModule(CustomModuleBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         CustomModuleBean[] _new;
         if (this._isSet(1)) {
            _new = (CustomModuleBean[])((CustomModuleBean[])this._getHelper()._extendArray(this.getCustomModules(), CustomModuleBean.class, param0));
         } else {
            _new = new CustomModuleBean[]{param0};
         }

         try {
            this.setCustomModules(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CustomModuleBean[] getCustomModules() {
      return this._CustomModules;
   }

   public boolean isCustomModulesInherited() {
      return false;
   }

   public boolean isCustomModulesSet() {
      return this._isSet(1);
   }

   public void removeCustomModule(CustomModuleBean param0) {
      this.destroyCustomModule(param0);
   }

   public void setCustomModules(CustomModuleBean[] param0) throws InvalidAttributeValueException {
      CustomModuleBean[] param0 = param0 == null ? new CustomModuleBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CustomModuleBean[] _oldVal = this._CustomModules;
      this._CustomModules = (CustomModuleBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public CustomModuleBean createCustomModule() {
      CustomModuleBeanImpl _val = new CustomModuleBeanImpl(this, -1);

      try {
         this.addCustomModule(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomModule(CustomModuleBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         CustomModuleBean[] _old = this.getCustomModules();
         CustomModuleBean[] _new = (CustomModuleBean[])((CustomModuleBean[])this._getHelper()._removeElement(_old, CustomModuleBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomModules(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(2);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._CustomModules = new CustomModuleBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ModuleProviders = new ModuleProviderBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Version = null;
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/weblogic-extension/1.0/weblogic-extension.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-extension";
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
            case 7:
               if (s.equals("version")) {
                  return 2;
               }
               break;
            case 13:
               if (s.equals("custom-module")) {
                  return 1;
               }
               break;
            case 15:
               if (s.equals("module-provider")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new ModuleProviderBeanImpl.SchemaHelper2();
            case 1:
               return new CustomModuleBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "weblogic-extension";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "module-provider";
            case 1:
               return "custom-module";
            case 2:
               return "version";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WeblogicExtensionBeanImpl bean;

      protected Helper(WeblogicExtensionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ModuleProviders";
            case 1:
               return "CustomModules";
            case 2:
               return "Version";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CustomModules")) {
            return 1;
         } else if (propName.equals("ModuleProviders")) {
            return 0;
         } else {
            return propName.equals("Version") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCustomModules()));
         iterators.add(new ArrayIterator(this.bean.getModuleProviders()));
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
            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getCustomModules().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCustomModules()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getModuleProviders().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getModuleProviders()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            WeblogicExtensionBeanImpl otherTyped = (WeblogicExtensionBeanImpl)other;
            this.computeChildDiff("CustomModules", this.bean.getCustomModules(), otherTyped.getCustomModules(), false);
            this.computeChildDiff("ModuleProviders", this.bean.getModuleProviders(), otherTyped.getModuleProviders(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicExtensionBeanImpl original = (WeblogicExtensionBeanImpl)event.getSourceBean();
            WeblogicExtensionBeanImpl proposed = (WeblogicExtensionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CustomModules")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCustomModule((CustomModuleBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCustomModule((CustomModuleBean)update.getRemovedObject());
                  }

                  if (original.getCustomModules() == null || original.getCustomModules().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("ModuleProviders")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addModuleProvider((ModuleProviderBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeModuleProvider((ModuleProviderBean)update.getRemovedObject());
                  }

                  if (original.getModuleProviders() == null || original.getModuleProviders().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
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
            WeblogicExtensionBeanImpl copy = (WeblogicExtensionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("CustomModules")) && this.bean.isCustomModulesSet() && !copy._isSet(1)) {
               CustomModuleBean[] oldCustomModules = this.bean.getCustomModules();
               CustomModuleBean[] newCustomModules = new CustomModuleBean[oldCustomModules.length];

               for(i = 0; i < newCustomModules.length; ++i) {
                  newCustomModules[i] = (CustomModuleBean)((CustomModuleBean)this.createCopy((AbstractDescriptorBean)oldCustomModules[i], includeObsolete));
               }

               copy.setCustomModules(newCustomModules);
            }

            if ((excludeProps == null || !excludeProps.contains("ModuleProviders")) && this.bean.isModuleProvidersSet() && !copy._isSet(0)) {
               ModuleProviderBean[] oldModuleProviders = this.bean.getModuleProviders();
               ModuleProviderBean[] newModuleProviders = new ModuleProviderBean[oldModuleProviders.length];

               for(i = 0; i < newModuleProviders.length; ++i) {
                  newModuleProviders[i] = (ModuleProviderBean)((ModuleProviderBean)this.createCopy((AbstractDescriptorBean)oldModuleProviders[i], includeObsolete));
               }

               copy.setModuleProviders(newModuleProviders);
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getCustomModules(), clazz, annotation);
         this.inferSubTree(this.bean.getModuleProviders(), clazz, annotation);
      }
   }
}
