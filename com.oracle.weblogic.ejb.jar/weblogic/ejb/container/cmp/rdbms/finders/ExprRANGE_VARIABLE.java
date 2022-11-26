package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.ArrayList;
import java.util.List;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public final class ExprRANGE_VARIABLE extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprRANGE_VARIABLE(int type, Expr e1, Expr e2) {
      super(type, e1, e2);
      this.debugClassName = "ExprRANGE_VARIABLE ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      requireTerm(this, 2);
      ExprID lhs = (ExprID)this.term1;
      ExprID rhs = (ExprID)this.term2;

      try {
         lhs.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var16) {
         this.addCollectionException(var16);
      }

      try {
         rhs.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var15) {
         this.addCollectionException(var15);
      }

      this.throwCollectionException();
      String abstractNameFromQL = lhs.getEjbqlID();
      String rangeVariable = rhs.getEjbqlID();
      if (rangeVariable.indexOf(".") != -1) {
         Loggable l = EJBLogger.logFinderRVDCannotBePathExpressionLoggable(abstractNameFromQL);
         Exception ex = new IllegalExpressionException(7, l.getMessageText());
         rhs.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      RDBMSBean finderBean = this.globalContext.getRDBMSBean();
      String finderBeanAbstractSchemaName = finderBean.getAbstractSchemaName();
      Loggable bean;
      if (finderBeanAbstractSchemaName == null) {
         bean = EJBLogger.logFinderCouldNotGetAbstractSchemaNameForRVDLoggable(abstractNameFromQL);
         Exception ex = new IllegalExpressionException(7, bean.getMessageText());
         lhs.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      bean = null;
      String tableName = null;
      String tableAlias = null;
      RDBMSBean bean;
      if (abstractNameFromQL.equals(finderBeanAbstractSchemaName)) {
         bean = finderBean;
         tableName = finderBean.getTableName();
         tableAlias = this.globalContext.registerTable(tableName);
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("+++ processing other Bean for abstract schema: " + abstractNameFromQL);
         }

         bean = finderBean.getRDBMSBeanForAbstractSchema(abstractNameFromQL);
         if (null == bean) {
            Loggable l = EJBLogger.logFinderCouldNotGetRDBMSBeanForAbstractSchemaNameLoggable(abstractNameFromQL);
            Exception ex = new IllegalExpressionException(7, l.getMessageText());
            lhs.markExcAndAddCollectionException(ex);
            this.addCollectionExceptionAndThrow(ex);
         } else {
            tableName = bean.getTableName();
            tableAlias = this.globalContext.registerTable(tableName);
         }
      }

      try {
         this.globalContext.addGlobalRangeVariable(rangeVariable, abstractNameFromQL);
         this.queryTree.addRangeVariable(rangeVariable, abstractNameFromQL);
      } catch (IllegalExpressionException var14) {
         this.markExcAndThrowCollectionException(var14);
      }

      JoinNode joinTree = this.queryTree.getJoinTree();
      JoinNode joinNode = new JoinNode(joinTree, rangeVariable, bean, tableName, tableAlias, -1, false, false, "", this.globalContext, new ArrayList());
      joinTree.putChild(rangeVariable, joinNode);

      try {
         this.globalContext.addIdAlias(rangeVariable, rangeVariable);
      } catch (IllegalExpressionException var13) {
         rhs.markExcAndAddCollectionException(var13);
         this.addCollectionExceptionAndThrow(var13);
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

   public String toSQL() {
      return "";
   }

   private static void debug(String s) {
      debugLogger.debug("[ExprRANGE_VARIABLE] " + s);
   }
}
