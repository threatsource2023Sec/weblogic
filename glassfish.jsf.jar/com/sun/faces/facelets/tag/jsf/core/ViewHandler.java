package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import com.sun.faces.util.FacesLogger;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.MethodExpression;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;

public final class ViewHandler extends TagHandlerImpl {
   private static final Logger LOGGER;
   private static final Class[] LISTENER_SIG;
   private final TagAttribute locale = this.getAttribute("locale");
   private final TagAttribute renderKitId = this.getAttribute("renderKitId");
   private final TagAttribute contentType = this.getAttribute("contentType");
   private final TagAttribute encoding = this.getAttribute("encoding");
   private final TagAttribute beforePhase;
   private final TagAttribute afterPhase;
   private final TagAttribute transientFlag;
   private final TagAttribute contracts;

   public ViewHandler(TagConfig config) {
      super(config);
      TagAttribute testForNull = this.getAttribute("beforePhase");
      this.beforePhase = null == testForNull ? this.getAttribute("beforePhaseListener") : testForNull;
      testForNull = this.getAttribute("afterPhase");
      this.afterPhase = null == testForNull ? this.getAttribute("afterPhaseListener") : testForNull;
      this.contracts = this.getAttribute("contracts");
      this.transientFlag = this.getAttribute("transient");
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      UIViewRoot root = ComponentSupport.getViewRoot(ctx, parent);
      if (root != null) {
         String viewId;
         if (this.renderKitId != null) {
            viewId = this.renderKitId.getValue(ctx);
            root.setRenderKitId(viewId);
         }

         if (this.contentType != null) {
            viewId = this.contentType.getValue(ctx);
            ctx.getFacesContext().getAttributes().put("facelets.ContentType", viewId);
         }

         if (this.encoding != null) {
            viewId = this.encoding.getValue(ctx);
            ctx.getFacesContext().getAttributes().put("facelets.Encoding", viewId);
            root.getAttributes().put("facelets.Encoding", viewId);
         }

         MethodExpression m;
         if (this.beforePhase != null) {
            m = this.beforePhase.getMethodExpression(ctx, (Class)null, LISTENER_SIG);
            root.setBeforePhaseListener(m);
         }

         if (this.afterPhase != null) {
            m = this.afterPhase.getMethodExpression(ctx, (Class)null, LISTENER_SIG);
            root.setAfterPhaseListener(m);
         }

         if (this.contracts != null) {
            if (ctx.getFacesContext().getAttributes().containsKey("com.sun.faces.uiCompositionCount") && LOGGER.isLoggable(Level.INFO) && ctx.getFacesContext().getApplication().getProjectStage().equals(ProjectStage.Development)) {
               LOGGER.log(Level.INFO, "f:view contracts attribute found, but not used at top level");
            }

            viewId = this.contracts.getValue(ctx);
            if (viewId != null) {
               List contractList = Arrays.asList(viewId.split(","));
               ctx.getFacesContext().setResourceLibraryContracts(contractList);
            }
         }

         if (this.transientFlag != null) {
            Boolean b = Boolean.valueOf(this.transientFlag.getValue(ctx));
            root.setTransient(b);
         }

         viewId = root.getViewId();

         assert null != viewId;

         assert 0 < viewId.length();
      }

      if (this.locale != null && root != null) {
         try {
            root.setLocale(ComponentSupport.getLocale(ctx, this.locale));
         } catch (TagAttributeException var7) {
            Object result = this.locale.getObject(ctx);
            if (null == result) {
               Locale l = Locale.getDefault();
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, "Using {0} for locale because expression {1} returned null.", new Object[]{l, this.locale.toString()});
               }

               root.setLocale(l);
            }
         }
      }

      this.nextHandler.apply(ctx, parent);
   }

   static {
      LOGGER = FacesLogger.TAGLIB.getLogger();
      LISTENER_SIG = new Class[]{PhaseEvent.class};
   }
}
