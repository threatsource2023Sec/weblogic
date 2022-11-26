package javax.faces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public interface Converter {
   Object getAsObject(FacesContext var1, UIComponent var2, String var3);

   String getAsString(FacesContext var1, UIComponent var2, Object var3);
}
