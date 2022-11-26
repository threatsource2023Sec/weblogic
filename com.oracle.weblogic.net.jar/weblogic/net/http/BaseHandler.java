package weblogic.net.http;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import javax.net.ssl.HostnameVerifier;
import weblogic.net.NetLogger;

public class BaseHandler extends URLStreamHandler {
   protected boolean isHTTP = true;
   protected static final int HTTP_DEFAULT_PORT = 80;
   protected static final int HTTPS_DEFAULT_PORT = 443;
   private static final String[] NEEDED_CLASSES;
   private static boolean inited;

   protected URLConnection openConnection(URL u) throws IOException {
      return this.openConnection(u, (Proxy)null);
   }

   public static void init() {
      if (!NETEnvironment.getNETEnvironment().useSunHttpHandler()) {
         if (!inited) {
            inited = true;

            try {
               if (SecurityHelper.getBoolean("UseSunHttpHandler")) {
                  return;
               }
            } catch (SecurityException var13) {
            }

            try {
               String jpe = SecurityHelper.getSystemProperty("javaplugin.enabled");

               for(int i = 0; i < NEEDED_CLASSES.length; ++i) {
                  try {
                     Class.forName(NEEDED_CLASSES[i]);
                  } catch (ThreadDeath var11) {
                     throw var11;
                  } catch (Throwable var12) {
                  }
               }

               String sFactory = SecurityHelper.getSystemProperty("weblogic.net.http.URLStreamHandlerFactory");
               if (sFactory != null) {
                  boolean hasException = false;

                  try {
                     Class c = Class.forName(sFactory);
                     Class[] main_signature = new Class[]{String[].class};
                     Object[] args = new Object[]{new String[0]};
                     c.getMethod("main", main_signature).invoke((Object)null, args);
                  } catch (IllegalAccessException var6) {
                     hasException = true;
                     NetLogger.logHandlerInitFailure("IllegalAccessException", sFactory, var6);
                  } catch (InvocationTargetException var7) {
                     hasException = true;
                     NetLogger.logHandlerInitFailure("InvocationTargetException", sFactory, var7);
                  } catch (NoSuchMethodException var8) {
                     hasException = true;
                     NetLogger.logHandlerInitFailure("NoSuchMethodException", sFactory, var8);
                  } catch (ClassNotFoundException var9) {
                     hasException = true;
                     NetLogger.logHandlerInitFailure("ClassNotFoundException", sFactory, var9);
                  } catch (Error var10) {
                     hasException = true;
                     NetLogger.logHandlerInitFailure("Error", sFactory, var10);
                  }

                  if (!hasException) {
                     return;
                  }
               }

               URLStreamHandlerFactory fac = new URLStreamHandlerFactory() {
                  public URLStreamHandler createURLStreamHandler(String proto) {
                     if ("http".equals(proto)) {
                        return new Handler(true);
                     } else {
                        return "https".equals(proto) ? new weblogic.net.https.Handler(false) : null;
                     }
                  }
               };
               if (jpe == null || jpe.equals("false")) {
                  URL.setURLStreamHandlerFactory(fac);
               }
            } catch (SecurityException var14) {
               System.out.println("caught a SecurityException.  That's OK.");
               return;
            } catch (Error var15) {
            }

         }
      }
   }

   static {
      String hv = SecurityHelper.getSystemProperty("SunDefaultHostnameVerifier");
      if (hv != null) {
         try {
            Object verObject = Class.forName(hv).newInstance();
            if (verObject instanceof HostnameVerifier) {
               javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((HostnameVerifier)verObject);
            }
         } catch (Throwable var2) {
         }
      }

      NEEDED_CLASSES = new String[]{"weblogic.net.http.BaseHandler", "weblogic.net.http.Handler", "weblogic.net.http.HttpClient", "weblogic.net.http.HttpOutputStream", "weblogic.net.http.HttpURLConnection", "weblogic.net.http.HttpUnauthorizedException", "weblogic.net.http.KeepAliveCache", "weblogic.net.http.KeepAliveStream", "weblogic.net.http.MessageHeader", "weblogic.net.http.KeepAliveKey", "weblogic.net.http.HttpsURLConnection", "weblogic.net.http.HttpsClient", "weblogic.net.https.Handler"};
      inited = false;
   }
}
