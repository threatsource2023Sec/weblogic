package com.sun.faces.application.view;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;

public abstract class ViewHandlingStrategy extends ViewDeclarationLanguage {
   private static final Logger logger;
   protected ApplicationAssociate associate;
   protected WebConfiguration webConfig;

   public ViewHandlingStrategy() {
      FacesContext ctx = FacesContext.getCurrentInstance();
      this.webConfig = WebConfiguration.getInstance(ctx.getExternalContext());
      this.associate = ApplicationAssociate.getInstance(ctx.getExternalContext());
   }

   public UIViewRoot restoreView(FacesContext ctx, String viewId) {
      ExternalContext extContext = ctx.getExternalContext();
      String mapping = Util.getFacesMapping(ctx);
      UIViewRoot viewRoot = null;
      if (extContext.getRequestPathInfo() == null && mapping != null && Util.isPrefixMapped(mapping)) {
         try {
            ctx.responseComplete();
            if (logger.isLoggable(Level.FINE)) {
               logger.log(Level.FINE, "Response Complete for" + viewId);
            }

            if (!extContext.isResponseCommitted()) {
               extContext.redirect(extContext.getRequestContextPath());
            }
         } catch (IOException var8) {
            throw new FacesException(var8);
         }
      } else {
         ViewHandler outerViewHandler = ctx.getApplication().getViewHandler();
         String renderKitId = outerViewHandler.calculateRenderKitId(ctx);
         viewRoot = Util.getStateManager(ctx).restoreView(ctx, viewId, renderKitId);
      }

      return viewRoot;
   }

   public UIViewRoot createView(FacesContext ctx, String viewId) {
      Util.notNull("context", ctx);
      UIViewRoot result = (UIViewRoot)ctx.getApplication().createComponent("javax.faces.ViewRoot");
      Locale locale = null;
      String renderKitId = null;
      if (ctx.getViewRoot() != null) {
         locale = ctx.getViewRoot().getLocale();
         renderKitId = ctx.getViewRoot().getRenderKitId();
      }

      if (logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "Created new view for " + viewId);
      }

      if (locale == null) {
         locale = ctx.getApplication().getViewHandler().calculateLocale(ctx);
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("Locale for this view as determined by calculateLocale " + locale.toString());
         }
      } else if (logger.isLoggable(Level.FINE)) {
         logger.fine("Using locale from previous view " + locale.toString());
      }

      if (renderKitId == null) {
         renderKitId = ctx.getApplication().getViewHandler().calculateRenderKitId(ctx);
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("RenderKitId for this view as determined by calculateRenderKitId " + renderKitId);
         }
      } else if (logger.isLoggable(Level.FINE)) {
         logger.fine("Using renderKitId from previous view " + renderKitId);
      }

      result.setLocale(locale);
      result.setRenderKitId(renderKitId);
      result.setViewId(viewId);
      return result;
   }

   public abstract boolean handlesViewId(String var1);

   static {
      logger = FacesLogger.APPLICATION.getLogger();
   }
}
