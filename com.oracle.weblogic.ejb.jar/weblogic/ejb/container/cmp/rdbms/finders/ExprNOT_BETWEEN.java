package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.utils.ErrorCollectionException;

public class ExprNOT_BETWEEN extends BaseExpr implements Expr, ExpressionTypes {
   private int dbType = 0;
   private Expr term1_clone = null;

   protected ExprNOT_BETWEEN(int type, Expr lhs, Expr left, Expr right) {
      super(type, lhs, left, right);
      this.debugClassName = "ExprNOT_BETWEEN  ";
   }

   public void init_method() throws ErrorCollectionException {
      RDBMSBean finderBean = this.globalContext.getRDBMSBean();
      this.dbType = finderBean.getDatabaseType();
      requireTerm(this, 1);
      requireTerm(this, 2);
      requireTerm(this, 3);

      try {
         this.term1.init(this.globalContext, this.queryTree);
         if (this.dbType != 4) {
            if (this.term1 instanceof ExprID) {
               String ejbqlID = ((ExprID)this.term1).getEjbqlID();
               this.term1_clone = new ExprID(this.term1.type(), ejbqlID);
               this.term1_clone.appendMainEJBQL(ejbqlID);
               this.term1_clone.init(this.globalContext, this.queryTree);
            } else {
               this.term1_clone = this.term1;
            }
         }
      } catch (ErrorCollectionException var5) {
         this.addCollectionException(var5);
      }

      try {
         this.term2.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var4) {
         this.addCollectionException(var4);
      }

      try {
         this.term3.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var3) {
         this.addCollectionException(var3);
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

      try {
         if (this.term2 instanceof ExprID) {
            ((ExprID)this.term2).calcTableAndColumnForCmpField();
         } else {
            this.term2.calculate();
         }
      } catch (ErrorCollectionException var4) {
         this.addCollectionException(var4);
      }

      if (this.dbType != 4 && this.term1_clone != null) {
         try {
            if (this.term1_clone instanceof ExprID) {
               ((ExprID)this.term1_clone).calcTableAndColumnForCmpField();
            } else {
               this.term1_clone.calculate();
            }
         } catch (ErrorCollectionException var3) {
            this.addCollectionException(var3);
         }
      }

      try {
         if (this.term3 instanceof ExprID) {
            ((ExprID)this.term3).calcTableAndColumnForCmpField();
         } else {
            this.term3.calculate();
         }
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

      this.throwCollectionException();
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      BaseExpr newExprBETWEEN = new ExprBETWEEN(12, this.term1, this.term2, this.term3);
      newExprBETWEEN.setPreEJBQLFrom(this);
      newExprBETWEEN.setMainEJBQL("BETWEEN ");
      newExprBETWEEN.setPostEJBQLFrom(this);
      return newExprBETWEEN;
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

      this.appendPostEJBQLTokensToList(l);
      if (this.term3 != null) {
         this.term3.appendEJBQLTokens(l);
      }

   }

   public String toSQL() throws ErrorCollectionException {
      if (this.dbType == 4) {
         this.clearSQLBuf();
         this.appendSQLBuf(this.term1.toSQL());
         this.appendSQLBuf("NOT BETWEEN ");
         this.appendSQLBuf(this.term2.toSQL());
         this.appendSQLBuf("AND ");
         this.appendSQLBuf(this.term3.toSQL());
      } else {
         this.clearSQLBuf();
         this.appendSQLBuf("( ");
         this.appendSQLBuf(this.term1.toSQL());
         this.appendSQLBuf("< ");
         this.appendSQLBuf(this.term2.toSQL());
         this.appendSQLBuf(")");
         this.appendSQLBuf(" OR ");
         this.appendSQLBuf("( ");
         this.appendSQLBuf(this.term1.toSQL());
         this.appendSQLBuf("> ");
         this.appendSQLBuf(this.term3.toSQL());
         this.appendSQLBuf(") ");
      }

      return this.getSQLBuf().toString();
   }
}
