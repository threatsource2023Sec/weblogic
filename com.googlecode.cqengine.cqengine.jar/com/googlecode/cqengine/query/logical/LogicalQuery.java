package com.googlecode.cqengine.query.logical;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.simple.SimpleQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class LogicalQuery implements Query {
   protected final Collection childQueries;
   private final List logicalQueries = new ArrayList();
   private final List simpleQueries = new ArrayList();
   private final boolean hasLogicalQueries;
   private final boolean hasSimpleQueries;
   private final int size;
   private transient int cachedHashCode = 0;

   public LogicalQuery(Collection childQueries) {
      Iterator var2 = childQueries.iterator();

      while(var2.hasNext()) {
         Query query = (Query)var2.next();
         if (query instanceof LogicalQuery) {
            this.logicalQueries.add((LogicalQuery)query);
         } else {
            if (!(query instanceof SimpleQuery)) {
               throw new IllegalStateException("Unexpected type of query: " + (query == null ? null : query + ", " + query.getClass()));
            }

            this.simpleQueries.add((SimpleQuery)query);
         }
      }

      this.hasLogicalQueries = !this.logicalQueries.isEmpty();
      this.hasSimpleQueries = !this.simpleQueries.isEmpty();
      this.size = childQueries.size();
      this.childQueries = childQueries;
   }

   public List getLogicalQueries() {
      return this.logicalQueries;
   }

   public List getSimpleQueries() {
      return this.simpleQueries;
   }

   public Collection getChildQueries() {
      return this.childQueries;
   }

   public boolean hasLogicalQueries() {
      return this.hasLogicalQueries;
   }

   public boolean hasSimpleQueries() {
      return this.hasSimpleQueries;
   }

   public int size() {
      return this.size;
   }

   public int hashCode() {
      int h = this.cachedHashCode;
      if (h == 0) {
         h = this.calcHashCode();
         if (h == 0) {
            h = -976419167;
         }

         this.cachedHashCode = h;
      }

      return h;
   }

   protected abstract int calcHashCode();
}
