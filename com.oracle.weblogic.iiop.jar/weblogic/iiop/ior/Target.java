package weblogic.iiop.ior;

public class Target {
   private String host;
   private int port;

   public Target(String host, int port) {
      this.host = host;
      this.port = port;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public boolean equals(Object o) {
      return this == o || o instanceof Target && this.equals((Target)o);
   }

   private boolean equals(Target target) {
      return this.port == target.port && this.host.equals(target.host);
   }

   public int hashCode() {
      return 31 * this.host.hashCode() + this.port;
   }

   public String toString() {
      return this.host + ':' + this.port;
   }
}
