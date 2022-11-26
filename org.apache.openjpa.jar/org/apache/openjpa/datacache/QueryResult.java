package org.apache.openjpa.datacache;

import java.util.ArrayList;
import java.util.Collection;

public class QueryResult extends ArrayList {
   private final long _ex;

   public QueryResult(QueryKey key, Collection data) {
      super(data);
      if (key.getTimeout() == -1) {
         this._ex = -1L;
      } else {
         this._ex = System.currentTimeMillis() + (long)key.getTimeout();
      }

   }

   public QueryResult(Collection data, long ex) {
      super(data);
      this._ex = ex;
   }

   public long getTimeoutTime() {
      return this._ex;
   }

   public boolean isTimedOut() {
      return this._ex != -1L && this._ex < System.currentTimeMillis();
   }
}
