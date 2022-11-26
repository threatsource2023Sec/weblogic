package kodo.jdbc.sql;

import com.solarmetric.jdbc.SQLFormatter;
import com.solarmetric.profile.MethodEnterEvent;
import com.solarmetric.profile.MethodExitEvent;
import com.solarmetric.profile.MethodInfoImpl;
import com.solarmetric.profile.ProfilingAgent;
import com.solarmetric.profile.ProfilingAgentProvider;
import com.solarmetric.profile.ProfilingCapable;
import com.solarmetric.profile.ProfilingEnvironment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.LogicalUnion;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.SelectExecutor;
import org.apache.openjpa.jdbc.sql.SelectImpl;
import org.apache.openjpa.jdbc.sql.Union;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class TrueUnion extends LogicalUnion implements ProfilingCapable {
   private static final Object POS_ID = new Object();
   private static final Localizer _loc = Localizer.forPackage(TrueUnion.class);
   private final Log _log;
   private final AdvancedSQL _advanced;
   private boolean _union;
   private List _compatSelects;
   private List _compatOrders;
   private String _order;

   public TrueUnion(JDBCConfiguration conf, int selects) {
      this(conf, selects, (Select[])null);
   }

   public TrueUnion(JDBCConfiguration conf, Select[] seeds) {
      this(conf, seeds.length, seeds);
   }

   private TrueUnion(JDBCConfiguration conf, int selects, Select[] seeds) {
      super(conf, selects, seeds);
      this._union = true;
      this._compatSelects = null;
      this._compatOrders = null;
      this._order = null;
      this._log = conf.getLog("openjpa.Runtime");
      this._advanced = ((KodoSQLFactory)conf.getSQLFactoryInstance()).getAdvancedSQL();
      this._union = selects > 1 && this._advanced.getSupportsUnion();

      for(int i = 0; i < this.sels.length; ++i) {
         ((TrueUnionSelect)this.sels[i]).selectPosition();
      }

   }

   protected LogicalUnion.UnionSelect newUnionSelect(SelectImpl seed, int pos) {
      return new TrueUnionSelect(seed, pos);
   }

   public boolean isUnion() {
      return this._union;
   }

   public void abortUnion() {
      this.abortUnion((String)null);
   }

   private void abortUnion(String reasonKey) {
      if (this._union) {
         this._union = false;
         this._compatSelects = null;
         this._compatOrders = null;
         this._order = null;

         for(int i = 0; i < this.sels.length; ++i) {
            ((TrueUnionSelect)this.sels[i]).clearUnionState();
         }

         if (reasonKey != null && this._log.isTraceEnabled()) {
            this._log.trace(_loc.get(reasonKey));
         }

      }
   }

   public String getOrdering() {
      return this._order;
   }

   public SQLBuffer toSelect(boolean forUpdate, JDBCFetchConfiguration fetch) {
      return !this._union ? super.toSelect(forUpdate, fetch) : this._advanced.toSelect(this, forUpdate, fetch);
   }

   public SQLBuffer toSelectCount() {
      return !this._union ? super.toSelectCount() : this._advanced.toSelectCount(this);
   }

   public boolean supportsRandomAccess(boolean forUpdate) {
      if (!this._union) {
         return super.supportsRandomAccess(forUpdate);
      } else {
         for(int i = 0; i < this.sels.length; ++i) {
            if (!this.sels[i].supportsRandomAccess(forUpdate)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean supportsLocking() {
      if (!this._union) {
         return super.supportsLocking();
      } else {
         for(int i = 0; i < this.sels.length; ++i) {
            if (!this.sels[i].supportsLocking()) {
               return false;
            }
         }

         return true;
      }
   }

   public int getCount(JDBCStore store) throws SQLException {
      if (!this._union) {
         return super.getCount(store);
      } else {
         Connection conn = null;
         PreparedStatement stmnt = null;
         ResultSet rs = null;

         try {
            SQLBuffer sql = this.toSelectCount();
            conn = store.getConnection();
            stmnt = sql.prepareStatement(conn);
            rs = stmnt.executeQuery();

            int count;
            for(count = 0; rs.next(); count += rs.getInt(1)) {
            }

            int var7 = count;
            return var7;
         } finally {
            if (rs != null) {
               try {
                  rs.close();
               } catch (SQLException var20) {
               }
            }

            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var19) {
               }
            }

            if (conn != null) {
               try {
                  conn.close();
               } catch (SQLException var18) {
               }
            }

         }
      }
   }

   public Result execute(JDBCStore store, JDBCFetchConfiguration fetch, int lockLevel) throws SQLException {
      return !this._union ? super.execute(store, fetch, lockLevel) : execute(this, store.getContext(), store, fetch, lockLevel);
   }

   private static Result execute(TrueUnion union, StoreContext var1, JDBCStore store, JDBCFetchConfiguration fetch, int var4) throws SQLException {
      MethodInfoImpl var12 = null;
      ProfilingAgent var13 = null;
      if (var1 instanceof ProfilingAgentProvider) {
         var13 = ((ProfilingAgentProvider)var1).getProfilingAgent();
         if (var13 != null) {
            String var14 = "" + (new SQLFormatter()).prettyPrint(union);
            var12 = new MethodInfoImpl("Select.select()", var14);
            var12.setCategory("SQL");
            var13.handleEvent(new MethodEnterEvent((ProfilingEnvironment)var1, var12));
         }
      }

      UnionSelectResult var17;
      try {
         if (fetch == null) {
            fetch = store.getFetchConfiguration();
         }

         SQLBuffer sql = union.toSelect(false, fetch);
         int rsType = union.isLRS() && union.supportsRandomAccess(false) ? -1 : 1003;
         Connection conn = store.getConnection();
         PreparedStatement stmnt = null;
         ResultSet rs = null;

         try {
            if (union.isLRS()) {
               stmnt = sql.prepareStatement(conn, fetch, rsType, -1);
            } else {
               stmnt = sql.prepareStatement(conn, rsType, -1);
            }

            rs = stmnt.executeQuery();
         } catch (SQLException var24) {
            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var23) {
               }
            }

            try {
               conn.close();
            } catch (SQLException var22) {
            }

            throw var24;
         }

         UnionSelectResult res = new UnionSelectResult(union, conn, stmnt, rs, fetch);
         res.setStore(store);
         var17 = res;
      } finally {
         if (var13 != null) {
            var13.handleEvent(new MethodExitEvent((ProfilingEnvironment)var1, var12));
         }

      }

      return var17;
   }

   public void select(Union.Selector selector) {
      super.select(selector);
      if (this._union) {
         int i;
         for(i = 0; this._union && i < this.sels.length; ++i) {
            ((TrueUnionSelect)this.sels[i]).selectPreviousPlaceholders();
         }

         if (this._union && this._compatOrders != null) {
            for(i = 0; i < this.sels.length; ++i) {
               this.sels[i].clearOrdering();
            }

            StringBuffer buf = new StringBuffer();

            for(int i = 0; i < this._compatOrders.size(); ++i) {
               if (buf.length() > 0) {
                  buf.append(", ");
               }

               buf.append(SelectImpl.toOrderAlias(i));
               if (this.desc.get(i)) {
                  buf.append(" DESC");
               } else {
                  buf.append(" ASC");
               }
            }

            this._order = buf.toString();
         }

      }
   }

   private static class UnionSelectResult extends SelectImpl.SelectResult {
      private final LogicalUnion.UnionSelect[] _sels;
      private final ClassMapping[] _mappings;
      private final JDBCFetchConfiguration _fetch;
      private Map[] _eager;

      public UnionSelectResult(TrueUnion union, Connection conn, Statement stmnt, ResultSet rs, JDBCFetchConfiguration fetch) {
         super(conn, stmnt, rs, union.getDBDictionary());
         this._sels = union.sels;
         this._mappings = union.mappings;
         this._fetch = fetch;
         this.setSelect(((TrueUnionSelect)this._sels[0]).getDelegate());
      }

      protected boolean absoluteInternal(int row) throws SQLException {
         if (!super.absoluteInternal(row)) {
            return false;
         } else {
            this.updateSelect();
            return true;
         }
      }

      protected boolean nextInternal() throws SQLException {
         if (!super.nextInternal()) {
            return false;
         } else {
            this.updateSelect();
            return true;
         }
      }

      public void close() {
         this.setEagerMap((Map)null);
         super.close();
         if (this._eager != null) {
            for(int i = 0; i < this._eager.length; ++i) {
               if (this._eager[i] != null) {
                  this.closeEagerMap(this._eager[i]);
               }
            }
         }

      }

      private void updateSelect() throws SQLException {
         int idx = this.getInt(TrueUnion.POS_ID);
         SelectImpl sel = ((TrueUnionSelect)this._sels[idx]).getDelegate();
         this.setSelect(sel);
         this.setBaseMapping(this._mappings[idx]);
         this.setIndexOf(idx);
         if (sel.getEagerMap() != null) {
            if (this._eager == null) {
               this._eager = new Map[this._sels.length];
            }

            if (this._eager[idx] == null) {
               this._eager[idx] = this.executeEagerSelects(sel);
            }

            this.setEagerMap(this._eager[idx]);
         }

      }

      private Map executeEagerSelects(SelectImpl sel) throws SQLException {
         Map eagerSels = sel.getEagerMap();
         Map eagerRes = new HashMap(eagerSels);
         Iterator itr = eagerRes.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            if (entry.getValue() == sel) {
               entry.setValue(this);
            } else {
               entry.setValue(((SelectExecutor)entry.getValue()).execute(this.getStore(), this._fetch));
            }
         }

         return eagerRes;
      }
   }

   private class TrueUnionSelect extends LogicalUnion.UnionSelect {
      private int _selIdx = 0;

      public TrueUnionSelect(SelectImpl sel, int pos) {
         super(TrueUnion.this, sel, pos);
      }

      public void selectPosition() {
         if (TrueUnion.this._union) {
            this.sel.select(String.valueOf(this.pos), TrueUnion.POS_ID);
         }

      }

      public void clearUnionState() {
         this.sel.clearPlaceholderSelects();
      }

      public void selectPreviousPlaceholders() {
         if (TrueUnion.this._compatSelects != null) {
            if (TrueUnion.this._compatOrders != null && this.orders < TrueUnion.this._compatOrders.size()) {
               TrueUnion.this.abortUnion("union-number-ordering");
            }

            if (TrueUnion.this._union && !TrueUnion.this._advanced.getSupportsUnionWithUnalignedOrdering() && this.pos > 0 && !ObjectUtils.equals(TrueUnion.this.sels[this.pos - 1].getSelectedOrderIndexes(), this.getSelectedOrderIndexes())) {
               TrueUnion.this.abortUnion("union-unaligned-ordering");
            }

            for(int i = this._selIdx; TrueUnion.this._union && i < TrueUnion.this._compatSelects.size(); ++i) {
               if (!(TrueUnion.this._compatSelects.get(i) instanceof Column)) {
                  TrueUnion.this.abortUnion("union-sql-placeholder");
               } else {
                  this.selectPlaceholder(TrueUnion.this.dict.getPlaceholderValueString((Column)TrueUnion.this._compatSelects.get(i)));
               }
            }

         }
      }

      public void clearSelects() {
         super.clearSelects();
         this._selIdx = 0;
      }

      public boolean select(SQLBuffer sql, Object id) {
         return this.select((SQLBuffer)sql, id, (Joins)null);
      }

      public boolean select(SQLBuffer sql, Object id, Joins joins) {
         if (!super.select(sql, id, joins)) {
            return false;
         } else {
            this.recordSQL(sql.getSQL(false));
            return true;
         }
      }

      public boolean select(String sql, Object id) {
         return this.select((String)sql, id, (Joins)null);
      }

      public boolean select(String sql, Object id, Joins joins) {
         if (!super.select(sql, id, joins)) {
            return false;
         } else {
            this.recordSQL(sql);
            return true;
         }
      }

      public boolean select(Column col) {
         return this.select((Column)col, (Joins)null);
      }

      public boolean select(Column col, Joins joins) {
         if (!super.select(col, joins)) {
            return false;
         } else {
            this.recordColumn(col, -1);
            return true;
         }
      }

      private void recordSQL(String sql) {
         if (TrueUnion.this._union) {
            if (TrueUnion.this._compatSelects == null) {
               TrueUnion.this._compatSelects = new ArrayList();
            }

            while(TrueUnion.this._union && this._selIdx < TrueUnion.this._compatSelects.size()) {
               if (!(TrueUnion.this._compatSelects.get(this._selIdx) instanceof Column)) {
                  ++this._selIdx;
                  return;
               }

               this.insertPlaceholder((Column)TrueUnion.this._compatSelects.get(this._selIdx), -1);
               ++this._selIdx;
            }

            if (this.pos > 0) {
               TrueUnion.this.abortUnion("union-sql-placeholder");
            } else if (TrueUnion.this._union) {
               TrueUnion.this._compatSelects.add(sql);
               ++this._selIdx;
            }

         }
      }

      private void insertPlaceholder(Column col, int pos) {
         this.sel.insertPlaceholder(TrueUnion.this.dict.getPlaceholderValueString(col), pos);
      }

      private void recordColumn(Column col, int pos) {
         if (TrueUnion.this._union) {
            if (TrueUnion.this._compatSelects == null) {
               TrueUnion.this._compatSelects = new ArrayList();
            }

            for(; TrueUnion.this._union && this._selIdx < TrueUnion.this._compatSelects.size(); ++this._selIdx) {
               if (!(TrueUnion.this._compatSelects.get(this._selIdx) instanceof Column)) {
                  TrueUnion.this.abortUnion("union-sql-placeholder");
               } else {
                  Column seld = (Column)TrueUnion.this._compatSelects.get(this._selIdx);
                  if (col.getType() == seld.getType() && col.getSize() <= seld.getSize()) {
                     ++this._selIdx;
                     return;
                  }

                  this.insertPlaceholder(seld, pos);
               }
            }

            if (TrueUnion.this._union) {
               TrueUnion.this._compatSelects.add(col);
               ++this._selIdx;
            }

         }
      }

      private void recordColumns(Column[] cols, int idxs) {
         if (TrueUnion.this._union && cols != null) {
            int count = 0;

            int i;
            for(i = 0; i < cols.length; ++i) {
               if (idxs == -1 || (idxs & 2 << i) != 0) {
                  ++count;
               }
            }

            i = 0;

            for(int col = 0; i < cols.length; ++i) {
               if (idxs == -1 || (idxs & 2 << i) != 0) {
                  this.recordColumn(cols[i], -(count - col++));
               }
            }

         }
      }

      public int select(Column[] cols) {
         return this.select((Column[])cols, (Joins)null);
      }

      public int select(Column[] cols, Joins joins) {
         int idxs = super.select(cols, joins);
         this.recordColumns(cols, idxs);
         return idxs;
      }

      public boolean selectIdentifier(Column col) {
         return this.selectIdentifier((Column)col, (Joins)null);
      }

      public boolean selectIdentifier(Column col, Joins joins) {
         if (!super.selectIdentifier(col, joins)) {
            return false;
         } else {
            this.recordColumn(col, -1);
            return true;
         }
      }

      public int selectIdentifier(Column[] cols) {
         return this.selectIdentifier((Column[])cols, (Joins)null);
      }

      public int selectIdentifier(Column[] cols, Joins joins) {
         int idxs = super.selectIdentifier(cols, joins);
         this.recordColumns(cols, idxs);
         return idxs;
      }

      public int selectPrimaryKey(ClassMapping mapping) {
         return this.selectPrimaryKey(mapping, (Joins)null);
      }

      public int selectPrimaryKey(ClassMapping mapping, Joins joins) {
         Column[] cols = null;
         if (TrueUnion.this._union) {
            ClassMapping pks;
            for(pks = mapping; !pks.isPrimaryKeyObjectId(true); pks = pks.getJoinablePCSuperclassMapping()) {
            }

            cols = pks.getPrimaryKeyColumns();
         }

         int idxs = super.selectPrimaryKey(mapping, joins);
         this.recordColumns(cols, idxs);
         return idxs;
      }

      public int orderByPrimaryKey(ClassMapping mapping, boolean asc, boolean select) {
         return this.orderByPrimaryKey(mapping, asc, (Joins)null, select);
      }

      public int orderByPrimaryKey(ClassMapping mapping, boolean asc, Joins joins, boolean select) {
         ClassMapping pks;
         for(pks = mapping; !pks.isPrimaryKeyObjectId(true); pks = pks.getJoinablePCSuperclassMapping()) {
         }

         Column[] cols = pks.getPrimaryKeyColumns();
         this.recordOrderColumns(cols, asc);
         int seld = this.sel.orderByPrimaryKey(mapping, asc, joins, select, TrueUnion.this._union);
         this.recordColumns(cols, seld);
         return seld;
      }

      protected void recordOrder(Object ord, boolean asc) {
         if (ord != null) {
            if (!TrueUnion.this._union) {
               super.recordOrder(ord, asc);
            } else {
               this.orderIdxs = null;
               if (TrueUnion.this._compatOrders == null) {
                  TrueUnion.this._compatOrders = new ArrayList(5);
               }

               int idx = this.orders++;
               if (idx == TrueUnion.this._compatOrders.size()) {
                  TrueUnion.this._compatOrders.add(ord);
                  if (!asc) {
                     TrueUnion.this.desc.set(idx);
                  }
               } else if (TrueUnion.this.desc.get(idx) != asc && ord instanceof Column && TrueUnion.this._compatOrders.get(idx) instanceof Column) {
                  Column col = (Column)ord;
                  Column prev = (Column)TrueUnion.this._compatOrders.get(idx);
                  if (col.getType() != prev.getType() || col.getSize() > prev.getSize()) {
                     TrueUnion.this.abortUnion("union-incompat-ordering");
                  }
               } else if (TrueUnion.this.desc.get(idx) == asc || !(ord instanceof String) || !(TrueUnion.this._compatOrders.get(idx) instanceof String)) {
                  TrueUnion.this.abortUnion("union-incompat-ordering");
               }

            }
         }
      }

      public boolean orderBy(Column col, boolean asc, boolean select) {
         return this.orderBy((Column)col, asc, (Joins)null, select);
      }

      public boolean orderBy(Column col, boolean asc, Joins joins, boolean select) {
         if (!super.orderBy(col, asc, joins, select)) {
            return false;
         } else {
            this.recordColumn(col, -1);
            return true;
         }
      }

      public int orderBy(Column[] cols, boolean asc, boolean select) {
         return this.orderBy((Column[])cols, asc, (Joins)null, select);
      }

      public int orderBy(Column[] cols, boolean asc, Joins joins, boolean select) {
         int seld = super.orderBy(cols, asc, joins, select);
         this.recordColumns(cols, seld);
         return seld;
      }

      public boolean orderBy(SQLBuffer sql, boolean asc, boolean select) {
         return this.orderBy((SQLBuffer)sql, asc, (Joins)null, select);
      }

      public boolean orderBy(SQLBuffer sql, boolean asc, Joins joins, boolean select) {
         if (!super.orderBy(sql, asc, joins, select)) {
            return false;
         } else {
            this.recordSQL(sql.getSQL(false));
            return true;
         }
      }

      public boolean orderBy(String sql, boolean asc, boolean select) {
         return this.orderBy((String)sql, asc, (Joins)null, select);
      }

      public boolean orderBy(String sql, boolean asc, Joins joins, boolean select) {
         if (!super.orderBy(sql, asc, joins, select)) {
            return false;
         } else {
            this.recordSQL(sql);
            return true;
         }
      }
   }
}
