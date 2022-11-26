package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprSTRING_FUNCTION extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprSTRING_FUNCTION(int type, Expr term1) {
      super(type, term1);
      this.debugClassName = "ExprSTRING_FUNCTION - " + getTypeName(type);
   }

   protected ExprSTRING_FUNCTION(int type, Expr term1, Expr term2) {
      super(type, term1, term2);
      this.debugClassName = "ExprSTRING_FUNCTION - " + getTypeName(type);
   }

   protected ExprSTRING_FUNCTION(int type, Expr term1, Expr term2, Expr term3) {
      super(type, term1, term2, term3);
      this.debugClassName = "ExprSTRING_FUNCTION - " + getTypeName(type);
   }

   public void init_method() throws ErrorCollectionException {
      switch (this.type()) {
         case 54:
            requireTerm(this, 1);
            requireTerm(this, 2);

            try {
               verifyStringExpressionTerm(this, 1);
            } catch (ErrorCollectionException var19) {
               this.addCollectionException(var19);
            }

            try {
               verifyStringExpressionTerm(this, 2);
            } catch (ErrorCollectionException var18) {
               this.addCollectionException(var18);
            }

            this.throwCollectionException();

            try {
               this.term1.init(this.globalContext, this.queryTree);
            } catch (ErrorCollectionException var17) {
               this.addCollectionException(var17);
            }

            try {
               this.term2.init(this.globalContext, this.queryTree);
            } catch (ErrorCollectionException var16) {
               this.addCollectionException(var16);
            }

            this.throwCollectionException();
            break;
         case 55:
            requireTerm(this, 1);
            requireTerm(this, 2);
            requireTerm(this, 3);

            try {
               verifyStringExpressionTerm(this, 1);
            } catch (ErrorCollectionException var15) {
               this.addCollectionException(var15);
            }

            try {
               this.verifyArithmeticExpressionTerm(this, 2);
            } catch (ErrorCollectionException var14) {
               this.addCollectionException(var14);
            }

            try {
               this.verifyArithmeticExpressionTerm(this, 3);
            } catch (ErrorCollectionException var13) {
               this.addCollectionException(var13);
            }

            this.throwCollectionException();

            try {
               this.term1.init(this.globalContext, this.queryTree);
            } catch (ErrorCollectionException var12) {
               this.addCollectionException(var12);
            }

            try {
               this.term2.init(this.globalContext, this.queryTree);
            } catch (ErrorCollectionException var11) {
               this.addCollectionException(var11);
            }

            try {
               this.term3.init(this.globalContext, this.queryTree);
            } catch (ErrorCollectionException var10) {
               this.addCollectionException(var10);
            }
            break;
         case 56:
            requireTerm(this, 1);
            requireTerm(this, 2);

            try {
               verifyStringExpressionTerm(this, 1);
            } catch (ErrorCollectionException var7) {
               this.addCollectionException(var7);
            }

            try {
               verifyStringExpressionTerm(this, 2);
            } catch (ErrorCollectionException var6) {
               this.addCollectionException(var6);
            }

            if (this.term3 != null) {
               try {
                  this.verifyArithmeticExpressionTerm(this, 3);
               } catch (ErrorCollectionException var5) {
                  this.addCollectionException(var5);
               }
            }

            this.throwCollectionException();

            try {
               this.term1.init(this.globalContext, this.queryTree);
            } catch (ErrorCollectionException var4) {
               this.addCollectionException(var4);
            }

            try {
               this.term2.init(this.globalContext, this.queryTree);
            } catch (ErrorCollectionException var3) {
               this.addCollectionException(var3);
            }

            if (this.term3 != null) {
               try {
                  this.term3.init(this.globalContext, this.queryTree);
               } catch (ErrorCollectionException var2) {
                  this.addCollectionException(var2);
               }
            }
            break;
         case 57:
            requireTerm(this, 1);

            try {
               verifyStringExpressionTerm(this, 1);
            } catch (ErrorCollectionException var9) {
               this.addCollectionException(var9);
            }

            try {
               this.term1.init(this.globalContext, this.queryTree);
            } catch (ErrorCollectionException var8) {
               this.addCollectionException(var8);
            }
            break;
         default:
            Exception ex = new Exception("Internal Error in " + this.debugClassName + ", unknown function type " + this.type());
            this.markExcAndThrowCollectionException(ex);
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      switch (this.type()) {
         case 54:
            try {
               this.term1.calculate();
            } catch (ErrorCollectionException var9) {
               this.addCollectionException(var9);
            }

            try {
               this.term2.calculate();
            } catch (ErrorCollectionException var8) {
               this.addCollectionException(var8);
            }

            this.throwCollectionException();
            break;
         case 55:
            try {
               this.term1.calculate();
            } catch (ErrorCollectionException var7) {
               this.addCollectionException(var7);
            }

            try {
               this.term2.calculate();
            } catch (ErrorCollectionException var6) {
               this.addCollectionException(var6);
            }

            try {
               this.term3.calculate();
            } catch (ErrorCollectionException var5) {
               this.addCollectionException(var5);
            }

            this.throwCollectionException();
            break;
         case 56:
            try {
               this.term1.calculate();
            } catch (ErrorCollectionException var4) {
               this.addCollectionException(var4);
            }

            try {
               this.term2.calculate();
            } catch (ErrorCollectionException var3) {
               this.addCollectionException(var3);
            }

            if (this.term3 != null) {
               try {
                  this.term3.calculate();
               } catch (ErrorCollectionException var2) {
                  this.addCollectionException(var2);
               }
            }
            break;
         case 57:
            this.term1.calculate();
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
      this.appendNewEJBQLTokenToList("( ", l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      if (this.term2 != null) {
         this.appendNewEJBQLTokenToList(", ", l);
         this.term2.appendEJBQLTokens(l);
      }

      if (this.term3 != null) {
         this.appendNewEJBQLTokenToList(", ", l);
         this.term3.appendEJBQLTokens(l);
      }

      this.appendNewEJBQLTokenToList(") ", l);
      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      StringBuffer sb = new StringBuffer();
      switch (this.type()) {
         case 54:
            this.getConcatFunction(sb);
            break;
         case 55:
            this.getSubstringFunction(sb);
            break;
         case 56:
            this.getLocateFunction(sb);
            break;
         case 57:
            this.getLengthFunction(sb);
            break;
         default:
            Exception ex = new Exception("Internal Error in " + this.debugClassName + ", unknown function type " + this.type());
            this.markExcAndThrowCollectionException(ex);
      }

      return sb.toString();
   }

   private void getConcatFunction(StringBuffer sb) throws ErrorCollectionException {
      switch (this.globalContext.getDatabaseType()) {
         case 1:
         case 4:
         case 10:
            sb.append(" CONCAT( ");
            sb.append(this.term1.toSQL()).append(", ");
            sb.append(this.term2.toSQL());
            sb.append(" ) ");
            break;
         case 2:
         case 5:
         case 7:
            sb.append(this.term1.toSQL()).append("+");
            sb.append(this.term2.toSQL());
            break;
         case 3:
         case 6:
         case 8:
         case 9:
         default:
            sb.append(" { fn CONCAT( ");
            sb.append(this.term1.toSQL()).append(", ");
            sb.append(this.term2.toSQL());
            sb.append(" ) } ");
      }

   }

   private void getSubstringFunction(StringBuffer sb) throws ErrorCollectionException {
      switch (this.globalContext.getDatabaseType()) {
         case 1:
         case 4:
         case 9:
         case 10:
            sb.append(" SUBSTR( ");
            sb.append(this.term1.toSQL()).append(", ");
            sb.append(this.term2.toSQL());
            if (this.term3 != null) {
               sb.append(", ");
               sb.append(this.term3.toSQL());
            }

            sb.append(" ) ");
            break;
         case 2:
         case 5:
         case 7:
            sb.append(" SUBSTRING( ");
            sb.append(this.term1.toSQL()).append(", ");
            sb.append(this.term2.toSQL()).append(", ");
            sb.append(this.term3.toSQL());
            sb.append(" ) ");
            break;
         case 3:
            sb.append(" SUBSTRING( ");
            sb.append(this.term1.toSQL()).append(" FROM ");
            sb.append(this.term2.toSQL()).append(" FOR ");
            sb.append(this.term3.toSQL());
            sb.append(" ) ");
            break;
         case 6:
         case 8:
         default:
            sb.append(" { fn SUBSTRING( ");
            sb.append(this.term1.toSQL()).append(", ");
            sb.append(this.term2.toSQL()).append(", ");
            sb.append(this.term3.toSQL());
            sb.append(" ) } ");
      }

   }

   private void getLengthFunction(StringBuffer sb) throws ErrorCollectionException {
      switch (this.globalContext.getDatabaseType()) {
         case 1:
         case 3:
         case 4:
         case 10:
            sb.append(" LENGTH( ");
            sb.append(this.term1.toSQL());
            sb.append(" ) ");
            break;
         case 2:
         case 7:
            sb.append(" LEN( ");
            sb.append(this.term1.toSQL());
            sb.append(" ) ");
            break;
         case 5:
            sb.append(" CHAR_LENGTH( ");
            sb.append(this.term1.toSQL());
            sb.append(" ) ");
            break;
         case 6:
         case 8:
         case 9:
         default:
            sb.append(" { fn LENGTH( ");
            sb.append(this.term1.toSQL());
            sb.append(" ) } ");
      }

   }

   private void getLocateFunction(StringBuffer sb) throws ErrorCollectionException {
      switch (this.globalContext.getDatabaseType()) {
         case 1:
            sb.append(" INSTR( ");
            sb.append(this.term2.toSQL()).append(", ");
            sb.append(this.term1.toSQL());
            sb.append(" )  ");
            break;
         case 2:
         case 7:
            sb.append(" CHARINDEX(");
            sb.append(this.term1.toSQL()).append(", ");
            sb.append(this.term2.toSQL());
            if (this.term3 != null) {
               sb.append(", ");
               sb.append(this.term3.toSQL());
            }

            sb.append(" ) ");
            break;
         case 3:
         case 6:
         case 8:
         default:
            sb.append(" { fn LOCATE( ");
            sb.append(this.term1.toSQL()).append(", ");
            sb.append(this.term2.toSQL());
            if (this.term3 != null) {
               sb.append(", ");
               sb.append(this.term3.toSQL());
            }

            sb.append(" ) } ");
            break;
         case 4:
         case 9:
         case 10:
            sb.append(" LOCATE(");
            sb.append(this.term1.toSQL()).append(", ");
            sb.append(this.term2.toSQL());
            if (this.term3 != null) {
               sb.append(", ");
               sb.append(this.term3.toSQL());
            }

            sb.append(" ) ");
            break;
         case 5:
            sb.append(" CHARINDEX(");
            sb.append(this.term1.toSQL()).append(", ");
            sb.append(this.term2.toSQL());
            sb.append(" ) ");
      }

   }
}
