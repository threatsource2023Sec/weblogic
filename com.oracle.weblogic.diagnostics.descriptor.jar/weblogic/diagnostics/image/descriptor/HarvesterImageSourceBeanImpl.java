package weblogic.diagnostics.image.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class HarvesterImageSourceBeanImpl extends AbstractDescriptorBean implements HarvesterImageSourceBean, Serializable {
   private HarvesterModuleBean[] _HarvesterModules;
   private HarvesterStatisticsBean _Statistics;
   private static SchemaHelper2 _schemaHelper;

   public HarvesterImageSourceBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public HarvesterImageSourceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public HarvesterImageSourceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addHarvesterModule(HarvesterModuleBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         HarvesterModuleBean[] _new;
         if (this._isSet(0)) {
            _new = (HarvesterModuleBean[])((HarvesterModuleBean[])this._getHelper()._extendArray(this.getHarvesterModules(), HarvesterModuleBean.class, param0));
         } else {
            _new = new HarvesterModuleBean[]{param0};
         }

         try {
            this.setHarvesterModules(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public HarvesterModuleBean[] getHarvesterModules() {
      return this._HarvesterModules;
   }

   public boolean isHarvesterModulesInherited() {
      return false;
   }

   public boolean isHarvesterModulesSet() {
      return this._isSet(0);
   }

   public void removeHarvesterModule(HarvesterModuleBean param0) {
      HarvesterModuleBean[] _old = this.getHarvesterModules();
      HarvesterModuleBean[] _new = (HarvesterModuleBean[])((HarvesterModuleBean[])this._getHelper()._removeElement(_old, HarvesterModuleBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setHarvesterModules(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setHarvesterModules(HarvesterModuleBean[] param0) throws InvalidAttributeValueException {
      HarvesterModuleBean[] param0 = param0 == null ? new HarvesterModuleBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      HarvesterModuleBean[] _oldVal = this._HarvesterModules;
      this._HarvesterModules = (HarvesterModuleBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public HarvesterModuleBean createHarvesterModule() {
      HarvesterModuleBeanImpl _val = new HarvesterModuleBeanImpl(this, -1);

      try {
         this.addHarvesterModule(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public HarvesterStatisticsBean getStatistics() {
      return this._Statistics;
   }

   public boolean isStatisticsInherited() {
      return false;
   }

   public boolean isStatisticsSet() {
      return this._isSet(1);
   }

   public void setStatistics(HarvesterStatisticsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getStatistics() != null && param0 != this.getStatistics()) {
         throw new BeanAlreadyExistsException(this.getStatistics() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         HarvesterStatisticsBean _oldVal = this._Statistics;
         this._Statistics = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public HarvesterStatisticsBean createStatistics() {
      HarvesterStatisticsBeanImpl _val = new HarvesterStatisticsBeanImpl(this, -1);

      try {
         this.setStatistics(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._HarvesterModules = new HarvesterModuleBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Statistics = null;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image/1.0/weblogic-diagnostics-image.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image";
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
               if (s.equals("statistics")) {
                  return 1;
               }
               break;
            case 16:
               if (s.equals("harvester-module")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new HarvesterModuleBeanImpl.SchemaHelper2();
            case 1:
               return new HarvesterStatisticsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "harvester-image-source";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "harvester-module";
            case 1:
               return "statistics";
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
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private HarvesterImageSourceBeanImpl bean;

      protected Helper(HarvesterImageSourceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "HarvesterModules";
            case 1:
               return "Statistics";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("HarvesterModules")) {
            return 0;
         } else {
            return propName.equals("Statistics") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getHarvesterModules()));
         if (this.bean.getStatistics() != null) {
            iterators.add(new ArrayIterator(new HarvesterStatisticsBean[]{this.bean.getStatistics()}));
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

            for(int i = 0; i < this.bean.getHarvesterModules().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getHarvesterModules()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getStatistics());
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
            HarvesterImageSourceBeanImpl otherTyped = (HarvesterImageSourceBeanImpl)other;
            this.computeChildDiff("HarvesterModules", this.bean.getHarvesterModules(), otherTyped.getHarvesterModules(), false);
            this.computeChildDiff("Statistics", this.bean.getStatistics(), otherTyped.getStatistics(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            HarvesterImageSourceBeanImpl original = (HarvesterImageSourceBeanImpl)event.getSourceBean();
            HarvesterImageSourceBeanImpl proposed = (HarvesterImageSourceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("HarvesterModules")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addHarvesterModule((HarvesterModuleBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeHarvesterModule((HarvesterModuleBean)update.getRemovedObject());
                  }

                  if (original.getHarvesterModules() == null || original.getHarvesterModules().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("Statistics")) {
                  if (type == 2) {
                     original.setStatistics((HarvesterStatisticsBean)this.createCopy((AbstractDescriptorBean)proposed.getStatistics()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Statistics", (DescriptorBean)original.getStatistics());
                  }

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
            HarvesterImageSourceBeanImpl copy = (HarvesterImageSourceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("HarvesterModules")) && this.bean.isHarvesterModulesSet() && !copy._isSet(0)) {
               HarvesterModuleBean[] oldHarvesterModules = this.bean.getHarvesterModules();
               HarvesterModuleBean[] newHarvesterModules = new HarvesterModuleBean[oldHarvesterModules.length];

               for(int i = 0; i < newHarvesterModules.length; ++i) {
                  newHarvesterModules[i] = (HarvesterModuleBean)((HarvesterModuleBean)this.createCopy((AbstractDescriptorBean)oldHarvesterModules[i], includeObsolete));
               }

               copy.setHarvesterModules(newHarvesterModules);
            }

            if ((excludeProps == null || !excludeProps.contains("Statistics")) && this.bean.isStatisticsSet() && !copy._isSet(1)) {
               Object o = this.bean.getStatistics();
               copy.setStatistics((HarvesterStatisticsBean)null);
               copy.setStatistics(o == null ? null : (HarvesterStatisticsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getHarvesterModules(), clazz, annotation);
         this.inferSubTree(this.bean.getStatistics(), clazz, annotation);
      }
   }
}
