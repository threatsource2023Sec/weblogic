package org.jboss.weld.contexts.bound;

import java.util.Iterator;
import java.util.Map;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.context.bound.BoundConversationContext;
import org.jboss.weld.context.bound.BoundRequest;
import org.jboss.weld.contexts.AbstractConversationContext;
import org.jboss.weld.contexts.beanstore.BoundBeanStore;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.contexts.beanstore.SessionMapBeanStore;

public class BoundConversationContextImpl extends AbstractConversationContext implements BoundConversationContext {
   private static final String NAMING_SCHEME_PREFIX = "WELD_BC";

   public BoundConversationContextImpl(String contextId, ServiceRegistry services) {
      super(contextId, services);
   }

   protected void setSessionAttribute(BoundRequest request, String name, Object value, boolean create) {
      request.getSessionMap(create).put(name, value);
   }

   protected Object getSessionAttribute(BoundRequest request, String name, boolean create) {
      return request.getSessionMap(create).get(name);
   }

   protected void removeRequestAttribute(BoundRequest request, String name) {
      request.getRequestMap().remove(name);
   }

   protected void setRequestAttribute(BoundRequest request, String name, Object value) {
      request.getRequestMap().put(name, value);
   }

   protected Object getRequestAttribute(BoundRequest request, String name) {
      return request.getRequestMap().get(name);
   }

   protected BoundBeanStore createRequestBeanStore(NamingScheme namingScheme, BoundRequest request) {
      return new SessionMapBeanStore(namingScheme, request.getSessionMap(false));
   }

   protected BoundBeanStore createSessionBeanStore(NamingScheme namingScheme, Map session) {
      return new SessionMapBeanStore(namingScheme, session);
   }

   protected Object getSessionAttributeFromSession(Map session, String name) {
      return session.get(name);
   }

   protected Map getSessionFromRequest(BoundRequest request, boolean create) {
      return request.getSessionMap(create);
   }

   protected String getNamingSchemePrefix() {
      return "WELD_BC";
   }

   protected Iterator getSessionAttributeNames(Map session) {
      return session.keySet().iterator();
   }
}
