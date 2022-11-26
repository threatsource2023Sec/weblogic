package com.sun.faces.context;

import com.sun.faces.util.Util;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletContext;

public class InitParameterMap extends BaseContextMap {
   private final ServletContext servletContext;

   public InitParameterMap(ServletContext newServletContext) {
      this.servletContext = newServletContext;
   }

   public String get(Object key) {
      Util.notNull("key", key);
      String keyString = key.toString();
      return this.servletContext.getInitParameter(keyString);
   }

   public Set entrySet() {
      return Collections.unmodifiableSet(super.entrySet());
   }

   public Set keySet() {
      return Collections.unmodifiableSet(super.keySet());
   }

   public Collection values() {
      return Collections.unmodifiableCollection(super.values());
   }

   public boolean containsKey(Object key) {
      return this.servletContext.getInitParameter(key.toString()) != null;
   }

   public boolean equals(Object obj) {
      return obj != null && obj.getClass() == ExternalContextImpl.theUnmodifiableMapClass && super.equals(obj);
   }

   public int hashCode() {
      int hashCode = 7 * this.servletContext.hashCode();

      for(Iterator i = this.entrySet().iterator(); i.hasNext(); hashCode += i.next().hashCode()) {
      }

      return hashCode;
   }

   protected Iterator getEntryIterator() {
      return new BaseContextMap.EntryIterator(this.servletContext.getInitParameterNames());
   }

   protected Iterator getKeyIterator() {
      return new BaseContextMap.KeyIterator(this.servletContext.getInitParameterNames());
   }

   protected Iterator getValueIterator() {
      return new BaseContextMap.ValueIterator(this.servletContext.getInitParameterNames());
   }
}
