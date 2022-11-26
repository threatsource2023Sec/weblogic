package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public class ExprISNULL extends BaseExpr implements Expr, ExpressionTypes {
   private int relationshipType;
   private int argType;
   private String ejbqlId;
   private String dealiasedEjbqlId;
   private String lastPathExpressionElement;
   protected boolean isNull = true;
   private StringBuffer preCalcSQLBuf = null;

   protected ExprISNULL(int type, Expr operand, boolean isNull) {
      super(type, operand);
      this.isNull = isNull;
      this.debugClassName = "ExprISNULL ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      this.term1.init(this.globalContext, this.queryTree);
      this.argType = this.term1.type();
      if (this.argType != 17 && this.argType != 25) {
         String s = this.term1.getMainEJBQL();
         if (s == null) {
            s = "-- unknown --";
         }

         Loggable l = EJBLogger.logISNULLArgMustBePathExpressionOrVariableLoggable(s);
         Exception e = new Exception(l.getMessageText());
         this.term1.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      }

      if (this.argType == 17) {
         this.ejbqlId = this.term1.getSval();
         this.dealiasedEjbqlId = this.globalContext.replaceIdAliases(this.ejbqlId);

         try {
            this.relationshipType = ((ExprID)this.term1).getRelationshipTypeForPathExpressionWithNoSQLGen();
         } catch (Exception var7) {
            Exception ex = new Exception("Error encountered while compiling EJB QL IS [NOT] NULL, " + var7.toString());
            this.term1.markExcAndAddCollectionException(ex);
            this.addCollectionExceptionAndThrow(ex);
         }

         if (this.relationshipType == 4 || this.relationshipType == 6) {
            JoinNode prevNode = null;

            try {
               JoinNode lastNode = this.queryTree.getJoinNodeForLastId(this.dealiasedEjbqlId);
               prevNode = lastNode.getPrevNode();
            } catch (Exception var6) {
            }

            String prevEjbName = " - unknown - ";
            this.lastPathExpressionElement = ((ExprID)this.term1).getLastField();
            if (prevNode != null) {
               RDBMSBean prevBean = prevNode.getRDBMSBean();
               prevEjbName = prevBean.getEjbName();
            }

            String typeString = RDBMSUtils.relationshipTypeToString(this.relationshipType);
            Loggable l = EJBLogger.logFinderNotNullOnWrongTypeLoggable(prevEjbName, this.lastPathExpressionElement, typeString);
            Exception e = new Exception(l.getMessageText());
            this.term1.markExcAndAddCollectionException(e);
            this.addCollectionExceptionAndThrow(e);
         }

         this.lastPathExpressionElement = ((ExprID)this.term1).getLastField();
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      this.preCalcSQLBuf = new StringBuffer();
      if (this.argType == 25) {
         this.term1.calculate();
         String variable = this.term1.toSQL();
         this.preCalcSQLBuf.append(" " + variable + " IS " + (this.isNull ? "" : "NOT ") + "NULL ");
      } else {
         switch (this.relationshipType) {
            case 0:
               this.term1.calculate();
               ((ExprID)this.term1).prepareIdentifierForSQLGen();
               this.preCalcSQLBuf.append(this.term1.toSQL());
               if (this.isNull) {
                  this.preCalcSQLBuf.append("IS NULL ");
               } else {
                  this.preCalcSQLBuf.append("IS NOT NULL ");
               }

               return;
            case 2:
            case 5:
               this.compute11orN1_fk_on_lhs(this.preCalcSQLBuf, ((ExprID)this.term1).getDealiasedEjbqlID());
               return;
            case 3:
               this.compute11_fk_on_rhs(this.preCalcSQLBuf, ((ExprID)this.term1).getDealiasedEjbqlID());
               return;
            case 7:
               this.compute_remote_interface(this.preCalcSQLBuf, ((ExprID)this.term1).getDealiasedEjbqlID());
               return;
            case 8:
               Exception e = new Exception("Cannot compute IS [NOT] NULL on path expression '" + ((ExprID)this.term1).getDealiasedEjbqlID() + "' that terminates in a remote relationship that involves a join table.");
               this.term1.markExcAndAddCollectionException(e);
               this.addCollectionExceptionAndThrow(e);
            case 1:
            case 4:
            case 6:
            default:
               Exception ex = new Exception("Internal Error, IS [NOT] NULL cannot handle relationship type number '" + this.relationshipType + "'  " + RDBMSUtils.relationshipTypeToString(this.relationshipType));
               this.term1.markExcAndAddCollectionException(ex);
               this.addCollectionExceptionAndThrow(ex);
         }
      }
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      ExprISNULL newExprISNULL;
      if (this.isNull) {
         newExprISNULL = new ExprISNULL(3, this.term1, false);
         newExprISNULL.setPreEJBQLFrom(this);
         newExprISNULL.setMainEJBQL("IS NOT NULL ");
         newExprISNULL.setPostEJBQLFrom(this);
         return newExprISNULL;
      } else {
         newExprISNULL = new ExprISNULL(3, this.term1, true);
         newExprISNULL.setPreEJBQLFrom(this);
         newExprISNULL.setMainEJBQL("IS NULL ");
         newExprISNULL.setPostEJBQLFrom(this);
         return newExprISNULL;
      }
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

   private void compute11orN1_fk_on_lhs(StringBuffer sb, String dealiasedId) throws ErrorCollectionException {
      ExprID truncatedExprID = null;
      List tableAndFKColList = null;

      try {
         truncatedExprID = this.prepareTruncatedPathExpression(dealiasedId);
         tableAndFKColList = this.globalContext.getTableAndFKColumnListForLocal11or1NPath(this.queryTree, this.dealiasedEjbqlId);
      } catch (Exception var10) {
         this.term1.markExcAndAddCollectionException(var10);
         this.addCollectionExceptionAndThrow(var10);
      }

      Iterator it = tableAndFKColList.iterator();
      String lhsEjbName;
      if (it.hasNext()) {
         while(it.hasNext()) {
            lhsEjbName = (String)it.next();
            sb.append(lhsEjbName);
            if (this.isNull) {
               sb.append(" IS NULL ");
            } else {
               sb.append(" IS NOT NULL ");
            }

            if (it.hasNext()) {
               sb.append("AND ");
            }
         }

      } else {
         lhsEjbName = " - unknown - ";

         try {
            JoinNode lhsJoinNode = this.queryTree.getJoinNodeForLastId(truncatedExprID.getDealiasedEjbqlID());
            lhsEjbName = lhsJoinNode.getRDBMSBean().getEjbName();
         } catch (Exception var9) {
         }

         Loggable l = EJBLogger.logfinderCouldNotGetFKColumnsLoggable(lhsEjbName, this.lastPathExpressionElement, "IS NULL");
         Exception e = new Exception("(1 to 1) or (1 to N) Relationship.  " + l.getMessageText());
         this.term1.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      }
   }

   private void compute11_fk_on_rhs(StringBuffer sb, String dealiasedId) throws ErrorCollectionException {
      ExprID truncatedExprID = null;

      try {
         truncatedExprID = this.prepareTruncatedPathExpression(dealiasedId);
      } catch (Exception var23) {
         this.term1.markExcAndAddCollectionException(var23);
         this.addCollectionExceptionAndThrow(var23);
      }

      String truncatedDealiasedId = truncatedExprID.getDealiasedEjbqlID();
      RDBMSBean lhsBean = truncatedExprID.getRDBMSBeanWithSQLGen();
      JoinNode lhsJoinNode = null;

      try {
         lhsJoinNode = this.queryTree.getJoinNodeForLastId(truncatedDealiasedId);
      } catch (Exception var22) {
         Exception ex = new Exception("Error encountered while compiling EJB QL IS [NOT] NULL, " + var22.toString());
         this.term1.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      String lhsTableName = lhsJoinNode.getTableName();
      String lhsEjbName = lhsJoinNode.getRDBMSBean().getEjbName();
      String outerQueryLhsTableAlias = lhsJoinNode.getTableAlias();
      RDBMSBean rhsBean = null;

      try {
         rhsBean = this.queryTree.getLastRDBMSBeanForPathExpressionWithNoSQLGen(dealiasedId);
      } catch (Exception var21) {
         Exception ex = new Exception("Error encountered while compiling EJB QL IS [NOT] NULL, " + var21.toString());
         this.term1.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      String rhsToPKCMRField = lhsBean.getRelatedFieldName(this.lastPathExpressionElement);
      String rhsTableName = rhsBean.getTableForCmrField(rhsToPKCMRField);
      if (rhsTableName == null) {
         Exception e = new Exception("Internal Error,  " + this.debugClassName + ":  for path: '" + dealiasedId + "', for the cmr-field: '" + rhsToPKCMRField + "', we could not get the name of the Table on Bean: '" + rhsBean.getEjbName() + "' that holds the Foreign Key Columns for the Relationship.  RelatedFieldName used for lookup was: '" + rhsToPKCMRField + "'");
         this.term1.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      }

      String localSubqueryRhsTableAlias = this.globalContext.registerTable(rhsTableName);
      this.queryTree.addTableAliasExclusionList(localSubqueryRhsTableAlias);
      Map fk2pkColMap = rhsBean.getColumnMapForCmrfAndPkTable(rhsToPKCMRField, lhsTableName);
      Iterator fkIter = fk2pkColMap.entrySet().iterator();
      if (!fkIter.hasNext()) {
         Loggable l = EJBLogger.logfinderFKColumnsMissingLoggable("IS [NOT] NULL", dealiasedId, rhsTableName, lhsTableName);
         Exception e = new Exception(l.getMessageText());
         this.term1.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      }

      List prevPKFieldList = lhsBean.getPrimaryKeyFields();
      String pkCol;
      if (prevPKFieldList.size() == 1) {
         String localSubqueryLhsTableAlias = this.globalContext.registerTable(lhsTableName);
         this.queryTree.addTableAliasExclusionList(localSubqueryLhsTableAlias);
         Map.Entry entry = (Map.Entry)fkIter.next();
         pkCol = (String)entry.getKey();
         String pkCol = (String)entry.getValue();
         sb.append(outerQueryLhsTableAlias).append(".");
         sb.append(RDBMSUtils.escQuotedID(pkCol));
         sb.append(" ");
         if (this.isNull) {
            sb.append("NOT ");
         }

         sb.append("IN ( SELECT ");
         sb.append(localSubqueryLhsTableAlias).append(".").append(RDBMSUtils.escQuotedID(pkCol));
         sb.append(" FROM ");
         sb.append(RDBMSUtils.escQuotedID(lhsTableName)).append(" ").append(localSubqueryLhsTableAlias);
         sb.append(", ");
         sb.append(RDBMSUtils.escQuotedID(rhsTableName)).append(" ").append(localSubqueryRhsTableAlias);
         sb.append(" ");
         sb.append("WHERE ");
         sb.append(localSubqueryLhsTableAlias).append(".").append(RDBMSUtils.escQuotedID(pkCol));
         sb.append(" = ");
         sb.append(localSubqueryRhsTableAlias).append(".").append(RDBMSUtils.escQuotedID(pkCol));
         sb.append(") ");
      } else {
         sb.append(" ( 0 ");
         if (this.isNull) {
            sb.append(" = ");
         } else {
            sb.append(" < ");
         }

         sb.append("( ");
         sb.append("SELECT COUNT(*) ");
         sb.append("FROM ").append(rhsTableName).append(" ").append(localSubqueryRhsTableAlias).append(" ");
         sb.append("WHERE ");

         while(fkIter.hasNext()) {
            Map.Entry entry = (Map.Entry)fkIter.next();
            String fkCol = (String)entry.getKey();
            pkCol = (String)entry.getValue();
            sb.append(outerQueryLhsTableAlias).append(".").append(pkCol);
            sb.append(" = ");
            sb.append(localSubqueryRhsTableAlias).append(".").append(fkCol);
            if (fkIter.hasNext()) {
               sb.append(" AND ");
            }
         }

         sb.append(" ) ) ");
      }
   }

   private void compute_remote_interface(StringBuffer sb, String dealiasedId) throws ErrorCollectionException {
      this.term1.calculate();
      ((ExprID)this.term1).prepareIdentifierForSQLGen();
      List tableAndFKColList = null;

      try {
         tableAndFKColList = this.globalContext.getTableAndFKColumnListForLocal11or1NPath(this.queryTree, ((ExprID)this.term1).getDealiasedEjbqlID());
      } catch (Exception var6) {
         this.term1.markExcAndAddCollectionException(var6);
         this.addCollectionExceptionAndThrow(var6);
      }

      if (tableAndFKColList.size() != 1) {
         Loggable l = EJBLogger.logFinderExpectedSingleFKLoggable("Remote Relationship", ((ExprID)this.term1).getDealiasedEjbqlID(), Integer.toString(tableAndFKColList.size()));
         Exception e = new Exception(l.getMessageText());
         this.term1.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      }

      Iterator it = tableAndFKColList.iterator();
      String tableAndFK = (String)it.next();
      sb.append(tableAndFK);
      if (this.isNull) {
         sb.append(" IS NULL ");
      } else {
         sb.append(" IS NOT NULL ");
      }

   }

   private ExprID prepareTruncatedPathExpression(String dealiasedId) throws ErrorCollectionException {
      int lastDot = dealiasedId.lastIndexOf(".");
      if (lastDot == -1) {
         Loggable l = EJBLogger.logFinderNotNullOnBadPathLoggable(dealiasedId);
         this.markExcAndThrowCollectionException(new Exception(l.getMessageText()));
      }

      String truncatedId = dealiasedId.substring(0, lastDot);
      ExprID truncatedExprID = ExprID.newInitExprID(this.globalContext, this.queryTree, truncatedId);
      truncatedExprID.prepareIdentifierForSQLGen();
      return truncatedExprID;
   }
}
