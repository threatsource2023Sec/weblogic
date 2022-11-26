package com.sun.faces.context;

import com.sun.faces.util.Util;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletRequest;

class RequestMap extends BaseContextMap {
   private final ServletRequest request;

   RequestMap(ServletRequest request) {
      this.request = request;
   }

   public void clear() {
      Enumeration e = this.request.getAttributeNames();

      while(e.hasMoreElements()) {
         this.request.removeAttribute((String)e.nextElement());
      }

   }

   public void putAll(Map t) {
      Iterator i = t.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         this.request.setAttribute((String)entry.getKey(), entry.getValue());
      }

   }

   public Object get(Object key) {
      Util.notNull("key", key);
      return this.request.getAttribute(key.toString());
   }

   public Object put(Object key, Object value) {
      Util.notNull("key", key);
      Object result = this.request.getAttribute(key.toString());
      this.request.setAttribute(key.toString(), value);
      return result;
   }

   public Object remove(Object key) {
      Util.notNull("key", key);
      String keyString = key.toString();
      Object result = this.request.getAttribute(keyString);
      this.request.removeAttribute(keyString);
      return result;
   }

   public boolean containsKey(Object key) {
      return this.request.getAttribute(key.toString()) != null;
   }

   public boolean equals(Object obj) {
      return obj != null && obj instanceof RequestMap && super.equals(obj);
   }

   public int hashCode() {
      int hashCode = 7 * this.request.hashCode();

      for(Iterator i = this.entrySet().iterator(); i.hasNext(); hashCode += i.next().hashCode()) {
      }

      return hashCode;
   }

   protected Iterator getEntryIterator() {
      return new BaseContextMap.EntryIterator(this.request.getAttributeNames());
   }

   protected Iterator getKeyIterator() {
      return new BaseContextMap.KeyIterator(this.request.getAttributeNames());
   }

   protected Iterator getValueIterator() {
      return new BaseContextMap.ValueIterator(this.request.getAttributeNames());
   }
}
