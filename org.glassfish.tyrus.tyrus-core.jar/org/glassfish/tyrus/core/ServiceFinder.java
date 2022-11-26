package org.glassfish.tyrus.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.ReflectPermission;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public final class ServiceFinder implements Iterable {
   private static final Logger LOGGER = Logger.getLogger(ServiceFinder.class.getName());
   private static final String PREFIX = "META-INF/services/";
   private final Class serviceClass;
   private final String serviceName;
   private final ClassLoader classLoader;
   private final boolean ignoreOnClassNotFound;

   private static Enumeration getResources(ClassLoader loader, String name) throws IOException {
      if (loader == null) {
         return getResources(name);
      } else {
         Enumeration resources = loader.getResources(name);
         return resources != null && resources.hasMoreElements() ? resources : getResources(name);
      }
   }

   private static Enumeration getResources(String name) throws IOException {
      return ServiceFinder.class.getClassLoader() != null ? ServiceFinder.class.getClassLoader().getResources(name) : ClassLoader.getSystemResources(name);
   }

   private static ClassLoader _getContextClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(ReflectionHelper.getContextClassLoaderPA());
   }

   public static ServiceFinder find(Class service, ClassLoader loader) throws ServiceConfigurationError {
      return find(service, loader, false);
   }

   public static ServiceFinder find(Class service, ClassLoader loader, boolean ignoreOnClassNotFound) throws ServiceConfigurationError {
      return new ServiceFinder(service, loader, ignoreOnClassNotFound);
   }

   public static ServiceFinder find(Class service) throws ServiceConfigurationError {
      return find(service, _getContextClassLoader(), false);
   }

   public static ServiceFinder find(Class service, boolean ignoreOnClassNotFound) throws ServiceConfigurationError {
      return find(service, _getContextClassLoader(), ignoreOnClassNotFound);
   }

   public static ServiceFinder find(String serviceName) throws ServiceConfigurationError {
      return new ServiceFinder(Object.class, serviceName, _getContextClassLoader(), false);
   }

   public static void setIteratorProvider(ServiceIteratorProvider sip) throws SecurityException {
      ServiceFinder.ServiceIteratorProvider.setInstance(sip);
   }

   private ServiceFinder(Class service, ClassLoader loader, boolean ignoreOnClassNotFound) {
      this(service, service.getName(), loader, ignoreOnClassNotFound);
   }

   private ServiceFinder(Class service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
      this.serviceClass = service;
      this.serviceName = serviceName;
      this.classLoader = loader;
      this.ignoreOnClassNotFound = ignoreOnClassNotFound;
   }

   public Iterator iterator() {
      return ServiceFinder.ServiceIteratorProvider.getInstance().createIterator(this.serviceClass, this.serviceName, this.classLoader, this.ignoreOnClassNotFound);
   }

   public Object[] toArray() throws ServiceConfigurationError {
      List result = new ArrayList();
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         Object t = var2.next();
         result.add(t);
      }

      return result.toArray((Object[])((Object[])Array.newInstance(this.serviceClass, result.size())));
   }

   public Class[] toClassArray() throws ServiceConfigurationError {
      List result = new ArrayList();
      ServiceIteratorProvider iteratorProvider = ServiceFinder.ServiceIteratorProvider.getInstance();
      Iterator i = iteratorProvider.createClassIterator(this.serviceClass, this.serviceName, this.classLoader, this.ignoreOnClassNotFound);

      while(i.hasNext()) {
         result.add(i.next());
      }

      return (Class[])result.toArray((Class[])((Class[])Array.newInstance(Class.class, result.size())));
   }

   private static void fail(String serviceName, String msg, Throwable cause) throws ServiceConfigurationError {
      ServiceConfigurationError sce = new ServiceConfigurationError(serviceName + ": " + msg);
      sce.initCause(cause);
      throw sce;
   }

   private static void fail(String serviceName, String msg) throws ServiceConfigurationError {
      throw new ServiceConfigurationError(serviceName + ": " + msg);
   }

   private static void fail(String serviceName, URL u, int line, String msg) throws ServiceConfigurationError {
      fail(serviceName, u + ":" + line + ": " + msg);
   }

   private static int parseLine(String serviceName, URL u, BufferedReader r, int lc, List names, Set returned) throws IOException, ServiceConfigurationError {
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
               fail(serviceName, u, lc, LocalizationMessages.ILLEGAL_CONFIG_SYNTAX());
            }

            int cp = ln.codePointAt(0);
            if (!Character.isJavaIdentifierStart(cp)) {
               fail(serviceName, u, lc, LocalizationMessages.ILLEGAL_PROVIDER_CLASS_NAME(ln));
            }

            for(int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
               cp = ln.codePointAt(i);
               if (!Character.isJavaIdentifierPart(cp) && cp != 46) {
                  fail(serviceName, u, lc, LocalizationMessages.ILLEGAL_PROVIDER_CLASS_NAME(ln));
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

   private static Iterator parse(String serviceName, URL u, Set returned) throws ServiceConfigurationError {
      InputStream in = null;
      BufferedReader r = null;
      ArrayList names = new ArrayList();

      try {
         URLConnection uConn = u.openConnection();
         uConn.setUseCaches(false);
         in = uConn.getInputStream();
         r = new BufferedReader(new InputStreamReader(in, "utf-8"));
         int lc = 1;

         while(true) {
            if ((lc = parseLine(serviceName, u, r, lc, names, returned)) >= 0) {
               continue;
            }
         }
      } catch (IOException var16) {
         fail(serviceName, ": " + var16);
      } finally {
         try {
            if (r != null) {
               r.close();
            }

            if (in != null) {
               in.close();
            }
         } catch (IOException var15) {
            fail(serviceName, ": " + var15);
         }

      }

      return names.iterator();
   }

   static {
      OsgiRegistry osgiRegistry = ReflectionHelper.getOsgiRegistryInstance();
      if (osgiRegistry != null) {
         LOGGER.log(Level.CONFIG, "Running in an OSGi environment");
         osgiRegistry.hookUp();
      } else {
         LOGGER.log(Level.CONFIG, "Running in a non-OSGi environment");
      }

   }

   public static final class DefaultServiceIteratorProvider extends ServiceIteratorProvider {
      public Iterator createIterator(Class service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
         return new LazyObjectIterator(service, serviceName, loader, ignoreOnClassNotFound);
      }

      public Iterator createClassIterator(Class service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
         return new LazyClassIterator(service, serviceName, loader, ignoreOnClassNotFound);
      }
   }

   public abstract static class ServiceIteratorProvider {
      private static volatile ServiceIteratorProvider sip;
      private static final Object sipLock = new Object();

      private static ServiceIteratorProvider getInstance() {
         ServiceIteratorProvider result = sip;
         if (result == null) {
            synchronized(sipLock) {
               result = sip;
               if (result == null) {
                  sip = (ServiceIteratorProvider)(result = new DefaultServiceIteratorProvider());
               }
            }
         }

         return (ServiceIteratorProvider)result;
      }

      private static void setInstance(ServiceIteratorProvider sip) throws SecurityException {
         SecurityManager security = System.getSecurityManager();
         if (security != null) {
            ReflectPermission rp = new ReflectPermission("suppressAccessChecks");
            security.checkPermission(rp);
         }

         synchronized(sipLock) {
            ServiceFinder.ServiceIteratorProvider.sip = sip;
         }
      }

      public abstract Iterator createIterator(Class var1, String var2, ClassLoader var3, boolean var4);

      public abstract Iterator createClassIterator(Class var1, String var2, ClassLoader var3, boolean var4);
   }

   private static final class LazyObjectIterator extends AbstractLazyIterator implements Iterator {
      private Object t;

      private LazyObjectIterator(Class service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
         super(service, serviceName, loader, ignoreOnClassNotFound, null);
      }

      public boolean hasNext() throws ServiceConfigurationError {
         if (this.nextName != null) {
            return true;
         } else {
            this.setConfigs();

            while(this.nextName == null) {
               while(this.pending == null || !this.pending.hasNext()) {
                  if (!this.configs.hasMoreElements()) {
                     return false;
                  }

                  this.pending = ServiceFinder.parse(this.serviceName, (URL)this.configs.nextElement(), this.returned);
               }

               this.nextName = (String)this.pending.next();

               try {
                  this.t = this.service.cast(((Class)AccessController.doPrivileged(ReflectionHelper.classForNameWithExceptionPEA(this.nextName, this.loader))).newInstance());
               } catch (InstantiationException var3) {
                  if (this.ignoreOnClassNotFound) {
                     if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                        ServiceFinder.LOGGER.log(Level.CONFIG, LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(this.nextName, this.service, var3.getLocalizedMessage()));
                     }

                     this.nextName = null;
                  } else {
                     ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(this.nextName, this.service, var3.getLocalizedMessage()), var3);
                  }
               } catch (IllegalAccessException var4) {
                  ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(this.nextName, this.service, var4.getLocalizedMessage()), var4);
               } catch (ClassNotFoundException var5) {
                  this.handleClassNotFoundException();
               } catch (NoClassDefFoundError var6) {
                  if (this.ignoreOnClassNotFound) {
                     if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                        ServiceFinder.LOGGER.log(Level.CONFIG, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(var6.getLocalizedMessage(), this.nextName, this.service));
                     }

                     this.nextName = null;
                  } else {
                     ServiceFinder.fail(this.serviceName, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(var6.getLocalizedMessage(), this.nextName, this.service), var6);
                  }
               } catch (PrivilegedActionException var7) {
                  Throwable cause = var7.getCause();
                  if (cause instanceof ClassNotFoundException) {
                     this.handleClassNotFoundException();
                  } else if (cause instanceof ClassFormatError) {
                     if (this.ignoreOnClassNotFound) {
                        if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                           ServiceFinder.LOGGER.log(Level.CONFIG, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(cause.getLocalizedMessage(), this.nextName, this.service));
                        }

                        this.nextName = null;
                     } else {
                        ServiceFinder.fail(this.serviceName, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(cause.getLocalizedMessage(), this.nextName, this.service), cause);
                     }
                  } else {
                     ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(this.nextName, this.service, cause.getLocalizedMessage()), cause);
                  }
               }
            }

            return true;
         }
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.nextName = null;
            if (ServiceFinder.LOGGER.isLoggable(Level.FINEST)) {
               ServiceFinder.LOGGER.log(Level.FINEST, "Loading next object: " + this.t.getClass().getName());
            }

            return this.t;
         }
      }

      private void handleClassNotFoundException() throws ServiceConfigurationError {
         if (this.ignoreOnClassNotFound) {
            if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
               ServiceFinder.LOGGER.log(Level.CONFIG, LocalizationMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
            }

            this.nextName = null;
         } else {
            ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
         }

      }

      // $FF: synthetic method
      LazyObjectIterator(Class x0, String x1, ClassLoader x2, boolean x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   private static final class LazyClassIterator extends AbstractLazyIterator implements Iterator {
      private LazyClassIterator(Class service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
         super(service, serviceName, loader, ignoreOnClassNotFound, null);
      }

      public Class next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            String cn = this.nextName;
            this.nextName = null;

            try {
               Class tClass = (Class)AccessController.doPrivileged(ReflectionHelper.classForNameWithExceptionPEA(cn, this.loader));
               if (ServiceFinder.LOGGER.isLoggable(Level.FINEST)) {
                  ServiceFinder.LOGGER.log(Level.FINEST, "Loading next class: " + tClass.getName());
               }

               return tClass;
            } catch (ClassNotFoundException var4) {
               ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_NOT_FOUND(cn, this.service));
            } catch (PrivilegedActionException var5) {
               Throwable thrown = var5.getCause();
               if (thrown instanceof ClassNotFoundException) {
                  ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_NOT_FOUND(cn, this.service));
               } else if (thrown instanceof NoClassDefFoundError) {
                  ServiceFinder.fail(this.serviceName, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(thrown.getLocalizedMessage(), cn, this.service));
               } else if (thrown instanceof ClassFormatError) {
                  ServiceFinder.fail(this.serviceName, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(thrown.getLocalizedMessage(), cn, this.service));
               } else {
                  ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_CLASS_COULD_NOT_BE_LOADED(cn, this.service, thrown.getLocalizedMessage()), thrown);
               }
            }

            return null;
         }
      }

      // $FF: synthetic method
      LazyClassIterator(Class x0, String x1, ClassLoader x2, boolean x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   private static class AbstractLazyIterator {
      final Class service;
      final String serviceName;
      final ClassLoader loader;
      final boolean ignoreOnClassNotFound;
      Enumeration configs;
      Iterator pending;
      Set returned;
      String nextName;

      private AbstractLazyIterator(Class service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
         this.configs = null;
         this.pending = null;
         this.returned = new TreeSet();
         this.service = service;
         this.serviceName = serviceName;
         this.loader = loader;
         this.ignoreOnClassNotFound = ignoreOnClassNotFound;
      }

      protected final void setConfigs() {
         if (this.configs == null) {
            try {
               String fullName = "META-INF/services/" + this.serviceName;
               this.configs = ServiceFinder.getResources(this.loader, fullName);
            } catch (IOException var2) {
               ServiceFinder.fail(this.serviceName, ": " + var2);
            }
         }

      }

      public boolean hasNext() throws ServiceConfigurationError {
         if (this.nextName != null) {
            return true;
         } else {
            this.setConfigs();

            while(true) {
               do {
                  if (this.nextName != null) {
                     return true;
                  }

                  while(this.pending == null || !this.pending.hasNext()) {
                     if (!this.configs.hasMoreElements()) {
                        return false;
                     }

                     this.pending = ServiceFinder.parse(this.serviceName, (URL)this.configs.nextElement(), this.returned);
                  }

                  this.nextName = (String)this.pending.next();
               } while(!this.ignoreOnClassNotFound);

               try {
                  AccessController.doPrivileged(ReflectionHelper.classForNameWithExceptionPEA(this.nextName, this.loader));
               } catch (ClassNotFoundException var3) {
                  this.handleClassNotFoundException();
               } catch (PrivilegedActionException var4) {
                  Throwable thrown = var4.getException();
                  if (thrown instanceof ClassNotFoundException) {
                     this.handleClassNotFoundException();
                  } else if (thrown instanceof NoClassDefFoundError) {
                     if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                        ServiceFinder.LOGGER.log(Level.CONFIG, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(thrown.getLocalizedMessage(), this.nextName, this.service));
                     }

                     this.nextName = null;
                  } else {
                     if (!(thrown instanceof ClassFormatError)) {
                        if (thrown instanceof RuntimeException) {
                           throw (RuntimeException)thrown;
                        }

                        throw new IllegalStateException(thrown);
                     }

                     if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
                        ServiceFinder.LOGGER.log(Level.CONFIG, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(thrown.getLocalizedMessage(), this.nextName, this.service));
                     }

                     this.nextName = null;
                  }
               }
            }
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      private void handleClassNotFoundException() {
         if (ServiceFinder.LOGGER.isLoggable(Level.CONFIG)) {
            ServiceFinder.LOGGER.log(Level.CONFIG, LocalizationMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
         }

         this.nextName = null;
      }

      // $FF: synthetic method
      AbstractLazyIterator(Class x0, String x1, ClassLoader x2, boolean x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
