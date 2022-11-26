package weblogic.entitlement.rules;

import java.util.Date;
import java.util.TimeZone;
import javax.security.auth.Subject;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public final class After extends BasePredicate {
   private static final String VERSION = "1.0";
   private static final PredicateArgument DATE_ARG = new DatePredicateArgument(86399999L);
   private static final PredicateArgument TIME_ZONE_ARG = new TimeZonePredicateArgument();
   private static PredicateArgument[] arguments;
   private long dateTime;
   private TimeZone timeZone = null;

   public After() {
      super("AfterPredicateName", "AfterPredicateDescription");
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length >= 1) {
         if (args.length > 2) {
            throw new IllegalPredicateArgumentException("Maximum two arguments are expected");
         } else {
            this.dateTime = this.parseDate(args[0]).getTime();
            this.timeZone = args.length > 1 ? this.parseTimeZone(args[1]) : null;
         }
      } else {
         throw new IllegalPredicateArgumentException("At least one argument is expected");
      }
   }

   private Date parseDate(String valueStr) throws IllegalPredicateArgumentException {
      return (Date)DATE_ARG.parseExprValue(valueStr);
   }

   private TimeZone parseTimeZone(String valueStr) throws IllegalPredicateArgumentException {
      return (TimeZone)TIME_ZONE_ARG.parseExprValue(valueStr);
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      long curTime = System.currentTimeMillis();
      if (this.timeZone == null) {
         return curTime > this.dateTime;
      } else {
         return curTime + (long)TimeZone.getDefault().getRawOffset() > this.dateTime + (long)this.timeZone.getRawOffset();
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

   static {
      arguments = new PredicateArgument[]{DATE_ARG, TIME_ZONE_ARG};
   }
}
