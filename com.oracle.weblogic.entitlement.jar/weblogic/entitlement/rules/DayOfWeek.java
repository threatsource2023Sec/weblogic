package weblogic.entitlement.rules;

import java.util.Locale;

public final class DayOfWeek {
   public static final DayOfWeek SUNDAY = new DayOfWeek(1, "Sunday", "Sunday");
   public static final DayOfWeek MONDAY = new DayOfWeek(2, "Monday", "Monday");
   public static final DayOfWeek TUESDAY = new DayOfWeek(3, "Tuesday", "Tuesday");
   public static final DayOfWeek WEDNESDAY = new DayOfWeek(4, "Wednesday", "Wednesday");
   public static final DayOfWeek THURSDAY = new DayOfWeek(5, "Thursday", "Thursday");
   public static final DayOfWeek FRIDAY = new DayOfWeek(6, "Friday", "Friday");
   public static final DayOfWeek SATURDAY = new DayOfWeek(7, "Saturday", "Saturday");
   private int id;
   private String name;
   private String nameId;

   private DayOfWeek(int id, String name, String nameId) {
      this.id = id;
      this.name = name;
      this.nameId = nameId;
   }

   public int getCalendarDayId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getLocalizedName(Locale locale) {
      return Localizer.getText(this.nameId, locale);
   }

   public static DayOfWeek getInstance(int id) {
      if (id == SUNDAY.id) {
         return SUNDAY;
      } else if (id == MONDAY.id) {
         return MONDAY;
      } else if (id == TUESDAY.id) {
         return TUESDAY;
      } else if (id == WEDNESDAY.id) {
         return WEDNESDAY;
      } else if (id == THURSDAY.id) {
         return THURSDAY;
      } else if (id == FRIDAY.id) {
         return FRIDAY;
      } else if (id == SATURDAY.id) {
         return SATURDAY;
      } else {
         throw new IllegalArgumentException("Unexpected day of week id: " + id);
      }
   }
}
