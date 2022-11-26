package weblogic.servlet.internal;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.IteratorEnumerator;

public final class FilterConfigImpl implements FilterConfig {
   private final String filterName;
   private final ServletContext ctx;
   private final Map initParams;

   public FilterConfigImpl(String n, ServletContext c, Map p) {
      this.filterName = n;
      this.ctx = c;
      this.initParams = p;
   }

   public String getFilterName() {
      return this.filterName;
   }

   public ServletContext getServletContext() {
      return this.ctx;
   }

   public String getInitParameter(String key) {
      return this.initParams == null ? null : (String)this.initParams.get(key);
   }

   public Enumeration getInitParameterNames() {
      return (Enumeration)(this.initParams == null ? new EmptyEnumerator() : new IteratorEnumerator(this.initParams.keySet().iterator()));
   }
}
