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
import weblogic.ejb.container.dd.DDConstants;
import weblogic.logging.Loggable;
import weblogic.utils.Debug;

public class JoinNode {
   private static final DebugLogger debugLogger;
   private JoinNode prevNode;
   private String prevCMRField;
   private RDBMSBean thisBean;
   private String tableAlias;
   private String tableName;
   private final Map otherTableNameAliasMap = new HashMap();
   private final Map otherTableName2JoinSQL = new HashMap();
   private int relationshipType;
   private boolean isManyToMany;
   private boolean isRemoteInterface;
   private List joinColumns;
   private boolean doLeftOuterJoin;
   private String tableAliasMN;
   private final Map children = new HashMap();
   private QueryContext queryContext;
   public static final int PATHS_DISTINCT = -1;
   public static final int PATHS_EQUAL = 0;
   public static final int PATHS_SUBSET = 1;

   public JoinNode(JoinNode prevNode, String prevCMRField, RDBMSBean thisBean, String tableName, String tableAlias, int relationshipType, boolean isManyToMany, boolean isRemoteInterface, String tableAliasMN, QueryContext qc, List joinColumns) {
      this.prevNode = prevNode;
      this.prevCMRField = prevCMRField;
      this.thisBean = thisBean;
      this.tableName = tableName;
      this.tableAlias = tableAlias;
      this.relationshipType = relationshipType;
      this.isManyToMany = isManyToMany;
      this.isRemoteInterface = isRemoteInterface;
      this.tableAliasMN = tableAliasMN;
      this.queryContext = qc;
      this.joinColumns = joinColumns;
   }

   public QueryContext getQueryContext() {
      return this.queryContext;
   }

   private int getDatabaseType() {
      return this.thisBean.getDatabaseType();
   }

   public void setDoLeftOuterJoin(boolean b) throws IllegalExpressionException {
      int databaseType = this.getDatabaseType();
      Loggable l;
      if (databaseType == 0) {
         l = EJBLogger.logCannotDoOuterJoinForUnspecifiedDBLoggable();
         throw new IllegalExpressionException(7, l.getMessageText());
      } else {
         if (this.isManyToMany && !this.isRemoteInterface) {
            if (!RDBMSUtils.dbSupportForMultiLeftOuterJoin(databaseType)) {
               l = EJBLogger.logCannotDoMultiOuterJoinForDBLoggable(this.prevNode.getRDBMSBean().getEjbName(), this.thisBean.getEjbName(), DDConstants.getDBNameForType(databaseType));
               throw new IllegalExpressionException(7, l.getMessageText());
            }
         } else if (!RDBMSUtils.dbSupportForSingleLeftOuterJoin(databaseType)) {
            l = EJBLogger.logCannotDoOuterJoinForDBLoggable(this.prevNode.getRDBMSBean().getEjbName(), this.thisBean.getEjbName(), DDConstants.getDBNameForType(databaseType));
            throw new IllegalExpressionException(7, l.getMessageText());
         }

         this.doLeftOuterJoin = b;
      }
   }

   public boolean getDoLeftOuterJoin() {
      return this.doLeftOuterJoin;
   }

   public int getAllDoLeftOuterJoinCount() {
      int count = 0;
      if (this.doLeftOuterJoin) {
         if (this.isManyToMany) {
            count = 2;
         } else {
            count = 1;
         }
      }

      JoinNode n;
      for(Iterator var2 = this.children.values().iterator(); var2.hasNext(); count += n.getAllDoLeftOuterJoinCount()) {
         n = (JoinNode)var2.next();
      }

      return count;
   }

   boolean isLeftOuterJoinANSIRoot() {
      if (!this.hasChildren()) {
         return false;
      } else if (this.isDoLeftOuterJoinANSI()) {
         return false;
      } else {
         Iterator it = this.getChildrenIterator();

         JoinNode n;
         do {
            if (!it.hasNext()) {
               return false;
            }

            n = (JoinNode)it.next();
         } while(!n.isDoLeftOuterJoinANSI());

         return true;
      }
   }

   boolean isDoLeftOuterJoinANSI() {
      if (!this.getDoLeftOuterJoin()) {
         return false;
      } else {
         int databaseType = this.getDatabaseType();
         switch (databaseType) {
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
               return true;
            case 5:
            default:
               return false;
         }
      }
   }

   public JoinNode getPrevNode() {
      return this.prevNode;
   }

   public String getPrevCMRField() {
      return this.prevCMRField;
   }

   public int getRelationshipType() {
      return this.relationshipType;
   }

   public boolean getIsManyToMany() {
      return this.isManyToMany;
   }

   public String getJoinTableAlias() {
      return this.tableAliasMN;
   }

   public String getTableName() {
      return this.tableName;
   }

   public String getTableAlias() {
      return this.tableAlias;
   }

   public void setTableAlias(String s) {
      this.tableAlias = s;
   }

   public List getTableAlias(List l) {
      if (this.tableAlias.length() > 0) {
         l.add(this.tableAlias);
      }

      if (this.isManyToMany) {
         l.add(this.tableAliasMN);
      }

      Iterator it = this.otherTableNameAliasMap.values().iterator();

      while(it.hasNext()) {
         l.add(it.next());
      }

      Iterator var3 = this.children.values().iterator();

      while(var3.hasNext()) {
         JoinNode n = (JoinNode)var3.next();
         n.getTableAlias(l);
      }

      return l;
   }

   public boolean otherTableNameContains(String tName) {
      return this.otherTableNameAliasMap.containsKey(tName);
   }

   public String getAnyTableNameAlias(String tName) throws IllegalExpressionException {
      return tName.equals(this.tableName) ? this.tableAlias : this.getOtherTableNameAlias(tName);
   }

   public String getOtherTableNameAlias(String tName) throws IllegalExpressionException {
      if (tName.equals(this.tableName)) {
         throw new IllegalExpressionException(7, " Internal Error: attempt to getOtherTableNameAlias on default table: '" + tName + "', this indicates an internal problem.  Should not be attempting to getOtherTableNameAlias on the default JoinNode.  contact technical support");
      } else {
         String alias = (String)this.otherTableNameAliasMap.get(tName);
         if (alias != null) {
            return alias;
         } else if (!this.thisBean.hasTable(tName)) {
            throw new IllegalExpressionException(7, "Attempt to register Table '" + tName + "'. for Bean: '" + this.thisBean.getEjbName() + "'  but that Table is not mapped to the Bean.");
         } else {
            alias = this.registerTable(tName);
            this.otherTableNameAliasMap.put(tName, alias);
            return alias;
         }
      }
   }

   public boolean isExcluded(List tableAliasExclusion) {
      if (tableAliasExclusion == null) {
         return false;
      } else if (tableAliasExclusion.size() == 0) {
         return false;
      } else if (tableAliasExclusion.contains(this.getTableAlias())) {
         return true;
      } else {
         return tableAliasExclusion.contains(this.getJoinTableAlias());
      }
   }

   public boolean hasChild(String cmrField) {
      return this.children.containsKey(cmrField);
   }

   public JoinNode getChild(String cmrField) {
      return (JoinNode)this.children.get(cmrField);
   }

   public void putChild(String cmrField, JoinNode child) {
      this.children.put(cmrField, child);
   }

   boolean hasChildren() {
      return this.getChildren().keySet().size() > 0;
   }

   public Map getChildren() {
      return this.children;
   }

   Iterator getChildrenIterator() {
      return this.getChildren().values().iterator();
   }

   String getFROMDeclaration(List tableAliasExclusion, int selectForUpdateVal) throws IllegalExpressionException {
      StringBuffer sb = new StringBuffer();
      if (this.getDatabaseType() == 1) {
         this.getOracleFROM(selectForUpdateVal, sb);
      } else {
         this.addANSIFROM(tableAliasExclusion, selectForUpdateVal, sb);
         this.addNonANSIFROM(tableAliasExclusion, selectForUpdateVal, sb);
      }

      return sb.toString();
   }

   private void addANSIFROM(List tableAliasExclusion, int selectForUpdateVal, StringBuffer sb) {
      if (!this.isExcluded(tableAliasExclusion) && (this.isLeftOuterJoinANSIRoot() || this.isDoLeftOuterJoinANSI())) {
         String tableAlias = this.getTableAlias();
         if (tableAlias != null && tableAlias.length() > 0) {
            String tableName = this.queryContext.getTableNameForAlias(tableAlias);
            if (this.isLeftOuterJoinANSIRoot()) {
               this.addTableToFROM(tableName, tableAlias, selectForUpdateVal, sb);
            } else {
               String joinTableAlias = this.getJoinTableAlias();
               if (joinTableAlias != null && joinTableAlias.length() > 0) {
                  String joinTableName = this.queryContext.getTableNameForAlias(joinTableAlias);
                  this.addANSIOuterJoinTableToFROM(joinTableName, joinTableAlias, selectForUpdateVal, sb);
               }

               this.addANSIOuterJoinTableToFROM(tableName, tableAlias, selectForUpdateVal, sb);
            }
         }
      }

      Iterator it = this.getChildrenIterator();

      while(it.hasNext()) {
         JoinNode jn = (JoinNode)it.next();
         jn.addANSIFROM(tableAliasExclusion, selectForUpdateVal, sb);
      }

   }

   private void addNonANSIFROM(List tableAliasExclusion, int selectForUpdateVal, StringBuffer sb) {
      Iterator it;
      if (!this.isExcluded(tableAliasExclusion)) {
         if (!this.isLeftOuterJoinANSIRoot() && !this.isDoLeftOuterJoinANSI()) {
            String tableAlias = this.getTableAlias();
            String joinTableAlias;
            if (tableAlias != null && tableAlias.length() > 0) {
               joinTableAlias = this.queryContext.getTableNameForAlias(tableAlias);
               this.addTableToFROM(joinTableAlias, tableAlias, selectForUpdateVal, sb);
            }

            joinTableAlias = this.getJoinTableAlias();
            if (joinTableAlias != null && joinTableAlias.length() > 0) {
               String joinTableName = this.queryContext.getTableNameForAlias(joinTableAlias);
               this.addTableToFROM(joinTableName, joinTableAlias, selectForUpdateVal, sb);
            }
         }

         if (this.otherTableNameAliasMap.keySet().size() > 0) {
            it = this.otherTableNameAliasMap.entrySet().iterator();

            while(it.hasNext()) {
               Map.Entry me = (Map.Entry)it.next();
               this.addTableToFROM((String)me.getKey(), (String)me.getValue(), selectForUpdateVal, sb);
            }
         }
      }

      it = this.getChildrenIterator();

      while(it.hasNext()) {
         JoinNode jn = (JoinNode)it.next();
         jn.addNonANSIFROM(tableAliasExclusion, selectForUpdateVal, sb);
      }

   }

   private void getOracleFROM(int selectForUpdateVal, StringBuffer sb) throws IllegalExpressionException {
      List list;
      try {
         list = getTableAliasList(this);
      } catch (Exception var7) {
         throw new IllegalExpressionException(7, var7.getMessage());
      }

      for(int i = list.size() - 1; i >= 0; --i) {
         String tableAlias = (String)list.get(i);
         String tableName = this.queryContext.getTableNameForAlias(tableAlias);
         this.addTableToFROM(tableName, tableAlias, selectForUpdateVal, sb);
      }

   }

   private void addTableToFROM(String tableName, String tableAlias, int selectForUpdateVal, StringBuffer sb) {
      if (sb.length() > 0) {
         sb.append(", ");
      }

      sb.append(RDBMSUtils.escQuotedID(tableName));
      sb.append(" ");
      sb.append(tableAlias);
      sb.append(" ");
      sb.append(this.queryContext.getFROMClauseSelectForUpdate(selectForUpdateVal));
   }

   private void addANSIOuterJoinTableToFROM(String tableName, String tableAlias, int selectForUpdateVal, StringBuffer sb) {
      if (this.isUseInnerJoin()) {
         sb.append("INNER JOIN ");
      } else {
         sb.append("LEFT OUTER JOIN ");
      }

      sb.append(RDBMSUtils.escQuotedID(tableName));
      sb.append(" ");
      sb.append(tableAlias);
      sb.append(" ");
      sb.append(this.queryContext.getFROMClauseSelectForUpdate(selectForUpdateVal));
      sb.append(" ON ");
      String andClause = "AND ";
      Iterator it = this.joinColumns.iterator();

      while(it.hasNext()) {
         List l = (List)it.next();
         Debug.assertion(l.size() == 2, "Internal Error !!  For Prev Bean: '" + this.prevNode.getRDBMSBean().getEjbName() + "' and Bean: '" + this.thisBean.getEjbName() + "', we expect all joinColumn List elements to contain 2 Columns. Instead we encountered one with '" + l.size() + "' Columns !");
         Iterator it2 = l.iterator();
         String lhs = (String)it2.next();
         String rhs = (String)it2.next();
         if (rhs.startsWith(tableAlias + ".")) {
            sb.append(lhs).append(" = ").append(rhs).append(" ");
            if (it.hasNext()) {
               sb.append(andClause);
            }
         }
      }

      String ending = sb.substring(sb.length() - andClause.length());
      if (ending.equals(andClause)) {
         sb.delete(sb.length() - andClause.length(), sb.length());
      }

   }

   public void setJoinSQL(List l) {
      this.joinColumns = l;
   }

   public void addJoinSQL(List l) {
      this.joinColumns.add(l);
   }

   public String getJoinSQL() throws IllegalExpressionException {
      StringBuffer sb = new StringBuffer();
      Iterator it1 = this.joinColumns.iterator();

      while(true) {
         int databaseType;
         do {
            do {
               if (!it1.hasNext()) {
                  if (this.prevNode != null && this.prevCMRField != null) {
                     String cmrfJoin = this.prevNode.getJoinSQLForCmrf(this.prevCMRField);
                     if (cmrfJoin != null) {
                        if (this.joinColumns.size() > 0) {
                           sb.append(" AND ");
                        }

                        sb.append(cmrfJoin);
                     }
                  }

                  return sb.toString();
               }

               List l = (List)it1.next();
               if (l.size() != 2) {
                  throw new IllegalExpressionException(7, "Internal Error !!  For Prev Bean: '" + this.prevNode.getRDBMSBean().getEjbName() + "' and Bean: '" + this.thisBean.getEjbName() + "', we expect all joinColumn List elements to contain 2 Columns. Instead we encountered one with '" + l.size() + "' Columns !");
               }

               Iterator it2 = l.iterator();
               String lhs = (String)it2.next();
               String rhs = (String)it2.next();
               if (this.getDoLeftOuterJoin()) {
                  if (!this.isDoLeftOuterJoinANSI()) {
                     databaseType = this.getDatabaseType();
                     switch (databaseType) {
                        case 1:
                           if (this.isUseInnerJoin()) {
                              sb.append(rhs).append(" = ").append(lhs);
                           } else {
                              sb.append(rhs).append(" (+)= ").append(lhs);
                           }
                           break;
                        case 2:
                        case 5:
                        case 10:
                           sb.append(lhs).append(" *= ").append(rhs);
                           break;
                        case 3:
                        case 4:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        default:
                           throw new IllegalExpressionException(7, "Internal Error !!  For Prev Bean: '" + this.prevNode.getRDBMSBean().getEjbName() + "' and Bean: '" + this.thisBean.getEjbName() + "', we were asked to do a Left Outer Join in the WHERE Clause for Database: '" + databaseType + "', but we don't know how to do this !");
                     }
                  }
               } else {
                  sb.append(lhs).append(" = ").append(rhs);
               }
            } while(!it1.hasNext());

            databaseType = this.getDatabaseType();
         } while(this.getDoLeftOuterJoin() && this.isDoLeftOuterJoinANSI() && (databaseType == 4 || databaseType == 9 || databaseType == 7 || databaseType == 8 || databaseType == 10 || databaseType == 6));

         sb.append(" AND ");
      }
   }

   public RDBMSBean getRDBMSBean() {
      return this.thisBean;
   }

   public void parseJoin(String pathExpression) throws IllegalExpressionException {
      if (debugLogger.isDebugEnabled()) {
         debug("  parse pathExpression: " + pathExpression);
      }

      if (pathExpression.length() != 0) {
         String remainingExpression = "";
         String currField = null;
         if (pathExpression.indexOf(".") == -1) {
            String colname = this.thisBean.getCmpColumnForField(pathExpression);
            if (colname == null) {
               colname = this.thisBean.getCmpColumnForVariable(pathExpression);
            }

            if (colname != null) {
               return;
            }

            currField = pathExpression;
         } else {
            int pos = pathExpression.indexOf(".");
            currField = pathExpression.substring(0, pos);
            remainingExpression = pathExpression.substring(pos + 1);
         }

         if (this.children.containsKey(currField)) {
            JoinNode n = (JoinNode)this.children.get(currField);
            n.parseJoin(remainingExpression);
         } else {
            StringBuffer joinBuf = new StringBuffer();
            RDBMSBean prevBean = this.thisBean;
            String joinTableName;
            String joinTableAlias;
            String currTableAlias;
            String otherCMRField;
            Iterator fkColIter;
            String fk;
            String newTableName;
            if (prevBean.isRemoteField(currField)) {
               Loggable l;
               if (remainingExpression.length() > 0) {
                  l = EJBLogger.logfinderTerminalCMRNotRemoteLoggable(currField, prevBean.getEjbName());
                  throw new IllegalExpressionException(7, l.getMessageText());
               } else if (prevBean.containsFkField(currField)) {
                  if (debugLogger.isDebugEnabled()) {
                     debug(" Bean contains FK Field. NO Join Node For Bean Type: Remote Interface. ");
                  }

               } else {
                  if (debugLogger.isDebugEnabled()) {
                     debug(" Doing join table JOIN for Bean: " + prevBean.getEjbName() + ", cmr-field: " + currField + " to Remote Interface ");
                  }

                  if (!prevBean.isForeignKeyField(currField)) {
                     l = EJBLogger.logfinderCouldNotGetFKColumnsLoggable(prevBean.getEjbName(), currField, "JOIN Calculation");
                     throw new IllegalExpressionException(7, l.getMessageText());
                  } else {
                     String theFKTableAlias = this.getFKTableAliasAndSQLForCmrf(currField);
                     joinTableName = prevBean.getJoinTableName(currField);
                     joinTableAlias = this.registerTable(joinTableName);
                     Map fk2pkColMap = prevBean.getColumnMapForCmrfAndPkTable(currField, joinTableName);
                     if (fk2pkColMap == null) {
                        throw new IllegalExpressionException(7, " could not find Map of foreign keys and primary keys table for Bean: " + prevBean.getEjbName() + "  Remote cmr-field: " + currField);
                     } else {
                        List joinCols = new ArrayList();
                        fkColIter = fk2pkColMap.entrySet().iterator();

                        while(fkColIter.hasNext()) {
                           Map.Entry entry = (Map.Entry)fkColIter.next();
                           fk = (String)entry.getKey();
                           newTableName = (String)entry.getValue();
                           currTableAlias = theFKTableAlias + "." + RDBMSUtils.escQuotedID(newTableName);
                           otherCMRField = joinTableAlias + "." + RDBMSUtils.escQuotedID(fk);
                           joinCols.add(createJoinListEntry(currTableAlias, otherCMRField));
                        }

                        if (debugLogger.isDebugEnabled()) {
                           debug(" add new join table Join Node for Remote Interface with JOIN: " + joinBuf.toString());
                        }

                        JoinNode nextNode = this.newJoinNode(this, currField, (RDBMSBean)null, joinTableName, "", 8, true, true, joinTableAlias, joinCols);
                        this.children.put(currField, nextNode);
                     }
                  }
               }
            } else {
               RDBMSBean currBean = prevBean.getRelatedRDBMSBean(currField);
               if (currBean == null) {
                  Loggable l = EJBLogger.logejbqlIdNotFieldAndNotBeanLoggable(currField);
                  throw new IllegalExpressionException(7, l.getMessageText());
               } else {
                  String thePKTableName;
                  Map fk2pkColMap;
                  ArrayList joinCols;
                  Iterator fkColIter;
                  Map.Entry entry;
                  String lhs;
                  String pk;
                  String lhs;
                  String rhs;
                  if (prevBean.isManyToManyRelation(currField)) {
                     if (debugLogger.isDebugEnabled()) {
                        debug(" Doing Many-to-Many JOIN for Bean: " + prevBean.getEjbName() + ", cmr-field: " + currField);
                     }

                     joinTableName = prevBean.getJoinTableName(currField);
                     joinTableAlias = this.registerTable(joinTableName);
                     if (debugLogger.isDebugEnabled()) {
                        debug(" cmrfield: " + currField + ", joinTable name: " + joinTableName + ", joinTableAlias: " + joinTableAlias);
                     }

                     if (debugLogger.isDebugEnabled()) {
                        debug("processing symmetric field: '" + currField + "'");
                     }

                     thePKTableName = this.getTableName();
                     String thePKTableAlias = this.getTableAlias();
                     if (debugLogger.isDebugEnabled()) {
                        debug(" the LHS Table is: '" + thePKTableName + "',  with Alias: '" + thePKTableAlias + "'");
                     }

                     if (prevBean.isSymmetricField(currField)) {
                        if (debugLogger.isDebugEnabled()) {
                           debug("processing     symmetric field: '" + currField + "'");
                        }

                        fk2pkColMap = prevBean.getSymColumnMapForCmrfAndPkTable(currField, thePKTableName);
                     } else {
                        if (debugLogger.isDebugEnabled()) {
                           debug("processing non-symmetric field: '" + currField + "'");
                        }

                        if (!prevBean.isForeignKeyField(currField)) {
                           Loggable l = EJBLogger.logfinderCMRFieldNotFKLoggable(prevBean.getEjbName(), currField, "M-N Relationship JOIN Calculation");
                           throw new IllegalExpressionException(7, l.getMessageText());
                        }

                        fk2pkColMap = prevBean.getColumnMapForCmrfAndPkTable(currField, this.tableName);
                     }

                     if (fk2pkColMap == null) {
                        throw new IllegalExpressionException(7, " could not find Map of foreign keys and primary keys table for EJB: '" + prevBean.getEjbName() + "'  cmr-field: '" + currField + "'   dest PK Table: '" + thePKTableName + "'  Join Table Name: '" + joinTableName + "'.   Please check your RDBMS Deployment Descriptors for this EJB.");
                     }

                     joinCols = new ArrayList();
                     fkColIter = fk2pkColMap.entrySet().iterator();

                     String fk;
                     while(fkColIter.hasNext()) {
                        entry = (Map.Entry)fkColIter.next();
                        currTableAlias = (String)entry.getKey();
                        otherCMRField = (String)entry.getValue();
                        lhs = thePKTableAlias + "." + RDBMSUtils.escQuotedID(otherCMRField);
                        fk = joinTableAlias + "." + RDBMSUtils.escQuotedID(currTableAlias);
                        joinCols.add(createJoinListEntry(lhs, fk));
                     }

                     if (debugLogger.isDebugEnabled()) {
                        debug(" joinSQL after 'from' side processed  '" + joinBuf.toString() + "'");
                     }

                     newTableName = RDBMSUtils.escQuotedID(currBean.chooseTableAsJoinTarget());
                     currTableAlias = this.registerTable(newTableName);
                     otherCMRField = prevBean.getRelatedFieldName(currField);
                     if (debugLogger.isDebugEnabled()) {
                        debug(" processing other side of M-N join:  for RHS Bean '" + currBean.getEjbName() + "',  we've chosen RHS Table: '" + newTableName + "'  with TableAlias: '" + currTableAlias + "'");
                     }

                     fk2pkColMap = currBean.getColumnMapForCmrfAndPkTable(otherCMRField, newTableName);
                     if (fk2pkColMap == null) {
                        throw new IllegalExpressionException(7, " could not find Map of foreign keys and primary keys table for Bean: '" + currBean.getEjbName() + "'   dest PK Table: '" + joinTableName + "'   cmr-field: '" + otherCMRField + "'.   Please check your RDBMS Deployment Descriptors for this EJB.");
                     }

                     fkColIter = fk2pkColMap.entrySet().iterator();
                     joinBuf.append(" AND ");

                     while(fkColIter.hasNext()) {
                        Map.Entry entry = (Map.Entry)fkColIter.next();
                        fk = (String)entry.getKey();
                        pk = (String)entry.getValue();
                        lhs = joinTableAlias + "." + RDBMSUtils.escQuotedID(fk);
                        rhs = currTableAlias + "." + RDBMSUtils.escQuotedID(pk);
                        joinCols.add(createJoinListEntry(lhs, rhs));
                     }

                     if (debugLogger.isDebugEnabled()) {
                        debug(" add new Many-to-Many Join Node with JOIN: " + joinBuf.toString());
                     }

                     JoinNode nextNode = this.newJoinNode(this, currField, currBean, newTableName, currTableAlias, 6, true, false, joinTableAlias, joinCols);
                     this.children.put(currField, nextNode);
                     if (remainingExpression.length() > 0) {
                        nextNode.parseJoin(remainingExpression);
                     }
                  } else {
                     joinTableName = this.tableAlias;
                     fk2pkColMap = null;
                     joinCols = null;
                     fkColIter = null;
                     entry = null;
                     boolean prevHasFK = prevBean.isForeignKeyField(currField);
                     Map fk2pkColMap;
                     String fkTable;
                     byte relationshipType;
                     if (prevHasFK) {
                        if (debugLogger.isDebugEnabled()) {
                           debug("\n\n parseJoin 1-N or 1-1.  prevHasFK.  about to call getFKTableAliasAndSQLForCmrf on cmrfield: '" + currField + "'\n\n");
                        }

                        lhs = this.getFKTableAliasAndSQLForCmrf(currField);
                        joinTableAlias = RDBMSUtils.escQuotedID(currBean.chooseTableAsJoinTarget());
                        thePKTableName = this.registerTable(joinTableAlias);
                        newTableName = thePKTableName;
                        fkTable = lhs;
                        fk2pkColMap = prevBean.getColumnMapForCmrfAndPkTable(currField, joinTableAlias);
                        if (prevBean.isOneToManyRelation(currField)) {
                           relationshipType = 5;
                        } else {
                           relationshipType = 2;
                        }
                     } else {
                        fk = prevBean.getRelatedFieldName(currField);
                        if (fk.length() < 1) {
                           throw new IllegalExpressionException(7, "Could not find cmr-field in Bean: " + currBean.getEjbName() + " that points to EJBean: " + prevBean.getEjbName());
                        }

                        joinTableAlias = currBean.getTableForCmrField(fk);
                        thePKTableName = this.registerTable(joinTableAlias);
                        newTableName = joinTableName;
                        fkTable = thePKTableName;
                        fk2pkColMap = currBean.getColumnMapForCmrfAndPkTable(fk, this.tableName);
                        if (prevBean.isOneToManyRelation(currField)) {
                           relationshipType = 4;
                        } else {
                           relationshipType = 3;
                        }
                     }

                     fkColIter = fk2pkColMap.entrySet().iterator();
                     if (fkColIter.hasNext() && joinBuf.length() > 0) {
                        joinBuf.append(" AND ");
                     }

                     List joinCols = new ArrayList();

                     while(fkColIter.hasNext()) {
                        Map.Entry entry = (Map.Entry)fkColIter.next();
                        pk = (String)entry.getKey();
                        lhs = (String)entry.getValue();
                        rhs = fkTable + "." + RDBMSUtils.escQuotedID(pk);
                        String rhs = newTableName + "." + RDBMSUtils.escQuotedID(lhs);
                        if (prevHasFK) {
                           joinCols.add(createJoinListEntry(rhs, rhs));
                        } else {
                           joinCols.add(createJoinListEntry(rhs, rhs));
                        }
                     }

                     if (debugLogger.isDebugEnabled()) {
                        debug(" add new Join Node with JOIN: " + joinBuf.toString());
                     }

                     JoinNode nextNode = this.newJoinNode(this, currField, currBean, joinTableAlias, thePKTableName, relationshipType, false, false, "", joinCols);
                     if (debugLogger.isDebugEnabled()) {
                        debug(" add new JoinNode to children ");
                     }

                     this.children.put(currField, nextNode);
                     if (this.queryContext.mainQueryContainsInSelectListForCachingElement(prevBean, currBean)) {
                        nextNode.setDoLeftOuterJoin(true);
                     }

                     if (remainingExpression.length() > 0) {
                        nextNode.parseJoin(remainingExpression);
                     }
                  }

               }
            }
         }
      }
   }

   public String getFKTableAliasAndSQLForCmrf(String cmrField) throws IllegalExpressionException {
      String theFKTable = this.thisBean.getTableForCmrField(cmrField);
      if (theFKTable == null) {
         throw new IllegalExpressionException(7, " could not find foreign key table for Bean: " + this.thisBean.getEjbName() + "  cmr-field: " + cmrField);
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("  in getFKTableAliasAndSQLForCmrf  for cmrField '" + cmrField + "',  theFKTable is '" + theFKTable + "',  default TableName is: '" + this.tableName + "'");
         }

         String theFKTableAlias;
         if (!theFKTable.equals(this.tableName)) {
            theFKTableAlias = this.getOtherTableNameAlias(theFKTable);
            this.getInnerBeanJoinSQLMaybe(theFKTable);
         } else {
            theFKTableAlias = this.tableAlias;
         }

         return theFKTableAlias;
      }
   }

   private String getInnerBeanJoinSQLMaybe(String destTable) throws IllegalExpressionException {
      return this.tableName.equals(destTable) ? null : this.getInnerBeanJoinSQL(destTable);
   }

   private String getInnerBeanJoinSQL(String destTable) throws IllegalExpressionException {
      String result = (String)this.otherTableName2JoinSQL.get(destTable);
      if (result != null) {
         return result;
      } else {
         StringBuffer sb = new StringBuffer();
         String destTableAlias = this.getOtherTableNameAlias(destTable);
         Map thisTablePKColMap = this.thisBean.getPKCmpf2ColumnForTable(this.tableName);
         if (thisTablePKColMap == null) {
            throw new IllegalExpressionException(7, "Internal Error in getInnerBeanJoinSQL, thisTablePKColMap:  Bean: '" + this.thisBean.getEjbName() + "', could not get Primary Key Field to Column Map for Table: '" + this.tableName + "'");
         } else {
            Map destTablePKColMap = this.thisBean.getPKCmpf2ColumnForTable(destTable);
            if (destTablePKColMap == null) {
               throw new IllegalExpressionException(7, "Internal Errorin getInnerBeanJoinSQL, destTablePKColMap:  Bean: '" + this.thisBean.getEjbName() + "', could not get Primary Key Field to Column Map for Table: '" + destTable + "'");
            } else {
               Iterator pkFieldIter = thisTablePKColMap.entrySet().iterator();

               while(pkFieldIter.hasNext()) {
                  Map.Entry entry = (Map.Entry)pkFieldIter.next();
                  String pkField = (String)entry.getKey();
                  String thisPKCol = (String)entry.getValue();
                  String thatPKCol = (String)destTablePKColMap.get(pkField);
                  sb.append(this.tableAlias + "." + RDBMSUtils.escQuotedID(thisPKCol) + " = " + destTableAlias + "." + RDBMSUtils.escQuotedID(thatPKCol));
                  if (pkFieldIter.hasNext()) {
                     sb.append(" AND ");
                  }
               }

               result = sb.toString();
               this.otherTableName2JoinSQL.put(destTable, result);
               return result;
            }
         }
      }
   }

   public String getJoinSQLForCmrf(String cmrfield) {
      String fkTableName = this.thisBean.getTableForCmrField(cmrfield);
      return fkTableName == null ? null : (String)this.otherTableName2JoinSQL.get(fkTableName);
   }

   void forceInternalMultiTableJoinMaybe(QueryNode queryTree, String destTable) throws IllegalExpressionException {
      if (!this.tableName.equals(destTable)) {
         this.forceInternalMultiTableJoin(queryTree, destTable);
      }
   }

   void forceInternalMultiTableJoin(QueryNode queryTree, String destTable) throws IllegalExpressionException {
      String defaultTableAlias = this.getTableAlias();
      String destTableAlias = this.getOtherTableNameAlias(destTable);
      String joinSQL = this.getInnerBeanJoinSQLMaybe(destTable);
      if (joinSQL != null) {
         queryTree.addCmpFieldJoinSQL(defaultTableAlias, destTableAlias, joinSQL);
      }

   }

   private String registerTable(String name) {
      return this.queryContext.registerTable(name);
   }

   public static String getPathWithoutTrailingCmpField(JoinNode root, String pathExpression) throws IllegalExpressionException {
      if (pathExpression == null) {
         return "";
      } else {
         root.parseJoin(pathExpression);
         if (!endsInField(root, pathExpression)) {
            return pathExpression;
         } else {
            int pos = pathExpression.lastIndexOf(".");
            if (pos == -1) {
               throw new IllegalExpressionException(7, "Path Expression: '" + pathExpression + "' is a cmp-field without a Range Variable or cmr-field pointing to it.  cmp-fields alone are not allowed in EJB QL Queries, they must be qualified.  Please re-examine your query.");
            } else {
               return pathExpression.substring(0, pos);
            }
         }
      }
   }

   public static int getRelationshipTypeForPathExpression(QueryContext queryContext, JoinNode root, String pathExpression) throws IllegalExpressionException {
      String dealiasedPath = pathExpression;
      if (queryContext != null) {
         dealiasedPath = queryContext.replaceIdAliases(pathExpression);
      }

      root.parseJoin(pathExpression);
      int pathNodeCount = countPathNodes(dealiasedPath);
      if (pathNodeCount < 1) {
         return -1;
      } else if (pathNodeCount == 1) {
         return 1;
      } else if (endsInField(root, dealiasedPath)) {
         return 0;
      } else {
         JoinNode lastNode = getNode(root, dealiasedPath);
         return lastNode.getRelationshipType();
      }
   }

   public static int getRelationshipTypeForPathExpressionWithNoSQLGen(QueryContext queryContext, JoinNode root, String pathExpression) throws IllegalExpressionException {
      JoinNode trialJoinTree = queryContext.makeTrialJoinRoot(root, pathExpression);
      return getRelationshipTypeForPathExpression(queryContext, trialJoinTree, pathExpression);
   }

   public static RDBMSBean getLastRDBMSBeanForPathExpressionWithNoSQLGen(QueryContext queryContext, JoinNode root, String pathExpression) throws IllegalExpressionException {
      JoinNode trialJoinTree = queryContext.makeTrialJoinRoot(root, pathExpression);
      trialJoinTree.parseJoin(pathExpression);
      JoinNode lastNode = getNode(trialJoinTree, pathExpression);
      return lastNode.getRDBMSBean();
   }

   public static boolean endsInField(JoinNode root, String pathExpression) throws IllegalExpressionException {
      JoinNode node = root;
      String cmrf = "";

      for(StringTokenizer st = new StringTokenizer(pathExpression, "."); st.hasMoreTokens(); node = node.getChild(cmrf)) {
         cmrf = (String)st.nextElement();
         if (!node.hasChild(cmrf)) {
            RDBMSBean rbean = node.getRDBMSBean();
            if (rbean.isRemoteField(cmrf)) {
               return false;
            }

            String colname = rbean.getCmpColumnForField(cmrf);
            if (colname == null) {
               colname = rbean.getCmpColumnForVariable(cmrf);
            }

            if (colname != null) {
               if (st.hasMoreTokens()) {
                  throw new IllegalExpressionException(7, "called endsInField on a pathExpression with an embedded field: " + pathExpression);
               }

               return true;
            }

            if (rbean.getCmrFieldNames().contains(cmrf)) {
               return false;
            }

            throw new IllegalExpressionException(7, "called endsInField on a pathExpression with field: '" + cmrf + "' that's neither Field nor Bean: " + pathExpression);
         }
      }

      return false;
   }

   public static boolean endsInLocalRelationship(JoinNode root, String pathExpression) throws IllegalExpressionException {
      if (endsInRemoteInterface(root, pathExpression)) {
         return false;
      } else if (endsInField(root, pathExpression)) {
         return false;
      } else {
         JoinNode node = root;
         String target_cmrf = getLastFieldFromId(pathExpression);
         StringTokenizer st = new StringTokenizer(pathExpression, ".");

         while(st.hasMoreTokens()) {
            String local_cmrf = (String)st.nextElement();
            if (node.hasChild(local_cmrf)) {
               if (target_cmrf.equals(local_cmrf)) {
                  RDBMSBean theBean = node.getRDBMSBean();
                  if (theBean.getCmrFieldNames().contains(target_cmrf)) {
                     return true;
                  }

                  throw new IllegalExpressionException(7, "unable to determine if  pathExpression '" + pathExpression + "' is terminated by a Remote Relationship, a cmp-field or a Local Relationship.    It appears to be none of them.");
               }

               node = node.getChild(local_cmrf);
            }
         }

         throw new IllegalExpressionException(7, "unable to determine if  pathExpression '" + pathExpression + "' is terminated by a Remote Relationship, a cmp-field or a Local Relationship.    It appears to be none of them.    Out of Tokens.");
      }
   }

   public static boolean endsInRemoteInterface(JoinNode root, String pathExpression) throws IllegalExpressionException {
      try {
         if (endsInField(root, pathExpression)) {
            return false;
         }
      } catch (IllegalExpressionException var7) {
      }

      JoinNode node = root;
      String cmrf = "";

      for(StringTokenizer st = new StringTokenizer(pathExpression, "."); st.hasMoreTokens(); node = node.getChild(cmrf)) {
         cmrf = (String)st.nextElement();
         if (!node.hasChild(cmrf) || !st.hasMoreTokens()) {
            RDBMSBean rbean = node.getRDBMSBean();
            String colname = rbean.getCmpColumnForField(cmrf);
            if (colname == null) {
               colname = rbean.getCmpColumnForVariable(cmrf);
            }

            if (colname != null) {
               return false;
            } else if (rbean.isRemoteField(cmrf)) {
               if (st.hasMoreTokens()) {
                  throw new IllegalExpressionException(7, "called endsInRemoteInterface on a pathExpression.  The Remote Interface is NOT the last field in the Path as defined in the EJB 2.0 public draft  spec  section 10.2.4.6: " + pathExpression);
               } else {
                  return true;
               }
            } else if (st.hasMoreTokens()) {
               throw new IllegalExpressionException(7, "called endsInRemoteInterface on an unparsed pathExpression: " + pathExpression);
            } else {
               return false;
            }
         }
      }

      return false;
   }

   public static void getJoinSQLForPath(JoinNode root, String pathExpression, List aliasExclusionList, StringBuffer sb) throws IllegalExpressionException {
      boolean hasSQL = false;
      String cmrf = "";
      if (pathExpression.length() != 0) {
         StringTokenizer st = new StringTokenizer(pathExpression, ".");
         cmrf = (String)st.nextElement();
         JoinNode node = root.getChild(cmrf);
         if (node == null) {
            throw new IllegalExpressionException(7, "Internal Error in JoinNode.getJoinSQLForPath, root node missing for path: '" + pathExpression + "'");
         } else {
            while(st.hasMoreTokens()) {
               cmrf = (String)st.nextElement();
               if (!node.hasChild(cmrf)) {
                  break;
               }

               node = node.getChild(cmrf);
               boolean exclude = false;
               if (aliasExclusionList != null) {
                  label55: {
                     Iterator xit = aliasExclusionList.iterator();

                     String tryAlias;
                     do {
                        if (!xit.hasNext()) {
                           break label55;
                        }

                        tryAlias = (String)xit.next();
                     } while(tryAlias.compareTo(node.getTableAlias()) != 0 && tryAlias.compareTo(node.getJoinTableAlias()) != 0);

                     exclude = true;
                  }
               }

               if (exclude) {
                  break;
               }

               String joinSQL = node.getJoinSQL();
               if (joinSQL.length() > 0) {
                  if (hasSQL) {
                     sb.append(" AND ");
                  }

                  sb.append(joinSQL);
                  hasSQL = true;
               }
            }

         }
      }
   }

   public static String getTableAndField(QueryNode queryTree, JoinNode root, String pathExpression) throws IllegalExpressionException {
      if (debugLogger.isDebugEnabled()) {
         debug(" called getTableAndField on pathExpression: '" + pathExpression + "'");
      }

      String cmrf;
      if (!endsInRemoteInterface(root, pathExpression)) {
         Loggable l = EJBLogger.logfinderPathEndsInXNotYLoggable("Bean", "Field");
         if (!endsInField(root, pathExpression)) {
            throw new IllegalExpressionException(7, "JoinNode.getTableAndField " + l.getMessageText());
         } else {
            if (debugLogger.isDebugEnabled()) {
               debug("  processing local cmp-field ");
            }

            cmrf = "";
            JoinNode node = root;

            for(StringTokenizer st = new StringTokenizer(pathExpression, "."); st.hasMoreTokens(); node = node.getChild(cmrf)) {
               cmrf = (String)st.nextElement();
               if (!node.hasChild(cmrf)) {
                  return writeTableAndFieldForLocalCmrf(queryTree, node, cmrf);
               }
            }

            return writeTableAndFieldForLocalCmrf(queryTree, node, cmrf);
         }
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("  processing RemoteInterface ");
         }

         JoinNode node = root;
         cmrf = "";

         for(StringTokenizer st = new StringTokenizer(pathExpression, "."); st.hasMoreTokens(); node = node.getChild(cmrf)) {
            cmrf = (String)st.nextElement();
            if (!node.hasChild(cmrf) || !st.hasMoreTokens()) {
               RDBMSBean rbean = node.getRDBMSBean();
               if (!rbean.isRemoteField(cmrf)) {
                  throw new IllegalExpressionException(7, "called getTableAndField on a pathExpression for a Remote Interface but the <cmr-field> " + cmrf + " seems not to be pointing to a Remote Field. " + pathExpression);
               } else if (rbean.getForeignKeyColNames(cmrf) == null) {
                  throw new IllegalExpressionException(7, "called getTableAndField on a pathExpression for a Remote Interface but the <cmr-field> " + cmrf + " from Bean " + rbean.getEjbName() + " does not point to Foreign Key Column Information. " + pathExpression);
               } else {
                  Iterator fkIter = rbean.getForeignKeyColNames(cmrf).iterator();
                  if (!fkIter.hasNext()) {
                     Loggable l = EJBLogger.logfinderCouldNotGetFKColumnsLoggable(rbean.getEjbName(), cmrf, "JoinNode.getTableAndField on Remote Interface: " + pathExpression);
                     throw new IllegalExpressionException(7, l.getMessageText());
                  } else if (rbean.containsFkField(cmrf)) {
                     String fk = (String)fkIter.next();
                     StringBuffer sb = new StringBuffer();
                     sb.append(node.getTableAlias());
                     sb.append(".");
                     sb.append(RDBMSUtils.escQuotedID(fk));
                     return sb.toString();
                  } else {
                     Map pkFieldToCol = rbean.getCmpFieldToColumnMap();
                     String fk = (String)fkIter.next();
                     String pk = (String)pkFieldToCol.get(rbean.getRelatedPkFieldName(cmrf, fk));
                     StringBuffer sb = new StringBuffer();
                     node = node.getChild(cmrf);
                     if (node == null) {
                        throw new IllegalExpressionException(7, "called getTableAndField on a pathExpression for a Remote Interface but the <cmr-field> " + cmrf + " from Bean " + rbean.getEjbName() + "  did not yield an expected JoinNode which is required to get the JoinTable info.  Path Expression: '" + pathExpression + "'");
                     } else {
                        sb.append(node.getJoinTableAlias());
                        sb.append(".");
                        sb.append(RDBMSUtils.escQuotedID(rbean.getRemoteColumn(cmrf)));
                        return sb.toString();
                     }
                  }
               }
            }
         }

         Loggable l = EJBLogger.logfinderCouldNotGetTableAndFieldLoggable(pathExpression);
         throw new IllegalExpressionException(7, "JoinNode processing: " + l.getMessageText());
      }
   }

   private static String writeTableAndFieldForLocalCmrf(QueryNode queryTree, JoinNode node, String cmrf) throws IllegalExpressionException {
      RDBMSBean rbean = node.getRDBMSBean();
      if (debugLogger.isDebugEnabled()) {
         debug(" writeTableAndFieldForLocalCmrf got RDBMSBean for '" + rbean.getEjbName() + "'");
      }

      String defaultTableName = node.getTableName();
      String cmpfTableAlias = "";
      String cmpfColName = null;
      String cmpfTableName;
      if (rbean.isPrimaryKeyField(cmrf)) {
         if (debugLogger.isDebugEnabled()) {
            debug(" processing PK field: '" + cmrf + "'");
         }

         cmpfTableAlias = node.getTableAlias();
         if (debugLogger.isDebugEnabled()) {
            debug(" defaultTableName is: '" + defaultTableName + "' tableAlias is: '" + cmpfTableAlias + "'");
         }

         cmpfColName = rbean.getColumnForCmpFieldAndTable(cmrf, defaultTableName);
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug(" processing non-PK field: '" + cmrf + "'");
         }

         cmpfTableName = rbean.getTableForCmpField(cmrf);
         if (debugLogger.isDebugEnabled()) {
            debug(" Table Name that contains non-PK field: '" + cmrf + "' is '" + cmpfTableName + "'");
         }

         if (cmpfTableName != null) {
            cmpfColName = rbean.getColumnForCmpFieldAndTable(cmrf, cmpfTableName);
            if (cmpfColName != null) {
               if (!cmpfTableName.equals(defaultTableName)) {
                  cmpfTableAlias = node.getOtherTableNameAlias(cmpfTableName);
                  node.forceInternalMultiTableJoin(queryTree, cmpfTableName);
               } else {
                  cmpfTableAlias = node.getTableAlias();
               }
            }
         }
      }

      if (cmpfColName == null) {
         if (debugLogger.isDebugEnabled()) {
            debug(" processing synthesized cmrfield variableName field: '" + cmrf + "'");
         }

         cmpfColName = rbean.getCmpColumnForVariable(cmrf);
         if (cmpfColName == null) {
            throw new IllegalExpressionException(7, "Error.  For Bean: '" + rbean.getEjbName() + "' could not find a Foreign Key Column Name for the field: '" + cmrf + "' check your RDBMS Deployment Descriptors for Errors or Omissions.");
         }

         cmpfTableName = rbean.getTableForVariable(cmrf);
         if (debugLogger.isDebugEnabled()) {
            debug(" column name for cmrfield variableName field: '" + cmrf + "', is: '" + cmpfColName + "'");
            debug(" table  name for cmrfield variableName field: '" + cmrf + "', is: '" + cmpfTableName + "'");
         }

         if (cmpfTableName == null) {
            throw new IllegalExpressionException(7, "Internal Error.  For Bean: '" + rbean.getEjbName() + "' could not get Foreign Key Table Name for variable name: '" + cmpfColName + "'.");
         }

         if (!cmpfTableName.equals(defaultTableName)) {
            cmpfTableAlias = node.getOtherTableNameAlias(cmpfTableName);
            node.forceInternalMultiTableJoin(queryTree, cmpfTableName);
         } else {
            cmpfTableAlias = node.getTableAlias();
         }
      }

      return cmpfTableAlias + "." + RDBMSUtils.escQuotedID(cmpfColName);
   }

   private static boolean lhsBeanHasFKForLocal11or1NPath(QueryNode queryTree, String fullPath) throws IllegalExpressionException {
      if (debugLogger.isDebugEnabled()) {
         debug(" lhsBeanHasFKForLocal11or1NPath  for path: '" + fullPath + "'");
      }

      String rootPath = chopLastFieldFromId(fullPath);
      String target_cmrf = getLastFieldFromId(fullPath);
      JoinNode node = queryTree.getJoinTree();

      String local_cmrf;
      for(StringTokenizer st = new StringTokenizer(rootPath, "."); st.hasMoreTokens(); node = node.getChild(local_cmrf)) {
         local_cmrf = (String)st.nextElement();
         if (!node.hasChild(local_cmrf)) {
            throw new IllegalExpressionException(7, "Internal Error.  lhsBeanHasFKFor11or1NPath:  Attempt to get CMR Table and Foreign Key Columns for path: '" + rootPath + ",  cmr-field: '" + target_cmrf + "'.  Could not traverse the JoinTree for the path: '" + rootPath + "'.  It's possible that this path has not been previously parsed.");
         }
      }

      RDBMSBean lhsBean = node.getRDBMSBean();
      if (lhsBean.isRemoteField(target_cmrf)) {
         throw new IllegalExpressionException(7, "Internal Error.  lhsBeanHasFKFor11or1NPath:  Attempt to get CMR Table and Foreign Key Columns for path: '" + rootPath + ",  cmr-field: '" + target_cmrf + "'.  This looks like a Remote Relationship.  This method should only be called on 1-1 or 1-N Local Relationships.");
      } else if (lhsBean.isManyToManyRelation(target_cmrf)) {
         throw new IllegalExpressionException(7, "Internal Error.  lhsBeanHasFKFor11or1NPath:  Attempt to get CMR Table and Foreign Key Columns for path: '" + rootPath + ",  cmr-field: '" + target_cmrf + "'.  This looks like a Many To Many Relationship.  This method should only be called on 1-1 or 1-N Local Relationships.");
      } else {
         return lhsBean.isForeignKeyField(target_cmrf);
      }
   }

   public static List getTableAndFKColumnListForLocal11or1NPath(QueryNode queryTree, String fullPath) throws IllegalExpressionException {
      if (debugLogger.isDebugEnabled()) {
         debug("  getTableAndFKColumnListForLocal11or1NPath for path: '" + fullPath + "'");
      }

      return lhsBeanHasFKForLocal11or1NPath(queryTree, fullPath) ? getTableAndFKColumnListForLocal11or1NPathFKonLHS(queryTree, fullPath) : getTableAndFKColumnListForLocal11or1NPathFKonRHS(queryTree, fullPath);
   }

   private static List getTableAndFKColumnListForLocal11or1NPathFKonLHS(QueryNode queryTree, String fullPath) throws IllegalExpressionException {
      String methodName = "JoinNode.getTableAndFKColumnListForLocal11or1NForPathFKonLHS";
      if (debugLogger.isDebugEnabled()) {
         debug(methodName + "  for path: '" + fullPath + "'");
      }

      String rootPath = chopLastFieldFromId(fullPath);
      String target_cmrf = getLastFieldFromId(fullPath);
      JoinNode node = queryTree.getJoinTree();

      String local_cmrf;
      for(StringTokenizer st = new StringTokenizer(rootPath, "."); st.hasMoreTokens(); node = node.getChild(local_cmrf)) {
         local_cmrf = (String)st.nextElement();
         if (!node.hasChild(local_cmrf)) {
            throw new IllegalExpressionException(7, "Internal Error.  " + methodName + ": Attempt to get CMR Table and Foreign Key Columns for path: '" + rootPath + ",  cmr-field: '" + target_cmrf + "'.  Could not traverse the JoinTree for the path: '" + rootPath + "'.  It's possible that this path has not been previously parsed.");
         }
      }

      RDBMSBean lhsBean = node.getRDBMSBean();
      String fkTableName = lhsBean.getTableForCmrField(target_cmrf);
      if (fkTableName == null) {
         throw new IllegalExpressionException(7, "Internal Error.  " + methodName + ": Could not get Table Name that contains the Foreign Keys for Bean: '" + lhsBean.getEjbName() + "'  and cmr-field: '" + target_cmrf + "'");
      } else {
         String defaultTableName = node.getTableName();
         String fkTableAlias;
         if (!fkTableName.equals(defaultTableName)) {
            fkTableAlias = node.getOtherTableNameAlias(fkTableName);
            node.forceInternalMultiTableJoin(queryTree, fkTableName);
         } else {
            fkTableAlias = node.getTableAlias();
         }

         List l = new ArrayList();

         String col;
         for(Iterator it = lhsBean.getForeignKeyColNames(target_cmrf).iterator(); it.hasNext(); l.add(fkTableAlias + "." + col)) {
            col = (String)it.next();
            if (debugLogger.isDebugEnabled()) {
               debug(methodName + " adding entry: '" + fkTableAlias + "." + col);
            }
         }

         return l;
      }
   }

   private static List getTableAndFKColumnListForLocal11or1NPathFKonRHS(QueryNode queryTree, String fullPath) throws IllegalExpressionException {
      String methodName = "JoinNode.getTableAndFKColumnListForLocal11or1NForPathFKonRHS";
      if (debugLogger.isDebugEnabled()) {
         debug(methodName + " for path: '" + fullPath + "'");
      }

      if (fullPath.indexOf(".") == -1) {
         throw new IllegalExpressionException(7, "Internal Error.  " + methodName + ":  the input pathExpression is required to represent at least 1 Relationship (between 2 Beans), the pathExpression: '" + fullPath + "' apparently does not.");
      } else {
         JoinNode root = queryTree.getJoinTree();
         root.parseJoin(fullPath);
         String target_cmrf = getLastFieldFromId(fullPath);
         String lhsPath = chopLastFieldFromId(fullPath);
         if (lhsPath.length() < 1) {
            throw new IllegalExpressionException(7, "Internal Error.  " + methodName + ":  the input pathExpression is required to represent at least 1 Relationship (between 2 Beans), the pathExpression: '" + fullPath + "' apparently does not.");
         } else {
            JoinNode lhsNode = getNode(root, lhsPath);
            RDBMSBean lhsBean = lhsNode.getRDBMSBean();
            JoinNode rhsNode = getNode(root, fullPath);
            RDBMSBean rhsBean = rhsNode.getRDBMSBean();
            String toPKCMRField = lhsBean.getRelatedFieldName(target_cmrf);
            if (toPKCMRField == null) {
               throw new IllegalExpressionException(7, "Internal Error.  " + methodName + ":  for path: '" + fullPath + "', for the cmr-field: '" + target_cmrf + "', we could not get the RelatedFieldName from the Bean represented by the cmr-field: '" + target_cmrf + "', that points back to the Previous Bean. ");
            } else {
               String fkTableName = rhsBean.getTableForCmrField(toPKCMRField);
               if (fkTableName == null) {
                  throw new IllegalExpressionException(7, "Internal Error.  " + methodName + ":  for path: '" + fullPath + "', for the cmr-field: '" + target_cmrf + "', we could not get the name of the Table on Bean: '" + rhsBean.getEjbName() + "' that holds the Foreign Key Columns for the Relationship.  RelatedFieldName used for lookup was: '" + toPKCMRField + "'");
               } else {
                  List fkColList = rhsBean.getForeignKeyColNames(toPKCMRField);
                  if (fkColList == null) {
                     throw new IllegalExpressionException(7, "Internal Error.  " + methodName + ":  for path: '" + fullPath + "', for the cmr-field: '" + target_cmrf + "', for Bean: '" + rhsBean.getEjbName() + "' that holds the Foreign Key Columns for the Relationship.  We could not get the Foreign Key Column Map for: RelatedFieldName '" + toPKCMRField + "'");
                  } else {
                     String fkTableAlias = rhsNode.getAnyTableNameAlias(fkTableName);
                     List l = new ArrayList();

                     String col;
                     for(Iterator it = fkColList.iterator(); it.hasNext(); l.add(fkTableAlias + "." + col)) {
                        col = (String)it.next();
                        if (debugLogger.isDebugEnabled()) {
                           debug(methodName + " adding entry: '" + fkTableAlias + "." + col);
                        }
                     }

                     return l;
                  }
               }
            }
         }
      }
   }

   public static int comparePaths(String path1, String path2) {
      if (path1 != null && path2 != null) {
         StringTokenizer st1 = new StringTokenizer(path1, ".");
         StringTokenizer st2 = new StringTokenizer(path2, ".");
         int size1 = st1.countTokens();
         int size2 = st2.countTokens();
         StringTokenizer shortTokens;
         StringTokenizer longTokens;
         if (st1.countTokens() < st2.countTokens()) {
            shortTokens = st1;
            longTokens = st2;
         } else {
            shortTokens = st2;
            longTokens = st1;
         }

         String sToken;
         String lToken;
         do {
            if (!shortTokens.hasMoreTokens()) {
               if (longTokens.hasMoreTokens()) {
                  return 1;
               }

               return 0;
            }

            sToken = shortTokens.nextToken();
            lToken = longTokens.nextToken();
         } while(sToken.equals(lToken));

         return -1;
      } else {
         return -1;
      }
   }

   public static int countPathNodes(String path) {
      if (path == null) {
         return 0;
      } else if (path.length() == 0) {
         return 0;
      } else if (path.indexOf(".") == -1) {
         return 1;
      } else {
         int count = 1;
         int fromIndex = 0;

         while(fromIndex != -1) {
            fromIndex = path.indexOf(".", fromIndex);
            if (fromIndex != -1) {
               ++count;
               ++fromIndex;
            }
         }

         return count;
      }
   }

   public static JoinNode getFirstNode(JoinNode root, String pathExpression) {
      String asID = getFirstFieldFromId(pathExpression);
      return root.getChild(asID);
   }

   public static JoinNode getNode(JoinNode root, String pathExpression) throws IllegalExpressionException {
      if (endsInField(root, pathExpression) || endsInRemoteInterface(root, pathExpression)) {
         int pos = pathExpression.lastIndexOf(".");
         if (pos == -1) {
            return root;
         }

         pathExpression = pathExpression.substring(0, pos);
      }

      String cmrf = "";
      JoinNode node = root;

      for(StringTokenizer st = new StringTokenizer(pathExpression, "."); st.hasMoreTokens(); node = node.getChild(cmrf)) {
         cmrf = (String)st.nextElement();
         if (!node.hasChild(cmrf)) {
            RDBMSBean rbean = node.getRDBMSBean();
            String colname = rbean.getCmpColumnForField(cmrf);
            if (colname == null) {
               colname = rbean.getCmpColumnForVariable(cmrf);
            }

            if (colname != null) {
               throw new IllegalExpressionException(7, "called getNode on an pathExpression that ends with a Field: " + pathExpression);
            }

            throw new IllegalExpressionException(7, "called getNode on an unparsed pathExpression: " + pathExpression);
         }
      }

      return node;
   }

   public static List createJoinListEntry(String lhsTableColumn, String rhsTableColumn) {
      List l = new ArrayList();
      l.add(lhsTableColumn);
      l.add(rhsTableColumn);
      return l;
   }

   public static void markDoLeftOuterJoins(JoinNode joinTree, String startPath, String endPath) throws IllegalExpressionException {
      if (countPathNodes(startPath) > countPathNodes(endPath)) {
         throw new IllegalExpressionException(7, "Internal Error !  in doLeftOuterJoins.  The number of path elements in Start Path: '" + startPath + "', is greater than the number of path elements in the End Path '" + endPath + "'.");
      } else {
         JoinNode firstStartNode = getFirstNode(joinTree, startPath);
         if (firstStartNode == null) {
            throw new IllegalExpressionException(7, "Internal Error !  in doLeftOuterJoins.  Start Path: '" + startPath + "', does not start with the Alias of an Abstract Schema Name defined in the Query.");
         } else {
            String firstStartNodeName = getFirstFieldFromId(startPath);
            JoinNode firstEndNode = getFirstNode(joinTree, endPath);
            if (firstEndNode == null) {
               throw new IllegalExpressionException(7, "Internal Error !  in doLeftOuterJoins.  End Path: '" + endPath + "', does not start with the Alias of an Abstract Schema Name defined in the Query.");
            } else {
               String firstEndNodeName = getFirstFieldFromId(endPath);
               if (!firstEndNodeName.equals(firstStartNodeName)) {
                  throw new IllegalExpressionException(7, "Internal Error !  in doLeftOuterJoins.  The Start Path: '" + startPath + "', begins with: '" + firstStartNodeName + "'.  The End Path: '" + endPath + "', begins with: '" + firstEndNodeName + "'.   The Start Path and End Path must begin with the same Root PathElement, but they do not.");
               } else {
                  int currCount = 0;
                  int startCount = countPathNodes(startPath);
                  StringTokenizer st = new StringTokenizer(endPath, ".");
                  JoinNode currNode = joinTree;

                  while(st.hasMoreTokens()) {
                     String currToken = st.nextToken();
                     ++currCount;
                     currNode = currNode.getChild(currToken);
                     if (currNode == null) {
                        throw new IllegalExpressionException(7, "Internal Error !  in doLeftOuterJoins.   Start Path: '" + startPath + ", End Path: '" + endPath + ", at element number: '" + currCount + "', we could not get a JoinNode for path element: '" + currToken + "'");
                     }

                     if (currCount > startCount) {
                        if (debugLogger.isDebugEnabled()) {
                           debug("in path: '" + endPath + "', element " + currCount + ": '" + currCount + "', setting WHERE Clause Left Outer Join to 'True'.");
                        }

                        currNode.setDoLeftOuterJoin(true);
                     }
                  }

                  checkLeftOuterJoinCountSupported(joinTree);
               }
            }
         }
      }
   }

   public static boolean checkLeftOuterJoinCountSupported(JoinNode joinTree) throws IllegalExpressionException {
      int count = joinTree.getAllDoLeftOuterJoinCount();
      if (count <= 0) {
         return true;
      } else {
         Map children = joinTree.getChildren();
         Iterator it = children.entrySet().iterator();
         if (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            JoinNode n = (JoinNode)entry.getValue();
            int dbType = n.getRDBMSBean().getDatabaseType();
            boolean supports = count > 1 ? RDBMSUtils.dbSupportForMultiLeftOuterJoin(dbType) : RDBMSUtils.dbSupportForSingleLeftOuterJoin(dbType);
            if (!supports) {
               Loggable l = EJBLogger.logCannotDoNOuterJoinForDBLoggable(count, DDConstants.getDBNameForType(dbType));
               throw new IllegalExpressionException(7, l.getMessageText());
            }
         }

         return true;
      }
   }

   public static List getTableAliasList(JoinNode root) {
      return root.getTableAlias(new ArrayList());
   }

   public static List getTableAliasListFromChildren(JoinNode root) {
      List l = new ArrayList();
      Map m = root.getChildren();
      Iterator it = m.values().iterator();

      while(it.hasNext()) {
         ((JoinNode)it.next()).getTableAlias(l);
      }

      return l;
   }

   public static RDBMSBean getTerminalBean(JoinNode root, String pathExpression) throws IllegalExpressionException {
      JoinNode node = getNode(root, pathExpression);
      return node.getRDBMSBean();
   }

   public static String getFirstFieldFromId(String id) {
      if (id == null) {
         return "";
      } else {
         String field = id;
         int pos = id.indexOf(".");
         if (pos != -1) {
            field = id.substring(0, pos);
         }

         return field;
      }
   }

   public static String getLastFieldFromId(String id) {
      if (id == null) {
         return "";
      } else {
         String field = id;
         int pos = id.lastIndexOf(".");
         if (pos != -1) {
            field = id.substring(pos + 1);
         }

         return field;
      }
   }

   public static String chopLastFieldFromId(String id) {
      if (id == null) {
         return "";
      } else {
         int pos = id.lastIndexOf(".");
         return pos != -1 ? id.substring(0, pos) : "";
      }
   }

   public static String getFirstNFieldsFromId(String id, int countToReturn) {
      if (id == null) {
         return "";
      } else {
         int count = 0;

         int pos;
         for(pos = -1; count < countToReturn; ++count) {
            pos = id.indexOf(".", pos + 1);
            if (pos == -1) {
               return id;
            }
         }

         if (pos > id.length()) {
            return id;
         } else {
            return id.substring(0, pos);
         }
      }
   }

   public static JoinNode makeJoinRoot(RDBMSBean rdbmsBean, QueryContext qc) {
      return new JoinNode((JoinNode)null, "", rdbmsBean, "", "", -1, false, false, "", qc, new ArrayList());
   }

   private JoinNode newJoinNode(JoinNode prevNode, String prevCMRField, RDBMSBean thisBean, String tableName, String tableAlias, int relationshipType, boolean isManyToMany, boolean isRemoteInterface, String tableAliasMN, List joinColumns) {
      if (debugLogger.isDebugEnabled()) {
         debug(" new JoinNode: using QueryContext ");
      }

      return new JoinNode(prevNode, prevCMRField, thisBean, tableName, tableAlias, relationshipType, isManyToMany, isRemoteInterface, tableAliasMN, this.queryContext, joinColumns);
   }

   private boolean isUseInnerJoin() {
      if (this.queryContext.getRDBMSBean() == null) {
         return false;
      } else {
         return !this.queryContext.getRDBMSBean().isUseInnerJoin() ? this.thisBean.isUseInnerJoin() : true;
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[JoinNode] " + s);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
