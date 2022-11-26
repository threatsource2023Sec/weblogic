package weblogic.j2ee.descriptor;

import java.util.Date;

public interface TimerBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   TimerScheduleBean getSchedule();

   TimerScheduleBean createSchedule();

   void destroySchedule(TimerScheduleBean var1);

   Date getStart();

   void setStart(Date var1);

   Date getEnd();

   void setEnd(Date var1);

   NamedMethodBean getTimeoutMethod();

   NamedMethodBean createTimeoutMethod();

   void destroyTimeoutMethod(NamedMethodBean var1);

   boolean getPersistent();

   void setPersistent(boolean var1);

   String getTimezone();

   void setTimezone(String var1);

   String getInfo();

   void setInfo(String var1);

   String getId();

   void setId(String var1);
}
