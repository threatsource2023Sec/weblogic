package weblogic.entitlement.rules;

import java.util.TimeZone;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;

public abstract class DayOfWeekPredicate extends BasePredicate {
   private static final String VERSION = "1.0";
   private static PredicateArgument DAY_OF_WEEK_ARG = new DayOfWeekPredicateArgument();
   private static PredicateArgument TIME_ZONE_ARG = new TimeZonePredicateArgument();
   private static PredicateArgument[] arguments;
   private DayOfWeek dayOfWeek = null;
   private TimeZone timeZone = null;

   public DayOfWeekPredicate(String displayNameId, String descriptionId) {
      super(displayNameId, descriptionId);
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length != 0) {
         if (args.length > 2) {
            throw new IllegalPredicateArgumentException("Maximum two arguments are expected");
         } else {
            this.dayOfWeek = this.parseDayOfTheWeek(args[0]);
            this.timeZone = args.length > 1 ? this.parseTimeZone(args[1]) : null;
         }
      } else {
         throw new IllegalPredicateArgumentException("At least one argument is expected");
      }
   }

   protected DayOfWeek getDayOfWeek() {
      return this.dayOfWeek;
   }

   protected TimeZone getTimeZone() {
      return this.timeZone;
   }

   private DayOfWeek parseDayOfTheWeek(String valueStr) throws IllegalPredicateArgumentException {
      return (DayOfWeek)DAY_OF_WEEK_ARG.parseExprValue(valueStr);
   }

   private TimeZone parseTimeZone(String valueStr) throws IllegalPredicateArgumentException {
      return (TimeZone)TIME_ZONE_ARG.parseExprValue(valueStr);
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

   static {
      arguments = new PredicateArgument[]{DAY_OF_WEEK_ARG, TIME_ZONE_ARG};
   }
}
