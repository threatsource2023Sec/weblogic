package weblogic.cache.webapp;

import java.util.Iterator;
import javax.servlet.http.HttpSession;
import weblogic.cache.CacheException;
import weblogic.cache.CacheScope;
import weblogic.cache.EmptyIterator;
import weblogic.cache.EnumerationIterator;
import weblogic.servlet.internal.session.SessionInternal;

public class ServletSessionAttributeScope implements CacheScope {
   private SessionInternal session;

   public ServletSessionAttributeScope() {
   }

   public ServletSessionAttributeScope(HttpSession session) {
      this.session = (SessionInternal)session;
   }

   public void setSession(HttpSession session) {
      this.session = (SessionInternal)session;
   }

   public boolean isReadOnly() {
      return false;
   }

   public Iterator getAttributeNames() throws CacheException {
      return (Iterator)(this.session == null ? new EmptyIterator() : new EnumerationIterator(this.session.getInternalAttributeNames()));
   }

   public void setAttribute(String key, Object value) throws CacheException {
      if (this.session == null) {
         throw new CacheException("Tried to set a value in a null session scope");
      } else {
         this.session.setInternalAttribute(key, value);
      }
   }

   public Object getAttribute(String key) throws CacheException {
      return this.session == null ? null : this.session.getInternalAttribute(key);
   }

   public void removeAttribute(String key) throws CacheException {
      if (this.session != null) {
         this.session.removeInternalAttribute(key);
      }
   }
}
