package org.glassfish.grizzly.servlet;

import java.io.CharConversionException;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;
import javax.servlet.DispatcherType;
import org.glassfish.grizzly.http.util.URLDecoder;

public class FilterMap implements Serializable {
   private static final EnumSet DEFAULT_DISPATCHER;
   private String filterName = null;
   private String servletName = null;
   private String urlPattern = null;
   private Set dispatcherTypes;

   public String getFilterName() {
      return this.filterName;
   }

   public void setFilterName(String filterName) {
      this.filterName = filterName;
   }

   public String getServletName() {
      return this.servletName;
   }

   public void setServletName(String servletName) {
      this.servletName = servletName;
   }

   public String getURLPattern() {
      return this.urlPattern;
   }

   public void setURLPattern(String urlPattern) {
      try {
         this.urlPattern = URLDecoder.decode(urlPattern);
      } catch (CharConversionException var3) {
         throw new IllegalStateException(var3);
      }
   }

   public Set getDispatcherTypes() {
      return (Set)(this.dispatcherTypes != null && !this.dispatcherTypes.isEmpty() ? this.dispatcherTypes : DEFAULT_DISPATCHER);
   }

   public void setDispatcherTypes(Set dispatcherTypes) {
      this.dispatcherTypes = dispatcherTypes;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("FilterMap[");
      sb.append("filterName=");
      sb.append(this.filterName);
      if (this.servletName != null) {
         sb.append(", servletName=");
         sb.append(this.servletName);
      }

      if (this.urlPattern != null) {
         sb.append(", urlPattern=");
         sb.append(this.urlPattern);
      }

      sb.append("]");
      return sb.toString();
   }

   static {
      DEFAULT_DISPATCHER = EnumSet.of(DispatcherType.REQUEST);
   }
}
