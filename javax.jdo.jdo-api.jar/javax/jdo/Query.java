package javax.jdo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface Query extends Serializable {
   String JDOQL = "javax.jdo.query.JDOQL";
   String SQL = "javax.jdo.query.SQL";

   void setClass(Class var1);

   void setCandidates(Extent var1);

   void setCandidates(Collection var1);

   void setFilter(String var1);

   void declareImports(String var1);

   void declareParameters(String var1);

   void declareVariables(String var1);

   void setOrdering(String var1);

   void setIgnoreCache(boolean var1);

   boolean getIgnoreCache();

   void compile();

   Object execute();

   Object execute(Object var1);

   Object execute(Object var1, Object var2);

   Object execute(Object var1, Object var2, Object var3);

   Object executeWithMap(Map var1);

   Object executeWithArray(Object... var1);

   PersistenceManager getPersistenceManager();

   void close(Object var1);

   void closeAll();

   void setGrouping(String var1);

   void setUnique(boolean var1);

   void setResult(String var1);

   void setResultClass(Class var1);

   void setRange(long var1, long var3);

   void setRange(String var1);

   void addExtension(String var1, Object var2);

   void setExtensions(Map var1);

   FetchPlan getFetchPlan();

   long deletePersistentAll(Object... var1);

   long deletePersistentAll(Map var1);

   long deletePersistentAll();

   void setUnmodifiable();

   boolean isUnmodifiable();

   void addSubquery(Query var1, String var2, String var3);

   void addSubquery(Query var1, String var2, String var3, String var4);

   void addSubquery(Query var1, String var2, String var3, String... var4);

   void addSubquery(Query var1, String var2, String var3, Map var4);

   void setDatastoreReadTimeoutMillis(Integer var1);

   Integer getDatastoreReadTimeoutMillis();

   void setDatastoreWriteTimeoutMillis(Integer var1);

   Integer getDatastoreWriteTimeoutMillis();

   void cancelAll();

   void cancel(Thread var1);

   void setSerializeRead(Boolean var1);

   Boolean getSerializeRead();
}
