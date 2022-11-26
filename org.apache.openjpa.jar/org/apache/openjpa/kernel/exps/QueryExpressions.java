package org.apache.openjpa.kernel.exps;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.meta.ClassMetaData;

public class QueryExpressions implements Serializable {
   public static final int DISTINCT_AUTO = 2;
   public static final int DISTINCT_TRUE = 4;
   public static final int DISTINCT_FALSE = 8;
   public static final Value[] EMPTY_VALUES = new Value[0];
   public Map updates;
   public int distinct;
   public String alias;
   public Value[] projections;
   public String[] projectionClauses;
   public String[] projectionAliases;
   public Class resultClass;
   public Expression filter;
   public Value[] grouping;
   public String[] groupingClauses;
   public Expression having;
   public Value[] ordering;
   public boolean[] ascending;
   public String[] orderingClauses;
   public LinkedMap parameterTypes;
   public int operation;
   public ClassMetaData[] accessPath;
   public String[] fetchPaths;
   public String[] fetchInnerPaths;
   public Value[] range;
   private Boolean _aggregate;

   public QueryExpressions() {
      this.updates = Collections.EMPTY_MAP;
      this.distinct = 2;
      this.alias = null;
      this.projections = EMPTY_VALUES;
      this.projectionClauses = StoreQuery.EMPTY_STRINGS;
      this.projectionAliases = StoreQuery.EMPTY_STRINGS;
      this.resultClass = null;
      this.filter = null;
      this.grouping = EMPTY_VALUES;
      this.groupingClauses = StoreQuery.EMPTY_STRINGS;
      this.having = null;
      this.ordering = EMPTY_VALUES;
      this.ascending = StoreQuery.EMPTY_BOOLEANS;
      this.orderingClauses = StoreQuery.EMPTY_STRINGS;
      this.parameterTypes = StoreQuery.EMPTY_PARAMS;
      this.operation = 1;
      this.accessPath = StoreQuery.EMPTY_METAS;
      this.fetchPaths = StoreQuery.EMPTY_STRINGS;
      this.fetchInnerPaths = StoreQuery.EMPTY_STRINGS;
      this.range = EMPTY_VALUES;
      this._aggregate = null;
   }

   public boolean isAggregate() {
      if (this.projections.length == 0) {
         return false;
      } else {
         if (this._aggregate == null) {
            this._aggregate = QueryExpressions.AggregateExpressionVisitor.isAggregate(this.projections) ? Boolean.TRUE : Boolean.FALSE;
         }

         return this._aggregate;
      }
   }

   public void putUpdate(Path path, Value val) {
      if (this.updates == Collections.EMPTY_MAP) {
         this.updates = new HashMap();
      }

      this.updates.put(path, val);
   }

   private static class AggregateExpressionVisitor extends AbstractExpressionVisitor {
      private Value _sub = null;
      private boolean _agg = false;

      public static boolean isAggregate(Value[] vals) {
         if (vals.length == 0) {
            return false;
         } else {
            AggregateExpressionVisitor v = new AggregateExpressionVisitor();

            for(int i = 0; i < vals.length && !v._agg; ++i) {
               vals[i].acceptVisit(v);
            }

            return v._agg;
         }
      }

      public void enter(Value val) {
         if (!this._agg) {
            if (this._sub == null) {
               if (val.isAggregate()) {
                  this._agg = true;
               }
            } else if (val instanceof Subquery) {
               this._sub = val;
            }

         }
      }

      public void exit(Value val) {
         if (val == this._sub) {
            this._sub = null;
         }

      }
   }
}
