package javax.faces.application;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.faces.FacesException;
import javax.faces.FacesWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;

public abstract class ViewHandlerWrapper extends ViewHandler implements FacesWrapper {
   private ViewHandler wrapped;

   public ViewHandlerWrapper(ViewHandler wrapped) {
      this.wrapped = wrapped;
   }

   public ViewHandler getWrapped() {
      return this.wrapped;
   }

   public void initView(FacesContext context) throws FacesException {
      this.getWrapped().initView(context);
   }

   public UIViewRoot restoreView(FacesContext context, String viewId) {
      return this.getWrapped().restoreView(context, viewId);
   }

   public UIViewRoot createView(FacesContext context, String viewId) {
      return this.getWrapped().createView(context, viewId);
   }

   public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {
      this.getWrapped().renderView(context, viewToRender);
   }

   public String calculateCharacterEncoding(FacesContext context) {
      return this.getWrapped().calculateCharacterEncoding(context);
   }

   public Locale calculateLocale(FacesContext context) {
      return this.getWrapped().calculateLocale(context);
   }

   public String deriveViewId(FacesContext context, String requestViewId) {
      return this.getWrapped().deriveViewId(context, requestViewId);
   }

   public String deriveLogicalViewId(FacesContext context, String requestViewId) {
      return this.getWrapped().deriveLogicalViewId(context, requestViewId);
   }

   public String calculateRenderKitId(FacesContext context) {
      return this.getWrapped().calculateRenderKitId(context);
   }

   public String getActionURL(FacesContext context, String viewId) {
      return this.getWrapped().getActionURL(context, viewId);
   }

   public Set getProtectedViewsUnmodifiable() {
      return this.getWrapped().getProtectedViewsUnmodifiable();
   }

   public void addProtectedView(String urlPattern) {
      this.getWrapped().addProtectedView(urlPattern);
   }

   public boolean removeProtectedView(String urlPattern) {
      return this.getWrapped().removeProtectedView(urlPattern);
   }

   public String getRedirectURL(FacesContext context, String viewId, Map parameters, boolean includeViewParams) {
      return this.getWrapped().getRedirectURL(context, viewId, parameters, includeViewParams);
   }

   public String getBookmarkableURL(FacesContext context, String viewId, Map parameters, boolean includeViewParams) {
      return this.getWrapped().getBookmarkableURL(context, viewId, parameters, includeViewParams);
   }

   public String getResourceURL(FacesContext context, String path) {
      return this.getWrapped().getResourceURL(context, path);
   }

   public String getWebsocketURL(FacesContext context, String channel) {
      return this.getWrapped().getWebsocketURL(context, channel);
   }

   public ViewDeclarationLanguage getViewDeclarationLanguage(FacesContext context, String viewId) {
      return this.getWrapped().getViewDeclarationLanguage(context, viewId);
   }

   public Stream getViews(FacesContext context, String path, ViewVisitOption... options) {
      return this.getWrapped().getViews(context, path, options);
   }

   public Stream getViews(FacesContext context, String path, int maxDepth, ViewVisitOption... options) {
      return this.getWrapped().getViews(context, path, maxDepth, options);
   }

   public void writeState(FacesContext context) throws IOException {
      this.getWrapped().writeState(context);
   }

   /** @deprecated */
   @Deprecated
   public ViewHandlerWrapper() {
   }
}
