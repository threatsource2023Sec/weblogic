package com.sun.faces.config;

import com.sun.faces.config.initfacescontext.NoOpELContext;
import com.sun.faces.config.initfacescontext.NoOpFacesContext;
import com.sun.faces.config.initfacescontext.ServletContextAdapter;
import com.sun.faces.context.ApplicationMap;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class InitFacesContext extends NoOpFacesContext {
   private static Logger LOGGER;
   private static final String INIT_FACES_CONTEXT_ATTR_NAME = "com.sun.faces.InitFacesContext";
   private ServletContextAdapter servletContextAdapter;
   private UIViewRoot viewRoot;
   private Map attributes;
   private ELContext elContext = new NoOpELContext();

   public InitFacesContext(ServletContext servletContext) {
      this.servletContextAdapter = new ServletContextAdapter(servletContext);
      servletContext.setAttribute("com.sun.faces.InitFacesContext", this);
      cleanupInitMaps(servletContext);
      this.addServletContextEntryForInitContext(servletContext);
      this.addInitContextEntryForCurrentThread();
   }

   public Map getAttributes() {
      if (this.attributes == null) {
         this.attributes = new HashMap();
      }

      return this.attributes;
   }

   public ExternalContext getExternalContext() {
      return this.servletContextAdapter;
   }

   public UIViewRoot getViewRoot() {
      if (this.viewRoot == null) {
         this.viewRoot = new UIViewRoot();
         this.viewRoot.setLocale(Locale.getDefault());
         this.viewRoot.setViewId("com.sun.faces.xhtml");
      }

      return this.viewRoot;
   }

   public ELContext getELContext() {
      return this.elContext;
   }

   public void setELContext(ELContext elContext) {
      this.elContext = elContext;
   }

   public Application getApplication() {
      ApplicationFactory factory = (ApplicationFactory)FactoryFinder.getFactory("javax.faces.application.ApplicationFactory");
      return factory.getApplication();
   }

   public boolean isProjectStage(ProjectStage stage) {
      if (stage == null) {
         throw new NullPointerException();
      } else {
         return stage.equals(this.getApplication().getProjectStage());
      }
   }

   public void release() {
      setCurrentInstance((FacesContext)null);
      Map viewMap;
      if (this.servletContextAdapter != null) {
         viewMap = this.servletContextAdapter.getApplicationMap();
         if (viewMap instanceof ApplicationMap && ((ApplicationMap)viewMap).getContext() != null) {
            viewMap.remove("com.sun.faces.InitFacesContext");
         }

         this.servletContextAdapter.release();
      }

      if (this.attributes != null) {
         this.attributes.clear();
         this.attributes = null;
      }

      this.elContext = null;
      if (this.viewRoot != null) {
         viewMap = this.viewRoot.getViewMap(false);
         if (viewMap != null) {
            viewMap.clear();
         }

         this.viewRoot = null;
      }

   }

   public void releaseCurrentInstance() {
      this.removeInitContextEntryForCurrentThread();
      setCurrentInstance((FacesContext)null);
   }

   public void addInitContextEntryForCurrentThread() {
      getThreadInitContextMap().put(Thread.currentThread(), this);
   }

   public void removeInitContextEntryForCurrentThread() {
      getThreadInitContextMap().remove(Thread.currentThread());
   }

   public void addServletContextEntryForInitContext(ServletContext servletContext) {
      getInitContextServletContextMap().put(this, servletContext);
   }

   public void removeServletContextEntryForInitContext() {
      getInitContextServletContextMap().remove(this);
   }

   public static void cleanupInitMaps(ServletContext servletContext) {
      Map facesContext2ServletContext = getInitContextServletContextMap();
      Map thread2FacesContext = getThreadInitContextMap();
      Iterator var3 = (new ArrayList(facesContext2ServletContext.entrySet())).iterator();

      while(true) {
         Map.Entry facesContext2ServletContextEntry;
         do {
            if (!var3.hasNext()) {
               return;
            }

            facesContext2ServletContextEntry = (Map.Entry)var3.next();
         } while(facesContext2ServletContextEntry.getValue() != servletContext);

         facesContext2ServletContext.remove(facesContext2ServletContextEntry.getKey());
         Iterator var5 = (new ArrayList(thread2FacesContext.entrySet())).iterator();

         while(var5.hasNext()) {
            Map.Entry thread2FacesContextEntry = (Map.Entry)var5.next();
            if (thread2FacesContextEntry.getValue() == facesContext2ServletContextEntry.getKey()) {
               thread2FacesContext.remove(thread2FacesContextEntry.getKey());
            }
         }
      }
   }

   static Map getThreadInitContextMap() {
      try {
         Field threadMap = FacesContext.class.getDeclaredField("threadInitContext");
         threadMap.setAccessible(true);
         return (Map)threadMap.get((Object)null);
      } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var1) {
         LOGGER.log(Level.FINEST, "Unable to get (thread, init context) map", var1);
         return null;
      }
   }

   static Map getInitContextServletContextMap() {
      try {
         Field initContextMap = FacesContext.class.getDeclaredField("initContextServletContext");
         initContextMap.setAccessible(true);
         return (Map)initContextMap.get((Object)null);
      } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var1) {
         LOGGER.log(Level.FINEST, "Unable to get (init context, servlet context) map", var1);
         return null;
      }
   }

   public static InitFacesContext getInstance(ServletContext servletContext) {
      InitFacesContext result = (InitFacesContext)servletContext.getAttribute("com.sun.faces.InitFacesContext");
      if (result != null) {
         result.addInitContextEntryForCurrentThread();
      }

      return result;
   }

   public void reInitializeExternalContext(ServletContext sc) {
      assert Util.isUnitTestModeEnabled();

      this.servletContextAdapter = new ServletContextAdapter(sc);
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
