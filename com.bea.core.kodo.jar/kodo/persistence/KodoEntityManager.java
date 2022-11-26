package kodo.persistence;

import org.apache.openjpa.persistence.OpenJPAEntityManagerSPI;

/** @deprecated */
public interface KodoEntityManager extends OpenJPAEntityManagerSPI {
   KodoEntityManagerFactory getEntityManagerFactory();

   FetchPlan getFetchPlan();

   Generator getNamedGenerator(String var1);

   Generator getIdGenerator(Class var1);

   Generator getFieldGenerator(Class var1, String var2);

   Extent createExtent(Class var1, boolean var2);

   KodoQuery createQuery(String var1);

   KodoQuery createNamedQuery(String var1);

   KodoQuery createNativeQuery(String var1);

   KodoQuery createNativeQuery(String var1, Class var2);

   KodoQuery createNativeQuery(String var1, String var2);

   KodoQuery createQuery(String var1, String var2);
}
