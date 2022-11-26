package org.glassfish.grizzly.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public final class ServiceFinder implements Iterable {
   private static final String prefix = "META-INF/services/";
   private final Class serviceClass;
   private final ClassLoader classLoader;

   public static ServiceFinder find(Class service, ClassLoader loader) {
      return new ServiceFinder(service, loader);
   }

   public static ServiceFinder find(Class service) {
      return find(service, Thread.currentThread().getContextClassLoader());
   }

   private ServiceFinder(Class service, ClassLoader loader) {
      this.serviceClass = service;
      this.classLoader = loader;
   }

   public Iterator iterator() {
      return new LazyIterator(this.serviceClass, this.classLoader);
   }

   public Object[] toArray() {
      List result = new ArrayList();
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         Object t = var2.next();
         result.add(t);
      }

      return result.toArray((Object[])((Object[])Array.newInstance(this.serviceClass, result.size())));
   }

   private static void fail(Class service, String msg, Throwable cause) throws ServiceConfigurationError {
      ServiceConfigurationError sce = new ServiceConfigurationError(service.getName() + ": " + msg);
      sce.initCause(cause);
      throw sce;
   }

   private static void fail(Class service, String msg) throws ServiceConfigurationError {
      throw new ServiceConfigurationError(service.getName() + ": " + msg);
   }

   private static void fail(Class service, URL u, int line, String msg) throws ServiceConfigurationError {
      fail(service, u + ":" + line + ": " + msg);
   }

   private static int parseLine(Class service, URL u, BufferedReader r, int lc, List names, Set returned) throws IOException, ServiceConfigurationError {
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

            if (!returned.contains(ln)) {
               names.add(ln);
               returned.add(ln);
            }
         }

         return lc + 1;
      }
   }

   private static Iterator parse(Class service, URL u, Set returned) throws ServiceConfigurationError {
      InputStream in = null;
      BufferedReader r = null;
      ArrayList names = new ArrayList();

      try {
         in = u.openStream();
         r = new BufferedReader(new InputStreamReader(in, "utf-8"));
         int lc = 1;

         while(true) {
            if ((lc = parseLine(service, u, r, lc, names, returned)) >= 0) {
               continue;
            }
         }
      } catch (IOException var15) {
         fail(service, ": " + var15);
      } finally {
         try {
            if (r != null) {
               r.close();
            }

            if (in != null) {
               in.close();
            }
         } catch (IOException var14) {
            fail(service, ": " + var14);
         }

      }

      return names.iterator();
   }

   private static class LazyIterator implements Iterator {
      final Class service;
      final ClassLoader loader;
      Enumeration configs;
      Iterator pending;
      final Set returned;
      String nextName;
      URL currentConfig;

      private LazyIterator(Class service, ClassLoader loader) {
         this.configs = null;
         this.pending = null;
         this.returned = new TreeSet();
         this.nextName = null;
         this.currentConfig = null;
         this.service = service;
         this.loader = loader;
      }

      public boolean hasNext() throws ServiceConfigurationError {
         if (this.nextName != null) {
            return true;
         } else {
            if (this.configs == null) {
               try {
                  String fullName = "META-INF/services/" + this.service.getName();
                  if (this.loader == null) {
                     this.configs = ClassLoader.getSystemResources(fullName);
                  } else {
                     this.configs = this.loader.getResources(fullName);
                  }
               } catch (IOException var2) {
                  ServiceFinder.fail(this.service, ": " + var2);
               }
            }

            while(this.pending == null || !this.pending.hasNext()) {
               if (!this.configs.hasMoreElements()) {
                  return false;
               }

               this.currentConfig = (URL)this.configs.nextElement();
               this.pending = ServiceFinder.parse(this.service, this.currentConfig, this.returned);
            }

            this.nextName = (String)this.pending.next();
            return true;
         }
      }

      public Object next() throws ServiceConfigurationError {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            String cn = this.nextName;
            this.nextName = null;

            try {
               return this.service.cast(Class.forName(cn, true, this.loader).newInstance());
            } catch (ClassNotFoundException var3) {
               ServiceFinder.fail(this.service, "Provider " + cn + " is specified in " + this.currentConfig + " but not found");
            } catch (Exception var4) {
               ServiceFinder.fail(this.service, "Provider " + cn + " is specified in " + this.currentConfig + "but could not be instantiated: " + var4, var4);
            }

            return null;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      LazyIterator(Class x0, ClassLoader x1, Object x2) {
         this(x0, x1);
      }
   }
}
