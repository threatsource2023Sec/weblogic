package org.apache.openjpa.jdbc.sql;

import java.util.Calendar;

public class Calendard {
   public final Object value;
   public final Calendar calendar;

   public Calendard(Object value, Calendar calendar) {
      this.value = value;
      this.calendar = calendar;
   }
}
