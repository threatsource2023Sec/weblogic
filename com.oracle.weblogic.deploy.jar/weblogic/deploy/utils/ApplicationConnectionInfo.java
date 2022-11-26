package weblogic.deploy.utils;

public class ApplicationConnectionInfo {
   private String defaultConnection;
   private String plainConnection;
   private String sslConnection;
   private String clusterConnection;
   private String partitionName;

   ApplicationConnectionInfo(String defaultConnection, String plainConnection, String SSLConnection, String clusterConnection) {
      this.defaultConnection = defaultConnection;
      this.plainConnection = plainConnection;
      this.sslConnection = SSLConnection;
      this.clusterConnection = clusterConnection;
   }

   ApplicationConnectionInfo(String defaultConnection, String plainConnection, String SSLConnection, String clusterConnection, String partitionName) {
      this(defaultConnection, plainConnection, SSLConnection, clusterConnection);
      this.partitionName = partitionName;
   }

   public String getDefaultConnection() {
      return this.defaultConnection;
   }

   public String getPlainConnection() {
      return this.plainConnection;
   }

   public String getSSLConnection() {
      return this.sslConnection;
   }

   public String getClusterConnection() {
      return this.clusterConnection;
   }

   public String getPartitionName() {
      return this.partitionName;
   }
}
