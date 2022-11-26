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
import weblogic.j2ee.descriptor.wl.customizers.ResDeploymentPlanBeanCustomizer;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ResourceDeploymentPlanBeanImpl extends AbstractDescriptorBean implements ResourceDeploymentPlanBean, Serializable {
   private ConfigResourceOverrideBean[] _ConfigResourceOverrides;
   private String _Description;
   private ExternalResourceOverrideBean[] _ExternalResourceOverrides;
   private boolean _GlobalVariables;
   private VariableDefinitionBean _VariableDefinition;
   private transient ResDeploymentPlanBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ResourceDeploymentPlanBeanImpl() {
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.ResDeploymentPlanBeanCustomizerFactory");
         this._customizer = (ResDeploymentPlanBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ResourceDeploymentPlanBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.ResDeploymentPlanBeanCustomizerFactory");
         this._customizer = (ResDeploymentPlanBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ResourceDeploymentPlanBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.ResDeploymentPlanBeanCustomizerFactory");
         this._customizer = (ResDeploymentPlanBeanCustomizer)customizerFactory.createCustomizer(this);
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

   public VariableDefinitionBean getVariableDefinition() {
      return this._VariableDefinition;
   }

   public boolean isVariableDefinitionInherited() {
      return false;
   }

   public boolean isVariableDefinitionSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getVariableDefinition());
   }

   public void setVariableDefinition(VariableDefinitionBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      VariableDefinitionBean _oldVal = this._VariableDefinition;
      this._VariableDefinition = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean isGlobalVariables() {
      return this._GlobalVariables;
   }

   public boolean isGlobalVariablesInherited() {
      return false;
   }

   public boolean isGlobalVariablesSet() {
      return this._isSet(2);
   }

   public void setGlobalVariables(boolean param0) {
      boolean _oldVal = this._GlobalVariables;
      this._GlobalVariables = param0;
      this._postSet(2, _oldVal, param0);
   }

   public void addExternalResourceOverride(ExternalResourceOverrideBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         ExternalResourceOverrideBean[] _new;
         if (this._isSet(3)) {
            _new = (ExternalResourceOverrideBean[])((ExternalResourceOverrideBean[])this._getHelper()._extendArray(this.getExternalResourceOverrides(), ExternalResourceOverrideBean.class, param0));
         } else {
            _new = new ExternalResourceOverrideBean[]{param0};
         }

         try {
            this.setExternalResourceOverrides(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ExternalResourceOverrideBean[] getExternalResourceOverrides() {
      return this._ExternalResourceOverrides;
   }

   public boolean isExternalResourceOverridesInherited() {
      return false;
   }

   public boolean isExternalResourceOverridesSet() {
      return this._isSet(3);
   }

   public void removeExternalResourceOverride(ExternalResourceOverrideBean param0) {
      this.destroyExternalResourceOverride(param0);
   }

   public void setExternalResourceOverrides(ExternalResourceOverrideBean[] param0) throws InvalidAttributeValueException {
      ExternalResourceOverrideBean[] param0 = param0 == null ? new ExternalResourceOverrideBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ExternalResourceOverrideBean[] _oldVal = this._ExternalResourceOverrides;
      this._ExternalResourceOverrides = (ExternalResourceOverrideBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public ExternalResourceOverrideBean createExternalResourceOverride() {
      ExternalResourceOverrideBeanImpl _val = new ExternalResourceOverrideBeanImpl(this, -1);

      try {
         this.addExternalResourceOverride(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyExternalResourceOverride(ExternalResourceOverrideBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         ExternalResourceOverrideBean[] _old = this.getExternalResourceOverrides();
         ExternalResourceOverrideBean[] _new = (ExternalResourceOverrideBean[])((ExternalResourceOverrideBean[])this._getHelper()._removeElement(_old, ExternalResourceOverrideBean.class, param0));
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
               this.setExternalResourceOverrides(_new);
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

   public void addConfigResourceOverride(ConfigResourceOverrideBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         ConfigResourceOverrideBean[] _new;
         if (this._isSet(4)) {
            _new = (ConfigResourceOverrideBean[])((ConfigResourceOverrideBean[])this._getHelper()._extendArray(this.getConfigResourceOverrides(), ConfigResourceOverrideBean.class, param0));
         } else {
            _new = new ConfigResourceOverrideBean[]{param0};
         }

         try {
            this.setConfigResourceOverrides(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConfigResourceOverrideBean[] getConfigResourceOverrides() {
      return this._ConfigResourceOverrides;
   }

   public boolean isConfigResourceOverridesInherited() {
      return false;
   }

   public boolean isConfigResourceOverridesSet() {
      return this._isSet(4);
   }

   public void removeConfigResourceOverride(ConfigResourceOverrideBean param0) {
      this.destroyConfigResourceOverride(param0);
   }

   public void setConfigResourceOverrides(ConfigResourceOverrideBean[] param0) throws InvalidAttributeValueException {
      ConfigResourceOverrideBean[] param0 = param0 == null ? new ConfigResourceOverrideBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ConfigResourceOverrideBean[] _oldVal = this._ConfigResourceOverrides;
      this._ConfigResourceOverrides = (ConfigResourceOverrideBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public ConfigResourceOverrideBean createConfigResourceOverride() {
      ConfigResourceOverrideBeanImpl _val = new ConfigResourceOverrideBeanImpl(this, -1);

      try {
         this.addConfigResourceOverride(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConfigResourceOverride(ConfigResourceOverrideBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         ConfigResourceOverrideBean[] _old = this.getConfigResourceOverrides();
         ConfigResourceOverrideBean[] _new = (ConfigResourceOverrideBean[])((ConfigResourceOverrideBean[])this._getHelper()._removeElement(_old, ConfigResourceOverrideBean.class, param0));
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
               this.setConfigResourceOverrides(_new);
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

   public ConfigResourceOverrideBean findConfigResourceOverride(String param0) {
      return this._customizer.findConfigResourceOverride(param0);
   }

   public ExternalResourceOverrideBean findExternalResourceOverride(String param0) {
      return this._customizer.findExternalResourceOverride(param0);
   }

   public boolean hasVariable(ConfigResourceOverrideBean param0, DescriptorBean param1, String param2) throws IllegalArgumentException {
      return this._customizer.hasVariable(param0, param1, param2);
   }

   public VariableBean findVariable(ConfigResourceOverrideBean param0, DescriptorBean param1, String param2) throws IllegalArgumentException {
      return this._customizer.findVariable(param0, param1, param2);
   }

   public VariableAssignmentBean[] findVariableAssignments(VariableBean param0) {
      return this._customizer.findVariableAssignments(param0);
   }

   public VariableAssignmentBean findVariableAssignment(ConfigResourceOverrideBean param0, DescriptorBean param1, String param2) throws IllegalArgumentException {
      return this._customizer.findVariableAssignment(param0, param1, param2);
   }

   public VariableBean findOrCreateVariable(ConfigResourceOverrideBean param0, DescriptorBean param1, String param2) throws IllegalArgumentException {
      return this._customizer.findOrCreateVariable(param0, param1, param2);
   }

   public VariableBean findOrCreateVariable(ConfigResourceOverrideBean param0, DescriptorBean param1, String param2, boolean param3) {
      return this._customizer.findOrCreateVariable(param0, param1, param2, param3);
   }

   public VariableBean findOrCreateVariable(ConfigResourceOverrideBean param0, DescriptorBean param1, String param2, boolean param3, Object param4) throws IllegalArgumentException {
      return this._customizer.findOrCreateVariable(param0, param1, param2, param3, param4);
   }

   public Object valueOf(VariableBean param0) {
      return this._customizer.valueOf(param0);
   }

   public VariableAssignmentBean assignVariable(VariableBean param0, ConfigResourceOverrideBean param1, DescriptorBean param2, String param3) {
      return this._customizer.assignVariable(param0, param1, param2, param3);
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._ConfigResourceOverrides = new ConfigResourceOverrideBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ExternalResourceOverrides = new ExternalResourceOverrideBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._VariableDefinition = new VariableDefinitionBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._VariableDefinition);
               if (initOne) {
                  break;
               }
            case 2:
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
      return "http://xmlns.oracle.com/weblogic/resource-deployment-plan/1.0/resource-deployment-plan.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/resource-deployment-plan";
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
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 16:
               if (s.equals("global-variables")) {
                  return 2;
               }
               break;
            case 19:
               if (s.equals("variable-definition")) {
                  return 1;
               }
               break;
            case 24:
               if (s.equals("config-resource-override")) {
                  return 4;
               }
               break;
            case 26:
               if (s.equals("external-resource-override")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new VariableDefinitionBeanImpl.SchemaHelper2();
            case 2:
            default:
               return super.getSchemaHelper(propIndex);
            case 3:
               return new ExternalResourceOverrideBeanImpl.SchemaHelper2();
            case 4:
               return new ConfigResourceOverrideBeanImpl.SchemaHelper2();
         }
      }

      public String getRootElementName() {
         return "resource-deployment-plan";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "variable-definition";
            case 2:
               return "global-variables";
            case 3:
               return "external-resource-override";
            case 4:
               return "config-resource-override";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
            default:
               return super.isBean(propIndex);
            case 3:
               return true;
            case 4:
               return true;
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ResourceDeploymentPlanBeanImpl bean;

      protected Helper(ResourceDeploymentPlanBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "VariableDefinition";
            case 2:
               return "GlobalVariables";
            case 3:
               return "ExternalResourceOverrides";
            case 4:
               return "ConfigResourceOverrides";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConfigResourceOverrides")) {
            return 4;
         } else if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("ExternalResourceOverrides")) {
            return 3;
         } else if (propName.equals("VariableDefinition")) {
            return 1;
         } else {
            return propName.equals("GlobalVariables") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConfigResourceOverrides()));
         iterators.add(new ArrayIterator(this.bean.getExternalResourceOverrides()));
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
            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getConfigResourceOverrides().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConfigResourceOverrides()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getExternalResourceOverrides().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getExternalResourceOverrides()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getVariableDefinition());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            ResourceDeploymentPlanBeanImpl otherTyped = (ResourceDeploymentPlanBeanImpl)other;
            this.computeChildDiff("ConfigResourceOverrides", this.bean.getConfigResourceOverrides(), otherTyped.getConfigResourceOverrides(), false);
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeChildDiff("ExternalResourceOverrides", this.bean.getExternalResourceOverrides(), otherTyped.getExternalResourceOverrides(), false);
            this.computeSubDiff("VariableDefinition", this.bean.getVariableDefinition(), otherTyped.getVariableDefinition());
            this.computeDiff("GlobalVariables", this.bean.isGlobalVariables(), otherTyped.isGlobalVariables(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ResourceDeploymentPlanBeanImpl original = (ResourceDeploymentPlanBeanImpl)event.getSourceBean();
            ResourceDeploymentPlanBeanImpl proposed = (ResourceDeploymentPlanBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConfigResourceOverrides")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConfigResourceOverride((ConfigResourceOverrideBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConfigResourceOverride((ConfigResourceOverrideBean)update.getRemovedObject());
                  }

                  if (original.getConfigResourceOverrides() == null || original.getConfigResourceOverrides().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ExternalResourceOverrides")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addExternalResourceOverride((ExternalResourceOverrideBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeExternalResourceOverride((ExternalResourceOverrideBean)update.getRemovedObject());
                  }

                  if (original.getExternalResourceOverrides() == null || original.getExternalResourceOverrides().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
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

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("GlobalVariables")) {
                  original.setGlobalVariables(proposed.isGlobalVariables());
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
            ResourceDeploymentPlanBeanImpl copy = (ResourceDeploymentPlanBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("ConfigResourceOverrides")) && this.bean.isConfigResourceOverridesSet() && !copy._isSet(4)) {
               ConfigResourceOverrideBean[] oldConfigResourceOverrides = this.bean.getConfigResourceOverrides();
               ConfigResourceOverrideBean[] newConfigResourceOverrides = new ConfigResourceOverrideBean[oldConfigResourceOverrides.length];

               for(i = 0; i < newConfigResourceOverrides.length; ++i) {
                  newConfigResourceOverrides[i] = (ConfigResourceOverrideBean)((ConfigResourceOverrideBean)this.createCopy((AbstractDescriptorBean)oldConfigResourceOverrides[i], includeObsolete));
               }

               copy.setConfigResourceOverrides(newConfigResourceOverrides);
            }

            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("ExternalResourceOverrides")) && this.bean.isExternalResourceOverridesSet() && !copy._isSet(3)) {
               ExternalResourceOverrideBean[] oldExternalResourceOverrides = this.bean.getExternalResourceOverrides();
               ExternalResourceOverrideBean[] newExternalResourceOverrides = new ExternalResourceOverrideBean[oldExternalResourceOverrides.length];

               for(i = 0; i < newExternalResourceOverrides.length; ++i) {
                  newExternalResourceOverrides[i] = (ExternalResourceOverrideBean)((ExternalResourceOverrideBean)this.createCopy((AbstractDescriptorBean)oldExternalResourceOverrides[i], includeObsolete));
               }

               copy.setExternalResourceOverrides(newExternalResourceOverrides);
            }

            if ((excludeProps == null || !excludeProps.contains("VariableDefinition")) && this.bean.isVariableDefinitionSet() && !copy._isSet(1)) {
               Object o = this.bean.getVariableDefinition();
               copy.setVariableDefinition((VariableDefinitionBean)null);
               copy.setVariableDefinition(o == null ? null : (VariableDefinitionBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getConfigResourceOverrides(), clazz, annotation);
         this.inferSubTree(this.bean.getExternalResourceOverrides(), clazz, annotation);
         this.inferSubTree(this.bean.getVariableDefinition(), clazz, annotation);
      }
   }
}
