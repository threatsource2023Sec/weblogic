package org.apache.openjpa.persistence;

import java.util.List;

public interface Extent extends Iterable {
   Class getElementClass();

   boolean hasSubclasses();

   OpenJPAEntityManager getEntityManager();

   FetchPlan getFetchPlan();

   boolean getIgnoreChanges();

   void setIgnoreChanges(boolean var1);

   List list();

   void closeAll();

   /** @deprecated */
   org.apache.openjpa.kernel.Extent getDelegate();
}
