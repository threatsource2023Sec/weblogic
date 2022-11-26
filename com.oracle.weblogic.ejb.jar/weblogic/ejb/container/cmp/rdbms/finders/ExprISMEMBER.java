package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public class ExprISMEMBER extends BaseExpr implements Expr, ExpressionTypes {
   private int lhsRelationshipType;
   private int lhsArgType;
   private boolean lhsIsVariable = false;
   private String lhsFullEjbqlId;
   private String lhsFullDealiasedEjbqlId;
   private String lhsLastPathExpressionElement;
   RDBMSBean lhsBean = null;
   Class lhsInterface = null;
   String lhsInterfaceName = null;
   ExprID truncatedLhsExprID = null;
   ExprID lhsExprIDForQuery = null;
   RDBMSBean lhsPrevBean = null;
   String lhsPrevTableName = null;
   String lhsPrevTableAlias = null;
   private boolean lhsIsRangeVariableIdentifier = false;
   private boolean lhsIsCollectionMemberIdentifier = false;
   private String rhsEjbqlId;
   private String rhsDealiasedEjbqlId;
   protected boolean isMember = true;
   boolean lhsRhsShareRoot = false;
   int lhsNumberOfPathNodes = 0;
   boolean rhsSharesSelectRoot = false;
   SelectNode rhsSelectNodeMatch = null;
   RDBMSBean rhsBean = null;
   Class rhsInterface = null;
   String rhsInterfaceName = null;
   List rhsPKFieldList = null;
   String rhsLastTableName = null;
   String rhsLocalSubqueryLastTableAlias = null;
   private StringBuffer preCalcSQLBuf = null;

   protected ExprISMEMBER(int type, Expr lhs, ExprID rhs, boolean isMember) {
      super(type, lhs, (Expr)rhs);
      this.isMember = isMember;
      this.debugClassName = "ExprISMEMBER ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      requireTerm(this, 2);
      String lhsFirst;
      Loggable l;
      IllegalExpressionException e;
      if (this.term1.type() != 17 && this.term1.type() != 25) {
         lhsFirst = this.term1.getSval();
         if (lhsFirst == null) {
            lhsFirst = "???";
         }

         l = EJBLogger.logfinderMemberLHSWrongTypeLoggable(lhsFirst);
         e = new IllegalExpressionException(7, l.getMessageText());
         this.term1.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      }

      if (this.term2.type() != 17) {
         lhsFirst = this.term2.getSval();
         if (lhsFirst == null) {
            lhsFirst = "???";
         }

         l = EJBLogger.logfinderMemberRHSWrongTypeLoggable(lhsFirst);
         Exception ex = new Exception(l.getMessageText());
         this.term2.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      try {
         this.term1.init(this.globalContext, this.queryTree);
      } catch (Exception var7) {
         this.addCollectionException(var7);
      }

      try {
         this.term2.init(this.globalContext, this.queryTree);
      } catch (Exception var6) {
         this.addCollectionException(var6);
      }

      this.throwCollectionException();
      this.lhsArgType = this.term1.type();
      if (this.lhsArgType == 25) {
         this.lhsIsVariable = true;
         this.lhsRelationshipType = -1;
      } else {
         this.lhsFullEjbqlId = ((ExprID)this.term1).getEjbqlID();
         this.lhsFullDealiasedEjbqlId = ((ExprID)this.term1).getDealiasedEjbqlID();

         try {
            this.lhsRelationshipType = ((ExprID)this.term1).getRelationshipTypeForPathExpressionWithNoSQLGen();
         } catch (Exception var5) {
            this.term1.markExcAndAddCollectionException(var5);
            this.addCollectionExceptionAndThrow(var5);
         }

         this.lhsIsRangeVariableIdentifier = this.queryTree.isRangeVariableInScope(this.lhsFullDealiasedEjbqlId);
         this.lhsIsCollectionMemberIdentifier = this.queryTree.isCollectionMemberInScope(this.lhsFullDealiasedEjbqlId);
         if (this.lhsRelationshipType != 2 && this.lhsRelationshipType != 3 && this.lhsRelationshipType != 5 && !this.lhsIsRangeVariableIdentifier && !this.lhsIsCollectionMemberIdentifier) {
            Loggable l = EJBLogger.logfinderMemberLHSWrongTypeLoggable(this.lhsFullEjbqlId);
            Exception e = new IllegalExpressionException(7, l.getMessageText());
            this.term1.markExcAndAddCollectionException(e);
            this.addCollectionExceptionAndThrow(e);
         }

         this.lhsLastPathExpressionElement = QueryContext.getLastFieldFromId(this.lhsFullDealiasedEjbqlId);
         this.lhsNumberOfPathNodes = ((ExprID)this.term1).countPathNodes();
         lhsFirst = ((ExprID)this.term1).getFirstField();
         String rhsFirst = ((ExprID)this.term2).getFirstField();
         this.lhsRhsShareRoot = lhsFirst.equals(rhsFirst);
      }

      this.rhsEjbqlId = ((ExprID)this.term2).getEjbqlID();
      this.rhsDealiasedEjbqlId = ((ExprID)this.term2).getDealiasedEjbqlID();
      int rhsRelationshipType = 0;

      try {
         rhsRelationshipType = ((ExprID)this.term2).getRelationshipTypeForPathExpressionWithNoSQLGen();
      } catch (Exception var4) {
         this.term2.markExcAndAddCollectionException(var4);
         this.addCollectionExceptionAndThrow(var4);
      }

      if (rhsRelationshipType != 6 && rhsRelationshipType != 4) {
         l = EJBLogger.logfinderMemberRHSWrongTypeLoggable(this.rhsEjbqlId);
         e = new IllegalExpressionException(7, l.getMessageText());
         this.term2.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      }

      this.rhsSelectNodeMatch = this.queryTree.selectListRootMatch(this.rhsDealiasedEjbqlId);
      if (this.rhsSelectNodeMatch != null) {
         this.rhsSharesSelectRoot = true;
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      try {
         this.term2.calculate();
      } catch (Exception var10) {
         this.addCollectionException(var10);
      }

      this.throwCollectionException();
      this.preCalcSQLBuf = new StringBuffer();

      try {
         this.rhsBean = this.queryTree.getLastRDBMSBeanForPathExpressionWithNoSQLGen(this.rhsDealiasedEjbqlId);
      } catch (Exception var9) {
         this.term2.markExcAndAddCollectionException(var9);
         this.addCollectionExceptionAndThrow(var9);
      }

      this.rhsInterface = QueryContext.getInterfaceClass(this.rhsBean);
      this.rhsInterfaceName = this.rhsInterface.getName();
      this.rhsPKFieldList = this.rhsBean.getPrimaryKeyFields();
      this.rhsLastTableName = this.rhsBean.getTableName();
      String firstRHSfield = ((ExprID)this.term2).getFirstField();
      Loggable lhsPrevNode;
      if (!this.queryTree.isRangeVariableInScope(firstRHSfield)) {
         lhsPrevNode = EJBLogger.logejbqlMissingRangeVariableDeclarationLoggable(firstRHSfield);
         Exception e = new IllegalExpressionException(7, lhsPrevNode.getMessageText());
         this.term2.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      }

      if (this.lhsIsVariable) {
         try {
            this.globalContext.addFinderBeanInputParameter(((ExprVARIABLE)this.term1).getVariableNumber(), this.rhsBean);
         } catch (Exception var8) {
            this.term1.markExcAndAddCollectionException(var8);
            this.addCollectionExceptionAndThrow(var8);
         }
      } else {
         try {
            this.term1.calculate();
         } catch (Exception var7) {
            this.addCollectionException(var7);
         }

         this.throwCollectionException();

         try {
            this.lhsBean = this.queryTree.getLastRDBMSBeanForPathExpressionWithNoSQLGen(this.lhsFullDealiasedEjbqlId);
         } catch (Exception var6) {
            this.term1.markExcAndAddCollectionException(var6);
            this.addCollectionExceptionAndThrow(var6);
         }

         this.lhsInterface = QueryContext.getInterfaceClass(this.lhsBean);
         this.lhsInterfaceName = this.lhsInterface.getName();
         if (!this.lhsInterfaceName.equals(this.rhsInterfaceName)) {
            lhsPrevNode = EJBLogger.logfinderMemberMismatchLoggable(this.lhsInterfaceName, this.rhsInterfaceName);
            this.markExcAndThrowCollectionException(new Exception(lhsPrevNode.getMessageText()));
         }

         if (this.lhsRelationshipType != 2 && this.lhsRelationshipType != 5) {
            try {
               this.lhsExprIDForQuery = (ExprID)this.term1;
               this.lhsExprIDForQuery.prepareIdentifierForSQLGen();
            } catch (Exception var4) {
               this.term1.markExcAndAddCollectionException(var4);
               this.addCollectionExceptionAndThrow(var4);
            }
         } else {
            lhsPrevNode = null;

            try {
               this.truncatedLhsExprID = this.globalContext.setupForLHSForeignKeysWithNoReferenceToRHS((ExprID)this.term1);
               this.lhsExprIDForQuery = this.truncatedLhsExprID;
               JoinNode lhsPrevNode = this.queryTree.getJoinNodeForLastId(this.truncatedLhsExprID.getDealiasedEjbqlID());
               this.lhsPrevBean = lhsPrevNode.getRDBMSBean();
               this.lhsPrevTableName = this.lhsPrevBean.getTableForCmrField(this.lhsLastPathExpressionElement);
               this.lhsPrevTableAlias = lhsPrevNode.getFKTableAliasAndSQLForCmrf(this.lhsLastPathExpressionElement);
            } catch (Exception var5) {
               this.term1.markExcAndAddCollectionException(var5);
               this.addCollectionExceptionAndThrow(var5);
            }
         }
      }

      if (this.isMember) {
         if (this.lhsIsVariable) {
            this.compute_member_11_fk_on_rhs(this.preCalcSQLBuf);
            return;
         }

         switch (this.lhsRelationshipType) {
            case 1:
            case 3:
               this.compute_member_11_fk_on_rhs(this.preCalcSQLBuf);
               return;
            case 2:
            case 5:
               this.compute_member_11orN1_fk_on_lhs(this.preCalcSQLBuf);
               return;
            case 4:
            default:
               Exception e = new Exception("Internal Error,  [NOT] MEMBER [OF] cannot handle lhs relationship type number '" + this.lhsRelationshipType + "'  " + RDBMSUtils.relationshipTypeToString(this.lhsRelationshipType));
               this.term1.markExcAndAddCollectionException(e);
               this.addCollectionExceptionAndThrow(e);
         }
      }

      if (!this.isMember) {
         this.setup_not_member();
         QueryNode localSubqueryTree = this.setup_rhs_local_subquery();
         ExprID localSubqueryRHSExprID = ExprID.newInitExprID(this.globalContext, localSubqueryTree, this.rhsDealiasedEjbqlId);
         if (this.rhsPKFieldList.size() == 1) {
            this.compute_not_member_rhs_simple_key(this.preCalcSQLBuf, localSubqueryTree);
         } else {
            this.compute_not_member_rhs_compound_key(this.preCalcSQLBuf, localSubqueryTree, localSubqueryRHSExprID);
         }
      }

   }

   private void compute_member_11orN1_fk_on_lhs(StringBuffer sb) throws ErrorCollectionException {
      JoinNode rhsLastJoinNode = null;
      String rhsLastTableAlias = null;

      try {
         rhsLastJoinNode = this.queryTree.getJoinNodeForLastId(this.rhsDealiasedEjbqlId);
         rhsLastTableAlias = rhsLastJoinNode.getTableAlias();
      } catch (Exception var9) {
         this.term2.markExcAndAddCollectionException(var9);
         this.addCollectionExceptionAndThrow(var9);
      }

      Map fk2pkColMap = this.lhsPrevBean.getColumnMapForCmrfAndPkTable(this.lhsLastPathExpressionElement, this.rhsLastTableName);
      if (fk2pkColMap == null) {
         Exception e = new Exception("Internal Error !  In MEMBER OF: Could not find Map of Foreign Keys to Primary Keys for Foreign Key Bean: '" + this.lhsPrevBean.getEjbName() + "', To Primary Key Table Name: '" + this.rhsLastTableName + "'");
         this.term2.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      } else {
         Iterator it = fk2pkColMap.entrySet().iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String fkCol = (String)entry.getKey();
            String pkCol = (String)entry.getValue();
            sb.append(this.lhsPrevTableAlias).append(".").append(fkCol).append(" = ");
            sb.append(rhsLastTableAlias).append(".").append(pkCol).append(" ");
            if (it.hasNext()) {
               sb.append(" AND ");
            }
         }
      }

   }

   private void compute_member_11_fk_on_rhs(StringBuffer sb) throws ErrorCollectionException {
      Iterator it = this.rhsPKFieldList.iterator();

      while(it.hasNext()) {
         String pkField = (String)it.next();
         String lhsTableAndColumn = "?";
         if (!this.lhsIsVariable) {
            lhsTableAndColumn = ExprID.calcTableAndColumn(this.globalContext, this.queryTree, this.lhsFullDealiasedEjbqlId + "." + pkField);
         }

         sb.append(lhsTableAndColumn);
         sb.append(" = ");
         String rhsTableAndColumn = ExprID.calcTableAndColumn(this.globalContext, this.queryTree, this.rhsDealiasedEjbqlId + "." + pkField);
         sb.append(rhsTableAndColumn);
         if (it.hasNext()) {
            sb.append(" AND ");
         }
      }

      sb.append(" ");
   }

   private void setup_not_member() throws ErrorCollectionException {
      if (!this.lhsIsVariable && this.lhsExprIDForQuery.countPathNodes() > 1) {
         if (debugLogger.isDebugEnabled()) {
            debug(" doMember: LHS path node count for parsedLHSId: '" + this.lhsExprIDForQuery.getEjbqlID() + "'  = '" + this.lhsExprIDForQuery.countPathNodes() + "'");
         }

         String lhsIdForQuery = this.lhsExprIDForQuery.getDealiasedEjbqlID();
         boolean selectPathLongerThanParsedLHS = false;
         boolean foundSelectPath = false;
         String selectPath = "";
         Iterator it = this.queryTree.getSelectList().iterator();

         while(it.hasNext()) {
            SelectNode sn = (SelectNode)it.next();
            String id = sn.getSelectTarget();
            id = this.globalContext.replaceIdAliases(id);
            JoinNode jtree = null;

            try {
               jtree = this.queryTree.getJoinTreeForId(id);
               id = JoinNode.getPathWithoutTrailingCmpField(jtree, id);
            } catch (Exception var12) {
               this.markExcAndThrowCollectionException(var12);
            }

            int pathCheck = JoinNode.comparePaths(lhsIdForQuery, id);
            switch (pathCheck) {
               case -1:
                  break;
               case 0:
                  foundSelectPath = true;
                  selectPath = id;
                  break;
               case 1:
                  if (JoinNode.countPathNodes(id) < JoinNode.countPathNodes(lhsIdForQuery)) {
                     if (JoinNode.countPathNodes(id) > JoinNode.countPathNodes(selectPath)) {
                        foundSelectPath = true;
                        selectPath = id;
                     }
                  } else {
                     foundSelectPath = true;
                     selectPathLongerThanParsedLHS = true;
                  }
                  break;
               default:
                  Exception e = new Exception("Internal Error !  unknown return type from JoinNode.comparePaths encountered in NOT MEMBER OF calculation, while examining LHS: '" + ((ExprID)this.term1).getEjbqlID() + "' and SELECT id: '" + id + "'");
                  this.term1.markExcAndAddCollectionException(e);
                  this.addCollectionExceptionAndThrow(e);
            }
         }

         if (!foundSelectPath) {
            Loggable l = EJBLogger.logNotMemberOfLHSNotInSelectLoggable(((ExprID)this.term1).getEjbqlID());
            Exception e = new Exception(l.getMessageText());
            this.term1.markExcAndAddCollectionException(e);
            this.addCollectionExceptionAndThrow(e);
         }

         if (!selectPathLongerThanParsedLHS) {
            if (debugLogger.isDebugEnabled()) {
               debug(" doMember: selectPathLongerThanParsedLHS, mark LeftOuter Joins  from: '" + selectPath + "'  to  '" + lhsIdForQuery + "'");
            }

            try {
               JoinNode.markDoLeftOuterJoins(this.lhsExprIDForQuery.getJoinTreeForID(), selectPath, lhsIdForQuery);
            } catch (Exception var11) {
               this.term1.markExcAndAddCollectionException(var11);
               this.addCollectionExceptionAndThrow(var11);
            }
         }
      }

   }

   private QueryNode setup_rhs_local_subquery() throws ErrorCollectionException {
      QueryNode subqueryQueryTree = this.globalContext.newQueryNode((QueryNode)null, -1);
      JoinNode localSubqueryJoinTree = subqueryQueryTree.getJoinTree();
      RDBMSBean rdbmsBean = localSubqueryJoinTree.getRDBMSBean();
      String thisAbstractSchemaName = rdbmsBean.getAbstractSchemaName();
      if (thisAbstractSchemaName == null) {
         Loggable l = EJBLogger.logFinderCouldNotGetAbstractSchemaNameForBeanLoggable(rdbmsBean.getEjbName());
         Exception e = new Exception(l.getMessageText());
         this.term2.markExcAndAddCollectionException(e);
         this.addCollectionExceptionAndThrow(e);
      }

      String firstRHSfield = ((ExprID)this.term2).getFirstField();
      String abstractSchemaName = null;

      try {
         abstractSchemaName = this.globalContext.getGlobalRangeVariableMap(firstRHSfield);
      } catch (IllegalExpressionException var12) {
         this.term2.markExcAndAddCollectionException(var12);
         this.addCollectionExceptionAndThrow(var12);
      }

      RDBMSBean rhsAsBean = null;
      if (abstractSchemaName.equals(thisAbstractSchemaName)) {
         rhsAsBean = rdbmsBean;
      } else {
         rhsAsBean = rdbmsBean.getRDBMSBeanForAbstractSchema(abstractSchemaName);
         if (null == rhsAsBean) {
            Loggable l = EJBLogger.logFinderCouldNotGetRDBMSBeanForAbstractSchemaNameLoggable(abstractSchemaName);
            Exception e = new Exception(l.getMessageText());
            this.term2.markExcAndAddCollectionException(e);
            this.addCollectionExceptionAndThrow(e);
         }
      }

      assert rhsAsBean != null;

      String rhsRootTableName = rhsAsBean.getTableName();
      JoinNode jn = new JoinNode(localSubqueryJoinTree, firstRHSfield, rhsAsBean, rhsRootTableName, this.globalContext.registerTable(rhsRootTableName), -1, false, false, "", this.globalContext, new ArrayList());
      localSubqueryJoinTree.putChild(firstRHSfield, jn);

      try {
         subqueryQueryTree.addRangeVariable(firstRHSfield, abstractSchemaName);
      } catch (IllegalExpressionException var11) {
         this.term2.markExcAndAddCollectionException(var11);
         this.addCollectionExceptionAndThrow(var11);
      }

      return subqueryQueryTree;
   }

   private void compute_not_member_rhs_simple_key(StringBuffer sb, QueryNode localSubqueryTree) throws ErrorCollectionException {
      String rhsPkField = (String)this.rhsPKFieldList.iterator().next();
      String rhsSubqueryTableAndColumn = ExprID.calcTableAndColumn(this.globalContext, localSubqueryTree, this.rhsDealiasedEjbqlId + "." + rhsPkField);
      String outerTargetId;
      if (this.lhsRelationshipType != 2 && this.lhsRelationshipType != 5) {
         rhsPkField = (String)this.rhsPKFieldList.iterator().next();
         String lhsOuterqueryTableAndColumn = "?";
         if (!this.lhsIsVariable) {
            lhsOuterqueryTableAndColumn = ExprID.calcTableAndColumn(this.globalContext, this.queryTree, this.lhsFullDealiasedEjbqlId + "." + rhsPkField);
         }

         sb.append(lhsOuterqueryTableAndColumn).append(" ");
      } else {
         if (this.truncatedLhsExprID == null) {
            Exception e = new Exception("Internal Error, in compute_not_member_rhs_simple_key truncatedLhsExprID is NULL.  It should NOT be.");
            this.term1.markExcAndAddCollectionException(e);
            this.addCollectionExceptionAndThrow(e);
         }

         Map fk2pkColMap = this.lhsPrevBean.getColumnMapForCmrfAndPkTable(this.lhsLastPathExpressionElement, this.rhsLastTableName);
         if (fk2pkColMap == null) {
            Exception e = new Exception("Internal Error !  In NOT MEMBER OF: Could not find Map of Foreign Keys to Primary Keys for Foreign Key Bean: '" + this.lhsPrevBean.getEjbName() + "', To Primary Key Table Name: '" + this.rhsLastTableName + "'");
            this.term1.markExcAndAddCollectionException(e);
            this.addCollectionExceptionAndThrow(e);
         } else {
            outerTargetId = (String)fk2pkColMap.keySet().iterator().next();
            sb.append(this.lhsPrevTableAlias).append(".").append(outerTargetId).append(" ");
         }
      }

      sb.append("NOT IN ");
      sb.append("(SELECT ");
      sb.append(rhsSubqueryTableAndColumn).append(" ");
      sb.append("FROM ");
      StringBuffer sb2 = new StringBuffer();

      try {
         sb.append(localSubqueryTree.getFROMDeclaration(0));
         sb2.append(localSubqueryTree.getMainJoinBuffer());
      } catch (Exception var10) {
         this.markExcAndThrowCollectionException(var10);
      }

      if (this.lhsRhsShareRoot) {
         outerTargetId = this.getSubQueryCorrelatedRootSQL(this.lhsExprIDForQuery.getDealiasedEjbqlID(), this.rhsDealiasedEjbqlId, this.queryTree, localSubqueryTree);
         if (outerTargetId.length() > 0) {
            if (sb2.length() > 0) {
               sb2.append(" AND ");
            }

            sb2.append(outerTargetId);
         }
      }

      if (this.rhsSharesSelectRoot) {
         outerTargetId = this.rhsSelectNodeMatch.getSelectTarget();
         JoinNode outerTargetJoinTree = null;

         try {
            this.queryTree.getJoinTreeForId(outerTargetId);
         } catch (Exception var9) {
            this.markExcAndThrowCollectionException(var9);
         }

         String join = this.getSubQueryCorrelatedRootSQL(outerTargetId, this.rhsDealiasedEjbqlId, this.queryTree, localSubqueryTree);
         if (join.length() > 0) {
            if (sb2.length() > 0) {
               sb2.append(" AND ");
            }

            sb2.append(join);
         }
      }

      if (sb2.length() > 0) {
         sb.append("WHERE ");
         sb.append(sb2);
      }

      sb.append(") ");
   }

   private void compute_not_member_rhs_compound_key(StringBuffer sb, QueryNode localSubqueryTree, ExprID localSubqueryRHSExprID) throws ErrorCollectionException {
      JoinNode rhsSubqueryLastJoinNode = null;

      try {
         rhsSubqueryLastJoinNode = localSubqueryTree.getJoinNodeForLastId(this.rhsDealiasedEjbqlId);
      } catch (Exception var17) {
         this.term2.markExcAndAddCollectionException(var17);
         this.addCollectionExceptionAndThrow(var17);
      }

      String rhsSubqueryLastTableAlias = rhsSubqueryLastJoinNode.getTableAlias();
      String rhsSubqueryLastTableName = rhsSubqueryLastJoinNode.getTableName();
      sb.append("NOT ");
      sb.append("EXISTS ");
      sb.append("(SELECT ");
      Map.Entry outerTargetJoinTree;
      String join;
      String outerTargetId;
      if (this.lhsRelationshipType != 2 && this.lhsRelationshipType != 5) {
         Iterator it = this.rhsPKFieldList.iterator();
         outerTargetId = (String)it.next();
         String rhsSubqueryTableAndColumn = ExprID.calcTableAndColumn(this.globalContext, localSubqueryTree, this.rhsDealiasedEjbqlId + "." + outerTargetId);
         sb.append(rhsSubqueryTableAndColumn).append(" ");
         sb.append("FROM ");

         try {
            sb.append(localSubqueryTree.getFROMDeclaration(0));
         } catch (Exception var15) {
            this.markExcAndThrowCollectionException(var15);
         }

         sb.append("WHERE ");
         it = this.rhsPKFieldList.iterator();

         while(it.hasNext()) {
            outerTargetId = (String)it.next();
            join = "?";
            if (!this.lhsIsVariable) {
               join = ExprID.calcTableAndColumn(this.globalContext, this.queryTree, this.lhsFullDealiasedEjbqlId + "." + outerTargetId);
            }

            sb.append(join);
            sb.append(" = ");
            rhsSubqueryTableAndColumn = ExprID.calcTableAndColumn(this.globalContext, localSubqueryTree, this.rhsDealiasedEjbqlId + "." + outerTargetId);
            sb.append(rhsSubqueryTableAndColumn);
            if (it.hasNext()) {
               sb.append(" AND ");
            } else {
               sb.append(" ");
            }
         }
      } else {
         Map fk2pkColMap = this.lhsPrevBean.getColumnMapForCmrfAndPkTable(this.lhsLastPathExpressionElement, rhsSubqueryLastTableName);
         if (fk2pkColMap == null) {
            Exception e = new Exception("Internal Error !  In NOT MEMBER OF: Could not find Map of Foreign Keys to Primary Keys for Foreign Key Bean: '" + this.lhsPrevBean.getEjbName() + "', To Primary Key Table Name: '" + this.rhsLastTableName + "'");
            this.term2.markExcAndAddCollectionException(e);
            this.addCollectionExceptionAndThrow(e);
         } else {
            Iterator it = fk2pkColMap.entrySet().iterator();
            outerTargetJoinTree = (Map.Entry)it.next();
            join = (String)outerTargetJoinTree.getKey();
            String pkCol = (String)outerTargetJoinTree.getValue();
            sb.append(rhsSubqueryLastTableAlias).append(".").append(pkCol).append(" ");
            sb.append("FROM ");

            try {
               sb.append(localSubqueryTree.getFROMDeclaration(0));
            } catch (Exception var16) {
               this.term2.markExcAndAddCollectionException(var16);
               this.addCollectionExceptionAndThrow(var16);
            }

            sb.append("WHERE ");
            sb.append(this.lhsPrevTableAlias).append(".").append(join);
            sb.append(" = ");
            sb.append(rhsSubqueryLastTableAlias).append(".").append(pkCol);

            while(it.hasNext()) {
               sb.append(" AND ");
               join = (String)((Map.Entry)it.next()).getKey();
               pkCol = (String)((Map.Entry)it.next()).getValue();
               sb.append(this.lhsPrevTableAlias).append(".").append(join);
               sb.append(" = ");
               sb.append(rhsSubqueryLastTableAlias).append(".").append(pkCol);
            }

            sb.append(" ");
         }
      }

      StringBuffer sb2 = new StringBuffer();

      try {
         sb2.append(localSubqueryTree.getMainJoinBuffer());
      } catch (Exception var14) {
         this.markExcAndThrowCollectionException(var14);
      }

      if (this.lhsRhsShareRoot) {
         outerTargetId = this.getSubQueryCorrelatedRootSQL(this.lhsExprIDForQuery.getDealiasedEjbqlID(), this.rhsDealiasedEjbqlId, this.queryTree, localSubqueryTree);
         if (outerTargetId.length() > 0) {
            if (sb2.length() > 0) {
               sb2.append(" AND ");
            }

            sb2.append(outerTargetId);
         }
      }

      if (this.rhsSharesSelectRoot) {
         outerTargetId = this.rhsSelectNodeMatch.getSelectTarget();
         outerTargetJoinTree = null;

         try {
            this.queryTree.getJoinTreeForId(outerTargetId);
         } catch (Exception var13) {
            this.markExcAndThrowCollectionException(var13);
         }

         join = this.getSubQueryCorrelatedRootSQL(outerTargetId, this.rhsDealiasedEjbqlId, this.queryTree, localSubqueryTree);
         if (join.length() > 0) {
            if (sb2.length() > 0) {
               sb2.append(" AND ");
            }

            sb2.append(join);
         }
      }

      if (sb2.length() > 0) {
         sb.append("AND ");
         sb.append(sb2);
      }

      sb.append(") ");
   }

   private String getSubQueryCorrelatedRootSQL(String lhsId, String rhsId, QueryNode lhsQueryTree, QueryNode rhsQueryTree) throws ErrorCollectionException {
      StringBuffer sb = new StringBuffer();
      JoinNode lhsJoinTree = lhsQueryTree.getJoinTree();
      String firstLHSId = JoinNode.getFirstFieldFromId(lhsId);
      String firstRHSId = JoinNode.getFirstFieldFromId(rhsId);
      JoinNode firstNode = null;
      RDBMSBean firstBean = null;

      try {
         firstNode = JoinNode.getFirstNode(lhsJoinTree, lhsId);
         firstBean = firstNode.getRDBMSBean();
      } catch (Exception var16) {
         this.markExcAndThrowCollectionException(var16);
      }

      List firstPKFieldList = firstBean.getPrimaryKeyFields();
      Iterator it = firstPKFieldList.iterator();

      while(it.hasNext()) {
         String pkField = (String)it.next();
         String lhsTableAndColumn = ExprID.calcTableAndColumn(this.globalContext, lhsQueryTree, firstLHSId + "." + pkField);
         String rhsTableAndColumn = ExprID.calcTableAndColumn(this.globalContext, rhsQueryTree, firstRHSId + "." + pkField);
         sb.append(lhsTableAndColumn).append(" = ").append(rhsTableAndColumn);
         if (it.hasNext()) {
            sb.append(" AND ");
         }
      }

      return sb.toString();
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      ExprISMEMBER newExprISMEMBER;
      if (this.isMember) {
         newExprISMEMBER = new ExprISMEMBER(63, this.term1, (ExprID)this.term2, false);
         newExprISMEMBER.setPreEJBQLFrom(this);
         newExprISMEMBER.setMainEJBQL("IS NOT MEMBER ");
         newExprISMEMBER.setPostEJBQLFrom(this);
         return newExprISMEMBER;
      } else {
         newExprISMEMBER = new ExprISMEMBER(62, this.term1, (ExprID)this.term2, true);
         newExprISMEMBER.setPreEJBQLFrom(this);
         newExprISMEMBER.setMainEJBQL("IS MEMBER ");
         newExprISMEMBER.setPostEJBQLFrom(this);
         return newExprISMEMBER;
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

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      if (this.preCalcSQLBuf == null) {
         throw new ErrorCollectionException("Internal Error !  ExprISMEMBER.toSQL  preCalcSQLBuf is null !");
      } else {
         return this.preCalcSQLBuf.toString();
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[ExprISMEMBER] " + s);
   }
}
