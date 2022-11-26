package weblogic.servlet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.Source;

public class ServiceLoader {
   private static final String PREFIX = "META-INF/services/";
   private static final ConcurrentHashMap caches = new ConcurrentHashMap();

   private ServiceLoader() {
   }

   public static ServiceLoader getInstance() {
      return ServiceLoader.Holder.INSTANCE;
   }

   public Set findService(Class serviceClass, ClassFinder finder, List filterPattern) {
      String serviceName = serviceClass.getName();
      String serviceURL = "META-INF/services/" + serviceName;
      Set globalServices = Collections.EMPTY_SET;
      if (!this.shouldFilterBy(serviceURL, filterPattern)) {
         globalServices = (Set)caches.get(serviceName);
         if (globalServices == null) {
            globalServices = this.findGlobalService(serviceClass);
            Set gs = (Set)caches.putIfAbsent(serviceName, globalServices);
            if (gs != null) {
               globalServices = gs;
            }
         }
      }

      Set result = new HashSet();
      result.addAll(globalServices);
      this.doFilter(result, filterPattern);
      if (finder == null) {
         return result;
      } else {
         Enumeration sources = finder.getSources(serviceURL);

         while(sources.hasMoreElements()) {
            Source source = (Source)sources.nextElement();
            URL url = source.getURL();
            result.addAll(this.parse(serviceClass, url));
         }

         return result;
      }
   }

   private void doFilter(Set result, List filterPattern) {
      if (filterPattern != null && !filterPattern.isEmpty()) {
         Iterator iter = result.iterator();

         while(iter.hasNext()) {
            String name = (String)iter.next();
            if (this.shouldFilterBy(name, filterPattern)) {
               iter.remove();
            }
         }

      }
   }

   private boolean shouldFilterBy(String resourceName, List filterPattern) {
      Iterator var3 = filterPattern.iterator();

      Matcher matcher;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         Pattern pattern = (Pattern)var3.next();
         matcher = pattern.matcher(resourceName);
      } while(!matcher.find());

      return true;
   }

   private Set findGlobalService(Class serviceClass) {
      ClassLoader globlLoader = AugmentableClassLoaderManager.getAugmentableSystemClassLoader();
      java.util.ServiceLoader standardServiceLoader = java.util.ServiceLoader.load(serviceClass, globlLoader);
      Set result = new HashSet();
      Iterator var5 = standardServiceLoader.iterator();

      while(var5.hasNext()) {
         Object object = var5.next();
         String clazzName = object.getClass().getName();
         result.add(clazzName);
      }

      Set result = Collections.unmodifiableSet(result);
      return result;
   }

   private Set parse(Class service, URL u) throws ServiceConfigurationError {
      InputStream in = null;
      BufferedReader r = null;
      Set names = new HashSet();

      try {
         in = u.openStream();
         r = new BufferedReader(new InputStreamReader(in, "utf-8"));
         int lc = 1;

         while(true) {
            if ((lc = this.parseLine(service, u, r, lc, names)) >= 0) {
               continue;
            }
         }
      } catch (IOException var15) {
         fail(service, "Error reading configuration file", var15);
      } finally {
         try {
            if (r != null) {
               r.close();
            }

            if (in != null) {
               in.close();
            }
         } catch (IOException var14) {
            fail(service, "Error closing configuration file", var14);
         }

      }

      return names;
   }

   private static void fail(Class service, String msg, Throwable cause) throws ServiceConfigurationError {
      throw new ServiceConfigurationError(service.getName() + ": " + msg, cause);
   }

   private static void fail(Class service, URL u, int line, String msg) throws ServiceConfigurationError {
      fail(service, u + ":" + line + ": " + msg);
   }

   private static void fail(Class service, String msg) throws ServiceConfigurationError {
      throw new ServiceConfigurationError(service.getName() + ": " + msg);
   }

   private int parseLine(Class service, URL u, BufferedReader r, int lc, Set names) throws IOException, ServiceConfigurationError {
      String ln = r.readLine();
      if (ln == null) {
         return -1;
      } else {
         int ci = ln.indexOf(35);
         if (ci >= 0) {
            ln = ln.substring(0, ci);
         }

         ln = ln.trim();
         int n = ln.length();
         if (n != 0) {
            if (ln.indexOf(32) >= 0 || ln.indexOf(9) >= 0) {
               fail(service, u, lc, "Illegal configuration-file syntax");
            }

            int cp = ln.codePointAt(0);
            if (!Character.isJavaIdentifierStart(cp)) {
               fail(service, u, lc, "Illegal provider-class name: " + ln);
            }

            for(int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
               cp = ln.codePointAt(i);
               if (!Character.isJavaIdentifierPart(cp) && cp != 46) {
                  fail(service, u, lc, "Illegal provider-class name: " + ln);
               }
            }

            if (!names.contains(ln)) {
               names.add(ln);
            }
         }

         return lc + 1;
      }
   }

   // $FF: synthetic method
   ServiceLoader(Object x0) {
      this();
   }

   private static class Holder {
      private static final ServiceLoader INSTANCE = new ServiceLoader();
   }
}
