package kodo.jdbc.kernel;

import java.io.IOException;
import java.util.Map;
import kodo.remote.Remote;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.kernel.JDBCBrokerFactory;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.MapConfigurationProvider;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.UserException;

public class StartPersistenceServer {
   private static final Localizer _loc = Localizer.forPackage(StartPersistenceServer.class);

   public static void main(String[] args) throws IOException {
      Options opts = new Options();
      opts.setFromCmdLine(args);
      if (!opts.containsKey("help") && !opts.containsKey("-help")) {
         JDBCConfiguration conf = new JDBCConfigurationImpl();
         Configurations.populateConfiguration(conf, opts);
         Map props = conf.toProperties(false);
         run(props);
      } else {
         System.out.println(_loc.get("server-usage"));
      }
   }

   public static void run(Map props) throws IOException {
      ConfigurationProvider cp = new MapConfigurationProvider(props);
      JDBCBrokerFactory factory = JDBCBrokerFactory.getInstance(cp);
      Remote remote = Remote.getInstance(factory);

      try {
         if (!remote.isPersistenceServerRunning()) {
            throw (new UserException(_loc.get("no-server-conf"))).setFatal(true);
         }

         System.out.println(_loc.get("press-key-end"));
         System.in.read();
      } finally {
         factory.close();
      }

   }
}
