package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RelationshipCaching;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public final class ExprSELECT extends BaseExpr implements Expr, ExpressionTypes {
   private boolean processSelectListForInitializationDone = false;
   private List completeSelectList = null;

   protected ExprSELECT(int type, Expr expr1, Vector terms) {
      super(type, expr1, terms);
      this.debugClassName = "ExprSELECT";
   }

   public void init_method() throws ErrorCollectionException {
      this.setMainQuerySelectDistinct();
      this.setMainQueryResultSetFinder();
      this.createEjbQLSelectList();
      this.createRelationshipCachingList();
      this.processSelectListForInitialization();
      this.processSelectPathsEndingInCmrFields();
      this.mainQueryResultSetFinderCheck();
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

      Enumeration en = this.terms.elements();

      while(en.hasMoreElements()) {
         Expr expr = (Expr)en.nextElement();
         expr.appendEJBQLTokens(l);
         if (en.hasMoreElements()) {
            this.appendNewEJBQLTokenToList(", ", l);
         }
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL(Properties props) {
      return "";
   }

   private void setMainQuerySelectDistinct() {
      if (this.queryTree.isMainQuery()) {
         if (this.term1.type() == 51) {
            this.globalContext.setMainQuerySelectDistinct();
         }

      }
   }

   private void createEjbQLSelectList() throws ErrorCollectionException {
      Vector targets = this.terms;
      if (targets.size() < 1) {
         Loggable l = EJBLogger.logExpressionRequiresXLoggable("SELECT", "target");
         IllegalExpressionException ex = new IllegalExpressionException(7, l.getMessageText());
         this.markExcAndThrowCollectionException(ex);
      }

      Iterator it = targets.iterator();

      while(it.hasNext()) {
         Expr expr = (Expr)it.next();
         expr.init(this.globalContext, this.queryTree);
         SelectNode sn = new SelectNode();
         this.queryTree.addSelectList(sn);
         sn.setSelectItemBaseExpr(expr);
         if (debugLogger.isDebugEnabled()) {
            debug(" create Select list item for: '" + TYPE_NAMES[expr.type()] + "'");
         }

         if (expr instanceof ExprAGGREGATE) {
            sn.setIsAggregate(true);
            if (expr.getTerm2() != null) {
               expr.getTerm2().init(this.globalContext, this.queryTree);
               if (expr.getTerm2().type() == 51) {
                  sn.setIsAggregateDistinct(true);
               }
            }
         }

         IllegalExpressionException exprID;
         if (expr instanceof ExprCASE && !(expr.getTerm1() instanceof ExprID)) {
            Loggable l = EJBLogger.logejbqlSelectCaseMustBePathExpressionLoggable(this.globalContext.getEjbName(), expr.getTerm1().getMainEJBQL());
            exprID = new IllegalExpressionException(7, l.getMessageText());
            expr.getTerm1().markExcAndThrowCollectionException(exprID);
         }

         sn.setSelectType(expr.type());
         sn.setSelectTypeName(ExpressionTypes.TYPE_NAMES[expr.type()]);
         String pathExpressionArg = "";
         exprID = null;

         try {
            ExprID exprID = BaseExpr.getExprIDFromSingleExprIDHolder(expr);
            exprID.init(this.globalContext, this.queryTree);
            pathExpressionArg = exprID.getEjbqlID();
            if (debugLogger.isDebugEnabled()) {
               debug(" got from ExprID: '" + pathExpressionArg + "'");
            }

            if (expr instanceof ExprAGGREGATE) {
               ((ExprAGGREGATE)expr).validate();
            }
         } catch (Exception var8) {
            this.markExcAndAddCollectionException(var8);
            continue;
         }

         sn.setSelectTarget(pathExpressionArg);
      }

      this.throwCollectionException();
   }

   private void processSelectListForInitialization() throws ErrorCollectionException {
      Iterator it = this.getCompleteSelectList().iterator();

      while(it.hasNext()) {
         SelectNode sn = (SelectNode)it.next();
         Expr expr = sn.getSelectItemBaseExpr();
         if (debugLogger.isDebugEnabled()) {
            debug("  processSelectListForInitialization  Expr: '" + TYPE_NAMES[expr.type()] + "'");
         }

         try {
            ExprID exprID = BaseExpr.getExprIDFromSingleExprIDHolder(expr);
            exprID.init(this.globalContext, this.queryTree);
            String id = exprID.getEjbqlID();
            int pathNodeCount;
            Loggable l;
            IllegalExpressionException ex;
            if (expr.type() == 61) {
               pathNodeCount = exprID.countPathNodes();
               if (pathNodeCount == 1 && !this.queryTree.containsCollectionMember(id) && this.queryTree.getRangeVariableMap(id) == null) {
                  l = EJBLogger.logejbqlSelectObjectMustBeRangeOrCollectionIdLoggable(id);
                  ex = new IllegalExpressionException(7, l.getMessageText());
                  exprID.markExcAndThrowCollectionException(ex);
               }
            }

            if (expr.type() == 17) {
               pathNodeCount = exprID.countPathNodes();
               if (pathNodeCount < 2) {
                  if (this.queryTree.thisQueryNodeOwnsId(id)) {
                     l = EJBLogger.logejbqlSELECTmustUseOBJECTargumentLoggable(id);
                     ex = new IllegalExpressionException(6, l.getMessageText());
                     this.globalContext.addWarning(ex);
                  } else {
                     l = EJBLogger.logInvalidEJBQLSELECTExpressionLoggable(id);
                     ex = new IllegalExpressionException(7, l.getMessageText());
                     exprID.markExcAndThrowCollectionException(ex);
                  }
               }
            }

            if (debugLogger.isDebugEnabled()) {
               debug("  about to  exprID.prepareIdentifierForSQLGen()  Expr: '" + TYPE_NAMES[exprID.type()] + "' val: '" + exprID.getEjbqlID() + "'");
            }

            exprID.prepareIdentifierForSQLGen();
            Expr aggExpr;
            if (this.globalContext.identifierIsRangeVariable(id)) {
               if (debugLogger.isDebugEnabled()) {
                  debug("  '" + id + "' is Range Variable");
               }

               if (!sn.getIsAggregate() && !sn.getIsAggregateDistinct()) {
                  this.initializeRangeVariable(sn, exprID);
               } else {
                  aggExpr = sn.getSelectItemBaseExpr();
                  if (aggExpr.type() == 48) {
                     this.initializeCountRangeVariable(sn, exprID);
                  }
               }
            } else if (exprID.isPathExpressionEndingInCmpFieldWithNoSQLGen()) {
               this.initializeCmpField(sn, exprID);
            } else if (!sn.getIsAggregate() && !sn.getIsAggregateDistinct()) {
               this.initializeCmrField(sn, exprID);
            } else {
               aggExpr = sn.getSelectItemBaseExpr();
               if (aggExpr.type() == 48) {
                  this.initializeCountCmrField(sn, exprID);
               }
            }
         } catch (ErrorCollectionException var9) {
            this.addCollectionException(var9);
         }
      }

      this.processSelectListForInitializationDone = true;
      this.throwCollectionException();
   }

   private void processSelectPathsEndingInCmrFields() throws ErrorCollectionException {
      if (!this.processSelectListForInitializationDone) {
         throw new AssertionError(" processSelectsOnPathsEndingInCmrFields() can  only be called after processSelectListForInitialization() has completed. \n The globalContext \n   QueryIsFinderLocalBean or \n   QueryIsSelectLocalBean \n information must be determined by processSelectListForInitialization()  first before calling processSelectsOnPathsEndingInCmrFields()");
      } else if (this.queryTree.isMainQuery()) {
         if (this.globalContext.getQueryIsSelectThisBean() || this.globalContext.getQueryIsSelectLocalBean() || this.globalContext.getQueryIsFinderLocalBean()) {
            List list = this.getCompleteSelectList();
            if (list.size() <= 1) {
               Iterator it = list.iterator();
               SelectNode sn = (SelectNode)it.next();
               Expr expr = sn.getSelectItemBaseExpr();
               ExprID exprID = BaseExpr.getExprIDFromSingleExprIDHolder(expr);
               String dealiasedId = exprID.getDealiasedEjbqlID();
               int pathNodeCount = exprID.countPathNodes();
               if (pathNodeCount > 1) {
                  int startPathNodeCount = pathNodeCount - 1;
                  String startPath = JoinNode.getFirstNFieldsFromId(dealiasedId, startPathNodeCount);
                  String endPath = dealiasedId;
                  JoinNode joinTree = this.queryTree.getJoinTree();

                  try {
                     JoinNode.markDoLeftOuterJoins(joinTree, startPath, endPath);
                  } catch (IllegalExpressionException var15) {
                     Loggable l = EJBLogger.logMayNotComplyWithEJB21_11_2_7_1_mustReturnAnyNullBeansLoggable(dealiasedId);
                     IllegalExpressionException ex = new IllegalExpressionException(6, l.getMessageText());
                     this.globalContext.addWarning(ex);
                  }

               }
            }
         }
      }
   }

   private void initializeCountRangeVariable(SelectNode sn, ExprID exprID) throws ErrorCollectionException {
      String id = exprID.getEjbqlID();

      assert id.indexOf(".") == -1 : "Attempt to call initializeCountRangeVariable on a path expression with more than 1 path member '" + id + "'";

      JoinNode rangeVariableJoinNode = this.getRangeVariableJoinNode(exprID);
      RDBMSBean targetBean = rangeVariableJoinNode.getRDBMSBean();
      List pkFieldList = targetBean.getPrimaryKeyFields();
      String pkField = (String)pkFieldList.get(0);
      id = id + "." + pkField;
      if (debugLogger.isDebugEnabled()) {
         debug("  COUNT new argument is: '" + id + "'");
      }

      exprID.setEjbqlID(id);
      exprID.init(this.globalContext, this.queryTree);
      exprID.prepareIdentifierForSQLGen();
      this.initializeCmpField(sn, exprID);
   }

   private void initializeRangeVariable(SelectNode sn, ExprID exprID) throws ErrorCollectionException {
      JoinNode rangeVariableJoinNode = this.getRangeVariableJoinNode(exprID);
      sn.setDbmsTarget(rangeVariableJoinNode.getTableAlias());
      RDBMSBean targetBean = rangeVariableJoinNode.getRDBMSBean();
      sn.setSelectBean(targetBean);
      int forceType = 61;
      sn.setSelectType(forceType);
      sn.setSelectTypeName(ExpressionTypes.TYPE_NAMES[forceType]);
      if (this.globalContext.queryIsSelect() || this.globalContext.queryIsSelectInEntity()) {
         if (targetBean.getEjbName().equals(this.globalContext.getRDBMSBean().getEjbName())) {
            if (this.queryTree.isMainQuery()) {
               this.globalContext.setQueryIsSelectThisBean();
            }

            this.queryTree.setQueryType(2);
         } else {
            if (this.queryTree.isMainQuery()) {
               this.globalContext.setQueryIsSelectLocalBean();
            }

            this.queryTree.setQueryType(4);
         }

         if (this.queryTree.isMainQuery()) {
            this.globalContext.setQuerySelectBeanTarget(targetBean);
         }
      }

   }

   private void initializeCmpField(SelectNode sn, ExprID exprID) throws ErrorCollectionException {
      String id = exprID.getEjbqlID();
      if (debugLogger.isDebugEnabled()) {
         debug("  EJB QL SELECT CLAUSE: " + id + "  is a field");
      }

      if (sn.getSelectType() == 61) {
         Loggable l = EJBLogger.logejbqlSelectObjectMustBeIdentificationVarNotCMPFieldLoggable(exprID.getEjbqlID());
         IllegalExpressionException ex = new IllegalExpressionException(7, l.getMessageText());
         exprID.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      String selectTableAndField = exprID.calcTableAndColumnForCmpField();
      if (debugLogger.isDebugEnabled()) {
         debug("  SELECT Table and Field : " + selectTableAndField);
      }

      if (this.queryTree.isMainQuery()) {
         this.globalContext.setQuerySelectFieldTableAndColumn(selectTableAndField);
      }

      sn.setDbmsTarget(selectTableAndField);
      JoinNode node = exprID.getJoinNodeForLastCmrFieldWithSQLGen();
      RDBMSBean targetBean = node.getRDBMSBean();
      sn.setSelectBean(targetBean);
      String cmpField = exprID.getLastField();
      Class fClass = targetBean.getCMPBeanDescriptor().getFieldClass(cmpField);
      if (fClass == null) {
         Loggable l = EJBLogger.logFinderCouldNotGetClassForIdBeanLoggable("SELECT", cmpField, exprID.getEjbqlID(), targetBean.getEjbName());
         IllegalExpressionException ex = new IllegalExpressionException(7, l.getMessageText());
         exprID.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      if (this.queryTree.isMainQuery()) {
         this.globalContext.setQuerySelectBeanTarget(targetBean);
         this.globalContext.setQuerySelectFieldClass(fClass);
      }

      sn.setSelectClass(fClass);
      if (id.indexOf(".") == -1) {
         this.queryTree.setQueryType(3);
         if (this.queryTree.isMainQuery()) {
            this.globalContext.setQueryIsSelectThisBeanField();
         }
      } else {
         this.queryTree.setQueryType(5);
         if (this.queryTree.isMainQuery()) {
            this.globalContext.setQueryIsSelectLocalBeanField();
         }
      }

   }

   private void initializeCountCmrField(SelectNode sn, ExprID exprID) throws ErrorCollectionException {
      String id = exprID.getEjbqlID();

      assert id.indexOf(".") != -1 : "Attempt to call initializeCountCmrField on a path expression with only 1 path member '" + id + "'";

      JoinNode lastNode = null;

      try {
         lastNode = exprID.getJoinNodeForLastCmrFieldWithSQLGen();
      } catch (Exception var8) {
         exprID.markExcAndAddCollectionException(var8);
         this.addCollectionExceptionAndThrow(var8);
      }

      RDBMSBean targetBean = lastNode.getRDBMSBean();
      List pkFieldList = targetBean.getPrimaryKeyFields();
      String pkField = (String)pkFieldList.get(0);
      id = id + "." + pkField;
      if (debugLogger.isDebugEnabled()) {
         debug("  COUNT new argument is: '" + id + "'");
      }

      exprID.setEjbqlID(id);
      exprID.init(this.globalContext, this.queryTree);
      exprID.prepareIdentifierForSQLGen();
      this.initializeCmpField(sn, exprID);
   }

   private void initializeCmrField(SelectNode sn, ExprID exprID) throws ErrorCollectionException {
      if (debugLogger.isDebugEnabled()) {
         debug("  EJB QL SELECT CLAUSE: " + exprID.getEjbqlID() + "  is not a field, it must be a Bean");
      }

      int forceType = 61;
      sn.setSelectType(forceType);
      sn.setSelectTypeName(ExpressionTypes.TYPE_NAMES[forceType]);
      JoinNode lastNode = null;

      try {
         lastNode = exprID.getJoinNodeForLastCmrFieldWithSQLGen();
      } catch (Exception var11) {
         exprID.markExcAndAddCollectionException(var11);
         this.addCollectionExceptionAndThrow(var11);
      }

      RDBMSBean targetBean = lastNode.getRDBMSBean();
      if (this.queryTree.isMainQuery()) {
         this.globalContext.setQuerySelectBeanTarget(targetBean);
      }

      sn.setSelectBean(targetBean);
      sn.setDbmsTarget(lastNode.getTableAlias());
      if (!this.globalContext.queryIsSelect() && !this.globalContext.queryIsSelectInEntity()) {
         if (this.queryTree.isMainQuery() && !targetBean.getEjbName().equals(this.globalContext.getRDBMSBean().getEjbName()) && !this.queryTree.containsInSelectListForCachingElement(sn)) {
            Loggable l = EJBLogger.logFinderSelectWrongBeanLoggable(this.globalContext.getFinderMethodName(), this.globalContext.getRDBMSBean().getEjbName(), targetBean.getEjbName());
            IllegalExpressionException ex = new IllegalExpressionException(7, l.getMessageText());
            exprID.markExcAndAddCollectionException(ex);
            this.addCollectionExceptionAndThrow(ex);
         }

         this.queryTree.setQueryType(0);
         if (this.queryTree.isMainQuery()) {
            this.globalContext.setQueryIsFinderLocalBean();
         }

         if (debugLogger.isDebugEnabled()) {
            debug("  EJB QL FINDER LOCAL BEAN set to " + targetBean.getEjbName());
         }

         List l = JoinNode.getTableAliasListFromChildren(this.queryTree.getJoinTree());
         int DefaultTableCount = 0;
         String DefaultTableString = "WL0";
         Iterator it2 = l.iterator();

         while(it2.hasNext()) {
            String alias = (String)it2.next();
            if (alias != null && alias.equals(DefaultTableString)) {
               ++DefaultTableCount;
            }
         }

         if (DefaultTableCount == 0) {
            this.queryTree.addTableAliasExclusionList(DefaultTableString);
         }
      } else {
         this.queryTree.setQueryType(4);
         if (this.queryTree.isMainQuery()) {
            this.globalContext.setQueryIsSelectLocalBean();
         }

         if (debugLogger.isDebugEnabled()) {
            debug("  EJB QL SELECT LOCAL BEAN set to " + targetBean.getEjbName());
         }
      }

   }

   private JoinNode getRangeVariableJoinNode(ExprID exprID) throws ErrorCollectionException {
      exprID.prepareIdentifierForSQLGen();
      String id = exprID.getEjbqlID();
      JoinNode rangeVariableJoinNode = null;

      try {
         rangeVariableJoinNode = this.queryTree.getJoinNodeForFirstId(id);
      } catch (Exception var6) {
         exprID.markExcAndThrowCollectionException(var6);
      }

      if (rangeVariableJoinNode == null) {
         Loggable l = EJBLogger.logFinderSelectTargetNoJoinNodeLoggable(id);
         IllegalExpressionException ex = new IllegalExpressionException(7, l.getMessageText());
         exprID.markExcAndAddCollectionException(ex);
         this.addCollectionExceptionAndThrow(ex);
      }

      return rangeVariableJoinNode;
   }

   private void setMainQueryResultSetFinder() {
      if (this.queryTree.isMainQuery() && this.globalContext.isResultSetFinder()) {
         this.globalContext.setMainQueryResultSetFinder();
      }

   }

   private void mainQueryResultSetFinderCheck() throws ErrorCollectionException {
      if (this.queryTree.isMainQuery() && this.globalContext.isResultSetFinder()) {
         Iterator it = this.queryTree.getSelectList().iterator();

         while(it.hasNext()) {
            SelectNode sn = (SelectNode)it.next();
            if (sn.getSelectType() == 61) {
               Loggable log = EJBLogger.logejbqlResultSetFinderCannotSelectBeansLoggable(sn.getSelectTarget(), sn.getSelectTypeName());
               Exception ex = new IllegalExpressionException(7, log.getMessageText());
               this.markExcAndThrowCollectionException(ex);
            }
         }
      }

   }

   private List getCompleteSelectList() {
      if (this.completeSelectList != null) {
         return this.completeSelectList;
      } else {
         List completeSelectList = new ArrayList();
         completeSelectList.addAll(this.queryTree.getSelectList());
         completeSelectList.addAll(this.queryTree.getSelectListForCachingElement());
         return completeSelectList;
      }
   }

   private void createRelationshipCachingList() throws ErrorCollectionException {
      if (this.queryTree.isMainQuery()) {
         String cachingName = this.globalContext.getRelationshipCachingName();
         if (cachingName != null) {
            this.parseRelationshipCaching(this.globalContext.getRDBMSBean(), cachingName);
         }
      }
   }

   public void parseRelationshipCaching(RDBMSBean rdbmsBean, String cachingName) throws ErrorCollectionException {
      RelationshipCaching caching = rdbmsBean.getRelationshipCaching(cachingName);
      if (caching != null) {
         SelectNode sn = (SelectNode)this.queryTree.getSelectList().get(0);
         if (sn.getSelectType() != 61) {
            Loggable l = EJBLogger.logrelationshipCachingCannotEnableIfSelectTypeIsNotObjectLoggable(rdbmsBean.getEjbName(), this.globalContext.getFinderMethodName());
            Exception ex = new IllegalExpressionException(7, l.getMessageText());
            this.markExcAndThrowCollectionException(ex);
         }

         Iterator cachingElements = caching.getCachingElements().iterator();
         this.parseCachingElements(rdbmsBean, cachingElements, sn.getSelectTarget());
      }
   }

   private void parseCachingElements(RDBMSBean prevBean, Iterator cachingElements, String selectTargetName) throws ErrorCollectionException {
      while(true) {
         try {
            if (cachingElements.hasNext()) {
               RelationshipCaching.CachingElement cachingElement = (RelationshipCaching.CachingElement)cachingElements.next();
               String cachingElementCmrField = cachingElement.getCmrField();
               String cachingElementGroupName = cachingElement.getGroupName();
               RDBMSBean cachingElementBean = prevBean.getRelatedRDBMSBean(cachingElementCmrField);
               SelectNode sn = new SelectNode();
               String targetName = selectTargetName + "." + cachingElementCmrField;
               sn.setSelectTarget(targetName);
               sn.setSelectBean(cachingElementBean);
               sn.setCachingElementGroupName(cachingElementGroupName);
               sn.setPrevBean(prevBean);
               ExprID exprID = new ExprID(17, targetName);
               exprID.init(this.globalContext, this.queryTree);
               sn.setSelectItemBaseExpr(exprID);
               this.queryTree.addSelectListForCachingElement(sn);
               Iterator cachingElementNested = cachingElement.getCachingElements().iterator();
               if (cachingElementNested.hasNext()) {
                  this.parseCachingElements(cachingElementBean, cachingElementNested, selectTargetName + "." + cachingElementCmrField);
               }
               continue;
            }
         } catch (Exception var12) {
            this.markExcAndThrowCollectionException(var12);
         }

         return;
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[ExprSELECT] " + s);
   }
}
