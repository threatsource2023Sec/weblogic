package weblogic.j2ee.descriptor;

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

public class TimerScheduleBeanImpl extends AbstractDescriptorBean implements TimerScheduleBean, Serializable {
   private String _DayOfMonth;
   private String _DayOfWeek;
   private String _Hour;
   private String _Id;
   private String _Minute;
   private String _Month;
   private String _Second;
   private String _Year;
   private static SchemaHelper2 _schemaHelper;

   public TimerScheduleBeanImpl() {
      this._initializeProperty(-1);
   }

   public TimerScheduleBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TimerScheduleBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getSecond() {
      return this._Second;
   }

   public boolean isSecondInherited() {
      return false;
   }

   public boolean isSecondSet() {
      return this._isSet(0);
   }

   public void setSecond(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Second;
      this._Second = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getMinute() {
      return this._Minute;
   }

   public boolean isMinuteInherited() {
      return false;
   }

   public boolean isMinuteSet() {
      return this._isSet(1);
   }

   public void setMinute(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Minute;
      this._Minute = param0;
      this._postSet(1, _oldVal, param0);
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
      String _oldVal = this._Hour;
      this._Hour = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getDayOfMonth() {
      return this._DayOfMonth;
   }

   public boolean isDayOfMonthInherited() {
      return false;
   }

   public boolean isDayOfMonthSet() {
      return this._isSet(3);
   }

   public void setDayOfMonth(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DayOfMonth;
      this._DayOfMonth = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getMonth() {
      return this._Month;
   }

   public boolean isMonthInherited() {
      return false;
   }

   public boolean isMonthSet() {
      return this._isSet(4);
   }

   public void setMonth(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Month;
      this._Month = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getDayOfWeek() {
      return this._DayOfWeek;
   }

   public boolean isDayOfWeekInherited() {
      return false;
   }

   public boolean isDayOfWeekSet() {
      return this._isSet(5);
   }

   public void setDayOfWeek(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DayOfWeek;
      this._DayOfWeek = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getYear() {
      return this._Year;
   }

   public boolean isYearInherited() {
      return false;
   }

   public boolean isYearSet() {
      return this._isSet(6);
   }

   public void setYear(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Year;
      this._Year = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._DayOfMonth = "*";
               if (initOne) {
                  break;
               }
            case 5:
               this._DayOfWeek = "*";
               if (initOne) {
                  break;
               }
            case 2:
               this._Hour = "0";
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Minute = "0";
               if (initOne) {
                  break;
               }
            case 4:
               this._Month = "*";
               if (initOne) {
                  break;
               }
            case 0:
               this._Second = "0";
               if (initOne) {
                  break;
               }
            case 6:
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 7;
               }
            case 3:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
               break;
            case 4:
               if (s.equals("hour")) {
                  return 2;
               }

               if (s.equals("year")) {
                  return 6;
               }
               break;
            case 5:
               if (s.equals("month")) {
                  return 4;
               }
               break;
            case 6:
               if (s.equals("minute")) {
                  return 1;
               }

               if (s.equals("second")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("day-of-week")) {
                  return 5;
               }
               break;
            case 12:
               if (s.equals("day-of-month")) {
                  return 3;
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
               return "second";
            case 1:
               return "minute";
            case 2:
               return "hour";
            case 3:
               return "day-of-month";
            case 4:
               return "month";
            case 5:
               return "day-of-week";
            case 6:
               return "year";
            case 7:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private TimerScheduleBeanImpl bean;

      protected Helper(TimerScheduleBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Second";
            case 1:
               return "Minute";
            case 2:
               return "Hour";
            case 3:
               return "DayOfMonth";
            case 4:
               return "Month";
            case 5:
               return "DayOfWeek";
            case 6:
               return "Year";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DayOfMonth")) {
            return 3;
         } else if (propName.equals("DayOfWeek")) {
            return 5;
         } else if (propName.equals("Hour")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("Minute")) {
            return 1;
         } else if (propName.equals("Month")) {
            return 4;
         } else if (propName.equals("Second")) {
            return 0;
         } else {
            return propName.equals("Year") ? 6 : super.getPropertyIndex(propName);
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

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
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
            TimerScheduleBeanImpl otherTyped = (TimerScheduleBeanImpl)other;
            this.computeDiff("DayOfMonth", this.bean.getDayOfMonth(), otherTyped.getDayOfMonth(), false);
            this.computeDiff("DayOfWeek", this.bean.getDayOfWeek(), otherTyped.getDayOfWeek(), false);
            this.computeDiff("Hour", this.bean.getHour(), otherTyped.getHour(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Minute", this.bean.getMinute(), otherTyped.getMinute(), false);
            this.computeDiff("Month", this.bean.getMonth(), otherTyped.getMonth(), false);
            this.computeDiff("Second", this.bean.getSecond(), otherTyped.getSecond(), false);
            this.computeDiff("Year", this.bean.getYear(), otherTyped.getYear(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TimerScheduleBeanImpl original = (TimerScheduleBeanImpl)event.getSourceBean();
            TimerScheduleBeanImpl proposed = (TimerScheduleBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DayOfMonth")) {
                  original.setDayOfMonth(proposed.getDayOfMonth());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("DayOfWeek")) {
                  original.setDayOfWeek(proposed.getDayOfWeek());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Hour")) {
                  original.setHour(proposed.getHour());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("Minute")) {
                  original.setMinute(proposed.getMinute());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Month")) {
                  original.setMonth(proposed.getMonth());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Second")) {
                  original.setSecond(proposed.getSecond());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Year")) {
                  original.setYear(proposed.getYear());
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
            TimerScheduleBeanImpl copy = (TimerScheduleBeanImpl)initialCopy;
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

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
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
