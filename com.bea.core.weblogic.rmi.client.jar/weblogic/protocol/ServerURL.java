package weblogic.protocol;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.utils.StringUtils;
import weblogic.utils.UnsyncStringBuffer;
import weblogic.utils.net.InetAddressHelper;

public class ServerURL {
   private static final int MAX_LOCATION_COUNT = 500;
   private static final int DEFAULT_PORT = 7001;
   private static final String DEFAULT_HOST = "localhost";
   public static final int UNKNOWN_PORT = -1;
   private static final String UNKNOWN_FILE = "";
   private static final String PROXY_STRING = "PROXY";
   private static boolean isServer = false;
   public static ServerURL DEFAULT_URL = getDefaultURL();
   public static final ServerURL DEFAULT_CONTEXT = new ServerURL();
   private String protocol;
   private ArrayList locations = new ArrayList(1);
   private int locationCount;
   private String file;
   private String query;
   private int hash;
   private String urlString = null;
   private boolean isProxy = false;
   private static final ProtocolTextTextFormatter formatter = ProtocolTextTextFormatter.getInstance();

   public static void setIsServer(boolean isServerVal) {
      isServer = isServerVal;
      if (isServer) {
         try {
            DEFAULT_URL = new ServerURL(RJVMEnvironment.getEnvironment().getDefaultProtocolName(), "localhost", -1, "");
         } catch (MalformedURLException var2) {
            throw new AssertionError(formatter.msgFailDefaultServerURL(), var2);
         }
      }

   }

   private static ServerURL getDefaultURL() {
      try {
         RJVMEnvironment.getEnvironment().ensureInitialized();
         return new ServerURL(RJVMEnvironment.getEnvironment().getDefaultProtocolName(), "localhost", 7001, "");
      } catch (MalformedURLException var1) {
         throw new AssertionError(formatter.msgFailDefaultServerURL(), var1);
      }
   }

   public ServerURL(String urlString) throws MalformedURLException {
      this.urlString = urlString;
      this.parseURL(DEFAULT_CONTEXT, urlString);
      this.calculateHashCode();
   }

   public ServerURL(ServerURL context, String urlString) throws MalformedURLException {
      this.urlString = urlString;
      this.parseURL(context, urlString);
      this.calculateHashCode();
   }

   public ServerURL(ServerURL url, String file, String query) {
      this.protocol = url.getProtocol();

      for(int i = 0; i < url.locations.size(); ++i) {
         Location location = url.getLocation(i);
         this.addLocation(location.host, location.port);
      }

      if (file == null) {
         this.file = "";
      } else if (file.length() > 0 && file.charAt(0) != '/') {
         this.file = '/' + file;
      } else {
         this.file = file;
      }

      this.query = query == null ? "" : query;
      this.calculateHashCode();
   }

   public ServerURL(String protocol, String host, int port, String file) throws MalformedURLException {
      this.protocol = protocol.toUpperCase();
      this.addLocation(host, port);
      if (file == null) {
         this.file = "";
      } else if (file.length() > 0 && file.charAt(0) != '/') {
         this.file = '/' + file;
      } else {
         this.file = file;
      }

      this.query = "";
      this.calculateHashCode();
   }

   private ServerURL() {
      this.protocol = "";
      this.file = "";
      this.query = "";
      this.addLocation("", -1);
      this.calculateHashCode();
   }

   public String getProtocol() {
      return this.protocol;
   }

   public String getHost() {
      if (this.locations.size() == 1) {
         return this.getLocation(0).host;
      } else {
         int numLoc = 0;

         for(int i = 0; i < this.locations.size(); ++i) {
            if (this.getLocation(i).port == this.getLocation(0).port) {
               ++numLoc;
            }
         }

         UnsyncStringBuffer sb = new UnsyncStringBuffer();

         for(int i = 0; i < numLoc; ++i) {
            sb.append(this.getLocation(i).host);
            if (i < numLoc - 1) {
               sb.append(",");
            }
         }

         return sb.toString();
      }
   }

   public int getPort(int i) {
      return this.getLocation(i).port;
   }

   public InetAddress getInetAddress(int i) {
      return this.getLocation(i).getAddress();
   }

   public String getHost(int i) {
      return this.getLocation(i).host;
   }

   public String getLowerCaseHost(int i) {
      return this.getLocation(i).lowerCaseHost;
   }

   public int getAddressCount() {
      return this.locations.size();
   }

   public String getUrlString() {
      return this.urlString;
   }

   public String getUrlString(int i) {
      return this.getLocation(i).getUrl();
   }

   public int getPort() {
      return this.getPort(0);
   }

   public String getFile() {
      return this.file;
   }

   public String getQuery() {
      return this.query;
   }

   private void incLocationCount(int amt, String url) throws MalformedURLException {
      this.locationCount += amt;
      if (this.locationCount > 500) {
         throw new MalformedURLException("expands to greater than 500 locations: " + url);
      }
   }

   private void skipWhiteSpace(String s, int[] ppos) {
      while(true) {
         if (s.length() < ppos[0]) {
            char c = s.charAt(ppos[0]);
            int var10002 = ppos[0]++;
            if (c == ' ' || c == '\t') {
               continue;
            }
         }

         return;
      }
   }

   private int parsePort(String s, int[] ppos, String url) throws MalformedURLException {
      this.skipWhiteSpace(s, ppos);
      if (s.length() <= ppos[0]) {
         throw new MalformedURLException("port expected: " + url);
      } else {
         char c = s.charAt(ppos[0]);
         int var10002 = ppos[0]++;

         int ret;
         for(ret = c - 48; ppos[0] != s.length() && (c = s.charAt(ppos[0])) >= '0' && c <= '9'; ret = ret * 10 + (c - 48)) {
            var10002 = ppos[0]++;
         }

         this.skipWhiteSpace(s, ppos);
         return ret;
      }
   }

   private ArrayList parsePorts(String s, int[] ppos, String url) throws MalformedURLException {
      ArrayList ret = null;

      while(true) {
         while(true) {
            int portA = this.parsePort(s, ppos, url);
            if (ret == null) {
               ret = new ArrayList();
            }

            this.incLocationCount(1, url);
            ret.add(portA);
            if (ppos[0] == s.length()) {
               return ret;
            }

            int var10002;
            if (s.charAt(ppos[0]) == '+') {
               var10002 = ppos[0]++;
            } else if (s.charAt(ppos[0]) == '-') {
               var10002 = ppos[0]++;
               int portB = this.parsePort(s, ppos, url);
               ++portA;

               while(portA <= portB) {
                  this.incLocationCount(1, url);
                  ret.add(portA);
                  ++portA;
               }

               if (ppos[0] == s.length()) {
                  return ret;
               }

               if (s.charAt(ppos[0]) != '+') {
                  throw new MalformedURLException("bad port: " + url);
               }

               var10002 = ppos[0]++;
            }
         }
      }
   }

   private void parseURL(ServerURL context, String url) throws MalformedURLException {
      url = this.parseClusterURL(url.trim());
      int firstColon;
      if ((firstColon = url.indexOf(":")) == -1) {
         throw new MalformedURLException("no protocol: " + url);
      } else {
         String protocol;
         String rest;
         if (firstColon + 1 == url.length()) {
            protocol = url.substring(0, firstColon);
            rest = "";
         } else {
            if (Character.isDigit(url.charAt(firstColon + 1))) {
               throw new MalformedURLException("no protocol: " + url);
            }

            protocol = url.substring(0, firstColon);
            rest = url.substring(protocol.length() + 1);
         }

         this.protocol = protocol.toUpperCase();
         if (this.getProtocol().equals("PROXY")) {
            this.protocol = "T3";
            this.isProxy = true;
         }

         if (rest.startsWith("//")) {
            String[] addresses = StringUtils.splitCompletely(rest.substring(2), ",");
            if (addresses.length == 0) {
               throw new MalformedURLException("no host: " + url);
            }

            String[] hosts = new String[addresses.length];
            ArrayList[] ports = new ArrayList[addresses.length];
            int[] ppos = new int[1];

            int i;
            for(i = 0; i < addresses.length; ++i) {
               rest = addresses[i];
               int firstSlash = rest.indexOf("/");
               int questionMark = rest.indexOf("?");
               int hostAndPortEnd;
               if (firstSlash != -1 && questionMark != -1) {
                  hostAndPortEnd = Math.min(firstSlash, questionMark);
               } else if (firstSlash == -1 && questionMark != -1) {
                  hostAndPortEnd = questionMark;
               } else if (firstSlash != -1) {
                  hostAndPortEnd = firstSlash;
               } else {
                  hostAndPortEnd = rest.length();
               }

               String hostAndPort = rest.substring(0, hostAndPortEnd);
               int v6StartIndex;
               int var10002;
               if ((v6StartIndex = hostAndPort.indexOf("[")) != -1) {
                  int v6EndIndex = hostAndPort.indexOf("]");
                  if (v6EndIndex == -1) {
                     throw new MalformedURLException(formatter.msgServerURLMissingSquareBrackets(url));
                  }

                  hosts[i] = hostAndPort.substring(v6StartIndex, v6EndIndex + 1);
                  String portList = hostAndPort.substring(v6EndIndex + 1, hostAndPort.length());
                  if (portList.trim().length() > 0 && (ppos[0] = portList.indexOf(":")) != -1) {
                     var10002 = ppos[0]++;
                     ports[i] = this.parsePorts(portList, ppos, url);
                  } else {
                     ports[i] = null;
                  }
               } else if (InetAddressHelper.isIPV6Address(hostAndPort)) {
                  ppos[0] = hostAndPort.lastIndexOf(":");
                  hosts[i] = hostAndPort.substring(0, ppos[0]);
                  var10002 = ppos[0]++;
                  ports[i] = this.parsePorts(hostAndPort, ppos, url);
               } else if ((ppos[0] = hostAndPort.indexOf(":")) == -1) {
                  hosts[i] = hostAndPort;
                  ports[i] = null;
               } else {
                  hosts[i] = hostAndPort.substring(0, ppos[0]);
                  var10002 = ppos[0]++;
                  ports[i] = this.parsePorts(hostAndPort, ppos, url);
               }

               if (hosts[i].length() == 0) {
                  throw new MalformedURLException("no host: " + url);
               }

               rest = rest.substring(hostAndPortEnd);
               this.parseFileAndQueryFromURL(context, rest);
            }

            i = addresses.length - 1;
            ArrayList lastPortList = ports[i];
            if (ports[i] == null) {
               lastPortList = ports[i] = new ArrayList(1);
               ports[i].add(context.getPort());
               this.incLocationCount(1, url);
            }

            for(i = addresses.length - 2; i >= 0; --i) {
               if (ports[i] == null) {
                  ports[i] = lastPortList;
                  this.incLocationCount(ports[i].size(), url);
               } else {
                  lastPortList = ports[i];
               }
            }

            for(i = 0; i < addresses.length; ++i) {
               for(int j = 0; j < ports[i].size(); ++j) {
                  this.addLocation(hosts[i], (Integer)ports[i].get(j));
               }
            }
         } else {
            int colon = rest.indexOf(":");
            if (colon == -1) {
               this.addLocation(context.getHost(), context.getPort());
               if (this.getLastLocation().host.length() == 0) {
                  throw new MalformedURLException("no host: " + url);
               }
            } else {
               try {
                  if (Character.isDigit(rest.charAt(colon + 1))) {
                     throw new MalformedURLException(formatter.msgServerURLMissingHost(url));
                  }
               } catch (StringIndexOutOfBoundsException var18) {
                  throw new MalformedURLException(formatter.msgServerURLMissingPort(url));
               }

               this.addLocation("", context.getPort());
            }

            this.incLocationCount(1, url);
            this.parseFileAndQueryFromURL(context, rest);
         }

      }
   }

   private void parseFileAndQueryFromURL(ServerURL context, String rest) {
      int questionMark;
      if ((questionMark = rest.indexOf("?")) != -1) {
         this.file = rest.substring(0, questionMark);
         this.query = rest.substring(questionMark);
      } else {
         this.file = rest;
         if (context != null) {
            this.query = context.query;
         }
      }

      if (this.file.length() > 0 && !this.file.startsWith("/")) {
         this.file = "/" + this.file;
      }

      if (this.file.length() > 1 && this.file.endsWith("/")) {
         this.file = this.file.substring(0, this.file.length() - 1);
      }

   }

   protected String parseClusterURL(String url) throws MalformedURLException {
      return ClusterURLFactory.getInstance().parseClusterURL(url);
   }

   private void addLocation(String host, int port) {
      this.locations.add(new Location(host, port));
   }

   protected Location getLocation(int i) {
      return (Location)this.locations.get(i);
   }

   private Location getLastLocation() {
      return (Location)this.locations.get(this.locations.size() - 1);
   }

   public UnsyncStringBuffer asUnsyncStringBuffer() {
      UnsyncStringBuffer sb = new UnsyncStringBuffer();
      sb.append(this.protocol.toLowerCase()).append("://");

      for(int i = 0; i < this.locations.size(); ++i) {
         sb.append(this.getLocation(i).host);
         if (i < this.locations.size() - 1) {
            if (this.getLocation(i).port != this.getLocation(i + 1).port) {
               sb.append(":").append(this.getLocation(i).port);
            }

            sb.append(",");
         }
      }

      if (this.getLastLocation().port != -1) {
         sb.append(":").append(this.getLastLocation().port);
      }

      sb.append(this.file);
      if (this.query != null && this.query.length() != 0) {
         sb.append(this.query);
      }

      return sb;
   }

   public String toString() {
      return this.asUnsyncStringBuffer().toString();
   }

   private void calculateHashCode() {
      this.hash = this.protocol.hashCode() ^ this.query.hashCode();

      for(int i = 0; i < this.locations.size(); ++i) {
         this.hash ^= this.getLocation(i).lowerCaseHost.hashCode();
         this.hash ^= this.getLocation(i).port;
      }

   }

   public int hashCode() {
      return this.hash;
   }

   public boolean equals(Object other) {
      try {
         ServerURL o = (ServerURL)other;
         if (o.locations.size() != this.locations.size()) {
            return false;
         } else {
            for(int i = 0; i < this.locations.size(); ++i) {
               if (o.getLocation(i).port != this.getLocation(i).port || !o.getLocation(i).lowerCaseHost.equals(this.getLocation(i).lowerCaseHost)) {
                  return false;
               }
            }

            return o.protocol.equals(this.protocol) && o.query.equals(this.query) && (o.file.length() == 0 && this.file.length() == 0 || o.file.equals(this.file));
         }
      } catch (ClassCastException var4) {
         return false;
      }
   }

   public boolean isProxy() {
      return this.isProxy;
   }

   public static void main(String[] args) {
      try {
         System.out.println(new ServerURL(DEFAULT_URL, args[0]));
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private final class Location {
      int port;
      String host;
      String lowerCaseHost;
      String urlString;
      InetAddress address;
      boolean addressResolved;

      Location(String _host, int _port) {
         this.port = _port;
         this.host = _host;
         this.lowerCaseHost = _host.toLowerCase();
      }

      String getUrl() {
         if (this.urlString == null) {
            StringBuilder buffer = new StringBuilder(ServerURL.this.getProtocol().toLowerCase());
            buffer.append("://");
            if (this.getAddress() != null && this.getAddress() instanceof Inet6Address && this.host.indexOf(58) != -1 && this.host.indexOf(91) == -1) {
               buffer.append('[').append(this.host).append(']');
            } else {
               buffer.append(this.host);
            }

            buffer.append(':').append(this.port);
            if (ServerURL.this.file != null) {
               buffer.append(ServerURL.this.file);
            }

            if (ServerURL.this.query != null) {
               buffer.append(ServerURL.this.query);
            }

            this.urlString = buffer.toString();
         }

         return this.urlString;
      }

      InetAddress getAddress() {
         if (!this.addressResolved) {
            try {
               this.address = InetAddress.getByName(this.host);
            } catch (UnknownHostException var2) {
            }

            this.addressResolved = true;
         }

         return this.address;
      }
   }
}
