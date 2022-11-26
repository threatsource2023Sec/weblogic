package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.SchemaGroup;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.StoreException;

public abstract class AbstractJDBCSeq implements JDBCSeq {
   protected int type = 0;
   protected Object current = null;
   private static ThreadLocal _outerTransaction = new ThreadLocal();

   public void setType(int type) {
      this.type = type;
   }

   public Object next(StoreContext ctx, ClassMetaData meta) {
      JDBCStore store = this.getStore(ctx);

      try {
         Object currentLocal = this.nextInternal(store, (ClassMapping)meta);
         this.current = currentLocal;
         return currentLocal;
      } catch (OpenJPAException var5) {
         throw var5;
      } catch (SQLException var6) {
         throw SQLExceptions.getStore(var6, store.getDBDictionary());
      } catch (Exception var7) {
         throw new StoreException(var7);
      }
   }

   public Object current(StoreContext ctx, ClassMetaData meta) {
      JDBCStore store = this.getStore(ctx);

      try {
         return this.currentInternal(store, (ClassMapping)meta);
      } catch (OpenJPAException var5) {
         throw var5;
      } catch (SQLException var6) {
         throw SQLExceptions.getStore(var6, store.getDBDictionary());
      } catch (Exception var7) {
         throw new StoreException(var7);
      }
   }

   public void allocate(int additional, StoreContext ctx, ClassMetaData meta) {
      JDBCStore store = this.getStore(ctx);

      try {
         this.allocateInternal(additional, store, (ClassMapping)meta);
      } catch (OpenJPAException var6) {
         throw var6;
      } catch (SQLException var7) {
         throw SQLExceptions.getStore(var7, store.getDBDictionary());
      } catch (Exception var8) {
         throw new StoreException(var8);
      }
   }

   public void addSchema(ClassMapping mapping, SchemaGroup group) {
   }

   public void close() {
   }

   protected abstract Object nextInternal(JDBCStore var1, ClassMapping var2) throws Exception;

   public abstract JDBCConfiguration getConfiguration();

   protected Object currentInternal(JDBCStore store, ClassMapping mapping) throws Exception {
      return this.current;
   }

   protected void allocateInternal(int additional, JDBCStore store, ClassMapping mapping) throws Exception {
   }

   private JDBCStore getStore(StoreContext ctx) {
      return (JDBCStore)ctx.getStoreManager().getInnermostDelegate();
   }

   protected Connection getConnection(JDBCStore store) throws SQLException {
      if (this.type != 2 && this.type != 3) {
         if (this.suspendInJTA()) {
            try {
               TransactionManager tm = this.getConfiguration().getManagedRuntimeInstance().getTransactionManager();
               _outerTransaction.set(tm.suspend());
               tm.begin();
               return store.getConnection();
            } catch (Exception var5) {
               throw new StoreException(var5);
            }
         } else {
            JDBCConfiguration conf = store.getConfiguration();
            DataSource ds = conf.getDataSource2(store.getContext());
            Connection conn = ds.getConnection();
            if (conn.getAutoCommit()) {
               conn.setAutoCommit(false);
            }

            return conn;
         }
      } else {
         return store.getConnection();
      }
   }

   protected void closeConnection(Connection conn) {
      if (conn != null) {
         if (this.type != 2 && this.type != 3) {
            if (this.suspendInJTA()) {
               try {
                  TransactionManager tm = this.getConfiguration().getManagedRuntimeInstance().getTransactionManager();
                  tm.commit();

                  try {
                     conn.close();
                  } catch (SQLException var23) {
                  }

                  Transaction outerTxn = (Transaction)_outerTransaction.get();
                  if (outerTxn != null) {
                     tm.resume(outerTxn);
                  }
               } catch (Exception var24) {
                  throw new StoreException(var24);
               } finally {
                  _outerTransaction.set((Object)null);
               }
            } else {
               try {
                  conn.commit();
               } catch (SQLException var21) {
                  throw SQLExceptions.getStore(var21);
               } finally {
                  try {
                     conn.close();
                  } catch (SQLException var20) {
                  }

               }
            }

         }
      }
   }

   protected boolean suspendInJTA() {
      return this.getConfiguration().isConnectionFactoryModeManaged() && this.getConfiguration().getConnectionFactory2() == null;
   }
}
