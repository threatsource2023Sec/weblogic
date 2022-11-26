package weblogic.entitlement.rules;

import java.util.Calendar;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public final class AfterDayOfMonth extends DayOfMonthPredicate {
   public AfterDayOfMonth() {
      super("AfterDayOfMonthPredicateName", "AfterDayOfMonthPredicateDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      Calendar curDate = this.getCurrentDate();
      return curDate.get(5) > this.getDayOfMonth(curDate);
   }
}
