package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.utils.ToStringUtils;
import weblogic.ejb20.cmp.rdbms.finders.ExpressionParserException;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public class ExprLIKE extends BaseExpr implements Expr, ExpressionTypes {
   private boolean hasEscape;
   private boolean notLike;

   protected ExprLIKE(int type, Expr lhs, Expr rhs, Expr escape, boolean notLike) {
      super(type, lhs, rhs, escape);
      this.hasEscape = false;
      this.notLike = false;
      this.notLike = notLike;
      this.debugClassName = "ExprLIKE ";
   }

   protected ExprLIKE(int type, Expr lhs, Expr rhs, Expr escape) {
      this(type, lhs, rhs, escape, false);
   }

   public void init_method() throws ErrorCollectionException {
      Loggable l;
      if (this.term1 == null) {
         l = EJBLogger.logLIKEmissingArgumentLoggable();
         this.markExcAndThrowCollectionException(new ExpressionParserException(l.getMessageText()));
      }

      try {
         this.term1.init(this.globalContext, this.queryTree);
      } catch (Exception var4) {
         this.addCollectionException(var4);
      }

      if (this.term1 instanceof ExprID) {
      }

      if (this.term2 == null) {
         l = EJBLogger.logLIKEmissingArgumentLoggable();
         this.markExcAndThrowCollectionException(new ExpressionParserException(l.getMessageText()));
      }

      try {
         this.term2.init(this.globalContext, this.queryTree);
      } catch (Exception var3) {
         this.addCollectionException(var3);
      }

      if (this.term3 != null) {
         this.hasEscape = true;

         try {
            this.term3.init(this.globalContext, this.queryTree);
         } catch (Exception var2) {
            this.addCollectionException(var2);
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
      } catch (Exception var4) {
         this.addCollectionException(var4);
      }

      try {
         this.term2.calculate();
      } catch (Exception var3) {
         this.addCollectionException(var3);
      }

      if (this.hasEscape) {
         try {
            this.term3.calculate();
         } catch (Exception var2) {
            this.addCollectionException(var2);
         }
      }

   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      ExprLIKE newExprLIKE;
      if (this.notLike) {
         newExprLIKE = new ExprLIKE(11, this.term1, this.term2, this.term3, false);
         newExprLIKE.setPreEJBQLFrom(this);
         newExprLIKE.setMainEJBQL("LIKE ");
         newExprLIKE.setPostEJBQLFrom(this);
         return newExprLIKE;
      } else {
         newExprLIKE = new ExprLIKE(11, this.term1, this.term2, this.term3, true);
         newExprLIKE.setPreEJBQLFrom(this);
         newExprLIKE.setMainEJBQL("NOT LIKE ");
         newExprLIKE.setPostEJBQLFrom(this);
         return newExprLIKE;
      }
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendMainEJBQLTokenToList(l);
      if (this.term2 != null) {
         this.term2.appendEJBQLTokens(l);
      }

      if (this.term3 != null) {
         this.term3.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      this.clearSQLBuf();
      if (this.notLike) {
         this.appendSQLBuf("NOT ( ");
      }

      this.appendSQLBuf("( ");
      this.appendSQLBuf(this.term1.toSQL());
      this.appendSQLBuf("LIKE ");
      String likeVal = this.term2.toSQL();
      String escapeVal = "";
      if (this.hasEscape) {
         escapeVal = this.term3.toSQL();
         if (escapeVal != null && escapeVal.length() > 0 && escapeVal.equals("'\\'")) {
            if (this.term2.type() == 18) {
               likeVal = ToStringUtils.escapeBackSlash(likeVal);
            }

            escapeVal = "'\\\\'";
         }
      }

      this.appendSQLBuf(likeVal);
      this.appendSQLBuf(" ");
      if (escapeVal.length() > 0) {
         this.appendSQLBuf("ESCAPE ");
         this.appendSQLBuf(escapeVal);
         this.appendSQLBuf(" ");
      }

      this.appendSQLBuf(") ");
      if (this.notLike) {
         this.appendSQLBuf(") ");
      }

      return this.getSQLBuf().toString();
   }
}
