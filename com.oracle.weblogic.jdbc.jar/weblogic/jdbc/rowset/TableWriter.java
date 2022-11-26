package weblogic.jdbc.rowset;

import java.io.CharArrayReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.AssertionError;
import weblogic.utils.PlatformConstants;

public class TableWriter implements PlatformConstants {
   protected static final ColumnFilter ALL = new ColumnFilter() {
      public boolean include(int i) {
         return true;
      }
   };
   private final boolean verbose = false;
   protected final String tableName;
   protected final BitSet columnMask;
   private final WLRowSetInternal rowSet;
   protected final WLRowSetMetaData metaData;
   protected final int columnCount;
   private final boolean batchInserts;
   protected boolean batchDeletes;
   private final boolean batchUpdates;
   protected boolean groupDeletes;
   protected final boolean verboseSQL;
   protected DebugLogger JDBCRowsetDebug = null;
   private final Map batchMap;
   private List groupDeleteList;
   private String __dont_touch_me_insert_statement;
   private String __dont_touch_me_update_header;
   private String __dont_touch_me_delete_header;
   private final int groupDeleteSize;
   protected boolean checkBatchUpdateCounts;
   protected List batchVerifyParams;
   protected final int batchVerifySize;

   public TableWriter(WLRowSetInternal rowSet, String tableName, BitSet columnMask) throws SQLException {
      this.groupDeleteList = Collections.EMPTY_LIST;
      this.__dont_touch_me_insert_statement = null;
      this.__dont_touch_me_update_header = null;
      this.__dont_touch_me_delete_header = null;
      this.checkBatchUpdateCounts = true;
      this.batchVerifyParams = Collections.EMPTY_LIST;
      this.rowSet = rowSet;
      this.tableName = tableName;
      this.columnMask = columnMask;
      this.metaData = (WLRowSetMetaData)rowSet.getMetaData();
      this.columnCount = this.metaData.getColumnCount();
      this.batchInserts = this.metaData.getBatchInserts();
      this.batchDeletes = this.metaData.getBatchDeletes();
      this.batchUpdates = this.metaData.getBatchUpdates();
      this.groupDeletes = this.metaData.getGroupDeletes();
      this.verboseSQL = this.metaData.getVerboseSQL();
      this.groupDeleteSize = this.metaData.getGroupDeleteSize();
      this.batchVerifySize = this.metaData.getBatchVerifySize();
      if (this.verboseSQL && this.JDBCRowsetDebug == null) {
         this.JDBCRowsetDebug = DebugLogger.createUnregisteredDebugLogger("JDBCRowset", true);
      }

      if (!this.batchInserts && !this.batchDeletes && !this.batchUpdates) {
         this.batchMap = Collections.EMPTY_MAP;
      } else {
         this.batchMap = new HashMap();
      }

   }

   public void issueSQL(Connection con) throws SQLException {
      if (this.groupDeletes) {
         this.groupDeleteList = new ArrayList();
      } else {
         this.groupDeleteList = Collections.EMPTY_LIST;
      }

      if (!this.checkBatchUpdateCounts) {
         this.batchVerifyParams = new ArrayList();
      }

      try {
         Iterator it = this.rowSet.getCachedRows().iterator();

         while(true) {
            while(true) {
               CachedRow row;
               do {
                  if (!it.hasNext()) {
                     this.executeGroupDeletes(con);
                     this.executeBatchVerifySelects(con);
                     this.executeBatches();
                     this.updateLOBs(con);
                     return;
                  }

                  row = (CachedRow)it.next();
               } while(row.isInsertRow() && row.isDeletedRow());

               if (row.isInsertRow() && row.isUpdatedRow()) {
                  this.updateRow(con, row);
               } else if (row.isInsertRow()) {
                  this.insertRow(con, row);
               } else if (row.isDeletedRow()) {
                  this.deleteRow(con, row);
               } else if (row.isUpdatedRow()) {
                  this.updateRow(con, row);
               }
            }
         }
      } finally {
         this.cleanupBatches();
      }
   }

   protected void insertRow(Connection con, CachedRow row) throws SQLException {
      Object[] cols = row.getColumns();
      if (this.verboseSQL) {
         this.printSQL(this.getInsertStatement(), cols, new AutoIncFilter(this.metaData));
      }

      PreparedStatement ps;
      if (this.batchInserts) {
         ps = this.getBatchPS(con, this.getInsertStatement());
         this.setInsertParameters(con, ps, cols);
         ps.addBatch();
      } else {
         ps = null;

         try {
            ps = con.prepareStatement(this.getInsertStatement());
            this.setInsertParameters(con, ps, cols);
            if (ps.executeUpdate() == 0) {
               this.throwOCE(this.formatSQL(this.getInsertStatement(), cols, new AutoIncFilter(this.metaData)), row);
            }
         } finally {
            if (ps != null) {
               try {
                  ps.close();
               } catch (Exception var11) {
               }
            }

         }
      }

   }

   protected BitSet getModifiedColumns(CachedRow row) {
      return row.getModifiedColumns();
   }

   protected Object insertedObject(Connection con, Object o) {
      return o;
   }

   protected Object updatedObject(Object o) {
      return o;
   }

   protected void executeBatchVerifySelects(Connection con) throws SQLException {
   }

   protected void updateLOBs(Connection con) throws SQLException {
   }

   private void printSQL(String sql) {
      this.JDBCRowsetDebug.debug(sql);
   }

   private void printSQL(String sql, Object[] setCols, ColumnFilter setFilter, Object[] whereCols, ColumnFilter whereFilter) throws SQLException {
      this.JDBCRowsetDebug.debug(this.formatSQL(sql, setCols, setFilter, whereCols, whereFilter));
   }

   private String formatSQL(String sql, Object[] setCols, ColumnFilter setFilter, Object[] whereCols, ColumnFilter whereFilter) throws SQLException {
      StringBuffer sb = new StringBuffer();
      sb.append(sql);
      sb.append(EOL);
      sb.append("PARAMETERS: ");
      String sep = "";

      int i;
      for(i = 0; i < setCols.length; ++i) {
         if (this.columnMask.get(i) && setFilter.include(i) && !this.metaData.isAutoIncrement(i + 1)) {
            sb.append(sep);
            sep = ", ";
            if (setCols[i] == null) {
               sb.append("<NULL>");
            } else {
               sb.append(setCols[i].toString());
            }
         }
      }

      for(i = 0; i < whereCols.length; ++i) {
         if (this.columnMask.get(i) && whereFilter.include(i) && !this.isLOB(i) && whereCols[i] != null) {
            sb.append(sep);
            sep = ", ";
            sb.append(whereCols[i].toString());
         }
      }

      return sb.toString();
   }

   protected void printSQL(String sql, Object[] params, ColumnFilter filter) throws SQLException {
      this.JDBCRowsetDebug.debug(this.formatSQL(sql, params, filter));
   }

   private String formatSQL(String sql, Object[] params, ColumnFilter filter) throws SQLException {
      StringBuffer sb = new StringBuffer();
      sb.append(sql);
      sb.append(EOL);
      if (params != null && params.length > 0) {
         sb.append("PARAMETERS: ");
         String sep = "";

         for(int i = 0; i < params.length; ++i) {
            if (this.columnMask.get(i) && filter.include(i)) {
               sb.append(sep);
               sep = ", ";
               if (params[i] == null) {
                  sb.append("<NULL>");
               } else {
                  sb.append(params[i].toString());
               }
            }
         }
      }

      return sb.toString();
   }

   protected void printSQL(String sql, Object[] params) throws SQLException {
      StringBuffer sb = new StringBuffer();
      sb.append(sql);
      sb.append(EOL);
      if (params != null && params.length > 0) {
         sb.append("PARAMETERS: ");
         String sep = "";

         for(int i = 0; i < params.length; ++i) {
            sb.append(sep);
            sep = ", ";
            if (params[i] == null) {
               sb.append("<NULL>");
            } else {
               sb.append(params[i].toString());
            }
         }
      }

      this.JDBCRowsetDebug.debug(sb.toString());
   }

   private void executeGroupDeletes(Connection con) throws SQLException {
      if (!this.groupDeleteList.isEmpty()) {
         int start = 0;

         do {
            this.executeGroupDeletes(con, start);
            start += this.groupDeleteSize;
         } while(start < this.groupDeleteList.size());

      }
   }

   private void executeGroupDeletes(Connection con, int start) throws SQLException {
      StringBuffer sb = new StringBuffer(500);
      sb.append(this.getDeleteHeader()).append("WHERE ");
      String sep = "";
      int stop = Math.min(start + this.groupDeleteSize, this.groupDeleteList.size());

      for(int i = start; i < stop; ++i) {
         sb.append(sep);
         sep = " OR ";
         GroupDelete d = (GroupDelete)this.groupDeleteList.get(i);
         sb.append(d.getWhereClause());
      }

      String sql = sb.toString();
      if (this.verboseSQL) {
         this.printSQL(sql);
      }

      PreparedStatement ps = null;

      try {
         ps = con.prepareStatement(sql);
         int param = 1;

         for(int i = start; i < stop; ++i) {
            GroupDelete d = (GroupDelete)this.groupDeleteList.get(i);
            param = this.setWhereParameters(ps, d.getCols(), d.getFilter(), param);
         }

         if (ps.executeUpdate() != stop - start) {
            this.throwOCE(sql, (CachedRow)null);
         }
      } finally {
         if (ps != null) {
            try {
               ps.close();
            } catch (Exception var16) {
            }
         }

      }

   }

   protected void throwOCE(String sql, CachedRow row) throws OptimisticConflictException {
      Connection c = null;

      try {
         if (!this.metaData.haveSetPKColumns()) {
            throw new OptimisticConflictException("You must use the WLRowSetMetaData.setPrimaryKeyColumn() method to establish primary key columns before updating rows.");
         }

         StringBuffer sb = new StringBuffer(500);
         String sep = "";

         for(int i = 0; i < this.columnCount; ++i) {
            if (this.metaData.isPrimaryKeyColumn(i + 1) && this.tableName.equals(this.metaData.getQualifiedTableName(i + 1))) {
               sb.append(sep).append(this.metaData.getWriteColumnName(i + 1));
               sb.append(" = ?");
               sep = " AND ";
            }
         }

         String whereClause = sb.toString();
         if ("".equals(whereClause)) {
            throw new OptimisticConflictException("You must use the WLRowSetMetaData.setPrimaryKeyColumn() method to establish primary key columns for table " + this.tableName);
         }

         sb = new StringBuffer(500);
         sep = "";
         int[] idx = new int[this.columnCount];
         int j = 0;

         for(int i = 0; i < this.columnCount; ++i) {
            if (this.tableName.equals(this.metaData.getQualifiedTableName(i + 1))) {
               sb.append(sep).append(this.metaData.getWriteColumnName(i + 1));
               idx[j++] = i;
               sep = ", ";
            }
         }

         if (j < this.columnCount) {
            idx[j] = -1;
         }

         String selectClause = "SELECT " + sb.toString() + " FROM " + this.tableName + " WHERE " + whereClause;
         if (this.verboseSQL) {
            this.printSQL(selectClause);
         }

         c = this.rowSet.getConnection();
         PreparedStatement ps = c.prepareStatement(selectClause);
         int i;
         if (row == null) {
            Iterator it = this.rowSet.getCachedRows().iterator();

            label145:
            while(true) {
               while(true) {
                  do {
                     if (!it.hasNext()) {
                        break label145;
                     }

                     row = (CachedRow)it.next();
                  } while(!row.isInsertRow() && !row.isDeletedRow() && !row.isUpdatedRow());

                  i = 1;

                  for(int i = 1; i <= this.columnCount; ++i) {
                     if (this.metaData.isPrimaryKeyColumn(i) && this.tableName.equals(this.metaData.getQualifiedTableName(i))) {
                        this.setObject(ps, i++, i - 1, row.getColumn(i));
                     }
                  }

                  ResultSet rs = ps.executeQuery();
                  if (rs != null && rs.next()) {
                     if (row.setConflictValue(rs, idx) && (!row.isInsertRow() || !row.isDeletedRow())) {
                        if (row.isDeletedRow()) {
                           row.setStatus(1);
                        } else if (row.isUpdatedRow()) {
                           row.setStatus(0);
                        } else if (row.isInsertRow()) {
                           row.setStatus(2);
                        }
                     }

                     rs.close();
                  } else if (!row.isInsertRow() || !row.isDeletedRow()) {
                     if (row.isUpdatedRow()) {
                        row.setStatus(0);
                     } else if (row.isInsertRow()) {
                        row.setStatus(2);
                     }
                  }
               }
            }
         } else {
            int paramNum = 1;

            for(i = 1; i <= this.columnCount; ++i) {
               if (this.metaData.isPrimaryKeyColumn(i) && this.tableName.equals(this.metaData.getQualifiedTableName(i))) {
                  this.setObject(ps, paramNum++, i - 1, row.getColumn(i));
               }
            }

            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) {
               row.setConflictValue(rs, idx);
               rs.close();
            }

            if (!row.isInsertRow() || !row.isDeletedRow()) {
               if (row.isDeletedRow()) {
                  row.setStatus(1);
               } else if (row.isUpdatedRow()) {
                  row.setStatus(0);
               } else if (row.isInsertRow()) {
                  row.setStatus(2);
               }
            }
         }

         this.rowSet.beforeFirst();
      } catch (Exception var14) {
      }

      throw new OptimisticConflictException("Optimistic conflict when issuing sql: " + sql, (CachedRowSetImpl)this.rowSet);
   }

   private PreparedStatement getBatchPS(Connection con, String sql) throws SQLException {
      PreparedStatement ps = (PreparedStatement)this.batchMap.get(sql);
      if (ps == null) {
         ps = con.prepareStatement(sql);
         this.batchMap.put(sql, ps);
      }

      return ps;
   }

   private void executeBatches() throws SQLException {
      Iterator it = this.batchMap.keySet().iterator();

      while(it.hasNext()) {
         String sql = (String)it.next();
         PreparedStatement ps = (PreparedStatement)this.batchMap.get(sql);
         int[] updates = ps.executeBatch();
         if (updates == null) {
            throw new SQLException("The JDBC Driver returned null from PreparedStatement.executeBatch().  The executed SQL was: " + sql);
         }

         for(int i = 0; i < updates.length; ++i) {
            if (updates[i] == 0) {
               this.throwOCE(sql, (CachedRow)null);
            } else {
               if (this.checkBatchUpdateCounts && updates[i] == -2) {
                  throw new SQLException("The Batch update with SQL: " + sql + " returned Statement.SUCCESS_NO_INFO indicating the statement was successful, but we are unable to determine whether an Optimistic conflict occurred.");
               }

               if (updates[i] == -3) {
                  throw new SQLException("The Batch update with SQL: " + sql + " returned Statement.EXECUTE_FAILED indicating the statement execution failed without providing any additional information.");
               }
            }
         }
      }

   }

   private void cleanupBatches() {
      Iterator it = this.batchMap.values().iterator();

      while(it.hasNext()) {
         PreparedStatement ps = (PreparedStatement)it.next();

         try {
            ps.close();
         } catch (Exception var4) {
         }
      }

      this.batchMap.clear();
   }

   private void setObject(PreparedStatement ps, int param, int column, Object val) throws SQLException {
      if (val == null) {
         ps.setNull(param, this.metaData.getColumnType(column + 1));
      } else if (this.metaData.getColumnType(column + 1) == 91) {
         if (val instanceof Calendar) {
            ps.setDate(param, new Date(((Calendar)val).getTimeInMillis()));
         } else if (val instanceof java.util.Date) {
            ps.setDate(param, new Date(((java.util.Date)val).getTime()));
         } else {
            ps.setObject(param, val, this.metaData.getColumnType(column + 1));
         }
      } else if (val instanceof Integer) {
         ps.setInt(param, (Integer)val);
      } else if (val instanceof Float) {
         ps.setFloat(param, (Float)val);
      } else if (val instanceof char[]) {
         ps.setCharacterStream(param, new CharArrayReader((char[])((char[])val)), ((char[])((char[])val)).length);
      } else {
         ps.setObject(param, val, this.metaData.getColumnType(column + 1));
      }

   }

   private void setInsertParameters(Connection con, PreparedStatement ps, Object[] colVals) throws SQLException {
      int paramNumber = 1;

      for(int i = 0; i < this.columnMask.length(); ++i) {
         if (this.columnMask.get(i) && !this.metaData.isAutoIncrement(i + 1)) {
            this.setObject(ps, paramNumber++, i, this.insertedObject(con, colVals[i]));
         }
      }

   }

   private int setWhereParameters(PreparedStatement ps, Object[] cols, ColumnFilter filter) throws SQLException {
      return this.setWhereParameters(ps, cols, filter, 1);
   }

   protected int setWhereParameters(PreparedStatement ps, Object[] cols, ColumnFilter filter, int paramNumber) throws SQLException {
      for(int i = 0; i < this.columnMask.length(); ++i) {
         if (this.columnMask.get(i) && filter.include(i) && cols[i] != null && !this.isLOB(i)) {
            this.setObject(ps, paramNumber++, i, cols[i]);
         }
      }

      return paramNumber;
   }

   private void deleteRow(Connection con, CachedRow row) throws SQLException {
      OptimisticPolicy p = getPolicy(this.metaData.getOptimisticPolicy());
      ColumnFilter filter = p.getColumnFilter(this, row.getModifiedColumns());
      Object[] cols = null;
      if (row.isUpdatedRow()) {
         cols = row.getOldColumns();
      } else {
         cols = row.getColumns();
      }

      if (this.groupDeletes) {
         this.groupDeleteList.add(new GroupDelete(this.getWhereClause(cols, filter), filter, cols));
      } else {
         String sql = this.getDeleteStatement(this.rowSet, cols, p, filter);
         if (this.verboseSQL) {
            this.printSQL(sql, cols, filter);
         }

         PreparedStatement ps = null;

         try {
            if (this.batchDeletes) {
               ps = this.getBatchPS(con, sql);
            } else {
               ps = con.prepareStatement(sql);
            }

            this.setWhereParameters(ps, cols, filter);
            if (this.batchDeletes) {
               ps.addBatch();
            } else if (ps.executeUpdate() == 0) {
               this.throwOCE(this.formatSQL(sql, cols, filter), row);
            }
         } finally {
            if (!this.batchDeletes && ps != null) {
               try {
                  ps.close();
               } catch (Exception var14) {
               }
            }

         }

      }
   }

   private void updateVersionColumns(CachedRow row) throws SQLException {
      for(int i = 0; i < this.columnMask.length(); ++i) {
         if (this.columnMask.get(i) && this.metaData.isAutoVersionColumn(i + 1)) {
            Object oldVal = row.getColumn(i + 1);
            if (oldVal instanceof Integer) {
               int newVal = (Integer)oldVal + 1;
               row.updateColumn(i + 1, new Integer(newVal));
            } else if (oldVal instanceof Long) {
               long newVal = (Long)oldVal + 1L;
               row.updateColumn(i + 1, new Long(newVal));
            } else if (oldVal instanceof Short) {
               short newVal = (short)((Short)oldVal + 1);
               row.updateColumn(i + 1, new Short(newVal));
            } else {
               if (!(oldVal instanceof BigDecimal)) {
                  throw new SQLException("Column: " + this.metaData.getWriteColumnName(i + 1) + " in table: " + this.tableName + " is marked as a version column, but it is not a numeric type.");
               }

               BigDecimal newVal = ((BigDecimal)oldVal).add(BigDecimal.valueOf(1L));
               row.updateColumn(i + 1, newVal);
            }
         }
      }

   }

   private void updateRow(Connection con, CachedRow row) throws SQLException {
      PreparedStatement ps = null;
      Object[] cols = row.getColumns();
      Object[] oldColumns = row.getOldColumns();
      BitSet modColMask = this.getModifiedColumns(row);
      if (row.isInsertRow() && row.isUpdatedRow()) {
         oldColumns = cols;
      }

      try {
         OptimisticPolicy p;
         if (row.isInsertRow() && row.isUpdatedRow()) {
            p = getPolicy(4);
         } else {
            p = getPolicy(this.metaData.getOptimisticPolicy());
         }

         ColumnFilter filter = p.getColumnFilter(this, modColMask);
         if (this.metaData.getOptimisticPolicy() == 5) {
            this.updateVersionColumns(row);
         }

         String sql = this.buildUpdateSQL(this.rowSet, row, p, filter, modColMask);
         if (sql != null) {
            if (this.verboseSQL) {
               this.printSQL(sql, cols, new ModFilter(modColMask), oldColumns, filter);
            }

            if (this.batchUpdates) {
               ps = this.getBatchPS(con, sql);
               if (!this.checkBatchUpdateCounts) {
                  this.batchVerifyParams.add(new BatchVerifyParam(this.getWhereClause(cols, filter), filter, oldColumns));
               }
            } else {
               ps = con.prepareStatement(sql);
            }

            int paramNumber = 1;

            for(int i = 0; i < this.columnMask.length(); ++i) {
               if (this.columnMask.get(i) && modColMask.get(i) && !this.metaData.isAutoIncrement(i + 1)) {
                  this.setObject(ps, paramNumber++, i, this.updatedObject(cols[i]));
               }
            }

            this.setWhereParameters(ps, oldColumns, filter, paramNumber);
            if (this.batchUpdates) {
               ps.addBatch();
            } else if (ps.executeUpdate() == 0) {
               this.throwOCE(this.formatSQL(sql, cols, new ModFilter(modColMask), oldColumns, filter), row);
               return;
            }

            return;
         }
      } finally {
         if (!this.batchUpdates && ps != null) {
            try {
               ps.close();
            } catch (Exception var18) {
            }
         }

      }

   }

   private String buildUpdateSQL(WLRowSetInternal rowSet, CachedRow row, OptimisticPolicy p, ColumnFilter filter, BitSet modColMask) throws SQLException {
      String setClause = this.getSetClause(row, modColMask);
      if (setClause == null) {
         return null;
      } else {
         StringBuffer sb = new StringBuffer(200);
         sb.append(this.getUpdateHeader());
         sb.append(setClause);
         sb.append("WHERE ");
         sb.append(this.getWhereClause(row.getOldColumns(), filter));
         return sb.toString();
      }
   }

   private String getSetClause(CachedRow row, BitSet modColMask) throws SQLException {
      StringBuffer sb = new StringBuffer();
      boolean noUpdate = true;
      String sep = "";

      for(int i = 0; i < this.columnCount; ++i) {
         if (this.columnMask.get(i) && modColMask.get(i) && !this.metaData.isAutoIncrement(i + 1)) {
            noUpdate = false;
            sb.append(sep);
            sb.append(this.metaData.getWriteColumnName(i + 1));
            sb.append(" = ?");
            sep = ", ";
         }
      }

      if (noUpdate) {
         return null;
      } else {
         sb.append(" ");
         return sb.toString();
      }
   }

   private String getUpdateHeader() {
      if (this.__dont_touch_me_update_header == null) {
         StringBuffer sb = new StringBuffer(200);
         sb.append("UPDATE ");
         sb.append(this.tableName);
         sb.append(" SET ");
         this.__dont_touch_me_update_header = sb.toString();
      }

      return this.__dont_touch_me_update_header;
   }

   private String getDeleteHeader() {
      if (this.__dont_touch_me_delete_header == null) {
         StringBuffer sb = new StringBuffer(200);
         sb.append("DELETE FROM ");
         sb.append(this.tableName);
         sb.append(" ");
         this.__dont_touch_me_delete_header = sb.toString();
      }

      return this.__dont_touch_me_delete_header;
   }

   private String getInsertStatement() throws SQLException {
      if (this.__dont_touch_me_insert_statement == null) {
         StringBuffer sb = new StringBuffer();
         sb.append("INSERT INTO ");
         sb.append(this.tableName);
         sb.append(" (");
         String sep = "";
         int count = 0;

         int i;
         for(i = 0; i < this.columnCount; ++i) {
            if (this.columnMask.get(i) && !this.metaData.isAutoIncrement(i + 1)) {
               ++count;
               sb.append(sep);
               sb.append(this.metaData.getWriteColumnName(i + 1));
               sep = ", ";
            }
         }

         sb.append(") VALUES (");
         sep = "";

         for(i = 0; i < count; ++i) {
            sb.append(sep);
            sb.append("?");
            sep = ", ";
         }

         sb.append(")");
         this.__dont_touch_me_insert_statement = sb.toString();
      }

      return this.__dont_touch_me_insert_statement;
   }

   private String getDeleteStatement(WLRowSetInternal rowSet, Object[] cols, OptimisticPolicy p, ColumnFilter filter) throws SQLException {
      StringBuffer sb = new StringBuffer(500);
      sb.append(this.getDeleteHeader());
      sb.append("WHERE ");
      sb.append(this.getWhereClause(cols, filter));
      return sb.toString();
   }

   protected boolean isLOB(int i) throws SQLException {
      int type = this.metaData.getColumnType(i + 1);
      return type == 2004 || type == 2005 || type == 2011;
   }

   protected String getWhereClause(Object[] cols, ColumnFilter filter) throws SQLException {
      if (!this.metaData.haveSetPKColumns()) {
         throw new SQLException("You must use the WLRowSetMetaData.setPrimaryKeyColumn() method to establish primary key columns before updating rows.");
      } else {
         StringBuffer sb = new StringBuffer(500);
         String sep = "";

         for(int i = 0; i < this.columnCount; ++i) {
            if (this.columnMask.get(i) && filter.include(i) && !this.isLOB(i)) {
               sb.append(sep).append(this.metaData.getWriteColumnName(i + 1));
               if (cols[i] == null) {
                  sb.append(" IS NULL");
               } else {
                  sb.append(" = ?");
               }

               sep = " AND ";
            }
         }

         return sb.toString();
      }
   }

   private void updateRowParameters(PreparedStatement ps, WLRowSetInternal rowSet, CachedRow row, String sql, BitSet modColMask) throws SQLException {
      Object[] cols = row.getColumns();
      Object[] oldColumns = row.getOldColumns();
      int paramNumber = 1;

      for(int i = 0; i < this.columnMask.length(); ++i) {
         if (this.columnMask.get(i) && modColMask.get(i)) {
            this.setObject(ps, paramNumber++, i, cols[i]);
         }
      }

      ColumnFilter filter = getPolicy(this.metaData.getOptimisticPolicy()).getColumnFilter(this, modColMask);

      for(int i = 0; i < this.columnMask.length(); ++i) {
         if (this.columnMask.get(i) && filter.include(i)) {
            this.setObject(ps, paramNumber++, i, oldColumns[i]);
         }
      }

      if (ps.executeUpdate() == 0) {
         this.throwOCE(sql, row);
      }

   }

   static OptimisticPolicy getPolicy(int i) {
      switch (i) {
         case 1:
            return TableWriter.OptimisticPolicy.VERIFY_READ_COLUMNS;
         case 2:
            return TableWriter.OptimisticPolicy.VERIFY_MODIFIED_COLUMNS;
         case 3:
            return TableWriter.OptimisticPolicy.VERIFY_SELECTED_COLUMNS;
         case 4:
            return TableWriter.OptimisticPolicy.VERIFY_NONE;
         case 5:
            return TableWriter.OptimisticPolicy.VERIFY_AUTO_VERSION_COLUMNS;
         case 6:
            return TableWriter.OptimisticPolicy.VERIFY_VERSION_COLUMNS;
         default:
            throw new AssertionError("Unexpected OptimisticPolicy: " + i);
      }
   }

   protected static final class BatchVerifyParam {
      private final String whereClause;
      private final ColumnFilter filter;
      private final Object[] params;

      BatchVerifyParam(String whereClause, ColumnFilter filter, Object[] params) {
         this.whereClause = whereClause;
         this.filter = filter;
         this.params = params;
      }

      String getWhereClause() {
         return this.whereClause;
      }

      ColumnFilter getFilter() {
         return this.filter;
      }

      Object[] getCols() {
         return this.params;
      }
   }

   private static final class GroupDelete {
      private final String whereClause;
      private final ColumnFilter filter;
      private final Object[] params;

      GroupDelete(String whereClause, ColumnFilter filter, Object[] params) {
         this.whereClause = whereClause;
         this.filter = filter;
         this.params = params;
      }

      String getWhereClause() {
         return this.whereClause;
      }

      ColumnFilter getFilter() {
         return this.filter;
      }

      Object[] getCols() {
         return this.params;
      }
   }

   abstract static class OptimisticPolicy {
      static OptimisticPolicy VERIFY_READ_COLUMNS = new OptimisticPolicy() {
         int getCode() {
            return 1;
         }

         String getName() {
            return "VERIFY_READ_COLUMNS";
         }

         ColumnFilter getColumnFilter(TableWriter tw, BitSet modColMask) {
            return TableWriter.ALL;
         }
      };
      static OptimisticPolicy VERIFY_MODIFIED_COLUMNS = new OptimisticPolicy() {
         int getCode() {
            return 2;
         }

         String getName() {
            return "VERIFY_MODIFIED_COLUMNS";
         }

         ColumnFilter getColumnFilter(TableWriter tw, final BitSet modColMask) {
            final WLRowSetMetaData metaData = tw.metaData;
            return new ColumnFilter() {
               public boolean include(int i) throws SQLException {
                  return modColMask.get(i) || metaData.isPrimaryKeyColumn(i + 1);
               }
            };
         }
      };
      static OptimisticPolicy VERIFY_SELECTED_COLUMNS = new OptimisticPolicy() {
         int getCode() {
            return 3;
         }

         String getName() {
            return "VERIFY_SELECTED_COLUMNS";
         }

         ColumnFilter getColumnFilter(TableWriter tw, BitSet modColMask) throws SQLException {
            final WLRowSetMetaData metaData = tw.metaData;
            return new ColumnFilter() {
               public boolean include(int i) throws SQLException {
                  return metaData.isSelectedColumn(i + 1) || metaData.isPrimaryKeyColumn(i + 1);
               }
            };
         }
      };
      static OptimisticPolicy VERIFY_NONE = new OptimisticPolicy() {
         int getCode() {
            return 4;
         }

         String getName() {
            return "VERIFY_NONE";
         }

         ColumnFilter getColumnFilter(TableWriter tw, BitSet modColMask) throws SQLException {
            final WLRowSetMetaData metaData = tw.metaData;
            return new ColumnFilter() {
               public boolean include(int i) throws SQLException {
                  return metaData.isPrimaryKeyColumn(i + 1);
               }
            };
         }
      };
      static OptimisticPolicy VERIFY_AUTO_VERSION_COLUMNS = new OptimisticPolicy() {
         int getCode() {
            return 5;
         }

         String getName() {
            return "VERIFY_AUTO_VERSION_COLUMNS";
         }

         ColumnFilter getColumnFilter(TableWriter tw, BitSet modColMask) throws SQLException {
            final WLRowSetMetaData metaData = tw.metaData;
            return new ColumnFilter() {
               public boolean include(int i) throws SQLException {
                  return metaData.isAutoVersionColumn(i + 1) || metaData.isPrimaryKeyColumn(i + 1);
               }
            };
         }
      };
      static OptimisticPolicy VERIFY_VERSION_COLUMNS = new OptimisticPolicy() {
         int getCode() {
            return 6;
         }

         String getName() {
            return "VERIFY_VERSION_COLUMNS";
         }

         ColumnFilter getColumnFilter(TableWriter tw, BitSet modColMask) throws SQLException {
            final WLRowSetMetaData metaData = tw.metaData;
            return new ColumnFilter() {
               public boolean include(int i) throws SQLException {
                  return metaData.isVersionColumn(i + 1) || metaData.isPrimaryKeyColumn(i + 1);
               }
            };
         }
      };

      abstract int getCode();

      abstract String getName();

      abstract ColumnFilter getColumnFilter(TableWriter var1, BitSet var2) throws SQLException;
   }

   private static final class ModFilter implements ColumnFilter {
      private final BitSet modColMask;

      private ModFilter(BitSet modColMask) {
         this.modColMask = modColMask;
      }

      public boolean include(int i) throws SQLException {
         return this.modColMask.get(i);
      }

      // $FF: synthetic method
      ModFilter(BitSet x0, Object x1) {
         this(x0);
      }
   }

   private static final class AutoIncFilter implements ColumnFilter {
      private WLRowSetMetaData metaData;

      private AutoIncFilter(WLRowSetMetaData metaData) {
         this.metaData = metaData;
      }

      public boolean include(int i) throws SQLException {
         return !this.metaData.isAutoIncrement(i + 1);
      }

      // $FF: synthetic method
      AutoIncFilter(WLRowSetMetaData x0, Object x1) {
         this(x0);
      }
   }

   interface ColumnFilter {
      boolean include(int var1) throws SQLException;
   }
}
