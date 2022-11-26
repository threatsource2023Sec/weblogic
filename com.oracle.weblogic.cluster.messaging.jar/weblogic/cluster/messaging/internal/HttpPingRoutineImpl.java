package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import weblogic.net.http.HttpURLConnection;
import weblogic.utils.collections.ConcurrentHashSet;

public class HttpPingRoutineImpl implements PingRoutine {
   private static final boolean DEBUG;
   private static final int connectTimeout;
   private static final int readTimeout;
   private static final String http = "http";
   private static final String reqURI = "wls_does_not_exist_url";
   private static final HttpPingRoutineImpl THE_ONE;
   private static final Set pendingPings;

   public static HttpPingRoutineImpl getInstance() {
      return THE_ONE;
   }

   public long ping(ServerConfigurationInformation info) {
      if (pendingPings.contains(info)) {
         if (DEBUG) {
            debug("There is already a ping pending for " + info);
         }

         return 0L;
      } else {
         pendingPings.add(info);
         long response = 0L;

         try {
            info.refreshAddress();
            response = this.httpPing(info.getAddress().getHostName(), info.getPort());
         } catch (IOException var5) {
            if (DEBUG) {
               debug("HttpPing Caught IOException: " + var5);
            }
         }

         pendingPings.remove(info);
         return response;
      }
   }

   private long httpPing(String host, int port) {
      try {
         URL url = new URL("http", host, port, "wls_does_not_exist_url");
         HttpURLConnection connection = new HttpURLConnection(url);
         connection.setConnectTimeout(connectTimeout);
         connection.setReadTimeout(readTimeout);
         connection.connect();
         if (this.isError(connection.getResponseCode())) {
            if (DEBUG) {
               debug("The http response code is an error (" + connection.getResponseCode() + ") and this httpPing will return 0");
            }

            return 0L;
         } else {
            if (DEBUG) {
               debug("The http response code was not an error (" + connection.getResponseCode() + ") and this httpPing will return 1");
            }

            return 1L;
         }
      } catch (MalformedURLException var5) {
         throw new AssertionError("Could not ping server at host: " + host + " and port: " + port);
      } catch (IOException var6) {
         if (DEBUG) {
            debug("HttpPing Caught IOException: " + var6);
         }

         return 0L;
      }
   }

   private boolean isError(int respCode) {
      return 300 <= respCode && respCode < 400 || 500 <= respCode && respCode < 600;
   }

   public static void main(String[] args) throws Exception {
      assert args != null;

      assert args.length == 4;

      if (args[0].equals("-host")) {
         String host = args[1];
         if (args[2].equals("-port")) {
            String tmpPort = args[3];
            int port = Integer.parseInt(tmpPort);
            System.out.println("HttpPing returns: " + THE_ONE.httpPing(host, port));
         } else {
            System.out.println("ERROR: HttpPingRoutingImpl -host <host> -port <port>");
         }
      } else {
         System.out.println("ERROR: HttpPingRoutingImpl -host <host> -port <port>");
      }
   }

   private static void debug(String s) {
      Environment.getLogService().debug("[HttpPingRoutine] " + s);
   }

   static {
      DEBUG = Environment.DEBUG;
      connectTimeout = Integer.parseInt(System.getProperty("httpPing.connectTimeout", "3000"));
      readTimeout = Integer.parseInt(System.getProperty("httpPing.readTimeout", "6000"));
      THE_ONE = new HttpPingRoutineImpl();
      pendingPings = new ConcurrentHashSet();
   }
}
