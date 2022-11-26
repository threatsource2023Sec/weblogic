package weblogic.cache.webapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import weblogic.cache.CacheException;
import weblogic.cache.CacheScope;

public class ServletRequestCookieScope implements CacheScope {
   private Map cookies = new HashMap();

   public ServletRequestCookieScope() {
   }

   public ServletRequestCookieScope(HttpServletRequest request) {
      this.setRequest(request);
   }

   public void setRequest(HttpServletRequest request) {
      Cookie[] cookies = request.getCookies();

      for(int i = 0; cookies != null && i < cookies.length; ++i) {
         this.cookies.put(cookies[i].getName(), cookies[i].getValue());
      }

   }

   public boolean isReadOnly() {
      return true;
   }

   public Iterator getAttributeNames() throws CacheException {
      return this.cookies.keySet().iterator();
   }

   public void setAttribute(String key, Object value) throws CacheException {
      throw new CacheException("Servlet request cookie scope is read only, cannot write " + key + " = " + value);
   }

   public Object getAttribute(String key) throws CacheException {
      return this.cookies.get(key);
   }

   public void removeAttribute(String key) throws CacheException {
      throw new CacheException("Servlet request cookie scope is read only, cannot remove " + key);
   }
}
