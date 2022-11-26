package kodo.remote;

import com.solarmetric.remote.CommandIO;
import com.solarmetric.remote.HTTPTransport;
import com.solarmetric.remote.Transport;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.Bootstrap;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.MapConfigurationProvider;
import org.apache.openjpa.lib.conf.ProductDerivations;

public class PersistenceServerServlet extends HttpServlet {
   private BrokerFactory _factory = null;
   private CommandIO _io = null;
   private HTTPTransport _transport = null;

   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      this._factory = this.getBrokerFactory(config);
      this._io = new KodoCommandIO(this._factory);
      this._transport = (HTTPTransport)PersistenceServerState.getInstance(this._factory.getConfiguration()).getTransport();
      if (this._transport == null) {
         this._transport = new HTTPTransport();
      }

   }

   protected BrokerFactory getBrokerFactory(ServletConfig config) throws ServletException {
      String[] prefixes = ProductDerivations.getConfigurationPrefixes();
      String jndi = null;

      for(int i = 0; i < prefixes.length && jndi == null; ++i) {
         jndi = config.getInitParameter(prefixes[i] + ".jndi");
      }

      if (jndi != null && jndi.length() > 0) {
         return (BrokerFactory)Configurations.lookup(jndi);
      } else {
         Properties props = new Properties();
         Enumeration enm = config.getInitParameterNames();
         String spec = null;

         while(enm.hasMoreElements()) {
            String key = (String)enm.nextElement();

            for(int i = 0; i < prefixes.length && spec == null; ++i) {
               if (key.equals(prefixes[i] + ".Specification")) {
                  spec = config.getInitParameter(key);
               }
            }

            if (spec == null) {
               props.setProperty(key, config.getInitParameter(key));
            }
         }

         String rsrc = null;

         for(int i = 0; i < prefixes.length && rsrc == null; ++i) {
            rsrc = config.getInitParameter(prefixes[i] + ".properties");
         }

         ConfigurationProvider cp = ProductDerivations.load(rsrc, (String)null, (ClassLoader)null);
         if (cp == null) {
            cp = new SpecConfigurationProvider(props, spec);
         } else {
            ((ConfigurationProvider)cp).addProperties(props);
         }

         return Bootstrap.getBrokerFactory((ConfigurationProvider)cp, (ClassLoader)null);
      }
   }

   public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
      resp.setContentType("text/html");
      PrintWriter out = resp.getWriter();
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Kodo Persistence Server</title>");
      out.println("</head>");
      out.println("<body bgcolor=\"white\">");
      out.println("<p><h2>Kodo Persistence Server</h2></p>");
      out.println("<table>");
      Configuration conf = this._factory.getConfiguration();
      Map props = conf.toProperties(true);
      Iterator itr = props.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         String key = String.valueOf(entry.getKey());
         String value = String.valueOf(entry.getValue());
         if (key.endsWith("UserName") || key.endsWith("Password") || key.endsWith("LicenseKey")) {
            value = "xxxxx";
         }

         out.println("<tr>");
         out.print("<td>");
         out.print(key);
         out.println(":</td>");
         out.print("<td>");
         out.print(value);
         out.println("</td>");
         out.println("</tr>");
      }

      out.println("</table>");
      out.println("</body>");
      out.println("</html>");
      out.flush();
   }

   public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
      Transport.Channel channel = this._transport.getServletChannel(req, resp);

      try {
         this._io.execute(channel);
      } finally {
         try {
            channel.close();
         } catch (Exception var10) {
         }

      }

   }

   public void destroy() {
      if (this._factory != null) {
         this._factory.close();
      }

   }

   private static class SpecConfigurationProvider extends MapConfigurationProvider {
      private final String _spec;

      public SpecConfigurationProvider(Map props, String spec) {
         super(props);
         this._spec = spec;
      }

      public void setInto(Configuration conf) {
         ((OpenJPAConfiguration)conf).setSpecification(this._spec);
         super.setInto(conf);
      }
   }
}
