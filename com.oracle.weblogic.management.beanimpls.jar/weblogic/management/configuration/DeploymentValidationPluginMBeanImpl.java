package weblogic.management.configuration;

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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class DeploymentValidationPluginMBeanImpl extends ConfigurationMBeanImpl implements DeploymentValidationPluginMBean, Serializable {
   private String _FactoryClassname;
   private ParameterMBean[] _Parameters;
   private static SchemaHelper2 _schemaHelper;

   public DeploymentValidationPluginMBeanImpl() {
      this._initializeProperty(-1);
   }

   public DeploymentValidationPluginMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DeploymentValidationPluginMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getFactoryClassname() {
      return this._FactoryClassname;
   }

   public boolean isFactoryClassnameInherited() {
      return false;
   }

   public boolean isFactoryClassnameSet() {
      return this._isSet(10);
   }

   public void setFactoryClassname(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FactoryClassname;
      this._FactoryClassname = param0;
      this._postSet(10, _oldVal, param0);
   }

   public void addParameter(ParameterMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         ParameterMBean[] _new;
         if (this._isSet(11)) {
            _new = (ParameterMBean[])((ParameterMBean[])this._getHelper()._extendArray(this.getParameters(), ParameterMBean.class, param0));
         } else {
            _new = new ParameterMBean[]{param0};
         }

         try {
            this.setParameters(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ParameterMBean[] getParameters() {
      return this._Parameters;
   }

   public boolean isParametersInherited() {
      return false;
   }

   public boolean isParametersSet() {
      return this._isSet(11);
   }

   public void removeParameter(ParameterMBean param0) {
      this.destroyParameter(param0);
   }

   public void setParameters(ParameterMBean[] param0) throws InvalidAttributeValueException {
      ParameterMBean[] param0 = param0 == null ? new ParameterMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ParameterMBean[] _oldVal = this._Parameters;
      this._Parameters = (ParameterMBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public void destroyParameter(ParameterMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         ParameterMBean[] _old = this.getParameters();
         ParameterMBean[] _new = (ParameterMBean[])((ParameterMBean[])this._getHelper()._removeElement(_old, ParameterMBean.class, param0));
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
               this.setParameters(_new);
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

   public ParameterMBean createParameter(String param0) {
      ParameterMBeanImpl _val = new ParameterMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addParameter(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._FactoryClassname = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._Parameters = new ParameterMBean[0];
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
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "DeploymentValidationPlugin";
   }

   public void putValue(String name, Object v) {
      if (name.equals("FactoryClassname")) {
         String oldVal = this._FactoryClassname;
         this._FactoryClassname = (String)v;
         this._postSet(10, oldVal, this._FactoryClassname);
      } else if (name.equals("Parameters")) {
         ParameterMBean[] oldVal = this._Parameters;
         this._Parameters = (ParameterMBean[])((ParameterMBean[])v);
         this._postSet(11, oldVal, this._Parameters);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("FactoryClassname")) {
         return this._FactoryClassname;
      } else {
         return name.equals("Parameters") ? this._Parameters : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("parameter")) {
                  return 11;
               }
               break;
            case 17:
               if (s.equals("factory-classname")) {
                  return 10;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 11:
               return new ParameterMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "factory-classname";
            case 11:
               return "parameter";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 11:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 11:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private DeploymentValidationPluginMBeanImpl bean;

      protected Helper(DeploymentValidationPluginMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "FactoryClassname";
            case 11:
               return "Parameters";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FactoryClassname")) {
            return 10;
         } else {
            return propName.equals("Parameters") ? 11 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getParameters()));
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
            if (this.bean.isFactoryClassnameSet()) {
               buf.append("FactoryClassname");
               buf.append(String.valueOf(this.bean.getFactoryClassname()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getParameters().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getParameters()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            DeploymentValidationPluginMBeanImpl otherTyped = (DeploymentValidationPluginMBeanImpl)other;
            this.computeDiff("FactoryClassname", this.bean.getFactoryClassname(), otherTyped.getFactoryClassname(), true);
            this.computeChildDiff("Parameters", this.bean.getParameters(), otherTyped.getParameters(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DeploymentValidationPluginMBeanImpl original = (DeploymentValidationPluginMBeanImpl)event.getSourceBean();
            DeploymentValidationPluginMBeanImpl proposed = (DeploymentValidationPluginMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FactoryClassname")) {
                  original.setFactoryClassname(proposed.getFactoryClassname());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("Parameters")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addParameter((ParameterMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeParameter((ParameterMBean)update.getRemovedObject());
                  }

                  if (original.getParameters() == null || original.getParameters().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
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
            DeploymentValidationPluginMBeanImpl copy = (DeploymentValidationPluginMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FactoryClassname")) && this.bean.isFactoryClassnameSet()) {
               copy.setFactoryClassname(this.bean.getFactoryClassname());
            }

            if ((excludeProps == null || !excludeProps.contains("Parameters")) && this.bean.isParametersSet() && !copy._isSet(11)) {
               ParameterMBean[] oldParameters = this.bean.getParameters();
               ParameterMBean[] newParameters = new ParameterMBean[oldParameters.length];

               for(int i = 0; i < newParameters.length; ++i) {
                  newParameters[i] = (ParameterMBean)((ParameterMBean)this.createCopy((AbstractDescriptorBean)oldParameters[i], includeObsolete));
               }

               copy.setParameters(newParameters);
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
         this.inferSubTree(this.bean.getParameters(), clazz, annotation);
      }
   }
}
