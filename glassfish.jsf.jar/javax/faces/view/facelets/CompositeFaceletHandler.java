package javax.faces.view.facelets;

import java.io.IOException;
import javax.faces.component.UIComponent;

public final class CompositeFaceletHandler implements FaceletHandler {
   private final FaceletHandler[] children;
   private final int len;

   public CompositeFaceletHandler(FaceletHandler[] children) {
      this.children = children;
      this.len = children.length;
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      for(int i = 0; i < this.len; ++i) {
         this.children[i].apply(ctx, parent);
      }

   }

   public FaceletHandler[] getHandlers() {
      return this.children;
   }
}
