package com.sun.faces.context;

import com.sun.faces.util.Util;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletContext;

public class ApplicationMap extends BaseContextMap {
   private final ServletContext servletContext;

   public ApplicationMap(ServletContext servletContext) {
      this.servletContext = servletContext;
   }

   public Object getContext() {
      return this.servletContext;
   }

   public void clear() {
      Enumeration e = this.servletContext.getAttributeNames();

      while(e.hasMoreElements()) {
         this.servletContext.removeAttribute((String)e.nextElement());
      }

   }

   public void putAll(Map t) {
      Iterator i = t.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         this.servletContext.setAttribute((String)entry.getKey(), entry.getValue());
      }

   }

   public Object get(Object key) {
      Util.notNull("key", key);
      return this.servletContext.getAttribute(key.toString());
   }

   public Object put(String key, Object value) {
      Util.notNull("key", key);
      Object result = this.servletContext.getAttribute(key);
      this.servletContext.setAttribute(key, value);
      return result;
   }

   public Object remove(Object key) {
      if (key == null) {
         return null;
      } else {
         String keyString = key.toString();
         Object result = this.servletContext.getAttribute(keyString);
         this.servletContext.removeAttribute(keyString);
         return result;
      }
   }

   public boolean containsKey(Object key) {
      return this.servletContext.getAttribute(key.toString()) != null;
   }

   public boolean equals(Object obj) {
      return obj != null && obj instanceof ApplicationMap && super.equals(obj);
   }

   public int hashCode() {
      int hashCode = 7 * this.servletContext.hashCode();

      for(Iterator i = this.entrySet().iterator(); i.hasNext(); hashCode += i.next().hashCode()) {
      }

      return hashCode;
   }

   protected Iterator getEntryIterator() {
      return new BaseContextMap.EntryIterator(this.servletContext.getAttributeNames());
   }

   protected Iterator getKeyIterator() {
      return new BaseContextMap.KeyIterator(this.servletContext.getAttributeNames());
   }

   protected Iterator getValueIterator() {
      return new BaseContextMap.ValueIterator(this.servletContext.getAttributeNames());
   }
}
