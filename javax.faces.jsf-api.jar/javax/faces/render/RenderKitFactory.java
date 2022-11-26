package javax.faces.render;

import java.util.Iterator;
import javax.faces.context.FacesContext;

public abstract class RenderKitFactory {
   public static final String HTML_BASIC_RENDER_KIT = "HTML_BASIC";

   public abstract void addRenderKit(String var1, RenderKit var2);

   public abstract RenderKit getRenderKit(FacesContext var1, String var2);

   public abstract Iterator getRenderKitIds();
}
