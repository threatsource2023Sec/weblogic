package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprARITH_FUNCTION extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprARITH_FUNCTION(int type, Expr term1) {
      super(type, term1);
      this.debugClassName = "ExprARITH_FUNCTION - " + getTypeName(type);
   }

   protected ExprARITH_FUNCTION(int type, Expr term1, Expr term2) {
      super(type, term1, term2);
      this.debugClassName = "ExprARITH_FUNCTION - " + getTypeName(type);
   }

   public void init_method() throws ErrorCollectionException {
      switch (this.type()) {
         case 58:
         case 59:
            requireTerm(this, 1);

            try {
               this.verifyArithmeticExpressionTerm(this, 1);
            } catch (ErrorCollectionException var7) {
               this.addCollectionException(var7);
            }

            this.throwCollectionException();

            try {
               this.term1.init(this.globalContext, this.queryTree);
            } catch (ErrorCollectionException var6) {
               this.addCollectionException(var6);
            }

            this.throwCollectionException();
            break;
         case 76:
            requireTerm(this, 1);
            requireTerm(this, 2);

            try {
               this.verifyArithmeticExpressionTerm(this, 1);
            } catch (ErrorCollectionException var5) {
               this.addCollectionException(var5);
            }

            try {
               this.verifyArithmeticExpressionTerm(this, 2);
            } catch (ErrorCollectionException var4) {
               this.addCollectionException(var4);
            }

            this.throwCollectionException();

            try {
               this.term1.init(this.globalContext, this.queryTree);
            } catch (ErrorCollectionException var3) {
               this.addCollectionException(var3);
            }

            try {
               this.term2.init(this.globalContext, this.queryTree);
            } catch (ErrorCollectionException var2) {
               this.addCollectionException(var2);
            }

            this.throwCollectionException();
            break;
         default:
            Exception ex = new Exception("Internal Error in " + this.debugClassName + ", unknown function type " + this.type());
            this.markExcAndThrowCollectionException(ex);
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      switch (this.type()) {
         case 58:
         case 59:
            try {
               this.term1.calculate();
            } catch (ErrorCollectionException var4) {
               this.addCollectionException(var4);
            }

            this.throwCollectionException();
            break;
         case 76:
            try {
               this.term1.calculate();
            } catch (ErrorCollectionException var3) {
               this.addCollectionException(var3);
            }

            try {
               this.term2.calculate();
            } catch (ErrorCollectionException var2) {
               this.addCollectionException(var2);
            }

            this.throwCollectionException();
            break;
         default:
            Exception ex = new Exception("Internal Error in " + this.debugClassName + ", unknown function type " + this.type());
            this.markExcAndThrowCollectionException(ex);
      }

   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      if (this.term1 != null) {
         this.appendNewEJBQLTokenToList("( ", l);
         this.term1.appendEJBQLTokens(l);
         if (this.term2 != null) {
            this.appendNewEJBQLTokenToList(", ", l);
            this.term2.appendEJBQLTokens(l);
         }

         this.appendNewEJBQLTokenToList(") ", l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      StringBuffer sb = new StringBuffer();
      switch (this.type()) {
         case 58:
            this.getAbsFunction(sb);
            break;
         case 59:
            this.getSqrtFunction(sb);
            break;
         case 76:
            this.getModFunction(sb);
            break;
         default:
            Exception ex = new Exception("Internal Error in " + this.debugClassName + ", unknown function type " + this.type());
            this.markExcAndThrowCollectionException(ex);
      }

      return sb.toString();
   }

   private void getAbsFunction(StringBuffer sb) throws ErrorCollectionException {
      int databaseType = this.globalContext.getDatabaseType();
      if (databaseType != 4 && databaseType != 1 && databaseType != 2 && databaseType != 7 && databaseType != 5 && databaseType != 10 && databaseType != 9) {
         sb.append(" { fn ABS( ");
         sb.append(this.term1.toSQL());
         sb.append(" ) } ");
      } else {
         sb.append(" ABS( ");
         sb.append(this.term1.toSQL());
         sb.append(" )");
      }

   }

   private void getSqrtFunction(StringBuffer sb) throws ErrorCollectionException {
      int databaseType = this.globalContext.getDatabaseType();
      if (databaseType != 4 && databaseType != 1 && databaseType != 2 && databaseType != 7 && databaseType != 5 && databaseType != 10 && databaseType != 9) {
         sb.append(" { fn SQRT( ");
         sb.append(this.term1.toSQL());
         sb.append(" ) } ");
      } else {
         sb.append(" SQRT( ");
         sb.append(this.term1.toSQL());
         sb.append(" ) ");
      }

   }

   private void getModFunction(StringBuffer sb) throws ErrorCollectionException {
      int databaseType = this.globalContext.getDatabaseType();
      if (databaseType != 4 && databaseType != 1 && databaseType != 3 && databaseType != 8 && databaseType != 6 && databaseType != 10 && databaseType != 9) {
         if (databaseType != 2 && databaseType != 7 && databaseType != 5) {
            sb.append(" { fn Mod( ");
            sb.append(this.term1.toSQL());
            sb.append(", ");
            sb.append(this.term2.toSQL());
            sb.append(" ) } ");
         } else {
            sb.append(" ( ");
            sb.append(this.term1.toSQL());
            sb.append(" % ");
            sb.append(this.term2.toSQL());
            sb.append(" ) ");
         }
      } else {
         sb.append(" MOD( ");
         sb.append(this.term1.toSQL());
         sb.append(", ");
         sb.append(this.term2.toSQL());
         sb.append(" ) ");
      }

   }
}
