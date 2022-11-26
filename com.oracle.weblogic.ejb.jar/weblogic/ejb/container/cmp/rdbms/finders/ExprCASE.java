package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.ejb.container.EJBTextTextFormatter;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.utils.ErrorCollectionException;

public final class ExprCASE extends BaseExpr implements Expr, ExpressionTypes, SingleExprIDHolder {
   protected ExprCASE(int type, Expr term1) {
      super(type, term1);
      this.debugClassName = "ExprSTRING_FUNCTION - " + getTypeName(type);
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      if (this.term1.type() != 19) {
         try {
            verifyStringExpressionTerm(this, 1);
         } catch (ErrorCollectionException var3) {
            this.addCollectionException(var3);
         }
      }

      try {
         this.term1.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

      switch (this.term1.type()) {
         case 17:
            this.validateID((ExprID)this.term1);
         case 18:
         case 19:
            break;
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         default:
            throw new AssertionError("Unexpected type for Upper or Lower target: " + ExpressionTypes.TYPE_NAMES[this.term1.type()]);
         case 25:
            this.validateVARIABLE((ExprVARIABLE)this.term1);
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      this.term1.calculate();
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

      this.appendNewEJBQLTokenToList(") ", l);
      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      StringBuffer sb = new StringBuffer();
      switch (this.type()) {
         case 70:
            sb.append("UPPER( ");
            break;
         case 71:
            sb.append("LOWER( ");
            break;
         default:
            Exception ex = new Exception("Internal Error in " + this.debugClassName + ", unknown function type " + this.type());
            this.markExcAndThrowCollectionException(ex);
      }

      sb.append(this.term1.toSQL());
      if (this.globalContext.getDatabaseType() == 4 && this.term1.type() == 25) {
         sb.append("||''");
      }

      sb.append(" ) ");
      return sb.toString();
   }

   public ExprID getExprID() {
      try {
         return (ExprID)this.term1;
      } catch (ClassCastException var2) {
         throw new AssertionError("InternalError: getExprID() can only be called when the argument is a path expression");
      }
   }

   private void validateVARIABLE(ExprVARIABLE exprVARIABLE) throws ErrorCollectionException {
      int variableNumber = exprVARIABLE.getVariableNumber();
      Class variableClass = this.globalContext.getFinderParameterTypeAt(variableNumber - 1);
      if (variableClass != String.class && variableClass != Character.class && variableClass != Character.TYPE) {
         EJBTextTextFormatter fmt = new EJBTextTextFormatter();
         IllegalExpressionException ex = new IllegalExpressionException(7, fmt.caseOperatorUsedOnNonStringField(this.globalContext.getEjbName(), "?" + variableNumber));
         exprVARIABLE.markExcAndThrowCollectionException(ex);
      }

   }

   private void validateID(ExprID exprID) throws ErrorCollectionException {
      String ejbqlID = exprID.getEjbqlID();
      if (exprID.isPathExpressionEndingInCmpFieldWithNoSQLGen()) {
         JoinNode node = exprID.getJoinNodeForLastCmrFieldWithSQLGen();
         RDBMSBean targetBean = node.getRDBMSBean();
         String cmpField = exprID.getLastField();
         Class fClass = targetBean.getCMPBeanDescriptor().getFieldClass(cmpField);
         if (fClass != String.class && fClass != Character.class && fClass != Character.TYPE) {
            EJBTextTextFormatter fmt = new EJBTextTextFormatter();
            IllegalExpressionException ex = new IllegalExpressionException(7, fmt.caseOperatorUsedOnNonStringField(this.globalContext.getEjbName(), ejbqlID));
            exprID.markExcAndThrowCollectionException(ex);
         }
      } else {
         EJBTextTextFormatter fmt = new EJBTextTextFormatter();
         IllegalExpressionException ex = new IllegalExpressionException(7, fmt.caseOperatorUsedOnNonStringField(this.globalContext.getEjbName(), ejbqlID));
         exprID.markExcAndThrowCollectionException(ex);
      }

   }
}
