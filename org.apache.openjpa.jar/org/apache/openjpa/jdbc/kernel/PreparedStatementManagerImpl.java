package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.RowImpl;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.OptimisticException;

public class PreparedStatementManagerImpl implements PreparedStatementManager {
   private static final Localizer _loc = Localizer.forPackage(PreparedStatementManagerImpl.class);
   protected final JDBCStore _store;
   protected final Connection _conn;
   protected final DBDictionary _dict;
   protected final Collection _exceptions = new LinkedList();

   public PreparedStatementManagerImpl(JDBCStore store, Connection conn) {
      this._store = store;
      this._dict = store.getDBDictionary();
      this._conn = conn;
   }

   public Collection getExceptions() {
      return this._exceptions;
   }

   public void flush(RowImpl row) {
      try {
         this.flushInternal(row);
      } catch (SQLException var3) {
         this._exceptions.add(SQLExceptions.getStore(var3, this._dict));
      } catch (OpenJPAException var4) {
         this._exceptions.add(var4);
      }

   }

   protected void flushInternal(RowImpl row) throws SQLException {
      Column[] autoAssign = null;
      if (row.getAction() == 1) {
         autoAssign = row.getTable().getAutoAssignedColumns();
      }

      this.flushAndUpdate(row);
      if (autoAssign != null && autoAssign.length > 0 && row.getPrimaryKey() != null) {
         OpenJPAStateManager sm = row.getPrimaryKey();
         ClassMapping mapping = (ClassMapping)sm.getMetaData();

         for(int i = 0; i < autoAssign.length; ++i) {
            Object val = this._dict.getGeneratedKey(autoAssign[i], this._conn);
            mapping.assertJoinable(autoAssign[i]).setAutoAssignedValue(sm, this._store, autoAssign[i], val);
         }

         sm.setObjectId(ApplicationIds.create(sm.getPersistenceCapable(), mapping));
      }

   }

   protected void flushAndUpdate(RowImpl row) throws SQLException {
      String sql = row.getSQL(this._dict);
      PreparedStatement stmnt = this.prepareStatement(sql);
      if (stmnt != null) {
         row.flush(stmnt, this._dict, this._store);
      }

      try {
         int count = this.executeUpdate(stmnt, sql, row);
         if (count != 1) {
            Object failed = row.getFailedObject();
            if (failed != null) {
               this._exceptions.add(new OptimisticException(failed));
            } else if (row.getAction() == 1) {
               throw new SQLException(_loc.get("update-failed-no-failed-obj", String.valueOf(count), sql).getMessage());
            }
         }
      } catch (SQLException var13) {
         throw SQLExceptions.getStore(var13, row.getFailedObject(), this._dict);
      } finally {
         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var12) {
            }
         }

      }

   }

   public void flush() {
   }

   protected int executeUpdate(PreparedStatement stmnt, String sql, RowImpl row) throws SQLException {
      return stmnt.executeUpdate();
   }

   protected PreparedStatement prepareStatement(String sql) throws SQLException {
      return this._conn.prepareStatement(sql);
   }
}
