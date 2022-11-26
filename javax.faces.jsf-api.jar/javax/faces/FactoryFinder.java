package javax.faces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKitFactory;

public final class FactoryFinder {
   public static final String APPLICATION_FACTORY = "javax.faces.application.ApplicationFactory";
   public static final String FACES_CONTEXT_FACTORY = "javax.faces.context.FacesContextFactory";
   public static final String LIFECYCLE_FACTORY = "javax.faces.lifecycle.LifecycleFactory";
   public static final String RENDER_KIT_FACTORY = "javax.faces.render.RenderKitFactory";
   private static final FactoryManagerCache FACTORIES_CACHE = new FactoryManagerCache();
   private static final String[] FACTORY_NAMES = new String[]{"javax.faces.application.ApplicationFactory", "javax.faces.context.FacesContextFactory", "javax.faces.lifecycle.LifecycleFactory", "javax.faces.render.RenderKitFactory"};
   private static Map factoryClasses = null;
   private static final Logger LOGGER = Logger.getLogger("javax.faces", "javax.faces.LogStrings");

   FactoryFinder() {
   }

   public static Object getFactory(String factoryName) throws FacesException {
      validateFactoryName(factoryName);
      ClassLoader classLoader = getClassLoader();
      FactoryManager manager = FACTORIES_CACHE.getApplicationFactoryManager(classLoader);
      return manager.getFactory(classLoader, factoryName);
   }

   public static void setFactory(String factoryName, String implName) {
      validateFactoryName(factoryName);
      ClassLoader classLoader = getClassLoader();
      FactoryManager manager = FACTORIES_CACHE.getApplicationFactoryManager(classLoader);
      manager.addFactory(factoryName, implName);
   }

   public static void releaseFactories() throws FacesException {
      ClassLoader cl = getClassLoader();
      FACTORIES_CACHE.removeApplicationFactoryManager(cl);
   }

   private static ClassLoader getClassLoader() throws FacesException {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl == null) {
         throw new FacesException("getContextClassLoader");
      } else {
         return cl;
      }
   }

   private static Object getImplementationInstance(ClassLoader classLoader, String factoryName, List implementations) throws FacesException {
      Object result = null;
      String curImplClass;
      int len;
      if (null != implementations && (1 < (len = implementations.size()) || 1 == len)) {
         curImplClass = (String)implementations.remove(len - 1);
         result = getImplGivenPreviousImpl(classLoader, factoryName, curImplClass, (Object)null);
      }

      List fromServices = getImplNameFromServices(classLoader, factoryName);
      String name;
      if (fromServices != null) {
         for(Iterator var7 = fromServices.iterator(); var7.hasNext(); result = getImplGivenPreviousImpl(classLoader, factoryName, name, result)) {
            name = (String)var7.next();
         }
      }

      if (null != implementations) {
         for(len = implementations.size() - 1; 0 <= len; --len) {
            curImplClass = (String)implementations.remove(len);
            result = getImplGivenPreviousImpl(classLoader, factoryName, curImplClass, result);
         }
      }

      return result;
   }

   private static List getImplNameFromServices(ClassLoader classLoader, String factoryName) {
      List result = null;
      String resourceName = "META-INF/services/" + factoryName;
      BufferedReader reader = null;

      try {
         Enumeration e = classLoader.getResources(resourceName);

         while(e.hasMoreElements()) {
            URL url = (URL)e.nextElement();
            URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            InputStream stream = conn.getInputStream();
            if (stream != null) {
               try {
                  reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                  if (result == null) {
                     result = new ArrayList(3);
                  }

                  result.add(reader.readLine());
               } catch (UnsupportedEncodingException var15) {
                  reader = new BufferedReader(new InputStreamReader(stream));
               } finally {
                  if (reader != null) {
                     reader.close();
                     reader = null;
                  }

                  if (stream != null) {
                     stream.close();
                     stream = null;
                  }

               }
            }
         }
      } catch (IOException var17) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, var17.toString(), var17);
         }
      } catch (SecurityException var18) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, var18.toString(), var18);
         }
      }

      return result;
   }

   private static Object getImplGivenPreviousImpl(ClassLoader classLoader, String factoryName, String implName, Object previousImpl) {
      Class factoryClass = null;
      Object[] newInstanceArgs = new Object[1];
      Object result = null;
      Class clazz;
      if (null != previousImpl && null != (factoryClass = getFactoryClass(factoryName))) {
         try {
            clazz = Class.forName(implName, false, classLoader);
            Class[] getCtorArg = new Class[]{factoryClass};
            Constructor ctor = clazz.getConstructor(getCtorArg);
            newInstanceArgs[0] = previousImpl;
            result = ctor.newInstance(newInstanceArgs);
         } catch (NoSuchMethodException var12) {
            factoryClass = null;
         } catch (Exception var13) {
            throw new FacesException(implName, var13);
         }
      }

      if (null == previousImpl || null == factoryClass) {
         try {
            clazz = Class.forName(implName, false, classLoader);
            result = clazz.newInstance();
         } catch (Exception var11) {
            throw new FacesException(implName, var11);
         }
      }

      return result;
   }

   private static Class getFactoryClass(String factoryClassName) {
      if (null == factoryClasses) {
         factoryClasses = new HashMap(FACTORY_NAMES.length);
         factoryClasses.put("javax.faces.application.ApplicationFactory", ApplicationFactory.class);
         factoryClasses.put("javax.faces.context.FacesContextFactory", FacesContextFactory.class);
         factoryClasses.put("javax.faces.lifecycle.LifecycleFactory", LifecycleFactory.class);
         factoryClasses.put("javax.faces.render.RenderKitFactory", RenderKitFactory.class);
      }

      return (Class)factoryClasses.get(factoryClassName);
   }

   private static void validateFactoryName(String factoryName) {
      if (factoryName == null) {
         throw new NullPointerException();
      } else if (Arrays.binarySearch(FACTORY_NAMES, factoryName) < 0) {
         throw new IllegalArgumentException(factoryName);
      }
   }

   private static final class FactoryManager {
      private final Map factories = new HashMap();
      private final ReentrantReadWriteLock lock;

      public FactoryManager() {
         String[] var1 = FactoryFinder.FACTORY_NAMES;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String name = var1[var3];
            this.factories.put(name, new ArrayList(4));
         }

         this.lock = new ReentrantReadWriteLock(true);
      }

      public void addFactory(String factoryName, String implementation) {
         Object result = this.factories.get(factoryName);
         this.lock.writeLock().lock();

         try {
            if (result instanceof List) {
               TypedCollections.dynamicallyCastList((List)result, String.class).add(0, implementation);
            }
         } finally {
            this.lock.writeLock().unlock();
         }

      }

      public Object getFactory(ClassLoader cl, String factoryName) {
         this.lock.readLock().lock();

         Object factoryOrList;
         Object factory;
         try {
            factoryOrList = this.factories.get(factoryName);
            if (!(factoryOrList instanceof List)) {
               factory = factoryOrList;
               return factory;
            }
         } finally {
            this.lock.readLock().unlock();
         }

         this.lock.writeLock().lock();

         Object var5;
         try {
            factoryOrList = this.factories.get(factoryName);
            if (!(factoryOrList instanceof List)) {
               factory = factoryOrList;
               return factory;
            }

            factory = FactoryFinder.getImplementationInstance(cl, factoryName, (List)factoryOrList);
            if (factory == null) {
               ResourceBundle rb = FactoryFinder.LOGGER.getResourceBundle();
               String message = rb.getString("severe.no_factory");
               message = MessageFormat.format(message, factoryName);
               throw new IllegalStateException(message);
            }

            this.factories.put(factoryName, factory);
            var5 = factory;
         } finally {
            this.lock.writeLock().unlock();
         }

         return var5;
      }
   }

   private static final class FactoryManagerCache {
      private ConcurrentMap applicationMap;

      private FactoryManagerCache() {
         this.applicationMap = new ConcurrentHashMap();
      }

      private FactoryManager getApplicationFactoryManager(ClassLoader cl) {
         while(true) {
            Future factories = (Future)this.applicationMap.get(cl);
            if (factories == null) {
               Callable callable = new Callable() {
                  public FactoryManager call() throws Exception {
                     return new FactoryManager();
                  }
               };
               FutureTask ft = new FutureTask(callable);
               factories = (Future)this.applicationMap.putIfAbsent(cl, ft);
               if (factories == null) {
                  factories = ft;
                  ft.run();
               }
            }

            try {
               return (FactoryManager)((Future)factories).get();
            } catch (CancellationException var5) {
               if (FactoryFinder.LOGGER.isLoggable(Level.FINEST)) {
                  FactoryFinder.LOGGER.log(Level.FINEST, var5.toString(), var5);
               }

               this.applicationMap.remove(cl);
            } catch (InterruptedException var6) {
               if (FactoryFinder.LOGGER.isLoggable(Level.FINEST)) {
                  FactoryFinder.LOGGER.log(Level.FINEST, var6.toString(), var6);
               }

               this.applicationMap.remove(cl);
            } catch (ExecutionException var7) {
               throw new FacesException(var7);
            }
         }
      }

      public void removeApplicationFactoryManager(ClassLoader cl) {
         this.applicationMap.remove(cl);
      }

      // $FF: synthetic method
      FactoryManagerCache(Object x0) {
         this();
      }
   }
}
