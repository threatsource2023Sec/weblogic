package weblogic.ejb.container.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.monitoring.QueryCacheRuntimeMBeanImpl;
import weblogic.ejb.container.persistence.spi.EloWrapper;
import weblogic.ejb.container.persistence.spi.EoWrapper;
import weblogic.ejb20.utils.OrderedSet;
import weblogic.management.ManagementException;
import weblogic.management.runtime.QueryCacheRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;

public class QueryCache implements weblogic.ejb.container.interfaces.QueryCache {
   private static final DebugLogger debugLogger;
   private final ConcurrentMap queryAxis = new ConcurrentHashMap();
   private final ConcurrentMap primaryKeyAxis = new ConcurrentHashMap();
   private final LRUList lrulist;
   private final int capacity;
   private QueryCacheRuntimeMBeanImpl rtmMBean;
   private final String name;

   public QueryCache(String name, int capacity) {
      this.name = name;
      this.capacity = capacity;
      this.lrulist = new LRUList(name, capacity);
      if (debugLogger.isDebugEnabled()) {
         debug(name, "Capacity: " + capacity);
      }

   }

   public QueryCacheRuntimeMBean createRuntimeMBean(String cacheName, RuntimeMBean parent) throws ManagementException {
      this.rtmMBean = new QueryCacheRuntimeMBeanImpl(cacheName, parent);
      return this.rtmMBean;
   }

   public void setRuntimeMBean(QueryCacheRuntimeMBean rtmMBean) {
      this.rtmMBean = (QueryCacheRuntimeMBeanImpl)rtmMBean;
   }

   public Object get(Object txOrThread, QueryCacheKey qkey, boolean isLocal, boolean isSet) throws InternalException {
      this.rtmMBean.incrementCacheAccessCount();
      if (debugLogger.isDebugEnabled()) {
         debug(this.name, "Get: " + qkey + " at: " + System.currentTimeMillis());
      }

      QueryAxisNode qn = (QueryAxisNode)this.queryAxis.get(qkey);
      if (debugLogger.isDebugEnabled()) {
         debug(this.name, "QueryAxis size: " + this.queryAxis.size());
      }

      if (qn == null) {
         if (debugLogger.isDebugEnabled()) {
            debug(this.name, "Cache miss: Not found: " + qkey);
         }

         this.rtmMBean.incrementCacheMissCount();
         if (debugLogger.isDebugEnabled()) {
            debug(this.name, "Got null QueryAxisNode for " + qkey);
         }

         return null;
      } else {
         Object o = qn.get(txOrThread, isLocal, isSet);
         if (o == null) {
            qn.delink();
            this.lrulist.remove(qn);
            if (debugLogger.isDebugEnabled()) {
               debug(this.name, "Cache miss: Timed out: " + qkey);
            }

            if (debugLogger.isDebugEnabled()) {
               debug(this.name, "Got null from QueryAxisNode for " + qkey);
            }

            return null;
         } else {
            this.lrulist.addMRU(qn);
            this.rtmMBean.incrementCacheHitCount();
            if (debugLogger.isDebugEnabled()) {
               debug(this.name, "Returning " + o.getClass() + " for " + qkey);
            }

            return o;
         }
      }
   }

   public boolean put(QueryCacheKey qkey, Collection elements) {
      if (debugLogger.isDebugEnabled()) {
         debug(this.name, "Put(coll): " + qkey + " size: " + elements.size() + " at: " + System.currentTimeMillis());
      }

      QueryAxisNode qn = new QueryAxisNode(qkey, this);
      QueryAxisNode nqn = qn.set(elements);
      this.lrulist.addMRU(nqn);
      if (debugLogger.isDebugEnabled()) {
         debug(this.name, "Putting collection for " + qkey + " " + (qn == nqn));
      }

      return qn == nqn;
   }

   public boolean put(QueryCacheKey qkey, QueryCacheElement element) {
      if (debugLogger.isDebugEnabled()) {
         debug(this.name, "Put: " + qkey + " at: " + System.currentTimeMillis());
      }

      QueryAxisNode qn = new QueryAxisNode(qkey, this);
      QueryAxisNode nqn = qn.set(element);
      this.lrulist.addMRU(nqn);
      if (debugLogger.isDebugEnabled()) {
         debug(this.name, "Putting singleton for " + qkey + " " + (qn == nqn));
      }

      return qn == nqn;
   }

   public void invalidate(CacheKey key) {
      this.invalidate(new QueryCacheElement(key));
   }

   public void invalidateAll() {
      Iterator var1 = this.primaryKeyAxis.keySet().iterator();

      while(var1.hasNext()) {
         QueryCacheElement qce = (QueryCacheElement)var1.next();
         this.invalidate(qce);
      }

   }

   protected void invalidate(QueryCacheKey qkey) {
      QueryAxisNode qnode = (QueryAxisNode)this.queryAxis.remove(qkey);
      if (qnode != null) {
         qnode.delink();
         this.lrulist.remove(qnode);
      }

   }

   protected boolean enrollQuery(Object txOrThread, QueryCacheKey qkey) throws InternalException {
      QueryAxisNode qnode = (QueryAxisNode)this.queryAxis.get(qkey);
      if (qnode == null) {
         return false;
      } else {
         boolean ret = qnode.enroll(txOrThread);
         if (ret) {
            this.lrulist.addMRU(qnode);
         } else {
            qnode.delink();
            this.lrulist.remove(qnode);
         }

         if (debugLogger.isDebugEnabled()) {
            debug(this.name, "Enrolling for " + qkey + ": " + ret);
         }

         return ret;
      }
   }

   private void invalidate(QueryCacheElement element) {
      PrimaryKeyAxisNode ckn = (PrimaryKeyAxisNode)this.primaryKeyAxis.remove(element);
      if (ckn != null) {
         ckn.clearAll();
      }
   }

   private static void debug(String name, String s) {
      debugLogger.debug("[QueryCache:" + name + "] " + s);
   }

   static {
      debugLogger = EJBDebugService.cachingLogger;
   }

   private static class LRUList {
      private QueryAxisNode mostRecent;
      private QueryAxisNode leastRecent;
      private String name;
      private final int capacity;
      private int size;

      public LRUList(String n, int c) {
         this.capacity = c;
         this.size = 0;
         this.name = n;
         if (QueryCache.debugLogger.isDebugEnabled()) {
            QueryCache.debug(this.name, "Capacity is: " + this.capacity);
         }

      }

      public int size() {
         return this.size;
      }

      public void addMRU(QueryAxisNode newnode) {
         if (this.capacity != 0) {
            QueryAxisNode evicted = null;
            synchronized(this) {
               if (newnode == this.mostRecent) {
                  return;
               }

               if (this.mostRecent == null) {
                  this.mostRecent = newnode;
                  this.mostRecent.older = this.leastRecent;
                  this.mostRecent.newer = null;
                  this.leastRecent = this.mostRecent;
                  this.leastRecent.newer = this.mostRecent;
                  this.leastRecent.older = null;
                  this.size = 1;
                  return;
               }

               if (newnode == this.mostRecent || newnode == this.leastRecent || newnode.older != null && newnode.newer != null) {
                  if (newnode == this.leastRecent) {
                     this.leastRecent = newnode.newer;
                     this.leastRecent.older = null;
                     newnode.newer = null;
                  } else {
                     newnode.newer.older = newnode.older;
                     newnode.older.newer = newnode.newer;
                     newnode.newer = null;
                     newnode.older = null;
                  }
               } else if (++this.size > this.capacity) {
                  evicted = this.shrink();
               }

               newnode.older = this.mostRecent;
               this.mostRecent.newer = newnode;
               this.mostRecent = newnode;
            }

            if (evicted != null) {
               evicted.delink();
            }

         }
      }

      public void remove(QueryAxisNode qn) {
         if (this.capacity != 0) {
            synchronized(this) {
               if (qn == this.mostRecent || qn == this.leastRecent || qn.older != null && qn.newer != null) {
                  if (qn.newer != null) {
                     qn.newer.older = qn.older;
                  }

                  if (qn.older != null) {
                     qn.older.newer = qn.newer;
                  }

                  if (qn == this.mostRecent) {
                     this.mostRecent = qn.older;
                     if (this.mostRecent != null) {
                        this.mostRecent.newer = null;
                     }
                  }

                  if (qn == this.leastRecent) {
                     this.leastRecent = qn.newer;
                     if (this.leastRecent != null) {
                        this.leastRecent.older = null;
                     }
                  }

                  qn.newer = null;
                  qn.older = null;
                  --this.size;
               }

            }
         }
      }

      private QueryAxisNode shrink() {
         QueryAxisNode qn = this.leastRecent;
         if (this.leastRecent.newer != null) {
            this.leastRecent.newer.older = null;
         } else if (QueryCache.debugLogger.isDebugEnabled()) {
            QueryCache.debug(this.name, "No newer");
         }

         this.leastRecent = this.leastRecent.newer;
         --this.size;
         if (QueryCache.debugLogger.isDebugEnabled()) {
            QueryCache.debug(this.name, "LRUList size is " + this.size);
         }

         return qn;
      }
   }

   private static class QueryElementNode {
      public QueryCacheKey qckey;
      public QueryCacheElement qcelement;
      public QueryElementNode up;
      public QueryElementNode left;
      public QueryElementNode right;
      public PrimaryKeyAxisNode qcelnode;

      public QueryElementNode(QueryCacheKey q, QueryCacheElement c) {
         this.qckey = q;
         this.qcelement = c;
      }
   }

   private static class PrimaryKeyAxisNode {
      private QueryElementNode head;
      private QueryElementNode tail;
      private QueryCache cache;
      private QueryCacheElement key;
      private int size = 0;

      public PrimaryKeyAxisNode(QueryCacheElement k, QueryCache c) {
         this.key = k;
         this.cache = c;
      }

      public synchronized void add(QueryElementNode qce) {
         try {
            if (this.head == null) {
               this.head = qce;
               this.tail = this.head;
               this.tail.left = this.head;
               this.tail.right = null;
               qce.left = null;
               qce.right = null;
               ++this.size;
               return;
            }

            this.tail.right = qce;
            qce.left = this.tail;
            this.tail = qce;
            this.tail.right = null;
            ++this.size;
         } finally {
            if (QueryCache.debugLogger.isDebugEnabled()) {
               this.validate("add");
            }

         }

      }

      public void clearAll() {
         List list = new ArrayList();
         synchronized(this) {
            try {
               for(QueryElementNode qce = this.head; qce != null; qce = qce.right) {
                  QueryAxisNode qn = (QueryAxisNode)this.cache.queryAxis.remove(qce.qckey);
                  if (qn != null) {
                     list.add(qn);
                  }
               }

               this.size = 0;
               this.head = null;
            } finally {
               if (QueryCache.debugLogger.isDebugEnabled()) {
                  this.validate("clearAll");
               }

            }
         }

         for(int i = 0; i < list.size(); ++i) {
            QueryAxisNode qn = (QueryAxisNode)list.get(i);
            qn.delink();
            this.cache.lrulist.remove(qn);
         }

      }

      public void delink(QueryElementNode qce) {
         synchronized(this) {
            try {
               if (qce.left != null) {
                  qce.left.right = qce.right;
               } else {
                  this.head = qce.right;
               }

               if (qce.right != null) {
                  qce.right.left = qce.left;
               } else {
                  this.tail = qce.left;
               }

               qce.left = null;
               qce.right = null;
               qce.qcelnode = null;
               --this.size;
            } finally {
               if (QueryCache.debugLogger.isDebugEnabled()) {
                  this.validate("delink");
               }

            }
         }

         if (this.size == 0) {
            synchronized(this.cache.primaryKeyAxis) {
               PrimaryKeyAxisNode o = (PrimaryKeyAxisNode)this.cache.primaryKeyAxis.remove(this.key);
               if (o != null && o != this) {
                  this.cache.primaryKeyAxis.put(this.key, o);
               }
            }
         }

      }

      private void validate(String op) {
         int i = 0;

         for(QueryElementNode qce = this.head; qce != null; qce = qce.right) {
            ++i;
         }

         if (i != this.size) {
            StringBuilder sb = new StringBuilder();
            sb.append("PrimaryKeyAxisNode." + op + " FAIL: got " + i + ", expected " + this.size);

            for(QueryElementNode qce = this.head; qce != null; qce = qce.right) {
               sb.append(qce.qckey + ", " + qce.qcelement);
            }

            QueryCache.debug(this.cache.name, sb.toString());
         }

      }
   }

   private static class QueryAxisNode {
      public QueryCacheKey key;
      private QueryElementNode head;
      private Lock rlock;
      private Lock wlock;
      private QueryCache cache;
      private boolean containsEmptyResult = false;
      private int size;
      private long expirationTime = 0L;
      private Set sourceQueries;
      private Set destinationQueries;
      private Set dependentQueries;
      public QueryAxisNode older;
      public QueryAxisNode newer;

      public QueryAxisNode(QueryCacheKey k, QueryCache c) {
         this.key = k;
         this.cache = c;
         this.sourceQueries = k.getSourceQueries();
         this.destinationQueries = k.getDestinationQueries();
         this.dependentQueries = k.getDependentQueries();
         ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
         this.rlock = lock.readLock();
         this.wlock = lock.writeLock();
         if (k.getTimeoutMillis() != 0) {
            this.expirationTime = System.currentTimeMillis() + (long)k.getTimeoutMillis();
            if (QueryCache.debugLogger.isDebugEnabled()) {
               QueryCache.debug(this.cache.name, "Expiration of: " + this.key + " at: " + this.expirationTime);
            }
         }

      }

      public QueryAxisNode set(QueryCacheElement element) {
         this.wlock.lock();

         try {
            QueryAxisNode qn = (QueryAxisNode)this.cache.queryAxis.putIfAbsent(this.key, this);
            if (qn != null && qn != this && !qn.hasTimedOut()) {
               if (QueryCache.debugLogger.isDebugEnabled()) {
                  QueryCache.debug(this.cache.name, "Cache put fail: Already exists: " + this.key);
               }

               if (this.sourceQueries != null) {
                  if (qn.sourceQueries == null) {
                     qn.sourceQueries = this.sourceQueries;
                  } else {
                     qn.sourceQueries.addAll(this.sourceQueries);
                  }
               }

               QueryAxisNode var8 = qn;
               return var8;
            }

            this.head = new QueryElementNode(this.key, element);
            if (element.isInvalidatable()) {
               PrimaryKeyAxisNode nckn = new PrimaryKeyAxisNode(this.head.qcelement, this.cache);
               PrimaryKeyAxisNode ckn = (PrimaryKeyAxisNode)this.cache.primaryKeyAxis.putIfAbsent(this.head.qcelement, nckn);
               if (ckn != null) {
                  nckn = ckn;
               }

               this.head.qcelnode = nckn;
               nckn.add(this.head);
            }

            this.size = 1;
         } finally {
            if (QueryCache.debugLogger.isDebugEnabled()) {
               this.validate("set(QueryCacheElement)");
            }

            this.wlock.unlock();
         }

         if (QueryCache.debugLogger.isDebugEnabled()) {
            QueryCache.debug(this.cache.name, "Size: " + this.cache.lrulist.size());
         }

         this.cache.rtmMBean.incrementTotalCachedQueriesCount();
         return this;
      }

      public QueryAxisNode set(Collection keys) {
         this.wlock.lock();

         try {
            QueryAxisNode qn = (QueryAxisNode)this.cache.queryAxis.putIfAbsent(this.key, this);
            QueryAxisNode var14;
            if (qn != null && qn != this) {
               if (!qn.hasTimedOut()) {
                  if (QueryCache.debugLogger.isDebugEnabled()) {
                     QueryCache.debug(this.cache.name, "Cache put fail: Already exists: " + this.key);
                  }

                  if (!qn.containsEmptyResult && this.sourceQueries != null) {
                     if (qn.sourceQueries == null) {
                        qn.sourceQueries = this.sourceQueries;
                     } else {
                        qn.sourceQueries.addAll(this.sourceQueries);
                     }
                  }

                  QueryAxisNode var15 = qn;
                  return var15;
               }

               Class threadClass = Thread.currentThread().getClass();
               if (threadClass.getSimpleName().contains("EagerRefresh")) {
                  if (QueryCache.debugLogger.isDebugEnabled()) {
                     QueryCache.debug(this.cache.name, "Cache put skip: EagerRefresh: " + this.key);
                  }

                  var14 = qn;
                  return var14;
               }

               if (QueryCache.debugLogger.isDebugEnabled()) {
                  QueryCache.debug(this.cache.name, "Replacing timed out result: " + this.key);
               }

               qn.delink();
               QueryAxisNode var10000 = (QueryAxisNode)this.cache.queryAxis.putIfAbsent(this.key, this);
            }

            this.size = 0;
            QueryElementNode qce = null;
            this.head = null;
            if (keys.size() == 0) {
               if (QueryCache.debugLogger.isDebugEnabled()) {
                  QueryCache.debug(this.cache.name, "Setting empty result: " + this.key);
               }

               this.containsEmptyResult = true;
               if (QueryCache.debugLogger.isDebugEnabled()) {
                  QueryCache.debug(this.cache.name, "Size: " + this.cache.lrulist.size());
               }

               this.cache.rtmMBean.incrementTotalCachedQueriesCount();
               var14 = this;
               return var14;
            }

            Iterator iterator = keys.iterator();
            HashMap map = new HashMap();

            for(boolean includableFound = false; iterator.hasNext(); ++this.size) {
               QueryCacheElement ck = (QueryCacheElement)iterator.next();
               if (this.head == null) {
                  this.head = new QueryElementNode(this.key, ck);
                  qce = this.head;
               } else {
                  qce.up = new QueryElementNode(this.key, ck);
                  qce = qce.up;
               }

               if (this.key.getReturnType() == 3 && ck.isIncludable()) {
                  if (includableFound) {
                     throw new AssertionError("Multiple includables found for singleton return query ");
                  }

                  includableFound = true;
               }

               if (!map.containsKey(ck) && ck.isInvalidatable()) {
                  PrimaryKeyAxisNode nckn = new PrimaryKeyAxisNode(qce.qcelement, this.cache);
                  PrimaryKeyAxisNode ckn = (PrimaryKeyAxisNode)this.cache.primaryKeyAxis.putIfAbsent(qce.qcelement, nckn);
                  if (ckn != null) {
                     nckn = ckn;
                  }

                  qce.qcelnode = nckn;
                  nckn.add(qce);
                  map.put(ck, ck);
               }
            }
         } finally {
            if (QueryCache.debugLogger.isDebugEnabled()) {
               this.validate("set(Collection)");
            }

            this.wlock.unlock();
         }

         if (QueryCache.debugLogger.isDebugEnabled()) {
            QueryCache.debug(this.cache.name, "Size: " + this.cache.lrulist.size());
         }

         this.cache.rtmMBean.incrementTotalCachedQueriesCount();
         return this;
      }

      public Object get(Object txOrThread, boolean isLocal, boolean isSet) throws InternalException {
         if (this.hasTimedOut()) {
            if (QueryCache.debugLogger.isDebugEnabled()) {
               QueryCache.debug(this.cache.name, "Cache miss: Timeout: " + this.key);
            }

            this.cache.rtmMBean.incrementCacheMissByTimeoutCount();
            return null;
         } else {
            this.rlock.lock();

            try {
               Object ret;
               if (!this.containsEmptyResult && this.head == null) {
                  if (QueryCache.debugLogger.isDebugEnabled()) {
                     QueryCache.debug(this.cache.name, "Cache miss: Invalidatad or evicted: " + this.key);
                  }

                  ret = null;
                  return ret;
               } else {
                  ret = null;
                  Object coll;
                  if (this.key.getReturnType() == 3) {
                     ret = this.getFirstIncludable().getReturnValue(txOrThread, isLocal);
                     if (ret == null) {
                        if (QueryCache.debugLogger.isDebugEnabled()) {
                           QueryCache.debug(this.cache.name, "Cache miss: Single-valued return is null: " + this.key);
                        }

                        this.cache.rtmMBean.incrementCacheMissByBeanEvictionCount();
                     }

                     coll = ret;
                     return coll;
                  } else {
                     coll = null;
                     if (this.key.getReturnType() == 1) {
                        coll = new OrderedSet();
                     } else {
                        coll = new ArrayList(this.size);
                     }

                     if (this.containsEmptyResult) {
                        if (QueryCache.debugLogger.isDebugEnabled()) {
                           QueryCache.debug(this.cache.name, "Returning empty result: " + this.key);
                        }

                        Object var13 = coll;
                        return var13;
                     } else {
                        QueryElementNode qce = this.head;

                        for(int i = 0; i < this.size; ++i) {
                           if (qce.qcelement.isIncludable()) {
                              Object val = qce.qcelement.getReturnValue(txOrThread, isLocal);
                              if (val == null) {
                                 if (QueryCache.debugLogger.isDebugEnabled()) {
                                    QueryCache.debug(this.cache.name, "Cache miss: Multi-valued return at " + i + " is null: " + this.key);
                                 }

                                 this.cache.rtmMBean.incrementCacheMissByBeanEvictionCount();
                                 Object var9 = null;
                                 return var9;
                              }

                              if (this.key.getReturnType() == 1) {
                                 if (isLocal) {
                                    val = new EloWrapper((EJBLocalObject)val);
                                 } else {
                                    val = new EoWrapper((EJBObject)val);
                                 }
                              }

                              if (val == QueryCache.NULL_VALUE) {
                                 ((Collection)coll).add((Object)null);
                              } else {
                                 ((Collection)coll).add(val);
                              }
                           }

                           qce = qce.up;
                        }

                        if (((Collection)coll).isEmpty()) {
                           if (QueryCache.debugLogger.isDebugEnabled()) {
                              StringBuilder sb = new StringBuilder();
                              qce = this.head;

                              for(int j = 0; j < this.size; ++j) {
                                 sb.append("For key: ").append(this.key).append(", Unincludable: ").append(qce.qcelement).append("\n");
                                 qce = qce.up;
                              }

                              QueryCache.debug(this.cache.name, sb.toString());
                           }

                           throw new AssertionError("multi-valued get, but no includable results");
                        } else {
                           Object var14;
                           if (!this.enrollDestinationQueries(txOrThread)) {
                              if (QueryCache.debugLogger.isDebugEnabled()) {
                                 QueryCache.debug(this.cache.name, "Cache miss: destination query enrollment failure: " + this.key);
                              }

                              this.cache.rtmMBean.incrementCacheMissByRelatedQueryMissCount();
                              var14 = null;
                              return var14;
                           } else if (!this.enrollDependentQueries(txOrThread)) {
                              if (QueryCache.debugLogger.isDebugEnabled()) {
                                 QueryCache.debug(this.cache.name, "Cache miss: peer query enrollment failure: " + this.key);
                              }

                              this.cache.rtmMBean.incrementCacheMissByDependentQueryMissCount();
                              var14 = null;
                              return var14;
                           } else {
                              var14 = coll;
                              return var14;
                           }
                        }
                     }
                  }
               }
            } finally {
               if (QueryCache.debugLogger.isDebugEnabled()) {
                  this.validate("get");
               }

               this.rlock.unlock();
            }
         }
      }

      public boolean enroll(Object txOrThread) throws InternalException {
         if (this.hasTimedOut()) {
            if (QueryCache.debugLogger.isDebugEnabled()) {
               QueryCache.debug(this.cache.name, "Cache enroll fail: Timeout for " + this.key);
            }

            this.cache.rtmMBean.incrementCacheMissByTimeoutCount();
            return false;
         } else {
            this.rlock.lock();

            try {
               boolean var8;
               if (this.head == null) {
                  if (QueryCache.debugLogger.isDebugEnabled()) {
                     QueryCache.debug(this.cache.name, "Cache enroll fail: null head for " + this.key);
                  }

                  var8 = false;
                  return var8;
               } else if (this.key.getReturnType() == 3) {
                  if (!this.getFirstIncludable().enroll(txOrThread)) {
                     if (QueryCache.debugLogger.isDebugEnabled()) {
                        QueryCache.debug(this.cache.name, "Cache single-value enroll fail: for " + this.key);
                     }

                     this.cache.rtmMBean.incrementCacheMissByBeanEvictionCount();
                     var8 = false;
                     return var8;
                  } else {
                     var8 = true;
                     return var8;
                  }
               } else {
                  QueryElementNode qce = this.head;

                  for(int i = 0; i < this.size; ++i) {
                     if (!qce.qcelement.enroll(txOrThread)) {
                        if (QueryCache.debugLogger.isDebugEnabled()) {
                           QueryCache.debug(this.cache.name, "Cache multi-value enroll fail: for " + this.key);
                        }

                        this.cache.rtmMBean.incrementCacheMissByBeanEvictionCount();
                        boolean var4 = false;
                        return var4;
                     }

                     qce = qce.up;
                  }

                  boolean var9;
                  if (!this.enrollDestinationQueries(txOrThread)) {
                     this.cache.rtmMBean.incrementCacheMissByRelatedQueryMissCount();
                     var9 = false;
                     return var9;
                  } else if (!this.enrollDependentQueries(txOrThread)) {
                     this.cache.rtmMBean.incrementCacheMissByDependentQueryMissCount();
                     var9 = false;
                     return var9;
                  } else {
                     var9 = true;
                     return var9;
                  }
               }
            } finally {
               if (QueryCache.debugLogger.isDebugEnabled()) {
                  this.validate("get");
               }

               this.rlock.unlock();
            }
         }
      }

      public void delink() {
         if (QueryCache.debugLogger.isDebugEnabled()) {
            QueryCache.debug(this.cache.name, "Size: " + this.cache.lrulist.size());
         }

         this.cache.rtmMBean.decrementTotalCachedQueriesCount();
         this.wlock.lock();

         try {
            synchronized(this.cache.queryAxis) {
               QueryAxisNode o = (QueryAxisNode)this.cache.queryAxis.remove(this.key);
               if (o != null && o != this) {
                  this.cache.queryAxis.put(this.key, o);
               }
            }

            for(QueryElementNode qce = this.head; qce != null; qce = qce.up) {
               if (qce.qcelnode != null) {
                  qce.qcelnode.delink(qce);
               }
            }

            this.head = null;
            this.size = 0;
         } finally {
            if (QueryCache.debugLogger.isDebugEnabled()) {
               this.validate("delink");
            }

            this.wlock.unlock();
         }

         this.invalidateSourceQueries();
         this.invalidateDependentQueries();
      }

      private boolean enrollDestinationQueries(Object txOrThread) throws InternalException {
         if (this.destinationQueries != null) {
            Iterator iterator = this.destinationQueries.iterator();

            while(iterator.hasNext()) {
               QueryCacheKey qkey = (QueryCacheKey)iterator.next();
               QueryCache otherCache = (QueryCache)qkey.getOwnerManager().getQueryCache();
               if (!otherCache.enrollQuery(txOrThread, qkey)) {
                  return false;
               }
            }
         }

         return true;
      }

      private boolean enrollDependentQueries(Object txOrThread) throws InternalException {
         if (this.dependentQueries != null) {
            Iterator iterator = this.dependentQueries.iterator();

            while(iterator.hasNext()) {
               QueryCacheKey qkey = (QueryCacheKey)iterator.next();
               QueryCache otherCache = (QueryCache)qkey.getOwnerManager().getQueryCache();
               if (!otherCache.enrollQuery(txOrThread, qkey)) {
                  return false;
               }
            }
         }

         return true;
      }

      private void invalidateSourceQueries() {
         if (this.sourceQueries != null) {
            Iterator iterator = this.sourceQueries.iterator();

            while(iterator.hasNext()) {
               QueryCacheKey qkey = (QueryCacheKey)iterator.next();
               QueryCache otherCache = (QueryCache)qkey.getOwnerManager().getQueryCache();
               otherCache.invalidate(qkey);
            }
         }

      }

      private void invalidateDependentQueries() {
         if (this.dependentQueries != null) {
            Iterator iterator = this.dependentQueries.iterator();

            while(iterator.hasNext()) {
               QueryCacheKey qkey = (QueryCacheKey)iterator.next();
               QueryCache otherCache = (QueryCache)qkey.getOwnerManager().getQueryCache();
               otherCache.invalidate(qkey);
            }
         }

      }

      private boolean hasTimedOut() {
         return this.expirationTime != 0L && System.currentTimeMillis() > this.expirationTime;
      }

      private QueryCacheElement getFirstIncludable() {
         for(QueryElementNode qce = this.head; qce != null; qce = qce.up) {
            if (qce.qcelement.isIncludable()) {
               return qce.qcelement;
            }
         }

         return null;
      }

      private void validate(String op) {
         int i = 0;

         for(QueryElementNode qce = this.head; qce != null; qce = qce.up) {
            ++i;
         }

         if (i != this.size) {
            StringBuilder sb = new StringBuilder();
            sb.append("QueryAxisNode.").append(op).append(" FAIL: got ").append(i).append(", expected ").append(this.size).append(", capacity ").append(this.cache.capacity).append("\n");

            for(QueryElementNode qce = this.head; qce != null; qce = qce.up) {
               sb.append(qce.qckey).append(", ").append(qce.qcelement);
            }

            QueryCache.debug(this.cache.name, sb.toString());
         }

      }
   }
}
