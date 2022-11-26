package com.sun.faces.application.view;

import com.sun.faces.application.ViewHandlerResponseWrapper;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.beans.BeanInfo;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Resource;
import javax.faces.application.ViewVisitOption;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.view.StateManagementStrategy;
import javax.faces.view.ViewMetadata;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

public class JspViewHandlingStrategy extends ViewHandlingStrategy {
   private static final Logger LOGGER;
   private int responseBufferSize;

   public JspViewHandlingStrategy() {
      try {
         this.responseBufferSize = Integer.parseInt(this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.ResponseBufferSize));
      } catch (NumberFormatException var2) {
         this.responseBufferSize = Integer.parseInt(WebConfiguration.WebContextInitParameter.ResponseBufferSize.getDefaultValue());
      }

   }

   public ViewMetadata getViewMetadata(FacesContext context, String viewId) {
      return null;
   }

   public void buildView(FacesContext context, UIViewRoot view) throws IOException {
      if (!Util.isViewPopulated(context, view)) {
         try {
            if (this.executePageToBuildView(context, view)) {
               context.getExternalContext().responseFlushBuffer();
               if (this.associate != null) {
                  this.associate.responseRendered();
               }

               context.responseComplete();
               return;
            }
         } catch (IOException var4) {
            throw new FacesException(var4);
         }

         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Completed building view for : \n" + view.getViewId());
         }

         context.getApplication().publishEvent(context, PostAddToViewEvent.class, UIViewRoot.class, view);
         Util.setViewPopulated(context, view);
      }
   }

   public void renderView(FacesContext context, UIViewRoot view) throws IOException {
      if (view.isRendered() && !context.getResponseComplete()) {
         ExternalContext extContext = context.getExternalContext();
         if (!Util.isViewPopulated(context, view)) {
            this.buildView(context, view);
         }

         RenderKitFactory renderFactory = (RenderKitFactory)FactoryFinder.getFactory("javax.faces.render.RenderKitFactory");
         RenderKit renderKit = renderFactory.getRenderKit(context, view.getRenderKitId());
         ResponseWriter oldWriter = context.getResponseWriter();
         WriteBehindStateWriter stateWriter = new WriteBehindStateWriter(extContext.getResponseOutputWriter(), context, this.responseBufferSize);
         ResponseWriter newWriter;
         if (null != oldWriter) {
            newWriter = oldWriter.cloneWithWriter(stateWriter);
         } else {
            newWriter = renderKit.createResponseWriter(stateWriter, (String)null, extContext.getRequestCharacterEncoding());
         }

         context.setResponseWriter(newWriter);
         if (context.getPartialViewContext().isPartialRequest()) {
            this.doRenderView(context, view);

            try {
               extContext.getFlash().doPostPhaseActions(context);
            } catch (UnsupportedOperationException var11) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine("ExternalContext.getFlash() throw UnsupportedOperationException -> Flash unavailable");
               }
            }
         } else {
            newWriter.startDocument();
            this.doRenderView(context, view);

            try {
               extContext.getFlash().doPostPhaseActions(context);
            } catch (UnsupportedOperationException var10) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine("ExternalContext.getFlash() throw UnsupportedOperationException -> Flash unavailable");
               }
            }

            newWriter.endDocument();
         }

         if (stateWriter.stateWritten()) {
            stateWriter.flushToWriter();
         }

         stateWriter.release();
         if (null != oldWriter) {
            context.setResponseWriter(oldWriter);
         }

         ViewHandlerResponseWrapper wrapper = (ViewHandlerResponseWrapper)RequestStateManager.remove(context, "com.sun.faces.AFTER_VIEW_CONTENT");
         if (null != wrapper) {
            wrapper.flushToWriter(extContext.getResponseOutputWriter(), extContext.getResponseCharacterEncoding());
         }

         extContext.responseFlushBuffer();
      }
   }

   public StateManagementStrategy getStateManagementStrategy(FacesContext context, String viewId) {
      return null;
   }

   public BeanInfo getComponentMetadata(FacesContext context, Resource componentResource) {
      throw new UnsupportedOperationException();
   }

   public Resource getScriptComponentResource(FacesContext context, Resource componentResource) {
      throw new UnsupportedOperationException();
   }

   public Stream getViews(FacesContext context, String path, ViewVisitOption... options) {
      return Stream.empty();
   }

   public Stream getViews(FacesContext context, String path, int maxDepth, ViewVisitOption... options) {
      return Stream.empty();
   }

   public boolean handlesViewId(String viewId) {
      return true;
   }

   public String getId() {
      return "java.faces.JSP";
   }

   private boolean executePageToBuildView(FacesContext context, UIViewRoot viewToExecute) throws IOException {
      String message;
      if (null == context) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else if (null == viewToExecute) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "viewToExecute");
         throw new NullPointerException(message);
      } else {
         ExternalContext extContext = context.getExternalContext();
         if ("/*".equals(RequestStateManager.get(context, "com.sun.faces.INVOCATION_PATH"))) {
            throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.FACES_SERVLET_MAPPING_INCORRECT"));
         } else {
            String requestURI = viewToExecute.getViewId();
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("About to execute view " + requestURI);
            }

            if (extContext.getRequest() instanceof ServletRequest) {
               Config.set((ServletRequest)extContext.getRequest(), "javax.servlet.jsp.jstl.fmt.locale", context.getViewRoot().getLocale());
            }

            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("Before dispacthMessage to viewId " + requestURI);
            }

            Object originalResponse = extContext.getResponse();
            ViewHandlerResponseWrapper wrapped = getWrapper(extContext);
            extContext.setResponse(wrapped);

            try {
               extContext.dispatch(requestURI);
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine("After dispacthMessage to viewId " + requestURI);
               }
            } finally {
               extContext.setResponse(originalResponse);
            }

            if (wrapped.getStatus() >= 200 && wrapped.getStatus() <= 299) {
               RequestStateManager.set(context, "com.sun.faces.AFTER_VIEW_CONTENT", wrapped);
               return false;
            } else {
               wrapped.flushContentToWrappedResponse();
               return true;
            }
         }
      }
   }

   private void doRenderView(FacesContext context, UIViewRoot viewToRender) throws IOException {
      if (null != this.associate) {
         this.associate.responseRendered();
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "About to render view " + viewToRender.getViewId());
      }

      viewToRender.encodeAll(context);
   }

   private static ViewHandlerResponseWrapper getWrapper(ExternalContext extContext) {
      Object response = extContext.getResponse();
      if (response instanceof HttpServletResponse) {
         return new ViewHandlerResponseWrapper((HttpServletResponse)response);
      } else {
         throw new IllegalArgumentException();
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
