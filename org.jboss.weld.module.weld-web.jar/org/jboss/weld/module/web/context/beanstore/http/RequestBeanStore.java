package org.jboss.weld.module.web.context.beanstore.http;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.jboss.weld.contexts.beanstore.AttributeBeanStore;
import org.jboss.weld.contexts.beanstore.LockStore;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.util.collections.EnumerationIterator;

public class RequestBeanStore extends AttributeBeanStore {
   private final HttpServletRequest request;

   public RequestBeanStore(HttpServletRequest request, NamingScheme namingScheme) {
      super(namingScheme, false);
      this.request = request;
   }

   protected Object getAttribute(String key) {
      return this.request.getAttribute(key);
   }

   protected void removeAttribute(String key) {
      this.request.removeAttribute(key);
   }

   protected Iterator getAttributeNames() {
      return new EnumerationIterator(this.request.getAttributeNames());
   }

   protected void setAttribute(String key, Object instance) {
      this.request.setAttribute(key, instance);
   }

   public HttpServletRequest getRequest() {
      return this.request;
   }

   public LockStore getLockStore() {
      return null;
   }

   protected boolean isLocalBeanStoreSyncNeeded() {
      return Boolean.TRUE.equals(this.request.getAttribute("org.jboss.weld.context.asyncStarted"));
   }
}
