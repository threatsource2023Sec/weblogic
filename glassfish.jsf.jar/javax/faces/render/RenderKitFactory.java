package javax.faces.render;

import java.util.Iterator;
import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

public abstract class RenderKitFactory implements FacesWrapper {
   private RenderKitFactory wrapped;
   public static final String HTML_BASIC_RENDER_KIT = "HTML_BASIC";

   /** @deprecated */
   @Deprecated
   public RenderKitFactory() {
   }

   public RenderKitFactory(RenderKitFactory wrapped) {
      this.wrapped = wrapped;
   }

   public RenderKitFactory getWrapped() {
      return this.wrapped;
   }

   public abstract void addRenderKit(String var1, RenderKit var2);

   public abstract RenderKit getRenderKit(FacesContext var1, String var2);

   public abstract Iterator getRenderKitIds();
}
