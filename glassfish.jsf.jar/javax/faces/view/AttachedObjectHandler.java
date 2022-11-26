package javax.faces.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public interface AttachedObjectHandler {
   void applyAttachedObject(FacesContext var1, UIComponent var2);

   String getFor();
}
