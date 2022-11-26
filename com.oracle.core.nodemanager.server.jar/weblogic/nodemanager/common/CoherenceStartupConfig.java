package weblogic.nodemanager.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import weblogic.nodemanager.NodeManagerTextTextFormatter;

public class CoherenceStartupConfig extends StartupConfig {
   public static final String UNICAST_PORT_PROP = "UnicastListenPort";
   public static final String UNICAST_ADDR_PROP = "UnicastListenAddress";
   public static final String UNICAST_PORT_AUTO_ADJUST_PROP = "UnicastPortAutoAdjust";
   public static final String MULTICAST_PORT_PROP = "MulticastListenPort";
   public static final String MULTICAST_ADDR_PROP = "MulticastListenAddress";
   public static final String TTL_PROP = "TTL";
   public static final String WKA_PROP = "WellKnownAddresses";
   public static final String CLUSTER_CONFIG_FILE_PROP = "ClusterConfigurationFileName";
   public static final String CLUSTER_NAME = "ClusterName";
   public static final int DEFAULT_TTL = 4;
   public static final boolean DEFAULT_UNICAST_PORT_AUTO_ADJUST = true;
   private final String unicastListenAddress;
   private final int unicastListenPort;
   private final boolean unicastPortAutoAdjust;
   private final String multicastListenAddress;
   private final int multicastListenPort;
   private final int timeToLive;
   private final ArrayList wellKnownAddresses;
   private final String customClusterConfigurationFileName;
   private final String clusterName;

   public CoherenceStartupConfig(Properties props) throws ConfigException {
      super(props);
      this.unicastListenAddress = this.getProperty("UnicastListenAddress");
      this.unicastListenPort = this.getIntProperty("UnicastListenPort", 0);
      this.unicastPortAutoAdjust = this.getBooleanProperty("UnicastPortAutoAdjust", true);
      this.multicastListenAddress = this.getProperty("MulticastListenAddress");
      this.multicastListenPort = this.getIntProperty("MulticastListenPort", 0);
      this.timeToLive = this.getIntProperty("TTL", 4);
      this.customClusterConfigurationFileName = this.getProperty("ClusterConfigurationFileName");
      this.clusterName = this.getProperty("ClusterName");
      this.wellKnownAddresses = new ArrayList();
      this.loadWka(props);
   }

   public CoherenceStartupConfig(ValuesHolder holder) {
      super((StartupConfig.ValuesHolder)holder);
      this.unicastListenAddress = holder.unicastListenAddress;
      this.unicastListenPort = holder.unicastListenPort;
      this.unicastPortAutoAdjust = holder.unicastPortAutoAdjust;
      this.multicastListenAddress = holder.multicastListenAddress;
      this.multicastListenPort = holder.multicastListenPort;
      this.timeToLive = holder.timeToLive;
      this.customClusterConfigurationFileName = holder.customClusterConfigurationFileName;
      this.clusterName = holder.clusterName;
      this.wellKnownAddresses = holder.wellKnownAddresses;
   }

   public Properties getStartupProperties() {
      Properties props = super.getStartupProperties();
      if (this.unicastListenAddress != null) {
         props.setProperty("UnicastListenAddress", this.unicastListenAddress);
      }

      if (this.unicastListenPort != 0) {
         props.setProperty("UnicastListenPort", "" + this.unicastListenPort);
      }

      if (!this.unicastPortAutoAdjust) {
         props.setProperty("UnicastPortAutoAdjust", Boolean.toString(this.unicastPortAutoAdjust));
      }

      if (this.multicastListenAddress != null) {
         props.setProperty("MulticastListenAddress", this.multicastListenAddress);
      }

      if (this.multicastListenPort != 0) {
         props.setProperty("MulticastListenPort", "" + this.multicastListenPort);
      }

      if (this.timeToLive != 4) {
         props.setProperty("TTL", "" + this.timeToLive);
      }

      if (this.customClusterConfigurationFileName != null) {
         props.setProperty("ClusterConfigurationFileName", this.customClusterConfigurationFileName);
      }

      if (this.clusterName != null) {
         props.setProperty("ClusterName", this.clusterName);
      }

      this.getStartupWka(props);
      return props;
   }

   public String getUnicastListenAddress() {
      return this.unicastListenAddress;
   }

   public int getUnicastListenPort() {
      return this.unicastListenPort;
   }

   public boolean isUnicastPortAutoAdjust() {
      return this.unicastPortAutoAdjust;
   }

   public String getMulticastListenAddress() {
      return this.multicastListenAddress;
   }

   public int getMulticastListenPort() {
      return this.multicastListenPort;
   }

   public int getTimeToLive() {
      return this.timeToLive;
   }

   /** @deprecated */
   @Deprecated
   void addWellKnownAddress(String name, String address, int port) {
      this.wellKnownAddresses.add(new WellKnownAddress(name, address, port));
   }

   void addWellKnownAddress(String name, String address) {
      this.wellKnownAddresses.add(new WellKnownAddress(name, address));
   }

   public WellKnownAddress[] getWellKnownAddresses() {
      return (WellKnownAddress[])this.wellKnownAddresses.toArray(new WellKnownAddress[this.wellKnownAddresses.size()]);
   }

   public String getCustomClusterConfigurationFileName() {
      return this.customClusterConfigurationFileName;
   }

   public String getClusterName() {
      return this.clusterName;
   }

   private void getStartupWka(Properties props) {
      if (this.wellKnownAddresses.size() != 0) {
         StringBuilder sb = new StringBuilder();
         Iterator var3 = this.wellKnownAddresses.iterator();

         while(var3.hasNext()) {
            WellKnownAddress wka = (WellKnownAddress)var3.next();
            if (sb.length() != 0) {
               sb.append(";");
            }

            sb.append(wka.getName()).append(",");
            sb.append(wka.getListenAddress()).append(",");
            sb.append(wka.getListenPort());
         }

         props.setProperty("WellKnownAddresses", sb.toString());
      }
   }

   private void loadWka(Properties props) throws ConfigException {
      String wkas = this.trim(props.getProperty("WellKnownAddresses"));
      if (wkas != null) {
         String[] var3 = wkas.split(";");
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String elem = var3[var5];
            String[] wka = elem.split(",");
            if (wka.length != 2 && wka.length != 3) {
               throw new ConfigException(NodeManagerTextTextFormatter.getInstance().getInvalidPropValue("WellKnownAddresses", wkas));
            }

            try {
               this.addWellKnownAddress(this.trim(wka[0]), this.trim(wka[1]));
            } catch (NumberFormatException var9) {
               throw new ConfigException(NodeManagerTextTextFormatter.getInstance().getInvalidPropValue("WellKnownAddresses", wkas));
            }
         }

      }
   }

   private String trim(String s) {
      if (s != null) {
         s = s.trim();
         if (s.length() == 0) {
            s = null;
         }
      }

      return s;
   }

   public static class ValuesHolder extends StartupConfig.ValuesHolder {
      private String unicastListenAddress;
      private int unicastListenPort;
      private boolean unicastPortAutoAdjust = true;
      private String multicastListenAddress;
      private int multicastListenPort;
      private int timeToLive = 4;
      private final ArrayList wellKnownAddresses = new ArrayList();
      private String customClusterConfigurationFileName;
      private String clusterName;

      public String getUnicastListenAddress() {
         return this.unicastListenAddress;
      }

      public void setUnicastListenAddress(String unicastListenAddress) {
         this.unicastListenAddress = unicastListenAddress;
      }

      public int getUnicastListenPort() {
         return this.unicastListenPort;
      }

      public void setUnicastListenPort(int unicastListenPort) {
         this.unicastListenPort = unicastListenPort;
      }

      public boolean isUnicastPortAutoAdjust() {
         return this.unicastPortAutoAdjust;
      }

      public void setUnicastPortAutoAdjust(boolean unicastPortAutoAdjust) {
         this.unicastPortAutoAdjust = unicastPortAutoAdjust;
      }

      public String getMulticastListenAddress() {
         return this.multicastListenAddress;
      }

      public void setMulticastListenAddress(String multicastListenAddress) {
         this.multicastListenAddress = multicastListenAddress;
      }

      public int getMulticastListenPort() {
         return this.multicastListenPort;
      }

      public void setMulticastListenPort(int multicastListenPort) {
         this.multicastListenPort = multicastListenPort;
      }

      public int getTimeToLive() {
         return this.timeToLive;
      }

      public void setTimeToLive(int timeToLive) {
         this.timeToLive = timeToLive;
      }

      public String getCustomClusterConfigurationFileName() {
         return this.customClusterConfigurationFileName;
      }

      public void setCustomClusterConfigurationFileName(String customClusterConfigurationFileName) {
         this.customClusterConfigurationFileName = customClusterConfigurationFileName;
      }

      public String getClusterName() {
         return this.clusterName;
      }

      public void setClusterName(String clusterName) {
         this.clusterName = clusterName;
      }

      public ArrayList getWellKnownAddresses() {
         return this.wellKnownAddresses;
      }

      /** @deprecated */
      @Deprecated
      public void addWellKnownAddress(String name, String address, int port) {
         this.wellKnownAddresses.add(new WellKnownAddress(name, address, port));
      }

      public void addWellKnownAddress(String name, String address) {
         this.wellKnownAddresses.add(new WellKnownAddress(name, address));
      }

      public CoherenceStartupConfig toStartupConfig() {
         return new CoherenceStartupConfig(this);
      }
   }

   public static class WellKnownAddress {
      private final String name;
      private final String address;
      private final int port;

      /** @deprecated */
      @Deprecated
      private WellKnownAddress(String name, String address, int port) {
         this.name = name;
         this.address = address;
         this.port = port;
      }

      private WellKnownAddress(String name, String address) {
         this.name = name;
         this.address = address;
         this.port = 0;
      }

      public String getName() {
         return this.name;
      }

      public String getListenAddress() {
         return this.address;
      }

      public int getListenPort() {
         return this.port;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append(this.getName()).append(",");
         sb.append(this.getListenAddress()).append(",");
         sb.append(this.getListenPort());
         return sb.toString();
      }

      // $FF: synthetic method
      WellKnownAddress(String x0, String x1, int x2, Object x3) {
         this(x0, x1, x2);
      }

      // $FF: synthetic method
      WellKnownAddress(String x0, String x1, Object x2) {
         this(x0, x1);
      }
   }
}
