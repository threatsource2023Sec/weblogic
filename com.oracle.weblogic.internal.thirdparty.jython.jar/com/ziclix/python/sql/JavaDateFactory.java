package com.ziclix.python.sql;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import org.python.core.Py;
import org.python.core.PyObject;

public class JavaDateFactory implements DateFactory {
   public PyObject Date(int year, int month, int day) {
      Calendar c = Calendar.getInstance();
      c.set(1, year);
      c.set(2, month - 1);
      c.set(5, day);
      return this.DateFromTicks(c.getTime().getTime() / 1000L);
   }

   public PyObject Time(int hour, int minute, int second) {
      Calendar c = Calendar.getInstance();
      c.set(11, hour);
      c.set(12, minute);
      c.set(13, second);
      return this.TimeFromTicks(c.getTime().getTime() / 1000L);
   }

   public PyObject Timestamp(int year, int month, int day, int hour, int minute, int second) {
      Calendar c = Calendar.getInstance();
      c.set(1, year);
      c.set(2, month - 1);
      c.set(5, day);
      c.set(11, hour);
      c.set(12, minute);
      c.set(13, second);
      c.set(14, 0);
      return this.TimestampFromTicks(c.getTime().getTime() / 1000L);
   }

   public PyObject DateFromTicks(long ticks) {
      Calendar c = Calendar.getInstance();
      c.setTime(new Date(ticks * 1000L));
      c.set(11, 0);
      c.set(12, 0);
      c.set(13, 0);
      c.set(14, 0);
      return Py.java2py(new java.sql.Date(c.getTime().getTime()));
   }

   public PyObject TimeFromTicks(long ticks) {
      return Py.java2py(new Time(ticks * 1000L));
   }

   public PyObject TimestampFromTicks(long ticks) {
      return Py.java2py(new Timestamp(ticks * 1000L));
   }
}
