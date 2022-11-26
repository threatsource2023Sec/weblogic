package com.sun.faces.context;

import com.sun.faces.util.Util;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletRequest;

public class RequestParameterValuesMap extends StringArrayValuesMap {
   private final ServletRequest request;

   public RequestParameterValuesMap(ServletRequest request) {
      this.request = request;
   }

   public String[] get(Object key) {
      Util.notNull("key", key);
      return this.request.getParameterValues(key.toString());
   }

   public boolean containsKey(Object key) {
      return this.request.getParameterValues(key.toString()) != null;
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

   public int hashCode() {
      return this.hashCode(this.request);
   }

   public boolean equals(Object obj) {
      return obj != null && obj.getClass() == ExternalContextImpl.theUnmodifiableMapClass && super.equals(obj);
   }

   protected Iterator getEntryIterator() {
      return new BaseContextMap.EntryIterator(this.request.getParameterNames());
   }

   protected Iterator getKeyIterator() {
      return new BaseContextMap.KeyIterator(this.request.getParameterNames());
   }

   protected Iterator getValueIterator() {
      return new BaseContextMap.ValueIterator(this.request.getParameterNames());
   }
}
