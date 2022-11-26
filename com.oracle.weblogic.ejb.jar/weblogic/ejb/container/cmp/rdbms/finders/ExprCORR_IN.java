package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprCORR_IN extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprCORR_IN(int type, Expr e1, Expr e2, String ejbqlCORR_IN) {
      super(type, e1, e2);
      this.debugClassName = "CORR_IN";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      requireTerm(this, 2);
      ExprID lhs = (ExprID)this.term1;
      ExprID rhs = (ExprID)this.term2;
      lhs.init(this.globalContext, this.queryTree);
      rhs.init(this.globalContext, this.queryTree);
      String lhsIdentifier = lhs.getEjbqlID();
      String rhsPath = rhs.getEjbqlID();
      if (debugLogger.isDebugEnabled()) {
         debug("+++ SQLExp: processing old style 'a IN apath.b' Corr var: " + lhsIdentifier + "  :  " + rhsPath);
      }

      String deAliasedRhsPath = this.globalContext.replaceIdAliases(rhsPath);
      if (debugLogger.isDebugEnabled()) {
         debug("+++ SQLExp: set Corr var: " + lhsIdentifier + "  :  " + deAliasedRhsPath);
      }

      try {
         this.globalContext.addIdAlias(lhsIdentifier, deAliasedRhsPath);
         this.queryTree.addCollectionMember(lhsIdentifier);
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
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendMainEJBQLTokenToList(l);
      if (this.term2 != null) {
         this.term2.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   private static void debug(String s) {
      debugLogger.debug("[ExprCORR_IN] " + s);
   }
}
