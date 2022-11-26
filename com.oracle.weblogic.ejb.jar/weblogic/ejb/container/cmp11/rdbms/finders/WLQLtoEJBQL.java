package weblogic.ejb.container.cmp11.rdbms.finders;

import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.ejb20.cmp.rdbms.RDBMSException;

public final class WLQLtoEJBQL {
   public static String doWLQLtoEJBQL(String wlql, String abstractSchemaName) throws RDBMSException {
      return "SELECT DISTINCT OBJECT(o) FROM " + abstractSchemaName + " o " + doWLQLtoEJBQL(wlql);
   }

   public static String doWLQLtoEJBQL(String wlql) throws RDBMSException {
      WLQLExpression wlqlExpression = null;
      WLQLParser parser = new WLQLParser();
      if (wlql != null && wlql.length() != 0) {
         try {
            wlqlExpression = parser.parse(wlql);
         } catch (EJBCException var7) {
            throw new RDBMSException("Could not parse WLQL: " + var7.toString());
         }

         WLQLtoEJBQLExpander expander = new WLQLtoEJBQLExpander(wlqlExpression);
         String out = "";

         try {
            String convertedQL = expander.toEJBQL();
            if (convertedQL != null) {
               out = "WHERE " + expander.toEJBQL();
            }

            return out;
         } catch (IllegalExpressionException var6) {
            throw new RDBMSException(var6.toString());
         }
      } else {
         return "";
      }
   }

   public static void main(String[] argv) {
      if (argv.length < 1) {
         System.out.println("\nUsage:  WLQLtoEJBQL <wlql-string>\n\n");
         System.exit(0);
      }

      try {
         System.out.println("EJBQL: '" + doWLQLtoEJBQL(argv[0]) + "'");
      } catch (RDBMSException var2) {
         System.out.println(var2.toString());
         System.exit(0);
      }

   }
}
