package javax.faces.application;

import java.io.IOException;
import java.util.Locale;
import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public abstract class ViewHandlerWrapper extends ViewHandler {
   protected abstract ViewHandler getWrapped();

   public String calculateCharacterEncoding(FacesContext context) {
      return this.getWrapped().calculateCharacterEncoding(context);
   }

   public Locale calculateLocale(FacesContext context) {
      return this.getWrapped().calculateLocale(context);
   }

   public String calculateRenderKitId(FacesContext context) {
      return this.getWrapped().calculateRenderKitId(context);
   }

   public UIViewRoot createView(FacesContext context, String viewId) {
      return this.getWrapped().createView(context, viewId);
   }

   public String getActionURL(FacesContext context, String viewId) {
      return this.getWrapped().getActionURL(context, viewId);
   }

   public String getResourceURL(FacesContext context, String path) {
      return this.getWrapped().getResourceURL(context, path);
   }

   public void initView(FacesContext context) throws FacesException {
      this.getWrapped().initView(context);
   }

   public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {
      this.getWrapped().renderView(context, viewToRender);
   }

   public UIViewRoot restoreView(FacesContext context, String viewId) {
      return this.getWrapped().restoreView(context, viewId);
   }

   public void writeState(FacesContext context) throws IOException {
      this.getWrapped().writeState(context);
   }
}
