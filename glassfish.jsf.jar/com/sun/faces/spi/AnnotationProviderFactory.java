package com.sun.faces.spi;

import com.sun.faces.config.manager.spi.FilterClassesFromFacesInitializerAnnotationProvider;
import com.sun.faces.util.FacesLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.servlet.ServletContext;

public class AnnotationProviderFactory {
   private static final Logger LOGGER;
   private static final Class DEFAULT_ANNOTATION_PROVIDER;
   private static final String ANNOTATION_PROVIDER_SERVICE_KEY = "com.sun.faces.spi.annotationprovider";

   public static AnnotationProvider createAnnotationProvider(ServletContext sc) {
      AnnotationProvider annotationProvider = createDefaultProvider(sc);
      String[] services = ServiceFactoryUtils.getServiceEntries("com.sun.faces.spi.annotationprovider");
      if (services.length > 0) {
         Object provider = null;

         try {
            provider = ServiceFactoryUtils.getProviderFromEntry(services[0], new Class[]{ServletContext.class, AnnotationProvider.class}, new Object[]{sc, annotationProvider});
         } catch (FacesException var7) {
            if (!NoSuchMethodException.class.isInstance(var7.getCause()) && LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, var7.toString(), var7);
            }
         }

         if (provider == null) {
            try {
               provider = ServiceFactoryUtils.getProviderFromEntry(services[0], new Class[]{ServletContext.class}, new Object[]{sc});
            } catch (FacesException var6) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, var6.toString(), var6);
               }
            }
         }

         if (provider != null) {
            if (!(provider instanceof AnnotationProvider)) {
               throw new FacesException("Class " + provider.getClass().getName() + " is not an instance of com.sun.faces.spi.AnnotationProvider");
            }

            annotationProvider = (AnnotationProvider)provider;
         }
      } else {
         ServiceLoader serviceLoader = ServiceLoader.load(AnnotationProvider.class);
         Iterator iterator = serviceLoader.iterator();
         if (iterator.hasNext()) {
            AnnotationProvider defaultAnnotationProvider = annotationProvider;
            annotationProvider = (AnnotationProvider)iterator.next();
            annotationProvider.initialize(sc, defaultAnnotationProvider);
         }
      }

      return annotationProvider;
   }

   private static AnnotationProvider createDefaultProvider(ServletContext sc) {
      AnnotationProvider result = null;

      try {
         Constructor c = DEFAULT_ANNOTATION_PROVIDER.getDeclaredConstructor(ServletContext.class);
         result = (AnnotationProvider)c.newInstance(sc);
         return result;
      } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var4) {
         throw new FacesException(var4);
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      DEFAULT_ANNOTATION_PROVIDER = FilterClassesFromFacesInitializerAnnotationProvider.class;
   }
}
