package weblogic.diagnostics.image.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class HarvesterModuleBeanImpl extends AbstractDescriptorBean implements HarvesterModuleBean, Serializable {
   private long _HarvesterCycleDurationNanos;
   private String _HarvesterCycleStartTime;
   private String[] _HarvesterSamples;
   private String _ModuleName;
   private HarvesterModuleStatisticsBean _ModuleStatistics;
   private static SchemaHelper2 _schemaHelper;

   public HarvesterModuleBeanImpl() {
      this._initializeProperty(-1);
   }

   public HarvesterModuleBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public HarvesterModuleBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getModuleName() {
      return this._ModuleName;
   }

   public boolean isModuleNameInherited() {
      return false;
   }

   public boolean isModuleNameSet() {
      return this._isSet(0);
   }

   public void setModuleName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ModuleName;
      this._ModuleName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getHarvesterCycleStartTime() {
      return this._HarvesterCycleStartTime;
   }

   public boolean isHarvesterCycleStartTimeInherited() {
      return false;
   }

   public boolean isHarvesterCycleStartTimeSet() {
      return this._isSet(1);
   }

   public void setHarvesterCycleStartTime(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._HarvesterCycleStartTime;
      this._HarvesterCycleStartTime = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void setHarvesterCycleDurationNanos(long param0) {
      long _oldVal = this._HarvesterCycleDurationNanos;
      this._HarvesterCycleDurationNanos = param0;
      this._postSet(2, _oldVal, param0);
   }

   public long getHarvesterCycleDurationNanos() {
      return this._HarvesterCycleDurationNanos;
   }

   public boolean isHarvesterCycleDurationNanosInherited() {
      return false;
   }

   public boolean isHarvesterCycleDurationNanosSet() {
      return this._isSet(2);
   }

   public void setHarvesterSamples(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._HarvesterSamples;
      this._HarvesterSamples = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean addHarvesterSample(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(3)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getHarvesterSamples(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setHarvesterSamples(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removeHarvesterSample(String param0) {
      String[] _old = this.getHarvesterSamples();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setHarvesterSamples(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public String[] getHarvesterSamples() {
      return this._HarvesterSamples;
   }

   public boolean isHarvesterSamplesInherited() {
      return false;
   }

   public boolean isHarvesterSamplesSet() {
      return this._isSet(3);
   }

   public HarvesterModuleStatisticsBean getModuleStatistics() {
      return this._ModuleStatistics;
   }

   public boolean isModuleStatisticsInherited() {
      return false;
   }

   public boolean isModuleStatisticsSet() {
      return this._isSet(4);
   }

   public void setModuleStatistics(HarvesterModuleStatisticsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getModuleStatistics() != null && param0 != this.getModuleStatistics()) {
         throw new BeanAlreadyExistsException(this.getModuleStatistics() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         HarvesterModuleStatisticsBean _oldVal = this._ModuleStatistics;
         this._ModuleStatistics = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public HarvesterModuleStatisticsBean createModuleStatistics() {
      HarvesterModuleStatisticsBeanImpl _val = new HarvesterModuleStatisticsBeanImpl(this, -1);

      try {
         this.setModuleStatistics(_val);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._HarvesterCycleDurationNanos = 0L;
               if (initOne) {
                  break;
               }
            case 1:
               this._HarvesterCycleStartTime = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._HarvesterSamples = new String[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ModuleName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._ModuleStatistics = null;
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
            case 11:
               if (s.equals("module-name")) {
                  return 0;
               }
               break;
            case 16:
               if (s.equals("harvester-sample")) {
                  return 3;
               }
               break;
            case 17:
               if (s.equals("module-statistics")) {
                  return 4;
               }
               break;
            case 26:
               if (s.equals("harvester-cycle-start-time")) {
                  return 1;
               }
               break;
            case 30:
               if (s.equals("harvester-cycle-duration-nanos")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 4:
               return new HarvesterModuleStatisticsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "module-name";
            case 1:
               return "harvester-cycle-start-time";
            case 2:
               return "harvester-cycle-duration-nanos";
            case 3:
               return "harvester-sample";
            case 4:
               return "module-statistics";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private HarvesterModuleBeanImpl bean;

      protected Helper(HarvesterModuleBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ModuleName";
            case 1:
               return "HarvesterCycleStartTime";
            case 2:
               return "HarvesterCycleDurationNanos";
            case 3:
               return "HarvesterSamples";
            case 4:
               return "ModuleStatistics";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("HarvesterCycleDurationNanos")) {
            return 2;
         } else if (propName.equals("HarvesterCycleStartTime")) {
            return 1;
         } else if (propName.equals("HarvesterSamples")) {
            return 3;
         } else if (propName.equals("ModuleName")) {
            return 0;
         } else {
            return propName.equals("ModuleStatistics") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getModuleStatistics() != null) {
            iterators.add(new ArrayIterator(new HarvesterModuleStatisticsBean[]{this.bean.getModuleStatistics()}));
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
            if (this.bean.isHarvesterCycleDurationNanosSet()) {
               buf.append("HarvesterCycleDurationNanos");
               buf.append(String.valueOf(this.bean.getHarvesterCycleDurationNanos()));
            }

            if (this.bean.isHarvesterCycleStartTimeSet()) {
               buf.append("HarvesterCycleStartTime");
               buf.append(String.valueOf(this.bean.getHarvesterCycleStartTime()));
            }

            if (this.bean.isHarvesterSamplesSet()) {
               buf.append("HarvesterSamples");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getHarvesterSamples())));
            }

            if (this.bean.isModuleNameSet()) {
               buf.append("ModuleName");
               buf.append(String.valueOf(this.bean.getModuleName()));
            }

            childValue = this.computeChildHashValue(this.bean.getModuleStatistics());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            HarvesterModuleBeanImpl otherTyped = (HarvesterModuleBeanImpl)other;
            this.computeDiff("HarvesterCycleDurationNanos", this.bean.getHarvesterCycleDurationNanos(), otherTyped.getHarvesterCycleDurationNanos(), false);
            this.computeDiff("HarvesterCycleStartTime", this.bean.getHarvesterCycleStartTime(), otherTyped.getHarvesterCycleStartTime(), false);
            this.computeDiff("HarvesterSamples", this.bean.getHarvesterSamples(), otherTyped.getHarvesterSamples(), false);
            this.computeDiff("ModuleName", this.bean.getModuleName(), otherTyped.getModuleName(), false);
            this.computeChildDiff("ModuleStatistics", this.bean.getModuleStatistics(), otherTyped.getModuleStatistics(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            HarvesterModuleBeanImpl original = (HarvesterModuleBeanImpl)event.getSourceBean();
            HarvesterModuleBeanImpl proposed = (HarvesterModuleBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("HarvesterCycleDurationNanos")) {
                  original.setHarvesterCycleDurationNanos(proposed.getHarvesterCycleDurationNanos());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("HarvesterCycleStartTime")) {
                  original.setHarvesterCycleStartTime(proposed.getHarvesterCycleStartTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("HarvesterSamples")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addHarvesterSample((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeHarvesterSample((String)update.getRemovedObject());
                  }

                  if (original.getHarvesterSamples() == null || original.getHarvesterSamples().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("ModuleName")) {
                  original.setModuleName(proposed.getModuleName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ModuleStatistics")) {
                  if (type == 2) {
                     original.setModuleStatistics((HarvesterModuleStatisticsBean)this.createCopy((AbstractDescriptorBean)proposed.getModuleStatistics()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ModuleStatistics", (DescriptorBean)original.getModuleStatistics());
                  }

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
            HarvesterModuleBeanImpl copy = (HarvesterModuleBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("HarvesterCycleDurationNanos")) && this.bean.isHarvesterCycleDurationNanosSet()) {
               copy.setHarvesterCycleDurationNanos(this.bean.getHarvesterCycleDurationNanos());
            }

            if ((excludeProps == null || !excludeProps.contains("HarvesterCycleStartTime")) && this.bean.isHarvesterCycleStartTimeSet()) {
               copy.setHarvesterCycleStartTime(this.bean.getHarvesterCycleStartTime());
            }

            if ((excludeProps == null || !excludeProps.contains("HarvesterSamples")) && this.bean.isHarvesterSamplesSet()) {
               Object o = this.bean.getHarvesterSamples();
               copy.setHarvesterSamples(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ModuleName")) && this.bean.isModuleNameSet()) {
               copy.setModuleName(this.bean.getModuleName());
            }

            if ((excludeProps == null || !excludeProps.contains("ModuleStatistics")) && this.bean.isModuleStatisticsSet() && !copy._isSet(4)) {
               Object o = this.bean.getModuleStatistics();
               copy.setModuleStatistics((HarvesterModuleStatisticsBean)null);
               copy.setModuleStatistics(o == null ? null : (HarvesterModuleStatisticsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getModuleStatistics(), clazz, annotation);
      }
   }
}
