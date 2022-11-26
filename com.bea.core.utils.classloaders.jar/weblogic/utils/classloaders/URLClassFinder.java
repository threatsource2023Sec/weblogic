package weblogic.utils.classloaders;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.AssertionError;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.debug.ClassLoaderDebugger;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.net.InetAddressHelper;

public class URLClassFinder extends AbstractClassFinder {
   private static final DebugLogger vDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingVerbose");
   private final String baseURL;

   public URLClassFinder(String servletURL) {
      if (servletURL == null) {
         throw new IllegalArgumentException("URL cannot be null");
      } else {
         String u = InetAddressHelper.isIPV6Address(servletURL) ? InetAddressHelper.convertToIPV6URL(servletURL) : servletURL;
         String s = HttpParsing.escape(u);
         if (!StringUtils.endsWith(s, '/')) {
            s = s + '/';
         }

         this.baseURL = s;
      }
   }

   public Source getSource(String name) {
      while(StringUtils.startsWith(name, '/')) {
         name = name.substring(1);
      }

      name = HttpParsing.escape(name);

      try {
         URL url = new URL(this.baseURL + name);
         if (vDebugLogger.isDebugEnabled()) {
            ClassLoaderDebugger.verbose(this, "getSource", name, "Getting source from URL " + url);
         }

         URLSource s = new URLSource(url);
         return new ByteArraySource(s.getBytes(), url);
      } catch (IOException var4) {
         if (vDebugLogger.isDebugEnabled()) {
            ClassLoaderDebugger.verbose(this, "getSource", name, "Could not load class: '" + name + "'", var4);
         }

         return null;
      }
   }

   public String getClassPath() {
      return "";
   }

   public String toString() {
      return super.toString() + " - url: '" + this.baseURL + "'";
   }

   static {
      try {
         Class handler = null;

         try {
            handler = Class.forName("weblogic.net.http.Handler");
         } catch (ClassNotFoundException var3) {
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            handler = Class.forName("weblogic.net.http.Handler", true, systemClassLoader);
         }

         Method m = handler.getMethod("init", (Class[])null);
         m.invoke(handler, (Object[])null);
      } catch (Exception var4) {
         throw new AssertionError("Cannot initialize weblogic protocol handler", var4);
      }
   }
}
