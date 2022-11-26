package com.sun.faces.context;

import com.sun.faces.util.Util;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public class RequestHeaderMap extends BaseContextMap {
   private final HttpServletRequest request;

   public RequestHeaderMap(HttpServletRequest request) {
      this.request = request;
   }

   public String get(Object key) {
      Util.notNull("key", key);
      return this.request.getHeader(key.toString());
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
      return this.request.getHeader(key.toString()) != null;
   }

   public boolean equals(Object obj) {
      return obj != null && obj.getClass() == ExternalContextImpl.theUnmodifiableMapClass && super.equals(obj);
   }

   public int hashCode() {
      int hashCode = 7 * this.request.hashCode();

      for(Iterator i = this.entrySet().iterator(); i.hasNext(); hashCode += i.next().hashCode()) {
      }

      return hashCode;
   }

   protected Iterator getEntryIterator() {
      return new BaseContextMap.EntryIterator(this.request.getHeaderNames());
   }

   protected Iterator getKeyIterator() {
      return new BaseContextMap.KeyIterator(this.request.getHeaderNames());
   }

   protected Iterator getValueIterator() {
      return new BaseContextMap.ValueIterator(this.request.getHeaderNames());
   }
}
