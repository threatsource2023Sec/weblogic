package weblogic.cache.webapp;

import java.util.Iterator;
import javax.servlet.ServletRequest;
import weblogic.cache.CacheException;
import weblogic.cache.CacheScope;
import weblogic.cache.EnumerationIterator;

public class ServletRequestParameterScope implements CacheScope {
   private ServletRequest request;

   public ServletRequestParameterScope() {
   }

   public ServletRequestParameterScope(ServletRequest request) {
      this.setRequest(request);
   }

   public void setRequest(ServletRequest request) {
      this.request = request;
   }

   public ServletRequest getRequest() {
      return this.request;
   }

   public boolean isReadOnly() {
      return true;
   }

   public Iterator getAttributeNames() throws CacheException {
      return new EnumerationIterator(this.request.getParameterNames());
   }

   public void setAttribute(String key, Object value) throws CacheException {
      throw new CacheException("Servlet request parameter scope is read only, cannot write " + key + " = " + value);
   }

   public Object getAttribute(String key) throws CacheException {
      return this.request.getParameter(key);
   }

   public void removeAttribute(String key) throws CacheException {
      throw new CacheException("Servlet request parameter scope is read only, cannot remove " + key);
   }
}
