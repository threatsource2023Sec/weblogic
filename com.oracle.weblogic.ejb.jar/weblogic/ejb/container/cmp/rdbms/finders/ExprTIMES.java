package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public class ExprTIMES extends ExprSIMPLE_ARITH implements Expr, ExpressionTypes {
   private static final int NOTHING_SPECIAL = 0;
   private static final int TWO_INTEGERS_EITHER_IS_MINUS_1 = 1;
   private static final int MINUS_1_INTEGER_TIMES_FLOAT = 2;
   private int specialHandling = 0;

   protected ExprTIMES(int type, Expr left, Expr right) {
      super(type, left, right);
      this.debugClassName = "ExprTIMES  ";
   }

   public void init_method() throws ErrorCollectionException {
      super.init_method();
      long n1;
      if (this.term1.type() == 19 && this.term2.type() == 19) {
         n1 = this.term1.getIval();
         long n2 = this.term2.getIval();
         if (n1 == -1L || n2 == -1L) {
            this.specialHandling = 1;
         }
      } else if (this.term1.type() == 19 && this.term2.type() == 20) {
         n1 = this.term1.getIval();
         if (n1 == -1L) {
            this.specialHandling = 2;
         }
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      super.calculate_method();
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      if (this.specialHandling == 2) {
         this.appendMainEJBQLTokenToList(l);
         if (this.term2 != null) {
            this.term2.appendEJBQLTokens(l);
         }

         this.appendPostEJBQLTokensToList(l);
      } else {
         if (this.term1 != null) {
            this.term1.appendEJBQLTokens(l);
         }

         this.appendMainEJBQLTokenToList(l);
         if (this.term2 != null) {
            this.term2.appendEJBQLTokens(l);
         }

         this.appendPostEJBQLTokensToList(l);
      }
   }

   public String toSQL() throws ErrorCollectionException {
      this.clearSQLBuf();
      long n1;
      Exception ex;
      switch (this.specialHandling) {
         case 0:
            return super.toSQL();
         case 1:
            n1 = this.term1.getIval();
            long n2 = this.term2.getIval();
            if (n1 == -1L) {
               this.appendSQLBuf(Long.toString(-1L * n2) + " ");
            } else if (n2 == -1L) {
               this.appendSQLBuf(Long.toString(-1L * n1) + " ");
            } else {
               ex = new Exception("Internal Error in " + this.debugClassName + ", attempt to perform toSQL for mode TWO_INTEGERS_EITHER_IS_MINUS_1, encountered error. term1 is '" + this.term1.getIval() + "', term2 is '" + this.term2.getIval() + "'");
               this.markExcAndThrowCollectionException(ex);
            }

            return this.getSQLBuf().toString();
         case 2:
            n1 = this.term1.getIval();
            this.appendSQLBuf("-" + this.term2.getSval() + " ");
            return this.getSQLBuf().toString();
         default:
            ex = new Exception("Internal Error in " + this.debugClassName + ", attempt to perform toSQL using an unknown operand type code: '" + this.type() + "'.  term1 is '" + this.term1.getIval() + "', term2 is '" + this.term2.getIval() + "'");
            this.markExcAndThrowCollectionException(ex);
            return "";
      }
   }
}
