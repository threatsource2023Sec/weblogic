package weblogic.diagnostics.descriptor;

public interface WLDFScheduleBean extends WLDFBean {
   String getHour();

   void setHour(String var1);

   String getMinute();

   void setMinute(String var1);

   String getSecond();

   void setSecond(String var1);

   String getYear();

   void setYear(String var1);

   String getMonth();

   void setMonth(String var1);

   String getDayOfMonth();

   void setDayOfMonth(String var1);

   String getDayOfWeek();

   void setDayOfWeek(String var1);

   String getTimezone();

   void setTimezone(String var1);
}
