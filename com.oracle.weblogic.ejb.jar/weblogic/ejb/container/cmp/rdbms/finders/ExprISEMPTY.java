package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public class ExprISEMPTY extends BaseExpr implements Expr, ExpressionTypes {
   protected boolean isEmpty = true;
   private int relationshipType;
   private String fullEjbqlId;
   private String fullDealiasedEjbqlId;
   private ExprID lhsExprID = null;
   private String lhsPathExpression = null;
   private JoinNode lhsJoinNode = null;
   private RDBMSBean lhsBean = null;
   private String lhsTableAlias = null;
   private String lhsBeanCMRField = null;
   private StringBuffer preCalcSQLBuf = null;

   protected ExprISEMPTY(int type, Expr expr, boolean isEmpty) {
      super(type, expr);
      this.isEmpty = isEmpty;
      this.debugClassName = "ExprISEMPTY ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      Loggable l;
      Exception e;
      if (this.term1.type() != 17) {
         l = EJBLogger.logfinderArgMustBeCollectionValuedLoggable("IS [NOT] EMPTY");
         e = new Exception(l.getMessageText());
         this.term1.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      }

      try {
         this.term1.init(this.globalContext, this.queryTree);
      } catch (Exception var4) {
         this.addCollectionExceptionAndThrow(var4);
      }

      this.fullEjbqlId = ((ExprID)this.term1).getEjbqlID();
      this.fullDealiasedEjbqlId = ((ExprID)this.term1).getDealiasedEjbqlID();

      try {
         this.relationshipType = ((ExprID)this.term1).getRelationshipTypeForPathExpressionWithNoSQLGen();
      } catch (Exception var3) {
         this.term1.markExcAndAddCollectionException(var3);
         this.addCollectionExceptionAndThrow(var3);
      }

      if (this.relationshipType != 4 && this.relationshipType != 6 && this.relationshipType != 8) {
         l = EJBLogger.logfinderArgMustBeCollectionValuedLoggable("IS (NOT) EMPTY");
         e = new Exception(l.getMessageText());
         this.term1.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      this.term1.calculate();
      this.lhsExprID = this.globalContext.setupForLHSPrimaryKeysWithNoReferenceToRHS((ExprID)this.term1);
      this.lhsPathExpression = this.lhsExprID.getDealiasedEjbqlID();

      try {
         this.lhsJoinNode = this.queryTree.getJoinNodeForLastId(this.lhsPathExpression);
      } catch (Exception var2) {
         this.term1.markExcAndThrowCollectionException(var2);
         this.addCollectionExceptionAndThrow(var2);
      }

      this.lhsBean = this.lhsJoinNode.getRDBMSBean();
      this.lhsTableAlias = this.lhsJoinNode.getTableAlias();
      this.lhsBeanCMRField = QueryContext.getLastFieldFromId(this.fullDealiasedEjbqlId);
      this.preCalcSQLBuf = new StringBuffer();
      switch (this.relationshipType) {
         case 4:
            this.compute_one_to_many(this.preCalcSQLBuf);
            return;
         case 5:
         case 7:
         default:
            this.markExcAndThrowCollectionException(new Exception("Internal Error,  IS [NOT] EMPTY cannot handle lhs relationship type number '" + this.relationshipType + "'  " + RDBMSUtils.relationshipTypeToString(this.relationshipType)));
            return;
         case 6:
            this.compute_many_to_many(this.preCalcSQLBuf);
            return;
         case 8:
            this.compute_remote_w_join_table(this.preCalcSQLBuf);
      }
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      ExprISEMPTY newExprISEMPTY;
      if (this.isEmpty) {
         newExprISEMPTY = new ExprISEMPTY(41, this.term1, false);
         newExprISEMPTY.setPreEJBQLFrom(this);
         newExprISEMPTY.setMainEJBQL("IS EMPTY ");
         newExprISEMPTY.setPostEJBQLFrom(this);
         return newExprISEMPTY;
      } else {
         newExprISEMPTY = new ExprISEMPTY(41, this.term1, true);
         newExprISEMPTY.setPreEJBQLFrom(this);
         newExprISEMPTY.setMainEJBQL("IS NOT EMPTY ");
         newExprISEMPTY.setPostEJBQLFrom(this);
         return newExprISEMPTY;
      }
   }

   private void compute_remote_w_join_table(StringBuffer sb) throws ErrorCollectionException {
      if (this.lhsBean.containsFkField(this.lhsBeanCMRField)) {
         Loggable l = EJBLogger.logfinderArgMustBeCollectionValuedLoggable("IS (NOT) EMPTY");
         Exception ex = new Exception(l.getMessageText());
         this.term1.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      String joinTableName = this.lhsBean.getJoinTableName(this.lhsBeanCMRField);
      Exception ex;
      Loggable l;
      if (joinTableName == null) {
         l = EJBLogger.logfinderCouldNotGetJoinTableLoggable();
         ex = new Exception(" IS (NOT) EMPTY  '" + this.fullEjbqlId + "'  " + l.getMessageText());
         this.term1.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      if (!this.lhsBean.isForeignKeyField(this.lhsBeanCMRField)) {
         l = EJBLogger.logfinderCMRFieldNotFKLoggable(this.lhsBean.getEjbName(), this.lhsBeanCMRField, RDBMSUtils.relationshipTypeToString(this.relationshipType));
         ex = new Exception(l.getMessageText());
         this.term1.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      Iterator fkIter = this.lhsBean.getForeignKeyColNames(this.lhsBeanCMRField).iterator();
      Map pkFieldToCol = this.lhsBean.getCmpFieldToColumnMap();
      sb.append(" ( 0 ");
      if (this.isEmpty) {
         sb.append("=");
      } else {
         sb.append("<");
      }

      sb.append(" (SELECT COUNT(*)  FROM ").append(joinTableName).append(" WHERE ");

      String pk;
      for(; fkIter.hasNext(); this.addToSelectList(this.lhsBean, this.lhsTableAlias + "." + RDBMSUtils.escQuotedID(pk))) {
         String fk = (String)fkIter.next();
         pk = (String)pkFieldToCol.get(this.lhsBean.getRelatedPkFieldName(this.lhsBeanCMRField, fk));
         sb.append(this.lhsTableAlias + "." + RDBMSUtils.escQuotedID(pk) + " = " + RDBMSUtils.escQuotedID(joinTableName) + "." + RDBMSUtils.escQuotedID(fk));
         if (fkIter.hasNext()) {
            sb.append(" AND ");
         } else {
            sb.append(" ");
         }
      }

      sb.append(") ) ");
   }

   private void compute_many_to_many(StringBuffer sb) throws ErrorCollectionException {
      String joinTableName = this.lhsBean.getJoinTableName(this.lhsBeanCMRField);
      Loggable l;
      Exception ex;
      if (joinTableName == null) {
         l = EJBLogger.logfinderCouldNotGetJoinTableLoggable();
         ex = new Exception(" IS (NOT) EMPTY  '" + this.fullEjbqlId + "'  " + l.getMessageText());
         this.term1.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      if (!this.lhsBean.isForeignKeyField(this.lhsBeanCMRField)) {
         l = EJBLogger.logfinderCMRFieldNotFKLoggable(this.lhsBean.getEjbName(), this.lhsBeanCMRField, RDBMSUtils.relationshipTypeToString(this.relationshipType));
         ex = new Exception(l.getMessageText());
         this.term1.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      Iterator fkIter = this.lhsBean.getForeignKeyColNames(this.lhsBeanCMRField).iterator();
      Map pkFieldToCol = this.lhsBean.getCmpFieldToColumnMap();
      sb.append(" ( 0 ");
      if (this.isEmpty) {
         sb.append("=");
      } else {
         sb.append("<");
      }

      sb.append(" (SELECT COUNT(*)  FROM ").append(joinTableName).append(" WHERE ");

      String pk;
      for(; fkIter.hasNext(); this.addToSelectList(this.lhsBean, this.lhsTableAlias + "." + RDBMSUtils.escQuotedID(pk))) {
         String fk = (String)fkIter.next();
         pk = (String)pkFieldToCol.get(this.lhsBean.getRelatedPkFieldName(this.lhsBeanCMRField, fk));
         sb.append(this.lhsTableAlias + "." + RDBMSUtils.escQuotedID(pk) + " = " + RDBMSUtils.escQuotedID(joinTableName) + "." + RDBMSUtils.escQuotedID(fk));
         if (fkIter.hasNext()) {
            sb.append(" AND ");
         } else {
            sb.append(" ");
         }
      }

      sb.append(") ) ");
   }

   private void compute_one_to_many(StringBuffer sb) throws ErrorCollectionException {
      RDBMSBean rhsBean = null;

      try {
         rhsBean = this.queryTree.getLastRDBMSBeanForPathExpressionWithNoSQLGen(this.fullDealiasedEjbqlId);
      } catch (Exception var10) {
         Loggable l = EJBLogger.logFinderCouldNotGetLastJoinNodeLoggable(RDBMSUtils.relationshipTypeToString(this.relationshipType), this.fullEjbqlId, var10.toString());
         Exception ex = new Exception(l.getMessageText());
         this.term1.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      Map pkFieldToCol = this.lhsBean.getCmpFieldToColumnMap();
      String toPKCMRField = null;

      try {
         toPKCMRField = this.lhsBean.getRelatedFieldName(this.lhsBeanCMRField);
      } catch (Exception var9) {
         this.term1.markExcAndAddCollectionException(var9);
         this.addCollectionExceptionAndThrow(var9);
      }

      if (toPKCMRField.length() < 1) {
         Loggable l = EJBLogger.logFinderCouldNotFindCMRPointingToBeanLoggable(rhsBean.getEjbName(), this.lhsBean.getEjbName());
         Exception ex = new Exception(l.getMessageText());
         this.term1.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      String rhsTableName = rhsBean.getTableForCmrField(toPKCMRField);
      if (rhsTableName == null) {
         Loggable l = EJBLogger.logfinderCouldNotGetFKTableLoggable();
         Exception ex = new Exception(" cmr-field: '" + toPKCMRField + "'  " + l.getMessageText());
         this.term1.markExcAndAddCollectionException(ex);
         rhsTableName = "";
         this.addCollectionExceptionAndThrow(ex);
      }

      Iterator fkIter = rhsBean.getForeignKeyColNames(toPKCMRField).iterator();
      sb.append(" ( 0 ");
      if (this.isEmpty) {
         sb.append("=");
      } else {
         sb.append("<");
      }

      sb.append(" (SELECT COUNT(*)  FROM ").append(RDBMSUtils.escQuotedID(rhsTableName)).append(" WHERE ");

      String pk;
      for(; fkIter.hasNext(); this.addToSelectList(this.lhsBean, this.lhsTableAlias + "." + RDBMSUtils.escQuotedID(pk))) {
         String fk = (String)fkIter.next();
         pk = (String)pkFieldToCol.get(rhsBean.getRelatedPkFieldName(toPKCMRField, fk));
         sb.append(this.lhsTableAlias + "." + RDBMSUtils.escQuotedID(pk) + " = " + rhsTableName + "." + RDBMSUtils.escQuotedID(fk));
         if (fkIter.hasNext()) {
            sb.append(" AND ");
         } else {
            sb.append(" ");
         }
      }

      sb.append(") ) ");
   }

   private void addToSelectList(RDBMSBean bean, String selectTableAliasAndColumn) {
      SelectNode sn = new SelectNode();
      sn.setSelectType(17);
      sn.setSelectTypeName(ExpressionTypes.TYPE_NAMES[17]);
      sn.setSelectBean(bean);
      sn.setDbmsTarget(selectTableAliasAndColumn);
      sn.setCorrelatedSubQuery();
      this.queryTree.addSelectList(sn);
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendMainEJBQLTokenToList(l);
      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      return this.preCalcSQLBuf.toString();
   }
}
