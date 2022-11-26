package weblogic.diagnostics.archive.dbstore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.NoSuchElementException;
import weblogic.utils.time.Timer;

public class JdbcRecordIterator implements Iterator {
   private JdbcDataArchive archive;
   private Connection conn;
   private Statement stmt;
   private ResultSet rs;
   private Object lookaheadRecord;
   private boolean endReached;
   private Timer timer = Timer.createTimer();

   JdbcRecordIterator(JdbcDataArchive archive, String sql) throws SQLException {
      long t0 = this.timer.timestamp();
      this.archive = archive;

      try {
         this.conn = archive.getConnection();
         this.stmt = this.conn.createStatement();
         this.rs = this.stmt.executeQuery(sql);
      } catch (SQLException var7) {
         this.release();
         throw var7;
      }

      long t1 = this.timer.timestamp();
      archive.incrementRecordSeekCount(1L);
      archive.incrementRecordSeekTime(t1 - t0);
   }

   void release() {
      if (this.rs != null) {
         try {
            this.rs.close();
         } catch (SQLException var4) {
         }
      }

      if (this.stmt != null) {
         try {
            this.stmt.close();
         } catch (SQLException var3) {
         }
      }

      if (this.conn != null) {
         try {
            this.conn.close();
         } catch (SQLException var2) {
         }
      }

      this.rs = null;
      this.stmt = null;
      this.conn = null;
      this.lookaheadRecord = null;
      this.endReached = true;
   }

   public boolean hasNext() {
      if (this.lookaheadRecord != null) {
         return true;
      } else {
         long t0 = this.timer.timestamp();
         boolean retVal = false;
         if (!this.endReached && this.rs != null) {
            try {
               retVal = this.rs.next();
               if (retVal) {
                  this.lookaheadRecord = this.archive.getDataRecord(this.rs);
               }
            } catch (Throwable var6) {
               retVal = false;
            }
         }

         if (!retVal) {
            this.release();
         }

         long t1 = this.timer.timestamp();
         this.archive.incrementRecordRetrievalTime(t1 - t0);
         return retVal;
      }
   }

   public Object next() throws NoSuchElementException {
      if (this.lookaheadRecord == null) {
         this.release();
         throw new NoSuchElementException("No more elements");
      } else {
         Object retVal = this.lookaheadRecord;
         this.lookaheadRecord = null;
         this.archive.incrementRetrievedRecordCount(1L);
         return retVal;
      }
   }

   public void remove() throws UnsupportedOperationException, IllegalStateException {
      throw new UnsupportedOperationException("Remove operation not supported");
   }
}
