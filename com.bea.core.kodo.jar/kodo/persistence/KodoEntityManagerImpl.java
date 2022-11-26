package kodo.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.persistence.EntityManagerImpl;
import org.apache.openjpa.persistence.OpenJPAQuerySPI;

/** @deprecated */
public class KodoEntityManagerImpl extends EntityManagerImpl implements KodoEntityManager {
   public KodoEntityManagerImpl(KodoEntityManagerFactoryImpl emf, Broker broker) {
      super(emf, broker);
   }

   public KodoEntityManagerFactory getEntityManagerFactory() {
      return KodoPersistence.cast((EntityManagerFactory)super.getEntityManagerFactory());
   }

   public FetchPlan getFetchPlan() {
      return new FetchPlanImpl((org.apache.openjpa.persistence.FetchPlanImpl)super.getFetchPlan());
   }

   public Generator getNamedGenerator(String name) {
      return new GeneratorImpl((org.apache.openjpa.persistence.GeneratorImpl)super.getNamedGenerator(name));
   }

   public Generator getIdGenerator(Class forClass) {
      return new GeneratorImpl((org.apache.openjpa.persistence.GeneratorImpl)super.getIdGenerator(forClass));
   }

   public Generator getFieldGenerator(Class forClass, String fieldName) {
      return new GeneratorImpl((org.apache.openjpa.persistence.GeneratorImpl)super.getFieldGenerator(forClass, fieldName));
   }

   public Extent createExtent(Class cls, boolean subs) {
      return new ExtentImpl((org.apache.openjpa.persistence.ExtentImpl)super.createExtent(cls, subs));
   }

   public KodoQuery createQuery(String query) {
      return new KodoQueryImpl((OpenJPAQuerySPI)super.createQuery(query));
   }

   public KodoQuery createNamedQuery(String name) {
      return new KodoQueryImpl((OpenJPAQuerySPI)super.createNamedQuery(name));
   }

   public KodoQuery createNativeQuery(String sql) {
      return new KodoQueryImpl((OpenJPAQuerySPI)super.createNativeQuery(sql));
   }

   public KodoQuery createNativeQuery(String sql, Class resultClass) {
      return new KodoQueryImpl((OpenJPAQuerySPI)super.createNativeQuery(sql, resultClass));
   }

   public KodoQuery createNativeQuery(String sql, String resultMapping) {
      return new KodoQueryImpl((OpenJPAQuerySPI)super.createNativeQuery(sql, resultMapping));
   }

   public KodoQuery createQuery(Query query) {
      return new KodoQueryImpl((OpenJPAQuerySPI)super.createQuery(query));
   }

   public KodoQuery createQuery(String language, String query) {
      return new KodoQueryImpl((OpenJPAQuerySPI)super.createQuery(language, query));
   }
}
