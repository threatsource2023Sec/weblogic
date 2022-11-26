package javax.faces.render;

import java.io.IOException;
import javax.faces.FacesWrapper;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

public abstract class RendererWrapper extends Renderer implements FacesWrapper {
   private Renderer wrapped;

   /** @deprecated */
   @Deprecated
   public RendererWrapper() {
   }

   public RendererWrapper(Renderer wrapped) {
      this.wrapped = wrapped;
   }

   public Renderer getWrapped() {
      return this.wrapped;
   }

   public String convertClientId(FacesContext context, String clientId) {
      return this.getWrapped().convertClientId(context, clientId);
   }

   public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
      return this.getWrapped().getConvertedValue(context, component, submittedValue);
   }

   public void decode(FacesContext context, UIComponent component) {
      this.getWrapped().decode(context, component);
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.getWrapped().encodeBegin(context, component);
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      this.getWrapped().encodeChildren(context, component);
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.getWrapped().encodeEnd(context, component);
   }

   public boolean getRendersChildren() {
      return this.getWrapped().getRendersChildren();
   }
}
