package javax.faces.view.facelets;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class Facelet {
   public abstract void apply(FacesContext var1, UIComponent var2) throws IOException;
}
