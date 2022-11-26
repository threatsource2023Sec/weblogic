package javax.jdo;

import java.util.Collection;
import java.util.Set;

public interface FetchPlan {
   String DEFAULT = "default";
   String ALL = "all";
   int DETACH_UNLOAD_FIELDS = 2;
   int DETACH_LOAD_FIELDS = 1;
   int FETCH_SIZE_GREEDY = -1;
   int FETCH_SIZE_OPTIMAL = 0;

   FetchPlan addGroup(String var1);

   FetchPlan removeGroup(String var1);

   FetchPlan clearGroups();

   Set getGroups();

   FetchPlan setGroups(Collection var1);

   FetchPlan setGroups(String... var1);

   FetchPlan setGroup(String var1);

   FetchPlan setMaxFetchDepth(int var1);

   int getMaxFetchDepth();

   FetchPlan setDetachmentRoots(Collection var1);

   Collection getDetachmentRoots();

   FetchPlan setDetachmentRootClasses(Class... var1);

   Class[] getDetachmentRootClasses();

   FetchPlan setFetchSize(int var1);

   int getFetchSize();

   FetchPlan setDetachmentOptions(int var1);

   int getDetachmentOptions();
}
