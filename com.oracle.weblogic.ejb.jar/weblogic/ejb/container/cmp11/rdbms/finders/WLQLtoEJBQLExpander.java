package weblogic.ejb.container.cmp11.rdbms.finders;

import java.util.Hashtable;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.EJBTextTextFormatter;
import weblogic.logging.Loggable;

public final class WLQLtoEJBQLExpander extends SQLQueryExpander {
   EJBTextTextFormatter fmt = null;

   public WLQLtoEJBQLExpander(WLQLExpression q) {
      super(q, (Hashtable)null);
   }

   public String toEJBQL() throws IllegalExpressionException {
      return this.toSQL(super.queryExpression);
   }

   public String toSQL(WLQLExpression q) throws IllegalExpressionException {
      switch (q.type()) {
         case 3:
            if (q.term(0).equals(q.term(1))) {
               return null;
            }
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 10:
         case 11:
         case 14:
         case 15:
         default:
            return super.toSQL(q);
         case 9:
            return this.perhapsAppendAbstractSchemaAlias(q.getSval());
         case 12:
            Loggable l = EJBLogger.logejbqlNoTokenSpecialLoggable(q.getSpecialName());
            throw new IllegalExpressionException(2, WLQLExpressionTypes.TYPE_NAMES[q.getType()], l.getMessageText());
         case 13:
            return this.getVariable(q);
         case 16:
            Loggable l1 = EJBLogger.logejbqlOrderByIsDifferentLoggable();
            throw new IllegalExpressionException(2, WLQLExpressionTypes.TYPE_NAMES[q.getType()], l1.getMessageText());
      }
   }

   private String getVariable(WLQLExpression q) {
      Integer Ival = new Integer(q.getSval());
      int i = Ival;
      ++i;
      return "?" + i;
   }

   private String perhapsAppendAbstractSchemaAlias(String id) {
      String retId = id;
      if (id != null && !id.startsWith("o.")) {
         retId = "o." + id;
      }

      return retId;
   }
}
