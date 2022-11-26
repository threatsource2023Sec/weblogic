package weblogic.cache.webapp;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import weblogic.cache.CacheException;
import weblogic.cache.CacheScope;
import weblogic.cache.EnumerationIterator;

public class ServletRequestHeaderScope implements CacheScope {
   private HttpServletRequest request;

   public ServletRequestHeaderScope() {
   }

   public ServletRequestHeaderScope(HttpServletRequest request) {
      this.setRequest(request);
   }

   public void setRequest(HttpServletRequest request) {
      this.request = request;
   }

   public HttpServletRequest getRequest() {
      return this.request;
   }

   public boolean isReadOnly() {
      return true;
   }

   public Iterator getAttributeNames() throws CacheException {
      return new EnumerationIterator(this.request.getHeaderNames());
   }

   public void setAttribute(String key, Object value) throws CacheException {
      throw new CacheException("Read only scope");
   }

   public Object getAttribute(String key) throws CacheException {
      return this.request.getHeader(key);
   }

   public void removeAttribute(String key) throws CacheException {
      throw new CacheException("You cannot remove a header once set: " + key);
   }
}
