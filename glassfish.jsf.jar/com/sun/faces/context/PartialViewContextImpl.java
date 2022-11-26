package com.sun.faces.context;

import com.sun.faces.component.visit.PartialVisitContext;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.HtmlUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ResourceHandler;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitContextFactory;
import javax.faces.component.visit.VisitContextWrapper;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;
import javax.faces.context.PartialViewContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseId;
import javax.faces.lifecycle.ClientWindow;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

public class PartialViewContextImpl extends PartialViewContext {
   private static Logger LOGGER;
   private boolean released;
   private PartialResponseWriter partialResponseWriter;
   private List executeIds;
   private Collection renderIds;
   private List evalScripts;
   private Boolean ajaxRequest;
   private Boolean partialRequest;
   private Boolean renderAll;
   private FacesContext ctx;
   private static final String ORIGINAL_WRITER = "com.sun.faces.ORIGINAL_WRITER";

   public PartialViewContextImpl(FacesContext ctx) {
      this.ctx = ctx;
   }

   public boolean isAjaxRequest() {
      this.assertNotReleased();
      if (this.ajaxRequest == null) {
         this.ajaxRequest = "partial/ajax".equals(this.ctx.getExternalContext().getRequestHeaderMap().get("Faces-Request"));
         if (!this.ajaxRequest) {
            this.ajaxRequest = "partial/ajax".equals(this.ctx.getExternalContext().getRequestParameterMap().get("Faces-Request"));
         }
      }

      return this.ajaxRequest;
   }

   public boolean isPartialRequest() {
      this.assertNotReleased();
      if (this.partialRequest == null) {
         this.partialRequest = this.isAjaxRequest() || "partial/process".equals(this.ctx.getExternalContext().getRequestHeaderMap().get("Faces-Request"));
      }

      return this.partialRequest;
   }

   public boolean isExecuteAll() {
      this.assertNotReleased();
      String execute = RenderKitUtils.PredefinedPostbackParameter.PARTIAL_EXECUTE_PARAM.getValue(this.ctx);
      return "@all".equals(execute);
   }

   public boolean isRenderAll() {
      this.assertNotReleased();
      if (this.renderAll == null) {
         String render = RenderKitUtils.PredefinedPostbackParameter.PARTIAL_RENDER_PARAM.getValue(this.ctx);
         this.renderAll = "@all".equals(render);
      }

      return this.renderAll;
   }

   public void setRenderAll(boolean renderAll) {
      this.renderAll = renderAll;
   }

   public boolean isResetValues() {
      Object value = RenderKitUtils.PredefinedPostbackParameter.PARTIAL_RESET_VALUES_PARAM.getValue(this.ctx);
      return null != value && "true".equals(value);
   }

   public void setPartialRequest(boolean isPartialRequest) {
      this.partialRequest = isPartialRequest;
   }

   public Collection getExecuteIds() {
      this.assertNotReleased();
      if (this.executeIds != null) {
         return this.executeIds;
      } else {
         this.executeIds = this.populatePhaseClientIds(RenderKitUtils.PredefinedPostbackParameter.PARTIAL_EXECUTE_PARAM);
         if (!this.executeIds.isEmpty()) {
            UIViewRoot root = this.ctx.getViewRoot();
            if (root.getFacetCount() > 0 && root.getFacet("javax_faces_metadata") != null) {
               this.executeIds.add(0, "javax_faces_metadata");
            }
         }

         return this.executeIds;
      }
   }

   public Collection getRenderIds() {
      this.assertNotReleased();
      if (this.renderIds != null) {
         return this.renderIds;
      } else {
         this.renderIds = this.populatePhaseClientIds(RenderKitUtils.PredefinedPostbackParameter.PARTIAL_RENDER_PARAM);
         return this.renderIds;
      }
   }

   public List getEvalScripts() {
      this.assertNotReleased();
      if (this.evalScripts == null) {
         this.evalScripts = new ArrayList(1);
      }

      return this.evalScripts;
   }

   public void processPartial(PhaseId phaseId) {
      PartialViewContext pvc = this.ctx.getPartialViewContext();
      Collection myExecuteIds = pvc.getExecuteIds();
      Collection myRenderIds = pvc.getRenderIds();
      UIViewRoot viewRoot = this.ctx.getViewRoot();
      PartialResponseWriter writer;
      if (phaseId != PhaseId.APPLY_REQUEST_VALUES && phaseId != PhaseId.PROCESS_VALIDATIONS && phaseId != PhaseId.UPDATE_MODEL_VALUES) {
         if (phaseId == PhaseId.RENDER_RESPONSE) {
            try {
               writer = pvc.getPartialResponseWriter();
               ResponseWriter orig = this.ctx.getResponseWriter();
               this.ctx.getAttributes().put("com.sun.faces.ORIGINAL_WRITER", orig);
               this.ctx.setResponseWriter(writer);
               ExternalContext exContext = this.ctx.getExternalContext();
               exContext.setResponseContentType("text/xml");
               exContext.addResponseHeader("Cache-Control", "no-cache");
               writer.startDocument();
               if (this.isResetValues()) {
                  viewRoot.resetValues(this.ctx, myRenderIds);
               }

               if (this.isRenderAll()) {
                  this.renderAll(this.ctx, viewRoot);
                  this.renderState(this.ctx);
                  writer.endDocument();
                  return;
               }

               this.renderComponentResources(this.ctx, viewRoot);
               if (myRenderIds != null && !myRenderIds.isEmpty()) {
                  this.processComponents(viewRoot, phaseId, myRenderIds, this.ctx);
               }

               this.renderState(this.ctx);
               this.renderEvalScripts(this.ctx);
               writer.endDocument();
            } catch (IOException var9) {
               this.cleanupAfterView();
            } catch (RuntimeException var10) {
               this.cleanupAfterView();
               throw var10;
            }
         }
      } else {
         if (myExecuteIds == null || myExecuteIds.isEmpty()) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "No execute and render identifiers specified.  Skipping component processing.");
            }

            return;
         }

         try {
            this.processComponents(viewRoot, phaseId, myExecuteIds, this.ctx);
         } catch (Exception var11) {
            if (LOGGER.isLoggable(Level.INFO)) {
               LOGGER.log(Level.INFO, var11.toString(), var11);
            }

            throw new FacesException(var11);
         }

         if (phaseId == PhaseId.APPLY_REQUEST_VALUES) {
            writer = pvc.getPartialResponseWriter();
            this.ctx.setResponseWriter(writer);
         }
      }

   }

   public PartialResponseWriter getPartialResponseWriter() {
      this.assertNotReleased();
      if (this.partialResponseWriter == null) {
         this.partialResponseWriter = new DelayedInitPartialResponseWriter(this);
      }

      return this.partialResponseWriter;
   }

   public void release() {
      this.released = true;
      this.ajaxRequest = null;
      this.renderAll = null;
      this.partialResponseWriter = null;
      this.executeIds = null;
      this.renderIds = null;
      this.evalScripts = null;
      this.ctx = null;
      this.partialRequest = null;
   }

   private List populatePhaseClientIds(RenderKitUtils.PredefinedPostbackParameter parameterName) {
      String param = parameterName.getValue(this.ctx);
      if (param == null) {
         return new ArrayList();
      } else {
         Map appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
         String[] pcs = Util.split(appMap, param, "[ \t]+");
         return pcs != null && pcs.length != 0 ? new ArrayList(Arrays.asList(pcs)) : new ArrayList();
      }
   }

   private void processComponents(UIComponent component, PhaseId phaseId, Collection phaseClientIds, FacesContext context) throws IOException {
      EnumSet hints = EnumSet.of(VisitHint.SKIP_UNRENDERED, VisitHint.EXECUTE_LIFECYCLE);
      VisitContextFactory visitContextFactory = (VisitContextFactory)FactoryFinder.getFactory("javax.faces.component.visit.VisitContextFactory");
      VisitContext visitContext = visitContextFactory.getVisitContext(context, phaseClientIds, hints);
      PhaseAwareVisitCallback visitCallback = new PhaseAwareVisitCallback(this.ctx, phaseId);
      component.visitTree(visitContext, visitCallback);
      PartialVisitContext partialVisitContext = unwrapPartialVisitContext(visitContext);
      if (partialVisitContext != null && LOGGER.isLoggable(Level.FINER) && !partialVisitContext.getUnvisitedClientIds().isEmpty()) {
         Collection unvisitedClientIds = partialVisitContext.getUnvisitedClientIds();
         StringBuilder builder = new StringBuilder();
         Iterator var12 = unvisitedClientIds.iterator();

         while(var12.hasNext()) {
            String cur = (String)var12.next();
            builder.append(cur).append(" ");
         }

         LOGGER.log(Level.FINER, "jsf.context.partial_visit_context_unvisited_children", new Object[]{builder.toString()});
      }

   }

   private static PartialVisitContext unwrapPartialVisitContext(VisitContext visitContext) {
      if (visitContext == null) {
         return null;
      } else if (visitContext instanceof PartialVisitContext) {
         return (PartialVisitContext)visitContext;
      } else {
         return visitContext instanceof VisitContextWrapper ? unwrapPartialVisitContext(((VisitContextWrapper)visitContext).getWrapped()) : null;
      }
   }

   private void renderAll(FacesContext context, UIViewRoot viewRoot) throws IOException {
      PartialViewContext pvc = context.getPartialViewContext();
      PartialResponseWriter writer = pvc.getPartialResponseWriter();
      Iterator var5;
      UIComponent uiComponent;
      if (!(viewRoot instanceof NamingContainer)) {
         writer.startUpdate("javax.faces.ViewRoot");
         if (viewRoot.getChildCount() > 0) {
            var5 = viewRoot.getChildren().iterator();

            while(var5.hasNext()) {
               uiComponent = (UIComponent)var5.next();
               uiComponent.encodeAll(context);
            }
         }

         writer.endUpdate();
      } else {
         writer.startUpdate(viewRoot.getClientId(context));
         viewRoot.encodeBegin(context);
         if (viewRoot.getChildCount() > 0) {
            var5 = viewRoot.getChildren().iterator();

            while(var5.hasNext()) {
               uiComponent = (UIComponent)var5.next();
               uiComponent.encodeAll(context);
            }
         }

         viewRoot.encodeEnd(context);
         writer.endUpdate();
      }

   }

   private void renderComponentResources(FacesContext context, UIViewRoot viewRoot) throws IOException {
      ResourceHandler resourceHandler = context.getApplication().getResourceHandler();
      PartialResponseWriter writer = context.getPartialViewContext().getPartialResponseWriter();
      boolean updateStarted = false;
      Iterator var6 = viewRoot.getComponentResources(context).iterator();

      while(var6.hasNext()) {
         UIComponent resource = (UIComponent)var6.next();
         String name = (String)resource.getAttributes().get("name");
         String library = (String)resource.getAttributes().get("library");
         if (resource.getChildCount() == 0 && resourceHandler.getRendererTypeForResourceName(name) != null && !resourceHandler.isResourceRendered(context, name, library)) {
            if (!updateStarted) {
               writer.startUpdate("javax.faces.Resource");
               updateStarted = true;
            }

            resource.encodeAll(context);
         }
      }

      if (updateStarted) {
         writer.endUpdate();
      }

   }

   private void renderState(FacesContext context) throws IOException {
      PartialViewContext pvc = context.getPartialViewContext();
      PartialResponseWriter writer = pvc.getPartialResponseWriter();
      String viewStateId = Util.getViewStateId(context);
      writer.startUpdate(viewStateId);
      String state = context.getApplication().getStateManager().getViewState(context);
      writer.write(state);
      writer.endUpdate();
      ClientWindow window = context.getExternalContext().getClientWindow();
      if (null != window) {
         String clientWindowId = Util.getClientWindowId(context);
         writer.startUpdate(clientWindowId);
         writer.writeText(window.getId(), (String)null);
         writer.endUpdate();
      }

   }

   private void renderEvalScripts(FacesContext context) throws IOException {
      PartialViewContext pvc = context.getPartialViewContext();
      PartialResponseWriter writer = pvc.getPartialResponseWriter();
      Iterator var4 = pvc.getEvalScripts().iterator();

      while(var4.hasNext()) {
         String evalScript = (String)var4.next();
         writer.startEval();
         writer.write(evalScript);
         writer.endEval();
      }

   }

   private PartialResponseWriter createPartialResponseWriter() {
      ExternalContext extContext = this.ctx.getExternalContext();
      String encoding = extContext.getRequestCharacterEncoding();
      extContext.setResponseCharacterEncoding(encoding);
      ResponseWriter responseWriter = null;
      Writer out = null;

      try {
         out = extContext.getResponseOutputWriter();
      } catch (IOException var8) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, var8.toString(), var8);
         }
      }

      if (out != null) {
         UIViewRoot viewRoot = this.ctx.getViewRoot();
         if (viewRoot != null) {
            responseWriter = this.ctx.getRenderKit().createResponseWriter(out, "text/xml", encoding);
         } else {
            RenderKitFactory factory = (RenderKitFactory)FactoryFinder.getFactory("javax.faces.render.RenderKitFactory");
            RenderKit renderKit = factory.getRenderKit(this.ctx, "HTML_BASIC");
            responseWriter = renderKit.createResponseWriter(out, "text/xml", encoding);
         }
      }

      return responseWriter instanceof PartialResponseWriter ? (PartialResponseWriter)responseWriter : new PartialResponseWriter(responseWriter);
   }

   private void cleanupAfterView() {
      ResponseWriter orig = (ResponseWriter)this.ctx.getAttributes().get("com.sun.faces.ORIGINAL_WRITER");

      assert null != orig;

      this.ctx.setResponseWriter(orig);
   }

   private void assertNotReleased() {
      if (this.released) {
         throw new IllegalStateException();
      }
   }

   static {
      LOGGER = FacesLogger.CONTEXT.getLogger();
   }

   private static final class DelayedInitPartialResponseWriter extends PartialResponseWriter {
      private ResponseWriter writer;
      private PartialViewContextImpl ctx;

      public DelayedInitPartialResponseWriter(PartialViewContextImpl ctx) {
         super((ResponseWriter)null);
         this.ctx = ctx;
         ExternalContext extCtx = ctx.ctx.getExternalContext();
         extCtx.setResponseContentType("text/xml");
         extCtx.setResponseCharacterEncoding(extCtx.getRequestCharacterEncoding());
         extCtx.setResponseBufferSize(ctx.ctx.getExternalContext().getResponseBufferSize());
      }

      public void write(String text) throws IOException {
         HtmlUtils.writeUnescapedTextForXML(this.getWrapped(), text);
      }

      public ResponseWriter getWrapped() {
         if (this.writer == null) {
            this.writer = this.ctx.createPartialResponseWriter();
         }

         return this.writer;
      }
   }

   private static class PhaseAwareVisitCallback implements VisitCallback {
      private PhaseId curPhase;
      private FacesContext ctx;

      private PhaseAwareVisitCallback(FacesContext ctx, PhaseId curPhase) {
         this.ctx = ctx;
         this.curPhase = curPhase;
      }

      public VisitResult visit(VisitContext context, UIComponent comp) {
         try {
            if (this.curPhase == PhaseId.APPLY_REQUEST_VALUES) {
               comp.processDecodes(this.ctx);
            } else if (this.curPhase == PhaseId.PROCESS_VALIDATIONS) {
               comp.processValidators(this.ctx);
            } else if (this.curPhase == PhaseId.UPDATE_MODEL_VALUES) {
               comp.processUpdates(this.ctx);
            } else {
               if (this.curPhase != PhaseId.RENDER_RESPONSE) {
                  throw new IllegalStateException("I18N: Unexpected PhaseId passed to  PhaseAwareContextCallback: " + this.curPhase.toString());
               }

               PartialResponseWriter writer = this.ctx.getPartialViewContext().getPartialResponseWriter();
               writer.startUpdate(comp.getClientId(this.ctx));
               comp.encodeAll(this.ctx);
               writer.endUpdate();
            }
         } catch (IOException var4) {
            if (PartialViewContextImpl.LOGGER.isLoggable(Level.SEVERE)) {
               PartialViewContextImpl.LOGGER.severe(var4.toString());
            }

            if (PartialViewContextImpl.LOGGER.isLoggable(Level.FINE)) {
               PartialViewContextImpl.LOGGER.log(Level.FINE, var4.toString(), var4);
            }

            throw new FacesException(var4);
         }

         return VisitResult.REJECT;
      }

      // $FF: synthetic method
      PhaseAwareVisitCallback(FacesContext x0, PhaseId x1, Object x2) {
         this(x0, x1);
      }
   }
}
