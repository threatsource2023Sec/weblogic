package com.sun.faces.config;

import com.sun.faces.cdi.CdiExtension;
import com.sun.faces.util.Util;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.bean.ManagedBean;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ListenerFor;
import javax.faces.event.ListenersFor;
import javax.faces.event.NamedEvent;
import javax.faces.event.PhaseListener;
import javax.faces.render.FacesBehaviorRenderer;
import javax.faces.render.Renderer;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.view.facelets.FaceletsResourceResolver;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;

@HandlesTypes({Converter.class, Endpoint.class, FaceletsResourceResolver.class, FacesBehavior.class, FacesBehaviorRenderer.class, FacesComponent.class, FacesConverter.class, FacesConfig.class, FacesValidator.class, ListenerFor.class, ListenersFor.class, ManagedBean.class, NamedEvent.class, PhaseListener.class, Renderer.class, Resource.class, ResourceDependencies.class, ResourceDependency.class, ServerApplicationConfig.class, ServerEndpoint.class, UIComponent.class, Validator.class})
public class FacesInitializer implements ServletContainerInitializer {
   private static final String FACES_SERVLET_CLASS = FacesServlet.class.getName();

   public void onStartup(Set classes, ServletContext servletContext) throws ServletException {
      Set annotatedClasses = new HashSet();
      if (classes != null) {
         annotatedClasses.addAll(classes);
      }

      servletContext.setAttribute("com.sun.faces.AnnotatedClasses", annotatedClasses);
      boolean appHasSomeJsfContent = this.appMayHaveSomeJsfContent(classes, servletContext);
      boolean appHasFacesServlet = this.getExistingFacesServletRegistration(servletContext) != null;
      if (appHasSomeJsfContent || appHasFacesServlet) {
         InitFacesContext initFacesContext = new InitFacesContext(servletContext);

         try {
            if (appHasSomeJsfContent) {
               this.handleMappingConcerns(servletContext);
            }

            this.handleWebSocketConcerns(servletContext);
         } finally {
            initFacesContext.releaseCurrentInstance();
            initFacesContext.release();
         }
      }

   }

   private boolean appMayHaveSomeJsfContent(Set classes, ServletContext context) {
      if (!Util.isEmpty((Collection)classes)) {
         return true;
      } else {
         try {
            if (context.getResource("/WEB-INF/faces-config.xml") != null) {
               return true;
            }
         } catch (MalformedURLException var7) {
         }

         try {
            CDI cdi = null;

            try {
               cdi = CDI.current();
               if (cdi != null) {
                  Instance extension = cdi.select(CdiExtension.class, new Annotation[0]);
                  if (!extension.isAmbiguous() && !extension.isUnsatisfied()) {
                     return ((CdiExtension)extension.get()).isAddBeansForJSFImplicitObjects();
                  }
               }
            } catch (IllegalStateException var5) {
            }
         } catch (Exception var6) {
         }

         return false;
      }
   }

   private void handleMappingConcerns(ServletContext servletContext) throws ServletException {
      ServletRegistration existingFacesServletRegistration = this.getExistingFacesServletRegistration(servletContext);
      if (existingFacesServletRegistration != null) {
         if (this.isADFApplication()) {
            existingFacesServletRegistration.addMapping(new String[]{"*.xhtml", "*.jsf"});
         }

      } else {
         ServletRegistration reg = servletContext.addServlet("FacesServlet", "javax.faces.webapp.FacesServlet");
         if ("true".equalsIgnoreCase(servletContext.getInitParameter("javax.faces.DISABLE_FACESSERVLET_TO_XHTML"))) {
            reg.addMapping(new String[]{"/faces/*", "*.jsf", "*.faces"});
         } else {
            reg.addMapping(new String[]{"/faces/*", "*.jsf", "*.faces", "*.xhtml"});
         }

         servletContext.setAttribute("com.sun.faces.facesInitializerMappingsAdded", Boolean.TRUE);
         servletContext.addListener(ConfigureListener.class);
      }
   }

   private ServletRegistration getExistingFacesServletRegistration(ServletContext servletContext) {
      Map existing = servletContext.getServletRegistrations();
      Iterator var3 = existing.values().iterator();

      ServletRegistration registration;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         registration = (ServletRegistration)var3.next();
      } while(!FACES_SERVLET_CLASS.equals(registration.getClassName()));

      return registration;
   }

   private void handleWebSocketConcerns(ServletContext ctx) throws ServletException {
      if (ctx.getAttribute(ServerContainer.class.getName()) == null) {
         if (Boolean.valueOf(ctx.getInitParameter("javax.faces.ENABLE_WEBSOCKET_ENDPOINT"))) {
            ClassLoader cl = ctx.getClassLoader();

            Class tyrusInitializerClass;
            try {
               tyrusInitializerClass = cl.loadClass("org.glassfish.tyrus.servlet.TyrusServletContainerInitializer");
            } catch (ClassNotFoundException var8) {
               return;
            }

            try {
               ServletContainerInitializer tyrusInitializer = (ServletContainerInitializer)tyrusInitializerClass.newInstance();
               Class configClass = cl.loadClass("org.glassfish.tyrus.server.TyrusServerConfiguration");
               HashSet filteredClasses = new HashSet();
               filteredClasses.add(configClass);
               tyrusInitializer.onStartup(filteredClasses, ctx);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var7) {
               throw new ServletException(var7);
            }
         }
      }
   }

   private boolean isADFApplication() {
      boolean hasResource = false;

      try {
         ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
         if (contextClassLoader != null) {
            hasResource = contextClassLoader.getResource("oracle/adf/view/rich/context/AdfFacesContext.class") != null;
         }
      } catch (Exception var3) {
      }

      return hasResource;
   }
}
