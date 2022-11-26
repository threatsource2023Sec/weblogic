package org.apache.openjpa.slice.jdbc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.kernel.JDBCStoreQuery;
import org.apache.openjpa.kernel.ExpressionStoreQuery;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OrderingMergedResultObjectProvider;
import org.apache.openjpa.kernel.QueryContext;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.kernel.exps.ExpressionParser;
import org.apache.openjpa.lib.rop.MergedResultObjectProvider;
import org.apache.openjpa.lib.rop.RangeResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.StoreException;

class DistributedStoreQuery extends JDBCStoreQuery {
   private List _queries = new ArrayList();
   private ExpressionParser _parser;

   public DistributedStoreQuery(JDBCStore store, ExpressionParser parser) {
      super(store, parser);
      this._parser = parser;
   }

   void add(StoreQuery q) {
      this._queries.add(q);
   }

   public DistributedStoreManager getDistributedStore() {
      return (DistributedStoreManager)this.getStore();
   }

   public StoreQuery.Executor newDataStoreExecutor(ClassMetaData meta, boolean subs) {
      ParallelExecutor ex = new ParallelExecutor(this, meta, subs, this._parser, this.ctx.getCompilation());
      Iterator i$ = this._queries.iterator();

      while(i$.hasNext()) {
         StoreQuery q = (StoreQuery)i$.next();
         ex.addExecutor(q.newDataStoreExecutor(meta, subs));
      }

      return ex;
   }

   public void setContext(QueryContext ctx) {
      super.setContext(ctx);
      Iterator i$ = this._queries.iterator();

      while(i$.hasNext()) {
         StoreQuery q = (StoreQuery)i$.next();
         q.setContext(ctx);
      }

   }

   public ExecutorService getExecutorServiceInstance() {
      DistributedJDBCConfiguration conf = (DistributedJDBCConfiguration)this.getStore().getConfiguration();
      return conf.getExecutorServiceInstance();
   }

   static class UpdateExecutor implements Callable {
      StoreQuery query;
      StoreQuery.Executor executor;
      Object[] params;

      public Number call() throws Exception {
         return this.executor.executeDelete(this.query, this.params);
      }
   }

   static class DeleteExecutor implements Callable {
      StoreQuery query;
      StoreQuery.Executor executor;
      Object[] params;

      public Number call() throws Exception {
         return this.executor.executeDelete(this.query, this.params);
      }
   }

   static class QueryExecutor implements Callable {
      StoreQuery query;
      StoreQuery.Executor executor;
      Object[] params;
      StoreQuery.Range range;

      public ResultObjectProvider call() throws Exception {
         return this.executor.executeQuery(this.query, this.params, this.range);
      }
   }

   public static class ParallelExecutor extends ExpressionStoreQuery.DataStoreExecutor {
      private List executors = new ArrayList();
      private DistributedStoreQuery owner = null;
      private ExecutorService threadPool = null;

      public void addExecutor(StoreQuery.Executor ex) {
         this.executors.add(ex);
      }

      public ParallelExecutor(DistributedStoreQuery dsq, ClassMetaData meta, boolean subclasses, ExpressionParser parser, Object parsed) {
         super(dsq, meta, subclasses, parser, parsed);
         this.owner = dsq;
         this.threadPool = dsq.getExecutorServiceInstance();
      }

      public ResultObjectProvider executeQuery(StoreQuery q, Object[] params, StoreQuery.Range range) {
         List futures = new ArrayList();
         List targets = this.findTargets();

         int i;
         for(i = 0; i < this.owner._queries.size(); ++i) {
            StoreQuery query = (StoreQuery)this.owner._queries.get(i);
            StoreManager sm = this.owner.getDistributedStore().getSlice(i);
            if (targets.contains(sm)) {
               QueryExecutor call = new QueryExecutor();
               call.executor = (StoreQuery.Executor)this.executors.get(i);
               call.query = query;
               call.params = params;
               call.range = range;
               futures.add(this.threadPool.submit(call));
            }
         }

         i = 0;
         ResultObjectProvider[] tmp = new ResultObjectProvider[futures.size()];
         Iterator i$ = futures.iterator();

         while(i$.hasNext()) {
            Future future = (Future)i$.next();

            try {
               tmp[i++] = (ResultObjectProvider)future.get();
            } catch (InterruptedException var13) {
               throw new RuntimeException(var13);
            } catch (ExecutionException var14) {
               throw new StoreException(var14.getCause());
            }
         }

         boolean[] ascending = this.getAscending(q);
         boolean isAscending = ascending.length > 0;
         boolean isUnique = q.getContext().isUnique();
         boolean hasRange = q.getContext().getEndRange() != Long.MAX_VALUE;
         ResultObjectProvider result = null;
         if (isUnique) {
            result = new UniqueResultObjectProvider(tmp, q, this.getQueryExpressions());
         } else if (isAscending) {
            result = new OrderingMergedResultObjectProvider(tmp, ascending, (StoreQuery.Executor[])((StoreQuery.Executor[])this.executors.toArray(new StoreQuery.Executor[this.executors.size()])), q, params);
         } else {
            result = new MergedResultObjectProvider(tmp);
         }

         if (hasRange) {
            result = new RangeResultObjectProvider((ResultObjectProvider)result, q.getContext().getStartRange(), q.getContext().getEndRange());
         }

         return (ResultObjectProvider)result;
      }

      public Number executeDelete(StoreQuery q, Object[] params) {
         Iterator qs = this.owner._queries.iterator();
         List futures = new ArrayList();
         Iterator i$ = this.executors.iterator();

         while(i$.hasNext()) {
            StoreQuery.Executor ex = (StoreQuery.Executor)i$.next();
            DeleteExecutor call = new DeleteExecutor();
            call.executor = ex;
            call.query = (StoreQuery)qs.next();
            call.params = params;
            futures.add(this.threadPool.submit(call));
         }

         int N = 0;
         Iterator i$ = futures.iterator();

         while(i$.hasNext()) {
            Future future = (Future)i$.next();

            try {
               Number n = (Number)future.get();
               if (n != null) {
                  N += n.intValue();
               }
            } catch (InterruptedException var9) {
               throw new RuntimeException(var9);
            } catch (ExecutionException var10) {
               throw new StoreException(var10.getCause());
            }
         }

         return new Integer(N);
      }

      public Number executeUpdate(StoreQuery q, Object[] params) {
         Iterator qs = this.owner._queries.iterator();
         List futures = new ArrayList();
         Iterator i$ = this.executors.iterator();

         while(i$.hasNext()) {
            StoreQuery.Executor ex = (StoreQuery.Executor)i$.next();
            UpdateExecutor call = new UpdateExecutor();
            call.executor = ex;
            call.query = (StoreQuery)qs.next();
            call.params = params;
            futures.add(this.threadPool.submit(call));
         }

         int N = 0;
         Iterator i$ = futures.iterator();

         while(i$.hasNext()) {
            Future future = (Future)i$.next();

            try {
               Number n = (Number)future.get();
               if (n != null) {
                  N += n.intValue();
               }
            } catch (InterruptedException var9) {
               throw new RuntimeException(var9);
            } catch (ExecutionException var10) {
               throw new StoreException(var10.getCause());
            }
         }

         return new Integer(N);
      }

      List findTargets() {
         FetchConfiguration fetch = this.owner.getContext().getFetchConfiguration();
         return this.owner.getDistributedStore().getTargets(fetch);
      }
   }
}
