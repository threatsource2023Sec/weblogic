package weblogic.corba.iiop.http;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

public final class TunnelUtils {
   private static final boolean DEBUG = getDebug();
   public static final String TUNNEL_SEND = "/bea_wls_internal/iiop/ClientSend";
   public static final String TUNNEL_RECV = "/bea_wls_internal/iiop/ClientRecv";
   public static final String TUNNEL_LOGIN = "/bea_wls_internal/iiop/ClientLogin";
   public static final String TUNNEL_CLOSE = "/bea_wls_internal/iiop/ClientClose";
   public static final int MSG_HEADER_LENGTH = 12;
   public static final String TUNNEL_OK = "OK";
   public static final String TUNNEL_DEAD = "DEAD";
   public static final String TUNNEL_RETRY = "RETRY";
   public static final String TUNNEL_UNAVAIL = "UNAVAIL";
   public static final String RESULT_HEADER = "WL-Result";
   public static final String TYPE_HEADER = "WL-Type";
   public static final String VERSION_HEADER = "WL-Version";
   public static final String ID_HEADER = "Conn-Id";
   public static final String CONNECT_PARAM_HEADER_LEN = "HL";
   public static final String DEST_HEADER = "WL-Dest";
   public static final String CLUSTER_LIST_HEADER = "WL-List";
   public static final String SCHEME_HEADER = "WL-Scheme";
   public static final String PARAM_CONNECTION_ID = "connectionID";
   public static final String PARAM_RAND = "rand";
   public static final String TUNNEL_COOKIE_NAME = "JSESSIONID";
   public static final String CLIENT_CLOSED_IDS = "WL-Client-closed-ids";
   public static final String PIPE_SEP = "|";
   private static final SecureRandom rand = new SecureRandom();

   private static boolean getDebug() {
      try {
         return Boolean.getBoolean("weblogic.debug.client.http.tunnelUtils");
      } catch (Exception var1) {
         return false;
      }
   }

   public static long getNextRandom() {
      return rand.nextLong() & Long.MAX_VALUE;
   }

   public static void drainStream(InputStream i) throws IOException {
      if (i != null) {
         int r = false;

         int r;
         do {
            r = i.read();
         } while(r != -1);

         i.close();
      }

   }

   public static int toInt(int b) {
      return b & 255;
   }

   public static void readConnectionParams(DataInputStream dIn) throws IOException {
      int headerLen = true;
      String param = "";

      while((param = dIn.readLine()) != null && param.length() != 0) {
         if (param.charAt(0) == "HL".charAt(0) && param.charAt(1) == "HL".charAt(1)) {
            try {
               Integer.parseInt(param.substring(param.indexOf(58) + 1, param.length()));
            } catch (Exception var4) {
               throw new ProtocolException("Invalid parameter: " + param);
            }
         }
      }

   }

   public static Properties getProperties(String allProps) {
      if (allProps == null) {
         return null;
      } else if (allProps.indexOf(61) == -1) {
         return null;
      } else {
         Properties returnProps = new Properties();
         if (allProps.indexOf(44) == -1) {
            setProperty(returnProps, allProps);
            return returnProps;
         } else {
            String[] splitProps = splitCompletely(allProps, ",");

            for(int i = 0; i < splitProps.length; ++i) {
               if (splitProps[i].indexOf(61) != -1) {
                  setProperty(returnProps, splitProps[i]);
               }
            }

            return returnProps;
         }
      }
   }

   public static void setCustomRequestProperties(Properties customHeaders, URLConnection conn) {
      String eachKey;
      String eachVal;
      if (customHeaders != null) {
         for(Enumeration propertyNames = customHeaders.propertyNames(); propertyNames.hasMoreElements(); conn.setRequestProperty(eachKey, eachVal)) {
            eachKey = (String)propertyNames.nextElement();
            eachVal = customHeaders.getProperty(eachKey);
            if (DEBUG) {
               p("setRequestProperty(" + eachKey + "," + eachVal + ")");
            }
         }
      }

   }

   private static String[] splitCompletely(String string, String sep) {
      StringTokenizer stringTokenizer = new StringTokenizer(string, sep);
      int i = stringTokenizer.countTokens();
      String[] st = new String[i];

      for(int j = 0; j < i; ++j) {
         st[j] = stringTokenizer.nextToken();
      }

      return st;
   }

   private static void p(String msg) {
      System.out.println("<TunnelUtils>: " + msg);
   }

   private static void setProperty(Properties props, String keyValue) {
      String key = keyValue.substring(0, keyValue.indexOf(61));
      String val = keyValue.substring(keyValue.indexOf(61) + 1);
      if (DEBUG) {
         p("CustomHeader(" + key + "," + val + ")");
      }

      props.setProperty(key, val);
   }

   public static String[] getRequestArgs(String connectionID) {
      return new String[]{"connectionID=" + connectionID, "rand=" + getNextRandom()};
   }

   public static String[] splitStringToArray(String string, String sep) {
      return splitCompletely(string, sep);
   }

   public static String convertListToString(ArrayList connectionIds, String sep) {
      if (connectionIds.isEmpty()) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder((String)connectionIds.get(0));
         int size = connectionIds.size();

         for(int j = 1; j < size; ++j) {
            sb.append(sep).append((String)connectionIds.get(j));
         }

         return sb.toString();
      }
   }
}
