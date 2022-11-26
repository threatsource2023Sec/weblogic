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

public class WatchStatisticsBeanImpl extends AbstractDescriptorBean implements WatchStatisticsBean, Serializable {
   private int _ActiveModulesCount;
   private long _AverageEventDataWatchEvaluationTime;
   private long _AverageHarvesterWatchEvaluationTime;
   private long _AverageLogWatchEvaluationTime;
   private long _MaximumEventDataWatchEvaluationTime;
   private long _MaximumHarvesterWatchEvaluationTime;
   private long _MaximumLogWatchEvaluationTime;
   private long _MinimumEventDataWatchEvaluationTime;
   private long _MinimumHarvesterWatchEvaluationTime;
   private long _MinimumLogWatchEvaluationTime;
   private long _TotalEventDataEvaluationCycles;
   private long _TotalEventDataWatchEvaluations;
   private long _TotalEventDataWatchesTriggered;
   private long _TotalFailedNotifications;
   private long _TotalHarvesterEvaluationCycles;
   private long _TotalHarvesterWatchEvaluations;
   private long _TotalHarvesterWatchesTriggered;
   private long _TotalLogEvaluationCycles;
   private long _TotalLogWatchEvaluations;
   private long _TotalLogWatchesTriggered;
   private long _TotalNotificationsPerformed;
   private static SchemaHelper2 _schemaHelper;

   public WatchStatisticsBeanImpl() {
      this._initializeProperty(-1);
   }

   public WatchStatisticsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WatchStatisticsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getActiveModulesCount() {
      return this._ActiveModulesCount;
   }

   public boolean isActiveModulesCountInherited() {
      return false;
   }

   public boolean isActiveModulesCountSet() {
      return this._isSet(0);
   }

   public void setActiveModulesCount(int param0) {
      int _oldVal = this._ActiveModulesCount;
      this._ActiveModulesCount = param0;
      this._postSet(0, _oldVal, param0);
   }

   public long getTotalHarvesterEvaluationCycles() {
      return this._TotalHarvesterEvaluationCycles;
   }

   public boolean isTotalHarvesterEvaluationCyclesInherited() {
      return false;
   }

   public boolean isTotalHarvesterEvaluationCyclesSet() {
      return this._isSet(1);
   }

   public void setTotalHarvesterEvaluationCycles(long param0) {
      long _oldVal = this._TotalHarvesterEvaluationCycles;
      this._TotalHarvesterEvaluationCycles = param0;
      this._postSet(1, _oldVal, param0);
   }

   public long getTotalHarvesterWatchEvaluations() {
      return this._TotalHarvesterWatchEvaluations;
   }

   public boolean isTotalHarvesterWatchEvaluationsInherited() {
      return false;
   }

   public boolean isTotalHarvesterWatchEvaluationsSet() {
      return this._isSet(2);
   }

   public void setTotalHarvesterWatchEvaluations(long param0) {
      long _oldVal = this._TotalHarvesterWatchEvaluations;
      this._TotalHarvesterWatchEvaluations = param0;
      this._postSet(2, _oldVal, param0);
   }

   public long getTotalHarvesterWatchesTriggered() {
      return this._TotalHarvesterWatchesTriggered;
   }

   public boolean isTotalHarvesterWatchesTriggeredInherited() {
      return false;
   }

   public boolean isTotalHarvesterWatchesTriggeredSet() {
      return this._isSet(3);
   }

   public void setTotalHarvesterWatchesTriggered(long param0) {
      long _oldVal = this._TotalHarvesterWatchesTriggered;
      this._TotalHarvesterWatchesTriggered = param0;
      this._postSet(3, _oldVal, param0);
   }

   public long getAverageHarvesterWatchEvaluationTime() {
      return this._AverageHarvesterWatchEvaluationTime;
   }

   public boolean isAverageHarvesterWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isAverageHarvesterWatchEvaluationTimeSet() {
      return this._isSet(4);
   }

   public void setAverageHarvesterWatchEvaluationTime(long param0) {
      long _oldVal = this._AverageHarvesterWatchEvaluationTime;
      this._AverageHarvesterWatchEvaluationTime = param0;
      this._postSet(4, _oldVal, param0);
   }

   public long getTotalLogEvaluationCycles() {
      return this._TotalLogEvaluationCycles;
   }

   public boolean isTotalLogEvaluationCyclesInherited() {
      return false;
   }

   public boolean isTotalLogEvaluationCyclesSet() {
      return this._isSet(5);
   }

   public void setTotalLogEvaluationCycles(long param0) {
      long _oldVal = this._TotalLogEvaluationCycles;
      this._TotalLogEvaluationCycles = param0;
      this._postSet(5, _oldVal, param0);
   }

   public long getTotalLogWatchEvaluations() {
      return this._TotalLogWatchEvaluations;
   }

   public boolean isTotalLogWatchEvaluationsInherited() {
      return false;
   }

   public boolean isTotalLogWatchEvaluationsSet() {
      return this._isSet(6);
   }

   public void setTotalLogWatchEvaluations(long param0) {
      long _oldVal = this._TotalLogWatchEvaluations;
      this._TotalLogWatchEvaluations = param0;
      this._postSet(6, _oldVal, param0);
   }

   public long getTotalLogWatchesTriggered() {
      return this._TotalLogWatchesTriggered;
   }

   public boolean isTotalLogWatchesTriggeredInherited() {
      return false;
   }

   public boolean isTotalLogWatchesTriggeredSet() {
      return this._isSet(7);
   }

   public void setTotalLogWatchesTriggered(long param0) {
      long _oldVal = this._TotalLogWatchesTriggered;
      this._TotalLogWatchesTriggered = param0;
      this._postSet(7, _oldVal, param0);
   }

   public long getAverageLogWatchEvaluationTime() {
      return this._AverageLogWatchEvaluationTime;
   }

   public boolean isAverageLogWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isAverageLogWatchEvaluationTimeSet() {
      return this._isSet(8);
   }

   public void setAverageLogWatchEvaluationTime(long param0) {
      long _oldVal = this._AverageLogWatchEvaluationTime;
      this._AverageLogWatchEvaluationTime = param0;
      this._postSet(8, _oldVal, param0);
   }

   public long getTotalEventDataEvaluationCycles() {
      return this._TotalEventDataEvaluationCycles;
   }

   public boolean isTotalEventDataEvaluationCyclesInherited() {
      return false;
   }

   public boolean isTotalEventDataEvaluationCyclesSet() {
      return this._isSet(9);
   }

   public void setTotalEventDataEvaluationCycles(long param0) {
      long _oldVal = this._TotalEventDataEvaluationCycles;
      this._TotalEventDataEvaluationCycles = param0;
      this._postSet(9, _oldVal, param0);
   }

   public long getTotalEventDataWatchEvaluations() {
      return this._TotalEventDataWatchEvaluations;
   }

   public boolean isTotalEventDataWatchEvaluationsInherited() {
      return false;
   }

   public boolean isTotalEventDataWatchEvaluationsSet() {
      return this._isSet(10);
   }

   public void setTotalEventDataWatchEvaluations(long param0) {
      long _oldVal = this._TotalEventDataWatchEvaluations;
      this._TotalEventDataWatchEvaluations = param0;
      this._postSet(10, _oldVal, param0);
   }

   public long getTotalEventDataWatchesTriggered() {
      return this._TotalEventDataWatchesTriggered;
   }

   public boolean isTotalEventDataWatchesTriggeredInherited() {
      return false;
   }

   public boolean isTotalEventDataWatchesTriggeredSet() {
      return this._isSet(11);
   }

   public void setTotalEventDataWatchesTriggered(long param0) {
      long _oldVal = this._TotalEventDataWatchesTriggered;
      this._TotalEventDataWatchesTriggered = param0;
      this._postSet(11, _oldVal, param0);
   }

   public long getAverageEventDataWatchEvaluationTime() {
      return this._AverageEventDataWatchEvaluationTime;
   }

   public boolean isAverageEventDataWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isAverageEventDataWatchEvaluationTimeSet() {
      return this._isSet(12);
   }

   public void setAverageEventDataWatchEvaluationTime(long param0) {
      long _oldVal = this._AverageEventDataWatchEvaluationTime;
      this._AverageEventDataWatchEvaluationTime = param0;
      this._postSet(12, _oldVal, param0);
   }

   public long getTotalNotificationsPerformed() {
      return this._TotalNotificationsPerformed;
   }

   public boolean isTotalNotificationsPerformedInherited() {
      return false;
   }

   public boolean isTotalNotificationsPerformedSet() {
      return this._isSet(13);
   }

   public void setTotalNotificationsPerformed(long param0) {
      long _oldVal = this._TotalNotificationsPerformed;
      this._TotalNotificationsPerformed = param0;
      this._postSet(13, _oldVal, param0);
   }

   public long getTotalFailedNotifications() {
      return this._TotalFailedNotifications;
   }

   public boolean isTotalFailedNotificationsInherited() {
      return false;
   }

   public boolean isTotalFailedNotificationsSet() {
      return this._isSet(14);
   }

   public void setTotalFailedNotifications(long param0) {
      long _oldVal = this._TotalFailedNotifications;
      this._TotalFailedNotifications = param0;
      this._postSet(14, _oldVal, param0);
   }

   public long getMinimumHarvesterWatchEvaluationTime() {
      return this._MinimumHarvesterWatchEvaluationTime;
   }

   public boolean isMinimumHarvesterWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMinimumHarvesterWatchEvaluationTimeSet() {
      return this._isSet(15);
   }

   public void setMinimumHarvesterWatchEvaluationTime(long param0) {
      long _oldVal = this._MinimumHarvesterWatchEvaluationTime;
      this._MinimumHarvesterWatchEvaluationTime = param0;
      this._postSet(15, _oldVal, param0);
   }

   public long getMaximumHarvesterWatchEvaluationTime() {
      return this._MaximumHarvesterWatchEvaluationTime;
   }

   public boolean isMaximumHarvesterWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMaximumHarvesterWatchEvaluationTimeSet() {
      return this._isSet(16);
   }

   public void setMaximumHarvesterWatchEvaluationTime(long param0) {
      long _oldVal = this._MaximumHarvesterWatchEvaluationTime;
      this._MaximumHarvesterWatchEvaluationTime = param0;
      this._postSet(16, _oldVal, param0);
   }

   public long getMinimumLogWatchEvaluationTime() {
      return this._MinimumLogWatchEvaluationTime;
   }

   public boolean isMinimumLogWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMinimumLogWatchEvaluationTimeSet() {
      return this._isSet(17);
   }

   public void setMinimumLogWatchEvaluationTime(long param0) {
      long _oldVal = this._MinimumLogWatchEvaluationTime;
      this._MinimumLogWatchEvaluationTime = param0;
      this._postSet(17, _oldVal, param0);
   }

   public long getMaximumLogWatchEvaluationTime() {
      return this._MaximumLogWatchEvaluationTime;
   }

   public boolean isMaximumLogWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMaximumLogWatchEvaluationTimeSet() {
      return this._isSet(18);
   }

   public void setMaximumLogWatchEvaluationTime(long param0) {
      long _oldVal = this._MaximumLogWatchEvaluationTime;
      this._MaximumLogWatchEvaluationTime = param0;
      this._postSet(18, _oldVal, param0);
   }

   public long getMinimumEventDataWatchEvaluationTime() {
      return this._MinimumEventDataWatchEvaluationTime;
   }

   public boolean isMinimumEventDataWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMinimumEventDataWatchEvaluationTimeSet() {
      return this._isSet(19);
   }

   public void setMinimumEventDataWatchEvaluationTime(long param0) {
      long _oldVal = this._MinimumEventDataWatchEvaluationTime;
      this._MinimumEventDataWatchEvaluationTime = param0;
      this._postSet(19, _oldVal, param0);
   }

   public long getMaximumEventDataWatchEvaluationTime() {
      return this._MaximumEventDataWatchEvaluationTime;
   }

   public boolean isMaximumEventDataWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMaximumEventDataWatchEvaluationTimeSet() {
      return this._isSet(20);
   }

   public void setMaximumEventDataWatchEvaluationTime(long param0) {
      long _oldVal = this._MaximumEventDataWatchEvaluationTime;
      this._MaximumEventDataWatchEvaluationTime = param0;
      this._postSet(20, _oldVal, param0);
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
               this._ActiveModulesCount = 0;
               if (initOne) {
                  break;
               }
            case 12:
               this._AverageEventDataWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 4:
               this._AverageHarvesterWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 8:
               this._AverageLogWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 20:
               this._MaximumEventDataWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 16:
               this._MaximumHarvesterWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 18:
               this._MaximumLogWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 19:
               this._MinimumEventDataWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 15:
               this._MinimumHarvesterWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 17:
               this._MinimumLogWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 9:
               this._TotalEventDataEvaluationCycles = 0L;
               if (initOne) {
                  break;
               }
            case 10:
               this._TotalEventDataWatchEvaluations = 0L;
               if (initOne) {
                  break;
               }
            case 11:
               this._TotalEventDataWatchesTriggered = 0L;
               if (initOne) {
                  break;
               }
            case 14:
               this._TotalFailedNotifications = 0L;
               if (initOne) {
                  break;
               }
            case 1:
               this._TotalHarvesterEvaluationCycles = 0L;
               if (initOne) {
                  break;
               }
            case 2:
               this._TotalHarvesterWatchEvaluations = 0L;
               if (initOne) {
                  break;
               }
            case 3:
               this._TotalHarvesterWatchesTriggered = 0L;
               if (initOne) {
                  break;
               }
            case 5:
               this._TotalLogEvaluationCycles = 0L;
               if (initOne) {
                  break;
               }
            case 6:
               this._TotalLogWatchEvaluations = 0L;
               if (initOne) {
                  break;
               }
            case 7:
               this._TotalLogWatchesTriggered = 0L;
               if (initOne) {
                  break;
               }
            case 13:
               this._TotalNotificationsPerformed = 0L;
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
            case 20:
               if (s.equals("active-modules-count")) {
                  return 0;
               }
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 28:
            case 30:
            case 31:
            case 32:
            case 35:
            case 36:
            case 37:
            case 38:
            default:
               break;
            case 26:
               if (s.equals("total-failed-notifications")) {
                  return 14;
               }
               break;
            case 27:
               if (s.equals("total-log-evaluation-cycles")) {
                  return 5;
               }

               if (s.equals("total-log-watch-evaluations")) {
                  return 6;
               }

               if (s.equals("total-log-watches-triggered")) {
                  return 7;
               }
               break;
            case 29:
               if (s.equals("total-notifications-performed")) {
                  return 13;
               }
               break;
            case 33:
               if (s.equals("average-log-watch-evaluation-time")) {
                  return 8;
               }

               if (s.equals("maximum-log-watch-evaluation-time")) {
                  return 18;
               }

               if (s.equals("minimum-log-watch-evaluation-time")) {
                  return 17;
               }

               if (s.equals("total-harvester-evaluation-cycles")) {
                  return 1;
               }

               if (s.equals("total-harvester-watch-evaluations")) {
                  return 2;
               }

               if (s.equals("total-harvester-watches-triggered")) {
                  return 3;
               }
               break;
            case 34:
               if (s.equals("total-event-data-evaluation-cycles")) {
                  return 9;
               }

               if (s.equals("total-event-data-watch-evaluations")) {
                  return 10;
               }

               if (s.equals("total-event-data-watches-triggered")) {
                  return 11;
               }
               break;
            case 39:
               if (s.equals("average-harvester-watch-evaluation-time")) {
                  return 4;
               }

               if (s.equals("maximum-harvester-watch-evaluation-time")) {
                  return 16;
               }

               if (s.equals("minimum-harvester-watch-evaluation-time")) {
                  return 15;
               }
               break;
            case 40:
               if (s.equals("average-event-data-watch-evaluation-time")) {
                  return 12;
               }

               if (s.equals("maximum-event-data-watch-evaluation-time")) {
                  return 20;
               }

               if (s.equals("minimum-event-data-watch-evaluation-time")) {
                  return 19;
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
               return "active-modules-count";
            case 1:
               return "total-harvester-evaluation-cycles";
            case 2:
               return "total-harvester-watch-evaluations";
            case 3:
               return "total-harvester-watches-triggered";
            case 4:
               return "average-harvester-watch-evaluation-time";
            case 5:
               return "total-log-evaluation-cycles";
            case 6:
               return "total-log-watch-evaluations";
            case 7:
               return "total-log-watches-triggered";
            case 8:
               return "average-log-watch-evaluation-time";
            case 9:
               return "total-event-data-evaluation-cycles";
            case 10:
               return "total-event-data-watch-evaluations";
            case 11:
               return "total-event-data-watches-triggered";
            case 12:
               return "average-event-data-watch-evaluation-time";
            case 13:
               return "total-notifications-performed";
            case 14:
               return "total-failed-notifications";
            case 15:
               return "minimum-harvester-watch-evaluation-time";
            case 16:
               return "maximum-harvester-watch-evaluation-time";
            case 17:
               return "minimum-log-watch-evaluation-time";
            case 18:
               return "maximum-log-watch-evaluation-time";
            case 19:
               return "minimum-event-data-watch-evaluation-time";
            case 20:
               return "maximum-event-data-watch-evaluation-time";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WatchStatisticsBeanImpl bean;

      protected Helper(WatchStatisticsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ActiveModulesCount";
            case 1:
               return "TotalHarvesterEvaluationCycles";
            case 2:
               return "TotalHarvesterWatchEvaluations";
            case 3:
               return "TotalHarvesterWatchesTriggered";
            case 4:
               return "AverageHarvesterWatchEvaluationTime";
            case 5:
               return "TotalLogEvaluationCycles";
            case 6:
               return "TotalLogWatchEvaluations";
            case 7:
               return "TotalLogWatchesTriggered";
            case 8:
               return "AverageLogWatchEvaluationTime";
            case 9:
               return "TotalEventDataEvaluationCycles";
            case 10:
               return "TotalEventDataWatchEvaluations";
            case 11:
               return "TotalEventDataWatchesTriggered";
            case 12:
               return "AverageEventDataWatchEvaluationTime";
            case 13:
               return "TotalNotificationsPerformed";
            case 14:
               return "TotalFailedNotifications";
            case 15:
               return "MinimumHarvesterWatchEvaluationTime";
            case 16:
               return "MaximumHarvesterWatchEvaluationTime";
            case 17:
               return "MinimumLogWatchEvaluationTime";
            case 18:
               return "MaximumLogWatchEvaluationTime";
            case 19:
               return "MinimumEventDataWatchEvaluationTime";
            case 20:
               return "MaximumEventDataWatchEvaluationTime";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActiveModulesCount")) {
            return 0;
         } else if (propName.equals("AverageEventDataWatchEvaluationTime")) {
            return 12;
         } else if (propName.equals("AverageHarvesterWatchEvaluationTime")) {
            return 4;
         } else if (propName.equals("AverageLogWatchEvaluationTime")) {
            return 8;
         } else if (propName.equals("MaximumEventDataWatchEvaluationTime")) {
            return 20;
         } else if (propName.equals("MaximumHarvesterWatchEvaluationTime")) {
            return 16;
         } else if (propName.equals("MaximumLogWatchEvaluationTime")) {
            return 18;
         } else if (propName.equals("MinimumEventDataWatchEvaluationTime")) {
            return 19;
         } else if (propName.equals("MinimumHarvesterWatchEvaluationTime")) {
            return 15;
         } else if (propName.equals("MinimumLogWatchEvaluationTime")) {
            return 17;
         } else if (propName.equals("TotalEventDataEvaluationCycles")) {
            return 9;
         } else if (propName.equals("TotalEventDataWatchEvaluations")) {
            return 10;
         } else if (propName.equals("TotalEventDataWatchesTriggered")) {
            return 11;
         } else if (propName.equals("TotalFailedNotifications")) {
            return 14;
         } else if (propName.equals("TotalHarvesterEvaluationCycles")) {
            return 1;
         } else if (propName.equals("TotalHarvesterWatchEvaluations")) {
            return 2;
         } else if (propName.equals("TotalHarvesterWatchesTriggered")) {
            return 3;
         } else if (propName.equals("TotalLogEvaluationCycles")) {
            return 5;
         } else if (propName.equals("TotalLogWatchEvaluations")) {
            return 6;
         } else if (propName.equals("TotalLogWatchesTriggered")) {
            return 7;
         } else {
            return propName.equals("TotalNotificationsPerformed") ? 13 : super.getPropertyIndex(propName);
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
            if (this.bean.isActiveModulesCountSet()) {
               buf.append("ActiveModulesCount");
               buf.append(String.valueOf(this.bean.getActiveModulesCount()));
            }

            if (this.bean.isAverageEventDataWatchEvaluationTimeSet()) {
               buf.append("AverageEventDataWatchEvaluationTime");
               buf.append(String.valueOf(this.bean.getAverageEventDataWatchEvaluationTime()));
            }

            if (this.bean.isAverageHarvesterWatchEvaluationTimeSet()) {
               buf.append("AverageHarvesterWatchEvaluationTime");
               buf.append(String.valueOf(this.bean.getAverageHarvesterWatchEvaluationTime()));
            }

            if (this.bean.isAverageLogWatchEvaluationTimeSet()) {
               buf.append("AverageLogWatchEvaluationTime");
               buf.append(String.valueOf(this.bean.getAverageLogWatchEvaluationTime()));
            }

            if (this.bean.isMaximumEventDataWatchEvaluationTimeSet()) {
               buf.append("MaximumEventDataWatchEvaluationTime");
               buf.append(String.valueOf(this.bean.getMaximumEventDataWatchEvaluationTime()));
            }

            if (this.bean.isMaximumHarvesterWatchEvaluationTimeSet()) {
               buf.append("MaximumHarvesterWatchEvaluationTime");
               buf.append(String.valueOf(this.bean.getMaximumHarvesterWatchEvaluationTime()));
            }

            if (this.bean.isMaximumLogWatchEvaluationTimeSet()) {
               buf.append("MaximumLogWatchEvaluationTime");
               buf.append(String.valueOf(this.bean.getMaximumLogWatchEvaluationTime()));
            }

            if (this.bean.isMinimumEventDataWatchEvaluationTimeSet()) {
               buf.append("MinimumEventDataWatchEvaluationTime");
               buf.append(String.valueOf(this.bean.getMinimumEventDataWatchEvaluationTime()));
            }

            if (this.bean.isMinimumHarvesterWatchEvaluationTimeSet()) {
               buf.append("MinimumHarvesterWatchEvaluationTime");
               buf.append(String.valueOf(this.bean.getMinimumHarvesterWatchEvaluationTime()));
            }

            if (this.bean.isMinimumLogWatchEvaluationTimeSet()) {
               buf.append("MinimumLogWatchEvaluationTime");
               buf.append(String.valueOf(this.bean.getMinimumLogWatchEvaluationTime()));
            }

            if (this.bean.isTotalEventDataEvaluationCyclesSet()) {
               buf.append("TotalEventDataEvaluationCycles");
               buf.append(String.valueOf(this.bean.getTotalEventDataEvaluationCycles()));
            }

            if (this.bean.isTotalEventDataWatchEvaluationsSet()) {
               buf.append("TotalEventDataWatchEvaluations");
               buf.append(String.valueOf(this.bean.getTotalEventDataWatchEvaluations()));
            }

            if (this.bean.isTotalEventDataWatchesTriggeredSet()) {
               buf.append("TotalEventDataWatchesTriggered");
               buf.append(String.valueOf(this.bean.getTotalEventDataWatchesTriggered()));
            }

            if (this.bean.isTotalFailedNotificationsSet()) {
               buf.append("TotalFailedNotifications");
               buf.append(String.valueOf(this.bean.getTotalFailedNotifications()));
            }

            if (this.bean.isTotalHarvesterEvaluationCyclesSet()) {
               buf.append("TotalHarvesterEvaluationCycles");
               buf.append(String.valueOf(this.bean.getTotalHarvesterEvaluationCycles()));
            }

            if (this.bean.isTotalHarvesterWatchEvaluationsSet()) {
               buf.append("TotalHarvesterWatchEvaluations");
               buf.append(String.valueOf(this.bean.getTotalHarvesterWatchEvaluations()));
            }

            if (this.bean.isTotalHarvesterWatchesTriggeredSet()) {
               buf.append("TotalHarvesterWatchesTriggered");
               buf.append(String.valueOf(this.bean.getTotalHarvesterWatchesTriggered()));
            }

            if (this.bean.isTotalLogEvaluationCyclesSet()) {
               buf.append("TotalLogEvaluationCycles");
               buf.append(String.valueOf(this.bean.getTotalLogEvaluationCycles()));
            }

            if (this.bean.isTotalLogWatchEvaluationsSet()) {
               buf.append("TotalLogWatchEvaluations");
               buf.append(String.valueOf(this.bean.getTotalLogWatchEvaluations()));
            }

            if (this.bean.isTotalLogWatchesTriggeredSet()) {
               buf.append("TotalLogWatchesTriggered");
               buf.append(String.valueOf(this.bean.getTotalLogWatchesTriggered()));
            }

            if (this.bean.isTotalNotificationsPerformedSet()) {
               buf.append("TotalNotificationsPerformed");
               buf.append(String.valueOf(this.bean.getTotalNotificationsPerformed()));
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
            WatchStatisticsBeanImpl otherTyped = (WatchStatisticsBeanImpl)other;
            this.computeDiff("ActiveModulesCount", this.bean.getActiveModulesCount(), otherTyped.getActiveModulesCount(), false);
            this.computeDiff("AverageEventDataWatchEvaluationTime", this.bean.getAverageEventDataWatchEvaluationTime(), otherTyped.getAverageEventDataWatchEvaluationTime(), false);
            this.computeDiff("AverageHarvesterWatchEvaluationTime", this.bean.getAverageHarvesterWatchEvaluationTime(), otherTyped.getAverageHarvesterWatchEvaluationTime(), false);
            this.computeDiff("AverageLogWatchEvaluationTime", this.bean.getAverageLogWatchEvaluationTime(), otherTyped.getAverageLogWatchEvaluationTime(), false);
            this.computeDiff("MaximumEventDataWatchEvaluationTime", this.bean.getMaximumEventDataWatchEvaluationTime(), otherTyped.getMaximumEventDataWatchEvaluationTime(), false);
            this.computeDiff("MaximumHarvesterWatchEvaluationTime", this.bean.getMaximumHarvesterWatchEvaluationTime(), otherTyped.getMaximumHarvesterWatchEvaluationTime(), false);
            this.computeDiff("MaximumLogWatchEvaluationTime", this.bean.getMaximumLogWatchEvaluationTime(), otherTyped.getMaximumLogWatchEvaluationTime(), false);
            this.computeDiff("MinimumEventDataWatchEvaluationTime", this.bean.getMinimumEventDataWatchEvaluationTime(), otherTyped.getMinimumEventDataWatchEvaluationTime(), false);
            this.computeDiff("MinimumHarvesterWatchEvaluationTime", this.bean.getMinimumHarvesterWatchEvaluationTime(), otherTyped.getMinimumHarvesterWatchEvaluationTime(), false);
            this.computeDiff("MinimumLogWatchEvaluationTime", this.bean.getMinimumLogWatchEvaluationTime(), otherTyped.getMinimumLogWatchEvaluationTime(), false);
            this.computeDiff("TotalEventDataEvaluationCycles", this.bean.getTotalEventDataEvaluationCycles(), otherTyped.getTotalEventDataEvaluationCycles(), false);
            this.computeDiff("TotalEventDataWatchEvaluations", this.bean.getTotalEventDataWatchEvaluations(), otherTyped.getTotalEventDataWatchEvaluations(), false);
            this.computeDiff("TotalEventDataWatchesTriggered", this.bean.getTotalEventDataWatchesTriggered(), otherTyped.getTotalEventDataWatchesTriggered(), false);
            this.computeDiff("TotalFailedNotifications", this.bean.getTotalFailedNotifications(), otherTyped.getTotalFailedNotifications(), false);
            this.computeDiff("TotalHarvesterEvaluationCycles", this.bean.getTotalHarvesterEvaluationCycles(), otherTyped.getTotalHarvesterEvaluationCycles(), false);
            this.computeDiff("TotalHarvesterWatchEvaluations", this.bean.getTotalHarvesterWatchEvaluations(), otherTyped.getTotalHarvesterWatchEvaluations(), false);
            this.computeDiff("TotalHarvesterWatchesTriggered", this.bean.getTotalHarvesterWatchesTriggered(), otherTyped.getTotalHarvesterWatchesTriggered(), false);
            this.computeDiff("TotalLogEvaluationCycles", this.bean.getTotalLogEvaluationCycles(), otherTyped.getTotalLogEvaluationCycles(), false);
            this.computeDiff("TotalLogWatchEvaluations", this.bean.getTotalLogWatchEvaluations(), otherTyped.getTotalLogWatchEvaluations(), false);
            this.computeDiff("TotalLogWatchesTriggered", this.bean.getTotalLogWatchesTriggered(), otherTyped.getTotalLogWatchesTriggered(), false);
            this.computeDiff("TotalNotificationsPerformed", this.bean.getTotalNotificationsPerformed(), otherTyped.getTotalNotificationsPerformed(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WatchStatisticsBeanImpl original = (WatchStatisticsBeanImpl)event.getSourceBean();
            WatchStatisticsBeanImpl proposed = (WatchStatisticsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ActiveModulesCount")) {
                  original.setActiveModulesCount(proposed.getActiveModulesCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("AverageEventDataWatchEvaluationTime")) {
                  original.setAverageEventDataWatchEvaluationTime(proposed.getAverageEventDataWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("AverageHarvesterWatchEvaluationTime")) {
                  original.setAverageHarvesterWatchEvaluationTime(proposed.getAverageHarvesterWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("AverageLogWatchEvaluationTime")) {
                  original.setAverageLogWatchEvaluationTime(proposed.getAverageLogWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("MaximumEventDataWatchEvaluationTime")) {
                  original.setMaximumEventDataWatchEvaluationTime(proposed.getMaximumEventDataWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("MaximumHarvesterWatchEvaluationTime")) {
                  original.setMaximumHarvesterWatchEvaluationTime(proposed.getMaximumHarvesterWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("MaximumLogWatchEvaluationTime")) {
                  original.setMaximumLogWatchEvaluationTime(proposed.getMaximumLogWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("MinimumEventDataWatchEvaluationTime")) {
                  original.setMinimumEventDataWatchEvaluationTime(proposed.getMinimumEventDataWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("MinimumHarvesterWatchEvaluationTime")) {
                  original.setMinimumHarvesterWatchEvaluationTime(proposed.getMinimumHarvesterWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("MinimumLogWatchEvaluationTime")) {
                  original.setMinimumLogWatchEvaluationTime(proposed.getMinimumLogWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("TotalEventDataEvaluationCycles")) {
                  original.setTotalEventDataEvaluationCycles(proposed.getTotalEventDataEvaluationCycles());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("TotalEventDataWatchEvaluations")) {
                  original.setTotalEventDataWatchEvaluations(proposed.getTotalEventDataWatchEvaluations());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("TotalEventDataWatchesTriggered")) {
                  original.setTotalEventDataWatchesTriggered(proposed.getTotalEventDataWatchesTriggered());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("TotalFailedNotifications")) {
                  original.setTotalFailedNotifications(proposed.getTotalFailedNotifications());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("TotalHarvesterEvaluationCycles")) {
                  original.setTotalHarvesterEvaluationCycles(proposed.getTotalHarvesterEvaluationCycles());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("TotalHarvesterWatchEvaluations")) {
                  original.setTotalHarvesterWatchEvaluations(proposed.getTotalHarvesterWatchEvaluations());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TotalHarvesterWatchesTriggered")) {
                  original.setTotalHarvesterWatchesTriggered(proposed.getTotalHarvesterWatchesTriggered());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("TotalLogEvaluationCycles")) {
                  original.setTotalLogEvaluationCycles(proposed.getTotalLogEvaluationCycles());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("TotalLogWatchEvaluations")) {
                  original.setTotalLogWatchEvaluations(proposed.getTotalLogWatchEvaluations());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("TotalLogWatchesTriggered")) {
                  original.setTotalLogWatchesTriggered(proposed.getTotalLogWatchesTriggered());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("TotalNotificationsPerformed")) {
                  original.setTotalNotificationsPerformed(proposed.getTotalNotificationsPerformed());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            WatchStatisticsBeanImpl copy = (WatchStatisticsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ActiveModulesCount")) && this.bean.isActiveModulesCountSet()) {
               copy.setActiveModulesCount(this.bean.getActiveModulesCount());
            }

            if ((excludeProps == null || !excludeProps.contains("AverageEventDataWatchEvaluationTime")) && this.bean.isAverageEventDataWatchEvaluationTimeSet()) {
               copy.setAverageEventDataWatchEvaluationTime(this.bean.getAverageEventDataWatchEvaluationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("AverageHarvesterWatchEvaluationTime")) && this.bean.isAverageHarvesterWatchEvaluationTimeSet()) {
               copy.setAverageHarvesterWatchEvaluationTime(this.bean.getAverageHarvesterWatchEvaluationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("AverageLogWatchEvaluationTime")) && this.bean.isAverageLogWatchEvaluationTimeSet()) {
               copy.setAverageLogWatchEvaluationTime(this.bean.getAverageLogWatchEvaluationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("MaximumEventDataWatchEvaluationTime")) && this.bean.isMaximumEventDataWatchEvaluationTimeSet()) {
               copy.setMaximumEventDataWatchEvaluationTime(this.bean.getMaximumEventDataWatchEvaluationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("MaximumHarvesterWatchEvaluationTime")) && this.bean.isMaximumHarvesterWatchEvaluationTimeSet()) {
               copy.setMaximumHarvesterWatchEvaluationTime(this.bean.getMaximumHarvesterWatchEvaluationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("MaximumLogWatchEvaluationTime")) && this.bean.isMaximumLogWatchEvaluationTimeSet()) {
               copy.setMaximumLogWatchEvaluationTime(this.bean.getMaximumLogWatchEvaluationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("MinimumEventDataWatchEvaluationTime")) && this.bean.isMinimumEventDataWatchEvaluationTimeSet()) {
               copy.setMinimumEventDataWatchEvaluationTime(this.bean.getMinimumEventDataWatchEvaluationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("MinimumHarvesterWatchEvaluationTime")) && this.bean.isMinimumHarvesterWatchEvaluationTimeSet()) {
               copy.setMinimumHarvesterWatchEvaluationTime(this.bean.getMinimumHarvesterWatchEvaluationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("MinimumLogWatchEvaluationTime")) && this.bean.isMinimumLogWatchEvaluationTimeSet()) {
               copy.setMinimumLogWatchEvaluationTime(this.bean.getMinimumLogWatchEvaluationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalEventDataEvaluationCycles")) && this.bean.isTotalEventDataEvaluationCyclesSet()) {
               copy.setTotalEventDataEvaluationCycles(this.bean.getTotalEventDataEvaluationCycles());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalEventDataWatchEvaluations")) && this.bean.isTotalEventDataWatchEvaluationsSet()) {
               copy.setTotalEventDataWatchEvaluations(this.bean.getTotalEventDataWatchEvaluations());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalEventDataWatchesTriggered")) && this.bean.isTotalEventDataWatchesTriggeredSet()) {
               copy.setTotalEventDataWatchesTriggered(this.bean.getTotalEventDataWatchesTriggered());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalFailedNotifications")) && this.bean.isTotalFailedNotificationsSet()) {
               copy.setTotalFailedNotifications(this.bean.getTotalFailedNotifications());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalHarvesterEvaluationCycles")) && this.bean.isTotalHarvesterEvaluationCyclesSet()) {
               copy.setTotalHarvesterEvaluationCycles(this.bean.getTotalHarvesterEvaluationCycles());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalHarvesterWatchEvaluations")) && this.bean.isTotalHarvesterWatchEvaluationsSet()) {
               copy.setTotalHarvesterWatchEvaluations(this.bean.getTotalHarvesterWatchEvaluations());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalHarvesterWatchesTriggered")) && this.bean.isTotalHarvesterWatchesTriggeredSet()) {
               copy.setTotalHarvesterWatchesTriggered(this.bean.getTotalHarvesterWatchesTriggered());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalLogEvaluationCycles")) && this.bean.isTotalLogEvaluationCyclesSet()) {
               copy.setTotalLogEvaluationCycles(this.bean.getTotalLogEvaluationCycles());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalLogWatchEvaluations")) && this.bean.isTotalLogWatchEvaluationsSet()) {
               copy.setTotalLogWatchEvaluations(this.bean.getTotalLogWatchEvaluations());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalLogWatchesTriggered")) && this.bean.isTotalLogWatchesTriggeredSet()) {
               copy.setTotalLogWatchesTriggered(this.bean.getTotalLogWatchesTriggered());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalNotificationsPerformed")) && this.bean.isTotalNotificationsPerformedSet()) {
               copy.setTotalNotificationsPerformed(this.bean.getTotalNotificationsPerformed());
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
