package org.apache.openjpa.jdbc.kernel;

import org.apache.openjpa.jdbc.kernel.exps.ExpContext;
import org.apache.openjpa.jdbc.kernel.exps.QueryExpressionsState;
import org.apache.openjpa.jdbc.kernel.exps.Val;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SelectExecutor;
import org.apache.openjpa.kernel.exps.QueryExpressions;

class ProjectionResultObjectProvider extends SelectResultObjectProvider {
   private final QueryExpressions[] _exps;
   private final QueryExpressionsState[] _state;
   private final ExpContext _ctx;

   public ProjectionResultObjectProvider(SelectExecutor sel, QueryExpressions exps, QueryExpressionsState state, ExpContext ctx) {
      this(sel, new QueryExpressions[]{exps}, new QueryExpressionsState[]{state}, ctx);
   }

   public ProjectionResultObjectProvider(SelectExecutor sel, QueryExpressions[] exps, QueryExpressionsState[] state, ExpContext ctx) {
      super(sel, ctx.store, ctx.fetch);
      this._exps = exps;
      this._state = state;
      this._ctx = ctx;
   }

   public Object getResultObject() throws Exception {
      Result res = this.getResult();
      res.setBaseMapping((ClassMapping)null);
      int idx = res.indexOf();
      Object[] arr = new Object[this._exps[idx].projections.length];

      for(int i = 0; i < this._exps[idx].projections.length; ++i) {
         arr[i] = ((Val)this._exps[idx].projections[i]).load(this._ctx, this._state[idx].projections[i], res);
      }

      return arr;
   }
}
