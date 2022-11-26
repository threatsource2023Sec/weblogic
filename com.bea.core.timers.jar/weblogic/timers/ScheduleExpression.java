package weblogic.timers;

import java.io.Serializable;
import java.util.Date;

public class ScheduleExpression implements Serializable {
   private static final long serialVersionUID = -7618726266519276062L;
   private String dayOfMonth = "*";
   private String dayOfWeek = "*";
   private String hour = "0";
   private String minute = "0";
   private String month = "*";
   private String second = "0";
   private String timezone;
   private String year = "*";
   private Date startDate;
   private Date endDate;
   private transient Date firstTimeout;

   public ScheduleExpression() {
   }

   public ScheduleExpression(String year, String month, String dayOfMonth, String dayOfWeek, String hour, String minute, String second, Date startDate, Date endDate, String timezone) {
      this.dayOfMonth = dayOfMonth;
      this.dayOfWeek = dayOfWeek;
      this.endDate = endDate;
      this.hour = hour;
      this.minute = minute;
      this.month = month;
      this.second = second;
      this.startDate = startDate;
      this.timezone = timezone;
      this.year = year;
   }

   public void setFirstTimeout(Date firstTimeout) {
      this.firstTimeout = firstTimeout;
   }

   public Date getFirstTimeout() {
      return this.firstTimeout;
   }

   public String getDayOfMonth() {
      return this.dayOfMonth;
   }

   public ScheduleExpression dayOfMonth(String dayOfMonth) {
      this.dayOfMonth = dayOfMonth;
      return this;
   }

   public String getDayOfWeek() {
      return this.dayOfWeek;
   }

   public ScheduleExpression dayOfWeek(String dayOfWeek) {
      this.dayOfWeek = dayOfWeek;
      return this;
   }

   public Date getEnd() {
      return this.endDate;
   }

   public ScheduleExpression end(Date endDate) {
      this.endDate = endDate;
      return this;
   }

   public String getHour() {
      return this.hour;
   }

   public ScheduleExpression hour(String hour) {
      this.hour = hour;
      return this;
   }

   public String getMinute() {
      return this.minute;
   }

   public ScheduleExpression minute(String minute) {
      this.minute = minute;
      return this;
   }

   public String getMonth() {
      return this.month;
   }

   public ScheduleExpression month(String month) {
      this.month = month;
      return this;
   }

   public String getSecond() {
      return this.second;
   }

   public ScheduleExpression second(String second) {
      this.second = second;
      return this;
   }

   public Date getStart() {
      return this.startDate;
   }

   public ScheduleExpression start(Date startDate) {
      this.startDate = startDate;
      return this;
   }

   public String getTimezone() {
      return this.timezone;
   }

   public ScheduleExpression timezone(String timezone) {
      this.timezone = timezone;
      return this;
   }

   public String getYear() {
      return this.year;
   }

   public ScheduleExpression year(String year) {
      this.year = year;
      return this;
   }

   public String toString() {
      return "ScheduleExpression{dayOfMonth='" + this.dayOfMonth + '\'' + ", dayOfWeek='" + this.dayOfWeek + '\'' + ", hour='" + this.hour + '\'' + ", minute='" + this.minute + '\'' + ", month='" + this.month + '\'' + ", second='" + this.second + '\'' + ", timeZone='" + this.timezone + '\'' + ", year='" + this.year + '\'' + ", startDate=" + this.startDate + ", endDate=" + this.endDate + '}';
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof ScheduleExpression)) {
         return false;
      } else {
         ScheduleExpression that = (ScheduleExpression)o;
         if (this.dayOfMonth != null) {
            if (!this.dayOfMonth.equals(that.dayOfMonth)) {
               return false;
            }
         } else if (that.dayOfMonth != null) {
            return false;
         }

         label121: {
            if (this.dayOfWeek != null) {
               if (this.dayOfWeek.equals(that.dayOfWeek)) {
                  break label121;
               }
            } else if (that.dayOfWeek == null) {
               break label121;
            }

            return false;
         }

         if (this.endDate != null) {
            if (!this.endDate.equals(that.endDate)) {
               return false;
            }
         } else if (that.endDate != null) {
            return false;
         }

         label107: {
            if (this.hour != null) {
               if (this.hour.equals(that.hour)) {
                  break label107;
               }
            } else if (that.hour == null) {
               break label107;
            }

            return false;
         }

         if (this.minute != null) {
            if (!this.minute.equals(that.minute)) {
               return false;
            }
         } else if (that.minute != null) {
            return false;
         }

         if (this.month != null) {
            if (!this.month.equals(that.month)) {
               return false;
            }
         } else if (that.month != null) {
            return false;
         }

         label86: {
            if (this.second != null) {
               if (this.second.equals(that.second)) {
                  break label86;
               }
            } else if (that.second == null) {
               break label86;
            }

            return false;
         }

         label79: {
            if (this.startDate != null) {
               if (this.startDate.equals(that.startDate)) {
                  break label79;
               }
            } else if (that.startDate == null) {
               break label79;
            }

            return false;
         }

         if (this.timezone != null) {
            if (!this.timezone.equals(that.timezone)) {
               return false;
            }
         } else if (that.timezone != null) {
            return false;
         }

         if (this.year != null) {
            if (!this.year.equals(that.year)) {
               return false;
            }
         } else if (that.year != null) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      int result = this.dayOfMonth != null ? this.dayOfMonth.hashCode() : 0;
      result = 31 * result + (this.dayOfWeek != null ? this.dayOfWeek.hashCode() : 0);
      result = 31 * result + (this.hour != null ? this.hour.hashCode() : 0);
      result = 31 * result + (this.minute != null ? this.minute.hashCode() : 0);
      result = 31 * result + (this.month != null ? this.month.hashCode() : 0);
      result = 31 * result + (this.second != null ? this.second.hashCode() : 0);
      result = 31 * result + (this.timezone != null ? this.timezone.hashCode() : 0);
      result = 31 * result + (this.year != null ? this.year.hashCode() : 0);
      result = 31 * result + (this.startDate != null ? this.startDate.hashCode() : 0);
      result = 31 * result + (this.endDate != null ? this.endDate.hashCode() : 0);
      return result;
   }
}
