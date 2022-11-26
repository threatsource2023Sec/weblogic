package weblogic.jdbc.common.internal;

import java.sql.Statement;

public final class StatementHolder {
   private Statement jstmt;
   private StatementCacheKey key;
   private boolean isCached;
   private boolean inUse;
   private int hitCount;
   private boolean to_be_closed = false;
   private boolean was_closed = false;

   public StatementHolder(Statement jstmt, StatementCacheKey key, boolean isCached, boolean inUse) {
      this.jstmt = jstmt;
      this.key = key;
      this.isCached = isCached;
      this.inUse = inUse;
   }

   public final Statement getStatement() {
      return this.jstmt;
   }

   public final void setStatement(Statement st) {
      this.jstmt = st;
   }

   public final String getSQL() {
      return this.key.getSQL();
   }

   public final StatementCacheKey getKey() {
      return this.key;
   }

   public final boolean getCached() {
      return this.isCached;
   }

   public final void setCached() {
      this.isCached = true;
   }

   public final void clearCached() {
      this.isCached = false;
   }

   public final boolean getInUse() {
      return this.inUse;
   }

   public final void setInUse() {
      this.inUse = true;
   }

   public final void clearInUse() {
      this.inUse = false;
   }

   public final void incrementHitCount() {
      ++this.hitCount;
   }

   public final int getHitCount() {
      return this.hitCount;
   }

   public final void setToBeClosed() {
      this.to_be_closed = true;
   }

   public final boolean toBeClosed() {
      return this.to_be_closed;
   }

   public final boolean wasClosed() {
      return this.was_closed;
   }

   public final void closed() {
      this.was_closed = true;
   }
}
