package kodo.remote;

import com.solarmetric.remote.GZIPStreamDecorator;
import com.solarmetric.remote.HTTPTransport;
import com.solarmetric.remote.StreamDecorator;
import com.solarmetric.remote.TCPTransport;
import com.solarmetric.remote.Transport;
import java.util.StringTokenizer;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Options;

public class PersistenceServerState extends PluginValue implements Closeable {
   public static final String KEY = "PersistenceServer";
   private static final String[] ALIASES = new String[]{"false", null, "tcp", TCPTransport.class.getName(), "http", HTTPTransport.class.getName()};
   private final OpenJPAConfiguration _conf;
   private Options _opts = null;
   private String _decorators = null;
   private StreamDecorator[] _streams = null;
   private Transport.Server _server = null;
   private Thread _serverThread = null;

   public static PersistenceServerState getInstance(OpenJPAConfiguration conf) {
      return (PersistenceServerState)conf.getValue("PersistenceServer");
   }

   public PersistenceServerState(OpenJPAConfiguration conf) {
      super("PersistenceServer", true);
      this.setAliases(ALIASES);
      this.setDefault(ALIASES[0]);
      this.setInstantiatingGetter("this.getTransport");
      this.setScope(this.getClass());
      this._conf = conf;
   }

   public StreamDecorator[] getStreamDecorators() {
      if (this._streams != null) {
         return this._streams;
      } else {
         this.parseOptions();
         if (this._decorators == null) {
            this._streams = new StreamDecorator[0];
         } else {
            StringTokenizer toke = new StringTokenizer(this._decorators, ";");
            this._streams = new StreamDecorator[toke.countTokens()];
            String clsName = null;
            String props = null;

            for(int i = 0; i < this._streams.length; ++i) {
               String plugin = toke.nextToken().trim();
               if ("gzip".equals(plugin)) {
                  this._streams[i] = new GZIPStreamDecorator();
               } else {
                  clsName = Configurations.getClassName(plugin);
                  props = Configurations.getProperties(plugin);
                  this._streams[i] = (StreamDecorator)this.newInstance(clsName, StreamDecorator.class, this._conf, true);
                  Configurations.configureInstance(this._streams[i], this._conf, props, this.getProperty());
               }
            }
         }

         return this._streams;
      }
   }

   public void setStreamDecorators(StreamDecorator[] decorators) {
      this._streams = decorators;
   }

   public Transport getTransport() {
      Transport transport = (Transport)this.get();
      if (transport == null) {
         transport = (Transport)this.instantiate(Transport.class, this._conf, true);
         this.getStreamDecorators();
      }

      return transport;
   }

   public void setTransport(Transport transport) {
      this.set(transport);
   }

   public Transport.Server getServer() {
      return this._server;
   }

   public Thread getServerThread() {
      return this._serverThread;
   }

   public void setServer(Transport.Server server, Thread serverThread) {
      this._server = server;
      this._serverThread = serverThread;
   }

   public void setProperties(String props) {
      super.setProperties(props);
      this._opts = null;
      this._decorators = null;
      this._streams = null;
   }

   public void setString(String str) {
      super.setString(str);
      this._opts = null;
      this._decorators = null;
      this._streams = null;
   }

   public Object instantiate(Class type, Configuration conf, boolean fatal) {
      Object obj = this.newInstance(this.getClassName(), type, conf, fatal);
      this.parseOptions();
      Configurations.configureInstance(obj, conf, this._opts, this.getProperty());
      this.set(obj, true);
      return obj;
   }

   private void parseOptions() {
      if (this._opts == null) {
         this._opts = Configurations.parseProperties(this.getProperties());
         this._decorators = this._opts.removeProperty("decorators", "Decorators", (String)null);
         if (this._decorators != null) {
            this._decorators = this._decorators.trim();
            if (this._decorators.length() == 0) {
               this._decorators = null;
            }
         }

      }
   }

   public void close() {
      stopServer(this);
      Transport t = this.getTransport();
      if (t != null) {
         try {
            t.close();
         } catch (Exception var3) {
         }
      }

   }

   static boolean stopServer(PersistenceServerState state) {
      Transport.Server server = state.getServer();
      if (server == null) {
         return false;
      } else {
         try {
            server.close();
         } catch (Exception var3) {
         }

         Thread serverThread = state.getServerThread();
         if (serverThread != null) {
            serverThread.interrupt();
         }

         state.setServer((Transport.Server)null, (Thread)null);
         return true;
      }
   }
}
