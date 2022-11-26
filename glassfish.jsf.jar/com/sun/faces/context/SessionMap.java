package com.sun.faces.context;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ProjectStage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionMap extends BaseContextMap {
   private static final Logger LOGGER;
   private final HttpServletRequest request;
   private final ProjectStage stage;

   public SessionMap(HttpServletRequest request, ProjectStage stage) {
      this.request = request;
      this.stage = stage;
   }

   public void clear() {
      HttpSession session = this.getSession(false);
      if (session != null) {
         Enumeration e = session.getAttributeNames();

         while(e.hasMoreElements()) {
            String name = (String)e.nextElement();
            session.removeAttribute(name);
         }
      }

   }

   public void putAll(Map t) {
      HttpSession session = this.getSession(true);

      Object v;
      Object k;
      for(Iterator i = t.entrySet().iterator(); i.hasNext(); session.setAttribute((String)k, v)) {
         Map.Entry entry = (Map.Entry)i.next();
         v = entry.getValue();
         k = entry.getKey();
         if (ProjectStage.Development.equals(this.stage) && !(v instanceof Serializable) && LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "jsf.context.extcontext.sessionmap.nonserializable", new Object[]{k, v.getClass().getName()});
         }
      }

   }

   public Object get(Object key) {
      Util.notNull("key", key);
      HttpSession session = this.getSession(false);
      return session != null ? session.getAttribute(key.toString()) : null;
   }

   public Object put(String key, Object value) {
      Util.notNull("key", key);
      HttpSession session = this.getSession(true);
      Object result = session.getAttribute(key);
      if (value != null && ProjectStage.Development.equals(this.stage) && !(value instanceof Serializable) && LOGGER.isLoggable(Level.WARNING)) {
         LOGGER.log(Level.WARNING, "jsf.context.extcontext.sessionmap.nonserializable", new Object[]{key, value.getClass().getName()});
      }

      boolean doSet = true;
      if (null != value && null != result) {
         int valCode = System.identityHashCode(value);
         int resultCode = System.identityHashCode(result);
         doSet = valCode != resultCode;
      }

      if (doSet) {
         session.setAttribute(key, value);
      }

      return result;
   }

   public Object remove(Object key) {
      if (key == null) {
         return null;
      } else {
         HttpSession session = this.getSession(false);
         if (session != null) {
            String keyString = key.toString();
            Object result = session.getAttribute(keyString);
            session.removeAttribute(keyString);
            return result;
         } else {
            return null;
         }
      }
   }

   public boolean containsKey(Object key) {
      HttpSession session = this.getSession(false);
      return session != null && session.getAttribute(key.toString()) != null;
   }

   public boolean equals(Object obj) {
      return obj != null && obj instanceof SessionMap && super.equals(obj);
   }

   public int hashCode() {
      HttpSession session = this.getSession(false);
      int hashCode = 7 * (session != null ? session.hashCode() : super.hashCode());
      if (session != null) {
         for(Iterator i = this.entrySet().iterator(); i.hasNext(); hashCode += i.next().hashCode()) {
         }
      }

      return hashCode;
   }

   protected Iterator getEntryIterator() {
      HttpSession session = this.getSession(false);
      if (session != null) {
         return new BaseContextMap.EntryIterator(session.getAttributeNames());
      } else {
         Map empty = Collections.emptyMap();
         return empty.entrySet().iterator();
      }
   }

   protected Iterator getKeyIterator() {
      HttpSession session = this.getSession(false);
      if (session != null) {
         return new BaseContextMap.KeyIterator(session.getAttributeNames());
      } else {
         Map empty = Collections.emptyMap();
         return empty.keySet().iterator();
      }
   }

   protected Iterator getValueIterator() {
      HttpSession session = this.getSession(false);
      if (session != null) {
         return new BaseContextMap.ValueIterator(session.getAttributeNames());
      } else {
         Map empty = Collections.emptyMap();
         return empty.values().iterator();
      }
   }

   protected HttpSession getSession(boolean createNew) {
      return this.request.getSession(createNew);
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
