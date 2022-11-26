package javax.faces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public interface Converter {
   String DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE_PARAM_NAME = "javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE";

   Object getAsObject(FacesContext var1, UIComponent var2, String var3);

   String getAsString(FacesContext var1, UIComponent var2, Object var3);
}
