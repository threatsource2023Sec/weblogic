package org.apache.openjpa.kernel.exps;

public interface Subquery extends Value {
   String getCandidateAlias();

   void setQueryExpressions(QueryExpressions var1);
}
