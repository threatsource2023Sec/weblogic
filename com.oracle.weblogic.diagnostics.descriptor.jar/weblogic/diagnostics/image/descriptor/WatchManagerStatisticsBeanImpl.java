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

public class WatchManagerStatisticsBeanImpl extends AbstractDescriptorBean implements WatchManagerStatisticsBean, Serializable {
   private long _AverageEventDataWatchEvaluationTime;
   private long _AverageHarvesterWatchEvaluationTime;
   private long _AverageLogWatchEvaluationTime;
   private long _MaximumEventDataWatchEvaluationTime;
   private long _MaximumHarvesterWatchEvaluationTime;
   private long _MaximumLogWatchEvaluationTime;
   private long _MinimumEventDataWatchEvaluationTime;
   private long _MinimumHarvesterWatchEvaluationTime;
   private long _MinimumLogWatchEvaluationTime;
   private long _TotalDIMGNotificationsPerformed;
   private long _TotalEventDataEvaluationCycles;
   private long _TotalEventDataWatchEvaluations;
   private long _TotalEventDataWatchesTriggered;
   private long _TotalFailedDIMGNotifications;
   private long _TotalFailedJMSNotifications;
   private long _TotalFailedJMXNotifications;
   private long _TotalFailedNotifications;
   private long _TotalFailedSMTPNotifications;
   private long _TotalFailedSNMPNotifications;
   private long _TotalHarvesterEvaluationCycles;
   private long _TotalHarvesterWatchEvaluations;
   private long _TotalHarvesterWatchesTriggered;
   private long _TotalJMSNotificationsPerformed;
   private long _TotalJMXNotificationsPerformed;
   private long _TotalLogEvaluationCycles;
   private long _TotalLogWatchEvaluations;
   private long _TotalLogWatchesTriggered;
   private long _TotalNotificationsPerformed;
   private long _TotalSMTPNotificationsPerformed;
   private long _TotalSNMPNotificationsPerformed;
   private static SchemaHelper2 _schemaHelper;

   public WatchManagerStatisticsBeanImpl() {
      this._initializeProperty(-1);
   }

   public WatchManagerStatisticsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WatchManagerStatisticsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public long getTotalHarvesterEvaluationCycles() {
      return this._TotalHarvesterEvaluationCycles;
   }

   public boolean isTotalHarvesterEvaluationCyclesInherited() {
      return false;
   }

   public boolean isTotalHarvesterEvaluationCyclesSet() {
      return this._isSet(0);
   }

   public void setTotalHarvesterEvaluationCycles(long param0) {
      long _oldVal = this._TotalHarvesterEvaluationCycles;
      this._TotalHarvesterEvaluationCycles = param0;
      this._postSet(0, _oldVal, param0);
   }

   public long getTotalHarvesterWatchEvaluations() {
      return this._TotalHarvesterWatchEvaluations;
   }

   public boolean isTotalHarvesterWatchEvaluationsInherited() {
      return false;
   }

   public boolean isTotalHarvesterWatchEvaluationsSet() {
      return this._isSet(1);
   }

   public void setTotalHarvesterWatchEvaluations(long param0) {
      long _oldVal = this._TotalHarvesterWatchEvaluations;
      this._TotalHarvesterWatchEvaluations = param0;
      this._postSet(1, _oldVal, param0);
   }

   public long getTotalHarvesterWatchesTriggered() {
      return this._TotalHarvesterWatchesTriggered;
   }

   public boolean isTotalHarvesterWatchesTriggeredInherited() {
      return false;
   }

   public boolean isTotalHarvesterWatchesTriggeredSet() {
      return this._isSet(2);
   }

   public void setTotalHarvesterWatchesTriggered(long param0) {
      long _oldVal = this._TotalHarvesterWatchesTriggered;
      this._TotalHarvesterWatchesTriggered = param0;
      this._postSet(2, _oldVal, param0);
   }

   public long getAverageHarvesterWatchEvaluationTime() {
      return this._AverageHarvesterWatchEvaluationTime;
   }

   public boolean isAverageHarvesterWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isAverageHarvesterWatchEvaluationTimeSet() {
      return this._isSet(3);
   }

   public void setAverageHarvesterWatchEvaluationTime(long param0) {
      long _oldVal = this._AverageHarvesterWatchEvaluationTime;
      this._AverageHarvesterWatchEvaluationTime = param0;
      this._postSet(3, _oldVal, param0);
   }

   public long getTotalLogEvaluationCycles() {
      return this._TotalLogEvaluationCycles;
   }

   public boolean isTotalLogEvaluationCyclesInherited() {
      return false;
   }

   public boolean isTotalLogEvaluationCyclesSet() {
      return this._isSet(4);
   }

   public void setTotalLogEvaluationCycles(long param0) {
      long _oldVal = this._TotalLogEvaluationCycles;
      this._TotalLogEvaluationCycles = param0;
      this._postSet(4, _oldVal, param0);
   }

   public long getTotalLogWatchEvaluations() {
      return this._TotalLogWatchEvaluations;
   }

   public boolean isTotalLogWatchEvaluationsInherited() {
      return false;
   }

   public boolean isTotalLogWatchEvaluationsSet() {
      return this._isSet(5);
   }

   public void setTotalLogWatchEvaluations(long param0) {
      long _oldVal = this._TotalLogWatchEvaluations;
      this._TotalLogWatchEvaluations = param0;
      this._postSet(5, _oldVal, param0);
   }

   public long getTotalLogWatchesTriggered() {
      return this._TotalLogWatchesTriggered;
   }

   public boolean isTotalLogWatchesTriggeredInherited() {
      return false;
   }

   public boolean isTotalLogWatchesTriggeredSet() {
      return this._isSet(6);
   }

   public void setTotalLogWatchesTriggered(long param0) {
      long _oldVal = this._TotalLogWatchesTriggered;
      this._TotalLogWatchesTriggered = param0;
      this._postSet(6, _oldVal, param0);
   }

   public long getAverageLogWatchEvaluationTime() {
      return this._AverageLogWatchEvaluationTime;
   }

   public boolean isAverageLogWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isAverageLogWatchEvaluationTimeSet() {
      return this._isSet(7);
   }

   public void setAverageLogWatchEvaluationTime(long param0) {
      long _oldVal = this._AverageLogWatchEvaluationTime;
      this._AverageLogWatchEvaluationTime = param0;
      this._postSet(7, _oldVal, param0);
   }

   public long getTotalEventDataEvaluationCycles() {
      return this._TotalEventDataEvaluationCycles;
   }

   public boolean isTotalEventDataEvaluationCyclesInherited() {
      return false;
   }

   public boolean isTotalEventDataEvaluationCyclesSet() {
      return this._isSet(8);
   }

   public void setTotalEventDataEvaluationCycles(long param0) {
      long _oldVal = this._TotalEventDataEvaluationCycles;
      this._TotalEventDataEvaluationCycles = param0;
      this._postSet(8, _oldVal, param0);
   }

   public long getTotalEventDataWatchEvaluations() {
      return this._TotalEventDataWatchEvaluations;
   }

   public boolean isTotalEventDataWatchEvaluationsInherited() {
      return false;
   }

   public boolean isTotalEventDataWatchEvaluationsSet() {
      return this._isSet(9);
   }

   public void setTotalEventDataWatchEvaluations(long param0) {
      long _oldVal = this._TotalEventDataWatchEvaluations;
      this._TotalEventDataWatchEvaluations = param0;
      this._postSet(9, _oldVal, param0);
   }

   public long getTotalEventDataWatchesTriggered() {
      return this._TotalEventDataWatchesTriggered;
   }

   public boolean isTotalEventDataWatchesTriggeredInherited() {
      return false;
   }

   public boolean isTotalEventDataWatchesTriggeredSet() {
      return this._isSet(10);
   }

   public void setTotalEventDataWatchesTriggered(long param0) {
      long _oldVal = this._TotalEventDataWatchesTriggered;
      this._TotalEventDataWatchesTriggered = param0;
      this._postSet(10, _oldVal, param0);
   }

   public long getAverageEventDataWatchEvaluationTime() {
      return this._AverageEventDataWatchEvaluationTime;
   }

   public boolean isAverageEventDataWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isAverageEventDataWatchEvaluationTimeSet() {
      return this._isSet(11);
   }

   public void setAverageEventDataWatchEvaluationTime(long param0) {
      long _oldVal = this._AverageEventDataWatchEvaluationTime;
      this._AverageEventDataWatchEvaluationTime = param0;
      this._postSet(11, _oldVal, param0);
   }

   public long getTotalNotificationsPerformed() {
      return this._TotalNotificationsPerformed;
   }

   public boolean isTotalNotificationsPerformedInherited() {
      return false;
   }

   public boolean isTotalNotificationsPerformedSet() {
      return this._isSet(12);
   }

   public void setTotalNotificationsPerformed(long param0) {
      long _oldVal = this._TotalNotificationsPerformed;
      this._TotalNotificationsPerformed = param0;
      this._postSet(12, _oldVal, param0);
   }

   public long getTotalFailedNotifications() {
      return this._TotalFailedNotifications;
   }

   public boolean isTotalFailedNotificationsInherited() {
      return false;
   }

   public boolean isTotalFailedNotificationsSet() {
      return this._isSet(13);
   }

   public void setTotalFailedNotifications(long param0) {
      long _oldVal = this._TotalFailedNotifications;
      this._TotalFailedNotifications = param0;
      this._postSet(13, _oldVal, param0);
   }

   public long getMinimumHarvesterWatchEvaluationTime() {
      return this._MinimumHarvesterWatchEvaluationTime;
   }

   public boolean isMinimumHarvesterWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMinimumHarvesterWatchEvaluationTimeSet() {
      return this._isSet(14);
   }

   public void setMinimumHarvesterWatchEvaluationTime(long param0) {
      long _oldVal = this._MinimumHarvesterWatchEvaluationTime;
      this._MinimumHarvesterWatchEvaluationTime = param0;
      this._postSet(14, _oldVal, param0);
   }

   public long getMaximumHarvesterWatchEvaluationTime() {
      return this._MaximumHarvesterWatchEvaluationTime;
   }

   public boolean isMaximumHarvesterWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMaximumHarvesterWatchEvaluationTimeSet() {
      return this._isSet(15);
   }

   public void setMaximumHarvesterWatchEvaluationTime(long param0) {
      long _oldVal = this._MaximumHarvesterWatchEvaluationTime;
      this._MaximumHarvesterWatchEvaluationTime = param0;
      this._postSet(15, _oldVal, param0);
   }

   public long getMinimumLogWatchEvaluationTime() {
      return this._MinimumLogWatchEvaluationTime;
   }

   public boolean isMinimumLogWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMinimumLogWatchEvaluationTimeSet() {
      return this._isSet(16);
   }

   public void setMinimumLogWatchEvaluationTime(long param0) {
      long _oldVal = this._MinimumLogWatchEvaluationTime;
      this._MinimumLogWatchEvaluationTime = param0;
      this._postSet(16, _oldVal, param0);
   }

   public long getMaximumLogWatchEvaluationTime() {
      return this._MaximumLogWatchEvaluationTime;
   }

   public boolean isMaximumLogWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMaximumLogWatchEvaluationTimeSet() {
      return this._isSet(17);
   }

   public void setMaximumLogWatchEvaluationTime(long param0) {
      long _oldVal = this._MaximumLogWatchEvaluationTime;
      this._MaximumLogWatchEvaluationTime = param0;
      this._postSet(17, _oldVal, param0);
   }

   public long getMinimumEventDataWatchEvaluationTime() {
      return this._MinimumEventDataWatchEvaluationTime;
   }

   public boolean isMinimumEventDataWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMinimumEventDataWatchEvaluationTimeSet() {
      return this._isSet(18);
   }

   public void setMinimumEventDataWatchEvaluationTime(long param0) {
      long _oldVal = this._MinimumEventDataWatchEvaluationTime;
      this._MinimumEventDataWatchEvaluationTime = param0;
      this._postSet(18, _oldVal, param0);
   }

   public long getMaximumEventDataWatchEvaluationTime() {
      return this._MaximumEventDataWatchEvaluationTime;
   }

   public boolean isMaximumEventDataWatchEvaluationTimeInherited() {
      return false;
   }

   public boolean isMaximumEventDataWatchEvaluationTimeSet() {
      return this._isSet(19);
   }

   public void setMaximumEventDataWatchEvaluationTime(long param0) {
      long _oldVal = this._MaximumEventDataWatchEvaluationTime;
      this._MaximumEventDataWatchEvaluationTime = param0;
      this._postSet(19, _oldVal, param0);
   }

   public long getTotalDIMGNotificationsPerformed() {
      return this._TotalDIMGNotificationsPerformed;
   }

   public boolean isTotalDIMGNotificationsPerformedInherited() {
      return false;
   }

   public boolean isTotalDIMGNotificationsPerformedSet() {
      return this._isSet(20);
   }

   public void setTotalDIMGNotificationsPerformed(long param0) {
      long _oldVal = this._TotalDIMGNotificationsPerformed;
      this._TotalDIMGNotificationsPerformed = param0;
      this._postSet(20, _oldVal, param0);
   }

   public long getTotalFailedDIMGNotifications() {
      return this._TotalFailedDIMGNotifications;
   }

   public boolean isTotalFailedDIMGNotificationsInherited() {
      return false;
   }

   public boolean isTotalFailedDIMGNotificationsSet() {
      return this._isSet(21);
   }

   public void setTotalFailedDIMGNotifications(long param0) {
      long _oldVal = this._TotalFailedDIMGNotifications;
      this._TotalFailedDIMGNotifications = param0;
      this._postSet(21, _oldVal, param0);
   }

   public long getTotalJMXNotificationsPerformed() {
      return this._TotalJMXNotificationsPerformed;
   }

   public boolean isTotalJMXNotificationsPerformedInherited() {
      return false;
   }

   public boolean isTotalJMXNotificationsPerformedSet() {
      return this._isSet(22);
   }

   public void setTotalJMXNotificationsPerformed(long param0) {
      long _oldVal = this._TotalJMXNotificationsPerformed;
      this._TotalJMXNotificationsPerformed = param0;
      this._postSet(22, _oldVal, param0);
   }

   public long getTotalFailedJMXNotifications() {
      return this._TotalFailedJMXNotifications;
   }

   public boolean isTotalFailedJMXNotificationsInherited() {
      return false;
   }

   public boolean isTotalFailedJMXNotificationsSet() {
      return this._isSet(23);
   }

   public void setTotalFailedJMXNotifications(long param0) {
      long _oldVal = this._TotalFailedJMXNotifications;
      this._TotalFailedJMXNotifications = param0;
      this._postSet(23, _oldVal, param0);
   }

   public long getTotalSMTPNotificationsPerformed() {
      return this._TotalSMTPNotificationsPerformed;
   }

   public boolean isTotalSMTPNotificationsPerformedInherited() {
      return false;
   }

   public boolean isTotalSMTPNotificationsPerformedSet() {
      return this._isSet(24);
   }

   public void setTotalSMTPNotificationsPerformed(long param0) {
      long _oldVal = this._TotalSMTPNotificationsPerformed;
      this._TotalSMTPNotificationsPerformed = param0;
      this._postSet(24, _oldVal, param0);
   }

   public long getTotalFailedSMTPNotifications() {
      return this._TotalFailedSMTPNotifications;
   }

   public boolean isTotalFailedSMTPNotificationsInherited() {
      return false;
   }

   public boolean isTotalFailedSMTPNotificationsSet() {
      return this._isSet(25);
   }

   public void setTotalFailedSMTPNotifications(long param0) {
      long _oldVal = this._TotalFailedSMTPNotifications;
      this._TotalFailedSMTPNotifications = param0;
      this._postSet(25, _oldVal, param0);
   }

   public long getTotalSNMPNotificationsPerformed() {
      return this._TotalSNMPNotificationsPerformed;
   }

   public boolean isTotalSNMPNotificationsPerformedInherited() {
      return false;
   }

   public boolean isTotalSNMPNotificationsPerformedSet() {
      return this._isSet(26);
   }

   public void setTotalSNMPNotificationsPerformed(long param0) {
      long _oldVal = this._TotalSNMPNotificationsPerformed;
      this._TotalSNMPNotificationsPerformed = param0;
      this._postSet(26, _oldVal, param0);
   }

   public long getTotalFailedSNMPNotifications() {
      return this._TotalFailedSNMPNotifications;
   }

   public boolean isTotalFailedSNMPNotificationsInherited() {
      return false;
   }

   public boolean isTotalFailedSNMPNotificationsSet() {
      return this._isSet(27);
   }

   public void setTotalFailedSNMPNotifications(long param0) {
      long _oldVal = this._TotalFailedSNMPNotifications;
      this._TotalFailedSNMPNotifications = param0;
      this._postSet(27, _oldVal, param0);
   }

   public long getTotalJMSNotificationsPerformed() {
      return this._TotalJMSNotificationsPerformed;
   }

   public boolean isTotalJMSNotificationsPerformedInherited() {
      return false;
   }

   public boolean isTotalJMSNotificationsPerformedSet() {
      return this._isSet(28);
   }

   public void setTotalJMSNotificationsPerformed(long param0) {
      long _oldVal = this._TotalJMSNotificationsPerformed;
      this._TotalJMSNotificationsPerformed = param0;
      this._postSet(28, _oldVal, param0);
   }

   public long getTotalFailedJMSNotifications() {
      return this._TotalFailedJMSNotifications;
   }

   public boolean isTotalFailedJMSNotificationsInherited() {
      return false;
   }

   public boolean isTotalFailedJMSNotificationsSet() {
      return this._isSet(29);
   }

   public void setTotalFailedJMSNotifications(long param0) {
      long _oldVal = this._TotalFailedJMSNotifications;
      this._TotalFailedJMSNotifications = param0;
      this._postSet(29, _oldVal, param0);
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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._AverageEventDataWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 3:
               this._AverageHarvesterWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 7:
               this._AverageLogWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 19:
               this._MaximumEventDataWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 15:
               this._MaximumHarvesterWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 17:
               this._MaximumLogWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 18:
               this._MinimumEventDataWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 14:
               this._MinimumHarvesterWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 16:
               this._MinimumLogWatchEvaluationTime = 0L;
               if (initOne) {
                  break;
               }
            case 20:
               this._TotalDIMGNotificationsPerformed = 0L;
               if (initOne) {
                  break;
               }
            case 8:
               this._TotalEventDataEvaluationCycles = 0L;
               if (initOne) {
                  break;
               }
            case 9:
               this._TotalEventDataWatchEvaluations = 0L;
               if (initOne) {
                  break;
               }
            case 10:
               this._TotalEventDataWatchesTriggered = 0L;
               if (initOne) {
                  break;
               }
            case 21:
               this._TotalFailedDIMGNotifications = 0L;
               if (initOne) {
                  break;
               }
            case 29:
               this._TotalFailedJMSNotifications = 0L;
               if (initOne) {
                  break;
               }
            case 23:
               this._TotalFailedJMXNotifications = 0L;
               if (initOne) {
                  break;
               }
            case 13:
               this._TotalFailedNotifications = 0L;
               if (initOne) {
                  break;
               }
            case 25:
               this._TotalFailedSMTPNotifications = 0L;
               if (initOne) {
                  break;
               }
            case 27:
               this._TotalFailedSNMPNotifications = 0L;
               if (initOne) {
                  break;
               }
            case 0:
               this._TotalHarvesterEvaluationCycles = 0L;
               if (initOne) {
                  break;
               }
            case 1:
               this._TotalHarvesterWatchEvaluations = 0L;
               if (initOne) {
                  break;
               }
            case 2:
               this._TotalHarvesterWatchesTriggered = 0L;
               if (initOne) {
                  break;
               }
            case 28:
               this._TotalJMSNotificationsPerformed = 0L;
               if (initOne) {
                  break;
               }
            case 22:
               this._TotalJMXNotificationsPerformed = 0L;
               if (initOne) {
                  break;
               }
            case 4:
               this._TotalLogEvaluationCycles = 0L;
               if (initOne) {
                  break;
               }
            case 5:
               this._TotalLogWatchEvaluations = 0L;
               if (initOne) {
                  break;
               }
            case 6:
               this._TotalLogWatchesTriggered = 0L;
               if (initOne) {
                  break;
               }
            case 12:
               this._TotalNotificationsPerformed = 0L;
               if (initOne) {
                  break;
               }
            case 24:
               this._TotalSMTPNotificationsPerformed = 0L;
               if (initOne) {
                  break;
               }
            case 26:
               this._TotalSNMPNotificationsPerformed = 0L;
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
            case 26:
               if (s.equals("total-failed-notifications")) {
                  return 13;
               }
               break;
            case 27:
               if (s.equals("total-log-evaluation-cycles")) {
                  return 4;
               }

               if (s.equals("total-log-watch-evaluations")) {
                  return 5;
               }

               if (s.equals("total-log-watches-triggered")) {
                  return 6;
               }
            case 28:
            case 31:
            case 35:
            case 36:
            case 37:
            case 38:
            default:
               break;
            case 29:
               if (s.equals("total-failedjmx-notifications")) {
                  return 23;
               }

               if (s.equals("total-notifications-performed")) {
                  return 12;
               }
               break;
            case 30:
               if (s.equals("total-faileddimg-notifications")) {
                  return 21;
               }

               if (s.equals("total-failed-jms-notifications")) {
                  return 29;
               }

               if (s.equals("total-failedsmtp-notifications")) {
                  return 25;
               }

               if (s.equals("total-failedsnmp-notifications")) {
                  return 27;
               }
               break;
            case 32:
               if (s.equals("totaljmx-notifications-performed")) {
                  return 22;
               }
               break;
            case 33:
               if (s.equals("average-log-watch-evaluation-time")) {
                  return 7;
               }

               if (s.equals("maximum-log-watch-evaluation-time")) {
                  return 17;
               }

               if (s.equals("minimum-log-watch-evaluation-time")) {
                  return 16;
               }

               if (s.equals("totaldimg-notifications-performed")) {
                  return 20;
               }

               if (s.equals("total-harvester-evaluation-cycles")) {
                  return 0;
               }

               if (s.equals("total-harvester-watch-evaluations")) {
                  return 1;
               }

               if (s.equals("total-harvester-watches-triggered")) {
                  return 2;
               }

               if (s.equals("total-jms-notifications-performed")) {
                  return 28;
               }

               if (s.equals("totalsmtp-notifications-performed")) {
                  return 24;
               }

               if (s.equals("totalsnmp-notifications-performed")) {
                  return 26;
               }
               break;
            case 34:
               if (s.equals("total-event-data-evaluation-cycles")) {
                  return 8;
               }

               if (s.equals("total-event-data-watch-evaluations")) {
                  return 9;
               }

               if (s.equals("total-event-data-watches-triggered")) {
                  return 10;
               }
               break;
            case 39:
               if (s.equals("average-harvester-watch-evaluation-time")) {
                  return 3;
               }

               if (s.equals("maximum-harvester-watch-evaluation-time")) {
                  return 15;
               }

               if (s.equals("minimum-harvester-watch-evaluation-time")) {
                  return 14;
               }
               break;
            case 40:
               if (s.equals("average-event-data-watch-evaluation-time")) {
                  return 11;
               }

               if (s.equals("maximum-event-data-watch-evaluation-time")) {
                  return 19;
               }

               if (s.equals("minimum-event-data-watch-evaluation-time")) {
                  return 18;
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
               return "total-harvester-evaluation-cycles";
            case 1:
               return "total-harvester-watch-evaluations";
            case 2:
               return "total-harvester-watches-triggered";
            case 3:
               return "average-harvester-watch-evaluation-time";
            case 4:
               return "total-log-evaluation-cycles";
            case 5:
               return "total-log-watch-evaluations";
            case 6:
               return "total-log-watches-triggered";
            case 7:
               return "average-log-watch-evaluation-time";
            case 8:
               return "total-event-data-evaluation-cycles";
            case 9:
               return "total-event-data-watch-evaluations";
            case 10:
               return "total-event-data-watches-triggered";
            case 11:
               return "average-event-data-watch-evaluation-time";
            case 12:
               return "total-notifications-performed";
            case 13:
               return "total-failed-notifications";
            case 14:
               return "minimum-harvester-watch-evaluation-time";
            case 15:
               return "maximum-harvester-watch-evaluation-time";
            case 16:
               return "minimum-log-watch-evaluation-time";
            case 17:
               return "maximum-log-watch-evaluation-time";
            case 18:
               return "minimum-event-data-watch-evaluation-time";
            case 19:
               return "maximum-event-data-watch-evaluation-time";
            case 20:
               return "totaldimg-notifications-performed";
            case 21:
               return "total-faileddimg-notifications";
            case 22:
               return "totaljmx-notifications-performed";
            case 23:
               return "total-failedjmx-notifications";
            case 24:
               return "totalsmtp-notifications-performed";
            case 25:
               return "total-failedsmtp-notifications";
            case 26:
               return "totalsnmp-notifications-performed";
            case 27:
               return "total-failedsnmp-notifications";
            case 28:
               return "total-jms-notifications-performed";
            case 29:
               return "total-failed-jms-notifications";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WatchManagerStatisticsBeanImpl bean;

      protected Helper(WatchManagerStatisticsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TotalHarvesterEvaluationCycles";
            case 1:
               return "TotalHarvesterWatchEvaluations";
            case 2:
               return "TotalHarvesterWatchesTriggered";
            case 3:
               return "AverageHarvesterWatchEvaluationTime";
            case 4:
               return "TotalLogEvaluationCycles";
            case 5:
               return "TotalLogWatchEvaluations";
            case 6:
               return "TotalLogWatchesTriggered";
            case 7:
               return "AverageLogWatchEvaluationTime";
            case 8:
               return "TotalEventDataEvaluationCycles";
            case 9:
               return "TotalEventDataWatchEvaluations";
            case 10:
               return "TotalEventDataWatchesTriggered";
            case 11:
               return "AverageEventDataWatchEvaluationTime";
            case 12:
               return "TotalNotificationsPerformed";
            case 13:
               return "TotalFailedNotifications";
            case 14:
               return "MinimumHarvesterWatchEvaluationTime";
            case 15:
               return "MaximumHarvesterWatchEvaluationTime";
            case 16:
               return "MinimumLogWatchEvaluationTime";
            case 17:
               return "MaximumLogWatchEvaluationTime";
            case 18:
               return "MinimumEventDataWatchEvaluationTime";
            case 19:
               return "MaximumEventDataWatchEvaluationTime";
            case 20:
               return "TotalDIMGNotificationsPerformed";
            case 21:
               return "TotalFailedDIMGNotifications";
            case 22:
               return "TotalJMXNotificationsPerformed";
            case 23:
               return "TotalFailedJMXNotifications";
            case 24:
               return "TotalSMTPNotificationsPerformed";
            case 25:
               return "TotalFailedSMTPNotifications";
            case 26:
               return "TotalSNMPNotificationsPerformed";
            case 27:
               return "TotalFailedSNMPNotifications";
            case 28:
               return "TotalJMSNotificationsPerformed";
            case 29:
               return "TotalFailedJMSNotifications";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AverageEventDataWatchEvaluationTime")) {
            return 11;
         } else if (propName.equals("AverageHarvesterWatchEvaluationTime")) {
            return 3;
         } else if (propName.equals("AverageLogWatchEvaluationTime")) {
            return 7;
         } else if (propName.equals("MaximumEventDataWatchEvaluationTime")) {
            return 19;
         } else if (propName.equals("MaximumHarvesterWatchEvaluationTime")) {
            return 15;
         } else if (propName.equals("MaximumLogWatchEvaluationTime")) {
            return 17;
         } else if (propName.equals("MinimumEventDataWatchEvaluationTime")) {
            return 18;
         } else if (propName.equals("MinimumHarvesterWatchEvaluationTime")) {
            return 14;
         } else if (propName.equals("MinimumLogWatchEvaluationTime")) {
            return 16;
         } else if (propName.equals("TotalDIMGNotificationsPerformed")) {
            return 20;
         } else if (propName.equals("TotalEventDataEvaluationCycles")) {
            return 8;
         } else if (propName.equals("TotalEventDataWatchEvaluations")) {
            return 9;
         } else if (propName.equals("TotalEventDataWatchesTriggered")) {
            return 10;
         } else if (propName.equals("TotalFailedDIMGNotifications")) {
            return 21;
         } else if (propName.equals("TotalFailedJMSNotifications")) {
            return 29;
         } else if (propName.equals("TotalFailedJMXNotifications")) {
            return 23;
         } else if (propName.equals("TotalFailedNotifications")) {
            return 13;
         } else if (propName.equals("TotalFailedSMTPNotifications")) {
            return 25;
         } else if (propName.equals("TotalFailedSNMPNotifications")) {
            return 27;
         } else if (propName.equals("TotalHarvesterEvaluationCycles")) {
            return 0;
         } else if (propName.equals("TotalHarvesterWatchEvaluations")) {
            return 1;
         } else if (propName.equals("TotalHarvesterWatchesTriggered")) {
            return 2;
         } else if (propName.equals("TotalJMSNotificationsPerformed")) {
            return 28;
         } else if (propName.equals("TotalJMXNotificationsPerformed")) {
            return 22;
         } else if (propName.equals("TotalLogEvaluationCycles")) {
            return 4;
         } else if (propName.equals("TotalLogWatchEvaluations")) {
            return 5;
         } else if (propName.equals("TotalLogWatchesTriggered")) {
            return 6;
         } else if (propName.equals("TotalNotificationsPerformed")) {
            return 12;
         } else if (propName.equals("TotalSMTPNotificationsPerformed")) {
            return 24;
         } else {
            return propName.equals("TotalSNMPNotificationsPerformed") ? 26 : super.getPropertyIndex(propName);
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

            if (this.bean.isTotalDIMGNotificationsPerformedSet()) {
               buf.append("TotalDIMGNotificationsPerformed");
               buf.append(String.valueOf(this.bean.getTotalDIMGNotificationsPerformed()));
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

            if (this.bean.isTotalFailedDIMGNotificationsSet()) {
               buf.append("TotalFailedDIMGNotifications");
               buf.append(String.valueOf(this.bean.getTotalFailedDIMGNotifications()));
            }

            if (this.bean.isTotalFailedJMSNotificationsSet()) {
               buf.append("TotalFailedJMSNotifications");
               buf.append(String.valueOf(this.bean.getTotalFailedJMSNotifications()));
            }

            if (this.bean.isTotalFailedJMXNotificationsSet()) {
               buf.append("TotalFailedJMXNotifications");
               buf.append(String.valueOf(this.bean.getTotalFailedJMXNotifications()));
            }

            if (this.bean.isTotalFailedNotificationsSet()) {
               buf.append("TotalFailedNotifications");
               buf.append(String.valueOf(this.bean.getTotalFailedNotifications()));
            }

            if (this.bean.isTotalFailedSMTPNotificationsSet()) {
               buf.append("TotalFailedSMTPNotifications");
               buf.append(String.valueOf(this.bean.getTotalFailedSMTPNotifications()));
            }

            if (this.bean.isTotalFailedSNMPNotificationsSet()) {
               buf.append("TotalFailedSNMPNotifications");
               buf.append(String.valueOf(this.bean.getTotalFailedSNMPNotifications()));
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

            if (this.bean.isTotalJMSNotificationsPerformedSet()) {
               buf.append("TotalJMSNotificationsPerformed");
               buf.append(String.valueOf(this.bean.getTotalJMSNotificationsPerformed()));
            }

            if (this.bean.isTotalJMXNotificationsPerformedSet()) {
               buf.append("TotalJMXNotificationsPerformed");
               buf.append(String.valueOf(this.bean.getTotalJMXNotificationsPerformed()));
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

            if (this.bean.isTotalSMTPNotificationsPerformedSet()) {
               buf.append("TotalSMTPNotificationsPerformed");
               buf.append(String.valueOf(this.bean.getTotalSMTPNotificationsPerformed()));
            }

            if (this.bean.isTotalSNMPNotificationsPerformedSet()) {
               buf.append("TotalSNMPNotificationsPerformed");
               buf.append(String.valueOf(this.bean.getTotalSNMPNotificationsPerformed()));
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
            WatchManagerStatisticsBeanImpl otherTyped = (WatchManagerStatisticsBeanImpl)other;
            this.computeDiff("AverageEventDataWatchEvaluationTime", this.bean.getAverageEventDataWatchEvaluationTime(), otherTyped.getAverageEventDataWatchEvaluationTime(), false);
            this.computeDiff("AverageHarvesterWatchEvaluationTime", this.bean.getAverageHarvesterWatchEvaluationTime(), otherTyped.getAverageHarvesterWatchEvaluationTime(), false);
            this.computeDiff("AverageLogWatchEvaluationTime", this.bean.getAverageLogWatchEvaluationTime(), otherTyped.getAverageLogWatchEvaluationTime(), false);
            this.computeDiff("MaximumEventDataWatchEvaluationTime", this.bean.getMaximumEventDataWatchEvaluationTime(), otherTyped.getMaximumEventDataWatchEvaluationTime(), false);
            this.computeDiff("MaximumHarvesterWatchEvaluationTime", this.bean.getMaximumHarvesterWatchEvaluationTime(), otherTyped.getMaximumHarvesterWatchEvaluationTime(), false);
            this.computeDiff("MaximumLogWatchEvaluationTime", this.bean.getMaximumLogWatchEvaluationTime(), otherTyped.getMaximumLogWatchEvaluationTime(), false);
            this.computeDiff("MinimumEventDataWatchEvaluationTime", this.bean.getMinimumEventDataWatchEvaluationTime(), otherTyped.getMinimumEventDataWatchEvaluationTime(), false);
            this.computeDiff("MinimumHarvesterWatchEvaluationTime", this.bean.getMinimumHarvesterWatchEvaluationTime(), otherTyped.getMinimumHarvesterWatchEvaluationTime(), false);
            this.computeDiff("MinimumLogWatchEvaluationTime", this.bean.getMinimumLogWatchEvaluationTime(), otherTyped.getMinimumLogWatchEvaluationTime(), false);
            this.computeDiff("TotalDIMGNotificationsPerformed", this.bean.getTotalDIMGNotificationsPerformed(), otherTyped.getTotalDIMGNotificationsPerformed(), false);
            this.computeDiff("TotalEventDataEvaluationCycles", this.bean.getTotalEventDataEvaluationCycles(), otherTyped.getTotalEventDataEvaluationCycles(), false);
            this.computeDiff("TotalEventDataWatchEvaluations", this.bean.getTotalEventDataWatchEvaluations(), otherTyped.getTotalEventDataWatchEvaluations(), false);
            this.computeDiff("TotalEventDataWatchesTriggered", this.bean.getTotalEventDataWatchesTriggered(), otherTyped.getTotalEventDataWatchesTriggered(), false);
            this.computeDiff("TotalFailedDIMGNotifications", this.bean.getTotalFailedDIMGNotifications(), otherTyped.getTotalFailedDIMGNotifications(), false);
            this.computeDiff("TotalFailedJMSNotifications", this.bean.getTotalFailedJMSNotifications(), otherTyped.getTotalFailedJMSNotifications(), false);
            this.computeDiff("TotalFailedJMXNotifications", this.bean.getTotalFailedJMXNotifications(), otherTyped.getTotalFailedJMXNotifications(), false);
            this.computeDiff("TotalFailedNotifications", this.bean.getTotalFailedNotifications(), otherTyped.getTotalFailedNotifications(), false);
            this.computeDiff("TotalFailedSMTPNotifications", this.bean.getTotalFailedSMTPNotifications(), otherTyped.getTotalFailedSMTPNotifications(), false);
            this.computeDiff("TotalFailedSNMPNotifications", this.bean.getTotalFailedSNMPNotifications(), otherTyped.getTotalFailedSNMPNotifications(), false);
            this.computeDiff("TotalHarvesterEvaluationCycles", this.bean.getTotalHarvesterEvaluationCycles(), otherTyped.getTotalHarvesterEvaluationCycles(), false);
            this.computeDiff("TotalHarvesterWatchEvaluations", this.bean.getTotalHarvesterWatchEvaluations(), otherTyped.getTotalHarvesterWatchEvaluations(), false);
            this.computeDiff("TotalHarvesterWatchesTriggered", this.bean.getTotalHarvesterWatchesTriggered(), otherTyped.getTotalHarvesterWatchesTriggered(), false);
            this.computeDiff("TotalJMSNotificationsPerformed", this.bean.getTotalJMSNotificationsPerformed(), otherTyped.getTotalJMSNotificationsPerformed(), false);
            this.computeDiff("TotalJMXNotificationsPerformed", this.bean.getTotalJMXNotificationsPerformed(), otherTyped.getTotalJMXNotificationsPerformed(), false);
            this.computeDiff("TotalLogEvaluationCycles", this.bean.getTotalLogEvaluationCycles(), otherTyped.getTotalLogEvaluationCycles(), false);
            this.computeDiff("TotalLogWatchEvaluations", this.bean.getTotalLogWatchEvaluations(), otherTyped.getTotalLogWatchEvaluations(), false);
            this.computeDiff("TotalLogWatchesTriggered", this.bean.getTotalLogWatchesTriggered(), otherTyped.getTotalLogWatchesTriggered(), false);
            this.computeDiff("TotalNotificationsPerformed", this.bean.getTotalNotificationsPerformed(), otherTyped.getTotalNotificationsPerformed(), false);
            this.computeDiff("TotalSMTPNotificationsPerformed", this.bean.getTotalSMTPNotificationsPerformed(), otherTyped.getTotalSMTPNotificationsPerformed(), false);
            this.computeDiff("TotalSNMPNotificationsPerformed", this.bean.getTotalSNMPNotificationsPerformed(), otherTyped.getTotalSNMPNotificationsPerformed(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WatchManagerStatisticsBeanImpl original = (WatchManagerStatisticsBeanImpl)event.getSourceBean();
            WatchManagerStatisticsBeanImpl proposed = (WatchManagerStatisticsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AverageEventDataWatchEvaluationTime")) {
                  original.setAverageEventDataWatchEvaluationTime(proposed.getAverageEventDataWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("AverageHarvesterWatchEvaluationTime")) {
                  original.setAverageHarvesterWatchEvaluationTime(proposed.getAverageHarvesterWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("AverageLogWatchEvaluationTime")) {
                  original.setAverageLogWatchEvaluationTime(proposed.getAverageLogWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("MaximumEventDataWatchEvaluationTime")) {
                  original.setMaximumEventDataWatchEvaluationTime(proposed.getMaximumEventDataWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("MaximumHarvesterWatchEvaluationTime")) {
                  original.setMaximumHarvesterWatchEvaluationTime(proposed.getMaximumHarvesterWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("MaximumLogWatchEvaluationTime")) {
                  original.setMaximumLogWatchEvaluationTime(proposed.getMaximumLogWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("MinimumEventDataWatchEvaluationTime")) {
                  original.setMinimumEventDataWatchEvaluationTime(proposed.getMinimumEventDataWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("MinimumHarvesterWatchEvaluationTime")) {
                  original.setMinimumHarvesterWatchEvaluationTime(proposed.getMinimumHarvesterWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("MinimumLogWatchEvaluationTime")) {
                  original.setMinimumLogWatchEvaluationTime(proposed.getMinimumLogWatchEvaluationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("TotalDIMGNotificationsPerformed")) {
                  original.setTotalDIMGNotificationsPerformed(proposed.getTotalDIMGNotificationsPerformed());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("TotalEventDataEvaluationCycles")) {
                  original.setTotalEventDataEvaluationCycles(proposed.getTotalEventDataEvaluationCycles());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("TotalEventDataWatchEvaluations")) {
                  original.setTotalEventDataWatchEvaluations(proposed.getTotalEventDataWatchEvaluations());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("TotalEventDataWatchesTriggered")) {
                  original.setTotalEventDataWatchesTriggered(proposed.getTotalEventDataWatchesTriggered());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("TotalFailedDIMGNotifications")) {
                  original.setTotalFailedDIMGNotifications(proposed.getTotalFailedDIMGNotifications());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("TotalFailedJMSNotifications")) {
                  original.setTotalFailedJMSNotifications(proposed.getTotalFailedJMSNotifications());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("TotalFailedJMXNotifications")) {
                  original.setTotalFailedJMXNotifications(proposed.getTotalFailedJMXNotifications());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("TotalFailedNotifications")) {
                  original.setTotalFailedNotifications(proposed.getTotalFailedNotifications());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("TotalFailedSMTPNotifications")) {
                  original.setTotalFailedSMTPNotifications(proposed.getTotalFailedSMTPNotifications());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("TotalFailedSNMPNotifications")) {
                  original.setTotalFailedSNMPNotifications(proposed.getTotalFailedSNMPNotifications());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("TotalHarvesterEvaluationCycles")) {
                  original.setTotalHarvesterEvaluationCycles(proposed.getTotalHarvesterEvaluationCycles());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("TotalHarvesterWatchEvaluations")) {
                  original.setTotalHarvesterWatchEvaluations(proposed.getTotalHarvesterWatchEvaluations());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("TotalHarvesterWatchesTriggered")) {
                  original.setTotalHarvesterWatchesTriggered(proposed.getTotalHarvesterWatchesTriggered());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TotalJMSNotificationsPerformed")) {
                  original.setTotalJMSNotificationsPerformed(proposed.getTotalJMSNotificationsPerformed());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("TotalJMXNotificationsPerformed")) {
                  original.setTotalJMXNotificationsPerformed(proposed.getTotalJMXNotificationsPerformed());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("TotalLogEvaluationCycles")) {
                  original.setTotalLogEvaluationCycles(proposed.getTotalLogEvaluationCycles());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("TotalLogWatchEvaluations")) {
                  original.setTotalLogWatchEvaluations(proposed.getTotalLogWatchEvaluations());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("TotalLogWatchesTriggered")) {
                  original.setTotalLogWatchesTriggered(proposed.getTotalLogWatchesTriggered());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("TotalNotificationsPerformed")) {
                  original.setTotalNotificationsPerformed(proposed.getTotalNotificationsPerformed());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("TotalSMTPNotificationsPerformed")) {
                  original.setTotalSMTPNotificationsPerformed(proposed.getTotalSMTPNotificationsPerformed());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("TotalSNMPNotificationsPerformed")) {
                  original.setTotalSNMPNotificationsPerformed(proposed.getTotalSNMPNotificationsPerformed());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
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
            WatchManagerStatisticsBeanImpl copy = (WatchManagerStatisticsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
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

            if ((excludeProps == null || !excludeProps.contains("TotalDIMGNotificationsPerformed")) && this.bean.isTotalDIMGNotificationsPerformedSet()) {
               copy.setTotalDIMGNotificationsPerformed(this.bean.getTotalDIMGNotificationsPerformed());
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

            if ((excludeProps == null || !excludeProps.contains("TotalFailedDIMGNotifications")) && this.bean.isTotalFailedDIMGNotificationsSet()) {
               copy.setTotalFailedDIMGNotifications(this.bean.getTotalFailedDIMGNotifications());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalFailedJMSNotifications")) && this.bean.isTotalFailedJMSNotificationsSet()) {
               copy.setTotalFailedJMSNotifications(this.bean.getTotalFailedJMSNotifications());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalFailedJMXNotifications")) && this.bean.isTotalFailedJMXNotificationsSet()) {
               copy.setTotalFailedJMXNotifications(this.bean.getTotalFailedJMXNotifications());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalFailedNotifications")) && this.bean.isTotalFailedNotificationsSet()) {
               copy.setTotalFailedNotifications(this.bean.getTotalFailedNotifications());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalFailedSMTPNotifications")) && this.bean.isTotalFailedSMTPNotificationsSet()) {
               copy.setTotalFailedSMTPNotifications(this.bean.getTotalFailedSMTPNotifications());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalFailedSNMPNotifications")) && this.bean.isTotalFailedSNMPNotificationsSet()) {
               copy.setTotalFailedSNMPNotifications(this.bean.getTotalFailedSNMPNotifications());
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

            if ((excludeProps == null || !excludeProps.contains("TotalJMSNotificationsPerformed")) && this.bean.isTotalJMSNotificationsPerformedSet()) {
               copy.setTotalJMSNotificationsPerformed(this.bean.getTotalJMSNotificationsPerformed());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalJMXNotificationsPerformed")) && this.bean.isTotalJMXNotificationsPerformedSet()) {
               copy.setTotalJMXNotificationsPerformed(this.bean.getTotalJMXNotificationsPerformed());
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

            if ((excludeProps == null || !excludeProps.contains("TotalSMTPNotificationsPerformed")) && this.bean.isTotalSMTPNotificationsPerformedSet()) {
               copy.setTotalSMTPNotificationsPerformed(this.bean.getTotalSMTPNotificationsPerformed());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalSNMPNotificationsPerformed")) && this.bean.isTotalSNMPNotificationsPerformedSet()) {
               copy.setTotalSNMPNotificationsPerformed(this.bean.getTotalSNMPNotificationsPerformed());
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
