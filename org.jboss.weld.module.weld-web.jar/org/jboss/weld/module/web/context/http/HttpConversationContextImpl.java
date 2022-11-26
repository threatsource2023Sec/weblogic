package org.jboss.weld.module.web.context.http;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.context.http.HttpConversationContext;
import org.jboss.weld.contexts.AbstractConversationContext;
import org.jboss.weld.contexts.beanstore.BoundBeanStore;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.module.web.context.beanstore.http.EagerSessionBeanStore;
import org.jboss.weld.module.web.context.beanstore.http.LazySessionBeanStore;
import org.jboss.weld.module.web.servlet.SessionHolder;
import org.jboss.weld.util.collections.EnumerationIterator;

public class HttpConversationContextImpl extends AbstractConversationContext implements HttpConversationContext {
   private static final String NAMING_SCHEME_PREFIX = "WELD_C";

   public HttpConversationContextImpl(String contextId, ServiceRegistry services) {
      super(contextId, services);
   }

   protected void setSessionAttribute(HttpServletRequest request, String name, Object value, boolean create) {
      if (create || SessionHolder.getSessionIfExists() != null) {
         this.getSessionFromRequest(request, true).setAttribute(name, value);
      }

   }

   protected Object getSessionAttribute(HttpServletRequest request, String name, boolean create) {
      return !create && SessionHolder.getSessionIfExists() == null ? null : this.getSessionFromRequest(request, true).getAttribute(name);
   }

   protected void removeRequestAttribute(HttpServletRequest request, String name) {
      request.removeAttribute(name);
   }

   protected void setRequestAttribute(HttpServletRequest request, String name, Object value) {
      request.setAttribute(name, value);
   }

   protected Object getRequestAttribute(HttpServletRequest request, String name) {
      return request.getAttribute(name);
   }

   protected BoundBeanStore createRequestBeanStore(NamingScheme namingScheme, HttpServletRequest request) {
      return new LazySessionBeanStore(request, namingScheme, false, this.getServiceRegistry());
   }

   protected BoundBeanStore createSessionBeanStore(NamingScheme namingScheme, HttpSession session) {
      return new EagerSessionBeanStore(namingScheme, session, this.getServiceRegistry());
   }

   protected Object getSessionAttributeFromSession(HttpSession session, String name) {
      return session.getAttribute(name);
   }

   protected HttpSession getSessionFromRequest(HttpServletRequest request, boolean create) {
      return SessionHolder.getSession(request, create);
   }

   protected String getNamingSchemePrefix() {
      return "WELD_C";
   }

   protected Iterator getSessionAttributeNames(HttpSession session) {
      return new EnumerationIterator(session.getAttributeNames());
   }
}
