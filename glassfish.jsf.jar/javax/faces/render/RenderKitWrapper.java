package javax.faces.render;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;
import javax.faces.FacesWrapper;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;

public abstract class RenderKitWrapper extends RenderKit implements FacesWrapper {
   private RenderKit wrapped;

   /** @deprecated */
   @Deprecated
   public RenderKitWrapper() {
   }

   public RenderKitWrapper(RenderKit wrapped) {
      this.wrapped = wrapped;
   }

   public RenderKit getWrapped() {
      return this.wrapped;
   }

   public void addRenderer(String family, String rendererType, Renderer renderer) {
      this.getWrapped().addRenderer(family, rendererType, renderer);
   }

   public ResponseStream createResponseStream(OutputStream out) {
      return this.getWrapped().createResponseStream(out);
   }

   public ResponseWriter createResponseWriter(Writer writer, String contentTypeList, String characterEncoding) {
      return this.getWrapped().createResponseWriter(writer, contentTypeList, characterEncoding);
   }

   public Renderer getRenderer(String family, String rendererType) {
      return this.getWrapped().getRenderer(family, rendererType);
   }

   public ResponseStateManager getResponseStateManager() {
      return this.getWrapped().getResponseStateManager();
   }

   public Iterator getComponentFamilies() {
      return this.getWrapped().getComponentFamilies();
   }

   public Iterator getRendererTypes(String componentFamily) {
      return this.getWrapped().getRendererTypes(componentFamily);
   }

   public void addClientBehaviorRenderer(String type, ClientBehaviorRenderer renderer) {
      this.getWrapped().addClientBehaviorRenderer(type, renderer);
   }

   public ClientBehaviorRenderer getClientBehaviorRenderer(String type) {
      return this.getWrapped().getClientBehaviorRenderer(type);
   }

   public Iterator getClientBehaviorRendererTypes() {
      return this.getWrapped().getClientBehaviorRendererTypes();
   }
}
