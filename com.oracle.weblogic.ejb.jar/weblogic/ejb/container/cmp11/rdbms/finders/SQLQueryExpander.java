package weblogic.ejb.container.cmp11.rdbms.finders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.utils.ToStringUtils;
import weblogic.logging.Loggable;

public class SQLQueryExpander implements WLQLExpressionTypes {
   private static final boolean verbose = false;
   private static final char DOT = '.';
   protected WLQLExpression queryExpression = null;
   private Hashtable parameterMap = null;
   private List variableMap = null;
   private Collection warnings = null;

   public SQLQueryExpander(WLQLExpression q, Hashtable map) {
      this.queryExpression = q;
      this.parameterMap = map;
      this.variableMap = new LinkedList();
   }

   public String toSQL() throws IllegalExpressionException {
      return this.toSQL(this.queryExpression);
   }

   public String toSQL(WLQLExpression q) throws IllegalExpressionException {
      switch (q.type()) {
         case 0:
            return this.join(q, "AND");
         case 1:
            return this.join(q, "OR");
         case 2:
            return "NOT " + this.toSQL(q.term(0));
         case 3:
            return this.twoTerm(q, "=");
         case 4:
            return this.twoTerm(q, "<");
         case 5:
            return this.twoTerm(q, ">");
         case 6:
            return this.twoTerm(q, "<=");
         case 7:
            return this.twoTerm(q, ">=");
         case 8:
            return this.twoTerm(q, "LIKE");
         case 9:
            return this.getIdentifier(q.getSval());
         case 10:
            return "'" + ToStringUtils.escapedQuotesToString(q.getSval()) + "'";
         case 11:
            return q.getSval();
         case 12:
            return q.getSpecialName() + " " + this.join(q, ",");
         case 13:
            return this.getVariable(q);
         case 14:
            return this.toSQL(q.term(0)) + " IS NULL";
         case 15:
            return this.toSQL(q.term(0)) + " IS NOT NULL";
         case 16:
            return this.toSQL(q.term(1)) + " ORDER BY " + q.term(0).getSval();
         case 17:
            return "";
         default:
            throw new IllegalExpressionException(2, WLQLExpressionTypes.TYPE_NAMES[q.getType()]);
      }
   }

   public String[] getParameterNames(WLQLExpression q) {
      Vector v = new Vector();
      this.getParameterNames(q, v);
      String[] names = new String[v.size()];
      v.copyInto(names);
      return names;
   }

   public void getParameterNames(WLQLExpression q, Vector v) {
      if (q.type() == 13) {
         v.addElement(q.getSval());
      } else {
         int i = 0;

         for(int num = q.numTerms(); i < num; ++i) {
            this.getParameterNames(q.term(i), v);
         }
      }

   }

   public int[] getVariableMap() {
      int[] varMap = new int[this.variableMap.size()];
      Iterator mappedElements = this.variableMap.iterator();

      Integer iMap;
      for(int iArrayCount = 0; mappedElements.hasNext(); varMap[iArrayCount++] = iMap) {
         iMap = (Integer)mappedElements.next();
      }

      return varMap;
   }

   boolean hasWarnings() {
      if (this.warnings == null) {
         return false;
      } else {
         return this.warnings.size() != 0;
      }
   }

   Collection getWarnings() {
      return (Collection)(this.warnings == null ? new ArrayList() : this.warnings);
   }

   private void addWarning(Exception e) {
      this.getWarnings().add(e);
   }

   protected String join(WLQLExpression q, String op) throws IllegalExpressionException {
      int num = q.numTerms();
      StringBuilder sb = new StringBuilder("(");

      for(int i = 0; i < num; ++i) {
         sb.append(this.toSQL(q.term(i)));
         if (i < num - 1) {
            sb.append(" ").append(op).append(" ");
         }
      }

      sb.append(")");
      return sb.toString();
   }

   protected String twoTerm(WLQLExpression q, String op) throws IllegalExpressionException {
      String term1 = this.toSQL(q.term(0));
      String term2 = this.toSQL(q.term(1));
      String rval = "(" + term1 + " " + op + " " + term2 + ")";
      return rval;
   }

   private String getIdentifier(String id) throws IllegalExpressionException {
      String mappedValue = (String)this.parameterMap.get(id);
      if (null == mappedValue) {
         throw new IllegalExpressionException(1, id);
      } else {
         char[] carray = mappedValue.toCharArray();
         if (carray.length > 0 && carray[0] != '.' && !Character.isJavaIdentifierStart(carray[0])) {
            Loggable l = EJBLogger.logInvalidStartCharacterForEJBQLIdentifierLoggable(carray[0], mappedValue);
            Exception e = new IllegalExpressionException(1, l.getMessageText());
            this.addWarning(e);
         }

         if (carray.length > 1) {
            for(int i = 1; i < carray.length; ++i) {
               if (carray[i] != '.' && !Character.isJavaIdentifierPart(carray[i])) {
                  Loggable l = EJBLogger.logInvalidPartCharacterForEJBQLIdentifierLoggable(carray[i], mappedValue);
                  Exception e = new IllegalExpressionException(1, l.getMessageText());
                  this.addWarning(e);
               }
            }
         }

         return mappedValue;
      }
   }

   private String getVariable(WLQLExpression q) {
      this.variableMap.add(new Integer(q.getSval()));
      return "?";
   }
}
