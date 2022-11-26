package weblogic.cache.webapp;

import java.util.Iterator;
import javax.servlet.ServletRequest;
import weblogic.cache.CacheException;
import weblogic.cache.CacheScope;
import weblogic.cache.EnumerationIterator;

public class ServletRequestAttributeScope implements CacheScope {
   private ServletRequest request;

   public ServletRequestAttributeScope() {
   }

   public ServletRequestAttributeScope(ServletRequest request) {
      this.setRequest(request);
   }

   public void setRequest(ServletRequest request) {
      this.request = request;
   }

   public ServletRequest getRequest() {
      return this.request;
   }

   public boolean isReadOnly() {
      return false;
   }

   public Iterator getAttributeNames() throws CacheException {
      return new EnumerationIterator(this.request.getAttributeNames());
   }

   public void setAttribute(String key, Object value) throws CacheException {
      this.request.setAttribute(key, value);
   }

   public Object getAttribute(String key) throws CacheException {
      return this.request.getAttribute(key);
   }

   public void removeAttribute(String key) throws CacheException {
      this.request.removeAttribute(key);
   }
}
