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
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.descriptor.beangen.CustomizerFactoryBuilder;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.customizers.DeploymentPlanBeanCustomizer;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class DeploymentPlanBeanImpl extends AbstractDescriptorBean implements DeploymentPlanBean, Serializable {
   private String _ApplicationName;
   private String _ConfigRoot;
   private String _Description;
   private boolean _GlobalVariables;
   private ModuleOverrideBean[] _ModuleOverrides;
   private VariableDefinitionBean _VariableDefinition;
   private String _Version;
   private transient DeploymentPlanBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public DeploymentPlanBeanImpl() {
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.DeploymentPlanBeanCustomizerFactory");
         this._customizer = (DeploymentPlanBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public DeploymentPlanBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.DeploymentPlanBeanCustomizerFactory");
         this._customizer = (DeploymentPlanBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public DeploymentPlanBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.DeploymentPlanBeanCustomizerFactory");
         this._customizer = (DeploymentPlanBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(0);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getApplicationName() {
      return this._ApplicationName;
   }

   public boolean isApplicationNameInherited() {
      return false;
   }

   public boolean isApplicationNameSet() {
      return this._isSet(1);
   }

   public void setApplicationName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ApplicationName;
      this._ApplicationName = param0;
      this._postSet(1, _oldVal, param0);
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

   public VariableDefinitionBean getVariableDefinition() {
      return this._VariableDefinition;
   }

   public boolean isVariableDefinitionInherited() {
      return false;
   }

   public boolean isVariableDefinitionSet() {
      return this._isSet(3) || this._isAnythingSet((AbstractDescriptorBean)this.getVariableDefinition());
   }

   public void setVariableDefinition(VariableDefinitionBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 3)) {
         this._postCreate(_child);
      }

      VariableDefinitionBean _oldVal = this._VariableDefinition;
      this._VariableDefinition = param0;
      this._postSet(3, _oldVal, param0);
   }

   public void addModuleOverride(ModuleOverrideBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         ModuleOverrideBean[] _new;
         if (this._isSet(4)) {
            _new = (ModuleOverrideBean[])((ModuleOverrideBean[])this._getHelper()._extendArray(this.getModuleOverrides(), ModuleOverrideBean.class, param0));
         } else {
            _new = new ModuleOverrideBean[]{param0};
         }

         try {
            this.setModuleOverrides(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ModuleOverrideBean[] getModuleOverrides() {
      return this._ModuleOverrides;
   }

   public boolean isModuleOverridesInherited() {
      return false;
   }

   public boolean isModuleOverridesSet() {
      return this._isSet(4);
   }

   public void removeModuleOverride(ModuleOverrideBean param0) {
      this.destroyModuleOverride(param0);
   }

   public void setModuleOverrides(ModuleOverrideBean[] param0) throws InvalidAttributeValueException {
      ModuleOverrideBean[] param0 = param0 == null ? new ModuleOverrideBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ModuleOverrideBean[] _oldVal = this._ModuleOverrides;
      this._ModuleOverrides = (ModuleOverrideBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public ModuleOverrideBean createModuleOverride() {
      ModuleOverrideBeanImpl _val = new ModuleOverrideBeanImpl(this, -1);

      try {
         this.addModuleOverride(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyModuleOverride(ModuleOverrideBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         ModuleOverrideBean[] _old = this.getModuleOverrides();
         ModuleOverrideBean[] _new = (ModuleOverrideBean[])((ModuleOverrideBean[])this._getHelper()._removeElement(_old, ModuleOverrideBean.class, param0));
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
               this.setModuleOverrides(_new);
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

   public ModuleOverrideBean findModuleOverride(String param0) {
      return this._customizer.findModuleOverride(param0);
   }

   public ModuleDescriptorBean findModuleDescriptor(String param0, String param1) {
      return this._customizer.findModuleDescriptor(param0, param1);
   }

   public String getConfigRoot() {
      return this._ConfigRoot;
   }

   public boolean isConfigRootInherited() {
      return false;
   }

   public boolean isConfigRootSet() {
      return this._isSet(5);
   }

   public void setConfigRoot(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConfigRoot;
      this._ConfigRoot = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean rootModule(String param0) {
      return this._customizer.rootModule(param0);
   }

   public boolean hasVariable(ModuleDescriptorBean param0, DescriptorBean param1, String param2) throws IllegalArgumentException {
      return this._customizer.hasVariable(param0, param1, param2);
   }

   public VariableBean findVariable(ModuleDescriptorBean param0, DescriptorBean param1, String param2) throws IllegalArgumentException {
      return this._customizer.findVariable(param0, param1, param2);
   }

   public VariableBean findOrCreateVariable(ModuleDescriptorBean param0, DescriptorBean param1, String param2, boolean param3) throws IllegalArgumentException {
      return this._customizer.findOrCreateVariable(param0, param1, param2, param3);
   }

   public VariableBean findOrCreateVariable(ModuleDescriptorBean param0, DescriptorBean param1, String param2) throws IllegalArgumentException {
      return this._customizer.findOrCreateVariable(param0, param1, param2);
   }

   public VariableBean findOrCreateVariable(ModuleDescriptorBean param0, DescriptorBean param1, String param2, boolean param3, Object param4) throws IllegalArgumentException {
      return this._customizer.findOrCreateVariable(param0, param1, param2, param3, param4);
   }

   public VariableAssignmentBean[] findVariableAssignments(VariableBean param0) {
      return this._customizer.findVariableAssignments(param0);
   }

   public VariableAssignmentBean findVariableAssignment(ModuleDescriptorBean param0, DescriptorBean param1, String param2) throws IllegalArgumentException {
      return this._customizer.findVariableAssignment(param0, param1, param2);
   }

   public Object valueOf(VariableBean param0) {
      return this._customizer.valueOf(param0);
   }

   public VariableAssignmentBean assignVariable(VariableBean param0, ModuleDescriptorBean param1, DescriptorBean param2, String param3) {
      return this._customizer.assignVariable(param0, param1, param2, param3);
   }

   public boolean isGlobalVariables() {
      return this._GlobalVariables;
   }

   public boolean isGlobalVariablesInherited() {
      return false;
   }

   public boolean isGlobalVariablesSet() {
      return this._isSet(6);
   }

   public void setGlobalVariables(boolean param0) {
      boolean _oldVal = this._GlobalVariables;
      this._GlobalVariables = param0;
      this._postSet(6, _oldVal, param0);
   }

   public ModuleOverrideBean findRootModule() {
      return this._customizer.findRootModule();
   }

   public void findAndRemoveAllBeanVariables(ModuleDescriptorBean param0, DescriptorBean param1) throws IllegalArgumentException {
      this._customizer.findAndRemoveAllBeanVariables(param0, param1);
   }

   public boolean isRemovable(DescriptorBean param0) throws IllegalArgumentException {
      return this._customizer.isRemovable(param0);
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
      return super._isAnythingSet() || this.isVariableDefinitionSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._ApplicationName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._ConfigRoot = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._ModuleOverrides = new ModuleOverrideBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._VariableDefinition = new VariableDefinitionBeanImpl(this, 3);
               this._postCreate((AbstractDescriptorBean)this._VariableDefinition);
               if (initOne) {
                  break;
               }
            case 2:
               this._Version = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._GlobalVariables = false;
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
      return "http://xmlns.oracle.com/weblogic/deployment-plan/1.0/deployment-plan.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/deployment-plan";
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
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 17:
            case 18:
            default:
               break;
            case 11:
               if (s.equals("config-root")) {
                  return 5;
               }

               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 15:
               if (s.equals("module-override")) {
                  return 4;
               }
               break;
            case 16:
               if (s.equals("application-name")) {
                  return 1;
               }

               if (s.equals("global-variables")) {
                  return 6;
               }
               break;
            case 19:
               if (s.equals("variable-definition")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new VariableDefinitionBeanImpl.SchemaHelper2();
            case 4:
               return new ModuleOverrideBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "deployment-plan";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "application-name";
            case 2:
               return "version";
            case 3:
               return "variable-definition";
            case 4:
               return "module-override";
            case 5:
               return "config-root";
            case 6:
               return "global-variables";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private DeploymentPlanBeanImpl bean;

      protected Helper(DeploymentPlanBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "ApplicationName";
            case 2:
               return "Version";
            case 3:
               return "VariableDefinition";
            case 4:
               return "ModuleOverrides";
            case 5:
               return "ConfigRoot";
            case 6:
               return "GlobalVariables";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ApplicationName")) {
            return 1;
         } else if (propName.equals("ConfigRoot")) {
            return 5;
         } else if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("ModuleOverrides")) {
            return 4;
         } else if (propName.equals("VariableDefinition")) {
            return 3;
         } else if (propName.equals("Version")) {
            return 2;
         } else {
            return propName.equals("GlobalVariables") ? 6 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getModuleOverrides()));
         if (this.bean.getVariableDefinition() != null) {
            iterators.add(new ArrayIterator(new VariableDefinitionBean[]{this.bean.getVariableDefinition()}));
         }

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
            if (this.bean.isApplicationNameSet()) {
               buf.append("ApplicationName");
               buf.append(String.valueOf(this.bean.getApplicationName()));
            }

            if (this.bean.isConfigRootSet()) {
               buf.append("ConfigRoot");
               buf.append(String.valueOf(this.bean.getConfigRoot()));
            }

            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getModuleOverrides().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getModuleOverrides()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getVariableDefinition());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            if (this.bean.isGlobalVariablesSet()) {
               buf.append("GlobalVariables");
               buf.append(String.valueOf(this.bean.isGlobalVariables()));
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
            DeploymentPlanBeanImpl otherTyped = (DeploymentPlanBeanImpl)other;
            this.computeDiff("ApplicationName", this.bean.getApplicationName(), otherTyped.getApplicationName(), false);
            this.computeDiff("ConfigRoot", this.bean.getConfigRoot(), otherTyped.getConfigRoot(), false);
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeChildDiff("ModuleOverrides", this.bean.getModuleOverrides(), otherTyped.getModuleOverrides(), false);
            this.computeSubDiff("VariableDefinition", this.bean.getVariableDefinition(), otherTyped.getVariableDefinition());
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
            this.computeDiff("GlobalVariables", this.bean.isGlobalVariables(), otherTyped.isGlobalVariables(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DeploymentPlanBeanImpl original = (DeploymentPlanBeanImpl)event.getSourceBean();
            DeploymentPlanBeanImpl proposed = (DeploymentPlanBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ApplicationName")) {
                  original.setApplicationName(proposed.getApplicationName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ConfigRoot")) {
                  original.setConfigRoot(proposed.getConfigRoot());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ModuleOverrides")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addModuleOverride((ModuleOverrideBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeModuleOverride((ModuleOverrideBean)update.getRemovedObject());
                  }

                  if (original.getModuleOverrides() == null || original.getModuleOverrides().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("VariableDefinition")) {
                  if (type == 2) {
                     original.setVariableDefinition((VariableDefinitionBean)this.createCopy((AbstractDescriptorBean)proposed.getVariableDefinition()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("VariableDefinition", (DescriptorBean)original.getVariableDefinition());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("GlobalVariables")) {
                  original.setGlobalVariables(proposed.isGlobalVariables());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            DeploymentPlanBeanImpl copy = (DeploymentPlanBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ApplicationName")) && this.bean.isApplicationNameSet()) {
               copy.setApplicationName(this.bean.getApplicationName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigRoot")) && this.bean.isConfigRootSet()) {
               copy.setConfigRoot(this.bean.getConfigRoot());
            }

            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("ModuleOverrides")) && this.bean.isModuleOverridesSet() && !copy._isSet(4)) {
               ModuleOverrideBean[] oldModuleOverrides = this.bean.getModuleOverrides();
               ModuleOverrideBean[] newModuleOverrides = new ModuleOverrideBean[oldModuleOverrides.length];

               for(int i = 0; i < newModuleOverrides.length; ++i) {
                  newModuleOverrides[i] = (ModuleOverrideBean)((ModuleOverrideBean)this.createCopy((AbstractDescriptorBean)oldModuleOverrides[i], includeObsolete));
               }

               copy.setModuleOverrides(newModuleOverrides);
            }

            if ((excludeProps == null || !excludeProps.contains("VariableDefinition")) && this.bean.isVariableDefinitionSet() && !copy._isSet(3)) {
               Object o = this.bean.getVariableDefinition();
               copy.setVariableDefinition((VariableDefinitionBean)null);
               copy.setVariableDefinition(o == null ? null : (VariableDefinitionBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("GlobalVariables")) && this.bean.isGlobalVariablesSet()) {
               copy.setGlobalVariables(this.bean.isGlobalVariables());
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
         this.inferSubTree(this.bean.getModuleOverrides(), clazz, annotation);
         this.inferSubTree(this.bean.getVariableDefinition(), clazz, annotation);
      }
   }
}
