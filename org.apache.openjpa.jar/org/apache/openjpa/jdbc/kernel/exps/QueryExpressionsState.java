package org.apache.openjpa.jdbc.kernel.exps;

public class QueryExpressionsState {
   public static final ExpState[] EMPTY_STATES = new ExpState[0];
   public ExpState[] projections;
   public ExpState filter;
   public ExpState[] grouping;
   public ExpState having;
   public ExpState[] ordering;

   public QueryExpressionsState() {
      this.projections = EMPTY_STATES;
      this.filter = null;
      this.grouping = EMPTY_STATES;
      this.having = null;
      this.ordering = EMPTY_STATES;
   }
}
