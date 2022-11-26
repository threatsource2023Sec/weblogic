package weblogic.entitlement.rules;

import java.util.Calendar;
import java.util.TimeZone;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public final class IsDayOfWeek extends DayOfWeekPredicate {
   public IsDayOfWeek() {
      super("IsDayOfWeekPredicateName", "IsDayOfWeekPredicateDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      TimeZone tz = this.getTimeZone();
      if (tz == null) {
         tz = TimeZone.getDefault();
      }

      Calendar curDate = Calendar.getInstance(tz);
      return curDate.get(7) == this.getDayOfWeek().getCalendarDayId();
   }
}
