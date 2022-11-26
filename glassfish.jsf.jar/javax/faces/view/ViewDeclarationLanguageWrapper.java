package javax.faces.view;

import java.beans.BeanInfo;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.faces.FacesWrapper;
import javax.faces.application.Resource;
import javax.faces.application.ViewVisitOption;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public abstract class ViewDeclarationLanguageWrapper extends ViewDeclarationLanguage implements FacesWrapper {
   private ViewDeclarationLanguage wrapped;

   /** @deprecated */
   @Deprecated
   public ViewDeclarationLanguageWrapper() {
   }

   public ViewDeclarationLanguageWrapper(ViewDeclarationLanguage wrapped) {
      this.wrapped = wrapped;
   }

   public ViewDeclarationLanguage getWrapped() {
      return this.wrapped;
   }

   public UIViewRoot restoreView(FacesContext context, String viewId) {
      return this.getWrapped().restoreView(context, viewId);
   }

   public ViewMetadata getViewMetadata(FacesContext context, String viewId) {
      return this.getWrapped().getViewMetadata(context, viewId);
   }

   public UIViewRoot createView(FacesContext context, String viewId) {
      return this.getWrapped().createView(context, viewId);
   }

   public void buildView(FacesContext context, UIViewRoot root) throws IOException {
      this.getWrapped().buildView(context, root);
   }

   public void renderView(FacesContext context, UIViewRoot view) throws IOException {
      this.getWrapped().renderView(context, view);
   }

   public String getId() {
      return super.getId();
   }

   public void retargetAttachedObjects(FacesContext context, UIComponent topLevelComponent, List handlers) {
      this.getWrapped().retargetAttachedObjects(context, topLevelComponent, handlers);
   }

   public void retargetMethodExpressions(FacesContext context, UIComponent topLevelComponent) {
      this.getWrapped().retargetMethodExpressions(context, topLevelComponent);
   }

   public boolean viewExists(FacesContext context, String viewId) {
      return this.getWrapped().viewExists(context, viewId);
   }

   public Stream getViews(FacesContext context, String path, ViewVisitOption... options) {
      return this.getWrapped().getViews(context, path, options);
   }

   public Stream getViews(FacesContext context, String path, int maxDepth, ViewVisitOption... options) {
      return this.getWrapped().getViews(context, path, maxDepth, options);
   }

   public List calculateResourceLibraryContracts(FacesContext context, String viewId) {
      return this.getWrapped().calculateResourceLibraryContracts(context, viewId);
   }

   public UIComponent createComponent(FacesContext context, String taglibURI, String tagName, Map attributes) {
      return this.getWrapped().createComponent(context, taglibURI, tagName, attributes);
   }

   public BeanInfo getComponentMetadata(FacesContext context, Resource componentResource) {
      return this.getWrapped().getComponentMetadata(context, componentResource);
   }

   public Resource getScriptComponentResource(FacesContext context, Resource componentResource) {
      return this.getWrapped().getScriptComponentResource(context, componentResource);
   }

   public StateManagementStrategy getStateManagementStrategy(FacesContext context, String viewId) {
      return this.getWrapped().getStateManagementStrategy(context, viewId);
   }
}
