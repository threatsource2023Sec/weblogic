package javax.faces;

import com.sun.faces.config.ConfigManager;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.util.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ApplicationFactory;
import javax.faces.component.search.SearchExpressionContextFactory;
import javax.faces.component.visit.VisitContextFactory;
import javax.faces.context.ExceptionHandlerFactory;
import javax.faces.context.ExternalContextFactory;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.context.FlashFactory;
import javax.faces.context.PartialViewContextFactory;
import javax.faces.flow.FlowHandlerFactory;
import javax.faces.lifecycle.ClientWindowFactory;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKitFactory;
import javax.faces.view.ViewDeclarationLanguageFactory;
import javax.faces.view.facelets.FaceletCacheFactory;
import javax.faces.view.facelets.TagHandlerDelegateFactory;

final class FactoryFinderInstance {
   private static final Logger LOGGER = Logger.getLogger("javax.faces", "javax.faces.LogStrings");
   private static final String INJECTION_PROVIDER_KEY = FactoryFinder.class.getPackage().getName() + "INJECTION_PROVIDER_KEY";
   private final Map factories = new ConcurrentHashMap();
   private final Map savedFactoryNames = new ConcurrentHashMap();
   private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
   private final String createdBy;
   private ServletContextFacesContextFactory servletContextFinder = new ServletContextFacesContextFactory();
   private final List factoryNames = this.asSortedList("javax.faces.application.ApplicationFactory", "javax.faces.component.visit.VisitContextFactory", "javax.faces.context.ExceptionHandlerFactory", "javax.faces.context.ExternalContextFactory", "javax.faces.context.FacesContextFactory", "javax.faces.context.FlashFactory", "javax.faces.flow.FlowHandlerFactory", "javax.faces.context.PartialViewContextFactory", "javax.faces.lifecycle.ClientWindowFactory", "javax.faces.lifecycle.LifecycleFactory", "javax.faces.render.RenderKitFactory", "javax.faces.view.ViewDeclarationLanguageFactory", "javax.faces.view.facelets.FaceletCacheFactory", "javax.faces.view.facelets.TagHandlerDelegateFactory", "javax.faces.component.search.SearchExpressionContextFactory");
   private final Map factoryClasses = this.buildFactoryClassesMap();

   FactoryFinderInstance(FacesContext facesContext) {
      Iterator var2 = this.factoryNames.iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         this.factories.put(name, new ArrayList(4));
      }

      this.copyInjectionProviderFromFacesContext(facesContext);
      this.createdBy = Util.generateCreatedBy(facesContext);
   }

   FactoryFinderInstance(FacesContext facesContext, FactoryFinderInstance toCopy) {
      this.factories.putAll(toCopy.savedFactoryNames);
      this.copyInjectionProviderFromFacesContext(facesContext);
      this.createdBy = Util.generateCreatedBy(facesContext);
   }

   public String toString() {
      return super.toString() + " created by" + this.createdBy;
   }

   void addFactory(String factoryName, String implementationClassName) {
      this.validateFactoryName(factoryName);
      Object result = this.factories.get(factoryName);
      this.lock.writeLock().lock();

      try {
         if (result instanceof List) {
            TypedCollections.dynamicallyCastList((List)result, String.class).add(0, implementationClassName);
         }
      } finally {
         this.lock.writeLock().unlock();
      }

   }

   Object getFactory(String factoryName) {
      this.validateFactoryName(factoryName);
      if (factoryName.equals("com.sun.faces.ServletContextFacesContextFactory")) {
         return this.servletContextFinder;
      } else {
         Object factoryOrList;
         if (factoryName.equals("com.sun.faces.ServletContextFacesContextFactory_Removal")) {
            try {
               this.lock.writeLock().lock();
               this.servletContextFinder = null;
               factoryOrList = null;
            } finally {
               this.lock.writeLock().unlock();
            }

            return factoryOrList;
         } else {
            this.lock.readLock().lock();

            Object factory;
            label210: {
               try {
                  factoryOrList = this.factories.get(factoryName);
                  if (factoryOrList instanceof List) {
                     break label210;
                  }

                  factory = factoryOrList;
               } finally {
                  this.lock.readLock().unlock();
               }

               return factory;
            }

            this.lock.writeLock().lock();

            Object var4;
            try {
               factoryOrList = this.factories.get(factoryName);
               if (!(factoryOrList instanceof List)) {
                  factory = factoryOrList;
                  return factory;
               }

               this.savedFactoryNames.put(factoryName, new ArrayList((List)factoryOrList));
               factory = this.getImplementationInstance(Util.getContextClassLoader2(), factoryName, (List)factoryOrList);
               if (factory == null) {
                  this.logNoFactory(factoryName);
                  factory = FactoryFinder.FACTORIES_CACHE.getFallbackFactory(this, factoryName);
                  this.notNullFactory(factoryName, factory);
               }

               this.factories.put(factoryName, factory);
               var4 = factory;
            } finally {
               this.lock.writeLock().unlock();
            }

            return var4;
         }
      }
   }

   InjectionProvider getInjectionProvider() {
      return (InjectionProvider)this.factories.get(INJECTION_PROVIDER_KEY);
   }

   void clearInjectionProvider() {
      this.factories.remove(INJECTION_PROVIDER_KEY);
   }

   void releaseFactories() {
      InjectionProvider provider = this.getInjectionProvider();
      if (provider != null) {
         this.lock.writeLock().lock();

         try {
            Iterator var2 = this.factories.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               Object curFactory = entry.getValue();
               if (!INJECTION_PROVIDER_KEY.equals(entry.getKey()) && curFactory != null && !(curFactory instanceof String)) {
                  try {
                     provider.invokePreDestroy(curFactory);
                  } catch (Exception var9) {
                     this.logPreDestroyFail(entry.getValue(), var9);
                  }
               }
            }
         } finally {
            this.factories.clear();
            this.lock.writeLock().unlock();
         }
      } else {
         LOGGER.log(Level.SEVERE, "Unable to call @PreDestroy annotated methods because no InjectionProvider can be found. Does this container implement the Mojarra Injection SPI?");
      }

   }

   Collection getFactories() {
      return this.factories.values();
   }

   private void copyInjectionProviderFromFacesContext(FacesContext facesContext) {
      InjectionProvider injectionProvider = null;
      if (facesContext != null) {
         injectionProvider = (InjectionProvider)facesContext.getAttributes().get(ConfigManager.INJECTION_PROVIDER_KEY);
      }

      if (injectionProvider != null) {
         this.factories.put(INJECTION_PROVIDER_KEY, injectionProvider);
      } else {
         LOGGER.log(Level.SEVERE, "Unable to obtain InjectionProvider from init time FacesContext. Does this container implement the Mojarra Injection SPI?");
      }

   }

   private Object getImplementationInstance(ClassLoader classLoader, String factoryName, List implementations) throws FacesException {
      Object implementation = null;
      String curImplClass;
      int len;
      if (implementations != null && (1 < (len = implementations.size()) || 1 == len)) {
         curImplClass = (String)implementations.remove(len - 1);
         implementation = this.getImplGivenPreviousImpl(classLoader, factoryName, curImplClass, (Object)null);
      }

      List fromServices = this.getImplNameFromServices(classLoader, factoryName);
      String name;
      if (fromServices != null) {
         for(Iterator var8 = fromServices.iterator(); var8.hasNext(); implementation = this.getImplGivenPreviousImpl(classLoader, factoryName, name, implementation)) {
            name = (String)var8.next();
         }
      }

      if (implementations != null) {
         for(len = implementations.size() - 1; 0 <= len; --len) {
            curImplClass = (String)implementations.remove(len);
            implementation = this.getImplGivenPreviousImpl(classLoader, factoryName, curImplClass, implementation);
         }
      }

      return implementation;
   }

   private List getImplNameFromServices(ClassLoader classLoader, String factoryName) {
      List implementationNames = new ArrayList();
      String resourceName = "META-INF/services/" + factoryName;

      try {
         Enumeration resources = classLoader.getResources(resourceName);

         while(resources.hasMoreElements()) {
            URL url = (URL)resources.nextElement();
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            InputStream stream = connection.getInputStream();
            Throwable var9 = null;

            try {
               if (stream != null) {
                  implementationNames.add(this.readLineFromStream(stream));
               }
            } catch (Throwable var19) {
               var9 = var19;
               throw var19;
            } finally {
               if (stream != null) {
                  if (var9 != null) {
                     try {
                        stream.close();
                     } catch (Throwable var18) {
                        var9.addSuppressed(var18);
                     }
                  } else {
                     stream.close();
                  }
               }

            }
         }
      } catch (SecurityException | IOException var21) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, var21.toString(), var21);
         }
      }

      return implementationNames;
   }

   private Object getImplGivenPreviousImpl(ClassLoader classLoader, String factoryName, String factoryImplClassName, Object previousFactoryImplementation) {
      Object factoryImplementation = null;
      Class factoryClass = this.getFactoryClass(factoryName);
      if (!Util.isAnyNull(previousFactoryImplementation, factoryClass)) {
         try {
            factoryImplementation = Class.forName(factoryImplClassName, false, classLoader).getConstructor(factoryClass).newInstance(previousFactoryImplementation);
         } catch (NoSuchMethodException var9) {
            factoryClass = null;
         } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException var10) {
            throw new FacesException(factoryImplClassName, var10);
         }
      }

      if (Util.isAnyNull(previousFactoryImplementation, factoryClass)) {
         try {
            factoryImplementation = Class.forName(factoryImplClassName, false, classLoader).newInstance();
         } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var8) {
            throw new FacesException(factoryImplClassName, var8);
         }
      }

      this.injectImplementation(factoryImplClassName, factoryImplementation);
      return factoryImplementation;
   }

   private Class getFactoryClass(String factoryClassName) {
      return (Class)this.factoryClasses.get(factoryClassName);
   }

   private String readLineFromStream(InputStream stream) throws IOException {
      try {
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
         Throwable var34 = null;

         String var35;
         try {
            var35 = reader.readLine();
         } catch (Throwable var30) {
            var34 = var30;
            throw var30;
         } finally {
            if (reader != null) {
               if (var34 != null) {
                  try {
                     reader.close();
                  } catch (Throwable var29) {
                     var34.addSuppressed(var29);
                  }
               } else {
                  reader.close();
               }
            }

         }

         return var35;
      } catch (UnsupportedEncodingException var33) {
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
         Throwable var4 = null;

         String var5;
         try {
            var5 = reader.readLine();
         } catch (Throwable var28) {
            var4 = var28;
            throw var28;
         } finally {
            if (reader != null) {
               if (var4 != null) {
                  try {
                     reader.close();
                  } catch (Throwable var27) {
                     var4.addSuppressed(var27);
                  }
               } else {
                  reader.close();
               }
            }

         }

         return var5;
      }
   }

   private void injectImplementation(String implementationName, Object implementation) {
      if (implementation != null) {
         InjectionProvider provider = this.getInjectionProvider();
         if (provider != null) {
            try {
               provider.inject(implementation);
               provider.invokePostConstruct(implementation);
            } catch (Exception var5) {
               throw new FacesException(implementationName, var5);
            }
         } else {
            LOGGER.log(Level.SEVERE, "Unable to inject {0} because no InjectionProvider can be found. Does this container implement the Mojarra Injection SPI?", implementation);
         }
      }

   }

   private void logNoFactory(String factoryName) {
      if (LOGGER.isLoggable(Level.SEVERE)) {
         LOGGER.log(Level.SEVERE, MessageFormat.format(LOGGER.getResourceBundle().getString("severe.no_factory"), factoryName));
      }

   }

   private void logPreDestroyFail(Object factory, Exception ex) {
      if (LOGGER.isLoggable(Level.SEVERE)) {
         LOGGER.log(Level.SEVERE, MessageFormat.format("Unable to invoke @PreDestroy annotated methods on {0}.", factory), ex);
      }

   }

   private void notNullFactory(String factoryName, Object factory) {
      if (factory == null) {
         throw new IllegalStateException(MessageFormat.format(LOGGER.getResourceBundle().getString("severe.no_factory_backup_failed"), factoryName));
      }
   }

   private void validateFactoryName(String factoryName) {
      if (factoryName == null) {
         throw new NullPointerException();
      } else if (!Util.isOneOf(factoryName, "com.sun.faces.ServletContextFacesContextFactory", "com.sun.faces.ServletContextFacesContextFactory_Removal")) {
         if (Collections.binarySearch(this.factoryNames, factoryName) < 0) {
            throw new IllegalArgumentException(factoryName);
         }
      }
   }

   private Map buildFactoryClassesMap() {
      Map buildUpFactoryClasses = new HashMap();
      buildUpFactoryClasses.put("javax.faces.application.ApplicationFactory", ApplicationFactory.class);
      buildUpFactoryClasses.put("javax.faces.component.visit.VisitContextFactory", VisitContextFactory.class);
      buildUpFactoryClasses.put("javax.faces.context.ExceptionHandlerFactory", ExceptionHandlerFactory.class);
      buildUpFactoryClasses.put("javax.faces.context.ExternalContextFactory", ExternalContextFactory.class);
      buildUpFactoryClasses.put("javax.faces.context.FacesContextFactory", FacesContextFactory.class);
      buildUpFactoryClasses.put("javax.faces.context.FlashFactory", FlashFactory.class);
      buildUpFactoryClasses.put("javax.faces.context.PartialViewContextFactory", PartialViewContextFactory.class);
      buildUpFactoryClasses.put("javax.faces.lifecycle.LifecycleFactory", LifecycleFactory.class);
      buildUpFactoryClasses.put("javax.faces.lifecycle.ClientWindowFactory", ClientWindowFactory.class);
      buildUpFactoryClasses.put("javax.faces.render.RenderKitFactory", RenderKitFactory.class);
      buildUpFactoryClasses.put("javax.faces.view.ViewDeclarationLanguageFactory", ViewDeclarationLanguageFactory.class);
      buildUpFactoryClasses.put("javax.faces.view.facelets.FaceletCacheFactory", FaceletCacheFactory.class);
      buildUpFactoryClasses.put("javax.faces.view.facelets.TagHandlerDelegateFactory", TagHandlerDelegateFactory.class);
      buildUpFactoryClasses.put("javax.faces.flow.FlowHandlerFactory", FlowHandlerFactory.class);
      buildUpFactoryClasses.put("javax.faces.component.search.SearchExpressionContextFactory", SearchExpressionContextFactory.class);
      return Collections.unmodifiableMap(buildUpFactoryClasses);
   }

   private List asSortedList(String... names) {
      List list = Arrays.asList(names);
      Collections.sort(list);
      return list;
   }
}
