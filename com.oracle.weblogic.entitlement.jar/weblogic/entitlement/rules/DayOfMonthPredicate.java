package weblogic.entitlement.rules;

import java.util.Calendar;
import java.util.TimeZone;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;

public abstract class DayOfMonthPredicate extends BasePredicate {
   private static final String VERSION = "1.0";
   private static PredicateArgument DAY_OF_MONTH_ARG = new DayOfMonthPredicateArgument();
   private static PredicateArgument TIME_ZONE_ARG = new TimeZonePredicateArgument();
   private static PredicateArgument[] arguments;
   private int dayOfMonth = 0;
   private TimeZone timeZone = null;

   public DayOfMonthPredicate(String displayNameId, String descriptionId) {
      super(displayNameId, descriptionId);
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length != 0) {
         if (args.length > 2) {
            throw new IllegalPredicateArgumentException("Maximum two arguments are expected");
         } else {
            this.dayOfMonth = this.parseDayOfMonth(args[0]);
            this.timeZone = args.length > 1 ? this.parseTimeZone(args[1]) : null;
         }
      } else {
         throw new IllegalPredicateArgumentException("At least one argument is expected");
      }
   }

   protected int getDayOfMonthArgument() {
      return this.dayOfMonth;
   }

   protected TimeZone getTimeZoneArgument() {
      return this.timeZone;
   }

   protected Calendar getCurrentDate() {
      return Calendar.getInstance(this.timeZone == null ? TimeZone.getDefault() : this.timeZone);
   }

   protected int getDayOfMonth(Calendar curDate) {
      int dayOfMonthArg = this.dayOfMonth;
      if (dayOfMonthArg < 0) {
         dayOfMonthArg += 1 + curDate.getActualMaximum(5);
      }

      return dayOfMonthArg;
   }

   private int parseDayOfMonth(String valueStr) throws IllegalPredicateArgumentException {
      return ((Number)DAY_OF_MONTH_ARG.parseExprValue(valueStr)).intValue();
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
      arguments = new PredicateArgument[]{DAY_OF_MONTH_ARG, TIME_ZONE_ARG};
   }
}
