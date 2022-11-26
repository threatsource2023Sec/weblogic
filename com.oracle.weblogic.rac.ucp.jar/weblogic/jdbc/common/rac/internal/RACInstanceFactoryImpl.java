package weblogic.jdbc.common.rac.internal;

import java.util.Properties;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.common.rac.RACInstanceFactory;

public class RACInstanceFactoryImpl extends RACInstanceFactory {
   public RACInstance create(Properties props) {
      return new RACInstanceImpl(props);
   }

   public RACInstance create(String database, String instance, String service) {
      return new RACInstanceImpl(database, (String)null, instance, service);
   }
}
