package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprCOLLECTION_MEMBER extends BaseExpr implements Expr, ExpressionTypes {
   private String ejbql_id;
   private String expanded_id;

   protected ExprCOLLECTION_MEMBER(int type, Expr e1, Expr e2, String ejbqlIN) {
      super(type, e1, e2);
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      requireTerm(this, 2);
      ExprID lhs = (ExprID)this.term1;
      ExprID rhs = (ExprID)this.term2;

      try {
         lhs.init(this.globalContext, this.queryTree);
      } catch (Exception var9) {
         this.addCollectionException(var9);
      }

      try {
         rhs.init(this.globalContext, this.queryTree);
      } catch (Exception var8) {
         this.addCollectionException(var8);
      }

      this.throwCollectionException();
      String lhsPath = lhs.getEjbqlID();
      String rhsIdentifier = rhs.getEjbqlID();
      if (debugLogger.isDebugEnabled()) {
         debug("+++ SQLExp: processing 'IN(apath.b)a' Corr var: " + rhsIdentifier + "  :  " + lhsPath);
      }

      String deAliasedLhsPath = this.globalContext.replaceIdAliases(lhsPath);
      if (debugLogger.isDebugEnabled()) {
         debug("+++ SQLExp: set Corr var: " + rhsIdentifier + "  :  " + deAliasedLhsPath);
      }

      try {
         this.globalContext.addIdAlias(rhsIdentifier, deAliasedLhsPath);
         this.queryTree.addCollectionMember(rhsIdentifier);
      } catch (IllegalExpressionException var7) {
         this.markExcAndThrowCollectionException(var7);
      }

   }

   public void calculate_method() throws ErrorCollectionException {
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      if (this.term2 != null) {
         this.term2.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() {
      return "";
   }

   private static void debug(String s) {
      debugLogger.debug("[ExprCOLLECTION_MEMBER] " + s);
   }
}
