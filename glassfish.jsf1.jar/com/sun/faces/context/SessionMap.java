package com.sun.faces.context;

import com.sun.faces.util.Util;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class SessionMap extends BaseContextMap {
   private final HttpServletRequest request;

   SessionMap(HttpServletRequest request) {
      this.request = request;
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
      Iterator i = t.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         session.setAttribute((String)entry.getKey(), entry.getValue());
      }

   }

   public Object get(Object key) {
      Util.notNull("key", key);
      HttpSession session = this.getSession(false);
      return session != null ? session.getAttribute(key.toString()) : null;
   }

   public Object put(Object key, Object value) {
      Util.notNull("key", key);
      HttpSession session = this.getSession(true);
      Object result = session.getAttribute(key.toString());
      session.setAttribute(key.toString(), value);
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

   private HttpSession getSession(boolean createNew) {
      return this.request.getSession(createNew);
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
      return (Iterator)(session != null ? new BaseContextMap.EntryIterator(session.getAttributeNames()) : Collections.emptyMap().entrySet().iterator());
   }

   protected Iterator getKeyIterator() {
      HttpSession session = this.getSession(false);
      return (Iterator)(session != null ? new BaseContextMap.KeyIterator(session.getAttributeNames()) : Collections.emptyMap().entrySet().iterator());
   }

   protected Iterator getValueIterator() {
      HttpSession session = this.getSession(false);
      return (Iterator)(session != null ? new BaseContextMap.ValueIterator(session.getAttributeNames()) : Collections.emptyMap().entrySet().iterator());
   }
}
