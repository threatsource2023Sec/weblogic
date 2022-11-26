package weblogic.ejb.container.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.cache.QueryCacheElement;
import weblogic.ejb.container.cache.QueryCacheKey;
import weblogic.ejb.container.cmp.rdbms.finders.Finder;
import weblogic.ejb.container.manager.TTLManager;

public class QueryCachingHandler {
   private TTLManager ownerManager;
   private String finderIdentifierOrQuery;
   private Object[] arguments;
   private int maxElements = -1;
   private QueryCacheKey queryCacheKey;
   private Map queryCacheEntries;
   private boolean isDynamic = false;
   private Finder finder;

   public QueryCachingHandler(String finderId, Object[] args, Finder f, TTLManager rom) {
      this.finderIdentifierOrQuery = finderId;
      this.arguments = args;
      this.finder = f;
      this.ownerManager = rom;
      this.isDynamic = false;
      Class returnType = f.getReturnClassType();
      if (Set.class.isAssignableFrom(returnType)) {
         this.queryCacheKey = new QueryCacheKey(finderId, args, rom, 1);
      } else if (Collection.class.isAssignableFrom(returnType)) {
         this.queryCacheKey = new QueryCacheKey(finderId, args, rom, 2);
      } else {
         this.queryCacheKey = new QueryCacheKey(finderId, args, rom, 3);
      }

      this.queryCacheEntries = new HashMap();
   }

   public QueryCachingHandler(String query, int me, Finder f, TTLManager rom) {
      this.finderIdentifierOrQuery = query;
      this.maxElements = me;
      this.finder = f;
      this.ownerManager = rom;
      this.isDynamic = true;
      Class returnType = f.getReturnClassType();
      if (Set.class.isAssignableFrom(returnType)) {
         this.queryCacheKey = new QueryCacheKey(query, me, rom, 1);
      } else if (Collection.class.isAssignableFrom(returnType)) {
         this.queryCacheKey = new QueryCacheKey(query, me, rom, 2);
      } else {
         this.queryCacheKey = new QueryCacheKey(query, me, rom, 3);
      }

      this.queryCacheEntries = new HashMap();
   }

   public QueryCachingHandler(Finder f) {
      this.finder = f;
      this.queryCacheEntries = new HashMap();
   }

   public boolean isQueryCachingEnabledForFinder() {
      return this.finder.isQueryCachingEnabled();
   }

   public void addQueryCachingEntry(TTLManager manager, QueryCacheElement entry) {
      QueryCacheKey qck = this.generateQueryCacheKey(manager);
      if (!qck.equals(this.queryCacheKey)) {
         qck.addSourceQuery(this.queryCacheKey);
         this.queryCacheKey.addDependentQuery(qck);
      }

      this.addToEntryCollection(manager, qck, entry);
   }

   public void addQueryCachingEntry(TTLManager manager, QueryCacheKey qck, QueryCacheElement entry) {
      if (this.finder.isQueryCachingEnabled()) {
         qck.addSourceQuery(this.queryCacheKey);
         this.queryCacheKey.addDependentQuery(qck);
      }

      this.addToEntryCollection(manager, qck, entry);
   }

   public void putInQueryCache() {
      Iterator iterator = this.queryCacheEntries.entrySet().iterator();

      while(iterator.hasNext()) {
         Map.Entry entry = (Map.Entry)iterator.next();
         TTLManager romgr = (TTLManager)entry.getKey();
         Map map = (Map)entry.getValue();
         Iterator i2 = map.entrySet().iterator();

         while(i2.hasNext()) {
            Map.Entry entry2 = (Map.Entry)i2.next();
            QueryCacheKey qck = (QueryCacheKey)entry2.getKey();
            romgr.putInQueryCache(qck, (Collection)entry2.getValue());
         }
      }

   }

   protected QueryCacheKey generateQueryCacheKey(TTLManager mgr) {
      if (mgr.equals(this.ownerManager) && this.queryCacheKey != null) {
         return this.queryCacheKey;
      } else {
         return this.isDynamic ? new QueryCacheKey(this.finderIdentifierOrQuery, this.maxElements, mgr, 0) : new QueryCacheKey(this.finderIdentifierOrQuery, this.arguments, mgr, 0);
      }
   }

   private void addToEntryCollection(TTLManager manager, QueryCacheKey qck, QueryCacheElement entry) {
      Map map = (Map)this.queryCacheEntries.get(manager);
      if (map == null) {
         map = new HashMap();
         this.queryCacheEntries.put(manager, map);
      }

      List list = (List)((Map)map).get(qck);
      if (list == null) {
         list = new ArrayList();
         ((Map)map).put(qck, list);
      }

      ((List)list).add(entry);
   }
}
