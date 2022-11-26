package weblogic.corba.j2ee.naming;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.naming.InvalidNameException;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import weblogic.iiop.RequestUrl;

public class EndPointSelector {
   private static int defaultMinorVersion = 2;
   private String protocol = "iiop";
   private String host = "";
   private int port = -1;
   private int majorVersion = 1;
   private int minorVersion;
   private String serviceName;
   private String path;
   static final List VALID_PROTOCOLS = Arrays.asList("http", "https", "rir", "iiop", "iiops", "tgiop");

   public EndPointSelector() {
      this.minorVersion = defaultMinorVersion;
   }

   public EndPointSelector(String protocol, String host, int port, int majorVersion, int minorVersion, String serviceName) {
      this.minorVersion = defaultMinorVersion;
      this.protocol = protocol;
      this.host = host;
      this.port = port;
      this.majorVersion = majorVersion;
      this.minorVersion = minorVersion;
      this.serviceName = serviceName;
   }

   private EndPointSelector(String serviceName) {
      this.minorVersion = defaultMinorVersion;
      this.serviceName = serviceName;
   }

   public static void setDefaultMinorVersion(int minorversion) {
      defaultMinorVersion = minorversion;
   }

   Object redirectToSelectedPartition(Object initialReference) {
      if (initialReference == null) {
         return null;
      } else {
         java.lang.Object var3;
         try {
            RequestUrl.set(this.getRequestUrl());
            initialReference._non_existent();
            Object var2 = initialReference;
            return var2;
         } catch (SystemException var7) {
            var3 = null;
         } finally {
            RequestUrl.clear();
         }

         return (Object)var3;
      }
   }

   public String getKey() {
      return this.protocol + this.getAddress();
   }

   public String getProtocol() {
      return this.protocol;
   }

   public String getHost() {
      return this.host.startsWith("[") && this.host.endsWith("]") ? this.host.substring(1, this.host.length() - 1) : this.host;
   }

   public int getPort() {
      return this.port;
   }

   public int getMajorVersion() {
      return this.majorVersion;
   }

   public int getMinorVersion() {
      return this.minorVersion;
   }

   public String getServiceName() {
      return this.serviceName;
   }

   public String getAddress() {
      return this.majorVersion + "." + this.minorVersion + "@" + this.host + (this.port > 0 ? ":" + this.port : "");
   }

   public String getCorbalocURL() {
      return "corbaloc:" + this.protocol + ":" + this.getAddress() + "/" + this.serviceName;
   }

   public String getPath() {
      return this.path;
   }

   public String getRequestUrl() {
      StringBuilder sb = new StringBuilder(this.protocol);
      sb.append("://").append(this.host);
      if (this.port > 0) {
         sb.append(':').append(this.port);
      }

      if (this.path != null) {
         sb.append('/').append(this.path);
      }

      return sb.toString();
   }

   public String toString() {
      return "EndPointSelector{ " + this.protocol + "://" + this.host + ":" + this.port + (this.serviceName == null ? "" : "/" + this.serviceName) + "}";
   }

   public static EndPointSelector createServiceEndPoint(String serviceName, String address) throws InvalidNameException {
      EndPointSelector epi = new EndPointSelector(serviceName);
      if (!address.contains(":")) {
         throw new InvalidNameException("No protocol specified in substring " + address);
      } else {
         String[] parts = address.split(":", 2);
         epi.setProtocol(parts[0]);
         parseVersionAndAddress(epi, parts[1]);
         return epi;
      }
   }

   public boolean equals(java.lang.Object o) {
      return this == o || o instanceof EndPointSelector && this.equals((EndPointSelector)o);
   }

   private boolean equals(EndPointSelector o) {
      return this.protocol.equals(o.protocol) && this.host.equals(o.host) && this.port == o.port && this.majorVersion == o.majorVersion && this.minorVersion == o.minorVersion && Objects.equals(this.serviceName, o.serviceName) && Objects.equals(this.path, o.path);
   }

   public int hashCode() {
      int result = this.protocol.hashCode();
      result = 31 * result + this.host.hashCode();
      result = 31 * result + this.port;
      result = 31 * result + this.majorVersion;
      result = 31 * result + this.minorVersion;
      result = 31 * result + (this.serviceName != null ? this.serviceName.hashCode() : 0);
      result = 31 * result + (this.path != null ? this.path.hashCode() : 0);
      return result;
   }

   private void setProtocol(String protocolString) throws InvalidNameException {
      if (!protocolString.equals("rir") && !protocolString.equals("")) {
         if (!VALID_PROTOCOLS.contains(protocolString)) {
            throw new InvalidNameException("Unknown protocol: " + protocolString);
         }

         this.protocol = protocolString;
      } else {
         this.protocol = "iiop";
      }

   }

   private static void parseVersionAndAddress(EndPointSelector epi, String address) throws InvalidNameException {
      if (address.contains("@")) {
         epi.parseAddress(address.split("@"));
      } else {
         epi.parseAddress(address);
      }

   }

   private void parseAddress(String[] versionAndAddress) throws InvalidNameException {
      this.parseVersion(versionAndAddress[0]);
      this.parseAddress(versionAndAddress[1]);
   }

   private void parseVersion(String version) throws InvalidNameException {
      if (!version.contains(".")) {
         throw new InvalidNameException("GIOP version missing '.'");
      } else {
         this.majorVersion = this.parseInt("major version", version.split("\\.")[0]);
         this.minorVersion = this.parseInt("minor version", version.split("\\.")[1]);
      }
   }

   private void parseAddress(String address) throws InvalidNameException {
      if (address.contains(":")) {
         this.setHostPort(this.splitHostAndPort(address, address.lastIndexOf(58)));
      } else {
         this.setHost(address);
      }

   }

   private String[] splitHostAndPort(String addressAndPort, int colonPos) {
      return new String[]{addressAndPort.substring(0, colonPos), addressAndPort.substring(colonPos + 1)};
   }

   private void setHost(String host) throws InvalidNameException {
      if (host.equals("")) {
         throw new InvalidNameException("Host name missing");
      } else {
         this.host = host;
      }
   }

   private void setHostPort(String[] hostAndPort) throws InvalidNameException {
      this.setHost(hostAndPort[0]);
      this.port = this.parseInt("port", hostAndPort[1]);
   }

   private int parseInt(String fieldName, String integerString) throws InvalidNameException {
      try {
         return Integer.parseInt(integerString);
      } catch (NumberFormatException var4) {
         throw new InvalidNameException(fieldName + " must be an integer");
      }
   }

   static EndPointSelector createSimpleEndPoint(String protocol, String endPointString) throws InvalidNameException {
      EndPointSelector epi = new EndPointSelector("NameService");
      int colonIndex = endPointString.lastIndexOf(58);
      if (protocol.equals("tgiop")) {
         colonIndex = 0;
      } else if (colonIndex < 0) {
         throw new InvalidNameException("No port specified");
      }

      epi.setProtocol(protocol);
      int slashIndex = endPointString.indexOf(47, colonIndex);
      if (slashIndex < 0) {
         epi.parseAddress(endPointString);
      } else {
         epi.path = endPointString.substring(slashIndex + 1);
         epi.parseAddress(endPointString.substring(0, slashIndex));
      }

      return epi;
   }
}
