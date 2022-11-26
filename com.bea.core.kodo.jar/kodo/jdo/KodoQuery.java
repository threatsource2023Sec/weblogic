package kodo.jdo;

import java.util.Collection;
import java.util.Map;
import javax.jdo.Extent;
import javax.jdo.Query;
import org.apache.openjpa.kernel.QueryFlushModes;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;

public interface KodoQuery extends Query, QueryFlushModes {
   String LANG_JDO_SQL = "javax.jdo.query.SQL";

   String getLanguage();

   void addFilterListener(FilterListener var1);

   void removeFilterListener(FilterListener var1);

   void addAggregateListener(AggregateListener var1);

   void removeAggregateListener(AggregateListener var1);

   String[] getDataStoreActions(Map var1);

   Extent getCandidateExtent();

   Collection getCandidateCollection();

   Class getCandidateClass();

   boolean hasSubclasses();

   String getQueryString();

   String getFilter();

   String getOrdering();

   String getImports();

   String getParameters();

   String getVariables();

   boolean isUnique();

   String getResult();

   Class getResultClass();

   String getGrouping();

   long getStartRange();

   long getEndRange();

   long updatePersistentAll();

   long updatePersistentAll(Object[] var1);

   long updatePersistentAll(Map var1);
}
