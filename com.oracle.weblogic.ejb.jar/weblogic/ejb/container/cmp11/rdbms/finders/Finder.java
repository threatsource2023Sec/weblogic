package weblogic.ejb.container.cmp11.rdbms.finders;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp11.rdbms.codegen.TypeUtils;
import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;

public final class Finder {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private String methodName = null;
   private List parameterTypes = null;
   private String ejbQuery = null;
   private String sqlQuery = null;
   private WLQLExpression wlqlExpression = null;
   private List finderExpressions = null;
   private FinderOptions finderOptions = null;
   private SQLQueryExpander sqlExpander = null;
   private boolean isSQL;
   private int[] sqlVariableMap;

   public Finder(String name, String query) throws InvalidFinderException {
      this.setName(name);
      this.setWeblogicQuery(query);
      this.parameterTypes = new ArrayList();
      this.finderExpressions = new ArrayList();
   }

   public void setUsingSQL(boolean b) {
      this.isSQL = b;
   }

   private void setName(String finderName) throws InvalidFinderException {
      if (finderName == null) {
         throw new InvalidFinderException(1, finderName);
      } else if (finderName.equals("")) {
         throw new InvalidFinderException(2, finderName);
      } else if (!finderName.startsWith("find")) {
         throw new InvalidFinderException(3, finderName);
      } else {
         this.methodName = finderName;
      }
   }

   public String getName() {
      return this.methodName;
   }

   public void addParameterType(String typeName) {
      if (typeName.length() > 0) {
         this.parameterTypes.add(typeName);
      }

   }

   public Iterator getParameterTypes() {
      return this.parameterTypes.iterator();
   }

   public void setWeblogicQuery(String query) {
      this.ejbQuery = query;
      this.sqlQuery = null;
   }

   public String getWeblogicQuery() {
      return this.ejbQuery;
   }

   public String getSQLQuery(Hashtable map) throws IllegalExpressionException {
      this.computeSQLQuery(map);
      if (this.sqlExpander.hasWarnings()) {
         Collection c = this.sqlExpander.getWarnings();
         if (c != null) {
            Iterator it = c.iterator();

            while(it.hasNext()) {
               Exception e = (Exception)it.next();
               EJBLogger.logWarningFromEJBQLCompiler(e.getMessage());
            }
         }
      }

      return this.getSQLQuery();
   }

   public String getSQLQuery() {
      return this.sqlQuery;
   }

   public WLQLExpression getWLQLExpression() {
      return this.wlqlExpression;
   }

   public void addFinderExpression(int number, String expressionText, String expressionType) throws InvalidFinderException {
      this.finderExpressions.add(new FinderExpression(number, expressionText, expressionType));
   }

   public void addFinderExpression(FinderExpression expr) {
      this.finderExpressions.add(expr);
   }

   public Iterator getFinderExpressions() {
      return this.finderExpressions.iterator();
   }

   public FinderExpression getFinderExpression(int number) {
      Iterator exps = this.getFinderExpressions();

      FinderExpression expression;
      do {
         if (!exps.hasNext()) {
            return null;
         }

         expression = (FinderExpression)exps.next();
      } while(expression.getNumber() != number);

      return expression;
   }

   public FinderOptions getFinderOptions() {
      return this.finderOptions;
   }

   public void setFinderOptions(FinderOptions finderOptions) {
      this.finderOptions = finderOptions;
   }

   public int getParameterIndex(int variableIndex) {
      if (this.isSQL) {
         return this.sqlVariableMap[variableIndex - 1];
      } else {
         int[] variableMap = this.sqlExpander.getVariableMap();
         return variableMap[variableIndex - 1];
      }
   }

   public int getVariableCount() {
      if (this.isSQL) {
         return this.sqlVariableMap.length;
      } else {
         int[] variableMap = this.sqlExpander.getVariableMap();
         Debug.assertion(variableMap != null);
         return variableMap.length;
      }
   }

   public FinderExpression getExpressionForVariable(int variableIndex, Class[] fieldTypes) {
      FinderExpression expression = this.getFinderExpression(this.getParameterIndex(variableIndex));
      if (expression == null) {
         try {
            expression = new FinderExpression(this.getParameterIndex(variableIndex), "@" + this.getParameterIndex(variableIndex), fieldTypes[this.getParameterIndex(variableIndex)].getName());
         } catch (InvalidFinderException var5) {
            throw new AssertionError("Internal logic produced invalid  FinderExpression " + StackTraceUtils.throwable2StackTrace(var5));
         }
      }

      return expression;
   }

   public boolean methodIsEquivalent(Method method) {
      if (!method.getName().equals(this.getName())) {
         return false;
      } else {
         String[] methodTypes = MethodUtils.classesToJavaSourceTypes(method.getParameterTypes());
         if (this.parameterTypes.size() != methodTypes.length) {
            return false;
         } else {
            for(int i = 0; i < methodTypes.length; ++i) {
               if (!methodTypes[i].equals(this.parameterTypes.get(i))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public boolean equals(Object other) {
      if (!(other instanceof Finder)) {
         return false;
      } else {
         Finder otherFinder = (Finder)other;
         if (!this.getName().equals(otherFinder.getName())) {
            return false;
         } else {
            if (this.getWLQLExpression() == null) {
               if (otherFinder.getWLQLExpression() != null) {
                  return false;
               }
            } else if (!this.getWLQLExpression().equals(otherFinder.getWLQLExpression())) {
               return false;
            }

            if (!this.finderExpressions.equals(otherFinder.finderExpressions)) {
               return false;
            } else {
               return this.parameterTypes.equals(otherFinder.parameterTypes);
            }
         }
      }
   }

   public int hashCode() {
      return this.getName().hashCode() ^ this.getWLQLExpression().hashCode();
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[Finder ");
      buf.append("methodName = " + this.methodName + "; ");
      buf.append("wlqlQuery = " + this.ejbQuery + "; ");
      buf.append("wlqlExpression = " + this.wlqlExpression + "; ");
      buf.append("sqlQuery = " + this.sqlQuery + " ");
      buf.append("Finder Expressions = " + this.finderExpressions + ";");
      buf.append("Parameter types = " + this.parameterTypes + ";");
      buf.append(" End-Finder]");
      return buf.toString();
   }

   public void parseExpression() throws EJBCException, InvalidFinderException {
      if (!this.isSQL) {
         WLQLParser parser = new WLQLParser();
         if (this.ejbQuery == null) {
            throw new InvalidFinderException(4, this.ejbQuery);
         } else {
            if (this.ejbQuery.equals("")) {
               this.wlqlExpression = new WLQLExpression(17);
            } else {
               try {
                  this.wlqlExpression = parser.parse(this.ejbQuery);
               } catch (EJBCException var3) {
                  throw var3;
               }
            }

         }
      }
   }

   public void computeSQLQuery(Hashtable map) throws IllegalExpressionException {
      if (this.isSQL) {
         this.computeLiteralSQLQuery();
      } else {
         try {
            this.sqlExpander = new SQLQueryExpander(this.wlqlExpression, map);
            this.sqlQuery = this.sqlExpander.toSQL();
            if (!this.sqlQuery.trim().equals("")) {
               this.sqlQuery = " WHERE " + this.sqlQuery;
            }

            if (this.getFinderOptions() != null && this.getFinderOptions().getFindForUpdate()) {
               this.sqlQuery = this.sqlQuery + " FOR UPDATE";
            }
         } catch (IllegalExpressionException var3) {
            var3.setFinder(this);
            throw var3;
         }
      }

   }

   public String toUserLevelString(boolean withLineBreaks) {
      StringBuffer sb = new StringBuffer();
      sb.append("Finder");
      if (withLineBreaks) {
         sb.append("\n\t");
      } else {
         sb.append(", ");
      }

      sb.append("Method Name: " + this.methodName);
      if (withLineBreaks) {
         sb.append("\n\t");
      } else {
         sb.append(", ");
      }

      sb.append("Parameter Types: (");
      Iterator typeNames = this.getParameterTypes();

      while(typeNames.hasNext()) {
         sb.append("" + typeNames.next());
         if (typeNames.hasNext()) {
            sb.append(", ");
         }
      }

      sb.append(")");
      if (withLineBreaks) {
         sb.append("\n\t");
      } else {
         sb.append(", ");
      }

      sb.append("WebLogic Query: " + this.ejbQuery);
      if (withLineBreaks) {
         sb.append("\n\t");
      } else {
         sb.append(", ");
      }

      sb.append("Finder Expressions: (");
      Iterator expressions = this.getFinderExpressions();

      while(expressions.hasNext()) {
         sb.append("" + expressions.next());
         if (expressions.hasNext()) {
            sb.append(", ");
         }
      }

      sb.append(")");
      return sb.toString();
   }

   public boolean isContentValid() {
      this.sqlQuery = null;

      try {
         this.parseExpression();
      } catch (EJBCException var9) {
         return false;
      } catch (InvalidFinderException var10) {
         return false;
      }

      Set exprNums = new HashSet();
      Iterator finderExpressions = this.getFinderExpressions();

      while(finderExpressions.hasNext()) {
         FinderExpression expression = (FinderExpression)finderExpressions.next();
         int feNum = expression.getNumber();
         if (exprNums.contains(new Integer(feNum))) {
            return false;
         }

         exprNums.add(new Integer(feNum));
         if (expression.getExpressionText() != null && !expression.getExpressionText().trim().equals("")) {
            String expressionTypeName = expression.getExpressionType();
            if (expressionTypeName != null && !expressionTypeName.trim().equals("")) {
               Class expressionType = null;

               try {
                  expressionType = ClassUtils.nameToClass(expressionTypeName, this.getClass().getClassLoader());
               } catch (ClassNotFoundException var8) {
                  return false;
               }

               if (!TypeUtils.isValidSQLType(expressionType)) {
                  return false;
               }
               continue;
            }

            return false;
         }

         return false;
      }

      return true;
   }

   public static boolean isQueryValid(String query) {
      if (query != null && !query.equals("")) {
         try {
            WLQLParser parser = new WLQLParser();
            parser.parse(query);
            return true;
         } catch (EJBCException var2) {
            return false;
         }
      } else {
         return false;
      }
   }

   private void computeLiteralSQLQuery() {
      List variableList = new ArrayList();
      StringBuffer sb = new StringBuffer();
      sb.append("WHERE ");
      int qLen = this.ejbQuery.length();

      int i;
      for(i = 0; i < qLen; ++i) {
         char c = this.ejbQuery.charAt(i);
         if (c != '$') {
            sb.append(c);
         } else {
            int dollarIndex;
            for(dollarIndex = i; i + 1 < qLen && Character.isDigit(this.ejbQuery.charAt(i + 1)); ++i) {
            }

            if (dollarIndex == i) {
               sb.append('$');
            } else {
               int mult = 1;
               int val = 0;

               for(int j = i; j != dollarIndex; --j) {
                  val += (this.ejbQuery.charAt(j) - 48) * mult;
                  mult *= 10;
               }

               sb.append('?');
               variableList.add(new Integer(val));
            }
         }
      }

      this.sqlQuery = sb.toString();
      this.sqlVariableMap = new int[variableList.size()];
      i = 0;

      for(Iterator it = variableList.iterator(); it.hasNext(); this.sqlVariableMap[i++] = (Integer)it.next()) {
      }

   }

   public static class FinderOptions {
      private boolean findForUpdate = false;

      public boolean getFindForUpdate() {
         return this.findForUpdate;
      }

      public void setFindForUpdate(boolean findForUpdate) {
         this.findForUpdate = findForUpdate;
      }
   }

   public static class FinderExpression {
      private static final boolean debug = false;
      private static final boolean verbose = false;
      private int number;
      private String expressionText;
      private String expressionType;

      public FinderExpression(int number) {
         this.number = number;
      }

      public FinderExpression(int number, String expressionText, String expressionType) throws InvalidFinderException {
         if (number < 0) {
            throw new InvalidFinderException(5, (String)null);
         } else {
            this.number = number;
            if (expressionText == null) {
               throw new InvalidFinderException(6, (String)null);
            } else {
               this.expressionText = expressionText;
               if (expressionType == null) {
                  throw new InvalidFinderException(7, (String)null);
               } else {
                  this.expressionType = expressionType;
               }
            }
         }
      }

      public int getNumber() {
         return this.number;
      }

      public void setNumber(int number) {
         this.number = number;
      }

      public String getExpressionText() {
         return this.expressionText;
      }

      public String getExpressionType() {
         return this.expressionType;
      }

      public void setExpressionText(String text) {
         this.expressionText = text;
      }

      public void setExpressionType(String type) {
         this.expressionType = type;
      }

      public String getExpressionWithParams(String[] parameterNames) {
         StringBuffer text = new StringBuffer();
         StringBuffer currParamReference = null;
         String stringRemainder = this.expressionText;

         while(true) {
            while(stringRemainder.length() > 0) {
               if (stringRemainder.charAt(0) == '@') {
                  stringRemainder = stringRemainder.substring(1);
                  currParamReference = new StringBuffer();

                  while(Character.isDigit(stringRemainder.charAt(0))) {
                     currParamReference.append(stringRemainder.charAt(0));
                     stringRemainder = stringRemainder.substring(1);
                     if (stringRemainder.length() <= 0) {
                        break;
                     }
                  }

                  int paramRef = true;

                  int paramRef;
                  try {
                     paramRef = Integer.parseInt(currParamReference.toString());
                  } catch (NumberFormatException var7) {
                     throw new AssertionError("Internal logic error reading a finder expression:" + StackTraceUtils.throwable2StackTrace(var7));
                  }

                  text.append(parameterNames[paramRef]);
               } else {
                  text.append(stringRemainder.charAt(0));
                  stringRemainder = stringRemainder.substring(1);
               }
            }

            return text.toString();
         }
      }

      public boolean equals(Object other) {
         if (!(other instanceof FinderExpression)) {
            return false;
         } else {
            FinderExpression fe = (FinderExpression)other;
            if (fe.getNumber() != this.getNumber()) {
               return false;
            } else if (!fe.getExpressionText().equals(this.getExpressionText())) {
               return false;
            } else {
               return fe.getExpressionType().equals(this.getExpressionType());
            }
         }
      }

      public int hashCode() {
         return this.number ^ this.expressionText.hashCode() ^ this.expressionType.hashCode();
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         buf.append("[Finder.FinderExpression: ");
         buf.append(" number: " + this.number + "; ");
         buf.append(" expressionText: " + this.expressionText + "; ");
         buf.append(" expressionType: " + this.expressionType + "; ");
         buf.append("]");
         return buf.toString();
      }
   }
}
