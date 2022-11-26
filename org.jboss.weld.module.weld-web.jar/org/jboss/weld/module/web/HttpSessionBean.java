package org.jboss.weld.module.web;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.util.Enumeration;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import org.jboss.weld.bean.builtin.AbstractStaticallyDecorableBuiltInBean;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.web.logging.ServletLogger;
import org.jboss.weld.module.web.servlet.SessionHolder;

public class HttpSessionBean extends AbstractStaticallyDecorableBuiltInBean {
   public HttpSessionBean(BeanManagerImpl manager) {
      super(manager, HttpSession.class);
   }

   protected HttpSession newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      return new SerializableProxy();
   }

   public Class getScope() {
      return SessionScoped.class;
   }

   private static class SerializableProxy implements HttpSession, Serializable {
      private static final long serialVersionUID = -617233973786462227L;
      @SuppressFBWarnings(
         value = {"SE_TRANSIENT_FIELD_NOT_RESTORED"},
         justification = "False positive from FindBugs - field is set lazily."
      )
      private transient volatile HttpSession session;

      private SerializableProxy() {
         this.session = this.obtainHttpSession();
      }

      public long getCreationTime() {
         return this.session().getCreationTime();
      }

      public String getId() {
         return this.session().getId();
      }

      public long getLastAccessedTime() {
         return this.session().getLastAccessedTime();
      }

      public ServletContext getServletContext() {
         return this.session().getServletContext();
      }

      public void setMaxInactiveInterval(int interval) {
         this.session().setMaxInactiveInterval(interval);
      }

      public int getMaxInactiveInterval() {
         return this.session().getMaxInactiveInterval();
      }

      public HttpSessionContext getSessionContext() {
         return this.session().getSessionContext();
      }

      public Object getAttribute(String name) {
         return this.session().getAttribute(name);
      }

      public Object getValue(String name) {
         return this.session().getValue(name);
      }

      public Enumeration getAttributeNames() {
         return this.session().getAttributeNames();
      }

      public String[] getValueNames() {
         return this.session().getValueNames();
      }

      public void setAttribute(String name, Object value) {
         this.session().setAttribute(name, value);
      }

      public void putValue(String name, Object value) {
         this.session().putValue(name, value);
      }

      public void removeAttribute(String name) {
         this.session().removeAttribute(name);
      }

      public void removeValue(String name) {
         this.session().removeValue(name);
      }

      public void invalidate() {
         this.session().invalidate();
      }

      public boolean isNew() {
         return this.session().isNew();
      }

      private HttpSession session() {
         if (this.session == null) {
            synchronized(this) {
               if (this.session == null) {
                  this.session = this.obtainHttpSession();
               }
            }
         }

         return this.session;
      }

      private HttpSession obtainHttpSession() {
         HttpSession session = SessionHolder.getSessionIfExists();
         if (session == null) {
            throw ServletLogger.LOG.cannotInjectObjectOutsideOfServletRequest(HttpSession.class.getSimpleName(), (Throwable)null);
         } else {
            return session;
         }
      }

      // $FF: synthetic method
      SerializableProxy(Object x0) {
         this();
      }
   }
}
