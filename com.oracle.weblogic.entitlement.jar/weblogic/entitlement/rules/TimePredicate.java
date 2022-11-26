package weblogic.entitlement.rules;

import java.util.Calendar;
import java.util.TimeZone;
import javax.security.auth.Subject;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public final class TimePredicate extends BasePredicate {
   private static final String VERSION = "1.0";
   private static PredicateArgument[] arguments = new PredicateArgument[]{new TimePredicateArgument("TimePredicateStartTimeArgumentName", "TimePredicateStartTimeArgumentDescription", (TimeOfDay)null), new TimePredicateArgument("TimePredicateEndTimeArgumentName", "TimePredicateEndTimeArgumentDescription", (TimeOfDay)null), new TimeZonePredicateArgument()};
   private TimeOfDay startTime = null;
   private TimeOfDay endTime = null;
   private TimeZone timeZone = null;

   public TimePredicate() {
      super("TimePredicateName", "TimePredicateDescription");
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args.length == 0) {
         throw new IllegalPredicateArgumentException("At least one argument is expected");
      } else if (args.length > 3) {
         throw new IllegalPredicateArgumentException("Maximum three arguments are expected");
      } else {
         this.startTime = this.parseTime(args[0]);
         this.endTime = args.length > 1 ? this.parseTime(args[1]) : null;
         this.timeZone = args.length > 2 ? this.parseTimeZone(args[2]) : null;
      }
   }

   private TimeOfDay parseTime(String valueStr) throws IllegalPredicateArgumentException {
      return (TimeOfDay)arguments[0].parseExprValue(valueStr);
   }

   private TimeZone parseTimeZone(String valueStr) throws IllegalPredicateArgumentException {
      return (TimeZone)arguments[2].parseExprValue(valueStr);
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      if (this.startTime == null && this.endTime == null) {
         return false;
      } else {
         TimeZone tz = this.timeZone == null ? TimeZone.getDefault() : this.timeZone;
         Calendar calendar = Calendar.getInstance(tz);
         int currentTime = (calendar.get(11) * 3600 + calendar.get(12) * 60 + calendar.get(13)) * 1000;
         int startTimeMs = this.startTime.getTime();
         if (this.endTime == null) {
            return currentTime >= startTimeMs;
         } else {
            int endTimeMs = this.endTime.getTime();
            return startTimeMs <= endTimeMs ? currentTime >= startTimeMs && currentTime < endTimeMs : currentTime >= startTimeMs || currentTime < endTimeMs;
         }
      }
   }

   public String getVersion() {
      return "1.0";
   }

   public int getArgumentCount() {
      return arguments.length;
   }

   public PredicateArgument getArgument(int index) {
      return arguments[index];
   }
}
