package org.apache.openjpa.persistence;

import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;

public interface OpenJPAQuerySPI extends OpenJPAQuery {
   OpenJPAQuery addFilterListener(FilterListener var1);

   OpenJPAQuery removeFilterListener(FilterListener var1);

   OpenJPAQuery addAggregateListener(AggregateListener var1);

   OpenJPAQuery removeAggregateListener(AggregateListener var1);
}
