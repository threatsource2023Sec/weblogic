package org.jboss.weld.module.jsf;

import java.lang.annotation.Annotation;
import java.util.Map;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.context.FacesContext;
import org.jboss.weld.Container;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.http.HttpConversationContext;

public class ConversationAwareViewHandler extends ViewHandlerWrapper {
   private final ViewHandler delegate;
   private volatile ConversationContext conversationContext;
   private static final ThreadLocal source = new ThreadLocal();
   private String contextId;

   public ConversationAwareViewHandler(ViewHandler delegate) {
      this.delegate = delegate;
   }

   private ConversationContext getConversationContext(String id) {
      if (this.conversationContext == null) {
         synchronized(this) {
            if (this.conversationContext == null) {
               if (!Container.available(id)) {
                  return null;
               }

               Container container = Container.instance(id);
               this.conversationContext = (ConversationContext)container.deploymentManager().instance().select(HttpConversationContext.class, new Annotation[0]).get();
            }
         }
      }

      return this.conversationContext;
   }

   public String getActionURL(FacesContext facesContext, String viewId) {
      if (this.contextId == null) {
         if (facesContext.getAttributes().containsKey("WELD_CONTEXT_ID_KEY")) {
            this.contextId = (String)facesContext.getAttributes().get("WELD_CONTEXT_ID_KEY");
         } else {
            this.contextId = "STATIC_INSTANCE";
         }
      }

      String actionUrl = super.getActionURL(facesContext, viewId);
      ConversationContext ctx = this.getConversationContext(this.contextId);
      return ctx != null && ctx.isActive() && !this.getSource().equals(ConversationAwareViewHandler.Source.BOOKMARKABLE) && !ctx.getCurrentConversation().isTransient() ? (new FacesUrlTransformer(actionUrl, facesContext)).appendConversationIdIfNecessary(this.getConversationContext(this.contextId).getParameterName(), ctx.getCurrentConversation().getId()).getUrl() : actionUrl;
   }

   private Source getSource() {
      return source.get() == null ? ConversationAwareViewHandler.Source.ACTION : (Source)source.get();
   }

   public String getBookmarkableURL(FacesContext context, String viewId, Map parameters, boolean includeViewParams) {
      String var5;
      try {
         source.set(ConversationAwareViewHandler.Source.BOOKMARKABLE);
         var5 = super.getBookmarkableURL(context, viewId, parameters, includeViewParams);
      } finally {
         source.remove();
      }

      return var5;
   }

   public String getRedirectURL(FacesContext context, String viewId, Map parameters, boolean includeViewParams) {
      String var5;
      try {
         source.set(ConversationAwareViewHandler.Source.REDIRECT);
         var5 = super.getRedirectURL(context, viewId, parameters, includeViewParams);
      } finally {
         source.remove();
      }

      return var5;
   }

   public String getResourceURL(FacesContext context, String path) {
      String var3;
      try {
         source.set(ConversationAwareViewHandler.Source.RESOURCE);
         var3 = super.getResourceURL(context, path);
      } finally {
         source.remove();
      }

      return var3;
   }

   public ViewHandler getWrapped() {
      return this.delegate;
   }

   private static enum Source {
      ACTION,
      BOOKMARKABLE,
      REDIRECT,
      RESOURCE;
   }
}
