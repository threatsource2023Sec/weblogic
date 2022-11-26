package org.apache.openjpa.jdbc.kernel;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.BitSet;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.SelectExecutor;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.util.InternalException;

public class PagingResultObjectProvider extends SelectResultObjectProvider {
   private final ClassMapping[] _mappings;
   private final Object[] _page;
   private final int[] _idxs;
   private final BitSet[] _paged;
   private int _pos;
   private int _pagePos;

   public static BitSet getPagedFields(Select sel, ClassMapping mapping, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode, long size) {
      if (size == Long.MAX_VALUE || !sel.getAutoDistinct()) {
         if (!sel.isLRS()) {
            return null;
         }

         if (fetch.getFetchBatchSize() < 0) {
            return null;
         }
      }

      eagerMode = Math.min(eagerMode, fetch.getEagerFetchMode());
      if (eagerMode != 2) {
         return null;
      } else {
         FieldMapping[] fms = mapping.getDefinedFieldMappings();
         BitSet paged = null;

         for(int i = 0; i < fms.length; ++i) {
            if (fetch.requiresFetch(fms[i]) == 1 && fms[i].supportsSelect(sel, 2, (OpenJPAStateManager)null, store, fetch) > 0 && (fms[i].isEagerSelectToMany() || fms[i].supportsSelect(sel, 1, (OpenJPAStateManager)null, store, fetch) == 0)) {
               if (paged == null) {
                  paged = new BitSet();
               }

               paged.set(fms[i].getIndex());
            }
         }

         return paged;
      }
   }

   public PagingResultObjectProvider(SelectExecutor sel, ClassMapping mapping, JDBCStore store, JDBCFetchConfiguration fetch, BitSet paged, long size) {
      this(sel, new ClassMapping[]{mapping}, store, fetch, new BitSet[]{paged}, size);
   }

   public PagingResultObjectProvider(SelectExecutor sel, ClassMapping[] mappings, JDBCStore store, JDBCFetchConfiguration fetch, BitSet[] paged, long size) {
      super(sel, store, fetch);
      this._pos = -1;
      this._pagePos = -1;
      this._mappings = mappings;
      this._paged = paged;
      if (size <= 1L) {
         throw new InternalException("size=" + size);
      } else {
         int batch = this.getFetchConfiguration().getFetchBatchSize();
         int pageSize;
         if (batch < 0) {
            pageSize = (int)size;
         } else {
            if (batch == 0) {
               batch = 50;
            }

            if (size <= (long)batch) {
               pageSize = (int)size;
            } else if (size <= (long)(batch * 2)) {
               if (size % 2L == 0L) {
                  pageSize = (int)(size / 2L);
               } else {
                  pageSize = (int)(size / 2L + 1L);
               }
            } else {
               pageSize = batch;
            }
         }

         this._page = new Object[pageSize];
         if (this._paged.length > 1) {
            this._idxs = new int[pageSize];
         } else {
            this._idxs = null;
         }

      }
   }

   public int getPageSize() {
      return this._page.length;
   }

   public void open() throws SQLException {
      super.open();
      this._pos = -1;
   }

   public boolean next() throws SQLException {
      ++this._pos;
      if (this.inPage()) {
         return this._page[this._pos - this._pagePos] != null;
      } else if (!super.next()) {
         this.setSize(this._pos);
         return false;
      } else {
         return true;
      }
   }

   public boolean absolute(int pos) throws SQLException {
      this._pos = pos;
      if (this.inPage()) {
         return this._page[this._pos - this._pagePos] != null;
      } else {
         return super.absolute(pos);
      }
   }

   public Object getResultObject() throws SQLException {
      if (!this.inPage()) {
         this.fillPage();
      }

      return this._page[this._pos - this._pagePos];
   }

   private boolean inPage() {
      return this._pagePos != -1 && this._pos >= this._pagePos && this._pos < this._pagePos + this._page.length;
   }

   private void fillPage() throws SQLException {
      Arrays.fill(this._page, (Object)null);
      JDBCStoreManager storeMgr = (JDBCStoreManager)this.getStore();

      for(int i = 0; i < this._page.length; ++i) {
         Result res = this.getResult();
         int idx = res.indexOf();
         if (this._idxs != null) {
            this._idxs[i] = idx;
         }

         ClassMapping mapping = res.getBaseMapping();
         if (mapping == null) {
            mapping = this._mappings[idx];
         }

         this._page[i] = storeMgr.load(mapping, this.getFetchConfiguration(), this._paged[idx], res);
         if (i != this._page.length - 1 && !this.getResult().next()) {
            this.setSize(this._pos + i + 1);
            break;
         }
      }

      this._pagePos = this._pos;
      if (this._page[0] != null) {
         if (this._page.length > 1 && this._page[1] == null) {
            this.loadEagerFields();
         } else {
            this.executeEagerSelects();
         }
      }

   }

   private void loadEagerFields() throws SQLException {
      int idx = this._idxs == null ? 0 : this._idxs[0];
      if (this._paged[idx] != null) {
         JDBCStore store = this.getStore();
         OpenJPAStateManager sm = store.getContext().getStateManager(this._page[0]);
         int i = 0;

         for(int len = this._paged[idx].length(); i < len; ++i) {
            if (this._paged[idx].get(i)) {
               this._mappings[idx].getFieldMapping(i).load(sm, store, this.getFetchConfiguration());
            }
         }

      }
   }

   private void executeEagerSelects() throws SQLException {
      if (this._idxs == null) {
         this.executeEagerSelects(this._mappings[0], this._paged[0], 0, this._page.length);
      } else {
         int start = 0;
         int idx = this._idxs[0];

         int pos;
         for(pos = 0; pos < this._page.length && this._page[pos] != null; ++pos) {
            if (idx != this._idxs[pos]) {
               if (this._paged[idx] != null) {
                  this.executeEagerSelects(this._mappings[idx], this._paged[idx], start, pos);
               }

               start = pos;
               idx = this._idxs[pos];
            }
         }

         if (start < pos && this._paged[idx] != null) {
            this.executeEagerSelects(this._mappings[idx], this._paged[idx], start, pos);
         }

      }
   }

   private void executeEagerSelects(ClassMapping mapping, BitSet paged, int start, int end) throws SQLException {
      if (mapping != null) {
         this.executeEagerSelects(mapping.getJoinablePCSuperclassMapping(), paged, start, end);
         FieldMapping[] fms = mapping.getDefinedFieldMappings();
         int sels = 0;

         for(int i = 0; i < fms.length; ++i) {
            if (paged.get(fms[i].getIndex())) {
               ++sels;
            }
         }

         if (sels != 0) {
            JDBCStore store = this.getStore();
            Select sel = store.getSQLFactory().newSelect();
            DBDictionary dict = store.getDBDictionary();
            SQLBuffer buf = new SQLBuffer(dict);
            Column[] pks = mapping.getPrimaryKeyColumns();
            if (pks.length == 1) {
               this.createInContains(sel, dict, buf, mapping, pks, start, end);
            } else {
               this.orContains(sel, buf, mapping, pks, start, end);
            }

            sel.where(buf);
            StoreContext ctx = store.getContext();
            JDBCFetchConfiguration fetch = this.getFetchConfiguration();
            int esels = 0;

            for(int i = 0; i < fms.length; ++i) {
               if (paged.get(fms[i].getIndex())) {
                  int unions = fms[i].supportsSelect(sel, 2, (OpenJPAStateManager)null, store, fetch);
                  if (unions != 0) {
                     ++esels;
                     Object esel;
                     if (esels >= sels && unions <= 1) {
                        esel = sel;
                     } else {
                        esel = sel.whereClone(unions);
                     }

                     fms[i].selectEagerParallel((SelectExecutor)esel, (OpenJPAStateManager)null, store, fetch, 2);
                     Object res = ((SelectExecutor)esel).execute(store, fetch);

                     try {
                        for(int j = start; j < end && this._page[j] != null; ++j) {
                           res = fms[i].loadEagerParallel(ctx.getStateManager(this._page[j]), store, fetch, res);
                        }
                     } finally {
                        if (res instanceof Closeable) {
                           try {
                              ((Closeable)res).close();
                           } catch (Exception var25) {
                           }
                        }

                     }
                  }
               }
            }

         }
      }
   }

   private void createInContains(Select sel, DBDictionary dict, SQLBuffer buf, ClassMapping mapping, Column[] pks, int start, int end) {
      int inClauseLimit = dict.inClauseLimit;
      if (inClauseLimit > 0 && end - start > inClauseLimit) {
         buf.append("(");

         int high;
         for(int low = start; low < end; low = high) {
            if (low > start) {
               buf.append(" OR ");
            }

            high = Math.min(low + inClauseLimit, end);
            this.inContains(sel, buf, mapping, pks, low, high);
         }

         buf.append(")");
      } else {
         this.inContains(sel, buf, mapping, pks, start, end);
      }

   }

   private void inContains(Select sel, SQLBuffer buf, ClassMapping mapping, Column[] pks, int start, int end) {
      buf.append(sel.getColumnAlias(pks[0])).append(" IN (");

      for(int i = start; i < end && this._page[i] != null; ++i) {
         if (i > start) {
            buf.append(", ");
         }

         buf.appendValue(mapping.toDataStoreValue(this._page[i], pks, this.getStore()), pks[0]);
      }

      buf.append(")");
   }

   private void orContains(Select sel, SQLBuffer buf, ClassMapping mapping, Column[] pks, int start, int end) {
      String[] aliases = new String[pks.length];

      for(int i = 0; i < pks.length; ++i) {
         aliases[i] = sel.getColumnAlias(pks[i]);
      }

      buf.append("(");

      for(int i = start; i < end && this._page[i] != null; ++i) {
         if (i > start) {
            buf.append(" OR ");
         }

         Object[] vals = (Object[])((Object[])mapping.toDataStoreValue(this._page[i], pks, this.getStore()));
         buf.append("(");

         for(int j = 0; j < vals.length; ++j) {
            if (j > 0) {
               buf.append(" AND ");
            }

            buf.append(aliases[j]);
            if (vals[j] == null) {
               buf.append(" IS ");
            } else {
               buf.append(" = ");
            }

            buf.appendValue(vals[j], pks[j]);
         }

         buf.append(")");
      }

      buf.append(")");
   }
}
