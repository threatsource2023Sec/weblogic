package weblogic.management.patching.model;

import java.io.Serializable;

public class VirtualTarget implements Serializable {
   private String vtName;
   private Cluster cluster;
   private Server server;
   private String uri;

   public VirtualTarget(String vtName, String uri) {
      this.vtName = vtName;
      this.uri = uri;
   }

   public String getVtName() {
      return this.vtName;
   }

   public Cluster getCluster() {
      return this.cluster;
   }

   public void setCluster(Cluster cluster) {
      this.cluster = cluster;
   }

   public Server getServer() {
      return this.server;
   }

   public void setServer(Server server) {
      this.server = server;
   }

   public String getURI() {
      return this.uri;
   }
}
