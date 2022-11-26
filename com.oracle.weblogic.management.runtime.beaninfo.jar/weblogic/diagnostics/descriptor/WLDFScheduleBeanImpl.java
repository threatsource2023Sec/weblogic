package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.diagnostics.descriptor.validation.WatchNotificationValidators;
import weblogic.utils.collections.CombinedIterator;

public class WLDFScheduleBeanImpl extends WLDFBeanImpl implements WLDFScheduleBean, Serializable {
   private String _DayOfMonth;
   private String _DayOfWeek;
   private String _Hour;
   private String _Minute;
   private String _Month;
   private String _Second;
   private String _Timezone;
   private String _Year;
   private static SchemaHelper2 _schemaHelper;

   public WLDFScheduleBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFScheduleBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFScheduleBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getHour() {
      return this._Hour;
   }

   public boolean isHourInherited() {
      return false;
   }

   public boolean isHourSet() {
      return this._isSet(2);
   }

   public void setHour(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WatchNotificationValidators.validateIncrementInterval(param0);
      String _oldVal = this._Hour;
      this._Hour = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getMinute() {
      return this._Minute;
   }

   public boolean isMinuteInherited() {
      return false;
   }

   public boolean isMinuteSet() {
      return this._isSet(3);
   }

   public void setMinute(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WatchNotificationValidators.validateIncrementInterval(param0);
      String _oldVal = this._Minute;
      this._Minute = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getSecond() {
      return this._Second;
   }

   public boolean isSecondInherited() {
      return false;
   }

   public boolean isSecondSet() {
      return this._isSet(4);
   }

   public void setSecond(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WatchNotificationValidators.validateIncrementInterval(param0);
      String _oldVal = this._Second;
      this._Second = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getYear() {
      return this._Year;
   }

   public boolean isYearInherited() {
      return false;
   }

   public boolean isYearSet() {
      return this._isSet(5);
   }

   public void setYear(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Year;
      this._Year = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getMonth() {
      return this._Month;
   }

   public boolean isMonthInherited() {
      return false;
   }

   public boolean isMonthSet() {
      return this._isSet(6);
   }

   public void setMonth(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Month;
      this._Month = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getDayOfMonth() {
      return this._DayOfMonth;
   }

   public boolean isDayOfMonthInherited() {
      return false;
   }

   public boolean isDayOfMonthSet() {
      return this._isSet(7);
   }

   public void setDayOfMonth(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DayOfMonth;
      this._DayOfMonth = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getDayOfWeek() {
      return this._DayOfWeek;
   }

   public boolean isDayOfWeekInherited() {
      return false;
   }

   public boolean isDayOfWeekSet() {
      return this._isSet(8);
   }

   public void setDayOfWeek(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DayOfWeek;
      this._DayOfWeek = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getTimezone() {
      return this._Timezone;
   }

   public boolean isTimezoneInherited() {
      return false;
   }

   public boolean isTimezoneSet() {
      return this._isSet(9);
   }

   public void setTimezone(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Timezone;
      this._Timezone = param0;
      this._postSet(9, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      WatchNotificationValidators.validateSchedule(this);
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
         idx = 7;
      }

      try {
         switch (idx) {
            case 7:
               this._DayOfMonth = "*";
               if (initOne) {
                  break;
               }
            case 8:
               this._DayOfWeek = "*";
               if (initOne) {
                  break;
               }
            case 2:
               this._Hour = "*";
               if (initOne) {
                  break;
               }
            case 3:
               this._Minute = WLDFWatchCustomizer.getDefaultMinuteSchedule();
               if (initOne) {
                  break;
               }
            case 6:
               this._Month = "*";
               if (initOne) {
                  break;
               }
            case 4:
               this._Second = "0";
               if (initOne) {
                  break;
               }
            case 9:
               this._Timezone = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Year = "*";
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("hour")) {
                  return 2;
               }

               if (s.equals("year")) {
                  return 5;
               }
               break;
            case 5:
               if (s.equals("month")) {
                  return 6;
               }
               break;
            case 6:
               if (s.equals("minute")) {
                  return 3;
               }

               if (s.equals("second")) {
                  return 4;
               }
            case 7:
            case 9:
            case 10:
            default:
               break;
            case 8:
               if (s.equals("timezone")) {
                  return 9;
               }
               break;
            case 11:
               if (s.equals("day-of-week")) {
                  return 8;
               }
               break;
            case 12:
               if (s.equals("day-of-month")) {
                  return 7;
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
            case 2:
               return "hour";
            case 3:
               return "minute";
            case 4:
               return "second";
            case 5:
               return "year";
            case 6:
               return "month";
            case 7:
               return "day-of-month";
            case 8:
               return "day-of-week";
            case 9:
               return "timezone";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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

   protected static class Helper extends WLDFBeanImpl.Helper {
      private WLDFScheduleBeanImpl bean;

      protected Helper(WLDFScheduleBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Hour";
            case 3:
               return "Minute";
            case 4:
               return "Second";
            case 5:
               return "Year";
            case 6:
               return "Month";
            case 7:
               return "DayOfMonth";
            case 8:
               return "DayOfWeek";
            case 9:
               return "Timezone";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DayOfMonth")) {
            return 7;
         } else if (propName.equals("DayOfWeek")) {
            return 8;
         } else if (propName.equals("Hour")) {
            return 2;
         } else if (propName.equals("Minute")) {
            return 3;
         } else if (propName.equals("Month")) {
            return 6;
         } else if (propName.equals("Second")) {
            return 4;
         } else if (propName.equals("Timezone")) {
            return 9;
         } else {
            return propName.equals("Year") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isDayOfMonthSet()) {
               buf.append("DayOfMonth");
               buf.append(String.valueOf(this.bean.getDayOfMonth()));
            }

            if (this.bean.isDayOfWeekSet()) {
               buf.append("DayOfWeek");
               buf.append(String.valueOf(this.bean.getDayOfWeek()));
            }

            if (this.bean.isHourSet()) {
               buf.append("Hour");
               buf.append(String.valueOf(this.bean.getHour()));
            }

            if (this.bean.isMinuteSet()) {
               buf.append("Minute");
               buf.append(String.valueOf(this.bean.getMinute()));
            }

            if (this.bean.isMonthSet()) {
               buf.append("Month");
               buf.append(String.valueOf(this.bean.getMonth()));
            }

            if (this.bean.isSecondSet()) {
               buf.append("Second");
               buf.append(String.valueOf(this.bean.getSecond()));
            }

            if (this.bean.isTimezoneSet()) {
               buf.append("Timezone");
               buf.append(String.valueOf(this.bean.getTimezone()));
            }

            if (this.bean.isYearSet()) {
               buf.append("Year");
               buf.append(String.valueOf(this.bean.getYear()));
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
            WLDFScheduleBeanImpl otherTyped = (WLDFScheduleBeanImpl)other;
            this.computeDiff("DayOfMonth", this.bean.getDayOfMonth(), otherTyped.getDayOfMonth(), true);
            this.computeDiff("DayOfWeek", this.bean.getDayOfWeek(), otherTyped.getDayOfWeek(), true);
            this.computeDiff("Hour", this.bean.getHour(), otherTyped.getHour(), true);
            this.computeDiff("Minute", this.bean.getMinute(), otherTyped.getMinute(), true);
            this.computeDiff("Month", this.bean.getMonth(), otherTyped.getMonth(), true);
            this.computeDiff("Second", this.bean.getSecond(), otherTyped.getSecond(), true);
            this.computeDiff("Timezone", this.bean.getTimezone(), otherTyped.getTimezone(), true);
            this.computeDiff("Year", this.bean.getYear(), otherTyped.getYear(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFScheduleBeanImpl original = (WLDFScheduleBeanImpl)event.getSourceBean();
            WLDFScheduleBeanImpl proposed = (WLDFScheduleBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DayOfMonth")) {
                  original.setDayOfMonth(proposed.getDayOfMonth());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("DayOfWeek")) {
                  original.setDayOfWeek(proposed.getDayOfWeek());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("Hour")) {
                  original.setHour(proposed.getHour());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Minute")) {
                  original.setMinute(proposed.getMinute());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Month")) {
                  original.setMonth(proposed.getMonth());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Second")) {
                  original.setSecond(proposed.getSecond());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Timezone")) {
                  original.setTimezone(proposed.getTimezone());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("Year")) {
                  original.setYear(proposed.getYear());
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
            WLDFScheduleBeanImpl copy = (WLDFScheduleBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DayOfMonth")) && this.bean.isDayOfMonthSet()) {
               copy.setDayOfMonth(this.bean.getDayOfMonth());
            }

            if ((excludeProps == null || !excludeProps.contains("DayOfWeek")) && this.bean.isDayOfWeekSet()) {
               copy.setDayOfWeek(this.bean.getDayOfWeek());
            }

            if ((excludeProps == null || !excludeProps.contains("Hour")) && this.bean.isHourSet()) {
               copy.setHour(this.bean.getHour());
            }

            if ((excludeProps == null || !excludeProps.contains("Minute")) && this.bean.isMinuteSet()) {
               copy.setMinute(this.bean.getMinute());
            }

            if ((excludeProps == null || !excludeProps.contains("Month")) && this.bean.isMonthSet()) {
               copy.setMonth(this.bean.getMonth());
            }

            if ((excludeProps == null || !excludeProps.contains("Second")) && this.bean.isSecondSet()) {
               copy.setSecond(this.bean.getSecond());
            }

            if ((excludeProps == null || !excludeProps.contains("Timezone")) && this.bean.isTimezoneSet()) {
               copy.setTimezone(this.bean.getTimezone());
            }

            if ((excludeProps == null || !excludeProps.contains("Year")) && this.bean.isYearSet()) {
               copy.setYear(this.bean.getYear());
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
