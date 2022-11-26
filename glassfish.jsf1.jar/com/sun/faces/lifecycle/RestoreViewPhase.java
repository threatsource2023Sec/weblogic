package com.sun.faces.lifecycle;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.render.ResponseStateManager;

public class RestoreViewPhase extends Phase {
   private static final String WEBAPP_ERROR_PAGE_MARKER = "javax.servlet.error.message";
   private static Logger LOGGER;
   private WebConfiguration webConfig;

   public PhaseId getId() {
      return PhaseId.RESTORE_VIEW;
   }

   public void doPhase(FacesContext context, Lifecycle lifecycle, ListIterator listeners) {
      Util.getViewHandler(context).initView(context);
      super.doPhase(context, lifecycle, listeners);
   }

   public void execute(FacesContext facesContext) throws FacesException {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Entering RestoreViewPhase");
      }

      if (null == facesContext) {
         throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_CONTEXT_ERROR"));
      } else {
         UIViewRoot viewRoot = facesContext.getViewRoot();
         if (viewRoot != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("Found a pre created view in FacesContext");
            }

            facesContext.getViewRoot().setLocale(facesContext.getExternalContext().getRequestLocale());
            this.doPerComponentActions(facesContext, viewRoot);
            if (!this.isPostback(facesContext)) {
               facesContext.renderResponse();
            }

         } else {
            String viewId;
            if (Util.isPortletRequest(facesContext)) {
               viewId = facesContext.getExternalContext().getRequestPathInfo();
               if (viewId == null) {
                  viewId = facesContext.getExternalContext().getRequestServletPath();
               }
            } else {
               Map requestMap = facesContext.getExternalContext().getRequestMap();
               viewId = (String)requestMap.get("javax.servlet.include.path_info");
               if (viewId == null) {
                  viewId = facesContext.getExternalContext().getRequestPathInfo();
               }

               if (viewId == null) {
                  viewId = (String)requestMap.get("javax.servlet.include.servlet_path");
               }

               if (viewId == null) {
                  viewId = facesContext.getExternalContext().getRequestServletPath();
               }
            }

            if (viewId == null) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.warning("viewId is null");
               }

               throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_REQUEST_VIEW_ERROR"));
            } else {
               boolean isPostBack = this.isPostback(facesContext) && !isErrorPage(facesContext);
               if (isPostBack) {
                  ViewHandler viewHandler = Util.getViewHandler(facesContext);
                  viewRoot = viewHandler.restoreView(facesContext, viewId);
                  if (viewRoot == null) {
                     if (!this.is11CompatEnabled(facesContext)) {
                        Object[] params = new Object[]{viewId};
                        throw new ViewExpiredException(MessageUtils.getExceptionMessageString("com.sun.faces.RESTORE_VIEW_ERROR", params), viewId);
                     }

                     viewRoot = viewHandler.createView(facesContext, viewId);
                     facesContext.renderResponse();
                  }

                  facesContext.setViewRoot(viewRoot);
                  this.doPerComponentActions(facesContext, viewRoot);
                  if (LOGGER.isLoggable(Level.FINE)) {
                     LOGGER.fine("Postback: Restored view for " + viewId);
                  }
               } else {
                  if (LOGGER.isLoggable(Level.FINE)) {
                     LOGGER.fine("New request: creating a view for " + viewId);
                  }

                  viewRoot = Util.getViewHandler(facesContext).createView(facesContext, viewId);
                  facesContext.setViewRoot(viewRoot);
                  facesContext.renderResponse();
               }

               assert null != viewRoot;

               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine("Exiting RestoreViewPhase");
               }

            }
         }
      }
   }

   protected void doPerComponentActions(FacesContext context, UIComponent uic) {
      ValueExpression valueExpression;
      if (null != (valueExpression = uic.getValueExpression("binding"))) {
         valueExpression.setValue(context.getELContext(), uic);
      }

      Iterator kids = uic.getFacetsAndChildren();

      while(kids.hasNext()) {
         this.doPerComponentActions(context, (UIComponent)kids.next());
      }

   }

   private boolean isPostback(FacesContext context) {
      String renderkitId = context.getApplication().getViewHandler().calculateRenderKitId(context);
      ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(context, renderkitId);
      return rsm.isPostback(context);
   }

   private static boolean isErrorPage(FacesContext context) {
      return context.getExternalContext().getRequestMap().get("javax.servlet.error.message") != null;
   }

   private WebConfiguration getWebConfig(FacesContext context) {
      if (this.webConfig == null) {
         this.webConfig = WebConfiguration.getInstance(context.getExternalContext());
      }

      return this.webConfig;
   }

   private boolean is11CompatEnabled(FacesContext context) {
      return this.getWebConfig(context).isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableRestoreView11Compatibility);
   }

   static {
      LOGGER = FacesLogger.LIFECYCLE.getLogger();
   }
}
