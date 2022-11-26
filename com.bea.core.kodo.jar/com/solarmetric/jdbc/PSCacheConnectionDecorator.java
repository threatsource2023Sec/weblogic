package com.solarmetric.jdbc;

import com.solarmetric.manage.BucketStatistic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.openjpa.lib.jdbc.ConnectionDecorator;
import org.apache.openjpa.lib.jdbc.DataSourceLogs;
import org.apache.openjpa.lib.jdbc.DelegatingConnection;
import org.apache.openjpa.lib.jdbc.DelegatingPreparedStatement;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.ReferenceHashSet;
import org.apache.openjpa.lib.util.concurrent.NullSafeConcurrentHashMap;

public class PSCacheConnectionDecorator implements ConnectionDecorator {
   private static final Localizer _loc = Localizer.forPackage(PSCacheConnectionDecorator.class);
   private final NullSafeConcurrentHashMap _psCache = new NullSafeConcurrentHashMap();
   private final ReferenceHashSet _active = new ReferenceHashSet(2);
   private final ReentrantLock _activeLock = new ReentrantLock();
   private final DataSourceLogs _logs = new DataSourceLogs();
   private int _maxSize = 50;
   private final BucketStatistic _sizeStat;
   private final BucketStatistic _requestsStat;
   private final BucketStatistic _leakedStat;
   private final BucketStatistic _hitsStat;
   private final BucketStatistic _createdStat;
   private final BucketStatistic _redundantStat;
   private final BucketStatistic _overflowStat;
   private final Collection _stats;

   public PSCacheConnectionDecorator() {
      this._sizeStat = new BucketStatistic(_loc.get("pscache.stat.size.name").getMessage(), _loc.get("pscache.stat.size.desc").getMessage(), _loc.get("pscache.stat.size.ord").getMessage(), 1, 2, 5000L);
      this._sizeStat.setValue(0.0);
      this._requestsStat = new BucketStatistic(_loc.get("pscache.stat.requests.name").getMessage(), _loc.get("pscache.stat.requests.desc").getMessage(), _loc.get("pscache.stat.requests.ord").getMessage(), 1, 1, 5000L);
      this._requestsStat.setValue(0.0);
      this._leakedStat = new BucketStatistic(_loc.get("pscache.stat.leaked.name").getMessage(), _loc.get("pscache.stat.leaked.desc").getMessage(), _loc.get("pscache.stat.leaked.ord").getMessage(), 1, 1, 5000L);
      this._leakedStat.setValue(0.0);
      this._hitsStat = new BucketStatistic(_loc.get("pscache.stat.hits.name").getMessage(), _loc.get("pscache.stat.hits.desc").getMessage(), _loc.get("pscache.stat.hits.ord").getMessage(), 1, 1, 5000L);
      this._hitsStat.setValue(0.0);
      this._createdStat = new BucketStatistic(_loc.get("pscache.stat.created.name").getMessage(), _loc.get("pscache.stat.created.desc").getMessage(), _loc.get("pscache.stat.created.ord").getMessage(), 1, 1, 5000L);
      this._createdStat.setValue(0.0);
      this._redundantStat = new BucketStatistic(_loc.get("pscache.stat.redundant.name").getMessage(), _loc.get("pscache.stat.redundant.desc").getMessage(), _loc.get("pscache.stat.redundant.ord").getMessage(), 1, 1, 5000L);
      this._redundantStat.setValue(0.0);
      this._overflowStat = new BucketStatistic(_loc.get("pscache.stat.overflow.name").getMessage(), _loc.get("pscache.stat.overflow.desc").getMessage(), _loc.get("pscache.stat.overflow.ord").getMessage(), 1, 1, 5000L);
      this._overflowStat.setValue(0.0);
      this._stats = Arrays.asList(this._sizeStat, this._requestsStat, this._leakedStat, this._hitsStat, this._createdStat, this._redundantStat, this._overflowStat);
   }

   public void clear() {
      this._sizeStat.setValue(0.0);
      this._psCache.clear();
   }

   public DataSourceLogs getLogs() {
      return this._logs;
   }

   public int getMaxCachedStatements() {
      return this._maxSize;
   }

   public void setMaxCachedStatements(int maxSize) {
      if (maxSize < 0) {
         maxSize = 0;
      }

      this._maxSize = maxSize;
   }

   public Connection decorate(Connection conn) {
      if (this._maxSize == 0) {
         return conn;
      } else {
         if (this._logs.isJDBCEnabled()) {
            StringBuffer buf = new StringBuffer();
            buf.append("prepared statement cache").append(": size=").append(this._psCache.size()).append(", max=").append(this._maxSize).append(", requests=").append((long)this._requestsStat.getValue()).append(", hits=").append((long)this._hitsStat.getValue()).append(", created=").append((long)this._createdStat.getValue()).append(", redundant=").append((long)this._redundantStat.getValue()).append(", overflow=").append((long)this._overflowStat.getValue()).append(", leaked=").append((long)this._leakedStat.getValue());
            this._logs.logJDBC(buf.toString(), (Connection)null);
         }

         Connection conn = new PSCacheConnection(conn);
         this.lockActive();

         try {
            this._active.add(conn);
         } finally {
            this.unlockActive();
         }

         return conn;
      }
   }

   private void lockActive() {
      this._activeLock.lock();
   }

   private void unlockActive() {
      this._activeLock.unlock();
   }

   private PSCachePreparedStatement checkout(Key key) throws SQLException {
      this._requestsStat.increment(1);
      PSCachePreparedStatement ps = (PSCachePreparedStatement)this._psCache.remove(key);
      if (ps != null) {
         this._hitsStat.increment(1);
         ps.checkout();
         return ps;
      } else {
         this._createdStat.increment(1);
         this._sizeStat.increment(1);
         return new PSCachePreparedStatement(key);
      }
   }

   private void checkin(PSCachePreparedStatement ps) {
      ps.checkin();
      PSCachePreparedStatement stmnt = (PSCachePreparedStatement)this._psCache.put(ps.getKey(), ps);
      if (stmnt != null) {
         this._redundantStat.increment(1);

         try {
            stmnt.getDelegate().close();
         } catch (SQLException var12) {
         }
      }

      if (this._psCache.size() > this._maxSize) {
         Iterator itr = this._psCache.randomEntryIterator();

         while(itr.hasNext() && this._psCache.size() > this._maxSize) {
            Map.Entry entry = (Map.Entry)itr.next();
            Key key = (Key)entry.getKey();
            boolean eq = key.conn.equals(ps.getKey().conn);
            if (!eq) {
               this.lockActive();
            }

            try {
               if (!eq && this._active.contains(key.conn) || this._psCache.remove(key) == null) {
                  continue;
               }

               this._sizeStat.decrement(1);
               this._overflowStat.increment(1);
               stmnt = (PSCachePreparedStatement)entry.getValue();
            } finally {
               if (!eq) {
                  this.unlockActive();
               }

            }

            try {
               stmnt.getDelegate().close();
            } catch (SQLException var11) {
            }

            if (this._logs.getJDBCLog().isTraceEnabled()) {
               this._logs.getJDBCLog().trace(_loc.get("ps-overflow", key.sql, String.valueOf(this._maxSize)));
            }
         }

      }
   }

   public Collection getStatistics() {
      return this._stats;
   }

   private class PSCachePreparedStatement extends DelegatingPreparedStatement {
      private Key _key = null;
      private boolean _checkedIn = false;
      private boolean _finalized = false;
      private long _reused = 0L;

      public PSCachePreparedStatement(Key key) throws SQLException {
         super(key.conn.getDelegate().prepareStatement(key.sql, key.rsType, key.rsConcur), key.conn);
         this._key = key;
      }

      public Key getKey() {
         return this._key;
      }

      public void checkout() {
         this._checkedIn = false;
         ++this._reused;
      }

      public void checkin() {
         this._checkedIn = true;
      }

      public void close() throws SQLException {
         if (!this._finalized) {
            try {
               this.clearParameters();
            } catch (Exception var3) {
            }

            try {
               ResultSet rs = super.getResultSet();
               if (rs != null) {
                  rs.close();
               }
            } catch (Throwable var2) {
            }

            PSCacheConnectionDecorator.this.checkin(this);
         }
      }

      protected void finalize() throws Throwable {
         this._finalized = true;
         super.finalize();
         if (!this._checkedIn) {
            PSCacheConnectionDecorator.this._leakedStat.increment(1);
         }

      }

      protected void appendInfo(StringBuffer buf) {
         buf.append(" [reused=").append(this._reused).append("]");
      }
   }

   private class PSCacheConnection extends DelegatingConnection {
      public PSCacheConnection(Connection conn) {
         super(conn);
      }

      protected PreparedStatement prepareStatement(String sql, boolean wrap) throws SQLException {
         return this.prepareStatement(sql, 1003, 1007, wrap);
      }

      protected PreparedStatement prepareStatement(String sql, int rsType, int rsConcur, boolean wrap) throws SQLException {
         return PSCacheConnectionDecorator.this.checkout(new Key(this, sql, rsType, rsConcur));
      }

      public void close() throws SQLException {
         PSCacheConnectionDecorator.this.lockActive();

         try {
            PSCacheConnectionDecorator.this._active.remove(this);
         } finally {
            PSCacheConnectionDecorator.this.unlockActive();
         }

         super.close();
      }
   }

   private static class Key {
      public final PSCacheConnection conn;
      public final String sql;
      public final int rsType;
      public final int rsConcur;

      public Key(PSCacheConnection conn, String sql, int rsType, int rsConcur) {
         this.conn = conn;
         this.sql = sql;
         this.rsType = rsType;
         this.rsConcur = rsConcur;
      }

      public String toString() {
         return this.sql;
      }

      public int hashCode() {
         return (this.conn.hashCode() + this.sql.hashCode() + this.rsType + this.rsConcur) % Integer.MAX_VALUE;
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else {
            Key key = (Key)other;
            return key.conn.equals(this.conn) && key.sql.equals(this.sql) && key.rsType == this.rsType && key.rsConcur == this.rsConcur;
         }
      }
   }
}
