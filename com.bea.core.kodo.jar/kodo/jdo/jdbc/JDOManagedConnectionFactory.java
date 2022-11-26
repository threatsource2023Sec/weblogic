package kodo.jdo.jdbc;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import kodo.jdbc.ee.JDBCManagedConnectionFactory;

public class JDOManagedConnectionFactory extends JDBCManagedConnectionFactory {
   public JDOManagedConnectionFactory() {
      this.setSpecification("jdo");
   }

   public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {
      return new JDOConnectionFactory(this, cxManager);
   }
}
