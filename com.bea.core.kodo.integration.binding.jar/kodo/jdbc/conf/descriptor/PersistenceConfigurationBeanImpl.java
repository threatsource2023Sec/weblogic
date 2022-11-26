package kodo.jdbc.conf.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
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

public class PersistenceConfigurationBeanImpl extends AbstractDescriptorBean implements PersistenceConfigurationBean, Serializable {
   private PersistenceUnitConfigurationBean[] _PersistenceUnitConfigurations;
   private String _Version;
   private static SchemaHelper2 _schemaHelper;

   public PersistenceConfigurationBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public PersistenceConfigurationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public PersistenceConfigurationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addPersistenceUnitConfiguration(PersistenceUnitConfigurationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         PersistenceUnitConfigurationBean[] _new;
         if (this._isSet(0)) {
            _new = (PersistenceUnitConfigurationBean[])((PersistenceUnitConfigurationBean[])this._getHelper()._extendArray(this.getPersistenceUnitConfigurations(), PersistenceUnitConfigurationBean.class, param0));
         } else {
            _new = new PersistenceUnitConfigurationBean[]{param0};
         }

         try {
            this.setPersistenceUnitConfigurations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PersistenceUnitConfigurationBean[] getPersistenceUnitConfigurations() {
      return this._PersistenceUnitConfigurations;
   }

   public boolean isPersistenceUnitConfigurationsInherited() {
      return false;
   }

   public boolean isPersistenceUnitConfigurationsSet() {
      return this._isSet(0);
   }

   public void removePersistenceUnitConfiguration(PersistenceUnitConfigurationBean param0) {
      this.destroyPersistenceUnitConfiguration(param0);
   }

   public void setPersistenceUnitConfigurations(PersistenceUnitConfigurationBean[] param0) throws InvalidAttributeValueException {
      PersistenceUnitConfigurationBean[] param0 = param0 == null ? new PersistenceUnitConfigurationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PersistenceUnitConfigurationBean[] _oldVal = this._PersistenceUnitConfigurations;
      this._PersistenceUnitConfigurations = (PersistenceUnitConfigurationBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public PersistenceUnitConfigurationBean lookupPersistenceUnitConfiguration(String param0) {
      Object[] aary = (Object[])this._PersistenceUnitConfigurations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      PersistenceUnitConfigurationBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (PersistenceUnitConfigurationBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public PersistenceUnitConfigurationBean createPersistenceUnitConfiguration(String param0) {
      PersistenceUnitConfigurationBeanImpl lookup = (PersistenceUnitConfigurationBeanImpl)this.lookupPersistenceUnitConfiguration(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         PersistenceUnitConfigurationBeanImpl _val = new PersistenceUnitConfigurationBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addPersistenceUnitConfiguration(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyPersistenceUnitConfiguration(PersistenceUnitConfigurationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         PersistenceUnitConfigurationBean[] _old = this.getPersistenceUnitConfigurations();
         PersistenceUnitConfigurationBean[] _new = (PersistenceUnitConfigurationBean[])((PersistenceUnitConfigurationBean[])this._getHelper()._removeElement(_old, PersistenceUnitConfigurationBean.class, param0));
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
               this.setPersistenceUnitConfigurations(_new);
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
      return this._isSet(1);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._PersistenceUnitConfigurations = new PersistenceUnitConfigurationBean[0];
               if (initOne) {
                  break;
               }
            case 1:
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
      return "http://xmlns.oracle.com/weblogic/persistence-configuration/1.0/persistence-configuration.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/persistence-configuration";
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
                  return 1;
               }
               break;
            case 30:
               if (s.equals("persistence-unit-configuration")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new PersistenceUnitConfigurationBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "persistence-configuration";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "persistence-unit-configuration";
            case 1:
               return "version";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isBean(propIndex);
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
      private PersistenceConfigurationBeanImpl bean;

      protected Helper(PersistenceConfigurationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "PersistenceUnitConfigurations";
            case 1:
               return "Version";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("PersistenceUnitConfigurations")) {
            return 0;
         } else {
            return propName.equals("Version") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getPersistenceUnitConfigurations()));
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

            for(int i = 0; i < this.bean.getPersistenceUnitConfigurations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPersistenceUnitConfigurations()[i]);
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
            PersistenceConfigurationBeanImpl otherTyped = (PersistenceConfigurationBeanImpl)other;
            this.computeChildDiff("PersistenceUnitConfigurations", this.bean.getPersistenceUnitConfigurations(), otherTyped.getPersistenceUnitConfigurations(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PersistenceConfigurationBeanImpl original = (PersistenceConfigurationBeanImpl)event.getSourceBean();
            PersistenceConfigurationBeanImpl proposed = (PersistenceConfigurationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("PersistenceUnitConfigurations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPersistenceUnitConfiguration((PersistenceUnitConfigurationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePersistenceUnitConfiguration((PersistenceUnitConfigurationBean)update.getRemovedObject());
                  }

                  if (original.getPersistenceUnitConfigurations() == null || original.getPersistenceUnitConfigurations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
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
            PersistenceConfigurationBeanImpl copy = (PersistenceConfigurationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("PersistenceUnitConfigurations")) && this.bean.isPersistenceUnitConfigurationsSet() && !copy._isSet(0)) {
               PersistenceUnitConfigurationBean[] oldPersistenceUnitConfigurations = this.bean.getPersistenceUnitConfigurations();
               PersistenceUnitConfigurationBean[] newPersistenceUnitConfigurations = new PersistenceUnitConfigurationBean[oldPersistenceUnitConfigurations.length];

               for(int i = 0; i < newPersistenceUnitConfigurations.length; ++i) {
                  newPersistenceUnitConfigurations[i] = (PersistenceUnitConfigurationBean)((PersistenceUnitConfigurationBean)this.createCopy((AbstractDescriptorBean)oldPersistenceUnitConfigurations[i], includeObsolete));
               }

               copy.setPersistenceUnitConfigurations(newPersistenceUnitConfigurations);
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
         this.inferSubTree(this.bean.getPersistenceUnitConfigurations(), clazz, annotation);
      }
   }
}
