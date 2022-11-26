package weblogic.cache.webapp;

import java.util.Iterator;
import javax.servlet.ServletContext;
import weblogic.cache.CacheException;
import weblogic.cache.CacheScope;
import weblogic.cache.EnumerationIterator;

public class ServletContextAttributeScope implements CacheScope {
   private ServletContext context;

   public ServletContextAttributeScope() {
   }

   public ServletContextAttributeScope(ServletContext context) {
      this.setContext(context);
   }

   public void setContext(ServletContext context) {
      this.context = context;
   }

   public ServletContext getContext() {
      return this.context;
   }

   public boolean isReadOnly() {
      return false;
   }

   public Iterator getAttributeNames() throws CacheException {
      return new EnumerationIterator(this.context.getAttributeNames());
   }

   public void setAttribute(String key, Object value) throws CacheException {
      this.context.setAttribute(key, value);
   }

   public Object getAttribute(String key) throws CacheException {
      return this.context.getAttribute(key);
   }

   public void removeAttribute(String key) throws CacheException {
      this.context.removeAttribute(key);
   }
}
