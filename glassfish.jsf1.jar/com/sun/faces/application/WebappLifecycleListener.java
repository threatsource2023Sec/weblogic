package com.sun.faces.application;

import com.sun.faces.el.ELUtils;
import com.sun.faces.io.FastStringWriter;
import com.sun.faces.mgbean.BeanManager;
import com.sun.faces.scripting.GroovyHelper;
import com.sun.faces.util.FacesLogger;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;

public class WebappLifecycleListener {
   private static final Logger LOGGER;
   private ServletContext servletContext;
   private ApplicationAssociate applicationAssociate;
   private List activeSessions;

   public WebappLifecycleListener() {
   }

   public WebappLifecycleListener(ServletContext servletContext) {
      this.servletContext = servletContext;
   }

   public void requestDestroyed(ServletRequestEvent event) {
      ServletRequest request = event.getServletRequest();
      Enumeration e = request.getAttributeNames();

      while(e.hasMoreElements()) {
         String beanName = (String)e.nextElement();
         this.handleAttributeEvent(beanName, request.getAttribute(beanName), ELUtils.Scope.REQUEST);
      }

      this.syncSessionScopedBeans(request);
      ApplicationAssociate.setCurrentInstance((ApplicationAssociate)null);
   }

   public void requestInitialized(ServletRequestEvent event) {
      ApplicationAssociate associate = this.getAssociate();
      if (associate != null) {
         ApplicationAssociate.setCurrentInstance(associate);
         if (associate.isDevModeEnabled()) {
            GroovyHelper helper = associate.getGroovyHelper();
            if (helper != null) {
               helper.setClassLoader();
            }
         }
      }

   }

   public void sessionDestroyed(HttpSessionEvent event) {
      HttpSession session = event.getSession();
      Enumeration e = session.getAttributeNames();

      while(e.hasMoreElements()) {
         String beanName = (String)e.nextElement();
         this.handleAttributeEvent(beanName, session.getAttribute(beanName), ELUtils.Scope.SESSION);
      }

   }

   public void sessionCreated(HttpSessionEvent event) {
      ApplicationAssociate associate = this.getAssociate();
      if (associate != null && associate.isDevModeEnabled()) {
         if (this.activeSessions == null) {
            this.activeSessions = new ArrayList();
         }

         this.activeSessions.add(event.getSession());
      }

   }

   public void attributeRemoved(ServletRequestAttributeEvent event) {
      this.handleAttributeEvent(event.getName(), event.getValue(), ELUtils.Scope.REQUEST);
   }

   public void attributeReplaced(ServletRequestAttributeEvent event) {
      String attrName = event.getName();
      Object newValue = event.getServletRequest().getAttribute(attrName);
      if (event.getValue() != newValue) {
         this.handleAttributeEvent(attrName, event.getValue(), ELUtils.Scope.REQUEST);
      }

   }

   public void attributeRemoved(HttpSessionBindingEvent event) {
      this.handleAttributeEvent(event.getName(), event.getValue(), ELUtils.Scope.SESSION);
   }

   public void attributeReplaced(HttpSessionBindingEvent event) {
      HttpSession session = event.getSession();
      String attrName = event.getName();
      Object newValue = session.getAttribute(attrName);
      if (event.getValue() != newValue) {
         this.handleAttributeEvent(attrName, event.getValue(), ELUtils.Scope.SESSION);
      }

   }

   public void attributeRemoved(ServletContextAttributeEvent event) {
      this.handleAttributeEvent(event.getName(), event.getValue(), ELUtils.Scope.APPLICATION);
   }

   public void attributeReplaced(ServletContextAttributeEvent event) {
      ServletContext context = event.getServletContext();
      String attrName = event.getName();
      Object newValue = context.getAttribute(attrName);
      if (event.getValue() != newValue) {
         this.handleAttributeEvent(attrName, event.getValue(), ELUtils.Scope.APPLICATION);
      }

   }

   public List getActiveSessions() {
      return new ArrayList(this.activeSessions);
   }

   private void handleAttributeEvent(String beanName, Object bean, ELUtils.Scope scope) {
      ApplicationAssociate associate = this.getAssociate();

      try {
         if (associate != null) {
            BeanManager beanManager = associate.getBeanManager();
            if (beanManager != null && beanManager.isManaged(beanName)) {
               beanManager.destroy(beanName, bean);
            }
         }
      } catch (Exception var9) {
         String className = var9.getClass().getName();
         String message = var9.getMessage();
         if (message == null) {
            message = "";
         }

         if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "jsf.config.listener.predestroy.error", new Object[]{className, beanName, scope, message});
         }

         if (LOGGER.isLoggable(Level.FINE)) {
            FastStringWriter writer = new FastStringWriter(128);
            var9.printStackTrace(new PrintWriter(writer));
            LOGGER.fine(writer.toString());
         }
      }

   }

   public void contextInitialized(ServletContextEvent event) {
      if (this.servletContext == null) {
         this.servletContext = event.getServletContext();
      }

   }

   public void contextDestroyed(ServletContextEvent event) {
      Enumeration e = this.servletContext.getAttributeNames();

      while(e.hasMoreElements()) {
         String beanName = (String)e.nextElement();
         this.handleAttributeEvent(beanName, this.servletContext.getAttribute(beanName), ELUtils.Scope.APPLICATION);
      }

      this.applicationAssociate = null;
   }

   private ApplicationAssociate getAssociate() {
      if (this.applicationAssociate == null) {
         this.applicationAssociate = ApplicationAssociate.getInstance(this.servletContext);
      }

      return this.applicationAssociate;
   }

   private void syncSessionScopedBeans(ServletRequest request) {
      if (request instanceof HttpServletRequest) {
         HttpSession session = ((HttpServletRequest)request).getSession(false);
         if (session != null) {
            ApplicationAssociate associate = this.getAssociate();
            if (associate == null) {
               return;
            }

            BeanManager manager = associate.getBeanManager();
            if (manager != null) {
               Enumeration e = session.getAttributeNames();

               while(e.hasMoreElements()) {
                  String name = (String)e.nextElement();
                  if (manager.isManaged(name)) {
                     session.setAttribute(name, session.getAttribute(name));
                  }
               }
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
