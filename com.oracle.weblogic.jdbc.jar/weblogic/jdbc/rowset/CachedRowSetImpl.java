package weblogic.jdbc.rowset;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.Predicate;
import javax.sql.rowset.spi.SyncProviderException;
import javax.sql.rowset.spi.SyncResolver;
import javax.transaction.Transaction;
import weblogic.jdbc.JDBCLogger;
import weblogic.transaction.TransactionHelper;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.stream.XMLOutputStreamFactory;

/** @deprecated */
@Deprecated
public class CachedRowSetImpl extends BaseRowSet implements WLCachedRowSet, WLRowSetInternal, SyncResolver, Serializable, Cloneable {
   private static final long serialVersionUID = -7682272538461932607L;
   private static final String DEBUGSTR = "weblogic.jdbc.rowset.CachedRowSet.debug";
   private static final String VERBOSESTR = "weblogic.jdbc.rowset.CachedRowSet.debug";
   private static final boolean DEBUG = Boolean.getBoolean("weblogic.jdbc.rowset.CachedRowSet.debug");
   private static final boolean VERBOSE = Boolean.getBoolean("weblogic.jdbc.rowset.CachedRowSet.debug");
   private static final Pattern FROM_PATTERN = Pattern.compile("(?i)\\bfrom\\s+([a-zA-Z_0-9.]+)");
   CachedRow insertRow;
   ArrayList allrows = new ArrayList();
   transient ArrayList rows = new ArrayList();
   transient Predicate filter;
   transient Comparator sorter;
   transient CachedRowSetImpl baseRowSet;
   transient volatile Object lock = null;
   transient int lockNum = 0;
   transient int currentPage = 0;
   transient Connection txConnection = null;
   transient Connection pendingConnection = null;
   transient ResultSet pendingResultSet = null;
   transient boolean populateFromResultSet = true;
   private transient int sc = 0;
   private boolean isPopulated = false;

   void lock() {
      if (this.lock == Thread.currentThread()) {
         ++this.lockNum;
      } else {
         boolean getit = false;

         do {
            while(this.lock != null) {
            }

            synchronized(this) {
               if (this.lock == null) {
                  this.lock = Thread.currentThread();
                  getit = true;
               }
            }
         } while(!getit);

         ++this.lockNum;
      }
   }

   void unlock() {
      if (this.lock == Thread.currentThread()) {
         --this.lockNum;
         if (this.lockNum == 0) {
            this.lock = null;
         }

      } else {
         throw new RuntimeException("============================Internal Error: CachedRowSetImpl sync lock is broken. lock: " + this.lock + " current: " + Thread.currentThread());
      }
   }

   public CachedRowSetImpl() {
      try {
         this.metaData = new CachedRowSetMetaData();
      } catch (Throwable var2) {
      }

      this.reset();
   }

   public CachedRowSetImpl(Hashtable env) {
      try {
         this.metaData = new CachedRowSetMetaData();
      } catch (Throwable var3) {
      }

      this.reset();
   }

   public void populate(ResultSetMetaData md) throws SQLException {
      this.checkOp(2);
      this.clearData();
      if (md instanceof CachedRowSetMetaData) {
         this.metaData = (CachedRowSetMetaData)((CachedRowSetMetaData)md).clone();
      } else {
         DatabaseMetaData dbmd = null;

         try {
            dbmd = this.getConnection().getMetaData();
         } catch (Exception var4) {
            JDBCLogger.logStackTrace(var4);
         }

         this.metaData.initialize(md, dbmd);
      }

      this.rowSetChanged();
   }

   void populateInternal(ResultSet rs) throws SQLException {
      this.populate(rs);
      this.populateFromResultSet = false;
      this.pendingResultSet = null;
      this.setIsClosed(false);
   }

   public void populate(ResultSet rs) throws SQLException {
      int startRow = this.currentPage * this.maxRows + 1;
      this.populate(rs, startRow);
   }

   public void populate(ResultSet rs, int i) throws SQLException {
      boolean forward = true;
      if (i < 0) {
         throw new SQLException(i + " is not a valid start position.");
      } else {
         this.checkOp(2);
         ResultSetMetaData md = rs.getMetaData();
         if (md instanceof CachedRowSetMetaData) {
            this.metaData = (CachedRowSetMetaData)((CachedRowSetMetaData)md).clone();
         } else {
            DatabaseMetaData dbmd = null;

            try {
               Statement st = rs.getStatement();
               if (st != null) {
                  Connection c = st.getConnection();
                  if (c != null) {
                     dbmd = c.getMetaData();
                  }
               }
            } catch (Exception var28) {
               JDBCLogger.logStackTrace(var28);
            }

            if (dbmd == null) {
               try {
                  dbmd = this.getConnection().getMetaData();
               } catch (Exception var27) {
                  JDBCLogger.logStackTrace(var27);
               }
            }

            this.metaData.initialize(md, dbmd);
         }

         int pos = -1;
         boolean validRow = false;

         try {
            pos = rs.getRow();
         } catch (Exception var26) {
         }

         try {
            if (pos == -1 && rs.isBeforeFirst()) {
               pos = 0;
            }
         } catch (Throwable var25) {
            pos = 0;
         }

         try {
            if (rs.getType() != 1003) {
               forward = false;
            }
         } catch (Exception var24) {
         }

         int rowCount;
         try {
            if (forward) {
               label272: {
                  if (pos >= 0 && pos <= i) {
                     rowCount = 0;

                     while(true) {
                        if (rowCount >= i - pos) {
                           break label272;
                        }

                        validRow = rs.next();
                        ++rowCount;
                     }
                  }

                  throw new SQLException("absolute not supported on ResultSet.TYPE_FORWARD_ONLY; ResultSet position is " + pos + "; populate position is " + i);
               }
            } else {
               validRow = rs.absolute(i);
            }
         } catch (SQLException var30) {
            if (pos < 0 || pos > i) {
               throw var30;
            }

            for(int x = 0; x < i - pos; ++x) {
               validRow = rs.next();
            }
         }

         try {
            rowCount = 0;
            if (validRow) {
               this.clearData();
               this.pendingResultSet = rs;
               this.populateFromResultSet = true;

               do {
                  ++rowCount;
                  if (this.maxRows != 0 && rowCount > this.maxRows) {
                     this.setIsComplete(false);
                     break;
                  }

                  this.allrows.add(new CachedRow(rs, this.metaData, this.getTypeMap()));
                  this.isPopulated = true;
               } while(rs.next());
            } else if (i == 1) {
               this.clearData();
            }
         } finally {
            if (pos <= 0) {
               try {
                  if (!forward) {
                     rs.beforeFirst();
                  }
               } catch (Exception var23) {
               }
            } else {
               try {
                  if (!forward) {
                     rs.absolute(pos);
                  }
               } catch (Exception var22) {
               }
            }

         }

         this.filter();
         this.rowSetChanged();
      }
   }

   public void execute() throws SQLException {
      if (this.command != null && !this.command.equals("")) {
         Connection con = this.getConnection();
         if (con == null) {
            throw new SQLException("Can not get a connection.");
         } else {
            try {
               this.execute(con);
            } finally {
               try {
                  con.close();
               } catch (Exception var8) {
               }

               this.cachedConnection = null;
               this.pendingConnection = null;
            }

         }
      } else {
         throw new SQLException("You must call setCommand with a SQL string before calling execute().");
      }
   }

   public void execute(Connection con) throws SQLException {
      if (con == null) {
         throw new SQLException("Can not calling execute with null connection");
      } else {
         Connection savedConn = this.cachedConnection;
         this.cachedConnection = con;

         try {
            this.reader.readData(this);
            this.pendingConnection = con;
         } finally {
            this.cachedConnection = savedConn;
         }

      }
   }

   public String executeAndGuessTableName() throws SQLException {
      this.execute();
      Matcher m = FROM_PATTERN.matcher(this.command);
      if (!m.find()) {
         return null;
      } else {
         String tableName = m.group(1);
         this.metaData.setTableName(tableName);
         return tableName;
      }
   }

   public boolean executeAndGuessTableNameAndPrimaryKeys() throws SQLException {
      String tableName = this.executeAndGuessTableName();
      if (tableName == null) {
         return false;
      } else {
         Connection con = null;
         ResultSet rs = null;

         try {
            con = this.getConnection();
            DatabaseMetaData dmd = con.getMetaData();
            TableNameParser parser = new TableNameParser(tableName, new DatabaseMetaDataHolder(dmd));
            String[] parts = parser.parse();
            String catalog = "".equals(parts[0]) ? null : parts[0];
            String schema = "".equals(parts[1]) ? null : parts[1];
            String table = parts[2];
            rs = dmd.getPrimaryKeys(catalog, schema, table);
            boolean var10;
            if (!rs.next()) {
               if (catalog != null) {
                  catalog = catalog.toUpperCase();
               }

               if (schema != null) {
                  schema = schema.toUpperCase();
               }

               if (table != null) {
                  table = table.toUpperCase();
               }

               rs = dmd.getPrimaryKeys(catalog, schema, table);
               if (!rs.next()) {
                  if (catalog != null) {
                     catalog = catalog.toLowerCase();
                  }

                  if (schema != null) {
                     schema = schema.toLowerCase();
                  }

                  if (table != null) {
                     table = table.toLowerCase();
                  }

                  rs = dmd.getPrimaryKeys(catalog, schema, table);
                  if (!rs.next()) {
                     var10 = false;
                     return var10;
                  }
               }
            }

            do {
               this.metaData.setPrimaryKeyColumn(rs.getString("COLUMN_NAME"), true);
            } while(rs.next());

            var10 = true;
            return var10;
         } catch (SQLException var26) {
            JDBCLogger.logStackTrace(var26);
            boolean var5 = false;
            return var5;
         } finally {
            if (rs != null) {
               try {
                  rs.close();
               } catch (Exception var25) {
               }
            }

            if (con != null) {
               try {
                  con.close();
               } catch (Exception var24) {
               }
            }

            this.cachedConnection = null;
         }
      }
   }

   void refresh() throws SyncProviderException {
      CachedRow r;
      for(Iterator it = this.rows.iterator(); it.hasNext(); r.acceptChanges()) {
         r = (CachedRow)it.next();
         if (r.isDeletedRow()) {
            this.allrows.remove(r);
         }
      }

      this.filter();
   }

   SQLException sync(Connection con) {
      SQLException ret = null;
      Connection savedConn = this.cachedConnection;
      this.cachedConnection = con;

      try {
         this.writer.writeData(this);
      } catch (SQLException var8) {
         ret = var8;
      } finally {
         this.cachedConnection = savedConn;
      }

      return ret;
   }

   public void acceptChanges() throws SyncProviderException {
      if (!this.isReadOnly()) {
         Connection con = null;
         SQLException se = null;
         boolean globalTx = false;

         try {
            Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();

            try {
               if (tx != null && tx.getStatus() == 0) {
                  globalTx = true;
               }
            } catch (Exception var28) {
            }

            if (globalTx) {
               if (this.cachedConnection != null) {
                  try {
                     this.cachedConnection.close();
                  } catch (Exception var27) {
                  }

                  this.cachedConnection = null;
               }

               con = this.getConnection();
               if (con == null) {
                  throw new SQLException("Can not get a connection");
               }

               this.checkOp(9);
               se = this.sync(con);
               if (se != null) {
                  try {
                     tx.setRollbackOnly();
                  } catch (Exception var26) {
                  }
               }
            } else {
               con = this.getConnection();
               con.setAutoCommit(false);
               this.checkOp(10);
               this.lock();

               try {
                  se = this.sync(con);
                  if (se == null) {
                     con.commit();
                     this.refresh();
                  } else {
                     con.rollback();
                  }
               } finally {
                  this.unlock();
               }
            }
         } catch (Exception var29) {
            throw new SyncProviderException(var29.toString());
         } finally {
            try {
               con.close();
            } catch (Exception var24) {
            }

            this.cachedConnection = null;
            if (se != null) {
               if (se instanceof SyncProviderException) {
                  throw (SyncProviderException)se;
               }

               throw new SyncProviderException(se.toString());
            }

         }

      }
   }

   public void acceptChanges(Connection con) throws SyncProviderException {
      if (!this.isReadOnly()) {
         if (con == null) {
            throw new SyncProviderException("acceptChanges(java.sql.Connection conn) can not be invoked with null conn");
         } else {
            SQLException se = null;
            boolean globalTx = false;
            Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();

            try {
               if (tx != null && tx.getStatus() == 0) {
                  globalTx = true;
               }
            } catch (Exception var22) {
               throw new SyncProviderException("failed");
            }

            try {
               if (globalTx) {
                  this.checkOp(9);
                  se = this.sync(con);
                  if (se != null) {
                     try {
                        tx.setRollbackOnly();
                     } catch (Exception var19) {
                        throw new SyncProviderException("failed");
                     }
                  }
               } else {
                  if (con == null) {
                     throw new SyncProviderException("Can not calling acceptChanges with null connection");
                  }

                  if (con.getAutoCommit()) {
                     con.setAutoCommit(false);
                     this.checkOp(10);
                     this.lock();

                     try {
                        se = this.sync(con);
                        if (se == null) {
                           con.commit();
                           this.refresh();
                        } else {
                           con.rollback();
                        }
                     } finally {
                        this.unlock();
                        con.setAutoCommit(true);
                     }
                  } else {
                     this.checkOp(9);
                     se = this.sync(con);
                     this.txConnection = con;
                  }
               }
            } catch (Exception var20) {
               throw new SyncProviderException(var20.toString());
            } finally {
               if (se != null) {
                  if (se instanceof SyncProviderException) {
                     throw (SyncProviderException)se;
                  }

                  throw new SyncProviderException(se.toString());
               }

            }

         }
      }
   }

   public void insertRow() throws SQLException {
      this.checkOp(8);
      if (this.insertRow == null) {
         throw new SQLException("There is no data to insert");
      } else if (this.filter != null && !this.filter.evaluate(this)) {
         throw new SQLException("Inserted row violates the criteria of the current filter.");
      } else {
         this.rows.add(this.insertRow);
         this.allrows.add(this.insertRow);
         this.insertRow = null;
         this.rowChanged();
      }
   }

   public void undoInsert() throws SQLException {
      this.checkOp(4);
      CachedRow row = this.currentRow();
      if (row.isInsertRow()) {
         try {
            this.rows.remove(this.rows.indexOf(row));
         } catch (Throwable var4) {
         }

         try {
            this.allrows.remove(this.rows.indexOf(row));
         } catch (Throwable var3) {
         }
      }

      this.rowChanged();
   }

   public void deleteRow() throws SQLException {
      this.checkOp(4);
      this.currentRow().setDeletedRow(true);
      this.rowChanged();
   }

   public void undoDelete() throws SQLException {
      this.checkOp(4);
      CachedRow row = this.currentRow();
      if (row.isDeletedRow()) {
         row.setDeletedRow(false);
      }

   }

   public void updateRow() throws SQLException {
      this.checkOp(8);
      if (this.filter != null && !this.filter.evaluate(this)) {
         throw new SQLException("Updated row violates the criteria of the current filter.");
      } else {
         CachedRow row = this.currentRow();
         if (row.isInsertRow()) {
            if (row.isUpdatedRow() && this.rows.indexOf(row) != -1) {
               this.rows.add(row);
               this.allrows.add(row);
               this.insertRow = null;
            }
         } else {
            row.setUpdatedRow(true);
         }

         this.rowChanged();
      }
   }

   public void undoUpdate() throws SQLException {
      this.checkOp(4);
      CachedRow row = this.currentRow();
      if (row.isInsertRow() && row.isUpdatedRow()) {
         try {
            this.rows.remove(this.rows.indexOf(row));
         } catch (Throwable var4) {
         }

         try {
            this.allrows.remove(this.rows.indexOf(row));
         } catch (Throwable var3) {
         }
      } else if (row.isUpdatedRow()) {
         row.cancelRowUpdates();
      }

   }

   public void cancelRowUpdates() throws SQLException {
      this.checkOp(8);
      CachedRow row = this.currentRow();
      if (row.isInsertRow()) {
         this.insertRow = null;
      } else {
         row.cancelRowUpdates();
      }

   }

   public void setOriginalRow() throws SQLException {
      this.checkOp(4);
      CachedRow row = this.currentRow();
      if (row.isInsertRow()) {
         try {
            this.rows.remove(this.rows.indexOf(row));
         } catch (Throwable var4) {
         }

         try {
            this.allrows.remove(this.rows.indexOf(row));
         } catch (Throwable var3) {
         }
      } else {
         if (row.isDeletedRow()) {
            row.setDeletedRow(false);
         }

         if (row.isUpdatedRow()) {
            row.cancelRowUpdates();
         }
      }

      this.rowSetChanged();
   }

   public void restoreOriginal() throws SQLException {
      this.checkOp(4);

      for(int i = 0; i < this.rows.size(); ++i) {
         CachedRow row = (CachedRow)this.rows.get(i);
         if (row.isInsertRow()) {
            try {
               this.rows.remove(this.rows.indexOf(row));
            } catch (Throwable var5) {
            }

            try {
               this.allrows.remove(this.rows.indexOf(row));
            } catch (Throwable var4) {
            }
         } else {
            if (row.isDeletedRow()) {
               row.setDeletedRow(false);
            }

            if (row.isUpdatedRow()) {
               row.cancelRowUpdates();
            }
         }
      }

      this.rowIndex = -1;
      this.isComplete = true;
      this.txConnection = null;
      this.cachedConnection = null;
      this.pendingResultSet = null;
      this.pendingConnection = null;
      this.state = LifeCycle.POPULATING;
      this.rowSetChanged();
   }

   public ResultSet getOriginal() throws SQLException {
      CachedRowSetImpl ret = (CachedRowSetImpl)this.clone();
      ret.resetState();
      ret.rowIndex = -1;
      ret.state = LifeCycle.MANIPULATING;
      ret.restoreOriginal();
      return ret;
   }

   public ResultSet getOriginalRow() throws SQLException {
      CachedRow row = this.currentRow();
      CachedRowSetImpl ret = (CachedRowSetImpl)this.createCopySchema();
      if (!row.isInsertRow()) {
         row = (CachedRow)row.clone((CachedRowSetMetaData)ret.getMetaData());
         row.setDeletedRow(false);
         row.cancelRowUpdates();
         ret.rows.add(row);
         ret.allrows.add(row);
      }

      ret.resetState();
      ret.rowIndex = -1;
      ret.state = LifeCycle.MANIPULATING;
      return ret;
   }

   public void refreshRow() throws SQLException {
      throw new SQLException("refreshRow is not supported.");
   }

   public void moveToInsertRow() {
      this.checkOp(5);
   }

   public void moveToCurrentRow() {
      this.checkOp(6);
   }

   public void moveToUpdateRow() {
      this.checkOp(5);

      try {
         this.currentRow().setUpdatedRow(true);
      } catch (Throwable var2) {
         throw new RuntimeException(var2.toString());
      }
   }

   public void setRowSynced() throws SQLException {
      CachedRow row = this.currentRow();
      if (!row.isDeletedRow() || !row.isInsertRow()) {
         if (row.isDeletedRow()) {
            try {
               this.rows.remove(this.rows.indexOf(row));
            } catch (Throwable var4) {
            }

            try {
               this.allrows.remove(this.rows.indexOf(row));
            } catch (Throwable var3) {
            }
         } else {
            row.acceptChanges();
         }
      }

   }

   public void setRowSetSynced() throws SQLException {
      for(int i = 0; i < this.rows.size(); ++i) {
         CachedRow row = (CachedRow)this.rows.get(i);
         if (!row.isDeletedRow() || !row.isInsertRow()) {
            if (row.isDeletedRow()) {
               try {
                  this.rows.remove(this.rows.indexOf(row));
               } catch (Throwable var5) {
               }

               try {
                  this.allrows.remove(this.rows.indexOf(row));
               } catch (Throwable var4) {
               }
            } else {
               row.acceptChanges();
            }
         }
      }

   }

   public void setFilter(Predicate p) {
      this.checkOp(2);
      this.filter = p;
      this.filter();
   }

   public Predicate getFilter() {
      return this.filter;
   }

   void filter() throws RuntimeException {
      int savedIndex = this.rowIndex;
      ArrayList savedRows = this.rows;
      LifeCycle.State savedState = this.state;
      this.rows = this.allrows;

      try {
         this.allrows = new ArrayList(this.rows.size());
         int i = 0;

         while(true) {
            if (i >= this.rows.size()) {
               savedRows = this.rows;
               this.rows = this.allrows;
               this.allrows = savedRows;
               break;
            }

            this.rowIndex = i;
            if (this.filter == null || this.filter.evaluate(this)) {
               this.allrows.add(this.rows.get(i));
            }

            ++i;
         }
      } catch (Throwable var5) {
         this.allrows = this.rows;
         this.rows = savedRows;
         this.rowIndex = savedIndex;
         throw new RuntimeException(var5.getMessage());
      }

      this.rowIndex = -1;
      if (this.sorter != null) {
         this.sort();
      }

      this.state = savedState;
   }

   public void setSorter(Comparator s) {
      this.checkOp(2);
      this.sorter = s;
      this.sort();
   }

   public Comparator getSorter() {
      return this.sorter;
   }

   void sort() throws RuntimeException {
      if (this.sorter != null) {
         Collections.sort(this.rows, this.sorter);
      } else {
         this.filter();
      }

   }

   public int size() {
      return this.rows.size();
   }

   public Map getCurrentRow() throws SQLException {
      return this.currentRow();
   }

   public Map getRow(int index) throws SQLException {
      try {
         return (Map)this.rows.get(index);
      } catch (IndexOutOfBoundsException var3) {
         throw new SQLException("getRow(" + index + ") is not a valid row index");
      }
   }

   public Map[] getRows(int startIndex, int endIndex) throws SQLException {
      if (startIndex >= 0 && startIndex < this.rows.size()) {
         if (startIndex > endIndex) {
            throw new SQLException("startIndex cannot be > endIndex");
         } else if (endIndex >= 0 && endIndex <= this.rows.size()) {
            List r = new ArrayList();

            for(int i = startIndex; i < endIndex; ++i) {
               r.add(this.rows.get(i));
            }

            Map[] m = new Map[r.size()];
            return (Map[])((Map[])r.toArray(m));
         } else {
            throw new SQLException("endIndex cannot be < 0 or > CachedRowSet.size()");
         }
      } else {
         throw new SQLException("startIndex must be > 0 or < CachedRowSet.size()");
      }
   }

   public Map[] getRows() throws SQLException {
      Map[] m = new Map[this.rows.size()];
      return (Map[])((Map[])this.rows.toArray(m));
   }

   public List getAllCachedRows() {
      return this.allrows;
   }

   public List getCachedRows() {
      return this.rows;
   }

   public void setCachedRows(ArrayList r) {
      this.allrows = r;
      this.filter();
      this.rowIndex = 0;
   }

   public Collection toCollection() throws SQLException {
      ArrayList ret = new ArrayList(this.rows.size());
      ret.addAll(this.rows);
      return ret;
   }

   public Collection toCollection(int column) throws SQLException {
      ArrayList ret = new ArrayList(this.rows.size());

      for(int i = 0; i < this.rows.size(); ++i) {
         ret.add(((CachedRow)this.rows.get(i)).getColumn(column));
      }

      return ret;
   }

   public Collection toCollection(String column) throws SQLException {
      int idx = this.findColumn(column);
      ArrayList ret = new ArrayList(this.rows.size());

      for(int i = 0; i < this.rows.size(); ++i) {
         ret.add(((CachedRow)this.rows.get(i)).getColumn(idx));
      }

      return ret;
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this.isComplete = true;
      in.defaultReadObject();
      this.params = new ArrayList();
      this.rows = new ArrayList();
      this.provider = new WLSyncProvider();
      this.writer = new CachedRowSetJDBCWriter();
      this.reader = new CachedRowSetJDBCReader();
      this.locked = false;
      Iterator it = this.allrows.iterator();

      while(it.hasNext()) {
         CachedRow row = (CachedRow)it.next();
         if (row.getMetaData() != null) {
            break;
         }

         row.setMetaData(this.metaData);
      }

      this.filter();
   }

   public void loadXML(XMLInputStream xis) throws IOException, SQLException {
      if (this.metaData != null && this.metaData.getColumnCount() != 0) {
         this.checkOp(2);
         XMLInstanceReader reader = new XMLInstanceReader(this);
         reader.loadXML(xis);
         this.allrows.clear();
         this.allrows.addAll(this.rows);
         this.state = LifeCycle.POPULATING;
      } else {
         throw new SQLException("You must either use CachedRowSetMetaData.loadSchema toload an XML schema or set the RowSet's metadata before calling loadXML.");
      }
   }

   public void writeXML(XMLOutputStream xos) throws IOException, SQLException {
      this.writeXML(xos, 32);
   }

   public void writeXML(XMLOutputStream xos, int rowStates) throws IOException, SQLException {
      XMLInstanceWriter writer = new XMLInstanceWriter(this);
      writer.writeXML(xos, rowStates);
   }

   public void readXml(XMLInputStream xis) throws IOException, SQLException {
      this.checkOp(2);
      WebRowSetReader reader = new WebRowSetReader(this);
      reader.loadXML(xis);
      this.allrows.clear();
      this.allrows.addAll(this.rows);
      this.state = LifeCycle.POPULATING;
   }

   public void readXml(Reader reader) throws SQLException {
      try {
         this.readXml(XMLInputStreamFactory.newInstance().newInputStream(reader));
      } catch (NullPointerException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new SQLException(var4.toString());
      }
   }

   public void readXml(InputStream iStream) throws SQLException, IOException {
      this.readXml(XMLInputStreamFactory.newInstance().newInputStream(iStream));
   }

   public void writeXml(Writer oWriter) throws SQLException {
      WebRowSetWriter writer = new WebRowSetWriter(this);

      try {
         writer.writeXML(XMLOutputStreamFactory.newInstance().newDebugOutputStream(oWriter), 32);
      } catch (Throwable var4) {
         throw new SQLException(var4.toString());
      }
   }

   public void writeXml(OutputStream oStream) throws SQLException, IOException {
      WebRowSetWriter writer = new WebRowSetWriter(this);
      writer.writeXML(XMLOutputStreamFactory.newInstance().newDebugOutputStream(oStream), 32);
   }

   public void writeXml(ResultSet rs, Writer writer) throws SQLException {
      CachedRowSetImpl one = new CachedRowSetImpl();
      one.populate(rs);
      one.writeXml(writer);
   }

   public void writeXml(ResultSet rs, OutputStream oStream) throws SQLException, IOException {
      CachedRowSetImpl one = new CachedRowSetImpl();
      one.populate(rs);
      one.writeXml(oStream);
   }

   protected Object clone() {
      try {
         this.lock();
         CachedRowSetImpl ret = null;

         try {
            ret = (CachedRowSetImpl)super.clone();
         } catch (Throwable var7) {
            Object var3 = null;
            return var3;
         }

         ret.metaData = (CachedRowSetMetaData)((CachedRowSetMetaData)this.metaData.clone());
         ret.allrows = new ArrayList(this.allrows.size());

         for(int i = 0; i < this.allrows.size(); ++i) {
            ret.allrows.add(((CachedRow)this.allrows.get(i)).clone(this.metaData));
         }

         if (this.insertRow != null) {
            ret.insertRow = (CachedRow)((CachedRow)this.insertRow.clone(this.metaData));
         }

         ret.rowSetListeners = (List)((ArrayList)this.rowSetListeners).clone();
         ret.cachedConnection = null;
         ret.params = (ArrayList)((ArrayList)this.params.clone());
         ret.filter();
         CachedRowSetImpl var9 = ret;
         return var9;
      } finally {
         this.unlock();
      }
   }

   void detach(SharedRowSetImpl srs) {
      this.lock();

      try {
         --this.sc;
         this.locked = this.sc > 0;
      } finally {
         this.unlock();
      }

   }

   void attach(SharedRowSetImpl srs) {
      this.lock();

      try {
         ++this.sc;
         this.locked = this.sc > 0;
      } finally {
         this.unlock();
      }

   }

   public RowSet createShared() throws SQLException {
      SharedRowSetImpl ret = null;
      this.lock();

      try {
         ret = new SharedRowSetImpl(this);
      } catch (Throwable var6) {
         throw new SQLException(this + " can not be shared because of " + var6);
      } finally {
         this.unlock();
      }

      return ret;
   }

   public CachedRowSet createCopy() throws SQLException {
      CachedRowSetImpl ret = (CachedRowSetImpl)this.clone();
      ret.rowSetListeners.clear();
      return ret;
   }

   public CachedRowSet createCopySchema() throws SQLException {
      CachedRowSetImpl ret = new CachedRowSetImpl();
      ret.populate(this.getMetaData());
      return ret;
   }

   public CachedRowSet createCopyNoConstraints() throws SQLException {
      CachedRowSetImpl ret = new CachedRowSetImpl();
      ret.populate((ResultSet)this);
      return ret;
   }

   public void close() {
      this.reset();
      this.setIsClosed(true);
   }

   public void release() throws SQLException {
      this.reset();
   }

   private void reset() {
      this.clearData();
      this.resetState();
   }

   private void clearData() {
      this.rows.clear();
      this.allrows.clear();
      this.rowIndex = -1;
      this.isComplete = true;
      this.txConnection = null;
      this.pendingResultSet = null;
      this.pendingConnection = null;
   }

   private void resetState() {
      this.command = "";
      this.dataSourceName = null;
      this.dataSource = null;
      this.url = "";
      this.userName = null;
      this.password = null;
      this.isolationLevel = -1;
      this.fetchDirection = 1002;
      this.fetchSize = 0;
      this.typeMap = null;
      this.queryTimeout = 0;
      this.maxRows = 0;
      this.maxFieldSize = 0;
      this.escapeProcessing = true;
      this.concurrency = 1008;
      this.resultSetType = 1004;
      this.preferDataSource = true;
      this.cachedConnection = null;
      this.rowSetListeners.clear();
      this.state = LifeCycle.DESIGNING;
      this.params.clear();
      this.metaData.setReadOnly(false);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("[CachedRowSet]: [" + System.identityHashCode(this) + "] rowIndex: " + this.rowIndex);
      if (this.metaData == null) {
         sb.append("\nMETADATA: <NULL>");
      } else {
         sb.append("\nMETADATA: " + this.metaData);
      }

      sb.append("\n\nROWS:\n\n");
      Iterator it = this.rows.iterator();

      while(it.hasNext()) {
         sb.append(it.next().toString());
      }

      return sb.toString();
   }

   public Object getConflictValue(int index) {
      try {
         return this.currentRow().getConflictValue(index);
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public Object getConflictValue(String columnName) {
      try {
         return this.currentRow().getConflictValue(this.findColumn(columnName));
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public int getStatus() {
      try {
         return this.currentRow().getStatus();
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public void setResolvedValue(int index, Object obj) {
      try {
         this.currentRow().setResolvedValue(index, obj);
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public void setResolvedValue(String columnName, Object obj) {
      try {
         this.currentRow().setResolvedValue(this.findColumn(columnName), obj);
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public boolean nextConflict() throws SQLException {
      boolean ret = false;

      while(this.next()) {
         if (this.currentRow().getStatus() != 3) {
            ret = true;
            break;
         }
      }

      return ret;
   }

   public boolean previousConflict() throws SQLException {
      boolean ret = false;

      while(this.previous()) {
         if (this.currentRow().getStatus() != 3) {
            ret = true;
            break;
         }
      }

      return ret;
   }

   CachedRow currentRow() throws SQLException {
      if (this.state == LifeCycle.INSERTING) {
         if (this.insertRow == null) {
            this.insertRow = new CachedRow(this.metaData);
            this.insertRow.setInsertRow(true);
         }

         return this.insertRow;
      } else {
         try {
            return (CachedRow)this.rows.get(this.rowIndex);
         } catch (IndexOutOfBoundsException var2) {
            throw new SQLException("The cursor " + this.rowIndex + " is not positioned over a valid row");
         }
      }
   }

   void updateCurrent(int i, Object o) throws SQLException {
      this.checkOp(7);
      this.currentRow().updateColumn(i, o);
   }

   public boolean previousPage() throws SQLException {
      if (this.populateFromResultSet && this.pendingResultSet == null) {
         throw new SQLException("execute() or populate() must be called before previousPage() can be invoked.");
      } else {
         boolean ret = false;
         --this.currentPage;
         if (this.currentPage < 0) {
            this.currentPage = 0;
         } else {
            try {
               if (this.populateFromResultSet) {
                  this.populate(this.pendingResultSet);
               } else if (this.pendingConnection != null) {
                  this.execute(this.pendingConnection);
               } else {
                  this.execute();
               }

               ret = true;
            } catch (SQLException var3) {
               ++this.currentPage;
            }
         }

         return ret;
      }
   }

   public boolean nextPage() throws SQLException {
      if (this.populateFromResultSet && this.pendingResultSet == null) {
         throw new SQLException("execute() or populate() must be called before nextPage() can be invoked.");
      } else {
         boolean ret = false;
         ++this.currentPage;

         try {
            this.isPopulated = false;
            if (this.populateFromResultSet) {
               this.populate(this.pendingResultSet);
            } else if (this.pendingConnection != null) {
               this.execute(this.pendingConnection);
            } else {
               this.execute();
            }

            if (this.isPopulated) {
               ret = true;
            } else {
               --this.currentPage;
            }
         } catch (SQLException var3) {
            --this.currentPage;
         }

         return ret;
      }
   }

   public int getPageSize() {
      return this.getMaxRows();
   }

   public void setPageSize(int i) throws SQLException {
      if (i < 0) {
         throw new SQLException(i + " is not a valid PageSize.");
      } else {
         this.setMaxRows(i);
      }
   }

   public void rowSetPopulated(RowSetEvent rse, int i) throws SQLException {
      if (i > 0 && i > this.getFetchSize() && this.size() % i == 0) {
         this.rowSetChanged();
      }

   }

   public void rollback(Savepoint s) throws SQLException {
      this.checkOp();
      boolean globalTx = false;
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();

      try {
         if (tx != null && tx.getStatus() == 0) {
            globalTx = true;
         }
      } catch (Exception var9) {
         throw new SyncProviderException("Rollback failed because the status of the current transaction is unknown.");
      }

      if (globalTx) {
         throw new SQLException("This operation is not supported since there is an active global transaction on the current thread.");
      } else {
         if (this.txConnection != null) {
            try {
               this.txConnection.rollback(s);
               this.state = LifeCycle.POPULATING;
            } finally {
               this.txConnection = null;
            }
         }

      }
   }

   public void rollback() throws SQLException {
      this.checkOp();
      boolean globalTx = false;
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();

      try {
         if (tx != null && tx.getStatus() == 0) {
            globalTx = true;
         }
      } catch (Exception var10) {
         throw new SyncProviderException("Rollback failed because the status of the current transaction is unknown.");
      }

      if (globalTx) {
         try {
            tx.rollback();
            this.state = LifeCycle.POPULATING;
         } catch (Exception var9) {
            throw new SQLException(var9.toString());
         }
      } else if (this.txConnection != null) {
         try {
            this.txConnection.rollback();
            this.state = LifeCycle.POPULATING;
         } finally {
            this.txConnection = null;
         }
      }

   }

   public void commit() throws SQLException {
      this.checkOp();
      boolean globalTx = false;
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();

      try {
         if (tx != null && tx.getStatus() == 0) {
            globalTx = true;
         }
      } catch (Exception var17) {
         throw new SyncProviderException("Rollback failed because the status of the current transaction is unknown.");
      }

      if (globalTx) {
         this.lock();

         try {
            tx.commit();
            this.refresh();
            this.state = LifeCycle.POPULATING;
         } catch (Exception var15) {
            throw new SQLException(var15.toString());
         } finally {
            this.unlock();
         }
      } else if (this.txConnection != null) {
         this.lock();

         try {
            this.txConnection.commit();
            this.refresh();
            this.state = LifeCycle.POPULATING;
         } finally {
            this.unlock();
            this.txConnection = null;
         }
      }

   }

   public Object unwrap(Class iface) throws SQLException {
      if (iface.isInstance(this)) {
         return iface.cast(this);
      } else {
         throw new SQLException(this + " is not an instance of " + iface);
      }
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      return iface.isInstance(this);
   }

   public Object getObject(int columnIndex, Class type) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }

   public Object getObject(String columnLabel, Class type) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }
}
