package org.glassfish.admin.rest.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.UriInfo;

public class AsyncUtil {
   private static final String PREFER_RESPOND_ASYNC = "respond-async";
   private static final String PREFER_WAIT = "wait";
   private static final String PREFER_WAIT_MILLI_SECONDS = "wait-milli-seconds";
   private static final String PREFER_INTERVAL_TO_POLL_MILLI_SECONDS = "interval-to-poll-milli-seconds";

   public static Invocation.Builder configureSyncAsync(Invocation.Builder builder, boolean isAsync, int syncMaxWaitMilliSeconds, int intervalToPollMilliSeconds) throws Exception {
      if (isAsync) {
         builder = addPreference(builder, "respond-async");
      } else {
         if (syncMaxWaitMilliSeconds != -1) {
            if (syncMaxWaitMilliSeconds % 1000 == 0) {
               builder = addPreference(builder, "wait=" + syncMaxWaitMilliSeconds / 1000);
            } else {
               builder = addPreference(builder, "wait-milli-seconds=" + syncMaxWaitMilliSeconds);
            }
         }

         if (intervalToPollMilliSeconds != -1) {
            builder = addPreference(builder, "interval-to-poll-milli-seconds=" + intervalToPollMilliSeconds);
         }
      }

      return builder;
   }

   private static Invocation.Builder addPreference(Invocation.Builder builder, String pref) throws Exception {
      return builder.header("Prefer", pref);
   }

   public static boolean isAsync(HttpServletRequest request, UriInfo uriInfo) throws Exception {
      Map prefs = getPreferences(request);
      if (parseOptionalInt((String)prefs.get("wait")) == -1 && parseOptionalInt((String)prefs.get("wait-milli-seconds")) == -1 && parseOptionalInt((String)prefs.get("interval-to-poll-milli-seconds")) == -1) {
         return "".equals(prefs.get("respond-async"));
      } else {
         return false;
      }
   }

   public static int getSyncMaxWaitMilliSeconds(HttpServletRequest request, UriInfo uriInfo) throws Exception {
      Map prefs = getPreferences(request);
      int val = parseOptionalInt((String)prefs.get("wait".toLowerCase()));
      if (val != -1) {
         return 1000 * val;
      } else {
         val = parseOptionalInt((String)prefs.get("wait-milli-seconds"));
         return val;
      }
   }

   public static int getIntervalToPollMilliSeconds(HttpServletRequest request, UriInfo uriInfo) throws Exception {
      int val = parseOptionalInt((String)getPreferences(request).get("interval-to-poll-milli-seconds"));
      return val;
   }

   private static int parseOptionalInt(String val) throws Exception {
      try {
         return Integer.parseInt(val);
      } catch (Exception var2) {
         return -1;
      }
   }

   private static Map getPreferences(HttpServletRequest request) throws Exception {
      Map prefs = new HashMap();
      Enumeration prefHeaders = request.getHeaders("Prefer");

      while(prefHeaders.hasMoreElements()) {
         getPreferences(prefs, (String)prefHeaders.nextElement());
      }

      return prefs;
   }

   private static void getPreferences(Map prefs, String prefHeader) throws Exception {
      StringTokenizer st = new StringTokenizer(prefHeader, ",");

      while(st.hasMoreElements()) {
         getPreference(prefs, st.nextToken());
      }

   }

   private static void getPreference(Map prefs, String pref) throws Exception {
      int idx = pref.indexOf("=");
      String key = null;
      String value = null;
      if (idx == -1) {
         key = pref;
         value = "";
      } else {
         key = pref.substring(0, idx);
         value = pref.substring(idx + 1);
      }

      key = key.trim().toLowerCase();
      value = value.trim();
      if (!prefs.containsKey(key)) {
         if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
         }

         prefs.put(key, value);
      }

   }
}
