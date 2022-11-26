package weblogic.diagnostics.image.descriptor;

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

public class HarvesterModuleStatisticsBeanImpl extends AbstractDescriptorBean implements HarvesterModuleStatisticsBean, Serializable {
   private long _AverageSamplingTime;
   private long _CurrentDataSampleCount;
   private long _MaximumSamplingTime;
   private long _MinimumSamplingTime;
   private long _TotalDataSampleCount;
   private long _TotalSamplingCycles;
   private static SchemaHelper2 _schemaHelper;

   public HarvesterModuleStatisticsBeanImpl() {
      this._initializeProperty(-1);
   }

   public HarvesterModuleStatisticsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public HarvesterModuleStatisticsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public long getAverageSamplingTime() {
      return this._AverageSamplingTime;
   }

   public boolean isAverageSamplingTimeInherited() {
      return false;
   }

   public boolean isAverageSamplingTimeSet() {
      return this._isSet(0);
   }

   public void setAverageSamplingTime(long param0) {
      long _oldVal = this._AverageSamplingTime;
      this._AverageSamplingTime = param0;
      this._postSet(0, _oldVal, param0);
   }

   public long getCurrentDataSampleCount() {
      return this._CurrentDataSampleCount;
   }

   public boolean isCurrentDataSampleCountInherited() {
      return false;
   }

   public boolean isCurrentDataSampleCountSet() {
      return this._isSet(1);
   }

   public void setCurrentDataSampleCount(long param0) {
      long _oldVal = this._CurrentDataSampleCount;
      this._CurrentDataSampleCount = param0;
      this._postSet(1, _oldVal, param0);
   }

   public long getMaximumSamplingTime() {
      return this._MaximumSamplingTime;
   }

   public boolean isMaximumSamplingTimeInherited() {
      return false;
   }

   public boolean isMaximumSamplingTimeSet() {
      return this._isSet(2);
   }

   public void setMaximumSamplingTime(long param0) {
      long _oldVal = this._MaximumSamplingTime;
      this._MaximumSamplingTime = param0;
      this._postSet(2, _oldVal, param0);
   }

   public long getMinimumSamplingTime() {
      return this._MinimumSamplingTime;
   }

   public boolean isMinimumSamplingTimeInherited() {
      return false;
   }

   public boolean isMinimumSamplingTimeSet() {
      return this._isSet(3);
   }

   public void setMinimumSamplingTime(long param0) {
      long _oldVal = this._MinimumSamplingTime;
      this._MinimumSamplingTime = param0;
      this._postSet(3, _oldVal, param0);
   }

   public long getTotalDataSampleCount() {
      return this._TotalDataSampleCount;
   }

   public boolean isTotalDataSampleCountInherited() {
      return false;
   }

   public boolean isTotalDataSampleCountSet() {
      return this._isSet(4);
   }

   public void setTotalDataSampleCount(long param0) {
      long _oldVal = this._TotalDataSampleCount;
      this._TotalDataSampleCount = param0;
      this._postSet(4, _oldVal, param0);
   }

   public long getTotalSamplingCycles() {
      return this._TotalSamplingCycles;
   }

   public boolean isTotalSamplingCyclesInherited() {
      return false;
   }

   public boolean isTotalSamplingCyclesSet() {
      return this._isSet(5);
   }

   public void setTotalSamplingCycles(long param0) {
      long _oldVal = this._TotalSamplingCycles;
      this._TotalSamplingCycles = param0;
      this._postSet(5, _oldVal, param0);
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
               this._AverageSamplingTime = 0L;
               if (initOne) {
                  break;
               }
            case 1:
               this._CurrentDataSampleCount = 0L;
               if (initOne) {
                  break;
               }
            case 2:
               this._MaximumSamplingTime = 0L;
               if (initOne) {
                  break;
               }
            case 3:
               this._MinimumSamplingTime = 0L;
               if (initOne) {
                  break;
               }
            case 4:
               this._TotalDataSampleCount = 0L;
               if (initOne) {
                  break;
               }
            case 5:
               this._TotalSamplingCycles = 0L;
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
            case 21:
               if (s.equals("average-sampling-time")) {
                  return 0;
               }

               if (s.equals("maximum-sampling-time")) {
                  return 2;
               }

               if (s.equals("minimum-sampling-time")) {
                  return 3;
               }

               if (s.equals("total-sampling-cycles")) {
                  return 5;
               }
            case 22:
            case 24:
            default:
               break;
            case 23:
               if (s.equals("total-data-sample-count")) {
                  return 4;
               }
               break;
            case 25:
               if (s.equals("current-data-sample-count")) {
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
               return "average-sampling-time";
            case 1:
               return "current-data-sample-count";
            case 2:
               return "maximum-sampling-time";
            case 3:
               return "minimum-sampling-time";
            case 4:
               return "total-data-sample-count";
            case 5:
               return "total-sampling-cycles";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private HarvesterModuleStatisticsBeanImpl bean;

      protected Helper(HarvesterModuleStatisticsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AverageSamplingTime";
            case 1:
               return "CurrentDataSampleCount";
            case 2:
               return "MaximumSamplingTime";
            case 3:
               return "MinimumSamplingTime";
            case 4:
               return "TotalDataSampleCount";
            case 5:
               return "TotalSamplingCycles";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AverageSamplingTime")) {
            return 0;
         } else if (propName.equals("CurrentDataSampleCount")) {
            return 1;
         } else if (propName.equals("MaximumSamplingTime")) {
            return 2;
         } else if (propName.equals("MinimumSamplingTime")) {
            return 3;
         } else if (propName.equals("TotalDataSampleCount")) {
            return 4;
         } else {
            return propName.equals("TotalSamplingCycles") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isAverageSamplingTimeSet()) {
               buf.append("AverageSamplingTime");
               buf.append(String.valueOf(this.bean.getAverageSamplingTime()));
            }

            if (this.bean.isCurrentDataSampleCountSet()) {
               buf.append("CurrentDataSampleCount");
               buf.append(String.valueOf(this.bean.getCurrentDataSampleCount()));
            }

            if (this.bean.isMaximumSamplingTimeSet()) {
               buf.append("MaximumSamplingTime");
               buf.append(String.valueOf(this.bean.getMaximumSamplingTime()));
            }

            if (this.bean.isMinimumSamplingTimeSet()) {
               buf.append("MinimumSamplingTime");
               buf.append(String.valueOf(this.bean.getMinimumSamplingTime()));
            }

            if (this.bean.isTotalDataSampleCountSet()) {
               buf.append("TotalDataSampleCount");
               buf.append(String.valueOf(this.bean.getTotalDataSampleCount()));
            }

            if (this.bean.isTotalSamplingCyclesSet()) {
               buf.append("TotalSamplingCycles");
               buf.append(String.valueOf(this.bean.getTotalSamplingCycles()));
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
            HarvesterModuleStatisticsBeanImpl otherTyped = (HarvesterModuleStatisticsBeanImpl)other;
            this.computeDiff("AverageSamplingTime", this.bean.getAverageSamplingTime(), otherTyped.getAverageSamplingTime(), false);
            this.computeDiff("CurrentDataSampleCount", this.bean.getCurrentDataSampleCount(), otherTyped.getCurrentDataSampleCount(), false);
            this.computeDiff("MaximumSamplingTime", this.bean.getMaximumSamplingTime(), otherTyped.getMaximumSamplingTime(), false);
            this.computeDiff("MinimumSamplingTime", this.bean.getMinimumSamplingTime(), otherTyped.getMinimumSamplingTime(), false);
            this.computeDiff("TotalDataSampleCount", this.bean.getTotalDataSampleCount(), otherTyped.getTotalDataSampleCount(), false);
            this.computeDiff("TotalSamplingCycles", this.bean.getTotalSamplingCycles(), otherTyped.getTotalSamplingCycles(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            HarvesterModuleStatisticsBeanImpl original = (HarvesterModuleStatisticsBeanImpl)event.getSourceBean();
            HarvesterModuleStatisticsBeanImpl proposed = (HarvesterModuleStatisticsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AverageSamplingTime")) {
                  original.setAverageSamplingTime(proposed.getAverageSamplingTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("CurrentDataSampleCount")) {
                  original.setCurrentDataSampleCount(proposed.getCurrentDataSampleCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MaximumSamplingTime")) {
                  original.setMaximumSamplingTime(proposed.getMaximumSamplingTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MinimumSamplingTime")) {
                  original.setMinimumSamplingTime(proposed.getMinimumSamplingTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("TotalDataSampleCount")) {
                  original.setTotalDataSampleCount(proposed.getTotalDataSampleCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("TotalSamplingCycles")) {
                  original.setTotalSamplingCycles(proposed.getTotalSamplingCycles());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            HarvesterModuleStatisticsBeanImpl copy = (HarvesterModuleStatisticsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AverageSamplingTime")) && this.bean.isAverageSamplingTimeSet()) {
               copy.setAverageSamplingTime(this.bean.getAverageSamplingTime());
            }

            if ((excludeProps == null || !excludeProps.contains("CurrentDataSampleCount")) && this.bean.isCurrentDataSampleCountSet()) {
               copy.setCurrentDataSampleCount(this.bean.getCurrentDataSampleCount());
            }

            if ((excludeProps == null || !excludeProps.contains("MaximumSamplingTime")) && this.bean.isMaximumSamplingTimeSet()) {
               copy.setMaximumSamplingTime(this.bean.getMaximumSamplingTime());
            }

            if ((excludeProps == null || !excludeProps.contains("MinimumSamplingTime")) && this.bean.isMinimumSamplingTimeSet()) {
               copy.setMinimumSamplingTime(this.bean.getMinimumSamplingTime());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalDataSampleCount")) && this.bean.isTotalDataSampleCountSet()) {
               copy.setTotalDataSampleCount(this.bean.getTotalDataSampleCount());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalSamplingCycles")) && this.bean.isTotalSamplingCyclesSet()) {
               copy.setTotalSamplingCycles(this.bean.getTotalSamplingCycles());
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
