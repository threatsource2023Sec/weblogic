package org.glassfish.tyrus.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.websocket.DeploymentException;
import org.glassfish.tyrus.spi.ServerContainer;
import org.glassfish.tyrus.spi.ServerContainerFactory;

public class Server {
   public static final String STATIC_CONTENT_ROOT = "org.glassfish.tyrus.server.staticContentRoot";
   private static final Logger LOGGER = Logger.getLogger(Server.class.getClass().getName());
   private static final int DEFAULT_PORT = 8025;
   private static final String DEFAULT_HOST_NAME = "localhost";
   private static final String DEFAULT_CONTEXT_PATH = "/";
   private final Map properties;
   private final Set configuration;
   private final String hostName;
   private volatile int port;
   private final String contextPath;
   private ServerContainer server;

   public Server(Class... configuration) {
      this((String)null, 0, (String)null, (Map)null, (Class[])configuration);
   }

   public Server(Map properties, Class... configuration) {
      this((String)null, 0, (String)null, properties, (Class[])configuration);
   }

   public Server(String hostName, int port, String contextPath, Map properties, Class... configuration) {
      this(hostName, port, contextPath, properties, (Set)(new HashSet(Arrays.asList(configuration))));
   }

   public Server(String hostName, int port, String contextPath, Map properties, Set configuration) {
      this.hostName = hostName == null ? "localhost" : hostName;
      if (port == 0) {
         this.port = 8025;
      } else if (port == -1) {
         this.port = 0;
      } else {
         this.port = port;
      }

      this.contextPath = contextPath == null ? "/" : contextPath;
      this.configuration = configuration;
      this.properties = properties == null ? null : new HashMap(properties);
   }

   public static void main(String[] args) {
      if (args.length < 4) {
         System.out.println("Please provide: (<hostname>, <port>, <websockets root path>, <;-sep fully qualfied classnames of your bean>) in the command line");
         System.out.println("e.g. localhost 8021 /websockets/myapp myapp.Bean1;myapp.Bean2");
         System.exit(1);
      }

      Set beanClasses = getClassesFromString(args[3]);
      int port = Integer.parseInt(args[1]);
      String hostname = args[0];
      String wsroot = args[2];
      Server server = new Server(hostname, port, wsroot, (Map)null, beanClasses);

      try {
         server.start();
         System.out.println("Press any key to stop the WebSocket server...");
         System.in.read();
      } catch (IOException var11) {
         System.err.println("IOException during server run");
         var11.printStackTrace();
      } catch (DeploymentException var12) {
         var12.printStackTrace();
      } finally {
         server.stop();
      }

   }

   private static Set getClassesFromString(String rawString) {
      Set beanClasses = new HashSet();
      StringTokenizer st = new StringTokenizer(rawString, ";");

      while(st.hasMoreTokens()) {
         String nextClassname = st.nextToken().trim();
         if (!"".equals(nextClassname)) {
            try {
               beanClasses.add(Class.forName(nextClassname));
            } catch (ClassNotFoundException var5) {
               throw new RuntimeException("Stop: cannot load class: " + nextClassname);
            }
         }
      }

      return beanClasses;
   }

   public void start() throws DeploymentException {
      try {
         if (this.server == null) {
            this.server = ServerContainerFactory.createServerContainer(this.properties);
            Iterator var1 = this.configuration.iterator();

            while(var1.hasNext()) {
               Class clazz = (Class)var1.next();
               this.server.addEndpoint(clazz);
            }

            this.server.start(this.contextPath, this.port);
            if (this.server instanceof TyrusServerContainer) {
               this.port = ((TyrusServerContainer)this.server).getPort();
            }

            LOGGER.info("WebSocket Registered apps: URLs all start with ws://" + this.hostName + ":" + this.getPort());
            LOGGER.info("WebSocket server started.");
         }

      } catch (IOException var3) {
         throw new DeploymentException(var3.getMessage(), var3);
      }
   }

   public int getPort() {
      return this.port;
   }

   public void stop() {
      if (this.server != null) {
         this.server.stop();
         this.server = null;
         LOGGER.info("Websocket Server stopped.");
      }

   }
}
