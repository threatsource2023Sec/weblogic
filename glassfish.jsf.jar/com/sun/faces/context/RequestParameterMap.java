package com.sun.faces.context;

import com.sun.faces.util.Util;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;

public class RequestParameterMap extends BaseContextMap {
   private String namingContainerPrefix;
   private final ServletRequest request;
   private boolean inspectedParameterNames = false;

   public RequestParameterMap(ServletRequest request) {
      this.request = request;
   }

   public String get(Object key) {
      Util.notNull("key", key);
      if (!this.inspectedParameterNames) {
         this.inspectedParameterNames = true;
         this.request.getParameterNames();
      }

      String mapKey = key.toString();
      String mapValue = this.request.getParameter(mapKey);
      if (mapValue == null && !mapKey.startsWith(this.getNamingContainerPrefix())) {
         mapValue = this.request.getParameter(this.getNamingContainerPrefix() + mapKey);
      }

      return mapValue;
   }

   public Set entrySet() {
      return Collections.unmodifiableSet(super.entrySet());
   }

   public Set keySet() {
      return Collections.unmodifiableSet(super.keySet());
   }

   protected String getNamingContainerPrefix() {
      if (null == this.namingContainerPrefix) {
         FacesContext context = FacesContext.getCurrentInstance();
         if (context == null) {
            return "";
         }

         this.namingContainerPrefix = Util.getNamingContainerPrefix(context);
      }

      return this.namingContainerPrefix;
   }

   public Collection values() {
      return Collections.unmodifiableCollection(super.values());
   }

   public boolean containsKey(Object key) {
      return key != null && this.get(key) != null;
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
      return new BaseContextMap.EntryIterator(this.request.getParameterNames());
   }

   protected Iterator getKeyIterator() {
      return new BaseContextMap.KeyIterator(this.request.getParameterNames());
   }

   protected Iterator getValueIterator() {
      return new BaseContextMap.ValueIterator(this.request.getParameterNames());
   }
}
