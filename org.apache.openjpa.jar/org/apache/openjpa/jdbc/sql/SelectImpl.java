package org.apache.openjpa.jdbc.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
import org.apache.commons.collections.iterators.EmptyIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCLockManager;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.kernel.JDBCStoreManager;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.Joinable;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.Id;
import org.apache.openjpa.util.InternalException;
import serp.util.Numbers;

public class SelectImpl implements Select, PathJoins {
   private static final int NONAUTO_DISTINCT = 2;
   private static final int DISTINCT = 4;
   private static final int NOT_DISTINCT = 8;
   private static final int IMPLICIT_DISTINCT = 16;
   private static final int TO_MANY = 32;
   private static final int AGGREGATE = 64;
   private static final int LOB = 128;
   private static final int OUTER = 256;
   private static final int LRS = 512;
   private static final int EAGER_TO_ONE = 1024;
   private static final int EAGER_TO_MANY = 2048;
   private static final int RECORD_ORDERED = 4096;
   private static final int GROUPING = 8192;
   private static final int FORCE_COUNT = 16384;
   private static final String[] TABLE_ALIASES = new String[16];
   private static final String[] ORDER_ALIASES = new String[16];
   private static final Object[] NULL_IDS = new Object[16];
   private static final Object[] PLACEHOLDERS = new Object[50];
   private static final Localizer _loc = Localizer.forPackage(Select.class);
   private final JDBCConfiguration _conf;
   private final DBDictionary _dict;
   private Map _aliases = null;
   private Map _tableAliases = null;
   private SortedMap _tables = null;
   protected final Selects _selects = this.newSelects();
   private List _ordered = null;
   private List _grouped = null;
   private int _flags = 0;
   private int _joinSyntax = 0;
   private long _startIdx = 0L;
   private long _endIdx = Long.MAX_VALUE;
   private int _nullIds = 0;
   private int _orders = 0;
   private int _placeholders = 0;
   private int _expectedResultCount = 0;
   private SQLBuffer _ordering = null;
   private SQLBuffer _where = null;
   private SQLBuffer _grouping = null;
   private SQLBuffer _having = null;
   private SelectJoins _joins = null;
   private Stack _preJoins = null;
   private Map _eager = null;
   private Set _eagerKeys = null;
   private List _subsels = null;
   private SelectImpl _parent = null;
   private String _subPath = null;
   private SelectImpl _from = null;
   protected SelectImpl _outer = null;
   private BitSet _removedAliasFromParent = new BitSet(16);

   static String toAlias(int index) {
      if (index == -1) {
         return null;
      } else {
         return index < TABLE_ALIASES.length ? TABLE_ALIASES[index] : "t" + index;
      }
   }

   public static String toOrderAlias(int index) {
      if (index == -1) {
         return null;
      } else {
         return index < ORDER_ALIASES.length ? ORDER_ALIASES[index] : "o" + index;
      }
   }

   public SelectImpl(JDBCConfiguration conf) {
      this._conf = conf;
      this._dict = this._conf.getDBDictionaryInstance();
      this._joinSyntax = this._dict.joinSyntax;
      this._selects._dict = this._dict;
   }

   public JDBCConfiguration getConfiguration() {
      return this._conf;
   }

   public SQLBuffer toSelect(boolean forUpdate, JDBCFetchConfiguration fetch) {
      return this._dict.toSelect(this, forUpdate, fetch);
   }

   public SQLBuffer toSelectCount() {
      return this._dict.toSelectCount(this);
   }

   public boolean getAutoDistinct() {
      return (this._flags & 2) == 0;
   }

   public void setAutoDistinct(boolean val) {
      if (val) {
         this._flags &= -3;
      } else {
         this._flags |= 2;
      }

   }

   public boolean isDistinct() {
      return (this._flags & 8) == 0 && ((this._flags & 4) != 0 || (this._flags & 2) == 0 && (this._flags & 16) != 0);
   }

   public void setDistinct(boolean distinct) {
      if (distinct) {
         this._flags |= 4;
         this._flags &= -9;
      } else {
         this._flags |= 8;
         this._flags &= -5;
      }

   }

   public boolean isLRS() {
      return (this._flags & 512) != 0;
   }

   public void setLRS(boolean lrs) {
      if (lrs) {
         this._flags |= 512;
      } else {
         this._flags &= -513;
      }

   }

   public int getExpectedResultCount() {
      return (this._flags & 16384) == 0 && this.hasEagerJoin(true) ? 0 : this._expectedResultCount;
   }

   public void setExpectedResultCount(int expectedResultCount, boolean force) {
      this._expectedResultCount = expectedResultCount;
      if (force) {
         this._flags |= 16384;
      } else {
         this._flags &= -16385;
      }

   }

   public int getJoinSyntax() {
      return this._joinSyntax;
   }

   public void setJoinSyntax(int joinSyntax) {
      this._joinSyntax = joinSyntax;
   }

   public boolean supportsRandomAccess(boolean forUpdate) {
      return this._dict.supportsRandomAccessResultSet(this, forUpdate);
   }

   public boolean supportsLocking() {
      return this._dict.supportsLocking(this);
   }

   public int getCount(JDBCStore store) throws SQLException {
      Connection conn = null;
      PreparedStatement stmnt = null;
      ResultSet rs = null;

      int var6;
      try {
         SQLBuffer sql = this.toSelectCount();
         conn = store.getConnection();
         stmnt = this.prepareStatement(conn, sql, (JDBCFetchConfiguration)null, 1003, 1007, false);
         rs = this.executeQuery(conn, stmnt, sql, false, store);
         var6 = this.getCount(rs);
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException var19) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var18) {
            }
         }

         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var17) {
            }
         }

      }

      return var6;
   }

   public Result execute(JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      if (fetch == null) {
         fetch = store.getFetchConfiguration();
      }

      return this.execute(store.getContext(), store, fetch, fetch.getReadLockLevel());
   }

   public Result execute(JDBCStore store, JDBCFetchConfiguration fetch, int lockLevel) throws SQLException {
      if (fetch == null) {
         fetch = store.getFetchConfiguration();
      }

      return this.execute(store.getContext(), store, fetch, lockLevel);
   }

   protected Result execute(StoreContext ctx, JDBCStore store, JDBCFetchConfiguration fetch, int lockLevel) throws SQLException {
      boolean forUpdate = false;
      if (!this.isAggregate() && this._grouping == null) {
         JDBCLockManager lm = store.getLockManager();
         if (lm != null) {
            forUpdate = lm.selectForUpdate(this, lockLevel);
         }
      }

      SQLBuffer sql = this.toSelect(forUpdate, fetch);
      boolean isLRS = this.isLRS();
      int rsType = isLRS && this.supportsRandomAccess(forUpdate) ? -1 : 1003;
      Connection conn = store.getConnection();
      PreparedStatement stmnt = null;
      ResultSet rs = null;

      try {
         if (isLRS) {
            stmnt = this.prepareStatement(conn, sql, fetch, rsType, -1, true);
         } else {
            stmnt = this.prepareStatement(conn, sql, (JDBCFetchConfiguration)null, rsType, -1, false);
         }

         this.setTimeout(stmnt, forUpdate, fetch);
         rs = this.executeQuery(conn, stmnt, sql, isLRS, store);
      } catch (SQLException var16) {
         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var15) {
            }
         }

         try {
            conn.close();
         } catch (SQLException var14) {
         }

         throw var16;
      }

      return this.getEagerResult(conn, stmnt, rs, store, fetch, forUpdate, sql.getSQL());
   }

   private static void addEagerResults(SelectResult res, SelectImpl sel, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      if (sel._eager != null) {
         Map.Entry entry;
         Object eres;
         Object eager;
         for(Iterator itr = sel._eager.entrySet().iterator(); itr.hasNext(); ((Map)eager).put(entry.getKey(), eres)) {
            entry = (Map.Entry)itr.next();
            if (entry.getValue() == sel) {
               eres = res;
            } else {
               eres = ((SelectExecutor)entry.getValue()).execute(store, fetch);
            }

            eager = res.getEagerMap(false);
            if (eager == null) {
               eager = new HashMap();
               res.setEagerMap((Map)eager);
            }
         }

      }
   }

   protected PreparedStatement prepareStatement(Connection conn, SQLBuffer sql, JDBCFetchConfiguration fetch, int rsType, int rsConcur, boolean isLRS) throws SQLException {
      return fetch == null ? sql.prepareStatement(conn, rsType, rsConcur) : sql.prepareStatement(conn, fetch, rsType, -1);
   }

   protected void setTimeout(PreparedStatement stmnt, boolean forUpdate, JDBCFetchConfiguration fetch) throws SQLException {
      if (forUpdate && this._dict.supportsQueryTimeout && fetch != null && fetch.getLockTimeout() > stmnt.getQueryTimeout() * 1000) {
         int timeout = fetch.getLockTimeout();
         if (timeout < 1000) {
            timeout = 1000;
            Log log = this._conf.getLog("openjpa.jdbc.JDBC");
            if (log.isWarnEnabled()) {
               log.warn(_loc.get("millis-query-timeout"));
            }
         }

         stmnt.setQueryTimeout(timeout / 1000);
      }

   }

   protected ResultSet executeQuery(Connection conn, PreparedStatement stmnt, SQLBuffer sql, boolean isLRS, JDBCStore store) throws SQLException {
      return stmnt.executeQuery();
   }

   protected int getCount(ResultSet rs) throws SQLException {
      rs.next();
      return rs.getInt(1);
   }

   protected Result getEagerResult(Connection conn, PreparedStatement stmnt, ResultSet rs, JDBCStore store, JDBCFetchConfiguration fetch, boolean forUpdate, String sqlStr) throws SQLException {
      SelectResult res = new SelectResult(conn, stmnt, rs, this._dict);
      res.setSelect(this);
      res.setStore(store);
      res.setLocking(forUpdate);

      try {
         addEagerResults(res, this, store, fetch);
         return res;
      } catch (SQLException var10) {
         res.close();
         throw var10;
      }
   }

   public int indexOf() {
      return 0;
   }

   public List getSubselects() {
      return this._subsels == null ? Collections.EMPTY_LIST : this._subsels;
   }

   public Select getParent() {
      return this._parent;
   }

   public String getSubselectPath() {
      return this._subPath;
   }

   public void setParent(Select parent, String path) {
      if (path != null) {
         this._subPath = path + ':';
      } else {
         this._subPath = null;
      }

      if (parent != this._parent) {
         if (this._parent != null) {
            this._parent._subsels.remove(this);
         }

         this._parent = (SelectImpl)parent;
         if (this._parent != null) {
            if (this._parent._subsels == null) {
               this._parent._subsels = new ArrayList(2);
            }

            this._parent._subsels.add(this);
            if (this._parent._joinSyntax == 0) {
               this._joinSyntax = 1;
            } else {
               this._joinSyntax = this._parent._joinSyntax;
            }
         }

         if (this._parent.getAliases() != null && this._subPath != null) {
            if (this._parent._joinSyntax != 2) {
               Set entries = this._parent.getAliases().entrySet();
               Iterator i$ = entries.iterator();

               while(true) {
                  Object key;
                  Integer alias;
                  Object tableString;
                  do {
                     Map.Entry entry;
                     if (!i$.hasNext()) {
                        if (this._aliases != null) {
                           entries = this._aliases.entrySet();
                           i$ = entries.iterator();

                           while(true) {
                              do {
                                 if (!i$.hasNext()) {
                                    return;
                                 }

                                 entry = (Map.Entry)i$.next();
                                 key = entry.getKey();
                                 alias = (Integer)entry.getValue();
                              } while(key.toString().indexOf(this._subPath) == -1 && this._parent.findTableAlias(alias));

                              this._parent.removeAlias(key);
                              tableString = this._parent.getTables().get(alias);
                              this._parent.removeTable(alias);
                           }
                        }

                        return;
                     }

                     entry = (Map.Entry)i$.next();
                     key = entry.getKey();
                     alias = (Integer)entry.getValue();
                  } while(key.toString().indexOf(this._subPath) == -1 && this._parent.findTableAlias(alias));

                  if (this._aliases == null) {
                     this._aliases = new HashMap();
                  }

                  this._aliases.put(key, alias);
                  tableString = this._parent.getTables().get(alias);
                  if (this._tables == null) {
                     this._tables = new TreeMap();
                  }

                  this._tables.put(alias, tableString);
                  this._removedAliasFromParent.set(alias);
               }
            }
         }
      }
   }

   private boolean findTableAlias(Integer alias) {
      String value = "t" + alias.toString() + ".";
      if (this._tableAliases != null) {
         if (this._tableAliases.containsValue(value)) {
            return this._tables.containsKey(alias);
         } else {
            return this._joins != null;
         }
      } else {
         return true;
      }
   }

   public Map getAliases() {
      return this._aliases;
   }

   public void removeAlias(Object key) {
      this._aliases.remove(key);
   }

   public Map getTables() {
      return this._tables;
   }

   public void removeTable(Object key) {
      this._tables.remove(key);
   }

   public Select getFromSelect() {
      return this._from;
   }

   public void setFromSelect(Select sel) {
      this._from = (SelectImpl)sel;
      if (this._from != null) {
         this._from._outer = this;
      }

   }

   public boolean hasEagerJoin(boolean toMany) {
      if (toMany) {
         return (this._flags & 2048) != 0;
      } else {
         return (this._flags & 1024) != 0;
      }
   }

   public boolean hasJoin(boolean toMany) {
      if (toMany) {
         return (this._flags & 32) != 0;
      } else {
         return this._tables != null && this._tables.size() > 1;
      }
   }

   public boolean isSelected(Table table) {
      PathJoins pj = this.getJoins((Joins)null, false);
      if (this._from != null) {
         return this._from.getTableIndex(table, pj, false) != -1;
      } else {
         return this.getTableIndex(table, pj, false) != -1;
      }
   }

   public Collection getTableAliases() {
      return (Collection)(this._tables == null ? Collections.EMPTY_SET : this._tables.values());
   }

   public List getSelects() {
      return Collections.unmodifiableList(this._selects);
   }

   public List getSelectAliases() {
      return this._selects.getAliases(false, this._outer != null);
   }

   public List getIdentifierAliases() {
      return this._selects.getAliases(true, this._outer != null);
   }

   public SQLBuffer getOrdering() {
      return this._ordering;
   }

   public SQLBuffer getGrouping() {
      return this._grouping;
   }

   public SQLBuffer getWhere() {
      return this._where;
   }

   public SQLBuffer getHaving() {
      return this._having;
   }

   public void addJoinClassConditions() {
      if (this._joins != null && this._joins.joins() != null) {
         Iterator itr = this._joins.joins().iterator();

         while(itr.hasNext()) {
            Join j = (Join)itr.next();
            if (j.getRelationTarget() != null) {
               j.getRelationTarget().getDiscriminator().addClassConditions(this, j.getSubclasses() == 1, j.getRelationJoins());
               j.setRelation((ClassMapping)null, 0, (Joins)null);
            }
         }

      }
   }

   public Joins getJoins() {
      return this._joins;
   }

   public Iterator getJoinIterator() {
      return this._joins != null && !this._joins.isEmpty() ? this._joins.joins().joinIterator() : EmptyIterator.INSTANCE;
   }

   public long getStartIndex() {
      return this._startIdx;
   }

   public long getEndIndex() {
      return this._endIdx;
   }

   public void setRange(long start, long end) {
      this._startIdx = start;
      this._endIdx = end;
   }

   public String getColumnAlias(Column col) {
      return this.getColumnAlias(col, (Joins)null);
   }

   public String getColumnAlias(Column col, Joins joins) {
      return this.getColumnAlias(col, this.getJoins(joins, false));
   }

   private String getColumnAlias(Column col, PathJoins pj) {
      return this.getColumnAlias(col.getName(), col.getTable(), pj);
   }

   public String getColumnAlias(String col, Table table) {
      return this.getColumnAlias(col, table, (Joins)null);
   }

   public String getColumnAlias(String col, Table table, Joins joins) {
      return this.getColumnAlias(col, table, this.getJoins(joins, false));
   }

   private String getColumnAlias(String col, Table table, PathJoins pj) {
      String tableAlias = null;
      if (pj != null && pj.path() != null) {
         return this.getTableAlias(table, pj).append(col).toString();
      } else {
         if (this._tableAliases == null) {
            this._tableAliases = new HashMap();
         }

         tableAlias = (String)this._tableAliases.get(table);
         if (tableAlias == null) {
            tableAlias = this.getTableAlias(table, pj).toString();
            this._tableAliases.put(table, tableAlias);
         }

         return tableAlias + col;
      }
   }

   private StringBuilder getTableAlias(Table table, PathJoins pj) {
      StringBuilder buf = new StringBuilder();
      if (this._from != null) {
         String alias = toAlias(this._from.getTableIndex(table, pj, true));
         return this._dict.requiresAliasForSubselect ? buf.append("s").append(".").append(alias).append("_") : buf.append(alias).append("_");
      } else {
         return buf.append(toAlias(this.getTableIndex(table, pj, true))).append(".");
      }
   }

   public boolean isAggregate() {
      return (this._flags & 64) != 0;
   }

   public void setAggregate(boolean agg) {
      if (agg) {
         this._flags |= 64;
      } else {
         this._flags &= -65;
      }

   }

   public boolean isLob() {
      return (this._flags & 128) != 0;
   }

   public void setLob(boolean lob) {
      if (lob) {
         this._flags |= 128;
      } else {
         this._flags &= -129;
      }

   }

   public void clearSelects() {
      this._selects.clear();
   }

   public boolean select(SQLBuffer sql, Object id) {
      return this.select((SQLBuffer)sql, id, (Joins)null);
   }

   public boolean select(SQLBuffer sql, Object id, Joins joins) {
      if (!this.isGrouping()) {
         return this.select((Object)sql, id, joins);
      } else {
         this.groupBy(sql, joins);
         return false;
      }
   }

   private boolean select(Object sql, Object id, Joins joins) {
      this.getJoins(joins, true);
      boolean contains;
      if (id == null) {
         int idx = this._selects.indexOfAlias(sql);
         contains = idx != -1;
         if (contains) {
            id = this._selects.get(idx);
         } else {
            id = this.nullId();
         }
      } else {
         contains = this._selects.contains(id);
      }

      if (contains) {
         return false;
      } else {
         this._selects.setAlias(id, sql, false);
         return true;
      }
   }

   private Object nullId() {
      return this._nullIds >= NULL_IDS.length ? new NullId() : NULL_IDS[this._nullIds++];
   }

   public boolean select(String sql, Object id) {
      return this.select((String)sql, id, (Joins)null);
   }

   public boolean select(String sql, Object id, Joins joins) {
      if (!this.isGrouping()) {
         return this.select((Object)sql, id, joins);
      } else {
         this.groupBy(sql, joins);
         return true;
      }
   }

   public void selectPlaceholder(String sql) {
      Object holder = this._placeholders >= PLACEHOLDERS.length ? new Placeholder() : PLACEHOLDERS[this._placeholders++];
      this.select(sql, holder);
   }

   public void insertPlaceholder(String sql, int pos) {
      Object holder = this._placeholders >= PLACEHOLDERS.length ? new Placeholder() : PLACEHOLDERS[this._placeholders++];
      this._selects.insertAlias(pos, holder, sql);
   }

   public void clearPlaceholderSelects() {
      this._selects.clearPlaceholders();
   }

   public boolean select(Column col) {
      return this.select(col, (Joins)null);
   }

   public boolean select(Column col, Joins joins) {
      if (!this.isGrouping()) {
         return this.select(col, this.getJoins(joins, true), false);
      } else {
         this.groupBy(col, joins);
         return false;
      }
   }

   public int select(Column[] cols) {
      return this.select((Column[])cols, (Joins)null);
   }

   public int select(Column[] cols, Joins joins) {
      if (cols != null && cols.length != 0) {
         if (this.isGrouping()) {
            this.groupBy(cols, joins);
            return 0;
         } else {
            PathJoins pj = this.getJoins(joins, true);
            int seld = 0;

            for(int i = 0; i < cols.length; ++i) {
               if (this.select(cols[i], pj, false)) {
                  seld |= 2 << i;
               }
            }

            return seld;
         }
      } else {
         return 0;
      }
   }

   private boolean select(Column col, PathJoins pj, boolean ident) {
      String alias = this.getColumnAlias(col, pj);
      Object id;
      if (pj != null && pj.path() != null) {
         id = alias;
      } else {
         id = col;
      }

      if (this._selects.contains(id)) {
         return false;
      } else {
         if (col.getType() == 2004 || col.getType() == 2005) {
            this.setLob(true);
         }

         this._selects.setAlias(id, alias, ident);
         return true;
      }
   }

   public void select(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, int eager) {
      this.select(mapping, subclasses, store, fetch, eager, (Joins)null);
   }

   public void select(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, int eager, Joins joins) {
      this.select(this, mapping, subclasses, store, fetch, eager, joins, false);
   }

   void select(Select wrapper, ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, int eager, Joins joins, boolean ident) {
      this.getJoins(joins, true);
      PathJoins pj = (PathJoins)joins;
      boolean hasJoins = pj != null && pj.isDirty();
      if (hasJoins) {
         if (this._preJoins == null) {
            this._preJoins = new Stack();
         }

         this._preJoins.push(pj);
      }

      boolean wasOuter = (this._flags & 256) != 0;
      if (hasJoins && !wasOuter && pj.isOuter()) {
         this._flags |= 256;
      }

      ((JDBCStoreManager)store).select(wrapper, mapping, subclasses, (OpenJPAStateManager)null, (BitSet)null, fetch, eager, ident, (this._flags & 256) != 0);
      if (hasJoins) {
         this._preJoins.pop();
      }

      if (!wasOuter && (this._flags & 256) != 0) {
         this._flags &= -257;
      }

   }

   public boolean selectIdentifier(Column col) {
      return this.selectIdentifier(col, (Joins)null);
   }

   public boolean selectIdentifier(Column col, Joins joins) {
      if (!this.isGrouping()) {
         return this.select(col, this.getJoins(joins, true), true);
      } else {
         this.groupBy(col, joins);
         return false;
      }
   }

   public int selectIdentifier(Column[] cols) {
      return this.selectIdentifier((Column[])cols, (Joins)null);
   }

   public int selectIdentifier(Column[] cols, Joins joins) {
      if (cols != null && cols.length != 0) {
         if (this.isGrouping()) {
            this.groupBy(cols, joins);
            return 0;
         } else {
            PathJoins pj = this.getJoins(joins, true);
            int seld = 0;

            for(int i = 0; i < cols.length; ++i) {
               if (this.select(cols[i], pj, true)) {
                  seld |= 2 << i;
               }
            }

            return seld;
         }
      } else {
         return 0;
      }
   }

   public void selectIdentifier(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, int eager) {
      this.selectIdentifier(mapping, subclasses, store, fetch, eager, (Joins)null);
   }

   public void selectIdentifier(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, int eager, Joins joins) {
      this.select(this, mapping, subclasses, store, fetch, eager, joins, true);
   }

   public int selectPrimaryKey(ClassMapping mapping) {
      return this.selectPrimaryKey(mapping, (Joins)null);
   }

   public int selectPrimaryKey(ClassMapping mapping, Joins joins) {
      return this.primaryKeyOperation(mapping, true, (Boolean)null, joins, false);
   }

   private int primaryKeyOperation(ClassMapping mapping, boolean sel, Boolean asc, Joins joins, boolean aliasOrder) {
      if (!sel && asc == null) {
         return 0;
      } else {
         ClassMapping sup;
         if (!mapping.isPrimaryKeyObjectId(true)) {
            sup = mapping.getJoinablePCSuperclassMapping();
            if (joins == null) {
               joins = this.newJoins();
            }

            joins = mapping.joinSuperclass(joins, false);
            return this.primaryKeyOperation(sup, sel, asc, joins, aliasOrder);
         } else {
            Column[] cols = mapping.getPrimaryKeyColumns();
            if (this.isGrouping()) {
               this.groupBy(cols, joins);
               return 0;
            } else {
               PathJoins pj = this.getJoins(joins, false);
               int seld = 0;

               for(int i = 0; i < cols.length; ++i) {
                  if (this.columnOperation(cols[i], sel, asc, pj, aliasOrder)) {
                     seld |= 2 << i;
                  }
               }

               boolean joined = false;

               for(sup = mapping.getJoinablePCSuperclassMapping(); sup != null; sup = sup.getJoinablePCSuperclassMapping()) {
                  if (sup.getTable() != mapping.getTable()) {
                     if (mapping.getTable() == sup.getTable() || this.getTableIndex(mapping.getTable(), pj, false) != -1 || this.getTableIndex(sup.getTable(), pj, false) == -1) {
                        break;
                     }

                     if (pj == null) {
                        pj = (PathJoins)this.newJoins();
                     }

                     pj = (PathJoins)mapping.joinSuperclass(pj, false);
                     joined = true;
                  }

                  mapping = sup;
               }

               if (joined) {
                  this.where((Joins)pj);
               }

               return seld;
            }
         }
      }
   }

   private boolean columnOperation(Column col, boolean sel, Boolean asc, PathJoins pj, boolean aliasOrder) {
      String as = null;
      if (asc != null && (aliasOrder || (this._flags & 4096) != 0)) {
         Object id;
         if (pj != null && pj.path() != null) {
            id = this.getColumnAlias(col, pj);
         } else {
            id = col;
         }

         if ((this._flags & 4096) != 0) {
            if (this._ordered == null) {
               this._ordered = new ArrayList(5);
            }

            this._ordered.add(id);
         }

         if (aliasOrder) {
            as = toOrderAlias(this._orders++);
            this._selects.setSelectAs(id, as);
         }
      }

      boolean seld = sel && this.select(col, pj, false);
      if (asc != null) {
         String alias = as != null ? as : this.getColumnAlias(col, pj);
         this.appendOrdering(alias, asc);
      }

      return seld;
   }

   private void appendOrdering(Object orderBy, boolean asc) {
      if (this._ordering == null) {
         this._ordering = new SQLBuffer(this._dict);
      } else {
         this._ordering.append(", ");
      }

      if (orderBy instanceof SQLBuffer) {
         this._ordering.append((SQLBuffer)orderBy);
      } else {
         this._ordering.append((String)orderBy);
      }

      if (asc) {
         this._ordering.append(" ASC");
      } else {
         this._ordering.append(" DESC");
      }

   }

   public int orderByPrimaryKey(ClassMapping mapping, boolean asc, boolean sel) {
      return this.orderByPrimaryKey(mapping, asc, (Joins)null, sel);
   }

   public int orderByPrimaryKey(ClassMapping mapping, boolean asc, Joins joins, boolean sel) {
      return this.orderByPrimaryKey(mapping, asc, joins, sel, false);
   }

   public int orderByPrimaryKey(ClassMapping mapping, boolean asc, Joins joins, boolean sel, boolean aliasOrder) {
      return this.primaryKeyOperation(mapping, sel, asc ? Boolean.TRUE : Boolean.FALSE, joins, aliasOrder);
   }

   public boolean orderBy(Column col, boolean asc, boolean sel) {
      return this.orderBy((Column)col, asc, (Joins)null, sel);
   }

   public boolean orderBy(Column col, boolean asc, Joins joins, boolean sel) {
      return this.orderBy(col, asc, joins, sel, false);
   }

   boolean orderBy(Column col, boolean asc, Joins joins, boolean sel, boolean aliasOrder) {
      return this.columnOperation(col, sel, asc ? Boolean.TRUE : Boolean.FALSE, this.getJoins(joins, true), aliasOrder);
   }

   public int orderBy(Column[] cols, boolean asc, boolean sel) {
      return this.orderBy((Column[])cols, asc, (Joins)null, sel);
   }

   public int orderBy(Column[] cols, boolean asc, Joins joins, boolean sel) {
      return this.orderBy(cols, asc, joins, sel, false);
   }

   int orderBy(Column[] cols, boolean asc, Joins joins, boolean sel, boolean aliasOrder) {
      PathJoins pj = this.getJoins(joins, true);
      int seld = 0;

      for(int i = 0; i < cols.length; ++i) {
         if (this.columnOperation(cols[i], sel, asc ? Boolean.TRUE : Boolean.FALSE, pj, aliasOrder)) {
            seld |= 2 << i;
         }
      }

      return seld;
   }

   public boolean orderBy(SQLBuffer sql, boolean asc, boolean sel) {
      return this.orderBy(sql, asc, (Joins)null, sel);
   }

   public boolean orderBy(SQLBuffer sql, boolean asc, Joins joins, boolean sel) {
      return this.orderBy(sql, asc, joins, sel, false);
   }

   boolean orderBy(SQLBuffer sql, boolean asc, Joins joins, boolean sel, boolean aliasOrder) {
      return this.orderBy((Object)sql, asc, joins, sel, aliasOrder);
   }

   private boolean orderBy(Object sql, boolean asc, Joins joins, boolean sel, boolean aliasOrder) {
      Object order = sql;
      if (aliasOrder) {
         order = toOrderAlias(this._orders++);
         this._selects.setSelectAs(sql, (String)order);
      }

      if ((this._flags & 4096) != 0) {
         if (this._ordered == null) {
            this._ordered = new ArrayList(5);
         }

         this._ordered.add(sql);
      }

      this.getJoins(joins, true);
      this.appendOrdering(order, asc);
      if (sel) {
         int idx = this._selects.indexOfAlias(sql);
         if (idx == -1) {
            this._selects.setAlias(this.nullId(), sql, false);
            return true;
         }
      }

      return false;
   }

   public boolean orderBy(String sql, boolean asc, boolean sel) {
      return this.orderBy((String)sql, asc, (Joins)null, sel);
   }

   public boolean orderBy(String sql, boolean asc, Joins joins, boolean sel) {
      return this.orderBy(sql, asc, joins, sel, false);
   }

   boolean orderBy(String sql, boolean asc, Joins joins, boolean sel, boolean aliasOrder) {
      return this.orderBy((Object)sql, asc, joins, sel, aliasOrder);
   }

   public void clearOrdering() {
      this._ordering = null;
      this._orders = 0;
   }

   void setRecordOrderedIndexes(boolean record) {
      if (record) {
         this._flags |= 4096;
      } else {
         this._ordered = null;
         this._flags &= -4097;
      }

   }

   List getOrderedIndexes() {
      if (this._ordered == null) {
         return null;
      } else {
         List idxs = new ArrayList(this._ordered.size());

         for(int i = 0; i < this._ordered.size(); ++i) {
            idxs.add(Numbers.valueOf(this._selects.indexOf(this._ordered.get(i))));
         }

         return idxs;
      }
   }

   public void wherePrimaryKey(Object oid, ClassMapping mapping, JDBCStore store) {
      this.wherePrimaryKey(oid, mapping, (Joins)null, store);
   }

   private void wherePrimaryKey(Object oid, ClassMapping mapping, Joins joins, JDBCStore store) {
      if (!mapping.isPrimaryKeyObjectId(false)) {
         ClassMapping sup = mapping.getJoinablePCSuperclassMapping();
         if (joins == null) {
            joins = this.newJoins();
         }

         joins = mapping.joinSuperclass(joins, false);
         this.wherePrimaryKey(oid, sup, joins, store);
      } else {
         Column[] cols = mapping.getPrimaryKeyColumns();
         this.where(oid, mapping, cols, cols, (Object[])null, (Column[])null, this.getJoins(joins, true), store);
      }
   }

   public void whereForeignKey(ForeignKey fk, Object oid, ClassMapping mapping, JDBCStore store) {
      this.whereForeignKey(fk, oid, mapping, (Joins)null, store);
   }

   private void whereForeignKey(ForeignKey fk, Object oid, ClassMapping mapping, Joins joins, JDBCStore store) {
      if (mapping.isPrimaryKeyObjectId(false) && containsAll(mapping.getPrimaryKeyColumns(), fk.getPrimaryKeyColumns())) {
         Column[] fromCols = fk.getColumns();
         Column[] toCols = fk.getPrimaryKeyColumns();
         Column[] constCols = fk.getConstantColumns();
         Object[] consts = fk.getConstants();
         this.where(oid, mapping, toCols, fromCols, consts, constCols, this.getJoins(joins, true), store);
      } else {
         if (joins == null) {
            joins = this.newJoins();
         }

         do {
            if (mapping.getTable() == fk.getPrimaryKeyTable()) {
               joins = joins.join(fk, false, false);
               this.wherePrimaryKey(oid, mapping, joins, store);
               return;
            }

            if (joins == null) {
               joins = this.newJoins();
            }

            joins = mapping.joinSuperclass(joins, false);
            mapping = mapping.getJoinablePCSuperclassMapping();
         } while(mapping != null);

         throw new InternalException();
      }
   }

   private void where(Object oid, ClassMapping mapping, Column[] toCols, Column[] fromCols, Object[] vals, Column[] constCols, PathJoins pj, JDBCStore store) {
      ValueMapping embed = mapping.getEmbeddingMapping();
      if (embed != null) {
         this.where(oid, embed.getFieldMapping().getDefiningMapping(), toCols, fromCols, vals, constCols, pj, store);
      } else {
         Object[] pks = null;
         if (mapping.getIdentityType() == 2) {
            pks = ApplicationIds.toPKValues(oid, mapping);
         }

         SQLBuffer buf = new SQLBuffer(this._dict);
         int count = 0;

         int i;
         for(i = 0; i < toCols.length; ++count) {
            Object val;
            if (pks == null) {
               val = oid == null ? null : Numbers.valueOf(((Id)oid).getId());
            } else {
               Joinable join = mapping.assertJoinable(toCols[i]);
               val = pks[mapping.getField(join.getFieldIndex()).getPrimaryKeyIndex()];
               val = join.getJoinValue(val, toCols[i], store);
            }

            if (count > 0) {
               buf.append(" AND ");
            }

            buf.append(this.getColumnAlias(fromCols[i], pj));
            if (val == null) {
               buf.append(" IS ");
            } else {
               buf.append(" = ");
            }

            buf.appendValue(val, fromCols[i]);
            ++i;
         }

         if (constCols != null && constCols.length > 0) {
            for(i = 0; i < constCols.length; ++count) {
               if (count > 0) {
                  buf.append(" AND ");
               }

               buf.append(this.getColumnAlias(constCols[i], pj));
               if (vals[i] == null) {
                  buf.append(" IS ");
               } else {
                  buf.append(" = ");
               }

               buf.appendValue(vals[i], constCols[i]);
               ++i;
            }
         }

         this.where(buf, pj);
      }
   }

   private static boolean containsAll(Column[] set, Column[] sub) {
      if (sub.length > set.length) {
         return false;
      } else {
         boolean found = true;

         for(int i = 0; i < sub.length && found; ++i) {
            found = false;

            for(int j = 0; j < set.length && !found; ++j) {
               found = sub[i] == set[j];
            }
         }

         return found;
      }
   }

   public void where(Joins joins) {
      if (joins != null) {
         this.where((String)null, joins);
      }

   }

   public void where(SQLBuffer sql) {
      this.where(sql, (Joins)null);
   }

   public void where(SQLBuffer sql, Joins joins) {
      this.where(sql, this.getJoins(joins, true));
   }

   private void where(SQLBuffer sql, PathJoins pj) {
      if (sql != null && !sql.isEmpty()) {
         if (this._where == null) {
            this._where = new SQLBuffer(this._dict);
         } else if (!this._where.isEmpty()) {
            this._where.append(" AND ");
         }

         this._where.append(sql);
      }
   }

   public void where(String sql) {
      this.where(sql, (Joins)null);
   }

   public void where(String sql, Joins joins) {
      this.where(sql, this.getJoins(joins, true));
   }

   private void where(String sql, PathJoins pj) {
      if (!StringUtils.isEmpty(sql)) {
         if (this._where == null) {
            this._where = new SQLBuffer(this._dict);
         } else if (!this._where.isEmpty()) {
            this._where.append(" AND ");
         }

         this._where.append(sql);
      }
   }

   public void having(SQLBuffer sql) {
      this.having(sql, (Joins)null);
   }

   public void having(SQLBuffer sql, Joins joins) {
      this.having(sql, this.getJoins(joins, true));
   }

   private void having(SQLBuffer sql, PathJoins pj) {
      if (sql != null && !sql.isEmpty()) {
         if (this._having == null) {
            this._having = new SQLBuffer(this._dict);
         } else if (!this._having.isEmpty()) {
            this._having.append(" AND ");
         }

         this._having.append(sql);
      }
   }

   public void having(String sql) {
      this.having(sql, (Joins)null);
   }

   public void having(String sql, Joins joins) {
      this.having(sql, this.getJoins(joins, true));
   }

   private void having(String sql, PathJoins pj) {
      if (!StringUtils.isEmpty(sql)) {
         if (this._having == null) {
            this._having = new SQLBuffer(this._dict);
         } else if (!this._having.isEmpty()) {
            this._having.append(" AND ");
         }

         this._having.append(sql);
      }
   }

   public void groupBy(SQLBuffer sql) {
      this.groupBy(sql, (Joins)null);
   }

   public void groupBy(SQLBuffer sql, Joins joins) {
      this.getJoins(joins, true);
      this.groupByAppend(sql.getSQL());
   }

   public void groupBy(String sql) {
      this.groupBy(sql, (Joins)null);
   }

   public void groupBy(String sql, Joins joins) {
      this.getJoins(joins, true);
      this.groupByAppend(sql);
   }

   public void groupBy(Column col) {
      this.groupBy((Column)col, (Joins)null);
   }

   public void groupBy(Column col, Joins joins) {
      PathJoins pj = this.getJoins(joins, true);
      this.groupByAppend(this.getColumnAlias(col, pj));
   }

   public void groupBy(Column[] cols) {
      this.groupBy((Column[])cols, (Joins)null);
   }

   public void groupBy(Column[] cols, Joins joins) {
      PathJoins pj = this.getJoins(joins, true);

      for(int i = 0; i < cols.length; ++i) {
         this.groupByAppend(this.getColumnAlias(cols[i], pj));
      }

   }

   private void groupByAppend(String sql) {
      if (this._grouped == null || !this._grouped.contains(sql)) {
         if (this._grouping == null) {
            this._grouping = new SQLBuffer(this._dict);
            this._grouped = new ArrayList();
         } else {
            this._grouping.append(", ");
         }

         this._grouping.append(sql);
         this._grouped.add(sql);
      }

   }

   public void groupBy(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch) {
      this.groupBy(mapping, subclasses, store, fetch, (Joins)null);
   }

   public void groupBy(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
      boolean wasGrouping = this.isGrouping();
      this._flags |= 8192;

      try {
         this.select(mapping, subclasses, store, fetch, 0, joins);
      } finally {
         if (!wasGrouping) {
            this._flags &= -8193;
         }

      }

   }

   private boolean isGrouping() {
      return (this._flags & 8192) != 0;
   }

   private PathJoins getJoins(Joins joins, boolean record) {
      PathJoins pj = (PathJoins)joins;
      boolean pre = (pj == null || !((PathJoins)pj).isDirty()) && this._preJoins != null && !this._preJoins.isEmpty();
      if (pre) {
         pj = (PathJoins)this._preJoins.peek();
      }

      if (pj != null && ((PathJoins)pj).isDirty()) {
         if (!pre) {
            if ((this._flags & 256) != 0) {
               pj = (PathJoins)this.outer((Joins)pj);
            }

            if (record) {
               if (!((PathJoins)pj).isEmpty()) {
                  this.removeParentJoins((PathJoins)pj);
               }

               if (!((PathJoins)pj).isEmpty()) {
                  this.removeJoinsFromSubselects((PathJoins)pj);
                  if (this._joins == null) {
                     this._joins = new SelectJoins(this);
                  }

                  if (this._joins.joins() == null) {
                     this._joins.setJoins(new JoinSet(((PathJoins)pj).joins()));
                  } else {
                     this._joins.joins().addAll(((PathJoins)pj).joins());
                  }
               }
            }
         }
      } else {
         pj = this._joins;
      }

      return (PathJoins)pj;
   }

   private void removeParentJoins(PathJoins pj) {
      if (this._parent != null) {
         if (this._parent._joins != null && !this._parent._joins.isEmpty()) {
            boolean removed = false;
            if (!this._removedAliasFromParent.isEmpty()) {
               Iterator itr = pj.joins().iterator();

               while(itr.hasNext()) {
                  Join jn = (Join)itr.next();
                  if (this._aliases.containsValue(jn.getIndex1())) {
                     removed = this._parent._joins.joins().remove(jn);
                  }
               }
            }

            if (!removed) {
               pj.joins().removeAll(this._parent._joins.joins());
            }
         }

         if (!pj.isEmpty()) {
            this._parent.removeParentJoins(pj);
         }

      }
   }

   private void removeJoinsFromSubselects(PathJoins pj) {
      if (this._subsels != null) {
         for(int i = 0; i < this._subsels.size(); ++i) {
            SelectImpl sub = (SelectImpl)this._subsels.get(i);
            if (sub._joins != null && !sub._joins.isEmpty()) {
               sub._joins.joins().removeAll(pj.joins());
            }
         }

      }
   }

   public SelectExecutor whereClone(int sels) {
      if (sels < 1) {
         sels = 1;
      }

      Select[] clones = null;

      for(int i = 0; i < sels; ++i) {
         SelectImpl sel = (SelectImpl)this._conf.getSQLFactoryInstance().newSelect();
         sel._flags = this._flags;
         sel._flags &= -65;
         sel._flags &= -257;
         sel._flags &= -513;
         sel._flags &= -1025;
         sel._flags &= -2049;
         sel._flags &= -16385;
         sel._joinSyntax = this._joinSyntax;
         if (this._aliases != null) {
            sel._aliases = new HashMap(this._aliases);
         }

         if (this._tables != null) {
            sel._tables = new TreeMap(this._tables);
         }

         if (this._joins != null) {
            sel._joins = this._joins.clone(sel);
         }

         if (this._where != null) {
            sel._where = new SQLBuffer(this._where);
         }

         if (this._from != null) {
            sel._from = (SelectImpl)this._from.whereClone(1);
            sel._from._outer = sel;
         }

         if (this._subsels != null) {
            sel._subsels = new ArrayList(this._subsels.size());

            for(int j = 0; j < this._subsels.size(); ++j) {
               SelectImpl sub = (SelectImpl)this._subsels.get(j);
               SelectImpl selSub = (SelectImpl)sub.fullClone(1);
               selSub._parent = sel;
               selSub._subPath = sub._subPath;
               sel._subsels.add(selSub);
               if (sel._where != null) {
                  sel._where.replace(sub, selSub);
               }
            }
         }

         if (sels == 1) {
            return sel;
         }

         if (clones == null) {
            clones = new Select[sels];
         }

         clones[i] = sel;
      }

      return this._conf.getSQLFactoryInstance().newUnion(clones);
   }

   public SelectExecutor fullClone(int sels) {
      if (sels < 1) {
         sels = 1;
      }

      Select[] clones = null;

      for(int i = 0; i < sels; ++i) {
         SelectImpl sel = (SelectImpl)this.whereClone(1);
         sel._flags = this._flags;
         sel._expectedResultCount = this._expectedResultCount;
         sel._selects.addAll(this._selects);
         if (this._ordering != null) {
            sel._ordering = new SQLBuffer(this._ordering);
         }

         sel._orders = this._orders;
         if (this._grouping != null) {
            sel._grouping = new SQLBuffer(this._grouping);
         }

         if (this._having != null) {
            sel._having = new SQLBuffer(this._having);
         }

         if (this._from != null) {
            sel._from = (SelectImpl)this._from.fullClone(1);
            sel._from._outer = sel;
         }

         if (sels == 1) {
            return sel;
         }

         if (clones == null) {
            clones = new Select[sels];
         }

         clones[i] = sel;
      }

      return this._conf.getSQLFactoryInstance().newUnion(clones);
   }

   public SelectExecutor eagerClone(FieldMapping key, int eagerType, boolean toMany, int sels) {
      if (eagerType == 1 && this._joinSyntax == 1) {
         return null;
      } else if (this._eagerKeys != null && this._eagerKeys.contains(key)) {
         return null;
      } else {
         if (this._eagerKeys == null) {
            this._eagerKeys = new HashSet();
         }

         this._eagerKeys.add(key);
         Object sel;
         if (eagerType != 2) {
            if (toMany) {
               this._flags |= 2048;
            } else {
               this._flags |= 1024;
            }

            sel = this;
         } else if (sels < 2) {
            sel = this.parallelClone();
         } else {
            Select[] clones = new Select[sels];

            for(int i = 0; i < clones.length; ++i) {
               clones[i] = this.parallelClone();
            }

            sel = this._conf.getSQLFactoryInstance().newUnion(clones);
         }

         if (this._eager == null) {
            this._eager = new HashMap();
         }

         this._eager.put(toEagerKey(key, this.getJoins((Joins)null, false)), sel);
         return (SelectExecutor)sel;
      }
   }

   private SelectImpl parallelClone() {
      SelectImpl sel = (SelectImpl)this.whereClone(1);
      sel._flags &= -3;
      sel._eagerKeys = this._eagerKeys;
      if (this._preJoins != null && !this._preJoins.isEmpty()) {
         sel._preJoins = new Stack();
         sel._preJoins.push(((SelectJoins)this._preJoins.peek()).clone(sel));
      }

      return sel;
   }

   public Map getEagerMap() {
      return this._eager;
   }

   public SelectExecutor getEager(FieldMapping key) {
      return this._eager != null && this._eagerKeys.contains(key) ? (SelectExecutor)this._eager.get(toEagerKey(key, this.getJoins((Joins)null, false))) : null;
   }

   private static Object toEagerKey(FieldMapping key, PathJoins pj) {
      return pj != null && pj.path() != null ? new Key(pj.path().toString(), key) : key;
   }

   public Joins newJoins() {
      if (this._preJoins != null && !this._preJoins.isEmpty()) {
         SelectJoins sj = (SelectJoins)this._preJoins.peek();
         return sj.clone(this);
      } else {
         return this;
      }
   }

   public Joins newOuterJoins() {
      return ((PathJoins)this.newJoins()).setOuter(true);
   }

   public void append(SQLBuffer buf, Joins joins) {
      if (joins != null && !joins.isEmpty()) {
         if (!buf.isEmpty()) {
            buf.append(" AND ");
         }

         Join join = null;
         Iterator itr = ((PathJoins)joins).joins().joinIterator();

         while(itr.hasNext()) {
            join = (Join)itr.next();
            switch (this._joinSyntax) {
               case 1:
                  buf.append(this._dict.toTraditionalJoin(join));
                  break;
               case 2:
                  buf.append(this._dict.toNativeJoin(join));
                  break;
               default:
                  throw new InternalException();
            }

            if (itr.hasNext()) {
               buf.append(" AND ");
            }
         }

      }
   }

   public Joins and(Joins joins1, Joins joins2) {
      return this.and((PathJoins)joins1, (PathJoins)joins2, true);
   }

   private SelectJoins and(PathJoins j1, PathJoins j2, boolean nullJoins) {
      if (j1 != null && !j1.isEmpty() || j2 != null && !j2.isEmpty()) {
         SelectJoins sj = new SelectJoins(this);
         if (j1 != null && !j1.isEmpty()) {
            JoinSet set;
            if (nullJoins) {
               set = j1.joins();
            } else {
               set = new JoinSet(j1.joins());
            }

            if (j2 != null && !j2.isEmpty()) {
               set.addAll(j2.joins());
            }

            sj.setJoins(set);
         } else if (nullJoins) {
            sj.setJoins(j2.joins());
         } else {
            sj.setJoins(new JoinSet(j2.joins()));
         }

         if (nullJoins && j1 != null) {
            j1.nullJoins();
         }

         if (nullJoins && j2 != null) {
            j2.nullJoins();
         }

         return sj;
      } else {
         return null;
      }
   }

   public Joins or(Joins joins1, Joins joins2) {
      PathJoins j1 = (PathJoins)joins1;
      PathJoins j2 = (PathJoins)joins2;
      boolean j1Empty = j1 == null || j1.isEmpty();
      boolean j2Empty = j2 == null || j2.isEmpty();
      if (!j1Empty && !j2Empty) {
         SelectJoins sj = new SelectJoins(this);
         if (j1.joins().equals(j2.joins())) {
            sj.setJoins(j1.joins());
            j1.nullJoins();
            j2.nullJoins();
         } else {
            JoinSet commonJoins = new JoinSet(j1.joins());
            commonJoins.retainAll(j2.joins());
            if (!commonJoins.isEmpty()) {
               sj.setJoins(commonJoins);
               j1.joins().removeAll(commonJoins);
               j2.joins().removeAll(commonJoins);
            }

            this.collectOuterJoins(j1);
            this.collectOuterJoins(j2);
            if (!j1.isEmpty() || !j2.isEmpty()) {
               this._flags |= 16;
            }
         }

         return sj;
      } else {
         if (j1Empty && !j2Empty) {
            this.collectOuterJoins(j2);
            if (!j2.isEmpty()) {
               this._flags |= 16;
            }
         } else if (j2Empty && !j1Empty) {
            this.collectOuterJoins(j1);
            if (!j1.isEmpty()) {
               this._flags |= 16;
            }
         }

         return null;
      }
   }

   public Joins outer(Joins joins) {
      if (this._joinSyntax != 1 && joins != null) {
         PathJoins pj = ((PathJoins)joins).setOuter(true);
         if (pj.isEmpty()) {
            return pj;
         } else {
            boolean hasJoins = this._joins != null && this._joins.joins() != null;
            Iterator itr = pj.joins().iterator();

            while(true) {
               while(true) {
                  Join join;
                  do {
                     if (!itr.hasNext()) {
                        return joins;
                     }

                     join = (Join)itr.next();
                  } while(join.getType() != 0);

                  if (!hasJoins) {
                     join.setType(1);
                  } else {
                     Join rec = this._joins.joins().getRecordedJoin(join);
                     if (rec == null || rec.getType() == 1) {
                        join.setType(1);
                     }
                  }
               }
            }
         }
      } else {
         return joins;
      }
   }

   private void collectOuterJoins(PathJoins pj) {
      if (this._joinSyntax != 1 && pj != null && !pj.isEmpty()) {
         if (this._joins == null) {
            this._joins = new SelectJoins(this);
         }

         boolean add = true;
         if (this._joins.joins() == null) {
            this._joins.setJoins(pj.joins());
            add = false;
         }

         Iterator itr = pj.joins().iterator();

         while(itr.hasNext()) {
            Join join = (Join)itr.next();
            if (join.getType() == 0) {
               if (join.getForeignKey() != null && !this._dict.canOuterJoin(this._joinSyntax, join.getForeignKey())) {
                  Log log = this._conf.getLog("openjpa.jdbc.JDBC");
                  if (log.isWarnEnabled()) {
                     log.warn(_loc.get("cant-outer-fk", (Object)join.getForeignKey()));
                  }
               } else {
                  join.setType(1);
               }
            }

            if (add) {
               this._joins.joins().add(join);
            }
         }

         pj.nullJoins();
      }
   }

   private int getTableIndex(Table table, PathJoins pj, boolean create) {
      if (this._from != null) {
         return -1;
      } else {
         Object key = table.getFullName();
         if (pj != null && pj.path() != null) {
            key = new Key(pj.path().toString(), key);
         }

         Integer i = this.findAlias(table, key, false, (SelectImpl)null);
         if (i != null) {
            return i;
         } else if (!create) {
            return -1;
         } else {
            i = Numbers.valueOf(this.aliasSize());
            this.recordTableAlias(table, key, i);
            return i;
         }
      }
   }

   private Integer findAlias(Table table, Object key, boolean fromParent, SelectImpl fromSub) {
      Integer alias = null;
      if (this._aliases != null) {
         alias = (Integer)(fromParent ? this._aliases.remove(key) : this._aliases.get(key));
         if (alias != null) {
            if (fromParent) {
               this._tables.remove(alias);
            }

            return alias;
         }
      }

      if (!fromParent && this._parent != null) {
         boolean removeAliasFromParent = key.toString().indexOf(":") != -1;
         alias = this._parent.findAlias(table, key, removeAliasFromParent, this);
         if (alias != null) {
            if (removeAliasFromParent) {
               this.recordTableAlias(table, key, alias);
               this._removedAliasFromParent.set(alias);
            }

            return alias;
         }
      }

      if (this._subsels != null) {
         for(int i = 0; i < this._subsels.size(); ++i) {
            SelectImpl sub = (SelectImpl)this._subsels.get(i);
            if (sub != fromSub) {
               if (alias != null) {
                  if (sub._aliases != null) {
                     sub._aliases.remove(key);
                  }

                  if (sub._tables != null) {
                     sub._tables.remove(alias);
                  }
               } else if (key instanceof String) {
                  alias = sub.findAlias(table, key, true, (SelectImpl)null);
                  if (!fromParent && alias != null) {
                     this.recordTableAlias(table, key, alias);
                  }
               }
            }
         }
      }

      return alias;
   }

   private void recordTableAlias(Table table, Object key, Integer alias) {
      if (this._aliases == null) {
         this._aliases = new HashMap();
      }

      this._aliases.put(key, alias);
      String tableString = this._dict.getFullName(table, false) + " " + toAlias(alias);
      if (this._tables == null) {
         this._tables = new TreeMap();
      }

      this._tables.put(alias, tableString);
   }

   private int aliasSize() {
      return this.aliasSize(false, (SelectImpl)null);
   }

   private int aliasSize(boolean fromParent, SelectImpl fromSub) {
      int aliases = !fromParent && this._parent != null ? this._parent.aliasSize(false, this) : 0;
      aliases += this._aliases == null ? 0 : this._aliases.size();
      if (this._subsels != null) {
         for(int i = 0; i < this._subsels.size(); ++i) {
            SelectImpl sub = (SelectImpl)this._subsels.get(i);
            if (sub != fromSub) {
               aliases += sub.aliasSize(true, (SelectImpl)null);
            }
         }
      }

      return aliases;
   }

   public String toString() {
      return this.toSelect(false, (JDBCFetchConfiguration)null).getSQL();
   }

   public boolean isOuter() {
      return false;
   }

   public PathJoins setOuter(boolean outer) {
      return (new SelectJoins(this)).setOuter(true);
   }

   public boolean isDirty() {
      return false;
   }

   public StringBuffer path() {
      return null;
   }

   public JoinSet joins() {
      return null;
   }

   public int joinCount() {
      return 0;
   }

   public void nullJoins() {
   }

   public boolean isEmpty() {
      return true;
   }

   public Joins crossJoin(Table localTable, Table foreignTable) {
      return (new SelectJoins(this)).crossJoin(localTable, foreignTable);
   }

   public Joins join(ForeignKey fk, boolean inverse, boolean toMany) {
      return (new SelectJoins(this)).join(fk, inverse, toMany);
   }

   public Joins outerJoin(ForeignKey fk, boolean inverse, boolean toMany) {
      return (new SelectJoins(this)).outerJoin(fk, inverse, toMany);
   }

   public Joins joinRelation(String name, ForeignKey fk, ClassMapping target, int subs, boolean inverse, boolean toMany) {
      return (new SelectJoins(this)).joinRelation(name, fk, target, subs, inverse, toMany);
   }

   public Joins outerJoinRelation(String name, ForeignKey fk, ClassMapping target, int subs, boolean inverse, boolean toMany) {
      return (new SelectJoins(this)).outerJoinRelation(name, fk, target, subs, inverse, toMany);
   }

   public Joins setVariable(String var) {
      return (Joins)(var == null ? this : (new SelectJoins(this)).setVariable(var));
   }

   public Joins setSubselect(String alias) {
      return (Joins)(alias == null ? this : (new SelectJoins(this)).setSubselect(alias));
   }

   protected Selects newSelects() {
      return new Selects();
   }

   static {
      int i;
      for(i = 0; i < TABLE_ALIASES.length; ++i) {
         TABLE_ALIASES[i] = "t" + i;
      }

      for(i = 0; i < ORDER_ALIASES.length; ++i) {
         ORDER_ALIASES[i] = "o" + i;
      }

      for(i = 0; i < NULL_IDS.length; ++i) {
         NULL_IDS[i] = new NullId();
      }

      for(i = 0; i < PLACEHOLDERS.length; ++i) {
         PLACEHOLDERS[i] = new Placeholder();
      }

   }

   protected static class Selects extends AbstractList {
      protected List _ids = null;
      protected List _idents = null;
      protected Map _aliases = null;
      protected Map _selectAs = null;
      protected DBDictionary _dict = null;

      public void addAll(Selects sels) {
         if (this._ids == null && sels._ids != null) {
            this._ids = new ArrayList(sels._ids);
         } else if (sels._ids != null) {
            this._ids.addAll(sels._ids);
         }

         if (this._idents == null && sels._idents != null) {
            this._idents = new ArrayList(sels._idents);
         } else if (sels._idents != null) {
            this._idents.addAll(sels._idents);
         }

         if (this._aliases == null && sels._aliases != null) {
            this._aliases = new HashMap(sels._aliases);
         } else if (sels._aliases != null) {
            this._aliases.putAll(sels._aliases);
         }

         if (this._selectAs == null && sels._selectAs != null) {
            this._selectAs = new HashMap(sels._selectAs);
         } else if (sels._selectAs != null) {
            this._selectAs.putAll(sels._selectAs);
         }

      }

      public Object getAlias(Object id) {
         return this._aliases == null ? null : this._aliases.get(id);
      }

      public int setAlias(Object id, Object alias, boolean ident) {
         if (this._ids == null) {
            this._ids = new ArrayList();
            this._aliases = new HashMap();
         }

         int idx;
         if (this._aliases.put(id, alias) != null) {
            idx = this._ids.indexOf(id);
         } else {
            this._ids.add(id);
            idx = this._ids.size() - 1;
            if (ident) {
               if (this._idents == null) {
                  this._idents = new ArrayList(3);
               }

               this._idents.add(id);
            }
         }

         return idx;
      }

      public void setAlias(int idx, Object alias) {
         Object id = this._ids.get(idx);
         this._aliases.put(id, alias);
      }

      public void insertAlias(int idx, Object id, Object alias) {
         this._aliases.put(id, alias);
         if (idx >= 0) {
            this._ids.add(idx, id);
         } else {
            this._ids.add(this._ids.size() + idx, id);
         }

      }

      public int indexOfAlias(Object alias) {
         if (this._aliases == null) {
            return -1;
         } else {
            for(int i = 0; i < this._ids.size(); ++i) {
               if (alias.equals(this._aliases.get(this._ids.get(i)))) {
                  return i;
               }
            }

            return -1;
         }
      }

      public List getAliases(final boolean ident, final boolean inner) {
         return (List)(this._ids == null ? Collections.EMPTY_LIST : new AbstractList() {
            public int size() {
               return ident && Selects.this._idents != null ? Selects.this._idents.size() : Selects.this._ids.size();
            }

            public Object get(int i) {
               Object id = ident && Selects.this._idents != null ? Selects.this._idents.get(i) : Selects.this._ids.get(i);
               Object alias = Selects.this._aliases.get(id);
               if (id instanceof Column && ((Column)id).isXML()) {
                  alias = alias + Selects.this._dict.getStringVal;
               }

               String as = null;
               if (inner) {
                  as = ((String)alias).replace('.', '_');
               } else if (Selects.this._selectAs != null) {
                  as = (String)Selects.this._selectAs.get(id);
               }

               if (as != null) {
                  if (ident && Selects.this._idents != null) {
                     return as;
                  }

                  if (alias instanceof SQLBuffer) {
                     alias = (new SQLBuffer((SQLBuffer)alias)).append(" AS ").append(as);
                  } else {
                     alias = alias + " AS " + as;
                  }
               }

               return alias;
            }
         });
      }

      public void setSelectAs(Object id, String as) {
         if (this._selectAs == null) {
            this._selectAs = new HashMap(7);
         }

         this._selectAs.put(id, as);
      }

      public void clearPlaceholders() {
         if (this._ids != null) {
            Iterator itr = this._ids.iterator();

            while(itr.hasNext()) {
               Object id = itr.next();
               if (id instanceof Placeholder) {
                  itr.remove();
                  this._aliases.remove(id);
               }
            }

         }
      }

      public boolean contains(Object id) {
         return this._aliases != null && this._aliases.containsKey(id);
      }

      public Object get(int i) {
         if (this._ids == null) {
            throw new ArrayIndexOutOfBoundsException();
         } else {
            return this._ids.get(i);
         }
      }

      public int size() {
         return this._ids == null ? 0 : this._ids.size();
      }

      public void clear() {
         this._ids = null;
         this._aliases = null;
         this._selectAs = null;
         this._idents = null;
      }
   }

   private static class SelectJoins extends PathJoinsImpl implements Cloneable {
      private final SelectImpl _sel;
      private JoinSet _joins = null;
      private boolean _outer = false;
      private int _count = 0;

      public SelectJoins(SelectImpl sel) {
         super(null);
         this._sel = sel;
      }

      public boolean isOuter() {
         return this._outer;
      }

      public PathJoins setOuter(boolean outer) {
         this._outer = outer;
         return this;
      }

      public boolean isDirty() {
         return super.isDirty() || !this.isEmpty();
      }

      public JoinSet joins() {
         return this._joins;
      }

      public void setJoins(JoinSet joins) {
         this._joins = joins;
         this._outer = joins != null && joins.last() != null && joins.last().getType() == 1;
      }

      public int joinCount() {
         return this._joins == null ? this._count : Math.max(this._count, this._joins.size());
      }

      public void nullJoins() {
         if (this._joins != null) {
            this._count = Math.max(this._count, this._joins.size());
         }

         this._joins = null;
      }

      public boolean isEmpty() {
         return this._joins == null || this._joins.isEmpty();
      }

      public Joins crossJoin(Table localTable, Table foreignTable) {
         this._sel._flags = 16;
         if (this._sel.getJoinSyntax() == 0 && this._sel._from == null) {
            String var = this.var;
            this.var = null;
            int alias1 = this._sel.getTableIndex(localTable, this, true);
            this.append(var);
            int alias2 = this._sel.getTableIndex(foreignTable, this, true);
            Join j = new Join(localTable, alias1, foreignTable, alias2, (ForeignKey)null, false);
            j.setType(2);
            if (this._joins == null) {
               this._joins = new JoinSet();
            }

            this._joins.add(j);
            this._outer = false;
            return this;
         } else {
            this.append(this.var);
            this.var = null;
            this._outer = false;
            return this;
         }
      }

      public Joins join(ForeignKey fk, boolean inverse, boolean toMany) {
         return this.join((String)null, fk, (ClassMapping)null, -1, inverse, toMany, false);
      }

      public Joins outerJoin(ForeignKey fk, boolean inverse, boolean toMany) {
         return this.join((String)null, fk, (ClassMapping)null, -1, inverse, toMany, true);
      }

      public Joins joinRelation(String name, ForeignKey fk, ClassMapping target, int subs, boolean inverse, boolean toMany) {
         return this.join(name, fk, target, subs, inverse, toMany, false);
      }

      public Joins outerJoinRelation(String name, ForeignKey fk, ClassMapping target, int subs, boolean inverse, boolean toMany) {
         return this.join(name, fk, target, subs, inverse, toMany, true);
      }

      private Joins join(String name, ForeignKey fk, ClassMapping target, int subs, boolean inverse, boolean toMany, boolean outer) {
         String var = this.var;
         this.var = null;
         boolean createJoin = this._sel._from == null;
         Table table1 = null;
         int alias1 = -1;
         if (createJoin) {
            table1 = inverse ? fk.getPrimaryKeyTable() : fk.getTable();
            alias1 = this._sel.getTableIndex(table1, this, true);
         }

         this.append(name);
         this.append(var);
         if (toMany) {
            this._sel._flags = 16;
            this._sel._flags = 32;
         }

         this._outer = outer;
         if (createJoin) {
            Table table2 = inverse ? fk.getTable() : fk.getPrimaryKeyTable();
            int alias2 = this._sel.getTableIndex(table2, this, true);
            Join j = new Join(table1, alias1, table2, alias2, fk, inverse);
            j.setType(outer ? 1 : 0);
            if (this._joins == null) {
               this._joins = new JoinSet();
            }

            if (this._joins.add(j) && (subs == 1 || subs == 2)) {
               j.setRelation(target, subs, this.clone(this._sel));
            }
         }

         return this;
      }

      public SelectJoins clone(SelectImpl sel) {
         SelectJoins sj = new SelectJoins(sel);
         sj.var = this.var;
         if (this.path != null) {
            sj.path = new StringBuffer(this.path.toString());
         }

         if (this._joins != null && !this._joins.isEmpty()) {
            sj._joins = new JoinSet(this._joins);
         }

         sj._outer = this._outer;
         return sj;
      }

      public String toString() {
         return super.toString() + " (" + this._outer + "): " + this._joins;
      }
   }

   private static class PathJoinsImpl implements PathJoins {
      protected StringBuffer path;
      protected String var;

      private PathJoinsImpl() {
         this.path = null;
         this.var = null;
      }

      public boolean isOuter() {
         return false;
      }

      public PathJoins setOuter(boolean outer) {
         return this;
      }

      public boolean isDirty() {
         return this.var != null || this.path != null;
      }

      public StringBuffer path() {
         return this.path;
      }

      public JoinSet joins() {
         return null;
      }

      public int joinCount() {
         return 0;
      }

      public void nullJoins() {
      }

      public Joins setVariable(String var) {
         this.var = var;
         return this;
      }

      public Joins setSubselect(String alias) {
         if (!alias.endsWith(":")) {
            alias = alias + ':';
         }

         this.append(alias);
         return this;
      }

      public boolean isEmpty() {
         return true;
      }

      public Joins crossJoin(Table localTable, Table foreignTable) {
         this.append(this.var);
         this.var = null;
         return this;
      }

      public Joins join(ForeignKey fk, boolean inverse, boolean toMany) {
         this.append(this.var);
         this.var = null;
         return this;
      }

      public Joins outerJoin(ForeignKey fk, boolean inverse, boolean toMany) {
         this.append(this.var);
         this.var = null;
         return this;
      }

      public Joins joinRelation(String name, ForeignKey fk, ClassMapping target, int subs, boolean inverse, boolean toMany) {
         this.append(name);
         this.append(this.var);
         this.var = null;
         return this;
      }

      public Joins outerJoinRelation(String name, ForeignKey fk, ClassMapping target, int subs, boolean inverse, boolean toMany) {
         this.append(name);
         this.append(this.var);
         this.var = null;
         return this;
      }

      protected void append(String str) {
         if (str != null) {
            if (this.path == null) {
               this.path = new StringBuffer(str);
            } else {
               this.path.append('.').append(str);
            }
         }

      }

      public String toString() {
         return "PathJoinsImpl<" + this.hashCode() + ">: " + this.path;
      }

      // $FF: synthetic method
      PathJoinsImpl(Object x0) {
         this();
      }
   }

   public static class SelectResult extends ResultSetResult implements PathJoins {
      private SelectImpl _sel = null;
      private int _pos = 0;
      private Stack _preJoins = null;

      public SelectResult(Connection conn, Statement stmnt, ResultSet rs, DBDictionary dict) {
         super(conn, stmnt, rs, dict);
      }

      public SelectImpl getSelect() {
         return this._sel;
      }

      public void setSelect(SelectImpl sel) {
         this._sel = sel;
      }

      public Object getEager(FieldMapping key) {
         if (this._sel._eager != null && this._sel._eagerKeys.contains(key)) {
            Map map = this.getEagerMap(true);
            if (map == null) {
               return null;
            } else {
               SelectImpl var10001 = this._sel;
               return map.get(SelectImpl.toEagerKey(key, this.getJoins((Joins)null)));
            }
         } else {
            return null;
         }
      }

      public void putEager(FieldMapping key, Object res) {
         Map map = this.getEagerMap(true);
         if (map == null) {
            map = new HashMap();
            this.setEagerMap((Map)map);
         }

         SelectImpl var10001 = this._sel;
         ((Map)map).put(SelectImpl.toEagerKey(key, this.getJoins((Joins)null)), res);
      }

      public Object load(ClassMapping mapping, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) throws SQLException {
         boolean hasJoins = joins != null && ((PathJoins)joins).path() != null;
         if (hasJoins) {
            if (this._preJoins == null) {
               this._preJoins = new Stack();
            }

            this._preJoins.push(joins);
         }

         Object obj = super.load(mapping, store, fetch, joins);
         if (hasJoins) {
            this._preJoins.pop();
         }

         return obj;
      }

      public Joins newJoins() {
         PathJoins pre = this.getPreJoins();
         if (pre != null && pre.path() != null) {
            PathJoinsImpl pj = new PathJoinsImpl();
            pj.path = new StringBuffer(pre.path().toString());
            return pj;
         } else {
            return this;
         }
      }

      protected boolean containsInternal(Object obj, Joins joins) {
         PathJoins pj = this.getJoins(joins);
         if (pj != null && pj.path() != null) {
            obj = this.getColumnAlias((Column)obj, pj);
         }

         return obj != null && this._sel._selects.contains(obj);
      }

      protected boolean containsAllInternal(Object[] objs, Joins joins) throws SQLException {
         PathJoins pj = this.getJoins(joins);

         for(int i = 0; i < objs.length; ++i) {
            Object obj;
            if (pj != null && pj.path() != null) {
               obj = this.getColumnAlias((Column)objs[i], pj);
            } else {
               obj = objs[i];
            }

            if (obj == null || !this._sel._selects.contains(obj)) {
               return false;
            }
         }

         return true;
      }

      public void pushBack() throws SQLException {
         this._pos = 0;
         super.pushBack();
      }

      protected boolean absoluteInternal(int row) throws SQLException {
         this._pos = 0;
         return super.absoluteInternal(row);
      }

      protected boolean nextInternal() throws SQLException {
         this._pos = 0;
         return super.nextInternal();
      }

      protected int findObject(Object obj, Joins joins) throws SQLException {
         if (this._pos == this._sel._selects.size()) {
            this._pos = 0;
         }

         PathJoins pj = this.getJoins(joins);
         Boolean pk = null;
         if (pj != null && pj.path() != null) {
            Column col = (Column)obj;
            pk = col.isPrimaryKey() ? Boolean.TRUE : Boolean.FALSE;
            obj = this.getColumnAlias(col, pj);
            if (obj == null) {
               throw new SQLException(col.getTable() + ": " + pj.path() + " (" + this._sel._aliases + ")");
            }
         }

         if (this._sel._selects.get(this._pos).equals(obj)) {
            return ++this._pos;
         } else {
            if (pk == null) {
               pk = obj instanceof Column && ((Column)obj).isPrimaryKey() ? Boolean.TRUE : Boolean.FALSE;
            }

            int i;
            if (pk) {
               for(i = this._pos - 1; i >= 0 && i >= this._pos - 3; --i) {
                  if (this._sel._selects.get(i).equals(obj)) {
                     return i + 1;
                  }
               }
            }

            for(i = this._pos + 1; i < this._sel._selects.size(); ++i) {
               if (this._sel._selects.get(i).equals(obj)) {
                  this._pos = i;
                  return ++this._pos;
               }
            }

            for(i = 0; i < this._pos; ++i) {
               if (this._sel._selects.get(i).equals(obj)) {
                  return i + 1;
               }
            }

            throw new SQLException(obj.toString());
         }
      }

      private PathJoins getJoins(Joins joins) {
         PathJoins pj = (PathJoins)joins;
         return pj != null && pj.path() != null ? pj : this.getPreJoins();
      }

      private PathJoins getPreJoins() {
         if (this._preJoins != null && !this._preJoins.isEmpty()) {
            return (PathJoins)this._preJoins.peek();
         } else {
            return this._sel._preJoins != null && !this._sel._preJoins.isEmpty() ? (PathJoins)this._sel._preJoins.peek() : null;
         }
      }

      private String getColumnAlias(Column col, PathJoins pj) {
         SelectImpl var10000;
         String alias;
         if (this._sel._from != null) {
            var10000 = this._sel;
            alias = SelectImpl.toAlias(this._sel._from.getTableIndex(col.getTable(), pj, false));
            if (alias == null) {
               return null;
            } else {
               return this._sel._dict.requiresAliasForSubselect ? "s." + alias + "_" + col : alias + "_" + col;
            }
         } else {
            var10000 = this._sel;
            alias = SelectImpl.toAlias(this._sel.getTableIndex(col.getTable(), pj, false));
            return alias == null ? null : alias + "." + col;
         }
      }

      public boolean isOuter() {
         return false;
      }

      public PathJoins setOuter(boolean outer) {
         return this;
      }

      public boolean isDirty() {
         return false;
      }

      public StringBuffer path() {
         return null;
      }

      public JoinSet joins() {
         return null;
      }

      public int joinCount() {
         return 0;
      }

      public void nullJoins() {
      }

      public boolean isEmpty() {
         return true;
      }

      public Joins crossJoin(Table localTable, Table foreignTable) {
         return this;
      }

      public Joins join(ForeignKey fk, boolean inverse, boolean toMany) {
         return this;
      }

      public Joins outerJoin(ForeignKey fk, boolean inverse, boolean toMany) {
         return this;
      }

      public Joins joinRelation(String name, ForeignKey fk, ClassMapping target, int subs, boolean inverse, boolean toMany) {
         return (new PathJoinsImpl()).joinRelation(name, fk, target, subs, inverse, toMany);
      }

      public Joins outerJoinRelation(String name, ForeignKey fk, ClassMapping target, int subs, boolean inverse, boolean toMany) {
         return (new PathJoinsImpl()).outerJoinRelation(name, fk, target, subs, inverse, toMany);
      }

      public Joins setVariable(String var) {
         return (Joins)(var == null ? this : (new PathJoinsImpl()).setVariable(var));
      }

      public Joins setSubselect(String alias) {
         return (Joins)(alias == null ? this : (new PathJoinsImpl()).setSubselect(alias));
      }
   }

   private static class Key {
      private final String _path;
      private final Object _key;

      public Key(String path, Object key) {
         this._path = path;
         this._key = key;
      }

      public int hashCode() {
         return this._path.hashCode() ^ this._key.hashCode();
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else if (other.getClass() != this.getClass()) {
            return false;
         } else {
            Key k = (Key)other;
            return k._path.equals(this._path) && k._key.equals(this._key);
         }
      }

      public String toString() {
         return this._path + "|" + this._key;
      }
   }

   private static class Placeholder {
      private Placeholder() {
      }

      // $FF: synthetic method
      Placeholder(Object x0) {
         this();
      }
   }

   private static class NullId {
      private NullId() {
      }

      // $FF: synthetic method
      NullId(Object x0) {
         this();
      }
   }
}
