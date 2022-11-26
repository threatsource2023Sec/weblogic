package org.glassfish.grizzly.servlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.Session;
import org.glassfish.grizzly.localization.LogMessages;

public class HttpSessionImpl implements HttpSession {
   private static final Logger LOGGER = Grizzly.logger(HttpSessionImpl.class);
   private final Session session;
   private final WebappContext contextImpl;

   public HttpSessionImpl(WebappContext contextImpl, Session session) {
      this.contextImpl = contextImpl;
      this.session = session;
   }

   public long getCreationTime() {
      if (!this.session.isValid()) {
         throw new IllegalStateException("The session was invalidated");
      } else {
         return this.session.getCreationTime();
      }
   }

   public String getId() {
      return this.session.getIdInternal();
   }

   protected boolean isValid() {
      return this.session.isValid();
   }

   public long getLastAccessedTime() {
      if (!this.session.isValid()) {
         throw new IllegalStateException("The session was invalidated");
      } else {
         return this.session.getTimestamp();
      }
   }

   protected void access() {
      this.session.access();
   }

   public ServletContext getServletContext() {
      return this.contextImpl;
   }

   public void setMaxInactiveInterval(int sessionTimeout) {
      if (sessionTimeout < 0) {
         sessionTimeout = -1;
      } else {
         sessionTimeout = (int)TimeUnit.MILLISECONDS.convert((long)sessionTimeout, TimeUnit.SECONDS);
      }

      this.session.setSessionTimeout((long)sessionTimeout);
   }

   public int getMaxInactiveInterval() {
      long sessionTimeout = this.session.getSessionTimeout();
      if (sessionTimeout < 0L) {
         return -1;
      } else {
         sessionTimeout = TimeUnit.SECONDS.convert(sessionTimeout, TimeUnit.MILLISECONDS);
         if (sessionTimeout > 2147483647L) {
            throw new IllegalArgumentException(sessionTimeout + " cannot be cast to int.");
         } else {
            return (int)sessionTimeout;
         }
      }
   }

   public HttpSessionContext getSessionContext() {
      return null;
   }

   public Object getAttribute(String key) {
      return this.session.getAttribute(key);
   }

   public Object getValue(String value) {
      return this.session.getAttribute(value);
   }

   public Enumeration getAttributeNames() {
      return Collections.enumeration(this.session.attributes().keySet());
   }

   public String[] getValueNames() {
      return (String[])this.session.attributes().entrySet().toArray(new String[this.session.attributes().size()]);
   }

   public void setAttribute(String key, Object value) {
      if (value == null) {
         this.removeAttribute(key);
      } else {
         Object unbound = this.session.getAttribute(key);
         this.session.setAttribute(key, value);
         if (unbound != null && unbound != value && unbound instanceof HttpSessionBindingListener) {
            try {
               ((HttpSessionBindingListener)unbound).valueUnbound(new HttpSessionBindingEvent(this, key));
            } catch (Throwable var12) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_SESSION_LISTENER_UNBOUND_ERROR(unbound.getClass().getName()));
               }
            }
         }

         HttpSessionBindingEvent event = null;
         if (value instanceof HttpSessionBindingListener && value != unbound) {
            event = new HttpSessionBindingEvent(this, key, value);

            try {
               ((HttpSessionBindingListener)value).valueBound(event);
            } catch (Throwable var11) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_SESSION_LISTENER_BOUND_ERROR(value.getClass().getName()));
               }
            }
         }

         EventListener[] listeners = this.contextImpl.getEventListeners();
         if (listeners.length != 0) {
            int i = 0;

            for(int len = listeners.length; i < len; ++i) {
               if (listeners[i] instanceof HttpSessionAttributeListener) {
                  HttpSessionAttributeListener listener = (HttpSessionAttributeListener)listeners[i];

                  try {
                     if (unbound != null) {
                        if (event == null) {
                           event = new HttpSessionBindingEvent(this, key, unbound);
                        }

                        listener.attributeReplaced(event);
                     } else {
                        if (event == null) {
                           event = new HttpSessionBindingEvent(this, key, value);
                        }

                        listener.attributeAdded(event);
                     }
                  } catch (Throwable var10) {
                     if (LOGGER.isLoggable(Level.WARNING)) {
                        LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_ADD_ERROR("HttpSessionAttributeListener", listener.getClass().getName()), var10);
                     }
                  }
               }
            }

         }
      }
   }

   public void putValue(String key, Object value) {
      this.setAttribute(key, value);
   }

   public void removeAttribute(String key) {
      Object value = this.session.removeAttribute(key);
      if (value != null) {
         HttpSessionBindingEvent event = null;
         if (value instanceof HttpSessionBindingListener) {
            event = new HttpSessionBindingEvent(this, key, value);
            ((HttpSessionBindingListener)value).valueUnbound(event);
         }

         EventListener[] listeners = this.contextImpl.getEventListeners();
         if (listeners.length != 0) {
            int i = 0;

            for(int len = listeners.length; i < len; ++i) {
               if (listeners[i] instanceof HttpSessionAttributeListener) {
                  HttpSessionAttributeListener listener = (HttpSessionAttributeListener)listeners[i];

                  try {
                     if (event == null) {
                        event = new HttpSessionBindingEvent(this, key, value);
                     }

                     listener.attributeRemoved(event);
                  } catch (Throwable var9) {
                     if (LOGGER.isLoggable(Level.WARNING)) {
                        LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_REMOVE_ERROR("HttpSessionAttributeListener", listener.getClass().getName()), var9);
                     }
                  }
               }
            }

         }
      }
   }

   public void removeValue(String key) {
      this.removeAttribute(key);
   }

   public synchronized void invalidate() {
      this.session.setValid(false);
      this.session.attributes().clear();
      EventListener[] listeners = this.contextImpl.getEventListeners();
      if (listeners.length > 0) {
         HttpSessionEvent event = new HttpSessionEvent(this);
         int i = 0;

         for(int len = listeners.length; i < len; ++i) {
            Object listenerObj = listeners[i];
            if (listenerObj instanceof HttpSessionListener) {
               HttpSessionListener listener = (HttpSessionListener)listenerObj;

               try {
                  listener.sessionDestroyed(event);
               } catch (Throwable var8) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_DESTROYED_ERROR("sessionDestroyed", "HttpSessionListener", listener.getClass().getName()), var8);
                  }
               }
            }
         }
      }

   }

   public boolean isNew() {
      if (!this.session.isValid()) {
         throw new IllegalStateException("The session was invalidated");
      } else {
         return this.session.isNew();
      }
   }

   protected void notifyNew() {
      EventListener[] listeners = this.contextImpl.getEventListeners();
      if (listeners.length > 0) {
         HttpSessionEvent event = new HttpSessionEvent(this);
         int i = 0;

         for(int len = listeners.length; i < len; ++i) {
            Object listenerObj = listeners[i];
            if (listenerObj instanceof HttpSessionListener) {
               HttpSessionListener listener = (HttpSessionListener)listenerObj;

               try {
                  listener.sessionCreated(event);
               } catch (Throwable var8) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_INITIALIZED_ERROR("sessionCreated", "HttpSessionListener", listener.getClass().getName()), var8);
                  }
               }
            }
         }
      }

   }

   protected void notifyIdChanged(String oldId) {
      EventListener[] listeners = this.contextImpl.getEventListeners();
      if (listeners.length > 0) {
         HttpSessionEvent event = new HttpSessionEvent(this);
         int i = 0;

         for(int len = listeners.length; i < len; ++i) {
            Object listenerObj = listeners[i];
            if (listenerObj instanceof HttpSessionIdListener) {
               HttpSessionIdListener listener = (HttpSessionIdListener)listenerObj;

               try {
                  listener.sessionIdChanged(event, oldId);
               } catch (Throwable var9) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_INITIALIZED_ERROR("sessionCreated", "HttpSessionListener", listener.getClass().getName()), var9);
                  }
               }
            }
         }
      }

   }
}
