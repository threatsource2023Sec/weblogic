package kodo.remote;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import kodo.kernel.jdoql.JDOQLParser;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.kernel.AbstractStoreQuery;
import org.apache.openjpa.kernel.ExpressionStoreQuery;
import org.apache.openjpa.kernel.Extent;
import org.apache.openjpa.kernel.MethodStoreQuery;
import org.apache.openjpa.kernel.Query;
import org.apache.openjpa.kernel.QueryContext;
import org.apache.openjpa.kernel.QueryImpl;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.kernel.jpql.JPQLParser;
import org.apache.openjpa.lib.rop.ListResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UnsupportedException;

class ClientStoreQuery extends AbstractStoreQuery {
   private static final int CONST_UNKNOWN = 0;
   private static final int CONST_JDOQL = 1;
   private static final int CONST_SQL = 2;
   private static final int CONST_METHODQL = 3;
   private static final int CONST_JPQL = 4;
   private final transient ClientStoreManager _store;
   private final int _langCode;
   private QueryContext _qctx;
   private Query _query = null;

   public ClientStoreQuery(ClientStoreManager store, String language) {
      this._store = store;
      if ("javax.jdo.query.JDOQL".equals(language)) {
         this._langCode = 1;
      } else if ("javax.persistence.JPQL".equals(language)) {
         this._langCode = 4;
      } else if ("openjpa.SQL".equals(language)) {
         this._langCode = 2;
      } else if ("openjpa.MethodQL".equals(language)) {
         this._langCode = 3;
      } else {
         this._langCode = 0;
      }

   }

   public QueryContext getContext() {
      return this._qctx;
   }

   public void setContext(QueryContext qctx) {
      this._qctx = qctx;
   }

   public void invalidateCompilation() {
      this._query = null;
   }

   public StoreQuery.Executor newInMemoryExecutor(ClassMetaData meta, boolean subclasses) {
      return new ClientExecutor(true);
   }

   public StoreQuery.Executor newDataStoreExecutor(ClassMetaData meta, boolean subclasses) {
      return new ClientExecutor(false);
   }

   public boolean supportsInMemoryExecution() {
      switch (this._langCode) {
         case 1:
         case 3:
         case 4:
            return true;
         case 2:
         default:
            return false;
      }
   }

   public boolean supportsDataStoreExecution() {
      return true;
   }

   public boolean supportsAbstractExecutors() {
      return true;
   }

   public boolean requiresCandidateType() {
      return false;
   }

   public boolean requiresParameterDeclarations() {
      return false;
   }

   private Query getInMemoryQuery() {
      if (this._query != null) {
         return this._query;
      } else {
         switch (this._langCode) {
            case 1:
               this._query = new QueryImpl(this._store.getBroker(), this._qctx.getLanguage(), new ExpressionStoreQuery(new JDOQLParser()));
            case 2:
            default:
               break;
            case 3:
               this._query = new QueryImpl(this._store.getBroker(), this._qctx.getLanguage(), new MethodStoreQuery());
               break;
            case 4:
               this._query = new QueryImpl(this._store.getBroker(), this._qctx.getLanguage(), new ExpressionStoreQuery(new JPQLParser()));
         }

         if (this._query != null) {
            this._query.setQuery(this._qctx.getQuery());
            this._query.setCandidateCollection(Collections.EMPTY_SET);
            if (this._query.isUnique()) {
               this._qctx.setUnique(true);
            }

            this._query.setUnique(false);
         }

         return this._query;
      }
   }

   private static class QueryResultObjectProvider extends ClientResultObjectProvider {
      private final Query _query;
      private final Object _params;
      private final boolean _lrs;
      private final long _startIdx;
      private final long _endIdx;
      private QueryResultCommand _initCmd = null;

      public QueryResultObjectProvider(ClientStoreQuery query, Object params, boolean lrs, long startIdx, long endIdx) {
         super(query._store, query.getContext().getFetchConfiguration());
         this._query = query.getContext().getQuery();
         this._params = params;
         this._lrs = lrs;
         this._startIdx = startIdx;
         this._endIdx = endIdx;
      }

      public QueryResultCommand getResultCommand() {
         return this._initCmd;
      }

      protected ResultCommand newResultCommand() {
         return new QueryResultCommand(this.getStoreManager().getBrokerId(), this._query, this._params, this._startIdx, this._endIdx);
      }

      protected boolean getResultsOnInitialize() {
         return !this._lrs;
      }

      protected void setResponseState(ResultCommand cmd) {
         super.setResponseState(cmd);
         if (cmd.getInitialize()) {
            this._initCmd = (QueryResultCommand)cmd;
         }

      }
   }

   private static class ClientExecutor implements StoreQuery.Executor {
      private final boolean _inMem;
      private String _alias = null;
      private Class _resultClass = null;
      private String[] _projAliases = null;
      private Class[] _projTypes = null;
      private Boolean _agg = null;
      private boolean _group = false;
      private int _op = -1;
      private boolean _candidate = false;

      public ClientExecutor(boolean inMem) {
         this._inMem = inMem;
      }

      public ResultObjectProvider executeQuery(StoreQuery q, Object[] params, StoreQuery.Range range) {
         ClientStoreQuery cq = (ClientStoreQuery)q;
         if (this._inMem) {
            Query mem = cq.getInMemoryQuery();
            return this.executeInMemoryQuery(cq, mem, params, range);
         } else {
            QueryResultObjectProvider rop = new QueryResultObjectProvider(cq, params, range.lrs, range.start, range.end);
            synchronized(this) {
               if (this._agg == null) {
                  this.setFromServerQuery(cq, rop);
               }

               return rop;
            }
         }
      }

      private ResultObjectProvider executeInMemoryQuery(ClientStoreQuery cq, Query mem, Object[] params, StoreQuery.Range range) {
         Query q = cq.getContext().getQuery();
         if (q.getCandidateCollection() == null) {
            Extent extent = q.getBroker().newExtent(q.getCandidateType(), q.hasSubclasses());
            extent.setIgnoreChanges(false);
            mem.setCandidateCollection(extent.list());
         } else {
            mem.setCandidateCollection(q.getCandidateCollection());
         }

         mem.setRange(range.start, range.end);
         List results = (List)mem.execute(params);
         mem.setCandidateCollection(Collections.EMPTY_SET);
         return new ListResultObjectProvider(results);
      }

      public Number executeDelete(StoreQuery q, Object[] params) {
         throw new UnsupportedException();
      }

      public Number executeUpdate(StoreQuery q, Object[] params) {
         throw new UnsupportedException();
      }

      public String[] getDataStoreActions(StoreQuery q, Object[] params, StoreQuery.Range range) {
         return StoreQuery.EMPTY_STRINGS;
      }

      public void validate(StoreQuery q) {
      }

      public void getRange(StoreQuery q, Object[] params, StoreQuery.Range range) {
      }

      public Object getOrderingValue(StoreQuery q, Object[] params, Object resultObject, int orderIndex) {
         throw new InternalException();
      }

      public boolean[] getAscending(StoreQuery q) {
         throw new InternalException();
      }

      public synchronized boolean isPacking(StoreQuery q) {
         if (this._agg == null) {
            this.setFromInMemoryQuery((ClientStoreQuery)q);
         }

         return !this._candidate && q.getContext().getCandidateType() == null;
      }

      public synchronized String getAlias(StoreQuery q) {
         if (this._agg == null) {
            this.setFromInMemoryQuery((ClientStoreQuery)q);
         }

         return this._alias;
      }

      public synchronized Class getResultClass(StoreQuery q) {
         if (this._resultClass == null) {
            this.setFromInMemoryQuery((ClientStoreQuery)q);
         }

         return this._resultClass;
      }

      public synchronized String[] getProjectionAliases(StoreQuery q) {
         if (this._agg == null) {
            this.setFromInMemoryQuery((ClientStoreQuery)q);
         }

         return this._projAliases;
      }

      public synchronized Class[] getProjectionTypes(StoreQuery q) {
         if (this._agg == null) {
            this.setFromInMemoryQuery((ClientStoreQuery)q);
         }

         return this._projTypes;
      }

      public ClassMetaData[] getAccessPathMetaDatas(StoreQuery q) {
         Query mem = ((ClientStoreQuery)q).getInMemoryQuery();
         return mem == null ? StoreQuery.EMPTY_METAS : mem.getAccessPathMetaDatas();
      }

      public synchronized int getOperation(StoreQuery q) {
         if (this._agg == null) {
            this.setFromInMemoryQuery((ClientStoreQuery)q);
         }

         return this._op;
      }

      public synchronized boolean isAggregate(StoreQuery q) {
         if (this._agg == null) {
            this.setFromInMemoryQuery((ClientStoreQuery)q);
         }

         return this._agg;
      }

      public synchronized boolean hasGrouping(StoreQuery q) {
         if (this._agg == null) {
            this.setFromInMemoryQuery((ClientStoreQuery)q);
         }

         return this._group;
      }

      public LinkedMap getParameterTypes(StoreQuery q) {
         Query mem = ((ClientStoreQuery)q).getInMemoryQuery();
         return mem == null ? null : mem.getParameterTypes();
      }

      public Map getUpdates(StoreQuery q) {
         Query mem = ((ClientStoreQuery)q).getInMemoryQuery();
         return mem == null ? null : mem.getUpdates();
      }

      private void setFromServerQuery(ClientStoreQuery cq, QueryResultObjectProvider rop) {
         try {
            rop.open();
            QueryResultCommand cmd = rop.getResultCommand();
            if (cmd.getResultType() != null) {
               cq.getContext().setResultType(cmd.getResultType());
            }

            if (cmd.isUnique()) {
               cq.getContext().setUnique(true);
            }

            this._alias = cmd.getAlias();
            this._resultClass = cmd.getResultType();
            this._projAliases = cmd.getProjectionAliases();
            if (this._projAliases == null) {
               this._projAliases = StoreQuery.EMPTY_STRINGS;
            }

            this._projTypes = cmd.getProjectionTypes();
            if (this._projTypes == null) {
               this._projTypes = StoreQuery.EMPTY_CLASSES;
            }

            this._agg = cmd.isAggregate() ? Boolean.TRUE : Boolean.FALSE;
            this._group = cmd.hasGrouping();
            this._op = cmd.getOperation();
            this._candidate = cmd.hasCandidateType();
         } catch (RuntimeException var7) {
            try {
               rop.close();
            } catch (Exception var6) {
            }

            throw var7;
         } catch (Exception var8) {
            try {
               rop.close();
            } catch (Exception var5) {
            }

            throw new StoreException(var8);
         }
      }

      private void setFromInMemoryQuery(ClientStoreQuery cq) {
         Query mem = cq.getInMemoryQuery();
         if (mem == null) {
            this._projAliases = StoreQuery.EMPTY_STRINGS;
            this._projTypes = StoreQuery.EMPTY_CLASSES;
            this._agg = Boolean.FALSE;
            this._group = false;
            this._op = 1;
            this._candidate = false;
         } else {
            this._alias = mem.getAlias();
            this._resultClass = mem.getResultType();
            this._projAliases = mem.getProjectionAliases();
            this._projTypes = mem.getProjectionTypes();
            this._agg = mem.isAggregate() ? Boolean.TRUE : Boolean.FALSE;
            this._group = mem.hasGrouping();
            this._op = mem.getOperation();
            this._candidate = mem.getCandidateType() != null;
         }

      }
   }
}
