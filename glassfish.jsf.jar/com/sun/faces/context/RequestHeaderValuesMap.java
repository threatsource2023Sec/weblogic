package com.sun.faces.context;

import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public class RequestHeaderValuesMap extends StringArrayValuesMap {
   private final HttpServletRequest request;

   public RequestHeaderValuesMap(HttpServletRequest request) {
      this.request = request;
   }

   public boolean containsKey(Object key) {
      return this.request.getHeaders(key.toString()) != null;
   }

   public String[] get(Object key) {
      Util.notNull("key", key);
      List valuesList = new ArrayList();
      Enumeration valuesEnum = this.request.getHeaders(key.toString());

      while(valuesEnum.hasMoreElements()) {
         valuesList.add((String)valuesEnum.nextElement());
      }

      return (String[])valuesList.toArray(new String[valuesList.size()]);
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
      return new BaseContextMap.EntryIterator(this.request.getHeaderNames());
   }

   protected Iterator getKeyIterator() {
      return new BaseContextMap.KeyIterator(this.request.getHeaderNames());
   }

   protected Iterator getValueIterator() {
      return new BaseContextMap.ValueIterator(this.request.getHeaderNames());
   }
}
