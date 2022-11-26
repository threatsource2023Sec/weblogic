package weblogic.cache.webapp;

import java.util.Iterator;
import weblogic.cache.CacheException;
import weblogic.cache.CacheScope;
import weblogic.servlet.internal.ServletResponseImpl;

public class ServletResponseHeaderScope implements CacheScope {
   private ServletResponseImpl response;

   public ServletResponseHeaderScope() {
   }

   public ServletResponseHeaderScope(ServletResponseImpl response) {
      this.setResponse(response);
   }

   public void setResponse(ServletResponseImpl response) {
      this.response = response;
   }

   public ServletResponseImpl getResponse() {
      return this.response;
   }

   public boolean isReadOnly() {
      return false;
   }

   public Iterator getAttributeNames() throws CacheException {
      throw new CacheException("You cannot list the headers in the repsponse");
   }

   public void setAttribute(String key, Object value) throws CacheException {
      this.response.setHeader(key, value.toString());
   }

   public Object getAttribute(String key) throws CacheException {
      return this.response == null ? null : this.response.getHeader(key);
   }

   public void removeAttribute(String key) throws CacheException {
   }
}
