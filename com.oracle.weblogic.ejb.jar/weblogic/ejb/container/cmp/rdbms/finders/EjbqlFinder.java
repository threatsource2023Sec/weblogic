package weblogic.ejb.container.cmp.rdbms.finders;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.FieldGroup;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.cmp.rdbms.RelationshipCaching;
import weblogic.ejb.container.compliance.Log;
import weblogic.ejb.container.internal.QueryCachingHandler;
import weblogic.ejb.container.manager.TTLManager;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb20.cmp.rdbms.finders.EJBQLCompilerException;
import weblogic.ejb20.cmp.rdbms.finders.EJBQLToken;
import weblogic.ejb20.cmp.rdbms.finders.InvalidFinderException;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.i18n.Localizer;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;
import weblogic.utils.AssertionError;
import weblogic.utils.ErrorCollectionException;

public class EjbqlFinder extends Finder {
   private String stopOnMethod = "findTestDisjointOR";
   public static final int FINDER_EXPRESSION_IN = 0;
   public static final int FINDER_EXPRESSION_NOT_IN = 1;
   public static final int REMOTE_BEAN_EQ = 2;
   public static final int REMOTE_BEAN_NOT_EQ = 3;
   public static final int EJBQL_REWRITE_REASON_FACTOR_OUT_NOT = 1;
   public static final String EJBQL_REWRITE_REASON_FACTOR_OUT_NOT_TEXT = "EJB QL 'NOT' Expressions have been factored out and removed to preserve any generated SQL joins.";
   public static final String EJBQL_REWRITE_REASON_DEFAULT_TEXT = "(Reason for rewrite not specified)";
   private String ejbQuery = null;
   private String whereSql = null;
   private String orderbySql = null;
   private String groupbySql = null;
   private String selectHint = null;
   private boolean includeResultCacheHint = false;
   private String sqlQuery = null;
   private String sqlQueryForUpdate = null;
   private String sqlQueryForUpdateSelective = null;
   private String sqlQueryForUpdateNoWait = null;
   private Expression ejbqlExpression = null;
   private Expr ejbqlExpr = null;
   private RDBMSBean keyBean = null;
   protected Map corrVarMap = null;
   protected Map globalRangeVariableMap = null;
   protected List tableJoinList = null;
   private String groupName = null;
   private String cachingName = null;
   private String orderbyColBuf = null;
   private StringBuffer subQueryColumnBuf = null;
   private QueryContext queryContext = null;
   private boolean testParser = false;
   private boolean isNativeQuery = false;
   private boolean isGeneratedRelationFinder = false;
   private boolean isAggregateQuery = false;
   private String selectTablePK = null;
   private String selectJoinBuf = null;
   private List remoteFinderNodes = null;
   private List remoteBeanParamList = null;
   private int remoteBeanCommand = 0;
   private boolean isPreparedQueryFinder = false;

   public EjbqlFinder(String name, String query, boolean testParser) throws InvalidFinderException {
      super(name, false);
      this.testParser = testParser;
      this.setEjbQuery(query);
   }

   public EjbqlFinder(String name, String query) throws InvalidFinderException {
      super(name, false);
      this.setEjbQuery(query);
   }

   public int getRemoteBeanCommand() {
      return this.remoteBeanCommand;
   }

   public void setRemoteBeanCommand(int i) {
      this.remoteBeanCommand = i;
   }

   public void addGlobalASMap(String id, String name) throws IllegalExpressionException {
      this.addGlobalRangeVariable(id, name);
   }

   public void addGlobalRangeVariable(String id, String name) throws IllegalExpressionException {
      if (this.globalRangeVariableMap == null) {
         this.globalRangeVariableMap = new HashMap();
      }

      if (this.globalRangeVariableMap.containsKey(id)) {
         Loggable l = EJBLogger.logduplicateRangeVariableDefinitionLoggable(id);
         throw new IllegalExpressionException(7, l.getMessageText());
      } else {
         this.globalRangeVariableMap.put(id, name);
      }
   }

   public int GlobalASMapSize() {
      return this.globalRangeVariableMapSize();
   }

   public int globalRangeVariableMapSize() {
      if (this.globalRangeVariableMap == null) {
         this.globalRangeVariableMap = new HashMap();
      }

      return this.globalRangeVariableMap.size();
   }

   public String getGlobalASMap(String id) throws IllegalExpressionException {
      return this.getGlobalRangeVariableMap(id);
   }

   public String getGlobalRangeVariableMap(String id) throws IllegalExpressionException {
      if (this.globalRangeVariableMap == null) {
         Loggable l = EJBLogger.logejbqlMissingRangeVariableDeclarationLoggable(id);
         throw new IllegalExpressionException(7, l.getMessageText());
      } else {
         String abstractSchemaName = (String)this.globalRangeVariableMap.get(id);
         if (abstractSchemaName != null) {
            return abstractSchemaName;
         } else {
            Loggable l = EJBLogger.logejbqlMissingRangeVariableDeclarationLoggable(id);
            throw new IllegalExpressionException(7, l.getMessageText());
         }
      }
   }

   public void setMethods(Method[] methods) throws Exception {
      assert methods[0] != null;

      super.setMethod(methods[0]);
      if (this.isFindByPrimaryKey()) {
         Class paramClass = this.getKeyBean().getCMPBeanDescriptor().getPrimaryKeyClass();
         this.setParameterClassTypes(new Class[]{paramClass});
      } else {
         this.setParameterClassTypes(methods[0].getParameterTypes());
      }

   }

   public List getGlobalASMapIdList() {
      return this.getGlobalRangeVariableMapIdList();
   }

   public Map getGlobalRangeVariableMap() {
      return this.globalRangeVariableMap;
   }

   public List getGlobalRangeVariableMapIdList() {
      List l = new ArrayList();
      if (this.globalRangeVariableMap == null) {
         return l;
      } else {
         Iterator it = this.globalRangeVariableMap.keySet().iterator();

         while(it.hasNext()) {
            l.add(it.next());
         }

         return l;
      }
   }

   public List getIDsFromGlobalASMapForSchema(String abstractSchema) {
      return this.getIDsFromGlobalRangeVariableMapForSchema(abstractSchema);
   }

   public List getIDsFromGlobalRangeVariableMapForSchema(String abstractSchema) {
      List l = new ArrayList();
      if (this.globalRangeVariableMap == null) {
         return l;
      } else {
         Iterator it = this.globalRangeVariableMap.keySet().iterator();

         while(it.hasNext()) {
            String id = (String)it.next();
            String schema = (String)this.globalRangeVariableMap.get(id);
            if (schema.equals(abstractSchema)) {
               l.add(id);
            }
         }

         return l;
      }
   }

   public boolean isGeneratedRelationFinder() {
      return this.isGeneratedRelationFinder;
   }

   public void setIsGeneratedRelationFinder(boolean b) {
      this.isGeneratedRelationFinder = b;
   }

   public boolean isAggregateQuery() {
      return this.isAggregateQuery;
   }

   public void setSelectHint(String s) {
      this.selectHint = s;
   }

   public String getSelectHint() {
      return this.selectHint;
   }

   public void setIncludeResultCacheHint(boolean b) {
      this.includeResultCacheHint = b;
   }

   public void setKeyBean(RDBMSBean keyBean) {
      this.keyBean = keyBean;
   }

   public RDBMSBean getKeyBean() {
      return this.keyBean;
   }

   public void setSelectJoinBuf(String s) {
      this.selectJoinBuf = s;
   }

   public String getSelectJoinBuf() {
      return this.selectJoinBuf;
   }

   public void setOrderbyColBuf(String s) {
      this.orderbyColBuf = s;
   }

   public String getOrderbyColBuf() {
      return this.orderbyColBuf;
   }

   public void setEjbQuery(String query) {
      this.ejbQuery = query;
      this.whereSql = null;
   }

   public String getEjbQuery() {
      return this.ejbQuery;
   }

   public String getFromSql(int selectForUpdateValue) throws IllegalExpressionException {
      return this.generateTableSQL(selectForUpdateValue);
   }

   public String getWhereSql() {
      return this.whereSql;
   }

   public ParamNode getParamNodeForVariableNumber(int i) {
      List l = this.getExternalMethodParmList();
      ParamNode theNode = null;
      Iterator it = l.iterator();

      while(it.hasNext()) {
         ParamNode pn = (ParamNode)it.next();
         if (pn.getVariableNumber() == i) {
            theNode = pn;
            break;
         }
      }

      return theNode;
   }

   public void setOrderbySql(String s) {
      this.orderbySql = s;
   }

   public String getOrderbySql() {
      return this.orderbySql;
   }

   public void setGroupbySql(String s) {
      this.groupbySql = s;
   }

   public String getGroupbySql() {
      return this.groupbySql;
   }

   public void addRemoteBeanParamList(ParamNode n) {
      if (this.remoteBeanParamList == null) {
         this.remoteBeanParamList = new ArrayList();
      }

      this.remoteBeanParamList.add(n);
   }

   public ParamNode getRemoteBeanParam() {
      return !this.hasRemoteBeanParam() ? null : (ParamNode)this.remoteBeanParamList.get(0);
   }

   public boolean hasRemoteBeanParam() {
      if (this.remoteBeanParamList == null) {
         return false;
      } else {
         return this.remoteBeanParamList.size() != 0;
      }
   }

   public void addSubQueryColumnBuf(String col) {
      if (this.subQueryColumnBuf == null) {
         this.subQueryColumnBuf = new StringBuffer();
      }

      if (this.subQueryColumnBuf.length() > 0) {
         this.subQueryColumnBuf.append(", ");
      }

      this.subQueryColumnBuf.append(col).append(" ");
   }

   public String getSubQueryColumnBuf() {
      return this.subQueryColumnBuf == null ? "" : this.subQueryColumnBuf.toString();
   }

   public String getSQLQuery() {
      return this.sqlQuery;
   }

   public String getSQLQueryForUpdateSelective() {
      return this.sqlQueryForUpdateSelective;
   }

   public String getSQLQueryForUpdate() {
      return this.sqlQueryForUpdate;
   }

   public String getSQLQueryForUpdateNoWait() {
      return this.sqlQueryForUpdateNoWait;
   }

   public String getTableAndFieldForCmpField(String id) throws IllegalExpressionException {
      return this.queryContext.getTableAndColumnFromMainQuery(id);
   }

   public String getMainJoinBuffer() throws IllegalExpressionException {
      return this.queryContext.getMainJoinBuffer();
   }

   public Expression getWLQLExpression() {
      return this.ejbqlExpression;
   }

   public Expr getEJBQLExpr() {
      return this.ejbqlExpr;
   }

   public void setIsPreparedQueryFinder(boolean val) {
      this.isPreparedQueryFinder = val;
   }

   public boolean equals(Object other) {
      if (!(other instanceof EjbqlFinder)) {
         return false;
      } else {
         EjbqlFinder otherFinder = (EjbqlFinder)other;
         if (!this.getName().equals(otherFinder.getName())) {
            return false;
         } else {
            if (this.getEJBQLExpr() == null) {
               if (otherFinder.getEJBQLExpr() != null) {
                  return false;
               }
            } else if (!this.getEJBQLExpr().equals(otherFinder.getEJBQLExpr())) {
               return false;
            }

            Class[] thisParams = this.getParameterClassTypes();
            Class[] otherParams = otherFinder.getParameterClassTypes();
            if (otherParams.length != thisParams.length) {
               return false;
            } else {
               for(int i = 0; i < thisParams.length; ++i) {
                  if (!otherParams[i].equals(thisParams[i])) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   public int hashCode() {
      return this.getName().hashCode() ^ this.getEJBQLExpr().hashCode();
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(super.toString());
      buf.append("[EjbqlFinder ");
      buf.append("ejbQuery = " + this.ejbQuery + "; ");
      buf.append("Expr = " + this.getEJBQLExpr() + "; ");
      buf.append("whereSql = " + this.whereSql + " ");
      buf.append("]]");
      return buf.toString();
   }

   public void parseExpression() throws EJBQLCompilerException {
      ExprParser parser = null;
      parser = new ExprParser(this);
      Expr testExpression = null;
      if (!this.ejbQuery.equals("")) {
         if (this.testParser) {
            System.out.println("\n\n+++++++++ PARSE QUERY: \n     " + this.ejbQuery);
         }

         if (debugLogger.isDebugEnabled()) {
            debug("\n Parse EJB QL: " + this.ejbQuery);
         }

         testExpression = parser.parse(this.ejbQuery);
         if (this.testParser && testExpression != null) {
            testExpression.dump();
         }
      }

      this.ejbqlExpr = testExpression;
   }

   private static void get_line(String s) {
      System.out.print(s);
      System.out.flush();

      try {
         while(true) {
            if (System.in.read() != 10) {
               continue;
            }
         }
      } catch (Exception var3) {
      }

   }

   public void computeSQLQuery(RDBMSBean rdbmsBean) throws EJBQLCompilerException {
      try {
         if (debugLogger.isDebugEnabled()) {
            debug("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n ");
            debug(" -------------------------------------------------------  ");
            debug("\n           computeSQLQuery for " + this.getName() + "  EJB QL: " + this.ejbQuery + "\n");
            if (this.getName().startsWith(this.stopOnMethod)) {
               get_line("              press key to continue ");
            }

            ((BaseExpr)this.ejbqlExpr).dump();
         }

         this.queryContext = new QueryContext(rdbmsBean, this, this.ejbqlExpr);
         String whereSql = "";

         try {
            this.queryContext.generateQuery();
         } catch (EJBQLCompilerException var21) {
            throw var21;
         }

         whereSql = this.queryContext.getWhereSql();
         whereSql = whereSql.trim();
         if (whereSql.compareTo("WHERE") == 0) {
            whereSql = "";
         }

         this.initExternalMethodParmList();
         List selectList = null;
         selectList = this.queryContext.getMainQuerySelectList();
         Loggable l;
         IllegalExpressionException e;
         if (!this.isSelect && !this.isSelectInEntity && selectList.size() > 0) {
            SelectNode sn = (SelectNode)selectList.get(0);
            if (sn.getSelectType() != 61) {
               l = EJBLogger.logFinderDoesNotReturnBeanLoggable(this.getName(), this.getEjbQuery());
               e = new IllegalExpressionException(7, l.getMessageText(), new DescriptorErrorInfo("<ejb-ql>", rdbmsBean.getEjbName(), this.getName()));
               throw this.newEJBQLCompilerException(e, (QueryContext)this.queryContext);
            }

            String selectId = sn.getSelectTarget();
            RDBMSBean bean = sn.getSelectBean();
            if (bean != null && !bean.getEjbName().equals(rdbmsBean.getEjbName())) {
               Loggable l = EJBLogger.logFinderReturnsBeanOfWrongTypeLoggable(this.getName(), rdbmsBean.getEjbName(), bean.getEjbName());
               Exception e = new IllegalExpressionException(7, l.getMessageText(), new DescriptorErrorInfo("<ejb-ql>", rdbmsBean.getEjbName(), this.getName()));
               throw this.newEJBQLCompilerException(e, (QueryContext)this.queryContext);
            }
         }

         if (this.isSelect && !this.isNativeQuery) {
            Iterator it = selectList.iterator();

            while(it.hasNext()) {
               SelectNode snode = (SelectNode)it.next();
               if (snode.isCorrelatedSubQuery()) {
                  it.remove();
               }
            }

            if (selectList.size() > 1 && !this.isResultSetFinder()) {
               l = EJBLogger.logSelectMultipleFieldsButReturnCollectionLoggable(this.returnClassType.getName());
               e = new IllegalExpressionException(7, l.getMessageText(), new DescriptorErrorInfo("<ejb-ql>", rdbmsBean.getEjbName(), this.getName()));
               throw this.newEJBQLCompilerException(e, (QueryContext)this.queryContext);
            }
         }

         String inEntitySQL = this.recomputeSelectInEntity(rdbmsBean);
         StringBuffer sb = new StringBuffer();
         sb.append("SELECT ");
         if (this.selectHint != null) {
            sb.append(this.selectHint).append(" ");
         } else if (this.includeResultCacheHint) {
            sb.append("/*+ RESULT_CACHE */ ");
         }

         int distinctStart = sb.length();
         if (this.isSelectDistinct() || this.isSetFinder()) {
            sb.append("DISTINCT ");
         }

         int distinctEnd = sb.length();
         CMPBeanDescriptor bd = rdbmsBean.getCMPBeanDescriptor();
         Iterator it = selectList.iterator();
         int selectCount = 0;

         SelectNode sn;
         while(it.hasNext()) {
            ++selectCount;
            if (selectCount > 1) {
               sb.append(", ");
            }

            sn = (SelectNode)it.next();
            if (sn.getSelectType() == 61) {
               if (this.finderLoadsBean) {
                  sb.append(this.generateFieldGroupSQLForFinder(sn.getSelectBean(), sn.getSelectTarget(), this.getGroupName(), false, (RDBMSBean)null));
               } else {
                  sb.append(this.generatePrimaryKeySQL(sn.getSelectBean(), sn.getSelectTarget()));
               }
            } else if (sn.getIsAggregate()) {
               this.isAggregateQuery = true;
               sb.append(sn.getSelectTypeName());
               sb.append("(");
               if (sn.getIsAggregateDistinct()) {
                  sb.append("DISTINCT ");
               }

               sb.append(sn.getDbmsTarget()).append(" ");
               sb.append(") ");
            } else {
               switch (sn.getSelectType()) {
                  case 17:
                     sb.append(sn.getDbmsTarget()).append(" ");
                     break;
                  case 70:
                  case 71:
                     sb.append(sn.getSelectTypeName());
                     sb.append("( ");
                     sb.append(sn.getDbmsTarget()).append(" ");
                     sb.append(") ");
                     break;
                  default:
                     throw new AssertionError("Unknown type!");
               }
            }
         }

         if (this.finderLoadsBean) {
            sn = null;
            List selectListForCachingElement = this.queryContext.getMainQuerySelectListForCachingElement();
            Iterator itCachingElements = selectListForCachingElement.iterator();

            while(itCachingElements.hasNext()) {
               SelectNode sn = (SelectNode)itCachingElements.next();
               if (debugLogger.isDebugEnabled()) {
                  debug("\n ------------------  BEGIN  relationship caching for caching element " + sn.getSelectTarget() + "----");
               }

               sb.append(", ");
               sb.append(this.generateFieldGroupSQLForFinder(sn.getSelectBean(), sn.getSelectTarget(), sn.getCachingElementGroupName(), true, sn.getPrevBean()));
               if (debugLogger.isDebugEnabled()) {
                  debug("\n ------------------  END    relationship caching for caching element " + sn.getSelectTarget() + "----\n");
               }
            }
         }

         String s = this.getOrderbyColBuf();
         if (s != null) {
            sb.append(", ");
            sb.append(s);
         }

         sb.append(" FROM ");
         int start = sb.length();
         String fromSql = this.generateTableSQL(0);
         String fromSqlForUpdate = this.generateTableSQL(1);
         sb.append(fromSql);
         int end = sb.length();
         if (whereSql.length() > 0) {
            sb.append(whereSql);
         }

         if (inEntitySQL.length() > 0) {
            if (whereSql.length() > 0) {
               sb.append(" AND ");
            } else {
               whereSql = " WHERE ";
               sb.append(whereSql);
            }

            sb.append(inEntitySQL);
         }

         String joinBuffer = this.queryContext.getMainQueryJoinBuffer();
         if (joinBuffer.length() > 0) {
            if (whereSql.length() > 0) {
               sb.append(" AND ");
            } else {
               whereSql = "WHERE ";
               sb.append(whereSql);
            }

            sb.append(joinBuffer);
            sb.append(" ");
         }

         if (this.groupbySql != null) {
            sb.append(this.groupbySql).append(" ");
         }

         if (this.orderbySql != null) {
            if (rdbmsBean.getUseSelectForUpdate()) {
               Loggable l = EJBLogger.logselectForUpdateSpecifiedWithOrderByLoggable(this.getName(), rdbmsBean.getEjbName());
               throw new IllegalExpressionException(7, l.getMessageText(), new DescriptorErrorInfo("<ejb-ql>", rdbmsBean.getEjbName(), this.getName()));
            }

            sb.append(this.orderbySql);
         }

         this.sqlQuery = sb.toString();
         if (distinctStart < distinctEnd) {
            sb.replace(distinctStart, distinctEnd, " ");
         }

         int dbType = rdbmsBean.getDatabaseType();
         switch (dbType) {
            case 0:
            case 3:
            case 6:
            case 8:
            case 9:
            case 10:
               this.sqlQueryForUpdate = sb.toString() + RDBMSUtils.selectForUpdateToString(1);
               this.sqlQueryForUpdateNoWait = sb.toString() + RDBMSUtils.selectForUpdateToString(2);
               this.assignSqlQueryForUpdateSelective();
               break;
            case 1:
               this.sqlQueryForUpdate = sb.toString() + RDBMSUtils.selectForUpdateToString(1);
               if (this.cachingName != null) {
                  if (this.allOrAnyBeansHaveSelectForUpdate(false)) {
                     this.sqlQueryForUpdateSelective = this.sqlQueryForUpdate;
                  } else {
                     String updateOfExtension = this.perhapsAppendForUpdateOf();
                     if (!updateOfExtension.equals("")) {
                        this.sqlQueryForUpdateSelective = this.sqlQueryForUpdate + updateOfExtension;
                     } else {
                        this.sqlQueryForUpdateSelective = this.sqlQuery;
                     }
                  }
               } else if (this.rdbmsBean.getUseSelectForUpdate()) {
                  this.sqlQueryForUpdateSelective = this.sqlQueryForUpdate;
               } else {
                  this.sqlQueryForUpdateSelective = this.sqlQuery;
               }

               this.sqlQueryForUpdateNoWait = sb.toString() + RDBMSUtils.selectForUpdateToString(2);
               break;
            case 2:
            case 5:
            case 7:
               this.sqlQueryForUpdateNoWait = sb.toString() + RDBMSUtils.selectForUpdateToString(2);
               sb.replace(start, end, fromSqlForUpdate);
               this.sqlQueryForUpdate = sb.toString();
               this.assignSqlQueryForUpdateSelective();
               break;
            case 4:
               this.sqlQueryForUpdate = sb.toString() + " FOR READ ONLY WITH RS USE AND KEEP UPDATE LOCKS";
               this.sqlQueryForUpdateNoWait = sb.toString() + RDBMSUtils.selectForUpdateToString(2);
               this.assignSqlQueryForUpdateSelective();
               break;
            default:
               throw new AssertionError("Undefined database type " + dbType);
         }

         if (debugLogger.isDebugEnabled()) {
            List l = this.queryContext.getSQLGenEJBQLTokenList();
            debug("\n\n\n\n +++++++++  view the EJBQL Token List +++++++++++=\n");
            StringBuffer sbb = new StringBuffer();
            it = l.iterator();

            while(it.hasNext()) {
               sbb.append(((EJBQLToken)it.next()).getTokenText());
            }

            debug("  '" + sbb.toString() + "'\n\n\n +++++++++++++++++++++++++++++++++++++++++++++\n");
            debug("SQL Query is: " + this.sqlQuery);
            debug("SQL Query with FOR UPDATE is: " + this.sqlQueryForUpdate);
            debug("SQL Query with FOR UPDATE NOWAIT is: " + this.sqlQueryForUpdateNoWait);
            if (this.cachingName != null && dbType == 1) {
               debug("SQL Query for case SelectForUpdateDisabled for dbtype Oracle is: " + this.sqlQueryForUpdateSelective);
            }

            debug("              ------------------------------------------------------- \n\n\n\n\n\n");
            if (this.getName().equals(this.stopOnMethod)) {
               get_line("              press key to continue ");
            }
         }

         QueryNode rootQuery = this.queryContext.getMainQueryTree();
         rootQuery.checkAllORCrossProducts();
         ErrorCollectionException warnings = this.queryContext.getWarnings();
         if (warnings != null) {
            EJBQLCompilerException ex = this.newEJBQLCompilerException(warnings, (QueryContext)this.queryContext);
            EJBLogger.logWarningFromEJBQLCompiler(ex.getMessage());
         }

      } catch (IllegalExpressionException var22) {
         var22.setDescriptorErrorInfo(new DescriptorErrorInfo("<ejb-ql>", rdbmsBean.getEjbName(), this.getName()));
         throw this.newEJBQLCompilerException(var22, (QueryContext)this.queryContext);
      }
   }

   public boolean allOrAnyBeansHaveSelectForUpdate(boolean any) {
      List selectListForCachingElement = null;
      selectListForCachingElement = this.queryContext.getMainQuerySelectListForCachingElement();
      if (selectListForCachingElement.size() != 0) {
         Iterator it1 = selectListForCachingElement.iterator();

         while(it1.hasNext()) {
            SelectNode sn = (SelectNode)it1.next();
            RDBMSBean rbean = sn.getSelectBean();
            if (any) {
               if (rbean.getUseSelectForUpdate()) {
                  return true;
               }
            } else if (!rbean.getUseSelectForUpdate()) {
               return false;
            }
         }

         if (this.rdbmsBean.getUseSelectForUpdate()) {
            return true;
         }
      }

      return false;
   }

   private void assignSqlQueryForUpdateSelective() {
      if (this.cachingName != null && this.allOrAnyBeansHaveSelectForUpdate(true)) {
         this.sqlQueryForUpdateSelective = this.sqlQueryForUpdate;
      } else if (this.rdbmsBean.getUseSelectForUpdate()) {
         this.sqlQueryForUpdateSelective = this.sqlQueryForUpdate;
      } else {
         this.sqlQueryForUpdateSelective = this.sqlQuery;
      }

   }

   public String perhapsAppendForUpdateOf() {
      StringBuffer sb = new StringBuffer();
      List selectListForCachingElement = null;
      String primaryKeyColumn = null;
      String tableName = null;
      String tableAlias = null;
      int count = 0;
      ArrayList rdbmsBeanList = new ArrayList();
      selectListForCachingElement = this.queryContext.getMainQuerySelectListForCachingElement();
      Iterator it1 = selectListForCachingElement.iterator();

      while(it1.hasNext()) {
         SelectNode sn = (SelectNode)it1.next();
         RDBMSBean rb = sn.getSelectBean();
         rdbmsBeanList.add(rb);
      }

      rdbmsBeanList.add(this.rdbmsBean);
      Comparator comp = new Comparator() {
         public int compare(Object o1, Object o2) {
            RDBMSBean rb1 = (RDBMSBean)o1;
            RDBMSBean rb2 = (RDBMSBean)o2;
            return rb1.getLockOrder() - rb2.getLockOrder();
         }
      };
      Collections.sort(rdbmsBeanList, comp);
      Iterator it2 = rdbmsBeanList.iterator();

      while(true) {
         RDBMSBean rbean;
         do {
            if (!it2.hasNext()) {
               if (debugLogger.isDebugEnabled()) {
                  debug("perhapsAppendForUpdateOf returns: " + sb.toString());
               }

               return sb.toString();
            }

            rbean = (RDBMSBean)it2.next();
         } while(!rbean.getUseSelectForUpdate());

         for(Iterator it3 = rbean.getTables().iterator(); it3.hasNext(); ++count) {
            tableName = (String)it3.next();
            tableAlias = this.queryContext.getAliasForTableName(tableName);
            Map pkf2ColumnMap = rbean.getPKCmpf2ColumnForTable(tableName);

            for(Iterator itColumn = pkf2ColumnMap.values().iterator(); itColumn.hasNext(); primaryKeyColumn = (String)itColumn.next()) {
            }

            if (count == 0) {
               sb.append("OF ");
            } else {
               sb.append(", ");
            }

            sb.append(tableAlias + "." + primaryKeyColumn);
         }
      }
   }

   private String recomputeSelectInEntity(RDBMSBean rdbmsBean) throws IllegalExpressionException {
      StringBuffer sb = new StringBuffer();
      if (this.isSelectInEntity) {
         CMPBeanDescriptor bd = rdbmsBean.getCMPBeanDescriptor();
         Class beanClass = bd.getBeanClass();
         boolean hasComplexPK = bd.hasComplexPrimaryKey();
         Class pkClass = null;
         if (hasComplexPK) {
            pkClass = bd.getPrimaryKeyClass();
         }

         int size = this.externalMethodParmList.size();
         String paramName = "param" + size;
         int varNumber = size + 1;
         ParamNode pNode = new ParamNode(rdbmsBean, paramName, varNumber, Object.class, "", "", false, true, pkClass, hasComplexPK, false);
         if (debugLogger.isDebugEnabled()) {
            debug(" created inEntity ParamNode: " + pNode.toString());
         }

         List pkList = rdbmsBean.getPrimaryKeyFields();
         int pkCount = pkList.size();

         for(int i = 0; i < pkCount; ++i) {
            String pkField = (String)pkList.get(i);
            pkClass = bd.getFieldClass(pkField);
            if (pkClass == null) {
               throw new IllegalExpressionException(7, "finder: " + this.getName() + ", the EJB QL for ejbSelect<>InEntity Finders, query recompute: could not get pkClass for pkField: " + pkField, new DescriptorErrorInfo("<ejb-ql>", rdbmsBean.getEjbName(), this.getName()));
            }

            boolean isNLS = RDBMSUtils.isOracleNLSDataType(rdbmsBean, pkField);
            if (i == 0 && !hasComplexPK) {
               pNode.setPrimaryKeyClass(pkClass);
               pNode.setOracleNLSDataType(isNLS);
            }

            ParamNode subPNode = new ParamNode(rdbmsBean, paramName, varNumber, pkClass, pkField, "", false, false, pkClass, false, isNLS);
            if (debugLogger.isDebugEnabled()) {
               debug(" added Sub ParamNode to inEntity ParamNode: " + subPNode.toString());
            }

            pNode.addParamSubList(subPNode);
            String tableAndField = null;
            List idList = this.getIDsFromGlobalRangeVariableMapForSchema(rdbmsBean.getAbstractSchemaName());
            if (idList.size() < 1) {
               Loggable l = EJBLogger.lograngeVariableNotFoundLoggable(rdbmsBean.getEjbName(), rdbmsBean.getAbstractSchemaName());
               throw new IllegalExpressionException(7, l.getMessageText(), new DescriptorErrorInfo("<ejb-ql>", rdbmsBean.getEjbName(), this.getName()));
            }

            String asId = (String)idList.get(0);
            String id = asId + "." + pkField;

            try {
               tableAndField = this.queryContext.getTableAndColumnFromMainQuery(id);
            } catch (Exception var22) {
               throw new IllegalExpressionException(7, "In Bean Parameter processing.  Could not get table and Field for path expression: " + id, new DescriptorErrorInfo("<ejb-ql>", rdbmsBean.getEjbName(), this.getName()));
            }

            sb.append(tableAndField).append(" = ? ");
            sb.append(" AND ");
         }

         if (sb.length() > 5) {
            sb.setLength(sb.length() - 5);
         }

         if (debugLogger.isDebugEnabled()) {
            debug(" ejbSelect<>InEntity SQL is: " + sb.toString());
         }

         this.addInternalInEntityParmList(pNode);
      }

      return sb.toString();
   }

   public List getExternalMethodAndInEntityParmList() {
      List l = new ArrayList(this.externalMethodParmList);
      Iterator it = this.internalInEntityParmList.iterator();

      while(it.hasNext()) {
         ParamNode pNode = (ParamNode)it.next();
         l.add(pNode);
      }

      return l;
   }

   public int getPKOrGroupColumnCount() {
      return this.finderLoadsBean ? this.getGroupColumnCount() : this.getPKColumnCount();
   }

   public int getPKColumnCount() {
      return this.rdbmsBean.getPrimaryKeyFields().size();
   }

   public int getGroupColumnCount() {
      String groupName = this.getGroupName();
      FieldGroup group = this.rdbmsBean.getFieldGroup(groupName);
      if (group == null) {
         return this.rdbmsBean.getPrimaryKeyFields().size();
      } else {
         Set columns = new HashSet();
         Set finderFieldSet = new TreeSet(group.getCmpFields());
         finderFieldSet.addAll(this.rdbmsBean.getPrimaryKeyFields());
         Iterator cmpFields = finderFieldSet.iterator();

         while(cmpFields.hasNext()) {
            String cmpField = (String)cmpFields.next();
            columns.add(this.rdbmsBean.getCmpColumnForField(cmpField));
         }

         Iterator cmrFields = group.getCmrFields().iterator();

         while(cmrFields.hasNext()) {
            String cmrField = (String)cmrFields.next();
            Iterator cmrColumns = this.rdbmsBean.getForeignKeyColNames(cmrField).iterator();

            while(cmrColumns.hasNext()) {
               String cmrColumn = (String)cmrColumns.next();
               columns.add(cmrColumn);
            }
         }

         return columns.size();
      }
   }

   public String getGroupName() {
      return this.groupName == null ? "defaultGroup" : this.groupName;
   }

   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   public String getCachingName() {
      return this.cachingName;
   }

   public void setCachingName(String cachingName) {
      this.cachingName = cachingName;
   }

   public void setSqlSelectDistinct(boolean sqlSelectDistinct) {
      if (sqlSelectDistinct) {
         RDBMSBean rbean = this.getRDBMSBean();
         Loggable l = null;
         if (rbean != null) {
            l = EJBLogger.logSqlSelectDistinctDeprecatedLoggable(rbean.getEjbName(), this.getName());
         } else {
            l = EJBLogger.logSqlSelectDistinctDeprecatedLoggable("UNKNOWN", this.getName());
         }

         l.log();
         this.setSelectDistinct(sqlSelectDistinct);
      }

   }

   public void setNativeQuery(boolean val) {
      this.isNativeQuery = val;
   }

   public String generateFieldGroupSQLForFinder(RDBMSBean rdbmsBean, String selectTarget, String groupName, boolean addCachingElementFKFields, RDBMSBean prevBean) throws IllegalExpressionException {
      return this.generateFieldGroupSQL(rdbmsBean, selectTarget, groupName, addCachingElementFKFields, prevBean, true);
   }

   public String generateFieldGroupSQLForNonFinder() throws IllegalExpressionException {
      Iterator it = null;
      it = this.queryContext.getMainQuerySelectList().iterator();
      if (it.hasNext()) {
         SelectNode sn = (SelectNode)it.next();
         if (it.hasNext()) {
            throw new IllegalExpressionException(7, "Internal Error during Non-Finder FieldGroup SQL Generation:  found more than one SelectNode for FieldGroup: '" + this.getGroupName() + "', query: '" + this.getEjbQuery() + "'", new DescriptorErrorInfo("<ejb-ql>", this.getRDBMSBean().getEjbName(), this.getName()));
         } else {
            return this.generateFieldGroupSQLForNonFinder(sn.getSelectBean(), sn.getSelectTarget(), this.getGroupName(), false, (RDBMSBean)null);
         }
      } else {
         throw new IllegalExpressionException(7, "Internal Error during Non-Finder FieldGroup SQL Generation:  could not get SelectNode for FieldGroup: '" + this.getGroupName() + "', query: '" + this.getEjbQuery() + "'", new DescriptorErrorInfo("<ejb-ql>", this.getRDBMSBean().getEjbName(), this.getName()));
      }
   }

   public String generateFieldGroupSQLForNonFinder(RDBMSBean rdbmsBean, String selectTarget, String groupName, boolean addCachingElementFKFields, RDBMSBean prevBean) throws IllegalExpressionException {
      return this.generateFieldGroupSQL(rdbmsBean, selectTarget, groupName, addCachingElementFKFields, prevBean, false);
   }

   private String generateFieldGroupSQL(RDBMSBean rdbmsBean, String selectTarget, String groupName, boolean addCachingElementFKFields, RDBMSBean prevBean, boolean addPKFields) throws IllegalExpressionException {
      StringBuffer sb = new StringBuffer();
      FieldGroup group = rdbmsBean.getFieldGroup(groupName);
      if (debugLogger.isDebugEnabled()) {
         debug("rdbms ejb name- " + rdbmsBean.getEjbName());
         debug("ejb name- " + rdbmsBean.getCMPBeanDescriptor().getEJBName());
         debug("groupName- " + groupName);
      }

      Set cmpColumns = new HashSet();
      ArrayList finderFieldList;
      if (addPKFields) {
         finderFieldList = new ArrayList(rdbmsBean.getPrimaryKeyFields());
      } else {
         finderFieldList = new ArrayList();
      }

      Set groupFieldSet = new TreeSet(group.getCmpFields());
      Iterator groupFieldNames = groupFieldSet.iterator();

      while(groupFieldNames.hasNext()) {
         String fieldName = (String)groupFieldNames.next();
         if (!finderFieldList.contains(fieldName)) {
            finderFieldList.add(fieldName);
         }
      }

      Iterator fieldNames = finderFieldList.iterator();

      String cmrFieldName;
      String fullPath;
      String l;
      while(fieldNames.hasNext()) {
         cmrFieldName = (String)fieldNames.next();
         fullPath = selectTarget + "." + cmrFieldName;
         if (debugLogger.isDebugEnabled()) {
            debug("  generateFieldGroupSQL: lookup: SQL SELECT ID for '" + fullPath + "'");
         }

         l = null;
         l = this.queryContext.getTableAndColumnFromMainQuery(fullPath);
         cmpColumns.add(l);
         if (debugLogger.isDebugEnabled()) {
            debug("fieldName- " + cmrFieldName + ", columnName- " + l);
         }

         assert l != null;

         sb.append(l);
         sb.append(", ");
      }

      fieldNames = group.getCmrFields().iterator();

      Iterator it;
      String s;
      List l;
      while(fieldNames.hasNext()) {
         cmrFieldName = (String)fieldNames.next();
         fullPath = null;
         l = null;
         fullPath = this.queryContext.replaceIdAliases(selectTarget) + "." + cmrFieldName;
         l = this.queryContext.getTableAndFKColumnListForLocal11or1NPathForMainQuery(fullPath);
         it = l.iterator();

         while(it.hasNext()) {
            s = (String)it.next();
            if (!cmpColumns.contains(s)) {
               sb.append(s);
               sb.append(", ");
            }
         }
      }

      if (addCachingElementFKFields) {
         if (debugLogger.isDebugEnabled()) {
            debug("\n--- BEGIN Caching Element FK Column Insertion ---");
            debug("selectTarget=" + selectTarget);
            debug("start sb=" + sb);
         }

         cmrFieldName = selectTarget.substring(selectTarget.lastIndexOf(".") + 1);
         if (rdbmsBean == prevBean.getRelatedRDBMSBean(cmrFieldName)) {
            fullPath = null;
            l = null;
            fullPath = this.queryContext.replaceIdAliases(selectTarget);
            l = this.queryContext.getTableAndFKColumnListForLocal11or1NPathForMainQuery(fullPath);
            it = l.iterator();

            while(it.hasNext()) {
               s = (String)it.next();
               if (debugLogger.isDebugEnabled()) {
                  debug(" adding Caching Element Column: '" + s + "'");
               }

               sb.append(s);
               sb.append(", ");
            }
         }

         if (debugLogger.isDebugEnabled()) {
            debug("end   sb=" + sb + "\n");
            debug("\n--- END   Caching Element FK Column Insertion ---");
         }
      }

      sb.deleteCharAt(sb.length() - 2);
      if (debugLogger.isDebugEnabled()) {
         debug("returning: " + sb.toString());
      }

      return sb.toString();
   }

   private String generatePrimaryKeySQL(RDBMSBean rdbmsBean, String selectTarget) throws IllegalExpressionException {
      Iterator iter = rdbmsBean.getPrimaryKeyFields().iterator();
      StringBuffer sb = new StringBuffer();

      while(iter.hasNext()) {
         String fieldName = (String)iter.next();
         String id = selectTarget + "." + fieldName;
         String tableAndField = this.queryContext.getTableAndColumnFromMainQuery(id);

         assert tableAndField != null;

         sb.append(tableAndField);
         if (iter.hasNext()) {
            sb.append(", ");
         }
      }

      sb.append(" ");
      return sb.toString();
   }

   private String generateTableSQL(int selectForUpdateVal) throws IllegalExpressionException {
      return this.queryContext.getMainQueryTree().getFROMDeclaration(selectForUpdateVal);
   }

   public String replaceCorrVars(String in) {
      if (this.corrVarMap != null && in.length() != 0) {
         int pos = in.indexOf("=>");
         StringBuffer out;
         if (pos != -1) {
            out = new StringBuffer(in.substring(0, pos));
            out.append(".");
            String remaining = "";
            if (in.length() > pos + 2) {
               remaining = in.substring(pos + 2);
            }

            out.append(remaining);
            in = out.toString();
         }

         out = new StringBuffer();
         StringTokenizer st = new StringTokenizer(in, ".");

         while(st.hasMoreTokens()) {
            String token = st.nextToken();
            String replacement = token;
            CorrelationVarInfo cvi = (CorrelationVarInfo)this.corrVarMap.get(token);
            if (cvi != null) {
               replacement = cvi.getValue();
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

   public void addToCorrVarMap(String id, CorrelationVarInfo cvi) throws IllegalExpressionException {
      if (this.corrVarMap == null) {
         this.corrVarMap = new HashMap();
      }

      if (this.corrVarMap.get(id) != null) {
         Loggable l = EJBLogger.logcorrelationVarDefinedMultipleTimesLoggable(id);
         throw new IllegalExpressionException(7, l.getMessageText(), new DescriptorErrorInfo("<ejb-ql>", this.getRDBMSBean().getEjbName(), this.getName()));
      } else {
         this.corrVarMap.put(id, cvi);
      }
   }

   public void updateTableJoinList(String in) {
      if (this.tableJoinList == null) {
         this.tableJoinList = new ArrayList();
      }

      if (!this.tableJoinList.contains(in)) {
         this.tableJoinList.add(in);
      }

   }

   private void initExternalMethodParmList() {
      for(int i = 0; i < this.parameterClassTypes.length; ++i) {
         int variableNumber = i + 1;
         ParamNode pn = this.getInternalQueryParmNode(i);
         if (pn == null) {
            pn = new ParamNode((RDBMSBean)null, "param" + i, variableNumber, this.parameterClassTypes[i], "", "", false, false, (Class)null, false, false);
            this.externalMethodParmList.add(pn);
         } else {
            ParamNode newpn = new ParamNode(pn.getRDBMSBean(), "param" + i, variableNumber, this.parameterClassTypes[i], pn.getId(), pn.getRemoteHomeName(), pn.isBeanParam(), pn.isSelectInEntity(), pn.getPrimaryKeyClass(), pn.hasCompoundKey(), pn.isOracleNLSDataType());
            if (pn.hasCompoundKey()) {
               Iterator iter = pn.getParamSubList().iterator();

               while(iter.hasNext()) {
                  ParamNode subpn = (ParamNode)iter.next();
                  ParamNode newsubpn = new ParamNode(subpn.getRDBMSBean(), "param" + i, variableNumber, this.parameterClassTypes[i], subpn.getId(), subpn.getRemoteHomeName(), subpn.isBeanParam(), subpn.isSelectInEntity(), subpn.getPrimaryKeyClass(), subpn.hasCompoundKey(), subpn.isOracleNLSDataType());
                  newpn.addParamSubList(newsubpn);
               }
            }

            this.externalMethodParmList.add(newpn);
         }
      }

   }

   public String toUserLevelString(boolean withLineBreaks) {
      String EjbName = "N/A";
      if (this.rdbmsBean != null) {
         EjbName = this.rdbmsBean.getEjbName();
      }

      StringBuffer sb = new StringBuffer();
      sb.append("Query:");
      if (withLineBreaks) {
         sb.append("\n\t");
      } else {
         sb.append(", ");
      }

      if (EjbName != null) {
         sb.append("EJB Name:        " + EjbName);
         if (withLineBreaks) {
            sb.append("\n\t");
         } else {
            sb.append(", ");
         }
      }

      if (this.methodName != null) {
         sb.append("Method Name:     " + this.methodName);
         if (withLineBreaks) {
            sb.append("\n\t");
         } else {
            sb.append(", ");
         }
      }

      if (this.parameterClassTypes != null) {
         sb.append("Parameter Types: (");

         for(int i = 0; i < this.parameterClassTypes.length; ++i) {
            sb.append("" + this.parameterClassTypes[i].getName());
            if (i < this.parameterClassTypes.length - 1) {
               sb.append(", ");
            }
         }

         sb.append(")");
         if (withLineBreaks) {
            sb.append("\n\t");
         } else {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   public boolean isContentValid() {
      if (this.ejbQuery != null && !this.ejbQuery.equals("")) {
         this.sqlQuery = null;

         try {
            this.parseExpression();
            return true;
         } catch (EJBQLCompilerException var2) {
            return false;
         }
      } else {
         return false;
      }
   }

   public EJBQLCompilerException newEJBQLCompilerException(Exception e, List ejbqlTokenList) {
      ErrorCollectionException ece;
      if (!(e instanceof ErrorCollectionException)) {
         ece = new ErrorCollectionException(e);
      } else {
         ece = (ErrorCollectionException)e;
      }

      return this.newEJBQLCompilerException(ece, false, 0, "", ejbqlTokenList);
   }

   public EJBQLCompilerException newEJBQLCompilerException(Exception e, QueryContext qc) {
      ErrorCollectionException ece;
      if (!(e instanceof ErrorCollectionException)) {
         ece = new ErrorCollectionException(e);
      } else {
         ece = (ErrorCollectionException)e;
      }

      return this.newEJBQLCompilerException(ece, qc.getEjbqlRewritten(), qc.getEjbqlRewrittenReasons(), qc.getOriginalEjbql(), qc.getSQLGenEJBQLTokenList());
   }

   public EJBQLCompilerException newEJBQLCompilerException(ErrorCollectionException e, boolean ejbqlRewritten, int ejbqlRewrittenReasons, String originalEjbql, List ejbqlTokenList) {
      String ejbqlRewrittenReasonsString = this.decodeEjbqlRewrittenReasons(ejbqlRewritten, ejbqlRewrittenReasons);
      return new EJBQLCompilerException(e, ejbqlRewritten, ejbqlRewrittenReasonsString, originalEjbql, ejbqlTokenList, this.toUserLevelString(true), this.newDescriptorErrorInfo());
   }

   private String decodeEjbqlRewrittenReasons(boolean ejbqlRewritten, int ejbqlRewrittenReasons) {
      if (!ejbqlRewritten) {
         return "";
      } else {
         String EOL = "\n       ";
         StringBuffer sb = new StringBuffer();
         sb.append(EOL);
         int rewrittenReasons = ejbqlRewrittenReasons;
         if ((1 ^ ejbqlRewrittenReasons) != 0) {
            Loggable l = EJBLogger.logEJBQL_REWRITE_REASON_FACTOR_OUT_NOT_TEXTLoggable();
            String reason = l.getMessageText();
            sb.append(reason).append(EOL);
            rewrittenReasons = ejbqlRewrittenReasons - 1;
         }

         if (sb.length() <= EOL.length()) {
            sb.append("(Reason for rewrite not specified)");
         }

         if (debugLogger.isDebugEnabled() && rewrittenReasons != 0) {
            throw new AssertionError(" unhandled rewrite reason in EjbqlFinder.decodeEjbqlRewrittenReasons().  Remaining reasons integer codes are " + rewrittenReasons);
         } else {
            return sb.toString();
         }
      }
   }

   public DescriptorErrorInfo newDescriptorErrorInfo() {
      String methodName = "";
      String ejbName = "";
      if (this.rdbmsBean != null) {
         ejbName = this.rdbmsBean.getEjbName();
      }

      if (this.getName() != null) {
         methodName = this.getName();
      }

      return new DescriptorErrorInfo("<ejb-ql>", ejbName, methodName);
   }

   public static void main(String[] argv) {
      String[] queries = new String[]{"WHERE aa.bb.cc.name = 'www'", "WHERE x > y", "WHERE x = 2 AND y <> -3", "WHERE $x_id = ?1", "WHERE _$x_id <= fieldx", "WHERE xxx IN ('a', 'b', 'c')", "WHERE xxx IN (1, 2, 3)", "WHERE xxx IN (BeanB>>findByName())", "WHERE xxx IN (BeanB>>findByName(?1, ?2))", "WHERE xxx IN (BeanB>>findByName('param1-literal', 'param2-literal'))", "FROM O IN orders WHERE O.orderID = ?1 ORDERBY accountBean.id", "SELECT S FROM S FOR subAccount, I FOR S.institution WHERE ( I.zip = '94702' AND S.subBalance > 20000 ) ORDERBY I.zip, S.subBalance", "WHERE (a = ?1 AND b = ?2) AND (c = ?3 AND d = ?4)", "WHERE a = ?1 AND b = ?2 AND c = ?3", "WHERE ( a = ?1 AND b = ?2 ) AND c = ?3 AND d = ?4 AND e = ?5", "SELECT S FROM S FOR subAccount WHERE S.subAccountType = ?1 AND S.subBalance > ?2", "SELECT stud.firstname FROM StudentBean AS stud WHERE stud.firstname IN (SELECT stud1.firstname FROM StudentBean AS stud1 WHERE stud1.firstname LIKE '%d%' OR stud1.firstname LIKE '%i%' AND stud1.testid = ?1)"};
      if (argv.length == 1) {
         queries = argv;
      }

      int numTests = queries.length;
      boolean testParser = true;
      if (argv.length == 1) {
         queries = argv;
      }

      Localizer l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.ejb.container.EJBTextTextLocalizer");

      for(int i = 0; i < numTests; ++i) {
         EjbqlFinder finder = null;

         try {
            finder = new EjbqlFinder("findTest", queries[i], testParser);
         } catch (InvalidFinderException var9) {
            System.out.println(var9.getMessage());
            continue;
         }

         try {
            finder.parseExpression();
         } catch (EJBQLCompilerException var8) {
            System.out.println(l10n.get("error") + var8.toString());
         }
      }

      System.out.println(l10n.get("completeButCheck"));
   }

   public static String printQueryType(int s) {
      switch (s) {
         case 0:
            return "IS_FINDER_LOCAL_BEAN";
         case 1:
         default:
            return "UNKNOWN_QUERY_TYPE";
         case 2:
            return "IS_SELECT_THIS_BEAN";
         case 3:
            return "IS_SELECT_THIS_BEAN_FIELD";
         case 4:
            return "IS_SELECT_LOCAL_BEAN";
         case 5:
            return "IS_SELECT_LOCAL_BEAN_FIELD";
         case 6:
            return "IS_SELECT_RESULT_SET";
      }
   }

   public static String generateGroupSQLNonFinder(RDBMSBean bean, String groupName, int selectForUpdateVal) throws IllegalExpressionException {
      StringBuffer sb = new StringBuffer();
      String selectObject = "bean";
      List pkFieldNames = bean.getPrimaryKeyFields();
      String ejbql = "SELECT OBJECT(" + selectObject + ") FROM " + bean.getAbstractSchemaName() + " AS " + selectObject;

      try {
         EjbqlFinder finder = new EjbqlFinder("find__WL_Group_" + groupName, ejbql);
         finder.setRDBMSBean(bean);
         finder.setGroupName(groupName);
         finder.setFinderLoadsBean(false);
         finder.setParameterClassTypes(new Class[0]);
         finder.parseExpression();
         if (debugLogger.isDebugEnabled()) {
            debug("groupSqlNonFinder: group: '" + groupName + "',parsed expression: '" + ejbql + "'");
         }

         finder.computeSQLQuery(bean);
         if (debugLogger.isDebugEnabled()) {
            debug("groupSqlNonFinder: group: '" + groupName + "', computed WHERE clause for expression: '" + ejbql + "'");
         }

         sb.append("SELECT ");
         sb.append(finder.generateFieldGroupSQLForNonFinder());
         StringBuffer wherebuf = new StringBuffer();
         wherebuf.append(" WHERE ");
         Iterator it = pkFieldNames.iterator();

         String extraJoin;
         while(it.hasNext()) {
            extraJoin = (String)it.next();
            String pkcol = finder.getTableAndFieldForCmpField(selectObject + "." + extraJoin);
            if (debugLogger.isDebugEnabled()) {
               debug("groupSqlNonFinder: group: '" + groupName + "', for Pk field: '" + extraJoin + "', got Pk column: '" + pkcol + "'");
            }

            wherebuf.append(pkcol).append(" = ? ");
            if (it.hasNext()) {
               wherebuf.append(" AND ");
            }
         }

         sb.append(" FROM ");
         sb.append(finder.getFromSql(selectForUpdateVal));
         sb.append(wherebuf.toString());
         extraJoin = finder.getMainJoinBuffer();
         if (extraJoin.length() > 0) {
            sb.append(" AND ");
         }

         sb.append(extraJoin);
         int dbType = bean.getDatabaseType();
         switch (dbType) {
            case 0:
            case 1:
            case 3:
            case 6:
            case 8:
            case 9:
            case 10:
               sb.append(RDBMSUtils.selectForUpdateToString(selectForUpdateVal));
               break;
            case 2:
            case 5:
            case 7:
               if (selectForUpdateVal != 0 && selectForUpdateVal != 1 && selectForUpdateVal == 2) {
                  sb.append(RDBMSUtils.selectForUpdateToString(selectForUpdateVal));
               }
               break;
            case 4:
               if (selectForUpdateVal == 1) {
                  sb.append(" FOR READ ONLY WITH RS USE AND KEEP UPDATE LOCKS");
               } else if (selectForUpdateVal == 2) {
                  sb.append(RDBMSUtils.selectForUpdateToString(2));
               }
               break;
            default:
               throw new AssertionError("Undefined database type " + dbType);
         }

         if (debugLogger.isDebugEnabled()) {
            debug("groupSqlNonFinder: group: '" + groupName + "'  query is: '" + sb.toString() + "'");
         }
      } catch (Exception var12) {
         throw new IllegalExpressionException(7, "Internal Error while attempting to generate an Internal Finder for FieldGroup: '" + groupName + "'.  With Query: '" + sb.toString() + "'    " + var12.toString(), new DescriptorErrorInfo("<group-name>", bean.getEjbName(), groupName));
      }

      return sb.toString();
   }

   public CorrelationVarInfo newCorrelationVarInfo(String id, String value, boolean isCollectionValue) {
      return new CorrelationVarInfo(id, value, isCollectionValue);
   }

   public QueryCachingHandler getQueryCachingHandler(Object[] args, TTLManager mgr) {
      return !this.isQueryCachingEnabled() ? new QueryCachingHandler(this) : new QueryCachingHandler(this.getEjbQuery(), this.getMaxElements(), this, mgr);
   }

   protected boolean checkIfQueryCachingLegal(RDBMSBean rbean) {
      if (!super.checkIfQueryCachingLegal(rbean)) {
         return false;
      } else if (this.isPreparedQueryFinder) {
         Log.getInstance().logWarning(this.fmt.QUERY_CACHING_NOT_SUPORTED_FOR_PREPARED_QUERY_FINDER(rbean.getEjbName(), this.getName()));
         return false;
      } else {
         String cachingName = this.getCachingName();
         if (cachingName == null) {
            return true;
         } else {
            RelationshipCaching rc = rbean.getRelationshipCaching(cachingName);
            List cachingElements = rc.getCachingElements();

            for(int i = 0; i < cachingElements.size(); ++i) {
               RelationshipCaching.CachingElement ce = (RelationshipCaching.CachingElement)cachingElements.get(i);
               if (!this.checkIfQueryCachingLegal(ce, rbean.getRelatedRDBMSBean(ce.getCmrField()))) {
                  Log.getInstance().logWarning(this.fmt.QUERY_CACHING_FINDER_HAS_RW_CACHING_ELEMENT_CMR_FIELD(rbean.getEjbName(), this.getName(), ce.toString()));
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean checkIfQueryCachingLegal(RelationshipCaching.CachingElement cachingElement, RDBMSBean rbean) {
      String cmrField = cachingElement.getCmrField();
      if (!rbean.isReadOnly()) {
         return false;
      } else {
         List cachingElements = cachingElement.getCachingElements();
         if (cachingElements != null) {
            for(int i = 0; i < cachingElements.size(); ++i) {
               RelationshipCaching.CachingElement ce = (RelationshipCaching.CachingElement)cachingElements.get(i);
               if (!this.checkIfQueryCachingLegal(ce, rbean.getRelatedRDBMSBean(ce.getCmrField()))) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[EjbqlFinder] " + s);
   }

   public class CorrelationVarInfo {
      String id = "";
      String value = "";
      boolean isCollectionValue = false;
      boolean isRemoteInterfaceRef = false;

      public CorrelationVarInfo(String id, String value, boolean isCollectionValue) {
         this.id = id;
         this.value = value;
         this.isCollectionValue = isCollectionValue;
      }

      public void setIsRemoteInterfaceRef(boolean b) {
         this.isRemoteInterfaceRef = b;
      }

      public String getValue() {
         return this.value;
      }
   }
}
