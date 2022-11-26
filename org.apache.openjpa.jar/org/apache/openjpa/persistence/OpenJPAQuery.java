package org.apache.openjpa.persistence;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;

public interface OpenJPAQuery extends Query {
   String HINT_RESULT_COUNT = "openjpa.hint.OptimizeResultCount";
   /** @deprecated */
   int OP_SELECT = 1;
   /** @deprecated */
   int OP_DELETE = 2;
   /** @deprecated */
   int OP_UPDATE = 2;
   /** @deprecated */
   int FLUSH_TRUE = 0;
   /** @deprecated */
   int FLUSH_FALSE = 1;
   /** @deprecated */
   int FLUSH_WITH_CONNECTION = 2;

   OpenJPAEntityManager getEntityManager();

   String getLanguage();

   QueryOperationType getOperation();

   FetchPlan getFetchPlan();

   String getQueryString();

   boolean getIgnoreChanges();

   OpenJPAQuery setIgnoreChanges(boolean var1);

   Collection getCandidateCollection();

   OpenJPAQuery setCandidateCollection(Collection var1);

   Class getResultClass();

   OpenJPAQuery setResultClass(Class var1);

   boolean hasSubclasses();

   OpenJPAQuery setSubclasses(boolean var1);

   int getFirstResult();

   int getMaxResults();

   OpenJPAQuery compile();

   boolean hasPositionalParameters();

   Object[] getPositionalParameters();

   Map getNamedParameters();

   OpenJPAQuery setParameters(Map var1);

   OpenJPAQuery setParameters(Object... var1);

   OpenJPAQuery closeAll();

   String[] getDataStoreActions(Map var1);

   OpenJPAQuery setMaxResults(int var1);

   OpenJPAQuery setFirstResult(int var1);

   OpenJPAQuery setHint(String var1, Object var2);

   OpenJPAQuery setParameter(String var1, Object var2);

   OpenJPAQuery setParameter(String var1, Date var2, TemporalType var3);

   OpenJPAQuery setParameter(String var1, Calendar var2, TemporalType var3);

   OpenJPAQuery setParameter(int var1, Object var2);

   OpenJPAQuery setParameter(int var1, Date var2, TemporalType var3);

   OpenJPAQuery setParameter(int var1, Calendar var2, TemporalType var3);

   OpenJPAQuery setFlushMode(FlushModeType var1);

   FlushModeType getFlushMode();

   /** @deprecated */
   OpenJPAQuery addFilterListener(FilterListener var1);

   /** @deprecated */
   OpenJPAQuery removeFilterListener(FilterListener var1);

   /** @deprecated */
   OpenJPAQuery addAggregateListener(AggregateListener var1);

   /** @deprecated */
   OpenJPAQuery removeAggregateListener(AggregateListener var1);
}
