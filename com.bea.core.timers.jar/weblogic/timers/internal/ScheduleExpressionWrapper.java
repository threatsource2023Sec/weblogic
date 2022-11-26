package weblogic.timers.internal;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import weblogic.timers.ScheduleExpression;

public class ScheduleExpressionWrapper implements Serializable {
   private static final long serialVersionUID = 6415430167817860137L;
   private final ScheduleExpression schedule;
   private byte[] seconds = new byte[60];
   private byte[] minutes = new byte[60];
   private byte[] hours = new byte[24];
   private boolean[] months = new boolean[12];
   private byte[] daysInMonth = new byte[32];
   int daysInMonthArrayMonth = -1;
   int daysInMonthArrayYear = -1;
   TimeZone timezone = null;
   boolean wildCardYearAttribute = false;
   int firstValidYearUpdated = -1;
   int firstValidYear = -1;

   private ScheduleExpressionWrapper(ScheduleExpression schedule) {
      this.schedule = schedule;
   }

   public static ScheduleExpressionWrapper create(ScheduleExpression schedule) throws IllegalArgumentException {
      if (schedule == null) {
         throw new IllegalArgumentException("ScheduleExpression cannot be null");
      } else {
         ScheduleExpressionWrapper wrapper = new ScheduleExpressionWrapper(schedule);
         if (!ScheduleExpressionUtil.parseHHMMSSvalue(schedule.getSecond(), wrapper.seconds, "second")) {
            wrapper.seconds = null;
         }

         if (!ScheduleExpressionUtil.parseHHMMSSvalue(schedule.getMinute(), wrapper.minutes, "minute")) {
            wrapper.minutes = null;
         }

         if (!ScheduleExpressionUtil.parseHHMMSSvalue(schedule.getHour(), wrapper.hours, "hour")) {
            wrapper.hours = null;
         }

         if (!ScheduleExpressionUtil.parseMonthValue(schedule.getMonth(), wrapper.months)) {
            wrapper.months = null;
         }

         wrapper.timezone = ScheduleExpressionUtil.parseTimeZoneValue(schedule.getTimezone());
         Calendar aCal = wrapper.timezone == null ? Calendar.getInstance() : Calendar.getInstance(wrapper.timezone);
         int forMonth = aCal.get(2);
         if (!wrapper.isMonthValid(forMonth)) {
            forMonth = wrapper.getNextValidMonth(forMonth);
         }

         int forYear = aCal.get(1);
         forYear = wrapper.updateFirstValidYear(forYear);
         wrapper.wildCardYearAttribute = ScheduleExpressionUtil.isWildCardYearAttribute(schedule.getYear());
         wrapper.updateDaysInMonthArrayIfNeeded(forYear, forMonth);
         return wrapper;
      }
   }

   public ScheduleExpression getSchedule() {
      return this.schedule;
   }

   public long getFirstTimeout() {
      long nextTimeout = this.getNextTimeout();
      Date firstTimeout = this.schedule.getFirstTimeout();
      return firstTimeout == null || firstTimeout.getTime() >= nextTimeout && (nextTimeout != -1L || firstTimeout.getTime() >= System.currentTimeMillis()) ? nextTimeout : firstTimeout.getTime();
   }

   public long getNextTimeout() {
      Calendar now = this.timezone == null ? Calendar.getInstance() : Calendar.getInstance(this.timezone);
      int year = now.get(1);
      if (!this.wildCardYearAttribute && year > this.firstValidYearUpdated) {
         this.updateFirstValidYear(year);
      }

      Date endDate = this.schedule.getEnd();
      if (endDate != null && endDate.getTime() < System.currentTimeMillis()) {
         return -1L;
      } else {
         Date startDate = this.schedule.getStart();
         if (startDate != null && startDate.getTime() > System.currentTimeMillis()) {
            now.setTimeInMillis(startDate.getTime() - 1000L);
         }

         Calendar next = this.findNextTimeout(now.get(1), now.get(2), now.get(5), now.get(11), now.get(12), now.get(13));
         long nextTimeout = next.getTimeInMillis();
         return endDate != null && endDate.getTime() < nextTimeout ? -1L : nextTimeout;
      }
   }

   Calendar findNextTimeout(int currYear, int currMonth, int currDay, int currHour, int currMinute, int currSecond) {
      if (this.findFirstValidYear(currYear) == currYear && this.isMonthValid(currMonth)) {
         this.updateDaysInMonthArrayIfNeeded(currYear, currMonth);
         boolean isCurrMinuteValid = this.minutes == null ? currMinute == 0 : currMinute == this.minutes[(currMinute + 59) % 60];
         boolean isCurrHourValid = this.hours == null ? currHour == 0 : currHour == this.hours[(currHour + 23) % 24];
         boolean isCurrDayValid = this.daysInMonth == null ? true : currDay == this.daysInMonth[(currDay + 31) % 32];
         int nextS = this.seconds == null ? 0 : this.seconds[currSecond];
         int nextM = currMinute;
         int nextH = currHour;
         int nextDay = currDay;
         if (nextS <= currSecond || !isCurrMinuteValid || !isCurrHourValid || !isCurrDayValid) {
            nextS = this.seconds == null ? 0 : this.seconds[59];
            nextM = this.minutes == null ? 0 : this.minutes[currMinute];
            if (nextM <= currMinute || !isCurrHourValid || !isCurrDayValid) {
               nextM = this.minutes == null ? 0 : this.minutes[59];
               nextH = this.hours == null ? 0 : this.hours[currHour];
               if (nextH <= currHour || !isCurrDayValid) {
                  nextH = this.hours == null ? 0 : this.hours[23];
                  nextDay = this.getNextValidDay(currDay, currMonth, currYear);
                  if (nextDay <= currDay) {
                     return this.findFirstTimeoutInMonthAfter(currYear, currMonth);
                  }
               }
            }
         }

         Calendar next = this.timezone == null ? Calendar.getInstance() : Calendar.getInstance(this.timezone);
         next.set(1, currYear);
         next.set(2, currMonth);
         next.set(5, nextDay);
         next.set(11, nextH);
         next.set(12, nextM);
         next.set(13, nextS);
         return next;
      } else {
         return this.findFirstTimeoutInMonthAfter(currYear, currMonth);
      }
   }

   private Calendar findFirstTimeoutInMonthAfter(int year, int month) {
      int numYearAdvances = 0;
      int nextYear = this.findFirstValidYear(year);
      int nextMonth;
      if (nextYear > year) {
         nextMonth = 0;
         if (!this.isMonthValid(nextMonth)) {
            nextMonth = this.getNextValidMonth(nextMonth);
         }
      } else {
         nextMonth = this.getNextValidMonth(month);
         if (nextMonth <= month) {
            nextYear = this.findFirstValidYear(year + 1);
            nextMonth = 0;
            if (!this.isMonthValid(nextMonth)) {
               nextMonth = this.getNextValidMonth(nextMonth);
            }
         }
      }

      if (nextYear == -100) {
         return this.createExpiredCalendar();
      } else {
         int nextDay;
         for(nextDay = this.getNextValidDay(0, nextMonth, nextYear); nextDay == -1; nextDay = this.getNextValidDay(0, nextMonth, nextYear)) {
            month = nextMonth;
            nextMonth = this.getNextValidMonth(nextMonth);
            if (nextMonth <= month) {
               if (this.wildCardYearAttribute) {
                  ++numYearAdvances;
               }

               nextYear = ScheduleExpressionUtil.getNextValidYear(this.schedule.getYear(), nextYear + 1);
               if (nextYear == -100 || numYearAdvances > 8) {
                  return this.createExpiredCalendar();
               }
            }
         }

         Calendar next = this.timezone == null ? Calendar.getInstance() : Calendar.getInstance(this.timezone);
         next.set(1, nextYear);
         next.set(2, nextMonth);
         next.set(5, nextDay);
         next.set(11, this.hours == null ? 0 : this.hours[23]);
         next.set(12, this.minutes == null ? 0 : this.minutes[59]);
         next.set(13, this.seconds == null ? 0 : this.seconds[59]);
         return next;
      }
   }

   private Calendar createExpiredCalendar() {
      Calendar next = Calendar.getInstance();
      next.setTimeInMillis(-1L);
      return next;
   }

   private int findFirstValidYear(int startYear) {
      if (this.wildCardYearAttribute) {
         return startYear;
      } else {
         return startYear >= this.firstValidYearUpdated && startYear == this.firstValidYear ? this.firstValidYear : ScheduleExpressionUtil.getNextValidYear(this.schedule.getYear(), startYear);
      }
   }

   private int updateFirstValidYear(int startYear) {
      int nextYear = ScheduleExpressionUtil.getNextValidYear(this.schedule.getYear(), startYear);
      this.firstValidYearUpdated = startYear;
      this.firstValidYear = nextYear;
      return nextYear;
   }

   private boolean isMonthValid(int monthIndex) {
      return this.months == null || this.months[monthIndex];
   }

   private int getNextValidMonth(int currMonthIndex) {
      if (this.months == null) {
         return (currMonthIndex + 1) % 12;
      } else {
         for(int i = (currMonthIndex + 1) % 12; i != currMonthIndex; i = (i + 1) % 12) {
            if (this.months[i]) {
               return i;
            }
         }

         return currMonthIndex;
      }
   }

   private int getNextValidDay(int currDayIndex, int month, int year) {
      if (this.daysInMonth == null) {
         int result = currDayIndex + 1;
         Calendar aCal = Calendar.getInstance();
         aCal.set(2, month);
         int daysInMonth = aCal.getActualMaximum(5);
         if (result > daysInMonth) {
            result = 1;
         }

         return result;
      } else {
         this.updateDaysInMonthArrayIfNeeded(year, month);
         return this.daysInMonth[currDayIndex];
      }
   }

   private void updateDaysInMonthArrayIfNeeded(int year, int month) {
      if (this.daysInMonthArrayMonth != month || this.daysInMonthArrayYear != year) {
         if (!ScheduleExpressionUtil.parseDayValues(this.schedule.getDayOfMonth(), this.schedule.getDayOfWeek(), this.daysInMonth, year, month)) {
            this.daysInMonth = null;
         } else {
            this.daysInMonthArrayMonth = month;
            this.daysInMonthArrayYear = year;
         }
      }

   }

   public String toString() {
      return "ScheduleExpressionWrapper for " + this.schedule;
   }
}
