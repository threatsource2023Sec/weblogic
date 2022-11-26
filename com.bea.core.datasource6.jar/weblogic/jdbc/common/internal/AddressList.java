package weblogic.jdbc.common.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddressList {
   private List hostPorts = new ArrayList();

   public void add(String host, int port) {
      this.hostPorts.add(new HostPort(host, port));
   }

   public void add(String host, int port, String protocol) {
      this.hostPorts.add(new HostPort(host, port, protocol));
   }

   public boolean remove(String host, int port) {
      return this.hostPorts.remove(new HostPort(host, port));
   }

   public boolean remove(String host, int port, String protocol) {
      return this.hostPorts.remove(new HostPort(host, port, protocol));
   }

   public List getList() {
      return this.hostPorts;
   }

   public String commaSeparatedList() {
      if (this.hostPorts.size() == 0) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder();
         Iterator it = this.hostPorts.iterator();

         while(it.hasNext()) {
            HostPort hp = (HostPort)it.next();
            sb.append(hp.host + ":" + hp.port);
            if (it.hasNext()) {
               sb.append(",");
            }
         }

         return sb.toString();
      }
   }

   public void setList(String commaSeparatedHostPortList) {
      this.hostPorts.clear();
      if (commaSeparatedHostPortList != null && !commaSeparatedHostPortList.equals("")) {
         String[] nodes = commaSeparatedHostPortList.split(",");
         if (nodes != null && nodes.length != 0) {
            for(int i = 0; i < nodes.length; ++i) {
               this.hostPorts.add(new HostPort(nodes[i].trim()));
            }

         }
      }
   }

   public String toString() {
      return this.hostPorts.toString();
   }

   public int size() {
      return this.hostPorts.size();
   }

   public Iterator iterator() {
      return this.hostPorts.iterator();
   }

   public void clear() {
      this.hostPorts.clear();
   }

   public class HostPort {
      public final String host;
      public final int port;
      public final String protocol;
      private final String id;

      public HostPort(String host, int port) {
         this.host = host;
         this.port = port;
         this.protocol = null;
         this.id = host + ":" + port;
      }

      public HostPort(String host, int port, String protocol) {
         this.host = host;
         this.port = port;
         this.protocol = protocol;
         this.id = host + ":" + port + ":" + protocol;
      }

      public HostPort(String hostColonPort) throws IllegalArgumentException {
         String[] hp = hostColonPort.split(":");
         if (hp != null && hp.length == 2) {
            this.host = hp[0];
            this.port = Integer.parseInt(hp[1]);
            this.protocol = null;
            this.id = hostColonPort;
         } else {
            throw new IllegalArgumentException(JDBCUtil.getTextFormatter().invalidHostPort(hostColonPort));
         }
      }

      public String toString() {
         return this.id;
      }

      public int hashCode() {
         return this.id.hashCode();
      }

      public boolean equals(Object o) {
         return !(o instanceof HostPort) ? false : this.id.equals(((HostPort)o).id);
      }
   }
}
