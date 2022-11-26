package kodo.persistence;

import org.apache.openjpa.persistence.OpenJPAQuerySPI;

/** @deprecated */
public interface KodoQuery extends OpenJPAQuerySPI {
   KodoEntityManager getEntityManager();
}
