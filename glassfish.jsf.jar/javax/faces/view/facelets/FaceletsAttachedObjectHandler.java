package javax.faces.view.facelets;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.AttachedObjectHandler;

public abstract class FaceletsAttachedObjectHandler extends DelegatingMetaTagHandler implements AttachedObjectHandler {
   public FaceletsAttachedObjectHandler(TagConfig config) {
      super(config);
   }

   protected final AttachedObjectHandler getAttachedObjectHandlerHelper() {
      return (AttachedObjectHandler)this.getTagHandlerDelegate();
   }

   public final void applyAttachedObject(FacesContext ctx, UIComponent parent) {
      this.getAttachedObjectHandlerHelper().applyAttachedObject(ctx, parent);
   }

   public final String getFor() {
      return this.getAttachedObjectHandlerHelper().getFor();
   }
}
