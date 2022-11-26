package weblogic.ejb.container.cache;

import java.util.HashSet;
import java.util.Set;
import weblogic.ejb.container.manager.TTLManager;

public class QueryCacheKey {
   public static final int RET_TYPE_UNKNOWN = 0;
   public static final int RET_TYPE_SET = 1;
   public static final int RET_TYPE_COLLECTION = 2;
   public static final int RET_TYPE_SINGLETON = 3;
   private final String methodIdOrQueryString;
   private final Object[] arguments;
   private final int maxelements;
   private final TTLManager manager;
   private int timeoutMillis = 0;
   private int hash;
   private int returnType;
   private Set sourceQueries;
   private Set destinationQueries;
   private Set dependentQueries;

   public QueryCacheKey(String queryString, int me, TTLManager mgr, int ret) {
      this.methodIdOrQueryString = queryString;
      this.arguments = new Object[0];
      this.maxelements = me;
      this.manager = mgr;
      this.returnType = ret;
      this.hash = this.methodIdOrQueryString.hashCode() ^ this.maxelements ^ this.manager.hashCode();
   }

   public QueryCacheKey(String mid, Object[] args, TTLManager mgr, int ret) {
      this.methodIdOrQueryString = mid;
      this.arguments = args == null ? new Object[0] : args;
      this.maxelements = 0;
      this.manager = mgr;
      this.returnType = ret;
      this.hash = this.methodIdOrQueryString.hashCode() ^ this.manager.hashCode();

      for(int i = 0; i < this.arguments.length; ++i) {
         this.hash ^= (i + 1) * this.arguments[i].hashCode();
      }

   }

   public Object[] getArguments() {
      return this.arguments;
   }

   public String getMethodId() {
      return this.methodIdOrQueryString;
   }

   public void setTimeoutMillis(int millis) {
      this.timeoutMillis = millis;
   }

   public int getTimeoutMillis() {
      return this.timeoutMillis;
   }

   public int getReturnType() {
      return this.returnType;
   }

   public TTLManager getOwnerManager() {
      return this.manager;
   }

   public void addSourceQuery(QueryCacheKey key) {
      if (this.sourceQueries == null) {
         this.sourceQueries = new HashSet();
      }

      if (!key.equals(this)) {
         this.sourceQueries.add(key);
      }

   }

   public void addDestinationQuery(QueryCacheKey key) {
      if (this.destinationQueries == null) {
         this.destinationQueries = new HashSet();
      }

      if (!key.equals(this)) {
         this.destinationQueries.add(key);
      }

   }

   public void addDependentQuery(QueryCacheKey key) {
      if (this.dependentQueries == null) {
         this.dependentQueries = new HashSet();
      }

      if (!key.equals(this)) {
         this.dependentQueries.add(key);
      }

   }

   protected Set getSourceQueries() {
      return this.sourceQueries;
   }

   protected Set getDestinationQueries() {
      return this.destinationQueries;
   }

   protected Set getDependentQueries() {
      return this.dependentQueries;
   }

   public int hashCode() {
      return this.hash;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other instanceof QueryCacheKey) {
         QueryCacheKey key = (QueryCacheKey)other;
         if (this.arguments.length != key.arguments.length) {
            return false;
         } else if (!this.manager.equals(key.manager)) {
            return false;
         } else if (!this.methodIdOrQueryString.equals(key.methodIdOrQueryString)) {
            return false;
         } else if (this.maxelements != key.maxelements) {
            return false;
         } else {
            for(int i = 0; i < this.arguments.length; ++i) {
               if (!this.arguments[i].equals(key.arguments[i])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      String str = this.methodIdOrQueryString;

      for(int i = 0; i < this.arguments.length; ++i) {
         if (i == 0) {
            str = str + ", L[";
         }

         str = str + this.arguments[i] + ";";
      }

      str = str + "], " + this.maxelements + ", " + this.timeoutMillis + ", " + this.manager;
      return str;
   }
}
