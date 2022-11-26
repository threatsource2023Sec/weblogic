package weblogic.management.j2ee.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.management.ObjectName;
import weblogic.management.j2ee.JVMMBean;

public final class JVMMBeanImpl extends J2EEManagedObjectMBeanImpl implements JVMMBean {
   private final ObjectName server;
   private String host = null;
   private static final String LA_DEFAULT = "127.0.0.1";

   public JVMMBeanImpl(String name, ObjectName server) {
      super(name);
      this.server = server;
   }

   public String getnode() {
      if (this.host != null) {
         return this.host;
      } else {
         String listenAddress = this.getListenAddress();

         try {
            if (!listenAddress.equals("localhost") && !listenAddress.equals("127.0.0.0")) {
               this.host = InetAddress.getByName(listenAddress).getHostName();
            } else {
               this.host = InetAddress.getLocalHost().getHostName();
            }
         } catch (UnknownHostException var3) {
            throw new Error(var3);
         }

         return this.host;
      }
   }

   private String getListenAddress() {
      String add = null;

      try {
         add = (String)MBeanServerConnectionProvider.geRuntimeMBeanServerConnection().getAttribute(this.getServerON(), "ListenAddress");
      } catch (Throwable var3) {
         throw new Error(var3);
      }

      return add != null ? add : "127.0.0.1";
   }

   private ObjectName getServerON() {
      String buf = this.server.getDomain() + ":Name=" + this.server.getKeyProperty("Name") + ",Type=Server";

      try {
         return new ObjectName(buf);
      } catch (Throwable var3) {
         throw new Error(var3);
      }
   }

   public String getjavaVendor() {
      return System.getProperty("java.vendor");
   }

   public String getjavaVersion() {
      return System.getProperty("java.version");
   }
}
