package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public class ExprIN extends BaseExpr implements Expr, ExpressionTypes {
   private int numRHSTerms;
   private boolean notIn;

   protected ExprIN(int type, Expr lhs, Vector inVector, boolean notIn) {
      super(type, lhs, inVector);
      this.numRHSTerms = 0;
      this.notIn = false;
      this.numRHSTerms = inVector.size();
      this.notIn = notIn;
      this.debugClassName = "ExprIN ";
   }

   protected ExprIN(int type, Expr lhs, Vector inVector) {
      this(type, lhs, inVector, false);
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);

      try {
         this.term1.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var5) {
         this.addCollectionException(var5);
      }

      if (this.term1 instanceof ExprID) {
      }

      Loggable l;
      if (this.terms == null) {
         l = EJBLogger.logExpressionRequiresXLoggable("IN", "Vector");
         this.markExcAndAddCollectionException(new IllegalExpressionException(7, l.getMessageText()));
      }

      if (this.numRHSTerms <= 0) {
         l = EJBLogger.logExpressionWrongNumberOfTermsLoggable("IN", Integer.toString(this.numRHSTerms));
         this.markExcAndAddCollectionException(new IllegalExpressionException(7, l.getMessageText()));
      }

      for(int i = 0; i < this.numRHSTerms; ++i) {
         Expr expr = (Expr)this.terms.elementAt(i);

         try {
            expr.init(this.globalContext, this.queryTree);
         } catch (ErrorCollectionException var4) {
            this.addCollectionException(var4);
         }
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      try {
         if (this.term1 instanceof ExprID) {
            ((ExprID)this.term1).calcTableAndColumnForCmpField();
         } else {
            this.term1.calculate();
         }
      } catch (ErrorCollectionException var5) {
         this.addCollectionException(var5);
      }

      for(int i = 0; i < this.numRHSTerms; ++i) {
         Expr expr = (Expr)this.terms.elementAt(i);

         try {
            if (expr instanceof ExprID) {
               ((ExprID)expr).calcTableAndColumnForCmpField();
            } else {
               expr.calculate();
            }
         } catch (ErrorCollectionException var4) {
            this.addCollectionException(var4);
         }
      }

   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      ExprIN newExprIN;
      if (this.notIn) {
         newExprIN = new ExprIN(13, this.term1, this.terms, false);
         newExprIN.setPreEJBQLFrom(this);
         newExprIN.setMainEJBQL("IN ");
         newExprIN.setPostEJBQLFrom(this);
         return newExprIN;
      } else {
         newExprIN = new ExprIN(13, this.term1, this.terms, true);
         newExprIN.setPreEJBQLFrom(this);
         newExprIN.setMainEJBQL("NOT IN ");
         newExprIN.setPostEJBQLFrom(this);
         return newExprIN;
      }
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendMainEJBQLTokenToList(l);
      this.appendNewEJBQLTokenToList("(", l);
      Enumeration en = this.terms.elements();

      while(en.hasMoreElements()) {
         Expr expr = (Expr)en.nextElement();
         expr.appendEJBQLTokens(l);
         if (en.hasMoreElements()) {
            this.appendNewEJBQLTokenToList(", ", l);
         }
      }

      this.appendNewEJBQLTokenToList(") ", l);
      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      this.clearSQLBuf();
      if (this.notIn) {
         this.appendSQLBuf("NOT ( ");
      }

      this.appendSQLBuf(this.term1.toSQL());
      this.appendSQLBuf("IN ( ");

      for(int i = 0; i < this.numRHSTerms; ++i) {
         if (i > 0) {
            this.appendSQLBuf(", ");
         }

         Expr expr = (Expr)this.terms.elementAt(i);
         this.appendSQLBuf(expr.toSQL());
      }

      this.appendSQLBuf(") ");
      if (this.notIn) {
         this.appendSQLBuf(") ");
      }

      return this.getSQLBuf().toString();
   }
}
