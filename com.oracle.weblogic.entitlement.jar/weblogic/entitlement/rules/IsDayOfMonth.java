package weblogic.entitlement.rules;

import java.util.Calendar;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public final class IsDayOfMonth extends DayOfMonthPredicate {
   public IsDayOfMonth() {
      super("IsDayOfMonthPredicateName", "IsDayOfMonthPredicateDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      Calendar curDate = this.getCurrentDate();
      return curDate.get(5) == this.getDayOfMonth(curDate);
   }
}
