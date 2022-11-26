package javax.jdo;

import java.util.Iterator;

public interface Extent extends Iterable {
   Iterator iterator();

   boolean hasSubclasses();

   Class getCandidateClass();

   PersistenceManager getPersistenceManager();

   void closeAll();

   void close(Iterator var1);

   FetchPlan getFetchPlan();
}
