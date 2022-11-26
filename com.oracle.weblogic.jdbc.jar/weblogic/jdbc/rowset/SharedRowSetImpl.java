package weblogic.jdbc.rowset;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.spi.SyncProviderException;
import javax.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.xml.stream.XMLInputStream;

/** @deprecated */
@Deprecated
class SharedRowSetImpl extends CachedRowSetImpl {
   SharedRowSetImpl(CachedRowSetImpl base) throws SQLException {
      this.baseRowSet = base;
      this.metaData = (CachedRowSetMetaData)((CachedRowSetMetaData)this.baseRowSet.metaData.clone());
      this.command = this.baseRowSet.command;
      this.dataSourceName = this.baseRowSet.dataSourceName;
      this.dataSource = this.baseRowSet.dataSource;
      this.url = this.baseRowSet.url;
      this.userName = this.baseRowSet.userName;
      this.password = this.baseRowSet.password;
      this.isolationLevel = this.baseRowSet.isolationLevel;
      this.fetchDirection = this.baseRowSet.fetchDirection;
      this.fetchSize = this.baseRowSet.fetchSize;
      this.typeMap = this.baseRowSet.typeMap;
      this.queryTimeout = this.baseRowSet.queryTimeout;
      this.maxRows = this.baseRowSet.maxRows;
      this.maxFieldSize = this.baseRowSet.maxFieldSize;
      this.escapeProcessing = this.baseRowSet.escapeProcessing;
      this.concurrency = this.baseRowSet.concurrency;
      this.resultSetType = this.baseRowSet.resultSetType;
      this.preferDataSource = this.baseRowSet.preferDataSource;
      this.insertRow = null;
      this.rowSetListeners = new ArrayList();
      this.cachedConnection = null;
      this.pendingConnection = null;
      this.params = new ArrayList();
      this.state = LifeCycle.POPULATING;
      this.repopulate();
   }

   void repopulate() throws SQLException {
      this.checkOp(2);
      this.allrows.clear();
      this.rows.clear();
      this.baseRowSet.lock();
      int startRow = this.currentPage * this.maxRows;

      try {
         int rowCount = 0;

         for(int i = startRow; i < this.baseRowSet.allrows.size() && (this.maxRows == 0 || rowCount <= this.maxRows); ++i) {
            CachedRow row = (CachedRow)((CachedRow)this.baseRowSet.allrows.get(i));
            if (!row.isInsertRow()) {
               this.allrows.add(row.createShared(this.metaData));
               ++rowCount;
            }
         }
      } finally {
         this.baseRowSet.unlock();
      }

      this.filter();
      this.pendingConnection = null;
      this.baseRowSet.attach(this);
   }

   public void populate(ResultSetMetaData md) throws SQLException {
      throw new SQLException("populate is not supported by SharedRowSet because SharedRowSet can only populate its data from CachedRowSet object.");
   }

   public void populate(ResultSet rs) throws SQLException {
      throw new SQLException("populate is not supported by SharedRowSet because SharedRowSet can only populate its data from CachedRowSet object.");
   }

   public void execute() throws SQLException {
      this.repopulate();
      this.rowSetChanged();
   }

   public void execute(Connection con) throws SQLException {
      this.execute();
   }

   public String executeAndGuessTableName() throws SQLException {
      this.execute();
      return this.metaData.getQualifiedTableName(1);
   }

   public boolean executeAndGuessTableNameAndPrimaryKeys() throws SQLException {
      this.execute();
      return true;
   }

   void updateMemory() throws SQLException {
      CachedRow r;
      for(Iterator it = this.getCachedRows().iterator(); it.hasNext(); r.acceptChanges()) {
         r = (CachedRow)it.next();
         if (r.isDeletedRow()) {
            this.allrows.remove(r);
            this.baseRowSet.allrows.remove(r.getBaseRow());
         } else if (r.isInsertRow()) {
            CachedRow copy = (CachedRow)r.clone((CachedRowSetMetaData)this.baseRowSet.getMetaData());
            this.baseRowSet.allrows.add(copy);
         } else if (r.isUpdatedRow()) {
            r.getBaseRow().copyFrom(r);
         }
      }

      this.filter();
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
               throw new SyncProviderException("failed");
            }

            if (globalTx) {
               if (this.cachedConnection != null) {
                  try {
                     this.cachedConnection.close();
                  } catch (Exception var27) {
                     throw new SyncProviderException("failed");
                  }

                  this.cachedConnection = null;
               }

               con = this.getConnection();
               if (con == null) {
                  throw new SyncProviderException("failed");
               }

               this.state = this.state.checkOp(9);
               se = this.sync(con);
               if (se != null) {
                  try {
                     tx.setRollbackOnly();
                  } catch (Exception var26) {
                     throw new SyncProviderException("failed");
                  }
               }
            } else {
               con = this.getConnection();
               con.setAutoCommit(false);
               this.state = this.state.checkOp(10);
               this.baseRowSet.lock();

               try {
                  se = this.sync(con);
                  if (se == null) {
                     con.commit();
                     this.updateMemory();
                  } else {
                     con.rollback();
                  }
               } finally {
                  this.baseRowSet.unlock();
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
                  this.state = this.state.checkOp(9);
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
                     throw new SyncProviderException("failed");
                  }

                  if (con.getAutoCommit()) {
                     con.setAutoCommit(false);
                     this.state = this.state.checkOp(10);
                     this.baseRowSet.lock();

                     try {
                        se = this.sync(con);
                        if (se == null) {
                           con.commit();
                           this.updateMemory();
                        } else {
                           con.rollback();
                        }
                     } finally {
                        this.baseRowSet.unlock();
                        con.setAutoCommit(true);
                     }
                  } else {
                     this.state = this.state.checkOp(9);
                     se = this.sync(con);
                     this.pendingConnection = con;
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

   public RowSet createShared() throws SQLException {
      return this.baseRowSet.createShared();
   }

   public CachedRowSet createCopy() throws SQLException {
      return (CachedRowSet)this.createShared();
   }

   public CachedRowSet createCopySchema() throws SQLException {
      return (CachedRowSet)this.createShared();
   }

   public CachedRowSet createCopyNoConstraints() throws SQLException {
      return (CachedRowSet)this.createShared();
   }

   protected Object clone() {
      try {
         return this.createShared();
      } catch (Exception var2) {
         throw new RuntimeException(this + " can not be cloned because of " + var2);
      }
   }

   public void close() {
      super.close();
      this.baseRowSet.detach(this);
   }

   protected void finalize() {
      this.close();
   }

   public void loadXML(XMLInputStream xis) throws IOException, SQLException {
      throw new SQLException("loadXML is not supported by SharedRowSet because SharedRowSet can only populate its data from CachedRowSet object.");
   }

   public void readXml(XMLInputStream xis) throws IOException, SQLException {
      throw new SQLException("readXml is not supported by SharedRowSet because SharedRowSet can only populate its data from CachedRowSet object.");
   }

   public void readXml(Reader reader) throws SQLException {
      throw new SQLException("readXml is not supported by SharedRowSet because SharedRowSet can only populate its data from CachedRowSet object.");
   }

   public void readXml(InputStream iStream) throws SQLException, IOException {
      throw new SQLException("readXml is not supported by SharedRowSet because SharedRowSet can only populate its data from CachedRowSet object.");
   }

   void toDesign() {
      throw new RuntimeException("Design operations are not supported by SharedRowSet because SharedRowSet populate its data from CachedRowSet object rather than DataSource.");
   }

   void toConfigQuery() {
      throw new RuntimeException("ConfigQuery operations are not supported by SharedRowSet because SharedRowSet populate its data from CachedRowSet object rather than DataSource.");
   }

   public boolean previousPage() {
      throw new RuntimeException("Method not implemented");
   }
}
