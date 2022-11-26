package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb20.cmp.rdbms.finders.EJBQLCompilerException;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public class QueryContext {
   private static final DebugLogger debugLogger;
   private RDBMSBean bean;
   private EjbqlFinder finder;
   private Expr exprTree;
   private QueryNode queryTree;
   private Map tableAliasMap = new HashMap();
   private int tableAliasCount;
   private Map idAliasMap;
   private String originalEjbql;
   private boolean ejbqlRewritten = false;
   private int ejbqlRewrittenReasons = 0;
   private ErrorCollectionException warnings;

   public QueryContext(RDBMSBean b, EjbqlFinder f, Expr r) {
      this.bean = b;
      this.finder = f;
      this.exprTree = r;
      JoinNode joinTree = JoinNode.makeJoinRoot(this.bean, this);
      this.queryTree = new QueryNode(this.finder, this, (QueryNode)null, joinTree);
   }

   public void generateQuery() throws EJBQLCompilerException {
      try {
         this.originalEjbql = ((ExprROOT)this.exprTree).getEJBQLText();
         this.factorOutNOT();
         this.setupForORCrossProducts();
         this.exprTree.init(this, this.getMainQueryTree());
         this.exprTree.calculate();
      } catch (ErrorCollectionException var3) {
         EJBQLCompilerException exx = this.newEJBQLCompilerException(var3);
         if (debugLogger.isDebugEnabled()) {
            debug("\n\n\n\n");
            debug(" generateQuery() encountered Exception, process Exceptions !");
            debug(exx.getMessage() + "\n\n");
            debug("\n\n\n\n");
         }

         throw exx;
      }
   }

   private void factorOutNOT() throws ErrorCollectionException {
      if (RewriteEjbqlNOT.hasNOTExpr((BaseExpr)this.exprTree)) {
         this.ejbqlRewritten = true;
         this.ejbqlRewrittenReasons |= 1;
         if (debugLogger.isDebugEnabled()) {
            debug("\n\n -----------  finder: '" + this.finder.getName() + "'");
            debug("\n\n------------------------------------------QueryContext finder method '" + this.finder.getName() + "' before rewriteNOT \n");
            debug(this.exprTree.dumpString());
            debug("\n");
         }

         this.exprTree = RewriteEjbqlNOT.rewriteEjbqlNOT((ExprROOT)this.exprTree, debugLogger.isDebugEnabled());
         if (debugLogger.isDebugEnabled()) {
            debug("\n\n QueryContext finder method '" + this.finder.getName() + "' after rewriteNOT \n");
            debug(this.exprTree.dumpString());
            debug("\n");
            debug("--------------------------------------------");
         }

      }
   }

   public String getWhereSql() throws IllegalExpressionException {
      try {
         return ((ExprROOT)this.exprTree).getWhereSql();
      } catch (ErrorCollectionException var3) {
         EJBQLCompilerException exx = this.newEJBQLCompilerException(var3);
         if (debugLogger.isDebugEnabled()) {
            debug("\n\n\n\n");
            debug(" getWhereSql() encountered Exception !, process Exceptions !");
            debug(exx.getMessage() + "\n\n");
            debug("\n\n\n");
         }

         throw new IllegalExpressionException(7, exx.getMessage());
      }
   }

   boolean getEjbqlRewritten() {
      return this.ejbqlRewritten;
   }

   int getEjbqlRewrittenReasons() {
      return this.ejbqlRewrittenReasons;
   }

   String getOriginalEjbql() {
      return this.originalEjbql;
   }

   public List getSQLGenEJBQLTokenList() {
      return ((ExprROOT)this.exprTree).getEJBQLTokenList();
   }

   public void addWarning(Exception e) {
      if (this.warnings == null) {
         this.warnings = new ErrorCollectionException(e);
      } else {
         this.warnings.add(e);
      }
   }

   public ErrorCollectionException getWarnings() {
      return this.warnings;
   }

   private EJBQLCompilerException newEJBQLCompilerException(Exception ex) {
      return this.finder.newEJBQLCompilerException(ex, this);
   }

   public QueryNode getMainQueryTree() {
      return this.queryTree;
   }

   private void setupForORCrossProducts() {
      List whereExprs = ((ExprROOT)this.exprTree).getWHEREList();
      Iterator it = whereExprs.iterator();

      while(it.hasNext()) {
         ExprWHERE where = (ExprWHERE)it.next();
         Expr term = where.getTerm1();
         if (term != null) {
            if (debugLogger.isDebugEnabled()) {
               debug("  setup FirstOR for WHERE " + where.printEJBQLTree());
            }

            this.findAndSetupFirstOR(term);
         }
      }

   }

   private void findAndSetupFirstOR(Expr term) {
      if (term instanceof ExprNOT) {
         term = term.getTerm1();
      }

      if (term instanceof ExprOR || term instanceof ExprAND) {
         if (term instanceof ExprOR) {
            if (debugLogger.isDebugEnabled()) {
               debug("findAndSetupFirstOR GOT TOPMOST OR for subtree.  setting ORJoinDataList for OR " + term.printEJBQLTree());
            }

            List orJoinDataList = new ArrayList();
            ((ExprOR)term).setOrJoinDataList(orJoinDataList);
            Expr arg = term.getTerm1();
            this.markORSubTree(orJoinDataList, arg);
            arg = term.getTerm2();
            this.markORSubTree(orJoinDataList, arg);
         } else {
            if (debugLogger.isDebugEnabled()) {
               debug(" findAndSetupFirstOR  term not OR, descend Left and Right ");
            }

            this.findAndSetupFirstOR(term.getTerm1());
            this.findAndSetupFirstOR(term.getTerm2());
         }
      }
   }

   private void markORSubTree(List l, Expr term) {
      if (term instanceof ExprOR || term instanceof ExprAND) {
         if (term instanceof ExprOR) {
            if (debugLogger.isDebugEnabled()) {
               debug("markORSubTree    OR found setOrJoinDataList on " + term.printEJBQLTree());
            }

            ((ExprOR)term).setOrJoinDataList(l);
         }

         this.markORSubTree(l, term.getTerm1());
         this.markORSubTree(l, term.getTerm2());
      }
   }

   public QueryNode newQueryNode(QueryNode parent, int queryNum) {
      return QueryNode.newQueryNode(this.getFinder(), this, parent, queryNum);
   }

   public List getMainQuerySelectList() {
      return this.queryTree.getSelectList();
   }

   public List getMainQuerySelectListForCachingElement() {
      return this.queryTree.getSelectListForCachingElement();
   }

   public boolean mainQueryContainsInSelectListForCachingElement(RDBMSBean left, RDBMSBean right) {
      return this.queryTree.containsInSelectListForCachingElement(left, right);
   }

   public JoinNode getRootJoinNodeForMainQuery(String pathExpression) throws IllegalExpressionException {
      return this.queryTree.getJoinNodeForFirstId(pathExpression);
   }

   public JoinNode getJoinTreeForMainQuery() {
      return this.queryTree.getJoinTree();
   }

   public List getTableAliasExclusionListForMainQuery() {
      return this.queryTree.getTableAliasExclusionList();
   }

   public List getTableAndFKColumnListForLocal11or1NPath(QueryNode inputQueryTree, String fullPath) throws IllegalExpressionException {
      return JoinNode.getTableAndFKColumnListForLocal11or1NPath(inputQueryTree, fullPath);
   }

   public List getTableAndFKColumnListForLocal11or1NPathForMainQuery(String fullPath) throws IllegalExpressionException {
      return JoinNode.getTableAndFKColumnListForLocal11or1NPath(this.queryTree, fullPath);
   }

   public String getMainQueryJoinBuffer() throws IllegalExpressionException {
      return this.queryTree.getMainORJoinBuffer();
   }

   public RDBMSBean getRDBMSBean() {
      return this.bean;
   }

   public EjbqlFinder getFinder() {
      return this.finder;
   }

   public String getEjbName() {
      return this.bean.getEjbName();
   }

   public int getDatabaseType() {
      return this.bean.getDatabaseType();
   }

   public Map getGlobalTableAliasMap() {
      return this.tableAliasMap;
   }

   String getTableNameForAlias(String tableAlias) {
      return (String)this.getGlobalTableAliasMap().get(tableAlias);
   }

   String getAliasForTableName(String tableName) {
      String tableAlias = null;
      String tableNameFromMap = null;
      Iterator it = this.tableAliasMap.keySet().iterator();

      while(it.hasNext()) {
         tableAlias = (String)it.next();
         tableNameFromMap = (String)this.tableAliasMap.get(tableAlias);
         if (tableNameFromMap.equals(tableName)) {
            break;
         }
      }

      return tableAlias;
   }

   public String getFinderMethodName() {
      return this.finder.getName();
   }

   public Class getFinderParameterTypeAt(int position) {
      return this.finder.getParameterTypeAt(position);
   }

   public boolean queryIsSelect() {
      return this.finder.isSelect();
   }

   public boolean queryIsSelectInEntity() {
      return this.finder.isSelectInEntity();
   }

   public boolean isKeyFinder() {
      return this.finder.isKeyFinder();
   }

   public void setQueryIsSelectThisBean() {
      this.finder.setQueryType(2);
   }

   public boolean getQueryIsSelectThisBean() {
      return this.finder.getQueryType() == 2;
   }

   public void setQueryIsSelectLocalBean() {
      this.finder.setQueryType(4);
   }

   public boolean getQueryIsSelectLocalBean() {
      return this.finder.getQueryType() == 4;
   }

   public void setQueryIsSelectThisBeanField() {
      this.finder.setQueryType(3);
   }

   public void setQueryIsSelectLocalBeanField() {
      this.finder.setQueryType(5);
   }

   public void setQueryIsFinderLocalBean() {
      this.finder.setQueryType(0);
   }

   public boolean getQueryIsFinderLocalBean() {
      return this.finder.getQueryType() == 0;
   }

   public void setQuerySelectBeanTarget(RDBMSBean bean) {
      this.finder.setSelectBeanTarget(bean);
   }

   public void setQuerySelectFieldTableAndColumn(String tableColumn) {
      this.finder.setSelectFieldColumn(tableColumn);
   }

   public void setQuerySelectFieldClass(Class fieldClass) {
      this.finder.setSelectFieldClass(fieldClass);
   }

   public String registerTable(String tableName) {
      String newAlias = "WL" + this.tableAliasCount++;
      this.tableAliasMap.put(newAlias, tableName);
      return newAlias;
   }

   public void addFinderInternalQueryParmList(ParamNode pNode) {
      this.finder.addInternalQueryParmList(pNode);
   }

   public void addFinderRemoteBeanParamList(ParamNode pNode) {
      this.finder.addRemoteBeanParamList(pNode);
   }

   public void setFinderRemoteBeanCommandEQ(boolean equals) {
      if (equals) {
         this.finder.setRemoteBeanCommand(2);
      } else {
         this.finder.setRemoteBeanCommand(3);
      }

   }

   public String replaceIdAliases(String in) {
      if (in == null) {
         return null;
      } else if (this.idAliasMap != null && in.length() != 0) {
         StringBuffer out = new StringBuffer();
         StringTokenizer st = new StringTokenizer(in, ".");

         while(st.hasMoreTokens()) {
            String token = st.nextToken();
            String replacement = token;
            String s = (String)this.idAliasMap.get(token);
            if (s != null) {
               replacement = s;
            }

            out.append(replacement);
            out.append(".");
         }

         out.setLength(out.length() - 1);
         return out.toString();
      } else {
         return in;
      }
   }

   public void addIdAlias(String alias, String id) throws IllegalExpressionException {
      if (this.idAliasMap == null) {
         this.idAliasMap = new HashMap();
      }

      if (this.idAliasMap.get(alias) != null) {
         throw new IllegalExpressionException(7, " Correlation variable '" + id + "' is defined more than once ", new DescriptorErrorInfo("<ejb-ql>", this.bean.getEjbName(), this.finder.getName()));
      } else {
         this.idAliasMap.put(alias, id);
      }
   }

   public void addGlobalRangeVariable(String id, String name) throws IllegalExpressionException {
      this.finder.addGlobalRangeVariable(id, name);
   }

   public int globalRangeVariableMapSize() {
      return this.finder.globalRangeVariableMapSize();
   }

   public String getGlobalRangeVariableMap(String id) throws IllegalExpressionException {
      return this.finder.getGlobalRangeVariableMap(id);
   }

   public List getGlobalRangeVariableMapIdList() {
      return this.finder.getGlobalRangeVariableMapIdList();
   }

   public List getIDsFromGlobalRangeVariableMapForSchema(String abstractSchema) {
      return this.finder.getIDsFromGlobalRangeVariableMapForSchema(abstractSchema);
   }

   public boolean identifierIsRangeVariable(String id) {
      String abstractSchemaName = null;

      try {
         abstractSchemaName = this.finder.getGlobalRangeVariableMap(id);
      } catch (IllegalExpressionException var4) {
         return false;
      }

      return abstractSchemaName != null;
   }

   public boolean pathExpressionEndsInField(QueryNode queryTree, String pathExpression) throws IllegalExpressionException {
      if (pathExpression == null) {
         return false;
      } else if (pathExpression.length() == 0) {
         return false;
      } else {
         JoinNode joinTree = queryTree.getJoinTreeForId(pathExpression);
         String deAliasedPathExpression;
         if (joinTree == null) {
            deAliasedPathExpression = JoinNode.getFirstFieldFromId(pathExpression);
            throw new IllegalExpressionException(7, "The pathExpression/Identifier '" + pathExpression + "', contains a root: '" + deAliasedPathExpression + "' that is not defined in an AS declaration in the FROM clause.");
         } else {
            deAliasedPathExpression = this.replaceIdAliases(pathExpression);
            return JoinNode.endsInField(joinTree, deAliasedPathExpression);
         }
      }
   }

   public boolean pathExpressionEndsInRemoteInterface(QueryNode queryTree, String pathExpression) throws IllegalExpressionException {
      if (pathExpression == null) {
         return false;
      } else if (pathExpression.length() == 0) {
         return false;
      } else {
         JoinNode joinTree = queryTree.getJoinTreeForId(pathExpression);
         String deAliasedPathExpression;
         if (joinTree == null) {
            deAliasedPathExpression = JoinNode.getFirstFieldFromId(pathExpression);
            throw new IllegalExpressionException(7, "The pathExpression/Identifier '" + pathExpression + "', contains a root: '" + deAliasedPathExpression + "' that is not defined in an AS declaration in the FROM clause.");
         } else {
            deAliasedPathExpression = this.replaceIdAliases(pathExpression);
            return JoinNode.endsInRemoteInterface(joinTree, deAliasedPathExpression);
         }
      }
   }

   public String getTableAndColumnFromMainQuery(String pathExpression) throws IllegalExpressionException {
      String tableAndField = null;

      try {
         tableAndField = ExprID.calcTableAndColumn(this, this.getMainQueryTree(), pathExpression);
         return tableAndField;
      } catch (Exception var5) {
         IllegalExpressionException ex = new IllegalExpressionException(7, var5.getMessage());
         throw ex;
      }
   }

   String getFROMClauseSelectForUpdate(int selectForUpdateVal) {
      return RDBMSUtils.getFROMClauseSelectForUpdate(this.bean.getDatabaseType(), selectForUpdateVal);
   }

   public String getMainJoinBuffer() throws IllegalExpressionException {
      return this.queryTree.getMainORJoinBuffer();
   }

   public void setMainQuerySelectDistinct() {
      this.finder.setSelectDistinct(true);
   }

   public void setOrderbyColBuf(String b) {
      this.finder.setOrderbyColBuf(b);
   }

   public void setOrderbySql(String s) {
      this.finder.setOrderbySql(s);
   }

   public void setGroupbySql(String s) {
      this.finder.setGroupbySql(s);
   }

   public void setSelectHint(String s) {
      this.finder.setSelectHint(s);
   }

   public boolean isResultSetFinder() {
      return this.finder.isResultSetFinder();
   }

   public void setMainQueryResultSetFinder() {
      this.queryTree.setQueryType(6);
   }

   public String getRelationshipCachingName() {
      return this.finder.getCachingName();
   }

   JoinNode makeTrialJoinRoot(JoinNode joinTree, String id) throws IllegalExpressionException {
      if (id == null) {
         throw new IllegalExpressionException(7, " <cmr-field> " + id + " could not get RDBMSBean ! ");
      } else {
         QueryContext trialContext = new QueryContext(this.bean, this.finder, this.exprTree);
         JoinNode jn = new JoinNode((JoinNode)null, "", this.bean, "", "", -1, false, false, "", trialContext, new ArrayList());
         String asId = JoinNode.getFirstFieldFromId(id);
         JoinNode actualRootNode = JoinNode.getFirstNode(joinTree, id);
         String tableName = actualRootNode.getTableName();
         String tableAlias = trialContext.registerTable(tableName);
         if (debugLogger.isDebugEnabled()) {
            debug(" makeTrialJoinRoot:  added 1st child JoinNode to trialRoot.  id: '" + id + "', the child JoinNode cmr-field was: '" + actualRootNode.getPrevCMRField() + "', the child tableName is: '" + tableName + "', the child tableNameAlias is: '" + tableAlias + "'");
         }

         JoinNode asNode = new JoinNode(jn, asId, actualRootNode.getRDBMSBean(), tableName, tableAlias, -1, false, false, "", trialContext, new ArrayList());
         jn.putChild(asId, asNode);
         return jn;
      }
   }

   public void addFinderBeanInputParameter(int variableNumber, RDBMSBean bean) throws IllegalExpressionException {
      Class beanInterface = this.finder.getParameterTypeAt(variableNumber - 1);
      CMPBeanDescriptor bd = bean.getCMPBeanDescriptor();
      List pkFieldList = bean.getPrimaryKeyFields();
      boolean hasComplexPK = bd.hasComplexPrimaryKey();
      Class pkClass = null;
      if (hasComplexPK) {
         pkClass = bd.getPrimaryKeyClass();
      }

      String pname = "param" + (variableNumber - 1);
      ParamNode beanNode = new ParamNode(bean, pname, variableNumber, beanInterface, "", "", true, false, pkClass, hasComplexPK, false);
      int pkCount = pkFieldList.size();

      for(int i = 0; i < pkCount; ++i) {
         String pkField = (String)pkFieldList.get(i);
         pkClass = bd.getFieldClass(pkField);
         if (pkClass == null) {
            if (debugLogger.isDebugEnabled()) {
               debug("  PK CLASS: " + pkField + " is NULL !!!!");
            }

            Loggable l = EJBLogger.logfinderNoPKClassForFieldLoggable(pkField);
            throw new IllegalExpressionException(7, "Bean: " + bean.getEjbName() + " " + l.getMessageText());
         }

         boolean isNLS = this.isOracleNLSDataType(pkField);
         if (i == 0 && !hasComplexPK) {
            beanNode.setPrimaryKeyClass(pkClass);
            beanNode.setOracleNLSDataType(isNLS);
         }

         ParamNode pNode = new ParamNode(bean, "N_A", variableNumber, pkClass, pkField, "", false, false, pkClass, false, isNLS);
         beanNode.addParamSubList(pNode);
      }

      this.finder.addInternalQueryParmList(beanNode);
   }

   public ExprID setupForLHSForeignKeysWithNoReferenceToRHS(ExprID exprID) throws ErrorCollectionException {
      QueryNode queryTree = exprID.getQueryTree();
      if (queryTree == null) {
         throw new ErrorCollectionException("Internal Error, setupForLHSForeignKeysWithNoReferenceToRHS called with ExprID before ExprID.init() has been called.  queryTree is NULL.");
      } else {
         int relationshipType;
         try {
            relationshipType = queryTree.getRelationshipTypeForPathExpressionWithNoSQLGen(exprID.getDealiasedEjbqlID());
         } catch (Exception var10) {
            throw new ErrorCollectionException(var10);
         }

         if (relationshipType != 2 && relationshipType != 5) {
            throw new ErrorCollectionException("Internal Error, setupForLHSForeignKeysWithNoReferenceToRHS called with ExprID  which is not a relationship of type: 'RDBMSUtils.ONE_TO_ONE_RELATION_FK_ON_LHS' or 'RDBMSUtils.MANY_TO_ONE_RELATION', type is: '" + RDBMSUtils.relationshipTypeToString(relationshipType));
         } else {
            String lastPathExpressionElement = getLastFieldFromId(exprID.getDealiasedEjbqlID());
            ExprID truncatedExprID = this.prepareTruncatedPathExpression(queryTree, exprID.getDealiasedEjbqlID());

            try {
               JoinNode lhsPrevNode = queryTree.getJoinNodeForLastId(truncatedExprID.getDealiasedEjbqlID());
               RDBMSBean lhsPrevBean = lhsPrevNode.getRDBMSBean();
               String lhsPrevTableName = lhsPrevBean.getTableForCmrField(lastPathExpressionElement);
               lhsPrevNode.forceInternalMultiTableJoinMaybe(queryTree, lhsPrevTableName);
               return truncatedExprID;
            } catch (Exception var9) {
               throw new ErrorCollectionException(var9);
            }
         }
      }
   }

   public ExprID setupForLHSPrimaryKeysWithNoReferenceToRHS(ExprID exprID) throws ErrorCollectionException {
      QueryNode queryTree = exprID.getQueryTree();
      if (queryTree == null) {
         throw new ErrorCollectionException("Internal Error, setupForLHSPrimaryKeysWithNoReferenceToRHS called with ExprID before ExprID.init() has been called.  queryTree is NULL.");
      } else {
         int relationshipType;
         try {
            relationshipType = queryTree.getRelationshipTypeForPathExpressionWithNoSQLGen(exprID.getDealiasedEjbqlID());
         } catch (Exception var5) {
            throw new ErrorCollectionException(var5);
         }

         if (relationshipType != 4 && relationshipType != 6 && relationshipType != 8) {
            throw new ErrorCollectionException("Internal Error, setupForLHSPrimaryKeysWithNoReferenceToRHS called with ExprID  which is not a relationship of type: 'RDBMSUtils.ONE_TO_MANY_RELATION' or 'RDBMSUtils.MANY_TO_MANY_RELATION' or 'RDBMSUtils.REMOTE_RELATION_W_JOIN_TABLE', type is: '" + RDBMSUtils.relationshipTypeToString(relationshipType));
         } else {
            String lastPathExpressionElement = getLastFieldFromId(exprID.getDealiasedEjbqlID());
            return this.prepareTruncatedPathExpression(queryTree, exprID.getDealiasedEjbqlID());
         }
      }
   }

   private ExprID prepareTruncatedPathExpression(QueryNode queryTree, String dealiasedId) throws ErrorCollectionException {
      int lastDot = dealiasedId.lastIndexOf(".");
      if (lastDot == -1) {
         Loggable l = EJBLogger.logFinderNotNullOnBadPathLoggable(dealiasedId);
         throw new ErrorCollectionException(l.getMessageText());
      } else {
         String truncatedId = dealiasedId.substring(0, lastDot);
         ExprID truncatedExprID = ExprID.newInitExprID(this, queryTree, truncatedId);
         truncatedExprID.prepareIdentifierForSQLGen();
         return truncatedExprID;
      }
   }

   public boolean isOracleNLSDataType(String fieldName) {
      return RDBMSUtils.isOracleNLSDataType(this.bean, this.replaceIdAliases(fieldName), this.finder.getGlobalRangeVariableMap());
   }

   public static String getFirstFieldFromId(String id) {
      return JoinNode.getFirstFieldFromId(id);
   }

   public static String getLastFieldFromId(String id) {
      return JoinNode.getLastFieldFromId(id);
   }

   public static Class getInterfaceClass(RDBMSBean bean) {
      CMPBeanDescriptor bd = bean.getCMPBeanDescriptor();
      return bd.hasLocalClientView() ? bd.getLocalInterfaceClass() : bd.getRemoteInterfaceClass();
   }

   private static void debug(String s) {
      debugLogger.debug("[QueryContext] " + s);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
