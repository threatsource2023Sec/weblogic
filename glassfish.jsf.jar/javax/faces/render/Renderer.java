package javax.faces.render;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

public abstract class Renderer {
   public static final String PASSTHROUGH_RENDERER_LOCALNAME_KEY = "elementName";

   public void decode(FacesContext context, UIComponent component) {
      if (null == context || null == component) {
         throw new NullPointerException();
      }
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      if (null == context || null == component) {
         throw new NullPointerException();
      }
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      if (context != null && component != null) {
         if (component.getChildCount() > 0) {
            Iterator kids = component.getChildren().iterator();

            while(kids.hasNext()) {
               UIComponent kid = (UIComponent)kids.next();
               kid.encodeAll(context);
            }
         }

      } else {
         throw new NullPointerException();
      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      if (null == context || null == component) {
         throw new NullPointerException();
      }
   }

   public String convertClientId(FacesContext context, String clientId) {
      if (context != null && clientId != null) {
         return clientId;
      } else {
         throw new NullPointerException();
      }
   }

   public boolean getRendersChildren() {
      return false;
   }

   public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
      if (context != null && component != null) {
         return submittedValue;
      } else {
         throw new NullPointerException();
      }
   }
}
