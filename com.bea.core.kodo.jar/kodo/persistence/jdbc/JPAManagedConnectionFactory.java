package kodo.persistence.jdbc;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import kodo.jdbc.ee.JDBCManagedConnectionFactory;

public class JPAManagedConnectionFactory extends JDBCManagedConnectionFactory {
   public JPAManagedConnectionFactory() {
      this.setSpecification("jpa");
   }

   public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {
      return new JPAConnectionFactory(this, cxManager);
   }
}
