package kodo.jdbc.runtime;

import java.util.Map;
import javax.jdo.PersistenceManagerFactory;
import kodo.jdo.PersistenceManagerFactoryImpl;
import kodo.runtime.FetchConfiguration;
import kodo.runtime.KodoPersistenceManager;
import kodo.runtime.KodoPersistenceManagerFactory;

/** @deprecated */
public class JDBCPersistenceManagerFactory extends KodoPersistenceManagerFactory {
   public static PersistenceManagerFactory getPersistenceManagerFactory(Map map) {
      return new JDBCPersistenceManagerFactory((PersistenceManagerFactoryImpl)PersistenceManagerFactoryImpl.getPersistenceManagerFactory(map));
   }

   public JDBCPersistenceManagerFactory(PersistenceManagerFactoryImpl pmf) {
      super(pmf);
   }

   public FetchConfiguration newFetchConfiguration(KodoPersistenceManager pm, org.apache.openjpa.kernel.FetchConfiguration fetch) {
      return (FetchConfiguration)(!(fetch instanceof org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration) ? super.newFetchConfiguration(pm, fetch) : new JDBCFetchConfiguration(pm, (org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration)fetch));
   }
}
