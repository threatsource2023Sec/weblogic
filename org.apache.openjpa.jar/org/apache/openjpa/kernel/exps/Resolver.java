package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.QueryContext;

public interface Resolver {
   Class classForName(String var1, String[] var2);

   FilterListener getFilterListener(String var1);

   AggregateListener getAggregateListener(String var1);

   OpenJPAConfiguration getConfiguration();

   QueryContext getQueryContext();
}
