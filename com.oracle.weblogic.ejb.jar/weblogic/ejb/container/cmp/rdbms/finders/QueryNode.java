package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.logging.Loggable;
import weblogic.utils.Debug;

public class QueryNode {
   private static final DebugLogger debugLogger;
   private final EjbqlFinder finder;
   private final QueryContext queryContext;
   private int queryId;
   private final QueryNode parent;
   private final List children;
   private int queryType;
   private final Map rangeVariableMap;
   private final Set collectionMemberSet;
   private final JoinNode joinTree;
   private final List selectList;
   private final List selectListForCachingElement;
   private final List tableAliasExclusionList;
   private int ORcount;
   private final Stack orJoinDataStack;
   private final ORJoinData mainOrJoinData;
   private List orJoinDataListList;

   public QueryNode(EjbqlFinder f, QueryContext qc, QueryNode parent, JoinNode jt, int queryNum) {
      this.children = new ArrayList();
      this.rangeVariableMap = new HashMap();
      this.collectionMemberSet = new HashSet();
      this.selectList = new ArrayList();
      this.selectListForCachingElement = new ArrayList();
      this.tableAliasExclusionList = new ArrayList();
      this.ORcount = 0;
      this.orJoinDataStack = new Stack();
      this.mainOrJoinData = new ORJoinData();
      this.finder = f;
      this.queryContext = qc;
      this.parent = parent;
      this.joinTree = jt;
      this.queryId = queryNum;
      this.orJoinDataStack.push(this.mainOrJoinData);
      if (parent != null) {
         parent.addChild(this);
      }

   }

   public QueryNode(EjbqlFinder f, QueryContext qc, QueryNode parent, JoinNode jt) {
      this(f, qc, parent, jt, 0);
   }

   public boolean isMainQuery() {
      return this.parent == null;
   }

   public QueryNode getParent() {
      Debug.assertion(this.parent != null);
      return this.parent;
   }

   public void addChild(QueryNode qn) {
      this.children.add(qn);
   }

   public Iterator getChildrenIterator() {
      return this.children.iterator();
   }

   public void setQueryType(int i) {
      this.queryType = i;
   }

   public int getQueryType() {
      return this.queryType;
   }

   public void addRangeVariable(String id, String name) throws IllegalExpressionException {
      if (this.rangeVariableMap.containsKey(id)) {
         Loggable l = EJBLogger.logduplicateAsDefinitionLoggable(id);
         throw new IllegalExpressionException(7, l.getMessageText());
      } else {
         this.rangeVariableMap.put(id, name);
      }
   }

   public int rangeVariableMapSize() {
      return this.rangeVariableMap.size();
   }

   public String getRangeVariableMap(String id) {
      return (String)this.rangeVariableMap.get(id);
   }

   public List getRangeVariableMapIdList() {
      return new ArrayList(this.rangeVariableMap.keySet());
   }

   public List getIDsFromRangeVariableMapForSchema(String abstractSchema) {
      List l = new ArrayList();
      Iterator var3 = this.rangeVariableMap.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry e = (Map.Entry)var3.next();
         String id = (String)e.getKey();
         if (((String)e.getValue()).equals(abstractSchema)) {
            l.add(id);
         }
      }

      return l;
   }

   public void addCollectionMember(String id) throws IllegalExpressionException {
      if (this.collectionMemberSet.contains(id)) {
         Loggable l = EJBLogger.logduplicateCollectionMemberDefinitionLoggable(id);
         throw new IllegalExpressionException(7, l.getMessageText());
      } else {
         this.collectionMemberSet.add(id);
      }
   }

   public boolean containsCollectionMember(String id) {
      if (id == null) {
         return false;
      } else {
         return JoinNode.countPathNodes(id) != 1 ? false : this.collectionMemberSet.contains(id);
      }
   }

   public boolean isCollectionMemberInScope(String id) {
      QueryNode qn = null;

      try {
         qn = this.getQueryNodeForCollectionMember(id);
      } catch (IllegalExpressionException var4) {
         return false;
      }

      return qn != null;
   }

   public QueryNode getQueryNodeForCollectionMember(String collectionMember) throws IllegalExpressionException {
      if (this.containsCollectionMember(collectionMember)) {
         return this;
      } else if (this.parent == null) {
         throw new IllegalExpressionException(5, "Error, attempt to reference a Collection Member Identifier, '" + collectionMember + "' that is outside of the scope of it's query or subquery.");
      } else {
         return this.parent.getQueryNodeForCollectionMember(collectionMember);
      }
   }

   public boolean isRangeVariableInScope(String id) {
      if (id == null) {
         return false;
      } else if (JoinNode.countPathNodes(id) != 1) {
         return false;
      } else {
         QueryNode qn = null;

         try {
            qn = this.getQueryNodeForId(id);
         } catch (IllegalExpressionException var4) {
            return false;
         }

         return qn != null;
      }
   }

   public boolean thisQueryNodeOwnsId(String id) {
      String rangeVariableID = JoinNode.getFirstFieldFromId(id);
      return this.getRangeVariableMap(rangeVariableID) != null;
   }

   public QueryNode getQueryNodeForId(String id) throws IllegalExpressionException {
      if (debugLogger.isDebugEnabled()) {
         debug("\n About to search for joinTree that owns: " + id);
      }

      String dealiasedId = this.queryContext.replaceIdAliases(id);
      String rangeVariableId = JoinNode.getFirstFieldFromId(dealiasedId);
      QueryNode qn = null;

      try {
         qn = this.getQueryNodeForRangeVariableID(rangeVariableId);
         return qn;
      } catch (IllegalExpressionException var8) {
         if (var8.getErrorCode() == 5) {
            Loggable l = EJBLogger.logpathExpressionNotInContextOfQueryTreeLoggable(dealiasedId, rangeVariableId);
            IllegalExpressionException ex = new IllegalExpressionException(5, l.getMessageText());
            throw ex;
         } else {
            throw var8;
         }
      }
   }

   public QueryNode getQueryNodeForRangeVariableID(String rangeVariableID) throws IllegalExpressionException {
      if (this.getRangeVariableMap(rangeVariableID) != null) {
         return this;
      } else if (this.parent == null) {
         throw new IllegalExpressionException(5, "Error, attempt to reference a path expression, '" + rangeVariableID + "' that is outside of the scope of it's query or subquery.");
      } else {
         return this.parent.getQueryNodeForRangeVariableID(rangeVariableID);
      }
   }

   public JoinNode getJoinTreeForId(String id) throws IllegalExpressionException {
      QueryNode qn = this.getQueryNodeForId(id);
      return qn == null ? null : qn.getJoinTree();
   }

   public JoinNode getJoinNodeForFirstId(String id) throws IllegalExpressionException {
      return JoinNode.getFirstNode(this.getJoinTreeForId(id), id);
   }

   public JoinNode getJoinNodeForLastId(String id) throws IllegalExpressionException {
      this.prepareIdentifierForSQLGen(id);
      return JoinNode.getNode(this.getJoinTreeForId(id), id);
   }

   public JoinNode getJoinTree() {
      return this.joinTree;
   }

   public void prepareIdentifierForSQLGen(String id) throws IllegalExpressionException {
      String dealiasedId = this.queryContext.replaceIdAliases(id);
      String rangeVariableId = JoinNode.getFirstFieldFromId(id);
      QueryNode owningQueryNode = this.getQueryNodeForId(dealiasedId);
      if (owningQueryNode == null) {
         Loggable l = EJBLogger.logpathExpressionNotInContextOfQueryTreeLoggable(id, rangeVariableId);
         throw new IllegalExpressionException(5, l.getMessageText());
      } else {
         this.parseJoin(dealiasedId);
         owningQueryNode.replaceORPathMaybe(dealiasedId);
      }
   }

   private void parseJoin(String id) throws IllegalExpressionException {
      JoinNode joinTree = this.getJoinTreeForId(id);
      if (joinTree == null) {
         String rangeVariableID = JoinNode.getFirstFieldFromId(id);
         Loggable l = EJBLogger.logidNotDefinedInAsDeclarationLoggable(id, rangeVariableID);
         throw new IllegalExpressionException(5, l.getMessageText());
      } else {
         joinTree.parseJoin(id);
      }
   }

   public void addSelectList(SelectNode sn) {
      this.selectList.add(sn);
   }

   public List getSelectList() {
      return this.selectList;
   }

   public SelectNode selectListRootMatch(String pathExpression) {
      if (pathExpression == null) {
         return null;
      } else if (pathExpression.length() <= 0) {
         return null;
      } else {
         String pathExpressionExpanded = this.finder.replaceCorrVars(pathExpression);
         String pathExpressionFirst = JoinNode.getFirstFieldFromId(pathExpressionExpanded);
         List l = this.getSelectList();
         Iterator it = l.iterator();

         SelectNode sn;
         String snIdFirst;
         do {
            if (!it.hasNext()) {
               return null;
            }

            sn = (SelectNode)it.next();
            String snId = sn.getSelectTarget();
            String snIdExpanded = this.finder.replaceCorrVars(snId);
            snIdFirst = JoinNode.getFirstFieldFromId(snIdExpanded);
         } while(!snIdFirst.equals(pathExpressionFirst));

         return sn;
      }
   }

   public void addSelectListForCachingElement(SelectNode sn) {
      this.selectListForCachingElement.add(sn);
   }

   public List getSelectListForCachingElement() {
      return this.selectListForCachingElement;
   }

   public boolean containsInSelectListForCachingElement(SelectNode sn) {
      return this.selectListForCachingElement.contains(sn);
   }

   public boolean containsInSelectListForCachingElement(RDBMSBean bean1, RDBMSBean bean2) {
      Iterator it = this.selectListForCachingElement.iterator();

      RDBMSBean selectBean;
      do {
         if (!it.hasNext()) {
            return false;
         }

         SelectNode sn = (SelectNode)it.next();
         selectBean = sn.getSelectBean();
      } while(bean1 != selectBean && bean2 != selectBean);

      return true;
   }

   public void addTableAliasExclusionList(String alias) {
      this.tableAliasExclusionList.add(alias);
   }

   public List getTableAliasExclusionList() {
      return this.tableAliasExclusionList;
   }

   public Set getTableNameSetMinusExcluded() throws IllegalExpressionException {
      return this.getTableInfoSetMinusExcluded(0);
   }

   private Set getTableInfoSetMinusExcluded(int infoType) throws IllegalExpressionException {
      List tableList;
      try {
         tableList = JoinNode.getTableAliasList(this.getJoinTree());
      } catch (Exception var11) {
         throw new IllegalExpressionException(7, var11.getMessage());
      }

      List exclusionList = this.getTableAliasExclusionList();
      Map m = this.queryContext.getGlobalTableAliasMap();
      Set s = new HashSet();
      Iterator it = tableList.iterator();

      while(it.hasNext()) {
         String tableAlias = (String)it.next();
         boolean exclude = false;
         if (exclusionList != null) {
            Iterator xit = exclusionList.iterator();

            while(xit.hasNext()) {
               String tryAlias = (String)xit.next();
               if (tryAlias.compareTo(tableAlias) == 0) {
                  exclude = true;
                  break;
               }
            }
         }

         if (!exclude) {
            if (infoType == 1) {
               s.add(tableAlias);
            } else {
               String tableName = (String)m.get(tableAlias);
               s.add(tableName);
            }
         }
      }

      return s;
   }

   String getFROMDeclaration(int selectForUpdateVal) throws IllegalExpressionException {
      return this.joinTree.getFROMDeclaration(this.getTableAliasExclusionList(), selectForUpdateVal);
   }

   public void setQueryId(int id) {
      this.queryId = id;
   }

   public int getQueryId() {
      return this.queryId;
   }

   public QueryNode getQueryNodeForQueryId(int inputId) {
      if (this.queryId == inputId) {
         return this;
      } else {
         QueryNode target = null;
         Iterator it = this.children.iterator();

         do {
            if (!it.hasNext()) {
               return null;
            }

            QueryNode node = (QueryNode)it.next();
            target = node.getQueryNodeForQueryId(inputId);
         } while(target == null);

         return target;
      }
   }

   public void pushOR(Expr term) {
      this.pushOR(this.newORJoinData(term));
      ++this.ORcount;
   }

   public void pushOR(ORJoinData curr) {
      this.orJoinDataStack.push(curr);
   }

   public ORJoinData popOR() {
      return (ORJoinData)this.orJoinDataStack.pop();
   }

   public ORJoinData currentOR() {
      return (ORJoinData)this.orJoinDataStack.peek();
   }

   public void addORJoinDataListList(List l) {
      if (this.orJoinDataListList == null) {
         this.orJoinDataListList = new ArrayList();
      }

      if (!this.orJoinDataListList.contains(l)) {
         this.orJoinDataListList.add(l);
      }

   }

   public List getORJoinDataListList() {
      return this.orJoinDataListList;
   }

   public void replaceORPathMaybe(String id) throws IllegalExpressionException {
      if (debugLogger.isDebugEnabled()) {
         debug(" Entered replaceORPathMaybe for id: '" + id + "'");
      }

      if (!this.thisQueryNodeOwnsId(id)) {
         if (debugLogger.isDebugEnabled()) {
            debug(" queryNode does not own id.  exiting.");
         }

      } else {
         this.checkORPath(id);
         this.addSQLTableGenSymbolMap(id);
      }
   }

   private void checkORPath(String id) throws IllegalExpressionException {
      ORJoinData orJoinData = this.currentOR();
      Vector orVector = orJoinData.getOrVector();
      int size = orVector.size();
      if (debugLogger.isDebugEnabled()) {
         debug("Entered checkORPath. With input: '" + id + "',  current OR Vector has: " + size + " entries");
      }

      id = JoinNode.getPathWithoutTrailingCmpField(this.joinTree, id);

      for(int i = 0; i < size; ++i) {
         String path = (String)orVector.elementAt(i);
         if (debugLogger.isDebugEnabled()) {
            debug(" checking input '" + id + "' against OR path: '" + path + "'");
         }

         int code = JoinNode.comparePaths(id, path);
         switch (code) {
            case -1:
               if (debugLogger.isDebugEnabled()) {
                  debug(" JoinNode.PATHS_DISTINCT  try again.");
               }
               break;
            case 0:
               if (debugLogger.isDebugEnabled()) {
                  debug(" JoinNode.PATHS_EQUAL  we're done.");
               }

               return;
            case 1:
               if (debugLogger.isDebugEnabled()) {
                  debug(" JoinNode.PATHS_SUBSET  replace shorter path if needed.");
               }

               if (id.length() > path.length()) {
                  orVector.setElementAt(id, i);
                  if (debugLogger.isDebugEnabled()) {
                     debug(" JoinNode.PATHS_SUBSET  replaced shorter path in OR Vector with '" + id + "'");
                  }
               }

               return;
            default:
               throw new IllegalExpressionException(7, " UNKNOWN JoinNode.comparePaths return code: " + code);
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debug(" Adding path '" + id + "' to current OR Vector.");
      }

      orVector.add(id);
   }

   private void addSQLTableGenSymbolMap(String id) throws IllegalExpressionException {
      if (debugLogger.isDebugEnabled()) {
         debug(" addSQLTableGenSymbols on '" + id + "'");
      }

      ORJoinData orJoinData = this.currentOR();
      id = JoinNode.getPathWithoutTrailingCmpField(this.joinTree, id);
      int nodeCount = JoinNode.countPathNodes(id);

      for(int i = 0; i < nodeCount; ++i) {
         String pathExpression = JoinNode.getFirstNFieldsFromId(id, i + 1);
         if (orJoinData.getOrSQLTableGenSymbolMap().containsKey(id)) {
            if (debugLogger.isDebugEnabled()) {
               debug(" addSQLTableGenSymbolMap: skipping, already processed '" + id + "'");
            }
         } else {
            if (debugLogger.isDebugEnabled()) {
               debug(" addSQLTableGenSymbolMap: checking pathExpression '" + pathExpression + "'");
            }

            int relationshipType = this.getRelationshipTypeForPathExpressionWithNoSQLGen(pathExpression);
            switch (relationshipType) {
               case -1:
               case 0:
               default:
                  break;
               case 1:
               case 2:
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
                  JoinNode lastNode = JoinNode.getNode(this.joinTree, pathExpression);
                  String tableName = lastNode.getTableName();
                  if (debugLogger.isDebugEnabled()) {
                     debug(" addSQLTableGenSymbolMap:  adding key value pair: '" + pathExpression + "', tableName: '" + tableName + "'");
                  }

                  orJoinData.addOrSQLTableGenInfo(pathExpression, tableName);
            }
         }
      }

   }

   public void checkAllORCrossProducts() {
      Map table2PathSetMap = new HashMap();
      this.checkAllORCrossProducts(table2PathSetMap);
      if (table2PathSetMap.size() > 0) {
         StringBuffer sb = new StringBuffer();
         Iterator it = table2PathSetMap.entrySet().iterator();
         boolean needComma = false;

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String tableName = (String)entry.getKey();
            Iterator it2 = ((Set)entry.getValue()).iterator();
            if (it2.hasNext()) {
               if (needComma) {
                  sb.append(", ");
               } else {
                  needComma = true;
               }

               sb.append(tableName);
            }
         }

         String problemList = sb.toString();
         Loggable l = EJBLogger.logOrMayYieldEmptyCrossProductLoggable(problemList);
         this.queryContext.addWarning(new IllegalExpressionException(6, l.getMessageText()));
      }

   }

   public void checkAllORCrossProducts(Map table2PathSetMap) {
      this.checkORCrossProducts(table2PathSetMap);
      Iterator it = this.getChildrenIterator();

      while(it.hasNext()) {
         QueryNode qn = (QueryNode)it.next();
         qn.checkAllORCrossProducts(table2PathSetMap);
      }

   }

   public void checkORCrossProducts(Map table2PathSetMap) {
      if (debugLogger.isDebugEnabled()) {
         debug("checkORCrossProducts()");
      }

      List orListList = this.getORJoinDataListList();
      if (orListList == null) {
         if (debugLogger.isDebugEnabled()) {
            debug("checkORCrossProducts() NO OR CLAUSES exit.  \n\n");
         }

      } else {
         int listSize = orListList.size();
         if (debugLogger.isDebugEnabled()) {
            debug(" checkORCrossProducts()  ORListList size " + listSize);
         }

         if (listSize > 0) {
            Iterator it = orListList.iterator();

            while(true) {
               List orList;
               do {
                  if (!it.hasNext()) {
                     return;
                  }

                  orList = (List)it.next();
                  listSize = orList.size();
               } while(orList.size() == 0);

               if (debugLogger.isDebugEnabled()) {
                  debug("\n check next orJoinDataList");
               }

               for(int i = 0; i < listSize - 1; ++i) {
                  ORJoinData baseData = (ORJoinData)orList.get(i);

                  for(int j = i + 1; j < listSize; ++j) {
                     ORJoinData checkData = (ORJoinData)orList.get(j);
                     this.checkORCrossProductPair(baseData, checkData, table2PathSetMap);
                  }
               }
            }
         }
      }
   }

   private void checkORCrossProductPair(ORJoinData data1, ORJoinData data2, Map table2PathSetMap) {
      Expr term1 = data1.getOrTerm();
      Expr term2 = data2.getOrTerm();
      if (debugLogger.isDebugEnabled()) {
         debug("\n\n\nchecking OR pair: clause 1: '" + term1.printEJBQLTree() + "', \n                  clause 2: '" + term2.printEJBQLTree() + "'\n");
      }

      Set set1 = (Set)((HashSet)data1.getOrSQLTableGenTableSet()).clone();
      Set set2 = (Set)((HashSet)data2.getOrSQLTableGenTableSet()).clone();
      if (set1.size() != 0 && set2.size() != 0) {
         Set diffSet1 = new HashSet();
         Iterator it1 = set1.iterator();

         while(it1.hasNext()) {
            Object table = it1.next();
            if (debugLogger.isDebugEnabled()) {
               debug(" set1 table: '" + table + "'");
            }

            if (set2.contains(table)) {
               it1.remove();
               set2.remove(table);
               if (debugLogger.isDebugEnabled()) {
                  debug("      table: '" + table + "' is in Set2, removing");
               }
            } else {
               if (debugLogger.isDebugEnabled()) {
                  debug(" table '" + table + "' in set1 but not set2. ");
               }

               diffSet1.add(table);
            }
         }

         if (set1.size() == 0 && set2.size() == 0) {
            if (debugLogger.isDebugEnabled()) {
               debug(" both sets have identical table lists. ");
            }

         } else {
            Set diffSet2 = new HashSet();
            if (set2.size() > 0) {
               diffSet2 = new HashSet();
               Iterator it2 = set2.iterator();

               while(it2.hasNext()) {
                  Object table = it2.next();
                  if (debugLogger.isDebugEnabled()) {
                     debug(" set2 table: '" + table + "'");
                  }

                  if (set1.contains(table)) {
                     it2.remove();
                     set1.remove(table);
                     if (debugLogger.isDebugEnabled()) {
                        debug("      table: '" + table + "' is in Set1, removing");
                     }
                  } else {
                     if (debugLogger.isDebugEnabled()) {
                        debug(" table '" + table + "' in set2 but not set1. ");
                     }

                     diffSet2.add(table);
                  }
               }
            }

            StringBuffer sb = new StringBuffer();
            String extraTables1;
            if (diffSet1.size() > 0) {
               it1 = diffSet1.iterator();

               while(it1.hasNext()) {
                  extraTables1 = (String)it1.next();
                  sb.append(extraTables1);
                  if (it1.hasNext()) {
                     sb.append(" ");
                  }
               }
            }

            extraTables1 = sb.toString();
            String extraTables2 = "";
            sb.setLength(0);

            String queryTableSet;
            for(Iterator it2 = diffSet2.iterator(); it2.hasNext(); extraTables2 = sb.toString()) {
               queryTableSet = (String)it2.next();
               sb.append(queryTableSet);
               if (it2.hasNext()) {
                  sb.append(" ");
               }
            }

            if (debugLogger.isDebugEnabled()) {
               debug("  tables in OR clause 1 but not in OR clause 2 '" + extraTables1 + "'");
               debug("  tables in OR clause 2 but not in OR clause 1 '" + extraTables2 + "'");
            }

            queryTableSet = null;

            Set queryTableSet;
            try {
               queryTableSet = this.getTableNameSetMinusExcluded();
            } catch (Exception var17) {
               return;
            }

            sb.setLength(0);
            this.listORCrossProductPairDiff(diffSet1, data1.getOrSQLTableGenSymbolMap(), queryTableSet, table2PathSetMap, term1);
            this.listORCrossProductPairDiff(diffSet2, data2.getOrSQLTableGenSymbolMap(), queryTableSet, table2PathSetMap, term2);
         }
      }
   }

   private void listORCrossProductPairDiff(Set diffSet, Map orSQLTableGenSymbolMap, Set queryTableSet, Map table2PathSetMap, Expr term) {
      if (diffSet.size() > 0) {
         Iterator it2 = diffSet.iterator();

         while(true) {
            while(it2.hasNext()) {
               String tableName = (String)it2.next();
               if (queryTableSet.contains(tableName)) {
                  Iterator it = orSQLTableGenSymbolMap.entrySet().iterator();

                  while(it.hasNext()) {
                     Map.Entry entry = (Map.Entry)it.next();
                     String pathExpression = (String)entry.getKey();
                     String mappedTable = (String)entry.getValue();
                     if (tableName.equals(mappedTable)) {
                        Set s = (Set)table2PathSetMap.get(tableName);
                        if (s == null) {
                           s = new HashSet();
                           table2PathSetMap.put(tableName, s);
                        }

                        ((Set)s).add(pathExpression);
                     }
                  }
               } else if (debugLogger.isDebugEnabled()) {
                  debug(" table '" + tableName + "' not in query's FROM clause, ignoring.");
               }
            }

            return;
         }
      }
   }

   public String getMainJoinBuffer() throws IllegalExpressionException {
      return this.getMainORJoinBuffer();
   }

   public String getMainORJoinBuffer() throws IllegalExpressionException {
      return this.getORJoinBuffer(this.mainOrJoinData);
   }

   public String getCurrentORJoinBuffer() throws IllegalExpressionException {
      ORJoinData orJoinData = (ORJoinData)this.orJoinDataStack.peek();
      return this.getORJoinBuffer(orJoinData);
   }

   private String getORJoinBuffer(ORJoinData orJoinData) throws IllegalExpressionException {
      Vector orVector = orJoinData.getOrVector();
      StringBuffer sb = new StringBuffer();
      boolean hasJoin = false;
      if (orVector.size() > 0) {
         Iterator it = orVector.iterator();

         while(it.hasNext()) {
            String path = (String)it.next();
            StringBuffer sb2 = new StringBuffer();
            JoinNode.getJoinSQLForPath(this.joinTree, path, this.tableAliasExclusionList, sb2);
            String joinSQL = sb2.toString();
            if (joinSQL.length() > 0) {
               if (hasJoin) {
                  sb.append(" AND ");
               }

               sb.append(joinSQL);
               hasJoin = true;
            }
         }
      }

      Map m = orJoinData.getOrCmpFieldJoinMap();
      Iterator it = m.keySet().iterator();

      while(it.hasNext()) {
         String joinSQL = (String)m.get(it.next());
         if (joinSQL.length() > 0) {
            if (hasJoin) {
               sb.append(" AND ");
            }

            sb.append(joinSQL);
            hasJoin = true;
         }
      }

      return sb.toString();
   }

   public void addCmpFieldJoinSQL(String defaultTableAlias, String toTableAlias, String joinSQL) {
      String key = defaultTableAlias + "." + toTableAlias;
      ORJoinData orJoinData = this.currentOR();
      Map m = orJoinData.getOrCmpFieldJoinMap();
      m.put(key, joinSQL);
   }

   void checkOracleORJoin(String whereClause) throws IllegalExpressionException {
      int dbType = this.getDbType();
      if (dbType == 1) {
         if (this.ORcount <= 0) {
            return;
         }

         int otJoinCount = this.joinTree.getAllDoLeftOuterJoinCount();
         if (otJoinCount > 0) {
            Loggable l = EJBLogger.logOracleCannotDoOuterJoinAndORLoggable(whereClause);
            throw new IllegalExpressionException(7, l.getMessageText());
         }
      }

   }

   int getRelationshipTypeForPathExpressionWithNoSQLGen(String pathExpression) throws IllegalExpressionException {
      JoinNode root = this.getJoinTreeForId(pathExpression);
      return JoinNode.getRelationshipTypeForPathExpressionWithNoSQLGen(this.queryContext, root, pathExpression);
   }

   public RDBMSBean getLastRDBMSBeanForPathExpressionWithNoSQLGen(String pathExpression) throws IllegalExpressionException {
      JoinNode root = this.getJoinTreeForId(pathExpression);
      return JoinNode.getLastRDBMSBeanForPathExpressionWithNoSQLGen(this.queryContext, root, pathExpression);
   }

   public RDBMSBean getLastRDBMSBeanForPathExpression(String pathExpression) throws IllegalExpressionException {
      JoinNode root = this.getJoinTreeForId(pathExpression);
      return JoinNode.getTerminalBean(root, pathExpression);
   }

   private int getDbType() {
      return this.queryContext.getRDBMSBean().getDatabaseType();
   }

   private ORJoinData newORJoinData(Expr orTerm) {
      return new ORJoinData(orTerm);
   }

   public static QueryNode newQueryNode(EjbqlFinder f, QueryContext qc, QueryNode parent, int queryNum) {
      JoinNode jt = JoinNode.makeJoinRoot(f.getRDBMSBean(), qc);
      return new QueryNode(f, qc, parent, jt, queryNum);
   }

   private static void debug(String s) {
      debugLogger.debug("[QueryNode] " + s);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
