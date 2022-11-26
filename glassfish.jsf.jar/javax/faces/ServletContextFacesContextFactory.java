package javax.faces;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.ServletContext;

final class ServletContextFacesContextFactory extends FacesContextFactory {
   static final String SERVLET_CONTEXT_FINDER_NAME = "com.sun.faces.ServletContextFacesContextFactory";
   static final String SERVLET_CONTEXT_FINDER_REMOVAL_NAME = "com.sun.faces.ServletContextFacesContextFactory_Removal";
   private static final Logger LOGGER = Logger.getLogger("javax.faces", "javax.faces.LogStrings");
   private ThreadLocal facesContextCurrentInstance;
   private ConcurrentHashMap facesContextThreadInitContextMap;
   private ConcurrentHashMap initContextServletContextMap;

   ServletContextFacesContextFactory() {
      try {
         Field instanceField = FacesContext.class.getDeclaredField("instance");
         instanceField.setAccessible(true);
         this.facesContextCurrentInstance = (ThreadLocal)instanceField.get((Object)null);
         Field threadInitContextMapField = FacesContext.class.getDeclaredField("threadInitContext");
         threadInitContextMapField.setAccessible(true);
         this.facesContextThreadInitContextMap = (ConcurrentHashMap)threadInitContextMapField.get((Object)null);
         Field initContextServletContextMapField = FacesContext.class.getDeclaredField("initContextServletContext");
         initContextServletContextMapField.setAccessible(true);
         this.initContextServletContextMap = (ConcurrentHashMap)initContextServletContextMapField.get((Object)null);
      } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException var4) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "Unable to access instance field of FacesContext", var4);
         }
      }

   }

   FacesContext getFacesContextWithoutServletContextLookup() {
      FacesContext result = (FacesContext)this.facesContextCurrentInstance.get();
      if (result == null && this.facesContextThreadInitContextMap != null) {
         result = (FacesContext)this.facesContextThreadInitContextMap.get(Thread.currentThread());
      }

      return result;
   }

   public FacesContext getFacesContext(Object context, Object request, Object response, Lifecycle lifecycle) throws FacesException {
      FacesContext result = null;
      if (this.initContextServletContextMap != null && !this.initContextServletContextMap.isEmpty()) {
         ServletContext servletContext = (ServletContext)FactoryFinder.FACTORIES_CACHE.getServletContextForCurrentClassLoader();
         if (servletContext != null) {
            Iterator var7 = this.initContextServletContextMap.entrySet().iterator();

            while(var7.hasNext()) {
               Map.Entry entry = (Map.Entry)var7.next();
               if (servletContext.equals(entry.getValue())) {
                  result = (FacesContext)entry.getKey();
                  break;
               }
            }
         }
      }

      return result;
   }
}
