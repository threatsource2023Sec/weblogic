package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import java.util.Vector;
import weblogic.ejb20.cmp.rdbms.finders.EJBQLToken;
import weblogic.utils.ErrorCollectionException;

public interface Expr {
   int CONSTRUCT = 0;
   int INIT = 1;
   int CALCULATE = 3;

   QueryContext getGlobalContext();

   QueryNode getQueryTree();

   void init(QueryContext var1, QueryNode var2) throws ErrorCollectionException;

   void calculate() throws ErrorCollectionException;

   String getMainEJBQL();

   void prependPreEJBQL(String var1);

   void appendMainEJBQL(String var1);

   void appendMainEJBQL(EJBQLToken var1);

   void appendPostEJBQL(String var1);

   void appendEJBQLTokens(List var1);

   String printEJBQLTree();

   String toSQL() throws ErrorCollectionException;

   void addCollectionException(Exception var1);

   void addCollectionExceptionAndThrow(Exception var1) throws ErrorCollectionException;

   void throwCollectionException() throws ErrorCollectionException;

   void markExcAndAddCollectionException(Exception var1);

   void markExcAndThrowCollectionException(Exception var1) throws ErrorCollectionException;

   boolean encounteredException();

   int type();

   Expr getTerm1();

   Expr getTerm2();

   Expr getTerm3();

   Expr getTerm4();

   Expr getTerm5();

   Expr getTerm6();

   int numTerms();

   void resetTermNumber();

   void setNextTerm(int var1);

   Expr getNextTerm();

   Expr getTerm(int var1);

   int getCurrTermNumber();

   boolean hasMoreTerms();

   Vector getTermVector();

   int termVectSize();

   String getSval();

   void setSval(String var1);

   long getIval();

   double getFval();

   String getTypeName();

   void dump();

   String dumpString();
}
