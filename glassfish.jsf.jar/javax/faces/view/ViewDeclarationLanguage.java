package javax.faces.view;

import java.beans.BeanInfo;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.faces.application.Resource;
import javax.faces.application.ResourceVisitOption;
import javax.faces.application.ViewVisitOption;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public abstract class ViewDeclarationLanguage {
   public static final String JSP_VIEW_DECLARATION_LANGUAGE_ID = "java.faces.JSP";
   public static final String FACELETS_VIEW_DECLARATION_LANGUAGE_ID = "java.faces.Facelets";

   public abstract UIViewRoot restoreView(FacesContext var1, String var2);

   public abstract ViewMetadata getViewMetadata(FacesContext var1, String var2);

   public abstract UIViewRoot createView(FacesContext var1, String var2);

   public abstract void buildView(FacesContext var1, UIViewRoot var2) throws IOException;

   public abstract void renderView(FacesContext var1, UIViewRoot var2) throws IOException;

   public abstract BeanInfo getComponentMetadata(FacesContext var1, Resource var2);

   public abstract Resource getScriptComponentResource(FacesContext var1, Resource var2);

   public UIComponent createComponent(FacesContext context, String taglibURI, String tagName, Map attributes) {
      return null;
   }

   public void retargetAttachedObjects(FacesContext context, UIComponent topLevelComponent, List handlers) {
   }

   public void retargetMethodExpressions(FacesContext context, UIComponent topLevelComponent) {
   }

   public List calculateResourceLibraryContracts(FacesContext context, String viewId) {
      return null;
   }

   public abstract StateManagementStrategy getStateManagementStrategy(FacesContext var1, String var2);

   public boolean viewExists(FacesContext context, String viewId) {
      return context.getApplication().getResourceHandler().createViewResource(context, viewId) != null;
   }

   public Stream getViews(FacesContext facesContext, String path, ViewVisitOption... options) {
      return facesContext.getApplication().getResourceHandler().getViewResources(facesContext, path, ResourceVisitOption.TOP_LEVEL_VIEWS_ONLY);
   }

   public Stream getViews(FacesContext facesContext, String path, int maxDepth, ViewVisitOption... options) {
      return facesContext.getApplication().getResourceHandler().getViewResources(facesContext, path, maxDepth, ResourceVisitOption.TOP_LEVEL_VIEWS_ONLY);
   }

   public String getId() {
      return this.getClass().getName();
   }
}
